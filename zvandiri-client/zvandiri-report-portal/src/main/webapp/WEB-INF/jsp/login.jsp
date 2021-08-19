<%@include file="template/header.jspf" %>
<link id="bootstrap-style" href="<c:url value="/resources/login/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/login/css/bootstrap-responsive.min.css" />" rel="stylesheet">
<link id="base-style" href="<c:url value="/resources/login/css/style.css" />" rel="stylesheet">
<link id="base-style-responsive" href="<c:url value="/resources/login/css/style-responsive.css" />" rel="stylesheet">
<!--<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>-->
<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<link id="ie-style" href="<c:url value="/resources/login/css/ie.css" />" rel="stylesheet">
<![endif]-->
<!--[if IE 9]>
<link id="ie9style" href="<c:url value="/resources/login/css/ie9.css" />" rel="stylesheet">
<![endif]-->
<!-- start: Favicon -->
<link rel="shortcut icon" href="<c:url value="/resources/login/img/favicon.ico" />">
<!-- end: Favicon -->
<link href="<c:url value="/resources/login/css/custom.css" />" rel="stylesheet">
<style type="text/css">
    .sidebar-nav{
        display: none;
    }
    #page-wrapper {
        margin: 0 ! important;
    }
    .make-scroll{
        height: 445px;
        overflow: scroll;
    }
    .fa-caret-down, .fa-user, .fa-envelope{
        display: none;
    }
</style>
<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("index.htm"); %>
</sec:authorize>
<div class="row">
    <div class="col-lg-12">
        <div class="row">
            <div class="col-lg-2">

            </div>
            <div class="col-lg-4">
                <!-- <img src="<c:url value="/resources/images/aids-logo.png" />"  />  -->
            </div>
            <div class="col-lg-2">

            </div>
            <div class="col-lg-4">
                <!-- <img src="<c:url value="/resources/images/applogo.png" />"  />  -->
            </div>
        </div>
    </div>
</div>
<div class="row-fluid">
    <div class="row-fluid">
        <div class="login-box">
            <div class="icons">
                <a href="index.html"><i class="halflings-icon home"></i></a>
                <a href="#"><i class="halflings-icon cog"></i></a>
            </div>
            <h2>Login to your account</h2>
            <%@include file="template/message.jspf" %>
            <form class="form-horizontal" action="<c:url value='j_spring_security_check' />" method="post">
                <fieldset>
                    <div class="input-prepend" title="Username">
                        <span class="add-on"><i class="halflings-icon user"></i></span>
                        <input class="input-large span10" name='j_username' id="username" type="text" placeholder="type username"/>
                    </div>
                    <div class="clearfix"></div>
                    <div class="input-prepend" title="Password">
                        <span class="add-on"><i class="halflings-icon lock"></i></span>
                        <input class="input-large span10" name='j_password' id="password" type="password" placeholder="type password"/>
                    </div>
                    <div class="clearfix"></div>							
                    <label class="remember" for="remember"><input type="checkbox" id="remember" />Remember me</label>
                    <div class="button-login">	
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                    <div class="clearfix"></div>
            </form>
        </div>
    </div>
</div>
<%@include file="template/footer.jspf" %>
<script type="text/javascript">
    $("#username").focus();
    $("#toggle-rem-side-bar").remove();
</script>