package run.dampharm.app.model;

import java.util.Date;

import lombok.Data;

@Data
public class InvoiceStatusUpdate {
	private String id;
	private Date paidDate;
	private InvoiceStatus status;
}
