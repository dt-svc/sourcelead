
CALL AlterTableAddColumn ('INTERVIEW_PROPOSE_TIME','INTERVIEW_TIME_STATUS','VARCHAR(20) NULL  AFTER `STATUS`');

#ProposeNewTime_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ProposeNewTime', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{ProposeNewTime.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewerName', '{{ProposeNewTime.interviewerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{ProposeNewTime.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewDate', '{{ProposeNewTime.interviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewTime', '{{ProposeNewTime.interviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{ProposeNewTime.requirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'proposeNewTimeList', '{{ProposeNewTime.proposeNewTimeList}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
('ProposeNewTime', 'Email', 'ProposeNewTime', 'Send mail to Applicant', ' Request for reschedule interview', 'sourcelead', 'Best Regards,\n{{ProposeNewTime.applicantName}}','Dear {{ProposeNewTime.interviewerName}},\n Please accept my sincere apologies for not being able to attend the interview that was scheduled on the {{ProposeNewTime.interviewDate}} & {{ProposeNewTime.interviewTime}} for the position for {{ProposeNewTime.requirementTitle}}. I would appreciate if you can reschedule the interview on following slots where i will be available .\n {{ProposeNewTime.proposeNewTimeList}} \n Looking forward for a positive response.
. ', 'N', 'SLADMIN', 'com','in.com');


#ProposeNewTime_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ProposeNewTime', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
('ProposeNewTime', 'Notification', 'ProposeNewTime', 'Applicant Propose New Time','org','Request for reschedule interview','N', 'SLADMIN', 'com', 'in.com');

