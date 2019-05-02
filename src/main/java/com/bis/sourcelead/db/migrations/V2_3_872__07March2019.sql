#DROP PROCEDURE associateBankDataProcedure;  

DELIMITER //
CREATE PROCEDURE associateBankDataProcedure 
(                                                       
    IN candidateId bigint,
    IN accountType varchar(250),
    IN bankName varchar(50),
	IN accountNumber varchar(20),
	IN routingNumber varchar(20),
	IN orgLocationID bigint,
	IN createdDate date,
	IN updatedDate date,
	IN associateBankUUID varchar(50)
) 

BEGIN
	   INSERT INTO ASSOCIATE_BANK_DATA(CANDIDATE_ID, ACCOUNT_TYPE, BANK_NAME, ACCOUNT_NUMBER, ROUTING_NUMBER, ORG_LOCATION_ID, CREATED_DATE, UPDATED_DATE, ASSOCIATE_BANK_UUID)
       values (candidateId, accountType, bankName, accountNumber, routingNumber, orgLocationID, createdDate, updatedDate, associateBankUUID); 

END;//

DELIMITER ;


#Added DEFAULT_ACCOUNT column
CALL AlterTableAddColumn ('ASSOCIATE_BANK_DATA','DEFAULT_ACCOUNT','BOOLEAN NULL AFTER `STATUS`');
