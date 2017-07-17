<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Request Associates</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="project_Planning_US.html">Planning</a> &raquo; Request Associates</div>
    </section>
    
<div class="content-area content-new clearfix">
    <div class="row form-horizontal mt myForm">
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
         <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Request Id </label>
<div class="col-md-9">
                  <div class="form-icon">
                <input class="form-control pull-left" name="Project Id" value="" type="text">
                
                  <i class="fa fa-refresh btn btn-default formic1" title="Project Id"></i>
                <span class="hidden">Project Id</span> </div>
                  </div>            </div>
            <div class="form-group">
              <label class="col-md-3">Base Line <span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="Website" value="">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3">Approver <span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <select class="form-control selectpicker">
                  <option>Approver 1</option>
                  <option>Approver 2</option>
                  <option>Approver 3</option>
                  <option>Approver 4</option>
                </select>
              </div>
            </div>
            </div>
          </div>

<div class="project_side_wrap_inn_tabs clearfix">
    <div class="project_tabs_inn_head">
        	<div class="project_tabs_inn_head">
        	   <a href="javascript:void(0)" class="" title="Search Associates" onclick="getAssociates();" id="addDiv">
                   <i class="glyphicon glyphicon-plus"></i>
                   Search Associates
               </a>
			</div>
<div class="require_wrap_table mt10">
		<table>
        	<tbody>
            	<tr>
                	<th>Name</th>
                    <th>Pay Rate</th>
                    <th>Bill Rate</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Consulting</th>
                    <th>Est Hours</th>
                    <th>Est Budget</th>
                    <th>Action</th>
                </tr>
                <tr>
                	<td><div class="tbldata-dotdot-100">Mr Jhon</div></td>
                    <td>
                        <div class="tbldata-dotdot-sm">
                          <small class="pull-left">USD</small>
                          <div class="tbldata-dotdot-100 pull-left">102456987</div>
                          <small class="pull-right" title="Semi_Monthly">SM</small>
                        </div>
                    </td>
                    <td>
                    <div class="changeRate_div">
                  <div class="pull-left dropdown-select drpdownCurrency">
                      <select>
                          <option>USD</option>
                          <option>INR</option>
                      </select>
                  </div>
                  <div class="pull-left inputRate">
                      <input class="form-control input-sm" type="text">
                  </div>
                  <div class="pull-left drpdownMonthly dropdown-select" title="Semi_Monthly">
                  <select>
                          <option value="Semimonthly">SM</option>
                          <option value="Monthly">M</option>
                      </select>
                  </div>
                </div>
                    </td>
                    <td>
                    <div class="tbldata-dotdot-100">
                   <input class="form-control input-sm calendar" type="text">
                </div>
                    </td>
                    <td>
                    <div class="tbldata-dotdot-100">
                        <input class="form-control input-sm calendar" type="text">
                    </div>
                    </td>
                    <td>
                    <select>
                          <option selected>--Select--</option>
                          <option value="Offsite">Off Site</option>
                          <option value="Onsite">On Site</option>
                          <option value="Onsite">Near Shore</option>
                          <option value="Onsite">Off Shore</option>
                        <option value="Onsite">Hybrid</option>
                      </select>
                    </td>
                    <td><div class="tbldata-dotdot-100">
                        <input class="form-control input-sm" type="text">
                    </div></td>
                    <td><div class="tbldata-dotdot-100">
                        <input class="form-control input-sm" type="text">
                    </div></td>
                    <td>
                        <div><i class="glyphicon glyphicon-trash"></i></div>
                    </td>
                </tr> 
            </tbody>
        </table>  
		</div>       
			<nav>
<ul id="" class="pagination">
	<li><a href="#">Previous</a></li>
	<li><a href="#">2</a></li>
	<li><a href="#">5</a></li>
	<li class="disabled"><a href="#">6</a></li>
	<li data-page="last"><a href="#">Next</a></li>
	</ul>
	<div class="clearfix"></div>
</nav>				
    </div>
</div>        

	<div class="form-footer clearfix mt">
		  <div class="col-lg-12">
			<div class="pull-right">
			  <input type="button" value="Save" class="btn btn-primary" id="save">
			  <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/projectSummary?projectUUID=${projectUUID}&tabType=resources'/>'" class="btn btn-default ml">
			</div>
		  </div>
	</div>        
   </div> 
      </div>
    </div>
  </section>

<jsp:include page="../masterFooter.jsp" />	



<div class="side_mask">
    <div class="proj_close"></div>
</div>  
<div id="associatesList"></div>

<script>
function getAssociates(){
	var result = '';
	
	result = result +'<div class="clients_side_wrap">';
	result = result +'<div class="clients_side_wrap_inn">';
	result = result +'<div class="timesheet_head">';
	      
	result = result +'<ul class="pull-right">';
	

	        
	result = result +'<li class="dropdown">';
	result = result +'<span class="dropdown-toggle" id="menu3" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter"></i><span>Role</span><i class="glyphicon glyphicon-play"></i></span>|';
	          
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">';
	result = result +'<ul class="location-filter">';
	result = result +'<div id="orgLocationRoles"></div>';  
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li>';  
	
	result = result +'<li class="dropdown">';
	result = result +'<span class="dropdown-toggle" id="menu1" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter"></i><span>Location</span><i class="glyphicon glyphicon-play"></i></span>|';
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu bootstrap_dropdownMenu-right" role="menu" aria-labelledby="menu1">';
	result = result +'<ul class="location-filter">';
	result = result +'<c:forEach var="locations" items="${orgLocationsList}">';
	result = result +'<li>';
	result = result +'<input class="ml10" value="${locations.id}" onclick="getOrgLocationRoles();"  name="orgLocations" ${SourceLeadWeb_orgLocations.id  == locations.id ? "checked" : ''} type="radio">';
	result = result +'<label class="radioAlign"><img src="resources/images/${locations.country}.gif">${locations.orgLocationName}</label>';
	result = result +'</li>';
	result = result +'</c:forEach>';
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li>';
	          
	result = result +'<li class="dropdown">';
	result = result +'<span class="dropdown-toggle" id="menu2" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter"></i><span>Filter</span><i class="glyphicon glyphicon-play"></i></span>|';
	          
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu2">';
	result = result +'<ul class="resourcetype-filter">';
	result = result +'<li>';
	result = result +'<input value="hotlist" id="hotlist" name="resourceType" checked type="checkbox" onclick="getAssociatesFilter()" >';
	result = result +'<label for="hotlist">Hotlist</label>';
	result = result +'</li>';
	result = result +'<li>';
	result = result +'<input  value="assigned" id="Assigned" name="resourceType" checked type="checkbox" onclick="getAssociatesFilter()" >';
	result = result +'<label for="Assigned">Assigned</label>';
	result = result +'</li>';
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li>';
	result = result +'<div class="clearfix"></div>';
	result = result +'</ul>';
	result = result +'<div class="clearfix"></div>';
	result = result +'</div>';
	     
	result = result +'<div id="associatesListLoader"></div>';
	result = result +'<div id="associatesListFilter"></div>';

	result = result +'</div>';
	result = result +'</div>';
	
	 $("#associatesList").empty();
     $("#associatesList").append(result);
    
    getOrgLocationRoles();
 	
}
</script>


<script>
function getOrgLocationRoles(){
	var orgLocations = [];
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocations.push($(this).val());
	    });
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getOrgLocationRoles?${_csrf.parameterName}=${_csrf.token}&orgLocations="+orgLocations, 
	        success: function(data) {
	        	
	        	var json = JSON.parse(data);
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		output = output + '<li><input value="'+opt.id+'" name="roles" checked type="checkbox" onclick="getAssociatesFilter()">'+opt.displayName+'</li>';
				}
	        	$("#orgLocationRoles").empty();
		        $("#orgLocationRoles").append(output);
		        getAssociatesFilter();
	        }
	        
	 });
}
</script>

<script>
function getAssociatesFilter(){
	
	var roles = [];
	$('input:checkbox[name=roles]:checked').each(function() 
		    {
		roles.push($(this).val());
	    });
	
	var orgLocationId
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocationId = $(this).val();
	    });
	
	var resourceType = [];
	$('input:checkbox[name=resourceType]:checked').each(function() 
		    {
		resourceType.push($(this).val());
    });
	
	
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#associatesListLoader").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getRequestedAssociatesList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId+"&roles="+roles+"&resourceType="+resourceType, 
	        success: function(data) {
	        	
	        	var json = JSON.parse(data);
	        	
	        	var result = '';
	        	result = result +'<div class="require_wrap_table datatable_filter overflow_inherint">';
	        	result = result +'<table id="associatesListPagination" cellspacing="0" width="100%">';
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result +'<th>';
	        	result = result +'<input id="checkbox1" type="checkbox">';
	        	result = result +'</th>';
	        	/* result = result +'<th>ID</th>'; */
	        	result = result +'<th>Name</th>';
	        	result = result +'<th>Primary Skill</th>';
	        	result = result +'<th>Rate</th>';
	        	result = result +'<th>Emp. Type</th>';
	        	result = result +'<th>Manager</th>';
	        	result = result +'<th>Resume</th>';
	        	result = result +'<th>Projects</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';
	        	
	        	for (var i=0; i<json.length; i++){
	        		var report = json[i];
	        		
	        		var candidate = '';
	        		var primarySkill = '';
	        		
	        		
	        		if(report.lastName != null && report.lastName != '' && report.lastName != 'undefined'){
	        			candidate = report.firstName+" "+report.lastName;
	        		}else{
	        			candidate = report.firstName;
	        		}
	        		
	        		if(report.primarySkill != null && report.primarySkill != '' && report.primarySkill != 'undefined'){
	        			primarySkill = report.primarySkill;
	        		}else{
	        			primarySkill = "-----"
	        		}
	        		
	        		    result = result +'<tr>';
			        	result = result +'<td>';
			        	result = result +'<input  type="checkbox">';
			        	result = result +'</td>';
			        	/*result = result +'<td>';
			        	 result = result +'<div><span title="0025896">0025896</span></div>'; 
			        	result = result +'</td>';*/
			        	result = result +'<td>';
			        	result = result +'<div title="'+candidate+'">';
			        	
			        	result = result + '<p class="tbldata-dotdot-me pull-left">'+candidate+'</p>';
			        	result = result +'<div class="show_Empdetails">';
			        	result = result +'<i class="fa fa-ellipsis-v"></i>';

			        	result = result +'<div class="emailvalues">';
			        	result = result +'<p title="'+report.email+'"><i class="fa fa-envelope"></i>'+report.email+'</p>';
			        	if(report.mobilePhone != null && report.mobilePhone != '' && report.mobilePhone != 'undefined'){
			        		result = result +'<p title="'+report.mobilePhone+'"><i class="fa fa-phone"></i>'+report.mobilePhone+'</p>';
			        	}
			        	result = result +'</div>';
			        	result = result +'</div>';


			        	result = result +'</div>';
			        	result = result +'</td>';
			      
			        	result = result +'<td>';
			        	result = result +'<div title="'+primarySkill+'" class="tbldata-dotdot_80">'+primarySkill+'</div>';
			        	result = result +'</td>';
			        	result = result +'<td>';
			        	result = result +'<div class="tbldata-dotdot-sm">';
			        	result = result +'<small class="pull-left">USD</small>';
			        	result = result +'<div class="tbldata-dotdot-100 pull-left">102365478</div>';
			        	result = result +'<small class="pull-right" title="Semi_Monthly">SM</small>';
			        	result = result +'</div>';
			        	result = result +'</td>';
			        	result = result +'<td>';
			        	result = result +'<div title="Development" class="tbldata-dotdot_80">C2C</div>';
			        	result = result +'</td>';
			        	result = result +'<td>';
			        	result = result +'<div title="Development" class="tbldata-dotdot_80">Manager</div>';
			        	result = result +'</td>';
			        	result = result +'<td>';
			        	result = result +'<i class="fa fa-download" title="Download Resume"></i>';
			        	result = result +'</td>';
			        	result = result +'<td>';
			        	result = result +'<div title="Development" class="tblminwidth-50 projects_div">  ';                
			        	result = result +'3';
			        	result = result +'</div>';
			        	result = result +'</td>';
			        	result = result +'</tr>';
	        	}
	        	
	        	        result = result +'</tbody>';
					    result = result +'</table>';
					    result = result +'</div>';
					
						$("#associatesListFilter").empty();
					    $("#associatesListFilter").append(result);
					    pagination();
					    
	        	}
			   
	        });
	
	 $("#associatesListLoader").removeAttr("style"); 
	
	
	
}
</script>

<script>
function pagination(){  
    $('#associatesListPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[15, 30, 50, -1], [15, 30, 50, "All"]],
		"aaSorting": [[4,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#associatesListPagination').wrap("<div class='overflow_Auto'></div>");
}
</script>



 
 	<script type="text/javascript">
	$(".AdRes").click(function(){
		$(".addResource").slideToggle(0);
	});
	$(".addResClo").click(function(){
		$(".addResource").slideUp(0);
	});		
	</script>
 
	<script type="text/javascript">
// project side bar tabs 
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});	
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
 
 <script type="text/javascript">
    $(document).ready(function() {
    $("#addDiv").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
        $('.addcandidate-popup').fadeOut(200);
	});	
});

</script>		
  
  <script type="text/javascript">
		function closePop(){
			$("div.cand-advsrch-popup").hide();
		}
		
		function openPop(){
			$("div.cand-advsrch-popup").toggle();
		}
	</script>