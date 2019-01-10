<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

    <section class="content-header clearfix">
      <div class="pull-left"><h2>Add Competence</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/performanceManagementConfiguration'/>">Configuration</a> &raquo; <a onclick="performanceManagement.performanceCompetenceList();">Competence List</a> &raquo; Add Competence</div>
    </section>
    
    <div class="project_wrap">
		 <div class="clearfix newAddWidth">
			 <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 mt">
			      <div class="row"> 
			          <label class="col-md-3">Skills<span class="text-danger paddingLeft">&nbsp;*</span></label>
				          <div class="col-md-9 select-field">
											<select class="chosen-select form-control" id="competenceSkill"
												name="competenceSkill" field-name="Project Name">
												<option value="">--Select Skill--</option>
												<c:forEach var="skill" items="${skillsList}">
													<option value="${skill}">${skill}</option>
												</c:forEach>
											</select>
						 </div>
						 
						 <div class="clearfix"></div>
						 
						 <div class="clearfix col-md-6 mt25">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Designation</th>
					<th>Weightage</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody id="addCompetenceDesignationDiv">
				<tr>
					<td>
						<div class=""><input type="text" class="form-control"></div>
					</td>
					<td>
						<div class=""><input type="text" class="form-control"></div>
					</td>
					<td>
						<div class="width50px pull-left">
							<i class="fa fa-plus btn btn-primary pull-left" onclick="performanceManagement.addNewCompetenceDesignation();"></i>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
			      </div>
			</div>
		 </div>
 	</div>
 	
 	
 	 <script>
 $(function(){
    var config = {
		  '.chosen-select'           : {},
		  '.chosen-select-deselect'  : {allow_single_deselect:true},
		  '.chosen-select-no-single' : {disable_search_threshold:10},
		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		  '.chosen-select-width'     : {width:"95%"}
		}
    
    $('#competenceSkill').chosen();
    
    $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
});
</script>
 