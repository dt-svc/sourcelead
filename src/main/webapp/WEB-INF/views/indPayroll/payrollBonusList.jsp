<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script> 
	$(document).ready(function(){
		getPayRollBonusList()
	});
</script>


<section class="content-wraper contentWraper">
<section class="content-header clearfix">
<div class="pull-left">
<h2>Bonus List</h2>
</div>
 <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>  &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a>  &raquo; Bonus List</div>
</section>

<div class="clearfix"></div>
<div class="timesheet_out">
	<div class="tablelist-wrap">
	<div class="timesheetHead minHeight40 mt minHeight40">
		<div class="pull-right">
			<ul class="list-unstyled list-inline">
					
				<li class="positionRelative paddingLeft">
				  <div class="dynamic_search">
		   			<input type='text' placeholder='Search' id='bonusDataTableSearch'/>
		   			<span class="cursorPointer" title="Search"><i class="fa fa-search" onClick="payrollBonusList();"></i></span>
				  </div>
			    </li>
			  
			     <li class="positionRelative">
					<a href="${appPath}/payrollBonus" class="addBtn">+ Create</a>
				</li>
			  
			</ul>
		</div>
	</div>
	<div class="styleTables positionRelative">
	<div id="bonusListLoading"></div>
	<div id="choicepopUp"></div>
	<div id="payrollBonusList" class="datatable_filter"></div>
	</div>
	</div>
</div>
</section>





<script>
 function getPayRollBonusList(){
	 
	 $("#payrollBonusList").empty();
	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  var locationCode = '${SourceLeadWeb_locationCode}';
	  $("#bonusListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  bonusListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  bonusListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  bonusListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  bonusListDate = 'YYYY-DD-MM'; 
	  }
	  
	  
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +"</div>";
	 	
	 	result = result + '<table id="bonusListTable"  cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '	<th>Payment Month</th>';
	  	result = result + '  <th>Pay Date<br>(<small class="custom-small">'+bonusListDate+'</small>)</th>';
	  	result = result + '  <th>Duration</th>';
	  	result = result + '  <th>Min Bonus (%)</th>';
	  	result = result + '  <th>Max Bonus (%)</th>';
	  	result = result + '  <th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#payrollBonusList").empty();
	    $("#payrollBonusList").append(result);
	    
	    payrollBonusList();
	  
 }
 </script>
 
 <script>
 function payrollBonusList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#bonusDataTableSearch").val();
	 var locationCode = '${SourceLeadWeb_locationCode}';
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getPayRollBonusList?searchValue="+searchValue+"&locationCode="+locationCode;
	 bonusListPagination(url,sourceleadUrl);
	 $("#bonusListLoading").removeAttr("style");
	 
 }
 </script>
 
 
 
 <script>
function bonusListPagination(url,sourceleadUrl){
	var colIndex = 0;
    $('#bonusListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 1
        }),
        "columns": [
            { "data": "paymentMonth" },
            { "data": "paymentDate"},
            { "data": "fromMonth"},
            { "data": "minPercentage" },
            { "data": "maxPercentage"},
            { "data": "actions"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "paymentMonth",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		 result = result +"<td><div><span>"+opt.paymentMonth+"</span></div></td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "paymentDate",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result +"<td><div><span>"+opt.paymentDate+"</span></div></td>";
	              return result;
              	 	
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "fromMonth",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  var duration = "";
	            	  if(opt.fromMonth != null && opt.fromMonth != '' && opt.fromMonth != 'undefined'){
	            		  duration = opt.fromMonth+" to "+opt.toMonth;
	            	  }else{
	            		  duration = "--";
	            	  }
	            	  result = result +"<td><div><span>"+duration+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "minPercentage",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td><div><span>"+opt.minPercentage+"</span></div></td>";
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "maxPercentage",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td><div><span>"+opt.maxPercentage+"</span></div></td>";
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
	            	    result = result +"<a href='<c:url value='/app/payrollBonus?uuid="+opt.uuid+"'/>'><span title='Edit Bonus'><i class='glyphicon glyphicon-pencil'></i></span></a>&nbsp;&nbsp;";
	            	    result = result +'<a><span title="Delete Bonus"><i class="glyphicon glyphicon-trash" onClick="deleteBonus(\''+opt.uuid+'\')";></i></span></a>';
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
    $("#bonusListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#bonusDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#bonusDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#bonusDataTableSearch').val();
   	    	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		payrollBonusList();
   	    	}else{
   	    		$("#bonusDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#bonusDataTableSearch').val(searchData);
    	   }
    	});
}

</script>

<script>
function deleteBonus(uuid){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to delete bonus ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="deletePayrollBonus(\''+uuid+'\');"><input class="u_can_color" type="button" id="cancel-id" value="Cancel" onClick="cancel();"></b>';
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function deletePayrollBonus(uuid){
	$("#confirm-id").attr("disabled", "disabled");
	$("#cancel-id").attr("disabled", "disabled");
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
    	type: "POST",
    	url : sourceleadUrl+"/deletePayrollBonus?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid, 
        success: function(result) {
        	if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		 location.replace(sourceleadUrl + "/payrollBonusList");
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