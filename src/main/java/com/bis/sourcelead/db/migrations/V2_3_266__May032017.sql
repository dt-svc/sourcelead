set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='New requirement released by {{ReleaseMechanism.FirstName}}'WHERE `UNIQUE_SCENARIO_NAME`='Release Mechanism' and `ENTITY_TYPE`='Notification';
set SQL_SAFE_UPDATES=1;