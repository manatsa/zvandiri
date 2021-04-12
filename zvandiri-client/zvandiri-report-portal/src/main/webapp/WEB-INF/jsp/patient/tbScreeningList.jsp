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
                    <div class="col-lg-10">
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
                        <b class="titleHeader">TB Screening Detail</b><hr/>
                        <c:if test="${tbScreen != null}">
                            <table class="table-condensed" width="100%">
                                <tr>
                                    <th>Screened For Tb</th>
                                    <td>${tbScreen.screenedForTb}</td>
                                </tr>
                                <tr>
                                    <th>Date Screened</th>
                                    <td>${tbScreen.dateScreened}</td>
                                </tr>
                                <tr>
                                    <th>Presence with signs or symptoms of TB</th>
                                    <td>${tbScreen.tbSymptoms}</td>
                                </tr>
                                <tr>
                                    <th>Identified with TB</th>
                                    <td>${tbScreen.identifiedWithTb}</td>
                                </tr>
                                <tr>
                                    <th>Action Taken</th>
                                    <td>${tbScreen.tbIdentificationOutcome}</td>
                                </tr>
                                <tr>
                                    <th>Date Started Treatment</th>
                                    <td>${tbScreen.dateStartedTreatment}</td>
                                </tr>
                                <tr>
                                    <th>Outcome</th>
                                    <td>${tbScreen.tbTreatmentOutcome}</td>
                                </tr>
                                <tr>
                                    <th>Referred For IPT</th>
                                    <td>${tbScreen.referredForIpt}</td>
                                </tr>
                                <tr>
                                    <th>On IPT</th>
                                    <td>${tbScreen.onIpt}</td>
                                </tr>
                                <tr>
                                    <th>Date Started On IPT</th>
                                    <td>${tbScreen.dateStartedIpt}</td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>
                                        <a href="${page}/patient/tb-screening/item.form?id=${tbScreen.id}">Edit</a> | 
<%--                                        <c:if test="${canEdit}"><a href="${page}/patient/tb-screening/item.delete?id=${tbScreen.id}">Delete</a></c:if>--%>
                                        </td>
                                    </tr>
                                </table>            
                        </c:if>
                        <c:if test="${tbScreen == null}">
                            <c:if test="${canEdit}"><a href="${page}/patient/tb-screening/item.form?patientId=${patient.id}">Add TB Screening </a></c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>