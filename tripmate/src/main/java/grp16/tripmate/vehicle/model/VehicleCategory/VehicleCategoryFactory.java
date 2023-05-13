package grp16.tripmate.vehicle.model.VehicleCategory;

import grp16.tripmate.vehicle.persistence.VehicleCategory.IVehicleCategoryPersistence;
import grp16.tripmate.vehicle.persistence.VehicleCategory.IVehicleCategoryQueryGenerator;
import grp16.tripmate.vehicle.persistence.VehicleCategory.VehicleCategoryPersistence;
import grp16.tripmate.vehicle.persistence.VehicleCategory.VehicleCategoryQueryGenerator;

public class VehicleCategoryFactory implements IVehicleCategoryFactory
{
    private static IVehicleCategoryFactory instance;

    private VehicleCategoryFactory()
    {

    }

    public static VehicleCategoryFactory getInstance() {
        if (instance == null) {
            instance = new VehicleCategoryFactory();
        }
        return (VehicleCategoryFactory) instance;
    }

    @Override
    public VehicleCategory getNewVehicleCategory() {
        return new VehicleCategory();
    }

    @Override
    public IVehicleCategoryPersistence getVehicleCategoryDatabase() {
        return new VehicleCategoryPersistence();
    }

    @Override
    public IVehicleCategoryQueryGenerator getVehicleCategoryQueryGenerator() {
        return VehicleCategoryQueryGenerator.getInstance();
    }
}
