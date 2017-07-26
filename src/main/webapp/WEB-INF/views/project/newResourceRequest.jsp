<!-- <link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet"> -->
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.ReadTextFli {
	text-align: center;
}
</style>

   <div id="resourceInfoRow" style="display: none; float: left;">
       <div class="addResource add-resource">
		<span class="addResClo addResClose"></span>
          <div class="col-xs-12 col-sm-12 myForm noPad clearfix request-resources">
              <div class="col-md-3 dropdown-select">
               <label>Role <span class="text-danger paddingLeft">*</span></label>
                <select class="form-control" name='roleREPLACEROWID' id='roleREPLACEROWID'  field-name="Role" data-validation="required" onchange="changeRole(this,REPLACEROWID);" >
                 <option value="">-Select Role-</option>
                   <c:forEach var="option" items="${rolesList}">
					 <option value="${option.id}">${option.displayName}</option>
				  </c:forEach>
				  <option value="Other">Other</option>
                </select>
              </div>
             <div class="col-md-3" id="otherRoleDivREPLACEROWID" style="display:none">
               <label>Custom Role<span class="text-danger paddingLeft">*</span></label>
                <input type="text" name='customRoleREPLACEROWID' id='customRoleREPLACEROWID' class="form-control" field-name="Custom Role">
              </div>
              <div class="col-md-3 dropdown-select">
               <label>No. of Resources <span class="text-danger paddingLeft">*</span></label>
                <input type="text" class="form-control" name='noOfResourcesREPLACEROWID' id='noOfResourcesREPLACEROWID'  field-name="No. of Resources" data-validation="required validate_length length1-15 validate_int">
              </div>
              <div class="col-md-3 dropdown-select">
               <label>Experience</label>
                  <div class="row experience-drop">
                      <div class="col-md-6">
	                      <select class="form-control" name='minREPLACEROWID' id='minREPLACEROWID' onblur="minMaxExperience(REPLACEROWID);">
	             		  <option value="">Min Years</option>
	             		  <c:forEach begin="0" end="25" varStatus="loop">
							    <option>${loop.index}</option>
							</c:forEach>
	             		  </select>
                       </div>
                      <div class="col-md-6">
                      <select class="form-control" name='maxREPLACEROWID' id='maxREPLACEROWID' onblur="minMaxExperience(REPLACEROWID);">
             		  <option value="">Max Years</option>
             		  <c:forEach begin="0" end="25" varStatus="loop">
						    <option>${loop.index}</option>
						</c:forEach>
             		</select>
                      </div>
                 </div>
                 <div class="row" id="expIdREPLACEROWID">
                  <input type="hidden" name="expValidationREPLACEROWID" id="expValidationREPLACEROWID">
              </div> 
              </div>
              
              <div class="col-md-3 dropdown-select">
               <label>Primary Skills <span class="text-danger paddingLeft">*</span></label>
                <input type="text" class="form-control" name='primarySkillREPLACEROWID' id='primarySkillREPLACEROWID' field-name="Primary Skill" data-validation="required validate_Space validate_length length2-200">
              </div>
              <!--  Rate Per div  -->
              <div class="col-md-6">
              <div class="clearfix">
              <label class="pull-left">Bill / Pay Rate</label>
             <div class="clearfix"></div>
             <div class="input-group payrate_p">
                <div class="input-group-btn selp-custom-min selp-customMin">
                  <select class="" name='currencyCodeREPLACEROWID' id='currencyCodeREPLACEROWID' >
	                 <option value="INR">INR</option>
	                 <option value="AUD">AUD</option>
	                 <option value="USD">USD</option>
	                 <option value="GBP">GBP</option>
	                 <option value="SGD">SGD</option>
	                 <option value="EUR">EUR</option>
              
                </select>
                </div>
                <div class="pull-left input_minmax">
                <input type="text" class="form-control" name='billRateREPLACEROWID' id='billRateREPLACEROWID' field-name="Bill Rate" placeholder="Bill Rate"  data-validation="validate_Space validate_length length1-8 validate_int" data-validation-optional="true"  value="" onblur="minMaxRate();">
                <input type="text" class="form-control" name='payRateREPLACEROWID' id='payRateREPLACEROWID'   field-name="Pay Rate" placeholder="Pay Rate"  data-validation="validate_Space validate_length length1-8 validate_int" data-validation-optional="true"  value="" onblur="minMaxRate();">
                </div>
                 <div class="row" id="rateValidateId">
                  <input type="hidden" name="rateValidation" id="rateValidation">
              </div> 
                <div class="input-group-btn selp-custom-min payrate_p">
                  <select class="ratetype"  name='rateTypeREPLACEROWID' id='rateTypeREPLACEROWID' field-name="Rate Per">
<!--                     <option value="">Rate Per</option> -->
                    <option value="Hourly">Hourly</option>
                    <option value="Weekly">Weekly</option>
                    <option value="Bi-weekly">Bi-weekly</option>
                    <option value="Semi-Monthly">Semi-Monthly</option>
                    <option value="Monthly" >Monthly</option>
                    <option value="Bi-Monthly">Bi-Monthly</option>
                    <option value="Yearly">Yearly</option>
                  </select>
                </div>
              </div>
            </div>
              </div>

              <div class="col-md-3">
               <label>Start Date</label>
                <input type="text" class="form-control calendar" name='startDateREPLACEROWID' id='startDateREPLACEROWID' placeholder="Start Date" readonly>
              </div>
              <div class="col-md-3">
               <label>End Date</label>
                <input type="text" class="form-control calendar" name='endDateREPLACEROWID' id='endDateREPLACEROWID' placeholder="End Date" readonly>
              </div>  
              <div class="col-md-3">
               <label>Estimated Hours<span class="text-danger paddingLeft">*</span></label>
                <input type="text" class="form-control timeicon" name='estimatedHoursREPLACEROWID' id='estimatedHoursREPLACEROWID' field-name="Estimated Hours" data-validation="required validate_length length1-10">
              </div>  
              <div class="col-md-3 currencySymbol">
                  <label>Estimated Budget<span class="text-danger paddingLeft">*</span></label>
                  <div class="positionRelative">
                <input type="text" class="form-control" name='estimatedBudgetREPLACEROWID' id='estimatedBudgetREPLACEROWID' field-name="Estimated Budget" data-validation="required validate_length length1-10">
                 <span class="currencyValue" value="${currencyCode}" name="budgetCurrency">${currencyCode}</span>
                      </div>
              </div>
               <div class="col-md-3 dropdown-select">
               <label>Consulting <span class="text-danger paddingLeft">*</span></label>
                <select class="form-control" name='consultingREPLACEROWID' id='consultingREPLACEROWID' field-name="Consulting" data-validation="required">
                  <option selected value="">--Select--</option>
                        <option value="Offsite">Off Site</option>
                        <option value="Onsite">On Site</option>
                        <option value="Onsite">Near Shore</option>
                        <option value="Onsite">Off Shore</option>
                        <option value="Onsite">Hybrid</option>
                </select>
              </div>
            
            </div>
			<div class="col-xs-12 col-sm-12 myForm noPad clearfix">
				<div class="pull-right mr">
					<input type='button' id='addResourceRequestButtonREPLACEROWID' class="btn btn-primary btn-sm mr10" onclick='addNewResourceRequestTbl(REPLACEROWID);' value='Add'/> 
				    <input type='button' id='cancelResourceRequestButtonREPLACEROWID' class="btn btn-default btn-sm cancel-btn" onclick="removeRow(REPLACEROWID);" value='Cancel'/>
				</div>
			</div>
			<div class="clearfix"></div>
          </div>
   </div>
      <!-- Content Area -->
       <section class="content-wraper">
        <section class="content-header clearfix">
         <h2 class="pull-left">New Resource Request</h2>
        <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector" style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/projectDashboard'/>">Projects List</a> &raquo;Project Summary</div>
        </section>
	   
		   <div class="content-area content-new clearfix">
		   <div id="associateRequestLoading"></div>	
		     <form:form id="add_newResource_form" name="add_newResource_form" >
		        <input type="hidden" id="estimatedBudgetCurrencyType" name="estimatedBudgetCurrencyType" value="${currencyCode}"> 	 
			    <div class="row form-horizontal mt myForm">
			      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
			         <div class="row">
			          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
			            <div class="form-group">
			              <label class="col-md-3">Request Id<span class="text-danger paddingLeft">*</span></label>
								<div class="col-md-9">
					                  <div class="form-icon">
					                   <input class="form-control pull-left" name="requestId" id="requestId" field-name="Request Id" data-validation="required validate_Space validate_length length1-15 validate_int" >
					                   <i class="fa fa-refresh btn btn-default formic1" title="Request Id"></i>
					                   <div class="clearfix"></div>
					                   </div>
					           </div>            
					     </div>
			            <div class="form-group">
			            <label class="col-md-3">Approver <span class="text-danger paddingLeft">*</span></label>
			            <div class="col-md-9">
			             <select class="chosen-select form-control" data-validation="required" id="approver" name="approver" field-name="Approver">
		                <option selected value=''>--Select Approver--</option>
			                <c:forEach var="approver" items="${approversList}">
			                    <option value="${approver.id}">${approver.fullName} (${approver.userType})</option>
			                </c:forEach>
		                </select>
		                
		                </div>
			            </div>
			            <div class="form-group">
			              <label class="col-md-3">Description<span class="text-danger paddingLeft">*</span></label>
			              <div class="col-md-9">
			                  <textarea cols="1" class="form-control" field-name="Description" id="description" name="description" data-validation="required validate_length length2-5000"></textarea>
			              </div>
			            </div>
			            </div>
			          </div>
			
			<div class="project_side_wrap_inn_tabs mt clearfix">
			    <input type="hidden"  name="maxRecCount" id="maxRecCount" value="1">
			    <div class="project_tabs_inn_head">
			        <a href="javascript:addResourceInfo()" class="AdRes"><i class="glyphicon glyphicon-plus"></i>Add</a>
			    </div>
			    <div id="resourceInfoDiv"></div>
			<!-- Table Starts Here -->
			<div class="project_tabs_inn_table overflow_Auto">
					<table id="newResourceReauestTable">
			        	<tbody>
			            	<tr id="divResourceTsRow0">
			                    <th>Role</th>
			                    <th>No <br> Resources</th>
			                    <th>Exp.</th>
			                    <th>Primary Skills</th>
			                    <th>Bill/Pay Rate</th>
			                    <th>Duration</th>
			                    <th>On/Offsite</th>
			                    <th>Est Hours</th>
			                    <th>Est Budget</th>
			                    <th>Action</th>
			                </tr>
			            </tbody>
			            
			            <tfoot class="table-footer">
			                <tr>
			                    <td><strong>Total</strong></td>
			                    <td><input type="text" value="00" class="ReadTextFli" readonly name="totalResourcesValue" id="totalResourcesId"></td>
			                    <td colspan="5"></td>
			                    <td><input type="text" value="00" class="ReadTextFli" name="totalEstHours" readonly id="totalEstHours"></td>
			                    <td><input type="text" value="00" class="ReadTextFli" name="totalEstBudget" readonly id="totalEstBudget"></td>
			                    <td></td>
			                </tr>
			            </tfoot>
			        </table>  
					      
			<!-- Table Ends Here -->
			    </div> 
			</div>        
				<div class="form-footer clearfix mt">
				  <div class="col-lg-12">
					<div class="pull-right">
					  <input type="button" value="Draft" class="btn btn-warning mr" onclick="saveNewResourceRequest('Draft');">
					  <input type="button" value="Send" class="btn btn-primary" onclick="saveNewResourceRequest('Send');">
					  <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/projectSummary?projectUUID=${projectUUID}&tabType=resources'/>'" class="btn btn-default ml">
					</div>
				  </div>
			</div>    	    
			        </div> 
			      </div>
			      </form:form>
			    </div>
			
	   </section>
          
           

		<div id="divAddRowTr1Td1" style="display: none; float: left;">
		    <div id="roleRowREPLACEROWID" class="tbldata-dotdot-100">ROLEVALUES</div>
		</div>
		<div id="divAddRowTr1Td2" style="display: none; float: left;">
		    <div id="noOfResourcesRowREPLACEROWID" name="noOfResourcesRowREPLACEROWID" class="width90px">RESOURCECOUNTVALUE</div>
		</div>
		<div id="divAddRowTr1Td3" style="display: none; float: left;">
		<div class="width90px inr_values"><small id="minRowREPLACEROWID">MINVALUE</small> <small id="maxRowREPLACEROWID">MAXVALUE</small></div>
		</div>
		<div id="divAddRowTr1Td4" style="display: none; float: left;">
		    <div id="primarySkillsRowREPLACEROWID" class="tbldata-dotdot-vsm">PRIMARYSKILLSVALUES</div>
		</div>
		<div id="divAddRowTr1Td5" style="display: none; float: left;">
		<div class="width180px">
              <small class="text-muted paddingRight pull-left mt10" id="currencyCodeRowREPLACEROWID">CURRENCYCODEVALUE</small>
              <div class="width90px inr_values">
	              <small id="billRateRowREPLACEROWID">BILLRATEVALUE</small>
	              <div class="clearfix"></div>
	              <small id="payRateRowREPLACEROWID">PAYRATEVALUE</small>
              </div>
              <small class="text-muted pull-right paddingLeft payrateType mt10" id="rateTypeRowREPLACEROWID" title="RATETYPEVALUE">RATETYPEVALUE</small>
           </div>
		</div>
		<div id="divAddRowTr1Td6" style="display: none; float: left;">
		    <div id="startDateRowREPLACEROWID"  class="width90px">STARTDATEVALUE</div>
		    <div id="endDateRowREPLACEROWID"  class="width90px">ENDDATEVALUE</div>
		</div>

		<div id="divAddRowTr1Td7" style="display: none; float: left;">
		    <div id="consultingRowREPLACEROWID">CONSULTINGVALUE</div>
		</div>
		<div id="divAddRowTr1Td8" style="display: none; float: left;">
		    <div id="estimatedHoursRowREPLACEROWID" class="width90px">ESTIMATEDHOURSVALUE</div>
		</div>
		<div id="divAddRowTr1Td9" style="display: none; float: left;">
		    <div id="estimatedBudgetRowREPLACEROWID" class="width90px">ESTIMATEDBUDGETVALUE</div>
		</div>
		
		<div id="divAddRowTr1Td10" style="display: none; float: left;">
		    <div><i class=" fa fa-edit" onclick="hideAndShowDetailsRow(REPLACEROWID,'Edit');"></i><i class="glyphicon glyphicon-trash" onclick="removeRow(REPLACEROWID)"></i></div>
		</div>
		

	<!-- <script>
	$(".addResource").slideUp(0); 
	$(".AdRes").click(function(){
		$(".addResource").slideToggle(0);
	});
	$(".addResClo").click(function(){
		$(".addResource").slideUp(0);
	});
	$(".cancel-btn").click(function(){
		$(".addResource").slideUp(0);
	});
	
	</script> -->
	
	<script>
	function addResourceInfo(){
		if ($('#add_newResource_form').validate(false, validationSettings)){
			var maxRowNum = parseInt($("input[name = 'maxRecCount']").val());
 		 
			 for(var i = 1; i < maxRowNum; i++){
				 if($("#resourceInfoContent"+i).is(':visible'))
					   {
							$("#resourceInfoContent"+i).removeAttr('style');
							$("#resourceInfoContent"+i).attr('style','display:none');
					   }
			  } 
			
			$("#maxRecCount").val(parseInt(maxRowNum + 1));
			
			$("#resourceInfoDiv").append("<div id='resourceInfoContent"+maxRowNum+"'></div>");
			
			var divRowTd2_0Str = document.getElementById("resourceInfoRow").innerHTML;
			divRowTd2_0Str = divRowTd2_0Str.replace(/REPLACEROWID/g, (maxRowNum));
			
			$("#resourceInfoContent"+maxRowNum).append(divRowTd2_0Str);
			 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
			  var finalDateFormatResourceReqSave = '';
			  if(locationDate.match("yyyy-MM-dd")){
				  finalDateFormatResourceReqSave = 'yy-mm-dd';
			  }if(locationDate.match("dd-MM-yyyy")){
				  finalDateFormatResourceReqSave = 'dd-mm-yy'; 
			  }if(locationDate.match("MM-dd-yyyy")){
				  finalDateFormatResourceReqSave = 'mm-dd-yy'; 
			  }if(locationDate.match("yyyy-dd-MM")){
				  finalDateFormatResourceReqSave = 'yy-dd-mm'; 
			  }
			
			$('#startDate'+maxRowNum).datepicker({
				dateFormat : finalDateFormatResourceReqSave,
				changeMonth: true,
				changeYear: true
			});
			$('#endDate'+maxRowNum).datepicker({
				dateFormat : finalDateFormatResourceReqSave,
				changeMonth: true,
				changeYear: true
			});
			$(".addResource").removeAttr('style');
			$(".addResource").attr('style','display:block');
			$('#currencyCode'+maxRowNum+'').selectpicker('refresh');
			$('#rateType'+maxRowNum+'').selectpicker('refresh');
			
		}  
		
	}
	</script>
	
	<script>
	function addNewResourceRequestTbl(maxRowNum){
		if ($('#add_newResource_form').validate(false, validationSettings)){ 
			var tbl = document.getElementById("newResourceReauestTable");
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divResourceTsRow" + maxRowNum);
			hideAndShowDetailsRow(maxRowNum,'Hide');
			
			$("#addResourceRequestButton"+maxRowNum).removeAttr('value');
			$("#addResourceRequestButton"+maxRowNum).attr('value','Update');
			
			$("#addResourceRequestButton"+maxRowNum).removeAttr('onclick');
			$("#addResourceRequestButton"+maxRowNum).attr('onclick','updateResourceRequestTbl('+maxRowNum+')');
			
			$("#cancelResourceRequestButton"+maxRowNum).removeAttr('onclick');
			$("#cancelResourceRequestButton"+maxRowNum).attr('onclick','hideAndShowDetailsRow('+maxRowNum+',"Hide")');
			
			
			//role
			var cell0 = row.insertCell(0);
			var divRowTd1Str = document.getElementById("divAddRowTr1Td1").innerHTML;
			divRowTd1Str = divRowTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			var roleId =  $('#role'+maxRowNum).val();
			var role = $("#role"+maxRowNum+" option[value='"+roleId+"']").text()
			var customeRole = $('#customRole'+maxRowNum).val();
			if(role == "Other"){
				divRowTd1Str = divRowTd1Str.replace(/ROLEVALUES/g,customeRole);
			}else{
				divRowTd1Str = divRowTd1Str.replace(/ROLEVALUES/g,role);
			}
			
			cell0.innerHTML = divRowTd1Str;
			
			//No.Resources
			var cell1 = row.insertCell(1);
			var divRowTd2Str = document.getElementById("divAddRowTr1Td2").innerHTML;
			divRowTd2Str = divRowTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			var resourceCount =  $('#noOfResources'+maxRowNum).val();
			if(resourceCount == null || resourceCount == '' || resourceCount == 'undefined'){
				resourceCount = "--";
			}
			divRowTd2Str = divRowTd2Str.replace(/RESOURCECOUNTVALUE/g,resourceCount);
			cell1.innerHTML = divRowTd2Str;
			
			//Experience
			var cell2 = row.insertCell(2);
			var divRowTd3Str = document.getElementById("divAddRowTr1Td3").innerHTML;
			divRowTd3Str = divRowTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			var minEx =  $('#min'+maxRowNum).val();
			var maxEx =  $('#max'+maxRowNum).val();
			if(minEx == null || minEx == '' || minEx == 'undefined'){
				minEx = "--";
			}if(maxEx == null || maxEx == '' || maxEx == 'undefined'){
				maxEx = "--";
			}
			divRowTd3Str = divRowTd3Str.replace(/MINVALUE/g,minEx);
			divRowTd3Str = divRowTd3Str.replace(/MAXVALUE/g,maxEx);
			cell2.innerHTML = divRowTd3Str;
			
			//Primary Skills
			var cell3 = row.insertCell(3);
			var divRowTd4Str = document.getElementById("divAddRowTr1Td4").innerHTML;
			divRowTd4Str = divRowTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			var primarySkills =  $('#primarySkill'+maxRowNum).val();
			if(primarySkills == null || primarySkills == '' || primarySkills == 'undefined'){
				primarySkills = "--";
			}
			divRowTd4Str = divRowTd4Str.replace(/PRIMARYSKILLSVALUES/g,primarySkills);
			cell3.innerHTML = divRowTd4Str;
			
			//Bill/Pay Rate 
			var cell4 = row.insertCell(4);
			var divRowTd4Str = document.getElementById("divAddRowTr1Td5").innerHTML;
			divRowTd4Str = divRowTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			var currencyCode =  $('#currencyCode'+maxRowNum).val();
			var billRate =  $('#billRate'+maxRowNum).val();
			var payRate =  $('#payRate'+maxRowNum).val();
			var rateType =  $('#rateType'+maxRowNum).val();
			
			if(currencyCode == null || currencyCode == '' || currencyCode == 'undefined'){
				currencyCode = "--";
			}if(billRate == null || billRate == '' || billRate == 'undefined'){
				billRate = "--";
			}if(payRate == null || payRate == '' || payRate == 'undefined'){
				payRate = "--";
			}if(rateType == null || rateType == '' || rateType == 'undefined'){
				rateType = "--";
			}
			divRowTd4Str = divRowTd4Str.replace(/CURRENCYCODEVALUE/g,currencyCode);
			divRowTd4Str = divRowTd4Str.replace(/BILLRATEVALUE/g,billRate);
			divRowTd4Str = divRowTd4Str.replace(/PAYRATEVALUE/g,payRate);
			divRowTd4Str = divRowTd4Str.replace(/RATETYPEVALUE/g,rateType);
			cell4.innerHTML = divRowTd4Str;
			
			//StartDate and EndDate
			var cell5 = row.insertCell(5);
			var divRowTd5Str = document.getElementById("divAddRowTr1Td6").innerHTML;
			divRowTd5Str = divRowTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			var startDate =  $('#startDate'+maxRowNum).val();
			if(startDate == null || startDate == '' || startDate == 'undefined'){
				startDate = "--";
			}
			divRowTd5Str = divRowTd5Str.replace(/STARTDATEVALUE/g,startDate);
			var endDate =  $('#endDate'+maxRowNum).val();
			if(endDate == null || endDate == '' || endDate == 'undefined'){
				endDate = "--";
			} 
			divRowTd5Str = divRowTd5Str.replace(/ENDDATEVALUE/g,endDate);
			cell5.innerHTML = divRowTd5Str;
			
			//Consulting Type
			var cell6 = row.insertCell(6);
			var divRowTd6Str = document.getElementById("divAddRowTr1Td7").innerHTML;
			divRowTd6Str = divRowTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			var consulting =  $('#consulting'+maxRowNum).val();
			if(consulting == null || consulting == '' || consulting == 'undefined'){
				consulting = "--";
			}
			divRowTd6Str = divRowTd6Str.replace(/CONSULTINGVALUE/g,consulting);
			cell6.innerHTML = divRowTd6Str;
			
			//Estimated Hours
			var cell7 = row.insertCell(7);
			var divRowTd7Str = document.getElementById("divAddRowTr1Td8").innerHTML;
			divRowTd7Str = divRowTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			var estimatedHours =  $('#estimatedHours'+maxRowNum).val();
			if(estimatedHours == null || estimatedHours == '' || estimatedHours == 'undefined'){
				estimatedHours = "--";
			}
			divRowTd7Str = divRowTd7Str.replace(/ESTIMATEDHOURSVALUE/g,estimatedHours);
			cell7.innerHTML = divRowTd7Str;
			
			//Estimated Budget
			var cell8 = row.insertCell(8);
			var divRowTd8Str = document.getElementById("divAddRowTr1Td9").innerHTML;
			divRowTd8Str = divRowTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
			var estimatedBudget =  $('#estimatedBudget'+maxRowNum).val();
			if(estimatedBudget == null || estimatedBudget == '' || estimatedBudget == 'undefined'){
				estimatedBudget = "--";
			}
			divRowTd8Str = divRowTd8Str.replace(/ESTIMATEDBUDGETVALUE/g,estimatedBudget);
			cell8.innerHTML = divRowTd8Str;
			
			
			var cell9 = row.insertCell(9);
			var divRowTd9Str = document.getElementById("divAddRowTr1Td10").innerHTML;
			divRowTd9Str = divRowTd9Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell9.innerHTML = divRowTd9Str;
			totalResoursec(maxRowNum);
			totalEstimatedHours(maxRowNum);
			totalEstimatedBudget(maxRowNum);
			return true;
		} else {
 			return false;
 		}
	}
	/* $('body').on('blur', '#add_resource_form', function() {
		$("#add_resource_form").showHelpOnFocus();
		$("#add_resource_form").validateOnBlur(false, validationSettings);
	}); */
	</script>
	
	
	<script>
	function updateResourceRequestTbl(rowId){
		if ($('#add_newResource_form').validate(false, validationSettings)){ 
		hideAndShowDetailsRow(rowId,'Hide');
		
		//Role
		var role =  $('#role'+rowId).val();
		var customeRole = $('#customRole'+rowId).val();
		if(role == "Other"){
			$('#roleRow'+rowId).empty();
			$('#roleRow'+rowId).html(customeRole);
		}else{
			$('#roleRow'+rowId).empty();
			$('#roleRow'+rowId).html(role);
		}
		
		//No Of Resources
		var noOfResources =  $('#noOfResources'+rowId).val();
		$('#noOfResourcesRow'+rowId).empty();
		$('#noOfResourcesRow'+rowId).html(noOfResources);
		
		
		//Experience
		var minEx =  $('#min'+rowId).val();
		var maxEx =  $('#max'+rowId).val();
		$('#minRow'+rowId).empty();
		$('#maxRow1'+rowId).empty();
		$('#minRow'+rowId).html(minEx);
		$('#maxRow'+rowId).html(maxEx);
		
		//PrimarySkill
		var primarySkill =  $('#primarySkill'+rowId).val();
		$('#primarySkillsRow'+rowId).empty();
		$('#primarySkillsRow'+rowId).html(primarySkill);
		
		
		//Bill / Pay Rate
		var currencyCode =  $('#currencyCode'+rowId).val();
		var billRate =  $('#billRate'+rowId).val();
		var payRate =  $('#payRate'+rowId).val();
		var rateType =  $('#rateType'+rowId).val();
		
		$('#currencyCodeRow'+rowId).empty();
		$('#billRateRow'+rowId).empty();
		$('#payRateRow'+rowId).empty();
		$('#rateTypeRow'+rowId).empty();
		
		$('#currencyCodeRow'+rowId).html(currencyCode);
		$('#billRateRow'+rowId).html(billRate);
		$('#payRateRow'+rowId).html(payRate);
		$('#rateTypeRow'+rowId).html(rateType);
		
		//Start date and End Date
		var startDate =  $('#startDate'+rowId).val();
		var endDate =  $('#endDate'+rowId).val();
		$('#startDateRow'+rowId).empty();
		$('#endDateRow1'+rowId).empty();
		
		$('#startDateRow'+rowId).html(startDate);
		$('#endDateRow1'+rowId).html(endDate);
		
		//consulting Type
		var consulting =  $('#consulting'+rowId).val();
		$('#consultingRow'+rowId).empty();
		$('#consultingRow'+rowId).html(consulting);
		
		
		//Estimated Hours
		var estimatedHours =  $('#estimatedHours'+rowId).val();
		$('#estimatedHoursRow'+rowId).empty();
		$('#estimatedHoursRow'+rowId).html(estimatedHours);
		
		//Estimated Budget
		var estimatedBudget =  $('#estimatedBudget'+rowId).val();
		$('#estimatedBudgetRow'+rowId).empty();
		$('#estimatedBudgetRow'+rowId).html(estimatedBudget);
		
		totalResoursec(rowId);
		totalEstimatedHours(rowId);
		totalEstimatedBudget(rowId);
	}
}
</script>
	
<script>
function changeRole(e,rowId){
	var other =  e.value;
	if(other == "Other"){
		 document.getElementById("otherRoleDiv"+rowId+"").style.display="block";
		 $('#customRole'+rowId+'').attr("data-validation","required"); 
	}else{
		document.getElementById("otherRoleDiv"+rowId+"").style.display="none";
		 $('#customRole'+rowId+'').removeAttr("data-validation","required"); 
	}
}
</script>

<script>
//calculate  Total Resources Start
function totalResoursec(rowId){
	var maxRowNum = parseInt($("input[name = 'maxRecCount']").val());
    var sum = 0;
 	    for(var i=1; i< maxRowNum; i++){
 	    	 if(document.getElementById("noOfResources"+i)){
 	    		 if(document.getElementById("noOfResources"+i).value){
 	    			var rowResourse = $('#noOfResources'+i).val();
		 	    	sum = parseFloat(sum) + parseFloat(rowResourse);
 	    		 }
 	    		
 	    	 }
 	    }
	$("#totalResourcesId").val(sum);
	
}


//calculate Total Estimated  Hours
function totalEstimatedHours(rowId){
	var maxRowNum = parseInt($("input[name = 'maxRecCount']").val());
    var sum = 0;
 	    for(var i=1; i< maxRowNum; i++){
 	    	 if(document.getElementById("estimatedHours"+i)){
 	    		 if(document.getElementById("estimatedHours"+i).value){
 	    			var rowEstimatedHours = $('#estimatedHours'+i).val();
		 	    	sum = parseFloat(sum) + parseFloat(rowEstimatedHours);
 	    		 }
 	    		
 	    	 }
 	    }
	$("#totalEstHours").val(sum);
}

//calculate Total Estimated  Budget
function totalEstimatedBudget(rowId){
	var maxRowNum = parseInt($("input[name = 'maxRecCount']").val());
    var sum = 0;
 	    for(var i=1; i< maxRowNum; i++){
 	    	 if(document.getElementById("estimatedBudget"+i)){
 	    		 if(document.getElementById("estimatedBudget"+i).value){
 	    			var rowEstBudget = $('#estimatedBudget'+i).val();
		 	    	sum = parseFloat(sum) + parseFloat(rowEstBudget);
 	    		 }
 	    		
 	    	 }
 	    }
	$("#totalEstBudget").val(sum);
}

//Remove Row 
function removeRow(rowId){
	
	//subtract row Total Resources
	var totalResources = parseInt($("input[name = 'totalResourcesValue']").val());
	var noOfResources =  $('#noOfResources'+rowId).val();
	var resourceTotal = totalResources - noOfResources; 
	$("#totalResourcesId").val(resourceTotal);
	
	
	//subtract row Estimated Hours 
	var totalEstimatedHours = parseInt($("input[name = 'totalEstHours']").val());
	var estimatedHours =  $('#estimatedHours'+rowId).val();
	var totalHours = totalEstimatedHours - estimatedHours; 
	$("#totalEstHours").val(totalHours);
	
	//subtract row Estimated Budget ,
	var totalEstimatedBudget = parseInt($("input[name = 'totalEstBudget']").val());
	var estimatedBudget =  $('#estimatedBudget'+rowId).val();
	var totalBudget = totalEstimatedBudget - estimatedBudget; 
	$("#totalEstBudget").val(totalBudget);
	
	
	$("#resourceInfoContent"+rowId).empty();
	document.getElementById('divResourceTsRow' + rowId).style.display = 'none';
	var r = document.getElementById('newResourceReauestTable').rows[rowId];
	var count = 2 * (r.cells.length);
	for (i = 0; i <= count; i++) {
		r.deleteCell(0);
	}
	
}
</script>

<script>
function hideAndShowDetailsRow(rowId,type){
	if ($('#add_newResource_form').validate(false, validationSettings)){
		var validation_required = "true";
		var entityId = parseInt(rowId-1);
		 var maxRecCount = $("#maxRecCount").val();
		 if(type != null && type != 'undefined' && type != '' && type == 'Edit'){
			 for(var i = 1; i < maxRecCount; i++){
				 if(rowId != i){
					 if($("#resourceInfoContent"+i).is(':visible'))
					   {
							$("#resourceInfoContent"+i).removeAttr('style');
							$("#resourceInfoContent"+i).attr('style','display:none');
					   }
				 }
			  } 
		 }
		
		if($("#resourceInfoContent"+rowId).is(':visible'))
	   {
			$("#resourceInfoContent"+rowId).removeAttr('style');
			$("#resourceInfoContent"+rowId).attr('style','display:none;');
	   }
		else{
			$("#resourceInfoContent"+rowId).removeAttr('style');
		}
	}
}
</script>

<script>
function flushValues(rowId){
	$("#role"+rowId+"").val('');
	$("#customRole"+rowId+"").val('');
	$("#noOfResources"+rowId+"").val('');
	$("#min"+rowId+"").val('');
	$("#max"+rowId+"").val('');
	$("#primarySkill"+rowId+"").val('');
	$("#billRate"+rowId+"").val('');
	$("#payRate"+rowId+"").val('');
	$("#startDate"+rowId+"").val('');
	$("#endDate"+rowId+"").val('');
	$("#estimatedHours"+rowId+"").val('');
	$("#estimatedBudget"+rowId+"").val('');
	$("#consulting"+rowId+"").val('');
	
}

</script>

<script>
//Function to validate if maximum exp value is more the minimum exp value
function minMaxExperience(rowId){
	var minExp = $("#min"+rowId+"").val();  
	var maxExp = $("#max"+rowId+"").val();    
    if (parseFloat(maxExp) <= parseFloat(minExp)){
        $('#expId'+rowId+'').find('p.jquery_form_error_message').remove(); 
		$('input#expValidation'+rowId+'').attr('class','error signin_fild');
		$("input#expValidation"+rowId+"").after("<p class='jquery_form_error_message'>Max Experience should be greater than Min Experience</p>");
		$('input#expValidation'+rowId+'').attr('record-exist','yes');
		$('input#expValidation'+rowId+'').attr('record-exist-errorMsg','Max Experience should be greater than Min Experience'); 
    }  else{
    	$('#expId'+rowId+'').find('p.jquery_form_error_message').remove(); 
    	$('input#expValidation'+rowId+'').removeAttr( "class record-exist record-exist-errormsg");
		$('#expId'+rowId+'').find('p.jquery_form_error_message').remove();
    }
}

</script>

<script>
function saveNewResourceRequest(saveType){
	if ($('#add_newResource_form').validate(false, validationSettings)) {
		var frm = $('#add_newResource_form').serializeFormJSON();
			var jsonData = JSON.stringify(frm);
			var form = $('#add_newResource_form')[0];
		    var formData = new FormData(form);
		  	formData.append("json",jsonData);
  			var sourceleadUrl ='${sourceleadUrl}';
  			var projectUUID = '${projectUUID}';
	  		$("#associateRequestLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  		$.ajax({
					type : "POST",
	  	    	    data: formData,
	  	    	    contentType : 'application/json; charset=utf-8',
	  		        processData: false,
	  		        contentType:false, 
	  	    	    dataType : 'json',
					url : sourceleadUrl+"/saveNewResourceRequest?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID+"&saveType="+saveType, 
					success : function(result) {
						location.replace(sourceleadUrl+"/projectSummary?projectUUID="+projectUUID+"&tabType=resources");
					},
				});  
  		       return true;
	      }else{
		       return false;
	    }
}
$('body').on('blur', '#add_newResource_form', function() {
	$("#add_newResource_form").showHelpOnFocus();
	$("#add_newResource_form").validateOnBlur(false, validationSettings);
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
 <jsp:include page="../masterFooter.jsp" />	
</html>