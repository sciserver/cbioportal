:: switchto-MYSQL.bat
::Switch context.XML from SQL Server to  MySQL version, maven build and startup-debug.bat
@ECHO

set "WAR_FILE=cbioportal-1.4.1.war"
set "DEPLOYED_FILE=cbioportal.war"

set "FROM_DIR= C:\eclipse_genomics\Git\cbioportal-mysql\portal\target\"
set "TO_DIR= C:\apache-tomcat-7.0.63\webapps"
set "BIN_DIR= C:\apache-tomcat-7.0.63\bin"

set "CONF_DIR=C:\apache-tomcat-7.0.63\conf"
set "CONTEXT_MYSQL=context-MYSQL.xml"
set "CONTEXT_SQLSERVER=context-SQLSERVER.xml"
set "CONTEXT= context.xml"


#Switch context file
cd %CONF_DIR%
move %CONTEXT% %CONTEXT_SQLSERVER%
move %CONTEXT_MYSQL% %CONTEXT%


cd %TO_DIR%

#Move(or del) the existing CBioPortal.war
#del %DEPLOYED_FILE%

#Copy CBioPortal.war of CBioPortal-MySQL to CBioPortal.war
copy %FROM_DIR%%WAR_FILE% %DEPLOYED_FILE%
rd /S /Q cbioportal


cd %BIN_DIR%
startup-debug.bat