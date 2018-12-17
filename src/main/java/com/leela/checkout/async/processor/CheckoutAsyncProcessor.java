package com.leela.checkout.async.processor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.leela.checkout.async.model.OrderInfo;

@Component
public class CheckoutAsyncProcessor implements AsyncProcessor {
	
	

	private final ExecutorService executorService = Executors.newFixedThreadPool(2);
	private static final Logger logger = LogManager.getLogger(CheckoutAsyncProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
	     logger.info("No implementation for sync");

	}

	@Override
	public boolean process(Exchange exchange, AsyncCallback callback) {
		logger.info("Async process started");
		 CountDownLatch countDownLatch = new CountDownLatch(1);
		 exchange.setProperty("countDownLatch", countDownLatch);
		 executorService.submit(new OrderDetailsFetcher(exchange, new AsyncCallback() {
		      @Override public void done(boolean b) {
		    	  logger.info("Async backend process fininshed");
		        exchange.getContext().getAsyncProcessorAwaitManager().countDown(exchange, exchange.getProperty("countDownLatch", CountDownLatch.class));
		      }
		    }));
		    return true;
	}
	
	public static void getResponseInBody(Exchange exchange) {
		CountDownLatch countDownLatch = exchange.getProperty("countDownLatch", CountDownLatch.class);
		exchange.getContext().getAsyncProcessorAwaitManager().await(exchange, countDownLatch);
		logger.debug("Retrieved async response " + exchange.getProperty("Response", OrderInfo.class));
	
		exchange.getIn().setBody(exchange.getProperty("Response", OrderInfo.class));
	}

	public static void getResponseInProperty(Exchange exchange) {
		CountDownLatch countDownLatch = exchange.getProperty("countDownLatch", CountDownLatch.class);
		exchange.getContext().getAsyncProcessorAwaitManager().await(exchange, countDownLatch);
		logger.debug("Retrieved async response " + exchange.getProperty("Response", OrderInfo.class));
	}

	public static OrderInfo getResponse(Exchange exchange) {
		CountDownLatch countDownLatch = exchange.getProperty("countDownLatch", CountDownLatch.class);
		exchange.getContext().getAsyncProcessorAwaitManager().await(exchange, countDownLatch);
		logger.debug("Retrieved async response " + exchange.getProperty("Response", OrderInfo.class));
		return exchange.getProperty("Response", OrderInfo.class);
	}

}
