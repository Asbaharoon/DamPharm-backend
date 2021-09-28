package run.dampharm.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import run.dampharm.app.domain.Product;
import run.dampharm.app.domain.ProductAdditionalQty;

public interface IProductAdditionalQtyDao extends JpaRepository<ProductAdditionalQty, Long> {

	public Page<Product> findByCreatedBy(Long createdBy, Pageable pageable);

	public List<Product> findByCreatedBy(Long createdBy);

	public long countByCreatedBy(Long createdBy);

}
