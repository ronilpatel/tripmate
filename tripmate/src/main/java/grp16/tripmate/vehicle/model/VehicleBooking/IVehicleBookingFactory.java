package grp16.tripmate.vehicle.model.VehicleBooking;

import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingPersistence;
import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingQueryGenerator;


public interface IVehicleBookingFactory {
    VehicleBooking getNewVehicleBooking();

    IVehicleBookingPersistence getVehicleBookingDatabase();

    IVehicleBookingQueryGenerator getVehicleBookingQueryBuilder();


    VehicleBookingValidator getVehicleBookingValidator();
}
