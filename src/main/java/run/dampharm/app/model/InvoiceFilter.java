package run.dampharm.app.model;

import lombok.Data;
import run.dampharm.app.domain.Customer;

@Data
public class InvoiceFilter {
	private String id;
	private Customer customer = new Customer();
}
