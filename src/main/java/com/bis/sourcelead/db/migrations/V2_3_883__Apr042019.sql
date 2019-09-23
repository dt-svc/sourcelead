DROP TABLE IF EXISTS `OFFER_LETTER_TEMPLATE`;
CREATE TABLE `OFFER_LETTER_TEMPLATE` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  TEMPLATE_NAME varchar(100),
  ORGLOCATION_ID BIGINT,
  LETTER_HEAD_ID BIGINT DEFAULT NULL,
  DESCRIPTION TEXT DEFAULT NULL,
  TEMPLATE_CONTENT LONGTEXT DEFAULT NULL,
  TEMPLATE_STATUS INT,
  TEMPLATE_UUID VARCHAR(50),
  CREATED_DATE DATE,
  CREATED_BY VARCHAR(50),
  UPDATED_DATE DATE,
  UPDATED_BY VARCHAR(50),
  PRIMARY KEY (`ID`),
  FOREIGN KEY(LETTER_HEAD_ID) REFERENCES `LETTER_HEAD`(ID),
  FOREIGN KEY(ORGLOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID)
) ;

CALL AlterTableAddColumn ('REQUIREMENT_OFFERS','OFFER_EXPIRE_DATE','DATE DEFAULT NULL AFTER `OFFER_UUID`');
CALL AlterTableAddColumn ('REQUIREMENT_OFFERS','OFFER_LETTER_TEMPLATE','BIGINT NULL AFTER `OFFER_EXPIRE_DATE`');

ALTER TABLE `REQUIREMENT_OFFERS` ADD CONSTRAINT FK2_OFFER_LETTER_TEMPLATE FOREIGN KEY (OFFER_LETTER_TEMPLATE) REFERENCES OFFER_LETTER_TEMPLATE(ID);


#Client to Vendor Acceptence
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Client to Vendor Acceptence' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'viewOffer', '[View_Offer]');
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Vendor_Contact],\n We are pleased to inform you that the applicant sent by you have been selected for [Requirement_Name] \n If you would like to release offer, Please click here \n[View_Offer] \n\n [Link]'  WHERE `UNIQUE_SCENARIO_NAME`='Client to Vendor Acceptence' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;


#Forward the Mail Offer Made
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Forward the Mail' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'viewOffer', '[View_Offer]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Forward the Mail' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'negotiationLink', '[Negotiation_Link]');
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Applicant_Name],\n Congratulations! You have been offered a job as a [Requirement_Title].\n We trust that your experience and skills will be a valuable asset to our Organization.\n Please find the offer details below \n Designation: [Designation]\n Date of Join: [Dateof_Join] \n [View_Offer] Please choose an option below for further processing. \n [Accept_Link]  [Reject_Link]  [Negotiation_Link]  \n [Attachment]'  WHERE `UNIQUE_SCENARIO_NAME`='Forward the Mail' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;


DROP TABLE IF EXISTS `XLS_BULK_UPLOAD_HISTORY`;
CREATE TABLE `XLS_BULK_UPLOAD_HISTORY`(
ID BIGINT NOT NULL AUTO_INCREMENT,
UPLOAD_ENTITY_TYPE VARCHAR(50),
DOCUMENT_KEY VARCHAR(50),
ORG_LOCATION LONGTEXT,
ORG_LOCATION_ID BIGINT DEFAULT NULL,
UPLOAD_TYPE VARCHAR(50),
UPLOAD_STATUS VARCHAR(50),
FAILED_COUNT INT,
UUID VARCHAR(50),
FILE_URL LONGTEXT DEFAULT NULL,
FAILURE_FILE_URL LONGTEXT DEFAULT NULL,
STATUS INT,
CREATED_DATE DATETIME,
CREATED_BY BIGINT DEFAULT NULL,
UPDATED_DATE DATETIME,
UPDATED_BY BIGINT DEFAULT NULL,
PRIMARY KEY (`ID`),
FOREIGN KEY (CREATED_BY) REFERENCES `USER`(ID),
FOREIGN KEY (UPDATED_BY) REFERENCES `USER`(ID),
FOREIGN KEY(ORG_LOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID)
);


#email should forward configured emailIds for candidate bulkimport to login users. 
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('CandidateBulkImport', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('CandidateBulkImport','Email', 'CandidateBulkImport', 'Mail should forward to Generate User.', 'File exceeds 2 Mb size you imported','sourcelead',
'Best Regards,\nSourcelead Team.', 'Dear [User_Name],\nThis is to notify that you have imported the candidate(s) file which exceeds more than 2 MB, Sourcelead Support \nwill take care of that & import the file in your organisation. Once the file got imported successfully, you will get notified. \nKindly be patience until we get back to you.\nThank You for associating with Sourcelead.','N', 'SLADMIN', 'slead', 'sleadIN', 1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'userName', '[User_Name]');


#email should forward configured emailIds for candidate bulkimport to support. 
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('CandidateBulkImportSupport', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('CandidateBulkImportSupport','Email', 'CandidateBulkImportSupport', 'Mail should forward to Sourcelead Support.', 'Import Request from the [Org_Name] ','sourcelead',
'Best Regards,\nSourcelead Team.', 'Dear Sourcelead Support,\nThis is to notify that [Org_Name] requested to import the candidates.\nRequested User : [User_Name] \nFile Size : [File_Size] Mb \n Kindly  make it done ASAP.','N', 'SLADMIN', 'slead', 'sleadIN', 1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'orgName', '[Org_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'userName', '[User_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'fileSize', '[File_Size]');

