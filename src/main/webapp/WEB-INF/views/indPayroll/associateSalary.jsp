
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div id="tab-5" class="tabsMenu_tabs_inn" style="display: block;">
                <div class="clearfix row minHeightcol5">
                 <div class="col-md-4">
                  <label>CTC <small>(Annual)</small><span class="text-danger paddingLeft">*</span></label>
                  <div>
                    <input class="form-control" id="ctc" name="ctc" value="${candidateCTCPerYear}" type="text">
                  </div>
                </div>
               <div class="col-md-4">
                  <label>FBP</label>
                  <div>
                    <input class="form-control" id="fbp" name="fbp" value="" type="text">
                  </div>
                </div>
                
                <div class="col-md-4">
                  <label>Variable Pay</label>
                  <div>
                    <input class="form-control" id="variablePay" name="variablePay" value="" type="text">
                  </div>
                </div>
                 <div class="clearfix"></div>
                 </div>
         <div class="clearfix styleTables tableStyles-textLeft">
              <div class="clearfix mb mt">
              	<label class="checkBox"><input type="checkbox" name="salary"></label>Dispaly CTC based components
              </div>
               <table>
               		<thead>
               		<tr>
               			<th style="width:50px;"><input type="checkbox" name="salary"></th>
               			<th>Payhead</th>
               			<th>Paytype</th>
               			<th>Method</th>
               			<th>Formula</th>
               			<th>Monthly</th>
               			<th>Annualy</th>
               		</tr>
               		</thead>
	               		<tbody>
	               			<c:forEach var="payHead" items="${payrollPayheadList}">
	               				<tr>
									<td><input type="checkbox" id="${payHead.shortCode}" name="${payHead.shortCode}" onclick="payHeadCalculation(${payHead.id}, '${payHead.shortCode}', '${payHead.method}', '${payHead.formula}')"></td>
									<td>${payHead.name}</td>
									<td>${payHead.type}</td>
									<td>${payHead.method}</td>
									<td>${payHead.formula}</td>
									<td><input type="text" id="payHeadMonthly[${payHead.shortCode}]" name="payHeadMonthly[${payHead.shortCode}]" onblur="calculateToPerYear('${payHead.shortCode}');" value="" class="form-control input-sm"></td>
									<td><input type="text" id="payHeadYearly[${payHead.shortCode}]" name="payHeadYearly[${payHead.shortCode}]" onblur="calculateToPerMonth('${payHead.shortCode}');" value="" class="form-control input-sm"></td>
								</tr>
							</c:forEach>
	               		</tbody>
               		</table>
                <!-- <div class="clearfix row">
                	<div class="col-md-5 mt">
                		<label class="col-md-5">Difference Amount</label><div class="col-md-7"><input type="text" class="form-control  input-sm"></div>
                	</div>
                </div> -->
                  
                <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                 <!-- <a href="javascript:void(0)" class="anchorBlack">Skip</a> -->
			     </div>
                  <div class="col-md-6">
                   <div class="pull-right">
                    <input value="Save &amp; Continue" class="btn btn-success btnSuccess" type="button">
                    <input value="Cancel" class="btn btn-default ml10" type="button">
                    </div>
                  </div>
                </div>
            </div>
      </div>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script>

<script>
function calculateToPerYear(shortCode){
	var monthly = document.getElementById("payHeadMonthly["+shortCode+"]").value;
	document.getElementById("payHeadYearly["+shortCode+"]").value = (monthly*12).toFixed(2);
}
function calculateToPerMonth(shortCode){
	var yearly = document.getElementById("payHeadYearly["+shortCode+"]").value;
	document.getElementById("payHeadMonthly["+shortCode+"]").value = (yearly/12).toFixed(2);
}

function payHeadCalculation(id, shortCode, method, formula){
	
	if(document.getElementById(shortCode).checked){
		try{
			switch(method) {
			    case "% of Payheads":
						var formatedFormula = formula.replace(/%/g, "/100*");
						var matches = [];
						var pattern = /\((.*?)\)/g;
						var match;
						while ((match = pattern.exec(formatedFormula)) != null)
						{
						  //matches.push(match[1]);
						  var yearPayHeadValue = document.getElementById("payHeadYearly["+match[1]+"]").value;
						  formatedFormula = formatedFormula.replace(match[1], yearPayHeadValue)
						}
						 var result = eval(formatedFormula);
						document.getElementById("payHeadMonthly["+shortCode+"]").value = (result).toFixed(2);
						document.getElementById("payHeadYearly["+shortCode+"]").value = (result*12).toFixed(2);
			        break;
			    case "% of CTC":
						var ctc = document.getElementById("ctc").value;
						var result = eval((ctc/100)*formula);
						document.getElementById("payHeadMonthly["+shortCode+"]").value = (result).toFixed(2);
						document.getElementById("payHeadYearly["+shortCode+"]").value = (result*12).toFixed(2);
			        break;
			    case "% of Gross pay":
						
			        break;
			    case "Flat":
				    	var result = eval(formula);
						document.getElementById("payHeadMonthly["+shortCode+"]").value = (result).toFixed(2);
						document.getElementById("payHeadYearly["+shortCode+"]").value = (result*12).toFixed(2);
			    	break;
			    default:
			    		document.getElementById("payHeadMonthly["+shortCode+"]").value = ("");
						document.getElementById("payHeadYearly["+shortCode+"]").value = ("");
			}
		}catch(err){
			document.getElementById("payHeadMonthly["+shortCode+"]").value = (0).toFixed(2);
			document.getElementById("payHeadYearly["+shortCode+"]").value = (0).toFixed(2);
		}
	}else{
		document.getElementById("payHeadMonthly["+shortCode+"]").value = ("");
		document.getElementById("payHeadYearly["+shortCode+"]").value = ("");
	}
}
</script>
