# RadiantReturns - Portfolio Management System

A Spring Boot application for managing investment portfolios, assets, and tracking market performance.

##  Features

- **Asset Management**: Create, update, and track various asset types (stocks, bonds, crypto, etc.)
- **Portfolio Tracking**: Organize assets into portfolios with holdings
- **Category Organization**: Categorize assets and holdings for better organization
- **Market Price Tracking**: Monitor real-time market prices for assets
- **RESTful APIs**: Complete REST endpoints for all operations

##  Tech Stack

- **Java 17**
- **Spring Boot 3.2.2**
- **Maven** - Build and dependency management
- **Spring Data JPA** - Data persistence
- **Spring Web** - REST API development
- **Spring Boot DevTools** - Development utilities

##  Project Structure

```
src/main/java/com/portfolio/portfoliomanager/
├── asset/              # Asset entities
├── category/           # Category management
├── controller/         # REST API controllers
├── dto/               # Data Transfer Objects
├── holding/           # Holdings and categories
├── market/            # Market price tracking
├── portfolio/         # Portfolio management
├── repository/        # Data access layer
└── service/           # Business logic layer
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Git

### Installation

1. Clone the repository:
```bash
git clone https://github.com/TPawarCodes/RadiantReturns.git
cd RadiantReturns
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔌 API Endpoints

### Assets
- `GET /api/assets` - Get all assets
- `POST /api/assets` - Create new asset
- `GET /api/assets/{id}` - Get asset by ID
- `PUT /api/assets/{id}` - Update asset
- `DELETE /api/assets/{id}` - Delete asset

### Categories
- `GET /api/categories` - Get all categories
- `POST /api/categories` - Create new category
- `GET /api/categories/{id}` - Get category by ID
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Portfolios
- `GET /api/portfolios` - Get all portfolios
- `POST /api/portfolios` - Create new portfolio
- `GET /api/portfolios/{id}` - Get portfolio by ID
- `PUT /api/portfolios/{id}` - Update portfolio
- `DELETE /api/portfolios/{id}` - Delete portfolio

### Holdings
- `GET /api/holdings` - Get all holdings
- `POST /api/holdings` - Create new holding
- `GET /api/holdings/{id}` - Get holding by ID

##  Configuration

Application properties can be configured in `src/main/resources/application.properties`:

```properties
# Server configuration
server.port=8080

# Application name
spring.application.name=RadReturns

# Logging
logging.level.root=INFO
logging.level.com.radreturns=DEBUG
```

##  Building for Production

```bash
mvn clean package
java -jar target/radreturns-0.0.1-SNAPSHOT.jar
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

##  Author

**TPawarCodes**

- GitHub: [@TPawarCodes](https://github.com/TPawarCodes)

##  Acknowledgments

- Spring Boot team for the excellent framework
- Contributors and maintainers

---

**Note**: This project is under active development. Features and APIs may change.
