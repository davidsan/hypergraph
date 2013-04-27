<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>HyperGraph</title>
    <link href="css/normalize.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link rel="shortcut icon" href="favicon.ico" />
    <link rel="stylesheet" href="css/font-awesome.css">
    <!--[if IE 7]>
    <link rel="stylesheet" href="css/font-awesome-ie7.min.css">
    <![endif]-->
    <script src="js/html5.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/main.js"></script>
    <script src="js/connection.js"></script>
    <script src="js/error.js"></script>
    <script src="js/signup.js"></script>
    <script src="js/user.js"></script>
    <script src="js/tweet.js"></script>
    <script src="js/md5.js"></script>
    <script>
      <%
        String id = request.getParameter("id");
        String token = request.getParameter("token");
        String username = request.getParameter("username");
      %>
      window.onload=function(){
        init_env();
      <%
        if(id!=null && token!=null && username!=null){
          out.print("main('"+id+"','"+token+"','"+username+"');");
        }
      %>
        handle_auth();
      };
    </script>
  </head>
  <body>
  	<div id="container">
  		<div id="navbar">
  			<div id="navbar-left">
	  			<ul>
	  			<li><a id="logo" href="#">HG</a></li>
	  			<li><a id="nav-self" href="#me"><i class="icon-user"></i></a></li>
	  			<li><a id="nav-friends" href="#friends"><i class="icon-group"></i></a></li>
  				<li><a id="nav-all" href="#all"><i class="icon-globe"></i></a></li>

	  			</ul>
  			</div>
  			<div id="navbar-right">
				<ul>
				<li>
	  			<form method="get" action="javascript:(function(){return;}) ()" id="navbar-form">
					 <input type="text" class="search" name="query" placeholder="search...">
           <i class="icon-search"></i>
				  </form>
  			</li>
	  			<li><a id="auth-signin" href="login.html"><i class="icon-signin"></i></a></li>
	  			<li><a id="auth-signout" href="#all"><i class="icon-off"></i></a></li>
	  			</ul>
  			</div>
  		</div>

  		<div id="userbar">
    		<div id="userbar-left"></div>
  			<div id="userbar-right">
  				<form method="post" action="javascript:updateStatus()" id="userbar-form">
  					<input type="text" class="compose" name="tweet" placeholder="what's on your mind ?">
  				</form>
  			</div>
  		</div>

  		<div id="sidebar">
  			<div id="avatar">
  			</div>
  			<div id="stats">
  				<ul>
	  				<li>
		  				<span class="stats-box-number" id="stats-box-statuses-count">0</span>
		  				<span class="stats-box-descr">statuses</span>
	  				</li>
	  				<li>
		  				<span class="stats-box-number" id="stats-box-friends-count">0</span>
		  				<span class="stats-box-descr">following</span>
	  				</li>
	  				<li>
		  				<span class="stats-box-number" id="stats-box-followers-count">0</span>
		  				<span class="stats-box-descr">followers</span>
	  				</li>
	  			</ul>
  			</div>

  			<div id="footer">&copy; 2013 HyperGraph</div>
  		</div>
  		<div id="content">

  		</div>
  	</div>
  </body>
</html>
