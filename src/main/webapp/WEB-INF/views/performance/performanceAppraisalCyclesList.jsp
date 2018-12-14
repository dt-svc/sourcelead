
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


	<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Appraisals List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="${appPath}/performanceManagementConfiguration">Performance Management</a> &raquo; Appraisals List <span id = "fname"></a> </div>
    </section>
      
      <div class="timesheet_out" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative clearfix">
            <ul class="pull-right list-unstyled list-inline mb0">
              <li class="noMar"><a class="newAdBtn" title="Add" id="addPop" onclick="performanceManagement.addNewAppraisalCycle();">+ Add Appraisal</a></li>
            </ul>
          </div>
          <div class="clearfix row">
          </div>
        </div>
     </div>
       