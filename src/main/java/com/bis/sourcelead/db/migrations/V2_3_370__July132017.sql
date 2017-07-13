SET SQL_SAFE_UPDATES=0;
UPDATE `sourcelead`.`email_scenario` SET `BODY`=' You need to update the timesheets of as soon as possible' WHERE `UNIQUE_SCENARIO_NAME`='Timesheet Scheduler' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES=1;