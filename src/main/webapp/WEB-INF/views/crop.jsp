<div class="post_popup_img">
    <div class="post_popup_img_main">
        <div class="img-container">
            <img src="" alt="Picture">
        </div>
    </div>
</div>

<div class="post_popup_comments" id="style-4">

    <div class="homeless_wrap_posts homeless_wrap_posts_pop">
        <div class="homeless_posts_head">
            <div class="homeless_posts_head_top homeless_posts_head_top1">
                <div class='jcrop-preview1' id='previewparent'>
                    <img src="" id="preview" />
                </div>
            </div>
            <div class="homeless_posts_like_bar">
                <ul>
                    <li id="applyImage">Apply</li>
                    <li id="cancelImage">Cancel</li>
                    <div class="clear"></div>
                </ul>
                <div class="clear"></div>
            </div>
            <div id="properImagePopUp"></div>
        </div>
    </div>
</div>
<style>
.remove_user_pop p {
    text-align: center;
    font-size: 17px;
}
.remove_user_pop input {
    width: 100%;
}
</style>
<script>
function getErrorMessage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Crop image properly</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancle();'></b>";
	result = result +"	</div>";
	$("#properImagePopUp").empty();
	$("#properImagePopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
function cancle(){
	$("#remove_user_pop").fadeOut(200);
	$(".project_inn_mask").remove();
}
</script>