package grp16.tripmate.vehicle.persistence.VehicleCategory;

public interface IVehicleCategoryQueryGenerator {
    String getVehicleCategoryByVehicleId(int vehicleId);
    String getVehicleCategoryById(int categoryId);
}
