<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Hierarchy Level</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a>&raquo;<a href="${appPath}/orgRolesList"> Roles List </a> &raquo; Hierarchy List </div>
    </section>
    
   <!-- Role Persmission -->
    <section class="project_wrap clearfix">
     
<div class="reportHead positionRelative clearfix">
      <ul class="pull-right list-unstyled list-inline">
			<li class="addcon-pop">
				<a href="#popAddLevel" class="newAdBtn mr5 addLevel-pop" >+ Level</a>
            </li>      
        </ul>
      </div>      
    
    
    <!-- Modal -->
  <div class="popupWrap" id="popAddLevel" role="dialog">
      <div id="hierarchyLoading"></div> 
        <form:form id="addnew_hierarchy"  name="addnew_hierarchy" method="post"> 
        
        <div class="popupHead">Add Level <i class="closePop pull-right popClose" onclick="flush();" data-dismiss="modal"></i></div>
        
        <div class="popupContent">
           <div class="form-group" id="levelDiv">
           	<label class="noPad" for="Role Name">Level Name <b class="valStar">*</b></label>
            <input type="text" class="form-control" data-validation="required validate_Space validate_length length2-20" field-name="Level"  onkeyup="validateLevelName();"  placeholder="Enter Level" name="level" id="level" autocomplete="off" autofocus onkeydown="return (event.keyCode!=13);">
            <input type="hidden" name="hierarchyId" id="hierarchyId">
           </div>
        </div>
        
        <div class="popupFoot text-right">
          <button type="button" id="add_hierarchy"  class="slBtn md colorBtn" onclick="storeHierarchyLevel()" >Add</button>
          <button type="button" class="slBtn md popClose" onclick="flush();" data-dismiss="modal">Cancel</button>
        </div>
        </form:form>
  </div>
  
  

<div id="popup"></div>
<div id="loading"></div>
	<div class="view-level mb">
	         <ul class="list-unstyled hierachy-role" id="sortable">
	         <c:forEach var="hierarchy" items="${rolesHierarchyList}">
	             <li id="hierarchyList${hierarchy.id}" value="${hierarchy.id}"><i class="fa fa-edit addLevel-pop"  onclick="editHierarchyLevel('${hierarchy.hierarchyName}','${hierarchy.id}');" title="Edit Role"></i><span class="sellevel">${hierarchy.hierarchyName}</span>
					 <ul class="inner">
					 	<c:forEach var="roles" items="${hierarchy.rolesList}">
					 	<li>${roles.displayName}</li>
					 	</c:forEach>
					 </ul>
				 </li>
			 </c:forEach>
			 </ul>
	</div>
    </section>
  </section>
 <jsp:include page="masterFooter.jsp" />	
 
 
 
 <script>
 function editHierarchyLevel(hierarchyName,hierarchyId){
	 
	 $("#level").val('');
	 $("#hierarchyId").val('');
	 
	 $("#level").val(hierarchyName);
	 $("#hierarchyId").val(hierarchyId);
 }
 </script>
 
 
 <script>
$('.addLevel-pop').click(function(){
	$('#popAddLevel').fadeIn(100);
	$(".mask").show();
}); 
</script> 
 
 <script>
 function flush(){
	    $("#add_hierarchy").removeAttr("disabled");
		$('#level').val('');
		$(".error").removeClass("error");
		$('#levelDiv').find('p.jquery_form_error_message').remove();
		$('#popAddLevel').fadeOut(100);
		$(".mask").hide();
 }
 </script>
 



<script type="text/javascript">
	function storeHierarchyLevel() {
		
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		
		 if ($('#addnew_hierarchy').validate(false, validationSettings)){ 
			
        	var sourceleadUrl ='${sourceleadUrl}';
       		$("#add_hierarchy").attr("disabled", "disabled");
       		$("#hierarchyLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
       		var formData = getFormData1("addnew_hierarchy");

			$.ajax({
			 	
				type: "POST",
				data: formData,
 		    	processData: false, 
		 		contentType: false,
		    	 url: sourceleadUrl+"/addNewRoleHierarcy?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(data) {
		        	
		        	if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
 		        		showError(data.responseMessage);
      	    		    $("#hierarchyLoading").removeAttr('style');
      	    		}
                		else if (data.response === "AWKWARD") {
                			location.href = "/errorPage";
      	    		}
                		else if(data.response === "SUCCESS"){
                			$("#hierarchyLoading").removeAttr('style');
                			location.replace(sourceleadUrl+"/roleHierarchy"); 
                			$('#popAddLevel').hide();
                			$(".mask").hide();                			
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
		 }else{ 
				return false;
		 } 
	} 
	
	$('body').on('blur', '#addnew_hierarchy', function() {
		$("#addnew_hierarchy").showHelpOnFocus();
		$("#addnew_hierarchy").validateOnBlur(false, validationSettings);
	});
</script>

<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
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
 
 <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 


 
 <script type="text/javascript">
function validateLevelName(){
	// 	var levelName = document.getElementById("level").value;
	var levelName = document.getElementById("level").value.trim();
	var hierarchyId = document.getElementById("hierarchyId").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(levelName.match(/^[^\s-](.*)*$/) !== null) {
		 $('#levelDiv').find('p.jquery_form_error_message').remove(); 
		$.ajax({
			type : "GET",
			url: sourceleadUrl+"/validateRoleHierarchyLevelName?${_csrf.parameterName}=${_csrf.token}&levelName="+levelName+"&hierarchyId="+hierarchyId, 
			success : function(data) {
				var result = data;
				if (result == "invalid") {
					$("#add_hierarchy").attr("disabled", "disabled");
				    $('#levelDiv').find('p.jquery_form_error_message').remove(); 
		    	    $("input#level").after("<p class='jquery_form_error_message'>"+levelName+" is Already Existed</span>");
    				$('input#level').attr('record-exist','yes');
    				$('input#level').attr('record-exist-errorMsg',levelName+' is Invalid');
					
				}else{
					$("#add_hierarchy").removeAttr("disabled");
					$('input#level').removeAttr("class record-exist record-exist-errormsg");
					$('input#level').attr('class','valid form-control');
					$('#levelDiv').find('p.jquery_form_error_message').remove();
					
					 $( "#add_hierarchy" ).click(function() {
						 if ($(this).validate(false, validationSettings)){
						 }
						});
				}
			},

		});
	}
	}
</script>
 
 <script>
		$("#sortable").sortable({    
			 update: function () { 
				 var order = $('#sortable').sortable('toArray');
				 var hierarchyOrder = new Array();
				 for(var i=0; i<order.length; i++){
					 hierarchyOrder.push($('#'+order[i]).val());
				 }
				 
				 alertMessage(hierarchyOrder);
		            
		        }
	    });
		
</script>
 
 
<script type="text/javascript">
function alertMessage(hierarchyOrder){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to change hierarchy levels?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='updateHierarchyOrder(\""+ hierarchyOrder+ "\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popup").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


<script type="text/javascript">
function updateHierarchyOrder(hierarchyOrder){
	$("#popup").empty();
	$(".project_inn_mask").fadeOut(200);
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 if(hierarchyOrder){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/changeHierarchyLevels?${_csrf.parameterName}=${_csrf.token}&hierarchyOrder="+hierarchyOrder, 
		        success: function(data) {
		        	$("#loading").removeAttr("style"); 
		        },
		    });
	 }

}
</script>


<script type="text/javascript">
function cancel(){
	$("#popup").empty();
	$(".project_inn_mask").fadeOut(200);
	var sourceleadUrl ='${sourceleadUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	location.replace(sourceleadUrl+"/roleHierarchy");  
}
</script>


<script>
// Accordion script
    // RoleLevel script
    function cloneRole(){
    var m = $("ul.hierachy-role > li");
    var clonerole = $("ul.hierachy-role > li:last-child");
    $(clonerole).clone().appendTo("ul.hierachy-role")
    };

//Append Role
var clonerole = $("ul.hierachy-role > li");
function appendRole(){
   var newrole = $('#addNewrole').val();
    $('ul.hierachy-role').append("<li>" + "<span class='sellevel'>" + newrole + "</span>" + "</li>")
};

//Auto focus
 $('#popAddLevel').on('shown.bs.modal', function () {
    $('#addNewrole').focus();
 });

//Sortable
    $( "#sortable").sortable();
    $( "#sortable").disableSelection();
    
    
    //Edit Pop
    function roleEditpop(){
        alert('hi');
    }
    //accordion two
    var hr = $('ul.hierachy-role').find('span.sellevel');
    $(hr).next('ul.inner').slideUp();
    $(hr).removeClass('role-active');
    $(hr).click(function(){
    $(hr).removeClass('role-active');
      $(hr).next('ul.inner').slideUp();
        if($(this).next('ul.inner').is(':hidden') == true) {
           $(this).next('ul.inner').slideDown('normal');
            $(this).addClass('role-active');
        };
        });
</script>
 
 