package grp16.tripmate.vehicle.model.VehicleBooking;

import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingPersistence;
import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingQueryGenerator;
import grp16.tripmate.vehicle.persistence.VehicleBooking.VehicleBookingPersistence;

public class VehicleBookingFactory implements IVehicleBookingFactory {
    private static IVehicleBookingFactory instance;

    private VehicleBookingFactory() {

    }

    public static VehicleBookingFactory getInstance() {
        if (instance == null) {
            instance = new VehicleBookingFactory();
        }
        return (VehicleBookingFactory) instance;
    }

    @Override
    public VehicleBooking getNewVehicleBooking() {
        return new VehicleBooking();
    }

    @Override
    public IVehicleBookingPersistence getVehicleBookingDatabase() {
        return new VehicleBookingPersistence();
    }

    @Override
    public IVehicleBookingQueryGenerator getVehicleBookingQueryBuilder() {
        return null;
    }

    @Override
    public VehicleBookingValidator getVehicleBookingValidator(){
        return new VehicleBookingValidator();
    }
}
