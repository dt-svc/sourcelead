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



DELIMITER //
CREATE PROCEDURE insertAssociateBankingDetails 
(                                                       
    IN candidateId bigint,
    IN email varchar(100),
    IN accountType varchar(45),
    IN bankName varchar(50),
	IN accountNumber varchar(20),
	IN bsrNumber varchar(20),
	IN micrCode varchar(20),	
	IN routingNumber varchar(20),	
    IN firstName varchar(100),
    IN lastName varchar(100),
    IN defaultAccount boolean,
    IN access boolean,
    IN disabled boolean,
    IN orgLocation bigint
) 
BEGIN
	   INSERT INTO PAYMENT_ACCOUNT(CANDIDATE_ID,EMAIL, ACCOUNT_TYPE, BANK_NAME, ACCOUNT_NUMBER, BSR_NUMBER, MICR_CODE, ROUTING_NUMBER, FIRST_NAME, LAST_NAME,IS_DEFAULT,GRANT_ACCESS,DISABLED,ORG_LOCATION_ID)
       values (candidateId,email, accountType, bankName, accountNumber, bsrNumber, micrCode, routingNumber ,firstName, lastName,defaultAccount,access,disabled,orgLocation); 

END//
DELIMITER ;




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
	   INSERT INTO ASSOCIATE_PF_ESI_DETAILS(CANDIDATE_ID,UAN_NUMBER, PF_NUMBER, ESI_NUMBER, ORG_LOCATION_ID,CREATED_DATE,ELIGIBLE_FOR_ESI,ELIGIBLE_FOR_PF)
       values (candidateId,uanNumber, pfNumber, esinumber, orgLocationId, createdDate,eligibleForEsi,eligibleForPf); 

END//
DELIMITER ;
