#connectionInvitation_Email
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('connectionInvitation', 'Email');

#connectionInvitation_Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('connectionInvitation', 'Notification');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverEmail', '{{connectionInvitation.receiverEmail}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{connectionInvitation.loginUserName}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverUserName', '{{connectionInvitation.receiverUserName}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'profilePicUrl', '{{connectionInvitation.profilePicUrl}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'receiverRole', '{{connectionInvitation.receiverRole}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'company', '{{connectionInvitation.company}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{connectionInvitation.loginLink}}');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('connectionInvitation','Email', 'connectionInvitation', 'connectionInvitation mail to user', '{{connectionInvitation.loginUserName}} invited you to connect','org', '', 'Dear {{connectionInvitation.receiverUserName}},\nHere is an outline of your recent invitation/s to connect.\nConnection Details : \n\n {{connectionInvitation.profilePicUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');
			
			
			
			
#Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('connectionInvitation', 'Notification', 'connectionInvitation', ' connectionInvitation Notification to user', '','sourcelead', '', '{{connectionInvitation.loginUserName}} invited you to connect','N','SLADMIN', 'com', 'in.com');
