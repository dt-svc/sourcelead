UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\nSourceLead.', `BODY`='Dear {{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\n\nYou need to update the timesheets of {{TimesheetScheduler.WeekFrequency}}So update as soon as possible' WHERE `ID`='11';
ALTER TABLE `sourcelead`.`ORG_DOCUMENTS`
CHANGE COLUMN `FILE_TYPE` `FILE_TYPE` VARCHAR(250) NULL DEFAULT NULL ;