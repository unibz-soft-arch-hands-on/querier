package it.unibz.bulletify.querier.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibz.bulletify.querier.core.Item;
import it.unibz.bulletify.querier.core.SearchItems;
import it.unibz.bulletify.querier.utils.ItemsGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchItemsController.class)
class SearchItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchItems searchItems;

    @MockBean private RecordItems recordItems;

    @MockBean private Queue itemQueue;

    @MockBean private FanoutExchange bulletifyExchange;

    @MockBean private Binding binding;

    @MockBean private MessageListenerAdapter listenerAdapter;

    @MockBean private SimpleMessageListenerContainer container;

    @Test
    void givenUnknownCategory_thenGetsEmptyDTO() throws Exception {
        // given
        Mockito
                .when(searchItems.findAllByCategory(Mockito.anyString()))
                .thenReturn(List.of());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/items")
                .queryParam("category", "anything");

        // when ... then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(emptyListDTO()));
    }

    @Test
    void givenKnownCategory_thenGetsContent() throws Exception {
        // given
        String knownCategory = "foo";
        List<Item> sample = generateItemsOfCategory(knownCategory);
        Mockito
                .when(searchItems.findAllByCategory(knownCategory))
                .thenReturn(sample);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/items")
                .queryParam("category", knownCategory);

        // when ... then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(contentListDTO(sample)));
    }

    private List<Item> generateItemsOfCategory(String category) {
        return ItemsGenerator.generateItemsOfCategory(category);
    }

    private String emptyListDTO() {
        ItemsDTO emptyDto = new ItemsDTO();
        emptyDto.setItems(List.of());
        return writeAsJson(emptyDto);
    }

    private String contentListDTO(List<Item> items) {
        ItemsDTO dto = new ItemsDTO();
        dto.setItems(items);
        return writeAsJson(dto);
    }

    private static String writeAsJson(ItemsDTO dto) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}