<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${SourceLeadWeb_pmsUrl}/resources/css/performanceorgchart.css" type="text/css" >



<section class="content-wraper contentWraper">
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
            <h3>Appraisal Heading</h3>
            <div class="profOrgDate clearfix">
                <input type="text" placeholder="from" />
                <input type="text" placeholder="to" />
                <button>Go</button>
            </div>
            <ul>
                <li><a class="act" href="javascript:viod(0)">Appraisal Name<span>from 06/05/19 - to 01/07/2020</span></a></li>
                <li><a href="javascript:viod(0)">Appraisal Name<span>from 06/05/19 - to 01/07/2020</span></a></li>
                <li><a href="javascript:viod(0)">Appraisal Name<span>from 06/05/19 - to 01/07/2020</span></a></li>
                <li><a href="javascript:viod(0)">Appraisal Name<span>from 06/05/19 - to 01/07/2020</span></a></li>
                <li><a href="javascript:viod(0)">Appraisal Name<span>from 06/05/19 - to 01/07/2020</span></a></li>
            </ul>
        </div>

        <div class="profOrgRight">
            <div class="immiStepsHead clearfix">
            <div class="selfAppraisalWidget clearfix">
            <ul class="list-unstyled list-inline">
                    <li class="activeApprisalwidget" title="overview">
                    <a href="selfappraisal-view.html" class="clearfix mb0"> 
                    <div class="overviewIcon"></div>
                        Overview
                    </a>
                    </li>
                    <li title="Feedback">
                        <a href="selfappraisal-category.html" class="clearfix mb0">
                        <div class="categoryIcon"></div>
                        <div class="clearfix"></div>
                        Feedback
                    </a>
                    </li>
                    <li title="Goals">
                        <a href="selfappraisal-goals.html" class="clearfix mb0">
                        <div class="goalsIcon"></div>
                        <div class="clearfix"></div>
                            Goals
                        </a>
                    </li>
                    <li title="Project">
                        <a href="selfappraisal-tasks.html" class="clearfix mb0">
                        <div class="taksIcon"></div>
                        <div class="clearfix"></div>
                        Tasks
                    </a>
                    </li>
                    <li title="Final Score">
                        <a href="selfappraisal-finalscore.html" class="clearfix mb0">
                        <div class="finalScoreIcon"></div>
                        <div class="clearfix"></div>
                    Final Score
                    </a>
                    </li>
                    <div class="clearfix"></div>
                </ul>
                </div>
            </div>

            <div class="clearfix goalssummar_widget">
                        <div class="clearfix">
                        <div class="form-group clearfix">
                            <label class="col-md-2 paddingRight">Appraisal Name<span class="pull-right paddingLeft">:</span></label>
                            <div class="col-md-5">Name</div>
                        </div>
                        <div class="form-group clearfix">
                            <label class="col-md-2 paddingRight">Process Period<span class="pull-right paddingLeft">:</span></label>
                            <div class="col-md-5">01/04/2018 <span class="text-muted ml mr">to</span> 01/04/2019</div>
                        </div>
                        <div class="form-group clearfix">
                            <label class="col-md-2 paddingRight">Due Date<span class="pull-right paddingLeft">:</span></label>
                            <div class="col-md-5">01/04/2018</div>
                        </div>
                        <div class="form-group clearfix">
                            <label class="col-md-2 paddingRight mt10">Peers<span class="pull-right paddingLeft">:</span></label>
                            <div class="col-md-10">
                            <ul class="list-inline list-unstyled selfAppraisalpeersList mb0">
                                <li>
                                    <div class="selfAppraisalImg">
                                        <img src="../images/24.jpg" alt="Image">
                                    </div>
                                    <span class="selfAppraisalpeerName">Employee Name1</span>
                                </li>
                                <li>
                                    <div class="selfAppraisalImg">
                                        <img src="../images/24.jpg" alt="Image">
                                    </div>
                                    <span class="selfAppraisalpeerName">Employee Name2</span>
                                </li>
                                <li>
                                    <div class="selfAppraisalImg">
                                        <img src="../images/24.jpg" alt="Image">
                                    </div>
                                    <span class="selfAppraisalpeerName">Employee Name3</span>
                                </li>
                                <li>
                                    <div class="selfAppraisalImg">
                                        <img src="../images/24.jpg" alt="Image">
                                    </div>
                                    <span class="selfAppraisalpeerName">Employee Name4</span>
                                </li>
                                <li>
                                    <div class="selfAppraisalImg">
                                        <img src="../images/24.jpg" alt="Image">
                                    </div>
                                    <span class="selfAppraisalpeerName">Employee Name5</span>
                                </li>
                                <li>
                                    <div class="selfAppraisalImg">
                                        <img src="../images/24.jpg" alt="Image">
                                    </div>
                                    <span class="selfAppraisalpeerName">Employee Name6</span>
                                </li>
                                <li>
                                    <div class="selfAppraisalImg">
                                        <img src="../images/24.jpg" alt="Image">
                                    </div>
                                    <span class="selfAppraisalpeerName">Employee Name6</span>
                                </li>

                            </ul>  

                            
                            
                            </div>
                        </div>

                        <div class="form-group clearfix">
                            <label class="col-md-2 paddingRight mt10">Manager<span class="pull-right paddingLeft">:</span></label>
                            <div class="col-md-5">
                            
                                <div class="selfAppraisalImg">
                                    <img src="../images/24.jpg" alt="Image">
                                </div>
                                <span class="selfAppraisalpeerName">Ramraj Kumar Savaram
                                    </span>
                            </div>
                            </div>
        
                            <div class="form-group clearfix">
                                <label class="col-md-2 paddingRight">Review  Peirod<span class="pull-right paddingLeft">:</span></label>
                                <div class="col-md-5">1 Year</div>
                            </div>


                        <div class="form-group clearfix">
                            <label class="col-md-2 paddingRight">Description<span class="pull-right paddingLeft">:</span></label>
                            <div class="col-md-10"> Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. </div>
                        </div>

                        </div>
            </div>
        </div>

        <div class="orgMask"></div>
        <div class="orgPop">
            <div class="perfOrgWrap">
                <h2><span>Org Chart Heading</span>
                    
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
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="${SourceLeadWeb_pmsUrl}/resources/js/performance/performanceorgchart.js"></script>
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
    	  //clearFilterResult();
      var $chart = $('#chart-container');
      // disalbe the expand/collapse feture
      $chart.addClass('noncollapsable');
      // distinguish the matched nodes and the unmatched nodes according to the given key word
      $chart.find('.node').filter(function(index, node) {
          return $(node).text().toLowerCase().indexOf(keyWord) > -1;
        }).addClass('matched')
        .closest('table').parents('table').find('tr:first').find('.node').addClass('retained');
      // hide the unmatched nodes
        $(node).removeClass('slide-up')
          .closest('.nodes').removeClass('hidden')
          .siblings('.lines').removeClass('hidden');
        var $unmatched = $(node).closest('table').parent().siblings().find('.node:first:not(.matched,.retained)')
          .closest('table').parent().addClass('hidden');
        $unmatched.parent().prev().children().slice(1, $unmatched.length * 2 + 1).addClass('hidden');
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
	       $(this).siblings('.second-menu').toggle();
	     }
	   });
	   var secondMenu = '<div class="second-menu"><img class="avatar" onerror="predefineUserImage(this);" src="'+data.avatar+'"></div>';
	   $node.append(secondMenuIcon).append(secondMenu);
	   }
	   });
	 
}
</script>

<script>
	function predefineUserImage(imageId){
		$(imageId).attr('src','/resources/images/userprofile-img.png');
	}
</script>