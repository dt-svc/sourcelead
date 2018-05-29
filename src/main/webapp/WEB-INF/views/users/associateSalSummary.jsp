  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 
 <script>
function associateSalSummary(userId){  
	alert()
	 $.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateSalary?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) { 
            	var result = "";
            	result = result + '<div class="clearfix mt minHeightcol5">';
            	result = result + '		<div class="col-md-3 col-md-offset-9">';
            	result = result + '	<ul class="list-inline list-unstyled pull-right mb0">';
            	result = result + '		<li><a href="javascript:void(0)" class="btn btn-primary btn-block" onClick="updateSalaryDetails();">Add New</a></li>';
            	result = result + '		<li><a href="javascript:void(0)" class="btn btn-warning btn-block" onClick="salaryHistory();">History</a></li>';
            	result = result + '		<li><a href="us-associates.html" class="btn btn-default btn-block">Edit</a></li>';
            	result = result + '		</ul>';
            	result = result + '	</div>';
            	result = result + '	<div class="clearfix"></div>';
            	result = result + '   <div class="col-md-5">';
            	result = result + '  <label>Pay Type</label>';
            	result = result + '   <div>';
            	result = result + '     <p class="form-control-static text-muted">'+json.payType+'</p>';
            	result = result + '   </div>';
            	result = result + ' </div>';

            	result = result + ' <div class="col-md-5">';
            	result = result + '   <label>Pay Frequency</label>';
            	result = result + '   <div>';
            	result = result + '     <p class="form-control-static text-muted">'+json.payFrequency+'</p>';
            	result = result + '    </div>';
            	result = result + '  </div>';
                    
            	result = result + ' <div class="col-md-5">';
            	result = result + '    <label>Standard Hours</label>';
            	result = result + '   <div>';
            	result = result + '     <p class="form-control-static text-muted">$ '+json.standardHours+'</p>';
                result = result + '   </div>';
                result = result + ' </div>';
                result = result + ' <div class="col-md-5">';
                result = result + '  <label>Annual Salary</label>';
                result = result + '  <div>';
                result = result + '    <p class="form-control-static text-muted">$ '+json.annualSal+'</p>';
                result = result + ' </div>';
                result = result + '   </div>';
                result = result + '   <div class="col-md-5">';
                result = result + '    <label>Per Pay Salary</label>';
                result = result + '    <div>';
                result = result + '     <p class="form-control-static text-muted">$ '+json.perPaySal+'</p>';
                result = result + '    </div>';
                result = result + '  </div>';
                result = result + ' <div class="col-md-5">';
                result = result + '   <label>Hourly</label>';
                result = result + '   <div>';
                result = result + '    <p class="form-control-static text-muted">$ '+json.hourlyRate+'</p>';
                result = result + '   </div>';
                result = result + ' </div>';
                result = result + ' </div>';
                
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
                result = result + '</tr>';
                result = result + '</thead>';
                result = result + '<tbody>';
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
                  result = result + '			<div class="tblminwidth-50">';
                  result = result + '			<div>'+opt.effectiveDate+'</div>';
                  result = result + '			</div>';
                  result = result + '	</td>';
                  result = result + '	<td>';
                  result = result + '			<div class="tblminwidth-50">';
                  result = result + '			<div>'+opt.endDate+'</div>';
                  result = result + '			</div>';
                  result = result + '	</td>';
                  result = result + '	<td>';
                  result = result + '		<div class="tblminwidth-50">';
                  result = result + '		<div>$ '+opt.payItemAmount+'</div>';
                  result = result + '		</div>';
                  result = result + '</td>';
                  result = result + '</tr>';
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
 
   
                
               <!--    <div class="clearfix"></div>
                  <h4 class="col-md-12">Pay Item</h4>
                 <div class="clearfix col-md-12 styleTables tableStyles-textLeft">
                 <div class="row">
                 	<table>
                 		<thead>
                 			<tr>
                 				<th>Pay Items</th>
                 				<th>Schedule</th>
                 				<th>Override</th>
                 				<th>Effective Date</th>
                 				<th>End Date</th>
                 				<th>Amount</th>
                 			</tr>
                 		</thead>
                 		<tbody>
                 			<tr>
                 				<td>
									<div class="tblminwidth-100 text-left">Bonus</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div>Every Pay</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>override1</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 			</tr>
                 		<tr>
                 				<td>
									<div class="tblminwidth-100 text-left">Commission</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div>Every Pay</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>override1</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 			</tr>
                 		<tr>
                 				<td>
									<div class="tblminwidth-100 text-left">Cash Tips</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div>Every Pay</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>override1</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>

                 			</tr>
                 		<tr>
                 				<td>
									<div class="tblminwidth-100 text-left">Jury</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div>Every Pay</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>override1</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>

                 			</tr>
                 		<tr>
                 				<td>
									<div class="tblminwidth-100 text-left">Reimbursement</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div>Every Pay</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>override1</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 			</tr>
                 		</tbody>
                 	</table>
                 </div>
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
                </div> -->
              
       
      
      <!-- Salary History Popup -->

<!-- Update salary details -->
<!-- Modal content Add Team-->
<div class="sideMask"></div>
<div class="modal updateSalaryDetails">
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
                <label>Paygroup</label>
                <div>
                  <select class="sel">
                    <option>--Select--</option>
                    <option selected>Monthly</option>
                    <option>Weekly</option>
                    <option>Semi-Monhtly</option>
                    <option>Bi-Weekly</option>
                  </select>
                </div>
              </div>
              <div class="col-md-5">
                <label>Pay Type</label>
                <div>
                  <select class="sel">
                    <option>--Select--</option>
                    <option selected>Hourly</option>
                    <option>Salary</option>
                    <option>Commission Only</option>
                  </select>
                </div>
              </div>
              <div class="col-md-5">
                <label>Pay Frequency</label>
                <div>
                  <select class="sel">
                    <option>--Select--</option>
                    <option>Annually</option>
                    <option selected>Quarterly</option>
                    <option>Semi-Monthly</option>
                    <option>Monthly</option>
                    <option>Bi-Weekly</option>
                    <option>Weekly</option>
                  </select>
                </div>
              </div>
              <div class="col-md-5">
                <label>Normaly Hours</label>
                <div>
                  <input class="form-control" name="" value="200" type="text">
                </div>
              </div>
              <div class="col-md-5">
                <label>Annual Salary</label>
                <div>
                  <input class="form-control" name="" value="10K" type="text">
                </div>
              </div>
              <div class="col-md-5">
                <label>Per Pay Salary</label>
                <div>
                  <input class="form-control" name="" value="85,000.00" type="text">
                </div>
              </div>
              <div class="col-md-5">
                <label>Hourly</label>
                <div>http://localhost:8080/app/userSummaryDetails?userCode=0zjJ6mN8
                  <input class="form-control" name="" value="100" type="text">
                </div>
              </div>
             
              <div class="col-md-5">
                <label>Effective Date</label>
                <div>
                  <input class="form-control calendar" name="" value="Enter" type="text">
                </div>
              </div>
              <div class="col-md-5">
                <label>% Increase</label>
                <div>
                  <input class="form-control " name="" value="Enter" type="text">
                </div>
              </div>
               <div class="col-md-5">
                <label>Change Reason</label>
                <div>
                  <textarea class="form-control" rows="2" placeholder="Enter"></textarea>
                </div>
              </div>
              <div class="clearfix"></div>
            </div>
        </div>
      </div>
      <div class="modal-footer">
        <button data-dismiss="modal" class="btn btn-success btnSuccess" type="button">Update</button>
        <button data-dismiss="modal" class="btn btn-default" type="button" onClick="closeupdateSalaryDetails();">Cancel</button>
      </div>
    </div>
  </div>
</div>
<!-- Add Team modal popup ends -->


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
function salaryHistory() {
	$(".salaryHistory, .comMask").fadeIn();
}
function closeSalaryHistory() {
	$(".salaryHistory, .comMask").fadeOut();
}

function updateSalaryDetails() {
	$(".updateSalaryDetails, .sideMask").fadeIn()
}
function closeupdateSalaryDetails() {
	$(".updateSalaryDetails, .sideMask").fadeOut()
}
		
</script>  
