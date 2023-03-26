package it.beata.ordermanager.orders.inbound;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.beata.ordermanager.orders.businesslogic.model.Order;
import it.beata.ordermanager.orders.inbound.command.CreateOrderCommand;
import it.beata.ordermanager.orders.inbound.command.UpdateOrderCommand;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order Controller", description = "To manage orders operations")
public interface SwaggerOrderController {
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get order by id")
    ResponseEntity<Order> getOrder(@PathVariable String id);

    @PostMapping
    @Operation(summary = "Create new order")
    ResponseEntity<Order> createOrder(@RequestBody @Valid CreateOrderCommand createOrderCommand);

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update order")
    ResponseEntity<Order> updateOrder(
            @PathVariable String id,
            @RequestBody UpdateOrderCommand updateOrderCommand);

    @PostMapping(path = "/{id}/pay")
    @Operation(summary = "Pay order")
    ResponseEntity<Order> payOrder(@PathVariable String id);

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete order by id")
    ResponseEntity deleteItem(@PathVariable String id);
}
