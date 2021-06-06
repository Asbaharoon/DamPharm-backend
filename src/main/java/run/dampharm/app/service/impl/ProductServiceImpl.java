package run.dampharm.app.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import run.dampharm.app.domain.Product;
import run.dampharm.app.exception.ResourceNotFoundException;
import run.dampharm.app.model.ProductDto;
import run.dampharm.app.repository.IProductDao;
import run.dampharm.app.service.IProductService;
import run.dampharm.app.utils.Constants;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductDao productDao;

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
	public ProductDto save(ProductDto ProductDto) {
		Product product = new Product();
		BeanUtils.copyProperties(ProductDto, product);
		product = productDao.save(product);
		BeanUtils.copyProperties(product, ProductDto);
		return ProductDto;
	}

	@Override
	public ProductDto update(ProductDto ProductDto) {
		Product product = null;
		ProductDto dto = new ProductDto();
		try {
			product = findProductById(ProductDto.getId());
			BeanUtils.copyProperties(ProductDto, product);
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
	public Product findProductById(Long id) throws Exception {
		Product product = productDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.INVALID_NOT_FOUND));
		return product;
	}
}
