package run.dampharm.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import run.dampharm.app.domain.audit.UserDateAudit;
import run.dampharm.app.utils.InvoiceCodePrefixedSequenceIdGenerator;

@Data
@Entity
@Table(name = "invoices")
public class Invoice extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
	@GenericGenerator(name = "book_seq", strategy = "run.dampharm.app.utils.InvoiceCodePrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = InvoiceCodePrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
			@Parameter(name = InvoiceCodePrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "INV_"),
			@Parameter(name = InvoiceCodePrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String id;

	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "invoice_id")
	private List<ItemInvoice> items;

	private Double totalPrice;

	public Invoice() {
		this.items = new ArrayList<ItemInvoice>();
	}

	public Double getTotal() {
		Double total = 0.0;
		int size = items.size();

		System.err.println(size);

		for (int i = 0; i < size; i++) {
			System.err.println(items.get(i).getQuantity());
			total += items.get(i).calculateImport();
		}

		return total;
	}

}
