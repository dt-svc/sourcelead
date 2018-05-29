#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('clientOwnerUpdateMail', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgContactName', '{{clientOwnerUpdateMail.orgContactName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientOrgName', '{{clientOwnerUpdateMail.clientOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ownerName', '{{clientOwnerUpdateMail.ownerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{clientOwnerUpdateMail.loginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{clientOwnerUpdateMail.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'changedOwnerName', '{{clientOwnerUpdateMail.changedOwnerName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('client Owner UpdateMail','Email', 'clientOwnerUpdateMail', 'client owner update mail to updated owner', 'You are the assigned as a owner for {{clientOwnerUpdateMail.clientOrgName}}-{{clientOwnerUpdateMail.orgContactName}}','org', 'Best Regards,\n{{clientOwnerUpdateMail.loginUser}}.', 'Dear {{clientOwnerUpdateMail.changedOwnerName}},\nCongratualtions!! You are assigned as a Point of contact for the {{clientOwnerUpdateMail.orgContactName}} from {{clientOwnerUpdateMail.clientOrgName}}\nFor brief details login into the system.\n{{clientOwnerUpdateMail.loginLink}}','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('clientOwnerUpdateMail', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('client Owner UpdateMail', 'Notification', 'clientOwnerUpdateMail', 'client owner update mail to updated owner', 'You are the assigned as a owner for {{clientOwnerUpdateMail.clientOrgName}}-{{clientOwnerUpdateMail.orgContactName}}', 'sourcelead', '', 'You are the assigned as a owner for {{clientOwnerUpdateMail.clientOrgName}} - {{clientOwnerUpdateMail.orgContactName}}','N','SLADMIN', 'com', 'in.com');




