<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<section class="content-wraper contentWraper">
    <div class="success_pop" id="selector1" style="display: none;">
        <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
    </div>
    <section class="content-header clearfix">
        <div class="pull-left">
            <h2>Job Board</h2>
        </div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/job-boards'/>"> Job Board Configuration</a> &raquo; Manage Accounts</div>
    </section>

    <div class="timesheet_out">
        <div class="tablelist-wrap">
            <div class="timesheetHead minHeight40 mt minHeight40">
                <div class="pull-right col-md-4">
                    <ul class="list-unstyled list-inline">
                        <li>
                            <a href="javascript:void(0)" class="addBtn" id="addAccountBtn">+ Add</a>
                        </li>
                        <li>
                            <div class="dynamic_search">
                                <select id='jobBoardFilter' class="selectpicker">
                                    <option selected="selected" value='0'>All</option>
                                    <c:forEach items="${jobBoardList}" var="j">
                                        <option value='${j.jobBoardId}'>${j.name}</option>
                                    </c:forEach>
                                </select>
                            </div>              
                        </li>
                    </ul>
                </div>
            </div>
            <div class="styleTables datatable_filter" id="jobBoardAccountTableDiv">
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
                    <tbody id="jobBoardAccountTableBody">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../masterFooter.jsp" />
<div class="sideMask"></div>
<div class="modal adduser-popup">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"  onClick="closeUserPop();">&times;</button>
                <h4 class="modal-title">
                    Account Setup
                </h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <form action='/' method="post" id='jobBoardAccountForm' onsubmit="return false">
                        <input type="hidden" id="accountIdHidden" value="0"/>
                        <input type="hidden" id="accountActiveHidden" value="false"/>
                        <input type="hidden" id="accountAccessHidden" value="false"/>
                        <div class="col-md-12 mb10">
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

                        <div data-fieldtype="account" data-prop='name' data-fieldfor="0" data-requiredfor="0" class="col-md-12 mb10">
                            <label class="clearfix">Name<span class="text-danger">&nbsp;*</span></label>
                            <div class="clearfix">
                                <input type="text" id="jobBoarddAccountName" name="jobBoarddAccountName" field-name="Account name" placeholder="Name" class="form-control" data-validation="required"/>
                            </div>
                        </div>

                        <div data-fieldtype="account" data-prop='username' data-fieldfor="2,3" data-requiredfor="2,3" class="col-md-12 mb10">
                            <label class="clearfix">Username<span class="text-danger">&nbsp;*</span></label>
                            <div class="clearfix">
                                <input type="text" autocomplete="off" id="jobBoarddAccountUsername" name="jobBoarddAccountUsername" field-name="Username" placeholder="Email"  class="form-control" data-validation="required"/>
                            </div>
                        </div>

                        <div data-fieldtype="account" data-prop='password' data-fieldfor="2,3" data-requiredfor="2,3" class="col-md-12 mb10">
                            <label class="clearfix">Password<span class="text-danger">&nbsp;*</span></label>
                            <div class="clearfix">
                                <input type="password" autocomplete="off" id="jobBoarddAccountPassword" name="jobBoarddAccountPassword" field-name="Password" placeholder="Password"  class="form-control" data-validation="required"/>
                            </div>
                        </div>

                        <div data-fieldtype="account" data-prop='accountId' data-fieldfor="1,3" data-requiredfor="" class="col-md-12 mb10">
                            <label class="clearfix">Account ID<span class="text-danger">&nbsp;*</span></label>
                            <div class="clearfix">
                                <input type="text" id="jobBoarddAccountId" name="jobBoarddAccountOId" placeholder="Account ID" field-name="Account ID"  class="form-control" data-validation="required"/>
                            </div>
                        </div>

                        <div data-fieldtype="account" data-prop='apiKey' data-fieldfor="1" data-requiredfor="" class="col-md-12 mb10">
                            <label class="clearfix">API Key<span class="text-danger">&nbsp;*</span></label>
                            <div class="clearfix">
                                <input type="text" id="jobBoarddAccountAPIKey" name="jobBoarddAccountAPIKey" placeholder="API Key" field-name="API Key" class="form-control" data-validation="required"/>
                            </div>
                        </div>

                        <div data-fieldtype="account" data-prop='accessToken' data-fieldfor="1" data-requiredfor="1" class="col-md-12 mb10">
                            <label class="clearfix">Access Token<span class="text-danger">&nbsp;*</span></label>
                            <div class="clearfix">
                                <input type="text" id="jobBoarddAccountAccessToken" name="jobBoarddAccountAccessToken" placeholder="Access Token" field-name="Access Token" class="form-control" data-validation="required"/>
                            </div>
                        </div>
                    </form>
                </div>

            </div>
            <div class="modal-footer">
                <input type="button" class="btn btn-primary btn-sm ml" id="saveJobBoardAccount" value="Save">
                <input type="button" class="btn btn-defautl btn-sm" value="Cancel" onClick="closeUserPop();">
            </div>
        </div>
    </div>
</div>

<div class="comMask"></div>
<div class="modal accountAssign-popup">
    <div class="modal-dialog">
        <div class="modal-content">
            <input type="hidden" id="orgJobBoardAccountId"/>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"  onClick="closeUserPop();">&times;</button>
                <h4 class="modal-title">
                    Assign Account
                </h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="form-group clearfix">
                        <label class="col-md-3">Account Name</label>
                        <div class="col-md-9">
                            <p class="" id="accountName"></p>
                        </div>
                    </div>
                    <div class="form-group clearfix">
                        <label class="col-md-3">Assign To</label>

                        <div class="col-md-9">
                            <span class="radioStyles mr" style="position: relative; z-index: 9;">
                                <input type="radio" id="all" name="applicableFor" checked>
                                <label for="all">All</label>
                            </span>
                            <span class="radioStyles mr" style="position: relative; z-index: 9;">
                                <input type="radio" id="user" name="applicableFor" checked>
                                <label for="user">User/Team</label>
                            </span>
                            <span class="radioStyles" style="position: relative; z-index: 9;">
                                <input type="radio" id="none" name="applicableFor">
                                <label for="none">None</label>
                            </span>
                        </div>
                    </div>
                    <div class="form-group clearfix" id="user-div">
                        <label class="col-md-3">Select User(s)</label>
                        <div class="col-md-9 reports-head">
                            <select class="chosen-select" id="userSelectList" data-placeholder="Select User(s)" multiple>
                                <c:forEach items="${userList}" var="j">
                                    <option value='${j.id}'>${j.firstName} ${j.lastName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group clearfix" id="team-div">
                        <label class="col-md-3">Select Team(s)</label>
                        <div class="col-md-9 reports-head">
                            <select class="chosen-select" id="teamSelectList" data-placeholder="Select Team(s)" multiple>
                                <c:forEach items="${teamList}" var="j">
                                    <option value='${j.id}'>${j.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <input type="button" class="btn btn-primary btn-sm ml" id="saveJBAccessInfo" value="Save">
                <input type="button" class="btn btn-defautl btn-sm" value="Cancel" onClick="closeUserPop();">
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/resources/js/jobBoard.js?staticTime=${staticTime}"></script>
<script type="text/javascript">
                    $(function () {
                        filterJobBoardAccounts(0, "");
                    });
</script>