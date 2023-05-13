package grp16.tripmate.vehicle.model;

import grp16.tripmate.vehicle.persistence.Vehicle.IVehiclePersistence;
import grp16.tripmate.vehicle.persistence.VehiclePersistenceMock;
import grp16.tripmate.vehicle.model.Vehicle.IVehicleFactory;
import grp16.tripmate.vehicle.model.Vehicle.Vehicle;
import grp16.tripmate.vehicle.model.Vehicle.VehicleFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestVehicle {

    IVehicleFactory factory;

    IVehiclePersistence database;

    Vehicle vehicle;

    public TestVehicle() throws ParseException {
        factory = VehicleFactory.getInstance();
        database = new VehiclePersistenceMock();
    }

    public float getVehicleRatePerKmByVehicleId() throws ParseException {
        vehicle = database.getVehicleById(this.vehicle.getId());
        return vehicle.getRatePerKm();
    }

    @Test
    @Order(1)
    void testGetAllVehiclesPositive() throws ParseException {
        Object object = database.getAllVehicles();
        List<Vehicle> vehicleListObj = new ArrayList<>();
        assertEquals(vehicleListObj.getClass(), object.getClass());
        assertEquals(((List<Vehicle>) object).size(), 1);
    }

    @Test
    @Order(2)
    void testGetAllVehiclesNegative() throws ParseException {
        Object object = database.getAllVehicles();
        assertNotEquals(String.class, object.getClass());
        assertNotEquals(((List<Vehicle>) object).size(), 10);
    }

    @Test
    @Order(3)
    void testGetVehicleByIdPositive() throws ParseException {
        Object object = database.getVehicleById(1);
        vehicle = factory.getNewVehicle();
        assertEquals(vehicle.getClass(), object.getClass());
    }

    @Test
    @Order(4)
    void testGetVehicleByIdNegative() throws ParseException {
        Object object = database.getVehicleById(5);
        assertEquals(null, object);
    }

    @Test
    @Order(5)
    void testGetVehicleRatePerKmByVehicleIdPositive() throws ParseException {
        vehicle = factory.getNewVehicle();
        vehicle.setId(1);
        Object object = getVehicleRatePerKmByVehicleId();
        assertEquals(object, 7.0F);
        assertEquals(Float.class, object.getClass());
    }

    @Test
    @Order(6)
    void testGetVehicleRatePerKmByVehicleIdNegative() throws ParseException {
        vehicle = factory.getNewVehicle();
        vehicle.setId(1);
        Object object = getVehicleRatePerKmByVehicleId();
        assertNotEquals(object, 9);
        assertNotEquals(Integer.class, object.getClass());
    }
}
