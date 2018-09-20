

#sarath   

#ManagerOfRequirement_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ManagerOfRequirement', 'Email');

#CLASS_FIELD

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'AssignedUser', '{{ManagerOfRequirement.AssignedUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ManagerName', '{{ManagerOfRequirement.ManagerName}}');  
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantName', '{{ManagerOfRequirement.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementName', '{{ManagerOfRequirement.RequirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementLocation', '{{ManagerOfRequirement.RequirementLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantRate', '{{ManagerOfRequirement.ApplicantRate}}');

 
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ApplicantAcceptanceByManagerOfRequirement', 'Email', 'ApplicantAcceptanceByManagerOfRequirement', 'Used for Applicant Acceptance By Manager Of Requirement', '{{ManagerOfRequirement.ManagerName}} have accepted the {{ManagerOfRequirement.ApplicantName}}', 'sourcelead', 'Best Regards {{ManagerOfRequirement.ManagerName}}', 'Dear{{ManagerOfRequirement.AssignedUser}},\n�{{ManagerOfRequirement.ManagerName}} have accepted the {{ManagerOfRequirement.ApplicantName}} for {{ManagerOfRequirement.RequirementName}} The brief details are below \n Requirement Location :{{ManagerOfRequirement.RequirementLocation}} \n Requirement Rate :{{ManagerOfRequirement.ApplicantRate}}', 'N', 'SLADMIN', 'com', 'in.com');




#ManagerOfRequirement_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ManagerOfRequirement', 'Notification');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ManagerName', '{{ManagerOfRequirement.ManagerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantName', '{{ManagerOfRequirement.ApplicantName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ApplicantAcceptanceByManagerOfRequirement', 'Notification', 'ApplicantAcceptanceByManagerOfRequirement', 'Used to notify Applicant Acceptance By Manager Of Requirement','org','{{ManagerOfRequirement.ManagerName}} have accepted the {{ManagerOfRequirement.ApplicantName}}','N', 'SLADMIN', 'com', 'in.com');





#sarath   

#ManagerOfRequirement_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AssignedUser', 'Email');

#CLASS_FIELD

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementName', '{{AssignedUser.RequirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApplicantName', '{{AssignedUser.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementLocation', '{{AssignedUser.RequirementLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'EmploymentType', '{{AssignedUser.EmploymentType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Rate', '{{AssignedUser.Rate}}');  
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'AssignedUser', '{{AssignedUser.AssignedUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ManagerName', '{{AssignedUser.ManagerName}}');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager', 'Email', 'ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager', 'Used for Applicant Will Receive Mail About The Acceptance Of Requirement By Manager', 'You have submitted for {{AssignedUser.RequirementName}}', 'sourcelead', 'Best Regards {{AssignedUser.ManagerName}}', 'Dear{{AssignedUser.ApplicantName}},\nYou have submitted for {{AssignedUser.RequirementName}}.\n Requirement Location :{{AssignedUser.RequirementLocation}} \n  Employment Type :{{AssignedUser.RequirementLocation}} \n Rate :{{AssignedUser.Rate}}', 'N', 'SLADMIN', 'com', 'in.com');


CALL AlterTableAddColumn ('REQUIREMENT_SUBMISSION','SUBMISSION_REQUEST_COMMENT','VARCHAR(50) NULL AFTER `SUBMISSION_RATE_DOC_FILE`');
CALL AlterTableAddColumn ('REQUIREMENT_SUBMISSION','SUBMISSION_REQUEST_DOC_FILE','MEDIUMBLOB NULL AFTER `SUBMISSION_REQUEST_COMMENT`');
CALL AlterTableAddColumn ('REQUIREMENT_SUBMISSION','SUBMISSION_REQUEST_FILE_CONTENT_TYPE','VARCHAR(50) NULL AFTER `SUBMISSION_REQUEST_DOC_FILE`');
CALL AlterTableAddColumn ('REQUIREMENT_SUBMISSION','SUBMISSION_REQUEST_FILE_NAME','TEXT NULL AFTER `SUBMISSION_REQUEST_FILE_CONTENT_TYPE`');

CALL AlterTableAddColumn ('REQUIREMENT_SUBMISSION','SUBMISSION_REQUEST_USER','VARCHAR(50) NULL AFTER `SUBMISSION_REQUEST_FILE_NAME`');

CALL AlterTableAddColumn ('REQUIREMENT_SUBMISSION_HISTORY','INTERVIEWER_ID','BIGINT NULL AFTER `REQUIREMENT_SUBMISSION_ID`');
ALTER TABLE REQUIREMENT_SUBMISSION_HISTORY ADD FOREIGN KEY (INTERVIEWER_ID) REFERENCES REQUIREMENT_INTERVIEWER(id);

ALTER TABLE `REQUIREMENT_SUBMISSION_HISTORY`
CHANGE COLUMN `CREATED_DATE` `CREATED_DATE` DATETIME NULL DEFAULT NULL ;

