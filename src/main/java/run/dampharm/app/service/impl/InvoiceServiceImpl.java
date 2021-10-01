package run.dampharm.app.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import freemarker.template.TemplateException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JsonDataSource;
import run.dampharm.app.domain.Invoice;
import run.dampharm.app.domain.ItemInvoice;
import run.dampharm.app.exception.ServiceException;
import run.dampharm.app.model.InvoiceFilter;
import run.dampharm.app.model.InvoiceStatusUpdate;
import run.dampharm.app.model.Mail;
import run.dampharm.app.model.Mail.EmailAttachment;
import run.dampharm.app.model.StatmentReport;
import run.dampharm.app.pdf.TemplateRenderService;
import run.dampharm.app.repository.IInvoiceDao;
import run.dampharm.app.search.GenericSpecificationsBuilder;
import run.dampharm.app.search.SpecificationFactory;
import run.dampharm.app.secuirty.UserPrinciple;
import run.dampharm.app.service.IInvoiceService;
import run.dampharm.app.service.IProductService;
import run.dampharm.app.service.MailService;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

	@Autowired
	private IInvoiceDao invoiceDao;

	@Autowired
	InvoiceStatusService invoiceStatusService;

	@Autowired
	private IProductService productService;

	@Autowired
	private MailService mailService;

	@Autowired
	private SpecificationFactory<Invoice> userSpecificationFactory;

	@Autowired
	private TemplateRenderService templateService;

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
		GenericSpecificationsBuilder<Invoice> builder = new GenericSpecificationsBuilder<>();

		if (StringUtils.isNotEmpty(filter.getId())) {
			builder.with(userSpecificationFactory.isEqual("id", filter.getId()));
		}
		if (Objects.nonNull(filter.getCustomer())) {
			builder.with(userSpecificationFactory.isEqual("customer", filter.getCustomer()));
		}

		if (Objects.nonNull(filter.getStatusList()) && !filter.getStatusList().isEmpty()) {
			builder.with(userSpecificationFactory.in("status", filter.getStatusList()));
		}

		if (StringUtils.isNotEmpty(filter.getState())) {
			builder.with(userSpecificationFactory.isLike("customer.state", filter.getState()));
		}

		if (Objects.nonNull(filter.getFromDate()) && Objects.nonNull(filter.getToDate())) {
			builder.with(userSpecificationFactory.isBetween("createdAt", filter.getFromDate(),
					addDays(filter.getToDate(), 1)));
		}

		Page<Invoice> dtoPage = invoiceDao.findAll(builder.build(), pageable);

		return dtoPage;
	}

	public List<Invoice> downloadStatment(Long createdBy, InvoiceFilter filter) {
		GenericSpecificationsBuilder<Invoice> builder = new GenericSpecificationsBuilder<>();

		if (StringUtils.isNotEmpty(filter.getId())) {
			builder.with(userSpecificationFactory.isEqual("id", filter.getId()));
		}
		if (Objects.nonNull(filter.getCustomer())) {
			builder.with(userSpecificationFactory.isEqual("customer", filter.getCustomer()));
		}

		if (Objects.nonNull(filter.getStatusList()) && !filter.getStatusList().isEmpty()) {
			builder.with(userSpecificationFactory.in("status", filter.getStatusList()));
		}

		if (StringUtils.isNotEmpty(filter.getState())) {
			builder.with(userSpecificationFactory.isLike("customer.state", filter.getState()));
		}

		if (Objects.nonNull(filter.getFromDate()) && Objects.nonNull(filter.getToDate())) {
			builder.with(userSpecificationFactory.isBetween("createdAt", filter.getFromDate(),
					addDays(filter.getToDate(), 1)));
		}

		List<Invoice> dtoPage = invoiceDao.findAll(builder.build());

		return dtoPage;
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	@Override
	public Invoice save(UserPrinciple currentUser, Invoice invoice) {
		List<ItemInvoice> items = invoice.getItems();
		items.forEach(item -> {
			item.setProduct(productService.updateAvailableQuantity(item.getProduct()));
			item.setTotalAfterDiscount(item.itemTotalAfterDiscount());
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
			mail.setHtml(true);

			String content = "";
			try {
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("currentUser", currentUser);
				params.put("message", "لقد تم إنشاء فاتوره خاصة بك , يمكنك مراجعة الفاتورة ف الملف المرفق بالبريد الإلكترونى , شكرأ لإستخدامك ونحن سعيدون بالتواصل معك على مدار 24 ساعة");
				content = templateService.getTemplateContent("emails/invoice.ftlh", params);
			} catch (IOException | TemplateException e1) {
				e1.printStackTrace();
			}
			mail.setMailContent(content);

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
	public Invoice updateStatus(UserPrinciple currentUser, InvoiceStatusUpdate rq) throws ServiceException {

		Invoice invoice = invoiceDao.findByIdAndCreatedBy(rq.getId(), currentUser.getId());

		invoice = invoiceStatusService.updateInvoiceStatus(rq, invoice);

		invoice = invoiceDao.save(invoice);

		if (Objects.nonNull(invoice) && Objects.nonNull(invoice.getCustomer())
				&& Objects.nonNull(invoice.getCustomer().getEmail())) {
			Mail mail = new Mail();
			mail.setSenderName("DamPharm");
			mail.setMailFrom(currentUser.getEmail());
			mail.setMailTo(invoice.getCustomer().getEmail());
			mail.setMailSubject("تم إنشاء فاتورة بنجاح رقم : " + invoice.getId());
			mail.setMailContent(
					"شكرا لإستخدامك منتجات دام فارم , مرفق لسيادتكم الفاتورة ف صيغة PDF ,  نتمنى لكم دوام الصحة والعافية");

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
		ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
		try {

			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
			String invoiceDate = sm.format(invoice.getCreatedAt());
			String invoicePaidDate = "";

			if (Objects.nonNull(invoice.getPaidAt()))
				invoicePaidDate = sm.format(invoice.getPaidAt());

			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(invoice);

			ClassPathResource cpr = new ClassPathResource("templates/dampharm.jasper");

			ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsonString.getBytes());
			JsonDataSource ds = new JsonDataSource(jsonDataStream);

			BufferedImage qrImg = null;
			if (currentUser.isQr()) {
				QRCodeWriter qrCodeWriter = new QRCodeWriter();
				BitMatrix bitMatrix = qrCodeWriter.encode(invoice.getId(), BarcodeFormat.QR_CODE, 250, 250);

				qrImg = MatrixToImageWriter.toBufferedImage(bitMatrix);
			}

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("qrImg", qrImg);
			parameters.put("invoiceDate", invoiceDate);
			parameters.put("invoicePaidDate", invoicePaidDate);
			parameters.put("logo", currentUser.getCompanyLogo());
			parameters.put("companyName", currentUser.getCompanyName());
			parameters.put("address", currentUser.getAddress());
			parameters.put("phone", currentUser.getPhone());
			parameters.put("email", currentUser.getEmail());

			JasperPrint jasperPrint = JasperFillManager.fillReport(cpr.getInputStream(), parameters, ds);

			JasperExportManager.exportReportToPdfStream(jasperPrint, pdfOutput);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pdfOutput;
	}

	@Override
	public ByteArrayOutputStream getStatmentAsByteStream(UserPrinciple currentUser, InvoiceFilter filter,
			List<Invoice> invoices) {
		ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
		try {

			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
			String filterFromDate = sm.format(filter.getFromDate());
			String filterToDate = sm.format(filter.getToDate());

			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(new StatmentReport(invoices));

			ClassPathResource cpr = new ClassPathResource("templates/dampharm-statment.jasper");

			ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(jsonString.getBytes());
			JsonDataSource ds = new JsonDataSource(jsonDataStream);

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("filterFromDate", filterFromDate);
			parameters.put("filterToDate", filterToDate);
			parameters.put("logo", currentUser.getCompanyLogo());
			parameters.put("companyName", currentUser.getCompanyName());
			parameters.put("address", currentUser.getAddress());
			parameters.put("phone", currentUser.getPhone());
			parameters.put("email", currentUser.getEmail());

			JasperPrint jasperPrint = JasperFillManager.fillReport(cpr.getInputStream(), parameters, ds);

			JasperExportManager.exportReportToPdfStream(jasperPrint, pdfOutput);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pdfOutput;
	}

}
