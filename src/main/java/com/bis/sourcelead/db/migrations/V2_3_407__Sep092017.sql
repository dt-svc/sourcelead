 #Rescheduled Mail

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RescheduledMail','Email');


INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewerFirstName', '{{RescheduledMail.interviewerFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewerLastName', '{{RescheduledMail.interviewerLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'jobTitle', '{{RescheduledMail.jobTitle}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantName', '{{RescheduledMail.applicantName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewDate', '{{RescheduledMail.interviewDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewTime', '{{RescheduledMail.interviewTime}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewType', '{{RescheduledMail.interviewType}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{RescheduledMail.loginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginorg', '{{RescheduledMail.loginorg}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'comment', '{{RescheduledMail.comment}}');


       
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                               ('Rescheduled Interview', 'Email', 'entity_type_rescheduled_email', 'The mail should forward to interviewer when interview got rescheduled', 'Interview got rescheduled for the {{RescheduledMail.jobTitle}} Position','sourcelead', 'Best Regards \n{{RescheduledMail.loginUser}}\n{{RescheduledMail.loginorg}}','Dear {{RescheduledMail.interviewerFirstName}},\nThis is to notify interview got rescheduled for the {{RescheduledMail.applicantName}} for {{RescheduledMail.jobTitle}} Position\nDate: {{RescheduledMail.interviewDate}}\nTime: {{RescheduledMail.interviewTime}}\nType: {{RescheduledMail.interviewType}}' ,'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

       
       
       
       
       
