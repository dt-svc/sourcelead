#email should forward configured emailIds for candidate bulkimport to login users. 
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('CandidateBulkImport', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('CandidateBulkImport','Email', 'CandidateBulkImport', 'Mail should forward to Generate User.', 'File exceeds 2 Mb size you imported','sourcelead',
'Best Regards,\nSourcelead Team.', 'Dear [User_Name],\nThis is to notify that you have imported the candidate(s) file which exceeds more than 2 MB, Sourcelead Support \nwill take care of that & import the file in your organisation. Once the file got imported successfully, you will get notified. \nKindly be patience until we get back to you.\nThank You for associating with Sourcelead.','N', 'SLADMIN', 'slead', 'sleadIN', 1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'userName', '[User_Name]');


#email should forward configured emailIds for candidate bulkimport to support. 
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('CandidateBulkImportSupport', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('CandidateBulkImportSupport','Email', 'CandidateBulkImportSupport', 'Mail should forward to Sourcelead Support.', 'Import Request from the [Org_Name] ','sourcelead',
'Best Regards,\nSourcelead Team.', 'Dear Sourcelead Support,\nThis is to notify that [Org_Name] requested to import the candidates.\nRequested User : [User_Name] \nFile Size : [File_Size] Mb \n Kindly  make it done ASAP.','N', 'SLADMIN', 'slead', 'sleadIN', 1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'orgName', '[Org_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'userName', '[User_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'fileSize', '[File_Size]');

