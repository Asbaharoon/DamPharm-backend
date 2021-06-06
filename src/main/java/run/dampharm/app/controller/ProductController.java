package run.dampharm.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import run.dampharm.app.model.ProductDto;
import run.dampharm.app.secuirty.CurrentUser;
import run.dampharm.app.secuirty.UserPrinciple;
import run.dampharm.app.service.IProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

	@Autowired
	private IProductService productService;

	@GetMapping
	public ResponseEntity<Page<ProductDto>> list(@CurrentUser UserPrinciple user,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
		Page<ProductDto> pageResult = productService.findByCreatedBy(user.getId(), pageRequest);

		return ResponseEntity.ok(pageResult);

	}

	@GetMapping("/all")
	public List<ProductDto> findAll(@CurrentUser UserPrinciple user) {
		return productService.findAll(user.getId());
	}

	@PostMapping
	public ProductDto create(@RequestBody ProductDto product) {
		log.info("Create product:{}", product.getName());
		return productService.save(product);
	}

	@PutMapping
	public ProductDto update(@RequestBody ProductDto product) {
		log.info("Update product:{}", product.getName());
		return productService.update(product);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") long id) {
		productService.delete(id);
	}

}
