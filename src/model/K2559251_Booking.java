package model;

import java.time.LocalDate;

public class K2559251_Booking {
    private String bookingId;
    private K2559251_Customer customer;
    private K2559251_Vehicle vehicle;

    private LocalDate creationDate;     // Date when the booking was created
    private LocalDate startDate;
    private LocalDate endDate;          // This will be calculated
    private int numberOfDays;

    private double estimatedKilometers; // Provided by customer at booking
    private double actualKilometers;    // Set when rental is completed

    private double depositAmount;
    private K2559251_BookingStatus status;

    public K2559251_Booking(
            String bookingId,
            K2559251_Customer customer,
            K2559251_Vehicle vehicle,
            LocalDate startDate,
            int numberOfDays,
            double estimatedKilometers) {
        this.bookingId = bookingId; // Simple unique ID
        this.customer = customer;
        this.vehicle = vehicle;
        this.creationDate = LocalDate.now();               // Set creation date to current date
        this.startDate = startDate;
        this.endDate = startDate.plusDays(numberOfDays);    // Calculate the end date automatically.
        this.numberOfDays = numberOfDays;
        this.estimatedKilometers = estimatedKilometers;
        this.actualKilometers = 0;                          // Initially zero, to be updated later.
        this.depositAmount = 5000.0;                        // Fixed deposit amount
        this.status = K2559251_BookingStatus.PENDING;       // Initial status
    }

    // This method called when the rental is finished and the car is returned.
    public void completeRental(double actualKilometers) {
        this.actualKilometers = actualKilometers;
        this.status = K2559251_BookingStatus.COMPLETED;
    }

    // Public Getters methods

    public String getBookingId() { return bookingId; }
    public K2559251_Customer getCustomer() { return customer; }
    public K2559251_Vehicle getVehicle() { return vehicle; }
    public LocalDate getCreationDate() { return creationDate; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getNumberOfDays() { return numberOfDays; }
    public double getEstimatedKilometers() { return estimatedKilometers; }
    public double getActualKilometers() { return actualKilometers; }
    public double getDepositAmount() { return depositAmount; }
    public K2559251_BookingStatus getStatus() { return status; }

    // Public Setters methods

    public void setStatus(K2559251_BookingStatus status) {
        this.status = status;
    }

    public void setVehicle(K2559251_Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEstimatedKilometers(double estimatedKm) {
        this.estimatedKilometers = estimatedKm;
    }

    @Override
    public String toString() {
        return "Booking[ID=" + bookingId +
                ", Customer=" + customer.getName() +
                ", Vehicle=" + vehicle.getModel() +
                ", Status=" + status + "]";
    }
}
