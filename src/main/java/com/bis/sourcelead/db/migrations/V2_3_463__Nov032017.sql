#applicant offer accept Mail
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('SpocPointOfContatChangeEmail', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'spocUserName', '{{SpocPointOfContatChangeEmail.spocUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'spocOwnerName', '{{SpocPointOfContatChangeEmail.spocOwnerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'spocUserOrgName', '{{SpocPointOfContatChangeEmail.spocUserOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'spocOwnerOrgName', '{{SpocPointOfContatChangeEmail.spocOwnerOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{SpocPointOfContatChangeEmail.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{SpocPointOfContatChangeEmail.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'spocOldOwnerName', '{{SpocPointOfContatChangeEmail.spocOldOwnerName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('SpocPointOfContatChangeEmail', 'Email', 'SpocPointOfContatChangeEmail','Used when spoc point of contact changed', 'Point of contact changed.','sourcelead','Thank You,\n{{SpocPointOfContatChangeEmail.loginUser}}\n{{SpocPointOfContatChangeEmail.loginOrgName}}','Dear {{SpocPointOfContatChangeEmail.spocUserName}},\n\nYour contact has changed for the org {{SpocPointOfContatChangeEmail.loginOrgName}} from {{SpocPointOfContatChangeEmail.spocOldOwnerName}} to {{SpocPointOfContatChangeEmail.spocOwnerName}}. ', 'N', 'SLADMIN', 'com','in.com');


SET SQL_SAFE_UPDATES=0;
UPDATE `PROJECT_BUDGET` SET `STATUS`='1';
SET SQL_SAFE_UPDATES=1;