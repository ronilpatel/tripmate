package grp16.tripmate.vehicle.model.VehicleBookingPayment;

import grp16.tripmate.persistence.connection.DatabaseConnection;
import grp16.tripmate.persistence.connection.IDatabaseConnection;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.vehicle.persistence.VehicleBookingPayment.IVehicleBookingPaymentPersistence;
import grp16.tripmate.vehicle.persistence.VehicleBookingPayment.IVehicleBookingPaymentQueryGenerator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VehicleBookingPayment implements IVehicleBookingPayment
{

    private final ILogger logger = new MyLoggerAdapter(this);
    private int paymentId;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCreatedOn() {
        return getSQLParsableDate(this.createdOn);
    }

    private String getSQLParsableDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    protected Date getJavaDate(String date) throws ParseException
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSetter = dateFormatter.parse(date);
        java.sql.Date finalDate = new java.sql.Date(dateSetter.getTime());
        return finalDate;
    }
    public void setCreatedOn(Date todaysDate) throws ParseException {
        String todaysDateOnly = new SimpleDateFormat("yyyy-MM-dd").format(todaysDate);
        this.createdOn = getJavaDate(todaysDateOnly);
    }

    private float amount;

    private Date createdOn;

    private int vehicleBookingId;

    public int getVehicleBookingId() {
        return vehicleBookingId;
    }

    public void setVehicleBookingId(int vehicleBookingId) {
        this.vehicleBookingId = vehicleBookingId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    private final IDatabaseConnection dbConnection;
    private final IVehicleBookingPaymentQueryGenerator queryBuilder;

    private static IVehicleBookingPaymentFactory vehicleBookingPaymentFactory = null;

    private final IVehicleBookingPaymentPersistence database;

    public VehicleBookingPayment()
    {
        vehicleBookingPaymentFactory = VehicleBookingPaymentFactory.getInstance();
        database = vehicleBookingPaymentFactory.getVehicleBookingPaymentDatabase();
        queryBuilder = vehicleBookingPaymentFactory.getVehicleBookingPaymentQueryBuilder();
        dbConnection = new DatabaseConnection();
    }
    @Override
    public List<VehicleBookingPayment> getVehicleBookingPaymentByUserId(int userId) throws ParseException {
        return database.getVehicleBookingPaymentByUserId(userId);
    }

    @Override
    public boolean createVehicleBookingPayment(IVehicleBookingPaymentPersistence vehicleBookingPayment)
    {
        return vehicleBookingPayment.createVehicleBookingPayment(this);
    }
}
