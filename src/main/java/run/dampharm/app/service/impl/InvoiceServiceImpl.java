package run.dampharm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import run.dampharm.app.domain.Invoice;
import run.dampharm.app.domain.ItemInvoice;
import run.dampharm.app.repository.IInvoiceDao;
import run.dampharm.app.repository.IProductDao;
import run.dampharm.app.service.IInvoiceService;
import run.dampharm.app.service.IProductService;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

	@Autowired
	private IInvoiceDao invoiceDao;

	@Autowired
	private IProductService productService;

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
	public Invoice save(long createdBy,Invoice invoice) {
		List<ItemInvoice> items = invoice.getItems();
		items.forEach(item -> {
			item.setProduct(productService.updateAvailableQuantity(item.getProduct()));
		});
		invoice.setItems(items);
		invoice = invoiceDao.save(invoice);
		return invoice;
	}

	@Override
	public void delete(String id) {
		invoiceDao.deleteById(id);
	}

}
