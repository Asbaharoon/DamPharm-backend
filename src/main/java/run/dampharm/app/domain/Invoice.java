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
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import run.dampharm.app.domain.audit.UserDateAudit;

@Data
@Entity
@Table(name = "invoices")
public class Invoice extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@NotEmpty
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
//	@JsonBackReference
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
