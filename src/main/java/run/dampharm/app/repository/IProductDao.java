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
	
	public Page<Product> findByCreatedBy(long createdBy, Pageable pageable);

	public List<Product> findByCreatedBy(Long createdBy);
	
	
//	@Query(nativeQuery=true,value="SELECT Name , STOCK  , NUMBER_OF_REMAIN_STOCK as orders , USER_ID, MIN(STOCK-NUMBER_OF_REMAIN_STOCK)  as sold FROM PRODUCT where USER_ID = ?1 group by USER_ID order by sold asc")
//	public List<TopProduct> getTopSellingProducts(String userID);
}
