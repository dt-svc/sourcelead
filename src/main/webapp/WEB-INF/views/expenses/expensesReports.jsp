<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<h2>Expense(s)</h2>
		</div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/hrReportsList'/>">HR Reports</a> &raquo; Expense(s)</div>
	</section>
	<div id="loadingId"></div>
	<div class="content-area content-new clearfix">
	
	
		<form:form id="expenses_Reports_form" name="expenses_Reports_form"	enctype='multipart/form-data'>
		
		
		
		<div class="reportHead">
    	<ul class="clearfix pull-right">
    		<li>
				<div class="timeReFi noPad mr10">
					<div id="selectYearDropDownId"></div>
				</div>
				<div class="timeReFi noPad">
					<div id="selectQuarterDropDownId"></div>
				</div>
				<div class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" onclick="expensesReportList();">Run</button></div>
          
    		</li>
    		<li class="staPdfExl"><i onclick="exportExpensesReports('pdf')" title="PDF" class="fa fa-file-pdf-o text-danger"></i><i onclick="exportExpensesReports('excel')" title="Excel" class="fa fa-file-excel-o text-primary"></i></li>
    		<li class="unVisable"><input type="text" class="repSearchBar" placeholder='Search' id='usersDataTableSearch'/><span class="repSearchIcon" onclick=""></span></li>
    	</ul>
    </div> 
		
	
		</form:form>
		
		
		
		<div id="YearQuartersDiv" style="display: none;" ></div>
		<div class="timesheetRepTab expensestable datatable_filter overflow_inherint repDataSearch repDataSearchFil">
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
		var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';
		$.ajax({
   	        type : "POST",
    	    contentType : 'application/json; charset=utf-8',
    	    enctype: 'multipart/form-data',
    	    dataType : 'json',
			url : sourceleadUrl+"/getYearsAndQuartersComboList?"+tokenParamName+"="+tokenValue+"&dateFormat="+dateFormat,
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
var datequarterly = $("#selectedQuarter").val();
$("#loadingId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
$.ajax({
    type : "POST",
     url: sourceleadUrl+"/getExpensesReportList?"+tokenParamName+"="+tokenValue+"&datequarterly="+datequarterly,       
     success :function(json) {
    	 $("#loadingId").removeAttr('style');
    	 	//var json = JSON.parse(data);
    	 if(json != null && json != '' && json != 'undefined'){
    	 	var result = '';
    	 	var  dateresult="";
 			result = result +'<table id="expensesReportsPagination" class="display" cellspacing="0" width="100%">';
 			result = result +'<thead>';
 			result = result +'<tr>';
 			result = result +'<th>Emp ID & Name</th>';
 			result = result +'<th>Emp Type</th>';
 			result = result +'<th>Project</th>';
 			var months = [];
 			for (var i = 0; i < 1; i++) {
 				var opt = json[i].monthsStringFormat;
 				if(opt != null && opt!='' && opt !='undefined'){
 					for (var j = 0; j < opt.length; j++) {
 	 					result=result+'<th>'+opt[j]+'</th>';
 	 					months.push(opt[j]);
 	 				}
 				}
		    	 /* for (var key in optMap.monthsStringFormat) {
		    		  if (optMap.monthsStringFormat.hasOwnProperty(key)) {
		    			var value=optMap.monthsStringFormat[key];
						result=result+'<th>'+value[0]+'</th>';
						dateresult = dateresult +'<td><div class="week"><span class="subm">'+value[1]+'</span>'; 
		    		  }
		    	} */
 			
 			}
 			result = result +'<th>Amount</th>';
 			/* result = result +'<th>Status</th>'; */
 			result = result +'<th>Claimed Expenses</th>';
 			result = result +'</tr>';
 			result = result +'</thead>';	
 			result = result +'<tbody>';
 		for (var i = 0; i < json.length; i++) {
    		var opt = json[i];
    		result = result +'<tr>';
 			result = result +'<td>';
 			result = result +'<div class="lg clearfix">';
 			result = result +'<div class="time_appr_user">';
 			result = result +'<img id="userImage" onerror="predefineProfileImage(this);" src="/resources/images/userprofile-img.png" alt="Profile Image" class="img-circle img-responsive">'; 
 			result = result +'<span>';
 			if(opt.employeeIdForExpenses != null && opt.employeeIdForExpenses !='' && opt.employeeIdForExpenses!='Undefined'){
 			result = result +'<i title="'+opt.employeeIdForExpenses+'">'+opt.employeeIdForExpenses+'</i>';	
 			}
 			if(opt.userName != null && opt.userName !='' && opt.userName!='Undefined'){
 			result = result +'<i title="'+opt.userName+'">'+opt.userName+'</i>';	
 	 		}
 			if(opt.userEmail != null && opt.userEmail !='' && opt.userEmail!='Undefined'){
 			result = result +'<i title="'+opt.userEmail+'">'+opt.userEmail+'</i>';
 	 		}
 			result = result +'</span>';
 			result = result +'</div>';
 			result = result +'</div>';
 			result = result +'</td>';
 			if(opt.employmentTypeVendors != null && opt.employmentTypeVendors !='' && opt.employmentTypeVendors!='Undefined' && opt.employementType != null && opt.employementType !='' && opt.employementType!='Undefined'){
 			result = result +'<td><div class="md"><i title="'+opt.employementType+'">'+opt.employementType+'</i><i title="client name">'+opt.employmentTypeVendors+'</i></div></td>';	
 			}else if(opt.employementType != null && opt.employementType !='' && opt.employementType!='Undefined'){
 			result = result +'<td><div class="md"><i title="'+opt.employementType+'">'+opt.employementType+'</i></div></td>';	
 			}else{
 			result = result +'<td><div class="md"><i title="--">--</i></div></td>';	
 			}
 			
 			if(opt.clientNameExpenses != null && opt.clientNameExpenses !='' && opt.clientNameExpenses!='Undefined'){
 			result = result +'<td><div class="md"><i title="'+opt.projectName+'">'+opt.projectName+'</i><i title="client name">'+opt.clientNameExpenses+'</i></div></td>';
 			}else{
 			result = result +'<td><div class="md"><i title="'+opt.projectName+'">'+opt.projectName+'</i></div></td>';	
 			}
 			
 			if(months.indexOf("January") > -1){
 			result = result +'<td>';
 			if(opt.janExpenseTotal != null && opt.janExpenseTotal !='' && opt.janExpenseTotal!='Undefined'){
 			result = result +'<div class="week"><span class="draft">'+opt.janExpenseTotal+'</span>';
 			}else{
 				result = result +'<div class="week"><span class="draft">0</span>';	
 			}
 			/* result = result +'<div class="progress">';
 			if(opt.janExpenseApprovedTotal != null && opt.janExpenseApprovedTotal !='' && opt.janExpenseApprovedTotal!='Undefined'){
 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.janExpenseApprovedTotal+'</div>';
 			}else{
 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 			}
 			if(opt.janExpenseUnApprovedTotal != null && opt.janExpenseUnApprovedTotal !='' && opt.janExpenseUnApprovedTotal!='Undefined'){
 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.janExpenseUnApprovedTotal+'</div>';
 			}else{
 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 			}
 			result = result +'</div>'; */                    
 			result = result +'</div>';
 			result = result +'</td>'; 
 			}
 			
 		
 			if(months.indexOf("February") > -1){
 	 			result = result +'<td>';
 	 			if(opt.febExpenseTotal != null && opt.febExpenseTotal !='' && opt.febExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.febExpenseTotal+'</span>';
 	 			}else{
 	 				result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.febExpenseApprovedTotal != null && opt.febExpenseApprovedTotal !='' && opt.febExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.febExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.febExpenseUnApprovedTotal != null && opt.febExpenseUnApprovedTotal !='' && opt.febExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.febExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>'; */                    
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}
 			
 			
 			if(months.indexOf("March") > -1){
 	 			result = result +'<td>';
 	 			if(opt.marExpenseTotal != null && opt.marExpenseTotal !='' && opt.marExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.marExpenseTotal+'</span>';
 	 			}else{
 	 			result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.marExpenseApprovedTotal != null && opt.marExpenseApprovedTotal !='' && opt.marExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.marExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.marExpenseUnApprovedTotal != null && opt.marExpenseUnApprovedTotal !='' && opt.marExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.marExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>'; */                    
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}
 			
 			
 			
 			if(months.indexOf("April") > -1){
 	 			result = result +'<td>';
 	 			if(opt.aprExpenseTotal != null && opt.aprExpenseTotal !='' && opt.aprExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.aprExpenseTotal+'</span>';
 	 			}else{
 	 				result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.aprExpenseApprovedTotal != null && opt.aprExpenseApprovedTotal !='' && opt.aprExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.aprExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.aprExpenseUnApprovedTotal != null && opt.aprExpenseUnApprovedTotal !='' && opt.aprExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.aprExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>'; */                    
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}
 			
 			
 			if(months.indexOf("May") > -1){
 	 			result = result +'<td>';
 	 			if(opt.mayExpenseTotal != null && opt.mayExpenseTotal !='' && opt.mayExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.mayExpenseTotal+'</span>';
 	 			}else{
 	 				result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.mayExpenseApprovedTotal != null && opt.mayExpenseApprovedTotal !='' && opt.mayExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.mayExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.mayExpenseUnApprovedTotal != null && opt.mayExpenseUnApprovedTotal !='' && opt.mayExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.mayExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>'; */                    
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}
 			
 			
 			
 			
 			if(months.indexOf("June") > -1){
 	 			result = result +'<td>';
 	 			if(opt.junExpenseTotal != null && opt.junExpenseTotal !='' && opt.junExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.junExpenseTotal+'</span>';
 	 			}else{
 	 				result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.junExpenseApprovedTotal != null && opt.junExpenseApprovedTotal !='' && opt.junExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.junExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.junExpenseUnApprovedTotal != null && opt.junExpenseUnApprovedTotal !='' && opt.junExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.junExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>'; */                    
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}
 			
 			
 			if(months.indexOf("July") > -1){
 	 			result = result +'<td>';
 	 			if(opt.julExpenseTotal != null && opt.julExpenseTotal !='' && opt.julExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.julExpenseTotal+'</span>';
 	 			}else{
 	 				result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.julExpenseApprovedTotal != null && opt.julExpenseApprovedTotal !='' && opt.julExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.julExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.julExpenseUnApprovedTotal != null && opt.julExpenseUnApprovedTotal !='' && opt.julExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.julExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>'; */                    
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}	
 			
 			
 			if(months.indexOf("August") > -1){
 	 			result = result +'<td>';
 	 			if(opt.augExpenseTotal != null && opt.augExpenseTotal !='' && opt.augExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.augExpenseTotal+'</span>';
 	 			}else{
 	 				result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.augExpenseApprovedTotal != null && opt.augExpenseApprovedTotal !='' && opt.augExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.augExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.augExpenseUnApprovedTotal != null && opt.augExpenseUnApprovedTotal !='' && opt.augExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.augExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>'; */                    
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}	
 			
 			if(months.indexOf("September") > -1){
 	 			result = result +'<td>';
 	 			if(opt.sepExpenseTotal != null && opt.sepExpenseTotal !='' && opt.sepExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.sepExpenseTotal+'</span>';
 	 			}else{
 	 				result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.sepExpenseApprovedTotal != null && opt.sepExpenseApprovedTotal !='' && opt.sepExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.sepExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.sepExpenseUnApprovedTotal != null && opt.sepExpenseUnApprovedTotal !='' && opt.sepExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.sepExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>'; */                    
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}	
 			
 			if(months.indexOf("October") > -1){
 	 			result = result +'<td>';
 	 			if(opt.octExpenseTotal != null && opt.octExpenseTotal !='' && opt.octExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.octExpenseTotal+'</span>';
 	 			}else{
 	 				result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.octExpenseApprovedTotal != null && opt.octExpenseApprovedTotal !='' && opt.octExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.octExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.octExpenseUnApprovedTotal != null && opt.octExpenseUnApprovedTotal !='' && opt.octExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.octExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>';  */                   
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}	
 			
 			if(months.indexOf("November") > -1){
 	 			result = result +'<td>';
 	 			if(opt.novExpenseTotal != null && opt.novExpenseTotal !='' && opt.novExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.novExpenseTotal+'</span>';
 	 			}else{
 	 				result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.novExpenseApprovedTotal != null && opt.novExpenseApprovedTotal !='' && opt.novExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.novExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.novExpenseUnApprovedTotal != null && opt.novExpenseUnApprovedTotal !='' && opt.novExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.novExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>';  */                   
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}	
 			
 			if(months.indexOf("December") > -1){
 	 			result = result +'<td>';
 	 			if(opt.decExpenseTotal != null && opt.decExpenseTotal !='' && opt.decExpenseTotal!='Undefined'){
 	 			result = result +'<div class="week"><span class="draft">'+opt.decExpenseTotal+'</span>';
 	 			}else{
 	 			result = result +'<div class="week"><span class="draft">0</span>';	
 	 			}
 	 			/* result = result +'<div class="progress">';
 	 			if(opt.decExpenseApprovedTotal != null && opt.decExpenseApprovedTotal !='' && opt.decExpenseApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.decExpenseApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 	 			}
 	 			if(opt.decExpenseUnApprovedTotal != null && opt.decExpenseUnApprovedTotal !='' && opt.decExpenseUnApprovedTotal!='Undefined'){
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.decExpenseUnApprovedTotal+'</div>';
 	 			}else{
 	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 	 			}
 	 			result = result +'</div>'; */                    
 	 			result = result +'</div>';
 	 			result = result +'</td>'; 
 	 			}	
 			
 			
 				result = result +'<td>';
	 			if(opt.expenseTotal != null && opt.expenseTotal !='' && opt.expenseTotal!='Undefined'){
	 			result = result +'<div class="week"><span class="draft">'+opt.expenseTotal+'</span>';
	 			}else{
	 				result = result +'<div class="week"><span class="draft">0</span>';	
	 			}
	 			/* result = result +'<div class="progress">';
	 			if(opt.expenseApprovedTotal != null && opt.expenseApprovedTotal !='' && opt.expenseApprovedTotal!='Undefined'){
	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.expenseApprovedTotal+'</div>';
	 			}else{
	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
	 			}
	 			if(opt.expenseUnApprovedTotal != null && opt.expenseUnApprovedTotal !='' && opt.expenseUnApprovedTotal!='Undefined'){
	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.expenseUnApprovedTotal+'</div>';
	 			}else{
	 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
	 			}
	 			result = result +'</div>';  */                   
	 			result = result +'</div>';
	 			result = result +'</td>';
 		
			/* result = result +'<td><div class="sm"><b class="proj_active">'+opt.projectStatus+'</b></div></td>'; */
			
			result = result +'<td>';
 			if(opt.claimedExpenseTotal != null && opt.claimedExpenseTotal !='' && opt.claimedExpenseTotal!='Undefined'){
 			result = result +'<div class="week"><span class="draft">'+opt.claimedExpenseTotal+'</span>';
 			}else{
 				result = result +'<div class="week"><span class="draft">0</span>';	
 			}
 			/* result = result +'<div class="progress">';
 			if(opt.claimedExpenseApprovedTotal != null && opt.claimedExpenseApprovedTotal !='' && opt.claimedExpenseApprovedTotal!='Undefined'){
 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">'+opt.claimedExpenseApprovedTotal+'</div>';
 			}else{
 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-info" role="progressbar" style="width:60%">0</div>';	
 			}
 			if(opt.claimedExpenseUnApprovedTotal != null && opt.claimedExpenseUnApprovedTotal !='' && opt.claimedExpenseUnApprovedTotal!='Undefined'){
 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">'+opt.claimedExpenseUnApprovedTotal+'</div>';
 			}else{
 			result = result +'<div class="progress-bar progress-bar-striped active progress-bar-warning" role="progressbar" style="width:40%">0</div>';	
 			}
 			result = result +'</div>';  */                   
 			result = result +'</div>';
 			result = result +'</td>'; 
 			
 			result = result +'</tr>';
 		}
 			result = result +'</tbody>';
			result = result +'</table>';
 			$("#expensesReportsListDiv").empty();
			$("#expensesReportsListDiv").append(result);
			pagination();
    	 }
    	 else {
    		 result = "<div class='dataTables_empty'></div>";
    		 $("#expensesReportsListDiv").empty();
 			 $("#expensesReportsListDiv").append(result);
    	 }
     }
});	
}     
</script>
 
 <script>
 function exportExpensesReports(type){
	var frequencyMonth =document.getElementById("selectedQuarter").value;
 window.location.href = sourceleadUrl+"/expensesReportExportList?"+tokenParamName+"="+tokenValue+"&frequencyMonth="+frequencyMonth+"&exportType="+type;
 }
 </script>
 
 
 <script>
function pagination(){ 
	//$.fn.dataTable.moment( 'DD-MM-YYYY' );
    $('#expensesReportsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"autoWidth": false,
		//"scrollX": true,
    	"aaSorting": [[7,'desc']],
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#expensesReportsPagination").wrap("<div class='overflow_Auto'></div>");
}
</script>
 
 
<jsp:include page="../masterFooter.jsp" />