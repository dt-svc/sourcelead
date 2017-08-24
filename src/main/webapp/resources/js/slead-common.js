function viewFile(sourceleadUrl, fileURL,title){
	if(fileURL != null && fileURL != ""){
		window.open(sourceleadUrl+'/viewDocument/'+title+'?docFile='+encodeURI(fileURL),title,"height=600,width=600");
	}  
}