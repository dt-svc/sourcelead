#Edit Misspalled Scenario Name In Email Scenario
SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `SCENARIO_NAME` = 'contractExpiryReminder' WHERE UNIQUE_SCENARIO_NAME = 'contractExpiryRemainder';
UPDATE `EMAIL_SCENARIO` SET `SCENARIO_NAME` = 'HolidayReminder' WHERE UNIQUE_SCENARIO_NAME = 'HolidayRemainder';
UPDATE `EMAIL_SCENARIO` SET `SCENARIO_NAME` = 'HolidayReminderNotification' WHERE UNIQUE_SCENARIO_NAME = 'HolidayRemainderNotification';
UPDATE `EMAIL_SCENARIO` SET `SCENARIO_NAME` = 'ApplyLeaveReminder' WHERE UNIQUE_SCENARIO_NAME = 'ApplyLeaveRemainder';
SET SQL_SAFE_UPDATES = 1;