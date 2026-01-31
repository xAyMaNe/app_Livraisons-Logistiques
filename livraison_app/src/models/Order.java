/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import interfaces.IOrder;

/**
 *
 * @author riomi
 */

public class Order implements IOrder {
    private String id;
    private String customerName;
    private String address;
    private String items;
    private String status;
    private String date;
    
    public Order(String id, String customerName, String address, String items, String status, String date) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.items = items;
        this.status = status;
        this.date = date;
    }
    
    public String getId() { return id; }
    
    public void setId(String id) { this.id = id; }
    
    @Override
    public String getCustomerName() { return customerName; }
    
    @Override
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    @Override
    public String getAddress() { return address; }
    
    @Override
    public void setAddress(String address) { this.address = address; }
    
    @Override
    public String getItems() { return items; }
    
    @Override
    public void setItems(String items) { this.items = items; }
    
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String getDate() { return date; }
    
    @Override
    public void setDate(String date) { this.date = date; }
}