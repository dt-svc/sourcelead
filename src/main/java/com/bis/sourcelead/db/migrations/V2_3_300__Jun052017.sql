#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('contractExpiryRemainder', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'contractTitle', '{{contractExpiryRemainder.contractTitle}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'contractEndDate', '{{contractExpiryRemainder.contractEndDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'contractOwner', '{{contractExpiryRemainder.contractOwner}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'contractStartDate', '{{contractExpiryRemainder.contractStartDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'receiverParty', '{{contractExpiryRemainder.receiverParty}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'status', '{{contractExpiryRemainder.status}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'contractType', '{{contractExpiryRemainder.contractType}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUrl', '{{contractExpiryRemainder.loginUrl}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('contractExpiryRemainder','Email', 'contractExpiryRemainder', 'Used to Remind the users about Contract Expiry', '{{contractExpiryRemainder.contractTitle}} Contract will expire on {{contractExpiryRemainder.contractEndDate}}.','org', 'Best Regards,\nSourceLead Team.', 'Dear {{contractExpiryRemainder.contractOwner}},\nReminder about the {{contractExpiryRemainder.contractTitle}} contract will expire on {{contractExpiryRemainder.contractEndDate}}.\nContract Start Date : {{contractExpiryRemainder.contractStartDate}}\nReceiver Party : {{contractExpiryRemainder.receiverParty}}\nStatus : {{contractExpiryRemainder.status}}\nContract Type : {{contractExpiryRemainder.contractType}}\nGo through following link for complete details of the Contract.\n{{contractExpiryRemainder.loginUrl}}
','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('contractExpiryRemainder', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('contractExpiryRemainder', 'Notification', 'contractExpiryRemainder', 'Used to Remind the users about Contract Expiry', '{{contractExpiryRemainder.contractTitle}} Contract will expire on {{contractExpiryRemainder.contractEndDate}}.', 'sourcelead', '', '{{contractExpiryRemainder.contractTitle}} Contract will expire on {{contractExpiryRemainder.contractEndDate}}.','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

