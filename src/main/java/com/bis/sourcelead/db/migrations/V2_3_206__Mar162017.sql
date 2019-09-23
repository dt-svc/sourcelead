CALL AlterTableAddColumn ('REFER_CONTACTS','CONTACT_SOURCE','VARCHAR(50) NULL');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('Add New User Contact','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contactName', '{{addNewContact.contactName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'registerLink', '{{addNewContact.registerLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'userName', '{{addNewContact.userName}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('Add New User Contact','Email', 'Add New User Contact', '{{addNewContact.userName}} Referred Sourcelead ', '{{addNewContact.userName}} Referred Sourcelead','org', 'Best Regards,\n{{addNewContact.userName}}.', 'Dear {{addNewContact.contactName}}\n\n I am a user of Sourcelead which is a new recruitment platform to find requirements.If you are intersted get register  \n\n Registration Link : {{addNewContact.registerLink}}\n\n ','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
									
