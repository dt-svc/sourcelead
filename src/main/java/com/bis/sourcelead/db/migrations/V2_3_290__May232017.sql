set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'New Rate request for the {{ClientRateNegotiation.candidateName}} to {{ClientRateNegotiation.requirementName}}' WHERE `UNIQUE_SCENARIO_NAME`='ClientRateNegotiation' and `ENTITY_TYPE`='Notification';

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`= 'New applicants received for the {{NotificationToClient.RequirementTitle}} from the {{NotificationToClient.VendorName}}' WHERE `UNIQUE_SCENARIO_NAME`='Notification To Client' and `ENTITY_TYPE`='Notification';