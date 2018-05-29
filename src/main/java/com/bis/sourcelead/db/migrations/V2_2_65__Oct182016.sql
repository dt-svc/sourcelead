UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\n', `BODY`='Dear {{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\n\nYou need to update the timesheets of {{TimesheetScheduler.WeekFrequency}}So update as soon as possible' WHERE `ID`='11';
ALTER TABLE `ORG_DOCUMENTS`
CHANGE COLUMN `FILE_TYPE` `FILE_TYPE` VARCHAR(250) NULL DEFAULT NULL ;