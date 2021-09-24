package run.dampharm.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import run.dampharm.app.domain.Product;

public interface IProductDao extends JpaRepository<Product, Long> {

	@Query("select p from Product p where p.name like %?1%")
	public List<Product> findByName(String name);
	
	public Page<Product> findByCreatedBy(Long createdBy, Pageable pageable);

	public List<Product> findByCreatedBy(Long createdBy);
	
	public long countByCreatedBy(Long createdBy);
	
	
	@Query(nativeQuery=true,value="SELECT product.created_by,product.name, min(product.available_quantity) as available_quantity" + 
			" FROM products product where created_by = ?1 group by created_by,product.name order by available_quantity desc;" )
	public List<Product> getTopSellingProducts(long created_by);
}
