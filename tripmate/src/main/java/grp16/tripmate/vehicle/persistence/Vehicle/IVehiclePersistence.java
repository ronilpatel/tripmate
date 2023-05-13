package grp16.tripmate.vehicle.persistence.Vehicle;

import grp16.tripmate.vehicle.model.Vehicle.Vehicle;

import java.text.ParseException;
import java.util.List;


public interface IVehiclePersistence
{
    List<Vehicle> getAllVehicles() throws ParseException;
    Vehicle getVehicleById(int vehicleId) throws ParseException;
    boolean deleteVehiclesByPostId(int postId);

}
