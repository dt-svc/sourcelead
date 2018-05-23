/* Created on 19 Feb 2018 by Srinvias Sabbu */   
CREATE OR REPLACE VIEW IMMIGRATION_TEMPLATE_VIEW AS
    SELECT 
        TEMP.ID,
        TEMP.ORG_LOCATION_ID,
        TEMP.NAME,
        TEMP.DESCRIPTION,
        TEMP.STATUS,
        TEMP.UPDATED_DATE,
        TEMP.COUNTRY,
        TEMP.REJECT_REASON,
        (SELECT 
                NAME
           FROM  `LOOKUP_DATA`
            WHERE
                ID = TEMP.PETITION_TYPE) AS PETITION_TYPE,
        
        (SELECT 
                COUNT(DOCS.ID)
            FROM
                `IMMIGRATION_TEMPLATE_DOCUMENTS` DOCS
            WHERE
                TEMP.ID = DOCS.IMMIGRATION_TEMPLATE_ID
                    AND DOCS.STATUS = 1) AS DOCS_COUNT,
        (SELECT 
                COUNT(FORM.ID)
            FROM
                `IMMIGRATION_TEMPLATE_FORMS` FORM
            WHERE
                TEMP.ID = FORM.IMMIGRATION_TEMPLATE_ID
                    AND FORM.STATUS = 1) AS FORMS_COUNT,
        (SELECT 
                COUNT(TASK.ID)
            FROM
                `IMMIGRATION_TEMPLATE_TASKS` TASK
            WHERE
                TEMP.ID = TASK.IMMIGRATION_TEMPLATE_ID
                    AND TASK.STATUS = 1) AS TASKS_COUNT
    FROM
        `IMMIGRATION_TEMPLATE` TEMP
         
          ORDER BY TEMP.UPDATED_DATE DESC;
        
   /* Created on 5 April 2018 by Srinvias Sabbu */     
CREATE OR REPLACE VIEW IMMIGRATION_LIST_VIEW AS
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
        IMM.CREATED_DATE,
        IMM.UPDATED_DATE,
        IMM.CURRENT_USCIS_STATUS AS CURRENT_USCIS_STATUS_ID,
        IMM.PETITION_TYPE,
        IMM.IMMIGRATION_UUID,
         (SELECT 
                UUID
            FROM
                IMMIGRATION_TEMPLATE
            WHERE
                ID = IMM.IMMIGRATION_TEMPLATE_ID) AS IMMI_TEMPLATE_UUID,
        (SELECT 
                CANDIDATE_UUID
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS CANDIDATE_UUID,
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
        IMM.OPEN_DATE,
        (SELECT 
                FIRST_NAME
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS FIRS_TNAME,
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
            START_DATE
        FROM
            CANDIDATE_LCA_I94_SUMMARY LCA
        WHERE
            LCA.CANDIDATE_ID = IMM.CANDIDATE_ID
                AND LCA.IS_CURR = 'Y') AS LCA_START_DATE,
    	(SELECT 
            END_DATE
        FROM
            CANDIDATE_LCA_I94_SUMMARY LCA
        WHERE
            LCA.CANDIDATE_ID = IMM.CANDIDATE_ID
                AND LCA.IS_CURR = 'Y') AS LCA_END_DATE,
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
   
    (SELECT U.FIRST_NAME
          FROM USER U, ORG_SPOC OS
           WHERE  OS.CLIENT_USER_ID= U.ID
             AND OS.ID=IMM.ORG_SPOC_ID ) AS SPOC_NAME_CLIENT,
   
   (SELECT U.FIRST_NAME
          FROM USER U, ORG_SPOC OS
           WHERE  OS.VENDOR_USER_ID= U.ID
             AND OS.ID=IMM.ORG_SPOC_ID ) AS SPOC_NAME_VENDOR
                    
    FROM
        IMMIGRATION IMM
        
        ORDER BY IMM.UPDATED_DATE DESC;

/* Created on 16 April 2018 by Srinvias Sabbu for the dashboard */           
CREATE OR REPLACE VIEW IMMIGRATION_DASHBOARD_VIEW AS
SELECT 
   IMM.ORG_LOCATION_ID, 
   SUM(CASE
        WHEN UPPER(LD.NAME) = 'DRAFT' THEN 1
        ELSE 0
    END) AS DRAFT_COUNT,
    SUM(CASE
        WHEN UPPER(LD.NAME) = 'REQUEST TO FILL' THEN 1
        ELSE 0
    END) AS REQUEST_SENT_COUNT,
    SUM(CASE
        WHEN UPPER(LD.NAME) = 'APPROVED' THEN 1
        ELSE 0
    END) AS APPROVED_COUNT,
    SUM(CASE
        WHEN UPPER(LD.NAME) = 'REJECTED' THEN 1
        ELSE 0
    END) AS REJECTED_COUNT,
    SUM(CASE
        WHEN UPPER(LD.NAME) = 'Filled' THEN 1
        WHEN UPPER(LD.NAME) = 'REVIEWED & SEND TO ATTORNEY' THEN 1
        WHEN UPPER(LD.NAME) = 'REQUEST FOR MODIFICATIONS' THEN 1
        WHEN UPPER(LD.NAME) = 'REVIEWED & FILED' THEN 1
        WHEN UPPER(LD.NAME) = 'RFE' THEN 1
        WHEN UPPER(LD.NAME) = 'RFE RECEIVED' THEN 1
        WHEN UPPER(LD.NAME) = 'MOTION TO RE-OPEN' THEN 1
        WHEN UPPER(LD.NAME) = 'RFE APPROVED' THEN 1
        WHEN UPPER(LD.NAME) = 'RFE DENIED' THEN 1
        WHEN UPPER(LD.NAME) = 'RFE RESPONDED' THEN 1
        ELSE 0
    END) AS INPROGRESS_COUNT,
    SUM(CASE
        WHEN UPPER(LD.NAME) = 'DENIED' THEN 1
        WHEN UPPER(LD.NAME) = 'REVOKED' THEN 1
        WHEN UPPER(LD.NAME) = 'CANCELLED' THEN 1
        WHEN UPPER(LD.NAME) = 'WITHDRAWN' THEN 1
        ELSE 0
    END) AS OTHERS_COUNT
FROM
    IMMIGRATION IMM,
    LOOKUP_DATA LD
WHERE
    IMM.CURRENT_USCIS_STATUS = LD.ID
        AND LD.CATEGORY = 'USCISStatus'
        
        GROUP BY IMM.ORG_LOCATION_ID;

          /* Created on 7 May 2018 by Srinvias Sabbu */     
CREATE OR REPLACE VIEW MY_IMMIGRATION_VIEW AS
 SELECT 
        IMM.ID,
        IMM.GENERATED_ID,
        IMM.IMMIGRATION_TEMPLATE_ID,
        IMM.CANDIDATE_ID,
        IMM.COUNTRY,
        IMM.CANDIDATE_USER_ID,
		IMM.ORG_LOCATION_ID,
		IMM.ATTORNEY_LOCATION_ID,
        IMM.STATUS,
        IMM.IMMIGRATION_STATUS,
        IMM.CREATED_DATE,
        IMM.UPDATED_DATE,
        IMM.CURRENT_USCIS_STATUS AS CURRENT_USCIS_STATUS_ID,
        IMM.PETITION_TYPE,
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
        IMM.OPEN_DATE,
        (SELECT 
                FIRST_NAME
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS FIRS_TNAME,
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
            START_DATE
        FROM
            CANDIDATE_LCA_I94_SUMMARY LCA
        WHERE
            LCA.CANDIDATE_ID = IMM.CANDIDATE_ID
                AND LCA.IS_CURR = 'Y') AS LCA_START_DATE,
    	(SELECT 
            END_DATE
        FROM
            CANDIDATE_LCA_I94_SUMMARY LCA
        WHERE
            LCA.CANDIDATE_ID = IMM.CANDIDATE_ID
                AND LCA.IS_CURR = 'Y') AS LCA_END_DATE
                    
    FROM
        IMMIGRATION IMM
        
        ORDER BY IMM.UPDATED_DATE DESC;