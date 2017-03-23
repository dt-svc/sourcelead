SET SQL_SAFE_UPDATES = 0;

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{RequirementPosting.Ownername}},\n\n{{RequirementPosting.JobInitiator}} assigned you as a manager for the requirement.\n\n The brief requirement details are below \n\n Requirement Title :{{RequirementPosting.RequirementTitle}} \n\n Requirement Location :{{RequirementPosting.RequirementLocation}} \n\n Requirement Rate :{{RequirementPosting.RequirementRate}} \n\n Experience :{{RequirementPosting.Experience}} \n\n For more details login into the system  Login {{RequirementPosting.link}}' WHERE `UNIQUE_SCENARIO_NAME`='Requirement Posting' and `ENTITY_TYPE`='Email';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='rejected the requirement {{RejectedMailAboutTheRequirement.approverName}}' WHERE `UNIQUE_SCENARIO_NAME`='Rejected Mail About The Requirement' and `ENTITY_TYPE`='Email';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='{{AssignToTeamMembers.RequirementTitle}} requirement assigned to you' WHERE `UNIQUE_SCENARIO_NAME`='AssignToTeamMembers' and `ENTITY_TYPE`='Email';

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='New requirement assigned to you.' WHERE `ID`='45';

#inviteuser Email
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{InviteUser.FirstName}} {{InviteUser.LastName}},\n You have been invited to SourceLead as a {{InviteUser.Role}}.\nYou can simply login into SourceLead by clicking below link with following credentials\n\n Username : {{InviteUser.UserEmailId}} \n Password : {{InviteUser.Password}}\n\n {{InviteUser.LoginURL}}' WHERE `UNIQUE_SCENARIO_NAME`='Invite User' and `ENTITY_TYPE`='Email';

SET SQL_SAFE_UPDATES = 1;