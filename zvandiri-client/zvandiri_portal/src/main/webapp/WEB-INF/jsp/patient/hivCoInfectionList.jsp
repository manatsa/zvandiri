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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back
                    To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">Hiv co-infection Detail</b> <c:if test="${canEdit}">| <a
                            href="item.form?patientId=${patient.id}">Add HIV Infection Hist </a></c:if>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Infection</th>
                                    <th>Diagnosis Date</th>
                                    <th>Medication</th>
                                    <th>Resolution</th>
                                    <th>&nbsp;</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="hiv" items="${hivInfections}">
                                    <tr>
                                        <td>${hiv.hivCoInfection.name}</td>
                                        <td><spring:eval expression="hiv.infectionDate"/></td>
                                        <td>${hiv.medication}</td>
                                        <td>${hiv.resolution}</td>
                                        <td>
                                            <a href="item.form?itemId=${hiv.id}">Edit</a> |
                                                <%--                                                <c:if test="${canEdit}"><a href="item.delete?id=${hiv.id}">Delete</a></c:if>--%>
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
<script type="text/javascript">
    $("#infectionDate").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "dd/mm/yy"
    });
</script>
ld!</h1>
</body>
</html>
