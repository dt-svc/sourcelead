<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script>

<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />
 



<link rel="stylesheet" href="resources/css/jquery.timepicker.css">
<script src="resources/js/jquery.timepicker.js"></script> 


<style type="text/css">
.add_inter .chosen-container{
width:145px !important;
}
#addInterviewTbl p.jquery_form_error_message{
padding:0!important;
}
</style>
<!-- For add New Interview -->
<script>
function addInterview(requirementId){
	
	var sourceleadUrl ='${sourceleadUrl}';
	var result = "";
	result = result + '<div class="add_inter">';
	result = result + '<form id="addInterview_form" name="addInterview_form" >';
	result = result + '<input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1" />';
	result = result + '<input type="hidden" id="reqId" name="requirementId" value='+requirementId+'>';
	result = result + '<div class="add_inter_head"> <span>Add Interview</span><a href="javascript:void(0)" onClick="addInterviewRow(this);"><i class="fa fa-plus"></i> Add row</a>';
	result = result + '<div class="clearfix"></div>';
	result = result + '</div>';
	
	result = result + '<table cellpadding="0" cellspacing="0" id="addInterviewTbl">';
	result = result + '<tbody>';
		result = result + '<tr id="divTsRow0">';
			result = result + '<th>Applicant</th>';
			result = result + '<th>Inteview Type</th>';
			result = result + '<th>Interview Date</th>';
			result = result + '<th>Time</th>';
			result = result + '<th>Location</th>';
			result = result + '<th>Interviewer</th>';
			result = result + '<th class="action">Action</th>';
		result = result + '</tr>';
	result = result + '</tbody>';
	result = result + '</table>';
	
	
	result = result + '<div class="row pad2">';
	result = result + '<div class="col-md-8 mt10">';
	//result = result + '<label> Set Reminder &nbsp;<i class="fa fa-gear reminder" title="Click here" onClick="loadRemiderDiv();"></i></label>';
	result = result + '<div class="ne-ch">';
	result = result + '<label><input type="checkbox" name="notificationToApplicant" value="Y" checked="checked" ><b>Notify to Applicants</b></label>';
	result = result + '<label><input type="checkbox" name="notificationToSpoc" value="Y" checked="checked"><b>Notify to Vendors</b></label>';
	result = result + '<label><input type="checkbox" name="notificationToOthers" id="notificationToOthers" value="Y" onClick="showOthersDiv();"><b>Notify to Others</b></label>';
	result = result + '</div>';
	
	result = result + '<div class="row mb10" id="notifyOthersDiv" style="display: none;">';
	result = result + '<div class="col-md-12">';
	result = result + '<input type="text" class="form-control input-sm" value="" data-validation="validate_Space validate_email" data-validation-optional="true" placeholder="Enter emails with comma(,) separated">';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="row mb10">';
	result = result + '<div class="col-md-12">';
	result = result + '<label>Remainder Before</lable>';
	result = result + '<select class="form-control" id="reminderHours" name="reminderHours">';
	result = result + '<c:forEach begin="0" end="24" var="hour">';
	result = result +     '<option value="${hour}">';
	result = result +         '<fmt:formatNumber value="${hour}" type="number" minIntegerDigits="2"/>';
	result = result +     '</option>';
	result = result + '</c:forEach>';
	result = result + '</select>';
	result = result + '</div>';
	
	
	result = result + '<div class="col-md-12">';
	result = result + '<label>Minutes</lable>';
	result = result + '<select class="form-control" id="reminderMinutes" name="reminderMinutes">';
	result = result + '<c:forEach begin="5" end="55" step="5" var="minute">';
	result = result +     '<option value="${minute}" ${minute  == 30 ? "selected" : ''}>';
	result = result +         '<fmt:formatNumber value="${minute}" type="number" minIntegerDigits="2"/>';
	result = result +     '</option>';
	result = result + '</c:forEach>';
	result = result + '</select>';
	result = result + '</div>';
	result = result + '</div>';
          
	result = result + '</div>';
	result = result + '</div>';
	result = result + '<div class="clearfix">';
	result = result + '<div class="pull-right">';
	result = result + '<button type="submit" id="add_interview" class="btn btn-sm btn-primary mr">Submit</button>';
	result = result + '<button type="button" class="btn btn-sm btn-danger" onClick="cancelAddInterviewDiv();">Cancel</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';

	result = result + '</div>';
	result = result + '</div>';   
	result = result + '</form>';
	
	
				
	$("#addInterviewDiv").empty();
	$("#addInterviewDiv").append(result);
	
	$(".add_inter_wrap").addClass("add_inter_wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
}
</script>


<script>
    $(".addInterview_close, .side_mask").click(function() {
    	$(".add_inter_wrap").removeClass("add_inter_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>

<script>
function showOthersDiv(){
	var isNofityToOthers = document.getElementById("notificationToOthers").checked;
	if (isNofityToOthers) {
		document.getElementById("notifyOthersDiv").style.display = '';
	}else {
		document.getElementById("notifyOthersDiv").style.display = 'none';
	}
}
</script>


<div id="divTaskTd1" style="display: none; float: left;">
       <select multiple class="chosen-select blminwidth-50" id="REPLACEROWIDapplicant" name="REPLACEROWIDapplicant" field-name="Applicant" data-validation="required" >
			
	  </select>
</div>

<div id="divTaskTd3" style="display: none; float: left;">
   	 <select class="form-control block" id="REPLACEROWIDinterviewType" name="REPLACEROWIDinterviewType">
		<option value="">--Select Type--</option>
		<option value="F2F">F2F</option>
		<option value="Telephonic">Telephonic</option>
	</select>
</div>


<div id="divTaskTd5" style="display: none; float: left;">
	  <input value="" type="text" id="REPLACEROWIDlocation" name="REPLACEROWIDlocation" class="form-control input-sm" placeholder="Location" field-name="Location" data-validation="required">
</div> 

<div id="divTaskTd6" style="display: none; float: left;">
		  <select multiple class="chosen-select" id="REPLACEROWIDinterviewer" name="REPLACEROWIDinterviewer" field-name="Interviewer" >
			
	  </select>
</div> 

 <div id="divTaskTd7" style="display: none; float: left;">
	 <div>
         <a href="#" onclick="removeInterviewRow(REPLACEROWID);"><i class="glyphicon glyphicon-trash deme"></i></a>
    </div>
</div> 

<script type="text/javascript">
	function addInterviewRow() {
		
		var checkMaxTsRowNum = $("#maxTsRowNum").val();
		var tbl = document.getElementById("addInterviewTbl");
		var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
		$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
		
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divTsRow" + maxRowNum);
		
		var cell0 = row.insertCell(0);
		var divTsTd1Str = document.getElementById("divTaskTd1").innerHTML;
		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
	    divTsTd1Str = divTsTd1Str.replace(/ROWNUMBERVALUE/g, ("")); 
		cell0.innerHTML = divTsTd1Str;
		
		
		var cell1 = row.insertCell(1);
		var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd3Str;
		
		var cell2 = row.insertCell(2);
		/* cell2.innerHTML = "<div class='positionRelative date'><input class='form-control input-sm Datepicker' placeholder='Date' name='"+maxRowNum+"dateOfInterview' id='"+maxRowNum+"dateOfInterview'  readonly='readonly'  type='text' data-validation='required' field-name='Interview Date' > <span class='input-group-addon input-groupAddon'><i class='fa fa-calendar'></i></span> </div>"; */
		cell2.innerHTML = "<div class='positionRelative date'><input class='form-control input-sm Datepicker' placeholder='Select Date' name='"+maxRowNum+"dateOfInterview' id='"+maxRowNum+"dateOfInterview'  readonly='readonly'  type='text' data-validation='required' field-name='Interview Date' ></div>";
		$(".Datepicker").datepicker();

		
		var cell3 = row.insertCell(3);
		cell3.innerHTML = " <div class='pull-left mr'> <span class='positionRelative'><input class='form-control input-sm timepicker' placeholder='Start Time' name='"+maxRowNum+"startTime' id='"+maxRowNum+"startTime'  type='text' data-validation='required' field-name='Start Time' ><span class='input-group-addon input-groupAddon'><i class='fa fa-clock-o'></i></span></span> ";
		cell3.innerHTML = cell3.innerHTML + "<span class='positionRelative'><input class='form-control input-sm timepicker' placeholder='End Time' name='"+maxRowNum+"endTime' id='"+maxRowNum+"endTime'  type='text' field-name='End Time' ><span class='input-group-addon input-groupAddon'><i class='fa fa-clock-o'></i></span></span> </div>";
		$(".timepicker").timepicker();
		
		var cell4 = row.insertCell(4);
		var divTsTd5Str = document.getElementById("divTaskTd5").innerHTML;
		divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd5Str = divTsTd5Str.replace(/PRICECALCULATION/g,("")); 
		cell4.innerHTML = divTsTd5Str;  
		
		var cell5 = row.insertCell(5);
		var divTsTd6Str = document.getElementById("divTaskTd6").innerHTML;
		divTsTd6Str = divTsTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell5.innerHTML = divTsTd6Str;  
		
		var cell6 = row.insertCell(6);
		var divTsTd7Str = document.getElementById("divTaskTd7").innerHTML;
		divTsTd7Str = divTsTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell6.innerHTML = divTsTd7Str;
		
		getInterviewers(maxRowNum);
		getShortListCandidates(maxRowNum);
		

	      $("#"+maxRowNum+"applicant").chosen();
	      $("#"+maxRowNum+"interviewer").chosen();
		
		
	      $("#"+maxRowNum+"applicant").on('change', function (e) {
	    	  getUpdatedShortListCandidates();
	       }); 
		    
		    
				
}		
</script>

<script type="text/javascript">
	function removeInterviewRow(rowId) {
		var rowNum = $("#divTsRow"+rowId).val();
		var maxRowNum = parseInt(rowNum);
		 document.getElementById("divTsRow"+rowId).style.display = 'none';
		 document.getElementById("divTsRow"+rowId).value= '';
		 var  rId = "divTsRow"+rowId;
		  var r = document.getElementById("addInterviewTbl").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
	}
</script>


<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

<script>
function cancelAddInterviewDiv(){
	 $(".add_inter_wrap").removeClass("add_inter_wrap_active");
	 $(".side_mask").fadeOut(200);
 	 $("body").css('overflow-y', 'auto');
}

</script>

<script type="text/javascript">
   	$('body').on('submit', '#addInterview_form', function(e){
	  if ($(this).validate(false, validationSettings)){
        e.preventDefault();
        var frm = $('#addInterview_form').serializeFormJSON();
        var con = JSON.stringify(frm);
        con = con.replace(/[{}]/g, "");
        var jsonData = encodeURIComponent(con);
        var sourceleadUrl ='${sourceleadUrl}';
        
        $("#add_interview").attr("disabled", "disabled");
 		$("#add_interview" ).addClass('button').val('Processing..');
 		
   	    $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/saveInterview?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData,
	        success: function(result) {
	        	    $(".add_inter_wrap").removeClass("add_inter_wrap_active");
	        		$(".side_mask").fadeOut(200);
	        		$("body").css('overflow-y', 'auto');
	        },
	    });  
        return true;
      }
      else{
      	return false;	
         }
    });
    	
   	$('#addInterview_form').showHelpOnFocus();
    $('#addInterview_form').validateOnBlur(false, validationSettings); 
  
  </script>
  
<script type="text/javascript">
 function getShortListCandidates(rowId) {
	 var existingApplicantsIds = [];    
	 for(i=rowId ; i > 0; i--){
		 var applicant = $("#"+(i)+"applicant").val();
		 if(applicant != null && applicant != '' && applicant != 'undefined'){
			 existingApplicantsIds.push(applicant);
		 }
	 }
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 var requirementId = $("#reqId").val();
     
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllShortListCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+"</option>";
					}
		        	$("#"+rowId+"applicant").empty();
		        	$("#"+rowId+"applicant").append(output);
		        	$("#"+rowId+"applicant").trigger("chosen:updated");
			       
		        },
		    });
	
 }
</script> 

<script type="text/javascript">
 function getUpdatedShortListCandidates() {
	 var rowId = $("#maxTsRowNum").val();
	 var existingApplicantsIds = [];    
	 for(i=rowId ; i > 0; i--){
		 var applicant = $("#"+(i)+"applicant").val();
		 if(applicant != null && applicant != '' && applicant != 'undefined'){
			 existingApplicantsIds.push(applicant);
		 }
	 }
	 var sourceleadUrl ='${sourceleadUrl}';
	 var requirementId = $("#reqId").val();
     
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllShortListCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+"</option>";
					}
		        	for(var i=rowId-1 ; i > 0; i--){
		        		 var internalOutput = "";
		        		 var applicantIdList= $("#"+(i)+"applicant").val();
		        		 var arr = jQuery.makeArray(applicantIdList);
                         for(var j = 0; j < arr.length; j++) {
                        	 var label = $("#"+(i)+"applicant option[value='"+arr[j]+"']").text();
                        	 internalOutput = internalOutput+"<option value='" +arr[j]+ "' selected>"+label+"</option>";
                         }
		        		$("#"+(i)+"applicant").empty();
		        		$("#"+(i)+"applicant").append(internalOutput);
			        	$("#"+(i)+"applicant").append(output);
			        	$("#"+(i)+"applicant").trigger("chosen:updated");
		       	    }
			       
		        },
		    });
 }
</script>    
  
  
<script type="text/javascript">
 function getInterviewers(rowId) {
	 var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllInterviewers?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#"+rowId+"interviewer").empty();
		        	$("#"+rowId+"interviewer").append(output);
		        	$("#"+rowId+"interviewer").trigger("chosen:updated");
			       
		        },
		    });
	
 }
</script>  
<script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	       
	      
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});
</script>


<jsp:include page="../masterFooter.jsp" />