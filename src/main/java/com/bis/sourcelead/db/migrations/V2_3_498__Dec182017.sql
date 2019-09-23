#Send Contract signature mail
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('SendContract', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverUser', '{{SendContract.receiverUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractCreator', '{{SendContract.contractCreator}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{SendContract.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractType', '{{SendContract.contractType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrg', '{{SendContract.loginOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractDuration', '{{SendContract.contractDuration}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Applicant', '{{SendContract.Applicant}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{SendContract.loginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{SendContract.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'signatureLink', '{{SendContract.signatureLink}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('SendContract','Email', 'SendContract', 'send contract mail to client or vendor', '{{SendContract.contractType}} from the {{SendContract.loginOrg}}','org', 'Best Regards,\n{{SendContract.loginUser}}', 'Dear {{SendContract.receiverUser}},\n\n{{SendContract.loginOrg}} has sent you {{SendContract.contractType}}. Kindly go through the contract details.\n\nContract Title : {{SendContract.contractTitle}}\nDuration : {{SendContract.contractDuration}}\nSignator : {{SendContract.receiverUser}}\n\nKindly login into the system to sign the contract.\n{{SendContract.loginLink}}{{SendContract.signatureLink}}','N', 'SLADMIN', 'slead', 'sleadIN');

#Send Contract Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('SendContract', 'Notification', 'SendContract', 'send contract notification to client or vendor.', '{{SendContract.contractType}} from the {{SendContract.loginOrg}}', 'sourcelead', '', '{{SendContract.contractType}} from the {{SendContract.loginOrg}}','N','SLADMIN', 'com', 'in.com');


#SOW send contract email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('SendSowContract','Email', 'SendSowContract', 'send sow contract mail to client or vendor', '{{SendContract.contractType}} from the {{SendContract.loginOrg}}','org', 'Best Regards,\n{{SendContract.loginUser}}', 'Dear {{SendContract.receiverUser}},\n\n{{SendContract.loginOrg}} has sent you {{SendContract.contractType}}. Kindly go through the contract details.\n\nContract Title : {{SendContract.contractTitle}}\nDuration : {{SendContract.contractDuration}}\nApplicant : {{SendContract.Applicant}}\nSignator : {{SendContract.receiverUser}}\n\nKindly login into the system to sign the contract.\n{{SendContract.loginLink}}{{SendContract.signatureLink}}','N', 'SLADMIN', 'slead', 'sleadIN');
