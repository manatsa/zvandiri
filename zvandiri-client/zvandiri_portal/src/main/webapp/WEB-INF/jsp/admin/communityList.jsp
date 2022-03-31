<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            District List
        </div>
        <div class="panel-body">
            <a href="../index.htm">Option Tables</a> | <a href="item.form">New Community</a> | <a href="item.list">Community
            List</a><br/><br/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-10">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Contact Name</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Facility</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Contact Name</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Facility</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="comm" items="${items}">
                            <tr>
                                <td><a href="<c:url value="item.form?id=${comm.id}"/>">${comm.name}</a></td>
                                <td>${comm.contactName}</td>
                                <td>${comm.district.province.name}</td>
                                <td>${comm.district.name}</td>
                                <td>${comm.facility.name}</td>
                                <td>
                                    <a href="<c:url value="item.form?id=${comm.id}"/>">Edit | </a>
                                    <a href="<c:url value="item.delete?id=${comm.id}"/>">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>

        </div>
        <div class="panel-footer">
            World Class Benchmark
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>