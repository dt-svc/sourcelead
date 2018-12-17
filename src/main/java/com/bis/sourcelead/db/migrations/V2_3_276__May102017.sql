#Approving of contract mail
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('approveContract', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApproverUser', '{{approveContract.approverUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ContractCreator', '{{approveContract.contractCreator}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ContractTitle', '{{approveContract.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginOrg', '{{approveContract.loginOrg}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Approve Contract','Email', 'Approve Contract', 'Used when Approving the Contract', '{{approveContract.contractTitle}} have been approved.','org', '\nBest Regards,\n{{approveContract.approverUser}}\n{{approveContract.loginOrg}}.', 'Dear {{approveContract.contractCreator}},\n {{approveContract.contractTitle}} have been approved ,you can go for further process.','N', 'SLADMIN', 'slead', 'sleadIN');


#Rejecting of contract mail
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('rejectContract', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ApproverUser', '{{rejectContract.approverUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ContractCreator', '{{rejectContract.contractCreator}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ContractTitle', '{{rejectContract.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginOrg', '{{rejectContract.loginOrg}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Reject Contract','Email', 'Reject Contract', 'Used when Rejecting the Contract', '{{rejectContract.contractTitle}} have been rejected.','org', '\nBest Regards,\n{{rejectContract.approverUser}}\n{{rejectContract.loginOrg}}.', 'Dear {{rejectContract.contractCreator}},\n {{rejectContract.contractTitle}} have been rejected ,you can go for further process.','N', 'SLADMIN', 'slead', 'sleadIN');


#Contract mail to owner
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ownerContract', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'OwnerUser', '{{ownerContract.ownerUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ContractCreator', '{{ownerContract.contractCreator}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ContractTitle', '{{ownerContract.contractTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ContractType', '{{ownerContract.contractType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'StartDate', '{{ownerContract.startDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'EndDate', '{{ownerContract.endDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginOrg', '{{ownerContract.loginOrg}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Contract Mail To Owner','Email', 'Contract Mail To Owner', 'Used to send the mail to Owner', 'New {{ownerContract.contractType}} contract allocated to you.','org', '\nBest Regards,\n{{ownerContract.contractCreator}}\n{{ownerContract.loginOrg}}.', 'Dear {{ownerContract.ownerUser}},\n	We are happy to allocate new {{ownerContract.contractType}} contract to you.Go through the complete contract details and process further.\n Contract Title : {{ownerContract.contractTitle}} \n Start Date : {{ownerContract.startDate}} \n End Date : {{ownerContract.endDate}}','N', 'SLADMIN', 'slead', 'sleadIN');

