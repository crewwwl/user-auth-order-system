-- Car Marketplace Database Schema

-- Create Cars Table
CREATE TABLE IF NOT EXISTS cars (
    car_id INTEGER PRIMARY KEY AUTOINCREMENT,
    brand TEXT NOT NULL CHECK(length(brand) > 0),
    model TEXT NOT NULL CHECK(length(model) > 0),
    year INTEGER NOT NULL CHECK(year >= 1900 AND year <= 2030),
    price DECIMAL(12,2) NOT NULL CHECK(price > 0),
    condition TEXT NOT NULL CHECK(condition IN ('NEW', 'USED')),
    seller_id INTEGER NOT NULL CHECK(seller_id > 0),
    mileage INTEGER DEFAULT 0 CHECK(mileage >= 0),
    color TEXT,
    fuel_type TEXT DEFAULT 'Gasoline' CHECK(fuel_type IN ('Gasoline', 'Diesel', 'Electric', 'Hybrid', 'CNG', 'LPG')),
    transmission TEXT DEFAULT 'Manual' CHECK(transmission IN ('Manual', 'Automatic', 'CVT', 'Semi-Automatic')),
    description TEXT,
    is_deleted BOOLEAN DEFAULT FALSE,
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Wishlist Table
CREATE TABLE IF NOT EXISTS wishlist (
    wishlist_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL CHECK(user_id > 0),
    car_id INTEGER NOT NULL,
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, car_id),
    FOREIGN KEY(car_id) REFERENCES cars(car_id) ON DELETE CASCADE
);

-- Create Sellers Table (for future extension)
CREATE TABLE IF NOT EXISTS sellers (
    seller_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL CHECK(length(name) > 0),
    email TEXT UNIQUE NOT NULL CHECK(email LIKE '%@%.%'),
    phone TEXT,
    address TEXT,
    rating DECIMAL(3,2) DEFAULT 0.0 CHECK(rating >= 0.0 AND rating <= 5.0),
    total_sales INTEGER DEFAULT 0 CHECK(total_sales >= 0),
    verified BOOLEAN DEFAULT FALSE,
    date_registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Users Table (for future extension)
CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL CHECK(length(username) >= 3),
    email TEXT UNIQUE NOT NULL CHECK(email LIKE '%@%.%'),
    password_hash TEXT NOT NULL,
    first_name TEXT,
    last_name TEXT,
    phone TEXT,
    date_registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Create Reviews Table (for future extension)
CREATE TABLE IF NOT EXISTS reviews (
    review_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    seller_id INTEGER NOT NULL,
    car_id INTEGER,
    rating INTEGER NOT NULL CHECK(rating >= 1 AND rating <= 5),
    title TEXT,
    comment TEXT,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_approved BOOLEAN DEFAULT FALSE,
    FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY(seller_id) REFERENCES sellers(seller_id) ON DELETE CASCADE,
    FOREIGN KEY(car_id) REFERENCES cars(car_id) ON DELETE SET NULL
);

-- Create Indexes for better performance
CREATE INDEX IF NOT EXISTS idx_cars_brand ON cars(brand);
CREATE INDEX IF NOT EXISTS idx_cars_model ON cars(brand, model);
CREATE INDEX IF NOT EXISTS idx_cars_condition ON cars(condition);
CREATE INDEX IF NOT EXISTS idx_cars_price ON cars(price);
CREATE INDEX IF NOT EXISTS idx_cars_year ON cars(year);
CREATE INDEX IF NOT EXISTS idx_cars_seller ON cars(seller_id);
CREATE INDEX IF NOT EXISTS idx_cars_not_deleted ON cars(is_deleted) WHERE is_deleted = FALSE;

CREATE INDEX IF NOT EXISTS idx_wishlist_user ON wishlist(user_id);
CREATE INDEX IF NOT EXISTS idx_wishlist_car ON wishlist(car_id);
CREATE INDEX IF NOT EXISTS idx_wishlist_user_car ON wishlist(user_id, car_id);

CREATE INDEX IF NOT EXISTS idx_reviews_seller ON reviews(seller_id);
CREATE INDEX IF NOT EXISTS idx_reviews_user ON reviews(user_id);
CREATE INDEX IF NOT EXISTS idx_reviews_car ON reviews(car_id);

-- Create Triggers for automatic updates
CREATE TRIGGER IF NOT EXISTS update_car_timestamp 
    AFTER UPDATE ON cars
BEGIN
    UPDATE cars SET date_updated = CURRENT_TIMESTAMP WHERE car_id = NEW.car_id;
END;

-- Sample Data for Testing
INSERT OR IGNORE INTO sellers (seller_id, name, email, phone, verified) VALUES
(1, 'Premium Auto Dealer', 'dealer@premiumauto.com', '+1-555-0101', TRUE),
(2, 'John Smith Motors', 'john@smithmotors.com', '+1-555-0102', TRUE),
(3, 'City Car Center', 'info@citycar.com', '+1-555-0103', FALSE),
(4, 'Electric Vehicle Hub', 'sales@evhub.com', '+1-555-0104', TRUE),
(5, 'Budget Cars Plus', 'contact@budgetcars.com', '+1-555-0105', FALSE);

INSERT OR IGNORE INTO users (user_id, username, email, first_name, last_name) VALUES
(101, 'car_enthusiast', 'enthusiast@email.com', 'Mike', 'Johnson'),
(102, 'family_buyer', 'family@email.com', 'Sarah', 'Wilson'),
(103, 'student_driver', 'student@email.com', 'Alex', 'Brown'),
(104, 'luxury_seeker', 'luxury@email.com', 'David', 'Davis'),
(105, 'eco_driver', 'eco@email.com', 'Emma', 'Green');

-- Sample Cars Data
INSERT OR IGNORE INTO cars (brand, model, year, price, condition, seller_id, mileage, color, fuel_type, transmission, description) VALUES
('Toyota', 'Camry', 2024, 35000.00, 'NEW', 1, 0, 'White', 'Gasoline', 'Automatic', 'Brand new Toyota Camry with advanced safety features'),
('Honda', 'Civic', 2020, 22000.00, 'USED', 2, 45000, 'Blue', 'Gasoline', 'Manual', 'Well-maintained Honda Civic with excellent fuel economy'),
('Tesla', 'Model 3', 2023, 45000.00, 'NEW', 4, 0, 'Black', 'Electric', 'Automatic', 'Tesla Model 3 with autopilot and premium interior'),
('BMW', 'X5', 2021, 55000.00, 'USED', 1, 32000, 'Silver', 'Gasoline', 'Automatic', 'Luxury BMW X5 with premium package'),
('Nissan', 'Leaf', 2022, 28000.00, 'USED', 4, 18000, 'Green', 'Electric', 'Automatic', 'Eco-friendly Nissan Leaf with fast charging'),
('Ford', 'F-150', 2023, 42000.00, 'NEW', 3, 0, 'Red', 'Gasoline', 'Automatic', 'Ford F-150 pickup truck with towing package'),
('Hyundai', 'Elantra', 2019, 18000.00, 'USED', 5, 62000, 'Gray', 'Gasoline', 'Manual', 'Reliable Hyundai Elantra, great for daily commuting'),
('Audi', 'A4', 2022, 48000.00, 'USED', 1, 25000, 'Black', 'Gasoline', 'Automatic', 'Audi A4 with leather interior and navigation'),
('Volkswagen', 'Golf', 2021, 25000.00, 'USED', 2, 38000, 'White', 'Gasoline', 'Manual', 'Sporty Volkswagen Golf with panoramic sunroof'),
('Chevrolet', 'Bolt', 2023, 32000.00, 'NEW', 4, 0, 'Blue', 'Electric', 'Automatic', 'Chevrolet Bolt EV with long range battery');

-- Sample Wishlist Data
INSERT OR IGNORE INTO wishlist (user_id, car_id) VALUES
(101, 1), -- Mike likes Toyota Camry
(101, 3), -- Mike likes Tesla Model 3
(102, 2), -- Sarah likes Honda Civic
(102, 6), -- Sarah likes Ford F-150
(103, 7), -- Alex likes Hyundai Elantra
(104, 4), -- David likes BMW X5
(104, 8), -- David likes Audi A4
(105, 3), -- Emma likes Tesla Model 3
(105, 5), -- Emma likes Nissan Leaf
(105, 10); -- Emma likes Chevrolet Bolt

-- Sample Reviews Data
INSERT OR IGNORE INTO reviews (user_id, seller_id, car_id, rating, title, comment, is_approved) VALUES
(101, 1, 1, 5, 'Excellent Service', 'Great experience buying from Premium Auto Dealer. Highly recommended!', TRUE),
(102, 2, 2, 4, 'Good Car, Fair Price', 'Honda Civic is in good condition. John was helpful throughout the process.', TRUE),
(103, 4, 3, 5, 'Amazing Electric Car', 'Tesla Model 3 exceeded my expectations. Great range and features.', TRUE),
(104, 1, 4, 4, 'Luxury Experience', 'BMW X5 is fantastic. Premium Auto Dealer provided excellent service.', TRUE),
(105, 4, 5, 5, 'Perfect for Eco-Driving', 'Nissan Leaf is perfect for city driving. Electric Vehicle Hub was very professional.', TRUE);

-- Views for common queries
CREATE VIEW IF NOT EXISTS available_cars AS
SELECT 
    c.*,
    s.name as seller_name,
    s.rating as seller_rating,
    (SELECT COUNT(*) FROM wishlist w WHERE w.car_id = c.car_id) as wishlist_count
FROM cars c
LEFT JOIN sellers s ON c.seller_id = s.seller_id
WHERE c.is_deleted = FALSE;

CREATE VIEW IF NOT EXISTS car_statistics AS
SELECT 
    COUNT(*) as total_cars,
    COUNT(CASE WHEN condition = 'NEW' THEN 1 END) as new_cars,
    COUNT(CASE WHEN condition = 'USED' THEN 1 END) as used_cars,
    AVG(price) as average_price,
    MIN(price) as min_price,
    MAX(price) as max_price,
    COUNT(DISTINCT brand) as unique_brands
FROM cars 
WHERE is_deleted = FALSE;

-- End of database schema