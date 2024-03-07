package it.unibz.bulletify.querier.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibz.bulletify.querier.core.Item;
import it.unibz.bulletify.querier.core.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordItems {
    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public RecordItems(ItemRepository itemRepository, ObjectMapper objectMapper) {
        this.itemRepository = itemRepository;
        this.objectMapper = objectMapper;
    }

    public void recordItemCreated(String itemJson) {
        System.out.println("Received <" + itemJson + ">");
        try {
            Item item = this.objectMapper.readValue(itemJson, Item.class);
            this.itemRepository.save(item);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
