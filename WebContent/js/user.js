function User(id, username, name, contact){
	this.id = id;
	this.username = username;
	this.name = name;
	this.contact = (contact === true) ? true : false;
	if(env === undefined){
		env = {};
	}
	if(env.users === undefined){
		env.users=[];
	}
	env.users[id]=this;
}
User.prototype.toggleContact = function(){
	this.contact = !this.contact;
};


function incrementStatusesCount(){
	var str_val=$("#stats-box-statuses-count").html();
	var val=parseInt(str_val,10);
	val++;
	$("#stats-box-statuses-count").html(val);
}

function decrementStatusesCount(){
	var str_val=$("#stats-box-statuses-count").html();
	var val=parseInt(str_val,10);
	val--;
	$("#stats-box-statuses-count").html(val);
}

function incrementFriendsCount(){
	var str_val=$("#stats-box-friends-count").html();
	var val=parseInt(str_val,10);
	val++;
	$("#stats-box-friends-count").html(val);
}

function decrementFriendsCount(){
	var str_val=$("#stats-box-friends-count").html();
	var val=parseInt(str_val,10);
	val--;
	$("#stats-box-friends-count").html(val);
}


function incrementFollowersCount(){
	var str_val=$("#stats-box-followers-count").html();
	var val=parseInt(str_val,10);
	val++;
	$("#stats-box-followers-count").html(val);
}

function decrementFollowersCount(){
	var str_val=$("#stats-box-followers-count").html();
	var val=parseInt(str_val,10);
	val--;
	$("#stats-box-followers-count").html(val);
}

User.processUserStatResponseJSON = function(json){
	var obj = JSON.parse(JSON.stringify(json));
	if(obj.error===undefined){
		$("#stats-box-statuses-count").html(obj.statuses_count);
		$("#stats-box-friends-count").html(obj.friends_count);
		$("#stats-box-followers-count").html(obj.followers_count);
	}else{
		alert(obj.error);
	}
};

function loadUserStat(){
	$.ajax({
		type:"GET",
		url:"user/show",
		data:"token="+env.token,
		dataType:"json",
		success:User.processUserStatResponseJSON,
		error:"Erreur de rappatriement des statistiques de l'utilisateur"
	});
}

User.processAddRemContactResponseJSON = function(json){
	var obj = JSON.parse(JSON.stringify(json));
	if(obj.error!==undefined){
		alert(obj.error);
	}
	refreshTimeline();
};

function addrem_contact(id){
	if(env.users[id].contact){
		//remove
		$.ajax({
			type:"GET",
			url:"friendship/destroy",
			data:"token="+env.token+"&requesteeId="+id,
			dataType:"json",
			success:User.processAddRemContactResponseJSON,
			error:"Erreur lors du changement de relation"
		});
		decrementFriendsCount();
		$(".tweet-author-follow-"+id).children().attr("class","icon-plus-sign");
	}else{
		//add
		$.ajax({
			type:"GET",
			url:"friendship/create",
			data:"token="+env.token+"&requesteeId="+id,
			dataType:"json",
			success:User.processAddRemContactResponseJSON,
			error:"Erreur lors du changement de relation"
		});
		incrementFriendsCount();
		$(".tweet-author-follow-"+id).children().attr("class","icon-minus-sign");
	}
	env.users[id].toggleContact();
}

function suggest(){
	if(env.token===undefined){
		return;
	}
	$.ajax({
		type:"GET",
		url:"user/suggest",
		data:"token="+env.token,
		dataType:"json",
		success:function(data){processSuggestResponse(data);},
		error:"Erreur lors de la suggestion d'utilisateurs"
	});
}

function processSuggestResponse(data){
	var json=eval(data);
	var users=json.users;
	var start_index = Math.floor(Math.random() * users.length);
	var res='<div id="who-to-follow">';
	res+='<div class="sidebar-title">Who to follow</div>';
	res+='<div class="sidebar-list">';
	res+='<ul>';
	for (var i = 0; i < 5; i++) {
		var usr=users[(start_index+i)%users.length];
		var li="";
		li+="<li>";
		li+="<a href='#"+usr.username+"' onclick='loadUserTimeline("+usr.id+")'>";
		var gravatar='<img src="http://www.gravatar.com/avatar/'+md5(usr.username+'@gmail.com')+'?s=32&d=identicon"/>';
		li+='<div class="sidebar-list-pic">'+gravatar+'</div>';
		li+='<div class="sidebar-list-text">';
		li+='<span class="sidebar-list-name">'+usr.name+'</span>';
		li+='<span class="sidebar-list-screen">@'+usr.username+'</span>';
		li+='</div>';
		li+="</a>";
		li+="</li>";
		res+=li;
	}
	res+='</ul>';
	res+='</div>';
	res+='</div>';
	if($('#who-to-follow').length===0){
		$("#stats").after(res);
	}else{
		$('#who-to-follow').replaceWith(res);
	}
}
