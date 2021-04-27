REM startServerWin.bat
REM
REM SET THE CLASSPATH DYNAMICALLY
REM
REM Call the CPAPPEND.BAT program to build the JARS env variable,
REM then append that variable to the CLASSPATH.
REM To be clear, CPAPPEND.BAT builds the JARS variable.
REM (Note: Borrowed this approach from the 
REM Jakarta Tomcat people - thanks!)
REM

set JARS=
set CLASSPATH=
@ECHO OFF
SET CLASSPATH=""
for %%i in (..\lib\*.jar) do call cpappend.bat %%i
set CLASSPATH=%JARS%;..\classes\shorten-1.0-SNAPSHOT.jar

for %%i in (..\classes\*.jar) do call cpappend.bat %%i
set CLASSPATH=%JARS%
ECHO %CLASSPATH% 
java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs/heap_dump.hprof -Xmx1024m -cp "%CLASSPATH%" com.canhlabs.ShortenApp --spring.profiles.active=dev --spring.config.location=../config/application.properties

PAUSE
