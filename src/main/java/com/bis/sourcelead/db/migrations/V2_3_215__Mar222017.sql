SET SQL_SAFE_UPDATES = 0;

#Mail to vendor when client change the requirement status
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ModifiedRequirementStatusMailtoVendorContact', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementManagerName', '{{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementManagerEmail', '{{ModifiedRequirementStatusMailtoVendorContact.requirementManagerEmail}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorContactName', '{{ModifiedRequirementStatusMailtoVendorContact.vendorContactName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorContactEmail', '{{ModifiedRequirementStatusMailtoVendorContact.vendorContactEmail}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{ModifiedRequirementStatusMailtoVendorContact.requirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementStatus', '{{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'logInOrgName', '{{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementClientName', '{{ModifiedRequirementStatusMailtoVendorContact.requirementClientName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementLocation', '{{ModifiedRequirementStatusMailtoVendorContact.requirementLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementRate', '{{ModifiedRequirementStatusMailtoVendorContact.requirementRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reqExp', '{{ModifiedRequirementStatusMailtoVendorContact.reqExp}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
										('ModifiedRequirementStatusMailtoVendorContact', 'Email', 'ModifiedRequirementStatusMailtoVendorContact', 'Modified Requirement Status Mail to Vendor or Contact', '{{ModifiedRequirementStatusMailtoVendorContact.requirementName}} is {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} by {{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}} ({{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}})', 'sourcelead', 'Best Regards \n{{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}}\n{{ModifiedRequirementStatusMailtoVendorContact.requirementManagerEmail}}', 'Dear {{ModifiedRequirementStatusMailtoVendorContact.vendorContactName}},\n&nbsp;&nbsp;&nbsp; {{ModifiedRequirementStatusMailtoVendorContact.requirementTitle}} has {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} by {{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}} ({{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}}). The brief requirement details are below \nRequirement Title : {{ModifiedRequirementStatusMailtoVendorContact.requirementTitle}} \nClient : {{ModifiedRequirementStatusMailtoVendorContact.requirementClientName}} \nRequirement Location : {{ModifiedRequirementStatusMailtoVendorContact.requirementLocation}} \nRequirement Rate : {{ModifiedRequirementStatusMailtoVendorContact.requirementRate}} \nExperience : {{ModifiedRequirementStatusMailtoVendorContact.reqExp}} \nStatus : {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} \nFor more details login into the system \n\n {{Registration.LoginURL}}', 'N', 'SLADMIN', 'com', 'in.com');



#Notification to vendor when client change the requirement status
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ModifiedRequirementStatusNotificationtoVendorContact', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('ModifiedRequirementStatusNotificationtoVendorContact', 'Notification', 'ModifiedRequirementStatusNotificationtoVendorContact', 'Modified Requirement Status Notification to Vendor or Contact','org','{{ModifiedRequirementStatusMailtoVendorContact.requirementName}} is {{ModifiedRequirementStatusMailtoVendorContact.requirementStatus}} by {{ModifiedRequirementStatusMailtoVendorContact.requirementManagerName}} ({{ModifiedRequirementStatusMailtoVendorContact.logInOrgName}})','N', 'SLADMIN', 'com', 'in.com');

SET SQL_SAFE_UPDATES = 1;