CALL AlterTableAddColumn ('INVOICE','PREVIOUS_INVOICE_STATUS','VARCHAR(100) NULL  AFTER `INVOICE_STATUS`');


#email to receiver end date edit for msa

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ModifyContractEndDate','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractOwner', '{{ModifyContractEndDate.contractOwner}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contactUser', '{{ModifyContractEndDate.contactUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{ModifyContractEndDate.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'previousEndDate', '{{ModifyContractEndDate.previousEndDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'presentEndDate', '{{ModifyContractEndDate.presentEndDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'startDate', '{{ModifyContractEndDate.startDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverOrg', '{{ModifyContractEndDate.receiverOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUrl', '{{ModifyContractEndDate.loginUrl}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{ModifyContractEndDate.loginOrgName}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ModifyContractEndDate','Email', 'ModifyContractEndDate', 'Used for Email To Receiver About Modify Contract End Date', 'Contract MSA Revised','org', 'Best Regards,\n{{ModifyContractEndDate.contractOwner}}\n{{ModifyContractEndDate.loginOrgName}}', ' Please note that change in the {{ModifyContractEndDate.contractTitle}} end date has been modified {{ModifyContractEndDate.previousEndDate}} to {{ModifyContractEndDate.presentEndDate}}.\n The details of the contracts are following \n Contract Title : {{ModifyContractEndDate.contractTitle}} \n Owner : {{ModifyContractEndDate.contractOwner}} \n Contract Start Date : {{ModifyContractEndDate.startDate}} \n Contract End Date : {{ModifyContractEndDate.presentEndDate}} \n Receiver Org : {{ModifyContractEndDate.receiverOrg}} \n Receiver Contact : {{ModifyContractEndDate.contactUser}} \n To view the details please login into system \n {{ModifyContractEndDate.loginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');



#email to receiver end date edit for sow

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ModifySowContractEndDate','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractOwner', '{{ModifySowContractEndDate.contractOwner}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contactUser', '{{ModifySowContractEndDate.contactUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{ModifySowContractEndDate.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobId', '{{ModifySowContractEndDate.jobId}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'JobTitle', '{{ModifySowContractEndDate.JobTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'InitiatorLocation', '{{ModifySowContractEndDate.InitiatorLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ProjectId', '{{ModifySowContractEndDate.ProjectId}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ProjectTitle', '{{ModifySowContractEndDate.ProjectTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Rate', '{{ModifySowContractEndDate.Rate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'StartDate', '{{ModifySowContractEndDate.StartDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'EndDate', '{{ModifySowContractEndDate.EndDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'PartnerOrganisation', '{{ModifySowContractEndDate.PartnerOrganisation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUrl', '{{ModifySowContractEndDate.loginUrl}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{ModifySowContractEndDate.loginOrgName}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ModifySowContractEndDate','Email', 'ModifySowContractEndDate', 'Used for Email To Receiver About Modify Sow Contract EndDate', 'Contract SOW Revised','org', 'Best Regards,\n{{ModifySowContractEndDate.contractOwner}}\n{{ModifySowContractEndDate.loginOrgName}}', ' Please note that change in the workorder end date for {{ModifySowContractEndDate.contractTitle}} submitted for {{ModifySowContractEndDate.jobId}} and {{ModifySowContractEndDate.JobTitle}}.\n The details of the workorder are as follows \n Contract Title : {{ModifySowContractEndDate.contractTitle}} \n Job Id : {{ModifySowContractEndDate.jobId}} \n Job Title : {{ModifySowContractEndDate.JobTitle}} \n Initiator Location : {{ModifySowContractEndDate.InitiatorLocation}} \n Project Id : {{ModifySowContractEndDate.ProjectId}} \n Project Title : {{ModifySowContractEndDate.ProjectTitle}} \n Rate : {{ModifySowContractEndDate.Rate}} \n Start Date : {{ModifySowContractEndDate.StartDate}} \n End Date : {{ModifySowContractEndDate.EndDate}} \n Partner Organisation : {{ModifySowContractEndDate.PartnerOrganisation}} \n To view the work order details, login into the system \n {{ModifyContractEndDate.loginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');


DROP TABLE IF EXISTS `CHECKS`;
CREATE TABLE `CHECKS` (
ID BIGINT NOT NULL AUTO_INCREMENT,
EMP_ID VARCHAR(250),
EMP_NAME VARCHAR(250),
ORG_LOCATION_ID BIGINT DEFAULT NULL,
Date DATE,
TYPE_OF_EXPENSE VARCHAR(250),      
BANK VARCHAR(250),
MONTH VARCHAR(250),
LEGAL_FEES DOUBLE DEFAULT NULL,
ADVANCE DOUBLE DEFAULT NULL,
GENERAL_EXPENSES DOUBLE DEFAULT NULL,
VENDOR_PAYMENTS	 DOUBLE DEFAULT NULL,
ELECTRONIC_WITHDRAWALS	 VARCHAR(250),
PAYROLL_CHECKS	 DOUBLE DEFAULT NULL,
AMOUNT	 DOUBLE DEFAULT NULL,
CHECK_NO VARCHAR(250),
STATUS VARCHAR(50),
CREATED_DATE DATETIME,
CREATED_BY VARCHAR(10),
UPDATED_DATE DATETIME,
UPDATED_BY VARCHAR(10),
PRIMARY KEY (`ID`),
FOREIGN KEY(ORG_LOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID)
);

CALL AlterTableAddColumn ('INVOICE','SUBMISSION_APPROVER_HIERARCHY_ID','VARCHAR(200) NULL AFTER `DOC_FILENAME`');
