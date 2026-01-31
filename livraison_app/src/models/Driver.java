/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import interfaces.IDriver;

/**
 *
 * @author riomi
 */

public class Driver implements IDriver {
    private String id;
    private String name;
    private String phone;
    private String license;
    private String status;
    
    public Driver(String id, String name, String phone, String license, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.license = license;
        this.status = status;
    }
    
    public String getId() { return id; }
    
    public void setId(String id) { this.id = id; }
    
    @Override
    public String getName() { return name; }
    
    @Override
    public void setName(String name) { this.name = name; }
    
    @Override
    public String getPhone() { return phone; }
    
    @Override
    public void setPhone(String phone) { this.phone = phone; }
    
    @Override
    public String getLicense() { return license; }
    
    @Override
    public void setLicense(String license) { this.license = license; }
    
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
}