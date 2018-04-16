/* Created on 19 Feb 2018 by Srinvias Sabbu */   
CREATE OR REPLACE VIEW IMMIGRATON_TEMPLATE_VIEW AS
    SELECT 
        TEMP.ID,
        TEMP.ORG_LOCATION_ID,
        TEMP.NAME,
        TEMP.DESCRIPTION,
        TEMP.STATUS,
        TEMP.UPDATED_DATE,
        TEMP.COUNTRY,
        (SELECT 
                NAME
           FROM  `SOURCELEAD`.`LOOKUP_DATA`
            WHERE
                ID = TEMP.PETITION_TYPE) AS PETITION_TYPE,
        
        (SELECT 
                COUNT(DOCS.ID)
            FROM
                `SOURCELEAD`.`IMMIGRATION_TEMPLATE_DOCUMENTS` DOCS
            WHERE
                TEMP.ID = DOCS.IMMIGRATION_TEMPLATE_ID
                    AND DOCS.STATUS = 1) AS DOCS_COUNT,
        (SELECT 
                COUNT(FORM.ID)
            FROM
                `SOURCELEAD`.`IMMIGRATION_TEMPLATE_FORMS` FORM
            WHERE
                TEMP.ID = FORM.IMMIGRATION_TEMPLATE_ID
                    AND FORM.STATUS = 1) AS FORMS_COUNT,
        (SELECT 
                COUNT(TASK.ID)
            FROM
                `SOURCELEAD`.`IMMIGRATION_TEMPLATE_TASKS` TASK
            WHERE
                TEMP.ID = TASK.IMMIGRATION_TEMPLATE_ID
                    AND TASK.STATUS = 1) AS TASKS_COUNT
    FROM
        `SOURCELEAD`.`IMMIGRATION_TEMPLATE` TEMP;
        
   /* Created on 5 April 2018 by Srinvias Sabbu */     
CREATE OR REPLACE VIEW IMMIGRATON_LIST_VIEW AS
     SELECT 
      IMM.ID,
        IMM.IMMIGRATION_TEMPLATE_ID,
        IMM.CANDIDATE_ID,
        IMM.COUNTRY,
		IMM.ORG_LOCATION_ID,
        IMM.STATUS,
        IMM.IMMIGRATION_STATUS,
        IMM.CREATED_DATE,
        IMM.UPDATED_DATE,
        IMM.CURRENT_USCIS_STATUS AS CURRENT_USCIS_STATUS_ID,
        (SELECT 
                NAME
            FROM
                SOURCELEAD.LOOKUP_DATA
            WHERE
                ID = IMM.CURRENT_USCIS_STATUS) AS CURRENT_USCIS_STATUS,
        (SELECT 
                NAME
            FROM
                SOURCELEAD.LOOKUP_DATA
            WHERE
                ID = IMM.PETITION_TYPE) AS PETITON_TYPE,
        IMM.OPEN_DATE,
        (SELECT 
                FIRST_NAME
            FROM
                SOURCELEAD.CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS FIRS_TNAME,
        (SELECT 
                EMAIL
            FROM
                SOURCELEAD.CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS EMAIL,
        (SELECT 
                MOBILE_PHONE
            FROM
                SOURCELEAD.CANDIDATE
            WHERE
                ID = IMM.CANDIDATE_ID) AS PHONE,
        (SELECT 
                O.ORG_NAME
            FROM
                SOURCELEAD.ORG_RELATIONS ORL,
                SOURCELEAD.ORG_LOCATIONS OL,
                SOURCELEAD.ORG O
            WHERE
                ORL.RESPONDER_LOCATION_ID = OL.ID
                    AND OL.ORG_ID = O.ID
                    AND ORL.ID = IMM.ORG_REL_ID) AS ORG_NAME
    FROM
        SOURCELEAD.IMMIGRATION IMM
        
        ORDER BY IMM.UPDATED_DATE DESC;