set SQL_SAFE_UPDATES=0;

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('onBehalfNotification', 'Notification', 'onBehalfNotification', 'Used for Submitting Notification to Employee.', 'Leave applied behalf of you', 'sourcelead', '', 'Leave applied behalf of you','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('Share Recommended Applicant Notification', 'Notification', 'Share Recommended Applicant Notification', 'Used for Notifying Candidate about Opening.', 'Opening Job position for {{ShareRecomendedApplicant.jobName}}', 'sourcelead', '', 'Opening Job position for {{ShareRecomendedApplicant.jobName}}','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('HolidayRemainderNotification', 'Notification', 'HolidayRemainderNotification', 'Used for Notifying Employee about Holiday.', 'Holiday on {{HolidayRemainder.date}}', 'sourcelead', '', 'Holiday on {{HolidayRemainder.date}}','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

set SQL_SAFE_UPDATES=1;