#Cancel Leave Email to User 

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`= 'Best Regards,\n{{LeaveCancelEmailToUser.approverUser}}.' WHERE `UNIQUE_SCENARIO_NAME`='LeaveCancelEmailToUser' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear {{LeaveCancelEmailToUser.employeeUser}},\n We are regret to inform you, your leave got cancelled for the requested period.\n Leave Type : {{LeaveCancelEmailToUser.leaveType}}\n Duration : {{LeaveCancelEmailToUser.fromDate}} to {{LeaveCancelEmailToUser.toDate}}\n Cancelled Reason : {{LeaveCancelEmailToUser.reason}}\n' WHERE `UNIQUE_SCENARIO_NAME`='LeaveCancelEmailToUser' and `ENTITY_TYPE`='Email';
 
