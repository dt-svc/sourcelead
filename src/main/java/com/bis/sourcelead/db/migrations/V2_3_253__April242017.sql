#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('approverUpdatedContract', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ContractTitle', '{{approverUpdatedContract.ContractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApproverName', '{{approverUpdatedContract.ApproverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'OwnerName', '{{approverUpdatedContract.OwnerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'StartDate', '{{approverUpdatedContract.StartDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'EndDate', '{{approverUpdatedContract.EndDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{approverUpdatedContract.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ContractType', '{{approverUpdatedContract.ContractType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ClientVendorName', '{{approverUpdatedContract.ClientVendorName}}');
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('approverUpdatedContract','Email', 'approverUpdatedContract', 'approver updated  mail', 'Request to approve the contract {{approverUpdatedContract.ContractTitle}}','org', 'Best Regards,\n{{approverUpdatedContract.loginUserName}}.', 'Dear {{approverUpdatedContract.ApproverName}},\n We have initiated the {{approverUpdatedContract.ContractType}} for the  {{approverUpdatedContract.ClientVendorName}}, so go through complete contract details and approve it.\n Brief details are below,\n\n Title :{{approverUpdatedContract.ContractTitle}}\n Owner : {{approverUpdatedContract.OwnerName}}\n Start Date : {{approverUpdatedContract.StartDate}}\n End Date : {{approverUpdatedContract.EndDate}}\n Contract Type : {{approverUpdatedContract.ContractType}}\n','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('approverUpdatedContract', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('approverUpdatedContract', 'Notification', 'approverUpdatedContract', 'approver updated contract notification', 'Request to approve the contract {{approverUpdatedContract.ContractTitle}}', 'sourcelead', '', 'Request to approve the contract {{approverUpdatedContract.ContractTitle}}','N','SLADMIN', 'com', 'in.com');


