<%@ include file="/pages/commons/taglibs.jsp" %>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Sign in</title>
    ${app_cssJsInclude}
</head>
<body>


<div class="panel panel-default" id="panel-signIn">

    <div class="panel-heading">
        <h3 class="panel-title"><i class="glyphicon glyphicon-user"></i> Sign in
            <small> - iTrac</small>
        </h3>
    </div>

    <div class="panel-body">

        <form class="form-horizontal" role="form" method="post">

            <div class="form-group">
                <label for="username" class="col-lg-3 control-label">Name</label>

                <div class="col-lg-6">
                    <input id="username" type="text" name="username" required="required" class="form-control"
                           placeholder="Name"/>
                </div>
            </div>

            <div class="form-group">
                <label for="password" class="col-lg-3 control-label">Password</label>

                <div class="col-lg-6">
                    <input id="password" type="password" name="password" required="required" class="form-control"
                           placeholder="Password"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-3 col-lg-6">
                    <button type="submit" class="btn btn-default btn-primary">Sign in</button>
                </div>
            </div>

        </form>


    </div>

    <div class="panel-footer">
        <a href="http://shinejava.uservoice.com/forums/226709-general" target="_blank"><i class="glyphicon glyphicon-question-sign"></i>  Feedback</a>
    </div>

</div>


</body>
</html>


