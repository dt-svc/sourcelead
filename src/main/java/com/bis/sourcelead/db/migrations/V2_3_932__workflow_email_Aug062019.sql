
#Email for Questioinnaire template
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToCandidateWorkflowSatgeQuestionnaire', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) 
	VALUES('MailToCandidateWorkflowSatgeQuestionnaire', 'Email', 'MailToCandidateWorkflowSatgeQuestionnaire', 'The mail need to be forward to candidate when the stage is associated with questionnaire/feedback expecting by manager/admin.', 'New questionnaires assigned to you for [JOB_TITLE]','org','Best Regards,\n [Login_User]\n [Login_Org]', 'Dear [ASSIGNED_USER],
\nThis is to notify that you  have been assigned to submit the questionnaires for the [STAGE_NAME]  here. [QUESTIONNNAIRE_LINK]\n\nFor more details please login to SourceLead\n\n[Login_Link]','N', 'SLADMIN', 'slead', 'sleadIN',1,'Y');


INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'assignedUser', '[ASSIGNED_USER]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'jobTitle', '[JOB_TITLE]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[LOGIN_USER]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[LOGIN_ORG]');
 INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginLink', '[LOGIN_LINK]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'stageName', '[STAGE_NAME]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'questionnaireLink', '[QUESTIONNNAIRE_LINK]'); 

#Notification for Questioinnaire template
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToCandidateWorkflowSatgeQuestionnaire', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES

('MailToCandidateWorkflowSatgeQuestionnaire', 'Notification', 'MailToCandidateWorkflowSatgeQuestionnaire', 'This notification is to Assign user Notify to take questionnaire assigned by manager/admin..', 'New questionnaires assigned', 'org', '', 'New questionnaires assigned','N','SLADMIN', 'slead', 'sleadIN',1,'Y');
	