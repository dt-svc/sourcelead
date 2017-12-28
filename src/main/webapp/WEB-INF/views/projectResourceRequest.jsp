<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
 <script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
 
 <script> 
	$(document).ready(function(){
		getProjectResourceReqestsListCount();
		resourceRequestList("Waiting For Approval");
	});
</script>
 
 
    <section class="clearfix">
      <div class="content-dashboard clearfix"> 
      	  <div class="clearfix dashboard-widget mb col-md-12">
      	    <h4>Resource Requests</h4>
      	    
      	    <div class="project_side_wrap_inn_tabs">
      	    <div id="resourceRequestsDivLoading"></div>
			    <ul class="project-tabs-menu">
			        <li class="current" id="waiting"><a data-toggle="tab" href="javascript:void(0)" onclick="resourceRequestList('Waiting For Approval')">Waiting (<span id="waitingCount"></span>)</a></li>
			        <li id="requestApproved"><a data-toggle="tab" href="javascript:void(0)"  onclick="resourceRequestList('Approved')">Approved (<span id="approvedCount"></span>)</a></li>
			        <li id="requestRejected"><a data-toggle="tab" href="javascript:void(0)" onclick="resourceRequestList('Rejected')">Rejected (<span id="rejectedCount"></span>)</a></li>
			        <li id="requestOnhold"><a data-toggle="tab" href="javascript:void(0)" onclick="resourceRequestList('On hold')">On hold (<span id="onholdCount"></span>)</a></li>
			        <div class="clearfix"></div>
			    </ul>
			    <div id="resourceRequestList"></div>
			    <div class="vendor_wrap_mask"></div>
   				<div id="requestedAssociatesDivLoading"></div>
   				<div id="requestOnHoldReasonDiv"></div>
   				<div id="rejectedReasonDiv"></div>
           </div>
      	  </div>
      </div>
    
 </section>
 
 <script>
 function getProjectResourceReqestsListCount(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 alert
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getProjectResourceRequestsCounts?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(json) {
	        	$("#waitingCount").html(json.waitingCount);
	        	$("#approvedCount").html(json.approvedCount);
	        	$("#rejectedCount").html(json.rejectedCount);
	        	$("#onholdCount").html(json.onholdCount);
	        }
	   
     });
 }
 </script>
 
 
 
 <script>
function resourceRequestList(requestType){
	  
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='requestAssociatesTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter mt5">';
  		result = result +'<div class="clearfix popupFilter datatable_filter">'; 
	 	
  		result = result +'<table id="requestAssociates" class="display" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'<th>Request Id</th>';
		result = result +'<th>Project</th>';
		result = result +'<th>Associate</th>';
		result = result +'<th>Initiator</th>';
		result = result +'<th>Approver</th>';
		result = result +'<th>Consulting</th>';
		result = result +'<th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	 
	    $("#resourceRequestList").empty();
	    $("#resourceRequestList").append(result);
	    requestAssociatesDataTableList(requestType);
}
</script>


<script>
function requestAssociatesDataTableList(requestType){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var approverUserId = '${SourceLeadWeb_user.id}';
	 var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	 
	 var searchValue = $("#requestAssociatesTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getProjectPlanningResourceRequestList?searchValue="+searchValue+"&resourceStatus="+requestType;
	 resourceRequestPagination(url,requestType,sourceleadUrl);
	 
	   if(requestType == 'Waiting For Approval'){
	    	 $("#waiting").addClass("current");
	    	 $("#requestApproved").removeClass("current");
	    	 $("#requestRejected").removeClass("current");
	    	 $("#requestOnhold").removeClass("current");
	    }
	   
	    else if(requestType == 'Approved'){
	    	 $("#requestApproved").addClass("current");
	    	 $("#waiting").removeClass("current");
	    	 $("#requestRejected").removeClass("current");
	    	 $("#requestOnhold").removeClass("current");
	    }
	    
	    else if(requestType == 'Rejected'){
	    	 $("#requestRejected").addClass("current");
	    	 $("#waiting").removeClass("current");
	    	 $("#requestApproved").removeClass("current");
	    	 $("#requestOnhold").removeClass("current");
	    }
	    
	    else if(requestType == 'On hold'){
	    	 $("#requestOnhold").addClass("current");
	    	 $("#waiting").removeClass("current");
	    	 $("#requestApproved").removeClass("current");
	    	 $("#requestRejected").removeClass("current");
	    	
	    }
	    
}
</script>


<script>
function resourceRequestPagination(url,requestType,sourceleadUrl){
	var colIndex = 0;
    $('#requestAssociates').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "projectPlanningRequestId" },
            { "data": "projectName" },
            { "data": "candidateName"},
            { "data": "requestedUser"},
            { "data": "approverName"},
            { "data": "consultingType"},
            { "data": ""}
           
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "projectPlanningRequestId",
    	  	"render": function(data, type, opt, meta) {
    	  		 var result = "";
    	  		 var flag = false;
    	  		 result = result + '<td><div  class="tbldata-dotdot-vsm"><a href="'+sourceleadUrl+'/requestedAssociatesList?requestUUID='+opt.projectPlanningRequestUUID+'">'+opt.projectPlanningRequestId+'</a></div></td>';
                 return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "projectName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/projectSummary?projectUUID='+opt.projectUUID+'&projectClientUUID='+opt.projectClientUUID+'&tabType=resources" title="'+opt.projectName+'">'+opt.projectName+'</a></div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "candidateName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/candidatesList?candidateUUID='+opt.candidateUUID+'" title="'+opt.candidateName+'">'+opt.candidateName+'</a></div></td>';
	              return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "requestedUser",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.requestedUser+'">'+opt.requestedUser+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "approverName",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.approverName+'">'+opt.approverName+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "consultingType",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.consultingType+'">'+opt.consultingType+'</span></div></td>';
	            		return result;
	              }
		     } ,{
		    	  "targets": colIndex++,
	              "data": "",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    
	            	    result = result + '<td id="action'+opt.requestUUID+'"><div class="tblminwidth-100">';
			    		if(opt.resourceStatus == 'Waiting For Approval' || opt.resourceStatus == 'On hold'){
			    			
			    			if(opt.resourceStatus == 'On hold'){
			    			  result = result +"<i class='glyphicon glyphicon-comment edit-reason'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>&nbsp;|&nbsp;";
			    			}
			    			result = result + '<a href="javascript:void(0)" onclick="acceptAssociate(\''+opt.requestUUID+'\',\''+requestType+'\');"><i class="fa fa-check text-success" alt="Approve" title="Approve"></i></a>&nbsp;|&nbsp;';
				    		result = result + '<a href="javascript:void(0)" onclick="rejectAssociate(\''+opt.requestUUID+'\',\''+opt.candidateName+'\');"><i class="fa fa-times text-danger" alt="Reject" title="Reject"></i></a>';
				    		
				    		if(opt.resourceStatus == 'Waiting For Approval'){
				    			result = result + '&nbsp;|&nbsp;<a href="javascript:void(0)" onclick="onholdAssociateRequest(\''+opt.requestUUID+'\',\''+opt.requestedUser+'\',\''+requestType+'\');"><i class="fa fa-clock-o text-primary" alt="On hold" title="On hold"></i></a>';
				    		}
				    		
			    		}else{
			    			if(opt.resourceStatus == 'Rejected'){
			    				result = result +"<i class='glyphicon glyphicon-comment edit-reason'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>";
			    				result = result + '<b class="bg-red">'+opt.resourceStatus+'</b>';
			    			}else{
			    				result = result + '<b>'+opt.resourceStatus+'</b>';
			    			}
			    			
			    		}
			    		
			    		result = result + '</div></td>';
			    		return result;
	              }
		     } ],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#requestAssociates").wrap("<div class='clearfix require_wrap_table'></div>"); 
    $("input[type=search]").hide();
    $("#requestAssociatesTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   requestAssociatesDataTableList(requestType);
    	   }
    	});
}

</script>


<script>
function acceptAssociate(requestUUID,requestType){
	 var userId = '${SourceLeadWeb_userId}';
     var orglocationId = '${SourceLeadWeb_orgLocationId}';
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#requestedAssociatesDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 $.ajax({
		    type : "POST",
	    	url: sourceleadUrl+"/acceptRequestedAssociate?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId, 
	        success: function(result) {
	        	$("#action"+requestUUID).html("<span>Accepted</span>");
	        	getProjectResourceReqestsListCount();
	        	resourceRequestList(requestType);
	        	$("#requestedAssociatesDivLoading").removeAttr("style"); 
	        },
	    });
}
</script>


<script>
function rejectAssociate(requestUUID,candidateName){
	
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Candidate Name">Name :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+candidateName+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_rejectedReason"  onclick="saveRejectAssociate(\''+requestUUID+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#rejectedReasonDiv").empty();
    $("#rejectedReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
</script>

<script>
function cancelRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>

<script>
function cancelRequestOnholdReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>

<script>
function saveRejectAssociate(requestUUID){
	
	if ($('#rejectedReason_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#rejectedReason_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		
	    var form = $('#rejectedReason_form')[0];
	    var formData = new FormData(form);
	  	formData.append("json",jsonData)
	  	 var userId = '${SourceLeadWeb_userId}';
        var orglocationId = '${SourceLeadWeb_orgLocationId}';
        var sourceleadUrl ='${sourceleadUrl}';
      
        $("#submit_rejectedReason").attr("disabled", "disabled");
		
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/rejectRequestedAssociate?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orglocationId="+orglocationId, 
		        success: function(result) {
		        	cancelRejectedReason();
		        	$("#action"+requestUUID).html("<span>Rejected</span>")
		        	getProjectResourceReqestsListCount();
		        	resourceRequestList("Waiting For Approval");
		        	$("#loading").removeAttr("style"); 
		        },
		    }); 
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#rejectedReason_form', function() {
	   		$("#rejectedReason_form").showHelpOnFocus();
	   		$("#rejectedReason_form").validateOnBlur(false, validationSettings);
	   	});
	
}
</script>

<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script>
 
<script>
function acceptAssociateRequest(requestUUID){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var userId = '${SourceLeadWeb_userId}';
     var orglocationId = '${SourceLeadWeb_orgLocationId}';
	 $("#resourceRequestsDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 $.ajax({
		    type : "POST",
	    	url: sourceleadUrl+"/acceptAssociateRequest?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId, 
	        success: function(result) {
	        	location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_orgLocationId}");  
	        },
	    });
	 
}
</script>

<script>
function onholdAssociateRequest(requestUUID,Initiator,requestType){
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="onHold_form" name="onHold_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Request On Hold Reason<i class="fa fa-times" onclick="cancelRequestOnholdReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Initiator Name">Initiator :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+Initiator+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_onHold"  onclick="saveOnHoldRequest(\''+requestUUID+'\',\''+requestType+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRequestOnholdReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#requestOnHoldReasonDiv").empty();
    $("#requestOnHoldReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
	 
}
</script>

<script>
function saveOnHoldRequest(requestUUID,requestType){
	
if ($('#onHold_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#onHold_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
	    var form = $('#onHold_form')[0];
	    var formData = new FormData(form);
	  	formData.append("json",jsonData)
	  	
        var sourceleadUrl ='${sourceleadUrl}';
        var userId = '${SourceLeadWeb_userId}';
        var orglocationId = '${SourceLeadWeb_orgLocationId}';
        
        $("#submit_onHold").attr("disabled", "disabled");
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/onholdRequestedAssociate?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId,
		        success: function(result) {
		        	
		        	cancelRequestOnholdReason();
		        	$("#action"+requestUUID).html("<span>Onhold</span>")
		        	getProjectResourceReqestsListCount();
		        	resourceRequestList(requestType);
		        	$("#loading").removeAttr("style"); 
		        },
		    });  
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#onHold_form', function() {
	   		$("#onHold_form").showHelpOnFocus();
	   		$("#onHold_form").validateOnBlur(false, validationSettings);
	   	});
}


</script>

<script>
function rejectAssociateRequest(requestUUID,Initiator){
	
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Request Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Initiator Name">Initiator :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+Initiator+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_rejectedReason"  onclick="saveRejectAssociateRequest(\''+requestUUID+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRequestRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#requestRejectedReasonDiv").empty();
    $("#requestRejectedReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
</script>

<script>
function cancelRequestRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>


<script>
function saveRejectAssociateRequest(requestUUID){
	
	if ($('#rejectedReason_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#rejectedReason_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		
	    var form = $('#rejectedReason_form')[0];
	    var formData = new FormData(form);
	  	formData.append("json",jsonData)
        var sourceleadUrl ='${sourceleadUrl}';
        var userId = '${SourceLeadWeb_userId}';
        
        var orglocationId = '${SourceLeadWeb_orgLocationId}';
        $("#submit_rejectedReason").attr("disabled", "disabled");
		
        $("#resourceRequestsDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/rejectAssociatesRequest?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orglocationId="+orglocationId,
		        success: function(result) {
		        	cancelRejectedReason();
		        	location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_orgLocationId}");  
		        },
		    });  
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#rejectedReason_form', function() {
	   		$("#rejectedReason_form").showHelpOnFocus();
	   		$("#rejectedReason_form").validateOnBlur(false, validationSettings);
	   	});
	
}
</script>
 
 
 