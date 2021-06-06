package run.dampharm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import run.dampharm.app.domain.Product;
import run.dampharm.app.model.ProductDto;

public interface IProductService {
	public List<ProductDto> findAll(long createdBy);

	public Page<ProductDto> findByCreatedBy(long createdBy, Pageable pageable);

	public ProductDto save(ProductDto category);

	public void delete(Long id);

	public List<Product> findByName(String name);

	public Product findProductById(Long id) throws Exception;

	public ProductDto update(ProductDto category);
}
