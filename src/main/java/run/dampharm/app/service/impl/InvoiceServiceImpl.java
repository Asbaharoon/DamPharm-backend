package run.dampharm.app.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import freemarker.template.TemplateException;
import run.dampharm.app.domain.Invoice;
import run.dampharm.app.domain.ItemInvoice;
import run.dampharm.app.model.InvoiceFilter;
import run.dampharm.app.model.Mail;
import run.dampharm.app.model.Mail.EmailAttachment;
import run.dampharm.app.pdf.CTemplate;
import run.dampharm.app.pdf.PDFParserService;
import run.dampharm.app.pdf.TemplateRenderService;
import run.dampharm.app.repository.IInvoiceDao;
import run.dampharm.app.secuirty.UserPrinciple;
import run.dampharm.app.service.IInvoiceService;
import run.dampharm.app.service.IProductService;
import run.dampharm.app.service.MailService;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

	@Autowired
	private IInvoiceDao invoiceDao;

	@Autowired
	private IProductService productService;

	@Autowired
	private PDFParserService pdfParser;

	@Autowired
	private TemplateRenderService templateRenderService;

	@Autowired
	private MailService mailService;

	@Override
	public List<Invoice> findAll(long createdBy) {
		return invoiceDao.findByCreatedBy(createdBy);
	}

	@Override
	public Page<Invoice> findByCreatedBy(Long createdBy, Pageable pageable) {

		Page<Invoice> dtoPage = invoiceDao.findByCreatedBy(createdBy, pageable);

		return dtoPage;
	}

	@Override
	public Page<Invoice> findByCreatedByAndIdOrCustomer(Long createdBy, InvoiceFilter filter, Pageable pageable) {

//		Page<Invoice> dtoPage = invoiceDao.findByCreatedByAndIdOrCustomer(createdBy, filter.getId(),
//				filter.getCustomer(), pageable);

		/* Build Search object */
		Invoice invoice = new Invoice();
		invoice.setCreatedBy(createdBy);
		invoice.setId(filter.getId());
		invoice.setCustomer(filter.getCustomer());

		/* Build Example and ExampleMatcher object */
		ExampleMatcher customExampleMatcher = ExampleMatcher.matching()
				.withMatcher("createdBy", ExampleMatcher.GenericPropertyMatchers.exact())
				.withMatcher("id", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("customer", ExampleMatcher.GenericPropertyMatchers.exact());

		Example<Invoice> invoiceExample = Example.of(invoice, customExampleMatcher);

		Page<Invoice> dtoPage = invoiceDao.findAll(invoiceExample, pageable);
		return dtoPage;
	}

	@Override
	public Invoice save(UserPrinciple currentUser, Invoice invoice) {
		List<ItemInvoice> items = invoice.getItems();
		items.forEach(item -> {
			item.setProduct(productService.updateAvailableQuantity(item.getProduct()));
		});
		invoice.setItems(items);
		invoice = invoiceDao.save(invoice);

		if (Objects.nonNull(invoice) && Objects.nonNull(invoice.getCustomer())
				&& Objects.nonNull(invoice.getCustomer().getEmail())) {
			Mail mail = new Mail();
			mail.setSenderName("DamPharm");
			mail.setMailFrom(currentUser.getEmail());
			mail.setMailTo(invoice.getCustomer().getEmail());
			mail.setMailSubject("تم إنشاء فاتورة بنجاح رقم : " + invoice.getId());
			mail.setMailContent("شكرا لإستخدامك منتجات دام فارم , مرفق لسيادتكم الفاتورة ف صيغة PDF ,  نتمنى لكم دوام الصحة والعافية");

			try {
				ByteArrayOutputStream pdfOutput = getInvoicePdfAsByteArray(currentUser, invoice);

				EmailAttachment attachment = new EmailAttachment();
				attachment.setName(invoice.getCustomer().getName() + "_" + invoice.getId() + ".pdf");
				attachment.setContentType("application/pdf");

				attachment.setContent(pdfOutput.toByteArray());
				mail.getAttachments().add(attachment);
				mailService.sendEmail(mail);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return invoice;
	}

	@Override
	public void delete(String id) {
		invoiceDao.deleteById(id);
	}

	@Override
	public Invoice findByIdAndCreatedBy(Long createdBy, String id) {
		return invoiceDao.findByIdAndCreatedBy(id, createdBy);
	}

	@Override
	public ByteArrayOutputStream getInvoicePdfAsByteArray(UserPrinciple currentUser, Invoice invoice) {
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

		try {
			ByteArrayOutputStream pdfOutput = pdfParser.parseTemplate(cTemplate);
			return pdfOutput;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
