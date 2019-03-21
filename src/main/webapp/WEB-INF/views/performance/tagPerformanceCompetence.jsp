<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 	
 	
 	 <section class="content-header clearfix">
     <div class="pull-left"><h2>Tag Competence</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration'/>">Configuration</a> &raquo; <a onclick="performanceManagement.performanceCompetenceList();">Competence List</a> &raquo; Add Competence</div>
    </section>
    
    
 <div class="project_wrap">
 <div id="tagCompetenceLoading"></div>
 <form:form id="tagCompetence_form"  name="tagCompetence_form" method="post">
 <div class="clearfix newAddWidth">
 <input type="hidden" name="tagCompetenceId" id="tagCompetenceId"/>
   <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
      <div class="col-md-6"> 
          <label class="col-md-12">Role<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-9">
            <select multiple class="chosen-select form-control"
								name="competenceRole" id="competenceRole" field-name="Role" data-placeholder="Select Role"
								data-validation="required">
			</select>
          </div>
      </div>
	</div>
	<div class="clearfix"></div>
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
				<div class="col-md-6">
				  <label class="col-md-12">Competence<span class="text-danger paddingLeft">&nbsp;*</span></label>
				  <div class="col-md-9">
						<select  multiple class="chosen-select form-control"
												name="performanceCompetence" data-placeholder="Competence"
												id="performanceCompetence" field-name="Competence" data-validation="required">
						</select>
				  </div>
				  <input type="hidden" name="jsonCompetenceString" id="jsonCompetenceString"/>
				</div> 
				
			<div class="clearfix"></div>
			<div class="col-md-6">
			<div class="clearfix" id="selectedCompetenceDiv">
			</div>
			</div>
	</div> 
    
    
    </div>
    
	<div class="footerstyelsNew">
	<div class="clearfix">
		<div class="pull-right mr">
			<button type="button" class="slBtn md colorBtn" id="savePerformanceBudget" onclick="performanceManagement.saveCompetenceTagging();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="performanceManagement.performanceCompetenceList();" >Cancel</button>
		</div>
	</div>
	</div>
 </form:form> 	 	
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
    
    $('#competenceRole').chosen();
    $('#performanceCompetence').chosen();
    
    $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
});
</script>
 