#for_approver_leave
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Approver Leave' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');

#withdraw_leave
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Withdraw Leave' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');

#cancel_leave
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'LeaveCancelEmailToUser' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');

#holiday_cron_mail
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'HolidayRemainder' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');



set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Employee_Name],\n\n We are regret to inform that your leave got rejected for the requested period.\n Leave Type : [Leave_Type_Name]\n  Duration : [From_Date] - [To_Date].\n  Rejected Reason : [Reason]\n\n[Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='Rejected Leave' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Approver_Name],\n\nThis is to inform i have withdrawn leave for the period [From_Date] - [To_Date] ,due to \nReason : [Reason].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Withdraw Leave' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Employee_Name],\n\n We are happy to inform you,your leave [From_Date] - [To_Date] got approved.\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Approver Leave' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY` ='Dear [Employee_User],\n\nWe are regret to inform you, your leave  got cancelled for the requested period.\nLeave Type : [Leave_Type]\nDuration : [From_Date] - [To_Date]\n Cancelled Reason : [Reason].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='LeaveCancelEmailToUser' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY` ='Dear [User_Name],\n\nThis is to inform that there will be holiday on [Date] due to [Holidayname].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='HolidayRemainder' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;

