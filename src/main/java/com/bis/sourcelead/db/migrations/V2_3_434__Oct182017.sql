#Email to vendor when approves the expense

SET SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Claimed expenses approved for the {{ExpensesApproveEmailToVendor.employeename}} by {{ExpensesApproveEmailToVendor.orgname}}. The expenses brief details are following \n Project Name : {{ExpensesApproveEmailToVendor.projectname}} \n Expense Duration : {{ExpensesApproveEmailToVendor.expensesduration}} \n Amount : {{ExpensesApproveEmailToVendor.amount}} \n 
For details login into the system \n ' WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApproveEmailToVendor' and `ENTITY_TYPE`='Email';

