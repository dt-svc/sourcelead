#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('updatedRequirementNotificationToVendor', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementTitle', '{{updatedRequirementNotificationToVendor.RequirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementLocation', '{{updatedRequirementNotificationToVendor.RequirementLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementRate', '{{updatedRequirementNotificationToVendor.RequirementRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientContactname', '{{updatedRequirementNotificationToVendor.clientContactname}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Experience', '{{updatedRequirementNotificationToVendor.Experience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'status', '{{updatedRequirementNotificationToVendor.status}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'link', '{{updatedRequirementNotificationToVendor.link}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{updatedRequirementNotificationToVendor.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginOrgName', '{{updatedRequirementNotificationToVendor.LoginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementManagerName', '{{updatedRequirementNotificationToVendor.requirementManagerName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('updated Requirement NotificationToVendor','Email', 'updatedRequirementNotificationToVendor', 'notify to vendor about updated requirement', '{{updatedRequirementNotificationToVendor.RequirementTitle}} details are updated by {{updatedRequirementNotificationToVendor.requirementManagerName}}','org', 'Best Regards,\n{{updatedRequirementNotificationToVendor.LoginUser}}\n{{updatedRequirementNotificationToVendor.LoginOrgName}}.', 'Dear {{updatedRequirementNotificationToVendor.clientContactname}},\n{{updatedRequirementNotificationToVendor.RequirementTitle}} details are updated by {{updatedRequirementNotificationToVendor.requirementManagerName}}.\nThe modified requirement details are below\nRequirement Title :{{updatedRequirementNotificationToVendor.RequirementTitle}}\nRequirement Location :{{updatedRequirementNotificationToVendor.RequirementLocation}}\nRequirement Rate :{{updatedRequirementNotificationToVendor.RequirementRate}}\nExperience :{{updatedRequirementNotificationToVendor.Experience}}\nStatus :{{updatedRequirementNotificationToVendor.status}}\nFor more details login into the system.\n{{updatedRequirementNotificationToVendor.link}}','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('updatedRequirementNotificationToVendor', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('updated Requirement NotificationToVendor', 'Notification', 'updatedRequirementNotificationToVendor', 'notify to vendor about updated requirement', '{{updatedRequirementNotificationToVendor.RequirementTitle}} details are updated by {{updatedRequirementNotificationToVendor.LoginUserName}}.', 'sourcelead', '', '{{updatedRequirementNotificationToVendor.RequirementTitle}} details are updated by {{updatedRequirementNotificationToVendor.LoginUserName}}.','N','SLADMIN', 'com', 'in.com');

