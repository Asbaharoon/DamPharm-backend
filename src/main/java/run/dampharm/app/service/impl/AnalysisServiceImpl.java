package run.dampharm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import run.dampharm.app.model.AnalyisCounts;
import run.dampharm.app.model.ProductDto;
import run.dampharm.app.repository.ICategoryDao;
import run.dampharm.app.repository.ICustomerDao;
import run.dampharm.app.repository.IInvoiceDao;
import run.dampharm.app.service.IAnalysisService;
import run.dampharm.app.service.IProductService;

@Service
public class AnalysisServiceImpl implements IAnalysisService {

	@Autowired
	private ICategoryDao categoryDao;

	@Autowired
	private IProductService productService;
	@Autowired
	private ICustomerDao customerDao;

	@Autowired
	private IInvoiceDao invoiceDao;

	public AnalyisCounts getCounts(long createdBy) {

		long cateories = categoryDao.countByCreatedBy(createdBy);
		long invoices = invoiceDao.countByCreatedBy(createdBy);
		long customers = customerDao.countByCreatedBy(createdBy);

		AnalyisCounts counts = new AnalyisCounts();
		counts.setCategories(cateories);
		
		counts.setCustomers(customers);
		counts.setInvoices(invoices);

//		List<Product> pro = productDao.getTopSellingProducts(createdBy);
//		for (Product product : pro) {
//			System.out.println(product.getName());
//		}

		List<ProductDto> products = productService.findAll(createdBy);
		counts.setProducts(products);

		return counts;
	}

}
