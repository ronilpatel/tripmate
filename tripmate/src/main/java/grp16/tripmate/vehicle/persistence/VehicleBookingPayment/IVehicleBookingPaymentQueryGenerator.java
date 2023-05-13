package grp16.tripmate.vehicle.persistence.VehicleBookingPayment;

import grp16.tripmate.vehicle.model.VehicleBookingPayment.VehicleBookingPayment;

public interface IVehicleBookingPaymentQueryGenerator
{
    String getVehicleBookingPaymentByUserId(int userId);

    String createVehicleBookingPayment(VehicleBookingPayment vehicleBookingPayment);
}
