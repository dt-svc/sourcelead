

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
			('LCAManagerExpiresInDaysNotification','Notification', 'LCAManagerExpiresInDaysNotification', 'LCA Manager Expires In Days Notification', 'NULL','org', 'NULL', '[No_Of_Lcas] LCA/s will be Expired in [Expiring_Days].','N', 'SLADMIN', 'slead', 'sleadIN', 23,1,'Y');
			
			
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'No_Of_Lcas', '[No_Of_Lcas]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Expiring_Days', '[Expiring_Days]');
