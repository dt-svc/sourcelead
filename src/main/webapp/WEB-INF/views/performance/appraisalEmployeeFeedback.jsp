<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript" src="${SourceLeadWeb_pmsUrl}/resources/js/performance/performanceAppraisal.js"></script>
<script type="text/javascript" src="${SourceLeadWeb_pmsUrl}/resources/js/performance/performanceFeedbackFormView-html-generator.js"></script>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />


<script>
function getAppraisalFeedbackDetails(){
	
    document.getElementById("feedbackWidget").className = 'activeApprisalwidget';
       if('${isGolasConfigured}' == 'Y'){
		  document.getElementById("goalsWidget").removeAttribute("class");
	  }
    document.getElementById("tasksWidget").removeAttribute("class");
    document.getElementById("finalScoreWidget").removeAttribute("class");
    $("#performanceAppraisalDiv").empty();  
	    
	  
	
	var result = "";  
	
	result = result + '<div class="goalssummar_widget clearfix backgroundGrey">';

	result = result + '<div class="row">';
      
	result = result + '<div class="col-xs-12 col-sm-12">';
	result = result + '<div class="feedbackTabs">';
	result = result + '<ul class="clearfix">';
	result = result + '<li class="active" onclick="getAppraisalFeedbackDetails(self)" id="selfTab">Self</li>';
	result = result + '<li id="peersTab">Peers</li>';
	result = result + '<li id="managerTab">Manager</li>';
	result = result + '</ul>';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="col-xs-12 col-sm-12 backgroundWhite">';
	result = result + '<div id="selfTab_div" class="minHeight_225">';
	
	result = result + '<div id="feedBackCategoryTemplateDiv">';
     
    result = result + '</div>';
    result = result + '</div>';  
     
	result = result + '</div>';
	result = result + '</div>';
	
	$("#performanceAppraisalDiv").append(result);
	
	getAppraisalFeedbackForm('self');
	
}
</script>


<script>
function getAppraisalFeedbackForm(appraisalType){
	
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
  		    	getAppraisalFeedbackTemplates(templateIds);
  		     },
    }); 
}


function getAppraisalFeedbackTemplates(templateIds){
	
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
	        	    result = result + '<div class="newRolLeft">';
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
        	    	    
	        	        result = result + '<li rel="'+opt.feedbackTemplateUUID+'"><a id="feedbackTemplate'+opt.feedbackTemplateUUID+'" '+selectedClass+' value="'+opt.feedbackTemplateUUID+'" onclick="getFeedbackCategoryForm(\''+opt.feedbackTemplateUUID+'\');">'+opt.templateName+'</a></li>';
        	    	}
        	
        	    result = result + '</ul>';
        	    result = result + '</div>';
                
                
        	    result = result + '<div class="newRolLRight">';
        	    result = result + '<div class="clearfix bor_top" id="tab-content">';
        	    result = result + '<div id="feedBackTemplateDiv">';
                result = result + '</div>';
                result = result + '</div>';
            	
        	    result = result + '</div>';
        	    }else{
        	    	 result = result + '<div class="odd"><div valign="top" colspan="4" class="dataTables_empty"></div></div>';
        	    }

        	    $("#feedBackCategoryTemplateDiv").append(result);
        	    
          	 if(feedbackTemplateUUID != ''){
        	    	getFeedbackCategoryForm(feedbackTemplateUUID);
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
function getFeedbackCategoryForm(feedbackTemplateUUID){
	
	
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
			
			$.ajax({
		  		  type: "GET",
		  		   url: pmsUrl+"/loadSelfPerformanceFeedbackTemplate?templateUUID="+feedbackTemplateUUID+"&assignedUUID="+assignedUUID, 
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
						    			  result = result +"<a href="+v['value']+"><img class='performanceThumbImg' onerror='predefineOrgImage(this);' data-id='orgLogoImage' src="+v['value']+" alt='image'></a>";
						    		  }else{
						    			  result = result +"-----";
						    		  }
						    	  }
						    	 
						    	  result = result + '</div>';
				  			  });
			  			              
			  				          
			  				result = result + '</div>';
			  			    result = result + '</span>';
			  			    result = result + '</div>';
			  			    
			  			    $("#feedBackTemplateDiv").empty(); 
					        $("#feedBackTemplateDiv").append(result);
					        
					        if(categoriesTotalScore != null){
						    	  $("#totalCategoryScore").val(categoriesTotalScore); 
						    }
		  		    	}else{
	  		    		    result = result + '<div class="odd"><div valign="top" colspan="4" class="dataTables_empty"></div></div>';
	  		    		    $("#feedBackTemplateDiv").empty(); 
					        $("#feedBackTemplateDiv").append(result);
	  		    	     } 
		  		    	
		  			
		  		     },
		  		   
			});
			 
	
  
    $("#appraisalLoading").removeAttr("style");
}
</script>