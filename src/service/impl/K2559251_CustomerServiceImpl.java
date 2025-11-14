package service.impl;

import model.K2559251_Customer;
import model.K2559251_ForeignCustomer;
import model.K2559251_LocalCustomer;
import repository.K2559251_DataStore;
import service.K2559251_CustomerService;

import java.util.List;

public class K2559251_CustomerServiceImpl implements K2559251_CustomerService {

    //  Encapsulation: The DataStore is private
    private final K2559251_DataStore dataStore;

    // Constructor (Dependency Injection).
    public K2559251_CustomerServiceImpl(K2559251_DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public K2559251_Customer addLocalCustomer(String nic, String name, String contactNumber, String email) {
        K2559251_LocalCustomer newCustomer = new K2559251_LocalCustomer(name, contactNumber, email, nic); // Create the local customer object
        dataStore.addCustomer(newCustomer); // Using DataStore to persist the customer
        return newCustomer; // Return the newly created customer
    }

    @Override
    public K2559251_Customer addForeignCustomer(String passport, String name, String contactNumber, String email) {
        K2559251_ForeignCustomer newCustomer = new K2559251_ForeignCustomer(name, contactNumber, email, passport); // Create the foreign customer object
        dataStore.addCustomer(newCustomer); // Using DataStore to persist the customer
        return newCustomer; // Return the newly created customer
    }

    @Override
    public K2559251_Customer findCustomerByIdentifier(String identifier) {
        for (K2559251_Customer customer : dataStore.getAllCustomers()) {

            // If it's a LocalCustomer
            if (customer instanceof K2559251_LocalCustomer local) {
                if (local.getNic().equals(identifier)) {
                    return local; // Found it
                }
            // If it's a ForeignCustomer
            } else if (customer instanceof K2559251_ForeignCustomer foreign) {
                if (foreign.getPassport().equals(identifier)) {
                    return foreign; // Found it
                }
            }
        }
        return null; // Not found
    }

    @Override
    public List<K2559251_Customer> getAllCustomers() {
        return dataStore.getAllCustomers();
    }

    @Override
    public K2559251_Customer updateCustomer(String identifier, String newContactNumber, String newEmail) {
        // Reuse our own find method
        K2559251_Customer customerToUpdate = findCustomerByIdentifier(identifier);

        // Check if the customer was found
        if (customerToUpdate != null) {
            customerToUpdate.setContactNumber(newContactNumber);
            customerToUpdate.setEmail(newEmail);

            // Return the updated customer
            return customerToUpdate;
        }

        // If no customer was found, return null
        return null;
    }
}
