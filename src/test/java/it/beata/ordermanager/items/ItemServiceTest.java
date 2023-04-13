package it.beata.ordermanager.items;

import it.beata.ordermanager.items.businesslogic.model.Item;
import it.beata.ordermanager.items.businesslogic.service.ItemService;
import it.beata.ordermanager.items.outbound.ItemRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    private static EasyRandom easyRandom;

    @BeforeAll
    public static void beforeAll () {
        easyRandom = new EasyRandom();
    }

    @Test
    public void createItem () {
        // generate a random item
        Item item = easyRandom.nextObject(Item.class);

        when(itemRepository.insert(any(Item.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Item itemInserted = itemService.createItem(item);

        assertEquals(item, itemInserted);

    }

    @Test
    public void createItem_failure () {
        // generate a random item
        Item item = easyRandom.nextObject(Item.class);

        when(itemRepository.insert(any(Item.class))).thenThrow(new RuntimeException("already present"));

        assertThrows(RuntimeException.class,  () -> itemService.createItem(item));

    }

    @Test
    public void updateItem () {
        // generate a random item
        Item item = easyRandom.nextObject(Item.class);

        when(itemRepository.save(any(Item.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Item itemUpdated = itemService.updateItem(item);

        assertEquals(item, itemUpdated);

    }

    @Test
    public void updateItem_failure () {
        // generate a random item
        Item item = easyRandom.nextObject(Item.class);

        when(itemRepository.save(any(Item.class))).thenThrow(new RuntimeException("already present"));

        assertThrows(RuntimeException.class,  () -> itemService.updateItem(item));

    }

    @Test
    public void deleteItem () {
        String id = "id";

        Mockito.doNothing().when(itemRepository).deleteById(any(String.class));

        itemService.deleteItem(id);

    }

    @Test
    public void deleteItem_failure () {
        String id = "id";

        Mockito.doThrow(new RuntimeException("already present")).when(itemRepository).deleteById(any(String.class));

        assertThrows(RuntimeException.class,  () -> itemService.deleteItem(id));

    }

    @Test
    public void getItem () {
        // generate a random item
        Item itemToGet = easyRandom.nextObject(Item.class);
        String id = "id";

        when(itemRepository.findById(any(String.class))).thenReturn(Optional.of(itemToGet) );

        Item item = itemService.getItem(id);

        assertEquals(itemToGet, item);
    }

    @Test
    public void getItem_failure () {
        String id = "id";

        when(itemRepository.findById(any(String.class))).thenThrow(new RuntimeException("already present"));

        assertThrows(RuntimeException.class,  () -> itemService.getItem(id));
    }

    @Test
    public void getAllItems () {
        // generate a random item
        Item itemToGet = easyRandom.nextObject(Item.class);

        when(itemRepository.findAll()).thenReturn( List.of(itemToGet) );

        List<Item> items = itemService.getAllItems();

        assertEquals(1, items.size());
        assertEquals(itemToGet, items.get(0));
    }

    @Test
    public void getAllItems_failure () {
        // generate a random item
        Item itemToGet = easyRandom.nextObject(Item.class);

        when(itemRepository.findAll()).thenThrow( new RuntimeException("already present") );

        assertThrows(RuntimeException.class,  () -> itemService.getAllItems());
    }

    @Test
    public void checkUniqueName () {
        // generate a random item
        Item item = easyRandom.nextObject(Item.class);

        when(itemRepository.findByName(any(String.class))).thenReturn( Optional.empty() );
        itemService.checkUniqueName( item );

    }

    @Test
    public void checkUniqueName_failure () {
        // generate a random item
        Item item = easyRandom.nextObject(Item.class);
        Item item2 = easyRandom.nextObject(Item.class);

        when(itemRepository.findByName(any(String.class))).thenReturn( Optional.of(item2) );

        assertThrows(RuntimeException.class,  () -> itemService.checkUniqueName( item ));

    }
}
