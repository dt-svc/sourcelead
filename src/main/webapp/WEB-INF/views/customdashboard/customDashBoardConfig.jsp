<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script>
	$(document).ready(function() {
		getCustomDashBoardConfiguredList();
	});
</script>



<!-- Content Area -->
<section class="content-wraper">

	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Dashboard list</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="${appPath}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>
			&raquo; <a href="${appPath}/settings">Settings</a> &raquo; <a
				href='${SourceLeadWeb_sourceleadUrl}/configureCustomDashBoard'>Dashboard
				list</a>
		</div>
	</section>

	<div class="vendor_wrap clearfix">
		<div id="loading"></div>
		<div class="positionRelative reportHead clearfix">
			<ul class="pull-right list-unstyled list-inline">
				<li><a class="newAdBtn mr5" href='${SourceLeadWeb_sourceleadUrl}/addCustomDashBoardConf' title="Add">+ Add</a></li>
				<li><a class="newAdBtn mr5" href='${SourceLeadWeb_sourceleadUrl}/addDynamicHeadersWithData' title="Add">+ Dayana</a></li>
				
				
				
				<li><a href="javascript:void(0)" class="overIcon"
					title="Actions" onclick="newActions();"></a>
					<div class="newActPop" style="">
						<span><a href="javascript:void(0)" onclick="exportToPdf();"
							title="PDF">Export PDF</a> <a href="javascript:void(0)"
							onclick="exportUserData();" title="Excel">Export Excel</a> </span>
					</div></li>
				<li class=""><input class="repSearchBar" placeholder="Search"
					id="dynamicUserDataTableSearch" type="text"> <span
					class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
			</ul>
		</div>

		<div
			class="require_wrap_table tableFirstSecond clearfix datatable_filter">
			<div id="customDashBoardConfDiv"></div>
		</div>
	</div>

</section>

<div class="popupWrap popupWrapFixed" id="addComponets">
	<div class="popupHead">
		<span>Add Components</span><i class="closePop pull-right popClose"></i>
	</div>
	<input type="hidden" id="customDashBoardConfUUID"
		name="customDashBoardConfUUID" />
	<div class="popupContent adddashComp">
		<a onclick="returnCustomReportPage();"><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/add-report.svg">Reports</a>
		<a onclick="returnCustomModulePage();"><img src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/add-module.svg">Modules</a>
	</div>
</div>







<script>
	function getCustomDashBoardConfiguredList() {
		var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
		$("#loading")
				.attr(
						'style',
						'position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.get(sourceleadUrl + "/viewCustomDashBoardConfiguredList?"+ tokenParamName + "=" + tokenValue,function(res) {
							var data = res.data;
							var result = "";
							result = result
									+ '<table id="dashBoardConfigure" class="display" cellspacing="0" width="100%">';
							result = result + '<thead>';
							result = result + '<tr>';
							result = result + '<th>Name</th>';
							result = result + '<th>No of Components</th>';
							result = result + '<th>Action</th>';
							result = result + '<th>Make Default</th>';
							result = result + '</tr>';
							result = result + '</thead>';
							result = result + '<tbody>';

							if (data != null && data.length > 0) {
								for (var i = 0; i < data.length; i++) {
									var opt = data[i];
									result = result + '<tr>';
									result = result + '<td><div>'+ opt.dashBoardName + '</div></td>';
									if (opt.numberOfComponents != null && opt.numberOfComponents != '') {
										result = result + '<td><div>'+ opt.numberOfComponents+ '</div></td>';
									} else {
										result = result+ '<td><div>0</div></td>';
									}
									result = result + '<td>';
									result = result+ '<div class="width150px">';
									result = result+ ' <ul class="list-unstyled list-inline">';
									result = result+ '  <li class="mr5"><i class="fa fa-plus cursorPointer text-primary" title="Add Components" id="addCustomComponents'+ opt.customDashBoardConfId
											+ '" onclick="showPopup(\''
											+ opt.customDashBoardConfUUID
											+ '\');"></i></li>';
									result = result+ '  <li class="mr5"><a href="'
											+ sourceleadUrl
											+ '/editCustomDashBoardConf?customDashBoardUUID='
											+ opt.customDashBoardConfUUID
											+ '"><i class="fa fa-pencil cursorPointer" title="Edit"></a></i></li>';
									result = result  + '  <li class="mr5"><a href="javascript:void(0)" title="Delete"><i class="fa fa-trash textDelete" onclick="deleteCustomDashBoardConf(\''+opt.customDashBoardConfUUID+'\');"></i></a></li> ';
									result = result+ '  <li class="mr5"><a href="'+sourceleadUrl+'/configureCustomDashBoardView?customDashBoardUUID='+opt.customDashBoardConfUUID+'"><i class="fa fa-eye cursorPointer" title="Preview"></i></a></li>';
									
									//result = result+ '  <li class="mr5"><a href="'+sourceleadUrl+'/configureCustomDashBoardViewJSON?customDashBoardUUID='+opt.customDashBoardConfUUID+'"><i class="fa fa-eye cursorPointer" title="Preview"></i></a></li>';

									result = result + ' </ul>';
									result = result + ' </div>';
									result = result + '  </td>';
									result = result + '<td>';

									result = result + '<div>';
										if(opt.active){
								        	 result += '<label class="switch"><input type="checkbox" checked  value = "" onclick="makeDefaultCustomDashBoard(\''+opt.customDashBoardConfUUID+'\',\''+opt.active+'\');"> <div class="slider round"></div> </label>';
								         }else{
								        	 result += '<label class="switch"><input type="checkbox" onclick="makeDefaultCustomDashBoard(\''+opt.customDashBoardConfUUID+'\',\''+opt.active+'\');"> <div class="slider round"></div> </label>';
								         }									
									result = result + '</div>';	
									result = result + '</td>';
									result = result + ' </tr> ';
								}
							}
							result = result + '</tbody>';
							result = result + ' </table>';
							$("#customDashBoardConfDiv").empty();
							$("#customDashBoardConfDiv").append(result);
							$("#loading").removeAttr("style");
							customDashBoardConfigureTable();

						});
	}
</script>

<script>
	function customDashBoardConfigureTable() {
		$('#dashBoardConfigure').DataTable({
			"scrollCollapse" : false,
			"lengthMenu" : [ [ 10, 15, 20, -1 ], [ 10, 15, 20, "All" ] ],
			"bLengthChange" : true,
			"bDestroy" : true,
			"ordering" : true,
			"oLanguage" : {
				"sSearch" : "",
				"sZeroRecords" : "",
				"sEmptyTable" : ""
			}
		});
		$("#dashBoardConfigure").wrap(
				"<div class='overflow_Auto width100percnt'></div>")
	}
</script>


<script>
	function getActions() {
		$(".action-pop").slideToggle(200);
		$(".newActPop").slideUp(200);
	}
	$("html").click(function() {
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e) {
		e.stopPropagation();
	});

	function newActions() {
		$(".newActPop").slideToggle(200);
		$(".action-pop").slideUp(200);
	}
	$("html").click(function() {
		$(".newActPop").slideUp(200);
	});
	$(".overIcon, .newActPop").click(function(e) {
		e.stopPropagation();
	});

	// popup script 
	/* $("#addCustomComponents").click(function(){ 
	 alert("Hi");
	 $("#addComponets").show();
	 $(".mask").fadeIn(100);
	 }); */
	$(".popClose").click(function() {
		$("#addComponets").hide();
		$(".mask").fadeOut(100);
	});
</script>

<script>
	function showPopup(customDashBoardConfUUID) {
		$("#addComponets").show();
		$("#customDashBoardConfUUID").val(customDashBoardConfUUID);
		$(".mask").fadeIn(100);
	}
</script>


<script>
function deleteCustomDashBoardConf(customDashBoardUUID){
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/deleteCustomDashBoardConf?"+tokenParamName+"="+tokenValue+"&customDashBoardUUID="+customDashBoardUUID,
		    success: function(data) {
			 	getCustomDashBoardConfiguredList();
		    }
	     });
}
</script>



<script>
	function returnCustomModulePage() {
		var customDashBoardConfUUID = $("#customDashBoardConfUUID").val();
		var sourceleadUrl = "${SourceLeadWeb_sourceleadUrl}";
		window.location.href = sourceleadUrl
				+ "/configureCustomDashBoardModule?" + tokenParamName + "="
				+ tokenValue + "&customDashBoardConfUUID="
				+ customDashBoardConfUUID;
	}
</script>

<script>
function returnCustomReportPage(){
	
	var customDashBoardConfUUID = $("#customDashBoardConfUUID").val();
	var sourceleadUrl = "${SourceLeadWeb_sourceleadUrl}";
	window.location.href = sourceleadUrl
			+ "/configureCustomDashBoardReport?" + tokenParamName + "="
			+ tokenValue + "&customDashBoardConfUUID="
			+ customDashBoardConfUUID;
}
</script>


<script>
 function makeDefaultCustomDashBoard(customDashBoardUUID,active){
	  $.post( sourceleadUrl+ "/makeDefaultCustomDashBoard?customDashBoardUUID="+customDashBoardUUID+"&isActiveStatus="+active, function(data) {
		  if(data){
			  getCustomDashBoardConfiguredList();
		  }
	  });
  }
 </script>



<jsp:include page="../masterFooter.jsp" />
