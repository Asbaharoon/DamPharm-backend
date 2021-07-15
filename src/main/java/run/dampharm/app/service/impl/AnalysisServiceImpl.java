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
import run.dampharm.app.repository.IProductDao;
import run.dampharm.app.service.IAnalysisService;
import run.dampharm.app.service.ICategoryService;
import run.dampharm.app.utils.Constants;

@Service
public class AnalysisServiceImpl implements IAnalysisService {

	@Autowired
	private ICategoryDao categoryDao;

	@Autowired
	private IProductDao productDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dam.pharm.starter.service.StatsService#getTopSellingProducts()
	 */
//	@Override
//	public List<TopProduct> getTopSellingProducts(String userID) {
//
//
//		List<TopProduct> products = productRepository.getTopSellingProducts(userID);
//
//		LOGGER.info(">>>>>>>> Top Products Number:{}", products.size());
//		return products;
//	}
//
//	@Override
//	public Page<CategoryDto> findByCreatedBy(long createdBy, Pageable pageable) {
//
//		Page<CategoryDto> dtoPage = categoryDao.findByCreatedBy(createdBy, pageable)
//				.map(new Function<Category, CategoryDto>() {
//					@Override
//					public CategoryDto apply(Category entity) {
//						CategoryDto dto = new CategoryDto();
//						BeanUtils.copyProperties(entity, dto);
//						return dto;
//					}
//				});
//
//		return dtoPage;
//	}
//
//	@Override
//	public CategoryDto save(CategoryDto categoryDto) {
//		Category category = new Category();
//		BeanUtils.copyProperties(categoryDto, category);
//		category = categoryDao.save(category);
//		BeanUtils.copyProperties(category, categoryDto);
//		return categoryDto;
//	}
//
//	@Override
//	public CategoryDto update(CategoryDto categoryDto) {
//		Category category = null;
//		try {
//			category = findCategoryById(categoryDto.getId());
//			BeanUtils.copyProperties(categoryDto, category);
//			category = categoryDao.save(category);
//			BeanUtils.copyProperties(category, categoryDto);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return categoryDto;
//	}
//
//	@Override
//	public void delete(Long id) {
//		categoryDao.deleteById(id);
//
//	}
//
//	@Override
//	public List<Category> findByName(String name) {
//		return categoryDao.findByName(name);
//	}
//
//	@Override
//	public Category findCategoryById(Long id) throws Exception {
//		Category category = categoryDao.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException(Constants.INVALID_NOT_FOUND));
//		return category;
//	}

}
