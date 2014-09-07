<%@ include file="/pages/commons/taglibs.jsp" %>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Report List</title>
    ${app_cssJsInclude}

</head>
<body>

<div class="wrap">

    ${pageHead_signedIn}

    <div class="page-header">
        <h1>Report List</h1>
    </div>


    <%--<c:choose>
        <c:when test="${not empty userId}">
            <a href="">Account Profile</a>
            <a href="">Import Wrike Rss</a>
        </c:when>
        <c:otherwise>
            <a href="${ctx}/user/signin">Sign In</a>
        </c:otherwise>
    </c:choose>--%>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Query</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <a href="${ctx}/report/150">150</a>
            </td>
            <td>My active tickets</td>
            <td>[status = open, assigned = me]</td>
        </tr>
        <tr>
            <td>
                <a href="${ctx}/report/151">151</a>
            </td>
            <td>Active Tickets which assist by me</td>
            <td>[status = open, assistBy = me]</td>
        </tr>
        <tr>
            <td>
                <a href="${ctx}/report/152">152</a>
            </td>
            <td>Tickets assist by me</td>
            <td>[status = (needFeedback, review, needTest), assistBy = me]</td>
        </tr>
        <tr>
            <td>
                <a href="${ctx}/report/153">153</a>
            </td>
            <td>Tickets need test</td>
            <td>[status = needTest]</td>
        </tr>
        <tr>
            <td>
                <a href="${ctx}/report/154">154</a>
            </td>
            <td>Daily Report</td>
            <td>[comment.user = me, comment.updateDate = Last 2 days]</td>
        </tr>
        <tr>
            <td>
                <a href="${ctx}/report/155">155</a>
            </td>
            <td>Tickets need deploy</td>
            <td>[status = needDeploy]</td>
        </tr>
        <tr>
            <td>
                <a href="${ctx}/report/156">156</a>
            </td>
            <td>Tickets deployed</td>
            <td>[status = deployed]</td>
        </tr>
        <tr>
            <td>
                <a href="${ctx}/report/157">157</a>
            </td>
            <td>Tickets holdOff</td>
            <td>[status = holdOff]</td>
        </tr>
        <tr>
            <td>
                <a href="${ctx}/report/158">158</a>
            </td>
            <td>Tickets completed</td>
            <td>[status = completed]</td>
        </tr>
        <tr>
            <td>
                <a href="${ctx}/report/160">160</a>
            </td>
            <td>Active tickets which haven't been assigned</td>
            <td>[status = open, assigned = unknown]</td>
        </tr>
        <tr>
            <td>
                <a href="${ctx}/report/161">161</a>
            </td>
            <td>All of active tickets</td>
            <td>[status = open]</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>


