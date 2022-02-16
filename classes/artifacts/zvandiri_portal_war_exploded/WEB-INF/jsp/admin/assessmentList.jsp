<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <a href="../index.htm">Option Tables</a> | <a href="item.form">New Assessment</a> | <a href="item.list">Assessment List</a><br/><br/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-10">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Type</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Type</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                            <c:forEach var="item" items="${items}" >
                                <tr>
                                    <td><a href="item.form?id=${item.id}">${item.name}</a></td>
                                    <td>${item.contactAssessment.name}</td>
                                    <td>
                                        <a href="item.form?id=${item.id}">Edit | </a>
                                        <a href="item.delete?id=${item.id}">Delete</a>
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