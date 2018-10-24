<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<section class="content-wraper">
  <section class="content-header clearfix">
  
   <div class="success_pop" id="candSelector" style="display: none;"> 
     <i class="fa fa-check-circle"></i><span id="candMsgDiv"></span> 
   </div>
   
   <c:if test="${!empty msg}">
	        <div class="success_pop" id="sucesspop"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
    <div class="pull-left">
      <h2 id="headerLbl">Marketplace List</h2>
    </div>
    
    <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; <label id="brudCrumLbl">Marketplace List </label> </div>
    </c:if> 
  </section>
  <div class="clearfix">
	<ul class="project-tabs-menu projecttabs-custom clearfix">
         <li id="" ><a href="${appPath}/candidatesList">Candidate List</a></li>
         <li id=""><a href="javascript:void(0)" onclick="javascript:goToOrgMarketPlaceList();">Marketplace</a></li>
         <li id="" class="current"><a href="javascript:void(0)">Global Marketplace</a></li>
         <div class="clearfix"></div>
     </ul>
</div>
    <!-- Content Area -->
      <div class="tablelist-wrap">
        <div class="timesheet_head minHeight40">
          <div class="pull-left headerItems">
			  <ul>
				  <li><a title="Card Model"  href="javascript:void(0)"  onClick="cardModel();"><i class="fa fa-th text-muted"></i></a></li>
				  <li><a title="List Model" href="javascript:void(0)" onClick="listModel();"><i class="fa fa-th-list text-muted"></i></a></li>
			  </ul>
          </div>
        </div>
         <div class="immigrationWrap noPad clearfix mt5">
		<div class="immigraInnWrap">
		
		<!-- Filter Global market place-->
		<div class="positionRelative reportHead clearfix">
     	 <ul class="pull-right list-unstyled list-inline">
		   <span><input class="immigraTabSerach" id ="globalMarketSearh" placeholder="Search" type="text"></span>
        </ul>
      </div>
		
	<div id="dynamicGlobalMarketCandidateViewDiv" class="project_wrap_table datatable_filter"></div>
    </div>
    </div>
    </div>
    </section>
  <script>
  function goToCandidateList(){
		location.href = "${appPath}/candidatesList";
	}
  </script>
  <script type="text/javascript">
	$(document).ready(function () {
		getDynamicGlobalMarketCandidateList(null);
	   
	});
	function getDynamicGlobalMarketCandidateList() {
		
	     var result = "";
	     result = result + '<table id="dynamicGlobalMarketCandidateListTbl">';
	     result = result + '<thead>';
	     result = result + '<tr>';
	     result = result + '<th>Name</th>';
	     result = result + '<th>Location</th>';
	     result = result + '<th>Primary Skills</th>';
	     result = result + '<th>Current CTC</th>';
	     result = result + '<th>Expected CTC</th>';
	     result = result + '<th>Profile %</th>';
	     result = result + '<th>Action</th>';
	     result = result + '</tr>';
	     result = result + '</thead>';

	     result = result + '</table>';

	     $("#dynamicGlobalMarketCandidateViewDiv").html(result);
	     
         var sourceleadUrl = '${sourceleadUrl}';
	     
	     var searchValue = $("#globalMarketSearh").val();
	     searchValue = searchValue ? searchValue : "";
	     
	     var url ="";
	     url = sourceleadUrl + "/getGlobalMarketplaceList?searchValue="+searchValue; 
	     
	     dynamicGlobalMarketCandidateListPagination(url);
	 }
	 //var count = 0;
	 function dynamicGlobalMarketCandidateListPagination(url) {
		 var colIndex = 0;
	    $('#dynamicGlobalMarketCandidateListTbl').DataTable({
	         "processing": false,
	         "serverSide": true,
	         "searching": false,
	         "ajax": $.fn.dataTable.pipeline({
	             url: url,
	             method: 'POST',
	             pages: 10
	         }), 
	         "columns": [
	             {"data": "Name"},
	             {"data": "Location"},
	             {"data": "Primary Skills"},
	             {"data": "Current CTC"},
	             {"data": "Expected CTC"},
	             {"data": "Action"}
	         ],
	         "columnDefs": [
	             {
	                 "targets": colIndex++,
	                 "data": "fullName", 
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.fullName !== null && opt.fullName !== '' && opt.fullName !== 'undefined') {
	                    	    result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.fullName +"'>" + opt.fullName + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     }
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "location",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.location !== null && opt.location !== '' && opt.location !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.location+"'>" + opt.location + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     }
	                     return result; 
	                 }
	             },
	      
	             {
	                 "targets": colIndex++,
	                 "data": "skillsName",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.skillsName !== null && opt.skillsName !== '' && opt.skillsName !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.skillsName+"'>" + opt.skillsName + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     }
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "currentRate",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.currentRate !== null && opt.currentRate !== '' && opt.currentRate !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.currentRate+"'>" + opt.currentRate + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     } 
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "expectedRate",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.expectedRate !== null && opt.expectedRate !== '' && opt.expectedRate !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.expectedRate+"'>" + opt.expectedRate + "</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     } 
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	                 "data": "profilePercentage",
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                     if (opt.profilePercentage !== null && opt.profilePercentage !== '' && opt.profilePercentage !== 'undefined') {
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.profilePercentage+"%'>" + opt.profilePercentage + "%</div></td>";
	                     } else {
	                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
	                     } 
	                     return result;
	                 }
	             },
	             {
	                 "targets": colIndex++,
	               /*   "data": "action", */
	                 "render": function (data, type, opt, meta) {
	                     var result = "";
	                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = 'Add To Marketplace'><a onclick='addCandidateToMarketCart(\""+opt.id+"\")'>Add</a></div></td>";
	                     return result;
	                 }
	             }
	         ],
	         "scrollCollapse": false,
	         "lengthMenu": [[10, 15, 20], [10, 15, 20]],
	         "aaSorting": [],
	         "bLengthChange": true,
	         "bDestroy": true,
	         "ordering": true,
	         "initComplete": function (settings, json) {
	         },
	         "oLanguage": {
	             "sSearch": "",
	             "sZeroRecords": "", "sEmptyTable": "",
	             "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	         }
	     });
	    
	     $("#dynamicGlobalMarketCandidateListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
	      
	     $("#globalMarketSearh").keyup(function (e) {
	         if (e.which === 13) {
	        	 getDynamicGlobalMarketCandidateList( $("#globalMarketSearh").val());
	         }
	     });
	     
	 }	
	
</script>
<script type="text/javascript">
/*function funPrice(event){
	var price = event.target.text || event.srcElement.text;
	$(".selectPrice").text(price);
	
}*/

function addCandidateToMarketCart(id) {
   $.ajax({
        url: sourceleadUrl + "/addCandidateToMarketCart?id="+id,
        type: "GET",
        data: id,
        success: function (response) {
        	if(response=="success"){
          	getDynamicGlobalMarketCandidateList();
        	}
        	}
    });
}
$(".funPrice").on("click", "li", function(){
	var x = $(this).text();
	$(this).parents(".selectPriceli").find(".selectPrice").text(x);
});
			  
function listModel() {
	$("#tableDesign").show();
	$("#cardDesign").hide();
}
function cardModel() {
	$("#tableDesign").hide();
	$("#cardDesign").show();
}
function goToOrgMarketPlaceList(){
	location.href = "${appPath}/OrgMarketplace";
}
</script>
<jsp:include page="../masterFooter.jsp" /> 	