<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script type="text/javascript" src="jquery-1.2.6.min.js"></script>
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Notifications</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Notifications</div>
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
			<div class="mailbox-controls clearfix">
			<div class="pull-left">
				<div class="notifica-checkbox noti-checkbox">
				<a href="#">
					<label>
						  <input type="checkbox" name="list" id = "selectall"/>
						  <span></span>
						</label>
				</a>
				<a href="#" onclick="deleleteAll();"><i class="fa fa-trash-o"></i></a>
				</div>
                  <div class="btn-group mr">
                    <button class="btn btn-default btn-sm" type="button"><i class="fa fa-chevron-left"></i></button>
                    <button class="btn btn-default btn-sm" type="button"><i class="fa fa-chevron-right"></i></button>
                  </div>
				  <span class="not-count">1-10/200</span>
                </div>
				<div class="box-tools pull-right">
                <div class="has-feedback">
                  <input type="text" placeholder="Search Mail" class="form-control input-sm">
                  <span class="glyphicon glyphicon-search form-control-feedback"></span>
                </div>
              </div>
              </div>
			<div class="notification-table table-responsive">
                <table class="table table-borderd table-hover table-striped">
                  <tbody>
                  <c:if test="${empty notificationsList}">
                   <tr>
                   <td>
						<div class="mailbox-subject">
						<b>Empty Inbox</b>
						</div>
                    </td>
                   </tr>
                  </c:if>
                  
                <c:if test="${!empty notificationsList}">
                <c:forEach var="report" items="${notificationsList}">
                 <tr>
				  <td class="noti-checkbox">
						<label>
						   <input type="checkbox" class="checkbox1 left" name="check[]" value="${report.id}" />
						  <span></span>
						</label>
					</td>
                    <td>
					<div class="notifi-image pull-left">
						<img class="img-responsive img-circle" title="${report.notificationFrom.firstName} ${report.notificationFrom.lastName}" alt="${report.notificationFrom.userType}" onerror="predefineProfileImage(this);" src="<c:url value='/displayUserImage/${report.notificationFrom.id}'/>">
					</div>
					</td>
					<td>
					<div class="mailbox-name">
						${report.notificationFrom.firstName}
					</div>
					</td>
                    <td>
						<div class="mailbox-subject">
							<a href="#"><b>${report.body}</b></a>
						</div>
                    </td>
                    <td><div class="mailbox-date">${report.calDays} days ago</div></td>
                    <td><div class="mailbox-trash"><a href="${report.actionLink}" title="Accept"><i class="text-muted fa fa-check-circle"></i></a></div></td>
					<td><div class="mailbox-trash"><a href="javascript:void(0);"  onclick="deleteNotification('+${report.id}+');"><i class="text-danger fa fa-trash-o"></i></a></div></td>
                  </tr>
                </c:forEach>
                </c:if>
                  
                 

                  </tbody>
                </table>
              </div>
			</div>
			</div>
			</div>
		   </div>
		</div>
  </section>
  <!-- Content Area Ends-->
  
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
