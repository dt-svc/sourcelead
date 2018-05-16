CALL AlterTableAddColumn ('ORG_REQUEST','ORG_TYPE','VARCHAR(50) NULL AFTER `ID`');
CALL AlterTableAddColumn ('ORG_REQUEST','DEMO_DATE','DATE NULL AFTER `COUNTRY`');
CALL AlterTableAddColumn ('ORG_REQUEST','DEMO_TIME','VARCHAR(50) NULL AFTER `DEMO_DATE`');
CALL AlterTableAddColumn ('ORG_REQUEST','DEMO_TIME_ZONE','BIGINT NULL AFTER `DEMO_TIME`');
ALTER TABLE `ORG_REQUEST` ADD CONSTRAINT `FK1_TIME_ZONE_ID`  FOREIGN KEY  (`DEMO_TIME_ZONE`)  REFERENCES  `TIMEZONE` (`ID`);

CALL AlterTableAddColumn ('ORG_REQUEST','REQUEST_STATUS','VARCHAR(50) NULL AFTER `STATUS`');


#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyName', '{{orgDemo.companyName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyWebsite', '{{orgDemo.companyWebsite}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyLocation', '{{orgDemo.companyLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyEmail', '{{orgDemo.companyEmail}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyEmail', '{{orgDemo.companyEmail}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'demoDate', '{{orgDemo.demoDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'demoTime', '{{orgDemo.demoTime}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'demoTimeZone', '{{orgDemo.demoTimeZone}}');



#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('orgDemoEmail','Email', 'orgDemoEmail', 'orgDemoEmail mail to Sourcelead', ' New demo request from {{orgDemo.companyName}}','org', 'Best Regards,\nSourceLead Team.', 'Dear Sourcelead ,\n We have new demo request from {{orgDemo.companyName}} organization, kindly go through brief details \n Website : {{orgDemo.companyWebsite}} \n Location : {{orgDemo.companyLocation}} \n Email id : {{orgDemo.companyEmail}} \n Date & Time : {{orgDemo.demoDate}} {{orgDemo.demoTime}} \n Timezone  : {{orgDemo.demoTimeZone}} \n Verify the Organization details and generate.','N', 'SLADMIN', 'slead', 'sleadIN');
			
			
#Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('orgDemoNotification', 'Notification', 'orgDemoNotification', ' orgDemoNotification Notification to Sourcelead', '','sourcelead', '', ' New Demo request from {{orgDemo.companyName}} organization','N','SLADMIN', 'com', 'in.com');




DROP TABLE IF EXISTS `SOURCELEAD_TICKETS`;
CREATE TABLE `SOURCELEAD_TICKETS` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  TICKET_GENERATED_ID VARCHAR(500) DEFAULT NULL,
  TICKET_TITLE VARCHAR(500) DEFAULT NULL,
  TICKET_TYPE VARCHAR(250) DEFAULT NULL,
  TICKET_URL TEXT DEFAULT NULL,
  TICKET_DESCRIPTION TEXT DEFAULT NULL,
  STATUS INT DEFAULT NULL,
  TICKET_STATUS VARCHAR(250) DEFAULT NULL,
  TICKET_UUID VARCHAR(50) DEFAULT NULL,
  TAB_ID BIGINT,
  ORGLOCATION_ID BIGINT,
  USER_ID BIGINT,
  CREATED_DATE DATE,
  CREATED_BY BIGINT,
  UPDATED_DATE DATE,
  UPDATED_BY BIGINT,
  
  PRIMARY KEY (`ID`),
  FOREIGN KEY(ORGLOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID),
  FOREIGN KEY(USER_ID) REFERENCES `USER`(ID),
  FOREIGN KEY(CREATED_BY) REFERENCES `USER`(ID),
  FOREIGN KEY(UPDATED_BY) REFERENCES `USER`(ID),
  FOREIGN KEY(TAB_ID) REFERENCES `TABS`(ID)
) ;

DROP TABLE IF EXISTS `SOURCELEAD_TICKETS_ATTACHMENTS`;
CREATE TABLE `SOURCELEAD_TICKETS_ATTACHMENTS` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  TICKET_ID BIGINT,
  ATTACHMENT_UUID VARCHAR(50) DEFAULT NULL,
  FILE_URL TEXT DEFAULT NULL,
  FILE_NAME VARCHAR(250) DEFAULT NULL,
  FILE_TYPE VARCHAR(250) DEFAULT NULL,
  STATUS INT DEFAULT NULL,
  CREATED_DATE DATE,
  CREATED_BY BIGINT,
  UPDATED_DATE DATE,
  UPDATED_BY BIGINT,
  
  PRIMARY KEY (`ID`),
  FOREIGN KEY(TICKET_ID) REFERENCES `SOURCELEAD_TICKETS`(ID),
  FOREIGN KEY(CREATED_BY) REFERENCES `USER`(ID),
  FOREIGN KEY(UPDATED_BY) REFERENCES `USER`(ID)
) ;



DROP TABLE IF EXISTS `SOURCELEAD_TICKETS_COMMENTS`;
CREATE TABLE `SOURCELEAD_TICKETS_COMMENTS` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  TICKET_ID BIGINT,
  TICKET_COMMENT_DESCRIPTION TEXT DEFAULT NULL,
  STATUS INT DEFAULT NULL,
  CREATED_DATE DATETIME,
  CREATED_BY BIGINT,
  UPDATED_DATE DATE,
  UPDATED_BY BIGINT,
  
  PRIMARY KEY (`ID`),
  FOREIGN KEY(TICKET_ID) REFERENCES `SOURCELEAD_TICKETS`(ID),
  FOREIGN KEY(CREATED_BY) REFERENCES `USER`(ID),
  FOREIGN KEY(UPDATED_BY) REFERENCES `USER`(ID)
) ;



#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyName', '{{sourceladTicket.companyName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'tikcetTitle', '{{sourceladTicket.tikcetTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ticketType', '{{sourceladTicket.ticketType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ticketModule', '{{sourceladTicket.ticketModule}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ticketLoginUrl', '{{sourceladTicket.ticketLoginUrl}}');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('sourceleadTicketEmail','Email', 'sourceleadTicketEmail', 'sourceleadTicketEmail mail to Sourcelead', ' New Ticket Request from {{sourceladTicket.companyName}}','org', 'Best Regards,\nSourceLead Team.', 'Dear Sourceleadadmin ,\n This is to notify that {{sourceladTicket.companyName}}  has requested a new ticket . The brief details of the ticket are following \n Title : {{sourceladTicket.tikcetTitle}} \n Type : {{sourceladTicket.ticketType}} \n Module : {{sourceladTicket.ticketModule}} \n Url : {{sourceladTicket.ticketLoginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');
			
			
#Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('sourceleadTicketNotification', 'Notification', 'sourceleadTicketNotification', ' sourceleadTicketNotification Notification to Sourcelead', '','sourcelead', '', ' New Ticket Request from {{sourceladTicket.companyName}}','N','SLADMIN', 'com', 'in.com');
