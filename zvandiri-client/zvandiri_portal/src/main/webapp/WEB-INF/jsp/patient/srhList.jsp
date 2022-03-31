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
                        <b class="titleHeader">SRH History Detail</b>
                        <hr/>
                        <c:if test="${srhHist != null}">
                            <table class="table-condensed" width="100%">
                                <c:if test="${female}">
                                    <tr>
                                        <th>How old were you when you started menstruating</th>
                                        <td>${srhHist.ageStartMen}</td>
                                    </tr>
                                    <tr>
                                        <th>How often do you bleed</th>
                                        <td>${srhHist.bleedHowOften}</td>
                                    </tr>
                                    <tr>
                                        <th>How many days</th>
                                        <td>${srhHist.bleeddays}</td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <th>Have you ever had sexual intercourse</th>
                                    <td>${srhHist.sexualIntercourse.name}</td>
                                </tr>
                                <tr>
                                    <th>Are you currently sexually active</th>
                                    <td>${srhHist.sexuallyActive.name}</td>
                                </tr>
                                <c:if test="${sexActive}">
                                    <tr>
                                        <th>Do you use condoms</th>
                                        <td>${srhHist.condomUse.name}</td>
                                    </tr>
                                    <tr>
                                        <th>Do you use any other form of birth control</th>
                                        <td>${srhHist.birthControl.name}</td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>
                                        <a href="${page}/beneficiary/srh/item.form?itemId=${srhHist.id}">Edit</a> |
                                            <%--                                        <c:if test="${canEdit}"><a href="${page}/beneficiary/srh/item.delete?id=${srhHist.id}">Delete</a></c:if>--%>
                                    </td>
                                </tr>
                            </table>
                        </c:if>
                        <c:if test="${srhHist == null}">
                            <c:if test="${canEdit}"><a href="${page}/beneficiary/srh/item.form?patientId=${patient.id}">Add
                                SRH Hist</a></c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>