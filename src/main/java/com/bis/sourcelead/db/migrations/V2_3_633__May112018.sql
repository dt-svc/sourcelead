ALTER TABLE `VENDOR_ON_BOARD_TASKS` 
CHANGE COLUMN `ASSIGN_TO_USERS` `ASSIGN_TO_USERS` TEXT NULL DEFAULT NULL ;

SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='{{NotificationToManager.ShortListApplicantCount}} applicants got shortlisted for the {{NotificationToManager.RequirementName}}' WHERE `UNIQUE_SCENARIO_NAME`='Notification To Manager';
SET SQL_SAFE_UPDATES = 1;

truncate table CLASS_FIELD;
