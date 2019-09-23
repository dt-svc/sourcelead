SET SQL_SAFE_UPDATES = 0;
  
  UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{AssignedUser.ApplicantName}},\n\nYou have submitted for {{AssignedUser.RequirementName}}.\nRequirement Location :{{AssignedUser.RequirementLocation}}\nEmployment Type :{{AssignedUser.EmploymentType}}\nRate :{{AssignedUser.Rate}}' WHERE`UNIQUE_SCENARIO_NAME`='ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager' and `ENTITY_TYPE`='Email';
      
  UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards \n{{AssignedUser.ManagerName}}' WHERE`UNIQUE_SCENARIO_NAME`='ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager' and `ENTITY_TYPE`='Email';
