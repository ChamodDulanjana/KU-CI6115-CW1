package model;

public class K2559251_Invoice {
    private final String invoiceId;
    private final K2559251_Booking booking; // The booking this invoice is for

    // --- Calculated Values ---
    private final double basePrice;       // (Daily Rate * Number of Days)
    private final double extraKmCharge;   // (Charge for kms over the free limit)
    private final double discount;        // (10% discount if >= 7 days)
    private final double subtotal;        // (Base Price + Extra Km Charge - Discount)
    private final double tax;             // (Tax applied to the subtotal)
    private final double depositDeducted; // (The LKR 5,000 deposit)
    private final double finalPayableAmount; // (Subtotal + Tax - Deposit)

    /**
     * Constructor for creating a new invoice.
     * All values are passed in after being calculated by a service class.
     */

    public K2559251_Invoice(
            K2559251_Booking booking,
            double basePrice,
            double extraKmCharge,
            double discount,
            double subtotal,
            double tax,
            double depositDeducted,
            double finalPayableAmount
    ) {
        this.invoiceId = "INV" + System.currentTimeMillis(); // Simple unique ID generation
        this.booking = booking;
        this.basePrice = basePrice;
        this.extraKmCharge = extraKmCharge;
        this.discount = discount;
        this.subtotal = subtotal;
        this.tax = tax;
        this.depositDeducted = depositDeducted;
        this.finalPayableAmount = finalPayableAmount;
    }

    // --- Public Getters (to read data) ---
    // No setters are provided, as an invoice is "immutable"
    // (it can't be changed) once it is created.

    public String getInvoiceId() { return invoiceId; }
    public K2559251_Booking getBooking() { return booking; }
    public double getBasePrice() { return basePrice; }
    public double getExtraKmCharge() { return extraKmCharge; }
    public double getDiscount() { return discount; }
    public double getSubtotal() { return subtotal; }
    public double getTax() { return tax; }
    public double getDepositDeducted() { return depositDeducted; }
    public double getFinalPayableAmount() { return finalPayableAmount; }

    /**
     * A helper method to print a formatted invoice to the console.
     * This is for demonstration purposes in your K2559251_Main class.
     */
    public void printInvoice() {
        System.out.println("--- INVOICE " + invoiceId + " ---");
        System.out.println("Booking ID: " + booking.getBookingId());
        System.out.println("Customer: " + booking.getCustomer().getName());
        System.out.println("Vehicle: " + booking.getVehicle().getModel());
        System.out.println("---------------------------------");
        System.out.printf("  Base Price (%d days): LKR %.2f\n", booking.getNumberOfDays(), basePrice);
        System.out.printf("  Extra KM Charge:      LKR %.2f\n", extraKmCharge);
        System.out.printf("  Discount (>= 7 days): LKR %.2f\n", discount);
        System.out.printf("  Subtotal:             LKR %.2f\n", subtotal);
        System.out.printf("  Tax:                  LKR %.2f\n", tax);
        System.out.printf("  Deposit Deducted:     LKR %.2f\n", depositDeducted);
        System.out.println("---------------------------------");
        System.out.printf("  FINAL AMOUNT DUE:     LKR %.2f\n", finalPayableAmount);
        System.out.println("--- END OF INVOICE ---");
    }
}
