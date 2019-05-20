<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 

 <c:forEach var="section" items="${SourceleadForm_Sections}" varStatus="sectionStatus">
     <div class="project_wrap mb">
     
     
     
        <div class="cusFielInnHead reportHead clearfix">
	  				<h2>${section.sectionName}</h2>
	  				<c:if test="${sectionStatus.index == 0}">
		          	    <a href="javascript:void(0)" id="addField" class="newAdBtn mr5" onclick="editFileDetails('','addNewCustomField');" >+ Field</a> 
		          	</c:if>
		</div>
		
	  	<div class="project_wrap_table" >
			<table>
			<tbody id="cusFielTable${section.id}">
			  <tr>
				<th>Action</th>
				<th class="tblminwidth-150">Field Name</th>
				<th class="tblminwidth-75">Field Type</th>
				<th class="tblminwidth-100">Required ?</th>
				<th class="tblminwidth-150">PlaceHolder</th>
				<th class="tblminwidth-75">Status</th>
				<!-- <th>Description</th>
				<th class="tblminwidth-150">Tool Tip</th> -->
				
				
			  </tr>
				<c:forEach var="fieldValue" items="${section.formFieldsList}">
					  <tr>
						<td class="cusFielAction">
						   <div>
							<i class="fa fa-eye" onclick="viewSectionFileDetails(${section.id},${fieldValue.id},'SourceleadSummary');"></i>
				            </div>
				        </td>
						<td><div class="tblminwidth-150">${fieldValue.labelName}</div></td>
						<td><div class="tblminwidth-75">${fieldValue.fieldType}</div></td>
						<td><div class="tblminwidth-30">${fieldValue.isFieldRequired == '0'? 'No':'Yes'}</div></td>
						<td><div class="tblminwidth-150">${fieldValue.placeHolder}</div></td>
						<td><div class="tblminwidth-75">${fieldValue.fieldstatus == '0'? 'InActive':'Active'}</div></td>
						
						
						<%-- <c:if test="${!empty fieldValue.fieldDesc}">
							<td><div class="showmore tblminwidth-150">${fieldValue.fieldDesc}</div></td>
						</c:if>
						<c:if test="${empty fieldValue.fieldDesc}">
							<td><div class="showmore tblminwidth-150 text-center">--</div></td>
						</c:if>
						<td><div class="showmore tblminwidth-150">${fieldValue.labelInfo}</div></td> --%>
						
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

<div class="popMaskCustom popMask-zindex"></div>
<div id="popUpDataCustom">
  <jsp:include page="addCustomPopUp.jsp"></jsp:include>
</div> 
 
<script>
function viewSectionFileDetails(sectionId,sourceleadFormId,type){
	$.ajax({
		 type : "POST",
		 url : sourceleadUrl+"/getSourceleadSectionDetailsById?&SectionId="+sectionId+"&sourceleadFormId="+sourceleadFormId+"&type="+type,
		 	 	success : function(data) {
		 	 		$("#popUpDataCustom").empty();
			        $("#popUpDataCustom").append(data);
			        addFieldPopUp(sectionId);			        
		},
	});
}
</script> 

 <script>
  function addOption(){
	  var optionCounter = $("#optionCounter").val();
	  if(optionCounter == 0){
		  optionCounter = +optionCounter + 1;
	  }
	  var divText = '<div id="optionDiv'+optionCounter+'" class="clearfix mb5">'+
	                   '<div class="col-md-11 noPad"><input id = "selectOptionId" placeholder="" name="optionData['+optionCounter+'].option" class="field-label form-control input-sm" type="text"> </div>'+
	                   '<div class="col-md-1 lblHeight"><span class="OpRemove"><i onclick = "removeOption('+optionCounter+')" class="fa fa-close"></i></span>'+
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
	 var slDependedntFieldId = $('#slDependedntFieldId').val();
	 var selectedSectionID = $("#addFieldSection").val();
	if(selectedSectionID != null){
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
			        			if(slDependedntFieldId == opt.id){
			        				output = output + "<option selected value='"+opt.id+"'>"+opt.labelName+"</option>";
			        				$("#dependentFieldDiv").attr("disabled","disabled");
			        			}else{
			        				output = output + "<option value='"+opt.id+"'>"+opt.labelName+"</option>";
			        			}
			        		
			        		}
			        	}
					}
		        	$("#dependentFieldDiv").empty();
			        $("#dependentFieldDiv").append(output);
			        $('#dependentFieldDiv').trigger("chosen:updated");
				 }
		  })
	}
 }
 </script>
 
 
  <script>
 function getCustomDependentFieldList(){
	 var customDependentField = $('#customDependndentFieldId').val();
	 var selectedSectionID = $("#addFieldSection").val();
	if(selectedSectionID != null){
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
			        			if(opt.id == customDependentField){
			        				output = output + "<option selected disabled value='"+opt.id+"'>"+opt.labelName+"</option>";
			        				$("#customDependentField").attr("disabled","disabled");
			        			}else{
			        				output = output + "<option value='"+opt.id+"'>"+opt.labelName+"</option>";
			        			}
			        		
			        		}
			        	}
					}
		        	$("#customDependentField").empty();
			        $("#customDependentField").append(output);
			        $('#customDependentField').trigger("chosen:updated");
				 }
		  })
 }
 }
 </script>
 
 <script>
function getSelectedFieldDetails(){
	
	var optionCounter = $("#optionCounter").val();
	for(var i=optionCounter ; i >=0 ; i--){
		$("#optionDiv"+i).remove();
	}
	 var field = $('#fieldsDropDown').val();
	 if(field == 'TextBox'){
		 $('#validateRangeDiv').show();
		 $('#validateLengthDiv').show();
	 }else{
		 $('#validateRangeDiv').hide();
		 $('#validateLengthDiv').hide();
		 
		 $('#validationRange').removeAttr("data-validation","required");
		 $('#validationRange').attr('data-validation-optional');
		 
		 $('#validationType').removeAttr("data-validation","required");
		 $('#validationType').attr('data-validation-optional');
		 
	 }
	 if(field == 'selectBox' || field == 'CheckBox' || field == 'radioButton'){
		 $('#selectFieldDiv').show();
		 $('#textBoxDiv').hide();
		 if(field == 'selectBox'){
			 $("#selectFieldDiv p:contains('Choices')").html("Options").append("<span class='text-danger paddingLeft'>*</span>");
			 $("#addOptionDiv p:contains('Add Choice')").html("Add Option");
			 $('#placeHolderDiv').show();
		 }else if(field == 'CheckBox' || field == 'radioButton'){
			 $("#selectFieldDiv p:contains('Options')").html("Choices").append("<span class='text-danger paddingLeft'>*</span>");
			 $("#addOptionDiv p:contains('Add Option')").html("Add Choice");
			 $('#placeHolderDiv').hide();
		 }
		 $('#selectOptionId').removeAttr('data-validation-optional');
		 $('#selectOptionId').attr("data-validation","required");
		 
	 }else{
		 $('#selectFieldDiv').hide();
		 $('#textBoxDiv').show();
		 $('#selectFieldDiv').find('p.jquery_form_error_message').remove(); 
		 $('#selectOptionId').removeAttr('data-validation');
		 $('#selectOptionId').attr("data-validation-optional","true");
	 }
	 
	 if(field == 'Date'){
		 $('#selectFieldDiv').hide();
		 $('#dateFieldDiv').show();
		 $('#placeHolderDiv').show();
	 }else{
		 $('#dateFieldDiv').hide();
		 $('#dateFieldDiv').find('p.jquery_form_error_message').remove(); 
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
 function saveAddFormField(customRowId){
	 var disabled = $('#disableFieldsId').val();
	 if(disabled != null && disabled != '' && disabled !='undefined' && disabled == 1){
		 $("#addFieldSectionHidden").val($("#addFieldSection").val());
		 $("#fieldsDropDownHidden").val($("#fieldsDropDown").val());
	 }
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
			 	 		   if(customRowId != null && customRowId != '' && customRowId !='undefined'){
			 	 			 var obj = $("#custom"+customRowId);
			 	 			 obj.empty();
			 	 			 obj.after(data);
			 	 			 obj.remove();
			 	 		   }else{
			 	 			 $("#cusFielTable"+selectedSectionID).append(data);
			 	 		   }
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
	$("#customFieldId").val('');
	$("#saveNewField").removeAttr('onclick');
	$("#saveNewField").attr('onclick','saveAddFormField("")');
	$("#labelName").val('');
	$("#isRequired").val('');
	$('#isRequired').removeAttr('checked');
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
	$('#fieldsDropDown').val('textfield');
	$('#fieldsDropDown').trigger('chosen:updated');
	getSelectedFieldDetails();
	
	
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
 function activateOrDeactivateCustomField(customRowId,status){
	 $.ajax({ 
		 type : "POST",
		 url : sourceleadUrl+"/deleteCustomField?&customID="+customRowId+"&status="+status,
		 	 	success : function(data) {
		 	 		$("#deletFildRow"+customRowId).removeAttr("class");
		 	 		if(status == 'activate'){

		 	 			$("#deletFildRow"+customRowId).prop('title', 'De-Activate');
		 	 			$("#deletFildRow"+customRowId).attr("class","fa  fa-trash");

		 	 			$("#deletFildRow"+customRowId).removeAttr("onclick");
		 	 			$("#deletFildRow"+customRowId).attr("onclick","activateOrDeactivateCustomField('"+customRowId+"', 'deactivate')");
		 	 			$("#customFieldStatus"+customRowId).empty();
		 	 			$("#customFieldStatus"+customRowId).html('Active');
		 	 		}if(status == 'deactivate'){
		 	 			$("#deletFildRow"+customRowId).prop('title', 'Activate');
		 	 			$("#deletFildRow"+customRowId).attr("class","fa  fa-check");
		 	 			$("#deletFildRow"+customRowId).removeAttr("onclick");
		 	 			$("#deletFildRow"+customRowId).attr("onclick","activateOrDeactivateCustomField('"+customRowId+"', 'activate')");
		 	 			$("#customFieldStatus"+customRowId).empty();
		 	 			$("#customFieldStatus"+customRowId).html('InActive');
		 	 		}
		},
	});
 }
 </script>
 
 <script>
 function editFileDetails(customRowId,type){
	 var selectedSectionID = $("#addFieldSection").val();
	 var actionIdEdit =  $('#actionId').val(); 
	 $.ajax({
		 type : "POST",
		 url : sourceleadUrl+"/editNewCustomFormField?&id="+customRowId+"&actionId="+actionIdEdit+"&type="+type,
		 	 	success : function(data) {
		 	 		$("#popUpDataCustom").empty();
			        $("#popUpDataCustom").append(data);
			        addFieldPopUp(selectedSectionID);
			        getSelectedFieldDetails();
			        getCustomDependentFieldList();
			        getDependentFieldList();
			        
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
