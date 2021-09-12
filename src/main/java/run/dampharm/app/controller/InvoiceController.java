package run.dampharm.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import run.dampharm.app.domain.Invoice;
import run.dampharm.app.model.UserDto;
import run.dampharm.app.pdf.CTemplate;
import run.dampharm.app.pdf.PDFParserService;
import run.dampharm.app.pdf.TemplateRenderService;
import run.dampharm.app.secuirty.CurrentUser;
import run.dampharm.app.secuirty.UserPrinciple;
import run.dampharm.app.service.IInvoiceService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/invoice")
@Slf4j
public class InvoiceController {
	@Autowired
	private IInvoiceService invoiceService;

	@Autowired
	private PDFParserService pdfParser;

	@Autowired
	private TemplateRenderService templateRenderService;

	@GetMapping
	public ResponseEntity<Page<Invoice>> list(@CurrentUser UserPrinciple user,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
		Page<Invoice> pageResult = invoiceService.findByCreatedBy(user.getId(), pageRequest);

		return ResponseEntity.ok(pageResult);

	}

	@GetMapping("/all")
	public List<Invoice> findAll(@CurrentUser UserPrinciple user) {
		return invoiceService.findAll(user.getId());
	}

	@PostMapping
	public Invoice create(@CurrentUser UserPrinciple user, @RequestBody Invoice invoice) {
		log.info("Create Invoice:{}", invoice.getTotal());
		invoice.setTotalPrice(invoice.getTotal());
		return invoiceService.save(user.getId(), invoice);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		invoiceService.delete(id);
	}

	@PostMapping("/download/{id}")
	public ResponseEntity<?> downloadInvoice(@CurrentUser UserPrinciple currentUser, @PathVariable("id") String id,
			HttpServletResponse response) {

		Invoice invoice = invoiceService.findByIdAndCreatedBy(currentUser.getId(), id);
		CTemplate cTemplate = new CTemplate();
		cTemplate.setTemplateName("test");
		try {
			Map<String, Object> model = new HashMap<>();
			model.put("currentUser", currentUser);
			model.put("invoice", invoice);
			cTemplate.setTemplateRendered(templateRenderService.getTemplateContent(model));
		} catch (IOException | TemplateException e1) {
			e1.printStackTrace();
		}

		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "attachment;filename=" + cTemplate.getTemplateName() + ".pdf");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			pdfParser.parseTemplate(cTemplate, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(null);
	}
}
