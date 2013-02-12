Misc's manual
===========

Misc contains elements not being a part of main program, but helping with
using/debuging it.

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
