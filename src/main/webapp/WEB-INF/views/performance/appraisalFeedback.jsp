
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript" src="${SourceLeadWeb_pmsUrl}/resources/js/performance/performanceAppraisal.js"></script>
<script type="text/javascript" src="${SourceLeadWeb_pmsUrl}/resources/js/performance/performanceFeedbackFormView-html-generator.js"></script>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />

<script>

function getAppraisalFeedbackDetails(appraisalType){
	    document.getElementById("feedbackWidget").className = 'activeApprisalwidget';
	    if(appraisalType == 'self'){
	        if('${isGolasConfigured}' == 'Y'){
				  document.getElementById("goalsWidget").removeAttribute("class");
			  }
		    document.getElementById("tasksWidget").removeAttribute("class");
	    }
	    document.getElementById("finalScoreWidget").removeAttribute("class");
	    $("#performanceAppraisalDiv").empty();  
	    
	    $("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
	    $.ajax({
	  		  type: "GET",
	  		    url: pmsUrl+"/getJsonCategoriesString?assignedUUID="+assignedUUID,
	  		     success :function(jsonCategoriesString) {
	  		    	var templateIds = new Array();
	  		    	 if(jsonCategoriesString != null 
		       				 && jsonCategoriesString != '' 
		       					 && jsonCategoriesString != 'undefined'){
		       			var data = eval(jsonCategoriesString);
		           		 if (data) {
		           			  $.each( data, function( k, v ) {
		           				  if(v['type'] == "feedbackTemplate"){
		           					if(appraisalType == 'self'){
		           					 if(typeof v['self'] != 'undefined'){
			           						templateIds.push(v['templateId']);
		           					   }
		           					}else if(appraisalType == 'peer'){
			           					 if(typeof v['peer'] != 'undefined'){
				           						templateIds.push(v['templateId']);
			           					   }
			           					}
		           					 
		           				  }
		           				 
		           			  });
		           		 }
		       		 }
	  		    	 
	  		    	getAppraisalFeedbackForm(templateIds,appraisalType);
	  		     },
	    });
}


function getAppraisalFeedbackForm(templateIds,appraisalType){
	    
	    	
	    	$("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
	    	$.ajax({
	  		  type: "GET",
	  		    url: pmsUrl+"/getAssignedFeedbackTemplateList?assignedUUID="+assignedUUID+"&templateIds="+templateIds,
	  		     success :function(response) {
	  		    	 
	  		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	  	    		    showError(response.responseMessage);
	  	    		    $("#appraisalLoading").removeAttr('style');
	  	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		
		        		var flag = true;
		        		var feedbackTemplateUUID = '';
		        		var result = "";  
		        	    result = result + '<div class="goalssummar_widget goalssummar_widgetPad clearfix">';
		        	    
		        	    
		        	    if(response.data != null && response.data.length > 0){
		        	    	
		        	    	result = result + '<div class="clearfix">';
			        	    result = result + '<div class="col-md-2 col-md-offset-10">';
			        	    
			        	   
			        	    result = result + '<div class="row">';
			        	    result = result + '<label class="col-md-3 mt10">Score</label> <div class="col-md-9"><input type="text" id="totalCategoryScore" value="0" disabled class="form-control"></div>';
			        	    result = result + '</div>'; 
			        	    result = result + '</div>';
			        	    result = result + '<div class="clearfix"></div>';
			        	    result = result + '</div>';
			        	    
			        	    result = result + '<div class="newRolWrap appraiQue clearfix">';
			        	    result = result + '<div class="newRolLeft" >';
			        	    result = result + '<ul id="feedbackTemplate" class="clearfix">';
			        	    
			        	    for (var i=0; i<response.data.length; i++)
			        	    	{
			        	    	    
			        	    	    var opt = response.data[i];
			        	    	    var selectedClass = '';
			        	    	    if(flag == true){
			        	    	    	selectedClass = 'class="active"';
			        	    	    	flag = false;
			        	    	    	feedbackTemplateUUID = opt.feedbackTemplateUUID;
			        	    	    }
			        	    	    
				        	        result = result + '<li rel="'+opt.feedbackTemplateUUID+'"><a id="feedbackTemplate'+opt.feedbackTemplateUUID+'" '+selectedClass+' value="'+opt.feedbackTemplateUUID+'" onclick="getFeedbackCategoryForm(\''+opt.feedbackTemplateUUID+'\',\''+appraisalType+'\');">'+opt.templateName+'</a></li>';
			        	    	}
			        	
			        	    result = result + '</ul>';
			        	    result = result + '</div>';
			        	    
			        	    result = result + '<form id="addFeedbackTemplate_form" name="addFeedbackTemplate_form" enctype="multipart/form-data" method="post">';
			        	    result = result + '<div class="newRolLRight">';
			        	    result = result + '<div class="clearfix bor_top" id="tab-content">';
			        	    result = result + '<div id="feedBackCategoryTemplateDiv">';
			                result = result + '</div>';
			                result = result + '</div>';
			                
			                result = result + '<div id="feedBackCategoryTemplateDiv">';
			            	
			            	result = result + '</form>';
			        	    
			        	    result = result + '</div>';
		        	    }else{
		        	    	 result = result + '<div class="odd"><div valign="top" colspan="4" class="dataTables_empty"></div></div>';
		        	    }

		        	    $("#performanceAppraisalDiv").append(result);
		        	    
		        	    if(feedbackTemplateUUID != ''){
		        	    	getFeedbackCategoryForm(feedbackTemplateUUID,appraisalType);
		        	    }
		        	    
		        	    
		        	    $("#appraisalLoading").removeAttr("style");
		        		
		        	}else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
	  			},
			}); 
	    
		
}
</script>

<script>
function getFeedbackCategoryForm(feedbackTemplateUUID,appraisalType){
	
	var submissionStage = '${submissionStage}';
	
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatScript = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatScript = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatScript = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatScript = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatScript = 'yy-dd-mm'; 
	  }
	
	$("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$('#feedbackTemplate li').each(function(){
		document.getElementById("feedbackTemplate"+$(this).attr('rel')).removeAttribute("class")
	});
	document.getElementById("feedbackTemplate"+feedbackTemplateUUID).className = 'active'
	
		if(submissionStage != 'Submitted_For_Review'){
			var result = ""; 
			result = result + '<div id="tab1" class="appraQueOut">';
			result = result + '<span class="appraQueInn">';
			result = result + '<div class="project_wrap_table">';
		    
			result = result + '<h4 class="clearfix mb10" id="viewTemplateName"></h4>';
		    result = result + '<div class="clearfix mb10"><div class="subScoreWrap">';
		    result = result + '<label>Category Score :</label>';
		    result = result + '<select class="form-control pull-left" id="categoryScore" name="categoryScore"  field-name="Category Score" data-validation="required" >';
		    result = result + '<option value="">-Select Score-</option>';
		    result = result + '</select>';
		    result = result + '</div></div>';
		              
			result = result + '<div id="sjfb-fields" class="viewTemplate">';
		      
			result = result + '</div>';
			
			result = result + '<div class="form-footer clearfix mt">';
			result = result + '<div class="pull-right mr">';
			result = result + '<button type="button" id="save_button"  class="btn btn-sm btn-primary " onclick="saveFeedbackTemplateDetails(\''+feedbackTemplateUUID+'\',\''+appraisalType+'\');">Submit</button>';
			result = result + '</div>';
			result = result + '</div>';
			result = result + '</div>';  
		              
			result = result + '</div> ';
		    result = result + '</span>';
		    result = result + '</div> ';
		    
		    $("#feedBackCategoryTemplateDiv").empty(); 
		    $("#feedBackCategoryTemplateDiv").append(result);
		    generateForm(pmsUrl,feedbackTemplateUUID,assignedUUID,finalDateFormatScript);
		    
		}else{
			 
			
			$.ajax({
		  		  type: "GET",
		  		   url: pmsUrl+"/loadPerformanceFeedbackTemplate?templateUUID="+feedbackTemplateUUID+"&assignedUUID="+assignedUUID, 
		  		     success :function(report) {
		  		    	 
		  		    	
		  		    	var result = ""; 
		  		    	
		  		    	if(report.submittedJsonStringValues != null){
		  		    		var data = eval(report.submittedJsonStringValues);
			  		    	var categoryScore = report.categoryScore;
			  		    	if(categoryScore == null){
			  		    		categoryScore = 0;
			  		    	}
			  		    	var categoriesTotalScore = report.categoriesTotalScore
			  		    	result = result + '<div id="tab1" class="appraQueOut">';
			  				result = result + '<span class="appraQueInn">';
			  				result = result + '<div class="project_wrap_table">';
			  				result = result + '<h4 class="clearfix mb10" id="viewTemplateName"></h4>';
			  			    result = result + '<div class="clearfix mb10"><div class="subScoreWrap">';
			  			    result = result + '<label>Category Score :</label>';
			  			    result = result + '<input type="text" disabled value="'+categoryScore+'" class="form-control pull-left"></div>';
			  			    result = result + '</div></div>';
			  			    
				  			  $.each( data, function( k, v ) {
				  				  result = result + '<div class="form-group clearfix">';
						    	  result = result + '<label class="col-md-3 paddingRight">'+v['label']+'<span class="pull-right paddingLeft">:</span></label>';
						    	  if(v['type'] != 'file'){
						    		  result = result + '<div class="col-md-5">'+v['value']+'</div>';
						    	  }else{
						    		  if(v['value'] != null){
						    			  result = result +"<a href="+v['value']+"><img class='performanceThumbImg' onerror='predefineDocImage(this);' data-id='orgLogoImage' src="+v['value']+" alt='image'></a>";
						    		  }else{
						    			  result = result +"-----";
						    		  }
						    	  }
						    	 
						    	  result = result + '</div>';
				  			  });
			  			              
			  				          
			  				result = result + '</div>';
			  			    result = result + '</span>';
			  			    result = result + '</div>';
			  			    
			  			    $("#feedBackCategoryTemplateDiv").empty(); 
					        $("#feedBackCategoryTemplateDiv").append(result);
					        
					        if(categoriesTotalScore != null){
						    	  $("#totalCategoryScore").val(categoriesTotalScore); 
						    }
		  		    	}else{
		  		    		    result = result + '<div class="odd"><div valign="top" colspan="4" class="dataTables_empty"></div></div>';
		  		    		    $("#feedBackCategoryTemplateDiv").empty(); 
						        $("#feedBackCategoryTemplateDiv").append(result);
		  		    	}
		  		    	
		  			
		  		     },
		  		   
			});
			 
		}
	
  
    $("#appraisalLoading").removeAttr("style");
}
</script>

<script>
	function predefineDocImage(imageId){
		$(imageId).attr('src','/resources/images/applied_icon.png');
	}
</script>

<script>
function saveFeedbackTemplateDetails(feedbackTemplateUUID,appraisalType){
	if ($('#addFeedbackTemplate_form').validate(false, validationSettings)) {
		$("#appraisalLoading").attr('style','position:absolute; width:200%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
		    var frm = $('#addFeedbackTemplate_form').serializeFormJSON();
		    var con1 = JSON.stringify(frm);
		    con1= con1.replace(/[{}]/g, "");
		    var jsonData = encodeURIComponent(con1);
		    var form = $('#addFeedbackTemplate_form')[0];
		    var formData = new FormData(form); 
		    var fields = [];
		    var fileCount = 0;
		   
		    $('.sjfb-field').each(function() {
		    	  var $this = $(this); 
		    	  var divId = $(this).attr('id');  
		    	  var fieldLabel = $this.find('label:first').text().replace("*","").trim();
		    	  var inputValue = $this.find('input[type="radio"]:checked').val();
		    	  var inputtype = null;
		    	  var flag = 1;
		    	  
		    	  inputtype = $(this).find('input').attr('field-type');
    			  if(inputtype != null || inputtype != '' || inputtype != 'undefined'){
    				  inputtype = $(this).find('input').attr('field-type');
    			  }
    			  
    			  if(inputtype == null || inputtype == '' || inputtype == 'undefined'){
    				  inputtype = $(this).find('input').attr('type');
    		    	  if(inputtype == null || inputtype == '' || inputtype == 'undefined'){
    		    		  if($(this).find('select').val() != null){
    		    			  inputtype = 'select';
    		    		  }else{
    		    			  inputtype = 'textarea';
    		    		  }
    		    	  }
    			  }
		    	 
		    	  
		    	  
		    	 if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		  		   inputValue = $this.find('input[type="checkbox"]:checked').val();
		  		   if(inputValue != null){
		  			   var selected = [];
		             	$this.find('input[type="checkbox"]:checked').each(function() {
		    		        selected.push($(this).val()); 
		    		       });
		             	inputValue = selected.toString()
		  		   }
		  		   
		    	 }
		    	 
		    	
		    	  if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		    		  inputValue = $this.find('input').val();
		    	  }
		    	  
		  	  
		   	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		   	    	 inputValue = $this.find('select').val();
		   	      }
		   	      
		   	     if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		   	    	 inputValue = $this.find('textarea').val();
		   	      }
		   	      
		   	    
		   	     
		   	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		   	    	inputValue = '';
		   	      }
		   	      
		       	  var name = $this.find('input[type="radio"]:checked').attr('name');
		       	  var id = $this.find('input[type="radio"]:checked').attr('id');
		     	  if(name == null || name == '' || name == 'undefined'){
		     		  name = $this.find('input').attr('name');
		     		  id = $this.find('input').attr('id');
		     	  }
		     	  if(name == null || name == '' || name == 'undefined'){
		     		 name = $this.find('select').attr('name');
		     		 id = $this.find('select').attr('id');
	   	          }
		     	  
		     	  if(name == null || name == '' || name == 'undefined'){
		     		 name = $this.find('textarea').attr('name');
	     	      }
		     	  
		     	  if(inputtype == 'file'){
		     		 inputValue = $("input[name = 'uploadedFileInfo["+fileCount+"].fileURL']").val()
		     		 name = $("input[name = 'uploadedFileInfo["+fileCount+"].fileName']").val();
		     		 fileCount = fileCount+1;
		     	  }
		     	  
		     		  fields.push({
			              label: fieldLabel,
			              type : inputtype,
			              id :id,
			              name :name,
			              value: inputValue,
			          });  
		    	
		     });
		    var con = JSON.stringify(fields);
		    con = encodeURIComponent(con);
		    
		    
		    $.ajax({
		    	type: "POST",
		    	data: formData,
		        contentType : 'application/json; charset=utf-8',
		        enctype: 'multipart/form-data',
		        processData: false,
		        contentType:false,
		        dataType : 'json',
		    	url : pmsUrl+"/saveSubmittedFeedbackTemplate?"+tokenParamName+"="+tokenValue+"&assignedUUID="+assignedUUID+"&jsonDataValues="+con+"&feedbackTemplateUUID="+feedbackTemplateUUID,
		        success: function(result) {
		        	 	if(result.response === "SESSION_TIMEDOUT") {
							location.replace(sourceleadUrl);
						} else if (result.response === "AWKWARD") {
							location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
							getAppraisalFeedbackDetails(appraisalType);
							$("#appraisalLoading").removeAttr("style");
						} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
							showError("Operation failed, please try again");
						}
		        },
		    }); 
		    
		 return true; 
	}else {
		return false;
	}
}

$('body').on('blur', '#addFeedbackTemplate_form', function() {
	$("#addFeedbackTemplate_form").showHelpOnFocus();
	$("#addFeedbackTemplate_form").validateOnBlur(false, validationSettings);
});
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

function uploadFeedbackAttachments(id,attachmentCount){
	appraisalFeedbackFileUpload("newUploadBtn"+id, "feedbackTemplateAttachments", "addFeedbackTemplate_form","uploadedFileInfo",attachmentCount);
	$('#newUploadFile'+id).val($("#newUploadBtn"+id).val().replace(/C:\\fakepath\\/i, ''));
	
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
