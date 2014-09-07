<%@ include file="/pages/commons/taglibs.jsp" %>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Report Detail</title>
    ${app_cssJsInclude}

</head>
<body>

<div class="wrap">

    ${pageHead_signedIn}

    <div class="page-header">
        <h1>Report Detail: #${report.reportId}</h1>
    </div>


    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Priority</th>
            <th>Title</th>
            <th>Assigned</th>
            <th>Status</th>
            <th>Assist By</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${report.dataList}" var="entity">
            <tr>
                <td>
                    <a href="${ctx}/ticket/${entity.id}" target="_blank">${entity.id}</a>
                </td>
                <td>${entity.priority}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty entity.externalUrl}">
                            <a href="${entity.externalUrl}" target="_blank">[!Wrike]</a>${entity.title}
                        </c:when>
                        <c:otherwise>
                            ${entity.title}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${entity.assigned}</td>
                <td>${entity.status}</td>
                <td>${entity.assistBy}</td>
                <td>${entity.updateDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


<%@ include file="/pages/commons/pagination.jsp" %>