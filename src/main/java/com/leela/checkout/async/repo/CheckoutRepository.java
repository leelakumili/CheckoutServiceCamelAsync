package com.leela.checkout.async.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.leela.checkout.async.model.OrderInfo;

public interface CheckoutRepository extends 
JpaRepository<OrderInfo, Long>{
OrderInfo findByid(Long id) ;


}
