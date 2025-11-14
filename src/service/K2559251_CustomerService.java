package service;

import model.K2559251_Customer;

import java.util.List;

/**
 * --- ABSTRACTION Demonstration ---
 * This is an INTERFACE for the Customer service.
 * It defines the "contract" for all customer-related business logic.
 * It only says *what* methods must exist, not *how* they work.
 * The K2559251_Main class will only ever use this interface,
 * hiding the implementation details.
 */

public interface K2559251_CustomerService {
    K2559251_Customer addLocalCustomer(String nic, String name, String contactNumber, String email);
    K2559251_Customer addForeignCustomer(String passport, String name, String contactNumber, String email);
    K2559251_Customer findCustomerByIdentifier(String identifier); // identifier - The NIC/Passport of the customer to find.
    List<K2559251_Customer> getAllCustomers();
    K2559251_Customer updateCustomer(String identifier, String newContactNumber, String newEmail); // identifier - The NIC/Passport of the customer to update.
}
