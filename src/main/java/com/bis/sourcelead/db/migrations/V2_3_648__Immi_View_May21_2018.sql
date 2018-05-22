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
