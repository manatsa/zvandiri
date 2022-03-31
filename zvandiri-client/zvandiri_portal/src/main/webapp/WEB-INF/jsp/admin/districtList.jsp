<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <a href="../index.htm">Option Tables</a> | <a href="item.form">New District</a> | <a href="item.list">District
            List</a><br/><br/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-10">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Region</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Region</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="district" items="${items}">
                            <tr>
                                <td>
                                    <a href="<c:url value="/admin/district/item.form?id=${district.id}"/>">${district.name}</a>
                                </td>
                                <td>${district.province.name}</td>
                                <td>
                                    <a href="<c:url value="/admin/district/item.form?id=${district.id}"/>">Edit | </a>
                                    <a href="<c:url value="/admin/district/item.delete?id=${district.id}"/>">Delete</a>
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