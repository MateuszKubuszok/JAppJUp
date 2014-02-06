Project of an automatic updater's library, client and server's repository.
===========

[![Build Status](https://travis-ci.org/MateuszKubuszok/JAppJUp.png)](https://travis-ci.org/MateuszKubuszok/JAppJUp)

Project of an updater application/library written in Java. Concerns development
of a library, an example client's application that make use of it and a server
repository.

Goals
-----------
Project is aiming mainly for creation of an updater capable of:
 * discriminating various packages (modules) that each program is made of,
 * downloading updates for each package,
 * termination of updated applications,
 * installation of updates by:
   * extracting ZIP package into specified location,
   * overwritting specified file with downloaded version,
   * executing downloaded script or program,
 * running installation processes with elevation,
 * ability to run installed programs,
 * displaying information about known bugs for a program and changelogs for its
   packages,
 * GUI allowing for easy access to all of its functionalities,
 * updater should be albe to work on Windows and Linux systems.

Updaters functionality should be also available via the library, allowing programmer
to implement his own update applicateion the way he likes.

Updates would be downloaded from servers based on repository project or compatible.

History
-----------

It has started as a project made by four students, as a part of Nokia Siemens
Networks' *Innovative Projects* program. Mariusz Kapcia and Paweł Kędzia were
responsible for the client part, while Maciej Jaworski and Mateusz Kubuszok
were responsible for the server.

Currently only Mateusz Kubuszok is an active developer and he's responsible for
development and maintenace of the project. In the process of preparing project
for usage in enterprise client had to be rewritten from the scratch, and server
was improved greatly. As such currently contribution from only one
developer can be noticed. It should be noted though, that many of solutions
origins from earlier versions of code. The reasearch concerning elevation of
process done by Mariusz and Paweł did not go to waste.

Specific manuals
-----------
 * [Wiki](https://github.com/MateuszKubuszok/JAppJUp/wiki) - project's Wiki page with
 a lot of informations about how to use Client and Server,
 * [Repository's manual](Readmes/README.REPOSITORY.md) - contains basic information about
 building and deploying server,
 * [Library's manual](Readmes/README.LIBRARY.md) - contains basic information about
 building and using libray, as well as about requirements,
 * [Client's manual](Readmes/README.CLIENT.md) - containg basic information about
 building, configuring and using client,
 * [Misc's manual](Readmes/README.MISC.md) - containg information about various utilities
 usage.

Development
-----------

**Things already done**:
 - [x] client appears to be able to perform some updates on Windows and Linux,
 - [x] repository just works - but it was not yet checked for performence
 or security issues,
 - [x] user is able to perform some minor configuration by GUI,
 - [x] `gradle build` runs tests.

**Things to do**:
 - [ ] implement ability to update the client itself,
 - [ ] distinguish between client's update and anothers program's update,
 - [ ] introduce AJAX validation into server,
 - [ ] ensure that repository will not blow server into space,
 - [ ] introduce functionality to install archival updates and *lock* updates (so
 that program won't be updated to version higher or equal to some defined),
 - [ ] add some documentation to Client's project,
 - [ ] introducing ability to install updates on Mac OS and others systems,
 - [ ] not give up too soon.

License
-----------
Project's own licence can be found [here](LICENSE.md). Licenses of referenced libraries
can be found either on their respective homepages or inside their source code files,
as well as inside this [NOTICE](NOTICE.md).
