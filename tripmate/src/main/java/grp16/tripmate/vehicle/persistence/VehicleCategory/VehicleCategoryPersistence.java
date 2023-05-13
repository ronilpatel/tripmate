package grp16.tripmate.vehicle.persistence.VehicleCategory;

import grp16.tripmate.persistence.execute.DatabaseExecutor;
import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.vehicle.model.VehicleCategory.IVehicleCategoryFactory;
import grp16.tripmate.vehicle.model.VehicleCategory.VehicleCategory;
import grp16.tripmate.vehicle.model.VehicleCategory.VehicleCategoryFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VehicleCategoryPersistence implements IVehicleCategoryPersistence {
    private final ILogger logger = new MyLoggerAdapter(this);

    IVehicleCategoryQueryGenerator queryGenerator;

    private final IVehicleCategoryFactory factory;

    private final IDatabaseExecutor databaseExecutor;

    public VehicleCategoryPersistence()
    {
        queryGenerator = VehicleCategoryQueryGenerator.getInstance();
        factory = VehicleCategoryFactory.getInstance();
        databaseExecutor = new DatabaseExecutor();
    }

    @Override
    public VehicleCategory getVehicleCategoryByVehicleId(int vehicleId) throws ParseException {
        String query = queryGenerator.getVehicleCategoryByVehicleId(vehicleId);
        List<VehicleCategory> vehicleCategory = querySetToVehicleCategory(databaseExecutor.executeSelectQuery(query));
        if (vehicleCategory.size() > 0)
        {
            return vehicleCategory.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public VehicleCategory getVehicleCategoryById(int categoryId) throws ParseException {
        String query = queryGenerator.getVehicleCategoryById(categoryId);
        List<VehicleCategory> vehicleCategory = querySetToVehicleCategory(databaseExecutor.executeSelectQuery(query));
        if (vehicleCategory.size() > 0)
        {
            return vehicleCategory.get(0);
        }
        else {
            return null;
        }
    }

    private List<VehicleCategory> querySetToVehicleCategory(List<Map<String, Object>> resultSet) throws ParseException {
        List<VehicleCategory> vehicleCategories = new ArrayList<>();
        for (Map<String, Object> rs : resultSet) {
            VehicleCategory vehicleCategoryObj = factory.getNewVehicleCategory();
            vehicleCategoryObj.setId((Integer) rs.get(VehicleCategoryDbColumns.ID));
            vehicleCategoryObj.setCategoryName((String) rs.get(VehicleCategoryDbColumns.NAME));
            vehicleCategories.add(vehicleCategoryObj);
        }
        logger.info(vehicleCategories.toString());
        return vehicleCategories;
    }
}
