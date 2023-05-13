package grp16.tripmate.vehicle.model.VehicleBooking;

import grp16.tripmate.post.model.exception.StartDateAfterEndDateException;

import java.text.ParseException;
import java.util.Date;

public class VehicleBookingValidator {
    public void isStartDateBeforeEndDate(IVehicleBooking vehicleBooking) throws ParseException, StartDateAfterEndDateException {
        VehicleBooking booking = (VehicleBooking) vehicleBooking;
        Date startDate = booking.getJavaDate(booking.getBookingStartDate());
        Date endDate = booking.getJavaDate(booking.getBookingEndDate());
        if(startDate.after(endDate)){
            throw new StartDateAfterEndDateException();
        }
    }
}
