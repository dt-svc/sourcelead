<script>
function candidateProfile(){  
	var result ="";
	result = result +'<div class="require_wrap_details">';				
	result = result +'		<h1>Summary:</h1>';
	result = result +'	    <textarea placeholder="Enter description">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.</textarea>';
	result = result +'		<h2>Skills:</h2>';
	result = result +'	    <textarea placeholder="Enter description">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.</textarea> ';              
	result = result +'	</div> ';
	
    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-1").append(result);  
    
    $("#tab-1").show();
	$(".project-tabs-menu a").click(function(event) {
	     event.preventDefault();
	     $(this).parent().addClass("current");
	     $(this).parent().siblings().removeClass("current");
	     var tab = $(this).attr("href");
	     $(".project_tabs_inn").not(tab).css("display", "none");
	     $(tab).fadeIn();
	 });
	
}
</script>

            