<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 <script>
 function dayCheckBox(){
 var dayValue =  $("#halfDayCheckBox").val();
	 if(dayValue == "off"){
		 $("#halfDayCheckBox").val('on');
		 document.getElementById("amPmDivId").style.display="block";
	 }else{
		 $("#halfDayCheckBox").val('off');
		 document.getElementById("amPmDivId").style.display="none";
	 }
 }
 </script>
<script>
$(document).ready(function() {
	var holidy = '${holidays.halfDayCheckBox}';
	if(holidy == "on"){
		document.getElementById("amPmDivId").style.display="block";
	}else{
		document.getElementById("amPmDivId").style.display="none";
	}
})
</script>
 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Holiday</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Management &raquo; Holiday</div>
    </section>
    <div class="content-area content-new clearfix">
    <form:form id="holiday_form" name="holiday_form" method="post">
    <input type="hidden" name="holidayId" value="${holidays.id}"/>
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Holiday Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" field-name="Holiday Name" data-validation="required validate_Space validate_length length1-250" id="holidayName" name="holidayName" value="${holidays.holidayName}">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Date</label>
              <div class="col-md-9">
                      <div class="width110px" id="errFromDate">
                      <input type="text" class="form-control calendar" value="${holidays.fromDate}" field-name="From Date" data-validation="required" name="fromDate" id="fromDate" placeholder="From Date">
                      </div>
                      <div class="width110px" id="errToDate">
                      <input type="text" class="form-control calendar" value="${holidays.toDate}" field-name="To Date" data-validation="required" name="toDate" id="toDate" placeholder="To Date">
                      </div>
                      <div class="width90px checkBox">
                      <c:if test="${!empty holidays.halfDayCheckBox}">
                      <input type="checkbox" id="halfDayCheckBox" name="halfDayCheckBox" checked="checked" value="${holidays.halfDayCheckBox}" onclick="dayCheckBox();">Half Day
                      </c:if>
                      <c:if test="${empty holidays.halfDayCheckBox}">
                      <input type="checkbox" id="halfDayCheckBox" name="halfDayCheckBox"  value="off" onclick="dayCheckBox();">Half Day
                      </c:if>
                      </div>
                      <div class="width50px dropdown-select dropdown-select-custom" id="amPmDivId" style="display:none">
                      <select name="halfDayAmPm">
                              <option value="AM">AM</option>
                              <option value="PM">PM</option>
                          </select>
                      </div>
                  </div>
            </div>
            
           <div class="form-group">
              <label class="col-md-3">Description</label>
              <div class="col-md-9">
                  <textarea rows="2" class="form-control" name="description" placeholder="Description" value="${holidays.description}">${holidays.description}</textarea>
              </div>
            </div>
                  
                  <div class="form-group">
                  <label class="col-md-3">Status</label>
                  <div class="col-md-9">
                                    <label class="paddingRight col-md-4">
                                      <input type="radio" value="Optional" name="holidayStatus" checked="checked"> Optional</label>
                                    <label class="paddingLeft col-md-4">
                                      <input type="radio" value="Restricted" name="holidayStatus"> Restricted </label>
                                      </div>
                                  </div>
                                  
              </div>
              
              
          </div>
        </div>

      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" class="btn btn-primary" id="save_holiday" onclick="saveHoliday();">
            <input type="button" value="Cancel" onclick="cancel();" class="btn btn-default ml10">
          </div>
        </div>
      </div>
      </div>
      <!-- Content Area Ends--> 
      </form:form>
    </div>
  </section>
  <script type="text/javascript">
	function saveHoliday() {
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		if ($('#holiday_form').validate(false, validationSettings)) {
		  	var formData = $($('#holiday_form')[0]).serialize();
		  	$("#holiday_form").attr("disabled", "disabled");
			$("#holiday_form").addClass('button').val('Processing..');
			$.post(sourceleadUrl+"/saveHoliday?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"", formData, function(data) {
					location.replace(sourceleadUrl+ "/viewHoliday");
			});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#holiday_form', function() {
		$("#holiday_form").showHelpOnFocus();
		$("#holiday_form").validateOnBlur(false, validationSettings);
	}); 
</script>
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
<script>
function cancel(){
	location.replace(sourceleadUrl+ "/viewHoliday");
}
</script>
<script>
	$(function() {
		var locationDate = '${SourceLeadWeb_orgLocations.dateFormat}';
		var finalDateFormat = '';
		if (locationDate.match("yyyy-MM-dd")) {
			finalDateFormat = 'yy-mm-dd';
		}
		if (locationDate.match("dd-MM-yyyy")) {
			finalDateFormat = 'dd-mm-yy';
		}
		if (locationDate.match("MM-dd-yyyy")) {
			finalDateFormat = 'mm-dd-yy';
		}
		if (locationDate.match("yyyy-dd-MM")) {
			finalDateFormat = 'yy-dd-mm';
		}
		$('#fromDate').datepicker({
			dateFormat : finalDateFormat,
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate() + 1);
				$("#toDate").datepicker("option", "minDate", dt);
				removeFromDate();
			}
		});

		$('#toDate').datepicker({
			dateFormat : finalDateFormat,
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				removeToDate();
			}
		});

	});
	
	function removeFromDate() {
		$('#errFromDate').find('p.jquery_form_error_message').remove();
	}
	function removeToDate() {
		$('#errToDate').find('p.jquery_form_error_message').remove();
	}
</script>
<script type="text/javascript">
function getHolidayGroup(){
	
}
</script>

<jsp:include page="../masterFooter.jsp" />
