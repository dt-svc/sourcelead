var bgc = new bgcCandidate();

var table = "";
var orgType ="";
var filterAttorneyVal = [];
var filterTypeVal = [];
var filterStatusVal = [];
var filterDurationVal = [];

function bgcCandidate(){
	this.getBgcCandidateEducationSummary= function(candidateId){
			$.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/getBgcCandidateEducationSummary?candidateId="+candidateId,
				success : function(dataList) {
					var result = "";	
					if(dataList != null && dataList.length > 0){
						$("#educationLoading").removeAttr("style");
						
						for(var i=0; i < dataList.length; i++){
						result = result + "<h2>Education "+(i+1)+"</h2>";
						result = result + "<ul>";
						result = result + "<li class='clearfix'><span>Higher Education</span><p>: "+(dataList[i].name == null ? "":dataList[i].name)+"</p></li>";
						result = result + "<li class='clearfix'><span>Institute Name</span><p>: "+(dataList[i].institute == null ? "":dataList[i].institute)+"</p></li>";
						result = result + "<li class='clearfix'><span>Academic Year</span><p>: "+(dataList[i].academicYear == null ? "":dataList[i].academicYear)+"</p></li>";
						result = result + "<li class='clearfix'><span>Grade/Percentage/Score</span><p>: "+(dataList[i].grade == null ? "":dataList[i].grade)+"</p></li>";
						/*result = result + "<li class='clearfix'><span>University / Authority</span><p>: --</p></li>";
						result = result + "<li class='clearfix'><span>Attach document</span><p>: "+(dataList[i].educationFileName == null ? "--":dataList[i].educationFileName)+"" +
								"<a download href = "+(dataList[i].educationFileUrl == null ? "--":dataList[i].educationFileUrl)+">  <i class='fa fa-download ml'></i></a></p></li>";
						/*result = result + "<li class='clearfix'><span>Comments</span><p>: --</p></li>";*/
						result = result + "</ul>";
						}
					}else{
						$("#educationLoading").removeAttr("style");
						result = result + "<div class='dataTables_empty'></div>";
					}
					$("#educationList").empty().append(result);
				},
			});
	}
	
	this.getBgcCandidateEmploymentSummary = function(candidateId){
		$.ajax({
			type : "GET",
			url : vmsUrl + "/bgc/getBgcCandidateEmploymentSummary?candidateId="+candidateId,
			success : function(dataList) {
				var result = "";
				if(dataList != null && dataList.length > 0){
					$("#employmentLoading").removeAttr("style");	
					for(var i=0; i < dataList.length; i++){
						result = result + "<h2>Previous Employer "+(i+1)+"</h2>";
						result = result + "<ul>";
						result = result + "<li class='clearfix'><span>Previous Employer Name</span><p>: "+(dataList[i].name == null ? "":dataList[i].name)+"</p></li>";
						result = result + "<li class='clearfix'><span>Designation</span><p>: "+(dataList[i].designation == null ? "":dataList[i].designation)+"</p></li>";
						result = result + "<li class='clearfix'><span>Start Year</span><p>: "+(dataList[i].startYear == null ? "":dataList[i].startYear)+"</p></li>";
						result = result + "<li class='clearfix'><span>End Year</span><p>: "+(dataList[i].endYear == null ? "":dataList[i].endYear)+"</p></li>";
						result = result + "<li class='clearfix'><span>Period</span><p>: "+(dataList[i].period == null ? "" : dataList[i].period)+" Year(s)</p></li>";
						/*result = result + "<li class='clearfix'><span>Attach document</span><p>: "+(dataList[i].employerFileName == null ? "--":dataList[i].employerFileName)+"" +
								"<a download href = "+(dataList[i].employerFileUrl == null ? "--":dataList[i].employerFileUrl)+"><i class='fa fa-download ml'></i></a></p></li>";//
*/						/*result = result + "<li class='clearfix'><span>Conduct</span><p>: --</p></li>";*/
						result = result + "</ul>";
					}
				}else{
					$("#employmentLoading").removeAttr("style");
					result = result + "<div class='dataTables_empty'></div>";
				}
				$("#employmentList").empty().append(result);
			},
		});
	}
	
	this.editBgcCandidate = function(){
		$("#bgcId").val(uuid);
		$("#bgcGeneratedId").val(bgcGeneratedId);
		$("#bgcName").val(name);
		$("#currentBgcId option:contains(" + currentBgcStatus + ")").attr('selected', 'selected');
		$("#educationId option:contains(" + educationCheckStaus + ")").attr('selected', 'selected');
		$("#employmentId option:contains(" + priorEmployersCheckStatus + ")").attr('selected', 'selected');
		$("#personalInfoId option:contains(" + personalInfoStatus + ")").attr('selected', 'selected');
		$("#healthId option:contains(" + healthCheckStatus + ")").attr('selected', 'selected');
		$("#criminalRecordId option:contains(" + crimeCheckStatus + ")").attr('selected', 'selected');
		$("#commentsId").html(comments);
	}
	

		this.saveEditBgcCandidate = function() {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#editBgcCandidateForm').validate(false, validationSettings)) {
			var formData = getFormData("editBgcCandidateForm");
			 $("#saveBgcCandidate").attr("disabled", "disabled");
	          $("#saveBgcCandidate").addClass('button').val('Processing..'); 
			$.ajax({
				type : "POST",
				data : formData,
				url : vmsUrl + "/bgc/saveEditBgcCandidate",
				success : function(result) {
					if (result.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						if (result.object === "DataMissing") {
							$("#saveBgcCandidate").removeAttr("disabled");
							$("#saveBgcCandidate").removeAttr('button').val('Save');
							showError("Invalid data");
						} else {
							location.replace(vmsUrl+"/bgc/bgcCandidates");	
						}
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}

				}
			});
		}
	}
		
		this.getBgcCandidateLegalAndCriminalSummary = function(uuid){
			$.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/getBgcCandidateLegalAndCriminalSummary?uuid="+uuid,
				success : function(data) {
					var result = "";	
					if(data != null){
						$("#legalAndCriminalLoading").removeAttr("style");
						result = result + "<h2>Legal and Criminial Check</h2>";
						result = result + "<ul>";
						/*result = result + "<li class='clearfix'><span>Financial Check</span><p>: --</p></li>";*/
						result = result + "<li class='clearfix'><span>Criminal Check</span><p>: "+(data.crimeCheckStatus==null? "--":data.crimeCheckStatus)+"</p></li>";
						/*result = result + "<li class='clearfix'><span>Bank and Property Check</span><p>: --</p></li>";
						result = result + "<li class='clearfix'><span>Salary Check</span><p>: --</p></li>";
						result = result + "	<li class='clearfix'><span>Attach document</span><p>: --</p></li>";*/
						result = result + "</ul>";
					}else{
						$("#legalAndCriminalLoading").removeAttr("style");
						result = result + "<div class='dataTables_empty'></div>";
					}
					$("#legalAndCriminalList").empty().append(result);
				},
			});
			
		}
	
		this.getBgcCandidateSummary = function(uuid){
			$.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/getBgcCandidateSummary?uuid="+uuid,
				success : function(data) {
					var result = "";	
					if(data != null){
						$("#detailsLoading").removeAttr("style");
						result = result + "<h2>Personal / Identification</h2>";
						result = result + "<ul>";
						result = result + "<li class='clearfix'><span>Aadhar Card</span><p>: "+(data.aadharNumber == null ? "--" : data.aadharNumber)+"</p></li>";
						result = result + "<li class='clearfix'><span>PAN Card</span><p>: "+(data.panNumber == null ? "--" : data.panNumber)+"</p></li>";
						result = result + "<li class='clearfix'><span>Address</span><p>: "+(data.address == null ? "--" : data.address)+"</p></li>";
						/*result = result + "<li class='clearfix'><span>Family</span><p>: --</p></li>";
						result = result + "<li class='clearfix'><span>Attach document</span><p>: <a>--<i class='fa fa-download ml'></i></a></p></li>";
						result = result + "<li class='clearfix'><span>Comments</span><p>: "+(data.comments == null ? "--" : data.comments)+"</p></li>";*/
						result = result + "</ul>";
					}else{
						$("#detailsLoading").removeAttr("style");
						result = result + "<div class='dataTables_empty'></div>";
					}
					$("#details").empty().append(result);
				},
			});
		}
		
		this.getDynamicBgcList = function(filterVal, DashBoardFilterStatus) {
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
		    result = result + '<th>Status</th>';
		    result = result + '<th>ID</th>';
		    result = result + '<th>Name</th>';
		    result = result + '<th>updated Perc.</th>';
		    result = result + '<th>Personal Info</th>';
		    result = result + '<th>Educatio/Academic</th>';
		    result = result + '<th>Prior Employement</th>';
		    result = result + '<th>Health</th>';
		    result = result + '<th>Criminal</th>';
		    result = result + '<th>Others Info</th>';
		    result = result + '<th>Comments</th>';
		    result = result + '<th>Client/Vendor</th>';
		    result = result + '<th>Updated Date</th>';
		    result = result + '<th>Action</th>';
		    result = result + '</tr>';
		    result = result + '</thead>';

		    
		    result = result + '</table>';
		    $("#dynamicBgcListViewDiv").empty();
		    $("#dynamicBgcListViewDiv").append(result);

//		    var statusFilter = $("#statusFilter").val();
		    var searchValue = $("#bgcListSearch").val();
		    searchValue = searchValue ? searchValue : "";

		     url = vmsUrl + "/bgc/getBgcCandidageteList?searchValue=" + searchValue+"&filterAttorneyVal="+filterAttorneyVal+"&filterTypeVal="+filterTypeVal+"&filterStatusVal="+filterStatusVal+"&filterDurationVal="+filterDurationVal+"&orgTypeVal="+orgTypeVal;
		    bgc.dynamicBgcListPagination(url);
		}
		//var count = 0;
		this.dynamicBgcListPagination = function(url) {
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
		        	{"data": "currentBgcStatus"},
		            {"data": "generatedId"},
		            {"data": "fullName"},
		          
		            {"data": "updatedPercentage"},
		            {"data": "personalInfoStatus"},
		            {"data": "educationCheckStaus"},
		            {"data": "priorEmployersCheckStatus"},
		            {"data": "healthCheckStatus"},
		            {"data": "crimeCheckStatus"},
		            {"data": "OthersInfoStatus"},
		            {"data":"clinetOrVendorName"},
		            {"data": "comments"},
		            {"data": "updatedDate"}
		        ],
		        "columnDefs": [
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
		                bSortable: false,
		                aTargets: [ -1 ],
		                "data": "generatedId",
		                "render": function (data, type, opt, meta) {
		                    var result = "";
		                    if (opt.generatedId !== null && opt.generatedId !== '' && opt.generatedId !== 'undefined') {
		                   	 result = result + "<td><div class='tbldata-dotdot-sm' title = '"+opt.generatedId +"'>"+opt.generatedId+"</div></td>";
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
		                    	 if(roleActionsArray.indexOf("ROLE_VIEW_BACKGROUND_DETAILS") > -1){ 
		                    	result = result + "<td><div class='tbldata-dotdot-100' title='"+opt.fullName+"'><a href="+vmsUrl+"/bgc/bgcCandidateSummary?bgcUUID="+opt.uuid+">" + opt.fullName + "</a></div></td>";
		                    	 }
		                    } else {
		                        result = result + "<td><div class='tbldata-dotdot-100'>---</div></td>";
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
		                        result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.updatedPercentage+"'>" + opt.updatedPercentage + "%</div></td>";
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
		                "data": "clinetOrVendorName",
		                "render": function (data, type, opt, meta) {
		                    var result = "";
		                    if (opt.clinetOrVendorName !== null && opt.clinetOrVendorName !== '' && opt.clinetOrVendorName !== 'undefined') {
		                        result = result + "<td><div class='tbldata-dotdot-vsm' title='"+opt.clinetOrVendorName+"'>" + opt.clinetOrVendorName + "</div></td>";
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
		                    if(orgType == ''){
		                       orgType= opt.orgType;
		                    }
		                 if(opt.orgType == "Vendor"){
		                	 if(roleActionsArray.indexOf("ROLE_EDIT_BACKGROUND_DETAILS") > -1){ 
		                       result = result + '<td><div><a href='+vmsUrl+'/bgc/editBgcCandidate?bgcUUID='+opt.uuid+'><i id="edit' + opt.uuid + '" class="glyphicon glyphicon-pencil"></i></a>';
		                	 }
		                	 if(roleActionsArray.indexOf("ROLE_DELETE_BACKGROUND_DETAILS") > -1){ 
		                        result = result + '<i id="delete' + opt.uuid + '" onclick="bgc.deleteBgcCandidateDetailsbyUuiId(\''+opt.uuid+'\',\''+opt.fullName+'\');" class="glyphicon glyphicon-trash ml10"></i></div></td>';
		                	 }
		                 }
		                    return result;
		                   
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
		        },
		        "initComplete": function( settings ) { // this call back function is added to remove last column "action" if org type is client.
		        	 if(orgType == "Client" && $('#dynamicBgcListTbl th:last').text() == "Action"){
		                 $('#dynamicBgcListTbl tr').find('th:last, td:last').hide();
		            }
		 		}
		    });
		   
		    $("#dynamicBgcListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		    
		    $("#bgcListSearch").keyup(function (e) {
		        if (e.which == 13) {
		        	bgc.getDynamicBgcList(null);
		       
		        }
		    });

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
		
		this.deleteBgcRecord = function(elem,arg1,arg2,value){
	    	$(".project_inn_mask").remove();
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='remove_Bgc_popup' class='remove_user_pop remove_user_pop_a'>";
			result = result +" <b>Do you want to remove \'"+decodeURIComponent(value)+"\' record permanently?</b>";
			result = result + ' <div id="reasonForTemplateDiv"/>';
			result = result +"	<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='bgc.deleteBgcRecordById("+elem+",\""+arg1+"\",\""+arg2+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick=bgc.deleteBgcPopupDiv();></b>";
			result = result +"	</div>";
			$("#deletepopUp").append(result);
			$("#remove_Bgc_popup").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	   
			}
		
		this.deleteBgcPopupDiv = function (){
			$("#remove_Bgc_popup").fadeOut(200);
			$(".project_inn_mask").fadeOut(200);
			$("#deletepopUp").empty();
		}
		this.deleteBgcRecordById=function(elem, arg1, arg2){	
			if(typeof elem ==='function') {
				if(typeof arg2 == 'undefiend'){
					elem(arg1);
				}
				else{
				elem(arg1, arg2);
			}
			}
			else{
				alert("Not a valid Function call.");
			}
		}
		
		this.deleteBgcStatusById = function (id) {
			$.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/deleteBgcStatusById?id="+id,
				success : function(result) {
					if (result.response == "AWKWARD") {
						location.href = "/errorPage";
					} else if (result.response == "SUCCESS") {
						if (result.object == "DataMissing") {
							showError("Invalid data");
						} else {
							bgc.deleteBgcPopupDiv();
							location.replace(vmsUrl+"/bgc/bgcOrgStatusList");
							showSuccessMsg("Record Deleted Successfully!");
						}
					} else {
						showError("Operation failed, please try again");
					}

				}
			});
		}
		this.deleteBgcComponentById = function (id) {
			$.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/deleteBgcComponentById?id="+id,
				success : function(result) {
					if (result.response == "AWKWARD") {
						location.href = "/errorPage";
					} else if (result.response == "SUCCESS") {
						if (result.object == "DataMissing") {
							showError("Invalid data");
						} else {
							bgc.deleteBgcPopupDiv();
							location.replace(vmsUrl+"/bgc/bgcOrgComponentList");
							showSuccessMsg("Record Deleted Successfully!");
						}
					} else {
						showError("Operation failed, please try again");
					}

				}
			});
		}
		this.deleteBgcCandidateDetailsbyUuiId = function(uuid, value){ // only stauts updated with reason. active 1 , deactive 0.
			 $(".project_inn_mask").remove();
				var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='remove_bgc_pop' class='remove_user_pop remove_user_pop_a'>";
				/* result = result +"		<  id='reasonForTemplate' name='reasonForSpoc' data-validation='required validate_Space validate_Description validate_length length2-250' placeholder='Reason' onkeypress=';' ></textarea>"; */
				result = result +" <b>Do you want to remove \'"+value+"\' record permanently?</b>";
				result = result + ' <div id="reasonForTemplateDiv"/>';
				result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='bgc.deleteBgcDetailsByUuid(\""+uuid+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='bgc.cancelBgcDelete();'></b>";
				result = result +"	</div>";
				$("#popUp").append(result);
				$("#remove_bgc_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
			}
		
		this.deleteBgcDetailsByUuid = function(uuidDelete){
			$.ajax({
		        url: vmsUrl  + "/bgc/deleteBgcCandidateDetailsbyUuid?uuid="+uuidDelete,
		        type: "GET",
		        success: function (result) {
		        	$("#remove_bgc_pop").fadeOut(200);
		    		$(".project_inn_mask").fadeOut(200);
		    		if(result == "Success"){
		    		  window.location.href = vmsUrl +"/bgc/bgcCandidates";
		    		}else{
		    			 window.location.href = vmsUrl +"/error";
		    		}
		        }
			});
		}
		this.cancelBgcDelete = function(){
			$("#remove_bgc_pop").fadeOut(200);
			$(".project_inn_mask").fadeOut(200);
		}


		this.newActions = function(){
			$(".newActPop").slideToggle(200);
			$(".action-pop").slideUp(200);
		}

		this.exportBgcToPdf = function(filterVal){
			 if(filterVal !== null){
				filterAttorneyVal = filterVal[0];
				filterTypeVal = filterVal[1];
				filterStatusVal = filterVal[2];
				filterDurationVal = filterVal[3];
			 }
			 var orgTypeVal =  $("#orgType").val();
			 var searchValue = $("#bgcListSearch").val();
			    searchValue = searchValue ? searchValue : "";
			
			  if (!table.data().count()) {
				  bgc.popupDisplay();
			  }else{
				  window.location.href = vmsUrl + "/bgc/exportBgcToPdfOrExcel?searchValue=" + searchValue+"&filterAttorneyVal="+filterAttorneyVal+"&filterTypeVal="+filterTypeVal+"&filterStatusVal="+filterStatusVal+"&filterDurationVal="+filterDurationVal+"&orgTypeVal="+orgTypeVal+"&exportType=pdf";
			  }
		}

		this.exportBgcToExcel = function(filterVal){
			 if(filterVal !== null){
				filterAttorneyVal = filterVal[0];
				filterTypeVal = filterVal[1];
				filterStatusVal = filterVal[2];
				filterDurationVal = filterVal[3];
			 }
			 var orgTypeVal =  $("#orgType").val();
			 var searchValue = $("#bgcListSearch").val();
			    searchValue = searchValue ? searchValue : "";
			
			 if (!table.data().count()) {
				 bgc.popupDisplay();
			 }else{
				 window.location.href = vmsUrl + "/bgc/exportBgcToPdfOrExcel?searchValue=" + searchValue+"&filterAttorneyVal="+filterAttorneyVal+"&filterTypeVal="+filterTypeVal+"&filterStatusVal="+filterStatusVal+"&filterDurationVal="+filterDurationVal+"&orgTypeVal="+orgTypeVal+"&exportType=excel";
			 }
		}

		this.popupDisplay = function(){
			$(".project_inn_mask").remove();
			var result = "";
			result = result +" <div class='project_inn_mask'></div>";
			result = result +" <div id='remove_Bgc_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +" <div class='modal-body'><p id='popupMsg'></p></div>";
			result = result +" <div class='clearfix'><input class='u_can_color width100percnt' type='button'  value='Ok' onClick='bgc.cancelBgcPopup();'></div>";
			result = result +" </div>";
			$("#emptyPopup").empty().append(result);
			$("#popupMsg").empty().append("No Records To Export in Background Verification.");
			$("#remove_Bgc_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}

		this.cancelBgcPopup = function(){
			$("#remove_Bgc_pop").fadeOut(200);
			$(".project_inn_mask").fadeOut(200);
		}
		
}