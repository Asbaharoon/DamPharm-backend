package run.dampharm.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import run.dampharm.app.domain.Category;

public interface ICategoryDao extends JpaRepository<Category, Long> {

	@Query("select p from Category p where p.name like %?1%")
	public List<Category> findByName(String name);

	public Page<Category> findByCreatedBy(Long createdBy, Pageable pageable);

	public List<Category> findByCreatedBy(Long createdBy);
	
	public long countByCreatedBy(Long createdBy);

}
