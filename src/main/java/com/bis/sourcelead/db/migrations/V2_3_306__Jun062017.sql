DROP TABLE IF EXISTS `CONTRACT_REQUEST_DOCUMENTS`;
CREATE TABLE `CONTRACT_REQUEST_DOCUMENTS` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  CONTRACT_ID BIGINT,
  DESCRIPTION TEXT DEFAULT NULL,
  DOC_FILE MEDIUMBLOB NULL,
  FILE_NAME TEXT NULL,
  FILE_TYPE VARCHAR(250) NULL,
  STATUS VARCHAR(20) NULL,
  CREATED_DATE DATE,
  CREATED_BY VARCHAR(100),
  UPDATED_DATE DATE,
  UPDATED_BY VARCHAR(100),
  PRIMARY KEY (`ID`),
  FOREIGN KEY(CONTRACT_ID) REFERENCES `CONTRACTS`(ID)
);



#entity_type for contract expiry remainder
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('contractExpiryRemainder', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{contractExpiryRemainder.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractEndDate', '{{contractExpiryRemainder.contractEndDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractOwner', '{{contractExpiryRemainder.contractOwner}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractStartDate', '{{contractExpiryRemainder.contractStartDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverParty', '{{contractExpiryRemainder.receiverParty}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'status', '{{contractExpiryRemainder.status}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractType', '{{contractExpiryRemainder.contractType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUrl', '{{contractExpiryRemainder.loginUrl}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('contractExpiryRemainder','Email', 'contractExpiryRemainder', 'Used to Remind the users about Contract Expiry', '{{contractExpiryRemainder.contractTitle}} Contract will expire on {{contractExpiryRemainder.contractEndDate}}.','org', 'Best Regards,\nSourceLead Team.', 'Dear {{contractExpiryRemainder.contractOwner}},\nReminder about the {{contractExpiryRemainder.contractTitle}} contract will expire on {{contractExpiryRemainder.contractEndDate}}.\nContract Start Date : {{contractExpiryRemainder.contractStartDate}}\nReceiver Party : {{contractExpiryRemainder.receiverParty}}\nStatus : {{contractExpiryRemainder.status}}\nContract Type : {{contractExpiryRemainder.contractType}}\nGo through following link for complete details of the Contract.\n{{contractExpiryRemainder.loginUrl}}
','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('contractExpiryRemainder', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('contractExpiryRemainder', 'Notification', 'contractExpiryRemainder', 'Used to Remind the users about Contract Expiry', '{{contractExpiryRemainder.contractTitle}} Contract will expire on {{contractExpiryRemainder.contractEndDate}}.', 'sourcelead', '', '{{contractExpiryRemainder.contractTitle}} Contract will expire on {{contractExpiryRemainder.contractEndDate}}.','N','SLADMIN', 'com', 'in.com');



#entity_type for receiving contract documents
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('contractDocumentsReceived', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{contractDocumentsReceived.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractOwner', '{{contractDocumentsReceived.contractOwner}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{contractDocumentsReceived.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrg', '{{contractDocumentsReceived.loginOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUrl', '{{contractDocumentsReceived.loginUrl}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('contractDocumentsReceived','Email', 'contractDocumentsReceived', 'Used when receiver party sends the requested documents', 'Submitted the requested Documents for the {{contractDocumentsReceived.contractTitle}}.','org', 'Best Regards,\n{{contractDocumentsReceived.loginUser}}\n{{contractDocumentsReceived.loginOrg}}', 'Dear {{contractDocumentsReceived.contractOwner}},\nWe have Updated the requested documents for the {{contractDocumentsReceived.contractTitle}}.\nTo view the documents please go through the System.\n{{contractDocumentsReceived.loginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('contractDocumentsReceived', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('contractDocumentsReceived', 'Notification', 'contractDocumentsReceived', 'Used when receiver party sends the requested documents', 'Submitted the requested Documents for the {{contractDocumentsReceived.contractTitle}}.', 'sourcelead', '', 'Submitted the requested Documents for the {{contractDocumentsReceived.contractTitle}}.','N','SLADMIN', 'com', 'in.com');

