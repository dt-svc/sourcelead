	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/resources/js/immigration.js?staticTime=${staticTime}"></script>
<script type="text/javascript" src="/resources/js/jquery.dataTables.min.js"></script>
<!-- <script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script> -->
<!-- <script type="text/javascript" src="/resources/js/colResizable-1.6.min.js"></script> -->
 <c:set var ="myImmigrationPage" value = "Y" scope="session"/> 
<title>Insert title here</title>
    <section class="content-wraper">
   
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Petition List</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; My Immigrations List</div>
	<div class="clearfix"></div>
    </section>    
    
    <div class="project_wrap">
    <div class="immigrationWrap noPad">
		<div class="immigraInnWrap">
		
		
<div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon">
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
					<input id="j" class="checkbox-custom toggle-vis" name="paidComponent" data-column="8"  type="checkbox" checked="">
					<label for="j" class="checkbox-custom-label" >LCA Start Date</label>
				</span>
			</li>
			<li>
				<span class="checkbox-new clearfix">
					<input id="k" class="checkbox-custom toggle-vis" name="paidComponent" type="checkbox"  data-column="9" checked="">
					<label for="k" class="checkbox-custom-label" >LCA End Date</label>
				</span>
			</li>
		</ul>
		</div>
	</div>
	<div class="colPopFot">
		<a href="javascript:void(0)" onclick="columHide();">Apply</a>
		<a href="javascript:void(0)" onclick="columClose();">Cancel</a>
	</div>
</div>				
			</div>     		
      		
      </span>
      <ul class="pull-right list-unstyled list-inline">
      		<li class="unVisable"><input type="text" class="repSearchBar" placeholder='Search' id='immiListSerach'/><span class="repSearchIcon" onClick=""></span></li>
      </ul>
      </div>		
			
			 <div id="dynamicImmigrationListViewDiv" class="project_wrap_table datatable_filter"></div>
			 
    </div>
  </div>
   </div>
  </section>
  <!-- Content Area -->

<div id="popUp"></div>




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
 
 
 
 var table = "";
 $(document).ready(function () {
     getDynamicImmigrationList(null);
  
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
     result = result + '<th>LCA Start Date</th>';
     result = result + '<th>LCA End Date</th>';
     result = result + '</tr>';
     result = result + '</thead>';

     result = result + '</table>';
     $("#dynamicImmigrationListViewDiv").empty();
     $("#dynamicImmigrationListViewDiv").append(result);

     var sourceleadUrl = '${sourceleadUrl}';
//     var statusFilter = $("#statusFilter").val();
     var url = sourceleadUrl + "/immigration/getMyImmigrationList";
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
             {"data": "LCA Start Date"},
             {"data": "LCA End Date"}
         ],
         "columnDefs": [
        	 {
                 "targets": colIndex++,
                 "data": "currUscisStatus",
                 "render": function (data, type, opt, meta) {
                     var result = "";
                     if (opt.currUscisStatus !== null && opt.currUscisStatus !== '' && opt.currUscisStatus !== 'undefined') {
                         result = result + "<td><div class='tbldata-dotdot-sm'>" + opt.currUscisStatus + "</div></td>";
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
                         result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.generatedId+"'><a href=/app/immigration/viewImmigration?immigrationUUID="+opt.immigrationUUID+"&candidateUUID="+opt.candidateUUID+"&immiTemplateUUID="+opt.immiTemplateUUID+"&myImmigrationPage=Y>"+opt.generatedId+"</a></div></td>";
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


    // $("table").colResizable(); // for resizing columns in tables.
 }
 


</script>

<script type="text/javascript">


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
		columClose();
		$(".toggle-vis").each(function() {
		   var chkdVal= $(this).prop('checked');
		   var column = table.column($(this).attr('data-column'));
			  if(chkdVal == true){
			         column.visible(true);
			    }
			    else{
			      column.visible(false);
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
	
	
	
</script>

