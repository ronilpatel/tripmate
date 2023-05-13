package grp16.tripmate.vehicle.persistence.VehicleBooking;

import grp16.tripmate.persistence.connection.DatabaseConnection;
import grp16.tripmate.persistence.connection.IDatabaseConnection;
import grp16.tripmate.persistence.execute.DatabaseExecutor;
import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBooking;
import grp16.tripmate.vehicle.model.VehicleBooking.IVehicleBooking;
import grp16.tripmate.vehicle.model.VehicleBooking.IVehicleBookingFactory;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBookingFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VehicleBookingPersistence implements IVehicleBookingPersistence {
    private final ILogger logger = new MyLoggerAdapter(this);

    IVehicleBookingQueryGenerator queryBuilder;

    private final IDatabaseConnection dbConnection;

    private final IVehicleBookingFactory factory;

    private final IDatabaseExecutor databaseExecutor;

    public VehicleBookingPersistence() {
        queryBuilder = VehicleBookingQueryGenerator.getInstance();
        dbConnection = new DatabaseConnection();
        factory = VehicleBookingFactory.getInstance();
        databaseExecutor = new DatabaseExecutor();
    }


    @Override
    public List<VehicleBooking> getVehicleBookingByPostId(int postId) {
        String query = queryBuilder.getVehicleBookingByPostId(postId);
        return listToVehicles(databaseExecutor.executeSelectQuery(query));
    }

    private List<VehicleBooking> listToVehicles(List<Map<String, Object>> results) {
        List<VehicleBooking> bookings = new ArrayList<>();
        for (Map<String, Object> result : results) {
            VehicleBooking vehicleObj = factory.getNewVehicleBooking();
            vehicleObj.setId((Integer) result.get(VehicleBookingDbColumnNames.ID));
            vehicleObj.setVehicleId((Integer) result.get(VehicleBookingDbColumnNames.VEHICLE_ID));
            vehicleObj.setPostId((Integer) result.get(VehicleBookingDbColumnNames.POST_ID));
            vehicleObj.setBookingStartDate((Date) result.get(VehicleBookingDbColumnNames.BOOKING_START_DATE));
            vehicleObj.setBookingEndDate((Date) result.get(VehicleBookingDbColumnNames.BOOKING_END_DATE));
            vehicleObj.setTotalKm((Float) result.get(VehicleBookingDbColumnNames.TOTAL_KM));
            vehicleObj.setHasPaid((Integer) result.get(VehicleBookingDbColumnNames.HAS_PAID) != 0);
            bookings.add(vehicleObj);
        }
        return bookings;
    }

    @Override
    public List<VehicleBooking> getVehicleBookingByUserId(int userId) {
        String query = queryBuilder.getVehicleBookingByUserId(userId);
        return listToVehicles(databaseExecutor.executeSelectQuery(query));
    }

    @Override
    public VehicleBooking getVehicleBookingByBookingId(int bookingId) {
        return null;
    }

    @Override
    public boolean createVehicleBooking(IVehicleBooking vehicleBooking) {
        String query = queryBuilder.createVehicleBooking((VehicleBooking) vehicleBooking);
        return databaseExecutor.executeInsertQuery(query);
    }

    @Override
    public VehicleBooking getLastVehicleBookingByUserId(int userId) {
        String query = queryBuilder.getLastVehicleBookingByUserId(userId);
        List<VehicleBooking> vehicleBooking = listToVehicles(databaseExecutor.executeSelectQuery(query));
        if (vehicleBooking.size() > 0)
        {
            return vehicleBooking.get(0);
        }
        else {
            return null;
        }
    }
}