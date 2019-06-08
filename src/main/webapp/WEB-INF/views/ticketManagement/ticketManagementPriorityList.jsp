<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="/resources/js/ticketManagement/ticket_configuration.js"></script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Ticket Priority</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a> &raquo; Ticket Priority List</div>
    </section>
    <div class="content-area content-new clearfix timesheet_out">
      <div class="clearfix mt mb">
        <div class="col-md-2 padding_right">
          <ul class="list-unstyled actTicketUL">
            <li><a href="/app/ticketManagementTypeList"><i class="fa fa-ticket mr10"></i>Type</a></li>
            <li><a href=/app/ticketManagementCategoryList><i class="fa fa-navicon mr10"></i>Category</a></li>
            <li class="actTicketList"><a href=/app/ticketManagementPriorityList><i class="fa fa-asterisk mr10"></i>Priority</a></li>
            <li><a href=/app/ticketManagementStatusList><i class="fa fa-check-circle mr10"></i> Status</a></li>
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
				                <input class="repSearchBar" placeholder="Search" id="ticketPriorityDataTableSearch" type="text">
				                <span class="repSearchIcon" onclick="getTicketCategoryList();"></span>
				            </li>
			            </ul>
					</div>
		          <div class="candidate_wrap_table datatable_filter" id="ticketPriorityParentList">
		           
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
      <div class="popupHead"><span id="ticketPriorityFormHead" >Add Priority</span><i class="closePop pull-right popClose"></i></div>
        <form id="addTicketPriorityForm" name="" action="" method="post">
        <input type="hidden" id="ticketPriorityId" name="id" value="0"/>
        <div class="popupContent">
            <div class="clearfix mb">
                <label class="noPad">Name <b class="valStar">*</b></label>
                <div class="clearfix">
                    <input class="form-control" placeholder="Name" name="name" type="text" id="ticketPriorityName" field-name="Name" data-validation="required validate_length length1-100">
                </div>
            </div>
            <div class="clearfix mb">
                <label class="noPad">Color</label>
                <div class="clearfix">
                    <div class="input-group">
                        <input id="favcolor" type="text" class="form-control valid" readonly name="colorPic" placeholder="Color Pic">
                        <span class="input-group-addon" style="padding: 2px 10px;">
                        <input type="color" id="favcolorP" name="colorCode" style="width: 20px; height: 20px;" onchange="colorPicker();">
                        </span> 
                      </div>
                </div>
            </div>
            <div class="clearfix mb">
                <label class="noPad">Icon</label>
				<div class="clearfix">
					<div class="input-group col-md-11 pull-left">
						<span class="input-group-btn">
							<label class="input-group-btn"> 
								<span class="btn btn-danger" onchange="uploadPriorityImage();">
									<i class="fa fa-folder-open-o"></i> &nbsp;Browse File 
									<input type="file" style="display: none;" multiple id="uploadBtn" class="upload" accept="image/*">
									<input type="hidden" id="dataFilePath">
									<input type="hidden" id="refreshImage" name="refreshImage" value="false"/>
								</span>
							</label>
						</span>
						<input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Priority Image ..." data-validation="validateimage">
					</div>
					<div class="col-md-1 pull-left">
						<i class="fa fa-refresh cursorPointer mt10 text-primary" title="Refresh Icon" onclick="refreshPriorityImage();"></i>
					</div>
					<div class="clearfix"></div>
					<div class="clearfix mt10 text-muted">
						<i class="fa fa-question-circle text-info"></i>&nbsp; Upload icon image ...
					</div>
				</div>
			</div>
            <div class="clearfix mb">
                <label class="noPad">Description</label>
                <div class="clearfix">
					<textarea placeholder="Description" name="description" id="ticketPriorityDesc" class="form-control" rows="2" data-validation="validate_length length0-5000"></textarea>
                </div>
            </div>
        </div>
        <div class="popupFoot text-right">
          <button type="button" class="slBtn md colorBtn" onclick="saveTicketPriority();">Save</button>
          <button type="button" class="slBtn md popClose" onclick="cancelTicketPriority();"> Cancel</button>
        </div>
	</form>
</div>

<div id='changeStatusPopup' class='remove_user_pop remove_user_pop_a'>
	<p id='changeStatusHeading' data-name="Ticket Priority"></p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmId' type='button' value='Ok' onclick='changeTicketPriorityStatus()' class="pull-left">
		<input id="cancelStatusPopup" class='u_can_color' type='button' value='Cancel' onClick='closeDeletePopup()'>
	</b>
</div>

<script type="text/javascript">
$(document).ready(function() {
	getTicketPriorityList();
});
function experienceRadio() {
	var radioCheck = document.getElementById("experienceRadio");
    var experienceRadio = document.getElementById("experienceDiv");
    if (radioCheck.checked == true){
       experienceRadio.style.display = "block";
    } else {
       experienceRadio.style.display = "none";
    }
}
function applicableForAll() {
	document.getElementById("experienceDiv").style.display="none";
}
$(function(){
	$("#addPop").click(function() {
		$(".mask, #modalTasktype").fadeIn();
	});
	$(".popClose").click(function() {
		$(".mask, #modalTasktype").fadeOut();
	});
});

function colorPicker() {
	var favColor = document.getElementById("favcolorP").value;
	document.getElementById("favcolor").value = favColor;
    document.getElementById("colorBox").style.background = favColor;
}

function ticketManagementPriorityPagination(url){
	var colIndex = 0;
    $('#ticketManagementPriorityTable').DataTable( {
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
           {"data": "icon"},
           {"data": "priority"},
           {"data": "description"},
           {"data": "color"},
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
	            "data": "icon",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.fileURL != null && opt.fileURL != '' && opt.fileURL != 'undefined') {
           			   result = result + '<div class="priorityImage"><img src=' + opt.fileURL + ' alt="' + opt.fileName + '" title="Priority Image"></img></div>';
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
	            "sortable": true,
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
	            "data": "color",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.colorCode != null && opt.colorCode != '' && opt.colorCode != 'undefined'){
	     	        	result = result + '<div class="tblminwidth-75"><small class="colorBox" id="colorBox" style="background:' + opt.colorCode + '"></small></div>';
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
		            	result +='		<a href="javascript:void(0)" title="Edit TicketPriority" onclick="eidtTicketPriority(' + opt.id + ')">';
		            	result +='		<i class="fa fa-pencil"></i>';
		            	result +='		</a>';
		            	result +='	</div>';
	            	'</sec:authorize>';
	            	'<sec:authorize access="hasRole(\'ROLE_DELETE_TICKET_CONFIGURATION\')">'; 
		            	result +='	<div class="text-center">';
		            	if(opt.status === 0) {
		            		result +='		<a href="javascript:void(0)" title="Active TicketPriority" onclick="showDeletePopup(' + opt.id + ', 1)">';
		            		result +='		<i class="fa fa-edit"></i>';
		            	} else {
		            		result +='		<a href="javascript:void(0)" title="In-active TicketPriority" onclick="showDeletePopup(' + opt.id + ', 0)">';
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
	    		row.setAttribute('id',"ticketPriority_"+data.id);
    	    },
	    	"initComplete": function( settings, json ) {
	    		 $('#ticketPriorityDataTableSearch').removeAttr('disabled');
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
   	 	        body.highlight($("#ticketPriorityDataTableSearch").val());   
   	 			
   	 			}, 1000);
   	      });
   	   	}
   });
   $("#ticketManagementPriorityTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   $("input[type=search]").hide();
}
</script>
<jsp:include page="../masterFooter.jsp" />	