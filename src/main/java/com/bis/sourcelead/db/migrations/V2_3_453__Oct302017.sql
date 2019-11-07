SET SQL_SAFE_UPDATES = 0;

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Interview scheduled for the {{InterviewerInvitationRedifined.RequirementTitle}} job for {{InterviewerInvitationRedifined.LoginUser}}' WHERE `UNIQUE_SCENARIO_NAME`='Interviewer InvitationRedifined' and `ENTITY_TYPE`='Email';

SET SQL_SAFE_UPDATES = 1;