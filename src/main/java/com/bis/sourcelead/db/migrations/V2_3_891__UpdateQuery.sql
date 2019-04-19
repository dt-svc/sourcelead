#ApplicantRejectMailToInterviewCreator
set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Interview_Creator_Name],\n\nI regret to say that i am no longer interested in attending the following interview\n\nJob Title :[Requirement_Name]\nInterview Date :[Interview_Date]\nInterview Time :[Interview_Time]\nInterview Type :[Interview_Type]\nDetails :[Details].\n\n[Login_URL]\n\nThank you for providing me the opportunity' WHERE `UNIQUE_SCENARIO_NAME`='ApplicantRejectMailToInterviewCreator' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;