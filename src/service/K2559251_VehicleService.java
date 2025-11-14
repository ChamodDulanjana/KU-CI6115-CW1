package service;

import model.K2559251_AvailabilityStatus;
import model.K2559251_Vehicle;
import model.K2559251_VehicleCategory;

import java.util.List;

public interface K2559251_VehicleService {
    K2559251_Vehicle addVehicle(String carId, String model, K2559251_VehicleCategory category);

    /** POLYMORPHISM (Overloading) Demonstration - Finds a *single* vehicle by its unique ID.*/
    K2559251_Vehicle findVehicles(String carId);

    /** POLYMORPHISM (Overloading) Demonstration - Finds a *list* of vehicles that match a specific category.*/
    List<K2559251_Vehicle> findVehicles(K2559251_VehicleCategory category);

    K2559251_Vehicle updateVehicle(String carId, String model, K2559251_VehicleCategory category);
    K2559251_Vehicle changeAvailabilityStatus(String carId, K2559251_AvailabilityStatus status);
    List<K2559251_Vehicle> getAllVehicles();

}