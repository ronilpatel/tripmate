package grp16.tripmate.vehicle.model.Vehicle;

import grp16.tripmate.vehicle.persistence.Vehicle.IVehiclePersistence;
import grp16.tripmate.vehicle.persistence.Vehicle.IVehicleQueryGenerator;
import grp16.tripmate.vehicle.persistence.Vehicle.VehiclePersistence;
import grp16.tripmate.vehicle.persistence.Vehicle.VehiclesQueryGenerator;

public class VehicleFactory implements IVehicleFactory
{
    private static IVehicleFactory instance;

    private VehicleFactory()
    {
        // private constructor for Singleton class
    }

    public static VehicleFactory getInstance() {
        if (instance == null) {
            instance = new VehicleFactory();
        }
        return (VehicleFactory) instance;
    }

    @Override
    public Vehicle getNewVehicle()
    {
        return new Vehicle();
    }
    @Override
    public IVehiclePersistence getVehicleDataBase()
    {
        return new VehiclePersistence();
    }
    public IVehicleQueryGenerator getVehicleQueryBuilder()
    {
        return VehiclesQueryGenerator.getInstance();
    }
}
