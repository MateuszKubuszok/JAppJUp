Project of an automatic updater's library, client and server's repository.
===========

Project of an updater application/library written in Java. Concerns development
of a library, an example client's application that make use of it and a server
repository.

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
 * [Repository's manual](README.REPOSITORY.md) - contains basic information about
 building and deploying server,
 * [Library's manual](README.LIBRARY.md) - contains basic information about
 building and using libray, as well as about requirements,
 * [Client's manual](README.CLIENT.md) - containg basic information about
 building, configuring and using client.

Development
-----------

**Things already done**:
 - [x] client appears to be able to perform some updates on Windows and Linux,
 - [x] repository just works - but it was not yet checked for performence
 or security issues,
 - [x] user is able to perform some minor configuration by GUI,
 - [x] *gradle build* runs tests.

**Things to do**:
 - [ ] implement ability to update the client itself,
 - [ ] distinguish between client's update and anothers program's update,
 - [ ] introduce the ability to handle JARs (obtaining java's process ID by usage of
 its *run path*),
 - [ ] introduce AJAX validation into server,
 - [ ] ensure that repository will not blow server into space,
 - [ ] introduce functionality to install archival updates and *lock* updates (so
 that program won't be updated to version higher or equal to some defined),
 - [ ] add some documentation to Client's project,
 - [ ] introducing ability to install updates on Mac OS and others systems,
 - [ ] considering creating separate repository for *Java's System-Dependent
 Process Utils* (JSDPU) project (quite a big name as for something so trivial
 and unimpressive...),
 - [ ] not give up too soon.

License
-----------
Project's own licence can be found [here](LICENSE.md). Licenses of referenced libraries
can be found either on their respective homepages or inside their source code files.
