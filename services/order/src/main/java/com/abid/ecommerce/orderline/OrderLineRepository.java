package com.abid.ecommerce.orderline;

import org.apache.logging.log4j.util.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository  extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
