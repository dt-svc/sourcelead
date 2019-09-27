
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/payrollReports.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.14.1/moment.min.js"></script>

<script>
	$(document).ready(function(){
		 loc ="${SourceLeadWeb_dateFormat}";
		  if(loc.match("yyyy-MM-dd")){
			  locDateFormat = 'YYYY-MM-DD';
		  }if(loc.match("dd-MM-yyyy")){
			  locDateFormat = 'DD-MM-YYYY'; 
		  }if(loc.match("MM-dd-yyyy")){
			  locDateFormat = 'MM-DD-YYYY'; 
		  }if(loc.match("yyyy-dd-MM")){
			  locDateFormat = 'YYYY-DD-MM'; 
		  } 
	});
</script>
<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>LCA Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard" title="Home">Home</a>&raquo;<a
					href="${SourceLeadWeb_sourceleadUrl}/reportsDashboard"> Reports Dashboard</a>&raquo;<a href="${SourceLeadWeb_sourceleadUrl}/immigrationReportsList">Immigration Reports</a>&raquo;LCA Report</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    <form:form id="lca_report_form" name="lca_report_form" method="post">
    <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
          
          <div class="form-group">
              <label class="col-md-12 paddingRight">LCA status<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
                  <select multiple data-placeholder="Select Petition Type" class="chosen-select form-control" name="lcaStatus" id="lcaStatus" field-name="LCA Status" data-validation="required">
	                <option ${lcaDTO.lcaStatus == 'Initiated' ? 'selected': ''} value='Initiated'>Initiated</option>
	                <option ${lcaDTO.lcaStatus == 'In progress' ? 'selected': ''} value='In progress'>In progress</option>
	                <option ${lcaDTO.lcaStatus == 'Certified' ? 'selected': ''} value='Certified'>Certified</option>
	                <option ${lcaDTO.lcaStatus == 'Denied' ? 'selected': ''}  value='Denied'>Denied</option>
	                <option ${lcaDTO.lcaStatus == 'Withdrawn' ? 'selected': ''} value='Withdrawn'>Withdrawn</option>
	                <option ${lcaDTO.lcaStatus == 'Voided' ? 'selected': ''} value='Voided'>Voided</option>
	                <option ${lcaDTO.lcaStatus == 'Invalidated' ? 'selected': ''} value='Invalidated'>Invalidated</option>
	                <option ${lcaDTO.lcaStatus == 'Rejected' ? 'selected': ''} value='Rejected'>Rejected</option>
	                <option ${lcaDTO.lcaStatus == 'Expired' ? 'selected': ''} value='Expired'>Expired</option>
               </select>
				</div>
            </div>
            </div>
            
            <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12">Expires on </label>
              <div class="col-md-12">
                <select  data-placeholder="Select Petition Type" class="chosen-select form-control" name="expiredOn" id="daysFilter" onchange="#" >
                    <option value="All" selected >All</option>
                    <option value="7">Expires In 7 Days</option>
                    <option value="15">Expires In 15 Days</option>
                    <option value="30">Expires In 30 Days</option>
                    <option value="60">Expires In 60 Days</option>
                    <option value="90">Expires In 90 Days</option>
                    <option value="120">Expires In 120 Days</option>
                    <option value="180">Expires In 180 Days</option>
                    <option value="360">Expires In 360 Days</option>
                </select>
              </div>
            </div>
            </div>
            
            <div class="col-md-5">
             <div class="form-group">
              <label class="col-md-12 paddingRight">Availability</label>
				<div class="col-md-12">
                 <select class="form-control" name="availability"  id="availability" >
				  <option value="All">All</option>
				  <option value="Available">Available</option>
				  <option value="Not Available">Not Available</option>
                </select>
				</div>
            </div>
            </div>
            
            <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">
				Status (Active/ Inactive)
				</label>
				<div class="col-md-12 mySkillsUl">
                  <select class="form-control" id="status" name="status">
					<option value="All">All</option>
					<option value="Active">Active</option>
					<option value="Inactive">Inactive </option>
                </select>
				</div>
            </div>
            </div>
			</div>
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onclick="runLCAReport();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onclick="resetLcaForm();">
            <input type="button" value="Cancel" onClick="javascript:window.location.href ='${SourceLeadWeb_sourceleadUrl}/reportsDashboard'" class="btn btn-default ml">
          </div>
        </div>
      </div>      
	</div>
   
   <div class="clearfix"></div>
   <div id="popUp"></div>	
   <div class="timesheet_out" id="immigrationReport" style="display: none;">
      <div class="clearfix">
        <div class="positionRelative reportHead clearfix">
  			<ul class="pull-right list-unstyled list-inline">
				<li>
				<a class="overIcon" title="Actions" onclick="newActions();"></a>
      				<div class="newActPop" style="display: block;">
     			    <span>
	           	   		<a href="javascript:void(0)" onclick="lcaExport('Pdf');" title="PDF">Export PDF</a>
						<a href="javascript:void(0)" onclick="lcaExport('xls');" title="Excel">Export Excel</a>            
					</span>
       			</div>
   				</li>
			    <span><input class="immigraTabSerach" id ="lcaSearch" placeholder="Search LCA No,LCA Title,Start date,End date" type="text" title="Search LCA No,LCA Title,SOC Code,SocTitle,Job Title,No. of positions,Start date,End date,LCA Status,Status"></span>
  			</ul>
		</div>
        <div class="require_wrap_table datatable_filter">
        	<div id="lcaReportTableDiv"></div>
        </div>
      </div>
    </div> 
   </form:form>
      <!-- Content Area Ends--> 
  </div>
    
  </section>
  <script type="text/javascript">
     $(function () {
         $('#datetimepicker3').datetimepicker({
             format: 'LT'
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
		})
		$(document).ready(function(){
			$("#cd").click(function(){
				$(".clientDetails").toggleClass('hidden', 500);
			});
   });
</script>
        
<script type="text/javascript">
	$(document).ready(function(e) {
		$("#savevendor").click(function(){
			$('#myform').validate({
			rules:{
				things:{
					required:true
				}
			}
			});
		});
	});
</script>

<script>	
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
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
function runLCAReport(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	     var validationSettings = {
	             errorMessagePosition: 'element',
	             borderColorOnError: '',
	             scrollToTopOnError: true,
	             dateFormat: 'yyyy/mm/dd'
	         };
	     if ($('#lca_report_form').validate(false, validationSettings)) {
	    	 var lcaStatus=$("#lcaStatus").val();
	    	 var expiredOn=$("#expiredOn").val();
	    	 var availability=$("#availability").val();
	    	 var status=$("#status").val();
	    	 
	    	 var searchValue = $("#lcaSearch").val();
	         searchValue = searchValue ? searchValue : "";
	    	 if(lcaStatus == "" || lcaStatus == null || lcaStatus == 'undefined'){
	    		 lcaStatus = "";
	    	 }
	    	 if(expiredOn == "" || expiredOn == null || expiredOn == 'undefined'){
	    		 expiredOn = "";
	    	 }
	    	 if(availability == "" || availability == null || availability == 'undefined'){
	    		 availability = "";
	    	 }
	    	 if(status == "" || status == null || status == 'undefined'){
	    		 status = "";
	    	 }
	    	 var sourceleadUrl = '${sourceleadUrl}';
	         var filterByDays = $("#daysFilter").val();
	         filterByDays = filterByDays ? filterByDays : "";
        	 var url = vmsUrl+ "/runLCAReport?&searchValue="+searchValue+"&lcaStatus="+lcaStatus+"&expiredOn="+expiredOn+"&availability="+availability+"&status="+status+"&filterByDays="+filterByDays;
             $("#immigrationReport").show(); 
             prepareLCAReportList(url);
	     }
}
function removePopUp(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function prepareLCAReportList(url){
 
	    $("#lcaReportTableDiv").empty();
	    var result = "";
		result = result +" <div class='remove_search'>";
		result = result +"</div>";
		result = result + '<table id="lcaReportTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		
		result = result + '<th>LCA Number</th>';
        result = result + '<th>LCA Title</th>';
        result = result + '<th>No. of positions</th>';
        result = result + '<th>Location</th>';
        result = result + '<th>SOC Code</th>';
        result = result + '<th>SOC Occupation Title</th>';
        result = result + '<th>Job Title</th>';
        result = result + '<th>Wage Level</th>';
        result = result + '<th>Prevailing Wage Currency</th>';
        result = result + '<th>Prevailing Wage Rate</th>';
        result = result + '<th>Prevailing Wage rate per</th>';
        result = result + '<th>LCA Start Date</th>';
        result = result + '<th>LCA End Date</th>';
        result = result + '<th>LCA Status</th>';
        result = result + '<th>Status</th>';
        result = result + '<th>Expiration </th>';
        
		result = result + '</tr>';
		result = result + '</thead>	';
        result = result +"</table>";
        $("#lcaReportTableDiv").append(result);
        lcaReportPagination(url);  
}
</script>

<script>
function lcaReportPagination(url){
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	var colIndex = 0;
    $('#lcaReportTable').DataTable({ 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "lcaNo"},
            { "data": "lcaTitle"},
            { "data": "noOfPositions"},
            { "data": "locationOne"},
            { "data": "socCode"},
            { "data": "socOccupationTitle"},
            { "data": "jobTitle"},
            { "data": "wagesLevel"},
            { "data": "privilingWageCurrency"},
            { "data": "prevailingWage" },
            { "data": "privilingWageRateType" },
            { "data": "startDate"},
            { "data": "endDate" },
            { "data": "lcaStatus" },
            { "data": "status" },
            { "data": "expiredOn" } 
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "lcaNo",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
		    	  	 	 if(opt.lcaNo !=null && opt.lcaNo != '' && opt.lcaNo !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.lcaNo+'" ><a href='+vmsUrl+'/immigration/viewLca?lcaUuid='+opt.lcaUuid+'>'+opt.lcaNo+'</a></div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		}  
		              return result; 
	    	  	}
	      },{
            "targets": colIndex++,
            "data": "lcaTitle",
            "render": function ( data, type, opt, meta ) {
            	 var result = "";
            	  if(opt.lcaTitle !=null && opt.lcaTitle != '' && opt.lcaTitle !='undefined'){
	    	  			result = result + '<div class="tblminwidth-75" title="'+opt.lcaTitle+'" >'+opt.lcaTitle+'</div>';
	        		}else{
	        			result = result + '<div class="tblminwidth-75">---</div>';
	        		} 
            	return result; 
            }
          }, {
              "targets": colIndex++,
              "data": "noOfPositions",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
            	  if(opt.noOfPositions !=null && opt.noOfPositions != '' && opt.noOfPositions !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-sm" title="'+opt.noOfPositions+'" >'+opt.noOfPositions+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-sm">---</div>';
	        		}
            	    return result; 
              }
            },{
	    	  	"targets": colIndex++,
	    	  	"data": "locationOne",
	    	  	 "sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
                     if (opt.location !== null && opt.location !== '' && opt.location !== 'undefined') {
                    	    result = result +'<td>';
		   		        	result = result +'<div class="positionRelative">';
			   		        result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.location+'</p>';
			   		        result = result +'<div class="show_Empdetails showjoblist">';
		   		        	result = result +'<i class="fa fa-ellipsis-v"></i>';
	
		   		        	result = result +'<div class="emailvalues emailvaluesNew">';
	 							for(var k=0; k<opt.locationsList.length; k++){
	 								if (k === 3) {
	 							        break;
	 							    }
	 								  if (opt.locationsList[k] !== null && opt.locationsList[k] !== '' && typeof opt.locationsList[k] !== 'undefined') {
	 									console.log( " test " +opt.locationsList[k]);
	 								    result = result +'<p><i class="fa fa-map-marker"></i>'+opt.locationsList[k]+'</p>';
	 								  }
	 								  
			   		    	 	}
		   		        	result = result +'</div>';
		   		        	result = result +'</div>';
		   		        	result = result +'</div>';
		   		        	result = result +'</td>';
                    	 
                     } else {
                         result = result + "<td><div class='tbldata-dotdot-sm'>---</div></td>";
                     }
                     return result;
	    	  	}
	    	  },{
                  "targets": colIndex++,
                  "data": "socCode",
                  "sortable": false,
                  "render": function ( data, type, opt, meta ) {
                	var result = "";
                	 if (opt.socCode !== null && opt.socCode !== '' && opt.socCode !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.socCode+"'>" + opt.socCode + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                    } 
  	    	  		return result; 
                  }
               }, {
                    "targets": colIndex++,
                    "data": "socOccupationTitle",
                    "sortable": false,
                    "render": function ( data, type, opt, meta ) {
                  		var result = "";
                  		 if (opt.socOccupationTitle !== null && opt.socOccupationTitle !== '' && opt.socOccupationTitle !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.socOccupationTitle+"'>" + opt.socOccupationTitle + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                        } 
    	    	  		return result; 
                    }
                }, {
                      "targets": colIndex++,
                      "data": "jobTitle",
                      "sortable": false,
                      "render": function ( data, type, opt, meta ) {
                   		var result = "";
                   		 if(opt.jobTitle !=null && opt.jobTitle != '' && opt.jobTitle !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.jobTitle+'" >'+opt.jobTitle+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		}  
      	    	  		return result; 
                      }
                }, {
                    "targets": colIndex++,
                    "data": "wagesLevel",
                    "sortable": false,
                    "render": function ( data, type, opt, meta ) {
                 		var result = "";
                 		 if(opt.wagesLevel !=null && opt.wagesLevel != '' && opt.wagesLevel !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.wagesLevel+'" >'+opt.wagesLevel+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		}  
    	    	  		return result; 
                    }
                  }, {
                      "targets": colIndex++,
                      "data": "privilingWageCurrency",
                      "sortable": false,
                      "render": function ( data, type, opt, meta ) {
                   		var result = "";
                   		 if(opt.privilingWageCurrency !=null && opt.privilingWageCurrency != '' && opt.privilingWageCurrency !='undefined'){
  		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.privilingWageCurrency+'" >'+opt.privilingWageCurrency+'</div>';
  		        		}else{
  		        			result = result + '     <div class="tblminwidth-75">---</div>';
  		        		}
      	    	  		return result; 
                      }
                    },{
                        "targets": colIndex++,
                        "data": "prevailingWage",
                        "sortable": false,
                        "render": function ( data, type, opt, meta ) {
                     		var result = "";
                     		 if(opt.prevailingWage !=null && opt.prevailingWage != '' && opt.prevailingWage !='undefined'){
    		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.prevailingWage+'" >'+opt.prevailingWage+'</div>';
    		        		}else{
    		        			result = result + '     <div class="tblminwidth-75">---</div>';
    		        		}  
        	    	  		return result; 
                        }
                      },{
                          "targets": colIndex++,
                          "data": "privilingWageRateType",
                          "sortable": false,
                          "render": function ( data, type, opt, meta ) {
                       		var result = "";
                       		 if(opt.privilingWageRateType !=null && opt.privilingWageRateType != '' && opt.privilingWageRateType !='undefined'){
      		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.privilingWageRateType+'" >'+opt.privilingWageRateType+'</div>';
      		        		}else{
      		        			result = result + '     <div class="tblminwidth-75">---</div>';
      		        		}
          	    	  		return result; 
                          }
                        },{
                            "targets": colIndex++,
                            "data": "startDate",
                            "sortable": false,
                            "render": function ( data, type, opt, meta ) {
                         		var result = "";
                         		if(opt.startDateStr !=null && opt.startDateStr != '' && opt.startDateStr !='undefined'){
        		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.startDateStr+'" >'+opt.startDateStr+'</div>';
        		        		}else{
        		        			result = result + '     <div class="tblminwidth-75">---</div>';
        		        		}
            	    	  		return result; 
                            }
                          },{
                              "targets": colIndex++,
                              "data": "endDate",
                              "sortable": false,
                              "render": function ( data, type, opt, meta ) {
                           		var result = "";
                           		 if(opt.endDateStr !=null && opt.endDateStr != '' && opt.endDateStr !='undefined'){
          		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.endDateStr+'" >'+opt.endDateStr+'</div>';
          		        		}else{
          		        			result = result + '     <div class="tblminwidth-75">---</div>';
          		        		}  
              	    	  		return result; 
                              }
                            },{
                                "targets": colIndex++,
                                "data": "lcaStatus",
                                "sortable": false,
                                "render": function ( data, type, opt, meta ) {
                             		var result = "";
                             		 if(opt.lcaStatus !=null && opt.lcaStatus != '' && opt.lcaStatus !='undefined'){
            		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.lcaStatus+'" >'+opt.lcaStatus+'</div>';
            		        		}else{
            		        			result = result + '     <div class="tblminwidth-75">---</div>';
            		        		} 
                	    	  		return result; 
                                }
                              },{
                                  "targets": colIndex++,
                                  "data": "status",
                                  "sortable": false,
                                  "render": function ( data, type, opt, meta ) {
                               		var result = "";
                               		if(opt.status !=null && opt.status != '' && opt.status !='undefined'){
              		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.status+'" >'+opt.status+'</div>';
              		        		}else{
              		        			result = result + '     <div class="tblminwidth-75">---</div>';
              		        		}
                  	    	  		return result; 
                                  }
                                },{
                        "targets": colIndex++,
                        "data": "expiredOn",
                        "sortable": false,
                        "render": function ( data, type, opt, meta ) {
                     		var result = "";
                     		if(opt.expired !=null && opt.expired != '' && opt.expired !='undefined'){
    		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.expired+'" >'+opt.expired+'</div>';
    		        		}else{
    		        			result = result + '     <div class="tblminwidth-75">---</div>';
    		        		} 
        	    	  		return result; 
                        }
                }],
                "scrollCollapse": false,
    	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	    	"aaSorting": [[1,'desc']],
    	    	"bLengthChange": true,
    	    	"bDestroy": true,
    	    	"ordering": true,
    	    	"oLanguage": {
    	     		"sSearch": "",
    	     		 "sZeroRecords": "", "sEmptyTable": "", 
    	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
    	     }
                });
              $("#lcaReportTable").wrap("<div class='overflow_Auto width100percnt'></div>")
              $("#lcaSearch").keyup(function (e) {
                  if (e.which === 13) {
                	  runLCAReport();
                  }
              });
}
</script>
<script>
function resetLcaForm(){
	 $("#lcaStatus").val('');
	 $("#lcaStatus").trigger("chosen:updated"); 
	 $("#daysFilter").val('All');
	 $("#daysFilter").trigger("chosen:updated"); 
	 // By default All should Shows when we Reset Form
	 $("#daysFilter").val('All');
	 $("#availability").val('All');
	 $("#status").val('All');
}
</script>

<!-- =============== Pdf and  Excel Export by prasad k =================== -->
<script>
function lcaExport(type){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 if(type == "Pdf"){
		exportType = "pdf";
	}else{
		exportType = "xls";
	}
	 var validationSettings = {
             errorMessagePosition: 'element',
             borderColorOnError: '',
             scrollToTopOnError: true,
             dateFormat: 'yyyy/mm/dd'
         };
	
	  if ($('#lca_report_form').validate(false, validationSettings)) {
		    
		     var lcaStatus=$("#lcaStatus").val();
	    	 var expiredOn=$("#expiredOn").val();
	    	 var availability=$("#availability").val();
	    	 var status=$("#status").val();
	    	 var searchValue = $("#lcaSearch").val();
	         searchValue = searchValue ? searchValue : "";
	    	 if(lcaStatus == "" || lcaStatus == null || lcaStatus == 'undefined'){
	    		 lcaStatus = "";
	    	 }
	    	 if(expiredOn == "" || expiredOn == null || expiredOn == 'undefined'){
	    		 expiredOn = "";
	    	 }
	    	 if(availability == "" || availability == null || availability == 'undefined'){
	    		 availability = "";
	    	 }
	    	 if(status == "" || status == null || status == 'undefined'){
	    		 status = "";
	    	 }
	    	 var sourceleadUrl = '${sourceleadUrl}';
	         var filterByDays = $("#daysFilter").val();
	         filterByDays = filterByDays ? filterByDays : "";
    	
		    window.location.href = vmsUrl+ "/exportLcaReportPdf?&searchValue="+searchValue+"&lcaStatus="+lcaStatus+"&expiredOn="+expiredOn+"&availability="+availability+"&status="+status+"&filterByDays="+filterByDays+"&exportType="+exportType; 
   }
}
</script>

<jsp:include page="../masterFooter.jsp" />