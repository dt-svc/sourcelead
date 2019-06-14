<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <c:set var ="lcaListCount" value ="${fn:length(lcaList)}"/>
 <div class="immigraTabsHead">
	<c:if test="${myImmigrationPage == 'N'}">
		<a href="javascript:void(0)" id="assignLca"><i class="glyphicon glyphicon-plus"></i> Assign LCA</a>
	</c:if>
</div>
		
	  	<div class="immigraTable overflow_Auto">
		  <table id ="visaTbl">
			<tbody>
			  <tr>
				<th>LCA Number</th>
				<th>LCA Title</th>
				<th>Location</th>
				<th>Prevailing Wage</th>
				<th>Valid From</th>
				<th>Valid To</th>
				<th>LCA Status</th>
				<th title="Total Positions">#Positions</th>
				<!-- <th title="Assigned Positions">#Employment</th> -->
				<th title="Available Positions">#Available</th>
				<th>Status</th>
			  </tr>
			  <c:forEach var="lcadetails" items="${lcaList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.lcaNo}"></div>${lcadetails.lcaNo}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.lcaTitle}"></div>${lcadetails.lcaTitle}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.location}"></div>${lcadetails.location}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.prevailingWage}"></div>${lcadetails.prevailingWage}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.startDate}"></div>${lcadetails.startDate}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.endDate}"></div>${lcadetails.endDate}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.lcaStatus}"></div>${lcadetails.lcaStatus}</td>
				
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.noOfPositions}"></div>${lcadetails.noOfPositions}</td>
				<%-- <c:if test="${!empty lcadetails.newEmploymentCount}">
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.newEmploymentCount}"></div>${lcadetails.newEmploymentCount}</td>
				</c:if>
				<c:if test="${empty lcadetails.newEmploymentCount}">
				<td><div class="tbldata-dotdot-sm" title = "0"></div>0</td>
				</c:if> --%>
				
				<td><div class="tbldata-dotdot-sm"></div><c:out value="${lcadetails.availablePositions}" /></td>
				
				<c:if test="${lcadetails.status == '1'}">
				 <td><div class="tbldata-dotdot-sm" title = "${lcadetails.status}"></div>Active</td>
				</c:if>
				<c:if test="${lcadetails.status == '0'}">
				 <td><div class="tbldata-dotdot-sm" title = "${lcadetails.status}"></div>In-Active</td>
				</c:if>
			 </tr>
			</c:forEach>
			 <c:if test="${empty lcaList}">
				<tr>
				 <td colspan="9" valign="top" class="dataTables_empty"><div class="tbldata-dotdot-sm" ></div></td>
				</tr>
			 </c:if>		
		</tbody>
		</table>
	</div>
	
	<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#visasTab');" class="btn btn-default btn-sm">
              	<input type="button" value="Continue" onclick="goToSummaryNextTab('#employersTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div>
		
		
		
<!-- Add LCA From Immigration Summary Starts Here (pop Up) -->

		<!-- popup code start -->
		<form id="lcaSave_Form" name = "lcaSave_Form" method = "post">
		<div class="popUpOut" id="popLcaView">
		<!--hidden id for edit -->
		<input type="hidden" name="lcaId" id="currlcaId" value="${immigrationDTO.lcaId}">
		<div class="popupHead">
			<h2>Assign LCA</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="closeLcapopup();"></a>
		</div>
		
		<div class="popupMid clearfix">

          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">LCA No<span class="text-danger paddingLeft">*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name="lcaNo"  id="lcaNumberAuto" class="form-control"  placeholder="Autofill Enabled For 2 Chars" field-name="LCA NO" data-validation="required" >
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date<span class="text-danger paddingLeft">*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup" id="lcaStartDateDiv">
				<input type="text" name="startDate" id="lcaStartDate" readonly='true'  class="form-control calendar" placeholder="Start Date" field-name="Start Date" data-validation="required">
				 <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearLcaDateValue('startDate');"></i></span>
          	</div>
			</div>	
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date<span class="text-danger paddingLeft">*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup" id="lcaEndDateDiv">
				<input type="text" id="lcaEndDate" name="endDate"  readonly='true' class="form-control calendar" placeholder="End Date" field-name="End Date" data-validation="required">
				<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearLcaDateValue('endDate');"></i></span>
			</div>
				</div>
			</div>
        <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Location<span class="text-danger">&nbsp;*</span></label>  
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "location" id= "locationlca" field-name= "location" placeholder="Location" onclick="initLocationAuto('location','country','state','city','zipcode','','','','streetAddress');" data-validation="required validate_Space validate_length length2-100" class="form-control" disabled="disabled"/> 
              		   <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('locationlca','country','state','city','zipcode','streetAddress');" ></i>         		
				</div>
			</div>	
			
			
			<div class="col-sm-12 noPad mt clearfix">
            <label class="col-md-4 padding_left">Prevailing  Wage <span class="text-danger paddingLeft">*</span> </label>
            <div class="col-md-8 padding_right">
            <div class="input-group er ratetype">
                <div class="input-group-btn">
                   <select class="form-control tblminwidth-75 sel" id="privilingWageCurrency" name="privilingWageCurrency" data-style="btn-primary" >
						<option value="INR" ${privilingWageCurrency == 'INR' ? 'selected' : ''}>INR</option>
						<option value="AUD" ${privilingWageCurrency == 'AUD' ? 'selected' : ''}>AUD</option>
						<option value="USD" ${privilingWageCurrency == 'USD' ? 'selected' : ''}>USD</option>
						<option value="GBP" ${privilingWageCurrency == 'GBP' ? 'selected' : ''}>GBP</option>
						<option value="SGD" ${privilingWageCurrency == 'SGD' ? 'selected' : ''}>SGD</option>
						<option value="EUR" ${privilingWageCurrency == 'EUR' ? 'selected' : ''}>EUR</option> 
					</select>
                </div>
                <input type="text" name="prevailingWage" id="prevailingWage" value="${lcaDTO.prevailingWage}" class="form-control" value="" field-name="Prevailing Wage"
								data-validation="required validate_Space validate_float validate_length length1-8" 
							    placeholder="Wage Or Rate" >
                <div class="input-group-btn ratetype">
                 <select class="form-control tblminwidth-75 sel" id="privilingWageRateType" name="privilingWageRateType" field-name="Rate Per" data-style="btn-primary" >
						 <option  value="">Rate Per</option>
						<option ${lcaDTO.privilingWageRateType == 'Hourly' ? 'selected' : ''} value="Hourly">Hourly</option>
						<option ${lcaDTO.privilingWageRateType == 'Weekly' ? 'selected' : ''} value="Weekly">Weekly</option>
						<option ${lcaDTO.privilingWageRateType == 'Bi-Weekly' ? 'selected' : ''} value="Bi-Weekly">Bi-Weekly</option>
						<option ${lcaDTO.privilingWageRateType == 'Semi-Monthly' ? 'selected' : ''} value="Semi-Monthly">Semi-Monthly</option>
						<option ${lcaDTO.privilingWageRateType == 'Monthly' ? 'selected' : ''} value="Monthly">Monthly</option>
						<option ${lcaDTO.privilingWageRateType == 'Bi-Monthly' ? 'selected' : ''} value="Bi-Monthly">Bi-Monthly</option>
						<option ${lcaDTO.privilingWageRateType == 'Yearly' ? 'selected' : ''} value="Yearly">Yearly</option> 
					</select>
                </div>
              </div>
            
          	</div>
          </div>
          
          <div class="clearfix"></div>
		  <div class="col-md-4 col-md-offset-4">
		   	<div class="mt5 clearfix">
			   <input id="multipleCheckBox123" title="Is Current" name="multiple"  type="checkbox" value = "Y" >
			   <label class="padding_left">Is Current</label>
			</div>
		   </div>
									
		</div>
		
		<div class="clearfix"></div>
		<div class="immiPopFoot mt clearfix">
            <input value="Save" id= "lcaSave_Button" class="" type="button" onclick ="immigration.saveLcaFromImmigration();">
            <input value="Cancel" class="popCan" type="button" onclick="closeLcapopup();">
          </div>		
	</div>
	</div><!-- popup code end -->
	</form>
	<script type="text/javascript">
	
	var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});

		});
			
	      $("#assignLca").click(function(){
				$(".popMask").fadeIn(200);
				$("#popLcaView").fadeIn(100);
			});
	function closeLcapopup(){
		$(".popMask").fadeOut(200);
		$("#popLcaView").fadeOut(100);
		$("#lcaSave_Form")[0].reset();
		/* $("#lcaId").val(''); */
		$('#popLcaView').find('p.jquery_form_error_message').remove();
		
	}
	
</script>
	<script type="text/javascript">

var locationDate ='${SourceLeadWeb_dateFormat}'; 
var finalDateFormatLcaSave = getJSDateFormat(locationDate);

function validDateClass(){
	 var startDate = document.getElementById("lcaStartDate").value;
	 if(startDate){
		 $('#lcaStartDateDiv').find('p.jquery_form_error_message').remove(); 
	 }
	 
	 var endDate = document.getElementById("lcaEndDate").value;
	 if(endDate){
		 $('#lcaEndDateDiv').find('p.jquery_form_error_message').remove(); 
	 }
}

$('#lcaStartDate').datepicker({
    dateFormat: finalDateFormatLcaSave,
    changeMonth: true,
	  changeYear: true, 
    onSelect: function (selected) {
          //var dt = new Date(selected);
          var dt = $(this).datepicker('getDate', '+1d');
          dt.setDate(dt.getDate() + 1);
          $("#lcaEndDate").datepicker("option", "minDate", dt);
          validDateClass();
      }
});

$('#lcaEndDate').datepicker({
    dateFormat: finalDateFormatLcaSave,
    changeMonth: true,
	  changeYear: true, 
      onSelect: function (selected) {
          //var dt = new Date(selected);
          var dt = $(this).datepicker('getDate', '-1d');
          dt.setDate(dt.getDate() - 1);
          $("#lcaStartDate").datepicker("option", "maxDate", dt);
          validDateClass();
      }
});


</script>	

<!-- <script type="text/javascript">
   function validateNumber(object){ 
	   object.value = object.value.replace(/[^0-9\.]/g,'');
	}
   
   $('#lcaNumber').mask('I-999-99999-999999', {
	    translation: {
	        '#': {
	            pattern: "/I{1}[0-9]{14}/", optional: true
	        }
	    },
	   placeholder:""
	}); 
 </script> -->
<script>
   function clearLcaDateValue(id){
	   $("#"+id).val(""); 
   }
</script>
<!-- LcaNumber AutoPopulate Ui script -->
<script type="text/javascript">
var locationDate ='${SourceLeadWeb_dateFormat}'; 
var finalDateFormatOrg = getOrgDateFormat(locationDate);
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
 $("#lcaNumberAuto").autocomplete({ 
	 minLength: 2, 
	 source : function(request, response) {
		 $.ajax({
			 url :vmsUrl+immiModulePath+ "/getLcaNumbersList",
			 type : "GET",
			 data : {
				 lcaNum : request.term,
				 expiryStatus:'1',
				 candidateId :$("#candidateId").val()
			 	},
			 dataType : "json",
			 success : function(data) {
			 	response(data);
			 }
		 });
	 },
	   select: function(event, ui ) {
		
		    $("#lcaNumberAuto").val(ui.item.label);
		 	$("#currlcaId").val(ui.item.value);//currentLcaId for LcaHistory 
		 	$("#lcaNumberAuto").attr("selecedVal", ui.item.label);
		 
			 $.ajax({
			    	type: "GET",
			        url: vmsUrl+immiModulePath+"/getLcaByLcaId?id="+ui.item.value, 
			        success: function(data) {
			        	
			        	if(data.lcaLocation != null && typeof data.lcaLocation != 'undefined' && data.lcaLocation !=''){
			    			 $("#locationlca").val(data.lcaLocation);
			    			 $("#locationlca" ).prop( "disabled", true);
			    		}
			    		
			    		// setting the startDate,endDate for current Lca.
			    		
			    		 if(data.lcaStartDateStr != null && typeof  data.lcaStartDateStr != 'undefined' && data.lcaStartDateStr!=''){
			    			//var startDateStr =  moment(data.lcaStartDateStr).format(finalDateFormatOrg);
			    		   $("#lcaStartDate").val(data.lcaStartDateStr);
			    		   $("#lcaStartDate").datepicker('disable');
			    		}
			    		if(data.lcaEndDateStr != null && typeof  data.lcaEndDateStr != 'undefined' && data.lcaEndDateStr!=''){
			    		//	var lcaEndDateStr =  moment(data.lcaEndDateStr).format(finalDateFormatOrg);
			    		   $("#lcaEndDate").val(data.lcaEndDateStr);
			    		   $("#lcaEndDate").datepicker('disable');
			    		} 
			    		
			    		if(data.privilingWageCurrency != null && typeof data.privilingWageCurrency != 'undefined' && data.privilingWageCurrency !=''){
			    			 $("#privilingWageCurrency").val(data.privilingWageCurrency);
			    			 $("#privilingWageCurrency" ).prop( "disabled", true);
			    		}
			    		if(data.prevailingWage != null && typeof data.prevailingWage != 'undefined' && data.prevailingWage !=''){
			    			 $("#prevailingWage").val(data.prevailingWage);
			    			 $("#prevailingWage" ).prop( "disabled", true);
			    		}
			    		
			    		if(data.privilingWageRateType != null && typeof data.privilingWageRateType != 'undefined' && data.privilingWageRateType !=''){
			    			 $("#privilingWageRateType").val(data.privilingWageRateType);
			    			 $("#privilingWageRateType" ).prop( "disabled", true);
			    		}
			    		
			        }
			 });
		 	return false;
		 },
	 focus: function(event, ui){
	          return false;
	    }
	 });
 
  $("input[id='lcaNumberAuto']").focusout(function(){
	 var seletedVal= $("#lcaNumberAuto").attr("selecedVal")
	  if(typeof seletedVal =='undefined') {
		  resetLcaDetails();
	  }else if (typeof seletedVal !='undefined' && $("#lcaNumberAuto").attr("selecedVal")!=$("#lcaNumberAuto").val().trim()){
		  resetLcaDetails();
	  }
 });
 
 function resetLcaDetails(){
		$("#lcaNumberAuto").val("");
		$("#lcaStartDate").val("");
		$("#lcaEndDate").val("");
		$("#locationlca").val("");
		$("#startDate").datepicker('enable');
		$("#endDate").datepicker('enable');
	}
 
</script> 
<script>
  var lcaCount = '<c:out value ="${lcaListCount}"/>';
  var initCount  = '<c:out value ="${requestScope.lcaOnLoadCount}"/>';
  if(lcaCount>0 || initCount!= ""){
        $('#lcafilled').show() // show and hides circle icon.
    }
	else {
        $('#lcafilled').hide()
	}
</script>
