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
                    <div class="col-lg-6"><a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow;
                        Back To ${patient.name} Dashboard</a></div>
                    <div class="col-lg-6"><a href="${page}/beneficiary/medhist/item.list?id=${patient.id}">Next To
                        Hospitalisation History &DoubleRightArrow;</a></div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">Opportunistic Infection Detail</b> <c:if test="${canEdit}">| <a
                            href="item.form?patientId=${patient.id}">Add Opportunistic Infection Item </a></c:if>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Infection</th>
                                    <th>Diagnosis Date</th>
                                    <th>Medication</th>
                                    <th>Current Status</th>
                                    <th>&nbsp;</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="infection" items="${infections}">
                                    <tr>
                                        <td>${infection.chronicInfection.name}</td>
                                        <td><spring:eval expression="infection.infectionDate"/></td>
                                        <td>${infection.medication}</td>
                                        <td>${infection.currentStatus.name}</td>
                                        <td>
                                            <a href="item.form?itemId=${infection.id}">Edit</a> |
                                                <%--                                                <c:if test="${canEdit}"><a href="item.delete?id=${infection.id}">Delete</a></c:if>--%>
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