:: Rerun single unit test after fixing a unittest code.
:: If code change occurs in different module, use debug-singletest.bat
@ECHO

set "WAR_FILE=cbioportal-1.4.1.war"
set "DEPLOYED_FILE=cbioportal.war"

set "PROJ_DIR= C:\eclipse_genomics\Git\cbioportal-sqlserver
set "FROM_DIR= C:\eclipse_genomics\Git\cbioportal-sqlserver\portal\target\"
set "TO_DIR= C:\apache-tomcat-7.0.63\webapps"
set "BIN_DIR= C:\apache-tomcat-7.0.63\bin"
set "LOG_FILE=C:\Users\jkim\AppData\Local\Temp\cbioportal.log"
set "WORK_DIR=C:\apache-tomcat-7.0.63\work\Catalina\localhost"

del %LOG_FILE%

cd %PROJ_DIR%

call mvn -e -DfailIfNoTests=false -Dtest=StudyMyBatisRepositoryTest -Dmaven.surefire.debug test -rf :persistence-mybatis-test

cd %BIN_DIR%