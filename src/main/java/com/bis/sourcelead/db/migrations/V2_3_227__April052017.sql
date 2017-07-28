#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('updatedRequirementEmailToManager', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'RequirementTitle', '{{updatedRequirementEmailToManager.RequirementTitle}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'RequirementLocation', '{{updatedRequirementEmailToManager.RequirementLocation}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'RequirementRate', '{{updatedRequirementEmailToManager.RequirementRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientContactname', '{{updatedRequirementEmailToManager.clientContactname}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'Experience', '{{updatedRequirementEmailToManager.Experience}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'status', '{{updatedRequirementEmailToManager.status}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'link', '{{updatedRequirementEmailToManager.link}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'LoginUser', '{{updatedRequirementEmailToManager.LoginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'LoginOrgName', '{{updatedRequirementEmailToManager.LoginOrgName}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('updated Requirement EmailToManager','Email', 'updatedRequirementEmailToManager', 'notify to manager about updated requirement', '{{updatedRequirementEmailToManager.RequirementTitle}} details are updated by {{updatedRequirementEmailToManager.LoginUser}}','org', 'Best Regards,\n{{updatedRequirementEmailToManager.LoginUser}}\n{{updatedRequirementEmailToManager.LoginOrgName}}.', 'Dear {{updatedRequirementEmailToManager.clientContactname}},\n\n{{updatedRequirementEmailToManager.RequirementTitle}} details are updated by {{updatedRequirementEmailToManager.LoginUser}}.\nThe modified requirement details are below,\nRequirement Title :{{updatedRequirementEmailToManager.RequirementTitle}}\nRequirement Location :{{updatedRequirementEmailToManager.RequirementLocation}}\nRequirement Rate :{{updatedRequirementEmailToManager.RequirementRate}}\nExperience :{{updatedRequirementEmailToManager.Experience}}\nStatus :{{updatedRequirementEmailToManager.status}}\n\nFor more details login into the system.\n{{updatedRequirementEmailToManager.link}}','N', 'SLADMIN', 'slead', 'sleadIN');

#Notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('updatedRequirementEmailToManager', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('updated Requirement EmailToManager', 'Notification', 'updatedRequirementNotificationToManager', 'notify to manager about updated requirement', '{{updatedRequirementEmailToManager.RequirementTitle}} details are updated by {{updatedRequirementEmailToManager.LoginUser}}.', 'sourcelead', '', '{{updatedRequirementEmailToManager.RequirementTitle}} details are updated by {{updatedRequirementEmailToManager.LoginUser}}.','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

