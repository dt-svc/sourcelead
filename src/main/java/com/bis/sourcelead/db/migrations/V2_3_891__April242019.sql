ALTER TABLE `CANDIDATE` ADD COLUMN `SSN_NUMBER` VARCHAR(20) NULL DEFAULT NULL AFTER `PAN_NUMBER`;
			
INSERT INTO `SOURCELEAD_FORM_FIELDS` (`SECTION_ID`, `LABEL_NAME`, `FIELD_NAME`, `FIELD_TYPE`, `IS_FIELD_REQUIRED`, `STATUS`, `IS_IMPORT_FIELD`) VALUES ('8', 'Pan Number', 'panNumber', 'TextBox', '0', '1', '1');
			
INSERT INTO `SOURCELEAD_FORM_FIELDS` (`SECTION_ID`, `LABEL_NAME`, `FIELD_NAME`, `FIELD_TYPE`, `IS_FIELD_REQUIRED`, `STATUS`, `IS_IMPORT_FIELD`) VALUES ('8', 'Aadhar Number', 'aadharNumber', 'TextBox', '0', '1', '1');
			
INSERT INTO `SOURCELEAD_FORM_FIELDS` (`SECTION_ID`, `LABEL_NAME`, `FIELD_NAME`, `FIELD_TYPE`, `IS_FIELD_REQUIRED`, `STATUS`, `IS_IMPORT_FIELD`) VALUES ('8', 'SSN Number', 'ssnNumber', 'TextBox', '0', '1', '1');


CALL AlterTableAddColumn ('EMAIL_SCENARIO_CONFIGURATION','IS_ACTIVE','VARCHAR(10) NULL AFTER `VERIFIED_STATUS`');

SET SQL_SAFE_UPDATES = 0;
UPDATE EMAIL_SCENARIO_CONFIGURATION SET USAGE_TYPE = 'resume_parsing', IS_ACTIVE ='Y' WHERE USAGE_TYPE ="resume_parsing" OR USAGE_TYPE ="job_parsing";
SET SQL_SAFE_UPDATES = 1;

#ApplicantRejectMailToInterviewCreator
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Interview_Creator_Name],\n\nI regret to say that i am no longer interested in attending the following interview\n\nJob Title :[Requirement_Name]\nInterview Date :[Interview_Date]\nInterview Time :[Interview_Time]\nInterview Type :[Interview_Type]\nDetails :[Details].\n\n[Login_URL]\n\nThank you for providing me the opportunity' WHERE `UNIQUE_SCENARIO_NAME`='ApplicantRejectMailToInterviewCreator' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;