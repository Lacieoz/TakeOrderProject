package it.beata.ordermanager.items.businesslogic.service;

import it.beata.ordermanager.items.businesslogic.model.Item;
import it.beata.ordermanager.items.outbound.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item createItem (Item item) {

        log.info("CREATE ITEM '{}'", item);
        // TO BE SAFE
        item.setId(null);

        try {
            return itemRepository.insert(item);
        } catch (Exception exception) {
            log.error("ERRORE IN CREATE : ", exception);
        }

        // SEARCH FOR PROBLEM
        checkUniqueName(item);

        throw new RuntimeException("Errore di sistema, riprovare!");
    }

    public Item updateItem (String id, Item item) {

        log.info("UPDATE ITEM id = '{}' , '{}'", id, item);
        item.setId(id);
        try {
            return itemRepository.save(item);
        } catch (Exception exception) {
            log.error("ERRORE IN UPDATE : ", exception);
        }

        // SEARCH FOR PROBLEM
        checkUniqueName(item);

        throw new RuntimeException("Errore di sistema, riprovare!");
    }

    public void deleteItem (String id) {

        log.info("DELETE ITEM id = '{}'", id);

        try {
            itemRepository.deleteById(id);
            return;
        } catch (Exception exception) {
            log.error("ERRORE IN DELETE : ", exception);
        }

        throw new RuntimeException("Errore di sistema, riprovare!");
    }

    public Item getItem (String id) {

        log.info("GET ITEM id = '{}'", id);

        try {
            return itemRepository.findById(id).orElse(null);
        } catch (Exception exception) {
            log.error("ERRORE IN GET : ", exception);
        }

        throw new RuntimeException("Errore di sistema, riprovare!");
    }

    public List<Item> getAllItems () {

        log.info("GET ALL ITEMS");

        try {
            return itemRepository.findAll();
        } catch (Exception exception) {
            log.error("ERRORE IN GET ALL : ", exception);
        }

        throw new RuntimeException("Errore di sistema, riprovare!");
    }

    public void checkUniqueName(Item item) {
        log.info("CHECKING UNIQUE NAME '{}'", item.getName());
        Optional<Item> optionalItem = itemRepository.findByName(item.getName());
        if (optionalItem.isPresent() && ! optionalItem.get().getId().equals( item.getId() )) {
            log.error("Nome già presente '{}'", item.getName());
            throw new RuntimeException("Nome già presente");
        }
    }
}
