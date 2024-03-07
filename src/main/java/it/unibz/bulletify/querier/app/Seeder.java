package it.unibz.bulletify.querier.app;

import it.unibz.bulletify.querier.core.Item;
import it.unibz.bulletify.querier.core.ItemRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Seeder {
    private final ItemRepository itemRepository;

    @Autowired
    public Seeder(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

//    @PostConstruct
    public void seedDatabase() {
        boolean notEmpty = this.itemRepository.existsById(42L);

        if (notEmpty) return;

        List<Item> items = List.of(
                new Item(42L, "canederli", "missing ingredient"),
                new Item(43L, "rabanada", "extra sweet"),
                new Item(44L, "rice and beans", "extra salty"),
                new Item(45L, "vin brule", "extra sweet"),
                new Item(46L, "mini pizza", "burned")
        );

        this.itemRepository.saveAll(items);
    }
}
