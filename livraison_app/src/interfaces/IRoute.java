/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;
import java.util.ArrayList;


/**
 *
 * @author riomi
 */

public interface IRoute extends IEntity {
    String getDriverName();
    void setDriverName(String driverName);
    String getVehiclePlate();
    void setVehiclePlate(String vehiclePlate);
    ArrayList<String> getOrderIds();
    void setOrderIds(ArrayList<String> orderIds);
    String getDate();
    void setDate(String date);
}