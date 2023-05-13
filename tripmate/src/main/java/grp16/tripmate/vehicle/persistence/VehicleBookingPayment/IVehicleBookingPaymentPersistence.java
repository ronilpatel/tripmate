package grp16.tripmate.vehicle.persistence.VehicleBookingPayment;

import grp16.tripmate.vehicle.model.VehicleBookingPayment.IVehicleBookingPayment;
import grp16.tripmate.vehicle.model.VehicleBookingPayment.VehicleBookingPayment;

import java.text.ParseException;
import java.util.List;

public interface IVehicleBookingPaymentPersistence
{
    List<VehicleBookingPayment> getVehicleBookingPaymentByUserId(int userId) throws ParseException;

    boolean createVehicleBookingPayment(IVehicleBookingPayment vehicleBookingPayment);

}
