package grp16.tripmate.vehicle.persistence.Vehicle;


public interface IVehicleQueryGenerator {
    String getAllVehicles();

    String getVehicleById(int vehicleId);

    String getVehiclesByPostId(int postId);

    String getVehiclesByUserId(int userId);

    String deleteVehiclesByPostId(int postId);
}