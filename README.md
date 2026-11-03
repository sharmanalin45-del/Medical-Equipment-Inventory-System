# Medical Equipment Inventory System

A Java Swing desktop application for managing medical equipment inventory, maintenance records, and equipment requests using MySQL.

## Project Overview
The **Medical Equipment Inventory System** helps hospitals and medical institutions maintain equipment records, monitor stock, manage maintenance, and handle equipment requests through a centralized desktop application.

Built with **Java Swing** for the GUI, **MySQL** for data storage, and **JDBC** for database connectivity.

## Key Features
- User authentication (Login and Sign Up)
- Dashboard navigation
- Add, view, update, and delete equipment records
- Medical equipment maintenance management
- Submit and view equipment requests
- MySQL database integration through JDBC
- **Smart Equipment Request Conflict Detector & Priority Recommendation System**
  - Checks requested quantity against available stock
  - Detects stock conflicts
  - Calculates priority score and priority level
  - Displays smart recommendations for administrator review
  - Administrator retains final approval/refusal authority

## Special Feature / USP
### Smart Equipment Request Conflict Detector & Priority Recommendation System

This feature analyzes equipment requests to assist administrators in reviewing urgency and possible stock conflicts. It can consider:
- Department urgency
- Request purpose
- Equipment criticality
- Requested quantity and available stock
- Pending requests / waiting time (where implemented)

The system generates a **Priority Score**, assigns a **Priority Level** (HIGH, MEDIUM, or LOW), checks for stock conflicts, and displays a recommendation.

> The recommendation is decision support only. The administrator retains final authority to accept or refuse a request.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application logic |
| Java Swing | Desktop GUI |
| MySQL | Relational database |
| JDBC | Java–MySQL connectivity |
| IntelliJ IDEA | Development environment |

## Project Structure

```text
Medical Equipment Inventory System/
├── src/
│   ├── database/
│   │   └── DBConnection.java
│   └── pages/
│       ├── Main.java
│       ├── MainFrame.java
│       ├── AppTheme.java
│       ├── LoginPage.java
│       ├── SignUpPage.java
│       ├── DashboardPage.java
│       ├── AddEquipmentPage.java
│       ├── ViewEquipmentPage.java
│       ├── UpdateEquipmentPage.java
│       ├── DeleteEquipmentPage.java
│       ├── MaintenancePage.java
│       ├── AddMaintenancePage.java
│        ├── UpdateMaintenancePage.java
│        ├── DeleteMaintenancePage.java
│        ├── EquipmentRequestPage.java
│        ├── RequestEquipmentPage.java
│        ├── ViewRequestsPage.java
│        ├── SmartRequestAnalyzer.java
│        └── NotificationPage.java
└── README.md
```

*Note: File names and structure may differ slightly in your current project version.*

## Database Setup

1. Install and start **MySQL Server**.
2. Open MySQL Workbench or the MySQL command line.
3. Create the database:

```sql
CREATE DATABASE medical_inventory;
USE medical_inventory;
```

4. Create or import the required tables: `users`, `equipment`, `maintenance`, and `equipment_requests`, using the SQL schema for your project.
5. Update database credentials in `src/database/DBConnection.java`.

## Configuration

Verify the connection URL, username, and password in `DBConnection.java`.

Example JDBC URL:

```java
jdbc:mysql://localhost:3306/medical_inventory
```

**Security:** Do not commit real database passwords or secrets to a public repository.

## How to Run

1. Clone or download the repository.
2. Open the project in **IntelliJ IDEA**.
3. Configure the JDK.
4. Add MySQL Connector/J if it is not already configured.
5. Start MySQL Server and set up the database/tables.
6. Check credentials in `DBConnection.java`.
7. Run `Main.java`.
8. Log in and navigate using the dashboard.

## Usage

1. Log in to the application.
2. Use **Equipment** to manage inventory.
3. Use **Maintenance** to manage maintenance records.
4. Open **Equipment Request** to submit requests.
5. Open **View Requests** to inspect request status, priority, conflict status, and recommendations.
6. Administrators review requests and make the final decision.

## Future Enhancements
- Email/SMS alerts for critical equipment shortages
- Inventory and request reports
- Export reports to PDF/Excel
- Audit history and improved request tracking
- Stock forecasting and analytics
- More granular role-based permissions

## Academic Project
Developed as a **B.Tech academic project**.

## License
Intended for academic and educational use. Add an open-source license if you plan to distribute the project publicly.
