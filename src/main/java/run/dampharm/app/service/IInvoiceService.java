package run.dampharm.app.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import run.dampharm.app.domain.Invoice;
import run.dampharm.app.model.InvoiceFilter;
import run.dampharm.app.secuirty.UserPrinciple;

public interface IInvoiceService {
	public List<Invoice> findAll(long createdBy);

	public Page<Invoice> findByCreatedBy(Long createdBy, Pageable pageable);

	public Invoice save(UserPrinciple createdBy, Invoice invoice);

	public void delete(String id);

	public Invoice findByIdAndCreatedBy(Long createdBy, String id);

	public ByteArrayOutputStream getInvoicePdfAsByteArray(UserPrinciple currentUser, Invoice invoice);

	public Page<Invoice> findByCreatedByAndIdOrCustomer(Long createdBy, InvoiceFilter filter, Pageable pageable);

}
