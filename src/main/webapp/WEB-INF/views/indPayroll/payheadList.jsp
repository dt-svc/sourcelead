<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script> 
	$(document).ready(function(){
		getPayRollPayheadList()
	});
</script>

<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Payroll</h2>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a> &raquo; Payhead List</div>
</section>
 <div class="clearfix"></div>
 
  <div class="tablelist-wrap">
    <div class="timesheet_head">
      <ul>
         <li>
               <a href="<c:url value='${appPath}/payrollPayhead'/>" class="addcon-pop"><i class="fa fa-plus paddingRight"></i>Add</a>
         </li>
      <li>
      <a class="addcon-pop" onclick="refreshPayheads()"><i class="fa fa-refresh paddingRight"></i>Refresh Payheads</a><small class="text-muted ml">|</small>
      <a href="<c:url value='${appPath}/payrollSalarySetup'/>" class="addcon-pop"><i class="fa fa-plus paddingRight"></i>Salary Setup</a><small class="text-muted ml">|</small>
      </li>
        <li></li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      <div class="remove_search">
        <input type="text" placeholder="Search" id="payheadDataTableSearch" onblur="payrollPayheadList();"/>
      </div>
    </div>
    
	<div class="styleTables">
		<div id="payheadListLoading"></div>
		<div id="choicepopUp"></div>
		<div id="payrollPayheadList" class="datatable_filter"></div>
	</div>
</div>
</section>


<script>
 function getPayRollPayheadList(){
	 $("#payrollPayheadList").empty();
	  var sourceleadUrl ='${sourceleadUrl}';
	  var locationCode = '${SourceLeadWeb_locationCode}';
	  $("#payheadListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  payheadListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  payheadListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  payheadListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  payheadListDate = 'YYYY-DD-MM'; 
	  }
	  
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +"</div>";
	 	result = result + '<table id="payheadListTable"  cellspacing="0" width="100%" >';
	 	result = result +"<thead>";
		result = result +"	<tr>";
		result = result +"		<th>Name</th>";
		result = result +"		<th>Short Code</th>";
		result = result +"		<th>Type</th>";
		result = result +"		<th>Tax</th>";
		result = result +"		<th>Component</th>";
		result = result +"		<th>Action</th>";
		result = result +"	</tr>";
		result = result +"</thead>";
	    result = result +"</table>";
	    $("#payrollPayheadList").empty();
	    $("#payrollPayheadList").append(result);
	    
	    payrollPayheadList();
	  
 }
 </script>
 
 <script>
 function payrollPayheadList(){
	 var searchValue = $("#payheadDataTableSearch").val();
	 var locationCode = '${SourceLeadWeb_locationCode}';
	 var listType = "PHList";
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getPayRollPayheadListWithPagination?searchValue="+searchValue+"&locationCode="+locationCode+"&listType="+listType;
	 payheadListPagination(url,sourceleadUrl);
	 $("#payheadListLoading").removeAttr("style");
	 
 }
 </script>
 
 
 
 <script>
function payheadListPagination(url,sourceleadUrl){
	var colIndex = 0;
    $('#payheadListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 1
        }),
        "columns": [
            { "data": "name" },
            { "data": "shortCode"},
            { "data": "type"},
            { "data": "tax"},
            { "data": "component"},
            { "data": "actions"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "name",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		 result = result +"<td><a href='<c:url value='/app/payrollPayheadSummary?uuid="+opt.uuid+"'/>'><div class='proj_show tbldata-dotdot_140'><span title='"+opt.name+"'>"+opt.name+"</span><i class='fa fa-binoculars'></i></div></a></td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "shortCode",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result +"<td><div><span>"+opt.shortCode+"</span></div></td>";
	              return result;
              	 	
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "type",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  var pType = "";
	            	  if(opt.type != null && opt.type != '' && opt.type != 'undefined'){
	            		  pType = opt.type;
	            	  }else{
	            		  pType = "--";
	            	  }
	            	  result = result +"<td><div><span>"+pType+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "tax",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var taxType = "";
		            	  if(opt.tax != null && opt.tax != '' && opt.tax != 'undefined'){
		            		  taxType = opt.tax;
		            	  }else{
		            		  taxType = "--";
		            	  }
		            	  result = result +"<td><div><span>"+taxType+"</span></div></td>";
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "component",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  var pComponent = "";
	            	  if(opt.component != null && opt.component != '' && opt.component != 'undefined'){
	            		  pComponent = opt.component;
	            	  }else{
	            		  pComponent = "--";
	            	  }
	            	  result = result +"<td><div><span>"+pComponent+"</span></div></td>";
	            	  return result;
	              }
		      },{
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var checked = 'checked';
	            	    if(opt.active=='NO'){
	            	    	checked = '';
	            	    }
	            	    result = result +"<td>";
	            	    result = result +"<div class='tblminwidth-50 mt10'>";
	            	    result = result +"<a  class='pull-left' href='<c:url value='/app/payrollPayhead?uuid="+opt.uuid+"'/>'><span title='Edit Payhead'><i class='glyphicon glyphicon-pencil'></i></span></a>&nbsp;&nbsp;";
	            	    /* result = result +'<a><span title="Delete Payhead"><i class="glyphicon glyphicon-trash" onClick="deletePayhead(\''+opt.uuid+'\')";></i></span></a>'; */
	            	    
	            	    result = result +"<div class='toggle-button pull-left ml'>";

	            	    result = result +"<label class='switch'>";
	            	    result = result +"<input type='checkbox' id='onOff"+opt.uuid+"' onclick='payheadHandler(\""+opt.uuid+"\");' "+checked+">";
						result = result +"<div class='slider round'></div>";
						result = result +"</label>";

	            	    result = result +"</div>";
	            	    
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
    $("#payheadListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;' class='project_wrap_table'></div>"); 
    $("input[type=search]").hide();
    $("#payheadDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#payheadDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#payheadDataTableSearch').val();
   	    	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		payrollPayheadList();
   	    	}else{
   	    		$("#payheadDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#payheadDataTableSearch').val(searchData);
    	   }
    	});
}

</script>

<script>
function payheadHandler(uuid) {
	var isChecked = 'YES';
	if($("#onOff"+uuid).is(":checked")){
		isChecked = 'YES';
	}else{
		isChecked = 'NO';
	}
	$.ajax({
		type : "POST",
		url: sourceleadUrl+"/payheadHandlerByOrg?uuid="+uuid+"&active="+isChecked,
		success :function(json) {
			
		},
	});
}
</script>

<script>
function refreshPayheads(){
	$("#payheadListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/refreshPayheadsForOrg",
		success :function(json) {
			payrollPayheadList();
		},
	});
}
</script>

<jsp:include page="../masterFooter.jsp" />