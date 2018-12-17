#22.Rate Negotiation[Recruiter/Account manager to applicant]
SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear {{UserRateNegotiation.applicantName}},\n\n{{UserRateNegotiation.userName}} Proposed new rate for the position {{UserRateNegotiation.requirementName}} with {{UserRateNegotiation.organizationName}}\n {{UserRateNegotiation.negotiationTable}}\n\nPlease login to SourceLead to check more details\n' WHERE `UNIQUE_SCENARIO_NAME`='UserRateNegotiation' and `ENTITY_TYPE`='Email';
											
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Rate Negotiation for {{UserRateNegotiation.requirementName}} from {{UserRateNegotiation.organizationName}} '  WHERE `UNIQUE_SCENARIO_NAME`='UserRateNegotiation' and `ENTITY_TYPE`='Email';
											   
											   
#23.Rate Negotiation[applicant to org user]								   
 UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ApplicantNegotiateRequirement.initiatorUserName}},\nI would like to request a rate change for the following job.\nKindly please review and update me as soon as possible\n\nPlease find out the details below details.\n \n{{ApplicantNegotiateRequirement.negotiationTable}}\n\nPlease login to SourceLead to check more details\n' WHERE `UNIQUE_SCENARIO_NAME`='ApplicantNegotiateRequirement' and `ENTITY_TYPE`='Email';
										  
										  
 UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Rate Negotiation for {{ApplicantNegotiateRequirement.requirementName}} from {{ApplicantNegotiateRequirement.applicantName}} '  WHERE `UNIQUE_SCENARIO_NAME`='ApplicantNegotiateRequirement' and `ENTITY_TYPE`='Email';

