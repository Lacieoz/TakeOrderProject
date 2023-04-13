package it.beata.ordermanager.items.inbound;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.beata.ordermanager.items.businesslogic.model.Item;
import it.beata.ordermanager.items.businesslogic.service.ItemService;
import it.beata.ordermanager.items.inbound.command.CreateItemCommand;
import it.beata.ordermanager.items.inbound.command.UpdateItemCommand;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/items")
@Tag(name = "Item Controller", description = "To manage items operations")
public class ItemController {

    private final ItemService itemService;

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get item by id")
    public ResponseEntity<Item> getItem (@PathVariable String id) {
        Item item = itemService.getItem(id);
        if (item == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(item);
    }

    @GetMapping
    @Operation(summary = "Get all items")
    public ResponseEntity<List<Item>> getAllItems ()
    {
        List<Item> allItems = itemService.getAllItems();
        return ResponseEntity.ok(allItems);
    }

    @PostMapping
    @Operation(summary = "Create new item")
    public ResponseEntity<Item> createItem (@RequestBody @Valid CreateItemCommand createItemCommand)
    {
        Item itemCreated = itemService.createItem( createItemCommand.toItem() );
        return ResponseEntity.status(HttpStatus.CREATED).body(itemCreated);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update item")
    public ResponseEntity<Item> updateItem (
            @PathVariable String id,
            @RequestBody UpdateItemCommand updateItemCommand)
    {
        updateItemCommand.setId(id);
        Item itemUpdated = itemService.updateItem(updateItemCommand.toItem());
        return ResponseEntity.ok(itemUpdated);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete item by id")
    public ResponseEntity deleteItem (@PathVariable String id)
    {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
