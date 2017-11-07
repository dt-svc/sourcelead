<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 

<!-- Content Area -->
   <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Shift</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='/leaveConfiguration'/>"> Leave Configuration</a> &raquo;<a href="<c:url value='/viewShift'/>">Shift List</a> &raquo; Add Shift</div>
    </section>
    <div class="content-area content-new clearfix">
    <div id="loading"></div>
    <form:form id="addshift_form" name="addshift_form">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
        
        
         
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
    <div class="form-group">
       <label class="col-md-3">Shift Name<span
							class="text-danger">&nbsp;*</span></label>
       <div class="col-md-9" id="shiftNameDiv">
       <input type="hidden" name="shiftId" id="shiftId" value="${shift.id}"/>
       <input type="text" name="shiftName" placeholder="Shift Name" id="shiftName"  value="${shift.shiftName}" onblur="validateShiftName();"  field-name="Shift Name" class="form-control" data-validation="required validate_length length1-100">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Time in</label>
        <div class="col-md-9">
       <input type="text" class="timepicker form-control" id="datetimepicker2"
               name="timeIn" placeholder="HH:MM" value="${shift.timeIn}" field-name="Time in" onblur="checktime()" data-validation = "required">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Time Out</label>
        <div class="col-md-9">
       <input type="text" class="timepicker form-control" id="datetimepicker3"
               name="timeOut" placeholder="HH:MM" value="${shift.timeOut}" field-name="Time Out" onblur="checktime()" data-validation = "required">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Shift Description</label>
       <div class="col-md-9">
 			<textarea rows="2" class="form-control" id="shiftDescription"
            name="shiftDescription" placeholder="Description" data-validation="validate_length length1-500" data-validation-optional="true" field-name="Shift Description">${shift.shiftDescription}</textarea>
       </div>
    </div>
          </div>
        </div>
      </div>
      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" id="save_shift" onclick="saveShift();" class="btn btn-primary" >
            <input type="button" value="Cancel" class="btn btn-default ml10" id="cancel_shift" onclick="cancelShift();">
          </div>
        </div>
      </div>
      </form:form>
<!--       Content Area Ends  -->
    </div>
  </section> 
  
<script>
function validateShiftName(){
	var locationId = '${SourceLeadWeb_orgLocationId}';
	var name = $("#shiftName").val();
	var id = $("#shiftId").val();	
	if(name != null && name != '' && name != 'undefined'){
		if(name.length < 100){
			$.get(sourceleadUrl+"/validateName?&orgLocationId="+locationId+"&from=Shift&name="+name+"&id="+id, function(data) {
	          	if(data == true){
	          		$('#shiftNameDiv').find('p.jquery_form_error_message').remove();
	          		$("#save_shift").removeAttr("disabled");
			        $("#save_shift").attr("disabled", "disabled");
					$("input#shiftName").after( "<p class='jquery_form_error_message'>" + name + " is already existed as Shift</span>");
	                 }else{
	                	$("#save_shift").removeAttr("disabled");
	                	$('#shiftNameDiv').find('p.jquery_form_error_message').remove();
	                 }
	            });
		}
		}
	}
</script>

<script type="text/javascript">
	function saveShift() {
		var userId = '${SourceLeadWeb_user.id}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		 if ($('#addshift_form').validate(false, validationSettings)) {
			var formData = getFormData("addshift_form");
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.post( sourceleadUrl+ "/saveShift?$ "+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId, formData, function(data) {
                       if(data == "success"){
                    	   $("#loading").removeAttr("style");
                    	   location.replace(sourceleadUrl+"/viewShift");
                       }
                    });
            
// 			$("addshift_form").validate().resetForm();
			return true;
		} else {
// 			$('#submissionOffersListDiv').find('p.text-primary').remove();
			return false;
		}
	}
	$('body').on('blur', '#addshift_form', function() {
		$("#addshift_form").showHelpOnFocus();
		$("#addshift_form").validateOnBlur(false, validationSettings);
	}); 
</script>

<script>
function cancelShift(){
	location.replace(sourceleadUrl+"/viewShift");
}

</script>
<script src="resources/js/jquery.timepicker.min.js?staticTime=${staticTime}"></script>
<script src="resources/js/bootstrap-datetimepicker.min.js?staticTime=${staticTime}"></script>
<script type="text/javascript">
            $(function () {
                $('#datetimepicker3,#datetimepicker2').datetimepicker({
                    format: 'LT'
                });
            });
 </script> 
<script>     
function checktime(){
	$("#"+maxRowNum+"startTime").timepicker({
		'showDuration': true,
		'disableTextInput': true
	}).on('changeTime', function() {
        $("#"+maxRowNum+"endTime").timepicker('option', 'minTime', $(this).val());
    });
	
	$("#"+maxRowNum+"endTime").timepicker({
		'showDuration': true,
		 'maxTime': '23:30:00',
		'disableTextInput': true
	}).on('changeTime', function() {
        $("#"+maxRowNum+"startTime").timepicker('option', 'maxTime', $(this).val());
    }); 
}
	
</script>  


<jsp:include page="../masterFooter.jsp" />
