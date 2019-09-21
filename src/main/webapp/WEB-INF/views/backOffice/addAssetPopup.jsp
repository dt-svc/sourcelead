<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <c:set var = "dropDownSelFlag" value="false"/>
 
 <script>
 $("#ui-datepicker-div").css({"z-index":"999999 !important"});
 var dropdownSelectedUserIds = []; 
 </script>
	  <div class="modal-dialog modal-lg">
		  <div class="modal-content">
		        <div class="modal-header">
			          <button type="button" class="close" data-dismiss="modal" onClick="closeassetManagementPopup();">&times;</button>
			          <h4 class="modal-title">Asset Management</h4>
		        </div>
		        <div class="modal-body overflow_Auto" style="height:500px">
		          <form:form id="addAsset_form"  name="addAsset_form" method="post"> 
		           <!-- <i class="fa fa-close close-div"></i> -->
					 
					<div class="popinnerdiv minHeightcol5 popinnerdiv-innerscroll"> 
		            <input type="hidden" name="assetId" value="${assetAssignData.id}">
		            <div class="clearfix assetaddpop-ul col-md-5" id="categoryDiv">
		            <label class="clearfix">Category<span class="text-danger paddingLeft">*</span></label>
		            <div class="clearfix">
						<select class="chosen-select" id="category" name="category" onChange="assetCategory();" data-validation="required" field-name="Asset Category">
						<option selected value="">-Select Category-</option>
							<c:forEach var="option" items="${assetCategoriesList}">
									<option value="${option.id}" ${option.id eq assetAssignData.assetCategory.id ? 'selected' : ''}>${option.categoryName}</option>
							</c:forEach>
						</select>	
		            </div>
		            </div>
		            
		            <div class="clearfix assetaddpop-ul col-md-5" id="workingStatusDiv">
		            <label class="clearfix">Working Status<span class="text-danger paddingLeft">*</span></label>
		            <div class="clearfix">
						<select class="chosen-select" id="workingStatus" name="workingStatus" data-validation="required" field-name="Working Status">
							<option selected value="">-Select Working Status-</option>
							<option value="Assigned" ${assetAssignData.workingStatus eq "Assigned" ? 'selected' : ''}>Assigned </option>
							<option value="unassigned" ${assetAssignData.workingStatus eq "unassigned" ? 'selected' : ''}>unassigned</option>
							<option value="end of life" ${assetAssignData.workingStatus eq "end of life" ? 'selected' : ''}>end of life</option>
							<option value="Out of service" ${assetAssignData.workingStatus eq "Out Of Service" ? 'selected' : ''}>Out of service</option>
						</select>	
		            </div>
		            </div>
		            
		         
		            <div class="clearfix">
		              <div class="form-horizontal clearfix">
		                
		                <div class="col-md-5" >
		                  <label class="clearfix">Name</label>
		                  <div class="clearfix">
		                    <input type="text" class="form-control input-sm" id="name" name="name" value="${assetAssignData.name}" placeholder="Name" field-name="Name"  data-validation="validate_length length1-100" data-validation-optional="true">
		                  </div>
		                  </div>
		                  
		                
		                  <div class="col-md-5" id="snoDiv">
		                  <label class="clearfix" >Serial Number<span class="text-danger paddingLeft">*</span></label>
			                  <div class="clearfix">
			                    <input type="text" class="form-control input-sm" id="sno" name="sno" value="${assetAssignData.slno}" placeholder="Serial Number" field-name="Serial Number" onkeyup="validateAssetSno('${assetAssignData.id}');" data-validation="required validate_Space validate_NumberAndAlphabet validate_length length1-200" >
			                  </div>
		                  </div>
		                  
		                  <div class="col-md-5" id="makeDiv">
			                  <label class="clearfix">Make</label>
			                  <div class="clearfix">
			                    <input type="text" class="form-control input-sm" id="make" value="${assetAssignData.make}" name="make" placeholder="Make" data-validation-optional="true" data-validation="validate_Space validate_length length2-100">
			                  </div>
		                   </div>
		                
		                
		                  
		                  <div class="col-md-5" id="modelDiv">
		                  <label class="clearfix">Model</label>
			                  <div class="clearfix">
			                    <input type="text" class="form-control input-sm" id="model" name="model" value="${assetAssignData.model}" placeholder="Model"  data-validation-optional="true" data-validation="validate_Space validate_length length2-100" >
			                  </div>
		                  </div>
		                  <!-- <div class="col-md-4 mb10" id="nameDiv">
		                    <input type="text" class="form-control input-sm" name="name" id="name" placeholder="Name" style="display: none;" data-validation-optional="true" data-validation="validate_Space validate_length length2-100">
		                  </div> -->
		                  
		                  <div class="col-md-5" id="memoryDiv"  style="display: none;" >
		                  <label class="clearfix">Memory</label>
			                  <div class="clearfix">
		                    <input type="text" class="form-control input-sm" name="memory" id="memory" placeholder="Memory"  value="${assetAssignData.memory}" data-validation-optional="true" data-validation="validate_Space validate_NumberAndAlphabet validate_length length2-50">
		                  </div>
		                  </div>
		                  
		                  <div class="col-md-5" id="cpuDiv"  style="display: none;">
		                  <label class="clearfix">CPU</label>
			                  <div class="clearfix">
		                    <input type="text" class="form-control input-sm" name="cpu" id="cpu" placeholder="CPU" value="${assetAssignData.cpu}" data-validation-optional="true" data-validation="validate_Space validate_length length2-100">
		                  </div>
		                  </div>
		                  
		                  <div class="col-md-5" id="harddiskDiv"  style="display: none;">
		                   <label class="clearfix">Hard Disk</label>
			                  <div class="clearfix">
		                    <input type="text" class="form-control input-sm" name="harddisk" id="harddisk" placeholder="Hard Disk" value="${assetAssignData.hardDisk}" data-validation-optional="true" data-validation="validate_Space validate_length length2-100">
		                  </div>
		                  </div>
		                  
		                  <div class="col-md-5" id="monitorDiv" style="display: none;">
		                  <label class="clearfix">Monitor Serial Number</label>
								<div class="clearfix">
		                    <input type="text" class="form-control input-sm" id="monitor" name="monitor" placeholder="Monitor Serial Number"  data-validation-optional="true" value="${assetAssignData.monitor}" data-validation="validate_Space validate_NumberAndAlphabet validate_length length2-100">
		                  </div>
		                </div>
		                
				        <div class="col-md-5">
			              	<label class="clearfix">MAC Address</label>
							<div class="clearfix">
			                	<input type="text" class="form-control input-sm" id="macAddress" name="macAddress" placeholder="MAC Address" field-name="MAC Address"  value="${assetAssignData.macAddress}" data-validation="validate_length length8-50" data-validation-optional="true">
			              	</div>
				          </div>
		                
		                
		                <div class="col-md-5">
			              	<label class="clearfix">Operating System</label>
							<div class="clearfix">
			                	<input type="text" class="form-control input-sm" id="operatingSystem" name="operatingSystem" placeholder="Operating System" field-name="Operating System"  value="${assetAssignData.operatingSystem}" data-validation="validate_length length1-100" data-validation-optional="true">
			              	</div>
				          </div>
				          
				          <div class="col-md-5">
			              	<label class="clearfix">OS Key</label>
							<div class="clearfix">
			                	<input type="text" class="form-control input-sm" id="osKey" name="osKey" placeholder="OS Key" field-name="OS Key"  value="${assetAssignData.osKey}" data-validation="validate_length length5-50" data-validation-optional="true">
			              	</div>
				          </div>
				          
				          <div class="col-md-5">
			              	<label class="clearfix">Anti-Virus</label>
							<div class="clearfix">
			                	<input type="text" class="form-control input-sm" id="antiVirus" name="antiVirus" placeholder="Anti-Virus" field-name="Anti-Virus"  value="${assetAssignData.antiVirus}" data-validation="validate_length length1-100" data-validation-optional="true">
			              	</div>
				          </div>
				          
				          <div class="col-md-5">
							<label class="clearfix">Manufactured Date</label>
								<div class="clearfix">
									<div class="input-group">
									<input type="text"  name="issueDate" placeholder = "Manufactured Date" class="form-control calendar input-sm" value="${issueDateStr}" id="issueDate" readonly="readonly">
									<span class="input-group-addon" onClick="clearIssueDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
									</div>
								</div>
						  </div>
						<div class="col-md-5">
							<label class="clearfix">Purchase Date</label>
								<div class="clearfix">
									<div class="input-group">
									<input type="text"  name="purchaseDate" placeholder = "Purchase Date" class="form-control calendar input-sm" value="${purchaseDate}" id="purchaseDate" readonly="readonly">
									<span class="input-group-addon" onClick="clearPurchaseDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
									</div>
								</div>
						  </div>
						
						<div class="col-md-5">
			              	<label class="clearfix">Cost</label>
							<div class="clearfix">
			                	<input type="text" class="form-control input-sm" id="cost" name="cost" placeholder="Cost" field-name="Cost"  value="${assetAssignData.cost}" data-validation="validate_float validate_length length1-50" data-validation-optional="true">
			              	</div>
				          </div>
				          
				          <div class="col-md-5">
							<label class="clearfix">Depreciation Assessment Date</label>
								<div class="clearfix">
									<div class="input-group">
									<input type="text"  name="depreciationDate" placeholder = "Depreciation Date" class="form-control calendar input-sm" value="${depreciationDate}" id="depreciationDate" readonly="readonly">
									<span class="input-group-addon" onClick="clearDepreciationDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
									</div>
								</div>
						  </div>
				          
				          <div class="col-md-5">
			              	<label class="clearfix">Depreciation Amount</label>
							<div class="clearfix">
			                	<input type="text" class="form-control input-sm" id="depreciationAmount" name="depreciationAmount" placeholder="Depreciation Amount" field-name="Depreciation Amount"  value="${assetAssignData.depreciationAmount}" data-validation="validate_float  validate_length length1-50" data-validation-optional="true">
			              	</div>
				          </div>
				          
				          <div class="col-md-5">
			              	<label class="clearfix">Vendor Details</label>
							<div class="clearfix">
			                	<input type="text" class="form-control input-sm" id="vendorDetails" name="vendorDetails" placeholder="Vendor Details" field-name="Vendor Details"  value="${assetAssignData.vendorDetails}" data-validation=" validate_length length1-100" data-validation-optional="true">
			              	</div>
				          </div>
				          
		                <div class="clearfix">
		                  
		                  <div class="col-md-5" id="attachmentDiv">
		                  <label class="clearfix">Attachment</label>
							<div class="clearfix">
							<c:if test="${empty assetAssignData.fileName}">
		                    <div class="input-group attachfile-error" >
		                      <label class="input-group-btn"> <b class="btn btn-primary" onchange="uploadFile();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
		                        <input type="file" id="attachment"   style="display: none;" class="upload">
		                        </b> </label>
		                      <input type="text" class="form-control" name="abc" data-validation = "validatedoc" data-validation-optional="true"  readonly id="uploadFileAttachment" placeholder="Files are not selected...">
		                    </div>
		                    </c:if>
		                    <c:if test="${!empty assetAssignData.fileName}">
		                    <div class="input-group attachfile-error" id="normalUploadFile" style="display:none" >
		                      <label class="input-group-btn"> <b class="btn btn-primary" onchange="uploadFileAttachment();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
		                        <input type="file" id="fileAttachment"   style="display: none;" class="upload">
		                        </b> </label>
		                      <input type="text" class="form-control" name="abc" data-validation = "validatedoc" data-validation-optional="true"  readonly id="dbUploadFileAttachment" placeholder="Files are not selected...">
		                    </div>

		                    <div class="timesheet_week_comm invoiceDocs sizeInputfield width100percnt" id="dbUploadFile" style="display:block">
				               <span>
						         <strong id="ticketDocument16">
				    		     <i class=""><a class="glyphicon glyphicon-save" href="${assetAssignData.fileUrl}" title="download"></a></i>
								 <input type="button" title="${assetAssignData.fileName}" value="${assetAssignData.fileName}">
								 <input class="dele" type="button" onclick="deleteDocument(${assetAssignData.id},'${assetAssignData.fileUrl}');">
								 </strong>
				              </span>
			                </div>
		                    </c:if>
		                    </div>
		                  </div>
		                  
		                  <div class="col-md-5" id="descriptionDiv">
		                   <label class="clearfix">Description</label>
		                   <div class="clearfix">
		                    <textarea class="form-control" rows="2" name="description" id="description" value="${assetAssignData.description}" placeholder="Description" data-validation-optional="true" data-validation="validate_length length2-250">${assetAssignData.description}</textarea>
		                  </div>
		                   </div>
		                  <div class="clearfix"></div>
		                   <c:if test="${!empty sourceleadSectionList}">
			            	<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
			               		<c:if test="${sectionOrder.index == 0}">
			                		  <div id="customDiv${section.id}" class="columnfields-custom clearfix customfieldfor-colmn5 mt"></div>
			              		 </c:if>
			            	</c:forEach>
			   			</c:if>
		                </div>
		                
		                <c:if test="${!empty siftList}">
		                <div class="clearfix">
		                  <h4 class="col-md-12">Assign Asset</h4>
		                </div>
		                 <c:forEach var="shift" items="${siftList}" varStatus="shiftOrder">
			                <div class="clearfix mb10">
			                  <div class="col-md-3" id="uploadFile-dskADiv${shiftOrder.index}">
			                   <label class="clearfix">${shift.shiftName} <span class="text-danger paddingLeft" id="shiftStart${shiftOrder.index}"  style="display: none">*</span></label>
			                    <input type="hidden" name="assignAsset[${shiftOrder.index}].shift" value="${shift.id}"/>
			                    <input type="hidden" id="assetAssignId${shiftOrder.index}" name="assignAsset[${shiftOrder.index}].id"/>
			                    <select class="width100percnt selectpicker" id="shiftAEmp${shiftOrder.index}" name="assignAsset[${shiftOrder.index}].shiftEmp" field-name="Employee Name" onchange="removeValidation(${shiftOrder.index})"  data-live-search="true">
			                      <option  value="">-Select Employee-</option>
			                      <c:forEach var="userRole" items="${userRoleList}">
			                        <c:set var = "NUM" value = "${0}"/>
			                        <c:forEach var="existedAsset" items="${assignAssetList}">
		                              <script>
								        var assetAssignId='<c:out value="${existedAsset.id}"/>';
								        if(dropdownSelectedUserIds.indexOf(assetAssignId)<0){
								          dropdownSelectedUserIds.push(assetAssignId);
								        }
								      </script>
									    <c:if test="${(userRole.id eq existedAsset.userRole.id) && (existedAsset.shift.id eq shift.id)}">
									    	<c:set var = "NUM" value = "${1}"/>
									       <option selected value="${userRole.id}" >${userRole.user.fullName} (${userRole.userType})</option>
									    </c:if>
									</c:forEach>
									<c:if test="${NUM == 0}">
			                      		<option value="${userRole.id}" >${userRole.user.fullName} (${userRole.userType})</option>
									</c:if>
								  </c:forEach>
			                    </select>
			                  </div>
			                  <div class="col-md-3" id="uploadFile-dskBDiv">
			                  <label class="clearfix">Attachment</label>
			                  <c:forEach var="existedAssetDocuments" items="${assignAssetList}">
							    <c:if test="${(existedAssetDocuments.shift.id eq shift.id) && (!empty existedAssetDocuments.fileName)}">
							    	<c:set var = "NUM" value = "${1}"/>
							    	 <div id="downloadFile${shiftOrder.index}">
                                      <div class="timesheet_week_comm invoiceDocs sizeInputfield width100percnt">
						               <span>
								         <strong id="ticketDocument16">
						    		     <i class=""><a class="glyphicon glyphicon-save" href="${existedAssetDocuments.fileURL}" title="download"></a></i>
										 <input type="button" title="${existedAssetDocuments.fileName}" value="${existedAssetDocuments.fileName}">
										 <input class="dele" type="button" onclick="deleteAssignDocument('${shiftOrder.index}',${existedAssetDocuments.id});">
										 </strong>
						              </span>
					                </div>
					               </div>
							    </c:if>
							</c:forEach>
							<c:if test="${NUM == 0}">
	                      		<div class="input-group attachfile-error">
			                      <label class="input-group-btn"> <b class="btn btn-primary" onchange="setFilePath('${shiftOrder.index}');uploadShiftFile('${shiftOrder.index}');"><i class="fa fa-folder-open-o"></i>
			                        <input type="file" id="uploadBtn-dskA${shiftOrder.index}"   style="display: none;" class="upload">
			                        </b> </label>
			                      <input type="text"  class="form-control" id="shiftFile${shiftOrder.index}" data-validation = "validatedoc" data-validation-optional="true"   readonly  placeholder="Upload File...">
			                    </div>
							</c:if>
			                </div>
			                
	                        <c:set var = "NUM" value = "${0}"/>
	                        <c:forEach var="existedAsset" items="${assignAssetList}">
	                        	<c:if test="${(existedAsset.shift.id eq shift.id)}">
							    	<c:set var = "NUM" value = "${1}"/>
										<div class="col-md-2 paddingRight">
							                <label>Assigned On</label>
							                <div class="clearfix">
							                	<input type="text" class="form-control calendar" id="assignedOn${shiftOrder.index}" name="assignAsset[${shiftOrder.index}].assignedOn"  value="${existedAsset.assignedOnStr}" readonly  placeholder="Assigned On">
							                </div>
							              </div>
								                
								         <div class="col-md-2 paddingLeft">
							                <label>Returned On</label>
							                <div class="clearfix">
							                	<input type="text" class="form-control calendar" id="returnedOn${shiftOrder.index}" name="assignAsset[${shiftOrder.index}].returnedOn"  value="${existedAsset.returnedOnStr}" readonly  placeholder="Returned On">
							                </div>
							                </div>
							                
							                 <c:forEach var="existedAssetDocuments" items="${assignAssetList}">
							                     <c:if test="${(existedAssetDocuments.shift.id eq shift.id) && (!empty existedAssetDocuments.fileName)}">
													   <div class="col-md-1 padding_left mt25">
													    <i class="refeIcon" onclick="refrshAssignData('${shiftOrder.index}');deleteAssignDocument('${shiftOrder.index}',${existedAssetDocuments.id});"></i>
													    </div>
												 </c:if>
		     				                     <c:if test="${(existedAssetDocuments.shift.id eq shift.id) && (empty existedAssetDocuments.fileName)}">
		     				                     <div class="col-md-1 padding_left mt25">
												 	<i class="refeIcon" onclick="refrshAssignData('${shiftOrder.index}');"></i>
												 </div>
						                        </c:if>
							                </c:forEach>
							                
							                
							       </c:if>
	                        </c:forEach>
	                        <c:if test="${NUM == 0}">
	                        <div class="col-md-2 paddingRight" id="errAssignDate${shiftOrder.index}">
				                <label>Assigned On<span class="text-danger paddingLeft" id="assignOnDate${shiftOrder.index}"  style="display: none">*</span></label>
				                <div class="clearfix">
				                	<input type="text" class="form-control calendar" id="assignedOn${shiftOrder.index}" name="assignAsset[${shiftOrder.index}].assignedOn"  field-name="AssignOn"  readonly  placeholder="Assigned On">
				                </div>
				                </div>
				                
				                <div class="col-md-2 paddingLeft">
				                <label>Returned On</label>
				                <div class="clearfix">
				                	<input type="text" class="form-control calendar" id="returnedOn${shiftOrder.index}" name="assignAsset[${shiftOrder.index}].returnedOn"   readonly  placeholder="Returned On">
				                </div>
				                </div>
				                
				                <div class="col-md-1 padding_left mt25">
								    <i class="refeIcon" onclick="refrshAssignData('${shiftOrder.index}');"></i>
								</div>
								
	                        </c:if>
			              </div>
		                </c:forEach>
		              </c:if>
		                </div>
		                </div>
		            </div>
		            <div class="clearfix mr">
		                  <div class="pull-right">
		                   <input type="button" value="Add" id="add_asset" onclick="storeAsset();" class="btn btn-primary mr">
		                   <input type="button" value="Cancel" id="" onclick="closeassetManagementPopup();" class="btn btn-default">
		                  </div>
		              </div>
		            </form:form>
		   		</div>
		   </div>
	   </div>
	    <div id="divAssignAtt" style="display:none;" > 
		   <div class="input-group attachfile-error">
	          <label class="input-group-btn"> <b class="btn btn-primary" onchange="setFilePath('REPLACEROWID');uploadShiftFile('REPLACEROWID');"><i class="fa fa-folder-open-o"></i>
	            <input type="file" id="uploadBtn-dskAREPLACEROWID"   style="display: none;" class="upload">
	            </b> </label>
	          <input type="text"  class="form-control" id="shiftFileREPLACEROWID" data-validation = "validatedoc" data-validation-optional="true"   readonly  placeholder="Upload File...">
	        </div>
        </div>
  <!--  Asset Management Popup Ends -->

<script>
function populateDropdownValues(){
for (i = 0; i < dropdownSelectedUserIds.length; i++) { // to populate dropdown values.
	   $('#assetAssignId'+i).val(dropdownSelectedUserIds[i]);
	}
}
</script>

<script>
function deleteAssignDocument(index,id){
	var divadttStr = document.getElementById("divAssignAtt").innerHTML;
	divadttStr = divadttStr.replace(/REPLACEROWID/g, (index));
	$('#downloadFile'+index).empty();
	$('#downloadFile'+index).html(divadttStr);
	 var atsUrl = '${SourceLeadWeb_atsUrl}';

   $.ajax({
  	  type: "POST",
     url: atsUrl+"/deleteAssignAssetDoc?"+tokenParamName+"="+tokenValue+"&assetAssignId="+id, 	   
	  success: function(data) {  },
   });
	   
}

</script>

<script>
function getCustomDependentField(sectionId){
	var assetId = '${assetAssignData.id}';
	 $.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getAllASectionCustomFields?sectionId="+sectionId+"&assetId="+assetId+"&requestFrom=asset",
				 success: function(data) {
		        	$('#customDiv'+sectionId).empty();
		        	$('#customDiv'+sectionId).append(data);
				 }
		  });
}
</script>

<script type="text/javascript">
 function validateAssetSno(assetId) {
	 var assetSno = $("#sno").val(); 
	 var atsUrl = '${SourceLeadWeb_atsUrl}';
	 if(assetSno != null && assetSno != ""){
		 $("#add_asset").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/validateAssetSno?"+tokenParamName+"="+tokenValue+"&assetSno="+assetSno+"&assetId="+assetId, 
		        success: function(data) {
		        	if(data == true){
		        		     $('#snoDiv').find('p.jquery_form_error_message').remove();
		        			  $("input#sno").after ("<p class='jquery_form_error_message'>"+assetSno+" is Already Existed</span>");
							  document.getElementById("sno").setAttribute('record-exist','yes');
							  document.getElementById("sno").setAttribute('record-exist-errorMsg',assetSno+' is Already Existed'); 
		        		  
		        	}else{
	        			 $('input#sno').removeAttr( "record-exist record-exist-errormsg"); 
		        		 $('#snoDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#add_asset" ).addClass('us-btn').val('Add');
                         $("#add_asset").removeAttr("disabled");
		        	}
		        },
		    });
	 } 
 }
 </script>  
 
 <script>
 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
 var finalDateFormat = getJSDateFormat(locationDate);
 $('#issueDate').datepicker({
     dateFormat: finalDateFormat,
     changeMonth: true,
 	  changeYear: true, 
 });
 $('#purchaseDate').datepicker({
     dateFormat: finalDateFormat,
     changeMonth: true,
 	  changeYear: true, 
 });
 $('#depreciationDate').datepicker({
     dateFormat: finalDateFormat,
     changeMonth: true,
 	  changeYear: true, 
 });
 
 <c:forEach var="shift" items="${siftList}" varStatus="shiftOrder">
    <c:set var = "NUM" value = "${0}"/>
	 <c:forEach var="existedAsset" items="${assignAssetList}">
	    <c:if test="${existedAsset.shift.id eq shift.id}">
	    var returnedOn = '${existedAsset.returnedOnStr}';
	    var assignedOn = '${existedAsset.assignedOnStr}';
	    
	    $('#assignedOn${shiftOrder.index}').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  maxDate:returnedOn,
		  	  onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate() + 1);
				$('#returnedOn${shiftOrder.index}').datepicker("option", "minDate", dt);
			}
		  });
	    
	    $('#returnedOn${shiftOrder.index}').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  minDate:assignedOn,
		  	  onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate() - 1);
				$('#assignedOn${shiftOrder.index}').datepicker("option", "maxDate", dt);
			}
		  });
	    
	    </c:if>
	 	
	</c:forEach>
	  <c:if test="${NUM == 0}">
	 $('#assignedOn${shiftOrder.index}').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate() + 1);
			$('#returnedOn${shiftOrder.index}').datepicker("option", "minDate", dt);
			assignOnDateValidation('${shiftOrder.index}')
		     
		}
	  });
	  $('#returnedOn${shiftOrder.index}').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate() - 1);
			$('#assignedOn${shiftOrder.index}').datepicker("option", "maxDate", dt);
			returnOnDateValidation('${shiftOrder.index}');
			
		}
	  });
	</c:if> 
	 
 </c:forEach>
 
 </script>

<script>
function returnOnDateValidation(id){
	 var shiftName = $("#shiftAEmp"+id).val();
	 var assignedOn = $("#assignedOn"+id).val();
	 if(shiftName != "" && shiftName != null && shiftName != "undefined" && assignedOn != null && assignedOn != "" && assignedOn != "undefined"){
		 $("#shiftAEmp"+id).removeAttr("data-validation");
	 }else{
		 document.getElementById("shiftStart"+id+"").style.display="inline-block";
		 document.getElementById("assignOnDate"+id+"").style.display="inline-block";
		 $("#shiftAEmp"+id).attr("data-validation","required");
		 $("#assignedOn"+id).attr("data-validation","required");
	 }
	 
}
function removeValidation(id){
	var shiftName = $("#shiftAEmp"+id).val();
	if(shiftName != null && shiftName != "" && shiftName != "undefined"){
		$('#uploadFile-dskADiv'+id).find('p.jquery_form_error_message').remove();
		$("#shiftAEmp"+id).removeAttr("data-validation");
		$("#returnedOn"+id).val("").datepicker( "option" , {
	        minDate: null,
	        maxDate: null} );
		$("#assignedOn"+id).val("").datepicker( "option" , {
	        minDate: null,
	        maxDate: null} );
		$("#shiftFile"+id).val("");
	}else{
		/* document.getElementById("shiftStart"+id+"").style.display="inline-block";
		$("#shiftAEmp"+id).attr("data-validation","required"); */
	}
	 
}
function assignOnDateValidation(id){
	 var assignedOn = $("#assignedOn"+id).val();
	 if(assignedOn != null && assignedOn != "" && assignedOn != "undefined"){
		 $('#errAssignDate'+id).find('p.jquery_form_error_message').remove();
	 } 
	
}
function refrshAssignData(id){
	$("#shiftFile"+id).val("");
	$("#shiftAEmp"+id).val('').selectpicker('refresh');
	$("#returnedOn"+id).val('').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$("#assignedOn"+id).val('').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	
	document.getElementById("shiftStart"+id+"").style.display="none";
	 document.getElementById("assignOnDate"+id+"").style.display="none";
	 $("#shiftAEmp"+id).removeAttr("data-validation","required");
	 $("#assignedOn"+id).removeAttr("data-validation","required");
	 $('#errAssignDate'+id).find('p.jquery_form_error_message').remove();
	 $('#uploadFile-dskADiv'+id).find('p.jquery_form_error_message').remove();
	
}

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
	$(".selectpicker").selectpicker('refresh');
</script> 

<script>
function clearIssueDate(){
	$("#issueDate").val("");
	};
</script>