package run.dampharm.app.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import run.dampharm.app.domain.Customer;
import run.dampharm.app.model.CustomerDto;
import run.dampharm.app.secuirty.CurrentUser;
import run.dampharm.app.secuirty.UserPrinciple;
import run.dampharm.app.service.ICustomerService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController {

	@Autowired
	private ICustomerService customerService;

	@GetMapping
	public ResponseEntity<Page<CustomerDto>> list(@CurrentUser UserPrinciple user,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
		Page<CustomerDto> pageResult = customerService.findByCreatedBy(user.getId(), pageRequest);

		return ResponseEntity.ok(pageResult);

	}

	@GetMapping("/all")
	public List<CustomerDto> findAll(@CurrentUser UserPrinciple user) {
		return customerService.findAll(user.getId());
	}

	@GetMapping("/details/{id}")
	public CustomerDto getCustomer(@CurrentUser UserPrinciple user, @PathVariable("id") long id) throws Exception {
		Customer customer = customerService.findCustomerById(id);
		CustomerDto dto = new CustomerDto();
		BeanUtils.copyProperties(customer, dto);
		return dto;
	}

	@PostMapping
	public CustomerDto create(@RequestBody CustomerDto customer) {
		log.info("Create Customer:{}", customer.getName());
		return customerService.save(customer);
	}

	@PutMapping
	public CustomerDto update(@CurrentUser UserPrinciple user, @RequestBody CustomerDto customer) {
		log.info("Update Customer:{}", customer.getName());
		return customerService.update(customer);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") long id) {
		customerService.delete(id);
	}

}
