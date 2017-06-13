
CALL sourcelead.AlterTableAddColumn ('sourcelead','INTERVIEW_PROPOSE_TIME','INTERVIEW_TIME_STATUS','VARCHAR(20) NULL  AFTER `STATUS`');

#ProposeNewTime_Email
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ProposeNewTime', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{ProposeNewTime.loginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewerName', '{{ProposeNewTime.interviewerName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantName', '{{ProposeNewTime.applicantName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewDate', '{{ProposeNewTime.interviewDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewTime', '{{ProposeNewTime.interviewTime}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementTitle', '{{ProposeNewTime.requirementTitle}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'proposeNewTimeList', '{{ProposeNewTime.proposeNewTimeList}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
('ProposeNewTime', 'Email', 'ProposeNewTime', 'Send mail to Applicant', ' Request for reschedule interview', 'sourcelead', 'Best Regards,\n{{ProposeNewTime.applicantName}}','Dear {{ProposeNewTime.interviewerName}},\n Please accept my sincere apologies for not being able to attend the interview that was scheduled on the {{ProposeNewTime.interviewDate}} & {{ProposeNewTime.interviewTime}} for the position for {{ProposeNewTime.requirementTitle}}. I would appreciate if you can reschedule the interview on following slots where i will be available .\n {{ProposeNewTime.proposeNewTimeList}} \n Looking forward for a positive response.
. ', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');


#ProposeNewTime_Notification
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ProposeNewTime', 'Notification');


#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
('ProposeNewTime', 'Notification', 'ProposeNewTime', 'Applicant Propose New Time','org','Request for reschedule interview','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

