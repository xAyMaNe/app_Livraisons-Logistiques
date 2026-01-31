-- =====================================================
-- BASE DE DONNÉES: LOGISTICS DELIVERY SYSTEM
-- =====================================================

-- Création de la base de données
CREATE DATABASE IF NOT EXISTS logistics_delivery;
USE logistics_delivery;

-- =====================================================
-- TABLE: DRIVER (Chauffeur)
-- =====================================================
CREATE TABLE driver (
    driver_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    license VARCHAR(50) NOT NULL UNIQUE,
    status ENUM('Available', 'On Duty', 'Off Duty') DEFAULT 'Available',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLE: VEHICLE (Véhicule)
-- =====================================================
CREATE TABLE vehicle (
    vehicle_id VARCHAR(50) PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    plate_number VARCHAR(20) NOT NULL UNIQUE,
    capacity VARCHAR(50) NOT NULL,
    status ENUM('Available', 'In Use', 'Maintenance') DEFAULT 'Available',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLE: CUSTOMER_ORDER (Commande)
-- =====================================================
CREATE TABLE customer_order (
    order_id VARCHAR(50) PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    items TEXT NOT NULL,
    status ENUM('Pending', 'In Transit', 'Delivered', 'Cancelled') DEFAULT 'Pending',
    order_date DATETIME NOT NULL,
    delivery_date DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLE: ROUTE (Itinéraire)
-- =====================================================
CREATE TABLE route (
    route_id VARCHAR(50) PRIMARY KEY,
    driver_id VARCHAR(50) NOT NULL,
    vehicle_id VARCHAR(50) NOT NULL,
    status ENUM('Active', 'Completed', 'Cancelled') DEFAULT 'Active',
    route_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id) ON DELETE CASCADE,
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

-- =====================================================
-- TABLE: ROUTE_ORDER (Association Route-Commande)
-- Table de liaison Many-to-Many
-- =====================================================
CREATE TABLE route_order (
    route_order_id INT AUTO_INCREMENT PRIMARY KEY,
    route_id VARCHAR(50) NOT NULL,
    order_id VARCHAR(50) NOT NULL,
    sequence_number INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (route_id) REFERENCES route(route_id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES customer_order(order_id) ON DELETE CASCADE,
    UNIQUE KEY unique_route_order (route_id, order_id)
);

-- =====================================================
-- INDEX POUR OPTIMISATION
-- =====================================================
CREATE INDEX idx_driver_status ON driver(status);
CREATE INDEX idx_vehicle_status ON vehicle(status);
CREATE INDEX idx_order_status ON customer_order(status);
CREATE INDEX idx_route_status ON route(status);
CREATE INDEX idx_route_date ON route(route_date);
CREATE INDEX idx_order_date ON customer_order(order_date);

-- =====================================================
-- DONNÉES D'EXEMPLE (SAMPLE DATA)
-- =====================================================

-- Insertion des chauffeurs
INSERT INTO driver (driver_id, name, phone, license, status) VALUES
('D001', 'Ahmed Hassan', '0612345678', 'LIC12345', 'Available'),
('D002', 'Fatima Zahra', '0623456789', 'LIC23456', 'Available'),
('D003', 'Mohammed Alami', '0634567890', 'LIC34567', 'On Duty');

-- Insertion des véhicules
INSERT INTO vehicle (vehicle_id, type, plate_number, capacity, status) VALUES
('V001', 'Van', 'ABC-1234', '1000 kg', 'Available'),
('V002', 'Truck', 'XYZ-5678', '5000 kg', 'Available'),
('V003', 'Motorcycle', 'MNO-9012', '50 kg', 'In Use');

-- Insertion des commandes
INSERT INTO customer_order (order_id, customer_name, address, items, status, order_date) VALUES
('O001', 'Mohammed Ali', '123 Rue Hassan II, Casablanca', 'Electronics', 'Pending', NOW()),
('O002', 'Sara Bennani', '45 Boulevard Zerktouni, Casablanca', 'Furniture', 'Pending', NOW()),
('O003', 'Karim Idrissi', '78 Avenue Mohammed V, Rabat', 'Books', 'In Transit', NOW()),
('O004', 'Amina Tazi', '90 Rue de Fès, Marrakech', 'Clothing', 'Delivered', NOW());

-- Insertion des routes
INSERT INTO route (route_id, driver_id, vehicle_id, status, route_date) VALUES
('R001', 'D001', 'V001', 'Active', CURDATE()),
('R002', 'D003', 'V003', 'Completed', CURDATE());

-- Insertion des associations route-commande
INSERT INTO route_order (route_id, order_id, sequence_number) VALUES
('R001', 'O001', 1),
('R001', 'O002', 2),
('R002', 'O003', 1);

-- =====================================================
-- REQUÊTES UTILES (USEFUL QUERIES)
-- =====================================================

-- Voir toutes les routes avec leurs détails
SELECT 
    r.route_id,
    d.name AS driver_name,
    v.plate_number AS vehicle,
    v.type AS vehicle_type,
    r.status AS route_status,
    r.route_date,
    COUNT(ro.order_id) AS total_orders
FROM route r
JOIN driver d ON r.driver_id = d.driver_id
JOIN vehicle v ON r.vehicle_id = v.vehicle_id
LEFT JOIN route_order ro ON r.route_id = ro.route_id
GROUP BY r.route_id, d.name, v.plate_number, v.type, r.status, r.route_date;

-- Voir les commandes d'une route spécifique
SELECT 
    ro.route_id,
    co.order_id,
    co.customer_name,
    co.address,
    co.items,
    co.status,
    ro.sequence_number
FROM route_order ro
JOIN customer_order co ON ro.order_id = co.order_id
WHERE ro.route_id = 'R001'
ORDER BY ro.sequence_number;

-- Chauffeurs disponibles
SELECT * FROM driver WHERE status = 'Available';

-- Véhicules disponibles
SELECT * FROM vehicle WHERE status = 'Available';

-- Commandes en attente
SELECT * FROM customer_order WHERE status = 'Pending';

-- Statistiques du jour
SELECT 
    (SELECT COUNT(*) FROM driver WHERE status = 'Available') AS available_drivers,
    (SELECT COUNT(*) FROM vehicle WHERE status = 'Available') AS available_vehicles,
    (SELECT COUNT(*) FROM customer_order WHERE status = 'Pending') AS pending_orders,
    (SELECT COUNT(*) FROM route WHERE status = 'Active' AND route_date = CURDATE()) AS active_routes;