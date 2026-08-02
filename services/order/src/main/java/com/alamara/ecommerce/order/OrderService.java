package com.alamara.ecommerce.order;

import com.alamara.ecommerce.customer.CustomerClient;
import com.alamara.ecommerce.exception.BusinessException;
import com.alamara.ecommerce.kafka.OrderConfirmation;
import com.alamara.ecommerce.kafka.OrderProducer;
import com.alamara.ecommerce.orderline.OrderLineRequest;
import com.alamara.ecommerce.orderline.OrderLineService;
import com.alamara.ecommerce.payment.PaymentClient;
import com.alamara.ecommerce.payment.PaymentRequest;
import com.alamara.ecommerce.product.ProductClient;
import com.alamara.ecommerce.record.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public  Integer createOrder(OrderRequest request) {
        //find customer exist or not
        var customer = customerClient.findCustomerById(request.customerId()).
                orElseThrow(()->new BusinessException("Cannot create order :: No Customer exist with the provided ID"));

        //send purchase request to the product service
        var purchaseProducts = this.productClient.purchaseProducts(request.products());

        //save the order
        var order = orderRepository.save(mapper.toOrder(request));

        //loop through the each product
        for(PurchaseRequest purchaseRequest: request.products()){
            //save the product to the respective orderline
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        //send payment request
        var paymentRequest=new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        //send the request to kafka producer for notification
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );
        //return Order Id
        return order.getId();
    }

    public  List<OrderResponce> findAll() {
        return orderRepository.findAll().stream().map(mapper::fromOrder).collect(Collectors.toList());
    }

    public  OrderResponce findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(()->new EntityNotFoundException(String.format("No order Found with the Provided ID: %d",orderId)));
    }
}
