	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/resources/js/immigration.js"></script>
<script type="text/javascript" src="/resources/js/jquery.dataTables.min.js"></script>
<!-- <script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script> -->
<!-- <script type="text/javascript" src="/resources/js/colResizable-1.6.min.js"></script> -->
<c:set var ="myImmigrationPage" value = "N" scope="session"/>
<title>Insert title here</title>
    <section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix addImmiHead">
      <div class="pull-left"><h2>Immigration list</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Immigrations List</div>
	<div class="clearfix"></div>
    </section>    
    
    <div class="immigrationWrap">
		<div class="immigraCountHead">
			<ul class="clearfix">
				<li class="clearfix" title="${!empty dashboardResult.draftsCount ? dashboardResult.draftsCount : 0}" >
					<span><i class="fa fa-plane"></i></span>
					<p>Draft <b>${!empty dashboardResult.draftsCount ? dashboardResult.draftsCount : 0}</b></p>
				</li>			
				<li class="clearfix" title="${!empty dashboardResult.reqSentCount ? dashboardResult.reqSentCount : 0}">
					<span><i class="fa fa-plane"></i></span>
					<p> Sent Request <b>${!empty dashboardResult.reqSentCount ? dashboardResult.reqSentCount : 0}</b></p>
				</li>
				<li class="clearfix" title="${!empty dashboardResult.inProgressCount ? dashboardResult.inProgressCount : 0}">
					<span><i class="fa fa-plane"></i></span>
					<p> Inprogress <b>${!empty dashboardResult.inProgressCount ? dashboardResult.inProgressCount : 0}</b></p>
				</li>
				<li class="clearfix" title="${!empty dashboardResult.apporovedCount ? dashboardResult.apporovedCount : 0}">
					<span><i class="fa fa-plane"></i></span>
					<p>Approved <b>${!empty dashboardResult.apporovedCount ? dashboardResult.apporovedCount : 0}</b></p>
				</li>
				<li class="clearfix" title="${!empty dashboardResult.rejectedCount ? dashboardResult.rejectedCount : 0}">
					<span><i class="fa fa-plane"></i></span>
					<p>Rejected <b>${!empty dashboardResult.rejectedCount ? dashboardResult.rejectedCount : 0}</b></p>
				</li>
				<li class="clearfix"title="${!empty dashboardResult.othersCount ? dashboardResult.othersCount : 0}">
					<span><i class="fa fa-plane"></i></span>
					<p>Others <b>${!empty dashboardResult.othersCount ? dashboardResult.othersCount : 0}</b></p>
				</li>				
			</ul>
		</div>
   
		<div class="immigraInnWrap">
			<div class="timesheet_head clearfix">
			
			<div class="pull-left colCho">
				<a href="javascript:void(0)" onclick="columPop();" title="Column Chooser" class="btn btn-sm btn-default"><i class="fa fa-table"></i></a>
			</div>
			
			<div class="filterSearchDiv pull-left colCho">
				<a href="javascript:void(0)" onclick="filterPop();" title="Filters" class="btn btn-sm btn-default"><i class="fa fa-filter"></i></a>
<div class="colPop">
	<div class="colPopMid">
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
	<div class="colPopFot">
		<a href="javascript:void(0)" onclick="columHide();">Apply</a>
		<a href="javascript:void(0)" onclick="columClose();">Cancel</a>
	</div>
</div>				
			</div>
			</div>
			  <ul class="pull-right">
			    <sec:authorize access="hasAnyRole('ROLE_ADD_PETITION','ROLE_EDIT_VIEW_PETITION','ROLE_DELETE_PETITION')">
				  <li class="noPad">
					  <a href="${appPath}/immigration/loadNewPetition" class="btn btn-success btnSuccess anchorWhite btn-sm"><i class="fa fa-plus paddingRight"></i> New </a>
				  </li>
                 </sec:authorize>
			  </ul>
			  <div class="remove_search">
				<input placeholder="Search"  id ="immiListSerach" type="text">
			  </div>
			
			</div> 
			  
			 <div id="dynamicImmigrationListViewDiv" class="project_wrap_table datatable_filter"></div>
			 
    </div>
  </section>
  <!-- Content Area -->
</div>
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
										<input type="text" id="attorneyFilterInput"  onkeyup="myClientsFunction('attorneyFilterInput','attorneyFilter');" placeholder="Attorney" title="Type in a managers">
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

    	$('#filterFromYear').append($('<option />').val("").html("--Select--"));
        for (i = new Date().getFullYear(); i > 1900; i--)
        {
            $('#filterFromYear').append($('<option />').val(i).html(i));
        } 
        });
        
        function getFilterTwoYear(selectedFrmYear){
        	$('#filterToYear').empty();
        	$('#filterToYear').append($('<option />').val("").html("--Select--"));
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

 function getDynamicImmigrationList(filterVal) {
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
     if('${SourceLeadWeb_orgLocations.isLawFirm}' == 'Y' && '${SourceLeadWeb_orgLocations.isAccountingFirm}' == 'Y'){
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

     var sourceleadUrl = '${sourceleadUrl}';
//     var statusFilter = $("#statusFilter").val();
     var searchValue = $("#immiListSerach").val();
     searchValue = searchValue ? searchValue : "";
     var url = sourceleadUrl + "/immigration/getImmigrationViewList?searchValue=" + searchValue+"&filterAttorneyVal="+filterAttorneyVal+"&filterTypeVal="+filterTypeVal+"&filterStatusVal="+filterStatusVal+"&filterDurationVal="+filterDurationVal+"&orgTypeVal="+orgTypeVal;
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
                    	 result = result + "<td><div class='mt5 mb5 dropdown-select' style='width:150px;'><select id='currUscisStatus' title='"+opt.currUscisStatus+"' onchange='changeSaveUscisStatus($(this),"+opt.id+");' name='currUscisStatus' class='form-control'>";
                    	 //alert(opt.currUscisStatusId);
                    	 for(var i=0;i<opt.currUsicisStatusList.length;i++){
                        	data = opt.currUsicisStatusList[i];
                        	if(data.id == opt.currUscisStatusId){
                        		//alert("inside selected");
                        		result = result + "<option selected value="+data.id+">"+data.name+"</option>";
                        	}else{
                        		result = result + "<option value="+data.id+">"+data.name+"</option>";
                        	}
                    	 }
                         result = result + "</select></div></td>";
                         $('#currUscisStatus').trigger("chosen:updated");
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
                         result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.generatedId+"'><a href=/app/immigration/viewImmigration?immigrationId="+opt.id+"&candidateId="+opt.candidateId+"&templateId="+opt.immTemplateId+">"+opt.generatedId+"</a></div></td>";
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
                     result = result + '<td><div><a href=/app/immigration/editImmigrationDetails?immigrationId='+opt.id+'><i id="edit' + opt.id + '" class="glyphicon glyphicon-pencil"></i></a><i id="delete' + opt.id + '" onclick="deleteImmigrationListId(' + opt.id + ');" class="glyphicon glyphicon-trash ml10"></i></div></td>';
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
 
 function deleteImmigrationListId(immigrationId){ // only stauts updated with reason. active 1 , deactive 0.
		$(".project_inn_mask").remove();
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a'>";
		/* result = result +"		<  id='reasonForTemplate' name='reasonForSpoc' data-validation='required validate_Space validate_Description validate_length length2-250' placeholder='Reason' onkeypress=';' ></textarea>"; */
		result = result +" <b>Do you want to remove record permanently?</b>";
		result = result + ' <div id="reasonForTemplateDiv"/>';
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='immigration.deleteImmigrationDetailsByRowId("+immigrationId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelImmigrationDelete();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_immigration_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
 
 function changeSaveUscisStatus(idVal,templateId){
	 var uscisId = idVal.val();
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/immigration/updateUscisTypeDropDown?templateId="+templateId+"&uscisId="+uscisId, 
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
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+immiModulePath+"/getPetitionTypeDropDown", 
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
			 $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getVendorsByOrgLocation?", 
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
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+immiModulePath+"/getUscisTypeDropDown", 
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
</script>

