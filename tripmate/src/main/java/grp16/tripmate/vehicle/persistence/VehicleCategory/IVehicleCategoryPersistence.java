package grp16.tripmate.vehicle.persistence.VehicleCategory;

import grp16.tripmate.vehicle.model.VehicleCategory.VehicleCategory;

import java.text.ParseException;

public interface IVehicleCategoryPersistence
{
    VehicleCategory getVehicleCategoryByVehicleId(int vehicleId) throws ParseException;

    VehicleCategory getVehicleCategoryById(int categoryId) throws ParseException;
}
