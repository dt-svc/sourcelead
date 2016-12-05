<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

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
</style>

<script type="text/javascript">
window.onload = function () {
	getNotificationtLists();
 };
</script>

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
				<a href="#" onclick="deleleteAll();"><i class="fa fa-trash-o delete-icn"></i></a>
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
	        	result = result +'	<th></th>';
	        	result = result +'	<th>Recived From</th>';
	        	result = result +'	<th>Notification</th>';
	        	result = result +'	<th>Recived On</th>';
	        	result = result +'	<th>Action</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
        	result = result +'<tbody>';
        	for (var i=0; i<json.length; i++)
				{
        		var report = json[i];
				    result = result +'	<tr>';
					result = result +'	  <td class="noti-checkbox">';
					result = result +'			<label>';
					result = result +'			   <input type="checkbox" class="checkbox1 left" name="check[]" value="'+report.id+'" />';
					result = result +'			  <span></span>';
					result = result +'			</label>';
					result = result +'	  </td>';
					
					result = result +'		<td>';
					result = result +'		<div class="notifi-image pull-left">';
					result = result +'			<img class="img-responsive img-circle" title="'+report.notificationFirstName+' '+report.notificationLastName+'" alt="${report.notificationFrom.userType}" onerror="predefineProfileImage(this);"  src="'+sourceleadUrl+'/displayUserImage/'+report.notificationFormId+'" ">';
					result = result +'		</div>';
					result = result +'		</td>';
					
		 			 result = result +'	<td>';
					result = result +'		<div class="mailbox-name">';
					result = result +		report.notificationFirstName;
					result = result +'		</div>';
				    result = result +'  </td>';
				    
				   result = result +'   <td>';
					result = result +'			<div class="mailbox-subject">';
					result = result +'				<a href="#"><b>'+report.body+'</b></a>';
					result = result +'			</div>';
				    result = result +'   </td>';
				    
				    result = result +'   <td><div class="mailbox-date">'+report.calDays+' days ago</div></td>';
				    result = result +'   <td>';
				     if(report.userNotificationStatus != "Accepted"){
				    	result = result +'<span class="mailbox-trash"><a href="'+report.actionLink+'" title="Accept"><i class="text-muted fa fa-check-circle"></i></a></span>';
				    }
				  if(report.userNotificationStatus == 'Accepted'){
				    	result = result +'   <span class="mailbox-trash"><font color="green">Accepted</font></span>';
				    }
				    result = result +'<span class="mailbox-trash"><a href="javascript:void(0);"  onclick="deleteNotification('+report.id+');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="text-danger fa fa-trash-o"></i></a></span>';
				    result = result +'   </td>';
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
     		"sSearch": ""
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
    
    //getAll();
});

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


 <!-- Delete Notification end  -->
 
 

  <!-- Footer Area -->
  <footer class="footer-area clearfix">
    <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
    <div class="powered-by"> Powered by: businessintelli.com </div>
  </footer>
  <!-- Footer Area -->
