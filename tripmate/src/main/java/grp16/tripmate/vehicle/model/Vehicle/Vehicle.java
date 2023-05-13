package grp16.tripmate.vehicle.model.Vehicle;

import grp16.tripmate.persistence.connection.DatabaseConnection;
import grp16.tripmate.persistence.connection.IDatabaseConnection;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.vehicle.persistence.Vehicle.IVehiclePersistence;
import grp16.tripmate.vehicle.persistence.Vehicle.IVehicleQueryGenerator;
import grp16.tripmate.vehicle.model.VehicleCategory.VehicleCategory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Vehicle implements IVehicle
{
    private final ILogger logger = new MyLoggerAdapter(this);

    private int id;
    private String name;
    private int numberOfSeats;
    private String registrationNumber;
    private boolean isAvailable;
    private boolean isForLongJourney;
    private float ratePerKm;
    private String description;
    private int categoryId;

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    private final IDatabaseConnection dbConnection;
    private final IVehicleQueryGenerator queryBuilder;
    private static IVehicleFactory vehicleFactory = null;
    private final IVehiclePersistence database;

    public Vehicle()
    {
        vehicleFactory = VehicleFactory.getInstance();
        database = vehicleFactory.getVehicleDataBase();
        queryBuilder = vehicleFactory.getVehicleQueryBuilder();
        dbConnection = new DatabaseConnection();
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    public float getRatePerKm() {
        return ratePerKm;
    }

    public void setRatePerKm(float ratePerKm) {
        this.ratePerKm = ratePerKm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private VehicleCategory vehicleCategory;

    public String getVehicleCategory() {
        return vehicleCategory.getName();
    }

    public void setVehicleCategory(VehicleCategory vehicleCategory)
    {
        this.vehicleCategory = vehicleCategory;
    }

    public void setVehicleCategoryId(int categoryId)
    {
        this.vehicleCategory = new VehicleCategory(categoryId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public boolean getIsForLongJourney() {
        return isForLongJourney;
    }

    public void setIsForLongJourney(boolean forLongJourney) {
        isForLongJourney = forLongJourney;
    }
    public List<Vehicle> getAllVehicles() throws ParseException {
        return database.getAllVehicles();
    }

    public Vehicle getVehicleById(int vehicleId) throws ParseException {
        return database.getVehicleById(vehicleId);
    }

    public List<Vehicle> getTripVehicles(int postId)
    {
        return new ArrayList<>();
    }

    public List<Vehicle> getRecommendedTripVehicles(int postId)
    {
        return new ArrayList<>();
    }

    public List<Vehicle> getVehiclesByUserId(int userId)
    {
        return new ArrayList<>();
    }

    public float getVehicleRatePerKmByVehicleId() throws ParseException {
        Vehicle vehicleObj = database.getVehicleById(this.id);
        return vehicleObj.getRatePerKm();
    }
}
