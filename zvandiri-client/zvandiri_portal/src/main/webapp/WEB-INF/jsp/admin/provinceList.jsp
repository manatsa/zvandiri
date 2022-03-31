<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            Regions List
        </div>
        <div class="panel-body">
            <a href="../index.htm">Option Tables</a> | <a href="item.form">New Region</a> | <a href="item.list">Region
            List</a>
            <hr/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="province" items="${items}">
                            <tr>
                                <td>
                                    <a href="<c:url value="/admin/province/item.form?id=${province.id}"/>">${province.name}</a>
                                </td>
                                <td>
                                    <a href="<c:url value="/admin/province/item.form?id=${province.id}"/>">Edit | </a>
                                    <a href="<c:url value="/admin/province/item.delete?id=${province.id}"/>">Delete</a>
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