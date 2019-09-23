set sql_safe_updates=0;
UPDATE `EMAIL_SCENARIO` SET `DEFAULT_TEMPLATE` = 'Y' WHERE `UNIQUE_SCENARIO_NAME` = 'notificationToImmigrationBeneficiary';
set sql_safe_updates=1;