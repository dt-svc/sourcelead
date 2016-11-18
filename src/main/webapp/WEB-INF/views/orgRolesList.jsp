<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>

<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 

<!-- <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> -->

<script type="text/javascript">
window.onload = function () {
	getRolesList();
 };
</script>



<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Roles List</h2>
          </div>
        </section>

        
	   <div class="timesheet_out">
			<div class="project_wrap">
			
			<div class="project_tabs_inn_head">
              <a href="javascript:void(0)" class="addcon-pop"><i class="glyphicon glyphicon-plus"></i>Add Role</a>
              
				<!-- Popup Div -->
                    <div class="collapse-div-adduser addrole col-md-4"> <i class="fa fa-close close-div" onclick="flushRole();"></i>
                          <div class="form-horizontal clearfix">
                        <form:form id="addRole_form"  name="addRole_form" method="post">                             
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="roleDiv">
                                <input type="text" data-validation="required" field-name="Role Name" onblur="validateRoleName()" name="roleName" id="roleName" class="form-control input-sm"  placeholder="Role Name">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="levelDiv">
                                <select class="form-control chosen-select" data-validation="required" field-name="Level" id="level" name="level">
                                	 <option value="">-Select Level-</option>
			                         <c:forEach var="option" items="${hierarchyList}">
										<option value="${option.id}">${option.hierarchyName}</option>
									 </c:forEach>
                                </select>
                              </div>
                            </div>
                            <div class="clearfix mr">
                              <button type="submit" id="add_role" class="btn btn-sm btn-primary pull-right">Add</button>
                            </div>
                          </form:form>  
                            
                          </div>
                        </div>
				<!-- Popup Div -->              
              
              </div>
			
			    <div class="project_wrap_table roles_list">
			    				<table id="rolesList" class="display" cellspacing="0" width="100%">
								</table>
				</div>	   
					
			</div>
	   </div>		
 </section>
 
 
 
 <jsp:include page="masterFooter.jsp" />	
 
 
 
 <script type="text/javascript">
function validateRoleName(){
	var roleName = document.getElementById("roleName").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(roleName != null && roleName != '') {
		$.ajax({
			type : "GET",
			url: sourceleadUrl+"/validateRoleName?${_csrf.parameterName}=${_csrf.token}&roleName="+roleName, 
			success : function(data) {
				var result = data;
				if (result == "invalid") {
			        $("#add_role").attr("disabled", "disabled");
					$("input#roleName").after( "<p class='jquery_form_error_message'>" + roleName + " is already existed</span>");
					document.getElementById("roleName").setAttribute( 'record-exist', 'yes');
					document.getElementById("roleName").setAttribute( 'record-exist-errorMsg', roleName +"  already existed");
					$('input#roleName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else{
					$("#add_role").removeAttr("disabled");
					$('input#roleName').removeAttr("class record-exist record-exist-errormsg");
					$('input#roleName').attr('class','valid form-control');
					$('#roleDiv').find('p.jquery_form_error_message').remove();
					$("#add_role").click(function() {
						
					});
				}
			},

		});
	}
	}
</script>

<script type="text/javascript">
	function flushRole() {
		$("#add_role").removeAttr("disabled");
		$('#roleName').val('');
		$('#level').val('');
		$(".error").removeClass("error");
		$('#roleDiv').find('p.jquery_form_error_message').remove();
		$('#levelDiv').find('p.jquery_form_error_message').remove();
		
	}
</script>
 
 
 <script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#addRole_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
        		    var frm = $('#addRole_form').serializeFormJSON();
        	        var con = JSON.stringify(frm);
        	        con = con.replace(/[{}]/g, "");
        	        
        	        //It will escape all the special characters
        	        var jsonData = encodeURIComponent(con);
				    
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		
	          	     $.ajax({
	     		    	type: "POST",
	     		    	 url: sourceleadUrl+"/addNewRole?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
	     		        success: function(data) {
	     		        	if(data != null && data != ''){
	     	   	        		var json = JSON.parse(data);
	     	   	        	    location.replace(sourceleadUrl+ "/roleActions/"+json[0]+"");
	     		        	}
	     			        
	     		        },
	     		    });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#addRole_form').showHelpOnFocus();
         $('#addRole_form').validateOnBlur(false, validationSettings); 
            
     
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
 
 

<script>
function getRolesList(){
	
	var sourceleadUrl ='${sourceleadUrl}';
	
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getOrgEnrolledList?${_csrf.parameterName}=${_csrf.token}", 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>Role</th>';
   		    	result = result +'    <th>Level</th>'; 
   		    	result = result +'     <th>Number Of Actions</th>';
   		    	result = result +'     <th>Action</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{   
   		    		var report = json[i];
   		    		
   		    		result = result +'<tr>';
   		    		
   		    		result = result +'<td><div class="tbldata-dotdot-me" title="'+report.displayName+'">'+report.displayName+'</div></td>';
   		    		result = result +'<td><div class="tbldata-dotdot-sm" title="'+report.hierarchyName+'">'+report.hierarchyName+'</div></td>';
   		    		result = result +'<td><div class="tbldata-dotdot-vsm" title="'+report.actionsCount+'">'+report.actionsCount+'</div></td>';
   		    		result = result +'<td>';
   		    		result = result +'<div>';
   		    		result = result +'<label class="label label-success">';
   		    		result = result +'<a href="<c:url value="/roleActions/'+report.id+'"/>""><i class="fa fa-key"></i>&nbsp;&nbsp;Configure</a>';
   		    		result = result +'</label>';
   		    		result = result +'</div>';
   		    		result = result +'</td>';
   		    		
                    result = result +'</tr>';
   				}
                result = result +'  </tbody>';
                result = result +'</div>';
                $("#rolesList").empty();  
   	    	    $("#rolesList").append(result);
   	    	    pagination();	
   		    }
	 }); 
	
	
}	
</script> 



<script>
function pagination(){  
    $('#rolesList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
		
    } );
}
</script>


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
 		</script> 

 		
<script>
$(".addcon-pop").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().removeClass("show-div");
	 });
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
</script>