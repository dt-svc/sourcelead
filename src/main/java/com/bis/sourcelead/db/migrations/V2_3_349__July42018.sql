	INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'employeeTerminationmailtomanager' and `ENTITY_TYPE` = 'Email'),
	'loginLink','{{employeeTerminationmailtomanager.loginLink}}');
	
	
		INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'employeeTerminationmailtomanager' and `ENTITY_TYPE` = 'Email'),
	'ProjectName','{{employeeTerminationmailtomanager.ProjectName}}');
	
	
	INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'employeeTerminationmailtomanager' and `ENTITY_TYPE` = 'Email'),
	'ProjectEndDate','{{employeeTerminationmailtomanager.ProjectEndDate}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'employeeReassign' and `ENTITY_TYPE` = 'Email'),
	'location','{{employeeReassign.location}}');
	
	SET SQL_SAFE_UPDATES = 0;			
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Termination Notice'  WHERE `UNIQUE_SCENARIO_NAME`='Employee Termination Mail To Manager' and `ENTITY_TYPE`='Email';
		
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='You are Relieved from {{employeeTermination.ProjectName}} with {{employeeTermination.LoginOrg}}'  WHERE `UNIQUE_SCENARIO_NAME`='Employee Termination' and `ENTITY_TYPE`='Email';
	
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{employeeTerminationmailtomanager.Manager}},\n\nThis is to inform you that the following resources have been terminated from Project\nProject Name: {{employeeTerminationmailtomanager.ProjectName}}\nEmployee: {{employeeTerminationmailtomanager.EmployeeName}}\nLast day: {{employeeTerminationmailtomanager.ProjectEndDate}}\nDetails and reason: {{employeeTerminationmailtomanager.Reason}}\nPlease login to SourceLead to find more details\n\n{{employeeTerminationmailtomanager.loginLink}}'  WHERE `UNIQUE_SCENARIO_NAME`='Employee Termination Mail To Manager' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear {{employeeReassign.EmployeeName}},\n\nThis is to inform you that you are assigned to the following Project work\nThe brief details of the project are following\nProject Start date:{{employeeReassign.ProjectStartDate}}\nProject End date:{{employeeReassign.ProjectEndDate}}\nManager: {{employeeReassign.Manager}}\nLocation: {{employeeReassign.location}}\n\nCongratulations and looking forward to work with you.'  WHERE `UNIQUE_SCENARIO_NAME`='Employee Reassign' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='New Assignment Notice'  WHERE `UNIQUE_SCENARIO_NAME`='Employee Reassign' and `ENTITY_TYPE`='Email';

#Requirement posting


INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
    VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'RequirementPosting' and `ENTITY_TYPE` = 'Email'),
    'positions','{{RequirementPosting.positions}}');
    
    INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
    VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'RequirementPosting' and `ENTITY_TYPE` = 'Email'),
    'priority','{{RequirementPosting.priority}}');
    
    INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
    VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'RequirementPosting' and `ENTITY_TYPE` = 'Email'),
    'description','{{RequirementPosting.description}}');

    INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
    VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'RequirementPosting' and `ENTITY_TYPE` = 'Email'),
    'skills','{{RequirementPosting.skills}}');

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='New Job has been assigned to manage', `BODY`='Dear {{RequirementPosting.Ownername}},\n\nYou have been designated to manage the fallowing Job.\nJob details\nJob Title :{{RequirementPosting.RequirementTitle}}\nJob Location :{{RequirementPosting.RequirementLocation}}\nJob Rate :{{RequirementPosting.RequirementRate}}\nExperience :{{RequirementPosting.Experience}}\nNo of Positions : {{RequirementPosting.positions}}\nPriority : {{RequirementPosting.priority}}\nSkills : {{RequirementPosting.skills}}\nDescription : {{RequirementPosting.description}}\nFor more details login into the system  Login {{RequirementPosting.link}}',`SIGNATURE` = 'Best Regards\n{{RequirementPosting.LoginUser}}.' WHERE `UNIQUE_SCENARIO_NAME`='Requirement Posting' and `ENTITY_TYPE`='Email';





    
 
    #Assign member
    
    INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
    VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'AssignToTeamMembers' and `ENTITY_TYPE` = 'Email'),
    'positions','{{AssignToTeamMembers.positions}}');
    
    INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
    VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'AssignToTeamMembers' and `ENTITY_TYPE` = 'Email'),
    'priority','{{AssignToTeamMembers.priority}}');
    
    INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
    VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'AssignToTeamMembers' and `ENTITY_TYPE` = 'Email'),
    'description','{{AssignToTeamMembers.description}}');

    INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
    VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'AssignToTeamMembers' and `ENTITY_TYPE` = 'Email'),
    'skills','{{AssignToTeamMembers.skills}}');
    
    INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
    VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'AssignToTeamMembers' and `ENTITY_TYPE` = 'Email'),
    'manager','{{AssignToTeamMembers.manager}}');
    
    INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
    VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'AssignToTeamMembers' and `ENTITY_TYPE` = 'Email'),
    'jobTitleId','{{AssignToTeamMembers.jobTitleId}}');
    
    
    
    set SQL_SAFE_UPDATES=0;
    UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Job assigned to you {{AssignToTeamMembers.RequirementTitle}}.', `BODY`='Dear {{AssignToTeamMembers.Ownername}},\n\n{{AssignToTeamMembers.LoginUser}} has assigned you a new open Job {{AssignToTeamMembers.jobTitleId}}\nFollowing are brief requirement details\nJob Title :{{AssignToTeamMembers.RequirementTitle}}\nJob Location :{{AssignToTeamMembers.RequirementLocation}} \nJob Rate :{{AssignToTeamMembers.RequirementRate}}\nExperience :{{AssignToTeamMembers.Experience}}\nNo of Positions : {{RequirementPosting.positions}}\nPriority : {{RequirementPosting.priority}}\nSkills : {{RequirementPosting.skills}}\nDescription : {{RequirementPosting.description}}\nManager :{{AssignToTeamMembers.manager}}\n\nFor more details login into the system\n {{AssignToTeamMembers.link}}',`SIGNATURE`= 'Best Regards\n {{AssignToTeamMembers.LoginUser}}' WHERE `UNIQUE_SCENARIO_NAME`='AssignToTeamMembers' and `ENTITY_TYPE`='Email';

 