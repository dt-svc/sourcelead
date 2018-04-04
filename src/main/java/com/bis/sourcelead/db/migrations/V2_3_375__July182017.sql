
# Contract_approve_Notification
SET SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Approval Request for Contract {{approverUpdatedContract.ContractTitle}}    '  WHERE `UNIQUE_SCENARIO_NAME`='approverUpdatedContract' and `ENTITY_TYPE`='Notification';


# Approved Contract_Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('approveContract', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('Approve Contract', 'Notification', 'Approve Contract', 'approver updated contract notification', '{{approveContract.contractTitle}} has been approved.', 'sourcelead', '', '{{approveContract.contractTitle}} has been approved.','N','SLADMIN', 'com', 'in.com');

# Rejected Contract_Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('Reject Contract', 'Notification', 'Reject Contract', 'Used when Rejecting the Contract', '{{rejectContract.contractTitle}} has been denied.', 'sourcelead', '', '{{rejectContract.contractTitle}} has been denied.','N','SLADMIN', 'com', 'in.com');


# Waiting to verify_Notification

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ContractSigned', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('ContractSigned', 'Notification', 'ContractSigned', 'used to notify contract owner about contract signed.', '{{ContractSigned.contractTitle}} has been signed by {{ContractSigned.receiverOrg}}. ', 'sourcelead', '', '{{ContractSigned.contractTitle}} has been signed by {{ContractSigned.receiverOrg}}.','N','SLADMIN', 'com', 'in.com');
SET SQL_SAFE_UPDATES=1;