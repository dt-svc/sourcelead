<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Feedback</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
      <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/settings'/>">Settings</a> &raquo;
      <a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">Performance Management</a> &raquo; Feedback <span id = "fname"></a> </div>
    </section>
      <div class="timesheet_out" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative clearfix">
            <ul class="pull-right list-unstyled list-inline mb0">
              <li class="noMar"><a class="newAdBtn" title="Add" id="addPop" onclick="performanceManagement.addFeedbackTemplate();">+ Add Category</a></li>
            </ul>
          </div>
          <div class="clearfix row">
            
            <c:if test="${feedbackListSize > 0}">
	            <c:forEach var="feedback" items="${feedbackList}">
	            
		            <div class="col-md-12 mb mt">
					 <div class="media profileDetails-Widget">
		              <div class="media-body">
		                <div class="col-md-11">
		                <h4 class="media-heading">
							${feedback.name}
						</h4>
		                <p class="text-muted fontSmall mt10">
						    ${feedback.description}	
						</p>
		                  <div class="clearfix"></div>
		               </div>
						  <div class="col-md-1">
							  <ul class="list-unstyled list-inline text-center mt25">
							  	<li><a onclick="performanceManagement.addFeedbackTemplate('${feedback.id}');" title="Edit"><i class="fa fa-pencil"></i></a></li>
							    <li><a onclick="performanceManagement.viewFeedbackTemplate('${feedback.id}');" title="View"><i class="fa fa-eye"></i></a></li>
							  	<li><a onclick="performanceManagement.deleteFeedbackTemplate('${feedback.id}');" title="Delete"><i class="fa fa-trash"></i></a></li>
							  </ul>
						  </div>
		              </div>
		            </div>				  
					</div>
	            </c:forEach>
            </c:if>
            
            
			<c:if test="${feedbackListSize == 0}">
				 <div class='row'> 
					 <div class='col-lg-12'> 
						 <div class='dataTables_empty'>
						 </div>
					 </div>
				 </div>
			</c:if>
			
			
          </div>
        </div>
    </div>