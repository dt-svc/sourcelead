
set sql_safe_updates=0;


UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{InterviewerInvitation.InterviewerFirstName}} {{InterviewerInvitation.InterviewerLastName}},\n\nInterview scheduled on the {{InterviewerInvitation.InterviewDate}} for the job.\n\nJob name : {{InterviewerInvitation.RequirementTitle}} \nInterview date : {{InterviewerInvitation.InterviewDate}} \nDetails : {{InterviewerInvitation.Details}} \nInterview Type : {{InterviewerInvitation.InterviewType}} \nApplicants : {{InterviewerInvitation.ApplicantName}} \n\nFor clear details login into the system.' WHERE `UNIQUE_SCENARIO_NAME`='Interviewer Invitation' and `ENTITY_TYPE`='Email';
set sql_safe_updates=1;
