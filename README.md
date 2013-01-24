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

Development
-----------

**Things already done**:
 * client appears to be able to perform some updates on Windows and Linux,
 * repository just works - but it was not yet checked for performence
 or security issues,
 * user is able to perform some minor configuration by GUI,
 * *gradle build* runs tests.

**Things to do**:
 * implement ability to update the client itself,
 * distinguish between client's update and anothers program's update,
 * introduce the ability to handle JARs (obtaining java's process ID by usage of
 its *run path*),
 * introduce ability to check, whether update was already downloaded preventing
 multiple download of the same file,
 * improve design of a repository, and introduce AJAX validation,
 * ensure that repository will not blow server into space,
 * introduce functionality to install archival updates and *lock* updates (so
 that program won't be updated to version higher or equal to some defined),
 * add some documentation to Client's project,
 * introducing ability to install updates on Mac OS and others systems,
 * considering creating separate repository for *Java's System-Dependent
 Process Utils* (JSDPU) project (quite a big name as for something so trivial
 and unimpressive...),
 * not give up too soon.