#for reuirement interview mail for Interviewers
SET SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{InterviewerInvitationRedifined.InterviewerFirstName}} {{InterviewerInvitationRedifined.InterviewerLastName}},\n\nWe invite you to kindly consent to be a Interviewer for the Position {{InterviewerInvitationRedifined.RequirementTitle}},Following are the details for interview.\n {{InterviewerInvitationRedifined.interviewTableDetails}}\n\nFor clear details login into the system.\n{{InterviewerInvitationRedifined.LoginLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Interviewer InvitationRedifined' and `ENTITY_TYPE`='Email';



# 4.Job Apply[Applicant]
	UPDATE `EMAIL_SCENARIO` SET `BODY`= ' Dear {{AppliedRequirement.professionalName}}\n\nThis is to confirm that we have received your application for the below mentioned Job.\nJob ID and Title : {{AppliedRequirement.requirementTitle}}\nEmployer Name : {{AppliedRequirement.employerName}}\nExperience : {{AppliedRequirement.experience}}\nLocation : {{AppliedRequirement.location}}\n\nIf your profile is suitable for the above position we will contact you.\nyou can also track the progress by logging in to SourceLead application.\n\nMake sure your contact information is up to date under your profile.' , `SUBJECT`='Alert !! - This is to confirm that we received your application for the {{AppliedRequirement.requirementTitle}} ' WHERE `UNIQUE_SCENARIO_NAME`='Applied To Requirement' and `ENTITY_TYPE`='Email';

	SET SQL_SAFE_UPDATES=1;	
	
	#2.Registration[Professional]
	INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'supportLink','{{ProfessionalInvitation.supportLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'facebookLink','{{ProfessionalInvitation.facebookLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'linkedInLink','{{ProfessionalInvitation.linkedInLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'twitterLink','{{ProfessionalInvitation.twitterLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'googleLink','{{ProfessionalInvitation.googleLink}}');

SET SQL_SAFE_UPDATES=0;

UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear {{ProfessionalInvitation.professionalName}}\n\nWelcome to SourceLead a Global Talent Harvesting Platform to Manage Human Resource.\n\nThis email is being sent to you because you have registered with \n\nSourceLead makes it easy for teams to work more efficiently by Automating the process of Human resource management.\nIt has many more benefits for professionals like you.Over 8,000 remote teams uses it daily.\n\nMore Technical Questions?\nSee:{{ProfessionalInvitation.supportLink}}\n\nNeed Support?\nWe have video tutorials, live chat, and a great support site to help if you get stuck.\n\n',`SIGNATURE`='Team SourceLead\nWelcome aboard, great to have you!\n{{ProfessionalInvitation.facebookLink}}\t{{ProfessionalInvitation.linkedInLink}}\t{{ProfessionalInvitation.twitterLink}}\t{{ProfessionalInvitation.googleLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Professional Invitation' and `ENTITY_TYPE`='Email';

SET SQL_SAFE_UPDATES=1;



#3.Registration [Recruiter]


INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'supportLink','{{RecruiterInvitation.supportLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'facebookLink','{{RecruiterInvitation.facebookLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'linkedInLink','{{RecruiterInvitation.linkedInLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'twitterLink','{{RecruiterInvitation.twitterLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'Professional Invitation' and `ENTITY_TYPE` = 'Email'),
'googleLink','{{RecruiterInvitation.googleLink}}');

set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear {{RecruiterInvitation.recruiterName}}\n\nWelcome to SourceLead a Global Talent Harvesting Platform to Manage Human Resource.\n\nThis email is being sent you because you have registered with SourceLead as freelance recruiter.\n\nSourceLead makes it easy for teams to work more efficiently by Managing the Resources. Over 8,000 remote teams uses it daily.\n\nMore Technical Questions?\nSee:{{RecruiterInvitation.supportLink}}\n\nNeed Support?\nWe have video tutorials, live chat, and a great support site to help if you get stuck.\n\nUser Name : {{RecruiterInvitation.loginUserName}}\n Your Sourcelead URL : {{RecruiterInvitation.resetLink}}\n\n',`SIGNATURE`='Team SourceLead\nWelcome aboard, great to have you!\n{{RecruiterInvitation.facebookLink}}\t{{RecruiterInvitation.linkedInLink}}\t{{RecruiterInvitation.twitterLink}}\t{{RecruiterInvitation.googleLink}}' WHERE `UNIQUE_SCENARIO_NAME`='Individual Recruiter Invitation' and `ENTITY_TYPE`='Email';
SET SQL_SAFE_UPDATES=1;