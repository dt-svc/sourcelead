	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Background details</title>
   <section class="content-wraper">
      <section class="content-header clearfix">
        <div class="pull-left">
          <h2>Background Verification</h2>
        </div>
        <div class="pull-right brud-crum">Home » Payhead List » BGC</div>
      </section>
      <div class="contentareaInner-styles clearfix">
        <div class="timesheet_out" id="payrollRegister" style="display: block;">
          <div class="clearfix">
            <div class="positionRelative reportHead clearfix mb5">
                <ul class="project-tabs-menu projecttabs-custom clearfix productListtab pull-left ml bgonProgressTab">
                    <li class="current" id="self">
                      <a href="#bgc-inProgress" class="pull-left">In progress</a>
                      <span class="label label-warning mt10 pull-left">30%</span>
                    </li>
                    <li id="public">
                        <a href="#bgc-Completed" class="pull-left">Completed</a>
                        <span class="label label-success mt10 pull-left">100%</span>
                    </li>
                  </ul>
                </div>

              <div id="bgc-inProgress" class="clearfix bgcTemplateInformation-inner" style="display: block;">
              <!-- <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span> -->
              <ul class="pull-right list-unstyled list-inline">
                <li><a class="newAdBtn mr5" href="bgc-initiate.html" title="Add">Initiate</a></li>
                <!-- <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
                <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a> -->
                  <div class="newActPop" style=""> <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a> </span> </div>
                
                <li class="positionRelative">
                  <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                  <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
              </ul>
            
                 <div id="dynamicBgcListViewDiv" class="project_wrap_table datatable_filter"></div>

          <div class="bgcTemplateInformation-inner clearfix" id="bgc-Completed" style="display:none">
            <div class="col-md-12">  
            <h4>Completed</h4>
          </div>
          </div>
        </div>
        </div>
        
        <!-- Content Area Ends--> 
        <div id="popUp"></div>
      </div>
    </section>
  <!-- Content Area -->
<jsp:include page="../masterFooter.jsp" />
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
$("#bgc").click(function(){
	$(this).find('ul').slideToggle();
});

$(document).ready(function() {
	  getDynamicBgcList(null);
$("#bgc-inProgress").show();
  $(".bgonProgressTab a").click(function() {
    $(this).parent().addClass("current").siblings().removeClass();
    var bgcTab = $(this).attr("href");
    $(".bgcTemplateInformation-inner").not(bgcTab).css("display","none");
    $(bgcTab).fadeIn();
  })
})
</script>
<script>
function getDynamicBgcList(filterVal, DashBoardFilterStatus) {
	 var filterAttorneyVal = [];
	 var filterTypeVal = [];
	 var filterStatusVal = [];
	 var filterDurationVal = [];
	 if(filterVal !== null){
		filterAttorneyVal = filterVal[0];
		filterTypeVal = filterVal[1];
		filterStatusVal = filterVal[2];
		filterDurationVal = filterVal[3];
	 }
	 var orgTypeVal =  $("#orgType").val();
    var result = "";
    result = result + '<table id="dynamicBgcListTbl">';
    result = result + '<thead>';
    result = result + '<tr>';
    result = result + '<th>ID</th>';
    result = result + '<th>Name</th>';
    result = result + '<th>Curr. Status</th>';
    result = result + '<th>updated Perc.</th>';
    result = result + '<th>Personal Info</th>';
    result = result + '<th>Educatio/Academic</th>';
    result = result + '<th>Prior Employement</th>';
    result = result + '<th>Health</th>';
    result = result + '<th>Criminal</th>';
    result = result + '<th>Others Info</th>';
    result = result + '<th>Comments</th>';
    result = result + '<th>Updated Date</th>';
    result = result + '<th>Action</th>';
    result = result + '</tr>';
    result = result + '</thead>';

    
    result = result + '</table>';
    $("#dynamicBgcListViewDiv").empty();
    $("#dynamicBgcListViewDiv").append(result);

    var vmsUrl = '${SourceLeadWeb_vmsUrl}';
//    var statusFilter = $("#statusFilter").val();
    var searchValue = $("#bgcListSerach").val();
    searchValue = searchValue ? searchValue : "";

     url = vmsUrl + "/bgc/getBgcCandidageteList?searchValue=" + searchValue+"&filterAttorneyVal="+filterAttorneyVal+"&filterTypeVal="+filterTypeVal+"&filterStatusVal="+filterStatusVal+"&filterDurationVal="+filterDurationVal+"&orgTypeVal="+orgTypeVal;
    dynamicBgcListPagination(url);
}
//var count = 0;
function dynamicBgcListPagination(url) {
	 var colIndex = 0;
    table = $('#dynamicBgcListTbl').DataTable({
        "processing": true,
        "serverSide": true,
        "searching": false,
       // "colReorder": true,
    	// 'dom': 'Rlfrtip',
        // "stateSave": true, // used to save the page state local or server side.
        "ajax": $.fn.dataTable.pipeline({
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            {"data": "uuid"},
            {"data": "fullName"},
            {"data": "currentBgcStatus"},
            {"data": "updatedPercentage"},
            {"data": "personalInfoStatus"},
            {"data": "educationCheckStaus"},
            {"data": "priorEmployersCheckStatus"},
            {"data": "healthCheckStatus"},
            {"data": "crimeCheckStatus"},
            {"data": "OthersInfoStatus"},
            {"data": "comments"},
            {"data": "updatedDate"}
        ],
        "columnDefs": [
            {
                "targets": colIndex++,
                bSortable: false,
                aTargets: [ -1 ],
                "data": "uuid",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.uuid !== null && opt.uuid !== '' && opt.uuid !== 'undefined') {
                   	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.uuid +"'>"+opt.uuid+"</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
                    }
                    return result;
                }
            },
            {
                "targets": colIndex++,
                "data": "fullName",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.fullName !== null && opt.fullName !== '' && opt.fullName !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.fullName+"'><a href=${SourceLeadWeb_vmsUrl}/bgc/bgcCandidateSummary?bgcUUID="+opt.uuid+">" + opt.fullName + "</a></div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                    }
                  
                    return result;
                }
            },
            {
                "targets": colIndex++,
                "data": "currentBgcStatus",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.currentBgcStatus !== null && opt.currentBgcStatus !== '' && opt.currentBgcStatus !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.currentBgcStatus+"'>" + opt.currentBgcStatus + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                    }
                    return result;
                }
            },
            {
                "targets": colIndex++,
                "data": "updatedPercentage",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.updatedPercentage !== null && opt.updatedPercentage !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.updatedPercentage+"'>" + opt.updatedPercentage + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                    }
                    return result;
                }
            },
            
            {
                "targets": colIndex++,
                "data": "personalInfoStatus",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.personalInfoStatus !== null && opt.personalInfoStatus !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.personalInfoStatus+"'>" + opt.personalInfoStatus + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                    }
                    return result;
                }
            },
            {
                "targets": colIndex++,
                "data": "educationCheckStaus",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.educationCheckStaus !== null && opt.educationCheckStaus !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.educationCheckStaus+"'>" + opt.educationCheckStaus + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                    }

                    return result;
                }
            },
            {
                "targets": colIndex++,
                "data": "priorEmployersCheckStatus",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.priorEmployersCheckStatus !== null && opt.priorEmployersCheckStatus !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.priorEmployersCheckStatus+"'>" + opt.priorEmployersCheckStatus  + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                    }
                    return result;
                }
            },
            {
                "targets": colIndex++,
                "data": "healthCheckStatus",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.healthCheckStatus !== null && opt.healthCheckStatus !== '' && opt.healthCheckStatus !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.healthCheckStatus+"'>" + opt.healthCheckStatus + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                    }
                    return result;
                }
            },
            {
                "targets": colIndex++,
                "data": "crimeCheckStatus",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.crimeCheckStatus !== null && opt.crimeCheckStatus !== '' && opt.crimeCheckStatus !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.crimeCheckStatus+"'>" + opt.crimeCheckStatus + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                    }
                    return result;
                }
            },
            {
                "targets": colIndex++,
                "data": "othersInfoStatus",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.othersInfoStatus !== null && opt.othersInfoStatus !== '' && opt.othersInfoStatus !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.othersInfoStatus+"'>" + opt.othersInfoStatus + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                    }
                    return result;
                }
            },
            {
                "targets": colIndex++,
                "data": "comments",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.comments !== null && opt.comments !== '' && opt.comments !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.comments+"'>" + opt.comments + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                    }
                    return result;
                }
            },
            {
                "targets": colIndex++,
                "data": "updatedDate",
                "render": function (data, type, opt, meta) {
                    var result = "";
                    if (opt.updatedDate !== null && opt.updatedDate !== '' && opt.updatedDate !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.updatedDate+"'>" + opt.updatedDate + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                    }
                    return result;
                }
            },
            {
                "targets": colIndex++,
                /*"data": "actions",*/
                "render": function (data, type, opt, meta) {
            
                    var result = "";
                    result = result + '<td><div><a href=${SourceLeadWeb_vmsUrl}/bgc/editBgcCandidate?bgcUUID='+opt.uuid+'><i id="edit' + opt.uuid + '" class="glyphicon glyphicon-pencil"></i></a>';
                    result = result + '<i id="delete' + opt.uuid + '" onclick="deleteBgcCandidateDetailsbyUuiId(\''+opt.uuid+'\');" class="glyphicon glyphicon-trash ml10"></i></div></td>';
                    return result;
                   ;
                }
            }
        ],
        "scrollCollapse": false,
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "aaSorting": [],
        "bLengthChange": true,
        "bDestroy": true,
        "ordering": true,
        "initComplete": function (settings, json) {
        },
        "oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": "",
            "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
        }
    });
   
    $("#dynamicBgcListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");

  /*   $("#immiListSerach").keyup(function (e) {
        if (e.which === 13) {
       	 getDynamicImmigrationList(null);
       	 for (var i=0; i<hideColumns.length; i++) {
       		 table.column(hideColumns[i]).visible(false); // hide the columns after filters also.
       		}
        }
    });
 */
   // $("table").colResizable(); // for resizing columns in tables.
}

function deleteBgcCandidateDetailsbyUuiId(uuid){ // only stauts updated with reason. active 1 , deactive 0.
	 $(".project_inn_mask").remove();
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_bgc_pop' class='remove_user_pop remove_user_pop_a'>";
		/* result = result +"		<  id='reasonForTemplate' name='reasonForSpoc' data-validation='required validate_Space validate_Description validate_length length2-250' placeholder='Reason' onkeypress=';' ></textarea>"; */
		result = result +" <b>Do you want to remove record permanently?</b>";
		result = result + ' <div id="reasonForTemplateDiv"/>';
		uuid="sffer5";
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteBgcDetailsByUuid(\""+uuid+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelBgcDelete();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_bgc_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
function deleteBgcDetailsByUuid(uuidDelete){
	$.ajax({
        url: vmsUrl  + "/bgc/deleteBgcCandidateDetailsbyUuid?uuid="+uuidDelete,
        type: "GET",
        success: function (result) {
        	$("#remove_bgc_pop").fadeOut(200);
    		$(".project_inn_mask").fadeOut(200);
    		if(result == "Success"){
    		  window.location.href = vmsUrl +"/bgc/completedBgcDetails";
    		}else{
    			 window.location.href = vmsUrl +"/error";
    		}
        }
	});
}
function cancelBgcDelete(){
	$("#remove_bgc_pop").fadeOut(200);
	$(".project_inn_mask").fadeOut(200);
}

</script>






