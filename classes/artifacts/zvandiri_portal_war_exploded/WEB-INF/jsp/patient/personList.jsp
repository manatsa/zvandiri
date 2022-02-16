<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <a href="../index.htm">Option Tables</a> | <a href="item.form">New Person</a> | <a href="item.list">Person List</a><br/><br/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-10">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name of Client</th>
                        <th>Mother's Name</th>
                        <th>District of Birth</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>Name of Client</th>
                        <th>Mother's Name</th>
                        <th>District of Birth</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                            <c:forEach var="item" items="${items}" >
                                <tr>
                                    <td>${item.nameOfClient}</td>
                                    <td>${item.nameOfMother}</td>
                                    <td>${item.district}</td>
                                    <td>
                                        <a href="<c:url value="/person/item.form?id=${item.id}"/>">Edit | </a>
<%--                                        <a href="<c:url value="/person/item.delete?id=${item.id}"/>">Delete | </a>--%>
                                         <a href="<c:url value="/hiv-self-testing/item.list?id=${item.id}"/>">Self Testing |</a>
                                         <a href="<c:url value="/tb-screening/item.list?id=${item.id}"/>">TB Screening</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <div class="panel-footer">
            &nbsp;
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>