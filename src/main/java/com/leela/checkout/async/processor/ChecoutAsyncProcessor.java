package com.leela.checkout.async.processor;

import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ChecoutAsyncProcessor implements AsyncProcessor {

	private final Logger logger = LogManager.getLogger(ChecoutAsyncProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		final long startTime = System.currentTimeMillis();
		logger.debug("Processing exchange - " + exchange.toString());
		final Map<String, Object> headers = exchange.getIn().getHeaders();

		final Object id = headers.get("id");
		logger.debug("ID: " + id);

		Long orderNumber = Long.parseLong(id.toString());

		exchange.getOut().setHeaders(exchange.getIn().getHeaders());
		exchange.getOut().setBody(orderNumber);
		logger.info("OrderDetailsProcessor process() completed in " + (System.currentTimeMillis() - startTime) + " ms");

	}

	@Override
	public boolean process(Exchange exchange, AsyncCallback callback) {
		
		return false;
	}

}
