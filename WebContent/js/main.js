function init_env(){
	env = {};
	env.users = [];
	env.active=false;
	env.token="";
	env.timeline="all";
}

function main(id, token, username){
	env.users[id]=new User(id, username, false);
	env.active=true;
	env.id=id;
	env.token=token;
	env.timeline="self";
	$("#nav-self").click(loadSelfTimeline);
	$("#nav-friends").click(loadFriendsTimeline);
	$("#nav-all").click(loadTimeline);
	$("#auth-signout").click(disconnect);
}

function handle_auth(){
	if(env.active===true){
		$("#auth-signin").css("display", "none");
		//$("#auth-signup").css("display", "none");
		$("#auth-signout").css("display", "block");
		$("#userbar").css("display", "block");
		$("#userbar-left").html("<a id=\"screename\">"+env.users[env.id].username+"</a>");
		$("#avatar").html('<img src="http://www.gravatar.com/avatar/'+md5(env.users[env.id].username+'@gmail.com')+'?s=240&d=identicon"/>');
		$("#nav-self").css("display", "block");
		$("#nav-friends").css("display", "block");
		$("#nav-all").css("display", "block");
		//$("#content").css("margin-left", "260px");
		loadUserStat();
	}else{
		$("#sidebar").css("display", "none");
		$("#userbar").css("display", "none");
		$("#auth-signin").css("display", "block");
		//$("#auth-signup").css("display", "block");
		$("#auth-signout").css("display", "none");
		$("#nav-self").css("display", "none");
		$("#nav-friends").css("display", "none");
		// $("#nav-all").css("display", "none");
		$("#content").css("margin", "20px");
	}
	loadMainTimeline();
}

function disable_compose(){
	$("input.compose").attr('readonly', true);
}

function toggleActive(){
	env.active=!env.active;
}
