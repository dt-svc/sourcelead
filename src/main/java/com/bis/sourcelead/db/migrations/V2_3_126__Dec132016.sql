
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`ID`, `SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('76', 'Timesheet Scheduler', 'Notification', 'Timesheet Scheduler', 'Used for Timesheet Scheduler Notification', 'You need to update timesheet', '', '', '', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\nYou need to update the timesheets of as soon as possible' WHERE `ID`='76';
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`ID`, `SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('75', 'Timesheet Scheduler Level1 Manager', 'Email', 'Timesheet Scheduler Level1 Manager', 'Used for Timesheet Scheduler Level1 Manager', 'Employees not update timesheet', 'sourcelead', 'Best Regards,\nSourceLead.', 'Dear {{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\n\n  Employees not update their  timesheet . The list of     & Frequencies are below\n{{TimesheetScheduler.WeekFrequency}}So update as soon as possible', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('InterviewrTemplate','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewerFirstName', '{{InterviewrTemplate.InterviewerFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewerLastName', '{{InterviewrTemplate.InterviewerLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementTitle', '{{InterviewrTemplate.RequirementTitle}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantName', '{{InterviewrTemplate.ApplicantName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{InterviewrTemplate.LoginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewDate', '{{InterviewrTemplate.InterviewDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewTime', '{{InterviewrTemplate.InterviewTime}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewType', '{{InterviewrTemplate.InterviewType}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'FeedbackLink', '{{InterviewrTemplate.FeedbackLink}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Interviewer Template','Email', 'Interviewer Template', 'Used for Inviting Interviewer to Interview', 'Give Feedback for the {{InterviewrTemplate.ApplicantName}} .','org', 'Best Regards,\n {{InterviewrTemplate.LoginUser}}.', 'Dear {{InterviewrTemplate.InterviewerFirstName}} {{InterviewrTemplate.InterviewerLastName}},\n\n Give the feedback for the {{InterviewrTemplate.ApplicantName}} of the {{InterviewrTemplate.RequirementTitle}} . \n\n Interview date : {{InterviewrTemplate.InterviewDate}} \n Interview time : {{InterviewrTemplate.InterviewTime}} \n Interview Type : {{InterviewrTemplate.InterviewType}} \n Click on the following link to give feedback. \n\n {{InterviewrTemplate.FeedbackLink}} ','N', 'SLADMIN', 'slead', 'sleadIN');



DROP TABLE IF EXISTS `sourcelead`.`INTERVIEWFEEDBACK_TEMPLATE`;
CREATE TABLE `sourcelead`.`INTERVIEWFEEDBACK_TEMPLATE` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  REQUIREMENT_INTERVIEW_ID BIGINT DEFAULT NULL,
  SUBMISSION_TEMPLATE_ID BIGINT DEFAULT NULL,
  INTERVIEW_TEMPLATE_JSON_STRING TEXT DEFAULT NULL,
  STATUS VARCHAR(20) DEFAULT NULL,
  COMMENT TEXT DEFAULT NULL,
  INTERVIEW_STATUS VARCHAR(10) DEFAULT NULL,
  CREATED_DATE DATE,
  CREATED_BY VARCHAR(10),
  UPDATED_DATE DATE,
  UPDATED_BY VARCHAR(10),
  PRIMARY KEY (`ID`), 
  FOREIGN KEY(REQUIREMENT_INTERVIEW_ID) REFERENCES `sourcelead`.`REQUIREMENT_INTERVIEW`(ID),
  FOREIGN KEY(SUBMISSION_TEMPLATE_ID) REFERENCES `sourcelead`.`SUBMISSION_TEMPLATE`(ID)
); 

