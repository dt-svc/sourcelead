<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
var atsUrl ='${SourceLeadWeb_atsUrl}';
$(document).ready(function() {
    $("#filterDropDown").click(function(){
		$(".approvedPop").slideToggle(200);
		$(".approvedPop1").slideUp(200);
	});
    
    $("#filterDropDown, .approvedPop, .approvedPop1").click(function(e) {  
        e.stopPropagation();
    });
    
    
    $("#filterDropDown1").click(function(){
		$(".approvedPop1").slideToggle(200);
		$(".approvedPop").slideUp(200);
	});
    $("#filterDropDown1, .approvedPop1, .approvedPop").click(function(e) {  
        e.stopPropagation();
    });
    
    $("html").click(function() {
        $(".approvedPop, .approvedPop1").slideUp(200);
    });
    
}); 
</script>

<script type="text/javascript">
window.onload = function () {
	usersListCheck("${searchValue}","${searchResult}");
	var view = $("#listView").val('GRIDVIEW');
 };
</script>

<script type="text/javascript">
function usersListCheck(searchValue,searchResult){
	if(searchResult == "universalUsersList"){
	$("#dynamicUserDataTableSearch").val(searchValue)
		getDynamciUserList();
	}
	else{
		getDynamciUserList();
	}
}
</script>

<script>
function getUsersSearchResult(searchValue){
	 $.ajax({
        type : "GET",
         url: atsUrl+"/getUniversalSearchUsersList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue,       
         success :function(data) {
        	 prepareUsersList(data);
        },
         
    }); 
}
</script>

<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
</script>
<input type="hidden" id="noUsers" name="noUsers"/>
<div class="success_pop" id="mailMsg" style="display: none;">
	<i class="fa fa-check-circle"></i><span>Invitation sent successfully</span>
</div>

  <!-- Content Area -->
  
  <section class="content-wraper">
  <div class="content-dashboard clearfix">
   <div id="loading"></div>
    <section class="content-header clearfix">
         <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
      <div class="pull-left"><h2>Associates </h2></div>
      
    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; Associates List </div>
    </section>

	<div class="project_wrap">
      <section class="clearfix usersHeader positionRelative  hidden">
      
      <div class="pull-left cand-advsrche onboardBtn">
       <input id="searchElement" type="hidden"></input>
        <input type="hidden" name="listView" id="listView"/>
      </div>
       
       
       <div class="section-action-btns clearfix">
       <div class="positionRelative clearfix">
       <ul class="pull-right list-unstyled list-inline">
		  
		    <li id="searchIdDiv">
			  <a>
			  <i class="fa fa-user"></i>
				<span class="action-search"><input type="text" placeholder="Email/Name" id="searchByName" ></span>
				</a>
		  </li>
		  <li id="dynamicSearch" style="display:none;">
			  <span class="width215px">
			  
			  </span>
		  </li>
		  
		  
        </ul>
      </div>
      </div>
      </section>
	    
				<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
				<h5 class="headerAdsrch">Filters
					<a href="javascript:void(0)" class="close-div" onClick="closePop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
							
						<div class="col-md-2">
						<div class="row">
						<div class="col-md-12 mb">
							<label><strong>Status</strong></label>
							 <div class="clearfix">
								<ul class="list-unstyled" id="statusFilter">
		                    	    <li class="checkBox positionRelative"><input   type="checkbox" name="userState" id="activeStatus" value ="ACTIVE" checked ><label>&nbsp;Active</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userState" id="notloggedInStatus" value ="NOT_LOGGEDIN" checked ><label>&nbsp;Not Logged In</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userState" id="inactiveStatus" value ="INACTIVE" ><label>&nbsp;In Active</label></li>
		               				
		                        </ul>
							 </div>
							 </div> 
							 
							 <div class="col-md-12 mb">
								<label><strong>Employment Type</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="employmentTypeFilter">
			                    	    <li class="checkBox positionRelative"><input   type="checkbox" name="employmentType"  value ="C2C"><label>&nbsp;C2C</label></li>
										<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType"  value ="C2C-1099" ><label>&nbsp;C2C-1099</label></li>
										<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="W-2" ><label>&nbsp;W-2</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="C2H"><label>&nbsp;C2H</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="C2H-1099"><label>&nbsp;C2H-1099</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="Offshore C2C"><label>&nbsp;Offshore C2C</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="Part Time"><label>&nbsp;Part Time</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="Full Time"><label>&nbsp;Full Time</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="Other"><label>&nbsp;Others</label></li>
			                        </ul>
								 </div>
							 </div> 
							 
						  </div>
						</div>
						
						<div class="col-md-10">
						
						<div class="row">
						
						<c:if test="${reportingManagersListSize > 0}">
							 <div class="col-md-4 positionRelative mb">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myManagersInput" onkeyup="myManagersFunction()" placeholder="Managers" title="Type in a managers">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL">
											<c:forEach var="manager" items="${reportingManagersList}">
											<li class="checkBox positionRelative"><input  type="checkbox" name="orgManagers" value ="${manager.userId}" ><label>${manager.fullName}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 </c:if>
						
						<div class="col-md-4 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myRolesInput" onkeyup="myRolesFunction()" placeholder="Roles" title="Type in a roles">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myRolesUL">
									<c:forEach var="roles" items="${rolesList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="userRole" value ="${roles.id}" ><label>${roles.displayName}</label></li>
									</c:forEach>
								</ul>
							 </div> 
						</div>
						
						<c:if test="${clientListSize > 0}">
						<div class="col-md-4 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myClientsInput" onkeyup="myClientsFunction()" placeholder="Vendor" title="Type in a vendor">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myClientsUL">
									<c:forEach var="vendor" items="${orgVendorList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="orgClients" value ="${vendor.orgRelId}" ><label>${vendor.vendorName}</label></li>
									</c:forEach>
								</ul>
							 </div> 
						</div>
						</c:if>
						
						<c:if test="${!empty associateTagsList}">
						<div class="col-md-4 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myClientsInput" onkeyup="myClientsFunction()" placeholder="Tags" title="Type in a tag">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myClientsUL">
									<c:forEach var="tag" items="${associateTagsList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="tags" value ="${tag}" ><label>${tag}</label></li>
									</c:forEach>
								</ul>
							 </div> 
						</div>
						</c:if>
						
						
						<c:if test="${teamsListSize > 0}">
						<div class="col-md-4 mb positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myTeamsInput" onkeyup="myTeamsFunction()" placeholder="Teams" title="Type in a teams">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myTeamsUL">
									<c:forEach var="team" items="${teamsList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="orgTeams" value ="${team.id}" ><label>${team.name}</label></li>
									</c:forEach>
								</ul>
							 </div> 
						</div>
						</c:if>
						
						<c:if test="${departmentListSize > 0}">
						<div class="col-md-4 mb positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myDepartmentInput" onkeyup="myDepartmentFunction()" placeholder="Departments" title="Type in a departments">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myDepartmentUL">
									<c:forEach var="dep" items="${departmentList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="orgDepartments" value ="${dep.id}"><label>${dep.departmentName}</label></li>
									</c:forEach>
								</ul>
							 </div> 
						</div>
						</c:if>
						
						</div>
						
						</div>
						
						
					</div>
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet"
								onclick="closePop();getDynamciUserList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="resetFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>
      
      <div class="project_wrap_table datatable_filter backgroundWhite" id="userListGrid">
      
      <div class="positionRelative reportHead clearfix">
          <span class="pull-left refreIcon">
           <a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a>
          </span>
      <ul class="pull-right list-unstyled list-inline">
        <input type="hidden" name="usersOffset" id="usersOffset" value="0"/>
		   <sec:authorize access="hasAnyRole('ROLE_CREATE_ASSOCIATE')"> 
	                    
                  <%-- <c:if test="${orgLocationsCount > 1}"> --%>
		                    <li>
								<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
								<div class="action-pop">
									<span class="ovrShow"> 
									<a href="${atsUrl}/userCreation">Add Associate</a>
									<a href="${atsUrl}/addAssociateFromLocations">Add From Loc</a>
									<sec:authorize access="hasAnyRole('ROLE_USER_BULK_IMPORTS')"> 
										<%-- <a href="<c:url value='${appPath}/uploadForm'/>">Import</a> --%>
										<div class="immSub">
											<a href="#">Import</a>
										<div class="immSubMenu immSubMenu-custome">

												<a href="${SourceLeadWeb_sourceleadUrl}/uploadForm">Associate Import</a>
								 
												<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
													<a href="${SourceleadWeb_sourceleadUrl}/uploadAssociateBankForm">Associate Bank Details Import</a>
												   <a href="${SourceleadWeb_sourceleadUrl}/uploadAssociateDeductionContributionDetails">Associate Deduction Contribution Import</a>
												</c:if>

												<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
												    <a href="${atsUrl}/uploadAssociateBankingDetailsForm">Associate Bank Detail</a>
												    <a href="${atsUrl}/uploadAssociateInformation">Associate Information</a>
													<a href="${atsUrl}/uploadAssociateSalary">Associate Salary Import</a>
												</c:if>
											</div>
										</div>
										
									</sec:authorize>									
									</span>
								</div>
							</li>
				<%-- </c:if> --%>
				
				<%-- <c:if test="${orgLocationsCount == 1}">
				           <sec:authorize access="hasRole('ROLE_USER_BULK_IMPORTS')"> 
				           <li>
								<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
								<div class="action-pop">
									<span class="ovrShow"> 
									<a href="${SourceLeadWeb_atsUrl}/userCreation">Add Associate</a>
									<sec:authorize access="hasAnyRole('ROLE_USER_BULK_IMPORTS')"> 
									<div class="immSub">
										<a href="${SourceLeadWeb_sourceleadUrl}/uploadForm">Import</a>
											<div class="immSubMenu">
												<a href="${SourceleadWeb_sourceleadUrl}/uploadForm" title="Associate Import">Associate Import</a>
												<a href="${SourceleadWeb_sourceleadUrl}/uploadAssociateBankForm" title="Associate Bank Details Import">Associate Bank Details Import</a>
												<a href="${SourceleadWeb_sourceleadUrl}/uploadAssociateDeductionContributionDetails" title="Associate Deduction Contribution Import">Associate Deduction Contribution Import</a>
												<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
												    <a href="${SourceleadWeb_sourceleadUrl}/uploadAssociateBankingDetailsForm" title="Associate Bank Detail">Associate Bank Detail</a>
												   <a href="${sourceleadUrl}/uploadAssociateInformationForm">Associate Information</a> 
													<a href="${SourceleadWeb_sourceleadUrl}/uploadAssociateSalary" title="Associate Salary Import">Associate Salary Import</a>
													
												</c:if>
											</div>
									</div>
									</sec:authorize>									
									</span>
								</div>
							</li>
							</sec:authorize>
							
				<sec:authorize access="!hasRole('ROLE_USER_BULK_IMPORTS')"> 
						<li><a class="newAdBtn mr5" href="${SourceLeadWeb_atsUrl}/userCreation">+  Associate</a></li>
				</sec:authorize>
							
				</c:if>	 --%>
	           
						
	       </sec:authorize>
			
			<sec:authorize access="hasAnyRole('ROLE_VIEW_ASSOCIATE_ADDITIONAL_DETAILS')"> 
				<li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
			</sec:authorize>
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
	
						<sec:authorize access="hasAnyRole('ROLE_EXPORT_ASSOCIATE')"> 
							<a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a>
						</sec:authorize>
						</span>
					</div>
				</li>
		    
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='dynamicUserDataTableSearch'/><span class="repSearchIcon" onClick="beforeSearch();"></span></li>
        </ul>
      </div>
      
              <div id="dynamicUserListViewDiv" class="filter_position filter_position_top60 positionRelative"></div> 
          </div>  
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
      <div class="row">
          <div id="usersListBody"></div> 
      </div>
      
               
      
      <div id="usersListEmptyBody"></div>
      <div id="popUp"></div>
      </div>
      </div>
      </div>
  </section>
  
      <!-- Content Area Ends--> 
 <div class="advancedsearch-mask"></div>
      <jsp:include page="../masterFooter.jsp" />
      <div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div>  
  
 <!--  Rajashkar
  SCript is used to export user data as xls format -->    

<script>
function myRolesFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myRolesInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myRolesUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}


function myClientsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myClientsInput");
    if(input != null){
    	filter = input.value.toUpperCase();
    }
    ul = document.getElementById("myClientsUL");
    if(ul != null){
    	 li = ul.getElementsByTagName("li");
    	    for (i = 0; i < li.length; i++) {
    	        a = li[i].getElementsByTagName("label")[0];
    	        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
    	            li[i].style.display = "";
    	        } else {
    	            li[i].style.display = "none";

    	        }
    	    }
    }
   
}


function myManagersFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myManagersInput");
    if(input != null){
    	filter = input.value.toUpperCase();
    }
    ul = document.getElementById("myManagersUL");
    if(ul != null){
    	 li = ul.getElementsByTagName("li");
    	    for (i = 0; i < li.length; i++) {
    	        a = li[i].getElementsByTagName("label")[0];
    	        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
    	            li[i].style.display = "";
    	        } else {
    	            li[i].style.display = "none";

    	        }
    	    }
    }
   
}


function myTeamsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTeamsInput");
    if(input != null){
    	filter = input.value.toUpperCase();
    }
    ul = document.getElementById("myTeamsUL");
    if(ul != null){
    	 li = ul.getElementsByTagName("li");
    	    for (i = 0; i < li.length; i++) {
    	        a = li[i].getElementsByTagName("label")[0];
    	        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
    	            li[i].style.display = "";
    	        } else {
    	            li[i].style.display = "none";

    	        }
    	    }
    }
   
}


function myDepartmentFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myDepartmentInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myDepartmentUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

$(document).keyup(function(e) {
	  if (e.keyCode === 27) {
		  closePop();
	  }
	});

</script>

<script>
function resetFilter(){
	$("ul#myRolesUL > li input:checkbox").prop("checked",false);
	$("ul#statusFilter > li input:checkbox").prop("checked",false);
	$("ul#myClientsUL > li input:checkbox").prop("checked",false);
	$("ul#myManagersUL > li input:checkbox").prop("checked",false);
	$("ul#myTeamsUL > li input:checkbox").prop("checked",false);
	$("ul#myDepartmentUL > li input:checkbox").prop("checked",false);
	$("ul#employmentTypeFilter > li input:checkbox").prop("checked",false);
	$("#dynamicUserDataTableSearch").val('');
	$("#myManagersInput").val('');
	$("#myRolesInput").val('');
	$("#myClientsInput").val('');
	$("#myTeamsInput").val('');
	$("#myDepartmentInput").val('');
	
	document.getElementById("activeStatus").checked = true;
	document.getElementById("notloggedInStatus").checked = true;
	
	myManagersFunction();
	myRolesFunction();
	myClientsFunction();
	myTeamsFunction();
	myDepartmentFunction();
	getDynamciUserList();
}
</script>


<script>
function exportUserData(){
	var userListEmpty = $("#noUsers").val();
	var exportType = "xls";
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	
	 var userRole = [];
		$('input:checkbox[name=userRole]:checked').each(function() 
	    {
			userRole.push($(this).val());
	    });
		
		
		var userState = [];
		$('input:checkbox[name=userState]:checked').each(function() 
	    {
			userState.push($(this).val());
	    });
		
		var orgClients = [];
		$('input:checkbox[name=orgClients]:checked').each(function() 
			    {
			orgClients.push($(this).val());
	     });
		
		var orgManagers = [];
		$('input:checkbox[name=orgManagers]:checked').each(function() 
			    {
			orgManagers.push($(this).val());
	     });
		
		
		var orgTeams = [];
		$('input:checkbox[name=orgTeams]:checked').each(function() 
			    {
			orgTeams.push($(this).val());
	     });
		
		
		var orgDepartments = [];
		$('input:checkbox[name=orgDepartments]:checked').each(function() 
			    {
			orgDepartments.push($(this).val());
	     });
		
		var employmentType = [];
		$('input:checkbox[name=employmentType]:checked').each(function() 
			    {
			employmentType.push($(this).val());
	     });
		
		var tags = [];
		$('input:checkbox[name=tags]:checked').each(function() 
	     {
			tags.push($(this).val());
	     });
		
    var searchValue = $("#dynamicUserDataTableSearch").val();
    searchValue = searchValue ? searchValue : "";
	if(userListEmpty != 'nousers'){
		window.location.href = atsUrl + "/getExportUsers?&userRole="+userRole+"&searchValue="+searchValue+"&userState="+userState+"&loginUserRole="+loginUserRole+"&orgClients="+orgClients+"&orgManagers="+orgManagers+"&orgTeams="+orgTeams+"&orgDepartments="+orgDepartments+"&employeMentType="+employmentType+"&tags="+tags+"&exportType=xls";
	}else{
		 var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Users List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	

}
</script> 


<script>
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
			$(".advancedsearch-mask").fadeOut();
		}
		function openPop(){
			$("div.cand-advsrch-popup").toggle();
			 $(".advancedsearch-mask").fadeIn();
		}
		
		/* function closeAdsrch(){
			$("div.cand-advsrch-popup").fadeOut();
			$(".advancedsearch-mask").fadeOut();
		} */
	</script>



<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
// 	$(".project_inn_mask").fadeOut(200);
}
</script>
<!-- script for flush advanced search form -->
<script>
function flushAdvancedData(){
	 $("#user_forms")[0].reset();
	 getUsersList("manual");
}
</script>
  
 <!--  Rajashkar
  SCript is used to export user data as pdf format --> 
  <script type="text/javascript">
  function validationMessage() {
	   var email = document.getElementById("email").value;
	   var mobilePhone = document.getElementById("mobilePhone").value;
	   $("#email").attr("disabled", "disabled");
	   $("#search_user").removeAttr("disabled");
	   
}
</script>
<script>
function exportToPdf(){
	var userListEmpty = $("#noUsers").val();
	var exportType = "pdf";
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    
    var userRole = [];
	$('input:checkbox[name=userRole]:checked').each(function() 
    {
		userRole.push($(this).val());
    });
	
	
	var userState = [];
	$('input:checkbox[name=userState]:checked').each(function() 
    {
		userState.push($(this).val());
    });
	
	var orgClients = [];
	$('input:checkbox[name=orgClients]:checked').each(function() 
		    {
		orgClients.push($(this).val());
     });
	
	var orgManagers = [];
	$('input:checkbox[name=orgManagers]:checked').each(function() 
		    {
		orgManagers.push($(this).val());
     });
	
	
	var orgTeams = [];
	$('input:checkbox[name=orgTeams]:checked').each(function() 
		    {
		orgTeams.push($(this).val());
     });
	
	
	var orgDepartments = [];
	$('input:checkbox[name=orgDepartments]:checked').each(function() 
		    {
		orgDepartments.push($(this).val());
     });
	
	var employmentType = [];
	$('input:checkbox[name=employmentType]:checked').each(function() 
		    {
		employmentType.push($(this).val());
     });

	var tags = [];
	$('input:checkbox[name=tags]:checked').each(function() 
		    {
		tags.push($(this).val());
     });
    
    
    var searchValue = $("#dynamicUserDataTableSearch").val();
    searchValue = searchValue ? searchValue : "";
	if(userListEmpty != 'nousers'){
	window.location.href = atsUrl+ "/getExportUsers?&userRole="+userRole+"&searchValue="+searchValue+"&userState="+userState+"&loginUserRole="+loginUserRole+"&orgClients="+orgClients+"&orgManagers="+orgManagers+"&orgTeams="+orgTeams+"&orgDepartments="+orgDepartments+"&employeMentType="+employmentType+"&tags="+tags+"&exportType=pdf";
	}else{
		 var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Users List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	
}
</script>
   
  
	<script type="text/javascript">	
	function loadChosenScripts(){
	    $('#search_user').removeAttr('disabled');
	    $('#rate').val(''); 
	    $('#ratePer').val('');
	    $('#mobilePhone').val('');

	   
	    $('#rateDiv').find('p.jquery_form_error_message').remove();
	    $('#mobilePhoneDiv').find('p.jquery_form_error_message').remove();
	    $('#ratePerDiv').find('p.jquery_form_error_message').remove();
	    $("#anyOfKeyword").val('').trigger("chosen:updated");
	    $("#allKeywords").val('').trigger("chosen:updated");
	    $("#excludeKeyWord").val('').trigger("chosen:updated");
	    $("#email").val('').trigger("chosen:updated");
	    $("#mobilePhone").val('').trigger("chosen:updated");
	    $("#userType").val('').trigger("chosen:updated");
	    $("#employeeId").val('').trigger("chosen:updated");
	    $("#userStatus").val('').trigger("chosen:updated");
	    
	    $("#anyOfKeyword").empty();
	    $("#allKeywords").empty();
	    $("#excludeKeyWord").empty();
	    $("#email").empty();
	    $("#mobilePhone").empty();
	    $("#employeeId").empty();
	    
	}
</script>
 

 
<script type="text/javascript">
function resendInvitation(userId){
	if(userId != null && userId != ""){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/resendInvitationEmail?"+tokenParamName+"="+tokenValue+"&userId="+userId, 
			        success: function(result) {
			        	$("#user"+userId).html("<i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)'  onClick='resendInvitation("+userId+");'>&nbsp;Resend</a>");
			        	$("#loading").removeAttr("style"); 
			        	$("#mailMsg").fadeIn(200);
		   		        $('#mailMsg').delay(2000).fadeOut('slow');
			        },
			    });
		 }
	}
  
</script>
<script type="text/javascript">
var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>
<script type="text/javascript">

    
    
	function searchUser(type) {
		if(type == "scroll"){
			offset = $("#usersOffset").val();
			var searchByName =  document.getElementById("searchElement").innerHTML 
    	}
		
		if(type == "manual"){
    		$('#usersListBody').empty();
    		$("#usersOffset").val(0);
    	 	$("#searchElement").val("");
    		var searchByName = document.getElementById("searchByName").value;
   		     $('#searchElement').append(searchByName);
    		 offset = 0;
    	}
		var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	    var userRole = document.getElementById("userRole").value;
		/* var searchByName = document.getElementById("searchByName").value; */
		var userState = document.getElementById("userState").value;
		
		if(loginUserRole != ''){
		 loginUserRole = '${SourceLeadWeb_loginUserRole}';
		}else{
			loginUserRole = "";	
		}
		if(userRole != ''){
			userRole  =  userRole;
		}else{
			userRole ="";
		}
		if(searchByName != ''){
			 searchByNames = searchByName;
		}else{
			searchByName = "";
		}
		if(userState != ''){
			userState = userState;
		}else{
			userState = "";
		}
		 if ($('#user_forms').validate(false, validationSettings)){
			 
      		var frm = $('#user_forms').serializeFormJSON();
			var con = JSON.stringify(frm);
		 	con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
		    var form = $('#user_forms')[0];
      		var formData = new FormData(form);  
			$("#search_user").attr("disabled", "disabled");
			 $.ajax({
						 type : "POST",
		        	     data: formData,
		        	     processData: false,
		    	         contentType:false,
		        	     dataType : 'json',
						 url : atsUrl+"/searchUser?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&offset="+offset+"&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole,
						 success : function(data) {
							$("#search_user").addClass('button').val('Search');
							$("#search_user").removeAttr("disabled");
							closePop();
							prepareUsersList(data,type);
						},
					}); 
			return true;
		 }else{ 
				return false;
		 } 
	} 
	
	$('body').on('blur', '#user_forms', function() {
		$("#user_forms").showHelpOnFocus();
		$("#user_forms").validateOnBlur(false, validationSettings);
	});
</script>

<script type="text/javascript">
function getUserSummary(userId){
	if(userId != null && userId != ""){
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/userSummary?"+tokenParamName+"="+tokenValue+"&userId="+userId, 
			        success: function(result) {
			        	 
			        },
			    });
		 }
	}
  
</script>
<script>
  function prepareUsersList(data,type){
	  if(data != null && data != 'undefined' && data != "" && data != '[]'){
		  var result = "";
		  var json = JSON.parse(data);
		  if(json.length > 0)	
	   		{
	    		$('#loadMoreDiv').empty(); 
	 			for(var i=0; i<json.length; i++){
	 				
			 				var userRole = json[i];
			 				var mobilePhone = ""
			 				var lastName = ""
			 			    if(userRole.mobilePhone != null && userRole.mobilePhone != ''){
			 			    	mobilePhone = userRole.mobilePhone;
			 			    }else{
			 			    	mobilePhone = "No Data"
			 			    }
			 				if(userRole.lastName != null && userRole.lastName != ''){
			 					lastName = userRole.lastName;
			 			    }else{
			 			    	lastName = " "
			 			    }
			       	    	result = "<div class='col-xs-12 col-sm-12 col-md-6 col-lg-4'>";
			       	    	result = result +"<div class='user-profile'>";
			       	    	result = result +"<div class='col-xs-4 col-sm-4'>";
			       	    	result = result +"<div class='text-center image-parent'>";
			       	    	result = result +"<div class='imageParent'>";
			       	    	result = result +"<img data-id= 'userProfileImage' onerror='predefineProfileImage(this);' class='m-t-xs'  src=\""+userRole.user.fileUrl+"\" alt='image'>";
			       	    	result = result +"</div>";
			       	    	
			       	    	
			       	    	result = result +"</div>";
			       	    	result = result +"</div>";
			       	    	result = result +"<div class='col-xs-8 col-sm-8'>";
			       	    	
			       	     '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSOCIATE\',\'ROLE_VIEW_EDIT_ASSOCIATE\',\'ROLE_VIEW_DELETE_ASSOCIATE\')">' 
			       	         	result = result +"<h4><a href='${SourceleadWeb_atsUrl}/userSummary/"+userRole.userId+"''> <span title='"+userRole.firstName+" "+lastName+"'> "+userRole.firstName+" "+lastName+" </span> </a> </h4>";
			       	    	'</sec:authorize>';
			       	    	
			       	     '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_ASSOCIATE\',\'ROLE_VIEW_EDIT_ASSOCIATE\',\'ROLE_VIEW_DELETE_ASSOCIATE\')">' 
		       	         	   result = result +"<h4><a href='#'> "+userRole.firstName+" " " "+lastName+"</a> </h4>";
		       	    	    '</sec:authorize>';
			       	    	
			       	    	result = result +"<p><label><i class='fa fa-envelope-o' aria-hidden='true'></i>&nbsp;<span title='"+userRole.email+"'> "+userRole.email+"</span></lable></p>";
			       	    	result = result +"<p><label><i class='fa fa-phone'  aria-hidden='true'></i>&nbsp;<span title='"+mobilePhone+"'> "+mobilePhone+"</span> </label></p>";
			       	    	result = result +"<p><label><i class='fa fa-user'  aria-hidden='true'></i>&nbsp;<span title='"+userRole.userType+"'> "+userRole.userType+"</span></label></p>";
			       	        
			       	    	if(userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'N' && userRole.userStatus != 'INACTIVE' && userRole.userStatus != 'ACTIVE'){
			       	    		result = result +"<p><label id='user"+userRole.userId+"' class='label label-info'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Invite</a></label></p>";
			 			    }else if((userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'Y') &&
			 			    		(userRole.userStatus != null && userRole.userStatus != "" && userRole.userStatus == 'NOT_LOGGEDIN') ){
			 			    	 result = result +"<p><label id='user"+userRole.userId+"' class='label label-info'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Resend</a></label></p>";
			 			    }else if(userRole.userStatus != null && userRole.userStatus != "" && userRole.userStatus == 'ACTIVE'){
			 			    	result = result +"<p><label class='label label-success'><i class='fa fa-check'></i>&nbsp;&nbsp;Active</label></p>";
			 			    }else if(userRole.userStatus != null && userRole.userStatus != "" && userRole.userStatus == 'INACTIVE'){
			 			    	result = result +"<p><label class='label label-danger'><i class='fa fa-ban'></i>&nbsp;&nbsp;In-active</label></p>";
			 			    } 
			       	    	
			       	    	result = result +"</div>";
			       	    	result = result +"<div class='clearfix'></div>";
			       	    	result = result +"</div>";
			       	    	result = result +"</div>";
			       	    	$("#loading").removeAttr("style"); 
			       	    	$('#usersListBody').append(result);
		            		$("#searchByName").val("");

				   }
	 			result = result + '<div class="clear"></div>';
	 			$("#noUsers").val("")
	   		    }
			  }else if(type == "scroll"){
		      	$("#noUsers").val("nousers")
		    	$('#loadMoreDiv').empty(); 
		    	$("#loading").removeAttr("style");
		    	var result ="";
		     	result = result + '<div id="loadMoreDiv" class="loadMoreDiv_styles">No More Records..</div>';
		     	$('#loadMoreDiv').append(result);
		    }
			  else{
		      	  $("#usersListBody").removeAttr("style");
		      	  $('#usersListBody').empty();
		      	  $('#loadMoreDiv').empty(); 
		          $("#loading").removeAttr("style");
		          var result ="";
		       	  result = result + '<div id="loadMoreDiv" class="loadMoreDiv_styles">No  Records..</div>';
		       	  $('#usersListBody').append(result);
		      }
  }
</script>    
<script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script> 
   

<script>
  $("#selector").fadeIn(200);
  $('#selector').delay(2000).fadeOut('slow');
	   $("#export-pop").click(function(){
	               $("#exportPop").toggle();
	   });
	   $("html").click(function() {
	       $("#exportPop").hide();
	   });
	   $("#exportPop, #export-pop").click(function(e) {
	       e.stopPropagation();
	   });        
</script> 
<!-- <script type="text/javascript">
    	   var config = {
    				  '.chosen-select'           : {},
    				  '.chosen-select-deselect'  : {allow_single_deselect:true},
    				  '.chosen-select-no-single' : {disable_search_threshold:10},
    				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
    				  '.chosen-select-width'     : {width:"95%"}
    	   }
			//Adding Customized Any Of Keyword to the list 
		    var select, chosen;
		    select = $("#anyOfKeyword");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosen = select.data('chosen');
		    chosen.search_field.on('keyup', function(e)
		    {
		    	var anyKeyword = this.value.replace(",","");
		        if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(anyKeyword).text(anyKeyword);
		            select.append(option);
		            select.find(option).prop('selected', true);
                    $("#anyOfKeyword option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#anyOfKeyword").trigger("chosen:updated");
	        		
		        }
		    });
		    
		  //Adding Customized Any Of Keyword to the list
		    var selectone, chosenone;
		    selectone = $("#allKeywords");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.on('keyup', function(e1)
		    {
		    	var allKeyword = this.value.replace(",","");
		        if ((e1.which == 13 || e1.which == 188) && (allKeyword != null  && allKeyword != '' && allKeyword != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(allKeyword).text(allKeyword);
		            selectone.append(option);
		            selectone.find(option).prop('selected', true);
                    $("#allKeywords option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#allKeywords").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selecttwo, chosentwo;
		    selecttwo = $("#excludeKeyWord");
		    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosentwo = selecttwo.data('chosen');
		    chosentwo.search_field.on('keyup', function(e2)
		    {
		    	var excludeKeyword = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (excludeKeyword != null  && excludeKeyword != '' && excludeKeyword != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(excludeKeyword).text(excludeKeyword);
		            selecttwo.append(option);
		            selecttwo.find(option).prop('selected', true);
                    $("#excludeKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#excludeKeyWord").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selectthree, chosenthree;
		    selectthree = $("#email");
		    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Email:' });
		    chosenthree = selectthree.data('chosen');
		    chosenthree.search_field.on('keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectthree.append(option);
		            selectthree.find(option).prop('selected', true);
                    $("#email option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#email").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selectfour, chosenfour;
		    selectfour = $("#mobilePhone");
		    selectfour.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Mobile Phone:' });
		    chosenfour = selectfour.data('chosen');
		    chosenfour.search_field.on('keyup', function(e6)
		    {
		    	var cert = this.value.replace(",","");
		        if ((e6.which == 13 || e6.which == 188) && (cert != null  && cert != '' && cert != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(cert).text(cert);
		            selectfour.append(option);
		            selectfour.find(option).prop('selected', true);
                    $("#mobilePhone option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#mobilePhone").trigger("chosen:updated");
	        		
		        }
		    });
		    
		     var selectfive, chosenfive;
		    selectfive = $("#employeeId");
		    selectfive.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add EmployeeId:' });
		    chosenfive = selectfive.data('chosen');
		    chosenfive.search_field.on('keyup', function(e6)
		    {
		    	var cert = this.value.replace(",","");
		        if ((e6.which == 13 || e6.which == 188) && (cert != null  && cert != '' && cert != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(cert).text(cert);
		            selectfive.append(option);
		            selectfive.find(option).prop('selected', true);
                    $("#employeeId option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#employeeId").trigger("chosen:updated");
	        		
		        }
		    });
		    
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
</script>  -->


<!-- ====================================== Prepare Dynamic User List By Laxman  ============================================================ -->
<script>
function getDynamciUserList(){
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	
	var userRole = [];
	$('input:checkbox[name=userRole]:checked').each(function() 
    {
		userRole.push($(this).val());
    });
	
	
	var userState = [];
	$('input:checkbox[name=userState]:checked').each(function() 
    {
		userState.push($(this).val());
    });
	
	var orgClients = [];
	$('input:checkbox[name=orgClients]:checked').each(function() 
		    {
		orgClients.push($(this).val());
     });
	
	var orgManagers = [];
	$('input:checkbox[name=orgManagers]:checked').each(function() 
		    {
		orgManagers.push($(this).val());
     });
	
	
	var orgTeams = [];
	$('input:checkbox[name=orgTeams]:checked').each(function() 
		    {
		orgTeams.push($(this).val());
     });
	
	var employmentType = [];
	$('input:checkbox[name=employmentType]:checked').each(function() 
		    {
		employmentType.push($(this).val());
     });
	
	
	var tags = [];
	$('input:checkbox[name=tags]:checked').each(function() 
	{
		tags.push($(this).val());
     });
	
	var orgDepartments = [];
	$('input:checkbox[name=orgDepartments]:checked').each(function() 
		    {
		orgDepartments.push($(this).val());
     });
	
	var searchValue = $("#dynamicUserDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	var url = atsUrl+"/getDynamicUserList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&userRole="+userRole+"&userState="+userState+"&loginUserRole="+loginUserRole+"&orgClients="+orgClients+"&orgManagers="+orgManagers+"&orgTeams="+orgTeams+"&orgDepartments="+orgDepartments+"&employeMentType="+employmentType+"&tags="+tags;
    $('#dynamicUserDataTableSearch').removeAttr('disabled');
	$('#dynamicUserDataTableSearch').attr('disabled','disabled');
	prepareDynamicUserList(url);
	
	
}
</script>

<script>
function prepareDynamicUserList(url){
	 	var result = "";
		result = result	+ '<div class="clearfix minHeight35 hidden">';
	    result = result	+ '<div class="remove_search">';
		result = result	+ '</div>';
		result = result	+ '</div>';
		result = result	+ '<div class="project_tabs_inn_table">';
		result = result + '<table id="dynamicUserListView" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Associate Name</th>';
		result = result + '<th>Email Id</th>';
		result = result + '<th>Phone No.</th>';
		result = result + '<th>Role</th>';
		result = result + '<th>Manager</th>';
		result = result + '<th>Employment Type</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#dynamicUserListViewDiv").empty();
		$("#dynamicUserListViewDiv").append(result);
		dynamicUserListPagination(url);
}
</script>
<script>
var table = '';
function dynamicUserListPagination(url){
	var colIndex = 0;
	table = $('#dynamicUserListView').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "searchHighlight": true,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
            { "data": "userName" },
            { "data": "email"},
            { "data": "mobilePhone"},
            { "data": "displayName"},
            { "data": "manager"},
            { "data": "userEmploymentType"},
            { "data": "status"}
        ],
		 "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "userNme",
		    	  	"render": function(data, type, userRole, meta) {
		    	  		var result = "";
		    	  		if(userRole.userName !=null && userRole.userName !='' && userRole.userName !='undefined'){
		        			   '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSOCIATE\',\'ROLE_VIEW_EDIT_ASSOCIATE\',\'ROLE_VIEW_DELETE_ASSOCIATE\')">' 
		        			   result = result +"<td><div class='proj_show divmax-width dotdotwithImage'>"    
		        			    result = result +" <a href='${SourceLeadWeb_atsUrl}/userSummary/"+userRole.userCode+"' class='UserTextLeft' title = '"+userRole.userName+"'>";
		        			    if(userRole.fileUrl != null && userRole.fileUrl !="" && userRole.fileUrl != "undefined"){
				       	    	result = result +"<img data-id= 'userProfileImage' onerror='predefineProfileImage(this);' class='m-t-xs pull-left'  src=\""+userRole.fileUrl+"\" alt='image'>";
		        			    }else{
					       	    result = result +"<img data-id= 'userProfileImage' onerror='predefineProfileImage(this);' class='m-t-xs pull-left'  src='/resources/images/userprofileImg.jpg' alt='image'>";
		        			    }
				       	    	result = result + '<div class="tbldata-dotdot-vsm">'+userRole.userName+'</div><i class="fa fa-binoculars pull-right"></i></a></div></td>';
								'</sec:authorize>';
							}else{
							result = result + ' <td><div>--</div></td>';
							}
			             return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
		          "data": "email",
		          "render": function ( data, type, userRole, meta ) {
		        	  var result = "";
		        	  if(userRole.userEmail !=null && userRole.userEmail !='' && userRole.userEmail !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot" title = "'+userRole.userEmail+'">'+userRole.userEmail+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						}
		              return result;
		          		
		          }
		          },{
		        "targets": colIndex++,
		        "data": "mobilePhone",
		        "render": function ( data, type, userRole, meta ) {
		        	if(userRole.mobilePhone != null && userRole.mobilePhone != ''){
					    	mobilePhone = userRole.mobilePhone;
					    }else{
					    	mobilePhone = "--"
					    }
		        	  var result = "";
							result = result + ' <td><div class="tbldata-dotdot-100" title = "'+mobilePhone+'">'+mobilePhone+'</div></td>';
		        	 return result;
		        }
		      }, {
		          "targets": colIndex++,
		          "data": "displayName",
		          "render": function ( data, type, userRole, meta ) {
		        	  var result = "";
		        	  result = result + ' <td><div class="tbldata-dotdot-100" title = "'+userRole.userRoleName+'">'+userRole.userRoleName+'</div></td>';
		        	  return result;
		          }
		        }, {
		            "targets": colIndex++,
		            "data": "manager",
		            "sortable": false,
		            "render": function ( data, type, userRole, meta ) {
		            	var result = "";
		            	if(userRole.reportingManagerName !=null && userRole.reportingManagerName !='' && userRole.reportingManagerName !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-sm" title = "'+userRole.reportingManagerName+'">'+userRole.reportingManagerName+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot-sm">--</div></td>';
						}
			              return result;
		            }
		      },
		      {
		          "targets": colIndex++,
		          "data": "userEmploymentType",
		          "sortable": false,
		          "render": function ( data, type, userRole, meta ) {
		          	var result = "";
		          	 if(userRole.employeMentType !=null && userRole.employeMentType !='' && userRole.employeMentType !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-100" title = "'+userRole.employeMentType+'">'+userRole.employeMentType+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot-100">--</div></td>';
						}
			              return result;
		          }
		    },
		    {
		        "targets": colIndex++,
		        "data": "status",
		        "sortable": false,
		        "render": function ( data, type, userRole, meta ) {
		        	var result = "";
		            if(userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'N'){
		            	'<sec:authorize access="hasRole(\'ROLE_VIEW_ASSOCIATE_ADDITIONAL_DETAILS\')">'  
		            	result = result +"<td><p class='tblminwidth-50'><label id='user"+userRole.userId+"' class='label label-info'><b class='fa fa-envelope' aria-hidden='true'></b><a class='color_white' href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Invite</a></label></p></td>";
		            	'</sec:authorize>';
	 			    	
	 			    	'<sec:authorize access="!hasRole(\'ROLE_VIEW_ASSOCIATE_ADDITIONAL_DETAILS\')">'  
	 			    			result = result +"<td><p class='tblminwidth-50'>---</p></td>";
	 			    	'</sec:authorize>';    
		            
		            }else if((userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'Y') &&
		 			    		(userRole.userState != null && userRole.userState != "" && userRole.userState == 'NOT_LOGGEDIN') ){
		 			    	'<sec:authorize access="hasRole(\'ROLE_VIEW_ASSOCIATE_ADDITIONAL_DETAILS\')">'  
		 			    		result = result +"<td><p class='tblminwidth-50'><label id='user"+userRole.userId+"' class='label label-info'><b class='fa fa-envelope' aria-hidden='true'></b><a class='color_white' href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Resend</a></label></p></td>";
		 			    	'</sec:authorize>';
		 			    	
		 			    	'<sec:authorize access="!hasRole(\'ROLE_VIEW_ASSOCIATE_ADDITIONAL_DETAILS\')">'  
		 			    			result = result +"<td><p class='tblminwidth-50'>---</p></td>";
		 			    	'</sec:authorize>';
		 			    }else if(userRole.userState != null && userRole.userState != "" && userRole.userState == 'ACTIVE'){
		 			    	result = result +"<td><p class='tblminwidth-50'><label class='label label-success'><b class='fa fa-check'></b>&nbsp;&nbsp;Active</label></p></td>";
		 			    }else if(userRole.userState != null && userRole.userState != "" && userRole.userState == 'INACTIVE'){
		 			    	result = result +"<td><p class='tblminwidth-50'><label class='label label-danger'><b class='fa fa-ban'></b>&nbsp;&nbsp;In-active</label></p></td>";
		 			    } 
		              return result;
		        }
		  }],
		    "scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"initComplete": function( settings, json ) {
	    		 $('#dynamicUserDataTableSearch').removeAttr('disabled');
	    	  },
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     },
    	  drawCallback: function(){
    	      $('a.paginate_button', this.api().table().container()).on('click', function(){
    	    	  searchHighlight();
    	         }); 
    	   }
    } );
    $("#dynamicUserListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
}
</script>
<script>
$("#dynamicUserDataTableSearch").keyup(function(event) {
	$('#dynamicUserDataTableSearch').parent().find('p.jquery_form_error_message').remove();
    if (event.keyCode === 13) {
    	$('#dynamicUserDataTableSearch').parent().find('p.jquery_form_error_message').remove();
    	var searchData = $('#dynamicUserDataTableSearch').val();
    	var regex =  new RegExp(/[\\]/g);
    	if(regex.test(searchData) == false){
    		getDynamciUserList();
		    searchHighlight();
    	}else{
    		$("#dynamicUserDataTableSearch").after("<p class='jquery_form_error_message'><span>Enter valid Search Data</span>");
    	}
    	$('#dynamicUserDataTableSearch').val(searchData);
    }
});

function beforeSearch(){
	$('#dynamicUserDataTableSearch').parent().find('p.jquery_form_error_message').remove();
	var searchData = $('#dynamicUserDataTableSearch').val();
	var regex =  new RegExp(/[\\]/g);
	if(regex.test(searchData) == false){
		getDynamciUserList();
	    searchHighlight();
	}else{
		$("#dynamicUserDataTableSearch").after("<p class='jquery_form_error_message'><span>Enter valid Search Data</span>");
	}
	$('#dynamicUserDataTableSearch').val(searchData);
}
</script>
 <script>
 function searchHighlight(){
	 setTimeout(function () {
 		var body = $( table.table().body() );
	        body.unhighlight();
	        body.highlight( $("#dynamicUserDataTableSearch").val() );   
			
			}, 1000); 
 }
 </script>
 <script>
	$(document).on( 'init.dt.dth', function (e, settings, json) {
    table = new $.fn.dataTable.Api( settings );
    var body = $( table.table().body() );
    if ( $( table.table().node() ).hasClass( 'searchHighlight' ) || settings.oInit.searchHighlight ||  $.fn.dataTable.defaults.searchHighlight ) {
	
	}
} );
	</script>
	
<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	


function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	


</script>
    