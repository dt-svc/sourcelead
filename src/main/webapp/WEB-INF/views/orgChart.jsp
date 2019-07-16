
<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery.orgchart.css?staticTime=${staticTime}">


<script>
var atsUrl = '${SourceLeadWeb_atsUrl}';
</script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2 class="pull-left">Org chart view</h2>
    </div>
    <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/settings">Settings</a>&raquo; Org Chart View </div>
    </c:if> 
    
    <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/recruiterDashboard">Home </a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/settings">Settings</a>&raquo; Org Chart View  </div>
    </c:if> 
  </section>
  
  <!--   Org Chart   -->

	<div class="OrgChartNew">
		 <div class="OrgSearch">
			<select class="form-control select-styles" id="chartLocation">
                    <c:forEach items="${locations}" var='l'>
                        <option ${l.id == SourceLeadWeb_orgLocationId ? "selected='selected'" : ""} value="${l.id}">${l.orgLocationName} - ${l.city}${l.state == null || l.state.isEmpty() ? "" : ", ".concat(l.state)}${l.country == null || l.country.isEmpty() ? "" : ", ".concat(l.country)}</option>
                    </c:forEach>
                </select>
            <input type="text" placeholder="Search" id="orgChartSearch"><input id='orgChartSearchButton' class="OrgBt" type="button" value="&nbsp" />
		<i class="fa fa-times refresh" id='orgChartSearchClear'></i>
		<div class="clearfix"></div>
		</div> 
		 
		<div id="chart-container" class="text-center"></div>
	</div>

  <!--   Org Chart   -->
  
</section>

<script type="text/javascript">
var sourceleadUrl = '${sourceleadUrl}';
$(document).ready(function(){
    $("#chartLocation").change(function() { getOrgChartData($("#chartLocation").val()); });
    getOrgChartData($("#chartLocation").val());
	
	/* $(".OrgChBtn").click(function(){
		$(this).toggleClass("OrgChBtnAct");
		if($(this).attr('class').indexOf('OrgChBtnAct') == -1) {
// 			$('#chart-container').html("");
			$('#chart-container').orgchart({"collapsed" : true});
		}else {
			$('#chart-container').orgchart({"collapsed" : false});
		}
	}); */
	
	$("#orgChartSearch").keyup(function(event) {
		if(event.which == 13) 
			$("#orgChartSearchButton").click();
	});
	
	$("#orgChartSearchButton").click(function() {
		var keyword = $("#orgChartSearch").val().trim();
		filterNodes(keyword);
	});
	
	$("#orgChartSearchClear").click(function() {
		$("#orgChartSearch").val('');
		clearFilterResult();
	});
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
          return $(node).text().toLowerCase().indexOf(keyWord.toLowerCase()) > -1;
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
	    $.post('${sourceleadUrl}/org/orgChart', {id: id}, function(ds) {
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
	          var nodePrompt = $('<i>', {
	            'class': 'fa fa-info-circle second-menu-icon',
	            'title': "Show Profile",
	            click: function() {
	            	window.open('${SourceLeadWeb_atsUrl}/userSummaryDetails?userCode='+data["userCode"], "_blank");
	            }
	          });
//	          var secondMenu = '<div class="second-menu">Extra Details</div>';
	          $node.append(nodePrompt); //.append(secondMenu);
	        }
	      });
}
</script>

 <jsp:include page="masterFooter.jsp" />	