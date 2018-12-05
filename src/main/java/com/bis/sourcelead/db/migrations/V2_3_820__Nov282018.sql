CALL AlterTableAddColumn ('EMPLOYEE','CONFIRMATION_DATE','DATE  DEFAULT NULL AFTER `HIRE_DATE`');
CALL AlterTableAddColumn ('EMPLOYEE','PROBATION_PERIOD','VARCHAR(50)  DEFAULT NULL AFTER `CONFIRMATION_DATE`');
CALL AlterTableAddColumn ('EMPLOYEE','PROBATION_STATUS','VARCHAR(50)  DEFAULT NULL AFTER `PROBATION_PERIOD`');



CREATE TABLE `PROBATION_HISTORY` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  EMPLOYEE_ID BIGINT,
  DATE_OF_JOIN DATE,
  PROBATION_PERIOD VARCHAR(50) DEFAULT NULL,
  FROM_STATUS VARCHAR(50) DEFAULT NULL,
  TO_STATUS VARCHAR(50) DEFAULT NULL,
  ACTUAL_CONFIRMATION_DATE DATE,
  CONFIRMED_DATE DATE,
  STATUS int,
  UUID VARCHAR(20) DEFAULT NULL,
  CREATED_DATE DATE,
  CREATED_BY BIGINT,
  UPDATED_DATE DATE,
  UPDATED_BY BIGINT,
  PRIMARY KEY (`ID`),
  FOREIGN KEY(EMPLOYEE_ID) REFERENCES `EMPLOYEE`(ID),
  FOREIGN KEY(CREATED_BY) REFERENCES `USER`(ID),
  FOREIGN KEY(UPDATED_BY) REFERENCES `USER`(ID)
);





#Mail to Resource Manager about probationary associates
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ProbationaryAssociatesEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('ProbationaryAssociatesEmail','Email', 'ProbationaryAssociatesEmail', 'Forward the email to resource manager about probationary confirmation', 'Probationary Associates','org', 'Best Regards,\nSourcelead Team.', 'Dear [Resource_Manager],\n This is to notify that following associates will complete Probationary period with in two days. \n [Associate_Table]','N', 'SLADMIN', 'slead', 'sleadIN', 2,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'resourceManager', '[Resource_Manager]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'associateTable', '[Associate_Table]');




#Mail to Associate about confirmation of employment
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ConfirmationOfEmploymentMail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('ConfirmationOfEmploymentMail','Email', 'ConfirmationOfEmploymentMail', 'Forward to the associate about their confirmation of employment', 'Employment Confirmation','org', 'Best Regards,\n[Login_User]\n[Login_org]', 'Dear [Associate_Name],\nWe are pleased to confirm that your employment in [Login_org] as [Associate_Role].\nFollowing the end of your probation at our company on [Confirmation_Date],\nwe have reviewed your performance and decided that you have the right qualities to be an employee at our company.
All The Best!!!','N', 'SLADMIN', 'slead', 'sleadIN', 2,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'associateName', '[Associate_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'associateEmail', '[Associate_Email]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'associateRole', '[Associate_Role]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'confirmationDate', '[Confirmation_Date]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrgName', '[Login_org]');


CALL AlterTableAddColumn ('ORG_LOCATIONS','PROBATION_PERIOD','VARCHAR(50) DEFAULT NULL AFTER `PROFILE_PERCENTAGE`');
CALL AlterTableAddColumn ('ORG_LOCATIONS','PROBATION_NO_OF_DAYS','INT(10) DEFAULT 0 AFTER `PROBATION_PERIOD`');
