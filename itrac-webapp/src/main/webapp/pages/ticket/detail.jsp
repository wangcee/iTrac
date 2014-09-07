<%@ include file="/pages/commons/taglibs.jsp" %>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Ticket Detail</title>
    ${app_cssJsInclude}
</head>
<body>

<div class="wrap">

    ${pageHead_signedIn}

    <div class="page-header">
        <h1>Ticket: #${ticket.id }</h1>
    </div>


    <form method="post" class="form-horizontal">

        <div class="form-group">
            <label class="col-lg-3 control-label">title:</label>

            <div class="col-lg-8">
                <p class="form-control-static"> ${ticket.title}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">externalUrl:</label>

            <div class="col-lg-8">
                <p class="form-control-static">
                    <a href="${ticket.externalUrl}" target="_blank">${ticket.externalUrl}</a>
                </p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">description:</label>

            <div class="col-lg-4">
                <p class="form-control-static"> ${ticket.description}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">authorId:</label>

            <div class="col-lg-4">
                <p class="form-control-static"> ${ticket.authorId}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">createDate:</label>

            <div class="col-lg-4">
                <p class="form-control-static"> ${ticket.createDate}</p>
            </div>
        </div>


        <div class="form-group">
            <label for="assigned" class="col-lg-3 control-label">Assigned:</label>

            <div class="col-lg-3">
                
                <select name="assigned" id="assigned" class="form-control">
                    <c:forEach items="${allUsers}" var="user">
                        <option value="${user.id}" <c:if test="${user.id == ticket.assigned}"> selected </c:if> > ${user.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>


        <div class="form-group">
            <label for="priority" class="col-lg-3 control-label">Priority:</label>

            <div class="col-lg-3">
               
                <select name="priority" id="priority" class="form-control">
                    <option value="1">Lowest</option>
                    <c:forEach begin="2" end="19" var="priorityLevel">
                        <option value="${priorityLevel}"  <c:if test="${priorityLevel == ticket.priority}"> selected </c:if> >${priorityLevel}</option>
                    </c:forEach>
                    <option value="20">Urgent</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="assistBy" class="col-lg-3 control-label">assistBy:</label>

            <div class="col-lg-3">
                <select name="assistBy" id="assistBy" class="form-control">
                	<option value="unknown">Unknown</option>
                    <c:forEach items="${allUsers}" var="user">
                        <option value="${user.id}" <c:if test="${user.id == ticket.assistBy}"> selected </c:if>>${user.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        
        <div class="form-group">
            <label for="status" class="col-lg-3 control-label">Status:</label>

            <div class="col-lg-3">
                <select name="status" id="status" class="form-control">
                    <option value="open" <c:if test="${'open' == ticket.status}"> selected </c:if> >Open</option>
                    <option value="needFeedback" <c:if test="${'needFeedback' == ticket.status}"> selected </c:if> >Need Feedback</option>
                    <option value="needTest" <c:if test="${'needTest' == ticket.status}"> selected </c:if>>Need Test</option>
                    <option value="review" <c:if test="${'review' == ticket.status}"> selected </c:if>>Review</option>
                    <option value="needDeploy" <c:if test="${'needDeploy' == ticket.status}"> selected </c:if>>Need Deploy</option>
                    <option value="deployed" <c:if test="${'deployed' == ticket.status}"> selected </c:if>>Deployed</option>
                    <option value="holdOff" <c:if test="${'holdOff' == ticket.status}"> selected </c:if> >Hold Off</option>
                    <option value="completed" <c:if test="${'completed' == ticket.status}"> selected </c:if> >Completed</option>
                </select>
            </div>
        </div>


        <div class="form-group">
            <div class="col-lg-offset-3 col-lg-6">
                <button type="submit" class="btn btn-default btn-primary">Save</button>
            </div>
        </div>

    </form>


    <div class="panel panel-info">
        <div class="panel-heading">Comment</div>

        <div class="panel-body">

            <table class="table">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Author</th>
                    <th>Comment</th>
                </tr>
                </thead>
                <tbody>


                <c:forEach items="${ticket.comments}" var="comment">
                    <tr>
                        <td>${comment.createDate} ${comment.createTime}</td>
                        <td>${comment.userId}</td>
                        <td>${comment.content}</td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>

            <form action="${ctx}/comment/${ticket.id}" method="post" class="form-horizontal">
                <div class="form-group">
                    <label for="content" class="col-lg-3 control-label">Comment:</label>

                    <div class="col-lg-6">
                        <textarea class="form-control" name="content" id="content" cols="80" rows="4"></textarea>
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-lg-offset-3 col-lg-6">
                        <button type="submit" class="btn btn-default btn-primary">Comment</button>
                    </div>
                </div>

            </form>
        </div>
    </div>


</div>
</body>
</html>