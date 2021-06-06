package run.dampharm.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import run.dampharm.app.domain.Customer;

public interface ICustomerDao extends JpaRepository<Customer, Long> {

	@Query("select p from Customer p where p.name like %?1%")
	public List<Customer> findByName(String name);

	public Page<Customer> findByCreatedBy(long createdBy, Pageable pageable);

	public List<Customer> findByCreatedBy(Long createdBy);

}
