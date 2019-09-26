CALL AlterTableAddColumn ('USER_ROLE','IS_SUBSCRIPTION','VARCHAR(10)  DEFAULT NULL AFTER `UUID`');

#Email for org unsubscription 
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OrgUnSubscription', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) 
	VALUES('OrgUnSubscription', 'Email', 'OrgUnSubscription', 'Email should forward to org parent user.', 'SourceLead has  declined services.','org','Best Regards,\n SourceLead.', 'Dear [SUBSCRIBE_USER],\n We regret to inform that SourceLead  has terminated its services for [ORG_NAME] of [ORG_LOCATION].\n For any  queries, do get back to our support team. [SUPPORT_LINK] \n','N', 'SLADMIN', 'slead', 'sleadIN',1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'subscribeUser', '[SUBSCRIBE_USER]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'orgLocation', '[ORG_LOCATION]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'orgName', '[ORG_NAME]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'supportLink', '[SUPPORT_LINK]');
