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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="col-lg-10">
                        <div class="form-group">
                            <label>Date of Contact :</label>
                            <spring:eval expression="item.contactDate"/>
                        </div>
                        <div class="form-group">
                            <label>Last Clinic Appointment Date :</label>
                            <spring:eval expression="item.lastClinicAppointmentDate"/>
                        </div>
                        <div class="form-group">
                            <label>Attended clinic appointment :</label>
                            ${item.attendedClinicAppointment.name}
                        </div>
                        <div class="form-group">
                            <label>Level of Care :</label>
                            ${item.careLevel.name}
                        </div>
                        <div class="form-group">
                            <label>Location :</label>
                            ${item.location.name}
                        </div>
                        <div class="form-group">
                            <label>Position :</label>
                            ${item.position.name}
                        </div>
                        <div class="form-group">
                            <label>Reason :</label>
                            ${item.reason.name}
                        </div>
                        <c:if test="${external}">
                            <div class="form-group">
                                <label>External Referral :</label>
                                ${item.externalReferral.name}
                            </div>
                        </c:if>
                        <c:if test="${internal}">
                            <div class="form-group">
                                <label>Internal Referral :</label>
                                ${item.internalReferral.name}
                            </div>
                        </c:if>                            
                        <c:if test="${stable}">
                            <div class="form-group">
                                <label>Stable :</label><br/>
                                <c:forEach items="${item.stables}" var="stable">
                                    ${stable.name} <br/>
                                </c:forEach>
                            </div>
                        </c:if>
                        <c:if test="${enhanced}">
                            <div class="form-group">
                                <label>Enhanced :</label><br/>
                                <c:forEach items="${item.enhanceds}" var="enhanced">
                                    ${enhanced.name} <br/>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Subjective :</label>
                            ${item.subjective}
                        </div> 
                        <div class="form-group">
                            <label>Objective :</label>
                            ${item.objective}
                        </div> 
                        <div class="form-group">
                            <label>Clinical Assessment :</label><br/>
                            <c:forEach items="${item.clinicalAssessments}" var="assessment">
                                ${assessment.name} <br/>
                            </c:forEach>
                        </div>
                        <div class="form-group">
                            <label>Non Clinical Assessment :</label><br/>
                            <c:forEach items="${item.nonClinicalAssessments}" var="assessment">
                                ${assessment.name} <br/>
                            </c:forEach>
                        </div>
                        <div class="form-group">
                            <label>Plan :</label>
                            ${item.plan}
                        </div>
                        <div class="form-group">
                            <label>Follow Up :</label>
                            ${item.followUp}
                        </div>
                        <div class="form-group">
                            <label>Action Taken :</label><br/>
                            ${item.actionTaken.name}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>