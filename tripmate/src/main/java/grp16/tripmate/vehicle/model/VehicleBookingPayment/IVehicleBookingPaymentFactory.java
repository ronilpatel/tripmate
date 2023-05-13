package grp16.tripmate.vehicle.model.VehicleBookingPayment;

import grp16.tripmate.vehicle.persistence.VehicleBookingPayment.IVehicleBookingPaymentPersistence;
import grp16.tripmate.vehicle.persistence.VehicleBookingPayment.IVehicleBookingPaymentQueryGenerator;

public interface IVehicleBookingPaymentFactory
{
    VehicleBookingPayment getNewVehicleBookingPayment();

    IVehicleBookingPaymentPersistence getVehicleBookingPaymentDatabase();

    IVehicleBookingPaymentQueryGenerator getVehicleBookingPaymentQueryBuilder();
}
