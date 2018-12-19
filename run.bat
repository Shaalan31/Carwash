@echo off
for %%f in (*.class) do del %%f
for %%f in (*.java) do del %%f
for %%f in (*.osl) do (
oosiml.exe %%f 
IF %ERRORLEVEL% NEQ 0 (
  echo An error occured, stopping
  pause
  exit
)
)
javac -classpath acm.jar;oosimlib.jar;. *.java
IF %ERRORLEVEL% NEQ 0 (
  echo An error occured, stopping
  pause
  exit
)
java -cp acm.jar;oosimlib.jar;. Carwash
pause