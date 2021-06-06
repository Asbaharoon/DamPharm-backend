package run.dampharm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import run.dampharm.app.domain.Category;
import run.dampharm.app.model.CategoryDto;

public interface ICategoryService {
	public List<CategoryDto> findAll(long createdBy);

	public Page<CategoryDto> findByCreatedBy(long createdBy, Pageable pageable);

	public CategoryDto save(CategoryDto category);

	public void delete(Long id);

	public List<Category> findByName(String name);

	public Category findCategoryById(Long id) throws Exception;


	public CategoryDto update(CategoryDto category);

}
