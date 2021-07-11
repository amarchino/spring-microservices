package guru.springframework.msscbrewery.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import guru.springframework.msscbrewery.web.service.CustomerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
	
	@MockBean private CustomerService customerService;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private CustomerDto validCustomer;

    @BeforeEach
    public void setUp() {
        validCustomer = CustomerDto.builder()
        		.id(UUID.randomUUID())
                .name("Customer1")
                .build();
    }

    @Test
    public void getCustomer() throws Exception {
    	BDDMockito.given(customerService.getCustomerById(Mockito.any(UUID.class))).willReturn(validCustomer);

        mockMvc.perform(get("/api/v1/customer/" + validCustomer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(validCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", Is.is("Customer1")));
    }

    @Test
    public void handlePost() throws Exception {
        //given
        CustomerDto customerDto = validCustomer;
        customerDto.setId(null);
        CustomerDto savedDto = CustomerDto.builder().id(UUID.randomUUID()).name("New Customer").build();
        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        BDDMockito.given(customerService.saveNewCustomer(Mockito.any())).willReturn(savedDto);

        mockMvc.perform(post("/api/v1/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void handlePostBadRquest() throws Exception {
        //given
        CustomerDto customerDto = CustomerDto.builder()
        		.name("Ad")
        		.build();
        CustomerDto savedDto = CustomerDto.builder().id(UUID.randomUUID()).name("New Customer").build();
        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        BDDMockito.given(customerService.saveNewCustomer(Mockito.any())).willReturn(savedDto);

        mockMvc.perform(post("/api/v1/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", Matchers.hasSize(1)));
    }

    @Test
    public void handleUpdate() throws Exception {
        //given
        CustomerDto customerDto = validCustomer;
        customerDto.setId(null);
        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        //when
        mockMvc.perform(put("/api/v1/customer/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isNoContent());

        BDDMockito.then(customerService).should().updateCustomer(Mockito.any(), Mockito.any());

    }
    
    @Test
    public void handleUpdateBadRequest() throws Exception {
        //given
        CustomerDto customerDto = validCustomer;
        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        //when
        mockMvc.perform(put("/api/v1/customer/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath("$.*", Matchers.hasSize(1)));

    }

}
