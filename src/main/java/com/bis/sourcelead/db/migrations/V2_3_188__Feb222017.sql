ALTER TABLE `sourcelead`.`REQUIREMENT_INTERVIEW` 
CHANGE COLUMN `TIME_ZONE` `TIME_ZONE` BIGINT(20) NULL DEFAULT NULL ,
ADD INDEX `requirement_interview_ibfk_2_idx` (`TIME_ZONE` ASC);
ALTER TABLE `sourcelead`.`REQUIREMENT_INTERVIEW` 
ADD CONSTRAINT `requirement_interview_ibfk_2`
  FOREIGN KEY (`TIME_ZONE`)
  REFERENCES `sourcelead`.`timezone` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((select  id  from `ENTITY_TYPE` where  `ENTITY_NAME`='ApplicantInterview' and `ENTITY_TYPE`='Email'), 'Details', '{{ApplicantInterview.Details}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((select  id  from `ENTITY_TYPE` where  `ENTITY_NAME`='InterviewerInvitation' and `ENTITY_TYPE`='Email'), 'Details', '{{InterviewerInvitation.Details}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((select  id  from `ENTITY_TYPE` where  `ENTITY_NAME`='VendorsInterview' and `ENTITY_TYPE`='Email'), 'Details', '{{VendorsInterview.Details}}');

SET SQL_SAFE_UPDATES = 0;

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{ApplicantInterview.ApplicantFirstName}} {{ApplicantInterview.ApplicantLastName}},\n \n Interview at {{ApplicantInterview.LoginOrgName}} for the {{ApplicantInterview.RequirementTitle}} position.\n \n   Interview date : {{ApplicantInterview.InterviewDate}}\n   Interview Time : {{ApplicantInterview.InterviewTime}}\n   Interview Type : {{ApplicantInterview.InterviewType}}\n   Details : {{ApplicantInterview.Details}}\n \n  To confirm the Interview :\n   {{ApplicantInterview.AcceptURL}}    {{ApplicantInterview.RejectURL}}    {{ApplicantInterview.TentativeURL}}' WHERE `UNIQUE_SCENARIO_NAME`='Applicant Interview' and `ENTITY_TYPE`='Email';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{InterviewerInvitation.InterviewerFirstName}} {{InterviewerInvitation.InterviewerLastName}},\n \n  Interview scheduled on the {{InterviewerInvitation.InterviewDate}} for the requirement.\n \n  Requirement name : {{InterviewerInvitation.RequirementTitle}}\n  Interview date : {{InterviewerInvitation.InterviewDate}}\n  Details : {{InterviewerInvitation.Details}}\n  Interview Type : {{InterviewerInvitation.InterviewType}}\n  Applicants : {{InterviewerInvitation.ApplicantName}} \n \n  For clear details login into the system.' WHERE `UNIQUE_SCENARIO_NAME`='Interviewer Invitation' and `ENTITY_TYPE`='Email';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{VendorsInterview.VendorFirstName}} {{VendorsInterview.VendorLastName}},\n \n  Interview scheduled for the following applicants,\n \n  Requirement name : {{VendorsInterview.RequirementName}}\n \n  Interview date : {{VendorsInterview.InterviewDate}}\n \n  Details : {{VendorsInterview.Details}}\n \n  Interview Type : {{VendorsInterview.InterviewType}}\n \n  Applicant : {{VendorsInterview.ApplicantName}}\n \n ' WHERE `UNIQUE_SCENARIO_NAME`='Vendors Interview' and `ENTITY_TYPE`='Email';