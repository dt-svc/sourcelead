SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`=' New Demo Request from {{orgDemo.companyName}}', `BODY`='Dear Sourceleadadmin,\n\n You have a New Demo Request from {{orgDemo.companyName}}, Please find the details below \n Website : {{orgDemo.companyWebsite}} \n Location : {{orgDemo.companyLocation}} \n Email id : {{orgDemo.companyEmail}} \n Date & Time : {{orgDemo.demoDate}} {{orgDemo.demoTime}} \n Timezone  : {{orgDemo.demoTimeZone}} \n Verify the Organization details and generate.' WHERE `UNIQUE_SCENARIO_NAME`='orgDemoEmail' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='New Registration Request from {{orgRequest.companyName}}', `BODY`='Dear Sourceleadadmin,\n\n You have a New Registration Request from {{orgRequest.companyName}}, Please find the details below \n Website : {{orgRequest.companyWebsite}} \n Location : {{orgRequest.companyLocation}} \n Email id : {{orgRequest.companyEmail}} \n Verify the Organization details and generate.' WHERE `UNIQUE_SCENARIO_NAME`='orgRequestEmail' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear Sourceleadadmin,\n\n This is to notify that {{sourceladTicket.companyName}}  has submitted a new ticket . please find the details below \n Title : {{sourceladTicket.tikcetTitle}} \n Type : {{sourceladTicket.ticketType}} \n Module : {{sourceladTicket.ticketModule}} \n Url : {{sourceladTicket.ticketLoginUrl}}' WHERE `UNIQUE_SCENARIO_NAME`='sourceleadTicketEmail' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `TAB_ID`='16' WHERE `UNIQUE_SCENARIO_NAME`='NDAOrNSAFinalizeNotification';

SET SQL_SAFE_UPDATES = 1;