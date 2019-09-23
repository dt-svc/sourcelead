INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ShortlistMailFromClientToVendor','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorContactName', '{{ShortlistMailFromClientToVendor.vendorContactName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{ShortlistMailFromClientToVendor.requirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'countOfApplicants', '{{ShortlistMailFromClientToVendor.countOfApplicants}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientContactName', '{{ShortlistMailFromClientToVendor.clientContactName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientOrgName', '{{ShortlistMailFromClientToVendor.clientOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{ShortlistMailFromClientToVendor.loginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'shortlistedTable', '{{ShortlistMailFromClientToVendor.shortlistedTable}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('ShortlistMailFromClientToVendor','Email', 'ShortlistMailFromClient_To_Vendor', 'Used to send shortlisted mail from client to vendor', '{{ShortlistMailFromClientToVendor.countOfApplicants}} got shortlisted for {{ShortlistMailFromClientToVendor.requirementTitle}}.','org', 'Best Regards,\n{{ShortlistMailFromClientToVendor.clientContactName}}\n{{ShortlistMailFromClientToVendor.clientOrgName}}.', 'Dear {{ShortlistMailFromClientToVendor.vendorContactName}},\n\n {{ShortlistMailFromClientToVendor.countOfApplicants}} got shortlisted for {{ShortlistMailFromClientToVendor.requirementTitle}}. For brief details login into the system \n\n {{ShortlistMailFromClientToVendor.loginLink}} \n\n {{ShortlistMailFromClientToVendor.shortlistedTable}}','N', 'SLADMIN', 'slead', 'sleadIN');


#shortlistNotificationFromClientToVendor
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ShortlistMailFromClientToVendor', 'Notification');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'countOfApplicants', '{{ShortlistMailFromClientToVendor.countOfApplicants}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ShortlistMailFromClientToVendor', 'Notification', 'ShortlistMailFromClient_To_Vendor', 'Used to notify vendor about candidate shortlist','org','{{ShortlistMailFromClientToVendor.countOfApplicants}} got shortlisted','N', 'SLADMIN', 'com', 'in.com');