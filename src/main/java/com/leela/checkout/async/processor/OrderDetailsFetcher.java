package com.leela.checkout.async.processor;

import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.leela.checkout.async.business.CheckoutBR;
import com.leela.checkout.async.model.OrderInfo;
import com.leela.checkout.async.service.ShippingService;

public class OrderDetailsFetcher implements Runnable {

	private static final Logger logger = LogManager.getLogger(OrderDetailsFetcher.class);
	private Exchange exchange;
	private AsyncCallback callback;

	@Autowired
	private CheckoutBR checkoutBR;

	private ShippingService shippingService;

	public OrderDetailsFetcher(Exchange exchange, AsyncCallback callback) {
		this.exchange = exchange;
		this.callback = callback;
	}

	@Override
	public void run() {

		logger.info("Async backend process started");

		final Map<String, Object> headers = exchange.getIn().getHeaders();

		final Object id = headers.get("id");
		logger.debug("ID: " + id);
		Long orderId = Long.parseLong(id.toString());
		OrderInfo order = checkoutBR.getOrderDetails(orderId);
		Double shippingCharges = shippingService.calculateShippingCharges(order.getShipping());

		order.setOrderTotal(order.getOrderTotal() + shippingCharges);

		exchange.setProperty("Response", order);
		logger.info("Async backend process completed");
		callback.done(false);

	}

	

}
