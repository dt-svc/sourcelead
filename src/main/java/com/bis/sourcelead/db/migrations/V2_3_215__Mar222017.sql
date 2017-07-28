SET SQL_SAFE_UPDATES = 0;

#Mail to vendor when client change the requirement status
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ModifiedRequirementStatusMailtoVendorContact', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementManagerName', '{{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementManagerEmail', '{{ModifiedRequirementStatusMailtoVendorContact.requirementManagerEmail}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'vendorContactName', '{{ModifiedRequirementStatusMailtoVendorContact.vendorContactName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'vendorContactEmail', '{{ModifiedRequirementStatusMailtoVendorContact.vendorContactEmail}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementTitle', '{{ModifiedRequirementStatusMailtoVendorContact.requirementTitle}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementStatus', '{{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'logInOrgName', '{{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementClientName', '{{ModifiedRequirementStatusMailtoVendorContact.requirementClientName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementLocation', '{{ModifiedRequirementStatusMailtoVendorContact.requirementLocation}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'requirementRate', '{{ModifiedRequirementStatusMailtoVendorContact.requirementRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reqExp', '{{ModifiedRequirementStatusMailtoVendorContact.reqExp}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
										('ModifiedRequirementStatusMailtoVendorContact', 'Email', 'ModifiedRequirementStatusMailtoVendorContact', 'Modified Requirement Status Mail to Vendor or Contact', '{{ModifiedRequirementStatusMailtoVendorContact.requirementName}} is {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} by {{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}} ({{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}})', 'sourcelead', 'Best Regards \n{{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}}\n{{ModifiedRequirementStatusMailtoVendorContact.requirementManagerEmail}}', 'Dear {{ModifiedRequirementStatusMailtoVendorContact.vendorContactName}},\n&nbsp;&nbsp;&nbsp; {{ModifiedRequirementStatusMailtoVendorContact.requirementTitle}} has {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} by {{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}} ({{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}}). The brief requirement details are below \nRequirement Title : {{ModifiedRequirementStatusMailtoVendorContact.requirementTitle}} \nClient : {{ModifiedRequirementStatusMailtoVendorContact.requirementClientName}} \nRequirement Location : {{ModifiedRequirementStatusMailtoVendorContact.requirementLocation}} \nRequirement Rate : {{ModifiedRequirementStatusMailtoVendorContact.requirementRate}} \nExperience : {{ModifiedRequirementStatusMailtoVendorContact.reqExp}} \nStatus : {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} \nFor more details login into the system \n\n {{Registration.LoginURL}}', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



#Notification to vendor when client change the requirement status
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ModifiedRequirementStatusNotificationtoVendorContact', 'Notification');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('ModifiedRequirementStatusNotificationtoVendorContact', 'Notification', 'ModifiedRequirementStatusNotificationtoVendorContact', 'Modified Requirement Status Notification to Vendor or Contact','org','{{ModifiedRequirementStatusMailtoVendorContact.requirementName}} is {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} by {{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}} ({{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}})','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

SET SQL_SAFE_UPDATES = 1;