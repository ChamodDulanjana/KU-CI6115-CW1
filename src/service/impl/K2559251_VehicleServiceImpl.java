package service.impl;

import model.K2559251_AvailabilityStatus;
import model.K2559251_Vehicle;
import model.K2559251_VehicleCategory;
import repository.K2559251_DataStore;
import service.K2559251_VehicleService;

import java.util.List;
import java.util.stream.Collectors;

public class K2559251_VehicleServiceImpl implements K2559251_VehicleService {
    //  Encapsulation: The DataStore is private
    private final K2559251_DataStore dataStore;

    // Constructor (Dependency Injection).
    public K2559251_VehicleServiceImpl(K2559251_DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public K2559251_Vehicle addVehicle(String carId, String model, K2559251_VehicleCategory category) {
        // Check for duplicate ID
        if (findVehicles(carId) != null) {
            System.err.println("Error: Vehicle with ID " + carId + " already exists.");
            return null;
        }

        // Create the vehicle object
        K2559251_Vehicle newVehicle = new K2559251_Vehicle(
                carId,
                model,
                category,
                K2559251_AvailabilityStatus.AVAILABLE
        );

        // Add it to the datastore
        dataStore.addVehicle(newVehicle);

        // Return the newly created vehicle
        return newVehicle;
    }

    @Override
    public K2559251_Vehicle findVehicles(String carId) {
        return dataStore.getAllVehicles().stream()
                .filter(vehicle -> vehicle.getCarId().equals(carId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<K2559251_Vehicle> findVehicles(K2559251_VehicleCategory category) {
        return dataStore.getAllVehicles().stream()
                .filter(vehicle -> vehicle.getCategory() == category)
                .collect(Collectors.toList());
    }

    @Override
    public K2559251_Vehicle updateVehicle(String carId, String model, K2559251_VehicleCategory category) {
        // Find the vehicle to update.
        K2559251_Vehicle vehicleToUpdate = findVehicles(carId);

        // Check if it was found
        if (vehicleToUpdate != null) {
            // Update the fields using public setters (Encapsulation)
            vehicleToUpdate.setModel(model);
            vehicleToUpdate.setCategory(category);

            // Return the updated vehicle
            return vehicleToUpdate;
        }

        // If not found, return null
        System.err.println("Error: Could not update vehicle. ID " + carId + " not found.");
        return null;
    }

    @Override
    public K2559251_Vehicle changeAvailabilityStatus(String carId, K2559251_AvailabilityStatus status) {
        // Find the vehicle
        K2559251_Vehicle vehicleToUpdate = findVehicles(carId);

        // Check if found
        if (vehicleToUpdate != null) {
            // Update the status using its public setter (Encapsulation)
            vehicleToUpdate.setAvailabilityStatus(status);

            // Return the updated vehicle
            return vehicleToUpdate;
        }

        // If not found, return null
        System.err.println("Error: Could not change status. ID " + carId + " not found.");
        return null;
    }

    @Override
    public List<K2559251_Vehicle> getAllVehicles() {
        return dataStore.getAllVehicles();
    }
}