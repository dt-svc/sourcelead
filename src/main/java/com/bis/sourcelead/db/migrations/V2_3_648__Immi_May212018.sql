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
		TEMP.UUID AS TEMPLATEUUID,
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