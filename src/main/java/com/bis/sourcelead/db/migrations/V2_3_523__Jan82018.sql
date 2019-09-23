set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET  `BODY`='Dear {{connectionInvitation.receiverUserName}},\nHere is an outline of your recent invitation/s to connect.\n\n{{connectionInvitation.profilePicUrl}}' WHERE `UNIQUE_SCENARIO_NAME`='connectionInvitation' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;
