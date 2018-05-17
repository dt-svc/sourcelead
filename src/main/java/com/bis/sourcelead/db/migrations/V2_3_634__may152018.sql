SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='{{NotificationToManager.ShortListApplicantCount}} applicants got shortlisted for the {{NotificationToManager.RequirementName}}' WHERE `UNIQUE_SCENARIO_NAME`='Notification To Manager';
SET SQL_SAFE_UPDATES = 1;
