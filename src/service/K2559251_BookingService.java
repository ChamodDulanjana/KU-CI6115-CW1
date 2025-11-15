package service;

import model.K2559251_Booking;
import model.K2559251_Invoice;

import java.time.LocalDate;
import java.util.List;

public interface K2559251_BookingService {
    K2559251_Booking createBooking(String customerIdentifier, String carId, LocalDate startDate,
                                   int numberOfDays, double estimatedKm);
    K2559251_Booking updateBooking(String bookingId, String carId, LocalDate startDate,
                                   int numberOfDays, double estimatedKm);
    K2559251_Booking cancelBooking(String bookingId);
    K2559251_Invoice completeRental(String bookingId, double actualKilometers);
    K2559251_Booking findBookingById(String bookingId);
    List<K2559251_Booking> findBookingsByCustomer(String customerIdentifier);
    List<K2559251_Booking> findBookingsByDate(LocalDate date);
    List<K2559251_Booking> getAllBookings();
}
