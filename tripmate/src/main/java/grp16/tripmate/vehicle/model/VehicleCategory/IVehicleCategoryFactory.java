package grp16.tripmate.vehicle.model.VehicleCategory;

import grp16.tripmate.vehicle.persistence.VehicleCategory.IVehicleCategoryPersistence;
import grp16.tripmate.vehicle.persistence.VehicleCategory.IVehicleCategoryQueryGenerator;

public interface IVehicleCategoryFactory {

    VehicleCategory getNewVehicleCategory();
    IVehicleCategoryPersistence getVehicleCategoryDatabase();

    IVehicleCategoryQueryGenerator getVehicleCategoryQueryGenerator();
}


