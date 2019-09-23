INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'ChangeReportingManagerNotification' and `ENTITY_TYPE` = 'Notification'), 'PreviousReportingManager', '[Previous_Reporting_Manager]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'ChangeReportingManagerNotification' and `ENTITY_TYPE` = 'Notification'), 'NewReportingManager', '[New_Reporting_Manager]');

SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Your reporting manager has been changed from [Previous_Reporting_Manager] to [New_Reporting_Manager].' WHERE `UNIQUE_SCENARIO_NAME` = 'ChangeReportingManagerNotification' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES = 1;

