<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<jsp:include page="../ticketManagement/ticketPopups.jsp" flush="true"></jsp:include>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="/resources/js/ticketManagement/ticket.js"></script>

 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Ticket Management</h2>
      </div>
	<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Ticket List</div>
    </section>
    <div class="content-area clearfix timesheet_out">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
            <sec:authorize access="hasRole('ROLE_ADD_TICKET')">
              <li><a href="/app/addTicket" class="newAdBtn mr5" title="Add" id="addPop">+ Add</a></li>
            </sec:authorize>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getTicketManagementList();"></span></li>
            </ul>
          </div>
          <div class="candidate_wrap_table">
            <div class="candidate_wrap_table datatable_filter require_wrap_table">            
            <table style="width: 100%;" id="ticketManagementListTable">
              <thead>
                <tr>
                  <th>Ticket Id</th>
                  <th>Ticket Title</th>
                  <!-- <th>Category</th> -->
                  <th>Priority</th>
                  <th>Requested By</th>
                  <th>Assign To</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody id="ticketManagementList">
                
              </tbody>
            </table>
          </div>
    </div>
    </div>
  </section>

  <script type="text/javascript">
	$(document).ready(function() {
		getTicketStatusList();
		getTicketManagementList();
	});
	
	function getTicketManagementList() {
		$("#ticketManagementList").empty();
		$.post(sourceleadUrl + "/getTicketList", {}, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.data.length > 0) {
					for(var e in response.data) {
						var text = "<tr>";
			            text+= "      <td><div class='tblminwidth-150'>" + response.data[e].generatedId + "</div></td>";
			            '<sec:authorize access="hasRole(\'ROLE_VIEW_TICKET\')">';
			            	text+= "      <td><div class='tbldata-dotdot-vsm'><a href='/app/ticketSummary?uuid=" + response.data[e].uuid + "' title='" + response.data[e].title + "'>" + response.data[e].title + "</a></div></td>";
			            '</sec:authorize>';
			            '<sec:authorize access="!hasRole(\'ROLE_VIEW_TICKET\')">';
			            	text+= "      <td><div class='tbldata-dotdot-vsm'>" + response.data[e].title + "</div></td>";
			            '</sec:authorize>';
			            text+= "   	<td><div class='tblminwidth-75' style='color:" + (response.data[e].ticketPriorityTextColor != null && response.data[e].ticketPriorityTextColor !== "" ? response.data[e].ticketPriorityTextColor : "") + "'>" + (response.data[e].ticketPriorityImage !== null && response.data[e].ticketPriorityImage !== "" ? "<div class='priorityImage pull-left mr10'><img src=\"" + response.data[e].ticketPriorityImage + "\" class='mr10'/></div>" : "<div class='blank-div mr10'></div>") + "<div class='pull-left'>" + response.data[e].ticketPriority + "</div></div></td>";
			            text+= "    <td><div class='tbldata-dotdot-vsm'>" + response.data[e].createdBy + "</div></td>";
			            var assignTo = "";
						for(var a in response.data[e].assignedTo) {
							assignTo += (assignTo === "" ? "" : ",") + response.data[e].assignedTo[a];
						}
						text += "    <td><div class='tbldata-dotdot'>" + (assignTo === "" ? "--" : assignTo) + "</div></td>";
						text += "<td><select class='sel' onChange='changeTicketStatus(this, \"" + response.data[e].id + "\", \"" + response.data[e].ticketStatusId + "\")'>";
						for(var s in statusList) {
							text += "<option data-comment=" + statusList[s].commentRequired + " value='" + statusList[s].id + "' " + (statusList[s].name === response.data[e].ticketStatus ? "selected" : "") + ">" + statusList[s].name + "</option>";
						}
						text += "</select></td>";
			            text+= "      <td>";
						text+= "		  <ul class=list-inline list-unstyled'>";
						'<sec:authorize access="hasRole(\'ROLE_ADD_TICKET\')">';
							text+= "			<li><i class='fa fa-pencil cursorPointer' onClick='editTicketManagement(\"" + response.data[e].uuid + "\");'></i></li>";
						'</sec:authorize>';
						'<sec:authorize access="hasRole(\'ROLE_DELETE_TICKET\')">';
							text+= "		  	<li><i class='fa fa-trash cursorPointer' onclick='showDeletePopup(" + response.data[e].id + ", 0);'></i></li>";
						'</sec:authorize>';
						text+= "	  </ul>";
						text+= "		</td>";
			            text+= "   </tr>";
			    		$("#ticketManagementList").append(text);
					}
				}
				ticketManagementListPagination();
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
</script>
<jsp:include page="../masterFooter.jsp" />	 