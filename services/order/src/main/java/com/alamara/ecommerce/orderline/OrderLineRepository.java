package com.alamara.ecommerce.orderline;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine,Integer> {
    @Nullable List<OrderLine> findAllByOrderId(Integer orderId);
}
