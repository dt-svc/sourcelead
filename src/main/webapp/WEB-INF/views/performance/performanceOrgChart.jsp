<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${SourceLeadWeb_pmsUrl}/resources/css/performanceorgchart.css" type="text/css" >



<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Employee Org Chart</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo; Employee Org Chart
		</div>
	</section>
	
	<div class="project_wrap clearfix posiR overX">

        <div class="profOrgLeft">
            <h3>Appraisal Date</h3>
            <div class="profOrgDate clearfix">
	  			  <div class="row">
					  <div class="col-md-8 valMsgPosition">
						  <div class="input-group">
							  <input type="text" class="form-control calendar"  value="" id="appraisalStartDate" placeholder="Start Date" name="appraisalStartDate" readonly="readonly">
							  <span class="input-group-addon" onClick="clearAppraisalStartDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
						  </div>
					  </div>
					  
					  <div class="clearfix mt10"></div>
					  
					  <div class="col-md-8 valMsgPosition mt10">
						   <div class="input-group">
						     <input type="text" class="form-control calendar" value="" id="appraisalEndDate" placeholder="End Date" name="appraisalEndDate" readonly="readonly">
						     <span class="input-group-addon" onClick="clearAppraisalEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
						   </div>
					  </div>
					  
					  <button id="searchButton">Go</button>
					 
				  </div>
			
                
            </div>
            <ul id="orgChartAppraisalTemplate">
               <div id="employeeAppraisalList"></div>
            </ul>
        </div>

    <div class="profOrgRight">
            <div class="immiStepsHead clearfix">
            <div id="appraisalLoading"/>
            <input type="hidden" id="appraisalUserCode"/>
            <div class="selfAppraisalWidget clearfix">
            <ul class="list-unstyled list-inline">
                      <li class="activeApprisalwidget" title="overview">
                        <a id="templateOverView" class="clearfix mb0"> 
                        <div class="overviewIcon"></div>
                          Overview
                        </a>
                      </li>
                      <li title="Feedback" id="feedbackWidget">
                          <a id="feedbackOverView"  class="clearfix mb0">
                            <div class="categoryIcon"></div>
                            <div class="clearfix"></div>
                            Feedback
                        </a>
                      </li>
                      
                      <li title="Goals" id="goalsWidget">
                          <a id="goalsOverView" class="clearfix mb0">
                            <div class="goalsIcon"></div>
                            <div class="clearfix"></div>
                             Goals
                          </a>
                      </li>
                      
                      <li title="Project"  id="tasksWidget">
                          <a id="tasksOverView" class="clearfix mb0">
                          <div class="taksIcon"></div>
                          <div class="clearfix"></div>
                          Tasks
                      </a>
                      </li>
                      <li title="Final Score" id="finalScoreWidget">
                          <a id="finalOverView" class="clearfix mb0">
                          <div class="finalScoreIcon"></div>
                          <div class="clearfix"></div>
                        Final Score
                        </a>
                      </li>
                      <div class="clearfix"></div>
                    </ul>
                      <div id="performanceAppraisalDiv"></div>
                </div>
            </div>
        </div> 
        
        
        

        <div class="orgMask"></div>
        <div class="orgPop">
            <div class="perfOrgWrap">
                     <h2><span></span>
				 <div class="pull-right posiR"><input type="text" id='orgChartSearch' class="repSearchBar" placeholder="Search" />
                        <span class="repSearchIcon" id='orgChartSearchButton'></span>
                    </div>
		
                </h2>
                <div class="orgWrapNew" id="chart-container"></div>
                <div class="orgArr"></div>
            </div>            
        </div> 

    </div>
</section>
<script type="text/javascript" src="${SourceLeadWeb_pmsUrl}/resources/js/performance/performanceorgchart.js"></script>

<jsp:include page="../performance/orgChart/employeePerformanceOverView.jsp" /> 
<jsp:include page="../performance/orgChart/employeeAppraisalFeedback.jsp" /> 
<jsp:include page="../performance/orgChart/employeeAppraisalGoals.jsp" /> 
<jsp:include page="../performance/orgChart/employeeAppraisalTasks.jsp" />
<jsp:include page="../performance/orgChart/employeeAppraisalFinal.jsp" />

<script>
$(document).ready(function(){
	
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormat = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormat = 'yy-dd-mm'; 
	  }
	  
	  $('#appraisalStartDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
	  		var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate() + 1);
			$("#appraisalEndDate").datepicker("option", "minDate", dt);
	 	  }
	  });


	  $('#appraisalEndDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
	  		var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate() - 1);
			$("#appraisalStartDate").datepicker("option", "maxDate", dt);
	 	  }
	  });
	  
	     var dataResult = '';
		 dataResult = dataResult + '<div class="odd"><div valign="top" colspan="4" class="dataTables_empty"></div></div>';
		 $("#performanceAppraisalDiv").append(dataResult);

});
</script>

<script>
function getUserPerformanceDetails(userCode){
   $("#performanceAppraisalDiv").empty();  
   $("#templateOverView").removeAttr("onclick");
   $("#feedbackOverView").removeAttr("onclick");
   $("#goalsOverView").removeAttr("onclick");
   $("#tasksOverView").removeAttr("onclick");
   $("#finalOverView").removeAttr("onclick");
   
   $("#searchButton").removeAttr("onclick");
   $("#searchButton").attr("onclick", "getUserPerformanceDetails('"+userCode+"')");
   
   var appraisalStartDate = $("#appraisalStartDate").val();
   var appraisalEndDate = $("#appraisalEndDate").val();
   
     document.getElementById("tasksWidget").removeAttribute("class");
	 document.getElementById("feedbackWidget").removeAttribute("class");
	 document.getElementById("goalsWidget").removeAttribute("class");
	 document.getElementById("finalScoreWidget").removeAttribute("class");
   
	 $("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		  type: "GET",
		    url: pmsUrl+"/getOrgChartEmployeeAppraisalList?userCode="+userCode+"&appraisalStartDate="+appraisalStartDate+"&appraisalEndDate="+appraisalEndDate,
		     success :function(response) {
		    	 
		    	 var result = "";
		    	 var dataResult = '';
		    	 var assignedUUID = '';
		    	 var flag = false;
		    	 if(response.data != null && response.data.length > 0){
		    		 for (var i=0; i<response.data.length; i++)
	        	    	{
		    			 var opt = response.data[i];
		    			 var selectedClass = '';
		    			 if(flag == false){
	        	    	    	selectedClass = 'class="act"';
	        	    	    	assignedUUID = opt.assignedUUID;
	        	    	    	flag = true;
	        	    	    } 
		    			 
		            	 result = result +'<li onclick="getEmployeeAppraisalOverView(\''+opt.assignedUUID+'\')" rel="'+opt.assignedUUID+'"><a '+selectedClass+' id="orgChartAppraisalTemplate'+opt.assignedUUID+'" href="javascript:viod(0)">'+opt.name+'<span>'+opt.appraisalStartDate+'  to '+opt.appraisalEndDate+'</span></a></li>';
		           	     result = result +"</div>";
	        	    	}
		    	 }else{
		    		 result = result + '<div><span class="ml colorRed">No Appraisals Found</span></div>';
		    		 dataResult = dataResult + '<div class="odd"><div valign="top" colspan="4" class="dataTables_empty"></div></div>';
		    	 }
		    	 
		    	 $("#employeeAppraisalList").empty(); 
		    	 $("#employeeAppraisalList").append(result);
		    	 $("#performanceAppraisalDiv").append(dataResult);
		    	 
		    	 if(flag){
		    		 getTemplateOverViewDetails(assignedUUID);
		    	 }
		    	 
		     },
	});
	$("#appraisalLoading").removeAttr("style");
}

function getEmployeeAppraisalOverView(assignedUUID){
	 $("#templateOverView").attr("onclick", "getTemplateOverViewDetails('"+assignedUUID+"')");
	 $("#feedbackOverView").attr("onclick", "getAppraisalFeedbackDetails('"+assignedUUID+"')");
	 $("#goalsOverView").attr("onclick", "getAppraisalGoalsDetails('"+assignedUUID+"')");
	 $("#tasksOverView").attr("onclick", "getAppraisalTaskDetails('"+assignedUUID+"')");
	 $("#finalOverView").attr("onclick", "getAppraisalFinalScoreDetails('"+assignedUUID+"')");
	 
	 $('#orgChartAppraisalTemplate li').each(function(){
			document.getElementById("orgChartAppraisalTemplate"+$(this).attr('rel')).removeAttribute("class")
		});
		document.getElementById("orgChartAppraisalTemplate"+assignedUUID).className = 'act'
		
	 getTemplateOverViewDetails(assignedUUID);
}
</script>





<script>
$(document).ready(function(){
    $(".orgArr").click(function(){
        $(this).toggleClass("act");
        $(".orgPop").toggleClass("orgPopAct");
        $(".orgMask").toggleClass("orgMaskfade");
    });
});
</script>

<script>
$(document).ready(function(){
    $(".node").click(function(e){
         e.stopPropagation();
    });	
    
});
</script>


<script type="text/javascript">
var sourceleadUrl = '${sourceleadUrl}';
var orgLocationId = '${SourceLeadWeb_orgLocationId}';
$(document).ready(function(){
    getOrgChartData(orgLocationId);
	
	$("#orgChartSearch").keyup(function(event) {
		if(event.which == 13) 
			$("#orgChartSearchButton").click();
	});
	
	$("#orgChartSearchButton").click(function() {
		var keyword = $("#orgChartSearch").val().trim();
		filterNodes(keyword);
	});
	
	/* $("#orgChartSearchClear").click(function() {
		$("#orgChartSearch").val('');
		clearFilterResult();
	});  */
});

function filterNodes(keyWord) {
    if(!keyWord.length) {
      window.alert('Please type key word firstly.');
      return;
    } else {
    	  clearFilterResult();
      var $chart = $('#chart-container');
      // disalbe the expand/collapse feture
      $chart.addClass('noncollapsable');
      // distinguish the matched nodes and the unmatched nodes according to the given key word
      $chart.find('.node').filter(function(index, node) {
          return $(node).text().toLowerCase().indexOf(keyWord) > -1;
        }).addClass('matched')
        .closest('table').parents('table').find('tr:first').find('.node').addClass('retained');
      // hide the unmatched nodes
      $chart.find('.matched,.retained').each(function(index, node) {
        $(node).removeClass('slide-up')
          .closest('.nodes').removeClass('hidden')
          .siblings('.lines').removeClass('hidden');
        var $unmatched = $(node).closest('table').parent().siblings().find('.node:first:not(.matched,.retained)')
          .closest('table').parent().addClass('hidden');
        $unmatched.parent().prev().children().slice(1, $unmatched.length * 2 + 1).addClass('hidden');
      });
      // hide the redundant descendant nodes of the matched nodes
      $chart.find('.matched').each(function(index, node) {
        if (!$(node).closest('tr').siblings(':last').find('.matched').length) {
          $(node).closest('tr').siblings().addClass('hidden');
        }
      });
    }
  }

  function clearFilterResult() {
    $('#chart-container').removeClass('noncollapsable')
      .find('.node').removeClass('matched retained')
      .end().find('.hidden').removeClass('hidden')
      .end().find('.slide-up, .slide-left, .slide-right').removeClass('slide-up slide-right slide-left');
  }
  var data = {};
  function getOrgChartData(id) {
	    $.post('${SourceLeadWeb_pmsUrl}/performanceOrgChart', {id: id}, function(ds) {
	         data = {};
	    $.each(ds, function(i) {
	    		if(ds[i]["parentId"] == "0") {
	    			var obj  = {};
	    			obj.id = ds[i].id;
	    			obj.userCode = ds[i].userCode;
	    			obj.name = ds[i].name;
	    			obj.designation = ds[i].designation;
	    			obj.email = ds[i].email;
	    			if(ds[i].fileURL && ds[i].fileURL !== "")
	          			obj.avatar = sourceleadUrl.replace("/app", "")+ds[i].fileURL;
	    			else 
	    				obj.avatar = sourceleadUrl.replace("/app", "")+"/resources/images/userprofile-img.png";
	          		data = obj;
		    		obj.children = [];
	    			$.each(ds, function(j) {
	    				if(ds[j].parentId == obj.id) {
							if(ds[j].fileURL && ds[j].fileURL !== "")	    					
	    						ds[j].avatar = sourceleadUrl.replace("/app", "")+ds[j].fileURL;
	    					else
	    						ds[j].avatar = sourceleadUrl.replace("/app", "")+"/resources/images/userprofile-img.png";
	    					obj.children.push(ds[j]);
	    				}
	    			});
	    		} else {
	    			ds[i].children = [];
	    			$.each(ds, function(j) {
	    				if(ds[i].id == ds[j].parentId) {
	    					if(ds[j].fileURL && ds[j].fileURL !== "")	    					
	    						ds[j].avatar = sourceleadUrl.replace("/app", "")+ds[j].fileURL;
	    					else
	    						ds[j].avatar = sourceleadUrl.replace("/app", "")+"/resources/images/userprofile-img.png";
	    					ds[i].children.push(ds[j]);
	    				}
	    			});
	    		}
	    });
	    
	   initChart(data);
	});
}

function initChart(data) {
	$('#chart-container').html("");  
	 $('#chart-container').orgchart({
	   'data' : data,
	   'depth': 2,
	   'nodeTitle': 'name',
       'nodeContent': 'designation',
       'nodeID': 'id',
       'nodeCode': 'userCode',
	   'createNode': function($node, data) {
	   var secondMenuIcon = $('<i>', {
	     'class': 'fa fa-user second-menu-icon',
	     click: function() {
	    	 $(".second-menu").hide();
	       $(this).siblings('.second-menu').toggle();
	     }
	   });
	   var secondMenu = '<div class="second-menu"><img class="avatar" onerror="predefineUserImage(this);" src="'+data.avatar+'"><h4>'+data.designation+'</h4><p>'+data.email+'</p></div>';

	   
	   $node.append(secondMenuIcon).append(secondMenu);
	   }
	   });
	 
}
</script>

<script>
	function predefineUserImage(imageId){
		$(imageId).attr('src','/resources/images/userprofile-img.png');
	}
	
  function clearAppraisalStartDate(){
		  $("#appraisalStartDate").val("");
		  $("#appraisalEndDate").val("");
		  
		  $("input[id$='appraisalStartDate'], input[id$='appraisalEndDate']").datepicker( "option", "maxDate", null );
		  $("input[id$='appraisalStartDate'], input[id$='appraisalEndDate']").datepicker( "option", "minDate", null );
	  }
	  
  function clearAppraisalEndDate(){
		  $("#appraisalEndDate").val("");
		  $("input[id$='appraisalStartDate'], input[id$='appraisalEndDate']").datepicker( "option", "maxDate", null );
	  }
</script>