package it.beata.ordermanager.orders.outbound;

import it.beata.ordermanager.orders.businesslogic.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

}
