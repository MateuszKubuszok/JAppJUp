Library's manual
===========

Building
-----------

To build Client it is required to have JDK 1.7+ and Gradle 1.0 or later
installed. Compilation and assembly is made by running command
`gradle buildLibrary` inside the main catalog.
	
Result is stored inside new `Dist/Client` folder. It expects presence of
dom4j, Jaxen and Guava libraries in the `/libraries` subdirectory and
AutoUpdaterCommons and JSDPU projects in the same directory.
	
Alternatively, it can be build by IDE. To prepare project for Eclipse run
`gradle eclipse` command. It will create all files necessary to import projects
into Eclipse as well as download all required dependancies.

Documentation and examples of usage
-----------
	
Documentation can be generated with tools such as Javadoc or Doxygen. Also IDE
such as Eclipse or Netbeans will provide code completion and documentation
making usage of library easier.

An example of how library could be used is AutoUpdaterGuiClient project. It
should be notes though that it does not provide documentation.
