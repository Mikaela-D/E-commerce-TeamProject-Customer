package ie.atu.ecommerceteamprojectcustomer;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer mockC = new Customer("1", "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");
        when(customerService.getCustomerById("1")).thenReturn(Optional.of(mockC));

        mockMvc.perform(get("/customers/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("mikaela"))
                .andExpect(jsonPath("$.email").value("mikaelairl@atu.ie"))
                .andExpect(jsonPath("$.address").value("41 Circular, Rahoon, Galway"));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer mockC = new Customer("1", "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");
        when(customerService.createCustomer(new Customer(null, "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway"))).thenReturn(mockC);

        String customerJson = "{\"name\": \"mikaela\", \"email\": \"mikaelairl@atu.ie\", \"address\": \"41 Circular, Rahoon, Galway\"}";
        mockMvc.perform(post("/customers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.name").value("mikaela"))
                .andExpect(jsonPath("$.email").value("mikaelairl@atu.ie"))
                .andExpect(jsonPath("$.address").value("41 Circular, Rahoon, Galway"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer existingC = new Customer("1", "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");
        Customer updatedC = new Customer("1", "queen", "queenirl@atu.ie", "41 Circular, Rahoon, Galway");

        when(customerService.updateCustomer("1", updatedC)).thenReturn(updatedC);
        String customerJson = "{\"id\": \"1\", \"name\": \"queen\", \"email\": \"queenirl@atu.ie\", \"address\": \"41 Circular, Rahoon, Galway\"}";
        mockMvc.perform(put("/customers/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("queen"))
                .andExpect(jsonPath("$.email").value("queenirl@atu.ie"))
                .andExpect(jsonPath("$.address").value("41 Circular, Rahoon, Galway"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        when(customerService.deleteCustomer("1")).thenReturn(true);
        mockMvc.perform(delete("/customers/delete/1"))
                .andExpect(status().isNoContent());
    }



}
