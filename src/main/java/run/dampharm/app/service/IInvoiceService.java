package run.dampharm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import run.dampharm.app.domain.Invoice;

public interface IInvoiceService {
	public List<Invoice> findAll(long createdBy);

	public Page<Invoice> findByCreatedBy(long createdBy, Pageable pageable);

	public Invoice save(Invoice customer);

	public void delete(String id);

}
