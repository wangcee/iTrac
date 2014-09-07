<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/pages/commons/taglibs.jsp" %>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Import</title>

    ${app_cssJsInclude}

</head>
<body>

<div class="wrap">

    ${pageHead_signedIn}

    <div class="page-header">
        <h1>Import Tickets (from Wrike.com RSS XML)</h1>
    </div>


    <form method="post" class="form-horizontal">




        <div class="form-group">
            <label for="xmltxt" class="col-lg-2 control-label">XML:</label>

            <div class="col-lg-8">
                <textarea rows="10" cols="90" name="xmltxt" id="xmltxt"></textarea>
            </div>
        </div>



        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-6">
                <button type="submit" class="btn btn-default btn-primary">Import</button>
            </div>
        </div>

    </form>


</div>
</body>
</html>