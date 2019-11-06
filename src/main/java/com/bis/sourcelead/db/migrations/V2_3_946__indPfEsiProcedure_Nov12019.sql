DROP PROCEDURE associateInfoDataProcedure;
DELIMITER //
CREATE PROCEDURE associateInfoDataProcedure 
(                                                       
    IN candidateId bigint,
    IN uanNumber varchar(50),
    IN pfNumber varchar(50),
    IN esinumber varchar(50),
    IN orgLocationId bigint,
	IN createdDate date,
	IN eligibleForEsi varchar(10),
	IN eligibleForPf varchar(10)
) 
BEGIN
	
    SELECT COUNT(*) AS count into @myVar FROM ASSOCIATE_PF_ESI_DETAILS ape WHERE ape.CANDIDATE_ID = candidateId;
   IF(@myVar = '0') THEN
         INSERT INTO ASSOCIATE_PF_ESI_DETAILS(CANDIDATE_ID,UAN_NUMBER, PF_NUMBER, ESI_NUMBER, ORG_LOCATION_ID,CREATED_DATE,ELIGIBLE_FOR_ESI,ELIGIBLE_FOR_PF)
       values (candidateId,uanNumber, pfNumber, esinumber, orgLocationId, createdDate,eligibleForEsi,eligibleForPf);   
	ELSE
    UPDATE ASSOCIATE_PF_ESI_DETAILS SET CANDIDATE_ID = candidateId,UAN_NUMBER = uanNumber,PF_NUMBER = pfNumber, ESI_NUMBER = esinumber,ORG_LOCATION_ID = orgLocationId,
    CREATED_DATE = createdDate, ELIGIBLE_FOR_ESI = eligibleForEsi, ELIGIBLE_FOR_PF = eligibleForPf  where CANDIDATE_ID = candidateId;
END IF;

END//
DELIMITER ;




set SQL_SAFE_UPDATES=0;

#sendMailToIndPayrollApprover
UPDATE `EMAIL_SCENARIO` SET `BODY` =  'Dear [Approver_Name],\n Request you to approve the payroll for [Frequency] duration.\n For more information go with the following details.\n Batch : [Get_The_Batch_Name]\n No of Associates : [Get_The_Count_No_Of_Associates]\n Amount :[Get_The_Total_Amount]\n [Login]'
WHERE `UNIQUE_SCENARIO_NAME`='sendMailToIndPayrollApprover' and `ENTITY_TYPE`='Email';  

#notifications
UPDATE `EMAIL_SCENARIO` SET `BODY` = 'Request for approving payroll'									   									   									   									   
WHERE `UNIQUE_SCENARIO_NAME`='sendNotificationToIndPayrollApprover' and `ENTITY_TYPE`='Notification';  


#sendApprovedMailToRequestedUserInd
UPDATE `EMAIL_SCENARIO` SET `BODY` = ' Dear [Request_User_name],\n Payroll got approved for the [frequency] duration.\n For more information go with the following details.\n Batch : [Get_The_Batch_Name]\n No of Associates : [Get_The_Count_No_Of_Associates]\n Amount :[Get_The_Total_Amount]\n [Login]'									   									   									   									   
WHERE `UNIQUE_SCENARIO_NAME`='sendApprovedMailToRequestedUserInd' and `ENTITY_TYPE`='Email';  

#notifications
UPDATE `EMAIL_SCENARIO` SET `BODY` = 'Payroll got approved'									   									   									   									   
WHERE `UNIQUE_SCENARIO_NAME`='sendApprovedNotificationToRequestedUserInd' and `ENTITY_TYPE`='Notification';  

set SQL_SAFE_UPDATES=1;

 