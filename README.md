# Car Marketplace Platform

## Overview

A comprehensive Java-based e-commerce platform for car buying and selling, developed by the Young Developers Team as part of a collaborative project.

## Team Members

- **Khantore** - Team Member
- **Amir** - Team Member
- **Ansar** - Car Listings & Wishlist Management

## Project Features

### Ansar's Components ✅

#### 1. **Car Listings Management**

- Complete CRUD operations for car listings
- Support for both NEW and USED vehicles
- Advanced validation system
- Soft delete functionality
- Search and filter capabilities

#### 2. **Wishlist/Favorites System**

- User-specific wishlist management
- Duplicate prevention
- In-memory caching for performance
- Bulk operations support
- Search within wishlists

#### 3. **Future Extensions**

- User reviews and ratings system (database schema ready)
- Seller profile management (database schema ready)

## Technology Stack

- **Language**: Java 11+
- **Database**: SQLite with JDBC
- **Architecture**: Singleton Pattern, MVC Design
- **Caching**: Java Collections (ConcurrentHashMap)
- **Build Tool**: Maven/Gradle compatible

## Project Structure

```
CarMarketplace/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── models/
│   │       │   ├── Car.java                    ✅ Complete
│   │       │   └── WishListItem.java           ✅ Complete
│   │       ├── managers/
│   │       │   ├── CarListingManager.java      ✅ Complete
│   │       │   └── WishListManager.java        ✅ Complete
│   │       └── CarMarketplaceDemo.java         ✅ Complete
│   └── resources/
│       └── database.sql                        ✅ Complete
├── lib/
└── README.md                                   ✅ Complete
```

## Quick Start

### Prerequisites

- Java 11 or higher
- SQLite JDBC driver
- IDE (VS Code, IntelliJ IDEA, Eclipse)

### Setup Instructions

1. **Clone/Download the project**
2. **Set up VS Code for Java**:

   ```bash
   # Install Java Extension Pack in VS Code
   # Ctrl+Shift+X → Search "Extension Pack for Java"
   ```

3. **Add SQLite JDBC Driver**:

   ```bash
   # Download sqlite-jdbc-x.x.x.jar
   # Place in lib/ directory
   ```

4. **Run the Demo**:
   ```bash
   javac -cp "lib/*" -d bin src/main/java/**/*.java
   java -cp "bin:lib/*" CarMarketplaceDemo
   ```

## API Documentation

### CarListingManager

```java
// Singleton instance
CarListingManager carManager = CarListingManager.getInstance();

// Add new car
boolean success = carManager.addCar(car);

// Update existing car
boolean updated = carManager.updateCar(car);

// Delete car (soft delete)
boolean deleted = carManager.deleteCar(carId);

// Search operations
List<Car> toyotaCars = carManager.searchCarsByBrand("Toyota");
List<Car> newCars = carManager.getCarsByCondition("NEW");
List<Car> priceRange = carManager.searchCarsByPriceRange(min, max);

// Get statistics
Map<String, Object> stats = carManager.getStatistics();
```

### WishListManager

```java
// Singleton instance
WishListManager wishManager = WishListManager.getInstance();

// Add to wishlist
boolean added = wishManager.addToWishList(userId, carId);

// Remove from wishlist
boolean removed = wishManager.removeFromWishList(userId, carId);

// Get user's wishlist
List<WishListItem> wishlist = wishManager.getUserWishList(userId);
List<Car> cars = wishManager.getUserWishListCars(userId);

// Search in wishlist
List<Car> results = wishManager.searchInUserWishList(userId, "Honda");

// Popular cars
List<Map<String, Object>> popular = wishManager.getMostPopularWishListCars(5);
```

## Database Schema

### Core Tables

- **cars** - Vehicle listings with full details
- **wishlist** - User favorites/wishlist items
- **sellers** - Seller profiles (ready for extension)
- **users** - User accounts (ready for extension)
- **reviews** - User reviews (ready for extension)

### Key Features

- Foreign key constraints for data integrity
- Indexes for optimized performance
- Triggers for automatic timestamp updates
- Sample data for testing
- Views for common queries

## Validation Rules

### Car Validation

- **Brand/Model**: Required, non-empty
- **Year**: 1900 - current year + 1
- **Price**: Must be positive
- **Condition**: Only "NEW" or "USED"
- **Seller ID**: Must be positive integer

### Wishlist Validation

- Duplicate prevention per user
- Car existence validation
- User ID validation

## Performance Features

- **In-Memory Caching**: Fast access to frequently used data
- **Database Indexing**: Optimized queries for large datasets
- **Connection Pooling**: Efficient database connections
- **Lazy Loading**: Load data only when needed

## Testing

Run the demo to see all features in action:

```bash
java CarMarketplaceDemo
```

### Demo Includes:

1. **Car Management**: Add, update, delete operations
2. **Wishlist Operations**: Add/remove favorites
3. **Search & Filter**: Multiple search criteria
4. **Statistics**: System analytics
5. **Validation**: Error handling examples

## Integration Points

This module is designed to integrate seamlessly with:

- **Khantore's Components**: User authentication, payment processing
- **Amir's Components**: Order management, shipping

### Integration Example:

```java
// Your components can use our managers
CarListingManager carManager = CarListingManager.getInstance();
WishListManager wishManager = WishListManager.getInstance();

// Example: Get user's wishlist for order processing
List<Car> userWishlistCars = wishManager.getUserWishListCars(userId);
```

## Error Handling

- Comprehensive validation with user-friendly messages
- SQL exception handling with logging
- Null pointer protection
- Graceful degradation for missing data

## Future Enhancements

### Ready for Implementation:

1. **Review System**: Database schema complete
2. **Seller Management**: Tables and relationships ready
3. **User Authentication**: User table structure prepared
4. **Advanced Search**: Additional filters and sorting
5. **Image Management**: File upload and storage
6. **Email Notifications**: Price alerts for wishlist items

## Contributing

This is part of the Young Developers Team project. Each team member is responsible for their assigned components while ensuring seamless integration.

### Code Standards:

- Java naming conventions
- Comprehensive error handling
- Documentation for public methods
- Unit test coverage (future)

## License

This project is developed for educational purposes as part of the Young Developers Team collaboration.

## Contact

**Ansar** - Car Listings & Wishlist Management

- Responsible for comprehensive car management and user favorites functionality
- Integration support for team components
