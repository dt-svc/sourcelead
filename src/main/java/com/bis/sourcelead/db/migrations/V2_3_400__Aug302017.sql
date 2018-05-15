#(mahesha)Update migration for vendor submit applicant to client and approver job rejected
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorOrgName', '{{NotificationToClient.vendorOrgName}}');

SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{RejectedMailAboutTheRequirement.requirementName}} Job was rejected by {{RejectedMailAboutTheRequirement.approverName}}',`BODY`='{{RejectedMailAboutTheRequirement.requirementName}} Job was rejected by {{RejectedMailAboutTheRequirement.approverName}}' WHERE `UNIQUE_SCENARIO_NAME`='Rejected Mail About The Requirement' and `ENTITY_TYPE`='Notification';
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`=' Applicants received for the job {{NotificationToClient.RequirementTitle}} from {{NotificationToClient.vendorOrgName}} by user {{NotificationToClient.VendorName}}' WHERE `UNIQUE_SCENARIO_NAME`='Notification To Client' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES = 1;