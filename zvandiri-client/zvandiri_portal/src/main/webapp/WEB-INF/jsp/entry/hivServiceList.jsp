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
                        <th>CBO</th>
                        <th>Period</th>
                        <th>HTC Male</th>
                        <th>HTC Female</th>
                        <th>VMMC</th>
                        <th>PMTCT</th>
                        <th>Family Planning</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>CBO</th>
                        <th>Period</th>
                        <th>HTC Male</th>
                        <th>HTC Female</th>
                        <th>VMMC</th>
                        <th>PMTCT</th>
                        <th>Family Planning</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td>
                                    <a href="<c:url value="/entry/hiv-service/item.form?id=${item.id}"/>">${item.organisation.name}</a>
                                </td>
                                <td><spring:eval expression="item.period.name"/></td>
                                <td><spring:eval expression="item.htc"/></td>
                                <td><spring:eval expression="item.htcFemale"/></td>
                                <td><spring:eval expression="item.vmmc"/></td>
                                <td><spring:eval expression="item.pmtct"/></td>
                                <td><spring:eval expression="item.familyPlanning"/></td>
                                <td>
                                    <a href="<c:url value="/entry/hiv-service/item.form?id=${item.id}"/>">Edit</a>
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