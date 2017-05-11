:: switchto-SQLSERVER.bat
::Switch from MySQL to SqlServer context.XML, maven build and startup-debug.bat
@ECHO

set "WAR_FILE=cbioportal-1.4.1.war"
set "DEPLOYED_FILE=cbioportal.war"

set "PROJ_DIR= C:\eclipse_genomics\Git\cbioportal-sqlserver
set "FROM_DIR= C:\eclipse_genomics\Git\cbioportal-sqlserver\portal\target\"
set "TO_DIR= C:\apache-tomcat-7.0.63\webapps"
set "BIN_DIR= C:\apache-tomcat-7.0.63\bin"
set "LOG_FILE=C:\Users\jkim\AppData\Local\Temp\cbioportal.log"

set "CONF_DIR=C:\apache-tomcat-7.0.63\conf"
set "CONTEXT_MYSQL=context-MYSQL.xml"
set "CONTEXT_SQLSERVER=context-SQLSERVER.xml"
set "CONTEXT= context.xml"


#Switch context file
cd %CONF_DIR%
move %CONTEXT% %CONTEXT_MYSQL%
move %CONTEXT_SQLSERVER% %CONTEXT%




del %LOG_FILE%

#Build 
#cd %PROJ_DIR%
#call mvn clean install -Dmaven.test.skip=true -DskipTests


cd %TO_DIR%
del %DEPLOYED_FILE%
copy %FROM_DIR%%WAR_FILE% %DEPLOYED_FILE%
rd /S /Q cbioportal
cd %BIN_DIR%
startup-debug.bat