<%@ include file="/pages/commons/taglibs.jsp" %>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Create Ticket</title>
    ${app_cssJsInclude}
</head>
<body>

<div class="wrap">

    ${pageHead_signedIn}

    <div class="page-header">
        <h1>Create Ticket</h1>
    </div>


    <form method="post" class="form-horizontal" action="${ctx}/ticket/create">


        <div class="form-group">
            <label for="title" class="col-lg-3 control-label">Title:</label>
            <div class="col-lg-6">
                <input class="form-control" type="text" name="title" id="title">
            </div>
        </div>

        <div class="form-group">
            <label for="assigned" class="col-lg-3 control-label">Assigned:</label>
            <div class="col-lg-3">
                <select name="assigned" id="assigned" class="form-control">
                    <c:forEach items="${allUsers}" var="user">
                        <option value="${user.id}">${user.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="priority" class="col-lg-3 control-label">Priority:</label>
            <div class="col-lg-3">
                <select name="priority" id="priority"  class="form-control">
                    <option value="1">Lowest</option>
                    <c:forEach begin="2" end="19" var="priorityLevel">
                        <option value="${priorityLevel}">${priorityLevel}</option>
                    </c:forEach>
                    <option value="20">Urgent</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="status" class="col-lg-3 control-label">Status:</label>
            <div class="col-lg-3">
                <select name="status" id="status"  class="form-control">
                    <option value="open">Open</option>
                    <option value="needFeedback">Need Feedback</option>
                    <option value="needTest">Need Test</option>
                    <option value="review">Review</option>
                    <option value="needDeploy">Need Deploy</option>
                    <option value="deployed">Deployed</option>
                    <option value="holdOff">Hold Off</option>
                    <option value="completed">Completed</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="description" class="col-lg-3 control-label">description:</label>
            <div class="col-lg-6">
                <textarea class="form-control" name="description" id="description" cols="80" rows="10"></textarea>
            </div>
        </div>


        <div class="form-group">
            <div class="col-lg-offset-3 col-lg-6">
                <button type="submit" class="btn btn-default btn-primary">Create</button>
            </div>
        </div>

    </form>


</div>
</body>
</html>