<jsp:include page="../masterHeader.jsp" />
<%-- <jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
 
          
                  <input type="text" value="Assing Role" class="form-control" data-target="#assignTeam" data-toggle="modal" onclick="getUserList();">
                 
                  <div id="userList"></div>
                       
<%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>

<script>
function getUserList(){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#userList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getUserList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#userList").removeAttr("style");
	                	 $("#userList").empty();
	                	    alert("one");
	                	    var result = '';
	                	    result = result +'<div class="modal-body clearfix">';
	                		result = result +'<div class="form-horizontal custom-form">';
	                		result = result +'     <div class="row">';
	                		result = result +'       <div class="col-xs-12 col-md-8 col-lg-8">';
	                		result = result +'         <div class="input-group">';
	                		result = result +'           <input type="search" class="form-control" value="">';
	                		result = result +'           <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> </div>';
	                		result = result +'       </div>';
	                		result = result +'       <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100">';
	                		result = result +'          <select class="selectpicker">';
	                		result = result +'          <option selected disabled>Select Role</option>';
	                		result = result +'           <option value="All">All</option>';
	                		result = result +'           <option value="Admin">Admin</option>';
	                		result = result +'           <option value="Account Manager">Account Manager</option>';
	                		result = result +'           <option value="Recruiting Manager">Recruiting Manager</option>';
	                		result = result +'           <option value="Hiring Manager">Hiring Manager</option>';
	                		result = result +'           <option value="Teams">Teams</option>';
	                		result = result +'          </select>';
	                		result = result +'     </div>';
	                		result = result +'     </div>';
	                		result = result +'     </div>';
	                		result = result +'     <div class="modal-body-custom mt clearfix">';
	                		result = result +'       <table id="userPagination" class="display" cellspacing="0" width="100%" >';
	                		result = result +'           <thead>';
	                		result = result +'           <tr>';
	                		result = result +'             <th><input type="checkbox"></th>';
	                		result = result +'             <th>Name</th>';
	                		result = result +'             <th>Email</th>';
	                		result = result +'             <th>Role</th>';
	                		result = result +'             <th>Active Jobs</th>';
	                		result = result +'           </tr>';
	                		result = result +'           </thead>';
		                    result = result +"<tbody>"; 
		                    var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 result = result +'           <tr>';
		                    	 result = result +'             <td><input type="checkbox" name="team"></td>';
		                    	 result = result +'             <td>Murali Krishna</td>';
		                    	 result = result +'             <td>murali@businessintelli.com</td>';
		                    	 result = result +'             <td>Account Manager</td>';
		                    	 result = result +'             <td>101</td>';
		                    	 result = result +'           <tr>';
		                    	 result = result +'           <tr>';
		                    	 result = result +'             <td><input type="checkbox" name="team"></td>';
		                    	 result = result +'             <td>Murali Krishna</td>';
		                    	 result = result +'             <td>murali@businessintelli.com</td>';
		                    	 result = result +'             <td>Account Manager</td>';
		                    	 result = result +'             <td>101</td>';
		                    	 result = result +'           <tr>';
		                    	 result = result +'           <tr>';
		                    	 result = result +'             <td><input type="checkbox" name="team"></td>';
		                    	 result = result +'             <td>Murali Krishna</td>';
		                    	 result = result +'             <td>murali@businessintelli.com</td>';
		                    	 result = result +'             <td>Account Manager</td>';
		                    	 result = result +'             <td>101</td>';
		                    	 result = result +'           <tr>';
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     result = result +'     </div>';
	                 	 /* result = result +'     <div class="clearfix">';
	                 	 result = result +'       <ul class="pagination pull-right pagination-sm" style="margin:0;">';
	                 	 result = result +'         <li><a href="#">1</a></li>';
	                 	 result = result +'         <li class="active"><a href="#">2</a></li>';
	                 	 result = result +'         <li><a href="#">3</a></li>';
	                 	 result = result +'         <li><a href="#">4</a></li>';
	                 	 result = result +'         <li><a href="#">5</a></li>';
	                 	 result = result +'       </ul>';
	                 	 result = result +'       </div>'; */
	                 	 result = result +'   </div>';
	                 	 result = result +'   <div class="modal-footer">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button">Assign</button>';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>';
	                 	 result = result +'   </div>';
	                 	 alert("three"+result);
	                     $("#userList").append(result); 
	                     pagination(); 
			    	} 
                 }else{
                	 $("#userList").removeAttr("style");
  	    	         var result ="";
		    	    
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No UserList Found</p>";
					 result = result+"			 </div>";
					
					$("#userList").empty();
				    $("#userList").append(result); 
                }
          },
            
       }); 
  } 
</script>

<%-- 
     @author : sarath kumar.
     this script is for prepare pagination to requirementList.
     1.usage     : used in candidateList() function.
--%>

<script>
function pagination(){
    $('#userPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[1, 15, 20, -1], [1, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>


<jsp:include page="../masterFooter.jsp" />