<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

    <div class="clearfix"></div>
    <div id="myPayrollFormLoading"></div>
    <div class="timesheet_out" id="myPayrollFormDiv">
      <div class="tablelist-wrap">
        <div class="timesheet_head table_head minHeight40 mt">
              <ul class="pull-right list-inline list-unstyled">
                <li>
                   <button type="button" class="btn btn-primary mr" onClick="uploaddocument();"><i class="fa fa-upload mr"></i>Upload</button>
                </li>
              </ul>
        </div>
          <div class="candidate_wrap_table datatable_filter">
          	<div id="payrollFormListDiv"></div>
          </div>
        <div class="clearfix"></div>
      </div>
    </div>
 
 <div class="sideMask"></div>
 <div class="modal" id="uploaddocument">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" onClick="closeRequestPop();">×</button>
        <h4 class="modal-title">Upload Form</h4>
      </div>
      <div class="modal-body">
        <form:form id="us_My_Payroll_form" name="us_My_Payroll_form" method="post">
	        <div class="form-group clearfix">
	              <label class="col-md-3">Form Name<span class="text-danger paddingLeft">*</span></label>
	            <div class="clearfix dropdown-select col-md-9">
	              <input class="form-control" placeholder="Enter Form Name" name="formName" id="formName" field-name = "Form Name" type="text" data-validation="required">
	            </div>
	        </div>
	        <div class="form-group clearfix">
	              <label class="col-md-3"></label>
	           <div class="col-md-9">
	           
	           <div class="input-group attachfile"><i class="clearUpload" title="clear" onClick="removedocfile();"></i>
                  
                  <div class="input-group ratetype col-md-12 pull-left" title="" id="uploadFileGroup">
	                      <label class="input-group-btn"> 
	                        <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Choose File
	                          <input type="file" id="uploadBtn" style="display: none;" class="upload">
	                        </span> 
	                      </label>
		                      <input type="text" class="form-control" readonly id="uploadFile" value="" placeholder="no file chosen" field-name="Attachment" data-validation="required">
	              </div>
                  </div>
	            </div>
	        </div>
        </form:form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="saveUsMyPayrollForm();">Submit</button>
        <button type="button" class="btn btn-default" onClick="closeRequestPop();">Close</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
function saveUsMyPayrollForm(){
	if ($('#us_My_Payroll_form').validate(false, validationSettings)) {
	  	var formData = getFormData('us_My_Payroll_form');
		$("#myPayrollFormLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/saveUsMyPayrollForms?"+tokenParamName+"="+tokenValue, formData, function(result) {
			if (result && result.response === "INVALID_DATA") {
    		    showError(result.responseMessage);
    		} else if (result.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (result.response === "SUCCESS") {
    			$("#uploaddocument, .sideMask").fadeOut();
    			getUsMyPayrollForms();
    		} else if (result.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
		}); 
		return true;
		}
}
</script>

<script>
function getUsMyPayrollForms(){
	$("#us_My_Payroll_form")[0].reset();
	$.get(sourceleadUrl+"/getUsMyPayrollFormsByOrgLocationId", function(res) {
		if (res && res.response === "INVALID_DATA") {
		    showError(res.responseMessage);
		} else if (res.response === "AWKWARD") {
		    location.href = "/errorPage";
		} else if (res.response === "SUCCESS") {
			var result = "";				        	
			result = result + '<table id="usPayrollFormListTable" class="display" cellspacing="0" width="100%" >';
			result = result + '<thead>';
			result = result + '<tr>';
			result = result + '<th>Form Name</th>';
			result = result + '<th>Action</th>';
			result = result + '</tr>';
			result = result + '</thead>	';
			result = result + '<tbody>';
			if(res.data != null && res.data != ''){
				 for (var i=0; i<res.data.length; i++)
					{
		        		 var opt = res.data[i];
		        		    result = result + ' <tr>';
		        		    
		        		    if(opt.formName !=null && opt.formName !='' && opt.formName !='undefined'){
		        		    	result = result + '<td><div class="tblminwidth-75">'+opt.formName+'</div></td>';
		        		    }else{
		        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
		        		    }
		        		    
		        		    result = result + '<td><a href="#" title="Preview"><i class="fa fa-eye" onclick="viewFile(\''+opt.fileUrl+'\', \''+opt.fileName+'\', \''+opt.fileContentType+'\');"></i></a>';
// 		        		    result = result + '   <a href="#" title="Edit"><i class="fa fa-pencil"></i></a>';
		        		    result = result + '   <a onclick="deleteForm(\''+opt.uuid+'\');" title="Delete"><i class="glyphicon glyphicon-trash"></i></a>';
		        		    result = result + ' </td>';
							result = result + ' </tr>';
					}
			}
        	 
			result = result + '  </tbody>';
			result = result + ' </table>';
			$("#payrollFormListDiv").empty();
			$("#payrollFormListDiv").append(result);
			$("#myPayrollFormLoading").removeAttr("style");
			usPayrollFormListTablePagination(); 
		} else if (res.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
		    showError("Operation failed, please try again");
		}
	});
}
</script>

<script>
function usPayrollFormListTablePagination(){ 
    $('#usPayrollFormListTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#usPayrollFormListTable").wrap("<div class='overflow_Auto  width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script>

<script>
function deleteForm(uuid){
	$("#myPayrollFormLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.get(sourceleadUrl+"/deleteUsMyPayrollFormByUUID?&uuid="+uuid, function(res) {
		if (res && res.response === "INVALID_DATA") {
		    showError(res.responseMessage);
		} else if (res.response === "AWKWARD") {
		    location.href = "/errorPage";
		} else if (res.response === "SUCCESS") {
			getUsMyPayrollForms();
		} else if (res.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
		    showError("Operation failed, please try again");
		}
	});
}
</script>

<script>
var selectedFile = document.getElementById("uploadBtn");
selectedFile.addEventListener("change", function() {
	 $("#uploadFile").focus();
	 if(validate_fileupload(this.value)){
		 var docSize =  $("#uploadBtn")[0].files[0].size;
	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
// 	    	  $('#leaveLoading').removeAttr('style');
	    	  document.getElementById("uploadFile").value = '';
	    	  $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
			  $("input#uploadFile").after("<p class='jquery_form_error_message'>File size should be less then 2MB </span>");
			  document.getElementById("uploadFile").setAttribute('record-exist','yes');
			  document.getElementById("uploadFile").setAttribute('record-exist-errorMsg','File size should be less then 2MB');
			  document.getElementById("uploadFile").value="";
	      }else{
	    	 $('input#uploadFile').removeAttr( "record-exist record-exist-errormsg");
  		 $('#uploadFileGroup').find('p.jquery_form_error_message').remove(); 
	    }
	 }
});

/* upload file script */
document.getElementById("uploadBtn").onchange = function () {
	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
};

$(function() {
	$("#uploadBtn").change(function() {
		fileUpload("uploadBtn", "usMyPayrollForms", "us_My_Payroll_form");
	});
 });
</script>
<script>
 function removedocfile(){
	 $("#uploadFile").val('');
 }
 </script> 
    
<script type="text/javascript">
	function uploaddocument() {
		$("#uploaddocument, .sideMask").fadeIn();
	}
	
	function closeRequestPop(){
		$(".sideMask").fadeOut();
		$("#uploaddocument").fadeOut();
		$("#us_My_Payroll_form")[0].reset();
	}
	
	$("#uploadBtn").change(function () {
			//var m = $(this).val();
		$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	})
</script>

<!--   </section> -->