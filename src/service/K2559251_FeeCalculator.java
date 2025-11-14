package service;

import model.K2559251_Booking;
import model.K2559251_Invoice;

public interface K2559251_FeeCalculator {
    // Calculates the final fee for a completed booking and returns an invoice.
    K2559251_Invoice calculateFinalFee(K2559251_Booking booking);
}
