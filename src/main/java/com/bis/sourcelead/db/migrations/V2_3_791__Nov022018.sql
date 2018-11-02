DELIMITER $$
CREATE PROCEDURE saveBulkCandidates 
(
  IN masterCandidateId int(50),
  IN parentCandidateId int(50),
  IN orgLocationId int(50),
  IN candidateType varchar(50),
  IN firstName varchar(500),
  IN lastName varchar(500),
  IN email varchar(500),
  IN mobilePhone varchar(50),
  IN country varchar(500),
  IN state varchar(500),
  IN city varchar(500),
  IN location varchar(5000),
  IN zipcode varchar(50),
  IN experienceInYears varchar(10),
  IN experienceInMonths varchar(10),
  IN noticePeriod varchar(50),
  IN designation varchar(500),
  IN designationType varchar(50),
  IN expectedRate varchar(50),
  IN rateType varchar(50),
  IN rateCurrency varchar(50),
  IN status int(10),
  IN generatedId varchar(50),
  IN createdDate Date,
  IN createdBy varchar(50),
  IN updatedDate Date,
  IN updatedBy varchar(50),
  IN skills varchar(5000),
  IN primarySkill varchar(500),
  IN orgRelationId int(50),
  IN spocId int(50),
  IN candidateUUID varchar(50),
  IN profilePercentate int(10)
  
) 
BEGIN
   
	    INSERT INTO CANDIDATE (MASTER_CANDIDATE_ID,PARENT_CANDIDATE_ID,ORG_LOCATION_ID,CANDIDATE_TYPE,FIRST_NAME,LAST_NAME,EMAIL,MOBILE_PHONE,COUNTRY,STATE,CITY,LOCATION,ZIPCODE,EXPERIENCE_YEARS,EXPERIENCE_MONTHS,NOTICE_PERIOD,DESIGNATION,DESIGNATION_TYPE,EXPECTED_RATE,RATE_TYPE,EXPECTED_RATE_CURRENCY,STATUS,GENERATED_ID,CREATED_DATE,CREATED_BY,UPDATED_DATE,UPDATED_BY,SKILLS_NAME,PRIMARY_SKILL,ORG_RELATION_ID,SPOC_ID,CANDIDATE_UUID,PROFILE_PERCENTAGE) 
	    VALUES (masterCandidateId,parentCandidateId,orgLocationId,candidateType,firstName,lastName,email,mobilePhone,country,state,city,location,zipcode,experienceInYears,experienceInMonths,noticePeriod,designation,designationType,expectedRate,rateType,rateCurrency,status,generatedId,createdDate,createdBy,updatedDate,updatedBy,skills,primarySkill,orgRelationId,spocId,candidateUUID,profilePercentate);
END

$$

DELIMITER $$
CREATE PROCEDURE saveCandiateResume
(
  IN orgLocationId int(50),
  IN candidateId int(50),
  IN title varchar(500),
  IN fileContentType varchar(500),
  IN fileName varchar(500),
  IN fileUrl varchar(500),
  IN resumeStatus varchar(50),
  IN resumeUUID varchar(500),
  IN status int(10),
  IN createdDate Date,
  IN createdBy varchar(50),
  IN updatedDate Date,
  IN updatedBy varchar(50)
  
  
) 
BEGIN
	    INSERT INTO CANDIDATE_RESUMES (ORG_LOCATION_ID,CANDIDATE_ID,TITLE,FILE_CONTENT_TYPE,FILE_NAME,FILE_URL,RESUME_STATUS,RESUME_UUID,STATUS,CREATED_DATE,CREATED_BY,UPDATED_DATE,UPDATED_BY) 
	    VALUES (orgLocationId,candidateId,title,fileContentType,fileName,fileUrl,resumeStatus,resumeUUID,status,createdDate,createdBy,updatedDate,updatedBy);
END
$$