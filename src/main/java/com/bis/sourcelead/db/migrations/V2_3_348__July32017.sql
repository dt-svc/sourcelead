INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'employeeTermination' and `ENTITY_TYPE` = 'Email'),
	'clientName','{{employeeTermination.clientName}}');
	
	INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'employeeTermination' and `ENTITY_TYPE` = 'Email'),
	'reason','{{employeeTermination.reason}}');
	
	INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'employeeTermination' and `ENTITY_TYPE` = 'Email'),
	'loginLink','{{employeeTermination.loginLink}}');
	
SET SQL_SAFE_UPDATES = 0;			
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{employeeTermination.EmployeeName}},\nThis is to inform you that you are relieved from {{employeeTermination.ProjectName}}  {{employeeTermination.clientName}}\nPlease find the details\nProject Name : {{employeeTermination.ProjectName}}\nLast Day : {{employeeTermination.ProjectEndDate}}\nReason : {{employeeTermination.reason}}\nPlease contact your manager for further details or login to SourceLead\n\n{{employeeTermination.loginLink}}'  WHERE `UNIQUE_SCENARIO_NAME`='Employee Termination' and `ENTITY_TYPE`='Email';