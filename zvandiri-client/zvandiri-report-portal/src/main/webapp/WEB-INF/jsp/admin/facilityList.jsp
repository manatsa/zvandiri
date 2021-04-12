<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            District List
        </div>
        <div class="panel-body">
            <a href="../index.htm">Option Tables</a> | <a href="item.form">New Facility</a> | <a href="item.list">Facility List</a><br/><br/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                            <c:forEach var="facility" items="${items}" >
                                <tr>
                                    <td><a href="<c:url value="/admin/facility/item.form?id=${facility.id}"/>">${facility.name}</a></td>
                                    <td>${facility.district.province.name}</td>
                                    <td>${facility.district.name}</td>
                                    <td>
                                        <a href="<c:url value="/admin/facility/item.form?id=${facility.id}"/>">Edit | </a>
                                        <a href="<c:url value="/admin/facility/item.delete?id=${facility.id}"/>">Delete</a>
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