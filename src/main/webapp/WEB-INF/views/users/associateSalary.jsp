 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  
<script>
$(document).ready(function(){
   getPayItemList();
});
</script>

  <div id="tab-6" class="tabsMenu_tabs_inn clearfix">
            <div class="clearfix row minHeightcol5">
              <div class="col-md-12 mb">
                <div class="clearfix row">
                  <div class="col-md-5">
                    <label>Pay Type</label>
                    <div>
                      <select class="sel" id="payType" name="payType">
                        <option selected>--Select--</option>
                        <option>hourlyRate</option>
                        <option>Salary</option>
                        <option>Commission Only</option>
                      </select>
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Pay Frequency</label>
                    <div>
                      <select class="sel" id="payFrequency" name="payFrequency" onchange="getPayFreqHours();">
                        <option value="">--Select--</option>
                        <option value="Annual">Annually</option>
                        <option value="Monthly">Monthly</option>
                        <option value="Bi-Weekly">Bi-Weekly</option>
                        <option value="Weekly">Weekly</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Standard Hours</label>
                    <div>
                      <input type="text" class="form-control"  id="standardHours" name="standardHours" onblur="calculateSal();">
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Annual Salary</label>
                    <div>
                      <input type="text" class="form-control" id="annualSal" name="annualSal" onblur="calculateSal();">
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Pay Per Salary</label>
                    <div>
                      <input type="text" class="form-control" id="perPaySal" name="perPaySal" onblur = "calculatePayPerSal();">
                    </div>
                  </div>  
                   <div class="col-md-5">
                    <label>hourlyRate Rate</label>
                    <div>
                      <input type="text" class="form-control" id="hourlyRate" name="hourlyRate" onblur = "calculateHourlyRateSal();">
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>&nbsp;</label>
                    <div>
						<div class="checkbox-new pull-left">
							<input id="overtimePay" class="checkbox-custom" name="allowOverTimePay" id="allowOverTimePay" value="YES" type="checkbox">
							<label for="overtimePay" class="checkbox-custom-label"><span class="ml10">Allow Overtime Pay</span></label>
						</div>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                  <h4 class="col-md-12">Pay Item</h4>
                 <div class="clearfix col-md-12">
                 	<table class="table">
                 		<thead>
                 			<tr>
                 				<th>
								<div class="checkbox-new">
										<input id="all" class="checkbox-custom" name="paidComponent" type="checkbox">
										<label for="all" class="checkbox-custom-label">
									</label>
								</div>
                 				</th>
                 				<th>Pay Items</th>
                 				<th>Schedule</th>
                 				<th>Override</th>
                 				<th>Effective Date</th>
                 				<th>End Date</th>
                 				<th>Amount</th>
                 				<th>Action</th>
                 			</tr>
                 		</thead>
                 		<tbody>
                 		<div id="payItemDiv"></div>
                 			<!-- <tr>
                 			<td>
								<div class="checkbox-new">
									<input id="bonus" class="checkbox-custom" name="paidComponent" type="checkbox">
									<label for="bonus" class="checkbox-custom-label"></label>
								</div>
                 				</td>
                 			
                 				<td>
									<div class="tblminwidth-100 text-left">Bonus</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<select class="sel">
											<option selected="">--Select--</option>
											<option>Every Pay</option>
											<option>Every First Month Pay</option>
											<option>Every Quarter</option>
											<option>Annually</option>
										</select>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<select class="sel">
									<option selected="">--Select--</option>
									<option>override1</option>
									<option>override2</option>
									<option>override3</option>
									<option>override4</option>
									<option>override5</option>
									</select>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
										<input class="form-control calendar" value="Enter" type="text">
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
										<input class="form-control calendar" value="Enter" type="text">
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
										<input class="form-control" value="Enter" type="text">
									</div>
                 				</td>
                 				<td>
                 					<ul class="list-inline list-unstyled tblminwidth-50 mt5">
                 						<li><a href="javascript:void(0)" title="Save" class="anchor-black"><i class="fa fa-floppy-o"></i></a></li>
                 						<li><a href="javascript:void(0)" title="Reset" class="anchor-black"><i class="fa fa-refresh"></i></a></li>
                 					</ul>
                 				</td>
                 			</tr> -->
                 	
                 		</tbody>
                 	</table>
                 	
                 </div>
                 
                 
                  
                  
                  
                                                      
					<div class="clearfix"></div>
                  <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                 <a href="javascript:void(0)" class="anchorBlack">Skip</a>
			     </div>
                  <div class="col-md-6">
                   <div class="pull-right">
                    <input value="Save & Continue" class="btn btn-success btnSuccess" type="button">
                    <input value="Cancel" class="btn btn-default ml10" type="button">
                    </div>
                  </div>
                </div>
                  
                  
                </div>
              </div>
            </div>
          </div>
          
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
	calculatePayPerSal()
}
</script>


<script>
function calculateSal(){
	var payFrequency = $("#payFrequency").val();
	var annualSal = $("#annualSal").val();
	var hours = $("#standardHours").val();
	if(annualSal != null && annualSal != '' && annualSal != 'undefined' && hours != null && hours != '' && hours != 'undefined'){
		
		if(payFrequency === 'Annual'){
			var rate = parseFloat(annualSal) / 1;
			$("#perPaySal").val(rate.toFixed(2));
			/* var hourRate = rate/hours;
			$("#hourlyRate").val(hourRate.toFixed(2)); */
		}
		if(payFrequency === 'Monthly'){
			var rate = parseFloat(annualSal) / 12;
			$("#perPaySal").val(rate.toFixed(2));
			/* var hourRate = rate/hours;
			$("#hourlyRate").val(hourRate.toFixed(2)); */
		}
		if(payFrequency === 'Bi-Weekly'){
			var rate = parseFloat(annualSal) / 26;
			$("#perPaySal").val(rate.toFixed(2));
			/* var hourRate = rate/hours;
			$("#hourlyRate").val(hourRate.toFixed(2)); */
		}
		if(payFrequency === 'Weekly'){
			var rate = parseFloat(annualSal) / 52;
			$("#perPaySal").val(rate.toFixed(2));
			/* var hourRate = rate/hours;
			$("#hourlyRate").val(hourRate.toFixed(2)); */
		}
		var hourRate = rate/hours;
		$("#hourlyRate").val(hourRate.toFixed(2));
	}
}
</script>

<script>
function calculatePayPerSal(){
	var payFrequency = $("#payFrequency").val();
	var perPaySal = $("#perPaySal").val();
	var hourlyRate = $("#hourlyRate").val();
	var standardHours = $("#standardHours").val();
	if(perPaySal !='' && perPaySal != 'undefined'){
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
		
		if(hourlyRate != null && hourlyRate != '' && hourlyRate != 'undefined'){
			var hourlyrate = parseFloat(perPaySal) / standardHours;
			$("#hourlyRate").val(hourlyrate.toFixed(2));
		}
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
	if(annualSal != null && annualSal != '' && annualSal != 'undefined'){
		var perPaySal = parseFloat(hourlyRate) * standardHours;
		$("#perPaySal").val(perPaySal.toFixed(2));
		calculatePayPerSal();
	}
}
</script>




<!-- <script type="text/javascript">
 function addPayItem() {
	 if ($('#add_pay_item_form').validate(false, validationSettings)){ 
			   /*  var formData = getFormData("add_pay_item_form"); */
			   var formData = $($('#add_pay_item_form')[0]).serialize();
				$("#save_payitem_button").attr("disabled", "disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	            $( "#save_payitem_button" ).addClass('button').val('Processing..');
	            $.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/addPayItem?"+tokenParamName+"="+tokenValue+"",
					success : function(result) {
						 if (result && result.response === "INVALID_DATA") {
			                    $("#save_payitem_button").removeAttr("disabled");
			                    $("#save_payitem_button").removeAttr('button').val('Save');
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	 location.replace(sourceleadUrl+ "/viewPayItem");
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    $("#save_payitem_button").removeAttr("disabled");
			                    $("#save_payitem_button").addClass('button').val('Save');
			                    showError("Operation failed, please try again");
			                }
						
					},
				});
		
	        return true;
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#add_pay_item_form', function() {
		$("#add_pay_item_form").showHelpOnFocus();
		$("#add_pay_item_form").validateOnBlur(false, validationSettings);  
   });
  
 
</script>  -->




<script>
  function getPayItemList(){
		$.get(sourceleadUrl+"/getPayItemList?"+tokenParamName+"="+tokenValue+"", function(response) {
			  if (response.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var result = "";				        	
				if(response !=null && response !=''){
					 for (var i=0; i<response.data.length; i++)
						{
			        		 var opt = response.data[i];
			        		result = result +' <tr>';
			        		result = result +' <td>';
			        		result = result +' 	<div class="checkbox-new">';
			        		result = result +' 	<input id="bonus" class="checkbox-custom" name="paidComponent" type="checkbox">';
			        		result = result +' 	<label for="bonus" class="checkbox-custom-label"></label>';
			        		result = result +' </div>';
			        		result = result +' </td>';
	                 			
			        		result = result +' <td>';
			        		result = result +' <div class="tblminwidth-100 text-left">Bonus</div>';
			        		result = result +' </td>';
			        		result = result +' <td>';
			        		result = result +' <div class="tblminwidth-125">';
			        		result = result +' 	<select class="sel">';
			        		result = result +' 	<option selected="">--Select--</option>';
			        		result = result +' 	<option>Every Pay</option>';
			        		result = result +' 	<option>Every First Month Pay</option>';
			        		result = result +' 	<option>Every Quarter</option>';
			        		result = result +' 	<option>Annually</option>';
			        		result = result +' </select>';
			        		result = result +'</div>';
			        		result = result +'</td>';
			        		result = result +'<td>';
			        		result = result +'<div class="tblminwidth-125">';
			        		result = result +'	<select class="sel">';
			        		result = result +'	<option selected="">--Select--</option>';
			        		result = result +'	<option>override1</option>';
			        		result = result +'	<option>override2</option>';
			        		result = result +'	<option>override3</option>';
			        		result = result +'	<option>override4</option>';
			        		result = result +'	<option>override5</option>';
			        		result = result +'	</select>';
			        		result = result +'</div>';
							result = result +'</td>';
							result = result +'<td>';
							result = result +'	<div class="tblminwidth-50">';
							result = result +'		<input class="form-control calendar" value="Enter" type="text">';
							result = result +'	</div>';
							result = result +'</td>';
							result = result +'<td>';
							result = result +'	<div class="tblminwidth-50">';
							result = result +'		<input class="form-control calendar" value="Enter" type="text">';
							result = result +'	</div>';
							result = result +'</td>';
							result = result +'<td>';
							result = result +'<div class="tblminwidth-50">';
							result = result +'	<input class="form-control" value="Enter" type="text">';
							result = result +'</div>';
							result = result +'</td>';
							result = result +'<td>';
							result = result +'	<ul class="list-inline list-unstyled tblminwidth-50 mt5">';
							result = result +'		<li><a href="javascript:void(0)" title="Save" class="anchor-black"><i class="fa fa-floppy-o"></i></a></li>';
							result = result +'		<li><a href="javascript:void(0)" title="Reset" class="anchor-black"><i class="fa fa-refresh"></i></a></li>';
							result = result +'	</ul>';
							result = result +'</td>';
							result = result +'</tr>';
						}
				}
				$("#payItemDiv").empty();
				$("#payItemDiv").append(result);
         }else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			} 
		}); 
  }
  </script>