function process_signup(form){
	var username = form.username.value;
	var password = form.password.value;
	var fullname = form.name.value;
	if(check_form_signup(username, password, fullname)){
		signup(username, password, fullname);
	}
}

function check_form_signup(username, password, fullname){
	if(username.length === 0){
		show_error_msg("Username required");
		return false;
	}
	if(password.length === 0){
		show_error_msg("Password required");
		return false;
	}
	if(password.length < 3){
		show_error_msg("The password must be at least 3 characters long");
		return false;
	}
	if(fullname.length === 0){
		show_error_msg("Full name required");
		return false;
	}
	return true;
}

function signup(username, password, name){
	$.ajax({
		type:"GET",
		url:"user/create",
		data:"username="+username+"&password="+password+"&name="+name,
		dataType:"json",
		success:function(){
			location.href="login.html";
		},
		error:"Error in signup"
	});
}


