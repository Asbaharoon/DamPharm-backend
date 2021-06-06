package run.dampharm.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import run.dampharm.app.domain.Invoice;
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

	@GetMapping("/all")
	public List<Invoice> findAll(@CurrentUser UserPrinciple user) {
		return invoiceService.findAll(user.getId());
	}

	@PostMapping
	public Invoice create(@RequestBody Invoice invoice) {
		log.info("Create Invoice:{}", invoice.getTotal());
		invoice.setTotalPrice(invoice.getTotal());
		return invoiceService.save(invoice);
	}
}
