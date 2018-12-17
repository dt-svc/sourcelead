<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "formsList" scope = "page" value = '<%= session.getAttribute("templateFormsList")%>'/>
<c:set var="rowIndex" value="0" scope="page" /><!-- used for dynamic row index  rowindex starts from 0-->
<section class="addImmiWrap">
    <div class="addImmiInnWrap">
        <div class="immiStepsHead clearfix">
            <h2>Form Details</h2>
            <c:if test="${empty formsList}">
                <ul class="addImmiSteps">
                    <li class="active page-updated">1</li>
                    <li class="active page-updated">2</li>
                    <li class="active">3</li>
                    <li>4</li>
                    <b></b>
                    <div class="clearfix"></div>
                </ul>
            </c:if>
            <c:if test="${!empty formsList}">
                <ul class="addImmiSteps">
                    <li class="active page-updated">1</li>
                    <li class="active page-updated">2</li>
                    <li class="active page-updated">3</li>
                    <li>4</li>
                    <b></b>
                    <div class="clearfix"></div>
                </ul>
            </c:if>
        </div>
        <form id="petitionTemplateFormDetails" name="petitionTemplateFormDetails">
            <input type="hidden" name="id" id="templateId"/>
            <div class="addImmiContent clearfix">
                <div class=" clearfix mt" id="templateFormDivStatic">
                    <c:choose>
                        <c:when  test="${empty formsList}"> <!--  when list is empty loads default row -->
                            <div id="templateDocDivDynamic" class="col-md-12 clearfix noPad mb">
                                <div class="col-md-12 clearfix noPad">
                                	<div class="col-md-2 paddingRight">
											<select class="form-control" name="immigrationFormList[0].formCategory" onchange="validationFormName($(this),0)" id="formCategory0" title='Form Category' field-name="Select Form Category" data-validation="required">
													<option value="" selected>--Select Form Type--</option>
													<option value="PetitionerCategory" >Petitioner</option>
												    <option value="Document Type" >Beneficiary</option>
													<option value="AttorneyCategory" >Attorney</option>
											</select>
											</div>
                                    <div class="col-md-2 padding_right"><input type="text" name="immigrationFormList[0].formName" id="formName0" class="form-control" field-name="Form Name" placeholder="Form name" data-name="formName" data-validation="required"/></div>
                                    <div class="col-md-3">

                                        <div class="input-group col-md-12 errorInptgroup" style="margin:0 auto">
                                            <label class="input-group-btn"> <span class="btn btn-primary" onchange="uploadFormDocs(0);"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                                                    <input style="display: none;" onchange="uploadBtnFile(0);"  id="uploadBtn0"  class="upload" type="file">
                                                </span></label>
                                            <input type="text" class="form-control" readonly id="uploadFile0" name = "immigrationFormList[0].immigrationFormFileInfo[0].docFile"  placeholder="Attachment" field-name="Attachment" data-validation="required validatepdf">
                                            <!-- 						<input class="form-control" id="uploadFile0" placeholder="Attachment" type="text"> -->
                                        </div>		  	
                                    </div>
                                    <div class="col-md-3 noPad">
                                        <input type="text" class="form-control" name="immigrationFormList[0].formDescription" id="formDescription0" placeholder="Description" data-validation = "validate_length length2-500" data-validation-optional="true"/>
                                    </div>
                                    <div class="col-md-2 clearfix">
                                        <div class="col-md-6 noPad">
                                            <label class="switch mt5 text-center">
                                                <input checked id="requiredCheck0" name="immigrationFormList[0].isRequired" title="Is Required" type="checkbox" onclick="checkIsReqDoc(0)" value="Y">

                                                <div class="slider round"></div>
                                            </label>
                                        </div>				  
                                        <div id="divFormButton" class="col-md-4">
                                            <button type="button" id="divFormButtonDynamicRow0" onclick="appendFormDetailsDiv();" class="btn btm-sm"><i class="fa fa-plus text-primary"></i></button>
                                        </div>			  					  					  						  	
                                    </div>
                                </div>
                            </div>
                            <c:set var="rowIndex" value="${rowIndex + 1}" scope="page"/><!--setting rowIndex as 1 when default row is loaded -->
                        </c:when> 
                        <c:otherwise>		
                            <c:forEach var="item" items="${formsList}" varStatus="rows">
                                <c:choose>
                                    <c:when test="${rows.count == 1}"><!--when rowIndex is 1 defatult(first) row is loaded with add button-->
                                        <div id="templateDocDivDynamic" class="col-md-12 clearfix noPad mb">
                                            <input type="hidden" name="immigrationFormList[${rows.count-1}].id" value="${item.id}" id="templateId${rows.count-1}"/>
											<div class="col-md-2 paddingRight">
											<select class="form-control" name="immigrationFormList[${rows.count-1}].formCategory" onchange="validationFormName($(this),${rows.count-1})" id="formCategory${rows.count-1}" title='Form Category' field-name="Select Form Category" data-validation="required">
													<option value="" selected>--Select Form Type--</option>
													<option ${item.formCategory == "PetitionerCategory" ? 'selected': ''} value="PetitionerCategory">Petitioner</option>
							        				<option ${item.formCategory == "Document Type" ? 'selected': ''} value="Document Type">Beneficiary</option>
							        				<option ${item.formCategory == "AttorneyCategory" ? 'selected': ''} value="AttorneyCategory">Attorney</option>
											</select>
											</div>
                                            <div class="col-md-2 padding_right"><input type="text" name="immigrationFormList[${rows.count-1}].formName" id="formName0" class="form-control" value = "${item.formName}" field-name="Form Name" placeholder="Form name" data-name="formName" field-name="Form Name" data-validation="required"/></div>

                                            <div class="col-md-3 mt10" id="docNameDiv${rows.count-1}">
                                                <label id="documentName${rows.count-1}"  class="text-primary tbldata-dotdot-vsm pull-left" title="${item.fileName}">${item.fileName}</label>&nbsp;&nbsp;<span id="delete_file${rows.count-1}" onclick = "deleteDoc('${rows.count-1}')"><i class="fa fa-trash removeIcon"></i></span>
                                                <input  type="hidden" id="generalExisitingFile${rows.count-1}" name = "generalExisitingFile[${rows.count-1}].immigrationFormFileInfo[0].docFile" value="${item.fileName}">
                                                <a  class="glyphicon glyphicon-download-alt"  href="${item.fileUrl}" download title="Download"></a>
                                            </div>

                                            <div class="col-md-3" id ="showDocDiv${rows.count-1}" style="display: none;">
                                                <div class="input-group col-md-12 errorInptgroup" style="margin:0 auto">
                                                    <label class="input-group-btn"> <span class="btn btn-primary" onchange="uploadFormDocs('${rows.count-1}');"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                                                            <input style="display: none;" onchange="uploadBtnFile('${rows.count-1}');"  id="uploadBtn${rows.count-1}"  class="upload" type="file">
                                                        </span></label>
                                                    <input type="text" class="form-control" readonly id="uploadFile${rows.count-1}" name = "immigrationFormList[${rows.count-1}].immigrationFormFileInfo[0].docFile"  placeholder="Attachment" field-name="Attachment">
                                                    <!-- 						<input class="form-control" id="uploadFile0" placeholder="Attachment" type="text"> -->
                                                </div>			  	
                                            </div>
                                            <div class="col-md-3 noPad">
                                                <input type="text" class="form-control" name="immigrationFormList[${rows.count-1}].formDescription" value= "${item.formDescription}" id="formDescription0" placeholder="Description" data-validation = "validate_length length2-100" data-validation-optional="true"/>
                                            </div>
                                            <div class="col-md-2 clearfix">
                                                <div class="col-md-6 noPad">
                                                    <label class="switch mt5 text-center">
                                                        <input  id="requiredCheck${rows.count-1}" name="immigrationFormList[${rows.count-1}].isRequired" title="Is Required" type="checkbox" onclick="checkIsReqDoc('${rows.count-1}')" value= "${item.isRequired}" ${item.isRequired == 'Y' ? 'checked' : ''}>

                                                        <div class="slider round"></div>
                                                    </label>
                                                </div>

                                                <div id="divFormButton" class="col-md-4">
                                                    <button type="button" id="divFormButtonDynamicRow0" onclick="appendFormDetailsDiv();" class="btn btm-sm"><i class="fa fa-plus text-primary"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div id="dynamicFormRow${rows.count-1}" class="col-md-12 clearfix noPad mb"><!--when rowIndex is 1, 2 row onwards loaded with close button-->
                                            <input type="hidden" name="immigrationFormList[${rows.count-1}].id" value="${item.id}" id="templateId${rows.count-1}"/>
                                            <div class="col-md-2 paddingRight">
											<select class="form-control" name="immigrationFormList[${rows.count-1}].formCategory" onchange="validationFormName($(this),${rows.count-1})" id="formCategory${rows.count-1}" title='Form Category' field-name="Select Form Category" data-validation="required">
													<option value="" selected>--Select Form Type--</option>
													<option ${item.formCategory == "PetitionerCategory" ? 'selected': ''} value="PetitionerCategory">Petitioner</option>
							        				<option ${item.formCategory == "Document Type" ? 'selected': ''} value="Document Type">Beneficiary</option>
							        				<option ${item.formCategory == "AttorneyCategory" ? 'selected': ''} value="AttorneyCategory">Attorney</option>
											</select>
											</div>
                                            <div class="col-md-2 padding_right"><input type="text" name="immigrationFormList[${rows.count-1}].formName" id="formName0" class="form-control" value = "${item.formName}" field-name="Form Name" placeholder="Form name" data-name="formName"/></div>

                                            <div class="col-md-3 mt10" id="docNameDiv${rows.count-1}">
                                                <label id="documentName${rows.count-1}"  class="text-primary tbldata-dotdot-vsm pull-left" title="${item.fileName}">${item.fileName}</label>&nbsp;&nbsp;<span id="delete_file${rows.count-1}" onclick = "deleteDoc('${rows.count-1}')"><i class="fa fa-trash removeIcon"></i></span>
                                                <input  type="hidden" id="generalExisitingFile${rows.count-1}" name = "generalExisitingFile[${rows.count-1}].immigrationFormFileInfo[0].docFile" value="${item.fileName}">
                                                <a  class="glyphicon glyphicon-download-alt"  href="${item.fileUrl}" download title="Download"></a>
                                            </div>

                                            <div class="col-md-3 mt10" id ="showDocDiv${rows.count-1}" style="display: none;">
                                                <div class="input-group col-md-12 errorInptgroup" style="margin:0 auto">
                                                    <label class="input-group-btn"> <span class="btn btn-primary" onchange="uploadFormDocs('${rows.count-1}');"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                                                            <input style="display: none;" onchange="uploadBtnFile('${rows.count-1}');"  id="uploadBtn${rows.count-1}"  class="upload" type="file">
                                                        </span></label>
                                                    <input type="text" class="form-control" readonly id="uploadFile${rows.count-1}" name = "immigrationFormList[${rows.count-1}].immigrationFormFileInfo[0].docFile"  placeholder="Attachment" field-name="Attachment">
                                                    <!-- 						<input class="form-control" id="uploadFile0" placeholder="Attachment" type="text"> -->
                                                </div>			  	
                                            </div>
                                            <div class="col-md-3 noPad">
                                                <input type="text" class="form-control" name="immigrationFormList[${rows.count-1}].formDescription" value= "${item.formDescription}" id="formDescription0" placeholder="Description" data-validation = "validate_length length2-100" data-validation-optional="true"/>
                                            </div>
                                            <div class="col-md-2 clearfix">
                                                <div class="col-md-6 noPad">
                                                    <label class="switch mt5 text-center">
                                                        <input  id="requiredCheck${rows.count-1}" name="immigrationFormList[${rows.count-1}].isRequired" title="Is Required" type="checkbox" onclick="checkIsReqDoc('${rows.count-1}')" value= "${item.isRequired}" ${item.isRequired == 'Y' ? 'checked' : ''}>

                                                        <div class="slider round"></div>
                                                    </label>
                                                </div>

                                                <div id="divButton" class="col-md-4">
                                                    <button type="button" class="btn btm-sm" id="btnDynamicRowDelete${rows.count-1}" onclick="removeTempFormRow('${rows.count-1}')"><i class='fa fa-remove text-danger'></i></button>
                                                </div>			  					  					  						  	
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <c:set var="rowIndex" value="${rowIndex + 1}" scope="page"/><!--increment the rowIndex  for dynamic rows after save or page navigation  -->
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form>
        <div class="addImmiFoot">
            <a href="javascript:void(0)" onclick="immigration.gotoTemplatePreviousPage('templateDocs')" class="pull-left">Back</a>
            <a href="javascript:void(0)" onclick="immigration.savePetitionFormDetails('saveContinue')" class="nextBtn">Save &amp; Continue</a>
            <a href="javascript:void(0)" onclick="immigration.savePetitionFormDetails('save')" class="nextBtn">Save</a>
            <a href="javascript:void(0)" onclick="immigration.resetFormData('petitionTemplateFormDetails')">Reset</a>
            <div class="clearfix"></div>
        </div>   
    </div>
</section>

<script>
    function uploadBtnFile(id) {
        $('#uploadFile' + id).val($("#uploadBtn" + id).val().replace(/C:\\fakepath\\/i, ''));
    }
    
    function validationFormName(thisVal,count){
    	if(thisVal.val() !== "" ){
    		$("#formName"+count).attr("data-validation","required validate_length length2-100")
    	}
    }
</script> 

<script>
    function checkIsReqDoc(id) {
        var checked = document.getElementById("requiredCheck" + id).checked
        if (checked == true) {
            $("#requiredCheck" + id).val("Y");
        } else {
            $("#requiredCheck" + id).val("N");
        }
    }
</script>

<script type="text/javascript">
    function deleteDoc(id) {
        $("#docNameDiv" + id).hide();
        $("#showDocDiv" + id).show();
        $("#uploadFile" + id).attr("data-validation", "required validatepdf");
    }
</script>

<script>
    function uploadFormDocs(id) {
        fileUpload("uploadBtn" + id + "", "ImmigrationFormDocs", "petitionTemplateFormDetails", "immigrationFormList[" + id + "].immigrationFormFileInfo");
    }

</script>

<script type="text/javascript">
    function appendTemplateFormDetails(rowId) {
        var result = "";
        result = result + "<div id='dynamicFormRow" + rowId + "' class='col-md-12 clearfix noPad mb'>";
        result = result + '<div class="col-md-2 paddingRight"><select class="form-control" name="immigrationFormList['+rowId+'].formCategory" onchange="validationFormName($(this),'+rowId+')" id="formCategory'+rowId+'" title="Form Category" field-name="Select Form Category" data-validation="required">';
		result = result + '<option value="" selected>--Select Form Type--</option><option value="PetitionerCategory" >Petitioner</option><option value="Document Type" >Beneficiary</option><option value="AttorneyCategory" >Attorney</option></select>';
		result = result + "</div>";
		result = result + '<div class="col-md-2 paddingRight">';
		result = result + "<input type='text' id= 'formName" + rowId + "' name = 'immigrationFormList[" + rowId + "].formName' class='form-control' field-name='Form Name' placeholder='Form name' data-name='formName'/>";
        result = result + "</div>";
        result = result + "<div class='col-md-3'>";
        result = result + " <div class='input-group col-md-12 errorInptgroup' style='margin:0 auto'>";
        result = result + " <label class='input-group-btn'> <span class='btn btn-primary' onchange='uploadFormDocs(" + rowId + ");'><i class='fa fa-folder-open-o'></i>&nbsp;Browse File";
        result = result + " <input style='display: none;' multiple onchange='uploadBtnFile(" + rowId + ");' id='uploadBtn" + rowId + "' class='upload' type='file'>";
        result = result + " </span></label>";
        result = result + '<input type="text" class="form-control" readonly id="uploadFile' + rowId + '" name = "immigrationFormList[' + rowId + '].immigrationFormFileInfo[0].docFile"  placeholder="Attachment" field-name="Attachment" data-validation="required validatepdf">';
        result = result + "</div>";
        result = result + "</div>";
        result = result + "<div class='col-md-3 noPad'>";
        result = result + "<input type='text' title='Description' name='immigrationFormList[" + rowId + "].formDescription' id='formDescription" + rowId + "' class='form-control' placeholder='Description' data-validation = 'validate_length length2-500' data-validation-optional='true' /></div>";
        result = result + "<div class='col-md-2 clearfix'>";
        result = result + "<label class='switch mt5 text-center'>";
        result = result + "<input checked title='Is Required' onclick='checkIsReqDoc(" + rowId + ")' value='Y' id='requiredCheck" + rowId + "' name='immigrationFormList[" + rowId + "].isRequired' type='checkbox'>";
        result = result + "<div class='slider round'></div></label>";
        result = result + "<div class='col-md-6 pull-right'><button type='button' class='btn btm-sm' id='btnDynamicRowDelete" + rowId + "' onclick='removeTempFormRow(" + rowId + ")' ><i class='fa fa-remove text-danger'></i></button></div></div>";
        return result;
    }

    /* $(document).ready(function(){
     $("#templateDocDivStatic").append(appendresult());
     }); */
    var rowId = '<c:out value ="${rowIndex}"/>'// set the value for dyamic row when clicked on add button in UI.
    function appendFormDetailsDiv() {
        $("#templateFormDivStatic").append(appendTemplateFormDetails(rowId++));
        applyChangeEvent();
    }
    var deletedFormsListIds = new Array();
    function removeTempFormRow(id) {
        if ($("#templateId" + id).val() !== undefined) {
            deletedFormsListIds.push($("#templateId" + id).val());
            $("#petitionTemplateFormDetails input[data-id*=uploadBtn]" + id).remove();
        }
        $("#dynamicFormRow" + id).remove();
        $("#petitionTemplateFormDetails input[data-id*=uploadBtn]" + id).remove();
        rowId--;
    }
</script>

<script>
    $(document).ready(function () {
        $("#templateId").val(immTemplId);
    });
 
</script>