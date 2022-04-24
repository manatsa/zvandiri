<%@include file="../../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="../../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-10">
                        <div class="panel panel-default">                            
                            <%@include file="../../template/dashboard/patientProfile.jspf" %>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <form:form modelAttribute="patientContact" id="contactForm">
                            <%@include file="../../template/formState.jspf" %>
                            <div class="row">
                                <div class="col-lg-12">

                                    <div id="info" role="tablist" aria-multiselectable="true">

                                        <div class="panel panel-success">
                                            <div class="panel-heading" role="tab" id="clientDetailsPanel">
                                                <h5 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#info" href="#clientDetails" class="btn btn-block text-left" >
                                                        <span class="white text-left"> Client Personal Info</span>
                                                    </a>
                                                </h5>
                                            </div>
                                            <div id="clientDetails" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="clientDetails">
                                                <div class="panel-body">
                                                    <table class="table table-responsive table-bordered table-striped">
                                                        <tr>
                                                            <th>UIC</th>
                                                            <td>${patientContact.patient.patientNumber}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>OI NUMBER</th>
                                                            <td>${patientContact.patient.oINumber}</td>
                                                        </tr>
                                                        <tr>
                                                            <th>FIRST NAME</th>
                                                            <td>${patientContact.patient.firstName}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>LAST NAME</th>
                                                            <td>${patientContact.patient.lastName}</td>
                                                        </tr>
                                                        <tr>
                                                            <th>DATE OF BIRTH</th>
                                                            <td><spring:eval expression="patientContact.patient.dateOfBirth"/></td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>GENDER</th>
                                                            <td>${patientContact.patient.gender.name}</td>
                                                        </tr>
                                                        <tr>
                                                            <th>FACILITY</th>
                                                            <td>${patientContact.patient.primaryClinic.name}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>SUPPORT GROUP</th>
                                                            <td>${patientContact.patient.supportGroup}</td>
                                                        </tr>
                                                        <tr>
                                                            <th>DISTRICT</th>
                                                            <td>${patientContact.patient.primaryClinic.district.name}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>PROVINCE</th>
                                                            <td>${patientContact.patient.primaryClinic.district.province.name}</td>
                                                        </tr>

                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="panel panel-success">
                                            <div class="panel-heading" role="tab" id="mentalHealthPanel">
                                                <h5 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#info" href="#mentalHealth" class="btn btn-block text-left">
                                                        <span class="white text-left">Mental Health Info</span>
                                                    </a>
                                                </h5>
                                            </div>
                                            <div id="mentalHealth" class="panel-collapse collapse" role="tabpanel" aria-labelledby="mentalHealth">
                                                <div class="panel-body">
                                                    <table class="table table-striped table-bordered table-responsive">
                                                        <tr>
                                                            <th>SCREENED FOR MENTAL HEALTH</th>
                                                            <td>${patientContact.mentalHealthScreening.screenedForMentalHealth}</td>


                                                        <c:if test="${patientContact.mentalHealthScreening.screenedForMentalHealth.name=='YES'}">

                                                                <td> &nbsp; &nbsp;  &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp;  &nbsp; </td>
                                                                <th>DATE SCREENED</th>
                                                                <td><spring:eval expression="patientContact.mentalHealthScreening.dateScreened"/></td>
                                                            </tr>
                                                            <tr>
                                                                <th>IDENTIFIED WITH SYMPTOMS</th>
                                                                <td>${patientContact.mentalHealthScreening.risk.name}</td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <th>IDENTIFIED SYMPTOMS</th>
                                                                <td>${patientContact.mentalHealthScreening.identifiedRisks}</td>
                                                            </tr>
                                                            <tr>
                                                                <th>SUPPORT</th>
                                                                <td>${patientContact.mentalHealthScreening.support.name}</td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <th>SUPPORTED BY</th>
                                                                <td>${patientContact.mentalHealthScreening.supports}</td>
                                                            </tr>
                                                        </c:if>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="panel panel-success">
                                            <div class="panel-heading" role="tab" id="tbTPTPanel">
                                                <h5 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#info" href="#tbTPT" class="btn btn-block collapsed text-left">
                                                        <span class="white">TP/TPT Info</span>
                                                    </a>
                                                </h5>
                                            </div>
                                            <div id="tbTPT" class="panel-collapse collapse" role="tabpanel" aria-labelledby="tbTPT">
                                                <div class="panel-body">
                                                    <table class="table table-striped table-bordered table-responsive">
                                                        <tr>
                                                            <th>SCREENED FOR TB</th>
                                                            <td>${patientContact.tbIpt.screenedForTb.name}</td>


                                                            <c:if test="${patientContact.tbIpt.screenedForTb.name=='YES' || patientContact.tbIpt.screenedForTb.name=='Yes'}">

                                                            <td> &nbsp; &nbsp;  &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp;  &nbsp; </td>
                                                            <th>DATE SCREENED</th>
                                                            <td><spring:eval expression="patientContact.tbIpt.dateScreened"/></td>

                                                        </tr>
                                                        <tr>
                                                            <th>IDENTIFIED WITH SYMPTOMS</th>
                                                            <td>${patientContact.tbIpt.identifiedWithTb.name}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>IDENTIFIED TB SYMPTOMS</th>
                                                            <td>${patientContact.tbIpt.tbSymptoms}</td>
                                                        </tr>
                                                        <tr>
                                                            <th>REFERRED FOR TB INVESTIGATION</th>
                                                            <td>${patientContact.tbIpt.referredForInvestigation.name}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>SCREENED BY HCW</th>
                                                            <td>${patientContact.tbIpt.screenedByHcw}</td>
                                                        </tr>
                                                        <tr>
                                                            <c:if test="${patientContact.tbIpt.screenedByHcw.name=='YES' || patientContact.tbIpt.screenedByHcw.name=='Yes'}">
                                                                <th>IDENTIFIED WITH TB BY HCW</th>
                                                                <td>${patientContact.tbIpt.identifiedWithTbByHcw.name}</td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            </c:if>
                                                            <th>ON TB TREATMENT</th>
                                                            <td>${patientContact.tbIpt.onTBTreatment}</td>
                                                        </tr>
                                                        <c:if test="${patientContact.tbIpt.onTBTreatment.name=='YES' || patientContact.tbIpt.onTBTreatment.name=='Yes'}">
                                                            <tr>
                                                                <th>DATE STARTED TB TREATMENT</th>
                                                                <td><spring:eval expression="patientContact.tbIpt.dateStartedTreatment"/></td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <th>DATE COMPLETED TB TREATMENT</th>
                                                                <td><spring:eval expression="patientContact.tbIpt.dateCompletedTreatment"/></td>
                                                            </tr>
                                                        </c:if>
                                                        <tr>
                                                            <th>ELIGIBLE FOR TPT</th>
                                                            <td>${patientContact.tbIpt.eligibleForIpt.name}</td>
                                                            <%--<c:if test="${patientContact.tbIpt.eligibleForIpt.name}=='YES'">--%>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <th>REFERRED FOR TPT</th>
                                                                <td>${patientContact.tbIpt.referredForIpt.name}</td>
                                                            <%--</c:if>--%>
                                                        </tr>
                                                        <tr>
                                                            <th>STARTED ON TPT</th>
                                                            <td>${patientContact.tbIpt.startedOnIpt.name}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>ALREADY ON TPT</th>
                                                            <td>${patientContact.tbIpt.onIpt.name}</td>
                                                        </tr>
                                                        <%--<c:if test="${patientContact.tbIpt.startedOnIpt.name}=='YES' || ${patientContact.tbIpt.startedOnIpt.name}=='Yes'">--%>
                                                            <tr>
                                                                <th>DATE STARTED ON TPT</th>
                                                                <td><spring:eval expression="patientContact.tbIpt.dateStartedOnIpt"/></td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <th>DATE COMPLETED ON TPT</th>${patientContact.tbIpt.onIpt.name}
                                                                <td><spring:eval expression="patientContact.tbIpt.dateCompletedOnIpt"/></td>
                                                            </tr>
                                                        <%--</c:if>--%>
                                                        <%--<c:if test="${patientContact.tbIpt.onIpt.name}=='YES' || ${patientContact.tbIpt.onIpt.name}=='Yes'">--%>
                                                            <tr>
                                                                <th>DATE STARTED TPT</th>
                                                                <td><spring:eval expression="patientContact.tbIpt.dateStartedIpt"/></td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <th>DATE COMPLETED TPT</th>
                                                                <td><spring:eval expression="patientContact.tbIpt.dateCompletedIpt"/></td>
                                                            </tr>
                                                        <%--</c:if>--%>
                                                        </c:if>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>

<%--                                        <div class="panel panel-success">--%>
<%--                                            <div class="panel-heading" role="tab" id="vlCD4Panel">--%>
<%--                                                <h5 class="panel-title">--%>
<%--                                                    <a  data-toggle="collapse" data-parent="#info" href="#vlAndCD4" class="btn btn-block collapsed text-left" >--%>
<%--                                                        <span class="white ">Viral Load/CD4 Count Info</span>--%>
<%--                                                    </a>--%>
<%--                                                </h5>--%>
<%--                                            </div>--%>
<%--                                            <div id="vlAndCD4" class="panel-collapse collapse" role="tabpanel" aria-labelledby="vlAndCD4">--%>
<%--                                                <div class="panel-body">--%>
<%--                                                    <table class="table table-striped table-bordered table-responsive">--%>
<%--                                                        <tr>--%>
<%--                                                            <th>VL/CD4 COUNT TEST DONE </th>--%>
<%--                                                            <td>${patientContact.investigationTest.testDone.name}</td>--%>

<%--                                                        </tr>--%>
<%--                                                        <c:if test="${patientContact.investigationTest.testDone.name=='Yes' || patientContact.investigationTest.testDone.name=='YES'}">--%>
<%--                                                            <tr>--%>
<%--                                                                <th>VL/CD4 SAMPLING DATE </th>--%>
<%--                                                                <td><spring:eval expression="patientContact.investigationTest.dateTaken"/></td>--%>
<%--                                                                <td> &nbsp; &nbsp;  &nbsp; &nbsp; </td>--%>
<%--                                                                <td> &nbsp; &nbsp; &nbsp;  &nbsp; </td>--%>
<%--                                                                <th>TEST TYPE</th>--%>
<%--                                                                <td>${patientContact.investigationTest.testType}</td>--%>
<%--                                                            </tr>--%>
<%--                                                            <tr>--%>
<%--                                                                <th>VL/CD4 RESULTS AVAILABLE</th>--%>
<%--                                                                <td>${patientContact.investigationTest.haveResult}</td>--%>
<%--                                                                <td> &nbsp; &nbsp;  &nbsp; &nbsp; </td>--%>
<%--                                                                <td> &nbsp; &nbsp; &nbsp;  &nbsp; </td>--%>
<%--                                                                <th>VL/CD4 RESULT</th>--%>
<%--                                                                <td><span class="(${patientContact.investigationTest.result!=null && patientContact.investigationTest.result>1000?'text-danger':'text-success'})">${patientContact.investigationTest.result!=null? patientContact.investigationTest.result: patientContact.investigationTest.tnd}</span></td>--%>
<%--                                                            </tr>--%>
<%--                                                            <tr>--%>
<%--                                                                <th>SOURCE</th>--%>
<%--                                                                <td>${patientContact.investigationTest.source}</td>--%>
<%--                                                                <td> &nbsp; &nbsp;  &nbsp; &nbsp; </td>--%>
<%--                                                                <td> &nbsp; &nbsp; &nbsp;  &nbsp; </td>--%>
<%--                                                                <th>NEXT LAB DUE</th>--%>
<%--                                                                <td><spring:eval expression="patientContact.investigationTest.nextTestDate"/></td>--%>
<%--                                                            </tr>--%>

<%--                                                        </c:if>--%>
<%--                                                    </table>--%>
<%--                                                </div>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>

                                        <div class="panel panel-success">
                                            <div class="panel-heading" role="tab" id="referralPanel">
                                                <h5 class="panel-title">
                                                    <a  data-toggle="collapse" data-parent="#info" href="#referrals" class="btn btn-block collapsed text-left" >
                                                        <span class="white ">Referrals Info</span>
                                                    </a>
                                                </h5>
                                            </div>
                                            <div id="referrals" class="panel-collapse collapse" role="tabpanel" aria-labelledby="referrals">
                                                <div class="panel-body">
                                                    <table class="table table-striped table-bordered table-responsive">
                                                        <tr>
                                                            <th colspan="4" class="warning">
                                                                <span style="color: orange;">GENERAL INFO</span>
                                                                <hr style="border: 1px solid orange;"/>
                                                            </th>

                                                        </tr>
                                                        <tr>
                                                            <th>MADE REFERRAL </th>
                                                            <td>${patientContact.referral.hasReferred.name}</td>

                                                        </tr>
                                                        <c:if test="${patientContact.referral.hasReferred.name=='Yes' || patientContact.referral.hasReferred.name=='YES'}">
                                                            <tr>
                                                                <th>REFERRAL DATE </th>
                                                                <td><spring:eval expression="patientContact.referral.referralDate"/></td>
                                                                <th>EXPECTED VISIT DATE</th>
                                                                <td><spring:eval expression="patientContact.referral.expectedVisitDate"/></td>
                                                            </tr>
                                                            <tr>
                                                                <th>ORGANIZATION REFERRED</th>
                                                                <td>${patientContact.referral.organisation}</td>
                                                                <th>DATE ATTENDED TO</th>
                                                                <td><spring:eval expression="patientContact.referral.dateAttended"/></td>
                                                            </tr>
                                                            <tr>
                                                                <th>ATTENDING OFFICER</th>
                                                                <td>${patientContact.referral.attendingOfficer}</td>
                                                                <th>OFFICER DESIGNATION</th>
                                                                <td>${patientContact.referral.designation}</td>
                                                            </tr>
                                                            <tr></tr>
                                                            <tr>
                                                                <th colspan="4" class="danger">
                                                                    <span style="color: red;">SERVICES REQUESTED</span>
                                                                    <hr style="border: 1px solid red;"/>
                                                                </th>
                                                            </tr>
                                                            <tr>
                                                                <th >HIV STI SERVICES REQUESTED</th>
                                                                <td>${patientContact.referral.hivStiServicesReq}</td>
                                                                <th>OI ART SERVICES REQUESTED</th>
                                                                <td>${patientContact.referral.oiArtReq}</td>
                                                            </tr>
                                                            <tr>
                                                                <th>SRH SERVICES REQUESTED</th>
                                                                <td>${patientContact.referral.srhReq}</td>
                                                                <th>LAB SERVICES REQUESTED</th>
                                                                <td>${patientContact.referral.laboratoryReq}</td>
                                                            </tr>
                                                            <tr>
                                                                <th>TB REQUESTED SERVICES</th>
                                                                <td>${patientContact.referral.tbReq}</td>
                                                                <th>PSYCH_SOCIAL SERVICES REQUESTED</th>
                                                                <td>${patientContact.referral.psychReq}</td>
                                                            </tr>
                                                           <tr>
                                                               <th>LEGAL SERVICES REQUESTED</th>
                                                               <td>${patientContact.referral.legalReq}</td>
                                                               <td> &nbsp; &nbsp;  &nbsp; &nbsp; </td>
                                                               <td> &nbsp; &nbsp; &nbsp;  &nbsp; </td>
                                                           </tr>
                                                            <tr></tr>
                                                            <tr>
                                                                <th colspan="4" class="success">
                                                                    <span style="color: green;">SERVICES AVAILED</span>
                                                                    <hr style="border: 1px solid green;"/>
                                                                </th>
                                                            </tr>
                                                            <tr>
                                                                <th>HIV/STI SERVICES AVAILED</th>
                                                                <td>${patientContact.referral.hivStiServicesAvailed}</td>
                                                                <th>OI ART SERVICES AVAILED</th>
                                                                <td>${patientContact.referral.oiArtAvailed}</td>
                                                            </tr>
                                                            <tr>
                                                                <th>SRH SERVICES AVAILED</th>
                                                                <td>${patientContact.referral.srhAvailed}</td>
                                                                <th>LAB SERVICES AVAILED</th>
                                                                <td>${patientContact.referral.laboratoryAvailed}</td>
                                                            </tr>
                                                            <tr>
                                                                <th>TB SERVICES AVAILED</th>
                                                                <td>${patientContact.referral.tbAvailed}</td>
                                                                <th>PSYCHO_SOCIAL SERVICES AVAILED </th>
                                                                <td>${patientContact.referral.psychAvailed}</td>
                                                            </tr>
                                                            <tr>
                                                                <th>LEGAL SERVICES AVAILED</th>
                                                                <td>${patientContact.referral.legalAvailed}</td>
                                                                <th></th>
                                                                <td></td>
                                                            </tr>
                                                        </c:if>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="panel panel-success">
                                            <div class="panel-heading" role="tab" id="contactPanel">
                                                <h5 class="panel-title">
                                                    <a  data-toggle="collapse" data-parent="#info" href="#contactData" class="btn btn-block collapsed text-left" >
                                                        <span class="white text-left">Contact Info</span>
                                                    </a>
                                                </h5>
                                            </div>
                                            <div id="contactData" class="panel-collapse collapse" role="tabpanel" aria-labelledby="contactData">
                                                <div class="panel-body">
                                                    <table class="table table-striped table-bordered table-responsive">
                                                        <tr>
                                                            <th colspan="6" class="warning">
                                                                <span style="color: orangered;">GENERAL INFO</span>
                                                                <hr style="border: 1px solid orangered;"/>
                                                            </th>

                                                        </tr>
                                                        <tr>
                                                            <th>DATE OF CONTACT </th>
                                                            <td><spring:eval expression="patientContact.contact.contactDate" /></td>

                                                        </tr>

                                                            <tr>
                                                                <th>LAST CLINIC APPOINTMENT DATE </th>
                                                                <td><spring:eval expression="patientContact.contact.lastClinicAppointmentDate"/></td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <th>ATTENDED CLINIC APPOINTMENT</th>
                                                                <td>${patientContact.contact.attendedClinicAppointment.name}</td>
                                                            </tr>
                                                            <tr>
                                                                <th>NEXT CLINIC APPOINTMENT DATE</th>
                                                                <td><spring:eval expression="patientContact.contact.nextClinicAppointmentDate"/></td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <th>CARE LEVEL BEFORE ASSESSMENT</th>
                                                                <td>${patientContact.contact.careLevel.name}</td>
                                                            </tr>
                                                            <tr>
                                                                <th>CARE LEVEL AFTER ASSESSMENT</th>
                                                                <td>${patientContact.contact.careLevelAfterAssessment.name}</td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                                <th>PLACE OF CONTACT</th>
                                                                <td>${patientContact.contact.location.name}</td>
                                                            </tr>
                                                        <tr>
                                                            <th>TYPE OF CONTACT</th>
                                                            <td>${patientContact.contact.contactPhoneOption}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>NUMBER OF SMS</th>
                                                            <td>${patientContact.contact.numberOfSms}</td>
                                                        </tr>
                                                        <tr>
                                                            <th>CONTACTED BY</th>
                                                            <td>${patientContact.contact.contactMadeBy}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>EAC SESSION DONE</th>
                                                            <td>${patientContact.contact.eac.name}</td>
                                                        </tr>
                                                        <tr>
                                                            <th>EAC SESSION 1</th>
                                                            <td>${patientContact.contact.eac1==1?'YES':'NO'}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <th>EAC SESSION 2</th>
                                                            <td>${patientContact.contact.eac2==1?'YES':'NO'}</td>
                                                        </tr>
                                                        <tr>
                                                            <th>EAC SESSION 3</th>
                                                            <td>${patientContact.contact.eac3==1?'YES':'NO'}</td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                            <td> &nbsp; &nbsp; &nbsp; &nbsp; </td>
                                                        </tr>

                                                            <tr>
                                                                <th colspan="6" class="warning">
                                                                    <span style="color: orangered;">CONTACT ASSESSMENTS</span>
                                                                    <hr style="border: 1px solid orangered;"/>
                                                                </th>
                                                            </tr>
                                                            <tr>
                                                                <th >CLINIC ASSESSMENTS</th>
                                                                <td COLSPAN="5">${patientContact.contact.clinicalAssessments}</td>

                                                            </tr>
                                                            <tr>
                                                                <th>NON CLINICAL ASSESSMENTS</th>
                                                                <td colspan="5">${patientContact.contact.nonClinicalAssessments}</td>
                                                            </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </div>

                            <div class="form-group row pull-right">
                                <button class="btn btn-primary" type="submit" id="back" name="_eventId_back">&Lt;&Lt;Back</button>
                                <button class="btn btn-primary " type="submit" id="next" name="_eventId_save">Save&Gt;&Gt;</button>
                                <button class="btn btn-primary " type="submit" id="cancel" name="_eventId_cancel">Cancel</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../../template/footer.jspf" %>
<script type="text/javascript">
    /*$("#reason").change(function () {
        $("#currentElement").val($(window).scrollTop());
        $("form").attr("action", "reload-form").submit();
    });

    $("#actionTaken").change(function () {
        $("#currentElement").val($(window).scrollTop());
        $("form").attr("action", "reload-form").submit();
    });

    $("#province").change(function () {
        $("#currentElement").val($(window).scrollTop());
        $("form").attr("action", "reload-form").submit();
    });

    $("#district").change(function () {
        $("#currentElement").val($(window).scrollTop());
        $("form").attr("action", "reload-form").submit();
    });

    $("#userLevel").change(function () {
        $this = $(this);
        var userLevel = $.trim($this.val());
        if (userLevel !== "" || userLevel !== 1) {
            $("#currentElement").val($(window).scrollTop());
            $("form").attr("action", "reload-form").submit();
        }
    });*/
    $("#eac").change(function () {
        var eac=$("#eac").val();
        if(eac==1){
            $(".eac").removeClass("hide")
            $("#eac-head").addClass("panel-heading")
        }else{
            $(".eac").addClass("hide")
            $("#eac-head").removeClass("panel-heading")
        }
    })
    $("#viralLoadResultTaken").change(function () {
        var name = $("#viralLoadResultTaken :selected").text().toLowerCase();
        if (name === "yes") {
            $(".viralLoad").removeClass("hide");
        } else {
            $(".viralLoadVal").val('');
            $(".viralLoad").addClass("hide");
        }
    });
    $("#cd4CountResultTaken").change(function () {
        var name = $("#cd4CountResultTaken :selected").text().toLowerCase();
        if (name === "yes") {
            $(".cd4Count").removeClass("hide");
        } else {
            $(".cd4CountVal").val('');
            $(".cd4Count").addClass("hide");
        }
    });
    $("#location").change(function () {
        var name = $("#location :selected").text().toLowerCase();
        if (name === "phone") {
            $(".locationPhone").removeClass("hide");
        } else {
            $("#contactPhoneOption").val('');
            $(".locationPhone").addClass("hide");
        }
    });
    $("#contactPhoneOption").change(function () {
        var name = $("#contactPhoneOption :selected").text().toLowerCase();
        if (name === "sms") {
            $(".contactPhoneOptionSms").removeClass("hide");
        } else {
            $("#numberOfSms").val('');
            $(".contactPhoneOptionSms").addClass("hide");
        }
    });
    var patientId = "<c:out value="${patient.id}"/>"
    $("#visitOutcome").change(function () {
        var name = $.trim($("#visitOutcome :selected").text().toLowerCase());
        console.log("NAME:"+name)
        if (name === "changed location") {
            location.href = path + "/patient/change-facility/item.form?id=" + patientId;
        } else if (name === "deceased") {
            location.href = path + "/patient/patient-death/item.form?id=" + patientId;
        }
    });
    $("#reason").change(function () {
        var name = $("#reason :selected").text().toLowerCase();
        if (name === "other") {
            $(".reason").removeClass("hide");
        } else {
            $("#otherReason").val('');
            $(".reason").addClass("hide");
        }
    });
    $(function () {
        window.onload = function () {
            var name = $("#viralLoadResultTaken :selected").text().toLowerCase();
            if (name === "yes") {
                $(".viralLoad").removeClass("hide");
            }
            var name = $("#cd4CountResultTaken :selected").text().toLowerCase();
            if (name === "yes") {
                $(".cd4Count").removeClass("hide");
            }
            var name = $("#location :selected").text();
            if (name === "Phone") {
                $(".locationPhone").removeClass("hide");
            }
            var name = $("#contactPhoneOption :selected").text().toLowerCase();
            if (name === "sms") {
                $(".contactPhoneOptionSms").removeClass("hide");
            }
            var name = $("#reason :selected").text().toLowerCase();
            if (name === "other") {
                $(".reason").removeClass("hide");
            }
            var eac=$("#eac").val();
            if(eac==1){
                $(".eac").removeClass("hide")
                $("#eac-head").addClass("panel-heading")
            }else{
                $(".eac").addClass("hide")
                $("#eac-head").removeClass("panel-heading")
            }
        };
    });
    $(window).scrollTop("<c:out value="${item.currentElement}"/>");
    //actionTaken
</script>