function process_login(form){
	var username = form.username.value;
	var password = form.password.value;
	if(check_form_login(username, password)){
		connect(username, password);
	}
}

function check_form_login(username, password){
	if(username.length === 0){
		show_error_msg("Username required");
		return false;
	}
	if(password.length === 0){
		show_error_msg("Password required");
		return false;
	}
	if(password.length < 1){
		show_error_msg("Wrong Username/Email and password combination.");
		return false;
	}
	return true;
}

function connect(username, password){
	$.ajax({
		type:"GET",
		url:"auth/login",
		data:"username="+username+"&password="+password,
		dataType:"json",
		success:processResponseConnect,
		error:"Erreur de connexion"
	});
}

function processResponseConnect(resp){
	if(resp.error===undefined){
		window.location.href="main.jsp?id="+resp.id+"&username="+resp.username+"&token="+resp.token;
	}else{
		show_error_msg(resp.error+" ("+resp.code+")");
	}
}

function disconnect(){
	env.active=false;
	//env={}; // clear the env object
	handle_auth();
}
