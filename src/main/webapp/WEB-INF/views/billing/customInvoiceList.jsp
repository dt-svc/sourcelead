<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content-wraper">

	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Invoice Templates</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="/app/dashboard/3">Home</a> &raquo; <a href="<c:url value='${appPath}/clientDashboard'/>"> Clients List </a>&raquo; Invoice Templates
		</div>
	</section>

	<div class="timesheet_out" id="templateTabContent">
		<div class="vendor_wrap">
			<div class="positionRelative reportHead clearfix">
				<ul class="pull-right list-unstyled list-inline">
					<li><a href="${appPath}/customInvoice/addTemplate?orgLocationId=${orgLocationId}&orgRelationId=${orgRelationId}" class="newAdBtn">+ Template</a></li>

					<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
						<div class="newActPop">
							<span>
								<a href="javascript:void(0)" onclick="exportToPdf(2);" title="PDF">Export PDF</a>
								<a href="javascript:void(0)" onclick="exportToExcel(2);" title="Excel">Export Excel</a>
							</span>
						</div>
					</li>

					<li class="">
						<input type="text" class="repSearchBar" placeholder='Search' id='templateSearch' />
						<span class="repSearchIcon" onClick="jobFairListCheck();"></span>
					</li>
				</ul>
			</div>

			<div class="require_wrap_table datatable_filter">
				<div id="templateList"></div>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript">
var orgRelationId = ${orgRelationId};
var orgLocationId = ${orgLocationId};

$(document).ready(function () {
	getInvoiceTemplateList();
});

function getInvoiceTemplateList() {
	var result = "";
	result += "<table id='templatePagination' class='display' cellspacing='0' width='100%' >"; 
 	result += "<thead>"; 
 	result += "	<tr>";
 	result += "		<th>Generated ID</th>";
 	result += "		<th>Title</th>";
 	result += "		<th>Description</th>";
 	result += "		<th>Action(s)</th>";
	result += "	</tr>"; 
 	result += "</thead>";
 	result += "<tbody>"; 
     
  	result += '<div class="new_side_wrap">';
	result += '<div class="new_side_wrap_inn">';
	result += '<div class="new_side_wrap_cta">';
	result += '<div id="addApplicantDiv"></div>';
	result += '</div>';
	result += '</div>';
	result += '</div>';
 	result += " </tbody>";
 	result += "</table>"; 
 	$("#templateList").append(result); 
 	var url = sourceleadUrl+"/invoiceTemplateList?orgLocationId=" + orgLocationId + "&orgRelationId=" + orgRelationId;
 	
 	var colIndex = 0;
    $('#templatePagination').DataTable( {
   		"processing": true,
       	"serverSide": true,
       	"searching": false,
       	"searchHighlight": true,
       	"ajax": $.fn.dataTable.pipeline( {
           url: url,
           method: 'POST',
           pages: 1
       	}),
       	"columns": [
    	   {"data": "uuid"},
           {"data": "name"},
           {"data": "description"},
           {"data": "actions" }
       	],
	    "columnDefs": [
    	   {
	            "targets": colIndex++,
	            "data": "uuid",
	            "render": function ( data, type, opt, meta ) {
	            	var result = "";
           		   if(opt.uuid != null && opt.uuid != '' && opt.uuid != 'undefined') {
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.uuid+'</div></td>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		           	return result;
	             }
	        },
	    	{
	           "targets": colIndex++,
	           "data": "name",
	           "render": function ( data, type, opt, meta ) {
	        	   $("#requirementEmptyListMessage").val(2);
	           		var result = "";
	       		    result = result +"<td><a href='<c:url value='/app/jobFairSummary?uuid="+opt.uuid+"'/>'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.name+"'>"+opt.name+"</span><i class='fa fa-binoculars'></i></div></a></td>";
		            return result;
	            }
            },
            {
	            "targets": colIndex++,
	            "data": "description",
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.description != null && opt.description != '' && opt.description != 'undefined') {
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.description+'</div></td>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		           		return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "actions",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            	var result = '<div class=" widthIcon widthIcon-three">';
	            	result +='	<div class="text-center">';
	            	result +='		<a href="' + sourceleadUrl + '/customInvoice/editTemplate?uuid=' + opt.uuid + '&orgLocationId=' + opt.orgLocationId + '&orgRelationId=' + opt.orgRelationId + '" title="View Template" onclick="" class="add-applicant">';
	            	result +='			<i class="fa fa-eye"></i>';
	            	result +='		</a>';
	            	result +='	</div>';
	            	result +='	<div class="text-center">';
	            	result +='		<a href="' + sourceleadUrl + '/customInvoice/editTemplate?uuid=' + opt.uuid + '&orgLocationId=' + opt.orgLocationId + '&orgRelationId=' + opt.orgRelationId + '" title="Edit Template" onclick="" class="add-applicant">';
	            	result +='			<i class="fa fa-edit"></i>';
	            	result +='		</a>';
	            	result +='	</div>';
	            	result +='	<div class="text-center">';
	            	result +='		<a href="javascript:void(0)" title="Delete Template" onclick="showChangeTemplateStatusPopup(' + opt.id + ',' + opt.orgLocationId + ',' + opt.orgRelationId + ')">';
	            	result +='		<i class="fa fa-trash-o"></i>';
	            	result +='		</a>';
	            	result +='	</div>';
	            	result +='</div>';
           	        return result;
	             }
	        }
           ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"createdRow": function ( row, data, index ) {
	    		row.setAttribute('id',"template_"+data.id);
    	    },
	    	"initComplete": function( settings, json ) {
	    		 $('#templateSearch').removeAttr('disabled');
	    	},
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
	    /*,
	     drawCallback: function(){
   	      $('a.paginate_button', this.api().table().container()).on('click', function(){
   	    	jobFairSearchHighlight();
   	      });
   	   		updateFairStatusEvents();
   	   	}*/
   });
   
  
   $("#templatePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   $("input[type=search]").hide();
   
   $("#templateSearch").keyup(function(e) {
   	   if(e.which === 13) {
   		jobFairListCheck();
   	   }
   	});
}

function showChangeTemplateStatusPopup(templateId, orgLocationId, orgRelationId) {
	ele = element;
	var statusText = "";
	if(status === "0") {
		statusText = "Are you sure to make the JobFair status to 'CLOSE' ?";
	} else if(status === "1") {
		statusText = "Are you sure to make the JobFair status to 'OPEN' ?";
	} else {
		statusText = "Are you sure to delete the JobFair ?";
	}
	$("#changeStatusPopup p").html(statusText);
	$("#confirmId").data("job_fair_id", jobFairId);
	$("#confirmId").data("status",status);
	$("#cancelStatusPopup").data("old_value", oldValue);
	$("#changeStatusPopup").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function closeChangeStatusPopup() {
	var oldValue = $("#cancelStatusPopup").data("old_value");
	$(ele).val(oldValue);
	$("#changeStatusPopup").slideUp(200);
	$(".project_inn_mask").fadeOut(200);
}

function changeJobFairStatus() {
	var jobFairId = $("#confirmId").data("job_fair_id");
	var status = $("#confirmId").data("status");
	var data = {
			jobFairId: jobFairId,
			status : status
	};
	$.post(sourceleadUrl + "/changeJobFairStatus?", data, function(result) {
		if (result.response === "SESSION_TIMEDOUT") {
			location.replace(sourceleadUrl);
		} else if (result.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (result.response === "SUCCESS") {
			location.href = sourceleadUrl + "/jobs?viewTab=jobFair";
		} else if (result.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}
</script>