-- Aug/22/2019
-- This Procedure Should Be Executed After Running '/ws/saveDefaultRequirementWorkflowAndStages' Public URL In 'RequirementWorkflowController' Controller.

DROP procedure IF EXISTS `UPDATE_DEFAULT_WORKFLOW_IN_REQUIREMENT_PROC`;

DELIMITER $$
CREATE PROCEDURE `UPDATE_DEFAULT_WORKFLOW_IN_REQUIREMENT_PROC`()
BEGIN
 SELECT ID AS workflowId into @defaultWorkflowId FROM REQUIREMENT_WORKFLOW rw WHERE rw.ORG_LOCATION_ID is NULL;
IF(@defaultWorkflowId != '0') 
THEN
 UPDATE REQUIREMENT SET WORKFLOW_ID = @defaultWorkflowId WHERE WORKFLOW_ID IS NULL; 
	END IF;
END$$

DELIMITER ;

call UPDATE_DEFAULT_WORKFLOW_IN_REQUIREMENT_PROC();
