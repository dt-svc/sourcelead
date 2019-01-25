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

<script type="text/javascript">
	function emailTemplateList() {
		var tabIds = [];
		$('input:checkbox[name=userTabIds]:checked').each(function() 
	    {
			tabIds.push($(this).val());
	    });
		
		var specification = [];
		$('input:checkbox[name=userSpec]:checked').each(function() 
	    {
			specification.push($(this).val());
	    });
		
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "GET",
	    	url : sourceleadUrl+"/getEmailTemplatesList?"+tokenParamName+"="+tokenValue+"&tabIds="+tabIds+"&specification="+specification, 
	        success: function(json) {
				var result = "";
				result = result +"<table id='templatePagination' class='display' cellspacing='0' width='100%' >"; 
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result + '<th>Template Name</th>';
	             result = result + '<th>Entity Type</th>'; 
	            result = result + '<th>Action</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';

	        	for (var i=0; i<json.length; i++)
				  {	 
	        		var opt = json[i];
	        		result = result + '<tr>';
	                result = result + '<td><div title="'+opt.scenarioName+'">'+opt.scenarioName+'</div></td>';
	                result = result + '<td><div class="tbldata-dotdot" title="'+opt.entityType+'"><span class="mid-span">'+opt.entityType+'</span></div></td>'; 
	                
	                result = result + '<td><div class="tblicons">';
	                    result = result + '<a class="mr10" title="summary" onclick="viewEmailTemplate('+opt.id+',\''+opt.uniqueScenarioName+'\')"><i class="fa fa-eye text-primery"></i></a>';
	                    result = result + '<a href="'+sourceleadUrl+'/editEmailTemplate?templateUUID='+opt.emailUUID+'" title="Edit"><i class="fa fa-pencil"></i></a>';
	                result = result +'</div></td>';
	                result = result + '	 </tr>';
				}  
	        		
	        	result = result +'</tbody>';
	        	result = result +"</table>";
	        	 $("#emailTemplateList").empty(); 
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
    <div class="pull-left" id="emailtemplate" style="display:block"><h2>Email Templates</h2> </div>
    <div class="pull-left" id="emailSummary" style="display:none"><h2>Email Template Summary</h2></div>
    <div class="pull-right brud-crum"  id="emailTemplates" style="display:block"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; <a href="<c:url value='${appPath}/goToSettings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>">Templates </a>&raquo; Email Templates</div>
    <div class="pull-right brud-crum" id="emailSumm" style="display:none"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>" class="paddingLeft">Home </a> &raquo; <a href="<c:url value='${appPath}/goToSettings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>">Templates </a>&raquo; <a href="<c:url value='${appPath}/emailTemplates'/>" class="paddingLeft">Email TemplatesList</a> &raquo; emailTemplateSummary </div>
  </section>
  	
  	
  	
  <div class="vendor_wrap clearfix">
  
	 <div class="reportHead reportHeadDataTab clearfix">
	    <ul class="pull-right list-unstyled list-inline">
	    	<li id="filterPops"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
		</ul>
	</div>	  
	
  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-small">
				<h5 class="headerAdsrch">Filters
					<a href="javascript:void(0)" class="close-div" onClick="closePop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
							
						<div class="col-md-4">
						<div class="clearfix mb">
							<label><strong>Entity</strong></label>
							 <div class="clearfix">
								<ul class="list-unstyled" id="entityFilter">
		                    	    <li class="checkBox positionRelative"><input   type="checkbox" name="userTabIds" id="associates" value ="2"  ><label>&nbsp;Associates</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userTabIds" id="clients" value ="7"  ><label>&nbsp;Clients</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userTabIds" id="jobs" value ="8" ><label>&nbsp;Jobs</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userTabIds" id="vendors" value ="9"  ><label>&nbsp;Vendors</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userTabIds" id="candidates" value ="10"  ><label>&nbsp;Candidates</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userTabIds" id="projects" value ="3" ><label>&nbsp;Projects</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userTabIds" id="myTimeSheets" value ="5"  ><label>&nbsp;My TimeSheets</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userTabIds" id="timeSheetApproval" value ="6"  ><label>&nbsp;TimeSheet Approval</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userTabIds" id="campaigns" value ="30" ><label>&nbsp;Campaigns</label></li>
		               				
		                        </ul>
							 </div>
							 </div> 
							 </div>
							 
							 <c:if test="${SourceLeadWeb_org.id == '1'}">
							 <div class="col-md-4 mb">
							<label><strong>Specification</strong></label>
							 <div class="clearfix">
								<ul class="list-unstyled" id="specificFilter">
		                    	    <li class="checkBox positionRelative"><input   type="checkbox" name="userSpec" id="sourceleadSpecific" value ="sourcelead"  ><label>&nbsp;Sourcelead</label></li>
									<li class="checkBox positionRelative"><input   type="checkbox" name="userSpec" id="OrgSpecific" value ="org"  ><label>&nbsp;Org</label></li>
		                        </ul>
							 </div>
							 </div> 
							 </c:if>
							 
							  </div>
							 </div> 
							 <div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet"
								onclick="closePop();emailTemplateList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="resetFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
		</div>
		
    <div class="clearfix project_tabs_inn_table">
	    <div id="emailTemplateList" class="datatable_filter datatable_filter-cutom dataSeaTop"></div>
     </div>
     
  </div>
</section>

</body>
</html>

<div class="advancedsearch-mask"></div>
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
			  document.getElementById("filterPops").style.display="none";
			 document.getElementById("emailTemplates").style.display="none";
			 document.getElementById("emailSumm").style.display="block";
			
			}
	});
		
}

</script>

<script>
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
			$(".advancedsearch-mask").fadeOut();
		}
		function openPop(){
			$("div.cand-advsrch-popup").toggle();
			 $(".advancedsearch-mask").fadeIn();
		}
		
		function closeAdsrch(){
			$("div.cand-advsrch-popup").fadeOut();
			$(".advancedsearch-mask").fadeOut();
		}
		
		
	</script>
	
	<script>
function resetFilter(){
	$("ul#entityFilter > li input:checkbox").prop("checked",false);
	$("ul#specificFilter > li input:checkbox").prop("checked",false);
	
	//emailTemplateList();
}
</script>


<jsp:include page="masterFooter.jsp" />	
