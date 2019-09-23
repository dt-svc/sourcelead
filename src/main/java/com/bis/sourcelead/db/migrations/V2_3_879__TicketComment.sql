CALL AlterTableAddColumn ('TICKET_COMMENT','COMMENT_MENTIONED_USERS','VARCHAR (500) DEFAULT NULL AFTER `COMMENT`');

#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NewTicketCommentEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) 
	VALUES('NewTicketCommentEmail', 'Email', 'NewTicketCommentEmail', 'Email should forward to Assign to and Notify to.', 'Ticket got a new comment on [Category_Name]','org','Best Regards,\n [Login_User]\n [Login_Org]', 'Dear [User_Name],
\nThis is to notify that the ticket got a new comment.\nTicket Title : [Ticket_Title]\nType : [Type]\nCategory : [Category_Name]\nStatus : [Status]\nComment : [Comment]','N', 'SLADMIN', 'slead', 'sleadIN',1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'UserName', '[User_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'TicketTitle', '[Ticket_Title]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'TicketType', '[Type]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'CategoryName', '[Category_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Status', '[Status]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Comment', '[Comment]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'LoginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'LoginOrg', '[Login_Org]');

#Notification for ticket comment
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NewTicketCommentNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('NewTicketCommentNotification', 'Notification', 'NewTicketCommentNotification', 'This notification is forward when the user commented on the ticket.', 'Ticket got a new comment on [Category_Name]', 'org', '', 'Ticket got a new comment on [Category_Name]','N','SLADMIN', 'slead', 'sleadIN',1,'Y');

#timesheet_submission changes
CALL AlterTableRemoveColumn ('TIMESHEET_SUBMISSIONS','SUBMISSION_STATUS');
CALL AlterTableRemoveColumn ('TIMESHEET_SUBMISSIONS','SUBMISSION_APPROVALSTATUS');

ALTER TABLE `TIMESHEET_SUBMISSIONS` DROP FOREIGN KEY `TIMESHEET_SUBMISSIONS_ibfk_2`;
CALL DropColumnIndex('TIMESHEET_SUBMISSIONS','APPROVER_USER_ID');
CALL AlterTableRemoveColumn ('TIMESHEET_SUBMISSIONS','APPROVER_USER_ID');

CALL AlterTableRemoveColumn ('TIMESHEET_SUBMISSIONS','LEVEL_OF_APPROVER');
CALL AlterTableRemoveColumn ('TIMESHEET_SUBMISSIONS','DOC_FILE');
CALL AlterTableRemoveColumn ('TIMESHEET_SUBMISSIONS','FILE_CONTENT_TYPE');
CALL AlterTableRemoveColumn ('TIMESHEET_SUBMISSIONS','FILE_NAME');
CALL AlterTableRemoveColumn ('TIMESHEET_SUBMISSIONS','ESTIMATED_HOURS');