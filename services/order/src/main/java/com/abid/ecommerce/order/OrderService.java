package com.abid.ecommerce.order;

import com.abid.ecommerce.customer.CustomerClient;
import com.abid.ecommerce.exception.BusinessException;
import com.abid.ecommerce.kafka.OrderConfirmation;
import com.abid.ecommerce.kafka.OrderProducer;
import com.abid.ecommerce.orderline.OrderLineRequest;
import com.abid.ecommerce.orderline.OrderLineService;
import com.abid.ecommerce.payment.PaymentClient;
import com.abid.ecommerce.payment.PaymentRequest;
import com.abid.ecommerce.product.ProductClient;
import com.abid.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createdOrder(OrderRequest request) {
        //check the customer
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order :: No Customer exist"));

        // purchase the products --> product-ms (RestTemplate)
        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        // persist order
        var order = this.orderRepository.save(mapper.toOrder(request));

        // persist order lines
        for (PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()

                    )
            );
        }

        // todo start payment process
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        // send the order confirmation --> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts

                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId)));
    }
}
