package service.impl;

import model.K2559251_Booking;
import model.K2559251_Invoice;
import model.K2559251_VehicleCategory;
import service.K2559251_FeeCalculator;

public class K2559251_FeeCalculatorImpl implements K2559251_FeeCalculator {

    @Override
    public K2559251_Invoice calculateFinalFee(K2559251_Booking booking) {
        // Get all necessary data from the booking
        K2559251_VehicleCategory category = booking.getVehicle().getCategory();
        int numDays = booking.getNumberOfDays();
        double actualKm = booking.getActualKilometers();
        double deposit = booking.getDepositAmount();

        // Calculate Base Price
        double basePrice = category.getDailyRentalFee() * numDays;

        // Calculate Extra Km Charges
        int freeKmAlloc = category.getFreeKmPerDay() * numDays;
        double extraKmDriven = 0;
        if (actualKm > freeKmAlloc) {
            extraKmDriven = actualKm - freeKmAlloc;
        }
        double extraKmCharge = extraKmDriven * category.getExtraKmCharge();

        // Apply 10% Discount
        double discount = 0;
        if (numDays >= 7) {
            // 10% discount on the *base rental price*
            discount = basePrice * 0.10;
        }

        // Calculate Subtotal
        double subtotal = basePrice + extraKmCharge - discount;

        // Add Tax
        double tax = subtotal * category.getTaxRate();

        // Calculate Final Amount (Deduct Deposit)
        double finalPayableAmount = (subtotal + tax) - deposit;

        // 6. Create and return the new invoice object
        return new K2559251_Invoice(
                booking,
                basePrice,
                extraKmCharge,
                discount,
                subtotal,
                tax,
                deposit,
                finalPayableAmount
        );
    }
}
