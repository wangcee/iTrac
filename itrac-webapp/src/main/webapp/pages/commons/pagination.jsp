<%@ include file="/pages/commons/taglibs.jsp" %>

<ul class="pagination">

    <c:choose>
        <c:when test="${report.pageNum > 1}">
            <%--First--%>
            <li><a href="${paginationBaseUri}1">First</a></li>

            <%--Prev--%>
            <li><a href="${paginationBaseUri}${report.pageNum - 1}">&laquo;</a></li>
            <li><a href="${paginationBaseUri}${report.pageNum - 1}">${report.pageNum - 1}</a></li>
        </c:when>
        <c:otherwise>
            <%--Disable Prev--%>
            <li class="disabled"><a href="#">&laquo;</a></li>
        </c:otherwise>
    </c:choose>

    <%--Current--%>
    <li class="active"><a href="#">${report.pageNum}</a></li>

    <c:choose>
        <c:when test="${report.hasMorePages}">
            <%--Next--%>
            <li><a href="${paginationBaseUri}${report.pageNum + 1}">${report.pageNum + 1}</a></li>
            <li><a href="${paginationBaseUri}${report.pageNum + 1}">&raquo;</a></li>
        </c:when>
        <c:otherwise>
            <%--Disable Next--%>
            <li class="disabled"><a href="#">&raquo;</a></li>
        </c:otherwise>
    </c:choose>
</ul>