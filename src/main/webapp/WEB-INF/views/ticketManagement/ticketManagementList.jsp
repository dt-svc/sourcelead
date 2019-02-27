<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="/resources/js/ticketManagement/ticket_configuration.js"></script>

 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Ticket Management</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a> &raquo; 
						<a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; 
						<a href="<c:url value='${appPath}/ticketManagementList'/>">TicketManagement</a>&raquo; Ticket Management</div>
    </section>
    <div class="content-area clearfix timesheet_out">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
              <li><a href="/app/addTicketManagement" class="newAdBtn mr5" title="Add" id="addPop">+ Add</a></li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div class="candidate_wrap_table">
            <div class="candidate_wrap_table datatable_filter">            
            <table style="width: 100%;" id="ticketManagementListTable">
              <thead>
                <tr>
                  <th>Ticket Id</th>
                  <th>Ticket Title</th>
                  <th>Category</th>
                  <th>Status</th>
                  <th>Priority</th>
                  <th>Requested By</th>
                  <th>Assign To</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody id="ticketManagementList">
                
              </tbody>
            </table>
          </div>
    </div>
  </section>
  <script type="text/javascript">
	$(document).ready(function() {
		getTicketManagementList();
	});
	$(function() {
		/* $("#addPop").click(function() {
			$(".mask, #modalTasktype").fadeIn();
		});
		$(".popClose").click(function() {
			$(".mask, #modalTasktype").fadeOut();
		}); */
	});
</script>
  
 <jsp:include page="../masterFooter.jsp" />	

 