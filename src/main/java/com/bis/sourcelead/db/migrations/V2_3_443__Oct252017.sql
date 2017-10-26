set SQL_SAFE_UPDATES=0;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{RejectedLeave.EmployeeName}} 
 We are regret to inform you,your leave got rejected‚ for the requested period. 
 Leave Type : {{RejectedLeave.LeaveTypeName}} 
 Duration : {{RejectedLeave.FromDate}} - {{RejectedLeave.ToDate}}. 
 Rejected Reason : {{RejectedLeave.Reason}}'  WHERE `UNIQUE_SCENARIO_NAME`='Rejected Leave' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;



