<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link href="resources/css/register-style.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/register-style.css" rel="stylesheet">

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Client Project</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo;<a href="<c:url value='/projectDashboard'/>"> Projects List</a>
			&raquo; Client Project
	</section>
	
	<div class="content-area clearfix">
	  <div id="loading"></div>
	<div class="newPrDetails newPrDetailsPMO"> 
      		<div class="newPrTabs">
   			  <ul class="newPrTabsMenu">
				<li class="current" onclick="getProjectDetails();"><a href="#tab-a" data-toggle="tab">Details</a></li>
				<li><a href="#tab-b" data-toggle="tab" id="assignedMembers" onclick="getAssignedMembers();">Assign Members</a></li>
				<li><a href="#tab-c" data-toggle="tab" id="additionalDetails" onclick="getAdditionalDetails();">Questionnaire</a></li>
				<div class="clearfix mb"></div>
			  </ul>
			  <div class="clearfix"></div>
		
		<div class="project_tabs">
		<div class="clearfix form-area form-horizontal toggle-div remove-div  newPrTabs_inn mt" id="tab-a">
			<form:form id="project_form" name="project_form" method="post">
              <div id="clientLevelsDiv"></div>
				<div class="form-footer clearfix mt">
					<div class="col-lg-12">
						<div class="pull-right">
							<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/projectDashboard'/>'" class="btn btn-danger ml">
							<input type="button" value="Save & Continue" id="save_project" onclick="saveProject();flushSaveProject()" class="btn btn-primary">
						</div>
					</div>
				</div>



			</form:form>
		</div>
	   <jsp:include page="projectAssignedMembers.jsp" />
	   <jsp:include page="projectAdditionalDetails.jsp" />
   </div>
		</div>
   	</div>
	
	
	</div>
	
</section>

<div id="divLevelTd1" style="display: none; float: left;">
	<div class="" id="REPLACEROWIDlevel" name="REPLACEROWIDlevel">
	<span id="REPLACEROWIDlevelName"></span>
	</div>
 </div>
 
 <div id="divLevelTd2" style="display: none; float: left;">
  <div class="dropdown-select">
              <select class="form-control select-bg CHOOSEN-SELECT-CLASS" data-validation="required" field-name="Client" id="REPLACEROWIDlevelClient" name="REPLACEROWIDlevelClient" >
                <option selected value="">--Select Client--</option>
                <c:forEach var="client" items="${clientsList}">
					<option value="${client.id}" >${client.id}</option>
    			</c:forEach> 
              </select>
  </div>
 </div>
 
 <div id="divLevelTd3" style="display: none; float: left;">
  <div>
     <label class="switch ml10 pull-left">
       <input type="checkbox" id="REPLACEROWIDchecked" name="REPLACEROWIDchecked" onclick="checkDocument(REPLACEROWID)" value="N">
       <div class="slider round"></div>
       </label>
       <span>Is end Client ?</span>
   </div>
</div>  


<div id="divLevelTd4" style="display: none; float: left;">
  <div>
         <div class="pull-right">
               <button type="button" class="btn btm-sm pull-right mt5" onclick="removelevel(REPLACEROWID)"><i class="fa fa-remove text-danger"></i></button>
          </div>
   </div>
</div>  

<jsp:include page="../masterFooter.jsp" />




<script>
	$(document).ready(function() {
		$("#tab-a").show();
		getClientLevels();
	    $(".newPrTabsMenu a").click(function(event) {
	        event.preventDefault();
	        $(this).parent().addClass("current");
	        $(this).parent().siblings().removeClass("current");
	        var tab = $(this).attr("href");
	        $(".newPrTabs_inn").not(tab).css("display", "none");
	        $(tab).fadeIn();
	    });
	});
</script>


<script>
function getClientLevels(){
	$("#clientLevelsDiv").empty();
	
	var result = "";
        
	result = result +'<div class="clearfix mb10 mt5">';
	result = result +'<input type="hidden" name="levelMaxRowNumber"  id="levelMaxRowNumber" value="1" />';
	result = result +'<table class="table table-responsive table-bordered table-hover"  width="100%" id="clientLevelTable" border="0">';
	result = result +'<tbody>';
	result = result +'<tr id="levelTsRow0">';
	result = result +'<div id="0levelFileGroup">';
	
	result = result +'<td width="150px">';
	result = result +'<div class="">';
	result = result +'<span>Level 1</span>';
	result = result +'</div>';
	result = result +'</td>';
	
	result = result +'<td width="100px">';
	result = result +'<div class="dropdown-select">';
	
	result = result +'<select class="form-control select-bg chosen-select" id="0levelClient" name="0levelClient">';
	result = result +'<option value="">--Select Client--</option>';
	<c:forEach var="report" items="${clientsList}">
	    if('${report.locationId.id}' == '${SourceLeadWeb_orgLocations.id}'){
	    	result = result +'<option value="${report.id},${report.responderLocationId.id}">${report.responderLocationId.org.orgName}</option>';
	    } 
	    if('${report.responderLocationId.id}' == '${SourceLeadWeb_orgLocations.id}'){
	    	result = result +'<option value="${report.id},${report.locationId.id}">${report.locationId.org.orgName}</option>';
	    }
    </c:forEach> 
 
	result = result +'</select>';
	
	result = result +'</div>';
	result = result +'</td>';
	
	result = result +'<td width="150px">';
	result = result +'<label class="switch ml10 pull-left">';
	result = result +'<input type="checkbox"  id="0checked" name="0checked" onclick="checkDocument(0)" value="N">';
	result = result +'<div class="slider round"></div>';
	result = result +'</label>';
	result = result +'<span>Is end Client ?</span>';	
	result = result +'</td>';
	
	result = result +'<td width="150px">';
	result = result +'<div class="pull-right">';
	result = result +'<button type="button" class="btn btm-sm pull-right mt5" onclick="addLevelRow();"><i class="fa fa-plus text-primary"></i></button>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</td>';
	
	result = result +'</tr>';
	result = result +'</tbody>';
	result = result +'</table>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
	
	$("#clientLevelsDiv").append(result);
	
	prepareChosenScripts();
	
	
}
</script>



<script>
function addLevelRow(){
	
	var checkMaxTsRowNum = $("#levelMaxRowNumber").val();
	var tbl = document.getElementById("clientLevelTable");
	var maxRowNum = parseInt($("input[name = 'levelMaxRowNumber']").val());
	$("input[name = 'levelMaxRowNumber']").val(maxRowNum + 1);
	

	var row = tbl.insertRow(maxRowNum);
	row.setAttribute("id", "divRequestedLevelRow" + maxRowNum);
	

	var cell0 = row.insertCell(0);
	var divDocTypeTdStr = document.getElementById("divLevelTd1").innerHTML;
	divDocTypeTdStr = divDocTypeTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell0.innerHTML = divDocTypeTdStr;
	
	var cell1 = row.insertCell(1);
	var divClientTdStr = document.getElementById("divLevelTd2").innerHTML;
	divClientTdStr = divClientTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	divClientTdStr = divClientTdStr.replace(/CHOOSEN-SELECT-CLASS/g, 'chosen-select');
	cell1.innerHTML = divClientTdStr;
	
	var cell2 = row.insertCell(2);
	var divEndClientTdStr = document.getElementById("divLevelTd3").innerHTML;
	divEndClientTdStr = divEndClientTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell2.innerHTML = divEndClientTdStr; 
	
	
	var cell3 = row.insertCell(3);
	var divRemoveLevelTdStr = document.getElementById("divLevelTd4").innerHTML;
	divRemoveLevelTdStr = divRemoveLevelTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell3.innerHTML = divRemoveLevelTdStr;
	
	prepareChosenScripts();
	
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
    setTimeout(function () {
    	getLevelDetails(maxRowNum);
    	 $("#loading").removeAttr('style');
	 }, 200);

			
	
	
	
	
}
</script>

<script>
function getLevelDetails(rowNum){
	$("#"+rowNum+"levelName").html("Level "+ parseInt(rowNum + 1));
	var checkMaxTsRowNum = $("#levelMaxRowNumber").val();
}
</script>


 <script type="text/javascript">
 function prepareChosenScripts(){ 
		var config = {
				  '.chosen-select'           : {},
				  '.chosen-select-deselect'  : {allow_single_deselect:true},
				  '.chosen-select-no-single' : {disable_search_threshold:10},
				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				  '.chosen-select-width'     : {width:"95%"}
				}
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({'width': '100%'});
					});
 }
</script>
