@ECHO OFF

rem Nayanika, picture viewer application
rem Copyright (C) 2021  Sukma Wardana
rem
rem This program is free software: you can redistribute it and/or modify
rem it under the terms of the GNU General Public License as published by
rem the Free Software Foundation, either version 3 of the License, or
rem (at your option) any later version.
rem
rem This program is distributed in the hope that it will be useful,
rem but WITHOUT ANY WARRANTY; without even the implied warranty of
rem MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
rem GNU General Public License for more details.
rem
rem You should have received a copy of the GNU General Public License
rem along with this program.  If not, see <https://www.gnu.org/licenses/>.

rem ------ ENVIRONMENT --------------------------------------------------------
rem The script depends on various environment variables to exist in order to
rem run properly. The java version we want to use, the location of the java
rem binaries (java home), and the project version as defined inside the pom.xml
rem file, e.g. 1.0-SNAPSHOT.

echo java home: %JAVA_HOME%
echo project version: %PROJECT_VERSION%
echo main JAR file: %MAIN_JAR%

rem ------ SETUP DIRECTORIES AND FILES ----------------------------------------
rem Remove previously generated java runtime and installers. Copy all required
rem jar files into the input/libs folder.

IF EXIST target\installer rmdir /S /Q target\installer
IF EXIST target\mods rmdir /S /Q target\mods
IF EXIST target\java-runtime rmdir /S /Q target\java-runtime
IF EXIST target\installer-work rmdir /S /Q target\installer-work

xcopy /S /Q target\dependency\* target\installer\input\libs\

rem ------ AUTOMATIC MODULES -------------------------------------------------
rem To create custom Java runtime, all the dependencies should be in modular
rem automatic modules is not supported to create custom Java runtime. So, we need
rem patch dependencies that still using automatic modules.

rem ------ REQUIRED MODULES ---------------------------------------------------
rem Use jlink to detect all modules that are required to run the application.
rem Starting point for the jdep analysis is the set of jars being used by the
rem application.

echo detecting required modules

"%JAVA_HOME%\bin\jdeps" ^
  -q ^
  --ignore-missing-deps ^
  --print-module-deps ^
  --module-path target/installer/input/libs ^
  --add-modules %MAIN_MODULE% target\installer\input\libs\%MAIN_JAR% > temp.txt

set /p detected_modules=<temp.txt

echo detected modules: %detected_modules%

rem ------ MANUAL MODULES -----------------------------------------------------
rem jdk.crypto.ec has to be added manually bound via --bind-services or
rem otherwise HTTPS does not work.
rem
rem See: https://bugs.openjdk.java.net/browse/JDK-8221674

set manual_modules=jdk.localedata
echo manual modules: %manual_modules%

rem ------ RUNTIME IMAGE ------------------------------------------------------
rem Use the jlink tool to create a runtime image for our application. We are
rem doing this is a separate step instead of letting jlink do the work as part
rem of the jpackage tool. This approach allows for finer configuration and also
rem works with dependencies that are not fully modularized, yet.

echo creating java runtime image

call "%JAVA_HOME%\bin\jlink" ^
  --strip-native-commands ^
  --no-header-files ^
  --no-man-pages ^
  --compress=2 ^
  --strip-debug ^
  --include-locales=en,in ^
  --module-path target\installer\input\libs ^
  --add-modules %detected_modules%,%manual_modules%,%MAIN_MODULE% ^
  --output target\java-runtime

rem ------ PACKAGING ----------------------------------------------------------
rem In the end we will find the package inside the target/installer directory.

call "%JAVA_HOME%\bin\jpackage" ^
  --type %INSTALLER_TYPE% ^
  --app-version %PROJECT_VERSION% ^
  --name %APP_NAME% ^
  --description "%APP_DESC%" ^
  --vendor "%VENDOR%" ^
  --copyright "%COPYRIGHT%" ^
  --license-file %LICENSE_FILE% ^
  --icon %ICON_PATH% ^
  --file-associations package\resources\properties\bmp.properties ^
  --file-associations package\resources\properties\jpg.properties ^
  --file-associations package\resources\properties\jpeg.properties ^
  --file-associations package\resources\properties\png.properties ^
  --runtime-image target\java-runtime ^
  --module %MAIN_MODULE%/%MAIN_CLASS% ^
  --dest target\installer ^
  --win-dir-chooser ^
  --win-shortcut ^
  --win-menu ^
  %EXTRA_ARGUMENTS% ^
  --temp %TEMP_DIR%
echo creating package installer

cd target\installer
jar -cfM %APP_NAME%-%PROJECT_VERSION%-%OPERATING_SYSTEM%-%ARCH%-%BUILD_NUMBER%.zip ^
  %APP_NAME%-%PROJECT_VERSION%.%INSTALLER_TYPE%
cd ..\..\
echo compress the installer
