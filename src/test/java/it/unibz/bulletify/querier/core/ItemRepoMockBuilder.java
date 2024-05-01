package it.unibz.bulletify.querier.core;

import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.util.List;

class ItemRepoMockBuilder {
    static ItemRepoMockBuilder init(ItemRepository itemRepository) {
        return new ItemRepoMockBuilder(itemRepository);
    }

    private final ItemRepository baseMock;
    private String givenCategory;
    private List<Item> valueToBeReturned;

    private ItemRepoMockBuilder(ItemRepository itemRepository) {
        baseMock = itemRepository;
        givenCategory = null;
        valueToBeReturned = null;
    }

    public ItemRepoMockBuilder whenGiven(String category) {
        this.givenCategory = category;
        return this;
    }

    public ItemRepoMockBuilder thenReturns(List<Item> list) {
        this.valueToBeReturned = list;
        return this;
    }

    public void mock() {
        OngoingStubbing<List<Item>> onGoing = Mockito.when(
                baseMock.findAllByCategory(this.givenCategory)
        );

        if (this.valueToBeReturned != null)
            onGoing.thenReturn(this.valueToBeReturned);
    }
}
