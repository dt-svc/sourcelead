CREATE OR REPLACE VIEW IMMIGRATION_LIST_EXPORT_VIEW AS
     SELECT 
        IMM.ID,
        IMM.GENERATED_ID,
        IMM.IMMIGRATION_TEMPLATE_ID,
        IMM.CANDIDATE_ID,
        IMM.COUNTRY,
		IMM.ORG_LOCATION_ID,
		IMM.ATTORNEY_LOCATION_ID,
        IMM.STATUS,
        IMM.IMMIGRATION_STATUS,
        IMM.CURRENT_USCIS_STATUS AS CURRENT_USCIS_STATUS_ID,
        IMM.PETITION_TYPE,
        IMM.DATE_OF_ARRIVAL,
        IMM.OPEN_DATE,
        IMM.RECEIPT_NO,
         (SELECT 
                ID_NUMBER
            FROM
                CANDIDATE_IDENTITIES
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND ID_TYPE = 'SSN') AS SSN_NUMBER,
		 (SELECT 
                NAME
            FROM
                IMMIGRATION_TEMPLATE
            WHERE
                ID = IMM.IMMIGRATION_TEMPLATE_ID) AS TEMPLATE_NAME,
		(SELECT 
                ID_NUMBER
            FROM
                CANDIDATE_PASSPORT_VISA
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND ID_TYPE = 'Visa' ORDER BY UPDATED_DATE DESC LIMIT 1) AS VISA_ID_NUMBER,
		(SELECT 
                VALID_FROM_DATE
            FROM
                CANDIDATE_PASSPORT_VISA
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND ID_TYPE = 'Visa' ORDER BY UPDATED_DATE DESC LIMIT 1) AS VISA_VALID_FROM_DATE,
		(SELECT 
                VALID_TO_DATE
            FROM
                CANDIDATE_PASSPORT_VISA
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND ID_TYPE = 'Visa' ORDER BY UPDATED_DATE DESC LIMIT 1) AS VISA_VALID_TO_DATE,
		(SELECT 
                ID_NUMBER
            FROM
                CANDIDATE_LCA_I94_SUMMARY
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND IS_CURR = 'Y') AS LCA_NUMBER,
		(SELECT 
                RATE
            FROM
                CANDIDATE_LCA_I94_SUMMARY
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND IS_CURR = 'Y') AS LCA_RATE,
		(SELECT 
                START_DATE
            FROM
                CANDIDATE_LCA_I94_SUMMARY
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND IS_CURR = 'Y') AS LCA_START_DATE,
		(SELECT 
                END_DATE
            FROM
                CANDIDATE_LCA_I94_SUMMARY
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND IS_CURR = 'Y') AS LCA_END_DATE,
		(SELECT 
                EMPLOYER_LOCATION
            FROM
                CANDIDATE_LCA_I94_SUMMARY
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND IS_CURR = 'Y') AS EMPLOYER_LOCATION,
		(SELECT 
                START_DATE
            FROM
                CANDIDATE_LCA_I94_SUMMARY
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND IS_CURR = 'N') AS PREVIOUS_LCA_START_DATE,
		(SELECT 
                END_DATE
            FROM
                CANDIDATE_LCA_I94_SUMMARY
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND IS_CURR = 'N') AS PREVIOUS_LCA_END_DATE,
		(SELECT 
                ID_NUMBER
            FROM
                CANDIDATE_LCA_I94_SUMMARY
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND IS_CURR = 'N') AS PREVIOUS_LCA_NUMBER,
		(SELECT 
                RATE
            FROM
                CANDIDATE_LCA_I94_SUMMARY
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND IS_CURR = 'N') AS PREVIOUS_LCA_RATE,
		(SELECT 
                EMPLOYER_LOCATION
            FROM
                CANDIDATE_LCA_I94_SUMMARY
            WHERE
                CANDIDATE_ID = IMM.CANDIDATE_ID AND IS_CURR = 'N') AS PREVIOUS_EMPLOYER_LOCATION,
        (SELECT 
                NAME
            FROM
                LOOKUP_DATA
            WHERE
                ID = IMM.CURRENT_USCIS_STATUS) AS CURRENT_USCIS_STATUS,
        (SELECT 
                NAME
            FROM
                LOOKUP_DATA
            WHERE
                ID = IMM.PETITION_TYPE) AS PETITON_TYPE,
        (SELECT 
                FIRST_NAME
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS FIRST_NAME,
		(SELECT 
                LAST_NAME
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS LAST_NAME,
        (SELECT 
                EMAIL
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS EMAIL,
        (SELECT 
                MOBILE_PHONE
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS PHONE,
		(SELECT 
                I_94_NO
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS I_94_NO, 
		(SELECT 
                EAD_NO
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS EAD_NO,
		(SELECT 
                I_20_SEVIS_NO
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS I_20_SEVIS_NO, 
		(SELECT 
                DATE_OF_BIRTH
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS DATE_OF_BIRTH, 
		(SELECT 
                MARITAL_STATUS
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS MARITAL_STATUS,
		(SELECT 
                LOCATION
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS LOCATION,
		(SELECT 
                EXPERIENCE_MONTHS
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS EXPERIENCE_MONTHS,
		(SELECT 
                EXPERIENCE_YEARS
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS EXPERIENCE_YEARS,
		(SELECT 
                JOB_TITLE
            FROM
                REQUIREMENT
            WHERE
                ID = IMM.JOB_ID) AS JOB_NAME,
      (SELECT 
                GENERATED_ID
            FROM
                REQUIREMENT
            WHERE
                ID = IMM.JOB_ID) AS JOB_ID,
      (SELECT 
            O.ORG_NAME
        FROM
            ORG_LOCATIONS OL,
            ORG O
        WHERE
            OL.ORG_ID = O.ID
                AND OL.ID = IMM.ORG_LOCATION_ID) AS CLIENT,
    (SELECT 
            O.ORG_NAME
        FROM
            ORG_LOCATIONS OL,
            ORG O
        WHERE
            OL.ORG_ID = O.ID
                AND OL.ID = IMM.ATTORNEY_LOCATION_ID) AS VENDOR,
   
    (SELECT CONCAT(U.FIRST_NAME,' ',U.LAST_NAME)
          FROM USER U, ORG_SPOC OS
           WHERE  OS.CLIENT_USER_ID= U.ID
             AND OS.ID=IMM.ORG_SPOC_ID ) AS SPOC_NAME_CLIENT,
   
   (SELECT CONCAT(U.FIRST_NAME,' ',U.LAST_NAME)
          FROM USER U, ORG_SPOC OS
           WHERE  OS.VENDOR_USER_ID= U.ID
             AND OS.ID=IMM.ORG_SPOC_ID ) AS SPOC_NAME_VENDOR,
    (SELECT U.EMAIL
          FROM USER U, ORG_SPOC OS
           WHERE  OS.CLIENT_USER_ID= U.ID
             AND OS.ID=IMM.ORG_SPOC_ID ) AS SPOC_EMAIL_CLIENT,
   
   (SELECT U.EMAIL
          FROM USER U, ORG_SPOC OS
           WHERE  OS.VENDOR_USER_ID= U.ID
             AND OS.ID=IMM.ORG_SPOC_ID ) AS SPOC_EMAIL_VENDOR                
    FROM
        IMMIGRATION IMM
        
        ORDER BY IMM.UPDATED_DATE DESC;
