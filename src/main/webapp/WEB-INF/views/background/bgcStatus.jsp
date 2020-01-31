	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<script src="${SourceLeadWeb_vmsUrl}/resources/js/bgcCandidate.js?staticTime=${staticTime}"></script>
   <section class="content-wraper">
      <section class="content-header clearfix">
        <div class="pull-left">
          <h2>Background Verification</h2>
        </div>
        <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a>&raquo;  
         Background Verification</div>
      </section>
      <!-- Content Area -->
    <div class="project_wrap clearfix">
    <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
				<li>
				<a class="newAdBtn mr5" title="Add" id="addStatus">+ Add Status</a>	
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicStatusListSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
       <div id="dynamicBgcStatusListViewDiv" class="project_wrap_table datatable_filter"></div>
      </div>
      <!-- Content Area Ends--> 
    </div>
        
        <!-- Content Area Ends--> 
        <div id="popUp"></div>
      </div>
    </section>
  <!-- Content Area -->
<jsp:include page="../masterFooter.jsp" />

<!--  POPUP code starts here -->
<div class="mask"></div>
<div class="popupWrap" id="modalTasktype">
  <div class="popupHead"><span>Add Status</span><i class="closePop pull-right popClose"></i></div>
    <form id="bgcStatusFrm" name="bgcStatusFrm" action="" method="post">
    <div class="popupContent">
        <div class="clearfix mb">
         <input type="hidden" id ="id" name ="id" val=""/>
            <label class="noPad">Name <b class="valStar">*</b></label>
            <div class="clearfix">
                <input class="form-control" placeholder="Name" type="text"  id ="statusName" name="statusName" field-name="Name" data-validation="required" data-validation="required validate_length length2-100 validate_AlphaNumber">
            </div>
        </div>
        <div class="clearfix mb">
            <label class="noPad">Color <b class="valStar">*</b></label>
            <div class="clearfix">
                <div class="input-group">
                    <input id="favcolor" type="text" class="form-control valid" readonly="" id="color" name="color" placeholder="Color Pic" field-name="Color" data-validation="required">
                    <span class="input-group-addon" style="padding: 2px 10px;">
                    <input type="color" id="favcolorP" style="width: 20px; height: 20px;" onchange="colorPicker();">
                    </span> 
                  </div>
            </div>
        </div>
        <div class="clearfix mb">
            <label class="noPad">Description</label>
            <div class="clearfix">
      <textarea placeholder="Description" id="description" name="description" class="form-control" field-name="Description" rows="3" data-validation="required validate_length length2-500"></textarea>
            </div>
        </div>

    </div>
    
    <div class="popupFoot text-right">
      <button type="button" id="saveBgcStatusBtn" class="slBtn md colorBtn" onclick="saveBgcStatus();">Save</button>
      <button type="button" class="slBtn md popClose" onclick=""> Cancel</button>
    </div>
    
    <div>
</div></form>
</div>
<!--  POPUP code ENDS here -->
<script>
var vmsUrl = '${SourceLeadWeb_vmsUrl}';
var sourceleadUrl = '${SourceLeadWeb_vmsUrl}';
$(document).ready(function() {
	 getDynamicStatusList();
})

$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	
</script>

<script>
getDynamicStatusList = function() {
  var result = "";
   result = result + '<table id="dynamicStatusListTbl">';
   result = result + '<thead>';
   result = result + '<tr>';
   result = result + '<th>Status</th>';
   result = result + '<th>Color</th>';
   result = result + '<th>Description</th>';
   result = result + '<th>Action</th>';
   result = result + '</tr>';
   result = result + '</thead>';
   
   result = result + '</table>';
   $("#dynamicBgcStatusListViewDiv").empty();
   $("#dynamicBgcStatusListViewDiv").append(result);

//   var statusFilter = $("#statusFilter").val();
   var searchValue = $("#dynamicStatusListSearch").val();
   searchValue = searchValue ? searchValue : "";

    url = vmsUrl + "/bgc/getBgcDynamicStatusList?searchValue=" + searchValue;
    dynamicStatusPagination(url);
}
//var count = 0;
dynamicStatusPagination = function(url) {
	 var colIndex = 0;
   table = $('#dynamicStatusListTbl').DataTable({
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
       	{"data": "statusName"},
           {"data": "color"},
           {"data": "description"}
       ],
       "columnDefs": [
       	{
               "targets": colIndex++,
               "data": "statusName",
               "render": function (data, type, opt, meta) {
                   var result = "";
                   if (opt.statusName !== null && opt.statusName !== '' && opt.statusName !== 'undefined') {
                       result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.statusName+"'>" + opt.statusName + "</div></td>";
                   } else {
                       result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                   }
                   return result;
               }
           },
           
           {
               "targets": colIndex++,
               "data": "color",
               "render": function (data, type, opt, meta) {
                   var result = "";
                   if (opt.color !== null && opt.color !== '' && opt.color !== 'undefined') {
                  	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.color +"'>"+opt.color+"</div></td>";
                   } else {
                       result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
                   }
                   return result;
               }
           },
           {
               "targets": colIndex++,
               "data": "description",
               "render": function (data, type, opt, meta) {
                   var result = "";
                   if (opt.description !== null && opt.description !== '' && opt.description !== 'undefined') {
                   	 if(roleActionsArray.indexOf("ROLE_VIEW_BACKGROUND_DETAILS") > -1){ 
                   		 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.description +"'>"+opt.description+"</div></td>";
                   	 }
                   } else {
                       result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                   }
                 
                   return result;
               }
           },
           
           {
               "targets": colIndex++,
               /*"data": "actions",*/
               "render": function (data, type, opt, meta) {
                   var result = "";
               	 if(roleActionsArray.indexOf("ROLE_EDIT_BACKGROUND_DETAILS") > -1){ 
                      result = result + '<td><i id="edit"  onclick=ediBgcStatusById('+opt.id+'); class="glyphicon glyphicon-pencil"></i></a>';
               	 }
               	 if(roleActionsArray.indexOf("ROLE_DELETE_BACKGROUND_DETAILS") > -1){ 
                       result = result + '<i id="delete" onclick=bgc.deleteBgcRecord(bgc.deleteBgcStatusById,'+opt.id+',"","'+opt.statusName+'"); class="glyphicon glyphicon-trash ml10"></i></div></td>';
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
       },
      
   });
  
   $("#dynamicStatusListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   
   $("#dynamicStatusListSearch").keyup(function (e) {
       if (e.which == 13) {
    	   getDynamicStatusList();
      
       }
   });

}

</script>
<script type="text/javascript">
$(function(){
  $("#addStatus").click(function() {
    $(".mask, #modalTasktype").fadeIn();
  });
  $(".popClose").click(function() {
    $(".mask, #modalTasktype").fadeOut();
  });
});
function colorPicker() {
	var favColor = document.getElementById("favcolorP").value;
	document.getElementById("favcolor").value = favColor;
  document.getElementById("favcolorP").style.background = favColor;
}
function saveInviteCompany() {
 let favColor = $("#favcolor").val();
  $(".mask, #modalTasktype").fadeOut();
  document.getElementById("coloredDiv").innerHTML = favColor;
}
</script>
<script>
function  saveBgcStatus() {
	if ($('#bgcStatusFrm').validate(false, validationSettings)) {
		var formData = getFormData("bgcStatusFrm");
		// below is for edit functionality of the status
		  $("#saveBgcStatusBtn").text('Processing..'); 
          $("#saveBgcStatusBtn").addClass("disabled");
		$.ajax({
			type : "POST",
			data : formData,
			url : vmsUrl + "/bgc/SaveOrUpdateBgcStatus",
			success : function(result) {
				if (result.response == "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response == "SUCCESS") {
					if (result.object == "DataMissing") {
						$("#saveBgcStatusBtn").removeClass("disabled");
						$("#saveBgcStatusBtn").text('Save');
						showError("Invalid data");
					} else {
						location.replace(vmsUrl+"/bgc/bgcOrgStatusList");	
					}
				} else if (result.indexOf('Sign In') != -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}

			}
		});
	}
}
function  ediBgcStatusById(statusId) {
			$.ajax({
			type : "GET",
			url : vmsUrl + "/bgc/getBgcStatusById?id="+statusId,
			success : function(data) {
				console.log(data);
				if (data) {
					$(".mask, #modalTasktype").fadeIn();
					$("#statusName").val(data.statusName);
					$("#id").val(data.id);
					$("#favcolor").val(data.color);
					$("textarea#description").val(data.description);
				} else {
					showError("Operation failed, please try again");
				}
			}

		});
	}
</script>