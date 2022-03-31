<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            District List
        </div>
        <div class="panel-body">
            <a href="../index.htm">Option Tables</a> | <a href="item.form">New Form</a> | <a href="item.list">Form
            List</a><br/><br/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-10">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Period Type</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Period Type</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="form" items="${items}">
                            <tr>
                                <td><a href="<c:url value="item.form?id=${form.id}"/>">${form.name}</a></td>
                                <td>${form.periodType.name}</td>
                                <td>
                                    <a href="<c:url value="item.form?id=${form.id}"/>">Edit | </a>
                                    <a href="<c:url value="item.delete?id=${form.id}"/>">Delete</a>
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