package grp16.tripmate.vehicle.model.VehicleBooking;

import grp16.tripmate.persistence.connection.DatabaseConnection;
import grp16.tripmate.persistence.connection.IDatabaseConnection;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.post.model.exception.StartDateAfterEndDateException;
import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingPersistence;
import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingQueryGenerator;
import grp16.tripmate.vehicle.model.Vehicle.VehicleFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class VehicleBooking implements IVehicleBooking {
    private final ILogger logger = new MyLoggerAdapter(this);

    private int id;
    private int vehicleId;
    private int postId;
    private float totalKm;
    private Date bookingStartDate;
    private Date bookingEndDate;
    private boolean hasPaid;
    private final IDatabaseConnection dbConnection;
    private final IVehicleBookingQueryGenerator queryBuilder;

    private static IVehicleBookingFactory vehicleBookingFactory = null;

    private final IVehicleBookingPersistence database;

    public VehicleBooking() {
        vehicleBookingFactory = VehicleBookingFactory.getInstance();
        database = vehicleBookingFactory.getVehicleBookingDatabase();
        queryBuilder = vehicleBookingFactory.getVehicleBookingQueryBuilder();
        dbConnection = new DatabaseConnection();

        this.setBookingStartDate(new Date());
        this.setBookingEndDate(new Date());
    }

    public float getTotalBookingAmount() throws ParseException {
        return VehicleFactory.getInstance().getNewVehicle().getVehicleById(
                this.vehicleId).getVehicleRatePerKmByVehicleId() * this.totalKm;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getBookingStartDate() {
        return getSQLParsableDate(bookingStartDate);
    }

    public void setBookingStartDate(String bookingStartDate) throws ParseException {
        this.bookingStartDate = getJavaDate(bookingStartDate);
    }

    public void setBookingStartDate(Date bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public String getBookingEndDate() {
        return getSQLParsableDate(bookingEndDate);
    }


    public void setBookingEndDate(Date bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

    public float getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(float totalKm) {
        this.totalKm = totalKm;
    }

    public boolean getHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public void setBookingEndDate(String bookingEndDate) throws ParseException {
        this.bookingEndDate = getJavaDate(bookingEndDate);
    }

    @Override
    public List<VehicleBooking> getVehicleBookingByUserId(int userId) {
        return null;
    }

    private String getSQLParsableDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    protected Date getJavaDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    @Override
    public List<VehicleBooking> getVehicleBookingByPostId(int postId) {
        return null;
    }

    @Override
    public VehicleBooking getVehicleBookingByBookingId(int bookingId) {
        return null;
    }

    @Override
    public boolean createVehicleBooking(IVehicleBookingPersistence vehicleBookingDatabaseObj)
    {
        return vehicleBookingDatabaseObj.createVehicleBooking(this);
    }

    public VehicleBooking getLastVehicleBookingByUserId(int userId)
    {
        return database.getLastVehicleBookingByUserId(userId);
    }

    public void validateBooking(VehicleBookingValidator validator) throws ParseException, StartDateAfterEndDateException {
        validator.isStartDateBeforeEndDate(this);
    }
}
