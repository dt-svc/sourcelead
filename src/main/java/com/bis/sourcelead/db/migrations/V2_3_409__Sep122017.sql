INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`) , 'RejectReason', '{{OfferRejected.RejectReason}}');


SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{OfferRejected.ClientContactName}},\n\n {{OfferRejected.OfferRejectedApplicantName}} rejected the offer for {{OfferRejected.RequirementName}}. \n Join Date :  {{OfferRejected.JoinDate}} \n Rate : {{OfferRejected.Rate}} \n RejectReason: {{OfferRejected.RejectReason}} \n\n Please login to SoruceLead to check more details '  WHERE `UNIQUE_SCENARIO_NAME`='Offer Rejected' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{OffBoardingProcessApplicantTermination.applicantName}},\n\nThis is to inform you that you have been terminated from your services with {{OffBoardingProcessApplicantTermination.companyName}}.\nWe Thank you for your time and service at {{OffBoardingProcessApplicantTermination.companyName}}.\n\nIf you have future questions or concerns please Contact Human resources.\n\nPlease click here to complete the exit formalities.'  WHERE `UNIQUE_SCENARIO_NAME`='OffBoardingProcessApplicantTermination' and `ENTITY_TYPE`='Email';
SET SQL_SAFE_UPDATES = 1;