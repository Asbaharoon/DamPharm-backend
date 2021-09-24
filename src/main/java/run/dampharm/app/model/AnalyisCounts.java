package run.dampharm.app.model;

import java.util.List;

import lombok.Data;

@Data
public class AnalyisCounts {

	private long categories;
	private long customers;
	private long invoices;

	private List<ProductDto> products;

}
