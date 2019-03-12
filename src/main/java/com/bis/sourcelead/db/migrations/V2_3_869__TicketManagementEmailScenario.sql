#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NewTicketEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) 
	VALUES('NewTicketEmail', 'Email', 'NewTicketEmail', 'Email should forward to Assign to and Notify to.', 'New ticket generated on [Category_Name]','org','Best Regards,\n [Login_User]\n [Login_Org]', 'Dear [User_Name],
\nThis is to notify that new ticket generated and assigned to you.\nTicket Title : [Ticket_Title]\nTicket Id : [Ticket_Id]\nCategory Name : [Category_Name]\nType : [Type]\nPriority : [Priority]\n [Login]\n','N', 'SLADMIN', 'slead', 'sleadIN',1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'UserName', '[User_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'TicketTitle', '[Ticket_Title]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'TicketId', '[Ticket_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'CategoryName', '[Category_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'TicketType', '[Type]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Priority', '[Priority]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Login', '[Login]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'LoginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'LoginOrg', '[Login_Org]');

#Notification for ticket management
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NewTicketNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('NewTicketNotification', 'Notification', 'NewTicketNotification', 'This notification is forward when the user commented on the ticket.', 'New ticket generated on [Category_Name] category..', 'org', '', 'New ticket generated on [Category_Name] category','N','SLADMIN', 'slead', 'sleadIN',1,'Y');

#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ChangeTicketStatusEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) 
	VALUES('ChangeTicketStatusEmail', 'Email', 'ChangeTicketStatusEmail', 'Email should forward  to Notify Users.', 'Ticket status got updated','org','Best Regards,\n [Login_User]\n [Login_Org]', 'Dear [User_Name],
\nThis is notify that status of ticket got updated from [PREVIOUS_STATUS] to [NEW_STATUS]
\nTicket Title : [Ticket_Title]\nTicket Id : [Ticket_Id]\nCategory Name : [Category_Name]\nType : [Type]\nPriority : [Priority]\n [Login]\n','N', 'SLADMIN', 'slead', 'sleadIN',1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'UserName', '[User_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'TicketTitle', '[Ticket_Title]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'TicketId', '[Ticket_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'CategoryName', '[Category_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'TicketType', '[Type]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Priority', '[Priority]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'PreviousStatus', '[PREVIOUS_STATUS]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'NewStatus', '[NEW_STATUS]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Login', '[Login]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'LoginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'LoginOrg', '[Login_Org]');

#Notification for ticket management
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ChangeTicketStatusNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('ChangeTicketStatusNotification', 'Notification', 'ChangeTicketStatusNotification', 'This notification is forwarded when the user update the ticket status.', 'Ticket id Ticket status got updated from [PREVIOUS_STATUS] to [NEW_STATUS]', 'org', '', 'Ticket Status update','N','SLADMIN', 'slead', 'sleadIN',1,'Y');



CALL AlterTableAddColumn ('ASSOCIATE_PF_ESI_DETAILS','AADHAR_NUMBER','VARCHAR (50)  DEFAULT NULL AFTER `ENROLLED_DATE`');