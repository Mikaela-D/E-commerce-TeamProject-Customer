package ie.atu.ecommerceteamprojectcustomer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void testGetAllCustomers() throws Exception {
        Customer c1 = new Customer("1", "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");
        Customer c2 = new Customer("2", "queen", "queenirl@atu.ie", "55 Drisin, Knocknacarra, Galway");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(c1, c2));
        List<Customer> customerList = customerService.getAllCustomers();
        assertEquals(2, customerList.size());
        assertEquals(c1.getName(), customerList.get(0).getName());
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer mockCustomer = new Customer("1", "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");
        when(customerRepository.findById("1")).thenReturn(Optional.of(mockCustomer));

        Optional<Customer> result = customerService.getCustomerById("1");
        assertNotNull(result);
        assertEquals("mikaela", result.get().getName());
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer newC = new Customer(null, "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");
        Customer savedC = new Customer("1", "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");

        when(customerRepository.save(newC)).thenReturn(savedC);
        Customer result = customerService.createCustomer(newC);
        assertNotNull(result.getId());
        assertEquals("mikaela", result.getName());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer existingC = new Customer("1", "mikaela", "mikaelairl@atu.ie", "41 Circular, Rahoon, Galway");
        Customer updatedC = new Customer("1", "queen", "queenirl@atu.ie", "41 Circular, Rahoon, Galway");

        when(customerRepository.findById("1")).thenReturn(Optional.of(existingC));
        when(customerRepository.save(updatedC)).thenReturn(updatedC);

        Customer result = customerService.updateCustomer("1", updatedC);
        assertNotNull(result);
        assertEquals("queen", result.getName());
    }

    @Test
    public void testDeleteCustomer() throws Exception{
        String customerId = "1";

        when(customerRepository.existsById("1")).thenReturn(true);
        boolean result = customerService.deleteCustomer(customerId);

        assertTrue(result, "Customer ID deleted successfully");
    }
}