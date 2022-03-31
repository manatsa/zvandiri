<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <a href="item.form">New Item</a><br/><br/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <c:if test="${sexWorker}">
                            <th>ID Number</th>
                        </c:if>
                        <th>Age</th>
                        <th>Sex</th>
                        <th>Contact</th>
                        <th>CBO</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Period</th>
                        <c:if test="${faci}">
                            <th>Pre-Test</th>
                            <th>Post-Test</th>
                            <th>Variance</th>
                        </c:if>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <c:if test="${sexWorker}">
                            <th>ID Number</th>
                        </c:if>
                        <th>Age</th>
                        <th>Sex</th>
                        <th>Contact</th>
                        <th>CBO</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Period</th>
                        <c:if test="${faci}">
                            <th>Pre-Test</th>
                            <th>Post-Test</th>
                            <th>Variance</th>
                        </c:if>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td>
                                    <a href="<c:url value="/entry/participant/item.form?id=${item.id}"/>">${item.name}</a>
                                </td>
                                <c:if test="${sexWorker}">
                                    <td><spring:eval expression="item.idNumber"/></td>
                                </c:if>
                                <td><spring:eval expression="item.age"/></td>
                                <td><spring:eval expression="item.gender.name"/></td>
                                <td><spring:eval expression="item.contactNumber"/></td>
                                <td><spring:eval expression="item.organisation.name"/></td>
                                <td><spring:eval expression="item.district.province.name"/></td>
                                <td><spring:eval expression="item.district.name"/></td>
                                <td><spring:eval expression="item.period.name"/></td>
                                <c:if test="${faci}">
                                    <td><spring:eval expression="item.preTest"/></td>
                                    <td><spring:eval expression="item.postTest"/></td>
                                    <td><spring:eval expression="item.variance"/></td>
                                </c:if>
                                <td>
                                    <a href="<c:url value="/entry/participant/item.form?id=${item.id}"/>">Edit</a>
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