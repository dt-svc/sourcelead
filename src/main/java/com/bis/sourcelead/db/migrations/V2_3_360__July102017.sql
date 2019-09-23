#Contract Siged mail
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ContractSigned', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverUser', '{{ContractSigned.receiverUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ownerUser', '{{ContractSigned.ownerUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{ContractSigned.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverOrg', '{{ContractSigned.receiverOrg}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('ContractSigned','Email', 'ContractSigned', 'used to notify contract owner about contract signed.', '{{ContractSigned.contractTitle}} has been signed by {{ContractSigned.receiverOrg}}','org', 'Best Regards,\n{{ContractSigned.receiverUser}}', 'Dear {{ContractSigned.ownerUser}},\n We are happy that the {{ContractSigned.contractTitle}} have been signed and forward to you for verifying.Please do verify and finalise the contract.','N', 'SLADMIN', 'slead', 'sleadIN');
