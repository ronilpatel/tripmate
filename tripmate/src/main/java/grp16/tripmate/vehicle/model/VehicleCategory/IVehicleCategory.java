package grp16.tripmate.vehicle.model.VehicleCategory;

import java.util.List;

public interface IVehicleCategory
{
    List<VehicleCategory> getAllVehicleCategory();

    VehicleCategory getVehicleCategoryByVehicleId(int vehicleId);

    String getCategoryNameByCategoryId(int categoryId);

}
