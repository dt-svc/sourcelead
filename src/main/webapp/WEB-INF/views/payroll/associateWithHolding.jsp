<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/payrollReports.js"></script>

<script>
$(document).ready(function(){
	associateWithHolding();
	populateDepartment();
});
</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Associate withholding</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard'/>" title="Home">Home</a>&raquo;<a
				href="<c:url value='${appPath}/usPayrollReport'/>"> US Payroll Reports List</a>&raquo;Associate withholding
		</div>
    </section>
    <div class="contentareaInner-styles clearfix">
  <div class="timesheet_out" id="payrollRegister" style="display: block;">
      <div class="clearfix">
        
        <div class="positionRelative reportHead clearfix"> <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="refreshPage();"></a></span>
  <ul class="pull-right list-unstyled list-inline">
    <li><a class="newAdBtn mr5 hidden" href="/app/userCreation">+  Associate</a></li>
    <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
    <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
      <div class="newActPop" style="">
       <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> 
       		<a href="javascript:void(0)" onclick="exportToExcel();" title="Excel">Export Excel</a>
        </span> 
      </div>
    </li>
    <li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/>
		<span class='repSearchIcon' onClick='associateWithHoldingSearch();'></span></li>
  </ul>
</div>
        <div class="require_wrap_table tableFirstSecond clearfix">
        <div id="associateWithHoldingDIV" class="datatable_filter"></div>
        </div>
      </div>
    </div>
      <!-- Content Area Ends--> 
    </div>
  </section>
  
  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
    <h5 class="headerAdsrch">Filters <a href="javascript:void(0)" class="close-div" onclick="closePop()"><i class="fa fa-close"></i></a> </h5>
    <div class="addInner">
      <div class="row">
        <div class="col-md-3 mb positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="deptSearch" onkeyup="deptSearchFunction()" placeholder="Department" title="" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="department">

            </ul>
          </div>
        </div>
        <div class="col-md-3 positionRelative">
          <div class="clearfix dynamic_search filterDynamicsearch">
            <input id="employeeType" onkeyup="employeeTypeFunction()" placeholder="Employee Type" title="Type in a roles" type="text">
            <span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span> </div>
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="employeeTypeUL">
              <li class="checkBox positionRelative">
                <input name="empType" value="C2C-1099" type="checkbox" id="c2c-1099">
                <label>C2C-1099</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="empType" value="W-2" type="checkbox" id="w-2">
                <label>W-2</label>
              </li>
              <li class="checkBox positionRelative">
                <input name="empType" value="C2H" type="checkbox" id="c2h">
                <label>C2H</label>
              </li>
               <li class="checkBox positionRelative">
                <input name="empType" value="C2H -1099" type="checkbox" id="c2h-1099">
                <label>C2H -1099</label>
              </li>
               <li class="checkBox positionRelative">
                <input name="empType" value="Part Time" type="checkbox" id="partTime">
                <label>Part Time</label>
              </li>
               <li class="checkBox positionRelative">
                <input name="empType" value="Full Time" type="checkbox" id="fullTime">
                <label>Full Time</label>
              </li>
               <li class="checkBox positionRelative">
                <input name="empType" value="Other" type="checkbox" id="other">
                <label>Other</label>
              </li>
            </ul>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
    </div>
<div class="clearfix"></div>
    <div class="clearfix advancedsearch-footer">
      <div class="pull-right mr">
        <input value="Apply" id="search_requiremet" onclick="closePop();associateWithHolding();" class="btn btn-sm btn-success" type="button">
        <input value="Reset" id="clear_search" onclick="resetFilter();" class="btn btn-sm btn-primary" type="button">
      </div>
    </div>
  </div>
  
  
  <script>
function associateWithHolding(){
	
	var dept = [];
	var empType = [];
	 
         $('input:checkbox[name=deptName]:checked').each(function() 
          {
    	  dept.push($(this).val());
          });
    
    
        $('input:checkbox[name=empType]:checked').each(function() 
    	    {
    	       empType.push($(this).val());
    	    });
    
   
        var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getAssociateWithHoldingReports?searchValue="+searchValue+"&dept="+dept+"&empType="+empType;
		associateWithHoldingList(url);	
}

</script>


<script>
	function associateWithHoldingList(url) {
		$("#associateWithHoldingDIV").empty();
		var result = "";
		result = result + '<table id="aasociatewithHoldingTabble" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Associate ID</th>';
		result = result + '<th>Name</th>';
		result = result + '<th>SSN</th>';
		result = result + '<th>Work Location</th>';
		result = result + '<th>Federal Filling Status</th>';
		result = result + '<th>Federal Allowances</th>';
		result = result + '<th>State</th>';
		result = result + '<th>State Filling Status</th>';
		result = result + '<th>State Allowances</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#associateWithHoldingDIV").append(result);
		$("#noOfChecks").text(0);
		$("#liveChecks").text(0);
		associateWithHoldingData(url);

	}
</script>

<script>
	function associateWithHoldingData(url) {
		var colIndex = 0;
		$('#aasociatewithHoldingTabble')
				.DataTable(
						{
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"ajax" : $.fn.dataTable.pipeline({
								url : url,
								method : 'POST',
								pages : 10
							}),
							"columns" : [ {
								"data" : "employeeId"
							}, {
								"data" : "empName"
							}, {
								"data" : "ssnNumber"
							}, {
								"data" : "workLocation"
							}, {
								"data" : "federalFillingStatus"
							}, {
								"data" : "federalTaxAllowances"
							}, {
								"data" : "residenceStateCode"
							}, {
								"data" : "stateFillingStatus"
							}, {
								"data" : "stateTaxAllowances"
							}],
							"columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "employeeId",
										"render" : function(data, type, opt,meta) {
											var result = "";
										 	if(opt.employeeId != null && opt.employeeId != '' && opt.employeeId != 'undefined'){
												result = result+ ' <td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
											}else{
												result = result+ ' <td><div class="tblminwidth-75">--</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "empName",
										"render" : function(data, type, opt,meta) {
											var result = "";
											 if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
												result = result+ '<td><div class="tblminwidth-75">'+opt.firstName+' '+opt.lastName+'</div></td>';
											}else{
												result = result+ '<td><div class="tblminwidth-75">'+opt.firstName+'</div></td>';
											} 
											
											return result;

										}
									},
									{
										"targets" : colIndex++,
										"data" : "ssnNumber",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											 if(opt.ssnNumber != null && opt.ssnNumber != '' && opt.ssnNumber != 'undefined'){
												result = result + '<td class="tblminwidth-75"><div>'+opt.ssnNumber+'</div></td>';
											}else{
												result = result + '<td class="tblminwidth-75"><div>--</div></td>';
											} 
											
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "workLocation",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.workLocation != null && opt.workLocation != ''){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="'+opt.workLocation+'">'+opt.workLocation+'</div></td>'; 
											}else{
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>'; 
											}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "federalFillingStatus",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.federalFillingStatus != null && opt.federalFillingStatus != ''){
											result = result+ '<td><div class="tbldata-dotdot-vsm" title="'+opt.federalFillingStatus+'">'+opt.federalFillingStatus+'</div></td>'; 
											}else{
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>';
											}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "federalTaxAllowances",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.federalTaxAllowances != null && opt.federalTaxAllowances != ''){
												 result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.federalTaxAllowances+'</div></td>'; 
											}else{
												 result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>'; 
											}
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "residenceStateCode",
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.residenceStateCode != null && opt.residenceStateCode != ''){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.residenceStateCode+'</div></td>';
											}else{
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>';
											}
											  
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "stateFillingStatus",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.stateFillingStatus != null && opt.stateFillingStatus != ''){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="'+opt.stateFillingStatus+'">'+opt.stateFillingStatus+'</div></td>'; 
											}else{
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>'; 
											}
											 
											return result;
										}
									},{
										"targets" : colIndex++,
										"data" : "stateTaxAllowances",
										"sortable": false,
										"render" : function(data, type, opt,meta) {
											var result = "";
											if(opt.stateTaxAllowances != null && opt.stateTaxAllowances != ''){
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">'+opt.stateTaxAllowances+'</div></td>';		
											}else{
												result = result+ '<td><div class="tbldata-dotdot-vsm" title="">--</div></td>';
											}
											
											return result;
										}
									}],
							"scrollCollapse" : false,
							"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
							"aaSorting" : [ [ 3, 'desc' ] ],
							"bLengthChange" : true,
							"bDestroy" : true,
							"ordering" : true,
							"oLanguage" : {
								"sSearch" : "",
								"sZeroRecords" : "",
								"sEmptyTable" : "",
								"sProcessing" : "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
							}
						});
		$("#aasociatewithHoldingTabble").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
	}
	
	$("#dataTableSearch").keyup(function(e) {
		if (e.which === 13) {
			associateWithHolding();
		}
	});
</script>




<script>
function associateWithHoldingSearch(){
	associateWithHolding();
}
</script>




<script>	
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});
	// Close Advanced Search
	function closePop(){
	$("div.cand-advsrch-popup").hide();
	$(".mask").hide();
	}
//Date Picker
$( function(){
	$("#frmDate").datepicker();
	$("#toDate").datepicker();
 });
</script>

<script>
function openPop(){
	   $(".mask").show();
	   $("div.cand-advsrch-popup").toggle();
	   
	  /*  function getPayGroupList(id){ */
			$.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/findAllDepartments?"+tokenParamName+"="+tokenValue                 , 
			        success: function(json) {
			        	 var result = "";
			        	 for(var i = 0;i<json.length;i++){
			        		 var opt = json[i];
			        		 result = result + '<li class="checkBox positionRelative">';
				        	 result = result + '<input name="deptName" value='+opt.id+' type="checkbox" id="depName">';
				        	 result = result + '<label>'+opt.departmentName+'</label>';
				        	 result = result + '</li>';	 
			        	 }
			        	 $("#department").empty();
			        	 $("#department").append(result);
			        },
			    });
		/* } */
	}
</script>

<script>
function populateDepartment(){
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/findAllDepartments?"+tokenParamName+"="+tokenValue                 , 
        success: function(json) {
        	 var result = "";
        	 for(var i = 0;i<json.length;i++){
        		 var opt = json[i];
        		 result = result + '<li class="checkBox positionRelative">';
	        	 result = result + '<input name="deptName" value='+opt.id+' type="checkbox">';
	        	 result = result + '<label>'+opt.departmentName+'</label>';
	        	 result = result + '</li>';	 
        	 }
        	 $("#department").empty();
        	 $("#department").append(result);
        },
    });
}
</script>
  
  
  
<script>
function deptSearchFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("deptSearch");
    filter = input.value.toUpperCase();
    ul = document.getElementById("department");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}
</script>


<script>
function employeeTypeFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("employeeType");
    filter = input.value.toUpperCase();
    ul = document.getElementById("employeeTypeUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}
</script>

<script>
function resetFilter(){
	document.getElementById("c2c-1099").checked=false;
	document.getElementById("w-2").checked=false;
	document.getElementById("c2h").checked=false;
	document.getElementById("c2h-1099").checked=false;
	document.getElementById("partTime").checked=false;
	document.getElementById("fullTime").checked=false;
	document.getElementById("other").checked=false;
	document.getElementById("depName").checked=false;
}
</script>

<script>
function refreshPage(){
    window.location.reload();
} 
</script>

<script>
function exportToPdf(){
	var dept = [];
	var empType = [];
	 
         $('input:checkbox[name=deptName]:checked').each(function() 
          {
    	  dept.push($(this).val());
          });
    
    
        $('input:checkbox[name=empType]:checked').each(function() 
    	    {
    	       empType.push($(this).val());
    	    });
    
   
        var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";

    	window.location.href = sourceleadUrl+"/exportAssociateWithholding?searchValue="+searchValue+"&dept="+dept+"&empType="+empType+"&exportType=pdf";
}
</script>

<script>
function exportToExcel(){
	var dept = [];
	var empType = [];
	 
         $('input:checkbox[name=deptName]:checked').each(function() 
          {
    	  dept.push($(this).val());
          });
    
    
        $('input:checkbox[name=empType]:checked').each(function() 
    	    {
    	       empType.push($(this).val());
    	    });
    
   
        var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";

    	window.location.href = sourceleadUrl+"/exportAssociateWithholding?searchValue="+searchValue+"&dept="+dept+"&empType="+empType+"&exportType=xls";
}
</script>


<script> 
function resetFilter(){
	$("ul#department > li input:checkbox").prop("checked",false);
	$("ul#employeeTypeUL > li input:checkbox").prop("checked",false);
	$("#dataTableSearch").val('');
	associateWithHolding();
}
</script>


<jsp:include page="../masterFooter.jsp" />