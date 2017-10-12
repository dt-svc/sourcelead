<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 <script>
 function fromDayCheckBox(){
 var dayValue =  $("#fromHalfDayCheckBox").val();
	 if(dayValue == "off"){
		 $("#fromHalfDayCheckBox").val('on');
		 document.getElementById("fromAmPmDivId").style.display="block";
	 }else{
		 $("#fromHalfDayCheckBox").val('off');
		 document.getElementById("fromAmPmDivId").style.display="none";
	 }
 }
 </script>
<script>
$(document).ready(function() {
	var holidy = '${holidayDTO.fromHalfDayCheckBox}';
	if(holidy == "on"){
		document.getElementById("fromAmPmDivId").style.display="block";
	}else{
		document.getElementById("fromAmPmDivId").style.display="none";
	}
})
</script>
<script>
 function toDayCheckBox(){
 var dayValue =  $("#toHalfDayCheckBox").val();
	 if(dayValue == "off"){
		 $("#toHalfDayCheckBox").val('on');
		 document.getElementById("toAmPmDivId").style.display="block";
	 }else{
		 $("#toHalfDayCheckBox").val('off');
		 document.getElementById("toAmPmDivId").style.display="none";
	 }
 }
 </script>
<script>
$(document).ready(function() {
	var holidy = '${holidayDTO.toHalfDayCheckBox}';
	if(holidy == "on"){
		document.getElementById("toAmPmDivId").style.display="block";
	}else{
		document.getElementById("toAmPmDivId").style.display="none";
	}
})
</script>
 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Holiday</h2>
      </div>
     <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='/leaveConfiguration'/>"> Leave Configuration</a> &raquo;<a href="<c:url value='/viewHoliday'/>">Holiday List</a> &raquo; Add Holiday</div>
    </section>
    <div class="content-area content-new clearfix">
    <form id="holiday_form" name="holiday_form" method="post">
    <input type="hidden" name="holidayId" value="${holidayDTO.id}"/>
      <div class="clearfix form-horizontal inner-content">
        <div class="clearfix">
          <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
            <div class="form-group">
              <label class="col-md-3">Holiday Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9" id="holidayNameDiv">
                <input type="text" class="form-control" field-name="Holiday Name"  onblur="validateHolidayName();"  data-validation="required validate_Space validate_length length1-250" id="holidayName" name="holidayName" value="${holidayDTO.holidayName}">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Date<span class="text-danger">*</span></label>
              <div class="col-md-9">
                      <div class="clearfix row">
                      
                      <div class="col-sm-5 paddingRight" id="errFromDate">
                      <input type="text" class="form-control calendar" value="${holidayDTO.fromDateStr}" field-name="From Date" data-validation="required" name="fromDate" id="fromDate" placeholder="From Date">
                      </div>
                      
                      <div class="col-sm-4 paddingLeft paddingRight checkBox">
                      <c:if test="${!empty holidayDTO.fromHalfDayCheckBox}">
                      <input type="checkbox" id="fromHalfDayCheckBox" name="fromHalfDayCheckBox" checked="checked" value="${holidayDTO.fromHalfDayCheckBox}" onclick="fromDayCheckBox();">Half Day
                      </c:if>
                      <c:if test="${empty holidayDTO.fromHalfDayCheckBox}">
                      <input type="checkbox" id="fromHalfDayCheckBox" name="fromHalfDayCheckBox"  value="off" onclick="fromDayCheckBox();">Half Day
                      </c:if>
                      </div>
                      
                      <div class="col-sm-3 dropdown-select dropdown-select-custom paddingLeft" id="fromAmPmDivId" style="display:none">
                      <select name="fromHalfDayAmPm">
                      
                      <c:if test="${holidayDTO.fromHalfDayAmPm  == 'AM'}">
                       <option value="AM" selected>&nbsp;&nbsp;AM</option>
                       <option value="PM">&nbsp;&nbsp;PM</option>
                      </c:if>
                       <c:if test="${holidayDTO.fromHalfDayAmPm  == 'PM'}">
                       <option value="AM">&nbsp;&nbsp;AM</option>
                       <option value="PM" selected>&nbsp;&nbsp;PM</option>
                       
                      </c:if>
                      <c:if test="${empty holidayDTO.fromHalfDayAmPm}">
	                      <option value="AM">&nbsp;&nbsp;AM</option>
	                      <option value="PM">&nbsp;&nbsp;PM</option>
                      </c:if>
                              <!-- <option value="AM">AM</option>
                              <option value="PM">PM</option> -->
                          </select>
                      </div>
                      
                      </div>
                      
                      <div class="clearfix row mt10 mb10">
                      <div class="col-sm-5 paddingRight" id="errToDate">
                      <input type="text" class="form-control calendar" value="${holidayDTO.toDateStr}" field-name="To Date" data-validation="required" name="toDate" id="toDate" placeholder="To Date">
                      </div>
                      <div class="col-sm-4 paddingLeft paddingRight checkBox">
                      <c:if test="${!empty holidayDTO.toHalfDayCheckBox}">
                      <input type="checkbox" id="toHalfDayCheckBox" name="toHalfDayCheckBox" checked="checked" value="${holidays.toHalfDayCheckBox}" onclick="toDayCheckBox();">Half Day
                      </c:if>
                      <c:if test="${empty holidayDTO.toHalfDayCheckBox}">
                      <input type="checkbox" id="toHalfDayCheckBox" name="toHalfDayCheckBox"  value="off" onclick="toDayCheckBox();">Half Day
                      </c:if>
                      </div>
                      <div class="col-sm-3 dropdown-select dropdown-select-custom paddingLeft" id="toAmPmDivId" style="display:none">
                      <select name="toHalfDayAmPm">
                      
                      <c:if test="${holidayDTO.toHalfDayAmPm  == 'AM'}">
                       <option value="AM" selected>&nbsp;&nbsp;AM</option>
                       <option value="PM">&nbsp;&nbsp;PM</option>
                      </c:if>
                       <c:if test="${holidayDTO.toHalfDayAmPm  == 'PM'}">
                       <option value="AM">&nbsp;&nbsp;AM</option>
                       <option value="PM" selected>&nbsp;&nbsp;PM</option>
                       
                      </c:if>
                      <c:if test="${empty holidayDTO.toHalfDayAmPm}">
	                      <option value="AM">&nbsp;&nbsp;AM</option>
	                      <option value="PM">&nbsp;&nbsp;PM</option>
                      </c:if>
                              <!-- <option value="AM">AM</option>
                              <option value="PM">PM</option> -->
                          </select>
                      </div>
                      </div>
                  </div>
            </div>
         </div>
         <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">   
                  
                  <div class="form-group">
                  <label class="col-md-3">Status ${holidayDTO.holidayStatus}</label>
                  <div class="col-md-9">
                                   
                                     <c:if test="${holidayDTO.holidayStatus  == 'Optional'}">
	                                   <label class="paddingRight col-md-4">
	                                     	<input type="radio" value="Optional"  checked name="holidayStatus" > Optional
	                                    </label>
	                                   <label class="paddingLeft col-md-4">
	                                      <input type="radio" value="Restricted"  name="holidayStatus"> Restricted 
	                                    </label>
                                      </c:if>
                                      <c:if test="${holidayDTO.holidayStatus  == 'Restricted'}">
	                                       <label class="paddingRight col-md-4">
		                                     	<input type="radio" value="Optional"   name="holidayStatus" > Optional
		                                    </label>
	                                        <label class="paddingLeft col-md-4">
		                                      <input type="radio" value="Restricted" checked name="holidayStatus"> Restricted 
	                                      </label>
                                      </c:if>
                                      <c:if test="${empty holidayDTO.holidayStatus}">
                                      <label class="paddingRight col-md-4">
	                                     	<input type="radio" value="Optional"  name="holidayStatus" > Optional
	                                    </label>
	                                   <label class="paddingLeft col-md-4">
	                                      <input type="radio" value="Restricted"  name="holidayStatus"> Restricted 
	                                    </label>
                                      </c:if>
                                      </div>
                                  </div>
                                  
              <div class="form-group">
              <label class="col-md-3">Description</label>
              <div class="col-md-9">
                  <textarea rows="2" class="form-control" name="description" placeholder="Description" value="${holidayDTO.description}">${holidayDTO.description}</textarea>
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
      </form>
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
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+0d');
		            dt.setDate(dt.getDate() + 0);
		            $("#toDate").datepicker("option", "minDate", dt);
		            removeFromDate();
		        }
		  });
		  
		  $('#toDate').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '-0d');
		            dt.setDate(dt.getDate() - 0);
		            $("#fromDate").datepicker("option", "maxDate", dt);
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
<!-- <script type="text/javascript">
function validateHolidayName(){
	var holidayName = (document.getElementById("holidayName").value).replace(/[{}]/g, "");
	var holiday = encodeURIComponent(holidayName);
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	if(holiday.match(/^[^\s-](.*)*$/) != null){
		$('#holidayNameDiv').find('p.jquery_form_error_message').remove();
	if (holiday != null && holiday != '') {
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/validateHolidayName?"+tokenParamName+"="+tokenValue+"&holidayName="+holiday+"&orgLocationId="+orgLocationId,
			success : function(data) {
				var result = data;
				if (result == 'true') {
					//duplicate holiday
					$("#save_holiday").removeAttr("disabled");
			        $("#save_holiday").attr("disabled", "disabled");
					$("input#holidayName").after( "<p class='jquery_form_error_message'>" + holidayName + " is already existed as holiday</span>");
					document.getElementById("holidayName").setAttribute( 'record-exist', 'yes');
					document.getElementById("holidayName").setAttribute( 'record-exist-errorMsg', holidayName +"  already existed as holiday");
					$('input#holidayName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else {
					$("#save_holiday").removeAttr("disabled");
					$('input#holidayName').removeAttr("class record-exist record-exist-errormsg");
					$('input#holidayName').attr('class','valid form-control');
					$('#holidayNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_holiday").click(function() {
						
					});
				}
				
				$("#loading").removeAttr("style");
			},

		});
	}
  }
}
</script>  -->

<jsp:include page="../masterFooter.jsp" />
