<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
window.onload = function () {
// 	getNotificationtLists();
	dynamicNotificationtLists();
 };
</script>
<div id="loadAcceptDiv"></div>
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Notifications</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; Notifications</div>
    </section>
    <div class="content-dashboard clearfix">
      <section class="action-btns clearfix hidden">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal">
		<!-- Notifications -->

			<!-- mail side-bar ends -->
			<div class="notificatons">
			<div class="mailbox-controls clearfix pull-left table-top">
			<div class="pull-left">
				 <div class="notifica-checkbox noti-checkbox" id="notificationListDivNonEmpty">
					<a href="#">
					<label>
						  <input type="checkbox" name="list" id = "selectall"/>
						  <span></span>
						</label>
					</a>
				<a href="#" onclick="deleleteAll();"><i id="navbar1" class="fa"></i></a>
				</div>
                </div>
              </div>
			<div class="notification-table datatable_filter notification-table"  id="notificationListDiv">
              </div>
			</div>
		   </div>
		</div>
  </section>
  <!-- Content Area Ends-->

<script>
function dynamicNotificationtLists(){
	var sourceleadUrl ='${sourceleadUrl}';
	var searchValue = $("#notificationDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl	+"/getDynamicNotificationtLists?searchValue="+searchValue;
	prepareDynamicNotificationtLists(url);
}
function prepareDynamicNotificationtLists(url){
	var result = "";
	result = result	+ '<div class="positionRelative reportHead clearfix">';
	result = result	+ '<ul class="pull-right list-unstyled list-inline">';
	result = result	+ '<li class=""><input type="text" class="repSearchBar" placeholder="Search" id="notificationDataTableSearch"/><span class="repSearchIcon" onClick="projectTaskList();"></span></li>';
	result = result	+ '</ul>';
	result = result	+ '</div>';
	
/*     result = result	+ '<div class="dynamic_search dynamic_search-new">';
    result = result +" <input type='text' placeholder='Search' id='notificationDataTableSearch'/>";
 	result = result +"  <span class='cursorPointer' title='Search'><i class='fa fa-search' onclick='projectTaskList();'></i></span></div>";    */
 	
 	
	result = result	+ '<div class="clearfix">';
	result = result + '<table id="notificationTaskPagination" class="display table table-borderd table-hover table-striped" cellspacing="0" width="100%">';
	result = result +'<thead>';
	result = result +'<tr>';
	result = result +'	<th>';
	result = result +'</th>';
	result = result +'	<th>Received From</th>';
	result = result +'	<th>Notification</th>';
	result = result +'	<th>Received On</th>';
	result = result +'	<th>Action</th>';
	result = result +'</tr>';
	result = result +'</thead>';
	result = result + '</table>';
	result = result	+ '</div>';
	$("#notificationListDiv").empty();  
	$("#notificationListDiv").append(result);  
	dynamicNotificationtPaginations(url);
}
function dynamicNotificationtPaginations(url){
	var colIndex = 0;
	$('#notificationTaskPagination').DataTable( {
		  "processing": true,
	      "serverSide": true,
	      "searching": false,
	      "ajax": $.fn.dataTable.pipeline( {
	    	  url: url,
	          method: 'POST',
	          pages: 10
	        }),
	        "columnDefs": [
	        	{
		    	  	"targets": colIndex++,
		    	  	"data": "",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		result = result +'	  <td>';
		    	  		result = result +'			<div class="noti-checkbox tblminwidth-50">';
		    	  		result = result +'			<label>';
						result = result +'			   <input type="checkbox"  id="selectid" onclick="totalCheck1();" class="checkbox1 left" name="check[]" value="'+report.id+'" />';
						result = result +'			  <span></span>';
						result = result +'			</label>';
						result = result +'			</div>';
						result = result +'	  </td>';
			            return result;
		    	  	}
		      },{
		    	  	"targets": colIndex++,
		    	  	"data": "",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		result = result +'		<td>';
		    	  		result = result +'		<div class="tblminwidth-175 clearfix">';
		    	  		result = result +'		<div class="notifi-image mr">';
						
						if(report.notificationImage != null && report.notificationImage !='' && report.notificationImage !="undefined"){
							result = result +'			<img data-id= "userProfileImage" class="img-responsive img-circle" title="'+report.notificationFirstName+' '+report.notificationLastName+'" alt="${report.notificationFrom.userType}" onerror="predefineProfileImage(this);"  src="'+report.notificationImage+'">';
						}else{
						result = result +'			<img data-id= "userProfileImage" class="img-responsive img-circle" title="'+report.notificationFirstName+' '+report.notificationLastName+'" alt="${report.notificationFrom.userType}" onerror="predefineProfileImage(this);"  src="/resources/images/userprofileImg.jpg">';
		
						}
						result = result +'		</div>';					
						result = result +'		<div class="tbldata-dotdot inlineBlock pull-left text-left mt5">';
						if(report.notificationLastName != null && report.notificationLastName != '' && report.notificationLastName != 'undefined'){
						result = result +'		'+report.notificationFirstName+' '+report.notificationLastName+' 					';
						}
						else{
							result = result +'		'+report.notificationFirstName+' 					';
						}					
						result = result +'		</div>';
						result = result +'		</div>';
						result = result +'	     </td>';
			            return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
	              "data": "",
	              "render": function ( data, type, report, meta ) {
	            	  var result = "";
	            	  result = result +'   <td>';
						result = result +'			<div class="mailbox-subject tbldata-dotdot-big500 pull-left text-left">';
						if((report.actionLink != null && report.actionLink != '' && report.actionLink != 'undefined') && ( report.spocUUID == null || report.spocUUID == "" || report.spocUUID == "undefined")){
							if(report.mode == "read"){
								result = result +'<span><a class="visitedAnchor" href="'+report.actionLink+'" title="'+report.body+'" onclick="linkReadAndUnread(\''+report.uuId+'\');">'+report.body+'</a></span>';
							}else{
								result = result +'<span><a href="'+report.actionLink+'" title="'+report.body+'" onclick="linkReadAndUnread(\''+report.uuId+'\');">'+report.body+'</a></span>';
							}
						}else if((report.actionLink != null && report.actionLink != '' && report.actionLink != 'undefined') && ( report.spocUUID != null && report.spocUUID != "" && report.spocUUID != "undefined") && (report.spocInvitationStatus == "Accepted" || report.spocInvitationStatus == "Rejected") && (report.orgRelationUUID != null && report.orgRelationUUID != "" && report.orgRelationUUID != "undefined") && report.responderType == "vendor"){
							if(report.mode == "read"){
								result = result +'<span><a  class="visitedAnchor" href="/app/vendorsList?orgRelationUUID='+report.orgRelationUUID+'" onclick="linkReadAndUnread(\''+report.uuId+'\');" title="'+report.body+'">'+report.body+'</a></span>';
							}else{
								result = result +'<span><a href="/app/vendorsList?orgRelationUUID='+report.orgRelationUUID+'" onclick="linkReadAndUnread(\''+report.uuId+'\');" title="'+report.body+'">'+report.body+'</a></span>';
							}
						}else if((report.actionLink != null && report.actionLink != '' && report.actionLink != 'undefined') && ( report.spocUUID != null && report.spocUUID != "" && report.spocUUID != "undefined") && (report.spocInvitationStatus == "Accepted" || report.spocInvitationStatus == "Rejected") && (report.orgRelationUUID != null && report.orgRelationUUID != "" && report.orgRelationUUID != "undefined")&& report.responderType == "client"){
							if(report.mode == "read"){
								  result = result +'<span><a class="visitedAnchor"  href="/app/clientDashboard?orgRelationUUID='+report.orgRelationUUID+'" onclick="linkReadAndUnread(\''+report.uuId+'\');" title="'+report.body+'">'+report.body+'</a></span>';
							}else{
							  result = result +'<span><a href="/app/clientDashboard?orgRelationUUID='+report.orgRelationUUID+'" onclick="linkReadAndUnread(\''+report.uuId+'\');" title="'+report.body+'">'+report.body+'</a></span>';
							}
						}else{
							if(report.mode == "read"){
								result = result +'<span><a class="visitedAnchor" href="#" title="'+report.body+'" onclick="linkReadAndUnread(\''+report.uuId+'\');">'+report.body+'</a></span>';
							}else{
								result = result +'<span><a href="#" title="'+report.body+'" onclick="linkReadAndUnread(\''+report.uuId+'\');">'+report.body+'</a></span>';
							}
						}
						result = result +'			</div>';
					    result = result +'   </td>';
		              return result;
	                }
		          },{
			    	  "targets": colIndex++,
		              "data": "",
		              "sortable": false,
		              "render": function ( data, type, report, meta ) {
		            	  var result = "";
						    result = result +'   <td><div class="mailbox-date tblminwidth-175" title="'+report.calDays+'">'+report.calDays+' </div></td>';
			              return result;
		               }
			       },{
				    	  "targets": colIndex++,
			              "data": "",
			              "sortable": false,
			              "render": function ( data, type, report, meta ) {
			            	  var result = "";
			            	  result = result +'   <td><div class="notiRej">';
							     if(report.userNotificationStatus == "Sent" && report.userNotificationStatus != 'Rejected' && report.actionLink != null && report.actionLink != "" && report.actionLink != "undefined"  && report.spocUUID != null && report.spocUUID != "" && report.spocUUID != "undefined"){
							    	result = result +'<span class="mailbox-trash"><a href="'+report.actionLink+'" title="Accept" onclick="loadingDiv();"><i class="text-muted fa fa-check-circle"></i></a></span>';
							     }
							  
							    if(report.userNotificationStatus == "Sent" && report.userNotificationStatus != 'Accepted' && report.rejectActionLink != null && report.rejectActionLink != "" && report.rejectActionLink != "undefined"){
							    	result = result +'<span class="mailbox-trash"><a href="'+report.rejectActionLink+'" title="Reject" onclick="loadingDiv();"><i class="text-muted fa fa-times"></i></a></span>';
							    }
							  
							   if(report.userNotificationStatus == 'Accepted'){
							    	result = result +'   <span class="mailbox-trash"><i class="fa fa-check icAcc" title="Accepted"></i></span>';
							    }
							  
							   if(report.userNotificationStatus == 'Rejected'){
							    	result = result +'   &nbsp;<span class="mailbox-trash"><i class="fa fa-times icRej" title="Rejected"></i></span>';
							   }
							    result = result +'<span class="mailbox-trash"><a href="javascript:void(0);"  onclick="deleteNotification('+report.id+');"><i class="text-danger fa fa-trash-o"></i></a></span>';
							    result = result +'   </div></td>';
							   return result;
			               }
				       }
	        	],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": false,
		    	"autoWidth": false,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     }
	      
	});
	 $("#notificationTaskPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#notificationDataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   dynamicNotificationtLists();
	    	   }
	    	});
}

</script>
 

<script>
function checkCheckBox(){
	var allCheckBoxCount  = $('input[name="check[]"]').length;
	var checked = $("input[name='check[]']:checked").length;
	if(checked < allCheckBoxCount ){
		$('#selectall').prop('checked', false);
		$("#navbar1").addClass("fa-trash-o delete-icn");
		if(checked == 0){
			$("#navbar1").removeClass("fa-trash-o delete-icn");
		}
	}else if(checked == allCheckBoxCount){
		$('#selectall').prop('checked', true);
		$("#navbar1").addClass("fa-trash-o delete-icn");
		if(checked == 0){
			$("#navbar1").removeClass("fa-trash-o delete-icn");
		}
	}
}
</script>

<!-- Delete Notification start  -->  
<script>
function deleteNotification(notificationId){   
	var  orgCode ='${orgCode}';
	var locationCode = '${locationCode}';
	  $.ajax({
	      type: "POST",
	  	  url: "${sourceleadUrl}"+"/deleteNotification?${_csrf.parameterName}=${_csrf.token}&notificationId="+notificationId+"&orgCode="+orgCode+"&locationCode="+locationCode,
	  			  success :function(data) {
	  				  if(data == 'success'){
		        		location.replace(sourceleadUrl+"/notificationsList");  
		        	} 		
      			 }							     
	});   
}
</script>

<script>
function getAll() {
	var ids = [];
	 $("input[name='check[]']:checked").each( function (i) {
		 ids[i] = $(this).val();
	});
}
</script>


<script>
$("#selectall").change(function () {
	
    $("input:checkbox").prop('checked', $(this).prop("checked"));
    
	if($("#selectall").is(':checked')){
        $("#navbar1").addClass("fa-trash-o delete-icn");
    } 
	else{
			 $("#navbar1").removeClass("fa-trash-o delete-icn");
		
		
	}
    
    //getAll();
});

</script>

<script>
function totalCheck1() {
	if($("input[name='check[]']").is(':checked')){
		
		var checked = $("input[name='check[]']:checked"); 
		var nbChecked = checked.size();
		if($("input[name='check[]']").is(':checked') && nbChecked >= 2){
	         $("#navbar1").addClass("fa-trash-o delete-icn");
	     } else{
		 $("#navbar1").removeClass("fa-trash-o delete-icn");
		
		
	}
}
}
</script>

<script>
function deleleteAll() {
	var notificationIds = [];
	 $("input[name='check[]']:checked").each( function (i) {
		 notificationIds[i] = $(this).val();
	});
	 var  orgCode ='${orgCode}';
	 var locationCode = '${locationCode}';
		 $.ajax({
		      type: "POST",
		  	  url: "${sourceleadUrl}"+"/deleteAllNotification?${_csrf.parameterName}=${_csrf.token}&notificationIds="+notificationIds+"&orgCode="+orgCode+"&locationCode="+locationCode,
		  			  success :function(data) {
		  				 if(data == 'success'){
				        		location.replace(sourceleadUrl+"/notificationsList");  
				        	} 		
	      			 }							     
		});  
	 
	 
}
</script>
<script type="text/javascript">
function loadingDiv(){
	$("#loadAcceptDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
}
</script>
<script>
function linkReadAndUnread(uuId){
	 $.ajax({
	      type: "POST",
	  	  url: sourceleadUrl+"/notificationStatusRead?${_csrf.parameterName}=${_csrf.token}&uuId="+uuId,
	  			  success :function(data) {
     			 }							     
	}); 
}
</script>
 
 <jsp:include page="../masterFooter.jsp" />