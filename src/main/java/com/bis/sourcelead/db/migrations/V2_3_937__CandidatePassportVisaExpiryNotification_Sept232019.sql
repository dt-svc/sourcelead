-- Passport expired

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('PassportExpiredNotification', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
			('PassportExpiredNotification','Notification', 'PassportExpiredNotification', 'Passport Expired Notification', '[Passport_Count] Passport(s) Expire(s) today.','org', '', 'Hi [Candidate_Passport_User],\n This is to notify that [Passport_Count] Passport(s) expire(s) today. ','N', 'SLADMIN', 'slead', 'sleadIN',1,'Y');


INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Passport_Count', '[Passport_Count]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Candidate_Passport_User', '[Candidate_Passport_User]');



-- Passport expired in Days 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('PassportExpiresInDaysNotification', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
			('PassportExpiresInDaysNotification','Notification', 'PassportExpiresInDaysNotification', 'Passport Expires In Days Notification', '[Passport_Count] Passport(s) Expiry Date','org', '', 'Hi [Candidate_Passport_User],\nThis is to notify that [Passport_Count] Passport(s) will expire in [Expires_In_Months_Count]([Expires_In_Days_Count] Day(s)) ','N', 'SLADMIN', 'slead', 'sleadIN',1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Passport_Count', '[Passport_Count]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Candidate_Passport_User', '[Candidate_Passport_User]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Expires_In_Months_Count', '[Expires_In_Months_Count]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Expires_In_Days_Count', '[Expires_In_Days_Count]');

			


-- Visa expired

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VisaExpiredNotification', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
			('VisaExpiredNotification','Notification', 'VisaExpiredNotification', 'Visa Expired Notification', '[Visa_Count] Visa(s) Expire(s) today.','org', '', 'Hi [Candidate_Visa_User],\n This is to notify that [Visa_Count] Visa(s) expire(s) today. ','N', 'SLADMIN', 'slead', 'sleadIN',1,'Y');


INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Visa_Count', '[Visa_Count]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Candidate_Visa_User', '[Candidate_Visa_User]');



-- Visa expired in Days 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('VisaExpiresInDaysNotification', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
			('VisaExpiresInDaysNotification','Notification', 'VisaExpiresInDaysNotification', 'Visa Expires In Days Notification', '[Visa_Count] Visa(s) Expiry Date','org', '', 'Hi [Candidate_Visa_User],\nThis is to notify that [Visa_Count] Visa(s) will expire in [Expires_In_Months_Count]([Expires_In_Days_Count] Day(s)) ','N', 'SLADMIN', 'slead', 'sleadIN',1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Visa_Count', '[Visa_Count]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Candidate_Visa_User', '[Candidate_Visa_User]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Expires_In_Months_Count', '[Expires_In_Months_Count]');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Expires_In_Days_Count', '[Expires_In_Days_Count]');
