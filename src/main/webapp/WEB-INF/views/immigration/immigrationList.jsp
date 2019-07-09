	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="${SourceLeadWeb_vmsUrl}/resources/js/immigration.js?staticTime=${staticTime}"></script>
<script type="text/javascript" src="/resources/js/jquery.dataTables.min.js"></script>
<!-- <script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script> -->
<!-- <script type="text/javascript" src="/resources/js/colResizable-1.6.min.js"></script> -->
<%-- <c:set var ="myImmigrationPage" value = "N" scope="session"/> --%>
<title>Insert title here</title>
    <section class="content-wraper">
   
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Petition List</h2></div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; Petition List</div>
	<div class="clearfix"></div>
    </section>    
    
    <div class="project_wrap">
    <div class="project_side_wrap_inn_tabs clearfix">
	    <ul class="project-tabs-menu">
		   
		   <sec:authorize access="hasAnyRole(\'ROLE_ADD_LCA\',\'ROLE_VIEW_LCA\',\'ROLE_VIEW_EDIT_LCA\',\'ROLE_VIEW_DELETE_LCA\',\'ROLE_IMPORT_LCA\',\'ROLE_EXPORT_LCA\')">
		    <li class=""><a href="${SourceLeadWeb_vmsUrl}/immigration/viewLCAList">LCA List</a></li>
		   </sec:authorize> 
		   
		   <li class="current"><a href="${SourceLeadWeb_vmsUrl}/immigration/viewImmigrationList">Petition List</a></li>
		   
	    </ul>
    </div>
    
    <div class="immigrationWrap noPad">
		<div class="immigraCountHead">
			<ul class="clearfix">
				<li class="clearfix" title="${!empty dashboardResult.draftsCount ? dashboardResult.draftsCount : 0}" >
					<p>Draft <b>
					
					<c:choose>
							<c:when test="${!empty dashboardResult.draftsCount and dashboardResult.draftsCount ne 0 }">
								<a href='javascript:getDynamicImmigrationList(null, "Draft")'>${dashboardResult.draftsCount}</a>
							</c:when>
							<c:otherwise>
					          <a href="javascript:void(0)">0</a>
					        </c:otherwise>
						</c:choose>
					</b></p>
				</li>			
				<li class="clearfix" title="${!empty dashboardResult.reqSentCount ? dashboardResult.reqSentCount : 0}">
					<p>Sent Request <b>
					<c:choose>
							<c:when test="${!empty dashboardResult.reqSentCount and dashboardResult.reqSentCount ne 0 }">
								<a href='javascript:getDynamicImmigrationList(null, "Request to fill")'>${dashboardResult.reqSentCount}</a>
							</c:when>
							<c:otherwise>
					           <a href="javascript:void(0)">0</a>
					        </c:otherwise>
						</c:choose>
					</b></p>
				</li>
				<li class="clearfix" title="${!empty dashboardResult.inProgressCount ? dashboardResult.inProgressCount : 0}">
					<p>Inprogress <b>
					<c:choose>
							<c:when test="${!empty dashboardResult.inProgressCount  and dashboardResult.inProgressCount ne 0}">
								<a href='javascript:getDynamicImmigrationList(null, "inProgress")'>${dashboardResult.inProgressCount}</a>
							</c:when>
							<c:otherwise>
					         <a href="javascript:void(0)">0</a>
					        </c:otherwise>
						</c:choose>
					</b></p>
				</li>
				<li class="clearfix" title="${!empty dashboardResult.apporovedCount ? dashboardResult.apporovedCount : 0}">
					<p>Approved <b>
					<c:choose>
							<c:when test="${!empty dashboardResult.apporovedCount and dashboardResult.apporovedCount ne 0 }">
								<a href='javascript:getDynamicImmigrationList(null, "Approved")'>${dashboardResult.apporovedCount}</a>
							</c:when>
							<c:otherwise>
					           <a href="javascript:void(0)">0</a>
					        </c:otherwise>
						</c:choose>
					</b></p>
				</li>
				<li class="clearfix" title="${!empty dashboardResult.rejectedCount ? dashboardResult.rejectedCount : 0}">
					<p>Rejected <b>
					<c:choose>
							<c:when test="${!empty dashboardResult.rejectedCount and dashboardResult.rejectedCount ne 0 }">
								<a href='javascript:getDynamicImmigrationList(null, "Rejected")'>${dashboardResult.rejectedCount}</a>
							</c:when>
							<c:otherwise>
					          <a href="javascript:void(0)">0</a>
					        </c:otherwise>
						</c:choose>
						
					</b></p>
				</li>
				<li class="clearfix"title="${!empty dashboardResult.rfeCount ? dashboardResult.rfeCount : 0}">
					<p>RFE <b>
					<c:choose>
							<c:when test="${!empty dashboardResult.rfeCount and dashboardResult.rfeCount ne 0 }">
								<a href='javascript:getDynamicImmigrationList(null, "rfe")'>${dashboardResult.rfeCount}</a>
							</c:when>
							<c:otherwise>
					           <a href="javascript:void(0)">0</a>
					        </c:otherwise>
						</c:choose>
					</b></p>
				</li>	
				<li class="clearfix"title="${!empty dashboardResult.deniedCount ? dashboardResult.deniedCount : 0}">
					<p>Denied <b>
					<c:choose>
							<c:when test="${!empty dashboardResult.deniedCount and dashboardResult.deniedCount ne 0 }">
								<a href='javascript:getDynamicImmigrationList(null, "denied")'>${dashboardResult.deniedCount}</a>
							</c:when>
							<c:otherwise>
					           <a href="javascript:void(0)">0</a>
					        </c:otherwise>
						</c:choose>
					</b></p>
				</li>	
				
				<li class="clearfix"title="${!empty dashboardResult.othersCount ? dashboardResult.othersCount : 0}">
					<p>Others <b>
					<c:choose>
							<c:when test="${!empty dashboardResult.othersCount and dashboardResult.othersCount ne 0 }">
								<a href='javascript:getDynamicImmigrationList(null, "others")'>${dashboardResult.othersCount}</a>
							</c:when>
							<c:otherwise>
					           <a href="javascript:void(0)">0</a>
					        </c:otherwise>
						</c:choose>
					</b></p>
				</li>				
			</ul>
		</div>
   
		<div class="immigraInnWrap">
		
		
<div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon">
      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="clearFilters();"></a>
      		<a href="javascript:void(0)" onclick="columPop();" title="Column Chooser" class="columIcon"></a>    
      		
<div class="colPop">
	<div class="colPopMid coldropdown-parent">
	<div class="clearfix">
		<div class="row">
				<div class="col-md-10 mb10 mt5 col-md-offset-2">
				<span class="checkbox-new  pull-left mr">
					<input id="columnsPickerUnCheckAll" class="radio-custom" name="columnsPicker" onclick="checkAllColumns(this);"  type="radio">
					<label for="columnsPickerUnCheckAll" class="radio-custom-label">Uncheck All</label>
				</span>
				<span class="checkbox-new pull-left">
					<input id="columnsPickerCheckAll" class="radio-custom" name="columnsPicker" onclick="checkAllColumns(this);"  type="radio" checked="">
					<label for="columnsPickerCheckAll" class="radio-custom-label" >Check All</label>
				</span>
			</div>
		</div>
	<div class="clearfix"></div>
		<div class="coldropdown-inner">
		<ul>
			<li>
				<span class="checkbox-new clearfix">
					<input id="a" class="checkbox-custom toggle-vis" name="paidComponent" data-column="0" type="checkbox" checked="">
					<label for="a" class="checkbox-custom-label" >Status</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="c" class="checkbox-custom toggle-vis" name="paidComponent" data-column="2" type="checkbox" checked="">
					<label for="c" class="checkbox-custom-label" >Country</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="d" class="checkbox-custom toggle-vis" name="paidComponent" data-column="3" type="checkbox" checked="">
					<label for="d" class="checkbox-custom-label" >Type</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="f" class="checkbox-custom toggle-vis" name="paidComponent" data-column="5" type="checkbox" checked="">
					<label for="f" class="checkbox-custom-label" >Email</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="g" class="checkbox-custom toggle-vis" name="paidComponent" data-column="6" type="checkbox" checked="">
					<label for="g" class="checkbox-custom-label" >Phone</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="h" class="checkbox-custom toggle-vis" name="paidComponent" data-column="7" type="checkbox" checked="">
					<label for="h" class="checkbox-custom-label" >On Date</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="j" class="checkbox-custom toggle-vis" name="paidComponent" data-column="9"  type="checkbox" checked="">
					<label for="j" class="checkbox-custom-label" >LCA Start Date</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="k" class="checkbox-custom toggle-vis" name="paidComponent" type="checkbox"  data-column="10" checked="">
					<label for="k" class="checkbox-custom-label" >LCA End Date</label>
				</span>
			</li>
		</ul>
		</div>
	</div>
	<div class="colPopFot">
		<a href="javascript:void(0)" onclick="columHide();">Apply</a>
		<a href="javascript:void(0)" onclick="columClose();uncheckPpup();">Cancel</a>
	</div>
</div>				
			</div>      		
      		
      </span>
      
      <ul class="pull-right list-unstyled list-inline">
      <%--  <a href="${appPath}/immigration/viewLCAList" class="btn btn-success btnSuccess anchorWhite btn-sm"><i class="paddingRight"></i> LCA List</a> --%>
      		<sec:authorize access="hasAnyRole('ROLE_ADD_PETITION','ROLE_EDIT_VIEW_PETITION','ROLE_DELETE_PETITION')">
            <li><a href="${SourceLeadWeb_vmsUrl}/immigration/loadNewPetition" class="action newAdBtn mr5">+ New </a></li>
			</sec:authorize>
				<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="filterPop();"></a></li>
				
				<sec:authorize access="hasAnyRole('ROLE_ADD_PETITION','ROLE_EDIT_VIEW_PETITION','ROLE_DELETE_PETITION')">
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
	                 		<a href="${SourceLeadWeb_sourceleadUrl}/uploadImmigrationForm">Import</a>
							<a href="javascript:void(0)" onclick="exportToExcel();" title="Excel">Export Excel</a>
						</span>
						<!-- <span>
	                 		<a href="/app/uploadLcaForm">Import LCA</a>
						</span> -->
					</div>
				</li>
		    	</sec:authorize>
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='immiListSerach'/><span class="repSearchIcon" onClick=""></span></li>
        </ul>
      </div>		
		
			  
			 <div id="dynamicImmigrationListViewDiv" class="project_wrap_table datatable_filter"></div>
			 
    </div>
    </div>
	</div>
  </section>
  <!-- Content Area -->
<div id="popUp"></div>

<div id="immigrationFilterMask" class="advancedsearch-mask"></div>
<div id="immigrationFilterPopup"  class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
<h5 class="headerAdsrch">
	Filters <a href="javascript:void(0)" class="close-div"
		onclick="closePop()"><i class="fa fa-close"></i></a>
	</h5>
		<div class="addInner">
			<div class="row">
			 	<div class="col-md-3">
						<div class="row">
							<div class="col-md-12">
								<label><strong>Duration</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="durationFilter">
			                    	    <li class="checkBox positionRelative"><input type="checkbox" name="userState" id="activeStatus" value="thisYear"><label>&nbsp;This year</label></li>
										<li class="checkBox positionRelative"><input type="checkbox" name="userState" id="notloggedInStatus" value="lastYear"><label>&nbsp;Last year</label></li>
										<li class="checkBox positionRelative"><input type="checkbox" name="userState" id="inactiveStatus" value="yearsRange" onClick="durationCustomsearch($(this));"><label>&nbsp;Custom</label></li>
			                        </ul>
								 </div>
							 </div>
							 <div class="clearfix"></div>
							 <div class="mb" id="durationCustomsearch" style="display:none;">
							 	<div class="col-md-6 paddingRight">
							 		<select placeholder="From Year" id="filterFromYear" onchange="getFilterTwoYear($(this).val());" class="form-control input-sm"></select>
							 	</div>
							 	<div class="col-md-6 paddingLeft">
							 		<select placeholder="To Year" id="filterToYear" class="form-control input-sm"></select>
							 	</div>
							 </div>
							 
						</div>
				</div>
				<input type="hidden" id="orgType" value="">
				<div class="col-md-3">
						<div class="row">
							<div class="col-md-12 mb">
								<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="attorneyFilterInput"  onkeyup="myClientsFunction('attorneyFilterInput','attorneyFilter');" placeholder="Attorney/Company" title="Type in a managers">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
								 <div class="clearfix filterSearch-ul mt5 filterSearch">
									<ul class="list-unstyled" id="attorneyFilter">
			                        </ul>
								 </div>
							 </div>
						</div>
				</div>
				
				<div class="col-md-3">
						<div class="row">
							<div class="col-md-12 mb">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="typeFilterInput" onkeyup="myClientsFunction('typeFilterInput','typeFilter');" placeholder="Type" title="Type in a managers">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
								 <div class="clearfix filterSearch-ul mt5 filterSearch">
									<ul class="list-unstyled" id="typeFilter">
			                        </ul>
								 </div>
							 </div>
						</div>
				</div>
			    <div class="col-md-3">
						<div class="row">
							<div class="col-md-12 mb">
								<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="statusFilterInput" onkeyup="myClientsFunction('statusFilterInput','statusFilter');" placeholder="Status" title="Type in a managers">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
								
								 <div class="clearfix mt5 filterSearch filterSearch-ul">
									<ul class="list-unstyled" id="statusFilter">
			                        </ul>
								 </div>
							 </div>
						</div>
				</div>
			</div>
		</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="closePop();getDynamicImmigrationsList();" class="btn btn-sm btn-success">
							<input type="button" value="Reset" id="clear_search" onclick="resetImmigrationFilter();" class="btn btn-sm btn-primary">
						</div>
					</div>		
</div>

<script>
var immiModulePath = "/immigration";
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		function exportToPdf(){
			window.location.href = vmsUrl+"/immigration/getImmigrationListExport?"+tokenParamName+"="+tokenValue+"&exportType=pdf";
		}
		
		function exportToExcel(){
			var vmsUrl ='${SourceLeadWeb_vmsUrl}';
			if (!table.data().count() ) {
			    alert( 'Empty table' );
			}else{
				window.location.href = vmsUrl+"/immigration/getImmigrationListExport?"+tokenParamName+"="+tokenValue+"&exportType=excel";
			}
		}

		$('#mypop').click(function(){
			$("#exportPop").toggle();
			$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, .action-pop").slideUp();
		}); 
	// Close Advanced Search
		function closePop(){
			$("#immigrationFilterPopup").hide();
			$("#immigrationFilterMask").fadeOut();
		}
		function filterPop(){
			$("#immigrationFilterPopup").toggle();
			$("#immigrationFilterMask").fadeIn();
		}
		
		function closeAdsrch(){
			$("#immigrationFilterPopup").hide();
			$("#immigrationFilterMask").fadeOut();
		}
		
		function durationCustomsearch(val){
			if(val.is(':checked')){
				$("#durationCustomsearch").show();
			}else{
				$("#filterToYear, #filterFromYear").val("");
				$("#durationCustomsearch").hide();
			}
		}
		
		
	</script>


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
        $(document).ready(function(){
        $("#cd").click(function(){
        $(".clientDetails").toggleClass('hidden', 500);
        });

    	$('#filterFromYear').append($('<option />').val("").html("--Start Year--"));
        for (i = new Date().getFullYear(); i > 1900; i--)
        {
            $('#filterFromYear').append($('<option />').val(i).html(i));
        } 
        });
        
        function getFilterTwoYear(selectedFrmYear){
        	$('#filterToYear').empty();
        	$('#filterToYear').append($('<option />').val("").html("--End Year--"));
        	for (i = new Date().getFullYear(); i >= selectedFrmYear ; i--)
            {
                $('#filterToYear').append($('<option />').val(i).html(i));
            } 
        }
    </script> 
<script>
    // Script for tabs next pre button
    var $tabs = $('.tabbable li');
    $('#prevtab').on('click', function() {
        $tabs.filter('.active').prev('li').find('a[data-toggle="tab"]').tab('show');
    });
    $('#nexttab').on('click', function() {
        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
    });
</script> 
<script>
    function showMenu() {
        $('.menupop').toggle();
    };
    function receivePaymentPop(){
       $("#recvPymntpop").fadeIn(200);
        $('.side_mask').fadeIn(200).addClass('in');
    }
    $(".close").click(function(){
       $("#recvPymntpop").fadeOut(200);
        $('.side_mask').fadeOut(200).removeClass('in');
    });
</script>

  <script>
    //addcandidate-popup script
    function createBill(){
        $('.addcandidate-popup').fadeIn(200);
        $('.pop_mask').fadeIn(200);
    };
       function closeCandidate(){
        $('.addcandidate-popup').fadeOut(200);
        $('.pop_mask').fadeOut(200);
       }
    </script>
    

    
    <!-- Script for Linked Invoice pop -->
    <script>
        $(".sendReminder").click(function(){
        $('.modal.modal_invoice').fadeIn(200);
        $('.side_mask').fadeIn(200);
        });
        
        $(".close, .cancel").click(function(){
        $('.modal.modal_invoice').fadeOut(200);
        $('.side_mask').fadeOut(200);
        });
    </script>
    <!-- Script for Linked Invoice pop -->
<jsp:include page="../masterFooter.jsp" />
 
  <!-- data table code starts below -->
 <script type="text/javascript">

 /* function getUscisStatusList(){
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/immigration/getUscisTypeDropDown", 
	        success: function(data) {
	        	$('#currUscisStatus').empty();
	        	$('#currUscisStatus').append('<option value="">--Select Type --</option>');
	            for(var i=0;i<data.length;i++){
	            	  var opt = data[i];
	            	  	if(opt.name == "Request to fill"){
	            	  		$('#currUscisStatus').append('<option selected value='+opt.id+' >'+opt.name+'</option>');
	            	  	}else{
	            			$('#currUscisStatus').append('<option value='+opt.id+' >'+opt.name+'</option>');
	            	  	}
	            }
	            $('#currUscisStatus').trigger("chosen:updated");		        	
	        }
	 });
 } */
 
 
 
 var hideColumns = [];
 var table = "";
 $(document).ready(function () {
     getDynamicImmigrationList(null);
     getPetitionTypeDropDown();
     getAttorneysByOrgLocation();
     getUscisTypeDropDown();
     /* getUscisStatusList(); */
     $('label.toggle-vis').on('click', function (e) {
         e.preventDefault();
         // Get the column API object
         var column = table.column($(this).attr('data-column'));
         $(this).toggleClass("active");
         // Toggle the visibility
         column.visible(!column.visible());
     });
});

 function getDynamicImmigrationList(filterVal, DashBoardFilterStatus) {
	 var filterAttorneyVal = [];
	 var filterTypeVal = [];
	 var filterStatusVal = [];
	 var filterDurationVal = [];
	 if(filterVal !== null){
		filterAttorneyVal = filterVal[0];
		filterTypeVal = filterVal[1];
		filterStatusVal = filterVal[2];
		filterDurationVal = filterVal[3];
	 }
	 var orgTypeVal =  $("#orgType").val();
     var result = "";
     result = result + '<table id="dynamicImmigrationListTbl">';
     result = result + '<thead>';
     result = result + '<tr>';
     result = result + '<th>Status</th>';
     result = result + '<th>ID</th>';
     result = result + '<th>Country</th>';
     result = result + '<th>Type</th>';
     result = result + '<th>Name</th>';
     result = result + '<th>Email</th>';
     result = result + '<th>Phone</th>';
     result = result + '<th>On Date</th>';
     if('${orgLocationInfo.isLawFirm}' == 'Y'){
     result = result + '<th>Company</th>';
     }else{
     result = result + '<th>Attorney</th>'; 
     }
     result = result + '<th>LCA Start Date</th>';
     result = result + '<th>LCA End Date</th>';
     result = result + '<th>Action</th>';
     result = result + '</tr>';
     result = result + '</thead>';

     result = result + '</table>';
     $("#dynamicImmigrationListViewDiv").empty();
     $("#dynamicImmigrationListViewDiv").append(result);

     var vmsUrl = '${SourceLeadWeb_vmsUrl}';
//     var statusFilter = $("#statusFilter").val();
     var searchValue = $("#immiListSerach").val();
     searchValue = searchValue ? searchValue : "";
     var url ="";
     if(DashBoardFilterStatus !== null && typeof DashBoardFilterStatus !='undefined'){
    	 url = vmsUrl + "/immigration/getImmigrationDashBoardFilterList?filterStatus="+DashBoardFilterStatus; 
     }
     else{
      url = vmsUrl + "/immigration/getImmigrationViewList?searchValue=" + searchValue+"&filterAttorneyVal="+filterAttorneyVal+"&filterTypeVal="+filterTypeVal+"&filterStatusVal="+filterStatusVal+"&filterDurationVal="+filterDurationVal+"&orgTypeVal="+orgTypeVal;
     }
     dynamicImmigrationListPagination(url);
 }
 //var count = 0;
 function dynamicImmigrationListPagination(url) {
	 var colIndex = 0;
     table = $('#dynamicImmigrationListTbl').DataTable({
         "processing": true,
         "serverSide": true,
         "searching": false,
        // "colReorder": true,
     	// 'dom': 'Rlfrtip',
         // "stateSave": true, // used to save the page state local or server side.
         "ajax": $.fn.dataTable.pipeline({
             url: url,
             method: 'POST',
             pages: 10
         }),
         "columns": [
             {"data": "Status"},
             {"data": "ID"},
             {"data": "Country"},
             {"data": "Type"},
             {"data": "Name"},
             {"data": "Email"},
             {"data": "Phone"},
             {"data": "On date"},
             {"data": "Attorney"},
             {"data": "LCA Start Date"},
             {"data": "LCA End Date"}
         ],
         "columnDefs": [
             {
                 "targets": colIndex++,
                 bSortable: false,
                 aTargets: [ -1 ],
                 /* "data": "currUscisStatus", */
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.currUscisStatusId !== null && opt.currUscisStatusId !== '' && opt.currUscisStatusId !== 'undefined') {
                    	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.currUscisStatus +"'>" + opt.currUscisStatus + "</div></td>";
                    	 //alert(opt.currUscisStatusId);
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "generatedId",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.generatedId !== null && opt.generatedId !== '' && opt.generatedId !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.generatedId+"'><a href=${SourceLeadWeb_vmsUrl}/immigration/viewImmigration?immigrationUUID="+opt.immigrationUUID+"&candidateUUID="+opt.candidateUUID+"&immiTemplateUUID="+opt.immiTemplateUUID+"&myImmigrationPage=N>"+opt.generatedId+"</a></div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "country",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.country !== null && opt.country !== '' && opt.country !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.country+"'>" + opt.country + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "petitionType",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.petitionType !== null && opt.petitionType !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.petitionType+"'>" + opt.petitionType + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                     }
                     return result;
                 }
             },
             
             {
                 "targets": colIndex++,
                 "data": "firstName",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.firstName !== null && opt.firstName !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.firstName+"'>" + opt.firstName + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "email",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.email !== null && opt.email !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.email+"'>" + opt.email + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                     }

                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "phone",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.phone !== null && opt.phone !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.phone+"'>" + opt.phone  + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "openDateStr",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.openDateStr !== null && opt.openDateStr !== '' && opt.openDateStr !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.openDateStr+"'>" + opt.openDateStr + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "orgName",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.orgName !== null && opt.orgName !== '' && opt.orgName !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.orgName+"'>" + opt.orgName +"("+opt.spocName+")</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "lcaStartDateStr",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.lcaStartDateStr !== null && opt.lcaStartDateStr !== '' && opt.lcaStartDateStr !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.lcaStartDateStr+"'>" + opt.lcaStartDateStr + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 "data": "lcaEndDateStr",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.lcaEndDateStr !== null && opt.lcaEndDateStr !== '' && opt.lcaEndDateStr !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.lcaEndDateStr+"'>" + opt.lcaEndDateStr + "</div></td>";
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                     }
                     return result;
                 }
             },
             {
                 "targets": colIndex++,
                 /*"data": "formsCount",*/
                 "render": function (data, type, opt, meta) {
                	 if( $("#orgType").val() == ""){
                	 	$("#orgType").val(opt.orgNameSel);
                	 }
                     var result = "";
                     '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_VIEW_PETITION\')">';
                     result = result + '<td><div><a href=${SourceLeadWeb_vmsUrl}/immigration/editImmigrationDetails?immigrationUUID='+opt.immigrationUUID+'><i id="edit' + opt.immigrationUUID + '" class="glyphicon glyphicon-pencil"></i></a>';
                     '</sec:authorize>';
                      '<sec:authorize access="hasAnyRole(\'ROLE_DELETE_PETITION\')">';
                     result = result + '<i id="delete' + opt.immigrationUUID + '" onclick="deleteImmigrationListId(\''+opt.immigrationUUID+'\');" class="glyphicon glyphicon-trash ml10"></i></div></td>';
                     '</sec:authorize>';
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
    
     $("#dynamicImmigrationListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");

     $("#immiListSerach").keyup(function (e) {
         if (e.which === 13) {
        	 getDynamicImmigrationList(null);
        	 for (var i=0; i<hideColumns.length; i++) {
        		 table.column(hideColumns[i]).visible(false); // hide the columns after filters also.
        		}
         }
     });

    // $("table").colResizable(); // for resizing columns in tables.
 }
 
 function deleteImmigrationListId(immigrationUUID){ // only stauts updated with reason. active 1 , deactive 0.
	 $(".project_inn_mask").remove();
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a'>";
		/* result = result +"		<  id='reasonForTemplate' name='reasonForSpoc' data-validation='required validate_Space validate_Description validate_length length2-250' placeholder='Reason' onkeypress=';' ></textarea>"; */
		result = result +" <b>Do you want to remove record permanently?</b>";
		result = result + ' <div id="reasonForTemplateDiv"/>';
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='immigration.deleteImmigrationDetailsByRowId(\""+immigrationUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelImmigrationDelete();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_immigration_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
 
 function changeSaveUscisStatus(idVal,templateId,uscisOldId){
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 var uscisId = idVal.val();
	 $.ajax({
	    	type: "GET",
	        url: vmsUrl+"/immigration/updateUscisTypeDropDown?templateId="+templateId+"&uscisId="+uscisId+"&uscisOldId="+uscisOldId, 
	        success: function(data) {
	        	if(data == "success"){
	        		//getDynamicImmigrationList();
	        	}
	        }
	 });
	 //alert(idVal.val()+" : "+templateId);
 }
</script>

<script type="text/javascript">

	function uncheckPpup(){
		$("#columnsPickerUnCheckAll").prop('checked',false);
		$("#columnsPickerCheckAll").prop('checked',true);
		$('.toggle-vis:input:checkbox').prop('checked',true);
	}	
	function cancelImmigrationDelete(){
		$("#remove_immigration_pop").fadeOut(200);
		$(".project_inn_mask").fadeOut(200);
	}
	function columPop(){
		$(".colPop").show();
	}
	function columClose(){
		$(".colPop").hide();
	}
	$("html").click(function(){
		$(".colPop").hide();
	});
	$(".refreIcon, .colPop").click(function(e){
	 	e.stopPropagation();
	});		
	
		
	function columHide(){
		hideColumns=[]; // reset the array object.
		$(".toggle-vis").each(function() {
			columClose();// 
		   var chkdVal= $(this).prop('checked');
		   var column = table.column($(this).attr('data-column'));
			  if(chkdVal == true){
			         column.visible(true);
			    }
			    else{
			      column.visible(false);
			      hideColumns.push($(this).attr('data-column')); // used for hiding the columns when user searched the data.
			  }
	    });
		$(".colPop").hide();
	   
	}
	function checkAllColumns(obj){
		var id = obj.id;
		if(id =='columnsPickerCheckAll'){
			$('.toggle-vis:input:checkbox').prop('checked',true);
		}
		else if(id =='columnsPickerUnCheckAll'){
			$('.toggle-vis:input:checkbox').prop('checked',false);
		}
	}
	
	function getPetitionTypeDropDown(){
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		$.ajax({
		    	type: "GET",
		        url: vmsUrl+immiModulePath+"/getPetitionTypeDropDown", 
		        success: function(data) {
		        	$('#typeFilter').empty();
		            for(var i=0;i<data.length;i++){
	    	        	var opt = data[i];
		           		$('#typeFilter').append('<li class="checkBox positionRelative"><input type="checkbox" name="userState" id="activeStatus" value="'+opt.id+'"><label>&nbsp;'+opt.name+'</label></li>');
		            }
		            $('#typeFilter').trigger("chosen:updated");		        	
		        }
		 });
	}
	
	function getAttorneysByOrgLocation(){
		var type = null ;
		if('${orgLocationInfo.isLawFirm}' == 'Y'){
			type = "Client";
		}else{
			type = "Vendor";
		}	 
		$.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getAllRelationsByOrgLocationAndType?"+tokenParamName+"="+tokenValue+"&type="+type, 
			        success: function(data) {
			        	var json = JSON.parse(data);
			        	$('#attorneyFilter').empty();
			            for(var i=0;i<json.length;i++){
			           		var opt = json[i];
			            	$('#attorneyFilter').append('<li class="checkBox positionRelative"><input type="checkbox" name="userState" id="activeStatus" value="'+opt.id+'"><label>&nbsp;'+opt.orgName+'</label></li>');
			            }
			            $('#attorneyFilter').trigger("chosen:updated");
			            
			        }
			 });
	 }
	
	function getUscisTypeDropDown(){
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		$.ajax({
		    	type: "GET",
		        url: vmsUrl+immiModulePath+"/getUscisTypeDropDown", 
		        success: function(data) {
		        	$('#statusFilter').empty();
		            for(var i=0;i<data.length;i++){
		            	var opt = data[i];
		            	$('#statusFilter').append('<li class="checkBox positionRelative"><input type="checkbox" name="userState" id="activeStatus" value="'+opt.id+'"><label>'+opt.name+'</label></li>');
		            }
		            $('#statusFilter').trigger("chosen:updated");		        	
		        }
		 });
	}
	
	function getDynamicImmigrationsList(){
		var filterStatusVal = [];
		 $('ul#statusFilter').find("input[type='checkbox']:checked").each(function (){
			filterStatusVal.push($(this).val());
		 });
		 var filterAttorneyVal = [];
		 $('ul#attorneyFilter').find("input[type='checkbox']:checked").each(function (){
			 filterAttorneyVal.push($(this).val());
		 });
		 var filterTypeVal = [];
		 $('ul#typeFilter').find("input[type='checkbox']:checked").each(function (){
			 filterTypeVal.push($(this).val());
		 });
		 var filterDurationVal = [];
		 $('ul#durationFilter').find("input[type='checkbox']:checked").each(function (){
			if($(this).val()=='thisYear'){
				filterDurationVal.push(((new Date()).getFullYear()));
			}
			if($(this).val()=='lastYear'){
				filterDurationVal.push(((new Date()).getFullYear()-1));
			}
			if($(this).val()=='yearsRange'){
				if($("#filterToYear").val() !== ""){
					if($("#filterFromYear").val() !== ""){
						for(var i = $("#filterFromYear").val();i<=$("#filterToYear").val();i++){
							filterDurationVal.push(i);
						}
					}
				}
			}
		});
		var filterVal = [];
		filterVal[0] = filterAttorneyVal;
		filterVal[1] = filterTypeVal;
		filterVal[2] = filterStatusVal;
		filterVal[3] = filterDurationVal;
		getDynamicImmigrationList(filterVal);
	}
	function resetImmigrationFilter(){
		$('ul#typeFilter input:checkbox, ul#attorneyFilter input:checkbox, ul#statusFilter input:checkbox, ul#durationFilter input:checkbox ').attr('checked',false);
		$("#filterToYear, #filterFromYear").val("");
		$("#durationCustomsearch").hide();
	}
	
	function myClientsFunction(searchId, uiId) {
	    var input, filter, ul, li, a, i;
	    input = document.getElementById(searchId);
	    filter = input.value.toUpperCase();
	    ul = document.getElementById(uiId);
	    li = ul.getElementsByTagName("li");
	    for (i = 0; i < li.length; i++) {
	        a = li[i].getElementsByTagName("label")[0];
	        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
	            li[i].style.display = "";
	        } else {
	            li[i].style.display = "none";
	        }
	    }
	}
	function clearFilters(){
		$("#immiListSerach").val('');
		getDynamicImmigrationsList();
	}
</script>

<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	


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
