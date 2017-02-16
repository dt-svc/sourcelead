INSERT INTO `sourcelead`.`TABS` (`ID`, `TAB_NAME`, `TAB_ORDER`) VALUES ('15', 'Profile', '10');

INSERT INTO `sourcelead`.`SOURCELEAD_ACTIONS` (`ID`, `ACTION_NAME`, `ACTION_ROLE`, `TAB_ID`) VALUES ('151', 'Crowd Sourcing Profile', 'ROLE_CROWDSOURCING_PROFILE', '15');
INSERT INTO `sourcelead`.`SOURCELEAD_ACTIONS` (`ID`, `ACTION_NAME`, `ACTION_ROLE`, `TAB_ID`) VALUES ('152', 'Crowd Sourcing Requirements', 'ROLE_CROWDSOURCING_REQUIREMENTS', '8');
INSERT INTO `sourcelead`.`SOURCELEAD_ACTIONS` (`ID`, `ACTION_NAME`, `ACTION_ROLE`, `TAB_ID`) VALUES ('153', 'Crowd Sourcing Candidates', 'ROLE_CROWDSOURCING_CANDIDATES', '10');


INSERT INTO `sourcelead`.`MODULES` (`ID`, `MODULE_NAME`, `ORG_ID`, `IS_ENROLED`) VALUES ('5', 'CROWD SOURCING', '1', 'Y');

INSERT INTO `sourcelead`.`MODULE_ACTIONS` (`ID`, `MODULE_ID`, `ACTION_ID`) VALUES ('273', '5', '151');
INSERT INTO `sourcelead`.`MODULE_ACTIONS` (`ID`, `MODULE_ID`, `ACTION_ID`) VALUES ('274', '5', '152');
INSERT INTO `sourcelead`.`MODULE_ACTIONS` (`ID`, `MODULE_ID`, `ACTION_ID`) VALUES ('275', '5', '153');



INSERT INTO `sourcelead`.`ROLES` (`ROLE_NAME`, `DISPLAY_NAME`, `ORG_LOCATION_ID`, `ORG_ID`, `ACTION_ID`, `DEFAULT_ROLE`, `STATUS`) VALUES ('ROLE_CROWDSOURCING_PROFESSIONAL', 'Professional', '1', '1', '151,152', '1', '1');
INSERT INTO `sourcelead`.`ROLES` (`ROLE_NAME`, `DISPLAY_NAME`, `ORG_LOCATION_ID`, `ORG_ID`, `ACTION_ID`, `DEFAULT_ROLE`, `STATUS`) VALUES ('ROLE_CROWDSOURCING_RECRUITER', 'Independent Recruiter', '1', '1', '152,153', '1', '1');



CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','ORG_ID','BIGINT NULL');
ALTER TABLE `sourcelead`.USER_ROLE ADD CONSTRAINT USER_ROLE_ORG_ID_ibfk_2 FOREIGN KEY (ORG_ID) REFERENCES `sourcelead`.ORG(id);

ALTER TABLE `sourcelead`.`USER` CHANGE COLUMN `USER_TYPE` `USER_TYPE` VARCHAR(50) NULL DEFAULT NULL ;

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','ORG_ID','BIGINT NULL');
ALTER TABLE `sourcelead`.CANDIDATE ADD CONSTRAINT CANDIDATE_ORG_ID_ibfk_2 FOREIGN KEY (ORG_ID) REFERENCES `sourcelead`.ORG(id);



INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ProfessionalInvitation','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'professionalName', '{{ProfessionalInvitation.professionalName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{ProfessionalInvitation.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resetLink', '{{ProfessionalInvitation.resetLink}}');



INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('Professional Invitation','Email', 'Professional Invitation', 'Welcome to SourceLead ', 'Welcome to SourceLead','org', 'Best Regards,\nSourceLead.', 'Welcome !!!\n\n Dear Professional {{ProfessionalInvitation.professionalName}}\n\n It is great to see that you have started accessing sourcelead.\n User Name : {{ProfessionalInvitation.loginUserName}}\n Your Sourcelead URL : {{ProfessionalInvitation.resetLink}}\n  I hope you will  have a wonderful time with SourceLead. If you have any questions or comments, feel free to reach out to us at info@sourcelead.com','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
									

		
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('Individual Recruiter Invitation','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'recruiterName', '{{RecruiterInvitation.recruiterName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{RecruiterInvitation.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resetLink', '{{RecruiterInvitation.resetLink}}');



INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('Individual Recruiter Invitation','Email', 'Individual Recruiter Invitation', 'Welcome to SourceLead ', 'Welcome to SourceLead','org', 'Best Regards,\nSourceLead.', 'Welcome !!!\n\n Dear Recruiter {{RecruiterInvitation.recruiterName}}\n\n It is great to see that you have started accessing sourcelead.\n User Name : {{RecruiterInvitation.loginUserName}}\n Your Sourcelead URL : {{RecruiterInvitation.resetLink}}\n  I hope you will  have a wonderful time with SourceLead. If you have any questions or comments, feel free to reach out to us at info@sourcelead.com','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
									

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','POSTED_ORG_NAME','VARCHAR(250) DEFAULT NULL');

CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','COUNTRY','VARCHAR(100) DEFAULT NULL');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','APPLIED_PROFESSIONALS','TEXT DEFAULT NULL');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','REQUIREMENT_UUID','VARCHAR(20) NULL AFTER `IS_CROWD_SOURCING`');


 