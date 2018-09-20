
#ajith

SET SQL_SAFE_UPDATES = 0;
  
  UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{AssignedUser.ApplicantName}},\n You have submitted for {{AssignedUser.RequirementName}}.\n Requirement Location :{{AssignedUser.RequirementLocation}} \n   Employment Type :{{AssignedUser.EmploymentType}} \n  Rate :{{AssignedUser.Rate}}' WHERE`UNIQUE_SCENARIO_NAME`='ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager' and `ENTITY_TYPE`='Email';
      
  UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards \n {{AssignedUser.ManagerName}}' WHERE`UNIQUE_SCENARIO_NAME`='ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager' and `ENTITY_TYPE`='Email';


