set SQL_SAFE_UPDATES=0;
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ModifyContractEndDateNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ModifyContractEndDateNotification', 'Notification', 'ModifyContractEndDateNotification', 'send updated end date notification to owner', 'Contract MSA Revised', 'sourcelead', '', 'Contract MSA Revised','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MSAFinalizeEmail', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('MsaFinalizedNotification', 'Notification', 'MsaFinalizedNotification', 'contract finalized notification', '"{{MSAFinalizeEmail.contractTitle}} Contract got finalized', 'sourcelead', '', '{{MSAFinalizeEmail.contractTitle}} Contract got finalized','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('SOWFinalizeEmail', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('SowFinalizedNotification', 'Notification', 'SowFinalizedNotification', 'contract finalized notification', '"{{SOWFinalizeEmail.contractTitle}} Contract got finalized', 'sourcelead', '', '{{SOWFinalizeEmail.contractTitle}} Contract got finalized','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

set SQL_SAFE_UPDATES=1;