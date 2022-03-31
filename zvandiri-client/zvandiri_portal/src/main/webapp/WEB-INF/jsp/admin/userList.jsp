<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            User List
        </div>
        <div class="panel-body">
            <a href="../index.htm">Option Tables</a> | <a href="user.form">New User</a> | <a href="user.list">User
            List</a>
            <hr/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-10">
                    <table id="userList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="user" items="${items}">
                            <tr>
                                <td><a href="<c:url value="/admin/user/user.form?id=${user.id}"/>">${user.userName}</a>
                                </td>
                                <td>
                                    <a href="<c:url value="/admin/managepassword.htm?id=${user.id}"/>">Change Password
                                        | </a>
                                    <a href="<c:url value="/admin/changeprivileges.htm?id=${user.id}"/>">Edit
                                        Privileges</a> |
                                        <%--                                        <a href="<c:url value="/admin/user/user.delete?id=${user.id}"/>">Delete</a>--%>
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