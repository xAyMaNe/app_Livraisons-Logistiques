/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dialogs;
import models.Driver;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author riomi
 */


public class DriverDialog extends JDialog {
    private Driver driver;
    private JTextField idField, nameField, phoneField, licenseField;
    private JComboBox<String> statusBox;
    
    public DriverDialog(Frame parent, Driver d) {
        super(parent, d == null ? "Add Driver" : "Edit Driver", true);
        setLayout(new GridLayout(6, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        add(new JLabel("ID:"));
        idField = new JTextField(d != null ? d.getId() : "D" + System.currentTimeMillis());
        idField.setEnabled(d == null);
        add(idField);
        
        add(new JLabel("Name:"));
        nameField = new JTextField(d != null ? d.getName() : "");
        add(nameField);
        
        add(new JLabel("Phone:"));
        phoneField = new JTextField(d != null ? d.getPhone() : "");
        add(phoneField);
        
        add(new JLabel("License:"));
        licenseField = new JTextField(d != null ? d.getLicense() : "");
        add(licenseField);
        
        add(new JLabel("Status:"));
        statusBox = new JComboBox<>(new String[]{"Available", "On Duty", "Off Duty"});
        if (d != null) statusBox.setSelectedItem(d.getStatus());
        add(statusBox);
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter name");
                return;
            }
            driver = new Driver(idField.getText(), nameField.getText(), phoneField.getText(),
                licenseField.getText(), (String) statusBox.getSelectedItem());
            dispose();
        });
        
        cancelBtn.addActionListener(e -> dispose());
        
        add(saveBtn);
        add(cancelBtn);
    }
    
    public Driver getDriver() { return driver; }
}
