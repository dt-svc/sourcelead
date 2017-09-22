<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 

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
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Holiday Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" field-name="Holiday Name" data-validation="required" name="holidayName" value="${holiday.holidayName}">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Date</label>
              <div class="col-md-9">
                      <div class="width110px">
                      <input type="text" class="form-control calendar" value="${holiday.fromDate}" field-name="From Date" data-validation="required" name="fromDate" id="fromDate" placeholder="From Date">
                      </div>
                      <div class="width110px">
                      <input type="text" class="form-control calendar" value="${holiday.toDate}" field-name="To Date" data-validation="required" name="toDate" id="toDate" placeholder="To Date">
                      </div>
                      <div class="width90px checkBox">
                      <input type="checkbox" name="halfDayCheckBox">Half Day
                      </div>
                      <div class="width50px dropdown-select dropdown-select-custom">
                      <select>
                              <option selected>AM</option>
                              <option selected>PM</option>
                          </select>
                      </div>
                  </div>
            </div>
            
            <div class="form-group hidden">
              <label class="col-md-3"></label>
              <div class="col-md-9">
                  <div class="row">
                      <div class="col-md-8 checkBox">
                      <input type="checkbox" name="halfDayAmPm">Half Day
                      </div>
                      <div class="col-md-4 dropdown-select">
                          <select class="form-control">
                              <option selected>AM</option>
                              <option selected>PM</option>
                          </select>
                      </div>
                  </div>
              </div>
            </div>
              
           <div class="form-group">
              <label class="col-md-3">Description</label>
              <div class="col-md-9">
                  <textarea rows="2" class="form-control" name="description" placeholder="Description"></textarea>
              </div>
            </div>
              
                  
                  <div class="form-group">
                                    <label class="paddingRight">Status
                                      <input type="radio" value="Optional" name="status" checked="checked" onclick="">
                                      Optional</label>
                                    <label class="paddingLeft">
                                      <input type="radio" value="Restricted" name="status"  onclick="">
                                      Restricted </label>
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
      
      <!-- Content Area Ends--> 
      </form:form>
    </div>
  </section>
  
  <script type="text/javascript">
	function saveHoliday() {
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		  	var formData = $($('#holiday_form')[0]).serialize();
		  	$("#holiday_form").attr("disabled", "disabled");
			$("#holiday_form").addClass('button').val('Processing..');
			$.post(sourceleadUrl+"/saveHoliday?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"", formData, function(data) {
				if(data == true){
					location.replace(sourceleadUrl+ "/viewHoliday");
				}
			});
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
<script>
function cancel(){
	location.replace(sourceleadUrl+ "/viewHoliday");
}
</script>
<script>
$( function() {
var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
var finalDateFormat = '';
if(locationDate.match("yyyy-MM-dd")){
finalDateFormat = 'yy-mm-dd';
}if(locationDate.match("dd-MM-yyyy")){
finalDateFormat = 'dd-mm-yy'; 
}if(locationDate.match("MM-dd-yyyy")){
finalDateFormat = 'mm-dd-yy'; 
}if(locationDate.match("yyyy-dd-MM")){
finalDateFormat = 'yy-dd-mm'; 
}
$('#fromDate').datepicker({
dateFormat: finalDateFormat,
changeMonth: true,
changeYear: true, 
});

$('#toDate').datepicker({
dateFormat: finalDateFormat,
changeMonth: true,
changeYear: true, 
});

} );
</script>
<script type="text/javascript">
function getHolidayGroup(){
	
}
</script>

<jsp:include page="../masterFooter.jsp" />
