package grp16.tripmate.vehicle.model.Vehicle;

import grp16.tripmate.vehicle.persistence.Vehicle.IVehiclePersistence;
import grp16.tripmate.vehicle.persistence.Vehicle.IVehicleQueryGenerator;

public interface IVehicleFactory
{
    Vehicle getNewVehicle();
    IVehiclePersistence getVehicleDataBase();
    IVehicleQueryGenerator getVehicleQueryBuilder();

}
