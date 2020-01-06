INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) 
VALUES ('backgroundcheckmailtovendorcontact', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) 
VALUES ('backgroundcheckmailtovendorcontact','Email', 'backgroundcheckmailtovendorcontact', 'background check mail', 'Candidate Assigned for Background Verification.','org', 'Best Regards \n [Org_Name]', 'Hi [Vendor_Contact_User],\n This is to notify that [Candidate_Name] has been assigned to you for \n background verification. To view more details, please login [Login_Link]','N', 'SLADMIN', 'slead', 'sleadIN',1,'Y');


INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) 
VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` LIKE 'backgroundcheckmailtovendorcontact'), 'Candidate_Name', '[Candidate_Name]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) 
VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` LIKE 'backgroundcheckmailtovendorcontact'), 'Vendor_Contact_User', '[Vendor_Contact_User]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) 
VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` LIKE 'backgroundcheckmailtovendorcontact'), 'Login_Link', '[Login_Link]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) 
VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` LIKE 'backgroundcheckmailtovendorcontact'), 'Org_Name', '[Org_Name]');
