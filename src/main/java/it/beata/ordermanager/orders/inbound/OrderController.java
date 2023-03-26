package it.beata.ordermanager.orders.inbound;

import it.beata.ordermanager.orders.businesslogic.model.Order;
import it.beata.ordermanager.orders.businesslogic.service.OrderService;
import it.beata.ordermanager.orders.inbound.command.CreateOrderCommand;
import it.beata.ordermanager.orders.inbound.command.UpdateOrderCommand;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;


@RestController
@RequestMapping("/api/v1/orders")
public class OrderController implements SwaggerOrderController {

    private final OrderService orderService;

    public OrderController (OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        Order order = orderService.getOrder(id);
        if (order == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid CreateOrderCommand createOrderCommand) {
        Order orderCreated = orderService.createOrder( createOrderCommand.toOrder() );
        return ResponseEntity.status(HttpStatus.CREATED).body(orderCreated);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable String id,
            @RequestBody UpdateOrderCommand updateOrderCommand) {
        Order orderUpdated = orderService.updateOrder(id, updateOrderCommand.toOrder());
        return ResponseEntity.ok(orderUpdated);
    }

    @PostMapping(path = "/{id}/pay")
    public ResponseEntity<Order> payOrder(@PathVariable String id) {
        Order orderUpdated = orderService.payOrder(id);
        return ResponseEntity.ok(orderUpdated);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteItem(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/qr-code")
    public ResponseEntity<?> createQrCode(@PathVariable String id) {
        BufferedImage bufferedImage = orderService.getQrCode(id);
        // TODO : final response
        return ResponseEntity.noContent().build();
    }

}
