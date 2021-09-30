package run.dampharm.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.dampharm.app.domain.Invoice;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatmentReport implements Serializable {

	List<Invoice> invoices = new ArrayList<Invoice>();

}
