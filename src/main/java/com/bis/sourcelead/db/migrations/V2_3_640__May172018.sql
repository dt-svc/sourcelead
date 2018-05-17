#ALTER TABLE `sourcelead`.`CONTRACT_OWNER_HIERARCHY` 
#RENAME TO  `sourcelead`.`CONTRACT_APPROVER_HIERARCHY` ;


DROP TABLE IF EXISTS `CONTRACT_APPROVER_HIERARCHY`;
CREATE TABLE `CONTRACT_OWNER_HIERARCHY` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  CONTRACT_ID BIGINT,
  LEVEL INT(10),
  HIERARCHY_TYPE VARCHAR(30) DEFAULT NULL,
  OWNER_USER_ID BIGINT DEFAULT NULL,
  CONTRACT_STATUS VARCHAR(20) DEFAULT NULL,
  STATUS VARCHAR(20) DEFAULT NULL,
  REJECTED_REASON VARCHAR(500),
  CREATED_DATE DATE,
  CREATED_BY VARCHAR(10),
  UPDATED_DATE DATE,
  UPDATED_BY VARCHAR(10),
  PRIMARY KEY (`ID`), 
  FOREIGN KEY(OWNER_USER_ID) REFERENCES `USER`(ID),
  FOREIGN KEY(CONTRACT_ID) REFERENCES `CONTRACTS`(ID)
); 

ALTER TABLE `CONTRACTS` 
ADD COLUMN `REVISED_REASON` TEXT NULL DEFAULT NULL AFTER `REJECTED_REASON`;



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`) VALUES
			('Revise Contract Email','Email', 'ReviseContractEmail', 'used to notify owner to Revise the Contract', 'Request for revise the contract.','org', 'Best Regards \n[Login_User]\n[Login_Org].', 'Dear [Contract_Owner],\nThis is to inform that the contract signed between your company and us on [Contract_StartDate].\nContract title : [Contract_Title]\nDuration : [Contract_Duration]\nContract Type :[Contract_Type]\nWe would like to have slight changes in the contract,for changes kindly go through the following link.\n[Revise_Link]\nWe expect that changes are made ASAP.','N', 'SLADMIN', 'slead', 'sleadIN', 16);

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractOwner', '[Contract_Owner]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractStartDate', '[Contract_StartDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractTitle', '[Contract_Title]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractDuration', '[Contract_Duration]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractType', '[Contract_Type]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'reviseLink', '[Revise_Link]');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`) VALUES
			('Reminder contract Signing Email Pending','Email', 'RemindercontractSigningEmailPending', 'used to notify owners', 'Reminder about the contract Signing.','org', 'Best Regards \nSourcelead Team.', 'Dear [Contract_Owner],\nThis is to notify that your contract [Contract_Id] - [Contract_Title] with [Receiver_Org_Name] was in still pending stage from [No_Of_Days]\nType: [Contract_Type]\nDuration: [Contract_StartDate] - [Contract_EndDate].','N', 'SLADMIN', 'slead', 'sleadIN', 16);

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractOwner', '[Contract_Owner]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractTitle', '[Contract_Title]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractStartDate', '[Contract_StartDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractEndDate', '[Contract_EndDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractType', '[Contract_Type]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'receiverOrgName', '[Receiver_Org_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractId', '[Contract_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'noOfDays', '[No_Of_Days]');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`) VALUES

			('Reminder contract Signing Email WTV','Email', 'RemindercontractSigningEmailWTV', 'used to notify owners', 'Reminder about the contract Signing.','org', 'Best Regards \nSourcelead Team.', 'Dear [Contract_Owner],\nThis is to notify that your contract [Contract_Id] - [Contract_Title] with [Receiver_Org_Name] was in still Sent for Verification stage from [No_Of_Days]\nType: [Contract_Type]\nDuration: [Contract_StartDate] - [Contract_EndDate].','N', 'SLADMIN', 'slead', 'sleadIN', 16);

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractOwner', '[Contract_Owner]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractTitle', '[Contract_Title]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractStartDate', '[Contract_StartDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractEndDate', '[Contract_EndDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractType', '[Contract_Type]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'receiverOrgName', '[Receiver_Org_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'contractId', '[Contract_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'noOfDays', '[No_Of_Days]');
			

