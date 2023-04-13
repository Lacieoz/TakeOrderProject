package it.beata.ordermanager.items;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.beata.ordermanager.items.businesslogic.model.Item;
import it.beata.ordermanager.items.businesslogic.service.ItemService;
import it.beata.ordermanager.items.inbound.ItemController;
import it.beata.ordermanager.items.inbound.command.CreateItemCommand;
import it.beata.ordermanager.items.inbound.command.UpdateItemCommand;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @MockBean
    ItemService itemService;

    @Autowired
    MockMvc mockMvc;

    private static EasyRandom easyRandom;

    @BeforeAll
    public static void beforeAll () {
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters();
        easyRandomParameters.setCollectionSizeRange(new EasyRandomParameters.Range<>(2, 4));
        easyRandom = new EasyRandom(easyRandomParameters);
    }

    @Test
    public void getItem() throws Exception {
        Item item = easyRandom.nextObject(Item.class);
        String id = "id";
        Mockito.when(itemService.getItem(any(String.class))).thenReturn(item);

        MvcResult result = mockMvc.perform(get("/api/v1/items/{id}", id))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        Item itemResult = new ObjectMapper().readValue(json, Item.class);

        assertEquals(item, itemResult);
    }

    @Test
    public void getItemNotFound() throws Exception {
        String id = "id";
        Mockito.when(itemService.getItem(any(String.class))).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/api/v1/items/{id}", id))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getAllItems() throws Exception {
        List<Item> items = easyRandom.objects(Item.class, 3)
                .collect(Collectors.toList());
        Mockito.when(itemService.getAllItems()).thenReturn(items);

        MvcResult result = mockMvc.perform(get("/api/v1/items"))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<Item> itemsResult = new ObjectMapper().readValue(json, new TypeReference<List<Item>>(){});

        assertEquals(items, itemsResult);
    }

    @Test
    public void getAllItemsZeroFound() throws Exception {
        List<Item> items = easyRandom.objects(Item.class, 0)
                .collect(Collectors.toList());
        Mockito.when(itemService.getAllItems()).thenReturn(items);

        MvcResult result = mockMvc.perform(get("/api/v1/items"))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<Item> itemsResult = new ObjectMapper().readValue(json, new TypeReference<List<Item>>(){});

        assertEquals(items, itemsResult);

    }

    @Test
    public void createItem() throws Exception {
        CreateItemCommand createItemCommand = new CreateItemCommand();
        createItemCommand.setName("name");
        createItemCommand.setCategory("category");
        createItemCommand.setFullPrice(new BigDecimal("1.00"));
        createItemCommand.setDiscountedPrice(new BigDecimal("1.00"));
        createItemCommand.setNote("note");
        createItemCommand.setLimitedQuantity(2);
        createItemCommand.setImageId("imageId");

        String body = new ObjectMapper().writeValueAsString(createItemCommand);

        when(itemService.createItem(any(Item.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        MvcResult result = mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( body ))
                .andExpect(status().isCreated())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        Item itemResult = new ObjectMapper().readValue(json, Item.class);

        assertEquals(createItemCommand.getName(), itemResult.getName());
        assertEquals(createItemCommand.getCategory(), itemResult.getCategory());
        assertEquals(createItemCommand.getFullPrice(), itemResult.getFullPrice());
        assertEquals(createItemCommand.getDiscountedPrice(), itemResult.getDiscountedPrice());
        assertEquals(createItemCommand.getNote(), itemResult.getNote());
        assertEquals(createItemCommand.getLimitedQuantity(), itemResult.getLimitedQuantity());
        assertEquals(createItemCommand.getImageId(), itemResult.getImageId());
    }

    @Test
    public void createItemFailedValidation() throws Exception {
        CreateItemCommand createItemCommand = new CreateItemCommand();
        createItemCommand.setName("");

        String body = new ObjectMapper().writeValueAsString(createItemCommand);

        mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( body ))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void updateItem() throws Exception {
        String id = "id";
        UpdateItemCommand updateItemCommand = new UpdateItemCommand();
        updateItemCommand.setId("id1");
        updateItemCommand.setName("name");
        updateItemCommand.setCategory("category");
        updateItemCommand.setFullPrice(new BigDecimal("1.00"));
        updateItemCommand.setDiscountedPrice(new BigDecimal("1.00"));
        updateItemCommand.setNote("note");
        updateItemCommand.setLimitedQuantity(2);
        updateItemCommand.setImageId("imageId");

        String body = new ObjectMapper().writeValueAsString(updateItemCommand);

        when(itemService.updateItem(any(Item.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        MvcResult result = mockMvc.perform(put("/api/v1/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( body ))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        Item itemResult = new ObjectMapper().readValue(json, Item.class);

        assertEquals(id, itemResult.getId());
        assertEquals(updateItemCommand.getName(), itemResult.getName());
        assertEquals(updateItemCommand.getCategory(), itemResult.getCategory());
        assertEquals(updateItemCommand.getFullPrice(), itemResult.getFullPrice());
        assertEquals(updateItemCommand.getDiscountedPrice(), itemResult.getDiscountedPrice());
        assertEquals(updateItemCommand.getNote(), itemResult.getNote());
        assertEquals(updateItemCommand.getLimitedQuantity(), itemResult.getLimitedQuantity());
        assertEquals(updateItemCommand.getImageId(), itemResult.getImageId());
    }

    @Test
    public void updateItemFailedValidation() throws Exception {
        String id = "id";
        UpdateItemCommand updateItemCommand = new UpdateItemCommand();
        updateItemCommand.setName("");

        String body = new ObjectMapper().writeValueAsString(updateItemCommand);

        mockMvc.perform(put("/api/v1/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( body ))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteItem() throws Exception {
        String id = "id";

        mockMvc.perform(delete("/api/v1/items/{id}", id))
                .andExpect(status().isNoContent());
    }
}
