<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script> 
	$(document).ready(function(){
		getPayRollLoanAndAdvancesList()
	});
</script>


<section class="content-wraper contentWraper">
<section class="content-header clearfix">
<div class="pull-left">
<h2>Loan & Advances List</h2>
</div>
 <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>  &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a>  &raquo; Loan & Advances List</div>
</section>

<div class="clearfix"></div>
<div class="timesheet_out">
	<div class="tablelist-wrap">
	<div class="timesheetHead minHeight40 mt minHeight40">
		<div class="pull-right">
			<ul class="list-unstyled list-inline">
					
				<li class="positionRelative paddingLeft">
				  <div class="dynamic_search">
		   			<input type='text' placeholder='Search' id='pldDataTableSearch'/>
		   			<span class="cursorPointer" title="Search"><i class="fa fa-search" onClick="payrollLoanAndAdvList();"></i></span>
				  </div>
			    </li>
			  
			     <li class="positionRelative">
					<a href="${appPath}/payrollLoanAndAdvances" class="addBtn">+ Create</a>
				</li>
			  
			</ul>
		</div>
	</div>
	<div class="styleTables positionRelative">
	<div id="pldListLoading"></div>
	<div id="choicepopUp"></div>
	<div id="payrollpldList" class="project_wrap_table datatable_filter"></div>
	</div>
	</div>
</div>
</section>


<script>
 function getPayRollLoanAndAdvancesList(){
	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  var locationCode = '${SourceLeadWeb_locationCode}';
	  $("#pldListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	  
	  
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +"</div>";
	 	
	 	result = result + '<table id="pldListTable"  cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '	<th>Type</th>';
	  	result = result + '  <th>Name</th>';
	  	result = result + '  <th>Amount</th>';
	  	result = result + '  <th>Interest Rate (%)</th>';
	  	result = result + '  <th>Applicable For</th>';
	  	result = result + '  <th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#payrollpldList").empty();
	    $("#payrollpldList").append(result);
	    
	    payrollLoanAndAdvList();
	  
 }
 </script>
 
 
 <script>
 function payrollLoanAndAdvList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#pldDataTableSearch").val();
	 var locationCode = '${SourceLeadWeb_locationCode}';
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getPayrollLoanAndAdvancesList?searchValue="+searchValue+"&locationCode="+locationCode;
	 loanAndAdvListPagination(url,sourceleadUrl);
	 $("#pldListLoading").removeAttr("style");
 }
 </script>
 
 
 <script>
function loanAndAdvListPagination(url,sourceleadUrl){
	var colIndex = 0;
    $('#pldListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 1
        }),
        "columns": [
            { "data": "type" },
            { "data": "name"},
            { "data": "minAmount"},
            { "data": "interestRate" },
            { "data": "applicableFor"},
            { "data": "actions"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "type",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		 result = result +"<td><div><span>"+opt.type+"</span></div></td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "name",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result +"<td><div><span>"+opt.name+"</span></div></td>";
	              return result;
              	 	
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "minAmount",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  var amount = "";
	            	  if(opt.minAmount != null && opt.minAmount != '' && opt.minAmount != 'undefined'){
	            		  amount = opt.minAmount+" to "+opt.maxAmount;
	            	  }else{
	            		  amount = "--";
	            	  }
	            	  result = result +"<td><div><span>"+amount+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "interestRate",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var interestRate = "";
	            	    if(opt.interestRate != null && opt.interestRate != '' && opt.interestRate != 'undefined'){
	            	    	interestRate = opt.interestRate;
		            	  }else{
		            		  interestRate = "--";
		            	  }
	            	    result = result +"<td><div><span>"+interestRate+"</span></div></td>";
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "applicableFor",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var applicableFor = "";
	            	    if(opt.applicableFor != null && opt.applicableFor != '' && opt.applicableFor != 'undefined'){
	            	    	applicableFor = opt.applicableFor;
		            	  }else{
		            		  applicableFor = "--";
		            	  }
	            	    result = result +"<td><div><span>"+applicableFor+"</span></div></td>";
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td>";
	            	    result = result +"<div class='tbldata-dotdot_50'>";
	            	    result = result +"<a href='<c:url value='/app/payrollLoanAndAdvances?uuid="+opt.uuid+"'/>'><span title='Edit Loan & Advance'><i class='glyphicon glyphicon-pencil'></i></span></a>&nbsp;&nbsp;";
	            	    result = result +'<a><span title="Delete Loan & Advance"><i class="glyphicon glyphicon-trash" onClick="deleteLoanAndAdv(\''+opt.uuid+'\')";></i></span></a>';
	            	    result = result +"</div>";
	            	    result = result +"</td>";
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#pldListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#pldDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#pldDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#pldDataTableSearch').val();
   	    	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		payrollLoanAndAdvList();
   	    	}else{
   	    		$("#pldDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#pldDataTableSearch').val(searchData);
    	   }
    	});
}

</script>

<script>
function deleteLoanAndAdv(uuid){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to loan and advance ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="deletePayrollLoanAndAdv(\''+uuid+'\');"><input class="u_can_color" type="button" id="cancel-id" value="Cancel" onClick="cancel();"></b>';
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function deletePayrollLoanAndAdv(uuid){
	$("#confirm-id").attr("disabled", "disabled");
	$("#cancel-id").attr("disabled", "disabled");
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
    	type: "POST",
    	url : sourceleadUrl+"/deletePayrollLoanAndAdvances?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid, 
        success: function(result) {
        	if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		 location.replace(sourceleadUrl + "/payrollLoanAndAdvancesList");
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
        }
	 });
}


function cancel(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}

</script>

<jsp:include page="../masterFooter.jsp" />