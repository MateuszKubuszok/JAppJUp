Repository's manual
===========

Building and deployment
-----------

To build Server it is required to have JDK 1.7+ and Gradle 1.0 or later
installed. Compilation and assembly is made by running command
`gradle buildServer` inside the main catalog.

Result is stored inside new `Dist/Server` folder. 

Database configuration
-----------
 
By default it uses MySQL5 database at *localhost:3306* with:
 * username = `mysql_admin`,
 * password = `pass`,
 * schema's name = `updater`.
It can be changed by edition of `/WEB-INF/classes/JDBC.properties`
and `/WEB-INF/classes/Hibernate.properties` files.
	
Application requires schema to be already present in target database. All
neccessary tables will be created at deployment.

Initial configuration
-----------

To manage repository it is required to log in. Files */sql/init_users.my.sql*
and *sql/init_users.postgre.sql* provide initial entries with administrative
account:
 * login = `root`,
 * password = `password`
for MySQL5 and PostgreSQL respectively.
	
Default password should be changed as soon as possible. Change of an username
is also recommended (via database edition or creation of new admin with other
username and deletion of old one).

API for clients
-----------

**Listing programs and packages**:
 * request: GET /server_address/api/list_repo

**Listing bugs for a specific program**:
 * request: GET /server_address/api/list_bugs/{programID}

**Listing newest updates (release and development) for a specific package**:
 * request: GET /server_address/api/list_updates/{packageID}

**Listing changelogs for a specific package**:
 * request: GET /server_address/api/list_changes/{packageID}

**Downloading a specific update**:
 * request: GET /server_address/api/download/{updateID}
