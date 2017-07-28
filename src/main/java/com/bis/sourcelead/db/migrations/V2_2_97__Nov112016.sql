
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('EditSpocContact','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientFirstName', '{{EditSpocContact.ClientFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientLastName', '{{EditSpocContact.ClientLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'ownerName', '{{EditSpocContact.OwnerName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'ownerEmail', '{{EditSpocContact.ownerEmail}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{EditSpocContact.LoginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{EditSpocContact.LoginOrgName}}');


INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Edit SpocContact','Email', 'Edit SpocContact', 'Used for Inviting Owner to  SpocContact', 'Point of contact changed ','org', 'Best Regards,\n {{VendorsInterview.LoginUser}} \n {{EditSpocContact.LoginOrgName}}.', 'Dear  {{EditSpocContact.ClientFirstName}} {{EditSpocContact.ClientLastName}},\n\n Your point of contact changed {{EditSpocContact.OwnerName}} ( {{EditSpocContact.ownerEmail}} ). So your future business process will be done through this contact only.\n For brief details login into the system.  ','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('EditSpocContact', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Edit SpocContact', 'Notification', 'Edit SpocContact', 'Used for Inviting Owner to  SpocContact', '{{EditSpocContact.OwnerName}} will be Point of contact for {{EditSpocContact.LoginOrgName}}', 'sourcelead', '', '{{EditSpocContact.OwnerName}} will be Point of contact for {{EditSpocContact.LoginOrgName}}','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
