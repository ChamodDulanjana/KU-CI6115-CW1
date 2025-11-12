package model;

public enum K2559251_VehicleCategory {
    COMPACT_PETROL(5000, 100, 50, 0.10),
    HYBRID(7500, 150, 60, 0.12),
    ELECTRIC(10000, 200, 40, 0.08),
    LUXURY_SUV(15000, 250, 75, 0.15);

    // Encapsulated fields
    private final double dailyRentalFee;
    private final int freeKmPerDay;
    private final double extraKmCharge;
    private final double taxRate;

    /**
     * Private constructor to initialize the encapsulated fields for each enum constant.
     */
    K2559251_VehicleCategory(double dailyRentalFee, int freeKmPerDay, double extraKmCharge, double taxRate) {
        this.dailyRentalFee = dailyRentalFee;
        this.freeKmPerDay = freeKmPerDay;
        this.extraKmCharge = extraKmCharge;
        this.taxRate = taxRate;
    }

    // Public getter methods to access the encapsulated data

    public double getDailyRentalFee() {
        return dailyRentalFee;
    }

    public int getFreeKmPerDay() {
        return freeKmPerDay;
    }

    public double getExtraKmCharge() {
        return extraKmCharge;
    }

    public double getTaxRate() {
        return taxRate;
    }
}

