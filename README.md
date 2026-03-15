SPIN TO WIN: BEYBLADE X META TRACKER
====================================

DESCRIPTION
-----------
Spin To Win is a full-stack web application designed for tracking and 
analyzing the Beyblade X competitive meta. The system provides real-time 
insights into winning part combinations and part popularity through a 
browser-based terminal dashboard.

FEATURES
--------
* Curated Auto-Import: Uses a custom Tournament Registry to manage and 
  process tournament data from the Challonge API.
* Unified Leaderboard: Merges Standard (BX/UX) and Modular (CX) product 
  lines into a single view using SQL COALESCE logic.
* Dynamic Dashboard: Displays match totals and identifies dominant blades 
  based on current data.

TECHNICAL STACK
---------------
* Backend: Java 23, Spring Boot 3.2.2, Spring Data JPA.
* Database: Microsoft SQL Server.
* Frontend: HTML5, CSS3, JavaScript, Chart.js.

THE TOURNAMENT REGISTRY LOGIC
-----------------------------
The system utilizes a SQL table called 'TournamentRegistry' to manage 
data imports efficiently:
1. Registry Table: Tracks TournamentID, TournamentName, and an 
   'IsProcessed' flag.
2. Automation: On application startup, the system queries the registry 
   for any IDs where 'IsProcessed' is set to 0.
3. Prevention: Once a tournament is successfully processed, the flag is 
   updated to 1 to avoid duplicate match entries.

SETUP AND INSTALLATION
----------------------
1. Database Setup: Create a database named 'BeybladeX_MetaTracker' in 
   SQL Server and run the 'database_setup.sql' script to initialize the 
   schema and 89 matches currently in the collection.
2. Properties Configuration: Create an 'application.properties' file in 
   the 'src/main/resources/' directory with your local SQL Server and 
   Challonge API credentials.
3. Execution: Launch the application using 'mvn spring-boot:run' and 
   access the dashboard via 'http://localhost:8081/index.html'.

DEVELOPER INFORMATION
---------------------
Dennis "Teddy" Jones
* U.S. Army Veteran.
* Student at Valencia College.
