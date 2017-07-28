#JobAlerts Email

#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('JobAlerts', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'alertJobsCount', '{{JobAlerts.alertJobsCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'alertTitle', '{{JobAlerts.alertTitle}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'alertUserName', '{{JobAlerts.alertUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'alertJobs', '{{JobAlerts.alertJobs}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('JobAlerts', 'Email', 'JobAlerts', 'Jobs matching your alert {{JobAlerts.alertTitle}} ', '{{JobAlerts.alertJobsCount}} new jobs matching your alert - {{JobAlerts.alertTitle}}', 'sourcelead', 'Wish you a good luck in job search.\n\nBest Regards,\nSourcelead Team','Dear {{JobAlerts.alertUserName}},\n\nHere are new jobs matching the criteria set by you in "{{JobAlerts.alertTitle}}".\n\n{{JobAlerts.alertJobs}}\n ', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');


CALL sourcelead.AlterTableAddColumn ('sourcelead','JOB_ALERTS','JOB_ALERT_UUID','VARCHAR(20) NULL  AFTER `STATUS`');