Client's manual
===========

Building
-----------

To build Client it is required to have JDK 1.7+ and Gradle 1.0 or later
installed. Compilation and assembly is made by running command
`gradle buildClient` inside the main catalog.
	
Result is stored inside new AutoUpdater/Client folder. It can be run with
`java -jar /path/to/Client.jar` command.

Configuration
-----------

To use updater it is required to properly configure it. Configuration mode is
obtainer by starting program with `--config` parameter.
	
In newly opened window we can see all information that defines single
installation:
 * Program Name: defines name used for identifying program on repository,
 * Directory: defines target for updates' installation. All installation paths
will be used as relative to this one,
 * Server: full repository's address,
 * Executable name: used for program termination right before update. For all
 process with such executable name attempt of termination will be made,
 * Running command: used for running application from tray menu,
 * Development version: whether program is in development or release version.
	
New installation can be added by clicking the "Add Program" button. Changes are
saved by clicking the "Save" button.
	
**It should be noted that there can be only one installation with the same**:
 * Program Name,
 * Directory,
 * Server values.
 
Usage
-----------
	
When run without `--config` parameter program will run in normal mode. It can
then be used for actual update check and perfomance. Program will check for
updates every 10 minutes, but it can fastened by clicking the *Check updates*
button or tray menu position with the same name. 

If updates are found they can be installed by clicking *Install updates* button
or tray menu possition. Client will then download updates, kill updated
programs and perform update with elevated process (elevation dialog window
will appear).
	
Client will provide information about download and update progress and success.
In case of failure it is possible to perform another attempt to update
programs.
