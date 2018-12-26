#for_rejected_leave

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Employee_Name],\n\n We are regret to inform that your leave got rejected for the requested period.\n Leave Type : [Leave_Type_Name]\n  Duration : [From_Date] - [To_Date].\n  Rejected Reason : [Reason]\n\n[Login_URL]'  WHERE `UNIQUE_SCENARIO_NAME`='Rejected Leave' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;
