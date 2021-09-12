package run.dampharm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import run.dampharm.app.domain.Product;
import run.dampharm.app.model.AnalyisCounts;
import run.dampharm.app.repository.ICategoryDao;
import run.dampharm.app.repository.ICustomerDao;
import run.dampharm.app.repository.IInvoiceDao;
import run.dampharm.app.repository.IProductDao;
import run.dampharm.app.service.IAnalysisService;

@Service
public class AnalysisServiceImpl implements IAnalysisService {

	@Autowired
	private ICategoryDao categoryDao;

	@Autowired
	private IProductDao productDao;

	@Autowired
	private ICustomerDao customerDao;

	@Autowired
	private IInvoiceDao invoiceDao;

	public AnalyisCounts getCounts(long createdBy) {

		long cateories = categoryDao.countByCreatedBy(createdBy);
		long products = productDao.countByCreatedBy(createdBy);

		long invoices = invoiceDao.countByCreatedBy(createdBy);
		long customers = customerDao.countByCreatedBy(createdBy);

		AnalyisCounts counts = new AnalyisCounts();
		counts.setCategories(cateories);
		counts.setProducts(products);
		counts.setCustomers(customers);
		counts.setInvoices(invoices);

		List<Product> pro = productDao.getTopSellingProducts(createdBy);
		for (Product product : pro) {
			System.out.println(product.getName());
		}

		return counts;
	}

}
