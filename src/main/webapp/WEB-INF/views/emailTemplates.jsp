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
.viewTemplate .sjfb-field {
float: left; min-height: 36px; margin-bottom: 15px;
}
.viewTemplate .sjfb-field textarea {
max-height: 65px;
}
.viewTemplate .sjfb-field  {
width: 80%;
margin: 0 0 10px 15px;
}
.viewTemplate .sjfb-field:nth-child(2n+2) {
    margin-left: 15px;
}
.viewTemplate .sjfb-field label {
float: left; width: 30%;
}
.sjfb-radio .choices-radio, .sjfb-checkbox .choices-checkbox {
float: left; width: 70%;
}
.sjfb-radio .choices-radio label, .sjfb-checkbox .choices-checkbox label {
	min-width: 20%;
	width: 25%;
	max-width: 50%;
	position:relative;
	padding-right: 10px;
	padding-left: 30px;
}
.choices input[type="checkbox"], .choices input[type="radio"] {
float: left;
position: absolute;
left:0;
}
.viewTemplate .sjfb-field select {
-webkit-appearance: none; -moz-appearance: none; -ms-appearance: none; -o-appearance: none;
}
.viewTemplate .sjfb-field select::-ms-expand {
    display: none;
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
	                result = result + '<td><div><span class="mid-span">'+opt.scenarioName+'</span></a></div></td>';
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
    <div class="pull-left">
      <h2>Email Templates</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/emailTemplates'/>"> Templates</a> &raquo; Email Templates</div>
    
  </section>
  
  <div class="timesheet_out">
    <div class="vendor_wrap">
	       <div class="project_tabs_inn_table">
	          <div id="emailTemplateList" class="datatable_filter"></div>
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
			result = result + '<div class="project_details col-md-10">';
			
			result = result	+ '<div id="ProjectIdDiv" class="row mb">';
			result = result + '<strong class="col-md-2 text-right">Subject:</strong>';
			result = result + '<div class="col-md-9 paddingLeft">'+subject+'</div>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="row mb" id="ProjectIdDiv">';
			result = result + '<strong class="col-md-2 text-right">Email Body:</strong>';
			result = result + '<div class="col-md-9 paddingLeft">'+body+'</div>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			result = result + '<div class="clearfix"></div>';
			
			result = result	+ '<div class="row mb" id="ProjectIdDiv">';
			result = result + '<strong class="col-md-2 text-right">Email Signature:</strong>';
			result = result + '<div class="col-md-9 paddingLeft">'+signature+'</div>';
			result = result + '<div class="clearfix"></div>';
			result = result + '</div>';
			
			
			
			$("#emailTemplateList").empty();  
			$("#emailTemplateList").append(result); 
			
			}
	});
		
}

</script>


<jsp:include page="masterFooter.jsp" />	
