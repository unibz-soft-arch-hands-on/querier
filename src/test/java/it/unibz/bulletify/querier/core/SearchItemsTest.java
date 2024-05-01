package it.unibz.bulletify.querier.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchItemsTest {
    private SearchItems underTest;

    private ItemRepository itemRepository;

    @BeforeEach
    void setup() {
        this.itemRepository = Mockito.mock(ItemRepository.class);
        underTest = new SearchItems(this.itemRepository);
    }

    @Test
    void givenUnknownCategory_thenGetsEmptyList() {
        // given
        String unknownCategory = "anything";
        List<Item> emptyList = List.of();
        ItemRepoMockBuilder
                .init(this.itemRepository)
                .whenGiven(unknownCategory)
                .thenReturns(emptyList)
                .mock();

        // when
        List<Item> items = underTest.findAllByCategory(unknownCategory);

        // then
        assertEquals(emptyList, items);
    }

    @Test
    void givenExistingCategory_thenGetsNonEmptyList() {
        // given
        String existingCategory = "whatever";
        List<Item> nonEmptyList = generateItemsOfCategory(existingCategory);
        ItemRepoMockBuilder
                .init(itemRepository)
                .whenGiven(existingCategory)
                .thenReturns(nonEmptyList)
                .mock();

        // when
        List<Item> items = underTest.findAllByCategory(existingCategory);

        // then
        assertEquals(nonEmptyList, items);
    }

    private List<Item> generateItemsOfCategory(String category) {
        return List.of(
                new Item(1L, "item one", category),
                new Item(2L, "item two", category),
                new Item(3L, "item three", category),
                new Item(4L, "item four", category)
        );
    }
}