<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<section class="content-wraper">
    <div class="success_pop" id="selector1" style="display: none;">
        <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
    </div>
    <section class="content-header clearfix">
        <div class="pull-left">
            <h2>Job Board</h2>
        </div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/job-boards'/>"> Job Board Configuration</a> &raquo; Manage Accounts</div>
    </section>

    <div class="project_wrap">
    
    
 <div class="reportHead reportHeadDataTab  clearfix">
     <ul class="pull-right list-unstyled list-inline">
		<li><a href="javascript:void(0)" class="newAdBtn mr5" id="addAccountBtn">+ Add</a></li>
		<li>
			<div class="">
	            <select id='jobBoardFilter' class="selectpicker noPad">
	                <option selected="selected" value='0'>All</option>
	                <c:forEach items="${jobBoardList}" var="j">
	                    <option value='${j.jobBoardId}'>${j.name}</option>
	                </c:forEach>
	            </select>
            </div>		
		</li>
	</ul>
</div>    
	            
	    <div class="project_wrap_table datatable_filter dataSeaTop" id="jobBoardAccountTableDiv">
	        <table id="jobBoardAccountTable">
	            <thead>
	                <tr>
	                    <th>Job Board</th>
	                    <th>Account Name</th>
	                    <th>User Name</th>
	                    <th>Assign To</th>
	                    <th>Action</th>
	                    <th>Active?</th>
	                </tr>
	            </thead>
	            <tbody id="jobBoardAccountTableBody"></tbody>
	        </table>
	    </div>
	    
    </div>
</section>

<jsp:include page="../masterFooter.jsp" />

<div class="popupWrap adduser-popup">
	<div class="popupHead">Account Setup <i class="closePop pull-right popClose" onclick="closeUserPop();"></i></div>
		<div class="popupContent">
             <form action='/' method="post" id='jobBoardAccountForm' onsubmit="return false">
                 <input type="hidden" id="accountIdHidden" value="0"/>
                 <input type="hidden" id="accountActiveHidden" value="false"/>
                 <input type="hidden" id="accountAccessHidden" value="false"/>
                 <div class="mb">
                     <label class="clearfix">Job Board<span class="text-danger">&nbsp;*</span></label>
                     <div class="clearfix">
                         <select onchange="showOrHideJobBoardFormFields($(this).val());" id="jobBoardId" name="jobBoardId" field-name="Job Board" class="form-control" data-validation="required">
                             <option value="" selected="selected">--Select Job Board--</option>
                             <c:forEach items="${jobBoardList}" var="j">
                                 <option value='${j.jobBoardId}' data-orgjobboardid="${j.id}">${j.name}</option>
                             </c:forEach>
                         </select>
                     </div>
                 </div>
                 <div data-fieldtype="account" data-prop='name' data-fieldfor="0" data-requiredfor="0" class="mb">
                     <label class="clearfix">Name<span class="text-danger">&nbsp;*</span></label>
                     <div class="clearfix">
                         <input type="text" id="jobBoarddAccountName" name="jobBoarddAccountName" field-name="Account name" placeholder="Name" class="form-control" data-validation="required"/>
                     </div>
                 </div>
                 <div data-fieldtype="account" data-prop='username' data-fieldfor="2,3" data-requiredfor="2,3" class="mb">
                     <label class="clearfix">Username<span class="text-danger">&nbsp;*</span></label>
                     <div class="clearfix">
                         <input type="text" autocomplete="off" id="jobBoarddAccountUsername" name="jobBoarddAccountUsername" field-name="Username" placeholder="Username"  class="form-control" data-validation="required"/>
                     </div>
                 </div>
                 <div data-fieldtype="account" data-prop='password' data-fieldfor="2,3" data-requiredfor="2,3" class="mb">
                     <label class="clearfix">Password<span class="text-danger">&nbsp;*</span></label>
                     <div class="clearfix">
                         <input type="password" autocomplete="off" id="jobBoarddAccountPassword" name="jobBoarddAccountPassword" field-name="Password" placeholder="Password"  class="form-control" data-validation="required"/>
                     </div>
                 </div>
                 <div data-fieldtype="account" data-prop='accountId' data-fieldfor="1,3" data-requiredfor="" class="mb">
                     <label class="clearfix">Account ID<span class="text-danger">&nbsp;*</span></label>
                     <div class="clearfix">
                         <input type="text" id="jobBoarddAccountId" name="jobBoarddAccountOId" placeholder="Account ID" field-name="Account ID"  class="form-control" data-validation="required"/>
                     </div>
                 </div>
                 <div data-fieldtype="account" data-prop='apiKey' data-fieldfor="1" data-requiredfor="" class="mb">
                     <label class="clearfix">API Key<span class="text-danger">&nbsp;*</span></label>
                     <div class="clearfix">
                         <input type="text" id="jobBoarddAccountAPIKey" name="jobBoarddAccountAPIKey" placeholder="API Key" field-name="API Key" class="form-control" data-validation="required"/>
                     </div>
                 </div>
                 <div data-fieldtype="account" data-prop='accessToken' data-fieldfor="1" data-requiredfor="1" class="mb">
                     <label class="clearfix">Access Token<span class="text-danger">&nbsp;*</span></label>
                     <div class="clearfix">
                         <input type="text" id="jobBoarddAccountAccessToken" name="jobBoarddAccountAccessToken" placeholder="Access Token" field-name="Access Token" class="form-control" data-validation="required"/>
                     </div>
                 </div>
             </form>
          </div>
          <div class="popupFoot text-right">
              <input type="button" class="slBtn md colorBtn" id="saveJobBoardAccount" value="Save">
              <input type="button" class="slBtn md popClose" value="Cancel" onClick="closeUserPop();">
          </div>
	</div>

<div class="popupWrap accountAssign-popup">
	<div class="popupHead"> Assign Account <i class="closePop pull-right popClose" onclick="closeUserPop();"></i></div>
    <div class="popupContent">
    	<input type="hidden" id="orgJobBoardAccountId"/>
            <div class="form-group clearfix">
                <label class="noPad">Account Name</label>
                <p class="noMar mt5" id="accountName"></p>
            </div>
            <div class="form-group clearfix">
                <label class="noPad">Assign To</label>
                <div class="clearfix row mt10">
                    <span class="col-md-3 radioStyles" style="position: relative; z-index: 9;">
                        <input type="radio" id="all" name="applicableFor" checked>
                        <label for="all">All</label>
                    </span>
                    <span class=" col-md-3 radioStyles" style="position: relative; z-index: 9;">
                        <input type="radio" id="user" name="applicableFor" checked>
                        <label for="user">User/Team</label>
                    </span>
                    <span class=" col-md-3 radioStyles" style="position: relative; z-index: 9;">
                        <input type="radio" id="none" name="applicableFor">
                        <label for="none">None</label>
                    </span>
                </div>
            </div>
            <div class="clearfix mb" id="user-div">
                <label class="noPad">Select User(s)</label>
                <div class="reports-head">
                    <select class="chosen-select" id="userSelectList" data-placeholder="Select User(s)" multiple>
                        <c:forEach items="${userList}" var="j">
                            <option value='${j.id}'>${j.firstName} ${j.lastName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="clearfix mb" id="team-div">
                <label class="noPad">Select Team(s)</label>
                <div class="reports-head">
                    <select class="chosen-select" id="teamSelectList" data-placeholder="Select Team(s)" multiple>
                        <c:forEach items="${teamList}" var="j">
                            <option value='${j.id}'>${j.name}</option>
                        </c:forEach>
                    </select>
                </div>
        </div>
    </div>
         
     <div class="popupFoot text-right">
         <input type="button" class="slBtn md colorBtn"" id="saveJBAccessInfo" value="Save">
         <input type="button" class="slBtn md popClose" value="Cancel" onClick="closeUserPop();">
     </div>
</div>
     
<script type="text/javascript" src="/resources/js/jobBoard.js?staticTime=${staticTime}"></script>
<script type="text/javascript">
                    $(function () {
                        filterJobBoardAccounts(0, "");
                    });
</script>