<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">                            
                            <%@include file="../template/dashboard/patientProfile.jspf" %>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6"><a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back To ${patient.name} Dashboard</a></div>
                </div><br/>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">Disabilities  Detail</b>  <c:if test="${canEdit}">| <a href="${page}/beneficiary/disability/item.form?patientId=${patient.id}">Add Disability Hist </a></c:if><br/><br/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Disability</th>
                                        <th>Severity</th>
                                        <th>Screened</th>
                                        <th>Date Screened</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="disability" items="${disabilityCategories}">
                                        <tr>
                                            <td>${disability.disabilityCategory.name}</td>
                                            <td>${disability.severity}</td>
                                            <td>${disability.screened}</td>
                                            <td><spring:eval expression="disability.dateScreened"/></td>
                                            <td>
                                                <a href="${page}/beneficiary/disability/item.form?itemId=${disability.id}">Edit</a> |
<%--                                                <c:if test="${canEdit}"><a href="${page}/beneficiary/disability/item.delete?id=${disability.id}">Delete</a></c:if>--%>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>