SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='New applicants received for the {{NotificationToClient.RequirementTitle}}.' WHERE `UNIQUE_SCENARIO_NAME`='Notification To Client' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES = 1;