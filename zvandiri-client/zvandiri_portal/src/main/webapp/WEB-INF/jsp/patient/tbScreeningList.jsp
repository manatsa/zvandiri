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
                        <b class="titleHeader">TB Screening Detail</b>  <c:if test="${canEdit}">| <a href="${page}/patient/tb-screening/item.form?patientId=${patient.id}">Add TB Screening </a></c:if>
                            <hr/>
                            <div class="table-responsive">
                                <table class="itemList" class="display" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Screened For Tb</th>
                                            <th>Date Screened</th>
                                            <th>Presence with signs or symptoms of TB</th>
                                            <th>Identified with TB</th>
                                            <th>Action Taken</th>
                                            <th>Date Started Treatment</th>
                                            <th>Outcome</th>
                                            <th>Referred For IPT</th>
                                            <th>On IPT</th>
                                            <th>Date Started On IPT</th>
                                            <th>&nbsp;</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="tbScreen" items="${items}">
                                        <tr>
                                            <td>${tbScreen.screenedForTb}</td>
                                            <td>${tbScreen.dateScreened}</td>
                                            <td>${tbScreen.tbSymptoms}</td>
                                            <td>${tbScreen.identifiedWithTb}</td>
                                            <td>${tbScreen.tbIdentificationOutcome}</td>
                                            <td>${tbScreen.dateStartedTreatment}</td>
                                            <td>${tbScreen.tbTreatmentOutcome}</td>
                                            <td>${tbScreen.referredForIpt}</td>
                                            <td>${tbScreen.onIpt}</td>
                                            <td>${tbScreen.dateStartedIpt}</td>
                                            <td>
                                                <a href="${page}/patient/tb-screening/item.form?id=${tbScreen.id}">Edit</a> 
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