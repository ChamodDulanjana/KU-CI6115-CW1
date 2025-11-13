package repository;

import model.K2559251_Booking;
import model.K2559251_Customer;
import model.K2559251_Vehicle;

import java.util.List;

public class K2559251_DataStore {

    // Encapsulated Fields
    private final List<K2559251_Vehicle> vehicles;
    private final List<K2559251_Booking> bookings;
    private final List<K2559251_Customer> customers; // POLYMORPHISM Demonstration - can hold objects of ANY child class (LocalCustomer, ForeignCustomer).

    // Constructor initializes the in-memory lists.
    public K2559251_DataStore(
            List<K2559251_Vehicle> vehicles,
            List<K2559251_Booking> bookings,
            List<K2559251_Customer> customers
    ) {
        this.vehicles = vehicles;
        this.bookings = bookings;
        this.customers = customers;
    }

    // public methods for accessing the lists.

    // Public Methods for VEHICLES
    public void addVehicle(K2559251_Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public List<K2559251_Vehicle> getAllVehicles() {
        return List.copyOf(this.vehicles);  // Returns an unmodifiable copy of the vehicle list to protect the original list (strong encapsulation).
    }

    // Public Methods for CUSTOMERS
    public void addCustomer(K2559251_Customer customer) {
        this.customers.add(customer);  // his method polymorphically accepts any object that IS-A K2559251_Customer.
    }

    public List<K2559251_Customer> getAllCustomers() {
        return List.copyOf(this.customers);
    }

    // Public Methods for BOOKINGS
    public void addBooking(K2559251_Booking booking) {
        this.bookings.add(booking);
    }

    public List<K2559251_Booking> getAllBookings() {
        return List.copyOf(this.bookings);
    }
}
