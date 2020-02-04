	
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
    <div class="project_wrap clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
				<li>
				<a class="newAdBtn mr5" title="Add" id="addComponent">+ Add Component</a>	
					
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id=dynamicComponentListSearch type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div id="dynamicBgcComponentListViewDiv" class="project_wrap_table datatable_filter"></div>
    </div>
    </div>
  </section>
<jsp:include page="../masterFooter.jsp" />
<!-- popup code start here -->

<div class="mask"></div>
<div class="popupWrap" id="modalTasktype">
  <div class="popupHead"><span>Add Component</span><i class="closePop pull-right popClose"></i></div>
    <form id="bgcComponentFrm" name="bgcComonentFrm" action="" method="post">
    <div class="popupContent">
        <div class="clearfix mb">
            <label class="noPad">Component Name <b class="valStar">*</b></label>
            <div class="clearfix">
                <input class="form-control" placeholder="Name" type="text"id ="componentName" name="name" field-name="Name" data-validation="required" data-validation="required validate_length length2-100 validate_AlphaNumber">
            </div>
        </div>
        <div class="clearfix mb">
            <label class="noPad">Description<b class="valStar">*</b></label>
            <div class="clearfix">
      <textarea placeholder="Description" id="description" name="description" class="form-control" field-name="Description" rows="3" data-validation="required validate_length length2-500"></textarea>
            </div>
        </div>

    </div>
    
    <div class="popupFoot text-right">
      <button type="button" id="saveBgcComponentBtn" class="slBtn md colorBtn" onclick="saveBgcComponent();">Save</button>
      <button type="button" class="slBtn md popClose" onclick=""> Cancel</button>
    </div>
    
    <div>
</div></form>
</div>
<!--  pop up code ends here -->
<script>
var vmsUrl = '${SourceLeadWeb_vmsUrl}';
var sourceleadUrl = '${SourceLeadWeb_vmsUrl}';
$(document).ready(function() {
	 getDynamicComponentList();
})

	$("html").click(function() {
		$(".newActPop").slideUp(200);
	});
	$(".overIcon, .newActPop").click(function(e) {
		e.stopPropagation();
	});

	$(function() {
		$("#addComponent").click(function() {
			$(".mask, #modalTasktype").fadeIn();
			$('#bgcComponentFrm')[0].reset();
		});

		$(".popClose").click(function() {
			$(".mask, #modalTasktype").fadeOut();
		});
	});
</script>

<script>
getDynamicComponentList = function() {
  var result = "";
   result = result + '<table id="dynamicComponentListTbl">';
   result = result + '<thead>';
   result = result + '<tr>';
   result = result + '<th>Name</th>';
   result = result + '<th>Description</th>';
   result = result + '<th>Action</th>';
   result = result + '</tr>';
   result = result + '</thead>';
   
   result = result + '</table>';
   $("#dynamicBgcComponentListViewDiv").empty();
   $("#dynamicBgcComponentListViewDiv").append(result);

//   var statusFilter = $("#statusFilter").val();
   var searchValue = $("#dynamicComponentListSearch").val();
   searchValue = searchValue ? searchValue : "";

    url = vmsUrl + "/bgc/getBgcDynamicComponentList?searchValue=" + searchValue;
    dynamicComponentPagination(url);
}
//var count = 0;
dynamicComponentPagination = function(url) {
	 var colIndex = 0;
   table = $('#dynamicComponentListTbl').DataTable({
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
       	{"data": "name"},
        {"data": "description"}
       ],
       "columnDefs": [
       	{
               "targets": colIndex++,
               "data": "name",
               "render": function (data, type, opt, meta) {
                   var result = "";
                   if (opt.name !== null && opt.name !== '' && opt.name !== 'undefined') {
                       result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.name+"'>" + opt.name + "</div></td>";
                   } else {
                       result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
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
                      result = result + '<td><i id="edit"  onclick=ediBgcComponentById('+opt.id+'); class="glyphicon glyphicon-pencil"></i></a>';
               	 }
               	 if(roleActionsArray.indexOf("ROLE_DELETE_BACKGROUND_DETAILS") > -1){ 
                       result = result + '<i id="delete" onclick=bgc.deleteBgcRecord(bgc.deleteBgcComponentById,'+opt.id+',"","'+encodeURIComponent(name)+'"); class="glyphicon glyphicon-trash ml10"></i></div></td>';
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
  
   $("#dynamicComponentListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   
   $("#dynamicComponentListSearch").keyup(function (e) {
       if (e.which == 13) {
    	   getDynamicComponentList();
      
       }
   });

}

</script>
<script>
function  saveBgcComponent() {
	if ($('#bgcComponentFrm').validate(false, validationSettings)) {
		var formData = getFormData("bgcComponentFrm");
		// below is for edit functionality of the status
		  $("#saveBgcComponentBtn").text('Processing..'); 
          $("#saveBgcComponentBtn").addClass("disabled");
		$.ajax({
			type : "POST",
			data : formData,
			url : vmsUrl + "/bgc/SaveOrUpdateBgcComponent",
			success : function(result) {
				if (result.response == "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response == "SUCCESS") {
					if (result.object == "DataMissing") {
						$("#saveBgcComponentBtn").removeClass("disabled");
						$("#saveBgcComponentBtn").text('Save');
						showError("Invalid data");
					} else {
						location.replace(vmsUrl+"/bgc/bgcOrgComponentList");	
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
function  ediBgcComponentById(componentId) {
			$.ajax({
			type : "GET",
			url : vmsUrl + "/bgc/getBgcComponentById?id="+componentId,
			success : function(data) {
				console.log(data);
				if (data) {
					$(".mask, #modalTasktype").fadeIn();
					$("#componentName").val(data.name);
					$("#id").val(data.id);
					$("textarea#description").val(data.description);
				} else {
					showError("Operation failed, please try again");
				}
			}

		});
	}
</script>
