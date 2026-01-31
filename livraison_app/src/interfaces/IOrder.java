/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 *
 * @author riomi
 */
public interface IOrder {
    String getCustomerName();
    void setCustomerName(String customerName);
    String getAddress();
    void setAddress(String address);
    String getItems();
    void setItems(String items);
    String getDate();
    void setDate(String date);
}
