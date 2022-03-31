<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <a href="item.form">New Media Monitoring</a><br/><br/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>CBO</th>
                        <th>Date</th>
                        <th>Month</th>
                        <th>Media House</th>
                        <th>Writer</th>
                        <th>Article</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>CBO</th>
                        <th>Date</th>
                        <th>Month</th>
                        <th>Media House</th>
                        <th>Writer</th>
                        <th>Article</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td>
                                    <a href="<c:url value="/entry/media-monitoring/item.form?id=${item.id}"/>">${item.organisation.name}</a>
                                </td>
                                <td><spring:eval expression="item.interviewDate"/></td>
                                <td><spring:eval expression="item.monthName"/></td>
                                <td><spring:eval expression="item.mediaHouse.name"/></td>
                                <td><spring:eval expression="item.writer"/></td>
                                <td><spring:eval expression="item.articleTitle"/></td>
                                <td>
                                    <a href="<c:url value="/entry/media-monitoring/item.form?id=${item.id}"/>">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="panel-footer">
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>