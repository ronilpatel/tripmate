package grp16.tripmate.vehicle.model.Vehicle;

import java.text.ParseException;
import java.util.List;

public interface IVehicle
{
    List<Vehicle> getAllVehicles() throws ParseException;

    Vehicle getVehicleById(int vehicleId) throws ParseException;

    List<Vehicle> getRecommendedTripVehicles(int postId);

    List<Vehicle> getTripVehicles(int postId);

    List<Vehicle> getVehiclesByUserId(int userId);
}
