import model.*;
import repository.K2559251_DataStore;
import service.K2559251_BookingService;
import service.K2559251_CustomerService;
import service.K2559251_FeeCalculator;
import service.K2559251_VehicleService;
import service.impl.K2559251_BookingServiceImpl;
import service.impl.K2559251_CustomerServiceImpl;
import service.impl.K2559251_FeeCalculatorImpl;
import service.impl.K2559251_VehicleServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class Main {
    private static final LocalDate TODAY = LocalDate.now();

    public static void main(String[] args) {
        System.out.println("====== ECO-RIDE SYSTEM DEMONSTRATION ======");
        System.out.println("Running demonstration as of date: " + TODAY + "\n");
        System.out.println("Student ID: K2559251\n");

        // DEPENDENCY INJECTION
        // We create all our objects. This is the "setup" phase.
        System.out.println("--- Initializing Services (Dependency Injection) ---");

        // Create the single DataStore (Repository)
        K2559251_DataStore dataStore = new K2559251_DataStore();

        // Create the FeeCalculator (Implementation)
        K2559251_FeeCalculator feeCalculator = new K2559251_FeeCalculatorImpl();

        // 1c. --- ABSTRACTION Demonstration (Interface) ---
        // We declare variables using the INTERFACE type, not the class type.
        // This proves we are "coding to an interface."
        K2559251_CustomerService customerService = new K2559251_CustomerServiceImpl(dataStore);
        K2559251_VehicleService vehicleService = new K2559251_VehicleServiceImpl(dataStore);
        K2559251_BookingService bookingService = new K2559251_BookingServiceImpl(
                dataStore, customerService, vehicleService, feeCalculator
        );
        System.out.println("Services Initialized.\n");

        // -------------------------------------------------------------------------------------------------------------
        System.out.println("================================================================================ \n");

        // DEMONSTRATING ENCAPSULATION & SERVICE ABSTRACTION
        System.out.println("--- Encapsulation (Adding Vehicles) ---");

        // The Main class only knows about 'addVehicle'.
        // The *implementation* (checking for duplicates, etc.) is hidden.
        K2559251_Vehicle car1 = vehicleService.addVehicle("V001", "Toyota Aqua", K2559251_VehicleCategory.HYBRID);
        K2559251_Vehicle car2 = vehicleService.addVehicle("V002", "Nissan Leaf", K2559251_VehicleCategory.ELECTRIC);
        K2559251_Vehicle car3 = vehicleService.addVehicle("V003", "BMW X5", K2559251_VehicleCategory.LUXURY_SUV);

        System.out.println("Added Vehicle: \n" + " Car Id   - " +  car1.getCarId() + "\n Model    - " + car1.getModel() + "\n Category - " +  car1.getCategory()  + "\n Status   - " + car1.getAvailabilityStatus() + "\n");
        System.out.println("Added Vehicle: \n" + " Car Id   - " +  car2.getCarId() + "\n Model    - " + car2.getModel() + "\n Category - " +  car2.getCategory()  + "\n Status   - " + car2.getAvailabilityStatus() + "\n");
        System.out.println("Added Vehicle: \n" + " Car Id   - " +  car3.getCarId() + "\n Model    - " + car3.getModel() + "\n Category - " +  car3.getCategory()  + "\n Status   - " + car3.getAvailabilityStatus() + "\n");

        // We can't do this (field is private): car1.availabilityStatus = K2559251_AvailabilityStatus.UNDER_MAINTENANCE;
        // We must use the public method (setter), which is good Encapsulation.
        vehicleService.changeAvailabilityStatus(car3.getCarId(), K2559251_AvailabilityStatus.UNDER_MAINTENANCE);
        System.out.println("Vehicle " + car3.getCarId() + " status set via public method: " + car3.getAvailabilityStatus());
        System.out.println(" Car Id   - " +  car3.getCarId() + "\n Model    - " + car3.getModel() + "\n Category - " +  car3.getCategory()  + "\n Status   - " + car3.getAvailabilityStatus() + "\n");


        // -------------------------------------------------------------------------------------------------------------
        System.out.println("================================================================================ \n");

        // DEMONSTRATING INHERITANCE & SUBTYPE POLYMORPHISM
        System.out.println("--- Inheritance & Polymorphism (Adding Customers) ---");

        // We create two different *types* of customers...
        K2559251_Customer cust1 = customerService.addLocalCustomer(
                "991234567V", "K2559251 Student", "077123456", "k2559251@uni.com"
        );
        K2559251_Customer cust2 = customerService.addForeignCustomer(
                "P123456", "John Doe", "011987654", "j.doe@email.com"
        );

        // ...and we store them in the *same* list (List<K2559251_Customer>).
        System.out.println("Looping through polymorphic List<K2559251_Customer>:");
        for (K2559251_Customer cust : customerService.getAllCustomers()) {
            // We call the *same* method 'getIdentifier()...'
            // ...but we get *different* behavior. This is Polymorphism.
            System.out.println("  - " + cust.getIdentifier());
        }
        System.out.println();

        // -------------------------------------------------------------------------------------------------------------
        System.out.println("================================================================================ \n");

        // DEMONSTRATING AD-HOC POLYMORPHISM (METHOD OVERLOADING)
        System.out.println("--- Polymorphism (Method Overloading) ---");

        // Calling findVehicles(String)
        K2559251_Vehicle foundCar = vehicleService.findVehicles("V002");
        System.out.println("Called findVehicles(\"V002\"). Found: " + foundCar.getModel());

        // Calling findVehicles(K2559251_VehicleCategory)
        List<K2559251_Vehicle> hybridCars = vehicleService.findVehicles(K2559251_VehicleCategory.HYBRID);
        System.out.println("Called findVehicles(Category.HYBRID). Found: " + hybridCars.size() + " car(s).");
        System.out.println("This is Polymorphism: The *same method name* 'findVehicles' was called");
        System.out.println("with different parameters, and the correct version was executed.\n");

        // -------------------------------------------------------------------------------------------------------------
        System.out.println("================================================================================ \n");

        // --- DEMONSTRATING BUSINESS LOGIC (BOOKING RULES) ---
        System.out.println("--- Business Logic & Rules (Bookings) ---");

        // Rule 1: 3-Day Advance. (Today is 14-Nov. We try to book for 15-Nov)
        System.out.println("Test 1: Trying to book 1 day in advance (Should Fail)...");
        K2559251_Booking bookingFail1 = bookingService.createBooking(
                "991234567V", "V001", TODAY.plusDays(1), 5, 300
        );
        if (bookingFail1 == null) System.out.println("Result: FAILED as expected.");

        // Rule 2: Availability. (V003 is UNDER_MAINTENANCE)
        System.out.println("\nTest 2: Trying to book unavailable car V003 (Should Fail)...");
        K2559251_Booking bookingFail2 = bookingService.createBooking(
                "991234567V", "V003", TODAY.plusDays(10), 5, 300
        );
        if (bookingFail2 == null) System.out.println("Result: FAILED as expected.");

        // Rule 3: Successful Booking (7 days, for discount test)
        System.out.println("\nTest 3: Making a valid 7-day booking (B001)...");
        K2559251_Booking booking1 = bookingService.createBooking(
                "991234567V", "V001", TODAY.plusDays(5), 7, 400
        );
        if (booking1 != null) System.out.println("Result: SUCCESS. Booking ID: " + booking1.getBookingId());

        // Rule 4: Date Conflict (Try to book same car on overlapping dates)
        System.out.println("\nTest 4: Trying to book V001 on overlapping dates (Should Fail)...");
        K2559251_Booking bookingFail3 = bookingService.createBooking(
                "P123456", "V001", TODAY.plusDays(6), 3, 150
        );
        if (bookingFail3 == null) System.out.println("Result: FAILED as expected.");

        // Rule 5: Successful Second Booking (3 days)
        System.out.println("\nTest 5: Making a valid 3-day booking (B002)...");
        K2559251_Booking booking2 = bookingService.createBooking(
                "P123456", "V002", TODAY.plusDays(4), 3, 200
        );
        if (booking2 != null) System.out.println("Result: SUCCESS. Booking ID: " + booking2.getBookingId());
        System.out.println();


        // -------------------------------------------------------------------------------------------------------------
        System.out.println("================================================================================ \n");

        // --- DEMONSTRATING 2-DAY CANCEL RULE ---
        System.out.println("--- Business Logic (2-Day Cancel Rule) ---");

        // Today is 14-Nov. Booking B002 was created on 14-Nov.
        // We are within the 2-day window, so this should succeed.
        System.out.println("Test 6: Cancelling booking B002 (Should Succeed)...");
        K2559251_Booking cancelledBooking = bookingService.cancelBooking("B002");
        if (cancelledBooking != null) {
            System.out.println("Result: SUCCESS. Status is now: " + cancelledBooking.getStatus());
            System.out.println("Check car status: V002 is now: " + car2.getAvailabilityStatus());
        }

        // (To test the failure case, we would change 'TODAY' in BookingServiceImpl
        // to 3 days after the creationDate and re-run, which would fail)
        System.out.println();


        // -------------------------------------------------------------------------------------------------------------
        System.out.println("================================================================================ \n");

        // --- DEMONSTRATING FINAL FEE CALCULATION ---
        System.out.println("--- Final Fee Calculation (B001) ---");

        System.out.println("Completing booking B001...");
        System.out.println("  - Rental was 7 days (Should get 10% discount).");
        System.out.println("  - Car is HYBRID (150 free km/day * 7 = 1050 free km).");
        System.out.println("  - Customer drove 1200 km (150km over limit).");

        // Complete the rental
        K2559251_Invoice finalInvoice = bookingService.completeRental("B001", 1200.0);

        if (finalInvoice != null) {
            System.out.println("\nRental complete. Final Invoice (INV-B001):");
            // Call the encapsulated print method
            finalInvoice.printInvoice();
        }
        System.out.println("\nCheck car status: V001 is now: " + car1.getAvailabilityStatus());
        System.out.println();


        System.out.println("====== DEMONSTRATION COMPLETE ======");
    }
}