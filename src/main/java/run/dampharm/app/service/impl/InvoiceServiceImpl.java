package run.dampharm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import run.dampharm.app.domain.Invoice;
import run.dampharm.app.repository.IInvoiceDao;
import run.dampharm.app.service.IInvoiceService;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

	@Autowired
	private IInvoiceDao invoiceDao;

	@Override
	public List<Invoice> findAll(long createdBy) {
		return invoiceDao.findByCreatedBy(createdBy);
	}

	@Override
	public Page<Invoice> findByCreatedBy(long createdBy, Pageable pageable) {

		Page<Invoice> dtoPage = invoiceDao.findByCreatedBy(createdBy, pageable);

		return dtoPage;
	}

	@Override
	public Invoice save(Invoice invoice) {
		invoice = invoiceDao.save(invoice);
		return invoice;
	}

	@Override
	public void delete(Long id) {
		invoiceDao.deleteById(id);
	}

}
