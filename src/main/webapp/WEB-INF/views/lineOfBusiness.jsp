<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script type="text/javascript">
$(document).ready(function(){
	getLOBList();
});
</script>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>LOB List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a>&raquo;<a href="${appPath}/orgRolesList"> Roles List </a> &raquo;  Department List </div>
    </section>


<div class="timesheet_out">
      <div class="vendor_wrap">
        
<div class="reportHead reportHeadDataTab clearfix">
      <ul class="pull-right list-unstyled list-inline">
      
			<li>
				<a href="javascript:void(0)" class="action newAdBtn addcon-pop" title="" onclick="flushLOB();">+ LOB</a>
            </li>      
                   
        </ul>
      </div>        
        
        <div class="candidate_wrap_table">
          <div id="lobresultId" class="datatable_filter dataSeaTop"></div>
        </div>
        
        <div class="vendor_wrap_mask"></div>
		<div id="editlobId"></div>
         
      </div>
    </div>
		<div id="popUp"></div>
			<form:form id="lob_form" name="lob_form" method="post">
				<div class="popupWrap" id="lobCloseDiv">
				<div class="popupHead">Add LOB <i class="closePop pull-right popClose"></i></div>
				<div  id="lobDivLoading"></div>
                    <div class="popupContent">
                      <input type="hidden" name="lobId" id="lobId"/>
                      <div class="clearfix mb">
                        <div class="" id="lobNameDiv">
                        	<label class="noPad">LOB Name <b class="valStar">*</b></label>
                          <input type="text" class="form-control input-sm" value="" name="lobName" id="lobName" data-validation="required validate_length length2-100" field-name="LOB Name" onkeyup="validDepartmentname();" onkeydown="return (event.keyCode!=13);">
                        </div>
                      </div>
                      
                      
                <div class="clearfix mb">
                  <div class="" id="departmentDiv">
                  <label class="noPad">Department <b class="valStar">*</b></label>
                   <div class="input-group col-md-10 pull-left errorInptgroup">
                         <select class="form-control chosen-select" id="departmentId" name="departmentId" field-name="Department" onchange="removeValidation();" data-validation="required" >
			              </select>
			               <span class="input-group-addon cursorPointer bg-blue" onclick="getDepartmentsList();"><i class="fa fa-refresh "></i></span>
                  </div>
                </div> 
                 </div> 
                      
                      
                      <div class="clearfix mb">
                        <div class="">
                        <label class="noPad">Description</label>
                          <textarea rows="2" placeholder="Description" class="form-control" name="description" id="description" data-validation="validate_length length2-250"
									data-validation-optional="true"></textarea>
                        </div>
                      </div>
                    </div>
                    
					<div class="popupFoot text-right">
						<button type="button" class="slBtn md colorBtn" onclick="saveLOB();">Add</button>
						<input type="button" value="Cancel" class="slBtn md popClose">
					</div>                    
				</div>
            </form:form>		

  </section>
  <!-- Content Area Ends-->
 


<jsp:include page="masterFooter.jsp" />	


<script>
function saveLOB(){
	if ($('#lob_form').validate(false, validationSettings)) {
		var sourceleadUrl = '${sourceleadUrl}';
		var formData = getFormData1("lob_form");
		$("#lobDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
	 	$.ajax({
			type: "POST",
			data: formData,
		    processData: false, 
	 		contentType: false,
			url : sourceleadUrl+ "/storeLOB?${_csrf.parameterName}=${_csrf.token}",
			success : function(data) {
				if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
		        		showError(data.responseMessage);
  	    		    $("#hierarchyLoading").removeAttr('style');
  	    		}
            		else if (data.response === "AWKWARD") {
            			location.href = "/errorPage";
  	    		}
            		else if(data.response === "SUCCESS"){
            			 $("#lobCloseDiv").toggleClass('show-div');
        				 location.reload();
		        	    }
            		else if (data.indexOf('Sign In') !== -1) {
            			location.replace(sourceleadUrl);
  				} else {
  					showError("Operation failed, please try again");
  				    $("#hierarchyLoading").removeAttr('style');
  				}
				
				
			},
		}); 
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#lob_form', function() {
	$("#lob_form").showHelpOnFocus();
	$("#lob_form").validateOnBlur(false, validationSettings);
});
</script>


<script>
function flushLOB(){
	 $("#lobName").val('');
	 $("#lobDesc").val('');
	 $('#lobNameDiv').find('p.jquery_form_error_message').remove();
	 $('#departmentDiv').find('p.jquery_form_error_message').remove();
	 getDepartmentsList();
}
</script>

<script>
function removeValidation(){
	 $('#departments').find('p.jquery_form_error_message').remove();
}
</script>

 <script>
 function getDepartmentsList(departmentId){
	 $.ajax({
	    	type: "POST",
	    	url: atsUrl+"/getDepartmentsList?"+tokenParamName+"="+tokenValue+"",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-- Select Department --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		var name = '';
	        		name = opt.departmentName;
	        		
	        		if(departmentId != null && departmentId != 'undefined' && departmentId != ''){
	        			var selected = '';
	        			if(departmentId == opt.id){
	        				selected = 'selected';
	        			}
	        			output = output + "<option value='" +opt.id+ "' "+selected+">"+name+"</option>";
	        		}else{
	        			output = output + "<option value='" +opt.id+ "'>"+name+"</option>";
	        		}
	        		
				}
	        	$("#departmentId").empty();
		        $("#departmentId").append(output);
		        $('#departmentId').trigger("chosen:updated");
	        },
	    });
 }
 
 </script>  
 
 
 <script>
 function getLOBList(){
		
	 var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "GET",
			url : sourceleadUrl+ "/getLineOfBusinessList?${_csrf.parameterName}=${_csrf.token}",
			success : function(json) {
   	        	var result = "";
   	        	result = result +'<table id="lobPagination">';
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>LOB Name</th>';
   		    	result = result +'	<th>Department Name</th>';
   		    	result = result +'    <th>Description</th>'; 
   		    	result = result +'     <th>Action</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.data.length; i++)
   				{
   		    		var report = json.data[i];
   		    		if(report.description != null && report.description !='' && report.description != 'undefined'){
   		    			description = report.description
   		    		}else{
   		    			description = "---";
   		    		}
   		    		
   		    		result = result +'<tr>';
   		    		result = result +'<td>'
   		    		result = result +'<div class="tbldata-dotdot-big" title="'+report.lobName+'">'+report.lobName+'</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'<td>'
   		    		result = result +'<div class="tbldata-dotdot-big" title="'+report.departmentId.departmentName+'">'+report.departmentId.departmentName+'</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'<td>'
   		    		result = result +'<div class="tbldata-dotdot-big" title="'+description+'" >'+description+'</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'<td>'
   		    		result = result +'  <div class="tbldata-dotdot-100 text-center">';
   		    		
   		    		result = result +'<a class="glyphicon glyphicon-edit" title="Edit" onclick="editLOB('+report.id+');"></a>';
   		    		
   		    		result = result +'</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'</tr>';
   		    		
   				}
   		    	
   		    	$("#lobresultId").empty();  
   	    	    $("#lobresultId").append(result);
   	    	    lobPagination();
			},
		});
 }
 </script>
 
 <script>
 function editLOB(lobId){
	 
	 $("#lobName").val('');
	 $("#lobDesc").val('');
	 $('#lobNameDiv').find('p.jquery_form_error_message').remove();
	 $('#departmentDiv').find('p.jquery_form_error_message').remove();
	 $("#lobCloseDiv").fadeIn(100);
	 $(".mask").show();
	 
	 $.ajax({
	    	type: "GET",
	    	url : sourceleadUrl+ "/getLineOfBusinessById?${_csrf.parameterName}=${_csrf.token}&lobId="+lobId,
	        success: function(data) {
	        	 $("#lobName").val(data.lobName);
	        	 if(data.description != null && data.description != '' && data.description != 'undefined'){
	        		 $("textarea[name=description]").val(data.description);
	        	 }
	        	 getDepartmentsList(data.deptId);
	        	 $("#lobId").val(data.lobId);
	        	 
	        },
	    });
 }
 
 </script>  
 
 <script>
function lobPagination(){  
    $('#lobPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sSearchPlaceholder":"Search",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>
 
 

 <script>
	  $(".addcon-pop").click(function(){
			 $("#lobCloseDiv").fadeIn(100);
			 $(".mask").show();
		 });
		 $(".popClose").click(function(){
			 $("#lobCloseDiv").fadeOut(100);
			 $(".mask").hide();
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