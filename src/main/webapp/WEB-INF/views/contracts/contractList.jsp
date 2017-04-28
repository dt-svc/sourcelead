<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
<!-- Content Area -->

<script type="text/javascript">
$(document).ready(function(){
   getTemplateList('draft');
});
</script>.

<style>
.CurDiv { position: static !important; float: left;}

</style>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>List by status</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; List by status</div>
</section>

<section class="contrHead"> 
<ul> 
<li title="Draft"><a href="#" id="draft" class="draft" onClick="mainTabEnable(this);getTemplateList('draft');">Draft</a></li>
<li title="Waiting for Approval"><a href="#" id="approve"  onClick="mainTabEnable(this);getTemplateList('WAITING_FOR_APPROVER');"><i class="fa fa-clock-o fa-2"></i>Approve</a></li> 
<li title="Waiting to be sign"><a href="#" id="sign" onClick="mainTabEnable(this);getTemplateList('Waiting to be signed');"><i class="fa fa-clock-o fa-2"></i>Sign</a></li>
<li title="Waiting for Verify"><a href="#" id="verify" onClick="mainTabEnable(this);getTemplateList('Waiting To Be Verify');"><i class="fa fa-clock-o fa-2"></i>Verify</a></li>
<li title="Finalised"><a href="#" id="finalised"  onClick="mainTabEnable(this); getTemplateList('Finalized');">Finalized</a></li>
<li title="Expired"><a href="#" id="expired" onClick="mainTabEnable(this);">Expired</a></li>
<li title="Rejected"><a href="#" id="rejected"  onClick="mainTabEnable(this);getTemplateList('REJECTED');">Rejected</a></li>

<div class="conBtn">
  <span>Add<i class="fa fa-caret-down"></i></span>
  <ul>  
	  <sec:authorize access="hasAnyRole('ROLE_ADD_NEW_CONTRACT')">
	  <li><a href="<c:url value="/addNewContract" />"><i class="fa fa-caret-right"></i>New Contract</a></li>
	  </sec:authorize>
	  <sec:authorize access="hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')">
	  <li><a href="<c:url value="/addExistingContract" />"><i class="fa fa-caret-right"></i>Existing Contract</a></li>
  	  </sec:authorize>
  </ul>
</div>
<div class="clearfix"></div>
</ul>
</section>
	
<div class="content-area content-new clearfix">
 <div class="project_wrap">
	<div class="timesheet_head">
		<div class="ContrBut">
		    <input type="hidden" name="tabType" id="tabType" value='Vendor'>
			<!-- <a href="#" id="Vendor" class="active tabActivation" onclick="tabEnable(this);getTemplateList('pending');">Vendors</a>
			<a href="#" id="Client" class="tabActivation" onclick="tabEnable(this);getTemplateList('pending');">Clients</a> -->
			<a href="#" id="Vendor1" class="active tabActivation" onclick="tabEnable(this);Check('Vendors');">Vendors</a>
			<a href="#" id="Client1" class="tabActivation" onclick="tabEnable(this);Check('Clients');">Clients</a>
		</div>
		<ul>
			<li id="export-pop"><i class="glyphicon glyphicon-export"></i>
				<div class="export_pop">
					<ul>
						<li>PDF<i class="fa fa-file-pdf-o"></i></li>
						<li>Excel<i class="fa fa-file-excel-o"></i></li>
						<li>Word<i class="fa fa-file-word-o"></i></li>
					</ul>
				</div>
			</li>	
			<div class="clearfix"></div>
		</ul>
		<div class="clearfix"></div>
            
	</div>
	<div id="loading"></div>
    <div class="project_wrap_table">
    <div id="vendorContractsList" ></div>
     <div id="clientContractsList" style="display: none"></div>
	</div>
	
</div>
  
</div>
</section>
<script type="text/javascript">
            $(function () {
                $('#datetimepicker3').datetimepicker({
                    format: 'LT'
                });
            });
        </script> 
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script> 
<script>
    $("#uasrch").selectpicker('refresh');
	
// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").show();
	});
    $("html").click(function() {
        $(".export_pop").hide();
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});		
</script>

<script type="text/javascript">
function getTemplateList(contractSubmissionType){
	var tabType = $("#tabType").val();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
      type : "GET",
       url: sourceleadUrl+"/getAllContractsByOrgLocationIdandStatus?${_csrf.parameterName}=${_csrf.token}&contractSubmissionType="+contractSubmissionType+"&tabType="+tabType,       
       success :function(data) {
    	   preparedContractList(data);
      	
      },
  }); 
}
</script>

<script>
function preparedContractList(data){
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTable1" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Contract Title</th>';
		result = result + '    <th>Owner</th>';
		result = result + '    <th>Start date</th>';
		result = result + '    <th>End date</th>'; 
		result = result + '    <th>Type Of the Contract</th>';
		result = result + '    <th>Vendor Contact</th>';
		result = result + '    <th>Status</th>';
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		           	result = result + '<div><span>'+report.contractTitle+'</span></div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span>'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         result = result + '<td>';
		         result = result + '<div><span>'+report.startDateStr+'</span></div>';
		         result = result + '</td>';
		         result = result + '<td>';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result + '<div><span>'+report.endDateStr+'</span></div>';
		         }else{
		        	 result = result + '<div><span>--</span></div>';
		         }
		         result = result + '<td>';
		         result = result + '<div><span>'+report.contractType+'</span></div>';
		         result = result + '</td>';
		         result = result + '<td>';
		         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.templateSubmissionStatus+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';//Waiting to be signed
		         result = result + '<div class="tbldata-dotdot-sm">';
		         if(report.parentId != null && report.parentId != '' && report.parentId != 'undefined'){
		     		 if(report.templateSubmissionStatus == "Finalized"){
		     			result = result + '<div id="removeStatus'+report.id+'">--</a></div>';
		            	 }
		     		 else{
			         	 	opt = opt + '<div><span>--</span></div>';
			        	  }
		     	}else{
		     		if(report.contractStatus == "completed" && report.templateSubmissionStatus != "Finalized"){
		     			result = result + '<div id="removeStatus'+report.id+'"><a class="btn btn-sm btn-success" title="finalize" onclick="finalizeContract('+report.id+',\'Waiting To Be Verify\');">finalize</a></div>';
		            	 }
		        	  if(report.templateSubmissionStatus == "Finalized"){
		        		  result = result + '<div id="removeStatus'+report.id+'">--</a></div>';
		            	 }
		        	  
		        	  if(report.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
		        		 '<sec:authorize access="hasAnyRole(\'ROLE_APPROVE_CONTRACT\')">';
		            		result = result + '<div class="CurDiv" id="removeStatus'+report.id+'"><a class="btn btn-sm btn-success pull-left" title="approve" onclick="approveContract('+report.id+',\'WAITING_FOR_APPROVER\');">Approve</a></div><div class="CurDiv" id="removeReject'+report.id+'"><a class="btn btn-sm btn-danger ml" title="REJECTED" onclick="approveContract('+report.id+',\'REJECTED\');">Reject</a></div>'; 
		            	 '</sec:authorize>';
		        	  }
		        	  else{
		         	 	result = result + '<div><span>--</span></div>';
		        	  }
		          }
		         result = result + '</div>';
		     	 result = result + '</td>';
		        result = result + '</tr>';
		    }
			
	}
	result = result + '	</tbody>';
	result = result + '</table>';
	var opt="";
	opt = opt +  '<table id="contractListTable" class="display" cellspacing="0" width="100%" >';
	opt = opt + '	<thead>';
	opt = opt + '   <tr>';
	opt = opt + '    <th>Contract Title</th>';
	opt = opt + '    <th>Owner</th>';
	opt = opt + '    <th>Start date</th>';
	opt = opt + '    <th>End date</th>'; 
	opt = opt + '    <th>Type Of the Contract</th>';
	opt = opt + '    <th>Client Contact</th>';
	opt = opt + '    <th>Status</th>';
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	       		 opt = opt + '<div><span>'+output.contractTitle+'</span></div>';
	         opt = opt + '</td>';
	    	 opt = opt + '<td>';
	       	 opt = opt + '<div><span>'+output.ownerName+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.startDateStr+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt + '<div><span>'+output.endDateStr+'</span></div>';
	         }else{
	        	 opt = opt + '<div><span>--</span></div>';
	         }
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.contractType+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.vendorContactName+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.templateSubmissionStatus+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';//Waiting to be signed
	         opt = opt + '<div class="tbldata-dotdot-sm">';
	         if(output.parentId != null && output.parentId != '' && output.parentId != 'undefined'){
	     		 if(output.templateSubmissionStatus == "Finalized"){
	     			opt = opt + '<div id="removeStatus'+output.id+'">--</a></div>';
	            	 }
	     		
	     		 else{
		         	 	opt = opt + '<div><span>--</span></div>';
		        	  }
	     	}else{
	        	  if(output.templateSubmissionStatus == "Finalized"){
	        		  opt = opt + '<div id="removeStatus'+output.id+'">--</a></div>';
	            	 }
	        	  else if(output.contractStatus == "completed" && output.templateSubmissionStatus != "Finalized"){
		     			opt = opt +  '<div id="removeStatus'+output.id+'"><a class="btn btn-sm btn-success" title="finalize" onclick="finalizeContract('+output.id+',\'Waiting To Be Verify\');">finalize</a></div>';
		     		}
	        	  else if(output.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
	        		   '<sec:authorize access="hasAnyRole(\'ROLE_APPROVE_CONTRACT\')">';
	            		opt = opt + '<div><div class="CurDiv" id="removeStatus'+output.id+'"><a class="btn btn-sm btn-success pull-left" title="approve" onclick="approveContract('+output.id+',\'WAITING_FOR_APPROVER\');">Approve</a></div><div class="CurDiv" id="removeReject'+output.id+'"><a class="btn btn-sm btn-danger ml" title="REJECTED" onclick="approveContract('+output.id+',\'REJECTED\');">Reject</a></div></div>'; 
	            		'</sec:authorize>';
	        	  }
	        	  else{
	         	 	opt = opt + '<div><span>--</span></div>';
	        	  }
	          }
	         opt = opt + '</div>';
	     	opt = opt + '</td>';
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    //listContractsPagination();	
   // vendorListContractsPagination();
	
}
</script>

<script>
function removeRecord(recordId,status){
	var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/updateContractStatus?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status,        
        success :function(data) {
        	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#removeStatus"+recordId).html("<span>Send For Verification</span>").addClass('text-primary');
       },
   }); 
}


function finalizeContract(recordId,status){
	 var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/updateContractStatus?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status,       
        success :function(data) {
        	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#removeStatus"+recordId).html("<span>Finalized</span>").addClass('text-success');
       },
   });  
}
</script>


<script>
function approveContract(recordId,status){
	 var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/approveContract?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status,       
        success :function(data) {
        	if(data == 'REJECTED'){
        		$("#row"+recordId).empty();
    	    	$("#row"+recordId).html(rowHtmlContent);
    	    	 $("#removeStatus"+recordId).html("");
    	        $("#removeReject"+recordId).html("<span>REJECTED</span>").addClass('text-success');
        	}else{
        		$("#row"+recordId).empty();
    	    	$("#row"+recordId).html(rowHtmlContent);
    	    	 $("#removeReject"+recordId).html("");
    	        $("#removeStatus"+recordId).html("<span>APPROVED</span>").addClass('text-success');
        	}
        	$("#loading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
       },
   });  
}
</script>

<script>
function listContractsPagination(){
	$('#contractListTable').DataTable({ 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
function vendorListContractsPagination(){ 
    $('#contractListTable1').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>
</script>

<script>
function tabEnable(local){
	$('.tabActivation').each(function() {
		 var localId = this.id;
		 $("#"+localId).removeClass("active");
    });
	var tabId = local.id;
	$("#tabType").val(tabId);
	$("#"+tabId).addClass("active");
}

</script>

<script>
function mainTabEnable(local){
   
	var tabId = local.id;
    if(tabId == 'draft'){
	$("#draft").addClass("draft"); 
    $("#approve").removeClass("wApp");
    $("#sign").removeClass("wSig");
    $("#verify").removeClass("wVer");
    $("#finalised").removeClass("fin");
    $("#expired").removeClass("exp");
    $("#rejected").removeClass("rej");
    }
    if(tabId == 'approve'){
    	$("#draft").removeClass("draft"); 
        $("#approve").addClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        }
    if(tabId == 'sign'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").addClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("expi");
        $("#rejected").removeClass("rej");
        }
    if(tabId == 'verify'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").addClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        }
    if(tabId == 'finalised'){  
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").addClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        }
    if(tabId == 'expired'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").addClass("exp");
        $("#rejected").removeClass("rej");
        }
    if(tabId == 'rejected'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").addClass("rej");
        }
	
}

</script>


<script>
function Check(val){
			 if(val=="Vendors"){
				 document.getElementById("vendorContractsList").style.display="block";
				 document.getElementById("clientContractsList").style.display="none";
			 }
			 if(val=="Clients"){
				 document.getElementById("clientContractsList").style.display="block";
				 document.getElementById("vendorContractsList").style.display="none";
			 }
}

</script>
<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->




