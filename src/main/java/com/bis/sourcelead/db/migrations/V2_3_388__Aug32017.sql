SET SQL_SAFE_UPDATES = 0;	
 UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear\t{{TaskMechanism.assignedUser}},\n\nSubject: {{TaskMechanism.subject}}\nPriority: {{TaskMechanism.priority}}\nDate: {{TaskMechanism.date}}\nTime: {{TaskMechanism.time}}\nDescription: {{TaskMechanism.description}}\n   '  WHERE `UNIQUE_SCENARIO_NAME`='Task Mechanism' and `ENTITY_TYPE`='Email';
SET SQL_SAFE_UPDATES = 1;