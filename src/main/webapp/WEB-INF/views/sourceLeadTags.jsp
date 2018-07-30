<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Source Lead Tags</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>  &raquo; Tags List </div>
</section>


<div class="timesheet_out">
		<div class="vendor_wrap">
		<div class="timesheet_head">
		  <ul>
			<li>
					<a href="javascript:void(0)" onclick="addTag();"><i class="fa fa-plus"></i>Add Tag</a>
			</li>
			<div class="clearfix"></div>
		  </ul>
		  <div class="clearfix"></div>
		  <div class="remove_search">
			<input type="text" placeholder="Search">
		  </div>
		</div>
		<!-- list Table -->
		<div class="require_wrap_table">
		  <table>
			<thead>
			  <tr>
				<th>Tag Name</th>
				<th>Associate</th>
				<th>Candidate</th>
				<th>Jobs</th>
				<th>Client</th>
				<th>Vendor</th>
				<th>Projects</th>
			  </tr>
			</thead>
			<tbody>
			<c:forEach var="tag" items="${tagsList}">
			  <tr>
			    <td>
					<div class="width215px">
						<div class="tbldata-dotdot inlineBlock" title="${tag.tagName}">${tag.tagName}</div>
						<div class="menuWidget  pull-right">
							<i class="fa fa-trash cursorPointer mt5 sideBlock paddingLeft paddingRight colorRed" onclick="deleteTagPop('${tag.tagName}');"></i>
						</div>
					</div>
				</td>
				<td><div class="tblminwidth-100"><a href="javascript:void(0)" title="Count">${tag.associateCount}</a></div></td>
				<td><div class="tblminwidth-100"><a href="javascript:void(0)" title="Count">${tag.candidateCount}</a></div></td>
				<td><div class="tblminwidth-100"><a href="javascript:void(0)" title="Count">${tag.jobCount}</a></div></td>
				<td><div class="tblminwidth-100"><a href="javascript:void(0)" title="Count">${tag.clientCount}</a></div></td>
				<td><div class="tblminwidth-100"><a href="javascript:void(0)" title="Count">${tag.vendorCount}</a></div></td>
				<td><div class="tblminwidth-100"><a href="javascript:void(0)" title="Count">${tag.projectCount}</a></div></td>
			  </tr>
			  </c:forEach>
			</tbody>
		  </table>
		</div>
		<div class="mb clearfix">&nbsp;</div>
		</div>
    </div>
  <div id="deleteTasgPopUp"></div>
  </section>
  <!-- Content Area Ends-->
  
  <script>
  function deleteTagPop(tagName){
	  var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop_resume' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>You are choosing to delete the Tag</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +'<b><input id="confirm-id" type="button" value="Ok" onclick="deleteTag(\''+tagName+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="deleteResumePopup();"></b>';
		result = result +"	</div>";
		$("#deleteTasgPopUp").empty();
		$("#deleteTasgPopUp").append(result);
		$("#remove_user_pop_resume").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
  }
  function deleteResumePopup(){
		$("#remove_user_pop_resume, .project_inn_mask").fadeOut();
	}
  function deleteTag(tagName){
	  alert("hello"+tagName)
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		alert("orgLocationId>>"+orgLocationId)
		$.post(sourceleadUrl+"/deleteTag?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&tagName="+tagName,{},function(result){
			$("#remove_user_pop_resume, .project_inn_mask").fadeOut();
			alert("hello")
			location.reload();
		});
  }
  </script>
 <jsp:include page="masterFooter.jsp" />	
 
  