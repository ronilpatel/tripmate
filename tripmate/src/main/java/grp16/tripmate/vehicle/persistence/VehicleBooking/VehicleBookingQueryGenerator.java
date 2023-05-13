package grp16.tripmate.vehicle.persistence.VehicleBooking;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.post.persistance.PostDbColumnNames;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBooking;

public class VehicleBookingQueryGenerator implements IVehicleBookingQueryGenerator {
    private final ILogger logger = new MyLoggerAdapter(this);

    private static VehicleBookingQueryGenerator instance;

    private VehicleBookingQueryGenerator() {

    }

    public static VehicleBookingQueryGenerator getInstance() {
        if (instance == null) {
            instance = new VehicleBookingQueryGenerator();
        }
        return instance;
    }

    @Override
    public String getVehicleBookingByPostId(int postId) {
        String query = "select * from " +
                VehicleBookingDbColumnNames.TABLE_NAME +
                " where " + VehicleBookingDbColumnNames.POST_ID + "  = " + postId + ";\n";
        logger.info(query);
        return query;
    }

    @Override
    public String getVehicleBookingByUserId(int userId) {
        String query = "select * from " +
                VehicleBookingDbColumnNames.TABLE_NAME + " where " +
                VehicleBookingDbColumnNames.POST_ID + " in ( select " + PostDbColumnNames.ID + " from " + PostDbColumnNames.TABLE_NAME
                + " where " + PostDbColumnNames.OWNER + " = " + userId + ");\n";

        return query;
    }

    @Override
    public String getVehicleBookingByBookingId(int bookingId) {
        return null;
    }

    @Override
    public String getLastVehicleBookingByUserId(int userId)
    {
        String query = "SELECT * FROM " +
                VehicleBookingDbColumnNames.TABLE_NAME +
                " ORDER BY " + VehicleBookingDbColumnNames.ID+ " DESC;";
        logger.info("query to get the last booking object : " + query);
        return query;
    }
    @Override
    public String createVehicleBooking(VehicleBooking vehicleBooking) {
        String query = "INSERT INTO " + VehicleBookingDbColumnNames.TABLE_NAME +
                " (" +
                VehicleBookingDbColumnNames.POST_ID + ", " +
                VehicleBookingDbColumnNames.VEHICLE_ID + ", " +
                VehicleBookingDbColumnNames.TOTAL_KM + ", " +
                VehicleBookingDbColumnNames.BOOKING_START_DATE + ", " +
                VehicleBookingDbColumnNames.BOOKING_END_DATE + ", " +
                VehicleBookingDbColumnNames.HAS_PAID + ") " +
                "VALUES " +
                "(" +
                vehicleBooking.getPostId() + ", " +
                vehicleBooking.getVehicleId() + ", " +
                vehicleBooking.getTotalKm() + ", " +
                "'" + vehicleBooking.getBookingStartDate() + "', " +
                "'" + vehicleBooking.getBookingEndDate() + "', " +
                "1" + ");";
        logger.info(query);
        return query;
    }
}
