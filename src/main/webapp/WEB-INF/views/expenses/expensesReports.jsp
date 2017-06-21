<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
		window.onload = function () {
			getYearsAndQuartersComboList();
			/* expensesReportList(); */
		 };
</script>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Timesheet Reports</h2>
		</div>
		<div class="pull-right brud-crum">Home &raquo; List by status</div>
	</section>

	<div class="content-area content-new clearfix">
		<form:form id="expenses_Reports_form" name="expenses_Reports_form"	enctype='multipart/form-data'>
			<div class="timesheet_head timesheetRep">
				<div class="timeReFi">
					<div id="selectYearDropDownId"></div>
				</div>
				<div class="timeReFi">
					<div id="selectQuarterDropDownId"></div>
				</div>
				<div class="timeReFi">
					<input type="button" class="btn btn-success" value="View"	onclick="expensesReportList();">
				</div>
				<ul>
					<li id="export-pop"><i class="glyphicon glyphicon-export"></i>
						<div class="export_pop">
							<ul>
								<li>PDF<i class="fa fa-file-pdf-o"></i></li>
								<li>Excel<i class="fa fa-file-excel-o"></i></li>
							</ul>
						</div></li>
				</ul>
				<div class="clearfix"></div>
				<div class="remove_search">
					<input placeholder="search" type="text">
				</div>

			</div>
		</form:form>
		<div id="YearQuartersDiv" style="display: none;" ></div>
		<div class="timesheetRepTab mt5">
			<div id="expensesReportsListDiv"></div>
		</div>
	</div>
</section>
<script type="text/javascript">
function getQuaters(){
	var selectedYear = $("#selectedYear").val();
    $("#selectedQuarter option").each(function(){
        $(this).remove();
    });
    $("#selectedQuarter").append($("#"+selectedYear+"YearQuartersDiv").html());
    $("#selectedQuarter").trigger("chosen:updated");
}
</script>
<script>
 function getYearsAndQuartersComboList(){
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
   	        type : "POST",
    	    contentType : 'application/json; charset=utf-8',
    	    enctype: 'multipart/form-data',
    	    dataType : 'json',
			url : sourceleadUrl+"/getYearsAndQuartersComboList?${_csrf.parameterName}=${_csrf.token}",
			success : function(data) {
				var result = "";
				var json = JSON.parse(data[0]);
				var json1 = JSON.parse(data[1]);
				var currentYear = "";
				var currentQuater = "";
				for (var key in json) {
					var value=json[key];
					currentYear = value[0];
					currentQuater= value[1];
				}
				 var yearResult= '<select class="chosen-select form-control" name="selectedYear" id="selectedYear" onchange="getQuaters();">';
				 var currentYearQuarters = "";
				 var previousYearMap  = {};
		    	 for (var key in json1) {
		    		 var value=json1[key];
		    		 if(value[0] == currentYear){
		    			 yearResult=yearResult+'<option value="'+value[0]+'" selected>'+value[0]+'</option>';
		    			 currentYearQuarters = value[1];
		    		 }
		    		 else{
		    			 yearResult=yearResult+'<option value="'+value[0]+'">'+value[0]+'</option>';
		    			 previousYearMap[value[0]]=value[1];
		    		 }
		    	 }
		    	 yearResult=yearResult+'</select>';
		    	 $("#selectYearDropDownId").empty();
				 $("#selectYearDropDownId").append(yearResult); 
				 
				 
				 var quarterResult= '';
		    	 for (var key in currentYearQuarters) {
		    		 var value=currentYearQuarters[key];
		    		 if(value[0] == currentQuater){
		    			 quarterResult=quarterResult+'<option value="'+value[1]+'" selected>'+value[0]+'</option>';
		    		 }
		    		 else{
		    			 quarterResult=quarterResult+'<option value="'+value[1]+'">'+value[0]+'</option>';
		    		 }
		    	 }
		    	 $("#YearQuartersDiv").empty();
		    	 $("#YearQuartersDiv").append("<div id='"+currentYear+"YearQuartersDiv'>"+quarterResult+"</div>");
		    	 
		    	 quarterResult='<select class="chosen-select form-control" name="selectedQuarter" id="selectedQuarter">'+quarterResult+'</select>';
		    	 $("#selectQuarterDropDownId").empty();
				 $("#selectQuarterDropDownId").append(quarterResult); 
				 
				 for (var outerKey in previousYearMap) {
					 var previousYearQuarters = previousYearMap[outerKey];
					 var previousYear = outerKey;
					 var prevQuarterResult= '';
			    	 for (var key in previousYearQuarters) {
			    		 var value=previousYearQuarters[key];
			    	     prevQuarterResult=prevQuarterResult+'<option value="'+value[1]+'">'+value[0]+'</option>';
			    	 }
			    	 $("#YearQuartersDiv").append("<div id='"+previousYear+"YearQuartersDiv'>"+prevQuarterResult+"</div>");
				 }
				
				 prepareChosenScripts();
				 expensesReportList();
			},
		});
 }
 </script>
 
 <script>
	var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>
<script type="text/javascript">
         function prepareChosenScripts(){ 
        	      var config = {
        			  '.chosen-select'           : {},
        			  '.chosen-select-deselect'  : {allow_single_deselect:true},
        			  '.chosen-select-no-single' : {disable_search_threshold:10},
        			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
        			  '.chosen-select-width'     : {width:"95%"}
        			};
        	      
        		   for (var selector in config) {
        			  $(selector).chosen(config[selector]);
        			};
           };
				
</script> 
 
 <script>
function expensesReportList(){
var sourceleadUrl = '${sourceleadUrl}';
var datequarterly = $("#selectedQuarter").val();
alert(datequarterly);
$.ajax({
    type : "POST",
     url: sourceleadUrl+"/getExpensesReportList?${_csrf.parameterName}=${_csrf.token}&datequarterly="+datequarterly,       
     success :function(data) {
    	 	var json = JSON.parse(data);
    	 	var result = '';
    	 	var  dateresult="";
 			result = result +'<table>';
 			result = result +'<thead>';
 			result = result +'<tr>';
 			result = result +'<th>Emp ID & Name</th>';
 			result = result +'<th>Emp Type</th>';
 			result = result +'<th>Project</th>';
 			result = result +'<th>purpose</th>';
 			for (var i = 0; i < json.length; i++) {
 				var optMap = json[i];
		    	 for (var key in optMap.monthsStringFormat) {
		    		  if (optMap.monthsStringFormat.hasOwnProperty(key)) {
		    			var value=optMap.monthsStringFormat[key];
						result=result+'<th>'+value[0]+'</th>';
						dateresult = dateresult +'<td><div class="week"><span class="subm">'+value[1]+'</span>'; 
		    		  }
		    	}
 			
 			}
 			//result = result +'<th>Start Date</th>';
 			//result = result +'<th>End Date</th>';
 			result = result +'<th>Raised Date</th>';
 			result = result +'<th>Amount</th>';
 			result = result +'<th>Status</th>';
 			result = result +'<th>Claimed Expenses</th>';
 			result = result +'</tr>';
 			result = result +'</thead>';	
 			result = result +'<tbody>';
 		for (var i = 0; i < json.length; i++) {
    		var opt = json[i];
    		result = result +'<tr>';
 			result = result +'<td>';
 			result = result +'<div class="lg clearfix">';
 			//result = result +'<div class="uimg"><img src="images/23.jpg" alt=""/></div>';
 			result = result +'<div class="Utext">';
 			result = result +'<i title="User name here">'+opt.userName+'</i>';
 			result = result +'<i title="Email id here">'+opt.userEmail+'</i>';
 			result = result +'</div>';
 			result = result +'</div>';
 			result = result +'</td>';
 			result = result +'<td><div class="md"><i title="Project name">'+opt.employementType+'</i><i title="client name">Client 1</i></div></td>';
 			result = result +'<td><div class="md"><i title="Project name">'+opt.projectName+'</i><i title="client name">Client 1</i></div></td>';
 			result = result +'<td><div  class="sm" title="Monthly">'+opt.purpose+'</div></td>';
 	 			result = result + dateresult; 
 			result = result +'</div></td>';
 			result = result +'<td><div class="week"><span class="subm">'+opt.totalAmount+'</span>'; 
 			result = result +'</div></td>';
 			result = result +'<td><div class="sm"><b class="proj_active">'+opt.expenseStatus+'</b></div></td>';
 			result = result +'<td><div class="sm"><b class="proj_active">'+opt.endDateStr+'</b></div></td>';
 			result = result +'</tr>';
 		}
 			result = result +'</tbody>';
			result = result +'</table>';
 			$("#expensesReportsListDiv").empty();
			$("#expensesReportsListDiv").append(result);
 		
     }
});	
}     
</script>
 
<jsp:include page="../masterFooter.jsp" />