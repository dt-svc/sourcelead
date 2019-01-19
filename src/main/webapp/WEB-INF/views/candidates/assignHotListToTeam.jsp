<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
 window.onload = function() {
 	 hotListCandidates();
};
</script>


<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Hot List</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; <a href="<c:url value='/associatesList'/>">Associates List </a>&raquo; Hot List</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head minHeight">
            <ul>
            <div id="teamDiv" style="display: none; width:175px;" class="mt5">
            	<select class="form-control chosen-select" id="team"  onchange="assignHotListToTeam();">
               </select>
           	</div>
           	</ul>
          <div class="clearfix"></div>
      </div>
      <div class="candidate_wrap_table">
        <div id="candidateList" class="datatable_filter"></div>
      </div>
    </div>
   
  </div>
</section>



<input type="hidden" id="noCandidates" name="noCandidates"/>
<script>
function hotListCandidates(){ 	 
	var sourceleadUrl ='${sourceleadUrl}';
	  /* var candTypeRadio = $('input[name=candidateType]');
	  var candidateType = candTypeRadio.filter(':checked').val(); */
	  $("#candidateList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
           type : "POST",
            url: sourceleadUrl+"/getHotListCandidates?"+tokenParamName+"="+tokenValue+"",       
            success :function(response) {
            	if (response.response === "AWKWARD") {
            	    location.href = "/errorPage";
            	} else if (response.response === "SUCCESS") {
            		$("#candidateList").removeAttr("style");
            		$("#candidateList").empty();
            		var result = "";
            		result = result +"<table id='candidatePagination' class='display' cellspacing='0' width='100%' >"; 
            		result = result +"<thead>"; 
            		result = result +"  <tr>"; 
            		result = result + ' <th><input type="checkbox" onclick="selectAllCandidates();"  id="selectallHotList" ></th>';   
            		result = result +"    <th>Name</th>"; 
            		result = result +"    <th>Designation</th>"; 
            		result = result +"    <th>Employment Type</th>"; 
            		result = result +"    <th>Email</th>"; 
            		result = result +"    <th>Mobile</th>"; 
            		result = result +"    <th>Type</th>"; 
            		result = result +"    <th>Location</th>"; 
            		result = result +"  </tr>"; 
            		result = result +"</thead>";
            		result = result +"<tbody>"; 
            		var json = response.data;
            		for (var i = 0; i < json.length; i++) { 
            		 var opt = json[i]; 
            		 var mobilePhone = "";
            		 var designation ="";
            		 var address="";
            		 
            		 if(opt.mobilePhone != null && opt.mobilePhone != 'undefined' && opt.mobilePhone != ""){
            			 mobilePhone = opt.mobilePhone;
            			}else{
            				mobilePhone = "----";
            			}
            			if(opt.designation != null && opt.designation != 'undefined' && opt.designation != ""){
            				designation = opt.designation;
            			}else{
            				designation = "----";
            			}
            			if(opt.address != null && opt.address != 'undefined' && opt.address != ""){
            				address = opt.address;
            			}else{
            				address = "----";
            			}
            			
            			
            		 result = result +"<tr id='hotList"+opt.id+"'>";
            		 result = result + '<td><input type="checkbox" onclick="selectedHotList();"  class="hotlistedChecks" value='+opt.id+' name="hotlist[]" ></td>';
            		 if(opt.lastName !='' && opt.lastName != 'undefined' && opt.lastName != null){
            			 result = result +"  <td><div class='proj_show'><span title='"+opt.firstName+" "+opt.lastName+"'>"+opt.firstName+" "+opt.lastName+"</span></div></td>";
            		 }else{
            			 result = result +"  <td><div class='proj_show'><span title='"+opt.firstName+"'>"+opt.firstName+"</span></div></td>";
            		 }
            		 

            		 result = result +"  <td><div><span>"+designation+"</span></div></td>";
            		 if(opt.designationType != null && opt.designationType != '' && opt.designationType != "undefined")
            			 {
            		      result = result +"  <td><div>"+opt.designationType+"</div></td>";
            			 }else{
            				 result = result +"  <td><div>----</div></td>"; 
            			 }
            		 result = result +"  <td><div ><span title='"+opt.email+"'>"+opt.email+"</span></div></td>";
            		 result = result +"  <td><div>"+mobilePhone+"</div></td>";
            		 result = result +"  <td><div>"+opt.candidateType+"</div></td>";
            		 
            		 
            		 if(opt.state != null && opt.state !='' && opt.state != 'undefined' && opt.city != null && opt.city !='' && opt.city != 'undefined'){
            		     result = result +"  <td><div><span title='"+opt.country+", "+opt.state+", "+opt.city+"'>"+opt.country+", "+opt.state+", "+opt.city+"</span></div></td>";
            		 }else if(opt.state != null && opt.state !='' && opt.state != 'undefined'){
            		     result = result +"  <td><div><span title='"+opt.country+", "+opt.state+"'>"+opt.country+", "+opt.state+"</span></div></td>";
            		 }
            		    else{
            		    result = result +"  <td><div><span title='"+opt.country+"'>"+opt.country+"</span></div></td>";
            		    }

            		 result = result +"</tr>"; 
            		}
            		result = result +" </tbody>";
            		result = result +"</table>";

            		$("#noCandidates").val('');
            		$("#candidateList").append(result);
            		cpagination();
            	} else if (response.indexOf('Sign In') !== -1) {
            		location.replace(sourceleadUrl);
            	} else {
            	    showError("Operation failed, please try again");
            	}			    	      
          },
       }); 
 } 
</script>

<script>
function cpagination(){ 
    $('#candidatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"aaSorting": [[1,'desc']],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>


<script>
function selectAllCandidates(){
	
	var checked = document.getElementById("selectallHotList").checked  
	
        if(checked) {
            $('.hotlistedChecks').each(function() {
                this.checked = true;   
            });
        }else{
            $('.hotlistedChecks').each(function() {
                this.checked = false;      
            });         
        } 
	
	selectedHotList();
}
</script> 


<script>
function selectedHotList(){
	var hotlist = [];
	 $("input[name='hotlist[]']:checked").each( function (i) {
		 hotlist[i] = $(this).val();
	});
	 if(hotlist.length > 0) {
			 var sourceleadUrl ='${sourceleadUrl}';
				 $.ajax({
				    type: "POST",
				    url: sourceleadUrl+"/getTeams?"+tokenParamName+"="+tokenValue+"", 
				    success: function(response) {
				    	if (response.response === "AWKWARD") {
				    	    location.href = "/errorPage";
				    	} else if (response.response === "SUCCESS") {
				    		var json = response.data;
				        	var output = "";
				        	output = output + "<option value=''>-- Assign To Team --</option>"; 
				        	for (var i=0; i<json.length; i++) {
					        		var opt = json[i];
					        		output = output + "<option value='" +opt.id+ "'>"+opt.name+"</option>";
							}
				        	$("#team").empty();
					        $("#team").append(output);
					        $('#team').trigger("chosen:updated");
				    	} else if (response.indexOf('Sign In') !== -1) {
				    		location.replace(sourceleadUrl);
				    	} else {
				    	    showError("Operation failed, please try again");
				    	}
				    },
				});
		 
		 document.getElementById("teamDiv").style.display="";
	 } else {
		 document.getElementById("teamDiv").style.display="none";
		 
		 var output = "";
     	 output = output + "<option value=''>-- Assign To Team --</option>"; 
     	 $("#team").empty();
	     $("#team").append(output);
	     $('#team').trigger("chosen:updated");
	 }
}
</script>


<script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	       
	       $("#team").chosen();
	       
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});
</script>


  <script>
function assignHotListToTeam(){
	var teamId = document.getElementById("team").value;
	
	var hotlist = [];
	
	var oTable = $('#candidatePagination').dataTable();
	var rowcollection =  oTable.$("input[name='hotlist[]']:checked", {"page": "all"});
	rowcollection.each(function(index,elem){
		hotlist[index] = $(elem).val();
		$("#hotList"+hotlist[index]).html("<td colspan='8'  id='loading"+hotlist[index]+"'></td>");
		$("#loading"+hotlist[index]).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	});
	 
	var sourceleadUrl = '${sourceleadUrl}';
	if(teamId && hotlist.length > 0){
	 	 $.ajax({
			type : "POST",
			url : sourceleadUrl+"/assignHotListCandiddatesToTeam?"+tokenParamName+"="+tokenValue+"&teamId="+teamId+"&hotlist="+hotlist,
			success : function(result) {
				if (response && response.response === "INVALID_DATA") {
				    showError(response.responseMessage);
				} else if (response.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					var table = $('#candidatePagination').DataTable();
					 rowcollection.each(function(index,elem){
						 hotlist[index] = $(elem).val();
						 table.row('#hotList'+hotlist[index]).remove().draw( false );
					 });	
				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    showError("Operation failed, please try again");
				} 
			},
		}); 
	}
}
</script>  

<jsp:include page="../masterFooter.jsp" />

<script>
$(document).ready(function() {
    
    $("#filter3ApprovePop").click(function(){
		$("#filter3ApprovedPop").slideToggle(100);
	});
    $("html").click(function() {
        $("#filter3ApprovedPop").slideUp(100);
    });
    $("#filter3ApprovedPop, #filter3ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
}); 

</script>