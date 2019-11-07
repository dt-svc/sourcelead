set SQL_SAFE_UPDATES=0;

#entity_type
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contractTitle', '{{NDAOrNSAFinalizeNotification.contractTitle}}');
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NDAOrNSAFinalizeNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('NDAOrNSAFinalizeNotification', 'Notification', 'NDAOrNSAFinalizeNotification', 'contract finalized notification', '"{{NDAOrNSAFinalizeNotification.contractTitle}} Contract got finalized', 'sourcelead', '', '{{NDAOrNSAFinalizeNotification.contractTitle}} Contract got finalized','N','SLADMIN', 'com', 'in.com');