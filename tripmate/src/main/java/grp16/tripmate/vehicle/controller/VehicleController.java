package grp16.tripmate.vehicle.controller;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.post.persistance.IPostPersistence;
import grp16.tripmate.post.model.Post;
import grp16.tripmate.post.model.exception.StartDateAfterEndDateException;
import grp16.tripmate.post.model.factory.IPostFactory;
import grp16.tripmate.post.model.factory.PostFactory;
import grp16.tripmate.session.SessionManager;
import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingPersistence;
import grp16.tripmate.vehicle.persistence.VehicleBookingPayment.IVehicleBookingPaymentPersistence;
import grp16.tripmate.vehicle.model.Vehicle.Vehicle;
import grp16.tripmate.vehicle.model.Vehicle.VehicleFactory;
import grp16.tripmate.vehicle.model.Vehicle.IVehicle;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBooking;
import grp16.tripmate.vehicle.model.VehicleBooking.IVehicleBookingFactory;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBookingValidator;
import grp16.tripmate.vehicle.model.VehicleBookingPayment.IVehicleBookingPaymentFactory;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBookingFactory;
import grp16.tripmate.vehicle.model.VehicleBookingPayment.VehicleBookingPayment;
import grp16.tripmate.vehicle.model.VehicleBookingPayment.VehicleBookingPaymentFactory;
import grp16.tripmate.vehicle.model.VehicleCategory.IVehicleCategory;
import grp16.tripmate.vehicle.model.VehicleCategory.IVehicleCategoryFactory;
import grp16.tripmate.vehicle.model.VehicleCategory.VehicleCategoryFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class VehicleController{
    private final ILogger logger = new MyLoggerAdapter(this);

    private final IPostFactory postFactory;
    private final IVehicleBookingFactory vehicleBookingFactory;
    private final IVehicleBookingPaymentFactory vehicleBookingPaymentFactory;
    private final IVehicleCategoryFactory vehicleCategoryFactory;
    private final IVehicle vehicle;
    private final IVehicleCategory vehicleCategory;
    private final IPostPersistence postDatabase;
    private final IVehicleBookingPersistence vehicleBookingDatabase;
    private final IVehicleBookingPaymentPersistence vehicleBookingPaymentDatabase;
    private final VehicleBookingValidator validator;

    public VehicleController() {
        postFactory = PostFactory.getInstance();
        vehicle = VehicleFactory.getInstance().getNewVehicle();
        vehicleCategory = VehicleCategoryFactory.getInstance().getNewVehicleCategory();
        vehicleBookingFactory = VehicleBookingFactory.getInstance();
        vehicleBookingPaymentFactory = VehicleBookingPaymentFactory.getInstance();
        validator = vehicleBookingFactory.getVehicleBookingValidator();
        vehicleBookingPaymentDatabase = vehicleBookingPaymentFactory.getVehicleBookingPaymentDatabase();
        postDatabase = postFactory.makePostDatabase();
        vehicleBookingDatabase = vehicleBookingFactory.getVehicleBookingDatabase();
        vehicleCategoryFactory = VehicleCategoryFactory.getInstance();
    }

    @GetMapping("/all-vehicles")
    public String getAllVehicles(Model model) throws ParseException {
        model.addAttribute("title", "Vehicles");
        List<Vehicle> vehicles = vehicle.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "listVehicles";
    }

    @GetMapping("/vehicle/{id}")
    public String getVehicleDetails(Model model, @PathVariable("id") int vehicleId) throws Exception {
        model.addAttribute("title", "Vehicle Details");

        Vehicle vehicle = this.vehicle.getVehicleById(vehicleId);
        model.addAttribute("vehicle", vehicle);

        int userId = SessionManager.getInstance().getLoggedInUserId();

        Post post = (Post) postFactory.makeNewPost();
        List<Post> userPosts = post.getPostsByUserId(postDatabase, userId);
        model.addAttribute("userposts", userPosts);

        VehicleBooking vehicleBooking = vehicleBookingFactory.getNewVehicleBooking();
        model.addAttribute("vehicleBookingObj", vehicleBooking);

        VehicleBookingPayment vehicleBookingPayment = vehicleBookingPaymentFactory.getNewVehicleBookingPayment();
        model.addAttribute("vehicleBookingPayment", vehicleBookingPayment);

        return "vehicleDetails";
    }

    @PostMapping("/confirm-booking/{id}")
    public String confirmVehicleBooking(Model model,
                                        @ModelAttribute Vehicle vehicle,
                                        @ModelAttribute VehicleBooking vehicleBooking,
                                        @ModelAttribute VehicleBookingPayment vehicleBookingPayment,
                                        @ModelAttribute ArrayList<Post> userPosts
    ) throws ParseException {
        logger.info(model.toString());
        vehicleBooking.setVehicleId(vehicle.getId());

        try {
            vehicleBooking.validateBooking(validator);
            vehicleBooking.createVehicleBooking(vehicleBookingDatabase);
            VehicleBooking vehicleBookingObj = vehicleBookingFactory.getNewVehicleBooking().getLastVehicleBookingByUserId(SessionManager.getInstance().getLoggedInUserId());
            vehicleBookingPayment.setVehicleBookingId(vehicleBookingObj.getId());
            vehicleBookingPayment.setCreatedOn(new Date());
            vehicleBookingPayment.createVehicleBookingPayment(vehicleBookingPaymentDatabase);
        } catch (StartDateAfterEndDateException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            List<Vehicle> vehicles = vehicle.getAllVehicles();
            model.addAttribute("vehicles", vehicles);
            return "listVehicles";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/my-vehicle-bookings";
    }

    @GetMapping("/my-vehicle-bookings")
    public String getAllVehicleBookingsByUserId(Model model) {
        model.addAttribute("title", "My Vehicle Bookings");
        try {
            List<VehicleBooking> vehicleBookings = vehicleBookingDatabase.getVehicleBookingByUserId(SessionManager.getInstance().getLoggedInUserId());
            logger.info(vehicleBookings.toString());
            model.addAttribute("vehicleBookings", vehicleBookings);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }
        return "myVehicleBookings";
    }

    @GetMapping("/my-vehicle-booking-transaction")
    public String getAllVehicleBookingPaymentTransactionByUserId(Model model) {
        model.addAttribute("title", "Vehicle Booking Transactions");
        try {
            List<VehicleBookingPayment> paymentTransactions = vehicleBookingPaymentDatabase.getVehicleBookingPaymentByUserId(
                    SessionManager.getInstance().getLoggedInUserId());
            model.addAttribute("vehicleBookingTransactions", paymentTransactions);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }
        return "myTransactions";
    }
}