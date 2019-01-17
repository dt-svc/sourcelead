<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

<script type="text/javascript">
 
function myExpensesListByStage(expenseStatus){
	if(expenseStatus !='' && expenseStatus != null && expenseStatus !='undefined'){
		expenseStatus = expenseStatus.toUpperCase();
	}
	$.ajax({
		    type : "POST",
		    url: pmsUrl+"/getExpensesList?expenseStatus="+expenseStatus+"&expenseDashboardStatus=dashboard",
		    success :function(json) {
		    		localStorage.setItem("myExpensesListGrid",json);
		    		window.location.replace(pmsUrl+'/expenses/'+expenseStatus);
		     }
		});
}
		
function employeeExpensesListByStage(expenseStatus){
	if(expenseStatus !='' && expenseStatus != null && expenseStatus !='undefined'){
		expenseStatus = expenseStatus.toUpperCase();
	}
$.ajax({
	    type : "GET",
	    url: pmsUrl+"/getExpensesApprovalList?expenseStatus="+expenseStatus,
	    success :function(json) {
	    		localStorage.setItem("empExpensesListGrid",json);
	    		window.location.replace(pmsUrl+'/expensesApprovalList/'+expenseStatus);
	     }
	});
}
</script>
