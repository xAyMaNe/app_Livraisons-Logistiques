/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dialogs;
import models.Vehicle;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author riomi
 */


public class VehicleDialog extends JDialog {
    private Vehicle vehicle;
    private JTextField idField, typeField, plateField, capacityField;
    private JComboBox<String> statusBox;
    
    public VehicleDialog(Frame parent, Vehicle v) {
        super(parent, v == null ? "Add Vehicle" : "Edit Vehicle", true);
        setLayout(new GridLayout(6, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        add(new JLabel("ID:"));
        idField = new JTextField(v != null ? v.getId() : "V" + System.currentTimeMillis());
        idField.setEnabled(v == null);
        add(idField);
        
        add(new JLabel("Type:"));
        typeField = new JTextField(v != null ? v.getType() : "");
        add(typeField);
        
        add(new JLabel("Plate Number:"));
        plateField = new JTextField(v != null ? v.getPlateNumber() : "");
        add(plateField);
        
        add(new JLabel("Capacity:"));
        capacityField = new JTextField(v != null ? v.getCapacity() : "");
        add(capacityField);
        
        add(new JLabel("Status:"));
        statusBox = new JComboBox<>(new String[]{"Available", "In Use", "Maintenance"});
        if (v != null) statusBox.setSelectedItem(v.getStatus());
        add(statusBox);
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            if (plateField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter plate number");
                return;
            }
            vehicle = new Vehicle(idField.getText(), typeField.getText(), plateField.getText(),
                capacityField.getText(), (String) statusBox.getSelectedItem());
            dispose();
        });
        
        cancelBtn.addActionListener(e -> dispose());
        
        add(saveBtn);
        add(cancelBtn);
    }
    
    public Vehicle getVehicle() { return vehicle; }
}
