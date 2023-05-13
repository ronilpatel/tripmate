package grp16.tripmate.vehicle.persistence.VehicleBooking;

import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBooking;

public interface IVehicleBookingQueryGenerator
{
    String getVehicleBookingByPostId(int postId);

    String getVehicleBookingByUserId(int userId);

    String getVehicleBookingByBookingId(int bookingId);
    String createVehicleBooking(VehicleBooking vehicleBooking);
    String getLastVehicleBookingByUserId(int userId);
}
