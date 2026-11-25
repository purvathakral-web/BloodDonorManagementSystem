@echo off
if not exist out mkdir out
dir /s /b src\*.java > sources.txt
echo Compiling Blood Donor Management System...
javac -d out @sources.txt
if errorlevel 1 (
    del sources.txt
    echo Compilation failed!
    pause
    exit /b 1
)
del sources.txt
echo Running Blood Donor Management System...
java -cp out main.BloodDonorApplication
