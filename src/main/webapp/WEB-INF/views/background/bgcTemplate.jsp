	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<script src="${SourceLeadWeb_vmsUrl}/resources/js/bgcCandidate.js?staticTime=${staticTime}"></script>
   <section class="content-wraper">
      <section class="content-header clearfix">
        <div class="pull-left">
          <h2>Background Verification</h2>
        </div>
        <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a>&raquo;  
         Background Verification</div>
      </section>
       <!-- Content Area -->
    <div class="project_wrap clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
				<li>
				<a class="newAdBtn mr5" title="Add Template" href="addbgcTemplate.html">+ Add Template</a>	
					
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div class="require_wrap_table clearfix">
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                <td>
                  <div class="tbldata-dotdot" title="Cleared"><a>Cleared</a></div>
                </td>
                <td>
                    <div class="tbldata-dotdot-verybig" title="Murali Krishnamurthy Avanapu">
                  Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                  </div>
              </td>
                <td>
                <div class="width90px">
                  <ul class="list-inline list-unstyled">
                    <li><a href="editworkflow.html"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
                    <li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
                  </ul>	
                </div>
                </td>
                </tr>
                <tr>
                  <td>
                    <div class="tbldata-dotdot" title="Not Cleared"><a>Not Cleared</a></div>
                  </td>
                  <td>
                      <div class="tbldata-dotdot-verybig" title="Murali Krishnamurthy Avanapu">
                    Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                    </div>
                </td>
                  <td>
                  <div class="width90px">
                    <ul class="list-inline list-unstyled">
                      <li><a href="editworkflow.html"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
                      <li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
                    </ul>	
                  </div>
                  </td>
                  </tr>
             </tbody>
            </table>
          </div>
          <nav>
            <ul id="paging" class="pagination">
              <li><a href="#">Previous</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">3</a></li>
              <li><a href="#">4</a></li>
              <li><a href="#">5</a></li>
              <li class="disabled"><a href="#">6</a></li>
              <li data-page="last"><a href="#">Next</a></li>
            </ul>
            <div class="clearfix"></div>
          </nav>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>
<jsp:include page="../masterFooter.jsp" />
<script>
var vmsUrl = '${SourceLeadWeb_vmsUrl}';

$(document).ready(function() {
	  bgc.getDynamicBgcList(null);
})

$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	
</script>


