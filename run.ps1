$ErrorActionPreference = "Stop"

New-Item -ItemType Directory -Force -Path "out" | Out-Null
$sources = Get-ChildItem -Path "src" -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }

Write-Host "Compiling $($sources.Count) Java source files..."
javac -d out $sources

Write-Host "Compilation succeeded! Launching Blood Donor Management System..."
java -cp out main.BloodDonorApplication
