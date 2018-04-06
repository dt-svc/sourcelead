SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='BestRegards\n{{AssignToTeamMembers.LoginUser}},\n{{AssignToTeamMembers.Role}}' WHERE `UNIQUE_SCENARIO_NAME`='AssignToTeamMembers' and `ENTITY_TYPE`='Email';


 UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{AssignToTeamMembers.Ownername}},\n\nnew requirement assigned to you. The brief requirement details are below  \nRequirement Title :{{AssignToTeamMembers.RequirementTitle}}\nRequirement Location :{{AssignToTeamMembers.RequirementLocation}} \nRequirement Rate :{{AssignToTeamMembers.RequirementRate}}\nExperience :{{AssignToTeamMembers.Experience}}\n\nFor more details login into the system\n {{AssignToTeamMembers.link}}' WHERE `UNIQUE_SCENARIO_NAME`='AssignToTeamMembers' and `ENTITY_TYPE`='Email';
 
 
 
 UPDATE `EMAIL_SCENARIO` SET `SUBJECT`= 'New applicants received for the {{NotificationToClient.RequirementTitle}} from {{NotificationToClient.VendorName}}' WHERE `UNIQUE_SCENARIO_NAME`='Notification To Client' and `ENTITY_TYPE`='Notification';