cd /d %~dp0

@REM mvnw test failsafe:integration-test
@REM mvnw test failsafe:verify
mvnw failsafe:integration-test
