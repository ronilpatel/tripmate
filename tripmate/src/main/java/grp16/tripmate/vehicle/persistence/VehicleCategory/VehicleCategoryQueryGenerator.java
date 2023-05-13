package grp16.tripmate.vehicle.persistence.VehicleCategory;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.vehicle.persistence.Vehicle.VehicleDbColumnNames;

public class VehicleCategoryQueryGenerator implements IVehicleCategoryQueryGenerator
{
    private final ILogger logger = new MyLoggerAdapter(this);

    private static VehicleCategoryQueryGenerator instance;

    private VehicleCategoryQueryGenerator()
    {

    }

    public static VehicleCategoryQueryGenerator getInstance()
    {
        if (instance == null)
        {
            instance = new VehicleCategoryQueryGenerator();
        }
        return instance;
    }
    @Override
    public String getVehicleCategoryByVehicleId(int vehicleId) {
        String query = "SELECT " +
                VehicleCategoryDbColumns.ID + ", " +
                VehicleCategoryDbColumns.NAME + " FROM " +
                VehicleDbColumnNames.TABLENAME + " INNER JOIN " +
                VehicleCategoryDbColumns.TABLENAME + " on " +
                VehicleDbColumnNames.TABLENAME + "." +
                VehicleDbColumnNames.ID + "=" +
                VehicleCategoryDbColumns.TABLENAME + "." +
                VehicleCategoryDbColumns.ID + " WHERE " +
                VehicleDbColumnNames.TABLENAME + "." +
                VehicleDbColumnNames.ID + "=" + vehicleId + ";";
        return query;
    }

    @Override
    public String getVehicleCategoryById(int categoryId) {
        String query = "SELECT " +
                VehicleCategoryDbColumns.ID + ", " +
                VehicleCategoryDbColumns.NAME + " FROM " +
                VehicleCategoryDbColumns.TABLENAME + " WHERE " +
                VehicleCategoryDbColumns.ID + "=" + categoryId + ";";
        return query;
    }
}
