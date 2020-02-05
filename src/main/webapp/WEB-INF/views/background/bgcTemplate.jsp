	
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
				<a class="newAdBtn mr5" title="Add Template" href="${SourceLeadWeb_vmsUrl}/bgc/addBgcTemplate">+ Add Template</a>	
					
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="DynamicTemplateListSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamicTemplateList();"></span></li>
            </ul>
          </div>
           <div id="dynamicBgcTemplateListViewDiv" class="project_wrap_table datatable_filter"></div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>
<jsp:include page="../masterFooter.jsp" />
<script>
var vmsUrl = '${SourceLeadWeb_vmsUrl}';
var sourceleadUrl = '${SourceLeadWeb_vmsUrl}';

$(document).ready(function() {
	getDynamicTemplateList();
})

$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	
</script>

<script>
getDynamicTemplateList = function() {
  var result = "";
   result = result + '<table id="DynamicTemplateListTbl">';
   result = result + '<thead>';
   result = result + '<tr>';
   result = result + '<th>Name</th>';
   result = result + '<th>Description</th>';
   result = result + '<th>Action</th>';
   result = result + '</tr>';
   result = result + '</thead>';
   
   result = result + '</table>';
   $("#dynamicBgcTemplateListViewDiv").empty();
   $("#dynamicBgcTemplateListViewDiv").append(result);

//   var statusFilter = $("#statusFilter").val();
   var searchValue = $("#DynamicTemplateListSearch").val();
   searchValue = searchValue ? searchValue : "";

    url = vmsUrl + "/bgc/getBgcDynamicTemplateList?searchValue=" + searchValue;
    DynamicTemplatePagination(url);
}
//var count = 0;
DynamicTemplatePagination = function(url) {
	 var colIndex = 0;
   table = $('#DynamicTemplateListTbl').DataTable({
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
                      result = result + '<td><i id="edit"  onclick=ediBgcTemplateById('+opt.id+'); class="glyphicon glyphicon-pencil"></i></a>';
               	 }
               	 if(roleActionsArray.indexOf("ROLE_DELETE_BACKGROUND_DETAILS") > -1){ 
                       result = result + '<i id="delete" onclick=bgc.deleteBgcRecord(bgc.deleteBgcTemplateById,'+opt.id+',"","'+encodeURITemplate(name)+'"); class="glyphicon glyphicon-trash ml10"></i></div></td>';
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
  
   $("#DynamicTemplateListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   
   $("#DynamicTemplateListSearch").keyup(function (e) {
       if (e.which == 13) {
    	   getDynamicTemplateList();
      
       }
   });

}

</script>
<script>
function  saveBgcTemplate() {
	if ($('#bgcTemplateFrm').validate(false, validationSettings)) {
		var formData = getFormData("bgcTemplateFrm");
		// below is for edit functionality of the status
		  $("#saveBgcTemplateBtn").text('Processing..'); 
          $("#saveBgcTemplateBtn").addClass("disabled");
		$.ajax({
			type : "POST",
			data : formData,
			url : vmsUrl + "/bgc/SaveOrUpdateBgcTemplate",
			success : function(result) {
				if (result.response == "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response == "SUCCESS") {
					if (result.object == "DataMissing") {
						$("#saveBgcTemplateBtn").removeClass("disabled");
						$("#saveBgcTemplateBtn").text('Save');
						showError("Invalid data");
					} else {
						location.replace(vmsUrl+"/bgc/bgcOrgTemplateList");	
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
function  ediBgcTemplateById(TemplateId) {
			$.ajax({
			type : "GET",
			url : vmsUrl + "/bgc/getBgcTemplateById?id="+TemplateId,
			success : function(data) {
				console.log(data);
				if (data) {
					$(".mask, #modalTasktype").fadeIn();
					$("#TemplateName").val(data.name);
					$("#id").val(data.id);
					$("textarea#description").val(data.description);
				} else {
					showError("Operation failed, please try again");
				}
			}

		});
	}
</script>
