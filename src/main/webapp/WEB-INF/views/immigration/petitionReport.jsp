
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/payrollReports.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.14.1/moment.min.js"></script>

<script>
$(document).ready(function(){
	 loc ="${SourceLeadWeb_dateFormat}";
	 getAllRelationssByOrgLocation();
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
        <h2>Petition Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard" title="Home">Home </a>&raquo;<a
					href="${SourceLeadWeb_sourceleadUrl}/reportsDashboard"> Reports Dashboard</a>&raquo;<a href="${SourceLeadWeb_sourceleadUrl}/immigrationReportsList">Immigration Reports</a>&raquo;Petition Report</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    <div id="ImmigReportLoading"></div>
    <form:form id="immigration_report_form" name="immigration_report_form" method="post">
    <div class="clearfix minHeightcol5">
        <div class="clearfix row">
          <div class="col-md-5">
           <div class="clearfix mb">
              <label class="clearfix">Petition Type</label>
				<div class="clearfix">
                 <select multiple data-placeholder="Select Petition Type" class="chosen-select form-control" name="petitionType" id="petitionType"  field-name="Petition type" data-validation="required"> 
	                <c:forEach var="visa" items="${petitionTypeList}">
		                     <option value="${visa.name}">${visa.name}</option>
	                </c:forEach>
                </select>
				</div>
            </div>
            
            <div class="clearfix mb">
            <div class="form-group">
              <label class="clearfix">Petition Status</label>
              <div class="clearfix">
                <select multiple data-placeholder="Select Petition Status" class="chosen-select form-control" name="petitionStatus" id="petitionStatus"  field-name="Petition Status" data-validation="validate_Space" data-validation-optional="true">
                <c:forEach var="petitionStatus" items="${petitionStatusList}">
	                     <option value="${petitionStatus.name}">${petitionStatus.name}</option>
                </c:forEach>
                </select>
              </div>
            </div>
            </div>
            </div>
            
            <div class="col-md-5">
            <div class="clearfix mb">
             <div class="form-group">
              <label class="clearfix">Duration</label>
				<div class="clearfix">
                 <select class="form-control sel" id="dateFrequency" name="dateFrequency" onchange="showCustomFields();">
						<option value="Last_7_days">This week</option>
						<option value="current_month">This Month</option>
						<option value="YTD" selected>This year</option>
						<option value="Last_Year">Last year</option>
						<option value="Custom">Custom </option>
                </select>
                 <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-select" style="display: none; width: 346px;">
		          <div class="pull-left">
		            <div class="pull-left mr5" style="width: 140px" id="custFromDateDiv">
		              <input value="" name="freqStartDate" id="custFromDate" field-name="From Date" placeholder="From Date" class="form-control calendar input-sm" type="text">
		            </div>
		            <div class="pull-left mr5" style="width: 140px" id="custToDateDiv">
		              <input value="" name="freqEndDate" id="custToDate" field-name="To Date" placeholder="To Date"  class="form-control calendar input-sm" type="text">
		            </div>
		            <button type="button" class="btn btn-primary btn-sm" onclick="runImmigrationReport();validateCustDates();" ><i class="fa fa-search"></i></button>
		          </div>
		        </div>
                
				</div>
            </div>
            </div>
            
            <div class="clearfix mb">
            <div class="form-group">
              <label class="clearfix">Attorney</label>
				<div class="clearfix mySkillsUl">
               <select multiple data-placeholder="Select Attorney" class="chosen-select form-control" name="attorneyId" id="attorneyId"  data-validation-optional="true">
				</select>
				</div>
            </div>
            </div>
            <div>
            
            </div>
			</div>
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onclick="runImmigrationReport();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onclick="resetImmForm();">
            <input type="button" value="Cancel" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/reportsDashboard'"class="btn btn-default ml">
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
           	   		<a href="javascript:void(0)" onclick="petitionExport123('Pdf');" title="PDF">Export PDF</a>
					<a href="javascript:void(0)" onclick="petitionExport123('xls');" title="Excel">Export Excel</a>            
				</span>
       			</div>
   				</li>
			    <span><input class="immigraTabSerach" id ="immSearch" placeholder="Search ID,Type,Name,Email,Phone" type="text" title="Search ID,Type,Name,Email,Phone"></span>
  			</ul>
		</div>
        <div class="require_wrap_table datatable_filter">
        	<div id="immigrationReportTableDiv"></div>
        </div>
      </div>
    </div> 
     	 </form:form>	
      <!-- Content Area Ends--> 
    </div>
    
  </section>

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
<script>
    function showCustomFields(){
    var frequency = $("#dateFrequency").val();
    if(frequency == "Custom"){
        $("#custom-select").show();
         $('#custFromDate').attr('data-validation',"required");
         $('#custToDate').attr('data-validation',"required");
    }else{
        $("#custom-select").hide();
        $('#custFromDate').removeAttr('data-validation');
        $('#custToDate').removeAttr('data-validation');
    }
    
    var locationDate ='${SourceLeadWeb_dateFormat}'; 
      var finalDateFormatEndDate = '';
      if(locationDate.match("yyyy-MM-dd")){
          finalDateFormatEndDate = 'yy-mm-dd';
      }if(locationDate.match("dd-MM-yyyy")){
          finalDateFormatEndDate = 'dd-mm-yy'; 
      }if(locationDate.match("MM-dd-yyyy")){
          finalDateFormatEndDate = 'mm-dd-yy'; 
      }if(locationDate.match("yyyy-dd-MM")){
          finalDateFormatEndDate = 'yy-dd-mm'; 
      }
      
      $('#custFromDate').datepicker({
	      dateFormat: finalDateFormatEndDate,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '+1d');
	            dt.setDate(dt.getDate() + 1);
	            $("#custToDate").datepicker("option", "minDate", dt);
	        }
	  });
      
      $('#custToDate').datepicker({
	      dateFormat: finalDateFormatEndDate,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() + 1);
	            $("#custFromDate").datepicker("option", "maxDate", dt);
	        }
	  });
  }

	function removeStartValid(){
	    $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
	}
	function removeEndValid(){
	    $('#custToDateDiv').find('p.jquery_form_error_message').remove();
	}

   function validateCustDates(){
    var frequency = $("#dateFrequency").val();
    var fromDate = $("#custFromDate").val();
    var toDate = $("#custToDate").val();
   
    if(frequency == "Custom"){
        if(fromDate == ''){
            $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
            $("input#custFromDate").after( "<p class='jquery_form_error_message'>FromDate is required.</span>");
            $("input#custFromDate").empty();
        }
        if(toDate == ''){
            $('#custToDateDiv').find('p.jquery_form_error_message').remove();
            $("input#custToDate").after( "<p class='jquery_form_error_message'>ToDate is required.</span>");
            $("input#custToDate").val('');
        }
        if((fromDate != '' && fromDate != 'undefined' && fromDate != null) && (toDate != '' && toDate != 'undefined' && toDate != null)){
            $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
            $('#custToDateDiv').find('p.jquery_form_error_message').remove();
            refreshWithNewFreq();
        }
    }else{
        $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
        $('#custToDateDiv').find('p.jquery_form_error_message').remove();
        refreshWithNewFreq();    
    } 
}
</script>

<script type="text/javascript">
function getAllRelationssByOrgLocation(){
	 console.log(" before function calling ---->");
	var type = "Vendor";
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getAllRelationsByOrgLocationAndType?"+tokenParamName+"="+tokenValue+"&type="+type, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#attorneyId').append('<option value="">--Select Attorney --</option>');
		        	$('#attorneyId').empty();
		            for(var i=0;i<json.length;i++){
		            var opt = json[i];
		            $('#attorneyId').append('<option attrval='+opt.immigrationRelatedId+' value="'+opt.orgName+'" >'+opt.orgName+'</option>');
		            }
		            $('#attorneyId').trigger("chosen:updated");
		        }
		 });
}
</script>

<script>
function exportPetitionReportPdf(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var searchValue = $("#payrollRegisterSearch").val();
	var payGroup = $('#payGroup').val();
	var department = $('#payrollDepartment').val();
	var payRollType = $('#payRollType').val();
	var freqStartDate = $('#custFromDate').val();
	var freqEndDate = $('#custToDate').val();
    window.location.href = vmsUrl+"/exportPetitionReportPdf?&payGroup="+payGroup+"&department="+department+"&payRollType="+payRollType+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&searchValue="+searchValue;
}
</script>

<script type="text/javascript">
function runImmigrationReport(){
	     var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	     var validationSettings = {
	             errorMessagePosition: 'element',
	             borderColorOnError: '',
	             scrollToTopOnError: true,
	             dateFormat: 'yyyy/mm/dd'
	         };
	     if ($('#immigration_report_form').validate(false, validationSettings)) {
	    	 
	    	 var petitionType=$("#petitionType").val();
	    	 var petitionStatus=$("#petitionStatus").val();
	    	 var dateFrequency=$("#dateFrequency").val();
	    	 var attorneyId=$("#attorneyId").val();
	    	 var freqStartDate = null;
	    	var freqEndDate = null;
	    	 var searchValue = $("#immSearch").val();
	         searchValue = searchValue ? searchValue : "";
	         
	    	 if(petitionStatus == "" || petitionStatus == null || petitionStatus == 'undefined'){
	    		 petitionStatus = "";
	    	 }
	    	 if(attorneyId == "" || attorneyId == null || attorneyId == 'undefined'){
	    		 attorneyId = 0;
	    	 }
	    	 if(dateFrequency == "" || dateFrequency == null || dateFrequency == 'undefined'){
	    		 dateFrequency = "";
	    	 }
	    	 
	    	 if(dateFrequency == 'Custom'){
	    		 freqStartDate = $("#custFromDate").val();
	 			freqEndDate = $("#custToDate").val();
	 			$("#errStartDate").find('p.jquery_form_error_message').remove();
	 			$("#errEndDate").find('p.jquery_form_error_message').remove();
	 			if(freqStartDate == null || freqStartDate == '' || freqStartDate == 'undefined'){
	 				$("input#custFromDateDiv") .after( "<p class='jquery_form_error_message'>Start Date is Required</span>");
	 				returnStat =  false;
	 			}
	 			if(freqEndDate == null || freqEndDate == '' || freqEndDate == 'undefined'){
	 				$("input#custToDateDiv") .after( "<p class='jquery_form_error_message'>End Date is Required</span>");
	 				returnStat =  false;
	 			}
	 		}
	    	 
        	var url = vmsUrl+ "/runImmigrationReport?&searchValue="+searchValue+"&petitionType="+petitionType+"&petitionStatus="+petitionStatus+"&dateFrequency="+dateFrequency+"&attorneyId="+attorneyId+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate;
            $("#immigrationReport").show(); 
            prepareImmigrationReportList(url);
	     }
    	
  }
	function removePopUp(){
		$("#popUp").empty();
		$(".project_inn_mask").fadeOut(200);
	}
</script>

<script>
function prepareImmigrationReportList(url){
	 $("#immigrationReportTableDiv").empty();
	  var result = "";
		result = result +" <div class='remove_search'>";
		result = result +"</div>";
		result = result + '<table id="immigReportTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>ID</th>';
        result = result + '<th>Name</th>';
        result = result + '<th>Email</th>';
        result = result + '<th>Phone</th>';
        result = result + '<th>Type</th>';
        result = result + '<th>Country</th>';
        result = result + '<th>Open Date</th>';
        result = result + '<th>Attorney(Vendor Name)</th>';
        result = result + '<th>LCA Start Date</th>';
        result = result + '<th>LCA End Date</th>';
        result = result + '<th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
        result = result +"</table>";
        $("#immigrationReportTableDiv").append(result);
        immigrationReportPagination(url);  
}
</script>

<script>
function immigrationReportPagination(url){
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	var colIndex = 0;
    $('#immigReportTable').DataTable({ 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "ID" },
            { "data": "name"},
            { "data": "email" },
            { "data": "phone" },
            { "data": "type" },
            { "data": "country" },
            { "data": "onDate"},
            { "data": "attorneyName" },
            { "data": "lcaStartDate" },
            { "data": "lcaEndDate" },
            { "data": "status" }
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "ID",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
		    	  	 	if(opt.generatedId !=null && opt.generatedId != '' && opt.generatedId !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.generatedId+'" ><a href='+vmsUrl+'/immigration/viewImmigration?immigrationUUID='+opt.immigrationUUID+'&candidateUUID='+opt.candidateUUID+'&immiTemplateUUID='+opt.immiTemplateUUID+'&myImmigrationPage=N>'+opt.generatedId+'</a></div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		} 
		              return result; 
	    	  	}
	      },{
            "targets": colIndex++,
            "data": "name",
            "render": function ( data, type, opt, meta ) {
            	 var result = "";
            	 if(opt.name !=null && opt.name != '' && opt.name !='undefined'){
	    	  			result = result + '<div class="tblminwidth-75" title="'+opt.name+'" >'+opt.name+'</div>';
	        		}else{
	        			result = result + '<div class="tblminwidth-75">---</div>';
	        		} 
            	return result; 
            }
          }, {
              "targets": colIndex++,
              "data": "email",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
            	   if(opt.email !=null && opt.email != '' && opt.email !='undefined'){
	    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.email+'" >'+opt.email+'</div>';
	        		}else{
	        			result = result + '     <div class="tblminwidth-75">---</div>';
	        		} 
            	    return result; 
              }
            },{
	    	  	"targets": colIndex++,
	    	  	"data": "phone",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		 if(opt.phoneNumber !=null && opt.phoneNumber != '' && opt.phoneNumber !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.phoneNumber+'" >'+opt.phoneNumber+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		} 
  	                return result; 
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "petitionType",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		 if(opt.petitionType !=null && opt.petitionType != '' && opt.petitionType !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.petitionType+'" >'+opt.petitionType+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		} 
	    	  		return result; 
	    	  	}
	    	  }, {
		    	  	"targets": colIndex++,
		    	  	"data": "country",
		    	  	"sortable": false,
		    	  	"render": function(data, type, opt, meta) {
		    	  		var result = "";
		    	  		if(opt.country !=null && opt.country != '' && opt.country !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.country+'" >'+opt.country+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		} 
		    	  		return result; 
		    	  	}
		    	  },{
                  "targets": colIndex++,
                  "data": "onDate",
                  "sortable": false,
                  "render": function ( data, type, opt, meta ) {
                	var result = "";
                	if (opt.openDateStr !== null && opt.openDateStr !== '' && opt.openDateStr !== 'undefined') {
                        result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.openDateStr+"'>" + opt.openDateStr + "</div></td>";
                    } else {
                        result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                    }
  	    	  		return result; 
                  }
               }, {
                    "targets": colIndex++,
                    "data": "attorneyName",
                    "sortable": false,
                    "render": function ( data, type, opt, meta ) {
                  		var result = "";
                  		if (opt.orgName !== null && opt.orgName !== '' && opt.orgName !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.orgName+"'>" + opt.orgName + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
                        }
    	    	  		return result; 
                    }
                }, {
                      "targets": colIndex++,
                      "data": "lcaStartDate",
                      "sortable": false,
                      "render": function ( data, type, opt, meta ) {
                   		var result = "";
                   		if(opt.lcaStartDateStr !=null && opt.lcaStartDateStr != '' && opt.lcaStartDateStr !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.lcaStartDateStr+'" >'+opt.lcaStartDateStr+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		} 
      	    	  		return result; 
                      }
                }, {
                    "targets": colIndex++,
                    "data": "lcaEndDate",
                    "sortable": false,
                    "render": function ( data, type, opt, meta ) {
                 		var result = "";
                 		if(opt.lcaEndDateStr !=null && opt.lcaEndDateStr != '' && opt.lcaEndDateStr !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.lcaEndDateStr+'" >'+opt.lcaEndDateStr+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		} 
    	    	  		return result; 
                    }
                  }, {
                        "targets": colIndex++,
                        "data": "status",
                        "sortable": false,
                        "render": function ( data, type, opt, meta ) {
                     		var result = "";
                     		if(opt.currUscisStatus !=null && opt.currUscisStatus != '' && opt.currUscisStatus !='undefined'){
    		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.currUscisStatus+'" >'+opt.currUscisStatus+'</div>';
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
              $("#immigReportTable").wrap("<div class='overflow_Auto width100percnt'></div>")
              $("#immSearch").keyup(function (e) {
                  if (e.which === 13) {
                	  runImmigrationReport();
                     
                  }
              });
}
</script>
<script>
function resetImmForm(){
	 $("#petitionType").val('');
	 $("#petitionType").trigger("chosen:updated"); 
	 $("#petitionStatus").val('');
	 $("#petitionStatus").trigger("chosen:updated"); 
	 $("#attorneyId").val('');
	 $("#attorneyId").trigger("chosen:updated"); 
	 $("#dateFrequency").val('YTD');
	 $('#custFromDateDiv').find('p.jquery_form_error_message').remove(); 
	 $("#custFromDate").val('');
	 $("#custToDateDiv").find('p.jquery_form_error_message').remove();
	 $("#custToDate").val('');
	 $("#custom-select").hide();
}
</script>
<!-- =============== Pdf and  Excel Export by prasad k =================== -->
<script>
	function petitionExport123(type){
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
		
		  if ($('#immigration_report_form').validate(false, validationSettings)) {
	    	 var petitionType=$("#petitionType").val();
	    	 var petitionStatus=$("#petitionStatus").val();
	    	 var dateFrequency=$("#dateFrequency").val();
	    	 var attorneyId=$("#attorneyId").val();
	    	 var searchValue = $("#immSearch").val();
	         searchValue = searchValue ? searchValue : "";
	    	 if(petitionStatus == "" || petitionStatus == null || petitionStatus == 'undefined'){
	    		 petitionStatus = "";
	    	 }
	    	 if(attorneyId == "" || attorneyId == null || attorneyId == 'undefined'){
	    		 attorneyId = 0;
	    	 }
	    	 if(dateFrequency == "" || dateFrequency == null || dateFrequency == 'undefined'){
	    		 dateFrequency = "";
	    	 }
	    	
			window.location.href = vmsUrl+ "/exportPetitionReportPdf?&searchValue="+searchValue+"&petitionType="+petitionType+"&petitionStatus="+petitionStatus+"&dateFrequency="+dateFrequency+"&attorneyId="+attorneyId+"&exportType="+exportType; 
	   }
	}
</script>
<jsp:include page="../masterFooter.jsp" />