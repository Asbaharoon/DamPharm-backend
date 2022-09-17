package run.dampharm.app.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import run.dampharm.app.domain.ItemInvoice;
import run.dampharm.app.domain.Product;
import run.dampharm.app.exception.ResourceNotFoundException;
import run.dampharm.app.model.ProductDto;
import run.dampharm.app.repository.IProductDao;
import run.dampharm.app.repository.ItemInvoiceRepository;
import run.dampharm.app.service.IProductService;
import run.dampharm.app.utils.Constants;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductDao productDao;

	@Autowired
	private ItemInvoiceRepository itemInvoiceRepo;

	@Override
	public List<ProductDto> findAll(long createdBy) {
		List<Product> products = productDao.findByCreatedBy(createdBy);
		List<ProductDto> dtos = products.stream().map(product -> {
			ProductDto dto = new ProductDto();
			BeanUtils.copyProperties(product, dto);
			return dto;
		}).collect(Collectors.toList());

		return dtos;
	}

	@Override
	public Page<ProductDto> findByCreatedBy(long createdBy, Pageable pageable) {

		Page<ProductDto> dtoPage = productDao.findByCreatedBy(createdBy, pageable)
				.map(new Function<Product, ProductDto>() {
					@Override
					public ProductDto apply(Product entity) {
						ProductDto dto = new ProductDto();
						BeanUtils.copyProperties(entity, dto);
						return dto;
					}
				});

		return dtoPage;
	}

	@Override
	public ProductDto save(ProductDto productDto) {
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		product.setAvailableQuantity(product.getQuantity());
		product = productDao.save(product);
		BeanUtils.copyProperties(product, productDto);
		return productDto;
	}

	@Override
	public ProductDto update(ProductDto productDto) {
		Product product = null;
		ProductDto dto = new ProductDto();
		try {
			productDto.setAvailableQuantity(productDto.getAvailableQuantity());
			product = findProductById(productDto.getId());
			BeanUtils.copyProperties(productDto, product);
			product = productDao.save(product);
			BeanUtils.copyProperties(product, dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public void delete(Long id) {
		productDao.deleteById(id);

	}

	@Override
	public List<Product> findByName(String name) {
		return productDao.findByName(name);
	}

	@Override
	public Product findProductById(long productId) throws Exception {
		Product product = productDao.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.INVALID_NOT_FOUND));
		return product;
	}

	@Override
	public Product updateAvailableQuantity(Product product) {
		try {
			Product currentProduct = findProductById(product.getId());
			currentProduct.setAvailableQuantity(product.getAvailableQuantity());
			product = productDao.save(currentProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public Product resetAvailableQuantity(long productId) {
		try {
			Product product = findProductById(productId);

			List<ItemInvoice> items = itemInvoiceRepo.findByProduct(product);

			product.setAvailableQuantity(product.getQuantity());

			items.forEach((item) -> {
				int total = item.getQuantity() + item.getBonus();
				long availableQty = product.getAvailableQuantity() - total;
				product.setAvailableQuantity(availableQty);
			});

			productDao.save(product);
			
			return product;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
