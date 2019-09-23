#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('updatedFeedbackNotificationToRequirementOwner', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{updatedFeedbackNotificationToRequirementOwner.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewType', '{{updatedFeedbackNotificationToRequirementOwner.interviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'comments', '{{updatedFeedbackNotificationToRequirementOwner.comments}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'selectedYesOrNo', '{{updatedFeedbackNotificationToRequirementOwner.selectedYesOrNo}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'feedBackLink', '{{updatedFeedbackNotificationToRequirementOwner.feedBackLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerName', '{{updatedFeedbackNotificationToRequirementOwner.managerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{updatedFeedbackNotificationToRequirementOwner.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewerName', '{{updatedFeedbackNotificationToRequirementOwner.interviewerName}}');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('updated Feedback Notification To RequirementOwner','Email', 'updatedFeedbackNotificationToRequirementOwner', 'notify to RequirementOwner about updated applicant feedback', 'Feedback updated for the {{updatedFeedbackNotificationToRequirementOwner.applicantName}} to  {{updatedFeedbackNotificationToRequirementOwner.requirementName}}','org', 'Best Regards,\n{{updatedFeedbackNotificationToRequirementOwner.interviewerName}}.', 'Dear {{updatedFeedbackNotificationToRequirementOwner.managerName}},\nFeedback have been updated for the {{updatedFeedbackNotificationToRequirementOwner.applicantName}}.\n\nGo through the brief details of the interview\nRequirement Title :{{updatedFeedbackNotificationToRequirementOwner.requirementName}}\nInterview Type :{{updatedFeedbackNotificationToRequirementOwner.interviewType}}\nSelected :{{updatedFeedbackNotificationToRequirementOwner.selectedYesOrNo}}\nFor Complete details click on te following link\n{{updatedFeedbackNotificationToRequirementOwner.feedBackLink}}','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('updatedFeedbackNotificationToRequirementOwner', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('updated Feedback Notification To RequirementOwner', 'Notification', 'updatedFeedbackNotificationToRequirementOwner', 'notify to vendor about updated requirement', 'Feedback updated for the {{updatedFeedbackNotificationToRequirementOwner.applicantName}} to  {{updatedFeedbackNotificationToRequirementOwner.requirementName}}.', 'sourcelead', '', 'Feedback updated for the {{updatedFeedbackNotificationToRequirementOwner.applicantName}} to  {{updatedFeedbackNotificationToRequirementOwner.requirementName}}.','N','SLADMIN', 'com', 'in.com');



