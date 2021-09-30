package run.dampharm.app.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import run.dampharm.app.domain.Invoice;
import run.dampharm.app.exception.ServiceException;
import run.dampharm.app.model.InvoiceFilter;
import run.dampharm.app.model.InvoiceStatus;
import run.dampharm.app.model.InvoiceStatusUpdate;
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

	@GetMapping
	public ResponseEntity<Page<Invoice>> list(@CurrentUser UserPrinciple user,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
		Page<Invoice> pageResult = invoiceService.findByCreatedBy(user.getId(), pageRequest);

		return ResponseEntity.ok(pageResult);

	}

	@PostMapping("/filter")
	public ResponseEntity<Page<Invoice>> filter(@CurrentUser UserPrinciple user, @RequestBody InvoiceFilter filter,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
		Page<Invoice> pageResult = invoiceService.findByCreatedByAndIdOrCustomer(user.getId(), filter, pageRequest);

		return ResponseEntity.ok(pageResult);

	}

	@GetMapping("/all")
	public List<Invoice> findAll(@CurrentUser UserPrinciple user) {
		return invoiceService.findAll(user.getId());
	}

	@PostMapping
	public Invoice create(@CurrentUser UserPrinciple currentUser, @RequestBody Invoice invoice) {
		log.info("Create Invoice:{}", invoice.getTotal());
		invoice.setTotalPrice(invoice.getTotal());
		invoice.setStatus(InvoiceStatus.NEW);
		Invoice createdInvoice = invoiceService.save(currentUser, invoice);

		return createdInvoice;
	}

	@PostMapping("/update-status")
	public Invoice updateStatus(@CurrentUser UserPrinciple currentUser, @RequestBody InvoiceStatusUpdate statusUpdateRq)
			throws ServiceException {
		log.info("Update Invoice status:{}", statusUpdateRq.getStatus());
		Invoice updatedInvoice = invoiceService.updateStatus(currentUser, statusUpdateRq);

		return updatedInvoice;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		invoiceService.delete(id);
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadInvoice(@CurrentUser UserPrinciple currentUser, @PathVariable("id") String id,
			HttpServletResponse response) throws IOException, JRException {
		Invoice invoice = invoiceService.findByIdAndCreatedBy(currentUser.getId(), id);

		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "attachment;filename=" + invoice.getId() + ".pdf");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			ByteArrayOutputStream pdfOutput = invoiceService.getInvoicePdfAsByteArray(currentUser, invoice);
			response.getOutputStream().write(pdfOutput.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(null);
	}

	@PostMapping("/download/statment")
	public ResponseEntity<?> downloadStatment(@CurrentUser UserPrinciple currentUser, @RequestBody InvoiceFilter filter,
			HttpServletResponse response) {
		List<Invoice> invoices = invoiceService.downloadStatment(currentUser.getId(), filter);

		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "attachment;filename=statment.pdf");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			ByteArrayOutputStream pdfOutput = invoiceService.getStatmentAsByteStream(currentUser, filter, invoices);
			response.getOutputStream().write(pdfOutput.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(null);
	}
}
