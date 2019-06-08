<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="/resources/js/ticketManagement/ticket_configuration.js"></script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Ticket Category</h2>
      </div>
            <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a> &raquo; Ticket Category List</div>
    </section>
    <div class="content-area content-new clearfix timesheet_out">
      <div class="clearfix mt mb">
        <div class="col-md-2 padding_right">
          <ul class="list-unstyled actTicketUL">
            <li><a href="/app/ticketManagementTypeList"><i class="fa fa-ticket mr10"></i>Type</a></li>
            <li class="actTicketList"><a href="/app/ticketManagementCategoryList"><i class="fa fa-navicon mr10"></i>Category</a></li>
            <li><a href="/app/ticketManagementPriorityList"><i class="fa fa-asterisk mr10"></i>Priority</a></li>
            <li><a href="/app/ticketManagementStatusList"><i class="fa fa-check-circle mr10"></i> Status</a></li>
          </ul>
        </div>
        <div class="col-md-10 padding_left">
			<div class="project_tabs projectTabsborder">
				<div id="tab-1">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
            <sec:authorize access="hasRole('ROLE_ADD_TICKET_CONFIGURATION')">
              <li><a class="newAdBtn mr5" title="Add" id="addPop">+ Add</a></li>
            </sec:authorize>
              <li>
                <input class="repSearchBar" placeholder="Search" id="ticketCategoryDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getTicketCategoryList();"></span>
              </li>
            </ul>
          </div>
			<div class="candidate_wrap_table datatable_filter" id="ticketCategoryParentList">
            	
			</div>
				
		  </div>
		  
        </div>
      </div>
    </div>
    </div>
  </section>
  <!-- Footer Area --> 
<div class="mask"></div>
<div class="popupWrap" id="modalTasktype">
      <div class="popupHead"><span id="ticketCategoryFormHead" >Add Category</span><i class="closePop pull-right popClose"></i></div>
        <form id="addTicketCategoryForm" name="" action="" method="post">
        <div class="popupContent">
        <input type="hidden" id="ticketCategoryId" name="id" value="0"/>
            <div class="clearfix mb">
                <label class="noPad">Name <b class="valStar">*</b></label>
                <div class="clearfix">
                    <input class="form-control" placeholder="Name" name="name" type="text" id="ticketCategoryName" field-name="Name" data-validation="required validate_length length1-100">
                </div>
            </div>
            <div class="clearfix mb">
              <label class="noPad">Assign to</label>
              <div class="clearfix">
					<select multiple class="chosen-select form-control" name="assignedTo" field-name="Assign To" id="assignedTo">
						<c:forEach items="${userList}" var="user">
							<option value="${user.id}">${user.firstName}&nbsp;${user.lastName}</option>
						</c:forEach>
					</select>
              </div>
          </div>
          <div class="clearfix mb">
            <label class="noPad">Notify to</label>
            <div class="clearfix">
                <textarea class="form-control" rows="4" id="notifyTo" name="notifyTo" placeholder="Enter multiple Email Id's separated by commas(,)" data-validation="validate_length length0-1000"></textarea>
            </div>
        </div>
            <div class="clearfix mb">
                <label class="noPad">Description</label>
                <div class="clearfix">
					<textarea placeholder="Description" name="description" id="ticketCategoryDesc" class="form-control" rows="2" data-validation="validate_length length0-5000"></textarea>
                </div>
            </div>

        </div>
        
        <div class="popupFoot text-right">
          <button type="button" class="slBtn md colorBtn" onclick="saveTicketCategory();">Save</button>
          <button type="button" class="slBtn md popClose" onclick="cancelTicketCategory();"> Cancel</button>
        </div>
        
        <div>
</div></form>
  </div>
 <div id='changeStatusPopup' class='remove_user_pop remove_user_pop_a'>
	<p id='changeStatusHeading' data-name="Ticket Category"></p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmId' type='button' value='Ok' onclick='changeTicketCategoryStatus()' class="pull-left">
		<input id="cancelStatusPopup" class='u_can_color' type='button' value='Cancel' onClick='closeDeletePopup()'>
	</b>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		getTicketCategoryList();
	});
	
	function ticketManagementCategoryPagination(url){
		var colIndex = 0;
	    $('#ticketManagementCategoryTable').DataTable( {
	    	"processing": true,
	       "serverSide": true,
	       "searching": true,
	       "searchHighlight": true,
	       "ajax": $.fn.dataTable.pipeline( {
	           url: url,
	           method: 'POST',
	           pages: 20
	       }),
	       "columns": [
	    	   {"data": "id"},
	           {"data": "category"},
	           {"data": "description"},
	           {"data": "assignTo"},
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
		            "data": "category",
		            "sortable": true,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.name != null && opt.name != '' && opt.name != 'undefined') {
		     	        	result = result + '<td><div  class="tbldata-dotdot-big500" title="'+opt.name+'">'+opt.name+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			           		return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "description",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            		var result = "";
	           		   if(opt.description != null && opt.description != '' && opt.description != 'undefined'){
		     	        	result = result + '<td><div  class="tbldata-dotdot-big500" title="'+opt.description+'">'+opt.description+'</div></td>';
	           		   } else {
							result = result +"<td><div>-----</div></td>";
	           	       }
			                return result;
		             }
		        },
		        {
		            "targets": colIndex++,
		            "data": "assignTo",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            	var assignTo = "";
		                for(var a in opt.assignedTo) {
		                  assignTo += (assignTo === "" ? "" : ",") + opt.assignedTo[a];
		                }
		            	var result = "";
	           		   if(assignTo !== ""){
		     	        	result = result + '<td><div  class="tbldata-dotdot-sm">' + assignTo + '</div></td>';
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
		            	'<sec:authorize access="hasRole(\'ROLE_ADD_TICKET_CONFIGURATION\')">';
			            	result +='	<div class="text-center">';
			            	result +='		<a href="javascript:void(0)" title="Edit TicketCategory" onclick="editTicketCategory(' + opt.id + ')">';
			            	result +='		<i class="fa fa-pencil"></i>';
			            	result +='		</a>';
			            	result +='	</div>';
		            	'</sec:authorize>';
		            	'<sec:authorize access="hasRole(\'ROLE_DELETE_TICKET_CONFIGURATION\')">'; 
			            	result +='	<div class="text-center">';
			            	if(opt.status === 0) {
			            		result +='		<a href="javascript:void(0)" title="Active TicketCategory" onclick="showDeletePopup(' + opt.id + ', 1)">';
			            		result +='		<i class="fa fa-edit"></i>';
			            	} else {
			            		result +='		<a href="javascript:void(0)" title="In-active TicketCategory" onclick="showDeletePopup(' + opt.id + ', 0)">';
			            		result +='		<i class="fa fa-trash"></i>';
			            	}
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
		    		row.setAttribute('id',"ticketCategory_"+data.id);
	    	    },
		    	"initComplete": function( settings, json ) {
		    		 $('#ticketCategoryDataTableSearch').removeAttr('disabled');
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
	   	 	        body.highlight($("#ticketCategoryDataTableSearch").val());   
	   	 			
	   	 			}, 1000);
	   	      });
	   	   	}
	   });
	   $("#ticketManagementCategoryTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
	   $("input[type=search]").hide();
	}
</script>
  


 <jsp:include page="../masterFooter.jsp" />	

 