package com.vroong.product.application;

import com.vroong.order.rest.Order;
import com.vroong.order.rest.OrderLineItem;
import com.vroong.product.application.port.in.ConsumerChannel;
import com.vroong.product.application.port.in.OrderEvent;
import com.vroong.product.application.port.out.OrderTemplate;
import com.vroong.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final ProductService productService;
    private final OrderTemplate orderTemplate;

    @StreamListener(value = ConsumerChannel.CHANNEL, condition = "headers['messageType']=='ORDER_CREATED'")
    public void onReceiveOrderCreated(OrderEvent message) {
        Order createdOrder = orderTemplate.getOrderByOrderId(message.getOrderId());

        assert createdOrder.getOrderLine() != null;
        assert createdOrder.getOrderLine().getData() != null;
        for (OrderLineItem orderLine : createdOrder.getOrderLine().getData()) {
            assert orderLine.getProduct() != null;

            Product product  = productService.getProduct(orderLine.getProductId());

            if(product.getInventory() > orderLine.getQuantity()) {
                productService.decreaseInventory(
                        orderLine.getProduct().getProductId(),
                        orderLine.getQuantity()
                );

                // Todo 재고가 변경되었다는 메시지를 발송한다.
            } else {
                // Todo 재고가 부족하여 처리할 수 없다는 메시지를 발송한다.
            }
        }
    }

    @StreamListener(value = ConsumerChannel.CHANNEL, condition = "headers['messageType']=='ORDER_CANCELED'")
    public void onReceiveOrderCanceled(OrderEvent message) {
        // order 를 조회해서 주문 수량 만큼 재고를 차감한다.
        Order createdOrder = orderTemplate.getOrderByOrderId(message.getOrderId());

        assert createdOrder.getOrderLine() != null;
        assert createdOrder.getOrderLine().getData() != null;
        for (OrderLineItem orderLine : createdOrder.getOrderLine().getData()) {
            assert orderLine.getProduct() != null;
            productService.increaseInventory(orderLine.getProduct().getProductId(),
                orderLine.getQuantity());
        }
    }
}
