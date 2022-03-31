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
                    <div class="col-lg-6"><a href="${page}/beneficiary/mental-health/item.list?id=${patient.id}">Next To
                        Mental History &DoubleRightArrow;</a></div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">Obstetric History Detail</b>
                        <hr/>
                        <c:if test="${obstericHist != null}">
                            <table class="table-condensed" width="100%">
                                <tr>
                                    <th>Have you ever been pregnant</th>
                                    <td>${obstericHist.pregnant}</td>
                                </tr>
                                <c:if test="${preg}">
                                    <tr>
                                        <th>Are you currently breast feeding</th>
                                        <td>${obstericHist.breafFeedingCurrent}</td>
                                    </tr>
                                    <tr>
                                        <th>Are you currently pregnant</th>
                                        <td>${obstericHist.pregCurrent}</td>
                                    </tr>
                                </c:if>
                                <c:if test="${pregCurrent}">
                                    <tr>
                                        <th>Number of ANC visits</th>
                                        <td>${obstericHist.numberOfANCVisit}</td>
                                    </tr>
                                    <tr>
                                        <th>Gestational age at first ANC visit</th>
                                        <td>${obstericHist.gestationalAge}</td>
                                    </tr>
                                    <tr>
                                        <th>ART Started</th>
                                        <td>${obstericHist.artStarted}</td>
                                    </tr>
                                </c:if>
                                <c:if test="${preg}">
                                    <tr>
                                        <th>How many living children</th>
                                        <td>${obstericHist.children}</td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>
                                        <a href="${page}/beneficiary/obstetric/item.form?itemId=${obstericHist.id}">Edit</a>
                                        |
                                            <%--                                        <c:if test="${canEdit}"><a href="${page}/beneficiary/obstetric/item.delete?id=${obstericHist.id}">Delete</a></c:if>--%>
                                    </td>
                                </tr>
                            </table>
                        </c:if>
                        <c:if test="${obstericHist == null}">
                            <c:if test="${canEdit}"><a
                                    href="${page}/beneficiary/obstetric/item.form?patientId=${patient.id}">Add Obstetric
                                Hist</a></c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>