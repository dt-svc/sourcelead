  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 
 <script>
function associateDeductionSummary(userId){  
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAllDeductionSummaryByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
             var data = json.data;
             var result = "";	
            result = result + '<div class="clearfix">';
            result = result + '<div class="table-header clearfix">';
            result = result + '<h4 class="pull-left margin-top0">Deductions</h4>';
            result = result + '<ul class="list-unstyled list-inline pull-right">';
            result = result + '<li><a><i class="fa fa-plus paddingRight"></i>Add</a></li>';
            result = result + '<div class="clearfix"></div>';
            result = result + '</ul>';
            result = result + '</div>';

            result = result + '<div class="styleTables">';
            result = result + '<table>';
            result = result + '<thead>';
            result = result + '<tr>';
            result = result + '<th>Type</th>';
            result = result + '<th>Description</th>';
            result = result + '<th>Method</th>';
            result = result + '<th>Amount</th>';
            result = result + '<th>Deducted<br>Amount</th>';
            result = result + '<th>Action</th>';
            result = result + '</tr>';
            result = result + '</thead>	';
            result = result + '<tbody>';
            
            for (var i=0; i<data.length; i++)
			{ 
            	 var opt = data[i];
            	   result = result + '<tr>';
            	   result = result + '<td><div class="proj_show tblminwidth-75"><span>'+opt.deductionType+'</span><i class="fa fa-binoculars"></i></div></td>';
                   if(opt.description != null && opt.description != '' && opt.description != 'undefined'){
                	   result = result + '<td><div class="tbldata-dotdot-verybig">'+opt.description+'</div></td>';
                   }else{
                	   result = result + '<td><div class="tbldata-dotdot-verybig">--</div></td>';
                   }
                   result = result + '<td><div  class="tblminwidth-100">'+opt.method+'</div></td>';
                   result = result + '<td><div  class="tblminwidth-100">$ '+opt.amount+'</div></td>';
                   result = result + '<td><div  class="tblminwidth-100">--</div></td>';
                   result = result + '<td><div class="tbldata-dotdot_50">';
                   result = result + '<a href="add-deduction.html" class="text-primary"><i class="fa fa-pencil-square-o"></i></a>';
                   result = result + '<a href="javascript:void(0)"><i class="glyphicon glyphicon-trash"></i></a>';
                   result = result + '</div>';
                   result = result + '</td>';
                   result = result + '</tr>';
			}		
            result = result + '</tbody>';
            result = result + '</table>';
            result = result + '</div>';
            result = result + '</div>';
            	
            	
               $("#tab-18").empty(); 
                $("#tab-17").empty(); 
                $("#tab-19").empty(); 
                $("#tab-20").empty(); 
                $("#candidateSideDiv").empty(); 
                $("#tab-20").empty();
                $("#tab-21").empty(); 
                $("#tab-22").empty(); 
                $("#tab-23").empty(); 
                $("#tab-23").append(result); 
                
          },
            
       }); 
  } 
</script>
 
 
 
<!--  <div id="tab-2" class="tabsMenu_tabs_inn clearfix"> -->
		
		<!--   </div> -->