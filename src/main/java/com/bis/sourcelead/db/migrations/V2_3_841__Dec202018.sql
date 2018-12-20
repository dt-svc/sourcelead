#email should forward when the_user_commented_on_the_ticket. 
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('CommentedRaisedTicketEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('CommentedRaisedTicketEmail','Email', 'CommentedRaisedTicketEmail', 'Mail should forward when the user commented on the ticket.', 'New Comment on the [Issue_Id].','org',
'Best Regards,\nSourcelead Team.', 'Dear [Ticket_Owner],\nSourcelead Support team comment on  Ticket id :[Ticket_Id]\nComment : [Latest_Comment]','N', 'SLADMIN', 'slead', 'sleadIN', 3,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'issueId', '[Issue_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'ticketId', '[Ticket_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'ticketOwner', '[Ticket_Owner]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'latestComment', '[Latest_Comment]');

#Notification when the_user_commented_on_the_ticket
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('CommentedRaisedTicketNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('CommentedRaisedTicketNotification', 'Notification', 'CommentedRaisedTicketNotification', 'This notification is forward when the user commented on the ticket.', 'New Comment on the [Issue_Id].', 'org', '', 'New Comment on the [Issue_Id].','N','SLADMIN', 'slead', 'sleadIN', 3,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'issueId', '[Issue_Id]');
