package service.impl;

import model.K2559251_Customer;
import model.K2559251_ForeignCustomer;
import model.K2559251_LocalCustomer;
import repository.K2559251_DataStore;
import service.K2559251_CustomerService;

import java.util.List;
import java.util.Objects;

public class K2559251_CustomerServiceImpl implements K2559251_CustomerService {

    //  Encapsulation: The DataStore is private
    private final K2559251_DataStore dataStore;

    // Constructor (Dependency Injection).
    public K2559251_CustomerServiceImpl(K2559251_DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public K2559251_Customer addLocalCustomer(String nic, String name, String contactNumber, String email) {
        // Check for duplicate NIC
        if (findCustomerByIdentifier(nic) != null) {
            System.err.println("Error: Customer with NIC " + nic + " already exists.");
            return null;
        }

        // Check for duplicate email
        if (findCustomerByEmail(email) != null) {
            System.err.println("Error: Customer with email " + email + " already exists.");
            return null;
        }

        // Check for duplicate contact number
        if (findCustomerByContact(contactNumber) != null) {
            System.err.println("Error: Customer with contact number " + contactNumber + " already exists.");
            return null;
        }

        // Create the local customer object
        K2559251_LocalCustomer newCustomer = new K2559251_LocalCustomer(
                name,
                contactNumber,
                email,
                nic
        );

        // Using DataStore to persist the customer
        dataStore.addCustomer(newCustomer);

        // Return the newly created customer
        return newCustomer;
    }

    @Override
    public K2559251_Customer addForeignCustomer(String passport, String name, String contactNumber, String email) {
        // Check for duplicate NIC
        if (findCustomerByIdentifier(passport) != null) {
            System.err.println("Error: Customer with Passport " + passport + " already exists.");
            return null;
        }

        // Check for duplicate email
        if (findCustomerByEmail(email) != null) {
            System.err.println("Error: Customer with email " + email + " already exists.");
            return null;
        }

        // Check for duplicate contact number
        if (findCustomerByContact(contactNumber) != null) {
            System.err.println("Error: Customer with contact number " + contactNumber + " already exists.");
            return null;
        }

        // Create the foreign customer object
        K2559251_ForeignCustomer newCustomer = new K2559251_ForeignCustomer(
                name,
                contactNumber,
                email,
                passport
        );

        // Using DataStore to persist the customer
        dataStore.addCustomer(newCustomer);

        // Return the newly created customer
        return newCustomer;
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
    public K2559251_Customer findCustomerByContact(String contactNumber) {
        return dataStore.getAllCustomers().stream()
                .filter(customer -> customer.getContactNumber().equals(contactNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public K2559251_Customer findCustomerByEmail(String email) {
        return dataStore.getAllCustomers().stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<K2559251_Customer> getAllCustomers() {
        return dataStore.getAllCustomers();
    }

    @Override
    public K2559251_Customer updateCustomer(String identifier, String newContactNumber, String newEmail) {
        // Reuse our own find method
        K2559251_Customer customerToUpdate = findCustomerByIdentifier(identifier);

        // Check for duplicate email
        K2559251_Customer emailOwner = findCustomerByEmail(newEmail);
        if (emailOwner != null && !Objects.equals(emailOwner.getIdentifier(), customerToUpdate.getIdentifier())) {
            System.err.println("Error: Another customer with email " + newEmail + " already exists.");
            return null;
        }

        // Check for duplicate contact number
        K2559251_Customer contactOwner = findCustomerByContact(newContactNumber);
        if (contactOwner != null && !Objects.equals(contactOwner.getIdentifier(), customerToUpdate.getIdentifier())) {
            System.err.println("Error: Another customer with contact number " + newContactNumber + " already exists.");
            return null;
        }

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
