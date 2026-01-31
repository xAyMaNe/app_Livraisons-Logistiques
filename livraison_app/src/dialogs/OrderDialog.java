/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dialogs;
import models.Order;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author riomi
 */


public class OrderDialog extends JDialog {
    private Order order;
    private JTextField idField, customerField, addressField, itemsField;
    
    public OrderDialog(Frame parent, Order o) {
        super(parent, o == null ? "Create Order" : "Edit Order", true);
        setLayout(new GridLayout(5, 2, 10, 10));
        setSize(400, 250);
        setLocationRelativeTo(parent);
        
        add(new JLabel("Order ID:"));
        idField = new JTextField(o != null ? o.getId() : "O" + System.currentTimeMillis());
        idField.setEnabled(o == null);
        add(idField);
        
        add(new JLabel("Customer:"));
        customerField = new JTextField(o != null ? o.getCustomerName() : "");
        add(customerField);
        
        add(new JLabel("Address:"));
        addressField = new JTextField(o != null ? o.getAddress() : "");
        add(addressField);
        
        add(new JLabel("Items:"));
        itemsField = new JTextField(o != null ? o.getItems() : "");
        add(itemsField);
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            if (customerField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter customer name");
                return;
            }
            order = new Order(idField.getText(), customerField.getText(), addressField.getText(),
                itemsField.getText(), "Pending", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            dispose();
        });
        
        cancelBtn.addActionListener(e -> dispose());
        
        add(saveBtn);
        add(cancelBtn);
    }
    
    public Order getOrder() { return order; }
}