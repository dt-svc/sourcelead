<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Hierarchy Level</h2>
      </div>
      <div class="pull-right brud-crum"><a href="orgRolesList">Roles List</a> &raquo; Hierarchy List </div>
    </section>
    
   <!-- Role Persmission -->
    <section class="clearfix">
     <div class="clearfix create-level">
     <div class="col-md-8 col-md-offset-2">
         <div class="row">
    
    <div class="col-md-offset-9 col-md-3">
		<a href="#popAddLevel" class="btn btn-sm btn-primary btn-block addLevel-pop" ><i class="fa fa-plus"></i>&nbsp;Add Level</a>
    </div>
    
    
    <!-- Modal -->
  <div class="modal fade" id="popAddLevel" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
      <div id="hierarchyLoading"></div> 
        <form:form id="addnew_hierarchy"  name="addnew_hierarchy" method="post"> 
        <div class="modal-header">
          <button type="button" onclick="flush();" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Add Level</h4>
        </div>
        <div class="modal-body">
           <div class="form-group" id="levelDiv">
            <input type="text" class="form-control input-sm" data-validation="required validate_Space validate_length length2-20" field-name="Level" onblur="validateLevelName();"onkeyup="validateLevelName();"  placeholder="Enter Level" name="level" id="level" autocomplete="off" autofocus>
            <input type="hidden" name="hierarchyId" id="hierarchyId">
           </div>
        </div>
        <div class="modal-footer">
          <button type="button" id="add_hierarchy"  class="btn btn-primary btn-sm" onclick="storeHierarchyLevel()" >Save</button>
          <button type="button" class="btn btn-default btn-sm" onclick="flush();" data-dismiss="modal">Close</button>
        </div>
        </form:form>
      </div>
      
    </div>
  </div>
</div>
<div id="popup"></div>
<div id="loading"></div>
	<div class="view-level">
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
     </div>
     </div>
    </section>
   <!-- Role Persmission Ends -->

  </section>
  <!-- Content Area Ends-->

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
$('#popAddLevel').modal({
backdrop: 'static'
});
}); 
</script> 
 
 <script>
 function flush(){
	    $("#add_hierarchy").removeAttr("disabled");
		$('#level').val('');
		$(".error").removeClass("error");
		$('#levelDiv').find('p.jquery_form_error_message').remove();
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
			
          		
    		    var frm = $('#addnew_hierarchy').serializeFormJSON();
    	        var con = JSON.stringify(frm);
    	        con = con.replace(/[{}]/g, "");
    	        
    	        //It will escape all the special characters
   	        var jsonData = encodeURIComponent(con);
       		var sourceleadUrl ='${sourceleadUrl}';
       		$("#add_hierarchy").attr("disabled", "disabled");
       		$("#hierarchyLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
       		

			$.ajax({
			 	
				type: "POST",
		    	 url: sourceleadUrl+"/addNewRoleHierarcy?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
		        success: function(data) {
		        	if(data != null && data != ''){
	   	        	    location.replace(sourceleadUrl+"/roleHierarchy");  
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
	var levelName = document.getElementById("level").value;
	var hierarchyId = document.getElementById("hierarchyId").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(levelName) {
		$("#add_hierarchy").attr("disabled", "disabled");
		$.ajax({
			type : "GET",
			url: sourceleadUrl+"/validateRoleHierarchyLevelName?${_csrf.parameterName}=${_csrf.token}&levelName="+levelName+"&hierarchyId="+hierarchyId, 
			success : function(data) {
				var result = data;
				if (result == "invalid") {
					$("#add_hierarchy").removeAttr("disabled");
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
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
 
 