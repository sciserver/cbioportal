@echo ***                ***
@echo **   SQL Installer  **  by Tamas Budavari, April 2015
@echo ***                ***
@echo off

set server=%1
set dbname=%2

:step1
sqlcmd -S %server% -d %dbname% -E -b -i Deploy.sql
if not errorlevel 1 goto :end
echo !!! INSTALL ERROR !!!
goto :usage


:usage
echo Syntax:   Install.bat <server> <database> 
echo Example:  Install.bat localhost Test 

:end
