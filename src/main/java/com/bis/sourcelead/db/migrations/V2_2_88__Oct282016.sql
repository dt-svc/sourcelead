
CALL AlterTableAddColumn ('REQUIREMENT_OFFERS','Notify_To_Vendor','VARCHAR(10) NULL AFTER `STATUS`');
CALL AlterTableAddColumn ('REQUIREMENT_OFFERS','Notify_To_Applicant','VARCHAR(10) NULL AFTER `Notify_To_Vendor`');
CALL AlterTableAddColumn ('REQUIREMENT_OFFERS','Rejected_Reason','TEXT NULL AFTER `Notify_To_Applicant`');




INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ShortListApplicants','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerFirstName', '{{ShortListApplicants.ManagerFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerLastName', '{{ShortListApplicants.ManagerLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ShortListApplicants.RequirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{ShortListApplicants.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{ShortListApplicants.LoginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'shortListApplicantCount', '{{ShortListApplicants.ShortListApplicantCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementExperience', '{{ShortListApplicants.RequirementExperience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementRate', '{{ShortListApplicants.RequirementRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementSkill', '{{ShortListApplicants.RequirementSkill}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginURL', '{{ShortListApplicants.LoginURL}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ShortList Applicants','Email', 'ShortList Applicants', 'Used to notify manager about Shorlisted Applicants.', '{{ShortListApplicants.ShortListApplicantCount}} shortlisted for the {{ShortListApplicants.RequirementRate}} ','org', 'Best Regards,\n{{ShortListApplicants.LoginUser}}.', 'Dear  {{ShortListApplicants.ManagerFirstName}} {{ShortListApplicants.ManagerLastName}},\n\n  {{ShortListApplicants.ShortListApplicantCount}} applicants shortlisted for the following requirement \n\n Requirement name :  {{ShortListApplicants.RequirementName}} \n Experience :  {{ShortListApplicants.RequirementExperience}} Years. \n Rate :   {{ShortListApplicants.RequirementRate}} \n Skills :   {{ShortListApplicants.RequirementSkill}} \n\n For brief details login into the system \n {{ShortListApplicants.LoginURL}} ','N', 'SLADMIN', 'slead', 'sleadIN');



CALL AlterTableAddColumn ('REQUIREMENT_SUBMISSION','VENDOR_LOCATION_ID','BIGINT NULL AFTER `ORG_LOCATION_ID`');
ALTER TABLE `REQUIREMENT_SUBMISSION` ADD FOREIGN KEY(VENDOR_LOCATION_ID) REFERENCES ORG_LOCATIONS(ID);

