$ErrorActionPreference = "Stop"

function Assert-LastExitCode([string]$stepName) {
    if ($LASTEXITCODE -ne 0) {
        throw "$stepName failed with exit code $LASTEXITCODE."
    }
}

$projectRoot = $PSScriptRoot
if ([string]::IsNullOrWhiteSpace($projectRoot)) {
    $projectRoot = (Get-Location).Path
}

$junitVersion = "1.10.2"
$junitDir = Join-Path $projectRoot "out\junit"
$junitJar = Join-Path $junitDir "junit-platform-console-standalone-$junitVersion.jar"
$runId = Get-Date -Format "yyyyMMddHHmmssfff"
$mainOut = Join-Path $projectRoot "out\test-main\$runId"
$testOut = Join-Path $projectRoot "out\test-tests\$runId"

New-Item -ItemType Directory -Force -Path $junitDir, $mainOut, $testOut | Out-Null

if (-not (Test-Path $junitJar)) {
    $url = "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$junitVersion/junit-platform-console-standalone-$junitVersion.jar"
    Write-Host "Downloading JUnit Platform Console $junitVersion..."
    Invoke-WebRequest -Uri $url -OutFile $junitJar
}

$srcRoot = Join-Path $projectRoot "src"
$testJavaRoot = Join-Path $projectRoot "src\test\java"

$mainSources = Get-ChildItem -Path $srcRoot -Recurse -Filter *.java |
        Where-Object {
            $_.Name -notlike "*Test.java" -and
            -not $_.FullName.StartsWith($testJavaRoot, [System.StringComparison]::OrdinalIgnoreCase)
        } |
        ForEach-Object { $_.FullName }

$testSources = @()
if (Test-Path $testJavaRoot) {
    $testSources = Get-ChildItem -Path $testJavaRoot -Recurse -Filter *.java | ForEach-Object { $_.FullName }
}

if (-not $testSources) {
    $legacyTestDir = Join-Path $projectRoot "test"
    if (Test-Path $legacyTestDir) {
        $testSources = Get-ChildItem -Path $legacyTestDir -Recurse -Filter *.java | ForEach-Object { $_.FullName }
    }
}

if (-not $testSources) {
    $testSources = Get-ChildItem -Path (Join-Path $projectRoot "src") -Recurse -Filter *Test.java | ForEach-Object { $_.FullName }
}

if (-not $mainSources) {
    throw "No Java source files found in src/."
}

if (-not $testSources) {
    throw "No Java test files found. Add tests under test/ or src/**/*Test.java."
}

Write-Host "Compiling source files..."
javac -encoding UTF-8 -d $mainOut $mainSources
Assert-LastExitCode "Main compilation"

Write-Host "Compiling tests..."
javac -encoding UTF-8 -cp "$junitJar;$mainOut" -d $testOut $testSources
Assert-LastExitCode "Test compilation"

Write-Host "Running tests..."
java -jar $junitJar execute --class-path "$mainOut;$testOut" --scan-class-path
Assert-LastExitCode "Test execution"
