SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Approve the Job {{ApprovalMechanismSequential.requirementName}}.', `BODY`='Approve the job {{ApprovalMechanismSequential.requirementName}}.' WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism Sequential' and `ENTITY_TYPE`='Notification';
