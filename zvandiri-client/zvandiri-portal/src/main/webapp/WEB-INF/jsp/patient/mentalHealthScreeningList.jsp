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
                </div> 
                <br/>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">Mental Health Screening Detail | <c:if test="${canEdit}">| <a href="${page}/beneficiary/mental-health-screening/item.form?patientId=${patient.id}">Add Mental Health Screening Detail </a></c:if><br/><br/></b><hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Screened For Mental Health</th>
                                        <th>Screening Type</th>
                                        <th>Risk</th>
                                        <td>Support</td>
                                        <th>Referral</th>
                                        <td>Diagnosis</td>
                                        <td>Intervention</td>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="mental" items="${screens}">
                                        <tr>
                                            <td>${mental.screenedForMentalHealth}</td>
                                            <td>${mental.screening}</td>
                                            <td>${mental.risk}</td>
                                            <td>${mental.support}</td>
                                            <td>${mental.referral}</td>
                                            <td>${mental.diagnosis}</td>
                                            <td>${mental.intervention}</td>
                                            <td>
                                                <a href="${page}/beneficiary/mental-health-screening/item.form?itemId=${mental.id}">Edit</a> |
                                                <c:if test="${canEdit}"><a href="${page}/beneficiary/mental-health-screening/item.delete?id=${mental.id}">Delete</a></c:if>
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