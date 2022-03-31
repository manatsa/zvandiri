<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            User Role List
        </div>
        <div class="panel-body">
            <a href="../index.htm">Option Tables</a> | <a href="item.form">New User Role</a> | <a href="item.list">User
            Role List</a>
            <hr/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-10">
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
                        <c:forEach var="userRole" items="${items}">
                            <tr>
                                <td>
                                    <a href="<c:url value="/admin/user-role/item.form?id=${userRole.id}"/>">${userRole.name}</a>
                                </td>
                                <td>
                                    <a href="<c:url value="/admin/user-role/item.form?id=${userRole.id}"/>">Edit | </a>
                                    <a href="<c:url value="/admin/user-role/item.delete?id=${userRole.id}"/>">Delete</a>
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