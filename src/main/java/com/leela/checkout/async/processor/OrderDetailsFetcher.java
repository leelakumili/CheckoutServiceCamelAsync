package com.leela.checkout.async.processor;

import java.io.OutputStream;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;

import com.leela.checkout.async.builder.CheckoutAsyncRouteBuilder;

public class OrderDetailsFetcher implements Runnable{

	 private Exchange exchange;
     private AsyncCallback callback;
     private OutputStream out;
     
     
     private OrderDetailsFetcher(Exchange exchange, AsyncCallback callback, OutputStream out) {
         this.exchange = exchange;
         this.callback = callback;
         this.out = out;
     }
	@Override
	public void run() {
		
		
		
	}

}
