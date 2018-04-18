	
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
<script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script>
<!-- <script type="text/javascript" src="/resources/js/colResizable-1.6.min.js"></script> -->
<style type="text/css">
    .active {
        color: red !important;
    }

</style>
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
				<li class="clearfix" title="${dashboardResult.draftsCount}" >
					<span><i class="fa fa-plane"></i></span>
					<p>Draft <b>${dashboardResult.draftsCount}</b></p>
				</li>			
				<li class="clearfix" title="${dashboardResult.reqSentCount}">
					<span><i class="fa fa-plane"></i></span>
					<p> Sent Request <b>${dashboardResult.reqSentCount}</b></p>
				</li>
				<li class="clearfix" title="${dashboardResult.inProgressCount}">
					<span><i class="fa fa-plane"></i></span>
					<p> Inprogress <b>${dashboardResult.inProgressCount}</b></p>
				</li>
				<li class="clearfix" title="${dashboardResult.apporovedCount}">
					<span><i class="fa fa-plane"></i></span>
					<p>Approved <b>${dashboardResult.apporovedCount}</b></p>
				</li>
				<li class="clearfix" title="${dashboardResult.rejectedCount}">
					<span><i class="fa fa-plane"></i></span>
					<p>Rejected <b>${dashboardResult.rejectedCount}</b></p>
				</li>
				<li class="clearfix"title="${dashboardResult.othersCount}">
					<span><i class="fa fa-plane"></i></span>
					<p>Others <b>${dashboardResult.othersCount}</b></p>
				</li>				
			</ul>
		</div>
   
		<div class="immigraInnWrap">
			<div class="timesheet_head">
			  <ul>
			    <sec:authorize access="hasAnyRole('ROLE_ADD_PETITION','ROLE_EDIT_VIEW_PETITION','ROLE_DELETE_PETITION')">
				  <li>
					  <a href="${appPath}/immigration/loadNewPetition"><i class="fa fa-plus paddingRight"></i> New </a>
				  </li>
                 </sec:authorize>
				  <li class="dropdown">
					<span class="dropdown-toggle" id="menu1" data-toggle="dropdown">
						<i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i></span>|

					<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">
					<ul>
					  <li>
						<input value="ch1" name="resourcerequest" id="ch1" type="checkbox">
						<label for="ch1">Draft</label>
					  </li>
					  <li>
						<input id="ch2" name="resourcerequest" type="checkbox">
						<label for="ch2">New</label>
					  </li>
					  <li>
						<input id="ch3" name="resourcerequest" type="checkbox">
						<label for="ch3">Inprogress</label>
					  </li>
					  <li>
						<input id="ch4" name="resourcerequest" type="checkbox">
						<label for="ch4">Accepted</label>
					  </li>
					  <li>
						<input id="ch5" name="resourcerequest" type="checkbox">
						<label for="ch5">Rejected</label>
					  </li>					  					  
					</ul>
				  </div>
				</li>

				  <li class="dropdown">
					<span class="dropdown-toggle" id="" data-toggle="dropdown">
						<i class="fa fa-filter"></i><span>Attorney</span><i class="glyphicon glyphicon-play"></i></span>|

					<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">
					<ul>
					  <li>
						<input value="ch6" name="resourcerequest" id="ch6" type="checkbox">
						<label for="ch6">Attorney 1</label>
					  </li>
					  <li>
						<input id="ch7" name="resourcerequest" type="checkbox">
						<label for="ch7">Attorney 2</label>
					  </li>
					  <li>
						<input id="ch8" name="resourcerequest" type="checkbox">
						<label for="ch8">Attorney 3</label>
					  </li>
					</ul>
				  </div>
				</li>

				  <li class="dropdown">
					<span class="dropdown-toggle" id="" data-toggle="dropdown">
						<i class="fa fa-filter"></i><span>Type</span><i class="glyphicon glyphicon-play"></i></span>|

					<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">
					<ul>
					  <li>
						<input value="ch11" name="resourcerequest" id="ch11" type="checkbox">
						<label for="ch11">H1B</label>
					  </li>
					  <li>
						<input id="ch12" name="resourcerequest" type="checkbox">
						<label for="ch12">H2B</label>
					  </li>
					  <li>
						<input id="ch13" name="resourcerequest" type="checkbox">
						<label for="ch13">Green card</label>
					  </li>
					</ul>
				  </div>
				  <li class="pull-left">
                    <div class="dropdown">
                        <strong>Column Chooser </strong>
                        <button class="glyphicon glyphicon-plus" type="button" id="menu1" data-toggle="dropdown"></button>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
                            <li><a class="toggle-vis " data-column="0">Status</a></li>
                            <li><a class="toggle-vis" data-column="1">ID</a></li>
                            <li><a class="toggle-vis" data-column="2">Type</a></li>
                            <li><a class="toggle-vis" data-column="3">Name</a></li>
                            <li><a class="toggle-vis" data-column="4">Email</a></li>
                            <li><a class="toggle-vis" data-column="6">Phone</a></li>
                            <li><a class="toggle-vis" data-column="7">On Date</a></li>
                             <li><a class="toggle-vis" data-column="8">Attorney</a></li>
                        </ul>
                    </div>
                </li>
				
				  <li class="dropdown">
					<span class="dropdown-toggle" id="" data-toggle="dropdown">
						<i class="fa fa-filter"></i><span>Duration</span><i class="glyphicon glyphicon-play"></i></span>|

					<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">
					<ul>
					  <li>
						<input value="ch15" name="resourcerequest" id="ch15" type="checkbox">
						<label for="ch15">This month</label>
					  </li>
					  <li>
						<input id="ch16" name="resourcerequest" type="checkbox">
						<label for="ch16">This year</label>
					  </li>
					  <li>
						<input id="ch17" name="resourcerequest" type="checkbox">
						<label for="ch17">Last year</label>
					  </li>
					  <li>
						<input id="ch18" name="resourcerequest" type="checkbox">
						<label for="ch18">Custom</label>
					  </li>					  
					</ul>
				  </div>
				</li> 				                                              

				<div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
			  <div class="remove_search">
				<input placeholder="Search"  id ="immiListSerach" type="text">
			  </div>
			</div>
			
			 <div id ="dynamicImmigrationListViewDiv" class="project_wrap_table datatable_filter"></div>
		
		</div>   
   
    </div>
  </section>
  <!-- Content Area -->
  
  <div class="theme-color"> <span class="icon-theme" title="click here"><i class="fa fa-calendar-check-o"></i></span>
    <ul class="list-unstyled">
      <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
      <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
      <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
    </ul>
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
        $(document).ready(function(){
        $("#cd").click(function(){
        $(".clientDetails").toggleClass('hidden', 500);
        });

        });
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
 
 
 
 
 var table = "";
 $(document).ready(function () {
     getDynamicImmigrationList();
     /* getUscisStatusList(); */
     $('a.toggle-vis').on('click', function (e) {
         e.preventDefault();
         // Get the column API object
         var column = table.column($(this).attr('data-column'));
         $(this).toggleClass("active");
         // Toggle the visibility
         column.visible(!column.visible());
     });
     $("#statusFilter a").on('click', function (e) {
         alert($(this).data("status"));
         getDynamicImmigrationList($(this).data("status"));
     }); 
 });

 function getDynamicImmigrationList(status) {

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
     result = result + '<th>Attorney</th>';
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
     var url = sourceleadUrl + "/immigration/getImmigrationViewList?searchValue=" + searchValue + "&status=" + (status ? status : 1);
     dynamicImmigrationListPagination(url);
 }
 //var count = 0;
 function dynamicImmigrationListPagination(url) {
	 var colIndex = 0;
     table = $('#dynamicImmigrationListTbl').DataTable({
         "processing": true,
         "serverSide": true,
         "searching": false,
         "colReorder": true,
     	 'dom': 'Rlfrtip',
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
                    	 result = result + "<td><div class='mt5 mb5' style='width:150px;'><select id='currUscisStatus' title='"+opt.currUscisStatus+"' onchange='changeSaveUscisStatus($(this),"+opt.id+");' name='currUscisStatus' class='form-control'>";
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
                         result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.orgName+"'>" + opt.orgName + "</div></td>";
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
                     var result = "";
                     result = result + '<td><div><i id="edit' + opt.id + '" onclick="immigration.editImmigrationDetails(' + opt.id + ');" class="glyphicon glyphicon-pencil"></i><i id="delete' + opt.id + '" onclick="immigration.deleteTempalteDetailsByRowId(' + opt.id + ');" class="glyphicon glyphicon-trash ml10"></i></div></td>';
                     return result;
                 }
             }
         ],
         "scrollCollapse": false,
         "lengthMenu": [[10, 15, 20], [10, 15, 20]],
         "aaSorting": [[6, 'desc']],
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
        	 getDynamicImmigrationList();
         }
     });

    // $("table").colResizable(); // for resizing columns in tables.
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

