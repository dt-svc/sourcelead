
#ALTER TABLE `REQUIREMENT_OFFERS` 
#DROP COLUMN `Notify_To_Vendor`,
#DROP COLUMN `Notify_To_Applicant`;

DROP TABLE IF EXISTS `REQUIREMENT_OFFER_HISTORY`;
CREATE TABLE `REQUIREMENT_OFFER_HISTORY` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  OFFER_ID BIGINT DEFAULT NULL,
  SUBMISSION_ID BIGINT DEFAULT NULL,
  ORG_LOCATION_ID BIGINT DEFAULT NULL,
  OFFER_SOURCE_TYPE VARCHAR(250),
  NOTIFY_TO_APPLICANT VARCHAR(3),
  NOTIFY_TO_VENDOR VARCHAR(2),
  STATUS INT(10) DEFAULT NULL,
  CREATED_DATE DATETIME,
  CREATED_BY VARCHAR(10),
  UPDATED_DATE DATETIME,
  UPDATED_BY VARCHAR(10),
  
  PRIMARY KEY (`ID`), 
  FOREIGN KEY(OFFER_ID) REFERENCES `REQUIREMENT_OFFERS`(ID),
  FOREIGN KEY(SUBMISSION_ID) REFERENCES `REQUIREMENT_SUBMISSION`(ID),
  FOREIGN KEY(ORG_LOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID)
);

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Timesheet Scheduler', 'Notification', 'Timesheet Scheduler', 'Used for Timesheet Scheduler Notification', 'You need to update timesheet', '', '', '', 'N', 'SLADMIN', 'com', 'in.com');

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\nYou need to update the timesheets of as soon as possible' WHERE `ID`='76';
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Timesheet Scheduler Level1 Manager', 'Email', 'Timesheet Scheduler Level1 Manager', 'Used for Timesheet Scheduler Level1 Manager', 'Employees not update timesheet', 'sourcelead', 'Best Regards,\n', 'Dear {{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\n\n  Employees not update their  timesheet . The list of     & Frequencies are below\n{{TimesheetScheduler.WeekFrequency}}So update as soon as possible', 'N', 'SLADMIN', 'com', 'in.com');

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('InterviewrTemplate','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewerFirstName', '{{InterviewrTemplate.InterviewerFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewerLastName', '{{InterviewrTemplate.InterviewerLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{InterviewrTemplate.RequirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{InterviewrTemplate.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{InterviewrTemplate.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewDate', '{{InterviewrTemplate.InterviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewTime', '{{InterviewrTemplate.InterviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewType', '{{InterviewrTemplate.InterviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'FeedbackLink', '{{InterviewrTemplate.FeedbackLink}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Interviewer Template','Email', 'Interviewer Template', 'Used for Inviting Interviewer to Interview', 'Give Feedback for the {{InterviewrTemplate.ApplicantName}} .','org', 'Best Regards,\n {{InterviewrTemplate.LoginUser}}.', 'Dear {{InterviewrTemplate.InterviewerFirstName}} {{InterviewrTemplate.InterviewerLastName}},\n\n Give the feedback for the {{InterviewrTemplate.ApplicantName}} of the {{InterviewrTemplate.RequirementTitle}} . \n\n Interview date : {{InterviewrTemplate.InterviewDate}} \n Interview time : {{InterviewrTemplate.InterviewTime}} \n Interview Type : {{InterviewrTemplate.InterviewType}} \n Click on the following link to give feedback. \n\n {{InterviewrTemplate.FeedbackLink}} ','N', 'SLADMIN', 'slead', 'sleadIN');



DROP TABLE IF EXISTS `INTERVIEW_FEEDBACK_TEMPLATE`;
CREATE TABLE `INTERVIEW_FEEDBACK_TEMPLATE` (
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
  FOREIGN KEY(REQUIREMENT_INTERVIEW_ID) REFERENCES `REQUIREMENT_INTERVIEW`(ID),
  FOREIGN KEY(SUBMISSION_TEMPLATE_ID) REFERENCES `SUBMISSION_TEMPLATE`(ID)
); 

