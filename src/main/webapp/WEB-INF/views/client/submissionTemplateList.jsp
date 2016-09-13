<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/bootstrap-select.min.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 	
<style type="text/css">
.collapse-div-adduser select{
width: 100% !important;
    padding-right: 10px !important;
    padding-left: 10px !important;
    border:1px solid #ccc !important
}
</style>
				

<!-- client details View starts-->
<script type="text/javascript">
	function submissionTemplateList(clientLocationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getSubmissionTemplatesList?${_csrf.parameterName}=${_csrf.token}&clientLocationId="+clientLocationId, 
	        success: function(data) {
	        	
	        var json = JSON.parse(data);
	        
    		var res = "";
			res = res + '<div class="timesheet_head">';
			res = res + '<ul>';
			res = res + '<li><a href="javascript:void(0)" onClick="addSubmissionTemplateFields('+clientLocationId+');" class="addcon-pop">';
			res = res + '<i class="glyphicon glyphicon-plus"></i>Add Template</a>';
			res = res + '<div class="clearfix"></div>';
			res = res + '</ul>';
			res = res + '<div id="popUpDivContact"> ';
			res = res + '</div>  ';
			res = res + '</div>'; 
			var result = "";
	      
    		 if(json.length != null && json.length != ''){
    	   		
				var result = "";
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result + '<th>Template Name</th>';
	            result = result + '<th>Description</th>';
	            result = result + '<th>Action</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';

	        	for (var i=0; i<json.length; i++)
				  {	        		
	        		var opt = json[i];
	        		
	        		
	        		result = result + '<tr>';
	                result = result + '<td><div>'+opt.templateName+'</a></div></td>';
	                result = result + '<td><div class="tbldata-dotdot">'+opt.description+'</div></td>';
	                result = result + '<td><div>';
	                /* result = result + '<a href="javascript:void(0)" onClick="addSubmissionTemplateFields('+clientLocationId+');">Customize</a>'; */
	                result = result + '<a href="javascript:void(0)">Customize</a>';
      	            result = result +'</div></td>';
	                result = result + '	 </tr>';
				} 
	        		
	        	
	        	result = result +'</tbody>';
	        	
    		}else{
      			 result = result + '<div class="table_nodata">';
       			 result = result + '<div class="table_nodata_inn">';
       			 result = result + '<img src="resources/images/no-data.png" />';
       			 result = result + '<span>Nothing is here to show </b>';
       			 result = result + '</span>';
       			 result = result + '<div class="clearfix"></div>';
       			 result = result + '</div>';
       			 result = result + '</div>';
       		 } 
    		 
       		$("#tab-2-addContact").empty();
       		$("#tab-2").empty(); 
    		$("#tab-3-addTemplate").empty();  
    	    $("#tab-3-addTemplate").append(res);
    		$("#tab-3").empty();  
    	    $("#tab-3").append(result);  
    	    $("#testTemplateDiv").show();
    	    
    	    $(".project-tabs-menu a").click(
					function(event) {
						event.preventDefault();
						$(this).parent().addClass("current");
						$(this).parent().siblings().removeClass(
								"current");
						var tab = $(this).attr("href");
						$(".project_tabs_inn").not(tab).css(
								"display", "none");
						$(tab).fadeIn();
					});
    	    contactPagination();
	        },
	    });
	}
</script>


 <!-- This Script is For Popup Div Starts--> 
<!-- <script>
function addSubmissionTemplate(clientLocationId) {	  
	var result = "";
	result = result  +'	<form id="clinetSubmissionTemplate_form" name="clinetSubmissionTemplate_form" method="post">';
	result = result  +'	<div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>';
	result = result  +'	<div class="form-horizontal clearfix">';
                              
	
		
	result = result  +'	<div class="clearfix mb10">';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	<input type="text" class="form-control input-sm" value="" id="templateName" name="templateName" field-name="Template name" placeholder="Template name *" data-validation="required validate_Space validate_length length2-100">';
	result = result  +'	</div>';
	result = result  +'	</div>';
	
	result = result  +'	<div class="clearfix mb10">';
	result = result  +'	  <div class="col-md-12">';
	result = result  +'	     <textarea rows="5" name="description" field-name="Description" data-validation="required" placeholder="Description *" class="form-control"></textarea>';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	result = result  +'	  <div class="clearfix mr">';
	result = result  +'	   <input type="button" id ="addSubmissionTemplate" value="Add" onclick="saveTemplate('+clientLocationId+');" class="btn btn-sm btn-primary pull-right"/>';
	result = result  +'	  </div>';
	
	
	
	result = result  +'	 </div>';
	result = result  +'	 </div>';
	result = result  +'	        </form> ';
	$("#popUpDivContact").empty();
	$("#popUpDivContact").append(result);
	
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
				})
	/* $(".addcon-pop").click(function(){
		 $(".collapse-div-adduser").toggleClass("show-div");
		 $(".collapse-div").removeClass("show-div");
	 }); */
	 
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){
		 $(this).closest('.edit-details').removeClass("edit-details-active");
		 });
	 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 
	};
</script> -->

 



 <!-- <script type="text/javascript">
	function saveTemplate(clientLocationId) {
		if ($('#clinetSubmissionTemplate_form').validate(false, validationSettings)) {
			var frm = $('#clinetSubmissionTemplate_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#addSubmissionTemplate").attr("disabled", "disabled");
			$("#addSubmissionTemplate").addClass('button').val('Processing..');
			
			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/saveClientSubmissionTemplate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&clientLocationId="+clientLocationId,
						success : function(result) {
							submissionTemplateList(clientLocationId);
						},
			});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#clinetSubmissionTemplate_form', function() {
		$("#clinetSubmissionTemplate_form").showHelpOnFocus();
		$("#clinetSubmissionTemplate_form").validateOnBlur(false, validationSettings);
	}); 
</script> -->

 
 <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
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

<jsp:include page="submissionTemplate.jsp" />	



