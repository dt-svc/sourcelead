<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

 <script type="text/javascript">
		window.onload = function () {
// 			getRequestedAssociateListDiv();
			var requestUUID = '${requestUUID}';
			if(requestUUID != null && requestUUID != '' && requestUUID != 'undefined'){
				loadRequestDetails();
			}else{
				getApproversList();
			}
		 };
   </script>

   <div id="resourceInfoRow" style="display: none; float: left;">
       <div class="addResource add-resource">
		<span class="addResClo addResClose"></span>
          <div class="col-xs-12 col-sm-12 myForm noPad clearfix request-resources">
            <input type="hidden" id="planningResources[REPLACEROWID].validateRow" name="planningResources[REPLACEROWID].validateRow" value="true">
              <div class="col-md-3 dropdown-select">
               <label>Role <span class="text-danger paddingLeft">*</span></label>
                <select class="form-control" name='planningResources[REPLACEROWID].roleId' id='roleREPLACEROWID'  field-name="Role" data-validation="required" onchange="changeRole(this,REPLACEROWID);" >
                 <option value="">-Select Role-</option>
                   <c:forEach var="option" items="${rolesList}">
					 <option value="${option.id}">${option.displayName}</option>
				  </c:forEach>
				  <option value="Other">Other</option>
                </select>
              </div>
             <div class="col-md-3" id="otherRoleDivREPLACEROWID" style="display:none">
               <label>Custom Role<span class="text-danger paddingLeft">*</span></label>
                <input type="text" name='planningResources[REPLACEROWID].customRole' id='customRoleREPLACEROWID' class="form-control" field-name="Custom Role">
              </div>
              <div class="col-md-3 dropdown-select">
               <label>No. of Resources <span class="text-danger paddingLeft">*</span></label>
                <input type="text" class="form-control" name='planningResources[REPLACEROWID].noOfResources' id='noOfResourcesREPLACEROWID'  field-name="No. of Resources" data-validation="required validate_length length1-15 validate_int">
              </div>
              <div class="col-md-3 dropdown-select">
               <label>Experience</label>
                  <div class="row experience-drop">
                      <div class="col-md-6">
	                      <select class="form-control" name='planningResources[REPLACEROWID].min' id='minREPLACEROWID' onblur="minMaxExperience(REPLACEROWID);">
	             		  <option value="">Min Years</option>
	             		  <c:forEach begin="0" end="25" varStatus="loop">
							    <option>${loop.index}</option>
							</c:forEach>
	             		  </select>
                       </div>
                      <div class="col-md-6">
                      <select class="form-control" name='planningResources[REPLACEROWID].max' id='maxREPLACEROWID' onblur="minMaxExperience(REPLACEROWID);">
             		  <option value="">Max Years</option>
             		  <c:forEach begin="0" end="25" varStatus="loop">
						    <option>${loop.index}</option>
						</c:forEach>
             		</select>
                      </div>
                 </div>
                 <div class="row" id="expIdREPLACEROWID">
                  <input type="hidden" name="planningResources[REPLACEROWID].expValidation" id="expValidationREPLACEROWID">
              </div> 
              </div>
              
              <div class="col-md-3 dropdown-select">
               <label>Primary Skills <span class="text-danger paddingLeft">*</span></label>
                <input type="text" class="form-control" name='planningResources[REPLACEROWID].primarySkill' id='primarySkillREPLACEROWID' field-name="Primary Skill" data-validation="required validate_Space validate_length length2-200">
              </div>
              <!--  Rate Per div  -->
              <div class="col-md-6">
              <div class="clearfix">
              <label class="pull-left">Bill / Pay Rate</label>
             <div class="clearfix"></div>
             <div class="input-group payrate_p">
                <div class="input-group-btn selp-custom-min selp-customMin">
                  <select class="" name='planningResources[REPLACEROWID].currencyCode' id='currencyCodeREPLACEROWID' >
	                 <option value="INR">INR</option>
	                 <option value="AUD">AUD</option>
	                 <option value="USD">USD</option>
	                 <option value="GBP">GBP</option>
	                 <option value="SGD">SGD</option>
	                 <option value="EUR">EUR</option>
              
                </select>
                </div>
                <div class="pull-left input_minmax">
                <input type="text" class="form-control" name='planningResources[REPLACEROWID].billRate' id='billRateREPLACEROWID' field-name="Bill Rate" placeholder="Bill Rate"  data-validation="validate_Space validate_length length1-8 validate_int" data-validation-optional="true"  value="" onblur="minMaxRate();">
                <input type="text" class="form-control" name='planningResources[REPLACEROWID].payRate' id='payRateREPLACEROWID'   field-name="Pay Rate" placeholder="Pay Rate"  data-validation="validate_Space validate_length length1-8 validate_int" data-validation-optional="true"  value="" onblur="minMaxRate();">
                </div>
                 <div class="row" id="rateValidateId">
                  <input type="hidden" name="rateValidation" id="rateValidation">
              </div> 
                <div class="input-group-btn selp-custom-min payrate_p">
                  <select class="ratetype"  name='planningResources[REPLACEROWID].rateType' id='rateTypeREPLACEROWID' field-name="Rate Per">
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
                <input type="text" class="form-control calendar" name='planningResources[REPLACEROWID].startDate' id='startDateREPLACEROWID' placeholder="Start Date" readonly>
              </div>
              <div class="col-md-3">
               <label>End Date</label>
                <input type="text" class="form-control calendar" name='planningResources[REPLACEROWID].endDate' id='endDateREPLACEROWID' placeholder="End Date" readonly>
              </div>  
              <div class="col-md-3">
               <label>Estimated Hours</label>
                <input type="text" class="form-control timeicon" name='planningResources[REPLACEROWID].estimatedHours' id='estimatedHoursREPLACEROWID' field-name="Estimated Hours" data-validation="validate_length length1-10 validate_int" data-validation-optional="true">
              </div>  
              <div class="col-md-3 currencySymbol">
                  <label>Estimated Budget</label>
                  <div class="positionRelative">
                <input type="text" class="form-control" name='planningResources[REPLACEROWID].estimatedBudget' id='estimatedBudgetREPLACEROWID' field-name="Estimated Budget" data-validation="validate_length length1-10 validate_int" data-validation-optional="true">
                 <span class="currencyValue" value="${currencyCode}" id="budgetCurrencyREPLACEROWID" name="budgetCurrencyREPLACEROWID">${currencyCode}</span>
                      </div>
              </div>
               <div class="col-md-3 dropdown-select">
               <label>Consulting <span class="text-danger paddingLeft">* </span><i class='fa fa-info-circle' title='The type of requests such as on-shore or off-shore'></i></label>
                <select class="form-control" name='planningResources[REPLACEROWID].consulting' id='consultingREPLACEROWID' field-name="Consulting" data-validation="required">
                  <option selected value="">--Select--</option>
                        <option value="OffSite">Off Site</option>
                        <option value="OnSite">On Site</option>
                        <option value="NearShore">Near Shore</option>
                        <option value="OffShore">Off Shore</option>
                        <option value="Hybrid">Hybrid</option>
                </select>
              </div>
            
            </div>
			<div class="col-xs-12 col-sm-12 myForm noPad clearfix">
				<div class="pull-right mr">
					<input type='button' id='addResourceRequestButtonREPLACEROWID' class="btn btn-primary btn-sm mr10" onclick='addNewResourceRequestTbl(REPLACEROWID);' value='Add'/> 
<!-- 				    <input type='button' id='cancelResourceRequestButtonREPLACEROWID' class="btn btn-default btn-sm cancel-btn" onclick="removeRow(REPLACEROWID);" value='Cancel'/> -->
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
          <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/projectDashboard">Projects List</a> &raquo;<a href="${SourceLeadWeb_pmsUrl}/projectSummary?projectUUID=${projectUUID}&projectClientUUID=${projectClientUUID}&tabType=resources">Project Summary</a> &raquo; New Resource Request</div>
        </section>
	   
		   <div class="content-area content-new clearfix">
		   <div id="associateRequestLoading"></div>	
		   <div id="choicepopUp"></div>	
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
					                   <i class="fa fa-refresh btn btn-default formic1" title="Request Id" onclick='$("#requestId").val("");'></i>
					                   <div class="clearfix"></div>
					                   </div>
					           </div>            
					     </div>
			            <div class="form-group">
			            <label class="col-md-3">Approver <span class="text-danger paddingLeft">* </span><i class='fa fa-info-circle' title='The person who will approve the new resource request'></i></label>
			            <div class="col-md-9" id="errApprover">
			             <select class="chosen-select form-control" data-validation="required" id="approver" name="approver" field-name="Approver" onchange="removeValidation();">
		                <option selected value=''>--Select Approver--</option>
			                <%-- <c:forEach var="approver" items="${approversList}">
			                    <option value="${approver.id}">${approver.fullName} (${approver.userType})</option>
			                </c:forEach> --%>
		                </select>
		                
		                </div>
			            </div>
			            <input type="hidden" name="savedPlanningRequestUUID" id="savedPlanningRequestUUID">
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
			    <input type="hidden"  name="maxRowCount" id="maxRowCount" value="0">
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
			                    <th>Experience</th>
			                    <th>Primary Skills</th>
			                    <th>Bill/Pay Rate</th>
			                    <th>Duration</th>
			                    <th>Emp Type</th>
			                    <th>Est Hours</th>
			                    <th>Est Budget</th>
			                    <th>Action</th>
			                </tr>
			            </tbody>
			            
			            <tfoot class="table-footer">
			                <tr>
			                    <td><strong>Total</strong></td>
			                    <td><input type="text" value="00" class="ReadTextFli ReadTextFliTxtCenter" readonly name="totalResourcesValue" id="totalResourcesId"></td>
			                    <td colspan="5"></td>
			                    <td><input type="text" value="00" class="ReadTextFli ReadTextFliTxtCenter" name="totalEstHours" readonly id="totalEstHours"></td>
			                    <td><input type="text" value="00" class="ReadTextFli ReadTextFliTxtCenter" name="totalEstBudget" readonly id="totalEstBudget"></td>
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
					  <input type="button" value="Cancel" onClick="javascript:window.location.href = '${SourceLeadWeb_pmsUrl}/projectSummary?projectUUID=${projectUUID}&projectClientUUID=${projectClientUUID}&tabType=resources'" class="btn btn-default ml">
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
		     <input type="hidden" class="form-control input-sm"  id="resourceRequestIdREPLACEROWID" name="resourceRequestIdREPLACEROWID">
		</div>
		<div id="divAddRowTr1Td2" style="display: none; float: left;">
		    <div id="noOfResourcesRowREPLACEROWID" name="noOfResourcesRowREPLACEROWID" class="width90px">RESOURCECOUNTVALUE</div>
		</div>
		<div id="divAddRowTr1Td3" style="display: none; float: left;">
		<div class="width180px inr_values"><small id="minRowREPLACEROWID">MINVALUE</small><small id="maxRowREPLACEROWID">MAXVALUE</small></div>
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
				changeYear: true,
				onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $('#endDate'+maxRowNum).datepicker("option", "minDate", dt);
		        }
			});
			$('#endDate'+maxRowNum).datepicker({
				dateFormat : finalDateFormatResourceReqSave,
				changeMonth: true,
				changeYear: true,
				onSelect: function (selected) {
		            //var dt = new Date(selected);
					var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $('#startDate'+maxRowNum).datepicker("option", "maxDate", dt);
		        }
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
			
			
			
			var maxRowCount = parseInt($("input[name = 'maxRowCount']").val());
			$("input[name = 'maxRowCount']").val(maxRowCount + 1);
			
			
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
			var year = "Year(s)";
			var to  = "To";
			if((minEx == null || minEx == '' || minEx == 'undefined') && (maxEx == null || maxEx == '' || maxEx == 'undefined')){
				minEx = "--";
				maxEx = "--";
				divRowTd3Str = divRowTd3Str.replace(/MINVALUE/g,minEx);
				divRowTd3Str = divRowTd3Str.replace(/MAXVALUE/g,maxEx);
			}else if((minEx != null || minEx != '' || minEx != 'undefined') && (maxEx == null || maxEx == '' || maxEx == 'undefined')){
				divRowTd3Str = divRowTd3Str.replace(/MINVALUE/g,minEx +year);
				divRowTd3Str = divRowTd3Str.replace(/MAXVALUE/g,maxEx);
			}else if((minEx == null || minEx == '' || minEx == 'undefined') && (maxEx !== null || maxEx != '' || maxEx != 'undefined')){
				divRowTd3Str = divRowTd3Str.replace(/MINVALUE/g,minEx);
				divRowTd3Str = divRowTd3Str.replace(/MAXVALUE/g,maxEx+year);
			}else if((minEx != null || minEx != '' || minEx != 'undefined') && (maxEx != null || maxEx != '' || maxEx != 'undefined')){
				divRowTd3Str = divRowTd3Str.replace(/MINVALUE/g,minEx+to);
				divRowTd3Str = divRowTd3Str.replace(/MAXVALUE/g,maxEx+year);
			}
			
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
				estimatedHours = "0";
			}
			divRowTd7Str = divRowTd7Str.replace(/ESTIMATEDHOURSVALUE/g,estimatedHours);
			cell7.innerHTML = divRowTd7Str;
			
			//Estimated Budget
			var cell8 = row.insertCell(8);
			var divRowTd8Str = document.getElementById("divAddRowTr1Td9").innerHTML;
			divRowTd8Str = divRowTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
			var estimatedBudget =  $('#estimatedBudget'+maxRowNum).val();
			if(estimatedBudget == null || estimatedBudget == '' || estimatedBudget == 'undefined'){
				estimatedBudget = "0";
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
	</script>
	
	
	<script>
	function updateResourceRequestTbl(rowId){
		if ($('#add_newResource_form').validate(false, validationSettings)){ 
		hideAndShowDetailsRow(rowId,'Hide');
		
		//Role
		
		var roleId =  $('#role'+rowId).val();
		var role = $("#role"+rowId+" option[value='"+roleId+"']").text()
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
		var year = " Year(s)";
		var to  = " To";
		if((minEx == null || minEx == '' || minEx == 'undefined') && (maxEx == null || maxEx == '' || maxEx == 'undefined')){
			$('#minRow'+rowId).html('--');	
			$('#maxRow'+rowId).html('--');
		}else if((minEx != null || minEx != '' || minEx != 'undefined') && (maxEx == null || maxEx == '' || maxEx == 'undefined')){
			$('#minRow'+rowId).html(minEx+year);
			$('#maxRow'+rowId).html("");
		}else if((minEx == null || minEx == '' || minEx == 'undefined') && (maxEx !== null || maxEx != '' || maxEx != 'undefined')){
			$('#maxRow'+rowId).html(maxEx+year);
		}else if((minEx != null || minEx != '' || minEx != 'undefined') && (maxEx != null || maxEx != '' || maxEx != 'undefined')){
			$('#minRow'+rowId).html(minEx + to);
			$('#maxRow'+rowId).html(maxEx+year);
		}
		
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
		if(billRate != null && billRate != ''){
			$('#billRateRow'+rowId).html(billRate);	
		}else{
			$('#billRateRow'+rowId).html('--');
		}
		if(payRate != null && payRate != ''){
			$('#payRateRow'+rowId).html(payRate);	
		}else{
			$('#payRateRow'+rowId).html('--');
		}
		$('#rateTypeRow'+rowId).html(rateType);
		
		//Start date and End Date
		var startDate =  $('#startDate'+rowId).val();
		var endDate =  $('#endDate'+rowId).val();
		$('#startDateRow'+rowId).empty();
		$('#endDateRow'+rowId).empty();
		
		if(startDate != null && startDate != ''){
			$('#startDateRow'+rowId).html(startDate);	
		}else{
			$('#startDateRow'+rowId).html('--');
		}
		if(endDate != null && endDate !=''){
			$('#endDateRow'+rowId).html(endDate);	
		}else{
			$('#endDateRow'+rowId).html('--');
		}
		
		//consulting Type
		var consulting =  $('#consulting'+rowId).val();
		$('#consultingRow'+rowId).empty();
		$('#consultingRow'+rowId).html(consulting);
		
		
		//Estimated Hours
		var estimatedHours =  $('#estimatedHours'+rowId).val();
		if(estimatedHours != ""){
			$('#estimatedHoursRow'+rowId).empty();
			$('#estimatedHoursRow'+rowId).html(estimatedHours);
		}else{
			$('#estimatedHoursRow'+rowId).empty();
			$('#estimatedHoursRow'+rowId).html(0);
		}
		
		
		//Estimated Budget
		var estimatedBudget =  $('#estimatedBudget'+rowId).val();
		if(estimatedBudget != ""){
			$('#estimatedBudgetRow'+rowId).empty();
			$('#estimatedBudgetRow'+rowId).html(estimatedBudget);
		}else{
			$('#estimatedBudgetRow'+rowId).empty();
			$('#estimatedBudgetRow'+rowId).html(0);
		}
		
		
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
	var cancelValue = $("#cancelResourceRequestButton"+rowId+"");
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
	
	var maxRowCount = parseInt($("input[name = 'maxRowCount']").val());
	$("input[name = 'maxRowCount']").val(maxRowCount - 1);

	
	
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
			var form = $('#add_newResource_form')[0];
		    var formData = new FormData(form);
  			var projectUUID = '${projectUUID}';
  			var projectClientUUID = '${projectClientUUID}';
	  		
  			var maxRowCount = $("#maxRowCount").val();
			
  			if(maxRowCount > 0){
  				$("#associateRequestLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
  		  		$.ajax({
  						type : "POST",
  		  	    	    data: formData,
  		  		        processData: false,
  		  		        contentType:false, 
  						url : pmsUrl+"/saveNewResourceRequest?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID+"&saveType="+saveType, 
  						success : function(result) {
  							if (result && result.response === "INVALID_DATA") {
  								$("#associateRequestLoading").removeAttr("style");
  			                    showError(result.responseMessage);
  			                } else if (result.response === "AWKWARD") {
  			                    location.href = "/errorPage";
  			                } else if (result.response === "SUCCESS") {
  	  							location.replace(pmsUrl+"/projectSummary?projectUUID="+projectUUID+"&projectClientUUID="+projectClientUUID+"&tabType=resources");
  			                } else if (result.indexOf('Sign In') !== -1) {
  								location.replace(pmsUrl);
  							} else {
  			                    showError("Operation failed, please try again");
  			                }
  						},
  					});
  		  	return true;
  			
  			}else{
  	 			 alertMessage();
  	 			 return false;
  			}
  		  
  		       
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
function alertMessage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Add atleast one resource request</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
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




<!-- ===================================================================================================================== -->


<script>
function loadRequestDetails(){
	var requestUUID = '${requestUUID}';
	$.ajax({
	    	type: "POST",
	    	 url: pmsUrl+"/getResourceRequestDetailsByRequestUUID?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID, 
	         success: function(json) {
	        	 $("#requestId").val(json.requestId);
	        	 $("textarea[name = 'description']").val(json.description);
	        	 getApproversList(json.approverId);
	        	 $("#savedPlanningRequestUUID").val(json.requestUUID);
	        	 loadResourceRequestsList(json.newResourceRequestList,json.estimatedHours,json.estimatedBudget,json.requestedResources);
	        	 
	        	 
	         }
	 });
	
}
</script>
<script>
function loadResourceRequestsList(newResourceRequestList,estimatedHoursTotal,estimatedBudgetTotal,requestedResources){
// 	 $("#maxRecCount").val(newResourceRequestList.length + 1);
	removeValidation();
	 for(var i=0; i<newResourceRequestList.length; i++){
		var report = newResourceRequestList[i];
		 var maxRowNum = i+1;
		 editResourceInfo(maxRowNum,report);
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
			var role = report.roleName;
			var customeRole = report.otherRole;
			if(role == "Other"){
				divRowTd1Str = divRowTd1Str.replace(/ROLEVALUES/g,customeRole);
			}else{
				divRowTd1Str = divRowTd1Str.replace(/ROLEVALUES/g,role);
			}
			cell0.innerHTML = divRowTd1Str;
			$("#resourceRequestId"+maxRowNum).val(report.id);
			
			//No.Resources
			var cell1 = row.insertCell(1);
			var divRowTd2Str = document.getElementById("divAddRowTr1Td2").innerHTML;
			divRowTd2Str = divRowTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			var resourceCount =  report.noOfResource;
			var resourceId = report.id;
			if(resourceCount == null || resourceCount == '' || resourceCount == 'undefined'){
				resourceCount = "--";
			}
			divRowTd2Str = divRowTd2Str.replace(/RESOURCECOUNTVALUE/g,resourceCount);
			if(resourceId != null && resourceId != '' && resourceId != 'undefined'){
				resourceId = report.id;
				divRowTd2Str = divRowTd2Str.replace(/RESOURCEVALUE/g,resourceId);
			}
			
			cell1.innerHTML = divRowTd2Str;
			
			
			//Experience
			var cell2 = row.insertCell(2);
			var divRowTd3Str = document.getElementById("divAddRowTr1Td3").innerHTML;
			divRowTd3Str = divRowTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			var minEx =  report.minExperience;
			var maxEx = report.maxExperience;
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
			var primarySkills =  report.skills;
			if(primarySkills == null || primarySkills == '' || primarySkills == 'undefined'){
				primarySkills = "--";
			}
			divRowTd4Str = divRowTd4Str.replace(/PRIMARYSKILLSVALUES/g,primarySkills);
			cell3.innerHTML = divRowTd4Str;
			
			//Bill/Pay Rate 
			var cell4 = row.insertCell(4);
			var divRowTd4Str = document.getElementById("divAddRowTr1Td5").innerHTML;
			divRowTd4Str = divRowTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			var currencyCode =  report.rateCurrency;
			var billRate =  report.billRate;
			var payRate =  report.payRate;
			var rateType =  report.rateType;
			
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
			var startDate = report.startDateStr;
			if(startDate == null || startDate == '' || startDate == 'undefined'){
				startDate = "--";
			}
			divRowTd5Str = divRowTd5Str.replace(/STARTDATEVALUE/g,startDate);
			var endDate =  report.endDateStr;
			if(endDate == null || endDate == '' || endDate == 'undefined'){
				endDate = "--";
			} 
			divRowTd5Str = divRowTd5Str.replace(/ENDDATEVALUE/g,endDate);
			cell5.innerHTML = divRowTd5Str;
			
			//Consulting Type
			var cell6 = row.insertCell(6);
			var divRowTd6Str = document.getElementById("divAddRowTr1Td7").innerHTML;
			divRowTd6Str = divRowTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			var consulting =  report.consultingType;
			if(consulting == null || consulting == '' || consulting == 'undefined'){
				consulting = "--";
			}
			divRowTd6Str = divRowTd6Str.replace(/CONSULTINGVALUE/g,consulting);
			cell6.innerHTML = divRowTd6Str;
			
			//Estimated Hours
			var cell7 = row.insertCell(7);
			var divRowTd7Str = document.getElementById("divAddRowTr1Td8").innerHTML;
			divRowTd7Str = divRowTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			var estimatedHours =  report.estimatedHours;
			if(estimatedHours == null || estimatedHours == '' || estimatedHours == 'undefined'){
				estimatedHours = "0";
				$("#estimatedHours"+maxRowNum).val("");
			}
			divRowTd7Str = divRowTd7Str.replace(/ESTIMATEDHOURSVALUE/g,estimatedHours);
			cell7.innerHTML = divRowTd7Str;
			
			//Estimated Budget
			var cell8 = row.insertCell(8);
			var divRowTd8Str = document.getElementById("divAddRowTr1Td9").innerHTML;
			divRowTd8Str = divRowTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
			var estimatedBudget =  report.estimatedBudget;
			if(estimatedBudget == null || estimatedBudget == '' || estimatedBudget == 'undefined'){
				estimatedBudget = "0";
				$("#estimatedBudget"+maxRowNum).val("");
			}
			divRowTd8Str = divRowTd8Str.replace(/ESTIMATEDBUDGETVALUE/g,estimatedBudget);
			cell8.innerHTML = divRowTd8Str;
			
			if(report.resourceStatus != null && report.resourceStatus == 'Approved'){
				var cell9 = row.insertCell(9);
				cell9.innerHTML = "<span class='text-success'><strong>"+report.resourceStatus+"</strong></span>"; 
			}else{
				var cell9 = row.insertCell(9);
				var divRowTd9Str = document.getElementById("divAddRowTr1Td10").innerHTML;
				divRowTd9Str = divRowTd9Str.replace(/REPLACEROWID/g, (maxRowNum));
				cell9.innerHTML = divRowTd9Str; 
			}
			
			
			totalResoursec(maxRowNum);
			totalEstimatedHours(maxRowNum);
			totalEstimatedBudget(maxRowNum);			
	 }
	     $("input[name = 'maxRowCount']").val(newResourceRequestList.length);
	     
	    $("#totalResourcesId").val(requestedResources);
		$("#totalEstHours").val(estimatedHoursTotal);
		$("#totalEstBudget").val(estimatedBudgetTotal);
}
</script>
<script>
function getApproversList(approverId){
var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
var actionType = "ROLE_RESOURCE_REQUEST_APPROVER";
	$.ajax({
	    	type: "GET",
	    	 url: pmsUrl+"/getSourceLeadActionApproversList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId+"&actionType="+actionType,
	    		success: function(json) {
	     		var output = "";
	     			output = output + "<option value=''>--Select Approver--</option>";
	     		for (var i=0; i<json.length; i++)
	     		{
		     		var opt = json[i];
		     				     		
		     		if(approverId != null && approverId != '' && approverId != 'undefined' && approverId == opt.id){
		     			var selected = 'selected';
		     			output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.name+" ("+opt.userRole+")</option>";
		     		}else{
		     			output = output + "<option value='" +opt.id+ "'>"+opt.name+" ("+opt.userRole+")</option>";
		     		} 
	     		}
	     		$("#approver").empty();
	     		$("#approver").append(output);
	     		$('#approver').trigger("chosen:updated");
	     		if(approverId != null && approverId != '' && approverId != 'undefined'){
	     			$('#add_newResource_form').validate(false, validationSettings);
	     		}
	     		
	         }
	 });
}
</script>
		

<script>
	function editResourceInfo(maxRowNum,report){
		
			$("#maxRecCount").val(parseInt(maxRowNum + 1));
			$("#resourceInfoDiv").append("<div id='resourceInfoContent"+maxRowNum+"'></div>");
			var billRate = '';
			var payRate = '';
			var startDateStr = '';
			var endDateStr = '';
			
			if(report.billRate != null && report.billRate != '' && report.billRate != 'undefined'){
				billRate = report.billRate;
			}
			if(report.payRate != null && report.payRate != '' && report.payRate != 'undefined'){
				payRate = report.payRate;
			}
			if(report.startDateStr != null && report.startDateStr != '' && report.startDateStr != 'undefined'){
				startDateStr = report.startDateStr;
			}
			if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
				endDateStr = report.endDateStr;
			}
			
			var divRowTd2_0Str ='';
			divRowTd2_0Str = divRowTd2_0Str +'<div class="addResource add-resource">';
			divRowTd2_0Str = divRowTd2_0Str +'	<span class="addResClo addResClose"></span>';
			divRowTd2_0Str = divRowTd2_0Str +'      <div class="col-xs-12 col-sm-12 myForm noPad clearfix request-resources">';
			divRowTd2_0Str = divRowTd2_0Str +'        <input type="hidden"  name="planningResources['+maxRowNum+'].validateRow" value="true">';

			divRowTd2_0Str = divRowTd2_0Str +'          <div class="col-md-3 dropdown-select">';
			divRowTd2_0Str = divRowTd2_0Str +'           <label>Role <span class="text-danger paddingLeft">*</span></label>';
			divRowTd2_0Str = divRowTd2_0Str +'            <select class="form-control" name="planningResources['+maxRowNum+'].roleId" id="role'+maxRowNum+'"  field-name="Role" data-validation="required" onchange="changeRole(this,'+maxRowNum+');" >';
			divRowTd2_0Str = divRowTd2_0Str +'             <option value="">-Select Role-</option>';
			if(report.roleName == 'Other'){
				divRowTd2_0Str = divRowTd2_0Str +'         <c:forEach var="option" items="${rolesList}">';
				divRowTd2_0Str = divRowTd2_0Str +'  <option value="${option.id}">${option.displayName}</option>';
		       divRowTd2_0Str = divRowTd2_0Str +'  </c:forEach>';
		       divRowTd2_0Str = divRowTd2_0Str +'			  <option value="Other" selected >Other</option>';
			}
			else{
				divRowTd2_0Str = divRowTd2_0Str +'         <c:forEach var="option" items="${rolesList}">';
				divRowTd2_0Str = divRowTd2_0Str +'  <option value="${option.id}" '+ (report.roleId.id == "${option.id}" ? "selected" : '') + '>${option.displayName}</option>';
		       divRowTd2_0Str = divRowTd2_0Str +'  </c:forEach>';
		       divRowTd2_0Str = divRowTd2_0Str +'			  <option value="Other"  >Other</option>';
			}
			divRowTd2_0Str = divRowTd2_0Str +'            </select>';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>';
			
			/* '+ (report.roleId.id == "${option.id}" ? "selected" : '' */
			divRowTd2_0Str = divRowTd2_0Str +'         <div class="col-md-3" id="otherRoleDiv'+maxRowNum+'" style="display:'+(report.roleName == 'Other' ? 'block':'none')+'">';
			divRowTd2_0Str = divRowTd2_0Str +'           <label>Custom Role<span class="text-danger paddingLeft">*</span></label>';
			divRowTd2_0Str = divRowTd2_0Str +'            <input type="text" value ="'+report.otherRole+'" '+(report.roleName == 'Other' ? 'data-validation="required"':'')+' name="planningResources['+maxRowNum+'].customRole" id="customRole'+maxRowNum+'" class="form-control" field-name="Custom Role">';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>'; 
			
			divRowTd2_0Str = divRowTd2_0Str +' <div class="col-md-3 dropdown-select">';
			divRowTd2_0Str = divRowTd2_0Str +'  <label>No. of Resources <span class="text-danger paddingLeft">*</span></label>';
			divRowTd2_0Str = divRowTd2_0Str +'  <input type="text" class="form-control" value="'+report.noOfResource+'" name="planningResources['+maxRowNum+'].noOfResources" id="noOfResources'+maxRowNum+'"  field-name="No. of Resources" data-validation="required validate_length length1-15 validate_int">';
			divRowTd2_0Str = divRowTd2_0Str +' </div>';
		
			divRowTd2_0Str = divRowTd2_0Str +'          <div class="col-md-3 dropdown-select">';
			divRowTd2_0Str = divRowTd2_0Str +'           <label>Experience</label>';
			divRowTd2_0Str = divRowTd2_0Str +'              <div class="row experience-drop">';
			divRowTd2_0Str = divRowTd2_0Str +'                  <div class="col-md-6">';
			divRowTd2_0Str = divRowTd2_0Str +'                      <select class="form-control" name="planningResources['+maxRowNum+'].min" id="min'+maxRowNum+'" onblur="minMaxExperience('+maxRowNum+');">';
			divRowTd2_0Str = divRowTd2_0Str +'             		  <option value="">Min Years</option>';
											 <c:forEach var="i" begin="0" end="25" >
			divRowTd2_0Str = divRowTd2_0Str +'<option value="${i}" '  + (report.minExperience == "${i}" ? "selected" : '') +  '>${i}</option>';
											</c:forEach>
			divRowTd2_0Str = divRowTd2_0Str +'             		  </select>';
			divRowTd2_0Str = divRowTd2_0Str +'                   </div>';
			
			divRowTd2_0Str = divRowTd2_0Str +'                  <div class="col-md-6">'; 
			divRowTd2_0Str = divRowTd2_0Str +'                  <select class="form-control" name="planningResources['+maxRowNum+'].max" id="max'+maxRowNum+'" onblur="minMaxExperience('+maxRowNum+');">';
			divRowTd2_0Str = divRowTd2_0Str +'         		  <option value="">Max Years</option>';
			                                  <c:forEach var="i" begin="0" end="25" >
				divRowTd2_0Str = divRowTd2_0Str +'<option value="${i}" '  + (report.maxExperience == "${i}" ? "selected" : '') +  '>${i}</option>';
											 </c:forEach>
			divRowTd2_0Str = divRowTd2_0Str +'         		</select>';
			divRowTd2_0Str = divRowTd2_0Str +'                  </div>';
			
			divRowTd2_0Str = divRowTd2_0Str +'             </div>';
			divRowTd2_0Str = divRowTd2_0Str +'             <div class="row" id="expIdREPLACEROWID">';
			divRowTd2_0Str = divRowTd2_0Str +'              <input type="hidden" name="expValidationREPLACEROWID" id="expValidationREPLACEROWID">';
			divRowTd2_0Str = divRowTd2_0Str +'          </div> ';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>';
			
			divRowTd2_0Str = divRowTd2_0Str +'          <div class="col-md-3 dropdown-select">';
			divRowTd2_0Str = divRowTd2_0Str +'           <label>Primary Skills <span class="text-danger paddingLeft">*</span></label>';
			divRowTd2_0Str = divRowTd2_0Str +'            <input type="text" class="form-control" value="'+ report.skills+'" name="planningResources['+maxRowNum+'].primarySkill" id="primarySkill'+maxRowNum+'" field-name="Primary Skill" data-validation="required validate_Space validate_length length2-200">';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>';
			
			divRowTd2_0Str = divRowTd2_0Str +'          <!--  Rate Per div  -->';
			divRowTd2_0Str = divRowTd2_0Str +'          <div class="col-md-6">';
			divRowTd2_0Str = divRowTd2_0Str +'          <div class="clearfix">';
			divRowTd2_0Str = divRowTd2_0Str +'          <label class="pull-left">Bill / Pay Rate</label>';
			divRowTd2_0Str = divRowTd2_0Str +'         <div class="clearfix"></div>';
			divRowTd2_0Str = divRowTd2_0Str +'         <div class="input-group payrate_p">';
			divRowTd2_0Str = divRowTd2_0Str +'            <div class="input-group-btn selp-custom-min selp-customMin">';
			divRowTd2_0Str = divRowTd2_0Str +'              <select class="" name="planningResources['+maxRowNum+'].currencyCode" id="currencyCode'+maxRowNum+'">';
			divRowTd2_0Str = divRowTd2_0Str +'                 <option '+(report.rateCurrency == "INR" ? "selected" : '') +' >INR</option>';
			divRowTd2_0Str = divRowTd2_0Str +'                 <option '+(report.rateCurrency == "AUD" ? "selected" : '') +' >AUD</option>';
			divRowTd2_0Str = divRowTd2_0Str +'                 <option '+(report.rateCurrency == "USD" ? "selected" : '') +' >USD</option>';
			divRowTd2_0Str = divRowTd2_0Str +'                 <option '+(report.rateCurrency == "GBP" ? "selected" : '') +' >GBP</option>';
			divRowTd2_0Str = divRowTd2_0Str +'                 <option '+(report.rateCurrency == "SGD" ? "selected" : '') +' >SGD</option>';
			divRowTd2_0Str = divRowTd2_0Str +'                 <option '+(report.rateCurrency == "EUR" ? "selected" : '') +' >EUR</option>';
			divRowTd2_0Str = divRowTd2_0Str +'            </select> ';
			divRowTd2_0Str = divRowTd2_0Str +'            </div>    ';
			
			divRowTd2_0Str = divRowTd2_0Str +'            <div class="pull-left input_minmax">';
			divRowTd2_0Str = divRowTd2_0Str +'            <input type="text" class="form-control"  value="'+billRate+'" name="planningResources['+maxRowNum+'].billRate" id="billRate'+maxRowNum+'" field-name="Bill Rate" placeholder="Bill Rate"  data-validation="validate_Space validate_length length1-8 validate_int" data-validation-optional="true"  value="" onblur="minMaxRate();">';
			divRowTd2_0Str = divRowTd2_0Str +'            <input type="text" class="form-control" value="'+payRate+'" name="planningResources['+maxRowNum+'].payRate" id="payRate'+maxRowNum+'"   field-name="Pay Rate" placeholder="Pay Rate"  data-validation="validate_Space validate_length length1-8 validate_int" data-validation-optional="true"  value="" onblur="minMaxRate();">';
			divRowTd2_0Str = divRowTd2_0Str +'            </div>';
			divRowTd2_0Str = divRowTd2_0Str +'             <div class="row" id="rateValidateId">';
			divRowTd2_0Str = divRowTd2_0Str +'              <input type="hidden" name="rateValidation" id="rateValidation">';
			divRowTd2_0Str = divRowTd2_0Str +'          </div> ';
			divRowTd2_0Str = divRowTd2_0Str +'            <div class="input-group-btn selp-custom-min payrate_p">';
			divRowTd2_0Str = divRowTd2_0Str +'              <select class="ratetype"  name="planningResources['+maxRowNum+'].rateType" id="rateType'+maxRowNum+'" field-name="Rate Per">';
			divRowTd2_0Str = divRowTd2_0Str +'                <option '+(report.rateType == "Hourly" ? "selected" : '') +' >Hourly</option>                  ';
			divRowTd2_0Str = divRowTd2_0Str +'                <option '+(report.rateType == "Weekly" ? "selected" : '') +'  >Weekly</option>                  ';
			divRowTd2_0Str = divRowTd2_0Str +'                <option '+(report.rateType == "Bi-weekly" ? "selected" : '') +' >Bi-weekly</option>            ';
			divRowTd2_0Str = divRowTd2_0Str +'                <option '+(report.rateType == "Semi-Monthly" ? "selected" : '') +' >Semi-Monthly</option>      ';
			divRowTd2_0Str = divRowTd2_0Str +'                <option '+(report.rateType == "Monthly" ? "selected" : '') +'  >Monthly</option>               ';
			divRowTd2_0Str = divRowTd2_0Str +'                <option '+(report.rateType == "Bi-Monthly" ? "selected" : '') +' >Bi-Monthly</option>          ';
			divRowTd2_0Str = divRowTd2_0Str +'                <option '+(report.rateType == "Yearly" ? "selected" : '') +'  >Yearly</option>                  ';
			divRowTd2_0Str = divRowTd2_0Str +'              </select>                                                 ';
			divRowTd2_0Str = divRowTd2_0Str +'            </div>                                                      ';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>                                                        ';
			divRowTd2_0Str = divRowTd2_0Str +'        </div>                                                          ';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>                                                        ';
			
			divRowTd2_0Str = divRowTd2_0Str +'          <div class="col-md-3">                                        ';
			divRowTd2_0Str = divRowTd2_0Str +'           <label>Start Date</label>                                    ';
			divRowTd2_0Str = divRowTd2_0Str +'            <input type="text" class="form-control calendar" value="'+startDateStr+'" name="planningResources['+maxRowNum+'].startDate" id="startDate'+maxRowNum+'" placeholder="Start Date" readonly>';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>';
			
			divRowTd2_0Str = divRowTd2_0Str +'          <div class="col-md-3">';
			divRowTd2_0Str = divRowTd2_0Str +'           <label>End Date</label>';
			divRowTd2_0Str = divRowTd2_0Str +'            <input type="text" class="form-control calendar" value="'+endDateStr+'" name="planningResources['+maxRowNum+'].endDate" id="endDate'+maxRowNum+'" placeholder="End Date" readonly>';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>  ';
			
			divRowTd2_0Str = divRowTd2_0Str +'          <div class="col-md-3">';
			divRowTd2_0Str = divRowTd2_0Str +'           <label>Estimated Hours</label>';
			divRowTd2_0Str = divRowTd2_0Str +'            <input type="text" class="form-control timeicon" value="'+report.estimatedHours+'" name="planningResources['+maxRowNum+'].estimatedHours" id="estimatedHours'+maxRowNum+'" field-name="Estimated Hours" data-validation="validate_length length1-10 validate_int" data-validation-optional="true">';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>  ';
			
			divRowTd2_0Str = divRowTd2_0Str +'          <div class="col-md-3 currencySymbol">';
			divRowTd2_0Str = divRowTd2_0Str +'              <label>Estimated Budget</label>';
			divRowTd2_0Str = divRowTd2_0Str +'              <div class="positionRelative">';
			divRowTd2_0Str = divRowTd2_0Str +'            <input type="text" class="form-control" value="'+report.estimatedBudget+'" name="planningResources['+maxRowNum+'].estimatedBudget" id="estimatedBudget'+maxRowNum+'" field-name="Estimated Budget" data-validation="validate_length length1-10 validate_int" data-validation-optional="true">';
			divRowTd2_0Str = divRowTd2_0Str +'             <span class="currencyValue" value="'+report.estimatedBudgetCurrency+'" id="budgetCurrency'+maxRowNum+'" name="planningResources['+maxRowNum+'].budgetCurrency">${currencyCode}</span>';
			divRowTd2_0Str = divRowTd2_0Str +'                  </div>';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>';
			
			divRowTd2_0Str = divRowTd2_0Str +'           <div class="col-md-3 dropdown-select">'; 
			divRowTd2_0Str = divRowTd2_0Str +'           <label>Consulting <span class="text-danger paddingLeft">*</span></label>';
			divRowTd2_0Str = divRowTd2_0Str +'            <select class="form-control" name="planningResources['+maxRowNum+'].consulting" id="consulting'+maxRowNum+'" field-name="Consulting" data-validation="required">';
			divRowTd2_0Str = divRowTd2_0Str +'              <option selected value="">--Select--</option>      ';
			divRowTd2_0Str = divRowTd2_0Str +'                    <option '+(report.consultingType == "OffSite" ? "selected" : '') +' value="OffSite">Off Site</option>    ';
			divRowTd2_0Str = divRowTd2_0Str +'                    <option '+(report.consultingType == "OnSite" ? "selected" : '') +' value="OnSite">On Site</option>      ';
			divRowTd2_0Str = divRowTd2_0Str +'                    <option '+(report.consultingType == "NearShore" ? "selected" : '') +' value="NearShore">Near Shore</option>   ';
			divRowTd2_0Str = divRowTd2_0Str +'                    <option '+(report.consultingType == "OffShore" ? "selected" : '') +' value="OffShore" >Off Shore</option>    ';
			divRowTd2_0Str = divRowTd2_0Str +'                    <option '+(report.consultingType == "Hybrid" ? "selected" : '') +'  value="Hybrid">Hybrid</option>       ';
			divRowTd2_0Str = divRowTd2_0Str +'            </select>                                            ';
			divRowTd2_0Str = divRowTd2_0Str +'          </div>                                                 ';
			divRowTd2_0Str = divRowTd2_0Str +'        </div>                                                   '; 
			
			divRowTd2_0Str = divRowTd2_0Str +'		<div class="col-xs-12 col-sm-12 myForm noPad clearfix">    ';
			divRowTd2_0Str = divRowTd2_0Str +'			<div class="pull-right mr">                            ';
			divRowTd2_0Str = divRowTd2_0Str +'				<input type="button" id="addResourceRequestButton'+maxRowNum+'" class="btn btn-primary btn-sm mr10" onclick="addNewResourceRequestTbl('+maxRowNum+');" value="Add"/>'; 
			divRowTd2_0Str = divRowTd2_0Str +'			    <input type="button" id="cancelResourceRequestButton'+maxRowNum+'" class="btn btn-default btn-sm cancel-btn" onclick="removeRow('+maxRowNum+');" value="Cancel"/>';
			divRowTd2_0Str = divRowTd2_0Str +'			</div>'; 
			divRowTd2_0Str = divRowTd2_0Str +'		</div>';
			divRowTd2_0Str = divRowTd2_0Str +'		<div class="clearfix"></div>';
			divRowTd2_0Str = divRowTd2_0Str +'		</div>';
		
			$("#resourceInfoContent"+maxRowNum).append(divRowTd2_0Str);
			$("#resourceInfoContent"+maxRowNum).removeAttr('style');
			$("#resourceInfoContent"+maxRowNum).attr('style','display:none');
			$('#startDate'+maxRowNum).datepicker({
				dateFormat : 'dd-mm-yy',
				changeMonth: true,
				changeYear: true
			});
			$('#endDate'+maxRowNum).datepicker({
				dateFormat : 'dd-mm-yy',
				changeMonth: true,
				changeYear: true
			});
			$(".addResource").removeAttr('style');
			$(".addResource").attr('style','display:block');
			$('#currencyCode'+maxRowNum+'').selectpicker('refresh');
			$('#rateType'+maxRowNum+'').selectpicker('refresh');
		
	}
	</script>
	<script>
function editChangeRole(e,rowId){
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
function removeValidation(){
	$('#errApprover').find('p.jquery_form_error_message').remove(); 
}
</script>
 <jsp:include page="../masterFooter.jsp" />	
</html>