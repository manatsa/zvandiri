<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Gender</th>
                        <th>Age</th>
                        <th>Mobile Num</th>
                        <th>Referrer</th>
                        <th>Date Joined</th>
                        <th>Primary Clinic</th>
                        <th>HIV Status</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>&nbsp</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Gender</th>
                        <th>Age</th>
                        <th>Mobile Num</th>
                        <th>Referrer</th>
                        <th>Date Joined</th>
                        <th>Primary Clinic</th>
                        <th>HIV Status</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>&nbsp</th>
                        </tfoot>
                        <tbody>
                            <c:forEach var="item" items="${items}" >
                                <tr>
                                    <td><a href="item.form?id=${item.id}">${item.name}</a></td>
                                    <td>${item.gender}</td>
                                    <td>${item.age}</td>
                                    <td>${item.mobileNumber}</td>
                                    <td>${item.referer.name}</td>
                                    <td><spring:eval expression="item.dateJoined"/></td>
                                    <td>${item.primaryClinic.name}</td>
                                    <td>${item.hivStatusKnown}</td>
                                    <td>${item.district.province.name}</td>
                                    <td>${item.district.name}</td>
                                    <td>
                                        <a href="item.form?id=${item.id}">Edit</a> | 
<%--                                        <c:if test="${canEdit}"><a href="item.delete?id=${item.id}">Delete</a></c:if>--%>
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