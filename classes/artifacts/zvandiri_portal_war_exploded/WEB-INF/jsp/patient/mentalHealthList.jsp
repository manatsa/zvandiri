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
                    <div class="col-lg-6"><a href="${page}/beneficiary/substance/item.list?id=${patient.id}">Next To Substance Use History &DoubleRightArrow;</a></div>
                </div><br/>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">Mental Health  Detail</b>  <c:if test="${canEdit}">| <a href="${page}/beneficiary/mental-health/item.form?patientId=${patient.id}">Add Mental Health Hist </a></c:if><br/><br/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Health Condition</th>
                                        <th>Past</th>
                                        <th>Current</th>
                                        <th>Medication</th>
                                        <td>Period</td>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="mental" items="${mentalHealths}">
                                        <tr>
                                            <td>${mental.mentalHealth.name}</td>
                                            <td>${mental.past}</td>
                                            <td>${mental.current}</td>
                                            <td>${mental.medication}</td>
                                            <td>${mental.age}</td>
                                            <td>
                                                <a href="${page}/beneficiary/mental-health/item.form?itemId=${mental.id}">Edit</a> |
<%--                                                <c:if test="${canEdit}"><a href="${page}/beneficiary/mental-health/item.delete?id=${mental.id}">Delete</a></c:if>--%>
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