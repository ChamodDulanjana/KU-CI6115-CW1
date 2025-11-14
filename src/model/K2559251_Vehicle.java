package model;

public class K2559251_Vehicle {
    private String carId;
    private String model;
    private K2559251_VehicleCategory category;
    private K2559251_AvailabilityStatus availabilityStatus;

    public K2559251_Vehicle(String carId, String model, K2559251_VehicleCategory category, K2559251_AvailabilityStatus availabilityStatus) {
        this.carId = carId;
        this.model = model;
        this.category = category;
        this.availabilityStatus = availabilityStatus;
    }

    // Getters and Setters

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public K2559251_VehicleCategory getCategory() {
        return category;
    }

    public void setCategory(K2559251_VehicleCategory category) {
        this.category = category;
    }

    public K2559251_AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(K2559251_AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    @Override
    public String toString() {
        return "K2559251_Vehicle{" +
                "carId='" + carId + '\'' +
                ", model='" + model + '\'' +
                ", category=" + category +
                ", availabilityStatus=" + availabilityStatus +
                '}';
    }
}
