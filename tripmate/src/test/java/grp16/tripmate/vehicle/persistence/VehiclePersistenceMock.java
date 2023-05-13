package grp16.tripmate.vehicle.persistence;

import grp16.tripmate.vehicle.persistence.Vehicle.IVehiclePersistence;
import grp16.tripmate.vehicle.model.Vehicle.Vehicle;
import grp16.tripmate.vehicle.model.Vehicle.VehicleFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehiclePersistenceMock implements IVehiclePersistence {

    private static final Map<Integer, Vehicle> vehicleDb = new HashMap<>();

    public VehiclePersistenceMock() {
        Vehicle vehicle = VehicleFactory.getInstance().getNewVehicle();
        vehicle.setId(1);
        vehicle.setName("Maruti 800");
        vehicle.setNumberOfSeats(5);
        vehicle.setRegistrationNumber("HFX8992");
        vehicle.setIsAvailable(true);
        vehicle.setIsForLongJourney(true);
        vehicle.setRatePerKm(7.0F);
        vehicle.setCategoryId(2);
        vehicle.setCategoryName("Sedan");
        vehicleDb.put(1, vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Map.Entry<Integer, Vehicle> entry : vehicleDb.entrySet()) {
            vehicles.add(entry.getValue());
        }
        return vehicles;
    }

    @Override
    public Vehicle getVehicleById(int vehicleId) {
        return vehicleDb.get(vehicleId);
    }

    @Override
    public boolean deleteVehiclesByPostId(int vehicleId) {
        return (vehicleDb.remove(vehicleId) == null);
    }

    public float getVehicleRatePerKmByVehicleId(int vehicleId) {
        return vehicleDb.get(vehicleId).getRatePerKm();
    }
}