<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<jsp:include page="../ticketManagement/ticketPopups.jsp" flush="true"></jsp:include>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="/resources/js/ticketManagement/ticket.js"></script>

<script>
function resetFilter(){
	$("#ticketListSearch").val('');
	getTicketManagementList();
}
</script>
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Ticket Management</h2>
      </div>
	<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Ticket List</div>
    </section>
    <div class="content-area clearfix timesheet_out">
          <div class="positionRelative reportHead clearfix">
          <span class="pull-left refreIcon">
           <a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a>
          <div></div></span>
            <ul class="pull-right list-unstyled list-inline">
            <sec:authorize access="hasRole('ROLE_ADD_TICKET')">
              <li><a href="/app/addTicket" class="newAdBtn mr5" title="Add" id="addPop">+ Add</a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_VIEW_TICKET')">
			  <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
							<div class="newActPop" style="display: none;">
								<span>
										<a href="javascript:void(0)" onclick="exportTicketManagementSheet('Pdf');" title="PDF" style="display: block">Export PDF</a>
										<a href="javascript:void(0)" onclick="exportTicketManagementSheet('Excel');" title="Excel" style="display: block">Export Excel</a>					
								</span>
							</div>
				</li>
			</sec:authorize>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="ticketListSearch" type="text">
                <span class="repSearchIcon" onclick="getTicketManagementList();"></span></li>
            </ul>
          </div>
          <div class="require_wrap_table datatable_filter" id="ticketListParentList">
            <!-- <div class="candidate_wrap_table datatable_filter require_wrap_table">            
            <table style="width: 100%;" id="ticketManagementListTable">
              <thead>
                <tr>
                  <th>Ticket Id</th>
                  <th>Ticket Title</th>
                  <th>Category</th>
                  <th>Priority</th>
                  <th>Requested By</th>
                  <th>Assign To</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody id="ticketManagementList">
                
              </tbody>
            </table>
          </div> -->
    </div>
    </div>
  </section>

  <script type="text/javascript">
	$(document).ready(function() {
		getTicketStatusList();
		getTicketManagementList();
	});
	
	function getTicketManagementList() {
		$("#ticketListParentList").empty();
		/* $.post(sourceleadUrl + "/getTicketList?searchValue=" + $("#ticketListSearch").val(), {}, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.data.length > 0) {
					for(var e in response.data) {
						var text = "<tr>";
			            text+= "      <td><div class='tblminwidth-150'>" + response.data[e].generatedId + "</div></td>";
			            '<sec:authorize access="hasRole(\'ROLE_VIEW_TICKET\')">';
			            	text+= "      <td><div class='tbldata-dotdot-vsm'><a href='/app/ticketSummary?uuid=" + response.data[e].uuid + "' title='" + response.data[e].title + "'>" + response.data[e].title + "</a></div></td>";
			            '</sec:authorize>';
			            '<sec:authorize access="!hasRole(\'ROLE_VIEW_TICKET\')">';
			            	text+= "      <td><div class='tbldata-dotdot-vsm'>" + response.data[e].title + "</div></td>";
			            '</sec:authorize>';
			            text+= "   	<td><div class='tblminwidth-75' style='color:" + (response.data[e].ticketPriorityTextColor != null && response.data[e].ticketPriorityTextColor !== "" ? response.data[e].ticketPriorityTextColor : "") + "'>" + (response.data[e].ticketPriorityImage !== null && response.data[e].ticketPriorityImage !== "" ? "<div class='priorityImage pull-left mr10'><img src=\"" + response.data[e].ticketPriorityImage + "\" class='mr10'/></div>" : "<div class='blank-div mr10'></div>") + "<div class='pull-left'>" + response.data[e].ticketPriority + "</div></div></td>";
			            text+= "    <td><div class='tbldata-dotdot-vsm'>" + response.data[e].createdBy + "</div></td>";
			            var assignTo = "";
						for(var a in response.data[e].assignedTo) {
							assignTo += (assignTo === "" ? "" : ",") + response.data[e].assignedTo[a];
						}
						text += "    <td><div class='tbldata-dotdot'>" + (assignTo === "" ? "--" : assignTo) + "</div></td>";
						text += "<td><select class='sel' onChange='changeTicketStatus(this, \"" + response.data[e].id + "\", \"" + response.data[e].ticketStatusId + "\")'>";
						for(var s in statusList) {
							text += "<option data-comment=" + statusList[s].commentRequired + " value='" + statusList[s].id + "' " + (statusList[s].name === response.data[e].ticketStatus ? "selected" : "") + ">" + statusList[s].name + "</option>";
						}
						text += "</select></td>";
			            text+= "      <td>";
						text+= "		  <ul class=list-inline list-unstyled'>";
						'<sec:authorize access="hasRole(\'ROLE_ADD_TICKET\')">';
							text+= "			<li><i class='fa fa-pencil cursorPointer' onClick='editTicketManagement(\"" + response.data[e].uuid + "\");'></i></li>";
						'</sec:authorize>';
						'<sec:authorize access="hasRole(\'ROLE_DELETE_TICKET\')">';
							text+= "		  	<li><i class='fa fa-trash cursorPointer' onclick='showDeletePopup(" + response.data[e].id + ", 0);'></i></li>";
						'</sec:authorize>';
						text+= "	  </ul>";
						text+= "		</td>";
			            text+= "   </tr>";
			    		$("#ticketManagementList").append(text);
					}
				}
				ticketManagementListPagination();
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		}); */
		
		var text = "<table style='width: 100%;' id='ticketManagementListTable'>";
		text += " <thead>";
		text += "    <tr>";
		text += "      	<th>Id</th>";
		text += "      	<th>Ticket Id</th>";
		text += "      	<th>Ticket Title</th>";
		text += "      	<th>Priority</th>";
		text += "      	<th>Requested By</th>";
		text += "      	<th>Assign To</th>";
		text += "      	<th>Status</th>";
		text += "      	<th>Action</th>";
		text += "    </tr>";
		text += "  </thead>";
		text += "  <tbody id='ticketTypeList'>";
		text += "  </tbody>";
		text += "</table>";
		$("#ticketListParentList").append(text);
		
		ticketManagementListPagination(sourceleadUrl + "/getTicketList?searchValue=" + $("#ticketListSearch").val());
	}
	
	function ticketManagementListPagination(url) {
		var colIndex = 0;
	    $('#ticketManagementListTable').DataTable( {
	    	"processing": true,
	       /* "serverSide": true, */
	       "searching": true,
	       "searchHighlight": true,
	       "ajax": $.fn.dataTable.pipeline( {
	           url: url,
	           method: 'POST',
	           pages: 1
	       }),
	       "columns": [
	    	   {"data": "id"},
	    	   {"data": "ticketId"},
	           {"data": "title"},
	           {"data": "priority"},
	           {"data": "requestedBy"},
	           {"data": "assignTo"},
	           {"data": "status"},
	           {"data": "actions" }
	       	],
		       "columnDefs": [
	    	   {
		            "targets": colIndex++,
		            "data": "id",
		            "visible": false,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.id != null && opt.id != '' && opt.id != 'undefined') {
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.id+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			           		return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "ticketId",
		            "sortable": true,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.generatedId != null && opt.generatedId != '' && opt.generatedId != 'undefined') {
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.generatedId+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			           		return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "title",
		            "sortable": true,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.title != null && opt.title != '' && opt.title != 'undefined'){
	           			 '<sec:authorize access="hasRole(\'ROLE_VIEW_TICKET\')">';
	           				result = result + "      <td><div class='tbldata-dotdot-vsm'><a href='/app/ticketSummary?uuid=" + opt.uuid + "' title='" + opt.title + "'>" + opt.title + "</a></div></td>";
			            '</sec:authorize>';
			            '<sec:authorize access="!hasRole(\'ROLE_VIEW_TICKET\')">';
			            	result = result + "      <td><div class='tbldata-dotdot-vsm'>" + opt.title + "</div></td>";
			            '</sec:authorize>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			                return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "priority",
		            "sortable": true,
		            "render": function ( data, type, opt, meta ) {
		            	var result = "";
		            	result = result += "<td><div class='tblminwidth-75' style='color:" + (opt.ticketPriorityTextColor != null && opt.ticketPriorityTextColor !== "" ? opt.ticketPriorityTextColor : "") + "'>" + (opt.ticketPriorityImage !== null && opt.ticketPriorityImage !== "" ? "<div class='priorityImage pull-left mr10'><img src=\"" + opt.ticketPriorityImage + "\" class='mr10'/></div>" : "<div class='blank-div mr10'></div>") + "<div class='pull-left'>" + opt.ticketPriority + "</div></div></td>";
			            return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "requestedBy",
		            "sortable": true,
		            "render": function ( data, type, opt, meta ) {
		            	var result = "";
		            	result += "    <td><div class='tbldata-dotdot-vsm'>" + opt.createdBy + "</div></td>";
			            return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "assignTo",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            	var result = "";
		            	var assignTo = "";
		            	for(var a in opt.assignedTo) {
		            		assignTo += (assignTo === "" ? "" : ",") + opt.assignedTo[a];
		            	}
		            	result += "    <td><div class='tbldata-dotdot'>" + (assignTo === "" ? "--" : assignTo) + "</div></td>";
			            return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "status",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            	var result = "";
		            	result += "<td><select class='sel' onChange='changeTicketStatus(this, \"" + opt.id + "\", \"" + opt.ticketStatusId + "\")'>";
		            	for(var s in statusList) {
		            		result += "<option data-comment=" + statusList[s].commentRequired + " value='" + statusList[s].id + "' " + (statusList[s].name === opt.ticketStatus ? "selected" : "") + ">" + statusList[s].name + "</option>";
		            	}
		            	result += "</select></td>";
			            return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "actions",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            	var result = '<div class=" widthIcon widthIcon-three">';
		            	'<sec:authorize access="hasRole(\'ROLE_ADD_TICKET\')">';
			            	result +='	<div class="text-center">';
			            	result +='		<a href="javascript:void(0)" title="Edit Ticket" onclick="editTicketManagement(\'' + opt.uuid + '\')">';
			            	result +='		<i class="fa fa-pencil"></i>';
			            	result +='		</a>';
			            	result +='	</div>';
		            	'</sec:authorize>';
		            	'<sec:authorize access="hasRole(\'ROLE_DELETE_TICKET\')">'; 
			            	result +='	<div class="text-center">';
			            	result +='		<a href="javascript:void(0)" title="Delete Ticket" onclick="showDeletePopup(' + opt.id + ', 0)">';
			            	result +='		<i class="fa fa-trash"></i>';
			            	result +='		</a>';
			            	result +='	</div>';
		            	'</sec:authorize>';
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
		    		row.setAttribute('id',"ticketStatus_"+data.id);
	    	    },
		    	"initComplete": function( settings, json ) {
		    		 $('#ticketListSearch').removeAttr('disabled');
		    	},
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     },
		     drawCallback: function(){
	   	      $('a.paginate_button', this.api().table().container()).on('click', function(){
	   	    	setTimeout(function () {
	   	  		var body = $( table.table().body() );
	   	 	        body.unhighlight();
	   	 	        body.highlight($("#ticketListSearch").val());   
	   	 			
	   	 			}, 1000);
	   	      });
	   	   	}
	   });
	   $("#ticketManagementListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
	   $("input[type=search]").hide();
	}
	
	function exportTicketManagementSheet(exportType){
		window.location.href = sourceleadUrl+"/getTicketManagementExportListByOrgLocationId?"+tokenParamName+"="+tokenValue+"&exportType="+exportType;
	}
	
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
<jsp:include page="../masterFooter.jsp" />	 