package grp16.tripmate.vehicle.model.VehicleCategory;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;

import java.util.ArrayList;
import java.util.List;

public class VehicleCategory implements IVehicleCategory
{
    private final ILogger logger = new MyLoggerAdapter(this);

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCategoryName(String categoryName){
        this.name = categoryName;
    }

    public VehicleCategory()
    {

    }
    public VehicleCategory(int categoryId)
    {
        this.id = categoryId;
    }
    public List<VehicleCategory> getAllVehicleCategory()
    {
        return new ArrayList<>();
    }

    public VehicleCategory getVehicleCategoryByVehicleId(int vehicleId)
    {
        return new VehicleCategory();
    }
    public String getCategoryNameByCategoryId(int categoryId)
    {
        return "sample Category";
    }
}
