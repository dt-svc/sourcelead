<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="success_pop" style="display: none;">
	<i class="fa fa-check-circle"></i><span>Dashboard items updated</span>
</div>

<!-- Configure Popup -->
<div class="filter-popup advancedsearch-popup filter-popupMid" id="configureDashboard" style="display: none;">
	<h5 class="headerAdsrch">
		Configure Dashboard Items
		<a href="javascript:void(0)" class="close-div" onClick="closeConfigurePopup();">
			<i class="fa fa-close"></i>
		</a>
	</h5>
	<div class="addInner">
		<div class="clearfix">
			<ul class="list-unstyled moveListAlign clearfix" id="configItemsList">
				<c:choose>
					<c:when test="${dashboardItems != null && dashboardItems.itemsObjectList.size() > 0}">
						<input type="hidden" id="dashboardId" value="${dashboardItems.id}"/>
						<c:forEach items="${dashboardItems.itemsObjectList}" var="itemObj">
							<li class="positionRelative ui-state-default ${itemObj.get('checked') ? '' : 'unCheck'}" style="width: ${itemObj.get('size').split(':')[0] == '4' ? itemObj.get('size').split(':')[0] * 24.5 : itemObj.get('size').split(':')[0] * 24}%; height: ${itemObj.get('size').split(':')[1] * 35}px;"><input class="checkbox-custom" id="cb${itemObj.get('order')}" type="checkbox" ${itemObj.get('checked') ? "checked":""} data-order="${itemObj.get('order')}" data-size="${itemObj.get('size')}"><label for="cb${itemObj.get('order')}">${itemObj.get('name')}</label><span class="movIcon"></span></li>											
						</c:forEach>
					</c:when>
					<c:otherwise>
						<input type="hidden" id="dashboardId" value="0"/>
						<c:set var="index" value="0"></c:set>
						<!-- Timesheets -->
						<sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')"> 
							<c:if test = "${SourceLeadWeb_timesheet == 'Y'}">
								<sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="bothTimesheets"/>
								<sec:authorize access="!hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="onlyTimesheets"/>
								<sec:authorize access="hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS') && !hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS')" var="onlyApprovalTimesheets"/>
								<li class="positionRelative" style="width: 48%; height: 30px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="2:1"><label for="cb${index}">${bothTimesheets ? 'Timesheets' : onlyTimesheets ? 'My Timesheets' : onlyApprovalTimesheets ? 'Approval Timesheets' : ''}</label><span class="movIcon"></span></li>  
								<c:set var="index" value="${index +1}"></c:set>
							</c:if> 
				      	</sec:authorize>
				      
				        <!-- Jobs -->
				        <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
				      		<c:if test="${userTabs.tabName == 'Jobs'}">
								<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
									<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
										<li class="positionRelative" style="width: 48%; height: 30px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="2:1"><label for="cb${index}">Jobs</label><span class="movIcon"></span></li>  
										<c:set var="index" value="${index +1}"></c:set>
									</c:if>
						     	</sec:authorize>
					    	</c:if>
					    </c:forEach>
				      	
				      	<!-- Expenses -->
				      	<sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES','ROLE_VIEW_EDIT_MY_EXPENSES','ROLE_EXPORT_MY_EXPENSES','ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">   
							<li class="positionRelative" style="width: 48%; height: 30px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="2:1"><label for="cb${index}">Expenses</label><span class="movIcon"></span></li>
					 		<c:set var="index" value="${index +1}"></c:set>
					 	</sec:authorize>
					 	  	
						<!-- Leaves -->
						<sec:authorize access="hasAnyRole('ROLE_APPLY_LEAVE','ROLE_APPLY_BEHALF_LEAVE','ROLE_APPROVE_LEAVE')">
							<li class="positionRelative" style="width: 48%; height: 30px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="2:1"><label for="cb${index}">Leaves</label><span class="movIcon"></span></li>
							<c:set var="index" value="${index +1}"></c:set>
						</sec:authorize>

						<c:if test="${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
							<!-- Assigned Jobs -->
							<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
								<c:if test="${userTabs.tabName == 'Jobs'}">
									<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
										<li class="positionRelative" style="width: 98%; height: 30px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="4:1"> <label for="cb${index}">Assigned Jobs</label><span class="movIcon"></span></li>
										<c:set var="index" value="${index +1}"></c:set>
									</sec:authorize>
								</c:if>
							</c:forEach>
							
							<!-- Candidates for Onboarding -->
							<sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_USER','ROLE_VIEW_EDIT_ON_BOARD_USER')">
								<li class="positionRelative" style="width: 98%; height: 30px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="4:1"><label for="cb${index}">Candidates for Onboarding</label><span class="movIcon"></span></li>
								<c:set var="index" value="${index +1}"></c:set>
							</sec:authorize>
							
							<!-- Candidates for Invoice -->
							<sec:authorize access="hasAnyRole('ROLE_ADD_INVOICE','ROLE_VIEW_REQUIREMENT')">
								<li class="positionRelative" style="width: 98%; height: 30px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="4:1"><label for="cb${index}">Candidates for Invoice</label><span class="movIcon"></span></li>
								<c:set var="index" value="${index +1}"></c:set>
							</sec:authorize>

							<!-- Associate Requests, New Resource Requests -->
							<sec:authorize
								access="hasAnyRole('ROLE_RESOURCE_REQUEST_APPROVER','ROLE_BUDGET_APPROVER')">
								<li class="positionRelative" style="width: 98%; height: 30px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="4:1"><label for="cb${index}">Project Requests</label><span class="movIcon"></span></li>
							</sec:authorize>
								
							 

							<!-- Job Approvals -->
							<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
								<c:if test="${userTabs.tabName == 'Jobs'}">
									<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
										<li class="positionRelative" style="width: 48%; height: 60px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="2:2"><label for="cb${index}">Job Approvals</label><span class="movIcon"></span></li>
										<c:set var="index" value="${index +1}"></c:set>
									</sec:authorize>
								</c:if>
							</c:forEach>
						</c:if>
						
						<!-- Applicant Permission -->
						<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
							<c:if test="${userTabs.tabName == 'Jobs'}">
								<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
									<li class="positionRelative" style="width: 48%; height: 60px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="2:2"><label for="cb${index}">Applicant Permission</label><span class="movIcon"></span></li>
									<c:set var="index" value="${index +1}"></c:set>
								</sec:authorize>
							</c:if>
						</c:forEach>

						<!-- Events -->
						<li class="positionRelative" style="width: 98%; height: 30px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="4:1"><label for="cb${index}">Events</label><span class="movIcon"></span></li>
						<c:set var="index" value="${index +1}"></c:set>
						
						<!-- Contracts -->
						<sec:authorize access="hasAnyRole('ROLE_VIEW_CONTRACT')">
							<li class="positionRelative" style="width: 98%; height: 30px;"><input class="checkbox-custom" id="cb${index}" type="checkbox" checked="checked" data-order="${index + 1}" data-size="4:1"><label for="cb${index}">Contracts</label><span class="movIcon"></span></li>
							<c:set var="index" value="${index +1}"></c:set>
						</sec:authorize>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
	<div class="clearfix advancedsearch-footer">
		<div class="pull-right mr">
			<input type="button" value="Apply" id="saveConfig" class="btn btn-sm btn-success"/>
			<input type="button" value="Cancel" onclick="closeConfigurePopup()" class="btn btn-sm btn-primary"/>
		</div>
	</div>
</div>
<div class="advancedsearch-mask"></div>

<script type="text/javascript">
	function closeConfigurePopup() {
		$('#configureDashboard').hide();$('.advancedsearch-mask').fadeOut();
	}
	$("#configItemsList li").on("change", function() {
		var checked = $(this).find("input").is(":checked");
		if(!checked) {
			$(this).addClass("unCheck");
		} else {
			$(this).removeClass("unCheck");
		}
	});
	$("#saveConfig").click(function() {
		var items = [];
		$("#configItemsList li").each(function() {
			var name = $(this).find("label").html();
			items.push({
				name : name,
				checked : $(this).find("input").is(":checked"),
				order : $(this).find("input").data("order"),
				size : $(this).find("input").data("size")
			});
			
		});
		
		/* var con1 = JSON.stringify(items);
	    con1= con1.replace(/[{}]/g, "");
	    var jsonData = encodeURIComponent(con1); */
		
		var id = $("#dashboardId").val();
		var data = {"id":id, "jsonData":JSON.stringify(items)};
		$.ajax({
	    	type: "POST",
	    	data: data,
	        url: sourceleadUrl+"/saveDashboardItems",
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "INVALID_DATA") {
					showError(response.responseMessage);
	        		closeConfigurePopup();
	        	} else if (response.response === "SUCCESS") {
	        		/* showError("Dashboard items saved"); */
	        		$(".success_pop").show();
	        		closeConfigurePopup();
	        		location.href = sourceleadUrl;
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        }
		});
		
	});
	
	$(function() {
		$("#configItemsList").sortable();
		$("#configItemsList").on("sortdeactivate", function( event, ui ) {
			/* alert("sortdeactivate"); */
			var index = 1;
			$("#configItemsList li").each(function() {
				$(this).find("input").data("order",index++);
			});
		} );
		$("#configItemsList").disableSelection();
	});
</script>