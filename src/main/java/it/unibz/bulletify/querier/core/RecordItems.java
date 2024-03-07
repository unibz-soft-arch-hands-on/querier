package it.unibz.bulletify.querier.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RecordItems {
    private final ItemRepository itemRepository;

    @Autowired
    public RecordItems(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void recordItemCreated(String item) {
        System.out.println("Received <" + item + ">");
//        this.itemRepository.save(item);
    }
}
