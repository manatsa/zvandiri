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
                        <b class="titleHeader">Patient Referrals</b> <c:if test="${canEdit}">| <a
                            href="${page}/contact?id==${patient.id}">Add New Referral </a></c:if>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Referral Date</th>
                                    <th>Organisation</th>
                                    <th>Officer</th>
                                    <th>Date Attended</th>
                                    <th>Action Taken</th>
                                    <%--<th>&nbsp;</th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${items}">
                                    <tr>
                                        <td><spring:eval expression="item.referralDate"/></td>
                                        <td>${item.organisation}</td>
                                        <td>${item.attendingOfficer}</td>
                                        <td><spring:eval expression="item.dateAttended"/></td>
                                        <td>${item.actionTaken.name}</td>
                                            <%--<td>
                                                <a href="item.view?itemId=${item.id}">View</a> |
                                                <a href="item.form?itemId=${item.id}">Edit</a> |
&lt;%&ndash;                                                <c:if test="${canEdit}"><a href="item.delete?id=${item.id}">Delete</a></c:if>&ndash;%&gt;
                                            </td>--%>
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