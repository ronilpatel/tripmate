package grp16.tripmate.vehicle.model.VehicleBookingPayment;

import grp16.tripmate.vehicle.persistence.VehicleBookingPayment.IVehicleBookingPaymentPersistence;
import grp16.tripmate.vehicle.persistence.VehicleBookingPayment.IVehicleBookingPaymentQueryGenerator;
import grp16.tripmate.vehicle.persistence.VehicleBookingPayment.VehicleBookingPaymentPersistence;
import grp16.tripmate.vehicle.persistence.VehicleBookingPayment.VehicleBookingPaymentQueryGenerator;

public class VehicleBookingPaymentFactory implements IVehicleBookingPaymentFactory
{
    private static IVehicleBookingPaymentFactory instance;

    private VehicleBookingPaymentFactory()
    {

    }

    public static VehicleBookingPaymentFactory getInstance() {
        if (instance == null) {
            instance = new VehicleBookingPaymentFactory();
        }
        return (VehicleBookingPaymentFactory) instance;
    }

    @Override
    public VehicleBookingPayment getNewVehicleBookingPayment() {
        return new VehicleBookingPayment();
    }

    @Override
    public IVehicleBookingPaymentPersistence getVehicleBookingPaymentDatabase() {
        return new VehicleBookingPaymentPersistence();
    }

    @Override
    public IVehicleBookingPaymentQueryGenerator getVehicleBookingPaymentQueryBuilder() {
        return VehicleBookingPaymentQueryGenerator.getInstance();
    }
}
