<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/moment.min.js"></script>
<script src="/resources/js/bootstrap-datetimepicker.min.js"></script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Schedule Maintenance</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; Schedule Maintenance</div>
    </section>
    <div class="timesheet_out">
      <div class="vendor_wrap">
        <div class="timesheet_head">
          <ul class="pull-right">
            <li><a href="javascript:void(0)" title="" onclick="showSchedulePopup(null);" id="scheduleMPopup"><i class="fa fa-plus"></i>Add Schedule</a></li>
          </ul>
          <div class="clearfix"></div>
          <div class="remove_search">
            <input type="text" id="scheduleMTableSearch" placeholder="search"/>
          </div>
        </div>
        
		<div id="scheduleFilterList" class="project_wrap_table datatable_filter">
		</div>        
      </div>
    </div>
    <div class="popMask"></div>
  <div class="popUpOut" id="scheduleMPopDiv">
		<div class="popupHead">
			<h2>Schedule Maintenance</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		<form:form id="scheduleMaintenance_form" name="scheduleMaintenance_form" method="post">
	<div class="popupMid popupMidHeight clearfix">
		<div id="scheduleDateValidation" class="scheduleDateValidation"></div>
          <div class="col-sm-12 noPad mt clearfix">
          		<input type="hidden" id="scheduleId" name="id" value=""/>
				<label class="col-md-4 padding_left">Start Date<span class="text-danger paddingLeft">*</span></label>
              	<div class="col-md-8 padding_right">
              		<div class="input-group errorInptgroup">
						<input type="text" value="" name="startDate" onkeydown="return false" class="form-control calendar" id="scheduleStartDate" field-name="Start Date" data-validation="required">
	                    <span class="input-group-addon" onclick="clearDate('scheduleStartDate');"><i class="colorRed fa fa-times cursorPointer"></i></span>
					</div>
				</div>
			</div>
			 <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date<span class="text-danger paddingLeft">*</span></label>
              	<div class="col-md-8 padding_right">
              		<div class="input-group errorInptgroup">
						<input type="text" value="" name="endDate" onkeydown="return false" class="form-control calendar" id="scheduleEndDate" field-name="End Date" data-validation="required">
	                    <span class="input-group-addon" onclick="clearDate('scheduleEndDate');"><i class="colorRed fa fa-times cursorPointer"></i></span>
					</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Message<span class="text-danger paddingLeft">*</span></label>
              	<div class="col-md-8 padding_right">
              		<textarea class="form-control" name="scheduleMessage" id="scheduleMessage" maxlength="250" field-name="Schedule Message" rows="3" data-validation="required"></textarea>
				</div>
			</div>
		</div>
		<div class="immiPopFoot">
            <input value="Save" class="" onclick="saveScheduleMaintenance();" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>
          </form:form>		
	</div>
  </section>
<jsp:include page="masterFooter.jsp" />
<script>
function showSchedulePopup(jsonValues){
	$("#scheduleMPopDiv").fadeIn();
	$(".popMask").fadeIn();
	//clearDate();
	if(jsonValues !== null){
		$("#scheduleId").val(jsonValues.id);
		$("#scheduleStartDate").val(jsonValues.startDate);
		$("#scheduleEndDate").val(jsonValues.endDate);
		$("#scheduleMessage").val(jsonValues.scheduleMessage);
	}
}

function flushScheduleMaintenance(){
	$("#scheduleDateValidation").empty();
	$("#scheduleId").val("");
	document.getElementById("scheduleMaintenance_form").reset();
	$("scheduleMaintenance_form").validate().resetForm();
	
}
var startDateTime = null;
var endDateTime = null;
$(function() {
	scheduleDataTableList();	
/* 	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	// written common method in slead-common.js for date formats start
	var finalDateFormat = getJSDateFormat(locationDate);
 */	
	$('#scheduleStartDate').datetimepicker({
		minDate: new Date()
	}).val('').on('dp.change', function (e) {
		startDateTime = e.date;
	});
	 
	$('#scheduleEndDate').datetimepicker({
		minDate: new Date()
	}).val('').on('dp.change', function (e) {
		endDateTime = e.date;
	});
});

function clearDate(dateId){
		$("#"+dateId).val("");
};

$(".popCan").click(function(){
	  $("#scheduleMPopDiv").fadeOut();
	  $(".popMask").fadeOut();
	  flushScheduleMaintenance();
});

function saveScheduleMaintenance() {
	if ($('#scheduleMaintenance_form').validate(false, validationSettings)) {
		if(new Date(startDateTime) > new Date(endDateTime)){
			$("#scheduleDateValidation").empty().append("End Date should not greater than Start Date");
			return false;
		}
			var formData = getFormData("scheduleMaintenance_form");
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
					type : "POST",
					 data: formData,
					url : sourceleadUrl+"/storeScheduleMaintenance?${_csrf.parameterName}=${_csrf.token}",
					success : function(result) {
						
 	       			if (result && result.response === "INVALID_DATA") {
					    showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						$("#scheduleMPopDiv").fadeOut();
						$(".popMask").fadeOut(); 
						scheduleDataTableList();
 	       			    flushScheduleMaintenance();
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
					}
				}); 
		return true;
	} else {
		return false;
	}
}

function scheduleDataTableList(){
	var result = "";
 	result = result +"<table id='scheduleListTable'><thead><tr><th>Start Date</th><th>End Date</th><th>Message</th><th>Action</th></tr></thead></table>";
    $("#scheduleFilterList").empty();
    $("#scheduleFilterList").append(result);
	var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#scheduleMTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getScheduleMaintenanceList?searchValue="+searchValue;
	 schedulePagination(url,sourceleadUrl);
}
	
function schedulePagination(url){
	var colIndex = 0;
    $('#scheduleListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
            { "data": "startDate" },
            { "data": "endDate"},
            { "data": "scheduleMessage"},
           /*  { "data": "id"} */
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "startDate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  if(opt.startDate !=null && opt.startDate !='' && opt.startDate !='undefined'){
			        		result = result + '<td>'+opt.startDate+'</td><input type="hidden" id="startDate'+opt.id+'" value="'+opt.startDate+'"/>';
			        	}else{
			        	  	result = result + '<td>---</td>';
			        	}
		             	return result;
	    	  		}
	      		},
	      	{
	    	  "targets": colIndex++,
              "data": "endDate",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
    	  		  if(opt.endDate !=null && opt.endDate !='' && opt.endDate !='undefined'){
		        		result = result + '<td>'+opt.endDate+'</td><input type="hidden" id="endDate'+opt.id+'" value="'+opt.endDate+'"/>';
		        		}else{
		        		  	result = result + '<td>---</td>';
		        		}
	             return result;
              }
	          },{
		    	  "targets": colIndex++,
	              "data": "scheduleMessage",
	              "render": function ( data, type, opt, meta ) {
	              		var result = "";
	        	  		if(opt.scheduleMessage !=null && opt.scheduleMessage !='' && opt.scheduleMessage !='undefined'){
	    		        	result = result + '<td>'+opt.scheduleMessage+'</td><input type="hidden" id="scheduleMessage'+opt.id+'" value="'+opt.scheduleMessage+'"/>';
	    		        }else{
	    		          	result = result + '<td>---</td>';
	    		        }
	    	     	 return result;
	             	 }
	              },
				{
			    	"targets": colIndex++,
		          	/* "data": "id", */
		          	"render": function ( data, type, opt, meta ) {
		            	var result = "";
		        	  	if(opt.id !=null && opt.id !='' && opt.id !='undefined'){
		    		       	result = result + '<td><div><i id="edit'+opt.id+'" onclick="editScheduleRow('+opt.id+');" class="glyphicon glyphicon-pencil"></i><i id="delete'+opt.id+'" onclick="deleteScheduleRow('+opt.id+');" class="glyphicon glyphicon-trash ml10"></i></div></td>';
		    		    }
		    	 return result;
		        }
		     }],
		     "scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[3,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"initComplete": function( settings, json ) {
		    		 $('#scheduleMTableSearch').removeAttr('disabled');
		    	  },
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": "", 
		     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		     }
    });
    $("#scheduleListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("#scheduleMTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   scheduleDataTableList();
    	   }
    });
}

function deleteScheduleRow(idValue){
	$.ajax({
		type : "GET",
		url : sourceleadUrl+"/deleteScheduleMaintenance?${_csrf.parameterName}=${_csrf.token}&id="+idValue,
		success : function(result) {
			
			if (result && result.response === "INVALID_DATA") {
			    showError(result.responseMessage);
			} else if (result.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				scheduleDataTableList();
				flushScheduleMaintenance();
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			}
		}
	}); 
}

function editScheduleRow(idValue){
	var jsonScheduleValues = {
		"id" : idValue,
		"startDate" : $("#startDate"+idValue).val(),
		"endDate" : $("#endDate"+idValue).val(),
		"scheduleMessage" : $("#scheduleMessage"+idValue).val()
	}
	showSchedulePopup(jsonScheduleValues);
}
</script>