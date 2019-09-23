#13.Interview Acceptance[vendor]
SET SQL_SAFE_UPDATES = 0;	
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear {{VendorApplicantAcceptMailToInterviewCreator.InterviewCreatorName}},\n\n{{ApplicantAcceptMailToInterviewCreator.ApplicantName}} Accepted the interview request for the following job.\n\nJob ID/Title :{{VendorApplicantAcceptMailToInterviewCreator.requirementName}}\nInterview Date :{{VendorApplicantAcceptMailToInterviewCreator.InterviewDate}}\nInterview Time :{{VendorApplicantAcceptMailToInterviewCreator.InterviewTime}}\nInterview Type :{{VendorApplicantAcceptMailToInterviewCreator.InterviewType}}\nDetails :{{VendorApplicantAcceptMailToInterviewCreator.Details}}' WHERE `UNIQUE_SCENARIO_NAME`='VendorApplicantAcceptMailToInterviewCreator' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{VendorApplicantAcceptMailToInterviewCreator.ApplicantName}} is ready for the interview.'  WHERE `UNIQUE_SCENARIO_NAME`='VendorApplicantAcceptMailToInterviewCreator' and `ENTITY_TYPE`='Email';


	   #17.Generate offer to applicant
	   
	   UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Congratulations !! {{ForwardOfferMail.organizationName}} sent you an offer'  WHERE `UNIQUE_SCENARIO_NAME`='Forward the Mail' and `ENTITY_TYPE`='Email';
	   
	   UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards\n{{ForwardOfferMail.loginUser}}\n{{ForwardOfferMail.organizationName}}'  WHERE `UNIQUE_SCENARIO_NAME`='Forward the Mail' and `ENTITY_TYPE`='Email';
	   
	   
	   
	   UPDATE `EMAIL_SCENARIO` SET `BODY`= ' Dear {{ForwardOfferMail.applicantName}},\n\n You have been selected for {{ForwardOfferMail.requirementTitle}} at {{ForwardOfferMail.organizationName}}.\n We trust that your experience and skills will be a valuable asset to our Organization.\n\nYour offer details\nDesignation:{{ForwardOfferMail.designation}}\nDate of Join:{{ForwardOfferMail.dateofJoin}}\n\nConfirm the offer by clicking on the following Accept link\n{{ForwardOfferMail.acceptLink}} {{ForwardOfferMail.rejectLink}}\n\n {{ForwardOfferMail.attachment}}' WHERE `UNIQUE_SCENARIO_NAME`='Forward the Mail' and `ENTITY_TYPE`='Email';
	   
	   
	   
	   
#18.offer accepte[vendor]

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'OfferAccepted' and `ENTITY_TYPE` = 'Email'),
	'requirementDescription','{{OfferAccepted.requirementDescription}}');

 UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{OfferAccepted.OfferedApplicantName}} accepted the offer on behalf of {{OfferAccepted.OfferedRequirementName}}'  WHERE `UNIQUE_SCENARIO_NAME`='Offer Accepted' and `ENTITY_TYPE`='Email';
 
 
 
 
 UPDATE `EMAIL_SCENARIO` SET `BODY`= ' Dear {{OfferAccepted.ClientContactName}},\n\n{{OfferAccepted.OfferedApplicantName}} accepted the offer for {{OfferAccepted.OfferedRequirementName}}.\nJoin Date :{{OfferAccepted.OfferedJoinDate}}\nJob Details : {{OfferAccepted.requirementDescription}}\nRate : {{OfferAccepted.OfferedRate}}\n\n' WHERE `UNIQUE_SCENARIO_NAME`='Offer Accepted' and `ENTITY_TYPE`='Email';
	   