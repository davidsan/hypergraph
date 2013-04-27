function show_error_msg(message){
	var html="<div class=\"text-error\">"+message+"</div>";
	var tab=$(".text-error");
	if(tab.length===0){
		$("form").prepend(html);
	}else{
		tab.replaceWith(html);
	}
}
