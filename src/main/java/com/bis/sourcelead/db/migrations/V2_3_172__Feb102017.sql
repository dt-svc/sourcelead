CALL sourcelead.AlterTableAddColumn ('sourcelead','PROJECT_TASKS','BILLABLE_TYPE','VARCHAR(100) DEFAULT NULL AFTER `DESCRIPTION`');

SET SQL_SAFE_UPDATES = 0;
UPDATE `sourcelead`.`PROJECT_TASKS` SET `BILLABLE_TYPE`='Billable';

UPDATE `sourcelead`.`PROJECT_TASKS` SET `BILLABLE_TYPE`='Billable' WHERE `TASK_NAME`='Holiday';
UPDATE `sourcelead`.`PROJECT_TASKS` SET `BILLABLE_TYPE`='Non-Billable' WHERE `TASK_NAME`='Vacation Leave';
UPDATE `sourcelead`.`PROJECT_TASKS` SET `BILLABLE_TYPE`='Non-Billable' WHERE `TASK_NAME`='Sick Leave';
UPDATE `sourcelead`.`PROJECT_TASKS` SET `BILLABLE_TYPE`='Non-Billable' WHERE `TASK_NAME`='Casual Leave';
UPDATE `sourcelead`.`PROJECT_TASKS` SET `BILLABLE_TYPE`='Billable' WHERE `TASK_NAME`='Compensatory Leave';
UPDATE `sourcelead`.`PROJECT_TASKS` SET `BILLABLE_TYPE`='Billable' WHERE `TASK_NAME`='Work From Home';
SET SQL_SAFE_UPDATES = 1;

