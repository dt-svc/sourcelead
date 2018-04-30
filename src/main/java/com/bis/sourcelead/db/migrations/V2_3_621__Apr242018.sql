#ALTER TABLE `sourcelead`.`CONTRACT_OWNER_HIERARCHY` 
#RENAME TO  `sourcelead`.`CONTRACT_APPROVER_HIERARCHY` ;


DROP TABLE IF EXISTS `sourcelead`.`CONTRACT_APPROVER_HIERARCHY`;
CREATE TABLE `sourcelead`.`CONTRACT_OWNER_HIERARCHY` (
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
  FOREIGN KEY(OWNER_USER_ID) REFERENCES `sourcelead`.`USER`(ID),
  FOREIGN KEY(CONTRACT_ID) REFERENCES `sourcelead`.`CONTRACTS`(ID)
); 

ALTER TABLE `sourcelead`.`CONTRACTS` 
ADD COLUMN `REVISED_REASON` TEXT NULL DEFAULT NULL AFTER `REJECTED_REASON`;




INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ReviseContractEmail', 'Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{ReviseContractEmail.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractOwner', '{{ReviseContractEmail.contractOwner}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractStartDate', '{{ReviseContractEmail.contractStartDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractEndDate', '{{ReviseContractEmail.contractEndDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{ReviseContractEmail.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractDuration', '{{ReviseContractEmail.contractDuration}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractType', '{{ReviseContractEmail.contractType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrg', '{{ReviseContractEmail.loginOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reviseLink', '{{ReviseContractEmail.reviseLink}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`) VALUES
			('Revise Contract Email','Email', 'ReviseContractEmail', 'used to notify owner to Revise the Contract', 'Request for revise the contract.','org', 'Best Regards \n{{ReviseContractEmail.loginUser}}\n{{ReviseContractEmail.loginOrg}}.', 'Dear {{ReviseContractEmail.contractOwner}},\nThis is to inform that the contract signed between your company and us on {{ReviseContractEmail.contractStartDate}}.\nContract title : {{ReviseContractEmail.contractTitle}}\nDuration : {{ReviseContractEmail.contractDuration}}\nContract Type :{{ReviseContractEmail.contractType}}\nWe would like to have slight changes in the contract,for changes kindly go through the following link.\n{{ReviseContractEmail.reviseLink}}\nWe expect that changes are made ASAP.','N', 'SLADMIN', 'slead', 'sleadIN', 16);
