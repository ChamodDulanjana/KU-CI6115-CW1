package service.impl;

import model.*;
import repository.K2559251_DataStore;
import service.K2559251_BookingService;
import service.K2559251_CustomerService;
import service.K2559251_FeeCalculator;
import service.K2559251_VehicleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class K2559251_BookingServiceImpl implements K2559251_BookingService {
    //  Encapsulation: The DataStore is private
    private final K2559251_DataStore dataStore;
    private final K2559251_CustomerService customerService;
    private final K2559251_VehicleService vehicleService;
    private final K2559251_FeeCalculator feeCalculator;

    // Constructor (Dependency Injection).
    public K2559251_BookingServiceImpl(K2559251_DataStore dataStore,
                                       K2559251_CustomerService customerService,
                                       K2559251_VehicleService vehicleService,
                                       K2559251_FeeCalculator feeCalculator) {
        this.dataStore = dataStore;
        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.feeCalculator = feeCalculator;
    }

    @Override
    public K2559251_Booking createBooking(String customerIdentifier, String carId, LocalDate startDate, int numberOfDays, double estimatedKm) {
        // Find Customer
        K2559251_Customer customer = customerService.findCustomerByIdentifier(customerIdentifier);
        if (customer == null) {
            System.err.println("Booking Error: Customer " + customerIdentifier + " not found.");
            return null;
        }

        // Find Vehicle
        K2559251_Vehicle vehicle = vehicleService.findVehicles(carId);
        if (vehicle == null) {
            System.err.println("Booking Error: Vehicle " + carId + " not found.");
            return null;
        }

        // Business Rule: Check Availability
        if (vehicle.getAvailabilityStatus() != K2559251_AvailabilityStatus.AVAILABLE) {
            System.err.println("Booking Error: Vehicle " + carId + " is not available.");
            return null;
        }

        // Check 3-day Advance Booking Rule
        LocalDate today = LocalDate.now();
        LocalDate earliestBookingDate = today.plusDays(3);

        if (startDate.isBefore(earliestBookingDate)) {
            System.err.println("Booking Error: Booking must be at least 3 days in advance. " +
                    "Earliest start date is " + earliestBookingDate);
            return null;
        }

        // Check for Date Conflicts - Overlapping Bookings
        LocalDate newEndDate = startDate.plusDays(numberOfDays);
        for (K2559251_Booking existingBooking : dataStore.getAllBookings()) {

            // Only check bookings for the *same car*
            if (existingBooking.getVehicle().getCarId().equals(carId)) {

                // Get the start and end of the existing booking
                LocalDate existingStart = existingBooking.getStartDate();
                LocalDate existingEnd = existingBooking.getEndDate();

                // Overlap Check: A new booking [A, B] overlaps an existing [X, Y]
                // if (A < Y) and (B > X).
                if (startDate.isBefore(existingEnd) && newEndDate.isAfter(existingStart)) {
                    System.err.println("Booking Error: Vehicle " + carId + " is already booked " +
                            "from " + existingStart + " to " + existingEnd);
                    return null;
                }
            }
        }

        // All Rules Passed: Create the Booking

        // Create the new Booking object
        K2559251_Booking newBooking = new K2559251_Booking(
                customer,
                vehicle,
                startDate,
                numberOfDays,
                estimatedKm
        );
        newBooking.setStatus(K2559251_BookingStatus.CONFIRMED);

        // Add booking to datastore
        dataStore.addBooking(newBooking);

        // Update vehicle status
        vehicleService.changeAvailabilityStatus(carId, K2559251_AvailabilityStatus.RESERVED);

        return newBooking;
    }

    @Override
    public K2559251_Booking updateBooking(String bookingId, String carId, LocalDate startDate, int numberOfDays, double estimatedKm) {
        // Find the booking to update
        K2559251_Booking booking = findBookingById(bookingId);
        if (booking == null) {
            System.err.println("Update Error: Booking " + bookingId + " not found.");
            return null;
        }

        // Allow updates only within 2 days of booking creation
        if (LocalDate.now().isAfter(booking.getCreationDate().plusDays(2))) {
             System.err.println("Update Error: Cannot update a booking more than 2 days after it was made.");
             return null;
        }

        // Business Rule: Check for Date Conflicts (on the *new* car/dates)
        LocalDate newEndDate = startDate.plusDays(numberOfDays);
        for (K2559251_Booking existingBooking : dataStore.getAllBookings()) {

            // Skip checking against itself
            if (existingBooking.getBookingId().equals(bookingId)) {
                continue;
            }

            // Only check bookings for the *newly selected car*
            if (existingBooking.getVehicle().getCarId().equals(carId)) {
                LocalDate existingStart = existingBooking.getStartDate();
                LocalDate existingEnd = existingBooking.getEndDate();

                if (startDate.isBefore(existingEnd) && newEndDate.isAfter(existingStart)) {
                    System.err.println("Update Error: The new vehicle/dates conflict with an existing booking.");
                    return null;
                }
            }
        }

        // All Rules Passed: Update the Booking

        // Find the new vehicle
        K2559251_Vehicle newVehicle = vehicleService.findVehicles(carId);
        if (newVehicle == null) {
            System.err.println("Update Error: New vehicle " + carId + " not found.");
            return null;
        }

        // Release the OLD vehicle (if it's different)
        K2559251_Vehicle oldVehicle = booking.getVehicle();
        if (!oldVehicle.getCarId().equals(newVehicle.getCarId())) {
            // We are changing cars. Make the old one available.
            vehicleService.changeAvailabilityStatus(oldVehicle.getCarId(), K2559251_AvailabilityStatus.AVAILABLE);
        }

        // Reserve the NEW vehicle
        vehicleService.changeAvailabilityStatus(newVehicle.getCarId(), K2559251_AvailabilityStatus.RESERVED);

        // Update the booking object's fields.
        booking.setVehicle(newVehicle);
        booking.setStartDate(startDate);
        booking.setNumberOfDays(numberOfDays);
        booking.setEndDate(newEndDate);
        booking.setEstimatedKilometers(estimatedKm);

        System.out.println("Booking " + bookingId + " updated successfully.");
        return booking;
    }

    @Override
    public K2559251_Booking cancelBooking(String bookingId) {
        // Find the booking to Cancel
        K2559251_Booking booking = findBookingById(bookingId);
        if (booking == null) {
            System.err.println("Cancel Error: Booking " + bookingId + " not found.");
            return null;
        }

        // Allow Cancel only within 2 days of booking creation
        if (LocalDate.now().isAfter(booking.getCreationDate().plusDays(2))) {
            System.err.println("Cancel Error: Cannot Cancel a booking more than 2 days after it was made.");
            return null;
        }

        // Check if booking is already completed or cancelled
        if (booking.getStatus() == K2559251_BookingStatus.COMPLETED ||
                booking.getStatus() == K2559251_BookingStatus.CANCELLED) {
            System.err.println("Cancel Error: Booking is already " + booking.getStatus());
            return null;
        }

        // All Rules Passed: Cancel the Booking

        // Set status to CANCELLED
        booking.setStatus(K2559251_BookingStatus.CANCELLED);

        // Make the vehicle available again
        vehicleService.changeAvailabilityStatus(
                booking.getVehicle().getCarId(),
                K2559251_AvailabilityStatus.AVAILABLE
        );

        System.out.println("Booking " + bookingId + " cancelled successfully.");
        return booking;
    }

    @Override
    public K2559251_Invoice completeRental(String bookingId, double actualKilometers) {
        // Find the booking
        K2559251_Booking booking = findBookingById(bookingId);
        if (booking == null) {
            System.err.println("Complete Error: Booking " + bookingId + " not found.");
            return null;
        }

        // Check status (can only complete a 'CONFIRMED' booking)
        if (booking.getStatus() != K2559251_BookingStatus.CONFIRMED) {
            System.err.println("Complete Error: Booking is not active. Current status: " + booking.getStatus());
            return null;
        }

        // Update the booking with actual KM and 'COMPLETED' status
        booking.completeRental(actualKilometers);

        // Make the vehicle available again
        vehicleService.changeAvailabilityStatus(
                booking.getVehicle().getCarId(),
                K2559251_AvailabilityStatus.AVAILABLE
        );

        // Call the Fee Calculator to get the final invoice
        K2559251_Invoice invoice = feeCalculator.calculateFinalFee(booking);

        System.out.println("Rental for booking " + bookingId + " completed. Invoice " + invoice.getInvoiceId() + " generated.");
        return invoice;
    }

    @Override
    public K2559251_Booking findBookingById(String bookingId) {
        return dataStore.getAllBookings().stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<K2559251_Booking> findBookingsByCustomer(String customerIdentifier) {
        // Find the customer first
        K2559251_Customer customer = customerService.findCustomerByIdentifier(customerIdentifier);
        if (customer == null) {
            System.err.println("Find Error: Customer " + customerIdentifier + " not found.");
            return new ArrayList<>(); // Return an empty list
        }

        // Filter bookings by customer
        return dataStore.getAllBookings().stream()
                .filter(booking -> booking.getCustomer().getIdentifier().equals(customer.getIdentifier()))
                .collect(Collectors.toList());
    }

    @Override
    public List<K2559251_Booking> findBookingsByDate(LocalDate date) {
        return dataStore.getAllBookings().stream()
                .filter(booking -> {
                    LocalDate bookingStart = booking.getStartDate();
                    LocalDate bookingEnd = booking.getEndDate();

                    // Check if the date is *on or after* the start AND *before* the end
                    // (Rentals are [startDate, endDate) )
                    return (date.isEqual(bookingStart) || date.isAfter(bookingStart)) &&
                            date.isBefore(bookingEnd);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<K2559251_Booking> getAllBookings() {
        return dataStore.getAllBookings();
    }
}
