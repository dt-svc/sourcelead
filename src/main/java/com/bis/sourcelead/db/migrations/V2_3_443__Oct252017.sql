

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{RejectedLeave.EmployeeName}},\n\n We are regret to inform that your leave got rejected for the requested period.\n Leave Type : {{RejectedLeave.LeaveTypeName}}\n  Duration : {{RejectedLeave.FromDate}} - {{RejectedLeave.ToDate}}.\n  Rejected Reason : {{RejectedLeave.Reason}}'  WHERE `UNIQUE_SCENARIO_NAME`='Rejected Leave' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;



