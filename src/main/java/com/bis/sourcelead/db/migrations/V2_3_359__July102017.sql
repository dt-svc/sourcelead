#41.Notification to Manager & assign memeber when job details updated
UPDATE `EMAIL_SCENARIO` SET `BODY`= ' Dear {{updatedRequirementEmailToManager.clientContactname}},\n\n{{updatedRequirementEmailToManager.RequirementTitle}} has been updated by {{updatedRequirementEmailToManager.LoginUser}}\nPlease find the modifications\nJob Title: {{updatedRequirementEmailToManager.RequirementTitle}}\nClient : {{updatedRequirementEmailToManager.clientContactname}}\nJob Location : {{updatedRequirementEmailToManager.RequirementLocation}}\nJob Rate : {{updatedRequirementEmailToManager.RequirementRate}}\nExperience : {{updatedRequirementEmailToManager.Experience}}\nStatus : {{updatedRequirementEmailToManager.status}}\nPlease login to SourceLead to find more details' WHERE `UNIQUE_SCENARIO_NAME`='updatedRequirementEmailToManager' and `ENTITY_TYPE`='Email';



   UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{updatedRequirementEmailToManager.RequirementTitle}} has been changed'  WHERE `UNIQUE_SCENARIO_NAME`='updatedRequirementEmailToManager' and `ENTITY_TYPE`='Email';
   
   
 #43.Reschedule Interview [Applicant]
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear {{interviewRescheduleMailToApplicant.applicantName}},\n\nI would like to reschedule the Interview for {{interviewRescheduleMailToApplicant.requirementTitle}}.\n\nPlease find the details\nDate :{{interviewRescheduleMailToApplicant.interviewDate}}\nTime :{{interviewRescheduleMailToApplicant.interviewTime}}\nType :{{interviewRescheduleMailToApplicant.interviewType}}\nComment :{{interviewRescheduleMailToApplicant.comment}}\n{{interviewRescheduleMailToApplicant.acceptURL}}{{interviewRescheduleMailToApplicant.rejectURL}}{{interviewRescheduleMailToApplicant.proposeNewTime}}\n\n' WHERE `UNIQUE_SCENARIO_NAME`='interviewRescheduleMailToApplicant' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Request to Reschedule the Interview for {{interviewRescheduleMailToApplicant.RequirementTitle}} from {{interviewRescheduleMailToApplicant.loginOrgName}}'  WHERE `UNIQUE_SCENARIO_NAME`='interviewRescheduleMailToApplicant' and `ENTITY_TYPE`='Email';



#44.Reschedule Interview [Vendor]
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('Vendors Interview Reschedule','Email', 'Vendors Interview Reschedule', 'Used for Reschedule Interview For Vendor', 'Request to Reschedule the Interview for {{VendorsInterview.RequirementName}} from {{ApplicantInterview.LoginOrgName}}  ','org', 'Best Regards,\n{{VendorsInterview.LoginUser}}', ' Dear {{VendorsInterview.VendorFirstName}} {{VendorsInterview.VendorLastName}},\n\n I would like to reschedule the Interview for\nJob ID/name : {{VendorsInterview.RequirementName}}\n\nPlease find the details\nInterview date : {{VendorsInterview.InterviewDate}}\nInterview Time : {{VendorsInterview.InterviewTime}}\nLocation : {{VendorsInterview.Location}}\nInterview Type : {{VendorsInterview.InterviewType}}\n\nApplicant : {{VendorsInterview.ApplicantName}}\n\n   ','N', 'SLADMIN', 'slead', 'sleadIN');

