<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content-wraper">

	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Invoice Templates</h2>
		</div>
		<div class="pull-right brud-crum">

			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_vmsUrl}/clientDashboard"> Clients List </a>&raquo; Invoice Templates

		</div>
	</section>

	<div class="timesheet_out" id="templateTabContent">
		<div class="vendor_wrap">
			<div class="positionRelative reportHead clearfix">
				<ul class="pull-right list-unstyled list-inline">
					<li><a href="${SourceLeadWeb_vmsUrl}/customInvoice/addTemplate?orgLocationId=${orgLocationId}&orgRelationId=${orgRelationId}" class="newAdBtn">+ Template</a></li>
					<%-- 
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
					--%>
				</ul>
			</div>

			<div class="require_wrap_table datatable_filter">
				<div id="templateList"></div>
			</div>
		</div>
	</div>
</section>

<div class='project_inn_mask'></div>

<div id='changeStatusPopup' class='remove_user_pop remove_user_pop_a'>
	<p>Are are sure you want to delete the Template?</p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmId' type='button' value='Ok' onclick='changeTemplateStatus()' class="pull-left">
		<input id="cancelStatusPopup" class='u_can_color' type='button' value='Cancel' onClick='closeChangeStatusPopup()'>
	</b>
</div>

<div class="success_pop" style="display: none;">
	<i class="fa fa-check-circle"></i><span>Invoice Template Saved</span>
</div>

<script type="text/javascript">
var orgRelationId = ${orgRelationId};
var orgLocationId = ${orgLocationId};

$(document).ready(function () {
	getInvoiceTemplateList();
});

function getInvoiceTemplateList() {
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var result = "";
	result += "<table id='templatePagination' class='display' cellspacing='0' width='100%' >"; 
 	result += "<thead>"; 
 	result += "	<tr>";
 	result += "		<th>Generated ID</th>";
 	result += "		<th>Title</th>";
 	result += "		<th>Description</th>";
 	result += "		<th>Status</th>";
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
 	var url = vmsUrl+"/invoiceTemplateList?orgLocationId=" + orgLocationId + "&orgRelationId=" + orgRelationId;
 	
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
           {"data": "status"},
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
	       		    result = result +"<td><a href='" + vmsUrl + "/customInvoice/editTemplate?uuid="+ opt.uuid +"&orgLocationId="+ opt.orgLocationId + "&orgRelationId="+ opt.orgRelationId +"'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.name+"'>"+opt.name+"</span><i class='fa fa-binoculars'></i></div></a></td>";
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
	            "data": "status",
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
	            		result += '<select class="templateStatus">';
	     	        	result += '<option data-id='+opt.id+' value="1" ' + (opt.status == 1?'selected=""':'') + '>ACTIVE</option>';
	     	        	result += '<option data-id='+opt.id+' value="0" ' + (opt.status == 0?'selected=""':'') + '>INACTIVE</option>';
	     	        	result += '</select>';
		           		return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "actions",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            	var result = '<div class=" widthIcon widthIcon-three">';
	            	/* result +='	<div class="text-center">';
	            	result +='		<a href="' + sourceleadUrl + '/customInvoice/editTemplate?uuid=' + opt.uuid + '&orgLocationId=' + opt.orgLocationId + '&orgRelationId=' + opt.orgRelationId + '" title="View Template" onclick="" class="add-applicant">';
	            	result +='			<i class="fa fa-eye"></i>';
	            	result +='		</a>';
	            	result +='	</div>'; */
	            	result +='	<div class="text-center">';
	            	result +='		<a href="' + vmsUrl + '/customInvoice/editTemplate?uuid=' + opt.uuid + '&orgLocationId=' + opt.orgLocationId + '&orgRelationId=' + opt.orgRelationId + '" title="Edit Template" onclick="" class="add-applicant">';
	            	result +='			<i class="fa fa-edit"></i>';
	            	result +='		</a>';
	            	result +='	</div>';
	            	result +='	<div class="text-center">';
	            	result +='		<a href="javascript:void(0)" title="Delete Template" onclick="showChangeTemplateStatusPopup(' + opt.id + ', -1)">';
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
	     },
	     drawCallback: function(){
   	      /* $('a.paginate_button', this.api().table().container()).on('click', function(){
   	    	jobFairSearchHighlight();
   	      }); */
   	   		updateTemplateStatusEvents();
   	   	}
   });
   
  
   $("#templatePagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   $("input[type=search]").hide();
   
   $("#templateSearch").keyup(function(e) {
   	   if(e.which === 13) {
   		jobFairListCheck();
   	   }
   	});
}

function updateTemplateStatusEvents() {
	$(".templateStatus").each(function() {
		$(this).on('focusin', function(){
		    $(this).data('prev_value', $(this).val());
		}).on('change', function(){
			var templateId = $(this).find("option:selected").data("id");
		    var prev = $(this).data("prev_value");
		    showChangeTemplateStatusPopup(templateId, $(this).val(), prev, $(this));
		});
	});
}

var ele;
function showChangeTemplateStatusPopup(templateId, status, oldValue, element) {
	ele = element;
	var statusText = "";
	if(status === "0") {
		statusText = "Are you sure to make the Template Status as Inactive ?";
	} else if(status === "1") {
		statusText = "Are you sure to make the Template Status as Active ?";
	} else {
		statusText = "Are you sure to delete the Template ?";
	}
	$("#changeStatusPopup p").html(statusText);
	$("#confirmId").data("template_id", templateId);
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

function changeTemplateStatus() {
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var templateId = $("#confirmId").data("template_id");
	var status = $("#confirmId").data("status");
	var data = {
			templateId: templateId,
			status : status
		};
	$.post(vmsUrl + "/changeCustomInvoiceTemplateStatus?orgLocationId=" + orgLocationId + "&orgRelationId=" + orgRelationId, data, function(result) {
		if (result.response === "SESSION_TIMEDOUT") {
			location.replace(sourceleadUrl);
		} else if (result.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (result.response === "SUCCESS") {
			closeChangeStatusPopup();
			$(".success_pop").css("display","block");
    		$(".success_pop span").html("Invoice Template " + (status == 1 ? "made as Active" : status == 0 ? "made as Inactive" : "Deleted"));
			setTimeout(function() {
    			location.reload();					 			
	 		},1000);
		} else if (result.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}
</script>