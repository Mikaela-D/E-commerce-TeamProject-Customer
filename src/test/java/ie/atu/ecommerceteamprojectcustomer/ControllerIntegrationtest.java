package ie.atu.ecommerceteamprojectcustomer;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import javax.swing.text.html.Option;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationtest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    public void testAddCustomer() throws Exception {
        Customer c1 = new Customer("1", "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");

        mockMvc.perform(post("/customers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(c1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("mikaela"))
                .andExpect(jsonPath("$.email").value("mikaelairl@atu.ie"))
                .andExpect(jsonPath("$.address").value("41 Circular, Rahoon, Galway"));

        Optional<Customer> savedCustomer = customerRepository.findById("1");
        assertThat(savedCustomer).isPresent();
        assertThat(savedCustomer.get().getName()).isEqualTo("mikaela");
    }

    @Test
    public void  testGetAllCustomers() throws Exception {
        Customer c1 = new Customer("1", "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");
        Customer c2 = new Customer("2", "queen", "queenirl@atu.ie", "55 Drisin, Knocknacarra, Galway");

        customerRepository.save(c1);
        customerRepository.save(c2);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Customer cus = new Customer("1", "queen", "queenirl@atu.ie", "55 Drisin, Knocknacarra, Galway");
        Customer savedCustomer = customerRepository.save(cus);

        mockMvc.perform(delete("/customers/delete/" + savedCustomer.getId()))
                .andExpect(status().isNoContent());

        Optional<Customer> deletedCustomer = customerRepository.findById(savedCustomer.getId());
        assertThat(deletedCustomer).isNotPresent();
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = new Customer("1", "queen", "queenirl@atu.ie", "55 Drisin, Knocknacarra, Galway");
        Customer savedCustomer = customerRepository.save(customer);

        Customer updatedCustomer = new Customer(savedCustomer.getId(), "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");
        mockMvc.perform(put("/customers/update/" + savedCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("mikaela"))
                .andExpect(jsonPath("$.email").value("mikaelairl@atu.ie"))
                .andExpect(jsonPath("$.address").value("41 Circular, Rahoon, Galway"));

        Customer updatedCustomerDb = customerRepository.findById(savedCustomer.getId()).orElseThrow();
        assertThat(updatedCustomerDb.getName()).isEqualTo("mikaela");
        assertThat(updatedCustomerDb.getEmail()).isEqualTo("mikaelairl@atu.ie");
    }
}
