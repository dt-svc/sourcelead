<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="/resources/js/ticketManagement/ticket_configuration.js"></script>



<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Ticket Category</h2>
      </div>
            <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a> &raquo; Ticket Category List</div>
    </section>
    <div class="content-area content-new clearfix timesheet_out">
      <div class="clearfix mt mb">
        <div class="col-md-2 padding_right">
          <ul class="list-unstyled actTicketUL">
            <li><a href="/app/ticketManagementTypeList"><i class="fa fa-ticket mr10"></i>Type</a></li>
            <li class="actTicketList"><a href="/app/ticketManagementCategoryList"><i class="fa fa-navicon mr10"></i>Category</a></li>
            <li><a href="/app/ticketManagementPriorityList"><i class="fa fa-asterisk mr10"></i>Priority</a></li>
            <li><a href="/app/ticketManagementStatusList"><i class="fa fa-check-circle mr10"></i> Status</a></li>
          </ul>
        </div>
        <div class="col-md-10 padding_left">
			<div class="project_tabs projectTabsborder">
				<div id="tab-1">
          <div class="positionRelative timesheet_head clearfix">
            <ul class="pull-right list-unstyled list-inline">
              <li><a class="newAdBtn mr5" title="Add" id="addPop">+ Add</a></li>
              <li class="hidden">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getTicketCategoryList();"></span></li>
            </ul>
          </div>
          <div class="candidate_wrap_table datatable_filter">
            <table style="width: 100%;" id="ticketManagementCategoryTable">
              <thead>
                <tr>
                  <th>Category</th>
                  <th>Description</th>
                  <th>Assign To</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody id="ticketCategoryList">
               
				  
              </tbody>
            </table>
          </div>
			</div>
				
		  </div>
		  
        </div>
      </div>
    </div>
  </section>
  <!-- Footer Area --> 
<div class="mask"></div>
<div class="popupWrap" id="modalTasktype">
      <div class="popupHead"><span id="ticketCategoryFormHead" >Add Category</span><i class="closePop pull-right popClose"></i></div>
        <form id="addTicketCategoryForm" name="" action="" method="post">
        <div class="popupContent">
        <input type="hidden" id="ticketCategoryId" name="id" value="0"/>
            <div class="clearfix mb">
                <label class="noPad">Name <b class="valStar">*</b></label>
                <div class="clearfix">
                    <input class="form-control" placeholder="Name" name="name" type="text" id="ticketCategoryName" field-name="Name" data-validation="required validate_length length1-100">
                </div>
            </div>
            <div class="clearfix mb">
              <label class="noPad">Assign to</label>
              <div class="clearfix">
					<select multiple class="chosen-select form-control" name="assignedTo" field-name="Assign To" id="assignedTo">
						<c:forEach items="${userList}" var="user">
							<option value="${user.id}">${user.firstName}&nbsp;${user.lastName}</option>
						</c:forEach>
					</select>
              </div>
          </div>
          <div class="clearfix mb">
            <label class="noPad">Notify to</label>
            <div class="clearfix">
                <textarea class="form-control" rows="4" id="notifyTo" name="notifyTo" placeholder="Enter multiple Email Id's separated by commas(,)" data-validation="validate_length length0-500"></textarea>
            </div>
        </div>
            <div class="clearfix mb">
                <label class="noPad">Description</label>
                <div class="clearfix">
					<textarea placeholder="Description" name="description" id="ticketCategoryDesc" class="form-control" rows="2"></textarea>
                </div>
            </div>

        </div>
        
        <div class="popupFoot text-right">
          <button type="button" class="slBtn md colorBtn" onclick="saveTicketCategory();">Save</button>
          <button type="button" class="slBtn md popClose" onclick="cancelTicketCategory();"> Cancel</button>
        </div>
        
        <div>
</div></form>
  </div>
 <div id='changeStatusPopup' class='remove_user_pop remove_user_pop_a'>
	<p id='changeStatusHeading' data-name="Ticket Category"></p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmId' type='button' value='Ok' onclick='changeTicketCategoryStatus()' class="pull-left">
		<input id="cancelStatusPopup" class='u_can_color' type='button' value='Cancel' onClick='closeDeletePopup()'>
	</b>
</div>
  <script type="text/javascript">
	$(document).ready(function() {
		getTicketCategoryList();
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

 