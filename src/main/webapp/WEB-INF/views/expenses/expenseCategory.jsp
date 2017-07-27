<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 
<!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Expenses Category</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Expenses Category</div>
        </section>
        
		<!-- Timesheet -->

<div class="timesheet_out">

<div class="project_wrap">

	<div class="timesheet_head candidateHead">
	  <ul class="mt10">
	  <sec:authorize access="hasAnyRole('ROLE_ADD_EXPENSE_TYPE')">
	  	<li><a href="javascript:void(0)" class="addcon-pop" onclick="addExpenseCategory();"> <i class="glyphicon glyphicon-plus"></i>Add Category</a></li>
	  </sec:authorize>
	  </ul>
	<div class="clearfix"></div>
	</div>

	 <div class="clients_wrap_table datatable_scroll mt10">
		 <div id="popUpDiv" class="edit-details col-md-4"> </div>
		 <div id="expenseCategoryList"></div>
	 </div>	  
		
    </div>  
	</div>
  
</section>
      <!-- Content Area Ends--> 


<script>
 window.onload = function() {
	 expenseCategoryList();
};
</script>

<script>
function addExpenseCategory(categoryId) {	
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="expenseCategory_form" name="expenseCategory_form" method="post" > ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result  +'   ';
	result = result  +'                  <div class="clearfix mb10" id="nameIdDiv">'  ;
	result = result  +'                    <div class="col-md-12 mt">' ;
	/* result = result  +'		 <select label=" " class="chosen-select form-control" field-name="Expense Category Name" data-placeholder = "Select Expense Category" name="expensesName" id="expensesName" data-validation="required" onchange="validateExpenseCategoryType();" onkeyup ="validateExpenseCategoryType();">';
	
						       <c:forEach var="expCat" items="${lookUpExpCatList}">
    result = result  +'	            <option value="" disabled selected></option>';										 
	result = result + ' 			<option value="${expCat.name}">${expCat.name}</option>';
	                	       </c:forEach>  
    result = result + '		</select>'; */
    
    result = result  +'<input type="text" name="expensesName" field-name="Expensesname" placeholder="Expense Category" data-validation="required validate_Space validate_length length1-100" id="expensesName" data-validation="required"  onkeyup ="validateExpenseCategoryType();" class="form-control">';
    
	result = result  +'</div>';
	result = result  +'</div>';
	result = result  +'';
	result = result  +'<div class="clearfix mb10" id="errTax">';
	result = result  +'<div class="col-md-5" >';
	result = result  +'<div class="btn-group btn_Block pull-left">';
	
	result = result  +' <button type="button" class="btn btn-sm btn-primary" id="taxflatBtn" value="flat" onClick="getTaxRewardType(this)">Flat</button>';
	result = result  +'<button type="button" class="btn btn-sm btn-primary active" id="taxPercentageBtn" value="percentage" onClick="getTaxRewardType(this)">%</button>';
	result = result  +'<input type="hidden" name="taxRewardType" id="taxRewardType" value=""/>';
	result = result  +' </div>';
	result = result  +'</div>';
	
	
	
	
	result = result  +'<div class="col-md-7 paddingLeft" id="taxFlatReward"  >';
	result = result  +'<div class="row flat">';
	result = result  +' <div class="form-group col-md-12 paddingRight positionRelative iconHolder" >';
	result = result  +'<input type="text" class="form-control input-sm" field-name="Flat" id="taxFlatRewardValue"  name="taxFlatReward" data-validation="validate_Space validate_float validate_length length1-10" data-validation-optional="true" placeholder="Flat">';
	result = result  +'<i class="fa fa-money" id="taxFlatRewardIcon"></i>';
	result = result  +' </div>';
	result = result  +' </div>';
	result = result  +' </div>';
	
	
	result = result  +'<div class="col-md-7 paddingLeft" id="taxPercentageReward" style="display: none">';
	result = result  +'<div class="positionRelative iconHolder percentage fullWid" >'; 
	result = result  +'<input type="text" class="form-control input-sm" field-name="Percentage" value="" id="taxPercentageRewardValue" name="taxPercentageReward" data-validation="validate_Space validate_float validate_length length1-10" data-validation-optional="true" onblur="getTaxPercentageValue()" placeholder="Percentage">';
	result = result  +' <i class="fa fa-percent"></i>'; 
	result = result  +' </div>';
	result = result  +' </div>';
	
	result = result  +' </div>';
	result = result  +' </div>';
	
	result = result  +' <div class="clearfix"></div>';	
	
	result = result  +'<div class="clearfix mb10">';
	result = result  +'<div class="col-md-12 mt">';
	result = result + '<textarea placeholder="Description" class="form-control input-sm" id="description" name="description" rows="2" cols="5" data-validation="validate_Space validate_length length1-500" data-validation-optional="true"></textarea>';
	result = result  +' </div>';
	result = result  +' </div>';
	result = result  +'';
	result = result  +'<div class="clearfix mr">';
    result = result +'<input type="button" value="Add" name="btnAddExpCat" id="save_button" class="btn btn-sm btn-primary pull-right" onclick="storeExpensesCategory();"/>'; 
	result = result  +' </div> ';
	result = result  +' </div> ';
	
	result = result  +' </form>';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){ 
		 $(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 
	 <%-- Expense Category Autocomplete Scripts Starts Here --%>
	 var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	 
	 
	/* //Adding Customized Expense Category to the list
     var selectone, chosenone;
     selectone = $("#expensesName");
     selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Category:' });
     chosenone = selectone.data('chosen');
     chosenone.search_field.on('keyup', function(e1)
	    {
	    	var newExpensesName = this.value.replace(",","");
	        if ((e1.which == 13 || e1.which == 188) && (newExpensesName != null  && newExpensesName != '' && newExpensesName != 'undefined'))
	        {
	        	var par = this.parent;
	            var option = $("<option>").val(newExpensesName).text(newExpensesName);
	            selectone.append(option);
	            selectone.find(option).prop('selected', true);
	          //to doesn't allow duplicates
             $("#expensesName option").each(function(){
                   $(this).siblings("[value='"+ this.value+"']").remove();
             });
             //end
     		$("#expensesName").trigger("chosen:updated");
     		
	        }
	    }); 
	    //Adding Expense Category to the list
	  */
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
		<%-- Expense Category Autocomplete Scripts Ends Here --%>
	}
</script>

<script>
function closePopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>


<script>
function expenseCategoryList(){  

	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#expenseCategoryLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/getExpensesCategoryList",    
            success :function(json) {
			    	     $("#expenseCategoryLoading").removeAttr("style");
	                	 $("#expenseCategoryList").empty();
	                	 var result = "";
	                	 result = result +"<table id='expenseCategoryPagenation' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Category Name</th>";
	                     result = result +"    <th>Amount</th>";
	                     result = result +"    <th>Type</th>";
	                     result = result +"    <th>Descrption</th>"; 
	                     result = result +"    <th>Status</th>"; 
	                     result = result +"    <th>Actions</th>";
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     /* var json = JSON.parse(data); */
	                    	for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>";
	                    	 
	                    	    if(opt.expensesName != null && opt.expensesName !='' && opt.expensesName !='undefined'){
	                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.expensesName+"'>"+opt.expensesName+"</span></div></td>";
	                    	     }else{
	                    	     result = result +"<td><div class='tbldata-dotdot-sm'><span title=''>---</span></div></td>"; 
	                    	     }
	                    	    
	                    	    if(opt.allowedAmount != null && opt.allowedAmount !='' && opt.allowedAmount !='undefined'){
	                    		 result = result +"<td><div><span title=''>"+opt.allowedAmount+"</span></div></td>";
	                    	    }else{
	                    	     result = result +"<td><div><span title=''>---</span></div></td>";	
	                    	    }
	                    	    
	                    	    if(opt.amountType != null && opt.amountType !='' && opt.amountType !='undefined'){
	                    		 result = result +"<td><div><span>"+opt.amountType+"</span></div></td>";
	                    	    }else{
	                    	     result = result +"<td><div><span>---</span></div></td>";
	                    	    }
	                    	    
	                    	    if(opt.description != null && opt.description !='' && opt.description !='undefined'){
	                    		 result = result +"<td><div><span title='"+opt.description+"'>"+opt.description+"</span></div></td>"; 
	                    	    }else{
	                    	     result = result +"<td><div><span>---</span></div></td>"; 
	                    	    }
	                    	    
	                    		 if(opt.status == 0){
	                    		 result = result +"<td><div><span>In Active</span></div></td>"; 
	                    		 }else{
	                    			 result = result +"<td><div><span>Active</span></div></td>"; 
	                    		 }
			                    result = result+' <td><div class="tblicons tblminwidth-75">';
			                    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EXPENSE_TYPE\')">'
			                    result = result+'<a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onclick ="editExpenseCategory('+opt.id+');" title="Edit"></a>';
			                    '</sec:authorize>';
			                    if(opt.status == '0'){
			                    	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EXPENSE_TYPE\')">'
			                    	result = result+'<a onclick="reActiveExpenseCategory('+opt.id+');" title="Remove">Re-Active</a></div></td>';
			                    	'</sec:authorize>';
			                    }else{
			                    	'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_EXPENSE_TYPE\')">'
			                    	result = result+'<i class="glyphicon glyphicon-trash" onclick="deleteExpenseCategory('+opt.id+');" title="Remove"></i></div></td>';
			                    	'</sec:authorize>';
			                    }
			                    
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#expenseCategoryLoading").removeAttr("style");
	                     $("#expenseCategoryList").append(result); 
	                     
	                     expenseCategoryPagenation(); 
                    
          },
            
       });  
  } 
</script>   

<script>
function expenseCategoryPagenation(){ 
	 $('#expenseCategoryPagenation').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
	    	"bLengthChange": false,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    } );
}
</script>

<script>
var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>
<script>
function storeExpensesCategory(){
	  if ($('#expenseCategory_form').validate(false, validationSettings)) {
		  
			 var frm = $('#expenseCategory_form').serializeFormJSON();
			 var con = JSON.stringify(frm);
			 con = con.replace(/[{}]/g, "");
		     var jsonData = encodeURIComponent(con);
			 var sourceleadUrl = '${sourceleadUrl}';
			 $("#expenseCategory_form").attr("disabled", "disabled");
			 $("#save_button").attr("disabled", "disabled");

			$.ajax({
					type : "POST",
					url : sourceleadUrl+"/storeExpensesCategory?${_csrf.parameterName}=${_csrf.token}&json="+con,
					success :function(data) {  
				                 if(data != null && data != 'undefined' && data != ""){
				               		if( data.indexOf('Sign In') >= 0){
							    		location.replace(sourceleadUrl);
							    	}
				               		else if (data.indexOf('exception') >= 0) {
				               			 location.replace(sourceleadUrl+"/errorPage");
				               		}
				               		else{
				               		    $("#popUpDiv").removeClass("edit-details-active");
			            				$("#eduMsgDiv").html("Success!! New Expenses Category added");
			            				expenseCategoryList();
				               			eduMsgFadeinFunction();
							    	}
				                 }   
				      }, 
		    });
			return true; 
		} else {
			return false;
		}
} 
$('body').on('blur', '#expenseCategory_form', function() {
	$("#expenseCategory_form").showHelpOnFocus();
	$("#expenseCategory_form").validateOnBlur(false, validationSettings);
});


</script>            
          

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>           

<script type="text/javascript">
function getTaxRewardType(e){
	var rewardType = e.value;
	 $("#taxRewardType").val(rewardType);
	 
	 
	 $("#taxFlatRewardValue").val('');
	 $("#taxPercentageRewardValue").val('');
	 
	 
	 $('#taxFlatReward').find('p.jquery_form_error_message').remove();
	 $('#taxPercentageReward').find('p.jquery_form_error_message').remove();
	 
	 if(rewardType == 'flat'){
		 
			document.getElementById("taxFlatReward").style.display="";
			document.getElementById("taxPercentageReward").style.display="none";
			
			document.getElementById("taxPercentageBtn").className = 'btn btn-sm btn-primary'
			document.getElementById("taxFlatBtn").className = 'btn btn-sm btn-primary active'
			$('#taxFlatRewardValue').attr("data-validation","required validate_float");
			$('#taxFlatRewardValue').removeAttr('data-validation-optional');
			$('#taxPercentageRewardValue').removeAttr('data-validation');
			$('#taxPercentageRewardValue').attr("data-validation-optional","true"); 
			
		} 
	 
	 if(rewardType == 'percentage'){
		 
		document.getElementById("taxPercentageReward").style.display="";
		document.getElementById("taxFlatReward").style.display="none";
		
		document.getElementById("taxPercentageBtn").className = 'btn btn-sm btn-primary active'
	    document.getElementById("taxFlatBtn").className = 'btn btn-sm btn-primary'
    	$('#taxPercentageRewardValue').attr("data-validation","required validate_float");  
		$('#taxPercentageRewardValue').removeAttr('data-validation-optional');
	    $('#taxFlatRewardValue').removeAttr('data-validation');
	    $('#taxFlatRewardValue').attr("data-validation-optional","true"); 
	    
	} 
	
}
</script>

<script>
function getTaxPercentageValue(){
	
	var percentageValue = document.getElementById("taxPercentageRewardValue").value;
	if(percentageValue > 100 || percentageValue < 0){
	    $('#taxPercentageReward').find('p.jquery_form_error_message').remove(); 
	   
	    if(percentageValue < 0){
	    	$("input#taxPercentageRewardValue").after("<p class='jquery_form_error_message'>Percentage value should not in negitive</span>");
	    }else{
	    	$("input#taxPpercentageRewardValue").after("<p class='jquery_form_error_message'>Percentage value not more than 100</span>");
	    }
	    
		$('input#taxPercentageRewardValue').attr('record-exist','yes');
		 if(percentageValue < 0){
			 $('input#taxPercentageRewardValue').attr('record-exist-errorMsg','Percentage value should not in negitive'); 
		 }else{
			 $('input#taxPercentageRewardValue').attr('record-exist-errorMsg','Percentage value not more than 100'); 
		 }
		
	}else{
		 $('input#taxPercentageRewardValue').removeAttr( "record-exist record-exist-errormsg");
		 $('#taxPercentageReward').find('p.jquery_form_error_message').remove(); 
		
	} 
	
}
</script>


<script>
function deleteExpenseCategory(categoryId){
 var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/removeExpensesCategoryByCategoryId?${_csrf.parameterName}=${_csrf.token}&categoryId="+categoryId,        
          success :function(data) {

				if(data != null && data != 'undefined' && data != ""){
             		if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	}else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		}else{
             			expenseCategoryList()
			    	}
               } 
        },
          
     }); 
	  
}
</script> 

<script>
function reActiveExpenseCategory(categoryId){
 var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/reActiveExpenseCategoryByCategoryId?${_csrf.parameterName}=${_csrf.token}&categoryId="+categoryId,        
          success :function(data) {
        	  expenseCategoryList()
        },
          
     }); 
	  
}
</script> 
 
 <script type="text/javascript">
 function editExpenseCategory(categoryId) {
	 var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({	type : "POST",
					url : sourceleadUrl+ "/getExpensesCategoryByCategoryId?${_csrf.parameterName}=${_csrf.token}&categoryId="+categoryId,
					success : function(data) {
						var json = JSON.parse(data);
						var expensesName = "";
						var allowedAmount = "";
						var amountType =""
						var description = "";
						
						if(json.expensesName !=null && json.expensesName !='undefined' && json.expensesName !=''){
							expensesName = json.expensesName;
						}
						
						if(json.allowedAmount !=null && json.allowedAmount !='undefined' && json.allowedAmount !='' && json.allowedAmount !='0'){
							allowedAmount = json.allowedAmount;
						}
						
						if(json.amountType !=null && json.amountType !='undefined' && json.amountType !=''){
							amountType = json.amountType;
						}else{
							amountType ="Flat";
						}
						
						if(json.description !=null && json.description !='undefined' && json.description !=''){
							description = json.description;
						}
						
						
						if (data != null && data != 'undefined' && data != "") {
							if (data.indexOf('Sign In') >= 0) {
								location.replace(sourceleadUrl);
							} else if (data.indexOf('exception') >= 0) {
								location.replace(sourceleadUrl + "/errorPage");
							} else {	
		
								var result = "";
								result = result  +'<!-- Popup Div -->';
								result = result  +'          <form role="form"  id="expenseCategory_form" name="expenseCategory_form" method="post" > ';
								result = result  +'          <i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
								result = result  +'                <div class="form-horizontal clearfix"> ';
								result = result + '<input type="hidden" class="form-control input-sm" name="categoryId"  value="'+categoryId+'">';
								result = result  +'   ';
								/* result = result  +'      <div class="clearfix mb10 col-md-12" id="nameIdDiv"> ';
								result = result  +'		 <select label=" " class="chosen-select form-control" field-name="Expense Category Name" data-placeholder = "Select Expense Category" name="expensesName" id="expensesName" data-validation="required" onchange="validateExpenseCategoryType();" onkeyup ="validateExpenseCategoryType();">';
								                	       for (var j = 0; j < json.expenseCategoryList.length; j++) {
																var epxCat = json.expenseCategoryList[j];
																   if(epxCat.name == expensesName){
																	   result = result+'<option value=\"'+epxCat.name+'\" selected >'+epxCat.name+'</option>';
																  }else{
																	   result = result+'<option value=\"'+epxCat.name+'\" >'+epxCat.name+'</option>';
												   		 	      } 
															}
								                	       
							    result = result + '		</select>';
							    
								result = result  +'</div>'; */
								
								result = result  +'<div class="clearfix mb10" id="nameIdDiv">'  ;
								result = result  +'<div class="col-md-12 mt">' ;
							    
							    result = result  +'<input type="text" name="expensesName" field-name="expensesName" value="'+expensesName+'" data-validation="required validate_Space validate_length length1-100" id="expensesName" data-validation="required"  onkeyup ="validateExpenseCategoryType();" class="form-control">';
							    
								result = result  +'</div>';
								result = result  +'</div>';
								
								result = result  +'';
								result = result  +'<div class="clearfix mb10" id="errTax">';
								result = result  +'<div class="col-md-5" >';
								result = result  +'<div class="btn-group btn_Block pull-left">';
								
								if(amountType == "Flat"){
								result = result  +' <button type="button" class="btn btn-sm btn-primary active" id="taxflatBtn" value="flat" onClick="getTaxRewardType(this)">Flat</button>';
								
								result = result  +'<button type="button" class="btn btn-sm btn-primary " id="taxPercentageBtn" value="percentage" onClick="getTaxRewardType(this)">%</button>';
								result = result  +'<input type="hidden" name="taxRewardType" id="taxRewardType" value="Flat"/>';
								}
								if(amountType == "Percentage"){
									result = result  +' <button type="button" class="btn btn-sm btn-primary" id="taxflatBtn" value="flat" onClick="getTaxRewardType(this)">Flat</button>';
									
									result = result  +'<button type="button" class="btn btn-sm btn-primary active" id="taxPercentageBtn" value="percentage" onClick="getTaxRewardType(this)">%</button>';
									result = result  +'<input type="hidden" name="taxRewardType" id="taxRewardType" value="Percentage"/>';
									}
								
								result = result  +' </div>';
								result = result  +'</div>';
								
								if(amountType == "Percentage" && allowedAmount != null){
								result = result  +'<div class="col-md-7 paddingLeft" id="taxPercentageReward">';
								result = result  +'<div class="positionRelative iconHolder percentage fullWid" >'; 
								result = result  +'<input type="text" class="form-control input-sm" field-name="Percentage"  id="taxPercentageRewardValue" value="'+json.allowedAmount+'" name="taxPercentageReward" data-validation="validate_Space validate_float validate_length length1-10" data-validation-optional="true"  onblur="getTaxPercentageValue()" placeholder="Percentage">';
								result = result  +' <i class="fa fa-percent"></i>'; 
								result = result  +' </div>';
								result = result  +' </div>';
								
								result = result  +'<div class="col-md-7 paddingLeft" id="taxFlatReward"  style="display: none">';
								result = result  +'<div class="row flat">';
								result = result  +' <div class="form-group col-md-8 paddingRight positionRelative iconHolder" >';
								result = result  +'<input type="text" class="form-control input-sm" field-name="Flat" id="taxFlatRewardValue" value="" data-validation="validate_Space validate_float validate_length length1-10" data-validation-optional="true" name="taxFlatReward" placeholder="Flat">';
								result = result  +'<i class="fa fa-money" id="taxFlatRewardIcon"></i>';
								result = result  +' </div>';
								result = result  +' </div>';
								result = result  +' </div>';
								}
								
								if(amountType == "Flat" && allowedAmount != null){
									result = result  +'<div class="col-md-7 paddingLeft" id="taxPercentageReward" style="display: none">';
									result = result  +'<div class="positionRelative iconHolder percentage fullWid" >'; 
									result = result  +'<input type="text" class="form-control input-sm" field-name="Percentage"  id="taxPercentageRewardValue" value="" name="taxPercentageReward" data-validation="validate_Space validate_float validate_length length1-10" data-validation-optional="true"  onblur="getTaxPercentageValue()" placeholder="Percentage">';
									result = result  +' <i class="fa fa-percent"></i>'; 
									result = result  +' </div>';
									result = result  +' </div>';
									
									result = result  +'<div class="col-md-7 paddingLeft" id="taxFlatReward"  ">';
									result = result  +'<div class="row flat">';
									result = result  +' <div class="form-group col-md-8 paddingRight positionRelative iconHolder" >';
									result = result  +'<input type="text" class="form-control input-sm" field-name="Flat" id="taxFlatRewardValue" value="'+json.allowedAmount+'" data-validation="validate_Space validate_float validate_length length1-10" data-validation-optional="true" name="taxFlatReward" placeholder="Flat">';
									result = result  +'<i class="fa fa-money" id="taxFlatRewardIcon"></i>';
									result = result  +' </div>';
									result = result  +' </div>';
									result = result  +' </div>';
									}
								result = result  +' </div>';
								result = result  +' </div>';
								
								result = result  +' <div class="clearfix"></div>';	
								
								result = result  +'<div class="clearfix mb10">';
								result = result  +'<div class="col-md-12 mt">';
								result = result + '<textarea placeholder="Description" class="form-control input-sm" id="description" name="description" rows="2" cols="5" data-validation="validate_Space validate_length length1-500" data-validation-optional="true">'+description+'</textarea>';
								result = result  +' </div>';
								result = result  +' </div>';
								result = result  +'';
								result = result  +'<div class="clearfix mr">';
								result = result	+ '<input type="hidden"  value="'+categoryId+'" id="categoryId" >';
							    result  = result + '<button type="button"  id="save_button"  onClick ="updateExpenseCategory('+categoryId+');" class="btn btn-sm btn-primary pull-right">Update</button>';
								result = result  +' </div> ';
								result = result  +' </div> ';
								
								result = result  +' </form>';
								
		$("#popUpDiv").empty();
		$("#popUpDiv").append(result);
		
		$(".edit-details").toggleClass("edit-details-active");
		 $(".close-div").click(function(){ 
			 $(this).parent("div.edit-details").removeClass("edit-details-active");
			 });
		 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
			 e.stopPropagation();
		 });
		
		 <%-- Expense Category Autocomplete Scripts Starts Here --%>
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
			<%--  Expense Category Autocomplete Scripts Ends Here --%>
			
	}
						}
				},

			});

	}
	 
</script>	 
 
 <script type="text/javascript">
	function updateExpenseCategory(categoryId) {
		
		if ($('#expenseCategory_form').validate(false,
				validationSettings)) {

			var frm = $('#expenseCategory_form').serializeFormJSON();

			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			//It will escape all the special characters
			var jsonData = encodeURIComponent(con);
			
			var sourceleadUrl = '${sourceleadUrl}';

			$.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/upDateExpensesCategoryByCategoryId?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData + "&categoryId="+categoryId,
						success : function(result) {
							closePopup();
							expenseCategoryList();
						},
					});
			return true;
		} else {
			return false;
		}
		$('body').on('blur','#expenseCategory_form',
				function() {
					$("#expenseCategory_form").showHelpOnFocus();
					$("#expenseCategory_form").validateOnBlur(false,
							validationSettings);
				});
	}
</script>

<script type="text/javascript">
 function validateExpenseCategoryType() {
	  var name = document.getElementById("expensesName").value; 
 	  $('#nameIdDiv').find('p.jquery_form_error_message').remove();
	  var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/validateExpenseCategoryType?name="+name, 
		        success: function(data) {
		        	if(data == true){
		        		  $('#nameIdDiv').find('p.jquery_form_error_message').remove();
		        		  
						   $("#expensesName").after("<p class='jquery_form_error_message'>"+name+" is Already Existed</span>");
						  $("#save_button").attr('disabled','disabled');
		        	}
		        	else{
		        		 $('#nameIdDiv').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#nameIdDiv').find('p.jquery_form_error_message').remove(); 
                         $("#save_button").removeAttr("disabled");
		        	}
		        },
		    });
 }
 </script> 
 
<jsp:include page="../masterFooter.jsp" />