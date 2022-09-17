package run.dampharm.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import run.dampharm.app.domain.ItemInvoice;
import run.dampharm.app.domain.Product;

public interface ItemInvoiceRepository extends JpaRepository<ItemInvoice, Long>, JpaSpecificationExecutor<ItemInvoice> {

	public List<ItemInvoice> findByProduct(Product product);

}
