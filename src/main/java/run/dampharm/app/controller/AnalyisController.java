package run.dampharm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import run.dampharm.app.model.AnalyisCounts;
import run.dampharm.app.secuirty.CurrentUser;
import run.dampharm.app.secuirty.UserPrinciple;
import run.dampharm.app.service.IAnalysisService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/analyis")
@Slf4j
public class AnalyisController {

	@Autowired
	private IAnalysisService analizeService;

	@GetMapping("/counts")
	public AnalyisCounts findAll(@CurrentUser UserPrinciple user) {

		return analizeService.getCounts(user.getId());
	}
//
//	@PostMapping
//	public CustomerDto create(@RequestBody CustomerDto customer) {
//		log.info("Create Customer:{}", customer.getName());
//		return customerService.save(customer);
//	}
//
//	@PutMapping
//	public CustomerDto update(@RequestBody CustomerDto customer) {
//		log.info("Update Customer:{}", customer.getName());
//		return customerService.update(customer);
//	}
//
//	@DeleteMapping("/{id}")
//	public void delete(@PathVariable("id") long id) {
//		customerService.delete(id);
//	}

}
