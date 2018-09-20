INSERT INTO `TABS` (`ID`, `TAB_NAME`, `TAB_ORDER`) VALUES ('15', 'Profile', '10');

INSERT INTO `SOURCELEAD_ACTIONS` (`ID`, `ACTION_NAME`, `ACTION_ROLE`, `TAB_ID`) VALUES ('151', 'Crowd Sourcing Profile', 'ROLE_CROWDSOURCING_PROFILE', '15');
INSERT INTO `SOURCELEAD_ACTIONS` (`ID`, `ACTION_NAME`, `ACTION_ROLE`, `TAB_ID`) VALUES ('152', 'Crowd Sourcing Requirements', 'ROLE_CROWDSOURCING_REQUIREMENTS', '8');
INSERT INTO `SOURCELEAD_ACTIONS` (`ID`, `ACTION_NAME`, `ACTION_ROLE`, `TAB_ID`) VALUES ('153', 'Crowd Sourcing Candidates', 'ROLE_CROWDSOURCING_CANDIDATES', '10');


INSERT INTO `MODULES` (`ID`, `MODULE_NAME`, `ORG_ID`, `IS_ENROLED`) VALUES ('5', 'CROWD SOURCING', '1', 'Y');

INSERT INTO `MODULE_ACTIONS` (`ID`, `MODULE_ID`, `ACTION_ID`) VALUES ('273', '5', '151');
INSERT INTO `MODULE_ACTIONS` (`ID`, `MODULE_ID`, `ACTION_ID`) VALUES ('274', '5', '152');
INSERT INTO `MODULE_ACTIONS` (`ID`, `MODULE_ID`, `ACTION_ID`) VALUES ('275', '5', '153');



INSERT INTO `ROLES` (`ROLE_NAME`, `DISPLAY_NAME`, `ORG_LOCATION_ID`, `ORG_ID`, `ACTION_ID`, `DEFAULT_ROLE`, `STATUS`) VALUES ('ROLE_CROWDSOURCING_PROFESSIONAL', 'Professional', '1', '1', '151,152', '1', '1');
INSERT INTO `ROLES` (`ROLE_NAME`, `DISPLAY_NAME`, `ORG_LOCATION_ID`, `ORG_ID`, `ACTION_ID`, `DEFAULT_ROLE`, `STATUS`) VALUES ('ROLE_CROWDSOURCING_RECRUITER', 'Independent Recruiter', '1', '1', '152,153', '1', '1');



CALL AlterTableAddColumn ('USER_ROLE','ORG_ID','BIGINT NULL');
ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_ORG_ID_ibfk_2 FOREIGN KEY (ORG_ID) REFERENCES ORG(id);

ALTER TABLE `USER` CHANGE COLUMN `USER_TYPE` `USER_TYPE` VARCHAR(50) NULL DEFAULT NULL ;

CALL AlterTableAddColumn ('CANDIDATE','ORG_ID','BIGINT NULL');
ALTER TABLE CANDIDATE ADD CONSTRAINT CANDIDATE_ORG_ID_ibfk_2 FOREIGN KEY (ORG_ID) REFERENCES ORG(id);



INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ProfessionalInvitation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'professionalName', '{{ProfessionalInvitation.professionalName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{ProfessionalInvitation.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resetLink', '{{ProfessionalInvitation.resetLink}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('Professional Invitation','Email', 'Professional Invitation', 'Welcome to SourceLead ', 'Welcome to SourceLead','org', 'Best Regards,\n', 'Welcome !!!\n\n Dear Professional {{ProfessionalInvitation.professionalName}}\n\n It is great to see that you have started accessing \n User Name : {{ProfessionalInvitation.loginUserName}}\n Your Sourcelead URL : {{ProfessionalInvitation.resetLink}}\n  I hope you will  have a wonderful time with  If you have any questions or comments, feel free to reach out to us at info@com','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
									

		
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('Individual Recruiter Invitation','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'recruiterName', '{{RecruiterInvitation.recruiterName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{RecruiterInvitation.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resetLink', '{{RecruiterInvitation.resetLink}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('Individual Recruiter Invitation','Email', 'Individual Recruiter Invitation', 'Welcome to SourceLead ', 'Welcome to SourceLead','org', 'Best Regards,\n', 'Welcome !!!\n\n Dear Recruiter {{RecruiterInvitation.recruiterName}}\n\n It is great to see that you have started accessing \n User Name : {{RecruiterInvitation.loginUserName}}\n Your Sourcelead URL : {{RecruiterInvitation.resetLink}}\n  I hope you will  have a wonderful time with  If you have any questions or comments, feel free to reach out to us at info@com','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
									

CALL AlterTableAddColumn ('REQUIREMENT','POSTED_ORG_NAME','VARCHAR(250) DEFAULT NULL');

CALL AlterTableAddColumn ('USER','COUNTRY','VARCHAR(100) DEFAULT NULL');

CALL AlterTableAddColumn ('REQUIREMENT','APPLIED_PROFESSIONALS','TEXT DEFAULT NULL');

CALL AlterTableAddColumn ('REQUIREMENT','REQUIREMENT_UUID','VARCHAR(20) NULL AFTER `IS_CROWD_SOURCING`');



INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('Applied To Requirement','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'professionalName', '{{AppliedRequirement.professionalName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employerName', '{{AppliedRequirement.employerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{AppliedRequirement.requirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'experience', '{{AppliedRequirement.experience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'location', '{{AppliedRequirement.location}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'professionalEmail', '{{AppliedRequirement.professionalEmail}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('Applied To Requirement','Email', 'Applied To Requirement', 'Confirmation - Requirement Application ', 'Confirmation - Requirement Application','org', 'Best Regards,\n', 'Dear Professional {{AppliedRequirement.professionalName}}\n\n\n you have applied to below mentioned requirement.\n\n Requirement Title : {{AppliedRequirement.requirementTitle}}\n\n Employer Name : {{AppliedRequirement.employerName}}\n\n Experience : {{AppliedRequirement.experience}}\n Location : {{AppliedRequirement.location}}\n\n  If the recruiters at {{AppliedRequirement.employerName}} find your profile suitable, they will email you at {{AppliedRequirement.professionalEmail}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
									


											
DROP TABLE IF EXISTS `REQUIREMENT_REFER`;
CREATE TABLE `REQUIREMENT_REFER` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  REFER_FROM_USER_ID BIGINT,
  REFER_TO_USER_ID BIGINT,
  REFER_FROM_EMAIL varchar(100) DEFAULT NULL,
  REFER_TO_EMAIL varchar(100) DEFAULT NULL,
  REQUIREMENT_ID BIGINT,
  REFER_ID BIGINT,
  CREATED_DATE DATETIME,
  CREATED_BY VARCHAR(10),
  UPDATED_DATE DATETIME,
  UPDATED_BY VARCHAR(10),

  PRIMARY KEY (`ID`),
  FOREIGN KEY(REFER_FROM_USER_ID) REFERENCES `USER`(ID),
  FOREIGN KEY(REFER_TO_USER_ID) REFERENCES `USER`(ID),
  FOREIGN KEY(REFER_ID) REFERENCES `REQUIREMENT_REFER`(ID),
  FOREIGN KEY(REQUIREMENT_ID) REFERENCES `REQUIREMENT`(ID)
 
) ;

ALTER TABLE `REQUIREMENT_REFER` ADD INDEX `REFER_FROM_EMAIL` (`REFER_FROM_EMAIL`);
ALTER TABLE `REQUIREMENT_REFER` ADD INDEX `REFER_TO_EMAIL` (`REFER_TO_EMAIL`);

 