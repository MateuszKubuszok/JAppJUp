Project for an Automatic Updater's library, client and server's repository.
===========

Project of an updater application/library written in Java. Concerns development
of a library, an example client's application that make use of it and a server
repository.

It has started as a project made by four students as a part of Nokia Siemens
Networks' "Innovative Projects" program. Mariusz Kapcia and Pawel Kedzia were
responsible for the client part while Maciej Jaworski and Mateusz Kubuszok
were responsible for the server.

Currently only the latter is an active developer and he is responsible for
development and maintenace of the project. In the process of preparing project
for usage in enterprise client had to be rewritten from the scrath, and server
was improved greatly. As such currently contribution from only one
developer can be noticed. It should be noted though, that many of solutions
origins from earlier versions of code. The reasearch concerning elevation of
process done by Mariusz and Pawel did not go to waste.

Things already done:
 * client appears to be able to perform some updates on Windows and Linux,
 * repository just works - but it was not checked for performence or security
 issues yet,
 * user is able to perform some minor configuration by GUI.

Things to do:
 * implement ability to update the client itself,
 * distinguish between client's update and others program's update,
 * introduce the ability to handle applications run as JARs (obtaining process
 ID by run path with use of AppName.jar),
 * introduce ability to check whether update was already downloaded preventing
 from multiple download of the same file,
 * improve design of a repository, and introduce AJAX validation for saving
 users time,
 * ensure that repository will not blow server into space,
 * ensure that gradle's builder runs tests,
 * improve quality of the tests (proper distiction between unit and funtional
 tests for instance),
 * introduce functionality to install archival updates and "lock" updates (so
 that program won't be updated to version higher or equal to defined),
 * add some documentation to Client's project,
 * resolve bugs with no refreshing after some of the actions in Client's GUI,
 * introducing ability to install updates on Mac OS and others systems,
 * considering creating separate repository for "Java's System-Dependent
 Process Utils" (JSDPU) project (quite a big name as for something so trivial
 and unimpressive...),
 * not give up too soon.