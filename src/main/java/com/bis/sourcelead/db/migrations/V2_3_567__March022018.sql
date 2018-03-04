CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_REQUEST','ORG_TYPE','VARCHAR(50) NULL AFTER `ID`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_REQUEST','DEMO_DATE','DATE NULL AFTER `COUNTRY`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_REQUEST','DEMO_TIME','VARCHAR(50) NULL AFTER `DEMO_DATE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_REQUEST','DEMO_TIME_ZONE','BIGINT NULL AFTER `DEMO_TIME`');
ALTER TABLE `sourcelead`.`ORG_REQUEST` ADD CONSTRAINT `FK1_TIME_ZONE_ID`  FOREIGN KEY  (`DEMO_TIME_ZONE`)  REFERENCES  `sourcelead`.`TIMEZONE` (`ID`);



#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'companyName', '{{orgDemo.companyName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'companyWebsite', '{{orgDemo.companyWebsite}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'companyLocation', '{{orgDemo.companyLocation}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'companyEmail', '{{orgDemo.companyEmail}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'companyEmail', '{{orgDemo.companyEmail}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'demoDate', '{{orgDemo.demoDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'demoTime', '{{orgDemo.demoTime}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'demoTimeZone', '{{orgDemo.demoTimeZone}}');



#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('orgDemoEmail','Email', 'orgDemoEmail', 'orgDemoEmail mail to Sourcelead', ' New demo request from {{orgDemo.companyName}}','org', 'Best Regards,\nSourceLead Team.', 'Dear Sourcelead ,\n We have new demo request from {{orgDemo.companyName}} organization, kindly go through brief details \n Website : {{orgDemo.companyWebsite}} \n Location : {{orgDemo.companyLocation}} \n Email id : {{orgDemo.companyEmail}} \n Date & Time : {{orgDemo.demoDate}} {{orgDemo.demoTime}} \n Timezone  : {{orgDemo.demoTimeZone}} \n Verify the Organization details and generate.','N', 'SLADMIN', 'slead', 'sleadIN');
			
			
#Notification
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('orgDemoNotification', 'Notification', 'orgDemoNotification', ' orgDemoNotification Notification to Sourcelead', '','sourcelead', '', ' New Demo request from {{orgDemo.companyName}} organization','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
