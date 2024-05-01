package it.unibz.bulletify.querier.utils;

import it.unibz.bulletify.querier.core.Item;

import java.util.List;

public class ItemsGenerator {
    public static List<Item> generateItemsOfCategory(String category) {
        return List.of(
                new Item(1L, "item one", category),
                new Item(2L, "item two", category),
                new Item(3L, "item three", category),
                new Item(4L, "item four", category)
        );
    }
}
