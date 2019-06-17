
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


	<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Appraisals List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
      <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/settings'/>">Settings</a> &raquo;
      <a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">Performance Management</a> &raquo; Appraisals List <span id = "fname"></a> </div>
    </section>
      
       <div class="contentareaInner-styles clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
            
          </div>
          <div class="require_wrap_table tableFirstSecond datatable_filter clearfix">
          
          <div class="timesheet_head clearfix minHeight40">
          <ul class="pull-right list-unstyled list-inline">
              <li><a class="newAdBtn mr5" onclick="performanceManagement.addNewAppraisalCycle();"  title="Add">+ Add</a></li>
            </ul>
          </div>
          
          
          
            <table id='appraisalTemplatesPagination' class='display' cellspacing='0' width='100%'>
              <thead>
                <tr>
                  <th>Cycle Name</th>
                  <th>Performance Period<br>(<small class="custom-small">${SourceLeadWeb_dateFormat == 'dd-MM-yyyy' ? 'DD-MM-YYYY' : 'SourceLeadWeb_dateFormat' == 'yyyy-MM-dd' ? 'YYYY-MM-DD' : 'SourceLeadWeb_dateFormat' == 'MM-dd-yyyy' ? 'MM-DD-YYYY' : 'SourceLeadWeb_dateFormat' == 'yyyy-dd-MM' ? 'YYYY-DD-MM' : '' }</small>)</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                
                <c:if test="${appraisalTemplatesListSize > 0}">
		            <c:forEach var="appraisal" items="${appraisalTemplatesList}">
		            
		            	<tr>
		                  <td><div class="tblminwidth-75">${appraisal.name}</div></td>
		                  <td>
							  <div>
							  <span class="tblminwidth-75 mr">${appraisal.selfAppraisalStartDate}</span>
							  <span class="tblminwidth-75">${appraisal.selfAppraisalEndDate}</span>
							  </div>
						  </td>
		                  <td>
							  <div class="tblminwidth-50">
								  <ul class="list-unstyled list-inline">
									  <li><a onclick="performanceManagement.assignAppraisalCycle('${appraisal.templateUUId}');" title="Assign"><i class="fa fa-user-plus cursorPointer text-primary"></i></a></li>
									  <li><a onclick="performanceManagement.addNewAppraisalCycle('${appraisal.templateUUId}');" title="Edit"><i class="fa fa-pencil"></i></a></li>
							  	      <c:if test="${appraisal.templateAssignedUserCount == 0}">
							  	      <li><a onclick="performanceManagement.deleteAppraisalCycle('${appraisal.templateUUId}');" title="Delete"><i class="fa fa-trash"></i></a></li>
								      </c:if>
								      <c:if test="${appraisal.templateAssignedUserCount > 0}">
								      <li><a href="javascript:vois(0)" title="Assigned Associates Count" onclick="templateAssignedAssociates('${appraisal.templateUUId}');">${appraisal.templateAssignedUserCount}</a>
								      </c:if>
								  </ul>
							  </div>
						  </td>
		                </tr>
		            
		            </c:forEach>
	            </c:if>
              </tbody>
            </table>
          </div>
        </div>
      </div>
       