SET SQL_SAFE_UPDATES = 0;
#23.Rate Negotiation [Applicant to org user]
  UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ApplicantNegotiateRequirement.initiatorUserName}},\nI would like to request a rate change for the following job.\nKindly please review and update me as soon as possible\n\nPlease find out the details below details . \n{{ApplicantNegotiateRequirement.negotiationTable}}\n\nPlease login to SourceLead to check more details\n' WHERE `UNIQUE_SCENARIO_NAME`='ApplicantNegotiateRequirement' and `ENTITY_TYPE`='Email';
										  
										  
 UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Rate Negotiation for {{ApplicantNegotiateRequirement.requirementName}} from {{ApplicantNegotiateRequirement.applicantName}} '  WHERE `UNIQUE_SCENARIO_NAME`='ApplicantNegotiateRequirement' and `ENTITY_TYPE`='Email';
									 
									   
									 
#26.Rate negotiation accept[ORG acceptence]					 
									 
 INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'UserAcceptRateNegotiation' and `ENTITY_TYPE` = 'Email'),
'loginOrgName','{{UserAcceptRateNegotiation.loginOrgName}}');
									 
									 
									 
 UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear {{UserAcceptRateNegotiation.applicantName}},\n\nWe are pleased to accept the rate negotiated by you for the following position\nplease find the details of the position below {{UserAcceptRateNegotiation.requirementName}}\nFinalized Rate  : {{UserAcceptRateNegotiation.finalRate}}\none of our Human Resources team will get in touch with you on next steps\nLooking forward to work with you ' WHERE `UNIQUE_SCENARIO_NAME`='UserAcceptRateNegotiation' and `ENTITY_TYPE`='Email';
									 
									 
									 
 UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{UserAcceptRateNegotiation.loginOrgName}} has accepted the Rate for {{UserAcceptRateNegotiation.requirementName}}'  WHERE `UNIQUE_SCENARIO_NAME`='UserAcceptRateNegotiation' and `ENTITY_TYPE`='Email';
									  
									  
									  
UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards \n{{UserAcceptRateNegotiation.userName}},\n{{UserAcceptRateNegotiation.loginOrgName}}' WHERE`UNIQUE_SCENARIO_NAME`='UserAcceptRateNegotiation' and `ENTITY_TYPE`='Email';
									  
									  
									  
									  
#24.Rate Negotiation Acceptance[Applicant]
 UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ApplicantAcceptRequirement.initiatorUserName}},\n\nI am pleased to accept the rate offered by you for the following position.\nplease let me know the next steps\n\nJob ID/name : {{ApplicantAcceptRequirement.requirementName}}\nExeperience : {{ApplicantAcceptRequirement.experience}}\nLocation : {{ApplicantAcceptRequirement.location}}\nRate :{{ApplicantAcceptRequirement.requirementRate}}\nSkills  : {{ApplicantAcceptRequirement.skills}}\nJob Description :  {{ApplicantAcceptRequirement.jobDescription}} . \n' WHERE `UNIQUE_SCENARIO_NAME`='ApplicantAcceptRequirement' and `ENTITY_TYPE`='Email';
										  
										  
 UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{ApplicantAcceptRequirement.applicantName}} has accepted the Rate for {{ApplicantAcceptRequirement.requirementName}}  '  WHERE `UNIQUE_SCENARIO_NAME`='ApplicantAcceptRequirement' and `ENTITY_TYPE`='Email';
									 
									 
#29.Rate negotiation [Cleint to vendor]	 
									 
 UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear {{ClientRateNegotiation.clientContact}},\n\nI would like to request a rate change for the following Job.\nKindly please review and update me as soon as possible\nPlease find out the details below\n{{UserRateNegotiation.negotiationTable}}' WHERE `UNIQUE_SCENARIO_NAME`='ClientRateNegotiation' and `ENTITY_TYPE`='Email';
										  
  UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Rate Negotiation for {{ClientRateNegotiation.requirementName}} from {{ClientRateNegotiation.loginOrgName}} '  WHERE `UNIQUE_SCENARIO_NAME`='ClientRateNegotiation' and `ENTITY_TYPE`='Email';
									 