#entity_type for contract waiting to be signed for Email
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('waitingContractToBeSigned', 'Email');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('waitingContractToBeSigned','Email', 'waitingContractToBeSigned', 'Used to Remind the users about Contract Waiting to be signed', '[contract_Title] Contract is waiting to be signed from [contract_StartDate].','org', 'Best Regards,\nSourceLead Team.', 'Dear [contract_Owner],\nReminder about the [contract_Title] contract is waiting for signed from [contract_StartDate].\nContract End Date : [contract_EndDate]\nReceiver Party : [receiver_Party]\nStatus : [status]\nContract Type : [contract_Type]\nGo through following link for complete details of the Contract.\n[loginUrl]
','N', 'SLADMIN', 'slead', 'sleadIN');


#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '[contract_Title]');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractEndDate', '[contract_EndDate]');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractOwner', '[contract_Owner]');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractStartDate', '[contract_StartDate]');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverParty', '[receiver_Party]');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'status', '[status]');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractType', '[contract_Type]');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUrl', '[loginUrl]');


#entity_type for contract waiting to be signed for Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('waitingContractToBeSigned', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('waitingContractToBeSigned', 'Notification', 'waitingContractToBeSigned', 'Used to Remind the users about Contract Waiting to be signed', '[contract_Title] Contract is waiting to be signed from [contract_StartDate].', 'sourcelead', '', '[contract_Title] Contract is waiting to be signed from [contract_StartDate].','N','SLADMIN', 'com', 'in.com');



