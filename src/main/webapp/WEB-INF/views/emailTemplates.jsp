<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<script src="/resources/js/InterViewFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script>
<script src="/resources/js/interViewForm-html-generator.js?staticTime=${staticTime}" type="text/javWascript" ></script> 
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />

<script type="text/javascript">
//On document ready
$(function(){
	emailTemplateList();
});
</script>

<style>
.datatable_filter {
position: relative !important; top:0 !important;
}

.datatable_filter input {
position: relative !important; top:0 !important;
}
</style>


<script type="text/javascript">
	function emailTemplateList() {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "GET",
	    	url : sourceleadUrl+"/getEmailTemplatesList?"+tokenParamName+"="+tokenValue+"", 
	        success: function(json) {
				var result = "";
				result = result +"<table id='templatePagination' class='display' cellspacing='0' width='100%' >"; 
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result + '<th>Template Name</th>';
	            result = result + '<th>Entity Type</th>';
	            result = result + '<th style="width:100px;">Action</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';

	        	for (var i=0; i<json.length; i++)
				  {	 
	        		var opt = json[i];
	        		result = result + '<tr>';
	                result = result + '<td><div><span class="mid-span" title="'+opt.scenarioName+'">'+opt.scenarioName+'</span></a></div></td>';
	                result = result + '<td><div class="tbldata-dotdot" title="'+opt.entityType+'"><span class="mid-span">'+opt.entityType+'</span></div></td>';
	                result = result + '<td><div>';
	                    result = result + '<a class="fa fa-eye text-blue ml" title="summary" onclick="viewEmailTemplate('+opt.id+',\''+opt.uniqueScenarioName+'\')"></a>';
	                    result = result + '&nbsp;<a href="'+sourceleadUrl+'/editEmailTemplate?templateUUID='+opt.emailUUID+'" title="Edit"><i class="fa fa-edit"></i></a>';
	                result = result +'</div></td>';
	                result = result + '	 </tr>';
				}  
	        		
	        	result = result +'</tbody>';
	        	result = result +"</table>";
    	       $("#emailTemplateList").append(result); 
    	       templatepagination();
    	  
	        },
	    });
	}
</script>




<script>
function templatepagination(){  
    $('#templatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>


<body>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left" id="emailtemplate" style="display:block">
      <h2>Email Templates</h2>
    </div>
    <div class="pull-left" id="emailSummary" style="display:none">
            <h2>Email Template Summary</h2>
          </div>
    <div class="pull-right brud-crum"  id="emailTemplates" style="display:block"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>">Templates</a>&raquo; Email Templates</div>
    <div class="pull-right brud-crum" id="emailSumm" style="display:none"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>" class="paddingLeft">Home</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>">Templates</a>&raquo; <a href="<c:url value='${appPath}/emailTemplates'/>" class="paddingLeft">Email TemplatesList</a> &raquo; emailTemplateSummary </div>
         
  </section>
  	
  <div class="timesheet_out">
  <div class="clearfix"></div>
    <div class="project_wrap">
	       <div class="project_wrap_table datatable_filter mt">
	    				<div id="emailTemplateList" class="datatable_filter datatable_filter-cutom"></div>
				</div>	
	       
       </div>
  </div>
</section>

</body>


</html>


<script type="text/javascript">
function viewEmailTemplate(id,uniqueScenarioName){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/viewEmailTemplate?${_csrf.parameterName}=${_csrf.token}&id="+id+ "&uniqueScenarioName="+ uniqueScenarioName,
		
		success : function(json) {
			
			var subject = "";
			var body = "";
			var signature="";
			var title="";
			
			if (json.scenarioName != null && json.scenarioName != '' && json.scenarioName != 'undefined') {
				title = json.scenarioName;
				} else {
					title = "--";
				}
			
			if (json.subject != null && json.subject != '' && json.subject != 'undefined') {
				subject = json.subject;
				} else {
				subject = "--";
				}
			if (json.body != null && json.body != '' && json.body != 'undefined') {
				body = json.body;
				} else {
					body = "--";
				}
			if (json.signature != null && json.signature != '' && json.signature != 'undefined') {
				signature = json.signature;
				} else {
					signature = "--";
				}
			
			var result = "";
			result = result	+ '<div class="project_tabs_inn_table clearfix">';
			result = result + '<div class="mt col-md-10">';
			
			result = result	+ '<div id="ProjectIdDiv" class="mb">';
			result = result + '<label class="clearfix">Template Name:</label>';
			result = result + '<div class="clearfix"><input type="text" value="'+title+'" class="form-control" readonly="true"></div>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div id="ProjectIdDiv" class="mb">';
			result = result + '<label class="clearfix">Subject:</label>';
			result = result + '<div class="clearfix"><textarea class="form-control" rows="2" readonly="true">'+subject+'</textarea></div>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			 result = result	+ '<div class="mb" id="ProjectIdDiv">';
			result = result + '<label class="clearfix">Email Body:</label>';
			result = result + '<div class="clearfix"><textarea class="form-control" rows="10" readonly="true">'+body+'</textarea></div>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>'; 
			
			/* result = result	+ '<div class="blogWrap" id="ProjectIdDiv">';
			result = result + '<strong>Email Body:</strong>';
			result = result + '<div class="clearfix"></div>';
			result = result + '<div class="blogpreviewContent">'+body+'</div>';
			result = result + '</div>'; */
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="mb" id="ProjectIdDiv">';
			result = result + '<label class="clearfix">Email Signature:</label>';
			result = result + '<div class="clearfix"><textarea class="form-control" rows="3" readonly="true">'+signature+'</textarea></div>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			
			
			$("#emailTemplateList").empty();  
			$("#emailTemplateList").append(result); 
			 document.getElementById("emailtemplate").style.display="none";
			 document.getElementById("emailSummary").style.display="block";
			 document.getElementById("emailTemplates").style.display="none";
			 document.getElementById("emailSumm").style.display="block";
			
			}
	});
		
}

</script>


<jsp:include page="masterFooter.jsp" />	
