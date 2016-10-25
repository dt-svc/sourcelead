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

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Applicant Interview','Email', 'Applicant Interview Invitation', 'Used for Inviting Applicant for Interview', 'Interview at {{ApplicantInterview.LoginOrgName}} for the {{ApplicantInterview.RequirementTitle}} position.','org', 'Best Regards,\n{{ApplicantInterview.LoginUser}}.', 'Dear {{ApplicantInterview.ApplicantFirstName}} {{ApplicantInterview.ApplicantLastName}},\n\nInterview at {{ApplicantInterview.LoginOrgName}} for the {{ApplicantInterview.RequirementTitle}} position.\n\n  Interview date : {{ApplicantInterview.InterviewDate}}\n  Interview Time : {{ApplicantInterview.InterviewTime}}\n  Interview Type : {{ApplicantInterview.InterviewType}}\n  Location : {{ApplicantInterview.Location}}\n\n To confirm the Interview :\n  {{ApplicantInterview.AcceptURL}}    {{ApplicantInterview.RejectURL}}    {{ApplicantInterview.TentativeURL}}','N', 'SLADMIN', 'slead', 'sleadIN');


ALTER TABLE `sourcelead`.`REQUIREMENT`
CHANGE COLUMN `DESCRIPTION` `DESCRIPTION` TEXT NULL DEFAULT NULL ;

