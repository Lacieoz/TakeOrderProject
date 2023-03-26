package it.beata.ordermanager.items.outbound;

import it.beata.ordermanager.items.businesslogic.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {

    Optional<Item> findByName(String name);

}
