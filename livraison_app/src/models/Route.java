/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import interfaces.IRoute;
import java.util.ArrayList;
/**
 *
 * @author riomi
 */


public class Route implements IRoute {
    private String id;
    private String driverName;
    private String vehiclePlate;
    private String status;
    private String date;
    private ArrayList<String> orderIds;
    
    public Route(String id, String driverName, String vehiclePlate, ArrayList<String> orderIds, String status, String date) {
        this.id = id;
        this.driverName = driverName;
        this.vehiclePlate = vehiclePlate;
        this.orderIds = orderIds;
        this.status = status;
        this.date = date;
    }
    
    @Override
    public String getId() { return id; }
    
    @Override
    public void setId(String id) { this.id = id; }
    
    @Override
    public String getDriverName() { return driverName; }
    
    @Override
    public void setDriverName(String driverName) { this.driverName = driverName; }
    
    @Override
    public String getVehiclePlate() { return vehiclePlate; }
    
    @Override
    public void setVehiclePlate(String vehiclePlate) { this.vehiclePlate = vehiclePlate; }
    
    @Override
    public ArrayList<String> getOrderIds() { return orderIds; }
    
    @Override
    public void setOrderIds(ArrayList<String> orderIds) { this.orderIds = orderIds; }
    
    @Override
    public String getStatus() { return status; }
    
    @Override
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String getDate() { return date; }
    
    @Override
    public void setDate(String date) { this.date = date; }
}