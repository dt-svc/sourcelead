<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <script type="text/javascript" src='resources/js/slead-common.js'></script>

 <c:forEach var="section" items="${SourceleadForm_Sections}" varStatus="sectionStatus">
     <div class="cusFielInn" >
        <div class="cusFielInnHead clearfix">
	  				<h2>${section.sectionName}</h2>
	  				<c:if test="${sectionStatus.index == 0}">
		          	    <a href="javascript:void(0)" id="addField" onclick="addFieldPopUp();" ><i class="glyphicon glyphicon-plus"></i> Add Field</a> 
		          	</c:if>
		</div>
	  	<div class="cusFielTable" >
			<table>
			<tbody id="cusFielTable${section.id}">
			  <tr>
				<th>Action</th>
				<th class="tblminwidth-150">Field Name</th>
				<th class="tblminwidth-75">Field Type</th>
				<th class="tblminwidth-100">Required ?</th>
				<th class="tblminwidth-150">PlaceHolder</th>
				<th class="tblminwidth-75">Status</th>
				<th>Description</th>
				<th class="tblminwidth-150">Tool Tip</th>
				
				
			  </tr>
				<c:forEach var="fieldValue" items="${section.formFieldsList}">
					  <tr>
						<td class="cusFielAction">
						   <div>
							<i class="fa fa-eye"></i>
				            </div>
				        </td>
						<td><div class="tblminwidth-150">${fieldValue.labelName}</div></td>
						<td><div class="tblminwidth-75">${fieldValue.fieldType}</div></td>
						<td><div class="tblminwidth-30">${fieldValue.isFieldRequired == '0'? 'No':'Yes'}</div></td>
						<td><div class="tblminwidth-150">${fieldValue.placeHolder}</div></td>
						<td><div class="tblminwidth-75">${fieldValue.fieldstatus == '0'? 'InActive':'Active'}</div></td>
						<c:if test="${!empty fieldValue.fieldDesc}">
							<td><div class="showmore tblminwidth-150">${fieldValue.fieldDesc}</div></td>
						</c:if>
						<c:if test="${empty fieldValue.fieldDesc}">
							<td><div class="showmore tblminwidth-150 text-center">--</div></td>
						</c:if>
						<td><div class="showmore tblminwidth-150">${fieldValue.labelInfo}</div></td>
						
					  </tr>
			    </c:forEach>
			     <c:forEach var="customFormDetailsDTO" items="${section.customFieldsList}">
			     	<c:set var="customFormDetails" value="${customFormDetailsDTO}" scope="request"/>
  					 <jsp:include page="customFormFieldData.jsp"></jsp:include>
                </c:forEach>						
			</tbody>
			 </table>
     </div>
     </div>
 </c:forEach>


 
 
  <!-- <!--   Popup Code  -->
 
 
  <form:form id="saveAddForm_form" name="saveAddForm_form">
  <div class="popMaskCustom popMask-zindex"></div>
	<div class="cusFielPopup cusFiel-popup " id="popUpDataCustom">
		<div class="popupHead">
			<h2>Add New Field</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		<div class="cuspopHead clearfix">
			<div class="col-md-6 padding_left">
			<c:if test="${!empty SourceleadForm_Sections}">
			<select class="chosen-select form-control" id="addFieldSection" name="sectionId" onchange="getDependentFieldList();getCustomDependentFieldList();">
				 <c:forEach var="section" items="${SourceleadForm_Sections}" varStatus="sectionStatus">
				  <option value="${section.id}">${section.sectionName}</option>
				  </c:forEach>
			</select> 
			</c:if>
			<c:if test="${!empty SourceleadForm_Sections_pop}">
			<select class="chosen-select form-control" id="addFieldSection" name="sectionId" onchange="getDependentFieldList();getCustomDependentFieldList();">
				 <c:forEach var="section" items="${SourceleadForm_Sections_pop}" varStatus="sectionStatus">
				  <option ${section.id == customFormPopDetails.sectionId ?'selected':''} value="${section.id}">${section.sectionName}</option>
				  </c:forEach>
			</select> 
			</c:if>
			</div>
			<div class="col-md-6 noPad">
				<select class="chosen-select form-control" id="fieldsDropDown" name="fieldType"  onchange="getSelectedFieldDetails();">
				  <option value ="textfield" value="">--Select Field Type--</option>
				  <option value ="textfield" ${customFormPopDetails.fieldType =='textfield' ? 'selected':''} selected>Text field</option>
				  <option value ="datefield" ${customFormPopDetails.fieldType =='datefield' ? 'selected':''} >Date field</option>
				  <option value ="selectfield" ${customFormPopDetails.fieldType =='selectfield' ? 'selected':''} >Select field</option>
				  <option value ="checkBox" ${customFormPopDetails.fieldType =='checkBox' ? 'selected':''} >Check box</option>
				  <option value ="radioButton" ${customFormPopDetails.fieldType =='radioButton' ? 'selected':''} >Radio button</option>
				</select>				
			</div>			
		</div>
		
		<div class="popupMid clearfix">

				 <div class="col-sm-12 noPad mt clearfix">
				  	  <div class="col-md-3 noPad labHeight">Label<span class="text-danger paddingLeft">*</span></div>
					  <div class="col-md-6"><input name="labelName" id="labelName" value="${customFormPopDetails.labelName}" field-name="Label Name" class="field-label form-control input-sm" data-validation="required validate_length length1-250"  type="text"></div>
				</div>
			  
			  <div class="col-sm-12 noPad mt clearfix">
				  	  <div class="col-md-3 noPad labHeight">Is Required? <span class="text-danger paddingLeft">*</span></div>
					  <div class="col-md-6"><input name="isRequired" value="${customFormPopDetails.isRequired}" id="isRequired" class="toggle-required" type="checkbox"></div>
				</div>
			  
				<div class="clearfix"></div>
				<div  id="dateFieldDiv" style="display: none">
					<div class="col-sm-12 noPad mt clearfix">
					   <div class="col-md-3 noPad labHeight">Min Date</div>
						<div class="col-md-6"><input name="minDate" id="minDate" value="${customFormPopDetails.minDate}" id = "minDate" class="field-label form-control input-sm calendar" type="text"> </div>
						<div class="col-md-3 lblHeight"><span>&nbsp;</span></div>
					</div>
					
					<div class="col-sm-12 noPad mt clearfix">
						   <div class="col-md-3 noPad labHeight">Max Date</div>
							<div class="col-md-6"><input name="maxDate" id="maxDate" value="${customFormPopDetails.maxDate}" id = "maxDate" class="field-label form-control input-sm calendar" type="text"> </div>
							<div class="col-md-3 lblHeight"><span>&nbsp;</span></div>
					</div>
			  </div>


            <div  id="selectFieldDiv" style="display: none">
				<div class="col-sm-12 noPad mt clearfix">
					   <div class="col-md-3 noPad labHeight"><p>Options</p></div>
						<div class="col-md-6"><input name="optionData[0].option" id ="selectOptionId" class="field-label form-control input-sm" type="text"> </div>
						<div class="col-md-3 lblHeight"><span>&nbsp;</span>
					</div>
				</div>
				
				<input type="hidden" id="optionCounter" value="1"/>
				<div id="optionsDiv"></div>	
				 <div class="col-sm-12 noPad mt clearfix">
				  <div class="col-md-3 noPad labHeight">&nbsp;</div>
				  <div class="col-md-6" id="addOptionDiv"><a href="#" onclick="addOption();"><p>Add Option</p></a></div>
				</div>			  
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="placeHolderDiv">
			  <div class="col-md-3 noPad labHeight">PlaceHolder</div>
			  <div class="col-md-6"><input name="placeHolder" id="placeHolder" value="${customFormPopDetails.placeHolder}" class="field-label form-control input-sm" type="text"> </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Status</div>
			  <div class="col-md-6 dropdown-select">
			  <select name="status" class="field-label form-control input-sm" id="statusId">
			  	<option value="" selected>--Select Status--</option>
			  	<option ${customFormPopDetails.status == 1 ? 'selected':''} value="1">Active</option>
			  	<option ${customFormPopDetails.status == 0 ? 'selected':''} value="0">In-Active</option>
			  </select>
			  </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="placeHolderDiv">
			  <div class="col-md-3 noPad labHeight">Description</div>
			  <div class="col-md-6"><input name="fieldDescription" id="fieldDescription" value="${customFormPopDetails.fieldDescription}" class="field-label form-control input-sm" type="text"> </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="labelInfoDiv">
			  <div class="col-md-3 noPad labHeight">Tool Tip</div>
			  <div class="col-md-6"><input type="text" name="labelinfo" id="labelinfo" value="${customFormPopDetails.labelinfo}" class="field-label form-control input-sm" > </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Dependent Fields</div>
			  <div class="col-md-6 dropdown-select"><select name="slDependedntField" class="field-label form-control input-sm" id="dependentFieldDiv"> 
			  </select>
			  </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="customDependentFieldDiv">
			  <div class="col-md-3 noPad labHeight">Custom Dependent Fields</div>
			  <div class="col-md-6"><select name="customDependentField" class="field-label form-control input-sm" id="customDependentField"> </select></div>
			</div>
			
			</div>
		
		
		<div class="immiPopFoot">
            <input value="Add" class="" name="save" id="saveNewField"  type="button" onclick="saveAddFormField();">
            <input value="Cancel" class="popCan" name = "cancel" type="button">
          </div>		
		</div>
</form:form>
<!--   Popup Code  -->
 
 <script>
  function addOption(){
	  var optionCounter = $("#optionCounter").val();
	  var divText = '<div id="optionDiv'+optionCounter+'" class="col-sm-12 noPad mt clearfix">'+
	                 '<div class="col-md-3 noPad labHeight">&nbsp;</div>'+
	                   '<div class="col-md-6"><input id = "selectOptionId" placeholder="" name="optionData['+optionCounter+'].option" class="field-label form-control input-sm" type="text"> </div>'+
	                   '<div class="col-md-3 lblHeight"><span class="OpRemove"><i onclick = "removeOption('+optionCounter+')" class="fa fa-close"></i></span>'+
		             '</div>'+
		             '</div>';
	  
      if(optionCounter >1){
    	  $("#optionCounter").val(parseInt(optionCounter)+1);
    	  $('#optionsDiv').append(divText);
      }else{
    	  $("#optionCounter").val(parseInt(optionCounter)+1);
    	  $('#optionsDiv').html('');
    	  $('#optionsDiv').append(divText);
      }
      
	}
    function removeOption(id)
	{
    	$("#optionDiv"+id).remove();
	}
 </script>
 <script>
$(document).ready(function() {
	getDependentFieldList();
	getCustomDependentFieldList();
});
</script>
 
 <script>
 function getDependentFieldList(){
	 var selectedSectionID = $("#addFieldSection").val();
	 $.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getFormFeildsBySectionId?sectionId="+selectedSectionID,
				 success: function(json) {
		        	var output = "";
		        	output = output + "<option value='' selected>--Select Dependent Field--</option>";
		        	for (var i=0; i<json.length; i++)
					{
			        	var opt = json[i];
			        	if(opt != null && opt !='' && opt != 'undefined'){
			        		if(opt.labelName != null && opt.labelName !='' && opt.labelName != 'undefined'){
			        		output = output + "<option value='"+opt.id+"'>"+opt.labelName+"</option>";
			        		}
			        	}
					}
		        	$("#dependentFieldDiv").empty();
			        $("#dependentFieldDiv").append(output);
			        $('#dependentFieldDiv').trigger("chosen:updated");
				 }
		  })
 }
 </script>
 
 
  <script>
 function getCustomDependentFieldList(){
	 var selectedSectionID = $("#addFieldSection").val();
	 $.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getAllCustomFormFieldsBySectionId?sectionId="+selectedSectionID,
				 success: function(json) {
		        	var output = "";
		        	output = output + "<option value='' selected>--Select Custom Dependent Field--</option>";
		        	for (var i=0; i<json.length; i++)
					{
			        	var opt = json[i];
			        	if(opt != null && opt !='' && opt != 'undefined'){
			        		if(opt.labelName != null && opt.labelName !='' && opt.labelName != 'undefined'){
			        		output = output + "<option value='"+opt.id+"'>"+opt.labelName+"</option>";
			        		}
			        	}
					}
		        	$("#customDependentField").empty();
			        $("#customDependentField").append(output);
			        $('#customDependentField').trigger("chosen:updated");
				 }
		  })
 }
 </script>
 
 <script>
function getSelectedFieldDetails(){
	
	var optionCounter = $("#optionCounter").val();
	for(var i=optionCounter ; i >=0 ; i--){
		$("#optionDiv"+i).remove();
	}
	 var field = $('#fieldsDropDown').val();
	 if(field == 'selectfield' || field == 'checkBox' || field == 'radioButton'){
		 $('#selectFieldDiv').show();
		 $('#textBoxDiv').hide();
		 if(field == 'selectfield'){
			 $("#selectFieldDiv p:contains('Choices')").html("Options");
			 $("#addOptionDiv p:contains('Add Choice')").html("Add Option");
			
		 }else if(field == 'checkBox' || field == 'radioButton'){
			 $("#selectFieldDiv p:contains('Options')").html("Choices");
			 $("#addOptionDiv p:contains('Add Option')").html("Add Choice");
		 }
	 }else{
		 $('#selectFieldDiv').hide();
		 $('#textBoxDiv').show();
	 }
	 
	 if(field == 'datefield'){
		 $('#selectFieldDiv').hide();
		 $('#dateFieldDiv').show();
		 $( "#datepicker" ).datepicker();
	 }else{
		 $('#dateFieldDiv').hide(); 
	 }
	 
	 
	 
 }
 </script>
 
 <script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>
 
 <script>
 $('#isRequired').click(function() {
	        var value = $(this).val();	
	        if( $( this ).attr( 'type' ) === 'checkbox' ) {
	            value = +$(this).is( ':checked' );
	            $('#isRequired').attr("value",value);
	        }
});	
 </script>
 
 <script>
 function saveAddFormField(){
	 if ($('#saveAddForm_form').validate(false, validationSettings)){
	    var formData = getFormData1("saveAddForm_form");
	    var selectedSectionID = $("#addFieldSection").val();
		 $.ajax({
			 type : "POST",
			 data: formData,
		     processData: false,
		     contentType:false, 
			 url : sourceleadUrl+"/storeNewCustomFormField",
			 	 	success : function(data) {
			 	 			$("#cusFielTable"+selectedSectionID).append(data);
			 	 			$(".popMaskCustom").fadeOut(200);
			 				$(".cusFielPopup").fadeOut(100);
							flushPopupData();
			},
		});
	 }
 }
 $('body').on('blur', '#saveAddForm_form', function() {
		$("#saveAddForm_form").showHelpOnFocus();
		$("#saveAddForm_form").validateOnBlur(false, validationSettings);
 });
 </script>

<script>
function flushPopupData(){
	$("#labelName").val('');
	$("#isRequired").val(''); 
	$("#selectOptionId").val('');
	$("#placeHolder").val(''); 
	$("#statusId").val('');
	$("#fieldDescription").val('');
	$("#dependentFieldDiv").val('');
	$("#customDependentField").val('');
	$("#minDate").val('');
	$("#maxDate").val('');
	$("#labelinfo").val('');
	$('#saveAddForm_form').find('p.jquery_form_error_message').remove();
}
</script>


<script>
var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
 };
</script>


 <script>
 function deleteCustomField(customRowId){
	 $.ajax({
		 type : "POST",
		 url : sourceleadUrl+"/deleteCustomField?&customID="+customRowId,
		 	 	success : function(data) {
		 	 		$("#custom"+customRowId).hide();
		},
	});
 }
 </script>
 
 <script>
 function editFileDetails(customRowId){
	 var selectedSectionID = $("#addFieldSection").val();
	 var actionIdEdit =  $('#actionId').val(); 
	 $.ajax({
		 type : "POST",
		 url : sourceleadUrl+"/editNewCustomFormField?&id="+customRowId+"&actionId="+actionIdEdit,
		 	 	success : function(data) {
		 	 		$("#popUpDataCustom").empty();
			        $("#popUpDataCustom").append(data);
			        addFieldPopUp(selectedSectionID);
			        getSelectedFieldDetails();
			        
		},
	});
 }
 </script>
 
 <!--  script for show or less -->
 <script>
(function() {
  (function($) {
    var Fewlines;
    Fewlines = (function() {

      function Fewlines(el, options) {
        var appendText, div, hidLines, i, lineHeight, needCut, string, text, visLines, words, _i, _ref,
          _this = this;
        this.defaults = {
          'closeMark': 'Less',
          'openMark': '...',
          'newLine': false,
          'lines': 2
        };
        this.opts = $.extend({}, this.defaults, options);
        this.$el = $(el);
        string = '';
        div = $("<div>x</div>").css({
          'position': 'absolute',
          'left': 0,
          'top': "-3000px",
          "visibility": "hidden"
        }).width($(this.$el).width()).css($(this.$el).css(['font-size', 'font-weight', 'font-family'])).appendTo('body');
        text = $(el).text();
        lineHeight = div.height();
        words = text.split(/\b/);
        needCut = false;
        this.openMark = $("<a href='#'>" + this.opts.openMark + "</a>");
        if (this.opts.newLine) {
          this.openMark.css({
            'display': 'block', 'float':'right', 'padding-right':'15px',
          });
        }
        this.openMark.bind('click', function(event) {
          event.preventDefault();
          return _this.show();
        });
        this.closeMark = $("<a href='#'>" + this.opts.closeMark + "</a>").hide();
        this.closeMark.bind('click', function(event) {
          event.preventDefault();
          return _this.close();
        });
        for (i = _i = 0, _ref = words.length - 1; 0 <= _ref ? _i <= _ref : _i >= _ref; i = 0 <= _ref ? ++_i : --_i) {
          appendText = this.getLines(words, i);
          if (!this.opts.newLine) {
            appendText += this.opts.openMark;
          }
          div.text(appendText);
          if (div.height() > this.opts.lines * lineHeight) {
            needCut = true;
            break;
          }
        }
        visLines = this.getLines(words, i - 1);
        hidLines = text.slice(visLines.length);
        this.hidePart = $('<span>').hide().text(hidLines);
        if (needCut) {
          this.$el.text(visLines).append(this.openMark).append(this.hidePart).append(this.closeMark);
        }
        div.remove();
        this.$el;
      }
      Fewlines.prototype.getLines = function(words, i) {
        return words.slice(0, +i + 1 || 9e9).join('').replace(/\W+$/, '');
      };
      Fewlines.prototype.show = function() {
        this.openMark.hide();
        this.hidePart.show();
        return this.closeMark.css({"display":"block", "float":"right", "padding-right":"15px"});
      };

      Fewlines.prototype.close = function() {
        this.openMark.show();
        this.hidePart.hide();
        return this.closeMark.hide();
      };

      Fewlines.prototype.init = function() {
        var _this;
        return _this = this;
      };
      return Fewlines;
    })();
    return $.fn.fewlines = function(options) {
      if (this.length) {
        return this.each(function() {
          var rev;
          rev = new Fewlines(this, options);
          rev.init();
          return $(this).data("fewlines", rev);
        });
      }
    };
  })(jQuery);

}).call(this);
</script>
<script>
$('.showmore').fewlines();
</script>
