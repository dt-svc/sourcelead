var bgc = new bgcCandidate();

function bgcCandidate(){
	this.getBgcCandidateEducationSummary= function(uuid){
			$.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/getBgcCandidateEducationSummary?uuid="+uuid,
				success : function(dataList) {
					if(dataList != null && dataList.length > 0){
						$("#educationLoading").removeAttr("style");
						var result = "";
						for(var i=0; i < dataList.length; i++){
						result = result + "<h2>Education "+(i+1)+"</h2>";
						result = result + "<ul>";
						result = result + "<li class='clearfix'><span>Higher Education</span><p>: "+(dataList[i].name == null ? "--":dataList[i].name)+"</p></li>";
						result = result + "<li class='clearfix'><span>Institute Name</span><p>: "+(dataList[i].institute == null ? "--":dataList[i].institute)+"</p></li>";
						result = result + "<li class='clearfix'><span>Academic Year</span><p>: "+(dataList[i].academicYear == null ? "--":dataList[i].academicYear)+"</p></li>";
						result = result + "<li class='clearfix'><span>Grade</span><p>: "+(dataList[i].grade == null ? "--":dataList[i].grade)+"</p></li>";
						result = result + "<li class='clearfix'><span>University / Authority</span><p>: --</p></li>";
						result = result + "<li class='clearfix'><span>Attach document</span><p>: "+(dataList[i].educationFileName == null ? "--":dataList[i].educationFileName)+"" +
								"<a download href = "+(dataList[i].educationFileUrl == null ? "--":dataList[i].educationFileUrl)+">  <i class='fa fa-download ml'></i></a></p></li>";
						result = result + "<li class='clearfix'><span>Comments</span><p>: --</p></li>";
						result = result + "</ul>";
						}
						$("#educationList").empty().append(result);
					}else{
						$("#educationLoading").removeAttr("style");
						return false;
					}
				},
			});
	}
	
	this.getBgcCandidateEmploymentSummary = function(uuid){
		$.ajax({
			type : "GET",
			url : vmsUrl + "/bgc/getBgcCandidateEmploymentSummary?uuid="+uuid,
			success : function(dataList) {
				if(dataList != null && dataList.length > 0){
					$("#employmentLoading").removeAttr("style");	
					var result = "";
					for(var i=0; i < dataList.length; i++){
						result = result + "<h2>Previous Employer "+(i+1)+"</h2>";
						result = result + "<ul>";
						result = result + "<li class='clearfix'><span>Previous Employer Name</span><p>: "+(dataList[i].name == null ? "--":dataList[i].name)+"</p></li>";
						result = result + "<li class='clearfix'><span>Designation</span><p>: "+(dataList[i].designation == null ? "--":dataList[i].designation)+"</p></li>";
						result = result + "<li class='clearfix'><span>Period</span><p>: "+(dataList[i].period == null ? "--" : dataList[i].period)+" Year(s)</p></li>";
						result = result + "<li class='clearfix'><span>Attach document</span><p>: "+(dataList[i].employerFileName == null ? "--":dataList[i].employerFileName)+"" +
								"<a download href = "+(dataList[i].employerFileUrl == null ? "--":dataList[i].employerFileUrl)+"><i class='fa fa-download ml'></i></a></p></li>";//
						result = result + "<li class='clearfix'><span>Conduct</span><p>: --</p></li>";
						result = result + "</ul>";
						$("#employmentList").empty().append(result);
					}
				}else{
					$("#employmentLoading").removeAttr("style");
					return false;
				}
				
			},
		});
	}
	
	this.editBgcCandidate = function(){
		$("#bgcId").val(uuid);
		$("#bgcName").val(name);
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
							location.replace(vmsUrl+"/bgc/completedBgcDetails");	
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
					if(data != null){
						$("#legalAndCriminalLoading").removeAttr("style");
						var result = "";
						result = result + "<h2>Legal and Criminial Check</h2>";
						result = result + "<ul>";
						result = result + "<li class='clearfix'><span>Financial Check</span><p>: --</p></li>";
						result = result + "<li class='clearfix'><span>Criminal Check</span><p>: "+(data.crimeCheckStatus==null? "--":data.crimeCheckStatus)+"</p></li>";
						result = result + "<li class='clearfix'><span>Bank and Property Check</span><p>: --</p></li>";
						result = result + "<li class='clearfix'><span>Salary Check</span><p>: --</p></li>";
						result = result + "	<li class='clearfix'><span>Attach document</span><p>: --</p></li>";
						result = result + "</ul>";
						$("#legalAndCriminalList").empty().append(result);
					}else{
						return false;
					}
				},
			});
			
		}
	
		this.getBgcCandidateSummary = function(uuid){
			$.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/getBgcCandidateSummary?uuid="+uuid,
				success : function(data) {
					if(data != null){
						$("#detailsLoading").removeAttr("style");
						var result = "";
						result = result + "<h2>Personal / Identification</h2>";
						result = result + "<ul>";
						result = result + "<li class='clearfix'><span>Aadhar Card</span><p>: "+(data.aadharNumber == null ? "--" : data.aadharNumber)+"</p></li>";
						result = result + "<li class='clearfix'><span>PAN Card</span><p>: "+(data.panNumber == null ? "--" : data.panNumber)+"</p></li>";
						result = result + "<li class='clearfix'><span>Address</span><p>: "+(data.address == null ? "--" : data.address)+"</p></li>";
						result = result + "<li class='clearfix'><span>Family</span><p>: --</p></li>";
						result = result + "<li class='clearfix'><span>Attach document</span><p>: <a>--<i class='fa fa-download ml'></i></a></p></li>";
						result = result + "<li class='clearfix'><span>Comments</span><p>: "+(data.comments == null ? "--" : data.comments)+"</p></li>";
						result = result + "</ul>";
						$("#details").empty().append(result);
					}else{
						return false;
					}
				},
			});
		}
}