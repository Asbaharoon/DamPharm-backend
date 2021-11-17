package run.dampharm.app.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import run.dampharm.app.domain.Invoice;
import run.dampharm.app.domain.User;
import run.dampharm.app.exception.ResourceNotFoundException;
import run.dampharm.app.repository.UserRepository;
import run.dampharm.app.secuirty.UserPrinciple;
import run.dampharm.app.service.IInvoiceService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/public")
@Slf4j
public class InvoicePuplicController {

	@Autowired
	private IInvoiceService invoiceService;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadInvoice(@PathVariable("id") String id, HttpServletResponse response)
			throws IOException, JRException {
		Invoice invoice = null;
		try {
			invoice = invoiceService.findById(id);
		} catch (ResourceNotFoundException e1) {
			e1.printStackTrace();
		}

		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "attachment;filename=" + invoice.getId() + ".pdf");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			Optional<User> user = userRepository.findById(invoice.getCreatedBy());
			if (user.isPresent()) {
				ByteArrayOutputStream pdfOutput = invoiceService
						.getInvoicePdfAsByteArray(UserPrinciple.build(user.get()), invoice);
				response.getOutputStream().write(pdfOutput.toByteArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(null);
	}
}
