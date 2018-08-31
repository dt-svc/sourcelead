  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 
 <script>
function associateSalSummary(userId){
	$.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateSalary?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) { 
            	var result = "";
            	result = result + '<div class="clearfix mt minHeightcol5">';
            	result = result + '		<div class="col-md-3 col-md-offset-9">';
            	result = result + '	<ul class="list-inline list-unstyled pull-right mb0">';
            	if(json.id != null && json.id != '' && json.id != 'undefined'){
            		result = result + '		<li><a href="javascript:void(0)" class="btn btn-primary btn-block" onClick="updateSalaryDetails(\''+json.id+'\',\''+json.payType+'\',\''+json.payFrequency+'\',\''+json.standardHours+'\',\''+json.annualSal+'\',\''+json.perPaySal+'\',\''+json.hourlyRate+'\',\''+userId+'\',\''+json.payGroupUUID+'\');">Update</a></li>';
                	result = result + '		<li><a href="javascript:void(0)" class="btn btn-warning btn-block" onClick="salaryHistory(\''+json.id+'\');">History</a></li>';
            	}else{
            		result = result + '		<li><a href="javascript:void(0)" class="btn btn-primary btn-block" onClick="editSalaryDetails(\''+userId+'\');">Update</a></li>';
            	}
            	result = result + '		</ul>';
            	result = result + '	</div>';
            	result = result + '	<div class="clearfix"></div>';
            	result = result + '   <div class="col-md-5">';
            	result = result + '  <label>Pay Type</label>';
            	result = result + '   <div>';
            	if(json.payType != null && json.payType != '' && json.payType != 'undefined'){
            		result = result + '     <p class="form-control-static text-muted">'+json.payType+'</p>';
            	}else{
            		result = result + '     <p class="form-control-static text-muted">--</p>';
            	}
            	result = result + '   </div>';
            	result = result + ' </div>';

            	result = result + ' <div class="col-md-5">';
            	result = result + '   <label>Pay Frequency</label>';
            	result = result + '   <div>';
            	if(json.payFrequency != null && json.payFrequency != '' && json.payFrequency != 'undefined'){
            	result = result + '     <p class="form-control-static text-muted">'+json.payFrequency+'</p>';
            	}else{
            		result = result + '     <p class="form-control-static text-muted">--</p>';
            	}
            	result = result + '    </div>';
            	result = result + '  </div>';
            	
            	result = result + ' <div class="col-md-5">';
            	result = result + '   <label>Pay Group</label>';
            	result = result + '   <div>';
            	if(json.payGroup != null && json.payGroup != '' && json.payGroup != 'undefined'){
            	result = result + '     <p class="form-control-static text-muted">'+json.payGroup+'</p>';
            	}else{
            		result = result + '     <p class="form-control-static text-muted">--</p>';
            	}
            	result = result + '    </div>';
            	result = result + '  </div>';
                    
            	if(json.payType != "LcaWage" && json.payType != "HourlyRate"){
            	result = result + ' <div class="col-md-5">';
            	result = result + '    <label>Standard Hours</label>';
            	result = result + '   <div>';
            	if(json.standardHours != null && json.standardHours != '' && json.standardHours != 'undefined'){
            	result = result + '     <p class="form-control-static text-muted">$ '+json.standardHours+'</p>';
            	}else{
            		result = result + '     <p class="form-control-static text-muted">--</p>';
            	}
                result = result + '   </div>';
                result = result + ' </div>';
                result = result + ' <div class="col-md-5">';
                result = result + '  <label>Annual Salary</label>';
                result = result + '  <div>';
                if(json.annualSal != null && json.annualSal != '' && json.annualSal != 'undefined'){
                result = result + '    <p class="form-control-static text-muted">$ '+json.annualSal+'</p>';
                }else{
                	result = result + '    <p class="form-control-static text-muted">--</p>';
                }
                result = result + ' </div>';
                result = result + '   </div>';
                result = result + '   <div class="col-md-5">';
                result = result + '    <label>Per Pay Salary</label>';
                result = result + '    <div>';
                if(json.perPaySal != null && json.perPaySal != '' && json.perPaySal != 'undefined'){
                result = result + '     <p class="form-control-static text-muted">$ '+json.perPaySal+'</p>';
                }else{
                	result = result + '     <p class="form-control-static text-muted">--</p>';
                }
                result = result + '    </div>';
                result = result + '  </div>';
                result = result + ' <div class="col-md-5">';
                result = result + '   <label>Hourly</label>';
                result = result + '   <div>';
                if(json.hourlyRate != null && json.hourlyRate != '' && json.hourlyRate != 'undefined'){
                result = result + '    <p class="form-control-static text-muted">$ '+json.hourlyRate+'</p>';
                }else{
                	result = result + '    <p class="form-control-static text-muted">--</p>';
                }
                result = result + '   </div>';
                result = result + ' </div>';
                result = result + ' </div>';
                
            	 }
                
                // Adding Pay Items
                result = result + '<div class="clearfix"></div>';
                result = result + '<h4 class="col-md-12">Pay Item</h4>';
                result = result + '<div class="clearfix col-md-12 styleTables tableStyles-textLeft">';
                result = result + '<div class="row">';
                result = result + '<table>';
                result = result + '<thead>';
                result = result + '<tr>';
                result = result + '<th>Pay Items</th>';
                result = result + '<th>Schedule</th>';
                result = result + '<th>Override</th>';
                result = result + '<th>Effective Date</th>';
                result = result + '<th>End Date</th>';
                result = result + '<th>Amount</th>';
                result = result + '<th>Action</th>';
                result = result + '</tr>';
                result = result + '</thead>';
                result = result + '<tbody>';
                if(json.associatePayItemDto != null && json.associatePayItemDto != '' && json.associatePayItemDto != 'undefined'){
                	for(var i=0;i<json.associatePayItemDto.length;i++){
                  	  
                  	  var opt = json.associatePayItemDto[i];
                  	  result = result + '<tr>';
                        
                        result = result + '<td>';
                        result = result + '		<div class="tblminwidth-100 text-left">'+opt.payItemName+'</div>';
                        result = result + '</td>';
                        result = result + '<td>';
                        result = result + '		<div class="tblminwidth-125">';
                        result = result + '			<div>'+opt.payItemSchedule+'</div>';
                        result = result + '		</div>';
                        result = result + '</td>';
                        result = result + '	<td>';
                        result = result + '	<div class="tblminwidth-125">';
                        result = result + '		<div>'+opt.overRide+'</div>';
                        result = result + '		</div>';
                        result = result + '	</td>';
                        result = result + '<td>';
                        result = result + '			<div class="width150px" id="effectiveDateDiv'+opt.id+'">';
                        result = result + '			<div>'+opt.effectiveDate+'</div>';
                        result = result + '			</div>';
                        
                        result = result + '<div class="edit-input width150px" id="editEffectiveDateDiv'+opt.id+'" style="display:none;"><input type="text" id ="preEffective'+opt.id+'" onclick = "editEffectiveDate(this)" value="'+opt.effectiveDate+'" class="form-control calendar" readonly="readonly"></div></td>';
                        
                        result = result + '	</td>';
                        result = result + '	<td>';payType
                        result = result + '			<div class="width150px" id="endDateDiv'+opt.id+'">';
                        result = result + '			<div>'+opt.endDate+'</div>';
                        result = result + '			</div>';
                        
                        result = result + '<div class="edit-input width150px" id="editEndDateDiv'+opt.id+'" style="display:none;"><input type="text" id ="preEnd'+opt.id+'" onclick = "editEndDate(this)" value="'+opt.endDate+'" class="form-control calendar"></div></td>';
                        
                        result = result + '	</td>';
                        result = result + '	<td>';
                        result = result + '		<div class="width150px" id="payItemAmountDiv'+opt.id+'">';
                        result = result + '		<div>$ '+opt.payItemAmount+'</div>';
                        result = result + '		</div>';
                        
                        result = result + '<div class="edit-input width150px" id="editpayItemAmountDiv'+opt.id+'" style="display:none;"><input type="text" id ="prePayAmount'+opt.id+'" value="'+opt.payItemAmount+'" class="form-control pull-left"></div></td>';
                        result = result + '</td>';
                        result = result + '	<td>';
                        result = result + '		<div class="tblminwidth-50">';
                        result = result + ' <a title="Edit"><i id="editDiv'+opt.id+'" class="glyphicon glyphicon-edit" onclick="editPayItem('+opt.id+')"></i><i id="saveDiv'+opt.id+'" class="fa fa-floppy-o" style="display:none;" onclick="updatePayItems('+opt.id+');"></i></a>';
                        result = result + ' <a title="Delete"><i class="glyphicon glyphicon-trash text-danger ml" onclick="deletePayItem('+opt.id+','+userId+');"></i></a>';
                        result = result + '		<div></div>';
                        result = result + '		</div>';
                        result = result + '</td>';
                        result = result + '</tr>';
                    }
                }
                result = result + '	</tbody>';
                result = result + '	</table>';
                result = result + ' 	</div>';
                result = result + ' 	</div>';
                
                $("#tab-18").empty(); 
                $("#tab-17").empty(); 
                $("#tab-19").empty(); 
                $("#tab-20").empty(); 
                $("#candidateSideDiv").empty(); 
                $("#tab-20").empty();
                $("#tab-21").empty(); 
                $("#tab-22").empty();
                $("#tab-23").empty(); 
                $("#tab-24").empty();
                $("#tab-25").empty(); 
                $("#tab-25").append(result); 
           },
            
       }); 
  } 
</script>
 
   
              
              
       
      
      <!-- Salary History Popup -->

<!-- Update salary details -->
<!-- Modal content Add Team-->

<div class="sideMask"></div>
<div class="modal updateSalaryDetails">
<div id="loading"></div>
 <form:form id="update_associate_salary_form" name="update_associate_salary_form" method="post">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button" onClick="closeupdateSalaryDetails();">&times;</button>
        <h4 class="modal-title">Update Salary</h4>
      </div>
      <div class="modal-body clearfix">
        <div class="clearfix row minHeightcol5">
            <div class="clearfix">
              <div class="col-md-5">
                <label>Pay Type</label>
                <div>
                 <input class="form-control" name="previousPayType" id="previousPayType" type="hidden">
                 <input class="form-control" name="salaryType" id="salaryType" type="hidden">
                  <input class="form-control" name="id" id="id" type="hidden">
                   <input class="form-control" name="userId" id="userId" type="hidden">
                  <select class="sel" id="payType" name="payType" data-validation="required" onchange="showHideSalaryDIV();" field-name="Pay Type">
                    <option value="">--Select--</option>
                    <option value="HourlyRate">Hourly Rate</option>
                    <option value="Salary">Salary</option>
                    <option value="LcaWage">LCA Wage</option>
                    <option value="Commission Only">Commission Only</option>
                  </select>
                </div>
              </div>
              <div class="col-md-5">
                <label>Pay Frequency</label>
                <div>
                <input class="form-control" name="previousPayFrequency" id="previousPayFrequency" type="hidden">
                  <select class="sel" id="payFrequency" name="payFrequency" onchange="getPayFreqHours();getPayGroups();" data-validation="required" field-name="Pay Type">
                    <option value="">--Select--</option>
                    <option value="Annual">Annually</option>
                    <option value="Monthly">Monthly</option>
                    <option value="Bi-Weekly">Bi-Weekly</option>
                    <option value="Weekly">Weekly</option>
                  </select>
                </div>
              </div>
               <div class="col-md-5">
                    <label>Pay Group</label>
                    <div>
                      <select class="sel" id="payGroupId" name="payGroup" data-validation="required" field-name="Pay Group">
                      </select>
                    </div>
                  </div>
             <div id="salaryDIV">
              <div class="col-md-5">
                <label>Standard Hours</label>
                <div>
                  <input class="form-control" id="standardHours" name="standardHours"  type="text" data-validation="required" field-name="Standard Hours" onblur="calculateSal();">
                  <input class="form-control" name="previousStandardHours" id="previousStandardHours" type="hidden">
                </div>
              </div>
              <div class="col-md-5">
                <label>Annual Salary</label>
                <div id="AnnualSal">
                  <input class="form-control" name="annualSal" id="annualSal" type="text" data-validation="required" field-name="Annual Salary" onblur = "calculateSal();">
                   <input class="form-control" name="previousAnnualSal" id="previousAnnualSal" type="hidden">
                   
                </div>
              </div>
              <div class="col-md-5">
                <label>Per Pay Salary</label>
                <div id="PerPaySal">
                  <input class="form-control" name="perPaySal" id="perPaySal" type="text" data-validation="required" field-name="Per Pay Salary" onblur = "calculatePayPerSal();">
                    <input class="form-control" name="previousPerPaySal" id="previousPerPaySal" type="hidden">
                </div>
              </div>
              <div class="col-md-5">
                <label>Hourly Rate</label>
                <div id="HourlyRate">
                  <input class="form-control" name="hourlyRate" id="hourlyRate" type="text" data-validation="required" field-name="Hourly Rate" onblur = "calculateHourlyRateSal();">
                   <input class="form-control" name="previousHourlyRate" id="previousHourlyRate" type="hidden">
                </div>
              </div>
             
              <div class="col-md-5">
                <label>Effective Date</label>
                <div>
                  <input class="form-control calendar" name="effectiveDate" readonly="readonly" id="effectiveDate" type="text" >
                </div>
              </div>
              <div class="col-md-5">
                <label>% Increase</label>
                <div>
                  <input class="form-control " name="increasePer" id="increasePer" type="text" onblur = "calculatePercentSal();">
                </div>
              </div>
               <div class="col-md-5">
                <label>Change Reason</label>
                <div>
                  <textarea class="form-control" rows="2" placeholder="Enter" name="changeReason" id="changeReason"></textarea>
                </div>
              </div>
              </div>
              <div class="clearfix"></div>
            </div>
        </div>
      </div>
      <div class="modal-footer">
        <button data-dismiss="modal" class="btn btn-success btnSuccess" type="button" id="update_associate_sal_button" onclick="updateAssociateSal();">Update</button>
        <button data-dismiss="modal" class="btn btn-default" type="button" onClick="closeupdateSalaryDetails();">Cancel</button>
      </div>
    </div>
  </div>
  </form:form>
</div>
<!-- Add Team modal popup ends -->


<script>
function showHideSalaryDIV(){
	var payType = $("#payType").val();
	 if(payType == "HourlyRate" || payType == "LcaWage"){
		$("#salaryDIV").hide();
		$("#standardHours").val('');    
		$("#annualSal").val('');
		$("#perPaySal").val('');
		$("#hourlyRate").val('');
		$("#standardHours").removeAttr('data-validation');
		$('#standardHours').attr('data-validation-optional',"true");
		$("#annualSal").removeAttr('data-validation');
		$('#annualSal').attr('data-validation-optional',"true");
		$("#perPaySal").removeAttr('data-validation');
		$('#perPaySal').attr('data-validation-optional',"true"); 
		$("#hourlyRate").removeAttr('data-validation');
		$('#hourlyRate').attr('data-validation-optional',"true");
	}else{
		$("#salaryDIV").show();
		$("#standardHours").removeAttr('data-validation-optional');
		$('#standardHours').attr('data-validation',"required validate_float");
		$("#annualSal").removeAttr('data-validation-optional ');
		$('#annualSal').attr('data-validation',"required validate_float");
		$("#perPaySal").removeAttr('data-validation-optional');
		$('#perPaySal').attr('data-validation',"required validate_float");
		$("#hourlyRate").removeAttr('data-validation-optional');
		$('#hourlyRate').attr('data-validation',"required validate_float");
	}
}
</script>

<!-- Ends update salary details -->


<script>
// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".tabsMenu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".tabsMenu_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

</script> 
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
</script> 
<script>
    $(function()
{
	$('.scroll-pane').jScrollPane();
});
    </script>
    
  <script>
//Pf Function
function pfFunction() {
    var checkBox = document.getElementById("pf");
    var pfel = document.getElementById("pfEligible");
    if (checkBox.checked == true){
        pfel.style.display = "block";
    } else {
       pfel.style.display = "none";
    }
}
		//ESI Function
function esiFunction() {
    var checkBox = document.getElementById("esi");
    var esiEl = document.getElementById("esiEligible");
    if (checkBox.checked == true){
       esiEl.style.display = "block";
    } else {
       esiEl.style.display = "none";
    }
}
//Salary history popup
function salaryHistory(id) {
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/findAssociateSalHistory?"+tokenParamName+"="+tokenValue+"&associateSalid="+id                  , 
		        success: function(json) {
		        var result = "";
		        result = result + '<table>';
		        result = result + '<thead>';
		        result = result + '<tr>';
		        result = result + '<th>Pay Type</th>';
		        result = result + '<th>Pay Frequency</th>';
		        result = result + '<th>Normal Hours</th>';
		        result = result + '<th>Annual Salary</th>';
		        result = result + '<th>Per Pay Salary</th>';
		        result = result + '<th>Hourly Rate</th>';
		        result = result + '<th>Effective</th>';
		        result = result + '<th>% Increase</th>';
		        result = result + '</tr>';
		        result = result + '</thead>';
		        result = result + '<tbody>';
				for (var i=0; i<json.length; i++)
				{
					var opt = json[i];
					result = result + '<tr>';
					result = result + '<td>'+opt.payType+'</td>';
					result = result + '<td>'+opt.payFrequency+'</td>';
					result = result + '<td>'+opt.standardHours+'</td>';
					result = result + '<td>'+opt.annualSalary+'</td>';
					result = result + '<td>'+opt.grossSalary+'</td>';
					result = result + '<td>'+opt.hourlyRate+'</td>';
					if(opt.effectiveDate != null && opt.effectiveDate !='' && opt.effectiveDate !='undefined'){
						result = result + '<td>'+opt.effectiveDate+'</td>';
					}else{
						result = result + '<td>0</td>';
					}
					if(opt.increasePercentage != null && opt.increasePercentage != '' && opt.increasePercentage != 'undefined'){
						result = result + '<td>'+opt.increasePercentage+'%</td>';
					}else{
						result = result + '<td>0%</td>';
					}
					
					result = result + '</tr>';
             			
             		
				}
				result = result + '</tbody>';
				result = result + '</table>';
				$("#salHistoryDiv").empty();
				$("#salHistoryDiv").append(result);
		        },
		    });
	
	
	
	$(".salaryHistory, .comMask").fadeIn();
}
function closeSalaryHistory() {
	$(".salaryHistory, .comMask").fadeOut();
}


function closeupdateSalaryDetails() {
	$(".updateSalaryDetails, .sideMask").fadeOut();
	setTimeout(function(){ document.getElementById("update_associate_salary_form").reset();  }, 1000);
}
		
</script>  

<script>
function updateSalaryDetails(id,payType,payFrequency,standardHours,annualSal,perPaySal,hourlyRate,userId,groupUUID) {
	 document.getElementById("update_associate_salary_form").reset(); 
	
	 if(id != null && id != '' && id != 'undefined'){
			 $("#payType").val(payType);
			$("#payFrequency").val(payFrequency);
			$("#standardHours").val(standardHours);
			$("#annualSal").val(annualSal);
			$("#perPaySal").val(perPaySal);
			$("#hourlyRate").val(hourlyRate);
//		 	$("#payGroupId").val(groupName);
//		 	$('#payGroupId').trigger("chosen:updated");
			
			$("#id").val(id);
			$("#previousPayType").val(payType);
			$("#previousPayFrequency").val(payFrequency);
			$("#previousStandardHours").val(standardHours);
			$("#previousAnnualSal").val(annualSal);
			$("#previousPerPaySal").val(perPaySal);
			$("#previousHourlyRate").val(hourlyRate);
			$("#userId").val(userId);
			
			getPayGroups(groupUUID);
			
			 if(payType == "HourlyRate" || payType == "LcaWage"){
				    $("#salaryDIV").hide();
					$("#standardHours").val('');    
					$("#annualSal").val('');
					$("#perPaySal").val('');
					$("#hourlyRate").val('');
					$("#standardHours").removeAttr('data-validation');
					$('#standardHours').attr('data-validation-optional',"true");
					$("#annualSal").removeAttr('data-validation');
					$('#annualSal').attr('data-validation-optional',"true");
					$("#perPaySal").removeAttr('data-validation');
					$('#perPaySal').attr('data-validation-optional',"true"); 
					$("#hourlyRate").removeAttr('data-validation');
					$('#hourlyRate').attr('data-validation-optional',"true");
			}else{
				$("#salaryDIV").show();
				$("#standardHours").removeAttr('data-validation-optional');
				$('#standardHours').attr('data-validation',"required validate_float");
				$("#annualSal").removeAttr('data-validation-optional ');
				$('#annualSal').attr('data-validation',"required validate_float");
				$("#perPaySal").removeAttr('data-validation-optional');
				$('#perPaySal').attr('data-validation',"required validate_float");
				$("#hourlyRate").removeAttr('data-validation-optional');
				$('#hourlyRate').attr('data-validation',"required validate_float");
			}
	 }
	 $(".updateSalaryDetails, .sideMask").fadeIn();
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	var finalDateFormat = getJSDateFormat(locationDate);
	 $("#effectiveDate").datepicker({
		 dateFormat: finalDateFormat,
		 changeMonth: true,
		  	  changeYear: true,
		  	minDate: 0
		 });
}

function editSalaryDetails(userId){
	$("#userId").val(userId);
	
	 $(".updateSalaryDetails, .sideMask").fadeIn();
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		var finalDateFormat = getJSDateFormat(locationDate);
		 $("#effectiveDate").datepicker({
			 dateFormat: finalDateFormat,
			 changeMonth: true,
			  	  changeYear: true,
			  	minDate: 0
			 });
}

</script>

<script>
function getPayFreqHours(){
	var payFrequency = $("#payFrequency").val();
	//Annual Monthly Bi-Weekly Weekly
	if(payFrequency === 'Annual'){
		$("#standardHours").val('2088');
	}
	if(payFrequency === 'Monthly'){
		$("#standardHours").val('176');
	}
	if(payFrequency === 'Bi-Weekly'){
		$("#standardHours").val('80');
	}
	if(payFrequency === 'Weekly'){
		$("#standardHours").val('40');
	}
	calculateSal();
   /*  calculatePayPerSal(); */
}
</script>

<script>
function calculateSal(){
	var payFrequency = $("#payFrequency").val();
	var annualSal = $("#annualSal").val();
	var hourlyRate = $("#hourlyRate").val();
	var hours = $("#standardHours").val();

	if(annualSal != null && annualSal != '' && annualSal != 'undefined' && hours != null && hours != '' && hours != 'undefined'){
		$('#AnnualSal').find('p.jquery_form_error_message').remove(); 
		$('#PerPaySal').find('p.jquery_form_error_message').remove();
		$('#HourlyRate').find('p.jquery_form_error_message').remove(); 
		var rate = null;
		if(payFrequency === 'Annual'){
			rate = parseFloat(annualSal) / 1;
			$("#perPaySal").val(rate.toFixed(2));
		}
		if(payFrequency === 'Monthly'){
			rate = parseFloat(annualSal) / 12;
			$("#perPaySal").val(rate.toFixed(2));
		}
		if(payFrequency === 'Bi-Weekly'){
			rate = parseFloat(annualSal) / 26;
			$("#perPaySal").val(rate.toFixed(2));
		}
		if(payFrequency === 'Weekly'){
			rate = parseFloat(annualSal) / 52;
			$("#perPaySal").val(rate.toFixed(2));
		}
		var hourRate = rate/hours;
		$("#hourlyRate").val(hourRate.toFixed(2));
		calaculateIncDecPer();
	}
}
</script>

<script>
function calculatePayPerSal(){
	var payFrequency = $("#payFrequency").val();
	var perPaySal = $("#perPaySal").val();
	var hourlyRate = $("#hourlyRate").val();
	var standardHours = $("#standardHours").val();
	var annualSal = $("#annualSal").val();
	
	if(perPaySal !='' && perPaySal != 'undefined'){
		$('#AnnualSal').find('p.jquery_form_error_message').remove(); 
		$('#PerPaySal').find('p.jquery_form_error_message').remove(); 
		 $('#HourlyRate').find('p.jquery_form_error_message').remove(); 
		if(payFrequency === 'Annual'){
			var annualRate = parseFloat(perPaySal) * 1;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		if(payFrequency === 'Monthly'){
			var annualRate = parseFloat(perPaySal) * 12;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		if(payFrequency === 'Bi-Weekly'){
			var annualRate = parseFloat(perPaySal) * 26;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		if(payFrequency === 'Weekly'){
			var annualRate = parseFloat(perPaySal) * 52;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		
// 		if(hourlyRate != null && hourlyRate != '' && hourlyRate != 'undefined'){
			var hourlyrate = parseFloat(perPaySal) / standardHours;
			$("#hourlyRate").val(hourlyrate.toFixed(2));
// 		}
			calaculateIncDecPer();
	}
	
	
}
</script>

<script>
function calculateHourlyRateSal(){
	var payFrequency = $("#payFrequency").val();
	var perPaySal = $("#perPaySal").val();
	var hourlyRate = $("#hourlyRate").val();
	var standardHours = $("#standardHours").val();
	var annualSal = $("#annualSal").val();

	if(hourlyRate != null && hourlyRate != '' && hourlyRate != 'undefined'){ 
		$('#AnnualSal').find('p.jquery_form_error_message').remove(); 
		$('#PerPaySal').find('p.jquery_form_error_message').remove(); 
		 $('#HourlyRate').find('p.jquery_form_error_message').remove(); 
		var perPaySal = parseFloat(hourlyRate) * standardHours;
		$("#perPaySal").val(perPaySal.toFixed(2));
		calculatePayPerSal();
		calaculateIncDecPer();
	}
}
</script>


<script>
function calculatePercentSal(){
	
	var increasePer  = $("#increasePer").val();
	if(increasePer != null && increasePer != '' && increasePer != 'undefined' && increasePer > 0){
		var annualSal = $("#previousAnnualSal").val();
		if(annualSal == null || annualSal == '' || annualSal == 'undefined')
			annualSal = $("#annualSal").val();
		var percentValue = increasePer/100;

		var percentMulValue =  percentValue * parseFloat(annualSal);
		var incrementAnnualSal = parseFloat(percentMulValue)  + parseFloat(annualSal) ;
		$("#annualSal").val(incrementAnnualSal);
		$("#salaryType").val('increment');
		calculateSal();
	} 
}
</script>

<script>
function calaculateIncDecPer(){
	var curAnnualSal = $("#annualSal").val();
	var perPaySal = $("#perPaySal").val();
	var HourlyRate = $("#HourlyRate").val();
	var previousAnnualSal = $("#previousAnnualSal").val();
	 if(annualSal != null && annualSal != '' && annualSal != 'undefined')
		{
		$('#AnnualSal').find('p.jquery_form_error_message').remove(); 
		}
		if(perPaySal != null && perPaySal != '' && perPaySal != 'undefined'){
			$('#PerPaySal').find('p.jquery_form_error_message').remove(); 
		}
	if(hourlyRate != null && hourlyRate != '' && hourlyRate != 'undefined'){
		 $('#HourlyRate').find('p.jquery_form_error_message').remove(); 
	}
	if(previousAnnualSal != null && previousAnnualSal != '' && previousAnnualSal != 'undefined' && curAnnualSal != null && curAnnualSal != '' && curAnnualSal != 'undefined'){
		if(curAnnualSal > previousAnnualSal){
			$("#salaryType").val('increment');
			var diffSal = parseFloat(curAnnualSal) - parseFloat(previousAnnualSal);
			var percent = diffSal / parseFloat(previousAnnualSal);
			var icrementPer = percent * 100;
			$("#increasePer").val(icrementPer.toFixed(2));
// 			calculateSal();
		}
		if(curAnnualSal < previousAnnualSal){
			$("#salaryType").val('decrement');
			var diffSal = parseFloat(previousAnnualSal) - parseFloat(curAnnualSal);
			var percent = diffSal / parseFloat(previousAnnualSal);
			var icrementPer = percent * 100;
			$("#increasePer").val(icrementPer.toFixed(2));
// 			calculateSal();
		}
// 		calculateSal();
	}
	
	
}
</script>

 <script type="text/javascript">
 function updateAssociateSal() {
	var salaryType = $("#salaryType").val();
	var userId = $("#userId").val();
	   if ($('#update_associate_salary_form').validate(false, validationSettings)){ 
			   var formData = $($('#update_associate_salary_form')[0]).serialize();
				/* $("#update_associate_sal_button").attr("disabled", "disabled"); */
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	            $( "#update_associate_sal_button" ).addClass('button').val('Processing..');
	            $.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/addAssociateSal?"+tokenParamName+"="+tokenValue+"&userId="+userId,
					success : function(result) {
						 if (result && result.response === "INVALID_DATA") {
			                    $("#update_associate_sal_button").removeAttr("disabled");
			                    $("#update_associate_sal_button").removeAttr('button').val('Save');
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	 $("#loading").removeAttr("style");
			                	 closeupdateSalaryDetails();
			                	 var userId = $("#userId").val();
			                	 associateSalSummary(userId);
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    $("#update_associate_sal_button").removeAttr("disabled");
			                    $("#update_associate_sal_button").addClass('button').val('Save');
			                    showError("Operation failed, please try again");
			                }
						
					},
				});
		
	        return true;
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#update_associate_salary_form', function() {
		$("#update_associate_salary_form").showHelpOnFocus();
		$("#update_associate_salary_form").validateOnBlur(false, validationSettings);  
   }); 
  
 
</script>


<!-- Salary History Popup -->
<div class="comMask"></div>
 <div class="modal salaryHistory" id="salaryHistory">
  <div class="modal-dialog modal-lg salaryHistory_modalLg">
    <div class="modal-content">
      <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button" onClick="closeSalaryHistory();">&times;</button>
        <h4 class="modal-title">History</h4>
      </div>
      <div class="modal-body clearfix">
        
        <div class="clearfix col-md-12 styleTables tableStyles-textLeft">
                 <div class="row">
                 	<div id="salHistoryDiv"></div>
                 </div>
                 </div>
      </div>
    </div>
  </div>
</div>



<script>
function editPayItem(payItemId){
	
	 document.getElementById("effectiveDateDiv"+payItemId).style.display="none";
	 document.getElementById("editEffectiveDateDiv"+payItemId).style.display="block";
	 
	 document.getElementById("endDateDiv"+payItemId).style.display="none";
	 document.getElementById("editEndDateDiv"+payItemId).style.display="block";
	 
	 document.getElementById("payItemAmountDiv"+payItemId).style.display="none";
	 document.getElementById("editpayItemAmountDiv"+payItemId).style.display="block";
	 
	 document.getElementById("editDiv"+payItemId).style.display="none";
	 document.getElementById("saveDiv"+payItemId).style.display="inline-block";
}
</script>

<script>

var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
var finalDateFormatEndDate = '';
if(locationDate.match("yyyy-MM-dd")){
	  finalDateFormatEndDate = 'yy-mm-dd';
}if(locationDate.match("dd-MM-yyyy")){
	  finalDateFormatEndDate = 'dd-mm-yy'; 
}if(locationDate.match("MM-dd-yyyy")){
	  finalDateFormatEndDate = 'mm-dd-yy'; 
}if(locationDate.match("yyyy-dd-MM")){
	  finalDateFormatEndDate = 'yy-dd-mm'; 
}

function editEffectiveDate(e){
	$("#"+e.id).datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : finalDateFormatEndDate,
	});
}

function editEndDate(e){
// 	$("#"+e.id).attr('readonly','readonly');
	$("#"+e.id).datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : finalDateFormatEndDate,
	});
}

</script>

<script>
function updatePayItems(payItemId){
	var effectiveDate = $("#preEffective"+payItemId).val();
	var endDate = $("#preEnd"+payItemId).val();
	var payAmount = $("#prePayAmount"+payItemId).val();
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/updateAssociatePayItem?"+tokenParamName+"="+tokenValue+"&id="+payItemId+"&effectiveDate="+effectiveDate+"&endDate="+endDate+"&payItemAmount="+payAmount,
		success : function(result) {
			if(result != null){
				displayNormalDivs(payItemId,effectiveDate,endDate,payAmount);
			}
			
		},
	}); 
	
}

function displayNormalDivs(payItemId,effectiveDate,endDate,payAmount){
	 document.getElementById("editEffectiveDateDiv"+payItemId).style.display="none";
	 document.getElementById("effectiveDateDiv"+payItemId).style.display="block";
	 $("#effectiveDateDiv"+payItemId).empty();
	 $("#effectiveDateDiv"+payItemId).append(effectiveDate);
	 
	 document.getElementById("editEndDateDiv"+payItemId).style.display="none";
	 document.getElementById("endDateDiv"+payItemId).style.display="block";
	 $("#endDateDiv"+payItemId).empty();
	 $("#endDateDiv"+payItemId).append(endDate);
	 
	 document.getElementById("editpayItemAmountDiv"+payItemId).style.display="none";
	 document.getElementById("payItemAmountDiv"+payItemId).style.display="block";
	 $("#payItemAmountDiv"+payItemId).empty();
	 $("#payItemAmountDiv"+payItemId).append(payAmount);
	 
	 document.getElementById("saveDiv"+payItemId).style.display="none";
	 document.getElementById("editDiv"+payItemId).style.display="inline-block";
} 
</script>

<div id="popUp"></div>

<script>
function deletePayItem(payItemId,userId){
	
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Do you want to delete this PayItem?</p>";
	result = result + ' <div id="payItemDeleteDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='proceedToDelete("+payItemId+","+userId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	
}

function proceedToDelete(payItemId,userId){
	
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/deleteAssociatePayItem?"+tokenParamName+"="+tokenValue+"&payItemId="+payItemId,
		success : function(result) {
			if(result != null){
				$("#popUp").empty();
	 			$(".project_inn_mask").fadeOut(200);
	 			associateSalSummary(userId);
			}
			
		},
	}); 
}
</script>

<script>
function getPayGroups(groupUUID){
	var payFrequency = $("#payFrequency").val();
		 $.ajax({
				type : "GET",
				url : sourceleadUrl+"/getPayGroupsByPayFrequency?"+tokenParamName+"="+tokenValue+"&payFrequency="+payFrequency,
				success : function(result) {
				var output = "";
				output = output + "<option value=''>Select Group</option>";
				for (var i=0; i<result.length; i++)
				{
					var opt = result[i];
					if(groupUUID != null && groupUUID != '' && groupUUID != 'undefined'){
						if(groupUUID == opt.groupUUID){
							output = output + "<option selected value='" +opt.groupUUID+ "'>"+opt.groupName+"</option>";
						}else{
							output = output + "<option value='" +opt.groupUUID+ "'>"+opt.groupName+"</option>";
						}
					}else{
						output = output + "<option value='" +opt.groupUUID+ "'>"+opt.groupName+"</option>";
					}
				}
				$("#payGroupId").empty();
				$("#payGroupId").append(output);
				$('#payGroupId').trigger("chosen:updated");
					
				}
		 });
}
</script>


<!-- Salary History Popup -->