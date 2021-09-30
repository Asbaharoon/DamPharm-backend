package run.dampharm.app.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import run.dampharm.app.domain.Customer;

@Data
public class InvoiceFilter {
	private String id;
	private String state;
	private Date fromDate;
	private Date toDate;
	private Customer customer = new Customer();
	private List<InvoiceStatus> statusList;
}
