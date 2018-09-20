set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET  `BODY`='{{connectionInvitation.loginUserName}} invited you to connect' WHERE `UNIQUE_SCENARIO_NAME`='connectionInvitation' and `ENTITY_TYPE`='Notification';

UPDATE `EMAIL_SCENARIO` SET  `BODY`='Dear {{connectionInvitation.receiverUserName}},\nHere is an outline of your recent invitation/s to connect.\nConnection Details :\n{{connectionInvitation.profilePicUrl}}' WHERE `UNIQUE_SCENARIO_NAME`='connectionInvitation' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;

INSERT INTO `SOURCELEAD_FORM_FIELDS` (`ID`, `SECTION_ID`, `LABEL_NAME`, `FIELD_NAME`, `IS_FIELD_REQUIRED`, `STATUS`, `IS_IMPORT_FIELD`) VALUES ('223', '3', 'Status', 'requirementSubmissionStatus', '0', '0', '1');
