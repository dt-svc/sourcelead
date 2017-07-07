<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.table-top{
    position: relative;
    top: 48px;
    z-index: 999;
}
.delete-icn {
position: relative !important;
top:2px !important;
color:#fff;
}
.notification-table table thead{
background:#375d81;
color:#fff;
}
.icAcc {
font-size: 18px; color: #2ca12c;
}
.icRej {
font-size: 18px; color: #ff3e3e;
}
</style>

<script type="text/javascript">
window.onload = function () {
	getNotificationtLists();
 };
</script>
<div id="loadAcceptDiv"></div>
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Notifications</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; Notifications</div>
    </section>
    <div class="content-dashboard clearfix">
      <section class="action-btns clearfix hidden">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal">
        <div class="row">
		<!-- Notifications -->
			<div class="col-md-3 hidden">
          <div class="mail-box">
            <div class="mail-box-header">
              <h3>Folders</h3>
            </div>
            <div>
              <ul class="nav nav-pills nav-stacked">
                <li class="active"><a href="mailbox.html"><i class="fa fa-inbox"></i> Inbox
                  <span class="label label-primary pull-right">12</span></a></li>
                <li><a href="#"><i class="fa fa-envelope-o"></i> Sent</a></li>
                <li><a href="#"><i class="fa fa-file-text-o"></i> Drafts</a></li>
                <li><a href="#"><i class="fa fa-filter"></i> Junk <span class="label label-warning pull-right">65</span></a>
                </li>
                <li><a href="#"><i class="fa fa-trash-o"></i> Trash</a></li>
              </ul>
            </div>
          </div>
        </div>
			<!-- mail side-bar ends -->
			<div class="col-md-12 col-lg-12">
			<div class="notificatons">
			<!-- /.box-tools -->
			<div class="mailbox-controls clearfix pull-left table-top">
			<div class="pull-left">
				 <div class="notifica-checkbox noti-checkbox">
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
              <div class="clearfix"></div>
			<div class="notification-table mt" id="notificationListDiv">
                
              </div>
			</div>
			</div>
			</div>
		   </div>
		</div>
  </section>
  <!-- Content Area Ends-->


<script>
function getNotificationtLists(){   
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getNotificationtLists?${_csrf.parameterName}=${_csrf.token}", 
        success: function(data) {
        	var json = JSON.parse(data);
        	var result = "";
        	result = result +'<table id="notificationList" class="display table table-borderd table-hover table-striped"  cellspacing="0" width="100%">';
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
        	result = result +'<tbody>';
        	for (var i=0; i<json.length; i++)
				{
        		var report = json[i];
				    result = result +'	<tr>';
					result = result +'	  <td class="noti-checkbox tblminwidth-50">';
					result = result +'			<label>';
					result = result +'			   <input type="checkbox"  id="selectid" onclick="totalCheck1();" class="checkbox1 left" name="check[]" value="'+report.id+'" />';
					result = result +'			  <span></span>';
					result = result +'			</label>';
					result = result +'	  </td>';
					
					result = result +'		<td><div class="notificationUser clearfix">';
					result = result +'		<div class="notifi-image">';
					result = result +'			<img class="img-responsive img-circle" title="'+report.notificationFirstName+' '+report.notificationLastName+'" alt="${report.notificationFrom.userType}" onerror="predefineProfileImage(this);"  src="'+sourceleadUrl+'/displayUserImage/'+report.notificationFormId+'" ">';
					result = result +'		</div>';					
					result = result +'		<div class="mailbox-name">';
					result = result +'		'+report.notificationFirstName+' '+report.notificationLastName+' 					';
					result = result +'		</div>';					
					result = result +'		</div></td>';
				   result = result +'   <td>';
					result = result +'			<div class="mailbox-subject tbldata-dotdot-big">';

					if(report.actionLink != null && report.actionLink != '' && report.actionLink != 'undefined'){
						result = result +'<span><a href="'+report.actionLink+'" title="'+report.body+'"><b>'+report.body+'</b></a></span>';
					}else{
						result = result +'<span><a href="#" title="'+report.body+'"><b>'+report.body+'</b></a></span>';
					}
					result = result +'			</div>';
				    result = result +'   </td>';
				    
				    result = result +'   <td><div class="mailbox-date tbldata-dotdot-vsm">'+report.calDays+' </div></td>';
				    
				    result = result +'   <td><div class="notiRej">';
				     if(report.userNotificationStatus == "Sent" && report.userNotificationStatus != 'Rejected' && report.actionLink != null && report.actionLink != "" && report.actionLink != "undefined"){
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
				    result = result +' </tr>';
				}
        	result = result +'</tbody>';
        	result = result +'</table>';
        	$("#notificationListDiv").empty();
        	$("#notificationListDiv").append(result);
        	notificationPagination();
        },
    });
}
</script>
<script>
function notificationPagination(){  
    $('#notificationList').DataTable( { 
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
</script>
<!-- Delete Notification start  -->  
<script>
function deleteNotification(notificationId){   
	var sourceleadUrl = '${sourceleadUrl}';
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
	 var sourceleadUrl = '${sourceleadUrl}';
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
	$("#loadAcceptDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
}
</script>

 <!-- Delete Notification end  -->
 
 <jsp:include page="../masterFooter.jsp" />
