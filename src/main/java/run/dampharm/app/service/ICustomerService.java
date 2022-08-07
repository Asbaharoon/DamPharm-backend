package run.dampharm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import run.dampharm.app.domain.Customer;
import run.dampharm.app.model.CustomerDto;

public interface ICustomerService {
	public List<CustomerDto> findAll(long createdBy);

	public Page<CustomerDto> findByCreatedBy(long createdBy, Pageable pageable);

	public CustomerDto save(CustomerDto customer);

	public void delete(Long id);

	public List<Customer> findByName(String name);

	public Customer findCustomerById(long customerId) throws Exception;

	public CustomerDto update(CustomerDto customer);

}
