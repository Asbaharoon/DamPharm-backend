package run.dampharm.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import run.dampharm.app.domain.audit.UserDateAudit;
import run.dampharm.app.model.InvoiceStatus;
import run.dampharm.app.utils.InvoiceCodePrefixedSequenceIdGenerator;

@Data
@Entity
@Table(name = "invoices")
public class Invoice extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inv_seq")
	@GenericGenerator(name = "inv_seq", strategy = "run.dampharm.app.utils.InvoiceCodePrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = InvoiceCodePrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
			@Parameter(name = InvoiceCodePrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "INV_"),
			@Parameter(name = InvoiceCodePrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	@Column(columnDefinition = "varchar(100)")
	private String id;

	private String description;

	@Enumerated(EnumType.ORDINAL)
	@ColumnDefault("0")
	private InvoiceStatus status;

	@Enumerated(EnumType.ORDINAL)
	@ColumnDefault("0")
	private ServiceType type;

	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paidAt;

	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnsAt;

	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "invoice_id")
	private List<ItemInvoice> items;

	@Column(name = "paid_amt", columnDefinition = "double precision default 0")
	private double paidAmt;
	private double totalPrice;

	public Invoice() {
		this.items = new ArrayList<ItemInvoice>();
	}

	public double getTotal() {
		totalPrice = 0.0;
		items.forEach(item -> {
			totalPrice += item.itemTotalAfterDiscount();
		});

		return totalPrice;
	}

}
