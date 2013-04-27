function Tweet(id, author, text, created_at, score){
	this.id=id;
	this.author=author;
	this.text=text;
	this.created_at=created_at;
	this.score= (score === undefined) ? 0 : score;
}

function find_author_id_from_username(username){
	if(env.users===undefined){
		return -1;
	}
	for (var i = env.users.length - 1; i >= 0; i--) {
		if(env.users[i] instanceof User && env.users[i].username==username){
			return i;
		}
	}
	return -1;
}

function replaceURLWithHTMLLinks(text) {
  var exp = /(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig;
  return text.replace(exp,"<a href='$1'>$1</a>");
}

function replaceArobaseWithHTMLLinks(text) {
  var exp = /(@([-A-Z0-9_\.]+))/ig;
	return text.replace(exp," <a href='#$2' onclick='loadUserTimeline(find_author_id_from_username(\"$2\"))'>$1</a>");
}
Tweet.prototype.getHtml = function(){
	var res="";
	res += '<div class="tweet-container" id="tweet-'+this.id+'">';
	// res += '<div class="tweet-author-pic"><img src="img/av'+this.author.id+'.png"/></div>';
	var gravatar='<img src="http://www.gravatar.com/avatar/'+md5(env.users[this.author.id].username+'@gmail.com')+'?s=64&d=identicon"/>';
	res += '<div class="tweet-author-pic"><a href="#'+this.author.username+'" onclick="loadUserTimeline('+this.author.id+')">'+gravatar+'</a></div>';
	res += '<div class="tweet-content">';
	res += '<div class="tweet-author">';
	res += '<a href="#'+this.author.username+'" onclick="loadUserTimeline('+this.author.id+')">';
	res += '<span class="tweet-author-name">'+this.author.name+'</span>';
	res += '<span class="tweet-author-screen">@'+this.author.username+'</span>';
	res += '</a>';
	if(env.active && env.id!=this.author.id){
		if(env.users[this.author.id].contact){
			res += '<a onclick="addrem_contact('+this.author.id+')">';
			res += '<span class="tweet-author-follow tweet-author-follow-'+this.author.id+'"><i class="icon-minus-sign"></i></span>';
		}else{
			res += '<a onclick="addrem_contact('+this.author.id+')">';
			res += '<span class="tweet-author-follow tweet-author-follow-'+this.author.id+'"><i class="icon-plus-sign"></i></span>';
		}
		res += '</a>';
	}
	res += '</div>';
	res += '<div class="tweet-msg">'+replaceURLWithHTMLLinks(replaceArobaseWithHTMLLinks(this.text))+'</div>';
	var date = new Date(this.created_at);
	res += '<div class="tweet-date"><i class="icon-time">&nbsp;</i>'+date.toLocaleString();

	if(env.active && env.id==this.author.id){
		res+='<a onclick="delete_tweet(\''+this.id+'\')">';
		res+='<span class="tweet-delete"><i class="icon-trash"></i></span>';
		res+='</a>';
	}
	res += '</div>';
	res += '</div>';
	res += '</div>';
	return res;
};

function delete_tweet(tweet_id){
	$.ajax({
		type:"GET",
		url:"status/destroy",
		data:"token="+env.token+"&id="+tweet_id,
		dataType:"json",
		success:refreshTimeline,
		error:"Erreur de suppression du tweet"
	});
	decrementStatusesCount();
}

function SearchTweets(results, query, contacts_only, author, date){
	this.results=[].concat(results);
	this.query=query;
	this.contacts_only=contacts_only;
	this.author=author;
	this.date=date;
	env.search=this;
}

SearchTweets.prototype.getHtml = function(){
	var res="";
	if(this.results[0]===undefined){
		return '<div class="text-error">No tweets to show!</div>';
	}
	for (var i = 0; i < this.results.length; i++) {
		res+=this.results[i].getHtml();
	}
	return res;
};

SearchTweets.reviver = function (key,value){
	if(key.length===0){
		if (value.error===undefined){
			return new SearchTweets(value.results, value.query, value.contacts_only, value.author, value.date);
		}else{
			return value;
		}
	}else if(value._id!==undefined && value.author instanceof User){ // array results
		return new Tweet(value._id, value.author, value.text, value.created_at, value.score);
	}else if(key=='author'){
		return new User(value.author_id, ""+value.author_username, ""+value.author_name, value.contact);
	}else if(key=='date'){
		return new Date(value);
	}else{
		return value;
	}
};

SearchTweets.processResponseJSON = function (json){
	var obj = JSON.parse(JSON.stringify(json), SearchTweets.reviver);
	//console.log(obj);
	if(obj.error===undefined){
		$("#content").html(obj.getHtml());
	}else{
		alert(obj.error);
	}
};

function loadTimeline(){
	env.timeline="all";
	$.ajax({
		type:"GET",
		url:"search/tweets",
		data:"token="+env.token+"&friends=0",
		dataType:"json",
		success:SearchTweets.processResponseJSON,
		error:"Erreur de rappatriement des tweets"
	});
	suggest();
}

function loadFriendsTimeline(){
	env.timeline="friends";
	$.ajax({
		type:"GET",
		url:"search/tweets",
		data:"token="+env.token+"&friends=1",
		dataType:"json",
		success:SearchTweets.processResponseJSON,
		error:"Erreur de rappatriement des tweets des amis"
	});
	suggest();
}

function loadQueryTweets(){
	query=$(".search").val();
	$.ajax({
		type:"GET",
		url:"search/tweets",
		data:"query="+query+"&token="+env.token+"&friends=0",
		dataType:"json",
		success:SearchTweets.processResponseJSON,
		error:"Erreur de rappatriement des tweets de la recherche"
	});
}

function loadSelfTimeline(){
	env.timeline="self";
	$.ajax({
		type:"GET",
		url:"status/list",
		data:"token="+env.token+"&id="+env.id,
		dataType:"json",
		success:SearchTweets.processResponseJSON,
		error:"Erreur de rappatriement des tweets"
	});
	suggest();
}

function loadUserTimeline(id){
	if(id<0){
		return;
	}
	$.ajax({
		type:"GET",
		url:"status/list",
		data:"token="+env.token+"&id="+id,
		dataType:"json",
		success:SearchTweets.processResponseJSON,
		error:"Erreur de rappatriement des tweets"
	});
}

function loadMainTimeline(){
	if(env.active){
		loadSelfTimeline();
	}else{
		loadTimeline();
	}
}

function refreshTimeline(){
	if(!env.active){
		return loadTimeline();
	}
	if(env.timeline=="self"){
		loadSelfTimeline();
	}else if(env.timeline=="friends"){
		loadFriendsTimeline();
	}else{
		loadTimeline();
	}
}

function updateStatus(){
	var text=""+$('input.compose').val();
	if(text==="" || !env.active || env.token===""){
		return;
	}
	$('input.compose').val('');
	$.ajax({
		type:"GET",
		url:"status/update",
		data:"token="+env.token+"&text="+text,
		dataType:"json",
		success:refreshTimeline,
		error:"Erreur de mise à jour du statut"
	});
	incrementStatusesCount();
}

