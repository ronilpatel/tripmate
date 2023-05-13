package grp16.tripmate.vehicle.persistence.VehicleBooking;

import grp16.tripmate.vehicle.model.VehicleBooking.IVehicleBooking;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBooking;

import java.util.List;

public interface IVehicleBookingPersistence {

    List<VehicleBooking> getVehicleBookingByPostId(int postId);

    List<VehicleBooking> getVehicleBookingByUserId(int userId);

    VehicleBooking getVehicleBookingByBookingId(int bookingId);

    boolean createVehicleBooking(IVehicleBooking vehicleBooking);

    VehicleBooking getLastVehicleBookingByUserId(int userId);

}
