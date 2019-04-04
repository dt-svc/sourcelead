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