package run.dampharm.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import run.dampharm.app.domain.ItemInvoice;

@Data
public class InvoiceStatusUpdate {
	private String id;
	private Date paidDate;
	private double paidAmt;
	private Date returnsDate;
	private boolean cancel;
	private InvoiceStatus status;
	private List<ItemInvoice> items = new ArrayList<>();
}
