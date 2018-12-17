package com.leela.checkout.async.builder;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leela.checkout.async.processor.CheckoutAsyncProcessor;

@Component
public class CheckoutAsyncRouteBuilder extends RouteBuilder{

	@Autowired	
	CheckoutAsyncProcessor checkoutAsyncProcessor;
	
	@Override
	public void configure() throws Exception {
		restConfiguration()
        .component("restlet")
        .host("localhost").port("9080")
        .bindingMode(RestBindingMode.auto);
		
		 rest("/checkout/order/")
	      .get("/{id}").to("direct:orderDetails");
		 
		 from("direct:orderDetails").process(checkoutAsyncProcessor)
         .log("exited processor")
         .bean(CheckoutAsyncProcessor.class, "getResponse(${exchange})")
         .log("${body} and Response Property ${property.Response}");
        
		
	}

}
