CALL AlterTableAddColumn ('REQUIREMENT_INTERVIEW','INTERVIEW_STATUS','TEXT NULL AFTER `LOCATION`');

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApplicantInterview','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantFirstName', '{{ApplicantInterview.ApplicantFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantLastName', '{{ApplicantInterview.ApplicantLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{ApplicantInterview.RequirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{ApplicantInterview.LoginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{ApplicantInterview.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewDate', '{{ApplicantInterview.InterviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewTime', '{{ApplicantInterview.InterviewTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewType', '{{ApplicantInterview.InterviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Location', '{{ApplicantInterview.Location}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'acceptURL', '{{ApplicantInterview.AcceptURL}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectURL', '{{ApplicantInterview.RejectURL}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'tentativeURL', '{{ApplicantInterview.TentativeURL}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Applicant Interview','Email', 'Applicant Interview', 'Used for Inviting Applicant for Interview', 'Interview at {{ApplicantInterview.LoginOrgName}} for the {{ApplicantInterview.RequirementTitle}} position.','org', 'Best Regards,\n{{ApplicantInterview.LoginUser}}.', 'Dear {{ApplicantInterview.ApplicantFirstName}} {{ApplicantInterview.ApplicantLastName}},\n\nInterview at {{ApplicantInterview.LoginOrgName}} for the {{ApplicantInterview.RequirementTitle}} position.\n\n  Interview date : {{ApplicantInterview.InterviewDate}}\n  Interview Time : {{ApplicantInterview.InterviewTime}}\n  Interview Type : {{ApplicantInterview.InterviewType}}\n  Location : {{ApplicantInterview.Location}}\n\n To confirm the Interview :\n  {{ApplicantInterview.AcceptURL}}    {{ApplicantInterview.RejectURL}}    {{ApplicantInterview.TentativeURL}}','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('InterviewerInvitation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewerFirstName', '{{InterviewerInvitation.InterviewerFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewerLastName', '{{InterviewerInvitation.InterviewerLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{InterviewerInvitation.RequirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{InterviewerInvitation.ApplicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{InterviewerInvitation.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewDate', '{{InterviewerInvitation.InterviewDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewType', '{{InterviewerInvitation.InterviewType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Location', '{{InterviewerInvitation.Location}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Interviewer Invitation','Email', 'Interviewer Invitation', 'Used for Inviting Interviewer to Interview', 'Interview scheduled for the {{InterviewerInvitation.RequirementTitle}} requirement.','org', 'Best Regards,\n{{InterviewerInvitation.LoginUser}}.', 'Dear {{InterviewerInvitation.InterviewerFirstName}} {{InterviewerInvitation.InterviewerLastName}},\n\n Interview scheduled on the {{InterviewerInvitation.InterviewDate}} for the requirement.\n\n Requirement name : {{InterviewerInvitation.RequirementTitle}}\n Interview date : {{InterviewerInvitation.InterviewDate}}\n Location : {{InterviewerInvitation.Location}}\n Interview Type : {{InterviewerInvitation.InterviewType}}\n Applicants : {{InterviewerInvitation.ApplicantName}} \n\n For clear details login into the system.','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InterviewerInvitation', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Interviewer Invitation', 'Notification', 'Interviewer Invitation', 'Used for Inviting Interviewer to Interview', 'Interview scheduled for the {{InterviewerInvitation.RequirementTitle}} requirement', 'sourcelead', '', 'Interview scheduled for the {{InterviewerInvitation.RequirementTitle}} requirement','N','SLADMIN', 'com', 'in.com');

ALTER TABLE `REQUIREMENT`
CHANGE COLUMN `DESCRIPTION` `DESCRIPTION` TEXT NULL DEFAULT NULL ;



