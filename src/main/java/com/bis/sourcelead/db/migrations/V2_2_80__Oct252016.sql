CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_INTERVIEW','INTERVIEW_STATUS','TEXT NULL AFTER `LOCATION`');

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApplicantInterview','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantFirstName', '{{ApplicantInterview.ApplicantFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantLastName', '{{ApplicantInterview.ApplicantLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementTitle', '{{ApplicantInterview.RequirementTitle}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{ApplicantInterview.LoginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{ApplicantInterview.LoginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewDate', '{{ApplicantInterview.InterviewDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewTime', '{{ApplicantInterview.InterviewTime}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewType', '{{ApplicantInterview.InterviewType}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'Location', '{{ApplicantInterview.Location}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'acceptURL', '{{ApplicantInterview.AcceptURL}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rejectURL', '{{ApplicantInterview.RejectURL}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'tentativeURL', '{{ApplicantInterview.TentativeURL}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Applicant Interview','Email', 'Applicant Interview', 'Used for Inviting Applicant for Interview', 'Interview at {{ApplicantInterview.LoginOrgName}} for the {{ApplicantInterview.RequirementTitle}} position.','org', 'Best Regards,\n{{ApplicantInterview.LoginUser}}.', 'Dear {{ApplicantInterview.ApplicantFirstName}} {{ApplicantInterview.ApplicantLastName}},\n\nInterview at {{ApplicantInterview.LoginOrgName}} for the {{ApplicantInterview.RequirementTitle}} position.\n\n  Interview date : {{ApplicantInterview.InterviewDate}}\n  Interview Time : {{ApplicantInterview.InterviewTime}}\n  Interview Type : {{ApplicantInterview.InterviewType}}\n  Location : {{ApplicantInterview.Location}}\n\n To confirm the Interview :\n  {{ApplicantInterview.AcceptURL}}    {{ApplicantInterview.RejectURL}}    {{ApplicantInterview.TentativeURL}}','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('InterviewerInvitation','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewerFirstName', '{{InterviewerInvitation.InterviewerFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewerLastName', '{{InterviewerInvitation.InterviewerLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementTitle', '{{InterviewerInvitation.RequirementTitle}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'applicantName', '{{InterviewerInvitation.ApplicantName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{InterviewerInvitation.LoginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewDate', '{{InterviewerInvitation.InterviewDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'interviewType', '{{InterviewerInvitation.InterviewType}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'Location', '{{InterviewerInvitation.Location}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Interviewer Invitation','Email', 'Interviewer Invitation', 'Used for Inviting Interviewer to Interview', 'Interview scheduled for the {{InterviewerInvitation.RequirementTitle}} requirement.','org', 'Best Regards,\n{{InterviewerInvitation.LoginUser}}.', 'Dear {{InterviewerInvitation.InterviewerFirstName}} {{InterviewerInvitation.InterviewerLastName}},\n\n Interview scheduled on the {{InterviewerInvitation.InterviewDate}} for the requirement.\n\n Requirement name : {{InterviewerInvitation.RequirementTitle}}\n Interview date : {{InterviewerInvitation.InterviewDate}}\n Location : {{InterviewerInvitation.Location}}\n Interview Type : {{InterviewerInvitation.InterviewType}}\n Applicants : {{InterviewerInvitation.ApplicantName}} \n\n For clear details login into the system.','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InterviewerInvitation', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Interviewer Invitation', 'Notification', 'Interviewer Invitation', 'Used for Inviting Interviewer to Interview', 'Interview scheduled for the {{InterviewerInvitation.RequirementTitle}} requirement', 'sourcelead', '', 'Interview scheduled for the {{InterviewerInvitation.RequirementTitle}} requirement','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

ALTER TABLE `sourcelead`.`REQUIREMENT`
CHANGE COLUMN `DESCRIPTION` `DESCRIPTION` TEXT NULL DEFAULT NULL ;



