 
 <jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Project Report</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Project Report</div>
    </section>

     
  <div class="vendor_wrap">
<div class="clearfix reportHead  reports-head">
		<div class="col-md-7 mt5">
			<div class="row mt5">
				<div class="col-md-1">
					<span class="refreIcon"><a href="javascript:void(0)" class="refeIcon" id ="resetFilter" title="Refresh" onclick="resetFilter();"></a></span>
				</div>
			
<div class="col-md-11">
			<select class="chosen-select" name ="projects" id = "projectsId" onchange = "projectReportDataTableList();"data-placeholder="Select Project" multiple>
			<c:forEach var="option" items="${projectList}">
				<option value="${option.id}">${option.projectName}</option>
			</c:forEach>
		  </select>
	</div>
				</div>
		</div>		 
	<div class="col-md-5">
		<ul class="pull-right list-unstyled list-inline">
              
              <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
              
              <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
		           	   		<a href="javascript:void(0)" onclick="projectReportExport('Pdf');" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="projectReportExport('Excel');" title="Excel">Export Excel</a>            
						</span>
					</div>
				</li>
              
              
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="projectReportDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="projectReportList();"></span></li>
            </ul>
	</div>
	 </div>
	 
	 <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">
    	<div id="projectFilterList" class="datatable_filter"></div>
	</div>
	 
  <div class="tableStyles mt10">
	 <div class="require_wrap_table">
	  <table class="table">
		<thead>
		  <tr>
		   	<th>ProjectId</th>
		   	<th>Project Name</th>
			<th>Project Status</th>
			<th>Project Manager</th>
			<th>Project StartDate</th>
			<th>Project EndDate</th>
			<th>Team Member</th>
			<th><br>TeamMember StartDate</th>
			<th><br>TeamMember End Date</th>
			<th>TeamMember Status</th>
		  </tr>
		</thead>
		<tbody id="projectReportList">
		</tbody>
	  </table>
	</div>
  </div>
  
	
	  <div class="advancedsearch-mask" onclick="closeAdsrch()"></div>
  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
    <h5 class="headerAdsrch">Filters <a href="javascript:void(0)" class="close-div" onclick="closePop()"><i class="fa fa-close"></i></a> </h5>
    <div class="addInner">
      <div class="row">
        <div class="col-md-3 mb positionRelative">
         
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="projectStatus">
              <li class="checkBox positionRelative">
                <input id ="projectStatus0" name="projectStatus" value="1883" type="checkbox">
                <label>All</label>
              </li>
              <li class="checkBox positionRelative">
                <input id ="projectStatus1" name="projectStatus" value="" type="checkbox">
                <label>Draft</label>
              </li>
              <li class="checkBox positionRelative">
                <inputid id="projectStatus2" name="projectStatus"  value="" type="checkbox">
                <label>Pending</label>
              </li>
             <li class="checkBox positionRelative">
                <input  id="projectStatus3" name="projectStatus"  value="" type="checkbox">
                <label>Approval</label>
              </li>
              <li class="checkBox positionRelative">
                <input id="projectStatus4" name="projectStatus"  value="" type="checkbox">
                <label>Approved</label>
              </li>
              <li class="checkBox positionRelative">
                <input  id="projectStatus5" name="projectStatus"  value="" type="checkbox">
                <label>Open</label>
              </li>
              <li class="checkBox positionRelative">
                <input id="projectStatus6" name="projectStatus"  value="" type="checkbox">
                <label>Closed</label>
              </li>
              <li class="checkBox positionRelative">
                <input id="projectStatus7" name="projectStatus"  value="" type="checkbox">
                <label>Void</label>
              </li>

            </ul>
          </div>
        </div>
        
        
        
        <c:if test="${clientListSize > 0}">
							 <div class="col-md-4 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myClientsInput" onkeyup="myClientsFunction()" placeholder="Clients" title="Type in a clients">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="clientsUL">
										    <c:forEach var="report" items="${add_client}">
											     <li class="checkBox positionRelative"><input  type="checkbox" name="clientName" value ="${report.orgRelId}" ><label>${report.clientName}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 </c:if>
							 
							 <c:if test="${projectManagersListSize > 0}">
							 <div class="col-md-4 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myManagersInput" onkeyup="myManagersFunction()" placeholder="Managers" title="Type in a managers">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="managersUL">
											<c:forEach var="manager" items="${projectManagersList}">
											<li class="checkBox positionRelative"><input  type="checkbox" name="projectManagers" value ="${manager.id}" ><label>${manager.fullName}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 </c:if>
        
      
        <div class="clearfix"></div>
      </div>
    </div>
<div class="clearfix"></div>
    <div class="clearfix advancedsearch-footer">
      <div class="pull-right mr">
        <input value="Apply" id="search_requiremet" onclick="closePop();getDynamciUserList();" class="btn btn-sm btn-success" type="button">
        <input value="Reset" id="clear_search" onclick="resetFilter();" class="btn btn-sm btn-primary" type="button">
      </div>
    </div>
  </div>


<script type="text/javascript">
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
	})
	$(document).ready(function(){
	$("#cd").click(function(){
	$(".clientDetails").toggleClass('hidden', 500);
	});

	});
</script>
<script>
	// Close Advanced Search
	function closePop(){
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").hide();
	projectReportDataTableList();
	}
	function openPop(){
	$(".advancedsearch-mask").show();
	$("div.cand-advsrch-popup").toggle();
	}	
</script>
<script>

function resetFilter(){

	$("ul#projectStatus > li input:checkbox").prop("checked",false);
	$("ul#managersUL > li input:checkbox").prop("checked",false);
	$("ul#clientsUL > li input:checkbox").prop("checked",false);

}

</script>
<script>

function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	

</script>

<script>
$(document).ready(function(){
	projectReportDataTableList();
});
function projectReportDataTableList(){
	
	
	var projects = $("#projectsId").val();
	var values = [];
	     $('input:checkbox[name=projectStatus]:checked').each(function(){
		  values.push($(this.val()));
	})
    var clients = [];
          $('input:checkbox[name=clientName]:checked').each(function() {
	     clients.push($(this).val());
    });
    var projectManagers = [];
          $('input:checkbox[name=projectManagers]:checked').each(function(){
	      projectManagers.push($(this).val());
     });
          
     var searchValue = $("#projectReportDataTableSearch").val();
     $.ajax({
			type : "POST",
			url : sourceleadUrl+"/getProjectReport?searchValue="+searchValue+"&values="+values+"&clients="+clients+"&projectManagers="+projectManagers+"&projects="+projects,
			success : function(result) {
				if(result && result.response === "SUCCESS") {
					if(result.data.length > 0) {     
						for(var e in result.data) {
							var report = result.data[e];
							var text = "<tr>";
							
							
							text += "		<td><div class='tbldata-dotdot'>" + (report.projectSeqNo === null ? "--" : report.projectSeqNo ) + "</div></td>";
		
							text += "		<td><div class='tbldata-dotdot'>" + report.projectName + "</div></td>";
							text += "		<td><div class='tbldata-dotdot-vsm'>" + report.projectStatus + "</div></td>";
							text += "		<td><div class='tbldata-dotdot-100'>" + report.projectManager + "</div></td>";
							text += "		<td><div class='tblminwidth-75'>" + report.projectStartDate + "</div></td>";
							text += "		<td><div class='tblminwidth-75'>" + report.projectEndDate + "</div></td>";
							text += "		<td><div class='tbldata-dotdot-100'>" + report.projectMembers + "</div></td>";
							text += "		<td><div class='tblminwidth-75'>" + report.memberStartDate + "</div></td>";
							text += "		<td><div class='tblminwidth-75'>" + report.memberEndDate + "</div></td>";
							text += "		<td><div class='tbldata-dotdot-100'>" + report.memberStatus + "</div></td>";
							text += "	</tr>";
							$("#projectReportList").append(text);
						}
					}
				} else 
					alert("Please try again....");
			}
		})
	}
</script>
<script>
function projectReportExport(type){

	var projects = $("#projectsId").val();
	var values = [];
	     $('input:checkbox[name=projectStatus]:checked').each(function(){
		  values.push($(this.val()));
	})
    var clients = [];
          $('input:checkbox[name=clientName]:checked').each(function() {
	     clients.push($(this).val());
    });
    var projectManagers = [];
          $('input:checkbox[name=projectManagers]:checked').each(function(){
	      projectManagers.push($(this).val());
     });
          
          $.ajax({
  			type : "POST",
  			url : sourceleadUrl+"/getProjectReportExport?type="+type+"&values="+values+"&clients="+clients+"&projectManagers="+projectManagers+"&projects="+projects
          })
	//window.location.href = sourceleadUrl+"/getProjectReportExport?type="+type+"&values="+values+"&clients="+clients+"&projectManagers="+projectManagers+"&projects="+projects;
}


</script>
	
</body>
</html>