/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import interfaces.IVehicle;


/**
 *
 * @author riomi
 */

public class Vehicle implements IVehicle {
    private String id;
    private String type;
    private String plateNumber;
    private String capacity;
    private String status;
    
    public Vehicle(String id, String type, String plateNumber, String capacity, String status) {
        this.id = id;
        this.type = type;
        this.plateNumber = plateNumber;
        this.capacity = capacity;
        this.status = status;
    }
    
    public String getId() { return id; }
    
    public void setId(String id) { this.id = id; }
    
    @Override
    public String getType() { return type; }
    
    @Override
    public void setType(String type) { this.type = type; }
    
    @Override
    public String getPlateNumber() { return plateNumber; }
    
    @Override
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    
    @Override
    public String getCapacity() { return capacity; }
    
    @Override
    public void setCapacity(String capacity) { this.capacity = capacity; }
    
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
}