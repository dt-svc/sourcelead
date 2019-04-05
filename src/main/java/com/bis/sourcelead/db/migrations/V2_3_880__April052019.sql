set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Approver_Name],\n Request you to approve the payroll for [Frequency] duration.\n For more information go with the following details.\n Batch : [Get_The_Batch_Name]\n No of Associates : [Get_The_Count_No_Of_Associates]\n Amount :[Get_The_Total_Amount]\n [Login]' WHERE `UNIQUE_SCENARIO_NAME`='sendMailToIndPayrollApprover';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Request_User_name],\n Payroll got approved for the [frequency] duration.\n For more information go with the following details.\n Batch : [Get_The_Batch_Name]\n No of Associates : [Get_The_Count_No_Of_Associates]\n Amount :[Get_The_Total_Amount]\n [Login]' WHERE `UNIQUE_SCENARIO_NAME`='sendApprovedMailToRequestedUserInd';

#Mail To Manager At Timesheet Submission
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Mail To Manager At Timesheet Submission' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi [Manager_Full_Name],\n[Login_User_Full_Name] have submitted timesheet for approval from [Time_Frequency].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Mail To Manager At Timesheet Submission';

#Timesheet Rejected By Approver
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Timesheet Rejected By Approver' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi [Employee_First_Name] [Employee_Last_Name],\nYour timesheet has been rejected for the [Time_Frequency].\nReason : [Rejected_Reason].\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Timesheet Rejected By Approver';

#Timesheet Approved By Approver
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` where UNIQUE_SCENARIO_NAME = 'Timesheet Approved By Approver' and `ENTITY_TYPE` = 'Email' and `IS_TEMPLATE_MODIFIED` = 'N'), 'loginURL', '[Login_URL]');
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Employee_First_Name] [Employee_Last_Name],\nThe timesheet for [Project_Name] for the [Frequency_Type], [Time_Frequency] has been approved.\n\n[Login_URL]' WHERE `UNIQUE_SCENARIO_NAME`='Timesheet Approved By Approver';
set SQL_SAFE_UPDATES=1;