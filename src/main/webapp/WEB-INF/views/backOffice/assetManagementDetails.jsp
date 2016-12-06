 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<script>
function assetDetails(assetId) {
		var sourceleadUrl = '${sourceleadUrl}';
		
		$.ajax({
			type : "POST",
			url : sourceleadUrl	+ "/getAssetById?${_csrf.parameterName}=${_csrf.token}&assetId="+ assetId,
			success : function(data) {
				var json = JSON.parse(data);
				    
				    var make = "NO DATA";
				    var model = "NO DATA";
				    var name = "NO DATA";
				    var memory = "NO DATA";
				    var cpu = "NO DATA";
				    var workingStatus = "NO DATA";
				    var hardDisk = "NO DATA";
				    var monitor = "NO DATA";
				    var description = "NO DATA";
				    var shiftAEmpName = " ";
				    var shiftBEmpName = " ";
					
					if (json.make != null && json.make != '' && json.make != 'undefined') {
						make = json.make;
					} 
					
					if (json.model != null && json.model != '' && json.model != 'undefined') {
						model = json.model;
					} 
					
					if (json.name != null && json.name != '' && json.name != 'undefined') {
						name = json.name;
					}  
					
					if (json.memory != null && json.memory != '' && json.memory != 'undefined') {
						memory = json.memory;
					} 
					
					if (json.cpu != null && json.cpu != '' && json.cpu != 'undefined') {
						cpu = json.cpu;
					} 
					
					if (json.workingStatus != null && json.workingStatus != '' && json.workingStatus != 'undefined') {
						workingStatus = json.workingStatus;
					}
					
					if (json.hardDisk != null && json.hardDisk != '' && json.hardDisk != 'undefined') {
						hardDisk = json.hardDisk;
					}
					
					if (json.monitor != null && json.monitor != '' && json.monitor != 'undefined') {
						monitor = json.monitor;
					}
					
					if (json.description != null && json.description != '' && json.description != 'undefined') {
						description = json.description;
					} 
					
					if (json.shiftAEmpName != null && json.shiftAEmpName != '' && json.shiftAEmpName != 'undefined') {
						shiftAEmpName = json.shiftAEmpName;
					}else{
						shiftAEmpName = "NO DATA";
					}
					
					if (json.shiftBEmpName != null && json.shiftBEmpName != '' && json.shiftBEmpName != 'undefined') {
						shiftBEmpName = json.shiftBEmpName;
					}else{
						shiftBEmpName = "NO DATA";
					}
					
					var result = "";
					result = result + '<div class="require_wrap_details">';
					//result = result + '<h1>Asset Management details: <a id="edit-user" href="javascript:void(0)">Edit<i class="glyphicon glyphicon-pencil"></i></a></h1>';
					result = result + '<div class="require_one">';
					
					result = result + '<div class="require_wrap_details_cta"> <b>S. No.:</b> <span>'+json.slno+'</span>';
					result = result + '<div class="clearfix"></div>';
					result = result + ' </div>';
					
					result = result + '<div class="require_wrap_details_cta"> <b>Make:</b> <span>'+make+'</span>';
					result = result + '  <div class="clearfix"></div>';
					result = result + '</div>';
					
					result = result + ' <div class="require_wrap_details_cta"> <b>Model:</b> <span>'+model+'</span>';
					result = result + '  <div class="clearfix"></div>';
					result = result + ' </div>';
					
					result = result + ' <div class="require_wrap_details_cta"> <b>Name:</b> <span>'+name+'</span>';
					result = result + '  <div class="clearfix"></div>';
					result = result + ' </div>'; 
					
					if (json.fileName != null && json.fileName != '' && json.fileName != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b>Attched File:</b> <span>'+json.fileName+'<a href=\"'+sourceleadUrl+'/downloadAssetFile?fileType=file&assetId='+json.id+'\"><i class="fa fa-download"></i></a></span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
					}else{
						result = result + ' <div class="require_wrap_details_cta"> <b>Attched File:</b> <span>NO DATA</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
					}
					
					result = result + ' <div class="require_wrap_details_cta"> <b>Description:</b> <span>'+description+'</span>';
					result = result + ' <div class="clearfix"></div>';
					result = result + '</div>';
					
					result = result + '<div class="require_wrap_details_cta"> <b>Working Status:</b> <span>'+workingStatus+'</span>';
					result = result + ' <div class="clearfix"></div>';
					result = result + ' </div>';
					
					//this block not visible to others category assets
					if (json.assetCategory != null && json.assetCategory != '' && json.assetCategory != 'undefined' && json.assetCategory.id != 3) {
						result = result + '<div class="require_wrap_details_cta"> <b>Memory:</b> <span>'+memory+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="require_wrap_details_cta"> <b>CPU:</b> <span>'+cpu+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + ' </div>';
						
						result = result + ' <div class="require_wrap_details_cta"> <b>Hard Disk:</b> <span>'+hardDisk+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + ' </div>';
						
						if (json.assetCategory != null && json.assetCategory != '' && json.assetCategory != 'undefined' && json.assetCategory.id == 2) {
							result = result + ' <div class="require_wrap_details_cta"> <b>Monitor:</b> <span>'+monitor+'</span>';
							result = result + ' <div class="clearfix"></div>';
							result = result + ' </div>';
						}
						
						result = result + ' <div class="require_wrap_details_cta"> <b>Shfit A:</b> <span>'+shiftAEmpName+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + '</div>';
						
						if (json.shiftAFileName != null && json.shiftAFileName != '' && json.shiftAFileName != 'undefined') {
							result = result + ' <div class="require_wrap_details_cta"> <b>Shfit A Attched File:</b> <span>'+json.shiftAFileName+'<a href=\"'+sourceleadUrl+'/downloadAssetFile?fileType=shiftAfile&assetId='+json.id+'\"><i class="fa fa-download"></i></a></span>';
							result = result + '   <div class="clearfix"></div>';
							result = result + '  </div>';
						}
						
						result = result + '  <div class="require_wrap_details_cta"> <b>Shfit B:</b> <span>'+shiftBEmpName+'</span>';
						result = result + '   <div class="clearfix"></div>';
						result = result + '  </div>';
						
						if (json.shiftBFileName != null && json.shiftBFileName != '' && json.shiftBFileName != 'undefined') {
							result = result + ' <div class="require_wrap_details_cta"> <b>Shfit B Attched File:</b> <span>'+json.shiftBFileName+'<a href=\"'+sourceleadUrl+'/downloadAssetFile?fileType=shiftBfile&assetId='+json.id+'\"><i class="fa fa-download"></i></a></span>';
							result = result + '   <div class="clearfix"></div>';
							result = result + '  </div>';
						}
					}
					//this block not visible to others category assets
					
					
					result = result + '  <div class="clearfix"></div>';
					result = result + '  </div>';
					$("#tab-1").empty();
					$("#tab-1").append(result);
					$("#tab-1").show();
			}
		});
}
</script>	
	

<script>
	$("#edit-user").click(function(){
		$(".user_summary_edit").slideToggle(100);
	});
    $("html, #canBTN, .us-btn_a").click(function() {
        $(".user_summary_edit").slideUp(100);
    });
    $("#edit-user, .user_summary_edit").click(function(e) {
        e.stopPropagation();
    });		
</script>			