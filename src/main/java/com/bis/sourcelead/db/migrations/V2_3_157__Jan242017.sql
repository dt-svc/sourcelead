DROP TABLE IF EXISTS `sourcelead`.`TASKS`;
CREATE TABLE `sourcelead`.`TASKS` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  OWNER_ORGLOCATION_ID BIGINT NOT NULL,
  TASK_OWNER_ID BIGINT NOT NULL,
  ASSIGNED_USER_ID BIGINT NOT NULL,
  PRIORITY VARCHAR(20),
  TASK_DATE DATE,
  TASK_TIME VARCHAR(10),
  SUBJECT VARCHAR(200),
  ALERT_TYPE VARCHAR(20),
  TASK_REPEAT VARCHAR(20),
  DESCRIPTION TEXT DEFAULT NULL,
  TASK_STATUS VARCHAR(10),
  STATUS INTEGER,
  CREATED_DATE DATETIME,
  CREATED_BY VARCHAR(10),
  UPDATED_DATE DATETIME,
  UPDATED_BY VARCHAR(10),
  PRIMARY KEY (`ID`),
  FOREIGN KEY(OWNER_ORGLOCATION_ID) REFERENCES `sourcelead`.`ORG_LOCATIONS`(ID),
  FOREIGN KEY(TASK_OWNER_ID) REFERENCES `sourcelead`.`USER`(ID),
  FOREIGN KEY(ASSIGNED_USER_ID) REFERENCES `sourcelead`.`USER`(ID)
) ;


/*Task mail and Notification*/


#shivayogi

#ReleaseMechanism_Email
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TaskMechanism', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'subject', '{{TaskMechanism.subject}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'priority', '{{TaskMechanism.priority}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'date', '{{TaskMechanism.date}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'time', '{{TaskMechanism.time}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'description', '{{TaskMechanism.description}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'assignedUser', '{{TaskMechanism.assignedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'LoginUser', '{{TaskMechanism.LoginUser}}');
 
#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Task Mechanism', 'Email', 'Task Mechanism', 'Used to assign Task for users', '{{TaskMechanism.subject}}', 'sourcelead', 'Best Regards\n{{TaskMechanism.LoginUser}}', 'Dear\t{{TaskMechanism.assignedUser}},\n\nPriority:{{TaskMechanism.priority}}.\nDate:{{TaskMechanism.date}}.\nTime:{{TaskMechanism.time}}.\nDescription:{{TaskMechanism.description}}\n', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



#ReleaseMechanism_Notification
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TaskMechanism', 'Notification');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'subject', '{{TaskMechanism.subject}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Task Mechanism', 'Notification', 'Task Mechanism', 'Used to assign Task for users','org','{{TaskMechanism.subject}}','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');




