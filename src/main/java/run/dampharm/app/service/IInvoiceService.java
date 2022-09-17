package run.dampharm.app.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import run.dampharm.app.domain.Invoice;
import run.dampharm.app.exception.ResourceNotFoundException;
import run.dampharm.app.exception.ServiceException;
import run.dampharm.app.model.InvoiceFilter;
import run.dampharm.app.model.InvoiceStatusUpdate;
import run.dampharm.app.secuirty.UserPrinciple;

public interface IInvoiceService {
	public List<Invoice> findAll(long createdBy);

	public Page<Invoice> findByCreatedBy(Long createdBy, Pageable pageable);

	public Invoice save(UserPrinciple createdBy, Invoice invoice);

	public Invoice updateStatus(UserPrinciple currentUser, InvoiceStatusUpdate statusUpdateRq) throws ServiceException;

	public void delete(String id) throws ServiceException;
	
	public Invoice findById(String id) throws ResourceNotFoundException;

	public Invoice findByIdAndCreatedBy(Long createdBy, String id);

	public ByteArrayOutputStream getInvoicePdfAsByteArray(UserPrinciple currentUser, Invoice invoice);
	
	public ByteArrayOutputStream getTaxBillPdfAsByteArray(UserPrinciple currentUser, Invoice invoice);

	public Page<Invoice> findByCreatedByAndIdOrCustomer(Long createdBy, InvoiceFilter filter, Pageable pageable);

	public List<Invoice> downloadStatment(Long id, InvoiceFilter filter);

	public ByteArrayOutputStream getStatmentAsByteStream(UserPrinciple currentUser, InvoiceFilter filter,
			List<Invoice> invoices);

}
