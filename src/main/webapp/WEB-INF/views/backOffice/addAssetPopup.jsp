 <!--  Asset Management Popup Start-->
  <!-- <div class="collapse-div-adduser pop-div col-md-12 assetaddpop"> -->
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
	  <div class="modal-dialog modal-lg">
		  <div class="modal-content">
		        <div class="modal-header">
			          <button type="button" class="close" data-dismiss="modal" onClick="closeassetManagementPopup();">&times;</button>
			          <h4 class="modal-title">Asset Management</h4>
		        </div>
		        <div class="modal-body">
		          <form:form id="addAsset_form"  name="addAsset_form"> 
		           <!-- <i class="fa fa-close close-div"></i> -->
					 
					 <div class="popinnerdiv minHeightcol5"> 
		            
		            <div class="clearfix assetaddpop-ul col-md-5" id="categoryDiv">
		            <label class="clearfix">Category</label>
		            <div class="clearfix">
						<select class="chosen-select" id="category" name="category" onChange="assetCategory(this);" data-validation="required" field-name="Asset Category">
						<option selected value="">-Select Category-</option>
							<c:forEach var="option" items="${assetCategoriesList}">
									<option value="${option.id}" ${option.id eq assetAssignData.assetCategory.id ? 'selected' : ''}>${option.categoryName}</option>
							</c:forEach>
						</select>	
		            </div>
		            </div>
		            
		            <div class="clearfix assetaddpop-ul col-md-5" id="workingStatusDiv">
		            <label class="clearfix">Working Status</label>
		            <div class="clearfix">
						<select class="chosen-select" id="workingStatus" name="workingStatus" data-validation="required" field-name="Working Status">
							<option selected value="">-Select Working Status-</option>
							<option value="Working">Working</option>
							<option value="Out Of Service">Out Of Service</option>
						</select>	
		            </div>
		            </div>
		            
		         
		            <div class="clearfix">
		              <div class="form-horizontal clearfix">
		                
		                
		                  <div class="col-md-5" id="snoDiv">
		                  <label class="clearfix">Serial / Port Number<span class="text-danger paddingLeft">*</span></label>
		                  <div class="clearfix">
		                    <input type="text" class="form-control input-sm" id="sno" name="sno" value="${assetAssignData.slno}" placeholder="Serial / Port Number" field-name="Serial / Port Number" onkeyup="validateAssetSno();" data-validation="required validate_Space validate_NumberAndAlphabet validate_length length1-200">
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
		                  
		                  
						<div class="col-md-5">
							<label class="clearfix">Issue Date</label>
								<div class="clearfix">
									<div class="input-group">
									<input type="text" value="" name="issueDate" placeholder = "Issue Date" class="form-control calendar input-sm" value="${assetAssignData.issueDate}" id="issueDate" readonly="readonly">
									<span class="input-group-addon" onClick="clearDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
									</div>
								</div>
						</div>
		                  
		                  <div class="col-md-5" id="monitorDiv" style="display: none;">
		                  <label class="clearfix">Monitor</label>
								<div class="clearfix">
		                    <input type="text" class="form-control input-sm" id="monitor" name="monitor" placeholder="Monitor"  data-validation-optional="true" value="${assetAssignData.monitor}" data-validation="validate_Space validate_NumberAndAlphabet validate_length length2-100">
		                  </div>
		                </div>
		                
				        <div class="col-md-5">
			              	<label class="clearfix">MAC Address</label>
							<div class="clearfix">
			                	<input type="text" class="form-control input-sm" id="macAddress" name="macAddress" placeholder="MAC Address" field-name="MAC Address"  data-validation="validate_length length8-50" data-validation-optional="true">
			              	</div>
				          </div>
		                
		                <div class="clearfix">
		                  
		                  <div class="col-md-5" id="attachmentDiv">
		                  <label class="clearfix">Attachment</label>
							<div class="clearfix">
		                    <div class="input-group attachfile-error" >
		                      <label class="input-group-btn"> <b class="btn btn-primary" onchange="uploadFile();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
		                        <input type="file" id="attachment"   style="display: none;" class="upload">
		                        </b> </label>
		                      <input type="text" class="form-control" name="abc" data-validation = "validatedoc" data-validation-optional="true"  readonly id="uploadFileAttachment" placeholder="Files are not selected...">
		                    </div>
		                    </div>
		                  </div>
		                  
		                  <div class="col-md-5" id="descriptionDiv">
		                   <label class="clearfix">Description</label>
		                   <div class="clearfix">
		                    <textarea class="form-control" rows="2" name="description" id="description" placeholder="Description" data-validation-optional="true" data-validation="validate_Space"></textarea>
		                  </div>
		                   </div>
		                  
		                   <c:if test="${!empty sourceleadSectionList}">
			            	<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
			               		<c:if test="${sectionOrder.index == 0}">
			                		  <div id="customDiv${section.id}" class="col-md-6 columnfields-custom"></div>
			              		 </c:if>
			            	</c:forEach>
			   			</c:if>
		                </div>
		                
		                <div class="clearfix">
		                  <h4 class="col-md-12">Assing Asset</h4>
		                </div>
		                 <c:forEach var="shift" items="${siftList}" varStatus="shiftOrder">
			                <div class="clearfix mb10">
			                  <div class="col-md-5" id="uploadFile-dskADiv">
			                   <label class="clearfix">${shift.shiftName}</label>
			                    <select class="width100percnt selectpicker" id="shiftAEmp" name="assignAsset[${shiftOrder.index}].shiftEmp" data-live-search="true">
			                      <option selected value="">-Select Employee-</option>
			                      <c:forEach var="userRole" items="${userRoleList}">
								     <option value="${userRole.id}">${userRole.user.fullName} (${userRole.userType})</option>
								  </c:forEach>
			                    </select>
			                  </div>
			                  <div class="col-md-5" id="uploadFile-dskBDiv">
			                  <label class="clearfix">Attachment</label>
			                    <div class="input-group attachfile-error">
			                      <label class="input-group-btn"> <b class="btn btn-primary" onchange="setFilePath('${shiftOrder.index}');uploadShiftFile('${shiftOrder.index}');"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
			                        <input type="file" id="uploadBtn-dskA${shiftOrder.index}"   style="display: none;" class="upload">
			                        </b> </label>
			                      <input type="text"  class="form-control" id="shiftFile${shiftOrder.index}" data-validation = "validatedoc" data-validation-optional="true"   readonly  placeholder="Files are not selected...">
			                    </div>
			                  </div>
			                  <button type="button" class="btn btn-primary" style="position:relative; top:22px;">
			                  	<i class="fa fa-refresh cursorPointer" title="Clear"></i>
			                  </button>
			                </div>
		                 </c:forEach>
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
  <!--  Asset Management Popup Ends -->

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