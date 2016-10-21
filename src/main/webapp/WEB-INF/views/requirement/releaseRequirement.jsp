

<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/sourcelead-main.css" rel="stylesheet">






<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 



<!-- Content Area Starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Release Jobs</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Release Jobs</div>
    </section>
    <div class="content-area clearfix">
      <div id="accordion-sl" class="release-jobs">
      <!-- Accordion First Tab -->
        <div class="clearfix">
          <h2>Profile</h2>
          <div class="content clearfix">
            <div class="vendor_wrap">
              <div class="col-md-2 custom-selectpicker mt10 mb10">
                <select class="form-control selectpicker"  id="sele-rel"  onchange="listChange(this);">
                  <option id="cont" value ="releaseByContact">Release By Contact</option>
                  <option id="ven"  value ="releaseByVendors" selected="selected">Release By  Vendors</option>
                </select>
              </div>
              <div class="timesheet_head">
                <ul>
                  <li class="li-vendor hidden">
                    <input type="text" class="vendorfild">
                    <i class="fa fa-search"></i>|</li>
                  <li><i class="fa fa-filter"></i><span>Level 1 (5, 10)</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
                    <div class="export_pop">
                      <ul>
                        <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                        <li>Excel<i class="fa fa-file-excel-o"></i></li>
                        <li>Word<i class="fa fa-file-word-o"></i></li>
                      </ul>
                    </div>
                  </li>
                  <li><i class="fa fa-filter"></i><span>Select Group</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
                    <div class="export_pop">
                      <ul>
                        <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                        <li>Excel<i class="fa fa-file-excel-o"></i></li>
                        <li>Word<i class="fa fa-file-word-o"></i></li>
                      </ul>
                    </div>
                  </li>
                  <div class="clearfix"></div>
                </ul>
                <div class="clearfix"></div>
              </div>
              <!-- Contact Table -->
              
              <div id="vendorContacts"> </div>
             
              <!-- Contact Table Ends --> 
              
              <!-- Vendor Table -->
              
              <div id="vendor"></div>
              
              <!-- Vendor Table Ends --> 
            </div>
          </div>
        </div>
	  <!-- Accordion First Tab Ends -->

      <!-- Accordion second Tab -->
        <div class="clearfix">
          <h2>Job boards, Social Networks &amp; Career Portal</h2>
          <div class="content clearfix">
            <div class="row">
              <div class="col-md-12">
                <div class="panel panel-success">
                  <div class="panel-heading ">Selected Job Boards</div>
                  <div class="panel-body">
                    <ul class="list-unstyled list-inline sl-jobboard">
                      <li class="bg-dice">
                        <label class="radio-inline">
                          <input type="radio" name="optradio">
                          <span><img src="images/dice.jpg" alt="dice" title="Dice"></span> </label>
                      </li>
                      <li>
                        <label class="radio-inline">
                          <input type="radio" name="optradio">
                          <span><img src="images/monster.jpg" alt="Monster" title="Monster"></span> </label>
                      </li>
                      <li>
                        <label class="radio-inline">
                          <input type="radio" name="optradio">
                          <span><img src="images/naukri.jpg" alt="Naukri" title="Naukri"></span> </label>
                      </li>
                    </ul>
                  </div>
                </div>
                <div class="panel panel-info">
                  <div class="panel-heading">Select Social Networks</div>
                  <div class="panel-body">
                    <ul class="list-unstyled list-inline connet-socialicons">
                      <li class="bg-facebook">
                        <label class="checkbox-inline">
                          <input type="checkbox" name="checkbox">
                          <span><i class="fa fa-facebook-square"></i>FaceBook</span> </label>
                      </li>
                      <li class="bg-linked">
                        <label class="checkbox-inline">
                          <input type="checkbox" name="checkbox">
                          <span><i class="fa fa-linkedin-square"></i>LinkedIn</span> </label>
                      </li>
                      <li class="bg-twitter">
                        <label class="checkbox-inline">
                          <input type="checkbox" name="checkbox">
                          <span><i class="fa fa-twitter-square"></i>Twitter</span> </label>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-md-6">
                <div class="row">
                  <div class="form-group clearfix toggle-button-lbl">
                    <label class="col-md-5">Career Portal</label>
                    <div class="col-md-7 toggle-button">
                      <label class="switch">
                      <input type="checkbox" checked>
                      <div class="slider round"></div>
                      </label>
                    </div>
                  </div>
                  <div class="form-group clearfix toggle-button-lbl">
                    <label class="col-md-5">Professional Network</label>
                    <div class="col-md-7 toggle-button">
                      <label class="switch">
                      <input type="checkbox">
                      <div class="slider round"></div>
                      </label>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-12">Notify to others</label>
                    <div class="col-md-12">
                      <textarea rows="2" class="form-control" placeholder="Upto 20 Email/Users/Grpoups">                                        
                                                            </textarea>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      <!-- Accordion second Tab Ends -->
      </div>
      <div class="form-footer clearfix row mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" class="btn btn-primary ml" onclick="releaseTo();" value="Save">
            <input type="button" class="btn btn-default ml" value="Cancel">
          </div>
        </div>
      </div>
    </div>
  </section>
<!-- Content Area Starts -->

<script> 
$(document).ready(function(){
	getVendorLists();
	//getVendorContactLists();
});
</script>
<script>
function releaseTo(){
		
	    var contactsIds = new Array();
	    $('input:checkbox[name=contactscheckbox]:checked').each(function() 
	    {
	    	contactsIds.push($(this).attr('id'));
	    });
	    
	    var vendorIds = new Array();
	    $('input:checkbox[name=vendorcheckbox]:checked').each(function() 
	    {
	    	vendorIds.push($(this).attr('id'));
	    });
	    
	    var sourceleadUrl ='${sourceleadUrl}';
	    var requirementId = '${param.requirementId}';
		$.ajax({
	           type : "POST",
	           url: sourceleadUrl+"/releaseJobsToClientsOrVendors?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&contactsIds="+contactsIds+"&vendorIds="+vendorIds,    
	            success :function(data) {
	            		alert("success");
						location.replace(sourceleadUrl+"/requirementList");
	            },
	    }); 
	 
	    
}
</script>


<%-- 
     @author : David Raju.
     this script is for get Contacts Lists.
     1.usage     : listChange at line number 409.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>


<script>
function getContactsLists(){
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	var requirementId = '${param.requirementId}';
	
	  $.ajax({
		   	type: "GET",
		    url: sourceleadUrl+"/getAllContactsbyOrgLocationIdAndUserIdForRequirement?requirementId="+requirementId, 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   	        	
                result = result +'<div class="require_wrap_table release-jobs-tbl">';
                result = result +'<table >';
                result = result +'<tbody>';
   	        	result = result +'<thead>';
   	        	result = result +'<tr>';
   	            result = result +'<th><input type="checkbox" id="contactscheckbox"/></th>'; 
   	        	result = result +'  <th>Name</th>';
   	        	result = result +'  <th>Email</th>';
   	        	result = result +' <th>Ph</th>';
   	        	result = result +' <th>Vendor</th>';
   	        	result = result +' <th>Status</th>';
   	        	result = result +' <th>Release</th>';
   	        	result = result +'</tr>';
   	        	result = result +'</thead>';
   	        	
   	        	for (var i=0; i<json.length; i++)
   				{  
   	        		var report = json[i];
   	        		
   	        		result = result +'<tr>';
   	        		if(report.releasedRequirement =="true"){
   	        			result = result +'  <td><input type="checkbox" checked disabled readonly id="'+report.id+'"/></td>';
   	        		}
   	        		if(report.releasedRequirement =="false"){
   	        			result = result +'  <td><input name="contactscheckbox" type="checkbox" id="'+report.id+'"/></td>';
   	        		}
	        			result = result +'<td>';
	        			result = result +'<div><span>'+report.spocName+'</span></div>';
	        			result = result +'</td>';
	        			result = result +'<td>';
	        			result = result +'<div><span>'+report.spocEmail+'</span></div>';
	        			result = result +'</td>';
	        			result = result +'<td>';
	        			result = result +'<div><span>'+report.spocMobilePhone+'</span></div>';
	        			result = result +'</td>';
	        			result = result +'<td>';
	        			result = result +'<div><span>'+report.orgName+'</span></div>';
	        			result = result +'</td>';
	        			result = result +'<td>';
	        			result = result +'<div><b class="proj_active">'+report.status+'</b></div>';
	        			result = result +'</td>';
	        			result = result +'<td>';
	        		if(report.releasedRequirement =="true"){
	        			result = result +'<div>Released</div>';
	        		}
					if(report.releasedRequirement =="false"){
						result = result +'<div><a href="javascript:void(0)"><i class="fa fa-bullhorn"></i></a></div>';
	        		}
	        			result = result +'</td>';
   	        		    result = result +'</tr>';
   				}
   	        	result = result +'</tbody>';
   	        	result = result +'</table>';
   	        	result = result +' </div>';
   	        
   	    	    $("#vendor").empty();  
   	    	    $("#vendorContacts").append(result)
   		    }
	 });
}	
</script>


<script>
function pagination(){  
    $('#candidatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>


<%-- 
     @author : David Raju.
     this script is for get VendorLists
     1.usage     : listChange at line number 421.
     2.parameter : csrf token .
     3.ajax url implementation in VendorRestController line number 94.
--%>

<script>
function getVendorLists(){
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	var requirementId = '${param.requirementId}';
	alert(requirementId);
	
	  $.ajax({
		   	type: "GET",
		    url: sourceleadUrl+"/getVendorListsForReleaseRequirement?requirementId="+requirementId, 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   	        	
                result = result +'<div class="require_wrap_table release-jobs-tbl">';
                result = result +'<table>';
                result = result +'<tbody>';
   	        	result = result +'<thead>';
   	        	result = result +'<tr>';
   	            result = result +'<th><input type="checkbox" id="checkbox1"/></th>'; 
   	        	result = result +'  <th>Name</th>';
   	        	result = result +'  <th>Location</th>';
   	        	result = result +' <th>No. of contacts</th>';
   	        	result = result +' <th>Level</th>';
   	        	result = result +' <th>Release</th>';
   	        	result = result +'</tr>';
   	        	result = result +'</thead>';
   	        	
   	        	for (var i=0; i<json.length; i++)
   				{  
   	        		var report = json[i];
   	        		var location = "";
   	        		if(report.city != null && report.city != '' && report.city != 'undefined'){
   	        			location = report.country+", "+report.state+", "+report.city;
   	        		}else if(report.state != null && report.state != '' && report.state != 'undefined'){
   	        			location = report.country+", "+report.state;
   	        		}else{
   	        			location = report.country;
   	        		}
   	        		
   	        		result = result +'<tr>';
   	        	    result = result +'  <td><input name="vendorcheckbox" type="checkbox" id="'+report.id+'" /></td>';
   	        	 
   	        		if( report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
   	        			result = result +'<td>';
   	        			result = result +'<div  class="tbldata-dotdot-sm text-left">';
   	        			result = result +'<span title='+report.orgName+'>'+report.orgName+'</span>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
   	        		
   	        		if(location != null && location  != '' && location  != 'undefined'){
   	        			result = result +'<td>';
   	        			result = result +'<div  class="tbldata-dotdot-sm text-left">';
   	        			result = result +'<span title="'+location+'">'+location+'</span>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
	       		result = result +'<td><div class="tbldata-dotdot-sm text-left">'+report.noOfContatcs+'</div></td>';
	       		result = result +' <td><div class="tbldata-dotdot-sm text-left"><span  title="'+report.level+'">'+report.level+'</span>';
   	        		result = result +'</tr>';
   				}
   	        	result = result +'</tbody>';
   	        	result = result +'</table>';
   	        	result = result +' </div>';
   	    	    $("#vendorContacts").empty();  
   	    	 	$("#vendor").empty();
   	    	    $("#vendor").append(result);
   	    	    
   		    }
	 });
	
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
// accordion script
$(document).ready(function(){
    $(function() {
    $('#accordion-sl .content').hide();
    $('#accordion-sl h2:first').addClass('active').next().slideDown('slow');
    $('#accordion-sl h2').click(function() {
        if($(this).next().is(':hidden')) {
            $('#accordion-sl h2').removeClass('active').next().slideUp('slow');
            $(this).toggleClass('active').next().slideDown('slow');
        }
    });
});
});
</script>



<%-- 
     @author : David Raju.
     this script is for get VendorLists
     1.usage     : listChange at line number 33.
--%>


<script type="text/javascript">
 function listChange(sel) {
	
    	if(sel.value=="releaseByVendors"){
			document.getElementById("vendor").style.display="block";
			document.getElementById("vendorContacts").style.display="none";
			getVendorLists()
        }
		 if(sel.value=="releaseByContact"){
			document.getElementById("vendorContacts").style.display="block";
			document.getElementById("vendor").style.display="none";
			getContactsLists();
        }
    }
</script>