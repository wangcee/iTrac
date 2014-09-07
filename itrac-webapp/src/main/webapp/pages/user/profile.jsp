<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/pages/commons/taglibs.jsp" %>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>My Profile</title>

    ${app_cssJsInclude}

</head>
<body>

<div class="wrap">

    ${pageHead_signedIn}

    <div class="page-header">
        <h1>My Profile</h1>
    </div>


    <form method="post" class="form-horizontal">

        <div class="form-group">
            <label class="col-lg-3 control-label">Name:</label>

            <div class="col-lg-4">
                <p class="form-control-static">${user.name}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">Role:</label>

            <div class="col-lg-4">
                <p class="form-control-static">${user.role}</p>
            </div>
        </div>

        <div class="form-group">
            <label for="email" class="col-lg-3 control-label">Email:</label>

            <div class="col-lg-4">
                <input type="email" required id="email" name="email" value="${user.email}" class="form-control">
            </div>
        </div>

        <div class="form-group">
            <label for="password" class="col-lg-3 control-label">New password:</label>

            <div class="col-lg-4">
                <input type="password" id="password" name="password" value="" class="form-control">
                <span class="help-block">Keep blank if you don't want change password.</span>
            </div>
        </div>

        <div class="form-group">
            <div class="col-lg-offset-3 col-lg-6">
                <button type="submit" class="btn btn-default btn-primary">Save</button>
            </div>
        </div>

    </form>


</div>
</body>
</html>