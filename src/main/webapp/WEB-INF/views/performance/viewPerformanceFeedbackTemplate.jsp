<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.slidecontainer {
    width: 70% !important;
}
</style>
     
  <section class="content-header clearfix">
   <h2 class="pull-left">Feedback Template</h2>
    <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration'/>">Configuration</a> &raquo; <a onclick="performanceManagement.getFeedbackList();">Feddback List</a> &raquo; Feedback Template</div>
  </section>
  
  <div class="timesheet_out">
    <div class="vendor_wrap">
    
<div class="reportHead reportHeadDataTab clearfix" id="addTemplate">
      <ul class="pull-right list-unstyled list-inline">
      
        </ul>
      </div>     
	      
	       <div class="project_wrap_table">
	          
	          <div class="form-horizontal assignTemplate datatable_filter dataSeaTop">
	                <h4 class="clearfix" id="viewTemplateName"></h4>
		                
		        <div id="sjfb-fields" class="viewTemplate">
		        
		        </div>
		        <div class="form-footer clearfix">
		        <div class="pull-right mr">
		         <input type="button" value="Cancel" onclick="performanceManagement.getFeedbackList()" class="btn btn-default" >
		        </div>
		        </div>
		                
		      </div>
	       </div> 
       </div>
  </div>
  
