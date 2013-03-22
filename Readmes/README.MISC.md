Misc's manual
===========

Misc contains elements not being a part of main program, but helping with
using/debuging it.

Exampliary launch4j installation
-----------

Placed at `Misc/Launch4j`.

Your can create package independent from local Java by using launch4j to create
`Client.exe` and `Installer.exe` files.

Later on You can add your own initial configuration to `installer/settings.xml`.
Keep in mind that it should point to Installer.exe as installer executable.

Finally add portable JRE installation e.g. created by jPortable, to `jre`
directory. Prepared installation can be packed with some installer or just
installed with install.bat script.

If You want to debug You can use loggers *.properties files (see below.)


Loggers' properties
-----------

Placed at `Misc/LoggerProperties`.

Logger properties defines behaviour of loggers of both client and installer. It
contains information about output, logging levels and formetters. The logger is
actually `java.util.loggging.Logger` instance wrapped to better suit the way
logger is used, e.g. renaming logging levels and adding better output formatting.

To use it simply copy `*.properties` into AutoUpdater's directory.

Repository's uploader
-----------

Placed at `Misc/PythonUploadScript`.

Simple Python script that can upload update into server with given data and
credencials.

The scipt was written and tested with Python 2.7.3. Since most Linux distros
contains Python interpreter, it can be used there out of the box. On Windows
it can be used after Python installation or via py2exe: after installing
interpreter and py2exe libraries, run `python script.py py2exe`. Directory
`dist` with file `uploader.exe` and dependancies can be then used as standalone
application. Folder `build` can be simply removed.

Application usage can be found out by running `python uploader.py --help` (or
`uploader.exe --help`).

Eclipse settings
-----------

Contains reccomended Eclipse settings. Configuration provides warnings, editor
save actions and MoreUnit settings as used during development.