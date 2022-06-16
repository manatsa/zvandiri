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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="col-lg-12">

                        <div class="form-group">
                                <label>Referral Date :</label>
                                <spring:eval expression="item.referralDate" />
                            </div>
                            <div class="form-group">
                                <label>Expected Visit Date :</label>
                                <spring:eval expression="item.expectedVisitDate" />
                            </div><br/>
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            Services Referred/ Requested
                                        </div>
                                        <div class="panel-body">
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>HIV/STI Prevention :</label><br/>
                                                    <c:forEach var="obj" items="${item.hivStiServicesReq}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                                <div class="form-group">
                                                    <label>Laboratory Diagnoses :</label><br/>
                                                    <c:forEach var="obj" items="${item.laboratoryReq}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>OI/ART Services :</label><br/>
                                                    <c:forEach var="obj" items="${item.oiArtReq}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                                <div class="form-group">
                                                    <label>TB Services :</label><br/>
                                                    <c:forEach var="obj" items="${item.tbReq}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>SRH Services :</label><br/>
                                                    <c:forEach var="obj" items="${item.srhReq}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                                <div class="form-group">
                                                    <label>Psycho-social & Economic Support :</label><br/>
                                                    <c:forEach var="obj" items="${item.psychReq}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                                <div class="form-group">
                                                    <label>Legal Services :</label><br/>
                                                    <c:forEach var="obj" items="${item.legalReq}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>                            
                                            </div>
                                        </div>                        
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Organisation Referred :</label>
                                ${item.organisation}
                            </div>
                            <div class="form-group">
                                <label>Date Attended To :</label>
                                <spring:eval expression="item.dateAttended"/>
                            </div>  
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            Servces Provided/ Received
                                        </div>
                                        <div class="panel-body">
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>HIV/STI Prevention :</label><br/>
                                                    <c:forEach var="obj" items="${item.hivStiServicesAvailed}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                                <div class="form-group">
                                                    <label>Laboratory Diagnoses :</label><br/>
                                                    <c:forEach var="obj" items="${item.laboratoryAvailed}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>OI/ART Services :</label><br/>
                                                    <c:forEach var="obj" items="${item.oiArtAvailed}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                                <div class="form-group">
                                                    <label>TB Services :</label><br/>
                                                    <c:forEach var="obj" items="${item.tbAvailed}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>SRH Services :</label><br/>
                                                    <c:forEach var="obj" items="${item.srhAvailed}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                                <div class="form-group">
                                                    <label>Psycho-social & Economic Support :</label><br/>
                                                    <c:forEach var="obj" items="${item.psychAvailed}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                                <div class="form-group">
                                                    <label>Legal Services :</label><br/>
                                                    <c:forEach var="obj" items="${item.legalAvailed}">
                                                        ${obj.name}
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>                        
                                    </div>
                                </div>      
                            </div>
                            <div class="form-group">
                                <label>Attending Officer :</label>
                                ${item.attendingOfficer}
                            </div>
                            <div class="form-group">
                                <label>Officer Designation :</label>
                                ${item.designation}
                            </div>
                            <div class="form-group">
                                <label>Action Taken :</label>
                                ${item.actionTaken}
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>