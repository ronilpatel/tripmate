package grp16.tripmate.vehicle.model.VehicleBookingPayment;

import grp16.tripmate.vehicle.persistence.VehicleBookingPayment.IVehicleBookingPaymentPersistence;

import java.text.ParseException;
import java.util.List;

public interface IVehicleBookingPayment {

    List<VehicleBookingPayment> getVehicleBookingPaymentByUserId(int userId) throws ParseException;

    boolean createVehicleBookingPayment(IVehicleBookingPaymentPersistence vehicleBookingPayment);
}
