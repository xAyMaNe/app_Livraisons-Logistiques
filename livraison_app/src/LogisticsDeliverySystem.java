import models.*;
import dialogs.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class LogisticsDeliverySystem extends JFrame {
    private JTabbedPane tabbedPane;
    private ArrayList<Driver> drivers;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Order> orders;
    private ArrayList<Route> routes;
    
    public LogisticsDeliverySystem() {
        drivers = new ArrayList<>();
        vehicles = new ArrayList<>();
        orders = new ArrayList<>();
        routes = new ArrayList<>();
        
        setTitle("Logistics Delivery Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Drivers", createDriverPanel());
        tabbedPane.addTab("Vehicles", createVehiclePanel());
        tabbedPane.addTab("Orders", createOrderPanel());
        tabbedPane.addTab("Routes", createRoutePanel());
        
        add(tabbedPane);
        
        addSampleData();
        updateDashboard();
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Modern header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 247, 250));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel title = new JLabel("📊 Dashboard Overview");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(44, 62, 80));
        
        JLabel subtitle = new JLabel("Real-time logistics monitoring");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(127, 140, 141));
        
        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(new Color(245, 247, 250));
        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(5));
        titleBox.add(subtitle);
        
        headerPanel.add(titleBox, BorderLayout.WEST);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Stats cards with modern design
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBackground(new Color(245, 247, 250));
        
        JPanel driversCard = createModernStatCard("👤 Drivers", "0", "Total available", 
            new Color(52, 152, 219), new Color(41, 128, 185));
        JPanel vehiclesCard = createModernStatCard("🚚 Vehicles", "0", "Ready to use", 
            new Color(46, 204, 113), new Color(39, 174, 96));
        JPanel ordersCard = createModernStatCard("📦 Active Orders", "0", "Pending delivery", 
            new Color(241, 196, 15), new Color(243, 156, 18));
        JPanel routesCard = createModernStatCard("🗺️ Routes", "0", "In progress", 
            new Color(155, 89, 182), new Color(142, 68, 173));
        
        statsPanel.add(driversCard);
        statsPanel.add(vehiclesCard);
        statsPanel.add(ordersCard);
        statsPanel.add(routesCard);
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createModernStatCard(String label, String value, String description, Color color1, Color color2) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient background
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Subtle shadow effect
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        card.setLayout(new BorderLayout(10, 10));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setPreferredSize(new Dimension(200, 140));
        
        // Icon and title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.WEST);
        
        card.add(topPanel, BorderLayout.NORTH);
        
        // Value
        JLabel valueLabel = new JLabel(value, SwingConstants.LEFT);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        valueLabel.setForeground(Color.WHITE);
        card.add(valueLabel, BorderLayout.CENTER);
        
        // Description
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(255, 255, 255, 200));
        card.add(descLabel, BorderLayout.SOUTH);
        
        // Hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        return card;
    }
    
    private JPanel createDriverPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"ID", "Name", "Phone", "License", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Add Driver");
        JButton editBtn = new JButton("Edit Driver");
        JButton deleteBtn = new JButton("Delete Driver");
        
        addBtn.addActionListener(e -> {
            DriverDialog dialog = new DriverDialog(this, null);
            dialog.setVisible(true);
            Driver driver = dialog.getDriver();
            if (driver != null) {
                drivers.add(driver);
                model.addRow(new Object[]{driver.getId(), driver.getName(), driver.getPhone(), driver.getLicense(), driver.getStatus()});
                updateDashboard();
            }
        });
        
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Driver driver = drivers.get(row);
                DriverDialog dialog = new DriverDialog(this, driver);
                dialog.setVisible(true);
                Driver updated = dialog.getDriver();
                if (updated != null) {
                    drivers.set(row, updated);
                    model.setValueAt(updated.getName(), row, 1);
                    model.setValueAt(updated.getPhone(), row, 2);
                    model.setValueAt(updated.getLicense(), row, 3);
                    model.setValueAt(updated.getStatus(), row, 4);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a driver to edit");
            }
        });
        
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this driver?");
                if (confirm == JOptionPane.YES_OPTION) {
                    drivers.remove(row);
                    model.removeRow(row);
                    updateDashboard();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a driver to delete");
            }
        });
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createVehiclePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"ID", "Type", "Plate Number", "Capacity", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Add Vehicle");
        JButton editBtn = new JButton("Edit Vehicle");
        JButton deleteBtn = new JButton("Delete Vehicle");
        
        addBtn.addActionListener(e -> {
            VehicleDialog dialog = new VehicleDialog(this, null);
            dialog.setVisible(true);
            Vehicle vehicle = dialog.getVehicle();
            if (vehicle != null) {
                vehicles.add(vehicle);
                model.addRow(new Object[]{vehicle.getId(), vehicle.getType(), vehicle.getPlateNumber(), vehicle.getCapacity(), vehicle.getStatus()});
                updateDashboard();
            }
        });
        
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Vehicle vehicle = vehicles.get(row);
                VehicleDialog dialog = new VehicleDialog(this, vehicle);
                dialog.setVisible(true);
                Vehicle updated = dialog.getVehicle();
                if (updated != null) {
                    vehicles.set(row, updated);
                    model.setValueAt(updated.getType(), row, 1);
                    model.setValueAt(updated.getPlateNumber(), row, 2);
                    model.setValueAt(updated.getCapacity(), row, 3);
                    model.setValueAt(updated.getStatus(), row, 4);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a vehicle to edit");
            }
        });
        
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this vehicle?");
                if (confirm == JOptionPane.YES_OPTION) {
                    vehicles.remove(row);
                    model.removeRow(row);
                    updateDashboard();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a vehicle to delete");
            }
        });
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"Order ID", "Customer", "Address", "Items", "Status", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Create Order");
        JButton updateStatusBtn = new JButton("Update Status");
        JButton deleteBtn = new JButton("Delete Order");
        
        addBtn.addActionListener(e -> {
            OrderDialog dialog = new OrderDialog(this, null);
            dialog.setVisible(true);
            Order order = dialog.getOrder();
            if (order != null) {
                orders.add(order);
                model.addRow(new Object[]{order.getId(), order.getCustomerName(), order.getAddress(), 
                    order.getItems(), order.getStatus(), order.getDate()});
                updateDashboard();
            }
        });
        
        updateStatusBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Order order = orders.get(row);
                String[] statuses = {"Pending", "In Transit", "Delivered", "Cancelled"};
                String newStatus = (String) JOptionPane.showInputDialog(this, 
                    "Select new status:", "Update Status", JOptionPane.QUESTION_MESSAGE,
                    null, statuses, order.getStatus());
                if (newStatus != null) {
                    order.setStatus(newStatus);
                    model.setValueAt(newStatus, row, 4);
                    updateDashboard();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an order");
            }
        });
        
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this order?");
                if (confirm == JOptionPane.YES_OPTION) {
                    orders.remove(row);
                    model.removeRow(row);
                    updateDashboard();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an order to delete");
            }
        });
        
        buttonPanel.add(addBtn);
        buttonPanel.add(updateStatusBtn);
        buttonPanel.add(deleteBtn);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createRoutePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"Route ID", "Driver", "Vehicle", "Orders", "Status", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Create Route");
        JButton viewBtn = new JButton("View Details");
        JButton deleteBtn = new JButton("Delete Route");
        
        addBtn.addActionListener(e -> {
            if (drivers.isEmpty() || vehicles.isEmpty() || orders.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please add drivers, vehicles, and orders first!");
                return;
            }
            RouteDialog dialog = new RouteDialog(this, null, drivers, vehicles, orders);
            dialog.setVisible(true);
            Route route = dialog.getRoute();
            if (route != null) {
                routes.add(route);
                model.addRow(new Object[]{route.getId(), route.getDriverName(), route.getVehiclePlate(), 
                    route.getOrderIds().size(), route.getStatus(), route.getDate()});
                updateDashboard();
            }
        });
        
        viewBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Route route = routes.get(row);
                String details = String.format(
                    "Route ID: %s\nDriver: %s\nVehicle: %s\nOrders: %s\nStatus: %s\nDate: %s",
                    route.getId(), route.getDriverName(), route.getVehiclePlate(), 
                    String.join(", ", route.getOrderIds()), route.getStatus(), route.getDate()
                );
                JOptionPane.showMessageDialog(this, details, "Route Details", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a route");
            }
        });
        
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this route?");
                if (confirm == JOptionPane.YES_OPTION) {
                    routes.remove(row);
                    model.removeRow(row);
                    updateDashboard();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a route to delete");
            }
        });
        
        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(deleteBtn);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void updateDashboard() {
        Component dashboardPanel = tabbedPane.getComponentAt(0);
        if (dashboardPanel instanceof JPanel) {
            JPanel panel = (JPanel) dashboardPanel;
            Component centerComp = ((BorderLayout) panel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
            if (centerComp instanceof JPanel) {
                JPanel statsPanel = (JPanel) centerComp;
                updateStatCard((JPanel) statsPanel.getComponent(0), String.valueOf(drivers.size()));
                updateStatCard((JPanel) statsPanel.getComponent(1), String.valueOf(vehicles.size()));
                updateStatCard((JPanel) statsPanel.getComponent(2), 
                    String.valueOf(orders.stream().filter(o -> !o.getStatus().equals("Delivered")).count()));
                updateStatCard((JPanel) statsPanel.getComponent(3), String.valueOf(routes.size()));
            }
        }
    }
    
    private void updateStatCard(JPanel card, String value) {
        Component centerComp = ((BorderLayout) card.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComp instanceof JLabel) {
            ((JLabel) centerComp).setText(value);
        }
    }
    
    private void addSampleData() {
        drivers.add(new Driver("D001", "Ahmed Hassan", "0612345678", "LIC12345", "Available"));
        drivers.add(new Driver("D002", "Fatima Zahra", "0623456789", "LIC23456", "Available"));
        
        vehicles.add(new Vehicle("V001", "Van", "ABC-1234", "1000 kg", "Available"));
        vehicles.add(new Vehicle("V002", "Truck", "XYZ-5678", "5000 kg", "Available"));
        
        orders.add(new Order("O001", "Mohammed Ali", "123 Rue Hassan II, Casablanca", 
            "Electronics", "Pending", new Date().toString()));
        orders.add(new Order("O002", "Sara Bennani", "45 Boulevard Zerktouni, Casablanca", 
            "Furniture", "Pending", new Date().toString()));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LogisticsDeliverySystem app = new LogisticsDeliverySystem();
            app.setVisible(true);
        });
    }
}