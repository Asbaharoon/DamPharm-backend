package run.dampharm.app.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import run.dampharm.app.domain.Customer;
import run.dampharm.app.exception.ResourceNotFoundException;
import run.dampharm.app.model.CustomerDto;
import run.dampharm.app.model.CustomerFilter;
import run.dampharm.app.repository.ICustomerDao;
import run.dampharm.app.search.GenericSpecificationsBuilder;
import run.dampharm.app.search.SpecificationFactory;
import run.dampharm.app.service.ICustomerService;
import run.dampharm.app.utils.Constants;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private ICustomerDao customerDao;

	@Autowired
	private SpecificationFactory<Customer> userSpecificationFactory;

	@Override
	public List<CustomerDto> findAll(long createdBy) {
		List<Customer> customers = customerDao.findByCreatedBy(createdBy);
		List<CustomerDto> dtos = customers.stream().map(customer -> {
			CustomerDto dto = new CustomerDto();
			BeanUtils.copyProperties(customer, dto);
			return dto;
		}).collect(Collectors.toList());

		return dtos;
	}

	@Override
	public Page<CustomerDto> filter(Long createdBy, CustomerFilter filter, Pageable pageable) {
		GenericSpecificationsBuilder<Customer> builder = new GenericSpecificationsBuilder<>();

		if (StringUtils.isNotEmpty(filter.getName())) {
			builder.with(userSpecificationFactory.isLike("name", filter.getName()));
		}

		if (StringUtils.isNotEmpty(filter.getAddress())) {
			builder.with(userSpecificationFactory.isLike("address", filter.getAddress()));
		}

		if (StringUtils.isNotEmpty(filter.getState())) {
			builder.with(userSpecificationFactory.isLike("state", filter.getState()));
		}

		Page<CustomerDto> dtoPage = customerDao.findAll(builder.build(), pageable)
				.map(new Function<Customer, CustomerDto>() {
					@Override
					public CustomerDto apply(Customer entity) {
						CustomerDto dto = new CustomerDto();
						BeanUtils.copyProperties(entity, dto);
						return dto;
					}
				});

		return dtoPage;
	}

	@Override
	public Page<CustomerDto> findByCreatedBy(long createdBy, Pageable pageable) {

		Page<CustomerDto> dtoPage = customerDao.findByCreatedBy(createdBy, pageable)
				.map(new Function<Customer, CustomerDto>() {
					@Override
					public CustomerDto apply(Customer entity) {
						CustomerDto dto = new CustomerDto();
						BeanUtils.copyProperties(entity, dto);
						return dto;
					}
				});

		return dtoPage;
	}

	@Override
	public CustomerDto save(CustomerDto customerDto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDto, customer);
		customer = customerDao.save(customer);
		BeanUtils.copyProperties(customer, customerDto);
		return customerDto;
	}

	@Override
	public CustomerDto update(CustomerDto CustomerDto) {
		Customer customer = null;
		try {
			customer = findCustomerById(CustomerDto.getId());
			BeanUtils.copyProperties(CustomerDto, customer);
			customer = customerDao.save(customer);
			BeanUtils.copyProperties(customer, CustomerDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CustomerDto;
	}

	@Override
	public void delete(Long id) {
		customerDao.deleteById(id);

	}

	@Override
	public List<Customer> findByName(String name) {
		return customerDao.findByName(name);
	}

	@Override
	public Customer findCustomerById(long customerId) throws Exception {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.INVALID_NOT_FOUND));

		return customer;

	}

}
