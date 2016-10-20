<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />



<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::Org Settings</title>
<style type="text/css">
.comp-textarea textarea {
min-height: 90px;
} 
#loading {
z-index: 9999999999 !important;
}
</style>
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">
//On document ready
$(function(){
	submissionTemplateList();
});
</script>


<script type="text/javascript">
	function submissionTemplateList() {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getOrgSubmissionTemplatesList?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(data) {
	        var json = JSON.parse(data);
			var result = "";
	      
				var result = "";
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result + '<th>Template Name</th>';
	            result = result + '<th>Description</th>';
	            result = result + '<th style="width:100px;">Action</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';

	        	for (var i=0; i<json.length; i++)
				  {	        		
	        		var opt = json[i];
	        		
	        		result = result + '<tr>';
	                result = result + '<td><div><span class="mid-span">'+opt.templateName+'</span></a></div></td>';
	                result = result + '<td><div class="tbldata-dotdot"><span class="mid-span">'+opt.description+'</span></div></td>';
	                result = result + '<td><div>';
	                result = result + '<a href="javascript:void(0)" onclick="viewSubmissionTemplate('+opt.id+',\''+opt.templateName+'\')">View</a>&nbsp;';
	                result = result + '|&nbsp;<a href="javascript:void(0)" onclick="addSubmissionTemplateFields('+opt.id+',\''+opt.templateName+'\',\''+opt.description+'\')">Edit</a>';
      	            result = result +'</div></td>';
	                result = result + '	 </tr>';
				}  
	        		
	        	result = result +'</tbody>';
    	       $("#clientSubmissionTemplateList").append(result); 
    	       
    	  
	        },
	    });
	}
</script>


<script>
function templatepagination(){  
    $('#templatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>

<body>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Submission Templates</h2>
    </div>
    <div class="pull-right brud-crum">Home » Vendor » Client Contact Details</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
	      <div class="timesheet_head">
	        <ul>
	          <li><a href="add-requirement.html"><i class="fa fa-plus"></i>Add Template</a></li>
	          <div class="clearfix"></div>
	        </ul>
	        <div class="clearfix"></div>
	        <div class="remove_search">
	          <input placeholder="search" type="text">
	        </div>
	      </div>
      
	       <div class="project_tabs_inn_table vendor_wrap_inn_table">
	          <table id="clientSubmissionTemplateList" class="display" cellspacing="0" width="100%"> </table>
	       </div>      
       </div>
	    <div class="side_mask">
	      <div class="proj_close"></div>
	    </div>
    
    <!-- Project --> 
  </div>
</section>

</body>


<jsp:include page="masterFooter.jsp" />
</html>


