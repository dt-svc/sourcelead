	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" /> 

<title>Insert title here</title>
    <section class="content-wraper">
   
    <section class="content-header clearfix">
      <div class="pull-left"><h2>LCA</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="/app/immigration/viewImmigrationList">Petition List</a> &raquo; LCA List</div>
	<div class="clearfix"></div>
    </section>
    
     <div class="project_side_wrap_inn_tabs clearfix">
	    <ul class="project-tabs-menu">
		   
		    <li class="current"><a href="${appPath}/immigration/viewLCAList" >LCA List</a></li>
		    
		     <li><a href="${appPath}/immigration/viewImmigrationList" >Petition List</a></li>
	    </ul>
    </div>
        
    <div class="immigrationWrap noPad clearfix mt5">
   
		<div class="immigraInnWrap">
		<!-- Filter For Lca -->
<div class="positionRelative reportHead clearfix">
      <ul class="pull-right list-unstyled list-inline">
        <sec:authorize access="hasRole('ROLE_ADD_LCA')">
			<li><a href="${appPath}/immigration/loadNewLCA" class="newAdBtn mr5">+ New </a></li>
		</sec:authorize>	
			<li>
			    <select id="daysFilter" onchange="getDynamicLCAList()" style="padding: 3px 5px;">
                   
                    <option value="All" selected >All</option>
                    <option value="7">Expires In 7 Days</option>
                    <option value="15">Expires In 15 Days</option>
                    <option value="30">Expires In 30 Days</option>
                    <option value="60">Expires In 60 Days</option>
                    <option value="90">Expires In 90 Days</option>
                    <option value="120">Expires In 120 Days</option>
                    <option value="180">Expires In 180 Days</option>
                    <option value="360">Expires In 360 Days</option>
                    
                </select>
			</li>
			
			<li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openFilterPop();"></a></li>
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
						<sec:authorize access="hasAnyRole(\'ROLE_IMPORT_LCA\')">
							<a href="/app/uploadLcaForm">Import LCA</a>
						 </sec:authorize>
						  
						  <sec:authorize access="hasAnyRole(\'ROLE_EXPORT_LCA\')">
							<a href="javascript:void(0)" onclick="exportToLCAExcel();" title="Excel">Export Excel</a>
						  </sec:authorize>	
						</span>
					</div>
				</li>
		    
		   <span><input class="immigraTabSerach" id ="lcaSearch" placeholder="Search" type="text"></span>
        </ul>
      </div>
		
	<div id="dynamicLCAListViewDiv" class="project_wrap_table datatable_filter"></div>
    </div>
    </div>
  </section>
  <!-- Content Area -->
<div id="lcapopUp"></div>
<!-- Filter popup -->
 <!--  Popup  -->
 <div class="advancedsearch-mask"></div>   
 <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
		       <h5 class="headerAdsrch">Filters
							<a href="javascript:void(0)" class="close-div" onClick="closeFilterPop()"><i
								class="fa fa-close"></i></a>
						</h5>
						<div class="addInner">
							<div class="row">
							
							<div class="col-md-3 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<p>Status</p>
									</div>
									 <div class="clearfix filterSearch-ul">
											<ul class="list-unstyled positionRelative filterSearch mt5" id="myStatus">
	                                            <!-- <li class="checkBox positionRelative"><input  type="checkbox" name="status" value ="all" ><label>All</label></li> -->
												<li class="checkBox positionRelative"><input  type="checkbox" name="status" value ="Active" ><label>Active</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="status" value ="InActive" ><label>In Active</label></li>
											</ul>
									 </div> 
								</div>
							<div class="col-md-3 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<p>LCA Status</p>
									</div>
									 <div class="clearfix filterSearch-ul">
											<ul class="list-unstyled positionRelative filterSearch mt5" id="myLcaStatus">
												<li class="checkBox positionRelative"><input  type="checkbox" name="lcaStatus" value ="Initiated" ><label>Initiated</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="lcaStatus" value ="In progress" ><label>In progress</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="lcaStatus" value ="Certified" ><label>Certified</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="lcaStatus" value ="Denied" ><label>Denied</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="lcaStatus" value ="Withdrawn" ><label>Withdrawn</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="lcaStatus" value ="Voided" ><label>Voided</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="lcaStatus" value ="Invalidated" ><label>Invalidated</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="lcaStatus" value ="Rejected" ><label>Rejected</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="lcaStatus" value ="Expired" ><label>Expired</label></li>
											</ul>
									 </div> 
								</div>
						<!-- <div class="col-md-3 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<p>By Expiration duration</p>
									</div>
									 <div class="clearfix filterSearch-ul">
											<ul class="list-unstyled positionRelative filterSearch mt5" id="lcaEndDate">
												<li class="checkBox positionRelative"><input  type="checkbox" name="endDate" value ="All" ><label>All</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="endDate" value ="7 Days" ><label>7 Days</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="endDate" value ="15 Days" ><label>15 Days</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="endDate" value ="30 Days" ><label>30 Days</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="endDate" value ="60 Days" ><label>60 Days</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="endDate" value ="90 Days" ><label>90 Days</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="endDate" value ="120 Days" ><label>120 Days</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="endDate" value ="180 Days" ><label>180 Days</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="endDate" value ="360 Days" ><label>360 Days</label></li>
												<li class="checkBox positionRelative"><input  type="checkbox" name="endDate" value ="360+Days" ><label>360+Days</label></li>
											</ul>
									 </div> 
								</div>			 -->												
							</div>
							</div>
							<div class="clearfix advancedsearch-footer">
								<div class="pull-right mr">
									<input type="button" value="Apply" id="search_requiremet"
										onclick="closeFilterPop();getDynamicLCAList();" class="btn btn-sm btn-success" />
									<input type="button" value="Reset" id="clear_search"
										onclick="resetLcaFilter();" class="btn btn-sm btn-primary" />
								</div>
							</div>
		       </div>
		       
		       
<!-- Footer Area --> 

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
    </script> 

<jsp:include page="../masterFooter.jsp" />
 
  <!-- data table code starts below -->
 <script type="text/javascript">
 $(document).ready(function () {
     $("#statusFilter a").on('click', function (e) {
         getDynamicLCAList();
     });
 });

 function getDynamicLCAList() {
	
     var result = "";
     result = result + '<table id="dynamicLCAListTbl">';
     result = result + '<thead>';
     result = result + '<tr>';
     result = result + '<th>LCA No</th>';
     result = result + '<th>LCA Title</th>';
     result = result + '<th>Location</th>';
     result = result + '<th>Prevailing Wage</th>';
     result = result + '<th>Valid from</th>';
     result = result + '<th>Valid To</th>';
     result = result + '<th>LCA Status</th>';
     result = result + '<th>Status </th>';
     result = result + '<th>Action </th>';
     result = result + '</tr>';
     result = result + '</thead>';

     result = result + '</table>';
     $("#dynamicLCAListViewDiv").empty();
     $("#dynamicLCAListViewDiv").append(result);
     
     
     var status = [];
 	 $('input:checkbox[name=status]:checked').each(function() {
 		status.push($(this).val());
     });
 	
 	 var lcaStatus = [];
   	 $('input:checkbox[name=lcaStatus]:checked').each(function() {
  		lcaStatus.push($(this).val());
      });
     var sourceleadUrl = '${sourceleadUrl}';
     
     var searchValue = $("#lcaSearch").val();
     searchValue = searchValue ? searchValue : "";
     
     var filterByDays = $("#daysFilter").val();
     filterByDays = filterByDays ? filterByDays : "";
     
     var url ="";
     url = sourceleadUrl + "/immigration/getLcaViewList?searchValue="+searchValue+"&lcaStatus="+lcaStatus+"&mainStatus="+status+"&filterByDays="+filterByDays; 
     dynamicLCAListPagination(url);
 }
 //var count = 0;
 function dynamicLCAListPagination(url) {
	 var colIndex = 0;
     table = $('#dynamicLCAListTbl').DataTable({
         "processing": false,
         "serverSide": true,
         "searching": false,
         "ajax": $.fn.dataTable.pipeline({
             url: url,
             method: 'POST',
             pages: 10
         }), 
         "columns": [
             {"data": "LCA No"},
             {"data": "LCA Title"},
             {"data": "location"},
             {"data": "prevailingWage"},
             {"data": "Valid from"},
             {"data": "Valid To"},
             {"data": "lcaStatus"},
             {"data": "Status"}
         ],
         "columnDefs": [
             {
                 "targets": colIndex++,
                 bSortable: true,
                 aTargets: [ -1 ],
                  "data": "lcaNo", 
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.lcaNo !== null && opt.lcaNo !== '' && opt.lcaNo !== 'undefined') {
                    	 '<sec:authorize access="hasRole(\'ROLE_VIEW_LCA\')">'; 
                    	    result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.lcaNo +"'><a href=/app/immigration/viewLca?lcaUuid="+opt.lcaUuid+">"+opt.lcaNo+"</a></div></td>";
                    	 '</sec:authorize>';
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "lcaTitle",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.lcaTitle !== null && opt.lcaTitle !== '' && opt.lcaTitle !== 'undefined') {
                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.lcaTitle+"'>" + opt.lcaTitle + "</div></td>";
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
                    	    result = result +'<td>';
		   		        	result = result +'<div class="positionRelative">';
			   		        result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.location+'</p>';
			   		        result = result +'<div class="show_Empdetails showjoblist">';
		   		        	result = result +'<i class="fa fa-ellipsis-v"></i>';
	
		   		        	result = result +'<div class="emailvalues emailvaluesNew">';
	 							for(var k=0; k<opt.locationsList.length; k++){
	 								if (k === 3) {
	 							        break;
	 							    }
	 								  if (opt.locationsList[k] !== null && opt.locationsList[k] !== '' && typeof opt.locationsList[k] !== 'undefined') {
	 									console.log( " test " +opt.locationsList[k]);
	 								    result = result +'<p><i class="fa fa-map-marker"></i>'+opt.locationsList[k]+'</p>';
	 								  }
	 								  
			   		    	 	}
		   		        	result = result +'</div>';
		   		        	result = result +'</div>';
		   		        	result = result +'</div>';
		   		        	result = result +'</td>';
                    	 
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "prevailingWage",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.prevailingWage !== null && opt.prevailingWage !== '' && opt.prevailingWage !== 'undefined') {
                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.prevailingWage+"'>" + opt.prevailingWage + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "startDate",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.startDateStr !== null && opt.startDateStr !== '' && opt.startDateStr !== 'undefined') {
                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.startDateStr+"'>" + opt.startDateStr + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
                     } 
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "endDate",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.endDateStr !== null && opt.endDateStr !== '' && opt.endDateStr !== 'undefined') {
                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.endDateStr+"'>" + opt.endDateStr + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
                     } 
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "lcaStatus",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.lcaStatus !== null && opt.lcaStatus !== '' && opt.lcaStatus !== 'undefined') {
                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.lcaStatus+"'>" + opt.lcaStatus + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "status",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if(opt.status  ==  1){
                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = 'Active'>Active</div></td>";
                     }else{
                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = 'In-Active'>In-Active</div></td>";
                     }
                     
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "typeStandard",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     '<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_LCA\')">'; 
                     result = result + '<td><div><a href=/app/immigration/editLcaDetails?lcaUuid='+opt.lcaUuid+'><i id="edit' + opt.lcaUuid + '" class="glyphicon glyphicon-edit" title="Edit"></i></a>';
                     '</sec:authorize>';
                     if(opt.status  ==  1){
                    '<sec:authorize access="hasRole(\'ROLE_VIEW_DELETE_LCA\')">'; 
                      result = result + '<i id="delete'+opt.lcaUuid+'" onclick="deleteLcaListId(\''+opt.lcaUuid+'\');" class="glyphicon glyphicon-trash ml10" title="Delete"></i></div></td>';
                    '</sec:authorize>';
                     }else{
                    '<sec:authorize access="hasRole(\'ROLE_RE_ACTIVE_LCA\')">'; 
                    	 result = result + '<i id="reActive'+opt.lcaUuid+'" onclick="reActivateLca(\''+opt.lcaUuid+'\');" class="fa fa-rotate-left ml10" title="Re Active"></i></div></td>'; 
                    '</sec:authorize>';
                     }
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
    
     $("#dynamicLCAListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
      
     $("#lcaSearch").keyup(function (e) {
         if (e.which === 13) {
        	 getDynamicLCAList( $("#myLcaStatus").val(),"");
            
         }
     });
     
    $("table").colResizable(); // for resizing columns in tables.
 }
// reActivateLca(lcaUuid)
</script>

<script type="text/javascript">
	$(document).ready(function () {
		getDynamicLCAList(null);
	   
	});
</script>

<script type="text/javascript">
function deleteLcaListId(lcaUuid){ // only stauts updated with reason. active 1 , deactive 0.
	 $(".project_inn_mask").remove();
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_lca_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<textarea  id='reasonForLca' name='reasonForSpoc'    data-validation='required validate_Space validate_Description validate_length length2-250' placeholder='Reason' onkeypress=';' ></textarea>"; 
		result = result +" <b>Do you want to remove record</b>";
		result = result + ' <div id="reasonForLcaDiv"/>';
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onClick='deleteLcaDetailsByRowId(\""+lcaUuid+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelLcaDelete();'></b>";
		result = result +"	</div>";
		$("#lcapopUp").append(result);
		$("#remove_lca_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
</script>

<script type="text/javascript">
 function deleteLcaDetailsByRowId(lcaUuid){
	 var reason = $("#reasonForLca").val();
     if(reason.match(/^[^\s-](.*)*$/) != null && reason !="" && reason.length <= 250){
     $("#reasonForLcaDiv").empty();
		$.ajax({
	        url: sourceleadUrl+"/immigration/deleteLcaDetailsById?lcaUuid="+lcaUuid,
	        type: "GET",
	        success: function (result) {
	        	$("#remove_lca_pop").fadeOut(200);
	    		$(".project_inn_mask").fadeOut(200);
	    		window.location.href = sourceleadUrl+"/immigration/viewLCAList";
	        }
		});
     }else if (reason.length >= 251){
     	$("#reasonForLcaDiv").find('p.jquery_form_error_message').remove();
			$("#reasonForLcaDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason Length 1-250 characters</font>") ; 
     }else{
     	$("#reasonForLcaDiv").find('p.jquery_form_error_message').remove();
			$("#reasonForLcaDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
     }
	}
 </script>
 
 <script type="text/javascript">
   function cancelLcaDelete(){
		$("#remove_lca_pop").fadeOut(200);
		$(".project_inn_mask").fadeOut(200);
		$("#reasonForLca").val('');
	}
	function columPop(){
		$(".colPop").show();
	}
	function columClose(){
		$(".colPop").hide();
	}
</script>

<script>
   function openFilterPop(){
		$("div.cand-advsrch-popup").toggle();
		 $(".advancedsearch-mask").fadeIn();
	}
   function closeFilterPop(){
		$("div.cand-advsrch-popup").hide();
		$(".advancedsearch-mask").fadeOut();
	}
 function resetLcaFilter(){
	$("ul#myStatus > li input:checkbox").prop("checked",false);
	$("ul#myLcaStatus > li input:checkbox").prop("checked",false)
  }
</script>
	


<script type="text/javascript">
 function reActivateLca(lcaUuid){
		$.ajax({
	        url: sourceleadUrl +"/immigration/reActivateLca?lcaUuid="+lcaUuid,
	        type: "GET",
	        success: function (result) {
	    		window.location.href = sourceleadUrl+"/immigration/viewLCAList";
	        }
		});
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

// LCA Export Excel

function exportToLCAExcel(){
	if (!table.data().count() ) {
	    alert( 'Empty table' );
	}else{
		window.location.href = sourceleadUrl+"/immigration/getLcaListExport?exportType=excel";
	}
}

$('#mypop').click(function(){
	$("#exportPop").toggle();
	$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, .action-pop").slideUp();
}); 

</script>
