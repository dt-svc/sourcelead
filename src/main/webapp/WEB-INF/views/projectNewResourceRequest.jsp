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
 function getNewProjectResourceReqestsListCount(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 alert
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getProjectNewResourceRequestsCounts?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(json) {
	        	$("#newWaitingCount").html(json.waitingCount);
	        	$("#newApprovedCount").html(json.approvedCount);
	        	$("#newRejectedCount").html(json.rejectedCount);
	        	$("#newOnholdCount").html(json.onholdCount);
	        }
	   
     });
 }
 </script>
 
 
 
 <script>
function newResourceRequestList(requestType){
	getNewProjectResourceReqestsListCount();
	  $('#newResourceRequestTabDiv').show();
	  $('#assocaiteRequestsTabDiv').hide();
	  $("#budgetTabDiv").hide();
	  $("#associateDiv").removeClass("active");
	  $("#newResourceDiv").addClass("active");
	  $("#budgetDiv").removeClass("active");
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' class='repSearchBar' placeholder='New Resource search' id='newRequestAssociatesTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter mt5">';
  		result = result +'<div class="clearfix popupFilter datatable_filter">'; 
	 	
  		result = result +'<table id="newRequestAssociates" class="display" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'<th>Request Id</th>';
		result = result +'<th>Project</th>';
		result = result +'<th>Role</th>';
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
    	 
	    $("#newResourceRequestList").empty();
	    $("#newResourceRequestList").append(result);
	    newRequestAssociatesDataTableList(requestType);
}
</script>


<script>
function newRequestAssociatesDataTableList(requestType){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var approverUserId = '${SourceLeadWeb_user.id}';
	 var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	 
	 var searchValue = $("#newRequestAssociatesTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getProjectPlanningNewResourceRequestList?searchValue="+searchValue+"&resourceStatus="+requestType;
	 newResourceRequestPagination(url,requestType,sourceleadUrl);
	 
	   if(requestType == 'Waiting For Approval'){
	    	 $("#newReqWaiting").addClass("activeIcon");
	    	 $("#newRequestApproved").removeClass("activeIcon");
	    	 $("#newRequestRejected").removeClass("activeIcon");
	    	 $("#newRequestOnhold").removeClass("activeIcon");
	    }
	   
	    else if(requestType == 'Approved'){
	    	 $("#newRequestApproved").addClass("activeIcon");
	    	 $("#newReqWaiting").removeClass("activeIcon");
	    	 $("#newRequestRejected").removeClass("activeIcon");
	    	 $("#newRequestOnhold").removeClass("activeIcon");
	    }
	    
	    else if(requestType == 'Rejected'){
	    	 $("#newRequestRejected").addClass("activeIcon");
	    	 $("#newReqWaiting").removeClass("activeIcon");
	    	 $("#newRequestApproved").removeClass("activeIcon");
	    	 $("#newRequestOnhold").removeClass("activeIcon");
	    }
	    
	    else if(requestType == 'On hold'){
	    	 $("#newRequestOnhold").addClass("activeIcon");
	    	 $("#newReqWaiting").removeClass("activeIcon");
	    	 $("#newRequestApproved").removeClass("activeIcon");
	    	 $("#newRequestRejected").removeClass("activeIcon");
	    	
	    }
	    
}
</script>


<script>
function newResourceRequestPagination(url,requestType,sourceleadUrl){
	var colIndex = 0;
    $('#newRequestAssociates').DataTable( { 
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
            { "data": "roleName"},
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
    	  		 result = result + '<td><div  class="tbldata-dotdot-vsm"><a href="'+sourceleadUrl+'/viewNewResourceRequestDashborad?requestUUID='+opt.projectPlanningRequestUUID+'">'+opt.projectPlanningRequestId+'</a></div></td>';
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
            	  result = result + '<td data-title="Id"><div class="tblminwidth-75" title="'+opt.roleName+'">'+opt.roleName+'</div></td>';
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
	            	    
	            	    result = result + '<td id="newResourceAction'+opt.requestUUID+'"><div class="tblminwidth-100">';
			    		if(opt.resourceStatus == 'Waiting For Approval' || opt.resourceStatus == 'On hold'){
			    			
			    			if(opt.resourceStatus == 'On hold'){
			    			  result = result +"<i class='glyphicon glyphicon-comment edit-reason colB'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>&nbsp;|&nbsp;";
			    			  result = result+" <br><span class='rejNam' title='"+opt.approvedUser+"'>By : "+opt.approvedUser+"</span>";
			    			}
			    			result = result + '<a href="javascript:void(0)" onclick="acceptNewAssociate(\''+opt.requestUUID+'\',\''+requestType+'\');"><i class="fa fa-check text-success" alt="Approve" title="Approve"></i></a>&nbsp;|&nbsp;';
				    		result = result + '<a href="javascript:void(0)" onclick="rejectNewAssociate(\''+opt.requestUUID+'\',\''+opt.roleName+'\');"><i class="fa fa-times text-danger" alt="Reject" title="Reject"></i></a>';
				    		
				    		if(opt.resourceStatus == 'Waiting For Approval'){
				    			result = result + '&nbsp;|&nbsp;<a href="javascript:void(0)" onclick="onholdNewAssociateRequest(\''+opt.requestUUID+'\',\''+opt.requestedUser+'\',\''+requestType+'\');"><i class="fa fa-clock-o text-primary" alt="On hold" title="On hold"></i></a>';
				    		}
				    		
			    		}else{
			    			if(opt.resourceStatus == 'Rejected'){
			    				result = result +"<i class='glyphicon glyphicon-comment colB edit-reason'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>";
			    				result = result + '<b class="bg-red">'+opt.resourceStatus+'</b>';
			    				result = result+" <br><span class='rejNam' title='"+opt.approvedUser+"'>By : "+opt.approvedUser+"</span>";
			    			}else{
			    				result = result + '<b class="bg-green">'+opt.resourceStatus+'</b>';
			    				result = result+" <br><span class='rejNam' title='"+opt.approvedUser+"'>By : "+opt.approvedUser+"</span>";
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
    $("#newRequestAssociates").wrap("<div class='clearfix require_wrap_table'></div>"); 
    $("input[type=search]").hide();
    $("#newRequestAssociatesTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   newRequestAssociatesDataTableList(requestType);
    	   }
    	});
}

</script>


<script>
function acceptNewAssociate(requestUUID,requestType){
	 var userId = '${SourceLeadWeb_userId}';
     var orglocationId = '${SourceLeadWeb_orgLocationId}';
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#requestedNewAssociatesDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 $.ajax({
		    type : "POST",
	    	url: sourceleadUrl+"/acceptNewRequestedResource?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId, 
	        success: function(result) {
	        	$("#newResourceAction"+requestUUID).html("<span>Accepted</span>");
	        	getNewProjectResourceReqestsListCount();
	        	newResourceRequestList(requestType);
	        	$("#requestedNewAssociatesDivLoading").removeAttr("style"); 
	        },
	    });
}
</script>


<script>
function rejectNewAssociate(requestUUID,roleName){
	
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="newResourceRejectedReason_form" name="newResourceRejectedReason_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>New Resource Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Role Name">Role :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+roleName+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_rejectedReason"  onclick="saveRejectNewAssociate(\''+requestUUID+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#resourceRejectedReasonDiv").empty();
    $("#resourceRejectedReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
</script>

<script>
function cancelRejectedReason(){
	$("#resourceRejectedReasonDiv").empty();
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
function saveRejectNewAssociate(requestUUID){
	
	if ($('#newResourceRejectedReason_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#newResourceRejectedReason_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		
	    var form = $('#newResourceRejectedReason_form')[0];
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
		    	url: sourceleadUrl+"/rejectNewRequestedResource?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orglocationId="+orglocationId, 
		        success: function(result) {
		        	cancelRejectedReason();
		        	$("#newResourceAction"+requestUUID).html("<span>Rejected</span>")
		        	getNewProjectResourceReqestsListCount();
		        	newResourceRequestList("Waiting For Approval");
		        	$("#loading").removeAttr("style"); 
		        },
		    }); 
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#newResourceRejectedReason_form', function() {
	   		$("#newResourceRejectedReason_form").showHelpOnFocus();
	   		$("#newResourceRejectedReason_form").validateOnBlur(false, validationSettings);
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
function onholdNewAssociateRequest(requestUUID,Initiator,requestType){
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="newRequestOnHold_form" name="newRequestOnHold_form" method="post">';
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
	result = result + '			<textarea class="form-control" name="onholdReason" id="onholdReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_onHold"  onclick="saveOnHoldNewRequest(\''+requestUUID+'\',\''+requestType+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRequestOnholdReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#newRequestOnHoldReasonDiv").empty();
    $("#newRequestOnHoldReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
	 
}
</script>

<script>
function saveOnHoldNewRequest(requestUUID,requestType){
	
if ($('#newRequestOnHold_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#newRequestOnHold_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
	    var form = $('#newRequestOnHold_form')[0];
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
		    	url: sourceleadUrl+"/onholdRequestedNewAssociate?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId,
		        success: function(result) {
		        	
		        	cancelRequestOnholdReason();
		        	$("#newResourceAction"+requestUUID).html("<span>Onhold</span>")
		        	getNewProjectResourceReqestsListCount();
		        	newResourceRequestList(requestType);
		        	$("#loading").removeAttr("style"); 
		        },
		    });  
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#newRequestOnHold_form', function() {
	   		$("#newRequestOnHold_form").showHelpOnFocus();
	   		$("#newRequestOnHold_form").validateOnBlur(false, validationSettings);
	   	});
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
	
	if ($('#newResourceRejectedReason_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#newResourceRejectedReason_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		
	    var form = $('#newResourceRejectedReason_form')[0];
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
	
	  $('body').on('blur', '#newResourceRejectedReason_form', function() {
	   		$("#newResourceRejectedReason_form").showHelpOnFocus();
	   		$("#newResourceRejectedReason_form").validateOnBlur(false, validationSettings);
	   	});
	
}
</script>
 
 
 