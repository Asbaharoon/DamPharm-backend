package run.dampharm.app.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import run.dampharm.app.domain.Category;
import run.dampharm.app.exception.ResourceNotFoundException;
import run.dampharm.app.model.CategoryDto;
import run.dampharm.app.repository.ICategoryDao;
import run.dampharm.app.service.ICategoryService;
import run.dampharm.app.utils.Constants;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private ICategoryDao categoryDao;

	@Override
	public List<CategoryDto> findAll(long createdBy) {
		List<Category> categories = categoryDao.findByCreatedBy(createdBy);
		List<CategoryDto> dtos = categories.stream().map(category -> {
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(category, dto);
			return dto;
		}).collect(Collectors.toList());

		return dtos;
	}

	@Override
	public Page<CategoryDto> findByCreatedBy(long createdBy, Pageable pageable) {

		Page<CategoryDto> dtoPage = categoryDao.findByCreatedBy(createdBy, pageable)
				.map(new Function<Category, CategoryDto>() {
					@Override
					public CategoryDto apply(Category entity) {
						CategoryDto dto = new CategoryDto();
						BeanUtils.copyProperties(entity, dto);
						return dto;
					}
				});

		return dtoPage;
	}

	@Override
	public CategoryDto save(CategoryDto categoryDto) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDto, category);
		category = categoryDao.save(category);
		BeanUtils.copyProperties(category, categoryDto);
		return categoryDto;
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto) {
		Category category = null;
		try {
			category = findCategoryById(categoryDto.getId());
			BeanUtils.copyProperties(categoryDto, category);
			category = categoryDao.save(category);
			BeanUtils.copyProperties(category, categoryDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryDto;
	}

	@Override
	public void delete(Long id) {
		categoryDao.deleteById(id);

	}

	@Override
	public List<Category> findByName(String name) {
		return categoryDao.findByName(name);
	}

	@Override
	public Category findCategoryById(Long id) throws Exception {
		Category category = categoryDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.INVALID_NOT_FOUND));
		return category;
	}

}
