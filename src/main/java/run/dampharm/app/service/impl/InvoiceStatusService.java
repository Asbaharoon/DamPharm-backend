package run.dampharm.app.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import run.dampharm.app.domain.Invoice;
import run.dampharm.app.domain.ItemInvoice;
import run.dampharm.app.domain.Product;
import run.dampharm.app.exception.ServiceException;
import run.dampharm.app.model.InvoiceStatus;
import run.dampharm.app.model.InvoiceStatusUpdate;
import run.dampharm.app.utils.Constants;

@Service
public class InvoiceStatusService {

	public Invoice updateInvoiceStatus(InvoiceStatusUpdate rq, Invoice invoice) throws ServiceException {

		if (!invoice.getStatus().equals(InvoiceStatus.RETURNS) && invoice.getStatus().equals(rq.getStatus()))
			throw new ServiceException(Constants.INVALID_INV_STATUS);
		if (invoice.getStatus().equals(InvoiceStatus.CANCELED))
			throw new ServiceException(Constants.INVALID_INV_CAN);

		invoice.setStatus(rq.getStatus());

		switch (rq.getStatus()) {
		case PAID:
			invoice = isMarkAsPaid(rq, invoice);
			break;
		case PAID_PARTIALLY:
			invoice = isMarkAsPaidPartially(rq, invoice);
			break;
		case CANCELED:
			invoice = isCanceled(rq, invoice);
			break;
		case RETURNS:
			invoice = isReturns(rq, invoice);
			break;

		default:
			break;
		}

		return invoice;
	}

	/**
	 * Check if mark as paid
	 * 
	 * @param rq
	 * @param invoice
	 * @return
	 */
	public Invoice isMarkAsPaid(InvoiceStatusUpdate rq, Invoice invoice) {
		if (Objects.nonNull(rq.getPaidDate())) {
			invoice.setPaidAt(rq.getPaidDate());
			invoice.setPaidAmt(invoice.getTotal());
		} else {
			invoice.setPaidAt(null);
		}
		return invoice;
	}

	/**
	 * Check if mark as partially paid
	 * 
	 * @param rq
	 * @param invoice
	 * @return
	 */
	public Invoice isMarkAsPaidPartially(InvoiceStatusUpdate rq, Invoice invoice) {
		if (Objects.nonNull(rq.getPaidDate()) && rq.getPaidAmt() > 0) {
			invoice.setPaidAt(rq.getPaidDate());
			invoice.setPaidAmt(rq.getPaidAmt());
		} else {
			invoice.setPaidAt(null);
			invoice.setPaidAmt(0);
		}
		return invoice;
	}

	/**
	 * Check if cancel add canceled items qty to product
	 * 
	 * @param rq
	 * @param invoice
	 * @return
	 */
	public Invoice isCanceled(InvoiceStatusUpdate rq, Invoice invoice) {
		if (rq.isCancel()) {
			List<ItemInvoice> items = invoice.getItems();

			items.forEach(itemInvoice -> {
				Product product = itemInvoice.getProduct();
				long avilableQty = product.getAvailableQuantity() + itemInvoice.getQuantity() + itemInvoice.getBonus();
				itemInvoice.getProduct().setAvailableQuantity(avilableQty);
			});

			invoice.setPaidAt(null);
			invoice.setPaidAmt(0);
			invoice.setItems(items);
			invoice.setTotalPrice(invoice.getTotal());
		}
		return invoice;
	}

	/**
	 * Check if returns for invoice
	 * 
	 * @param rq
	 * @param invoice
	 * @return
	 */
	public Invoice isReturns(InvoiceStatusUpdate rq, Invoice invoice) {

		List<ItemInvoice> oldItems = invoice.getItems();

		oldItems.forEach(oldItem -> {
			List<ItemInvoice> updatedItems = rq.getItems();

			updatedItems.forEach(updatedItem -> {
				if (oldItem.getId().equals(updatedItem.getId())) {

					oldItem.setReturns(updatedItem.getReturns());

					oldItem.getProduct()
							.setAvailableQuantity(oldItem.getProduct().getAvailableQuantity() + oldItem.getReturns());
					oldItem.setTotalAfterDiscount(oldItem.itemTotalAfterDiscount());
				}
			});

		});

		if (Objects.nonNull(rq.getReturnsDate())) {
			invoice.setReturnsAt(rq.getReturnsDate());
			invoice.setItems(oldItems);
			invoice.setTotalPrice(invoice.getTotal());
		}

		return invoice;
	}

}
