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
import run.dampharm.app.model.CategoryDto;
import run.dampharm.app.secuirty.CurrentUser;
import run.dampharm.app.secuirty.UserPrinciple;
import run.dampharm.app.service.ICategoryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
@Slf4j
public class CatogeryController {

	@Autowired
	private ICategoryService categoryService;

	@GetMapping
	public ResponseEntity<Page<CategoryDto>> list(@CurrentUser UserPrinciple user,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
		Page<CategoryDto> pageResult = categoryService.findByCreatedBy(user.getId(), pageRequest);

		return ResponseEntity.ok(pageResult);

	}

	@GetMapping("/all")
	public List<CategoryDto> findAll(@CurrentUser UserPrinciple user) {
		return categoryService.findAll(user.getId());
	}

	@PostMapping
	public CategoryDto create(@RequestBody CategoryDto category) {
		log.info("Create Category:{}", category.getName());
		return categoryService.save(category);
	}

	@PutMapping
	public CategoryDto update(@RequestBody CategoryDto category) {
		log.info("Update Category:{}", category.getName());
		return categoryService.update(category);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") long id) {
		categoryService.delete(id);
	}

}
