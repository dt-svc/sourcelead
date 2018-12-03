CREATE OR REPLACE VIEW IMMIGRATION_REPORT_VIEW AS
   SELECT 
        IMM.ID,
        IMM.GENERATED_ID, 
        IMM.CANDIDATE_ID,
        IMM.COUNTRY,
		IMM.ORG_LOCATION_ID,
		IMM.ATTORNEY_LOCATION_ID,
        IMM.STATUS,
        IMM.CREATED_DATE,
        IMM.UPDATED_DATE,
        IMM.IMMIGRATION_UUID,
        IMM.IMMIGRATION_STATUS,
        IMM.CURRENT_USCIS_STATUS AS CURRENT_USCIS_STATUS_ID,
        IMM.PETITION_TYPE,
        IMM.IMMIGRATION_TEMPLATE_ID,
       (SELECT 
                UUID
            FROM
                IMMIGRATION_TEMPLATE
            WHERE
                ID = IMM.IMMIGRATION_TEMPLATE_ID) AS IMMI_TEMPLATE_UUID,
        (SELECT 
                FIRST_NAME
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS FIRST_NAME,
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
                ID = IMM.PETITION_TYPE) AS PETITON_TYPE,
          IMM.OPEN_DATE,
        (SELECT 
                NAME
            FROM
                LOOKUP_DATA
            WHERE
                ID = IMM.CURRENT_USCIS_STATUS) AS CURRENT_USCIS_STATUS,
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
	           lca.START_DATE
	        FROM
	            LCA lca, 
	            LCA_HISTORY lh
	        WHERE
                lh.IMMIGRATION_ID=IMM.ID
                AND lh.CANDIDATE_ID= IMM.CANDIDATE_ID
                AND lca.id= lh.LCA_ID
                AND lh.IS_CURR = 'Y') AS LCA_START_DATE,
    	(SELECT 
	            lca.END_DATE
	            FROM
	            LCA lca, 
	            LCA_HISTORY lh
              WHERE
	             lh.IMMIGRATION_ID=IMM.ID
				 AND lh.CANDIDATE_ID= IMM.CANDIDATE_ID
	             AND lca.id= lh.LCA_ID
                 AND lh.IS_CURR = 'Y') AS LCA_END_DATE,
      
        (SELECT 
             	O.ORG_NAME
            FROM
	            ORG_LOCATIONS OL,
	            ORG O
             WHERE
                OL.ORG_ID = O.ID
                AND OL.ID = IMM.ATTORNEY_LOCATION_ID) AS VENDOR  
       FROM
        IMMIGRATION IMM
        
        ORDER BY IMM.UPDATED_DATE DESC;
        
        
   #Attoerney Report View     
   
   
   CREATE OR REPLACE VIEW IMMIGRATION_ATTORNEY_REPORT_VIEW AS
   SELECT 
        IMM.ID,
        IMM.GENERATED_ID,
        IMM.CANDIDATE_ID,
        IMM.COUNTRY,
		IMM.ORG_LOCATION_ID,
		IMM.ATTORNEY_LOCATION_ID,
        IMM.STATUS,
        IMM.CREATED_DATE,
        IMM.UPDATED_DATE,
        IMM.IMMIGRATION_UUID,
        IMM.IMMIGRATION_STATUS,
        IMM.CURRENT_USCIS_STATUS AS CURRENT_USCIS_STATUS_ID,
        IMM.PETITION_TYPE,
        IMM.IMMIGRATION_TEMPLATE_ID,
        (SELECT 
                UUID
            FROM
                IMMIGRATION_TEMPLATE
            WHERE
                ID = IMM.IMMIGRATION_TEMPLATE_ID) AS IMMI_TEMPLATE_UUID,
        (SELECT 
                FIRST_NAME
            FROM
                CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS FIRST_NAME,
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
                ID = IMM.PETITION_TYPE) AS PETITON_TYPE,
          IMM.OPEN_DATE,
        (SELECT 
                NAME
            FROM
                LOOKUP_DATA
            WHERE
                ID = IMM.CURRENT_USCIS_STATUS) AS CURRENT_USCIS_STATUS,
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
           lca.START_DATE
        FROM
            LCA lca, 
            LCA_HISTORY lh
        WHERE
             lh.IMMIGRATION_ID=IMM.ID
             AND lh.CANDIDATE_ID= IMM.CANDIDATE_ID
            AND lca.id= lh.LCA_ID
                AND lh.IS_CURR = 'Y') AS LCA_START_DATE,
    	(SELECT 
           lca.END_DATE
        FROM
           LCA lca, 
            LCA_HISTORY lh
        WHERE
             lh.IMMIGRATION_ID=IMM.ID
			 AND lh.CANDIDATE_ID= IMM.CANDIDATE_ID
             AND lca.id= lh.LCA_ID
                AND lh.IS_CURR = 'Y') AS LCA_END_DATE,
      
  (SELECT 
            O.ORG_NAME
        FROM
            ORG_LOCATIONS OL,
            ORG O
        WHERE
            OL.ORG_ID = O.ID
                AND OL.ID = IMM.ORG_LOCATION_ID) AS CLIENT
   
    FROM
        IMMIGRATION IMM
        
        ORDER BY IMM.UPDATED_DATE DESC;

