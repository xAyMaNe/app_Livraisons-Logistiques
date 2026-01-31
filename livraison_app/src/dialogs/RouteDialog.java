/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dialogs;
import models.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author riomi
 */


public class RouteDialog extends JDialog {
    private Route route;
    private JTextField idField;
    private JComboBox<String> driverBox, vehicleBox;
    private JList<String> ordersList;
    
    public RouteDialog(Frame parent, Route r, ArrayList<Driver> drivers, 
                      ArrayList<Vehicle> vehicles, ArrayList<Order> orders) {
        super(parent, r == null ? "Create Route" : "Edit Route", true);
        setLayout(new BorderLayout(10, 10));
        setSize(450, 400);
        setLocationRelativeTo(parent);
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("Route ID:"));
        idField = new JTextField(r != null ? r.getId() : "R" + System.currentTimeMillis());
        idField.setEnabled(r == null);
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Driver:"));
        String[] driverNames = drivers.stream().map(d -> d.getName()).toArray(String[]::new);
        driverBox = new JComboBox<>(driverNames);
        formPanel.add(driverBox);
        
        formPanel.add(new JLabel("Vehicle:"));
        String[] vehiclePlates = vehicles.stream().map(v -> v.getPlateNumber()).toArray(String[]::new);
        vehicleBox = new JComboBox<>(vehiclePlates);
        formPanel.add(vehicleBox);
        
        add(formPanel, BorderLayout.NORTH);
        
        JPanel ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.setBorder(BorderFactory.createTitledBorder("Select Orders"));
        String[] orderIds = orders.stream()
            .filter(o -> o.getStatus().equals("Pending"))
            .map(o -> o.getId() + " - " + o.getCustomerName())
            .toArray(String[]::new);
        ordersList = new JList<>(orderIds);
        ordersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        ordersPanel.add(new JScrollPane(ordersList), BorderLayout.CENTER);
        add(ordersPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            if (ordersList.getSelectedValuesList().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select at least one order");
                return;
            }
            ArrayList<String> selectedOrders = new ArrayList<>();
            for (String s : ordersList.getSelectedValuesList()) {
                selectedOrders.add(s.split(" - ")[0]);
            }
            route = new Route(idField.getText(), (String) driverBox.getSelectedItem(),
                (String) vehicleBox.getSelectedItem(), selectedOrders, "Active",
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            dispose();
        });
        
        cancelBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public Route getRoute() { return route; }
}