<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">


<script>
function candidateCommentsList(candidateId){
	 
	var result = "";
	result  = result + '<div class="project_tabs_inn_head">';
	result  = result + '              <input class="vendorfild" type="text"><i class="fa fa-search"></i> </div>';
	result  = result + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result  = result + '                <table>';
	result  = result + '                  <tbody>';
	result  = result + '                    <tr>';
	result  = result + '                      <th>By</th>';
	result  = result + '                      <th>To</th>';
	result  = result + '                      <th>Date & Time</th>';
	result  = result + '                      <th>Action</th>';
	result  = result + '                    </tr>';
	result  = result + '                    <tr>';
	result  = result + '                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>';
	result  = result + '                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>';
	result  = result + '                      <td><div><span>30/08/2016 7:30 AM</span></div></td>';
	result  = result + '                      <td><div><a href="javascript:void(0)">View</a></div></td>';
	result  = result + '                    </tr>';
	result  = result + '                    <tr>';
	result  = result + '                      <td><div><span class="mid-span">Vannam (vannam@bis.com)</span></div></td>';
	result  = result + '                      <td><div><span class="mid-span">Mahesh (mahesh@amazon.in)</span></div></td>';
	result  = result + '                      <td><div><span>30/08/2016 7:30 AM</span></div></td>';
	result  = result + '                      <td><div><a href="javascript:void(0)">View</a></div></td>';
	result  = result + '                    </tr>';
	result  = result + '                  </tbody>';
	result  = result + '                </table>';
	result  = result + '              </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();
    $("#tab-7").empty();
    $("#tab-7").append(result);
    
}
</script>


<script type="text/javascript">
var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>

<!-- this script is for serializeFormJSON -->
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

<script>
function commentsPagination(){  
    $('#candidateCommentsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
 
 }
</script>
  