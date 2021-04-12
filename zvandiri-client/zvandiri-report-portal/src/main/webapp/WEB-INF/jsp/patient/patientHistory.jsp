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
                        <b class="titleHeader">Past Medical History  Detail</b><hr/>
                        <b class="titleHeader">Hospitalisation History</b>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>When</th>
                                        <th>Where</th>
                                        <th>Cause</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="medHist" items="${medHists}">
                                        <tr>
                                            <td><spring:eval expression="medHist.hospWhen"/></td>
                                            <td>${medHist.hospWhere}</td>
                                            <td>${medHist.hospCause}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <b class="titleHeader">Opportunistic Infection Detail</b>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Infection</th>
                                        <th>Diagnosis Date</th>
                                        <th>Medication</th>
                                        <th>Current Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="infection" items="${infections}">
                                        <tr>
                                            <td>${infection.chronicInfection.name}</td>
                                            <td><spring:eval expression="infection.infectionDate"/></td>
                                            <td>${infection.medication}</td>
                                            <td>${infection.currentStatus.name}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <b class="titleHeader">Hiv co-infection  Detail</b>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Infection</th>
                                        <th>Diagnosis Date</th>
                                        <th>Medication</th>
                                        <th>Resolution</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="hiv" items="${hivInfections}">
                                        <tr>
                                            <td>${hiv.hivCoInfection.name}</td>
                                            <td><spring:eval expression="hiv.infectionDate"/></td>
                                            <td>${hiv.medication}</td>
                                            <td>${hiv.resolution}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div><br/>
                        <b class="titleHeader">ARV Hist Detail</b>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Medicine</th>
                                        <th>Start Date</th>
                                        <th>End Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="arvHist" items="${arvHists}">
                                        <tr>
                                            <td>${arvHist.arvMedicine.name}</td>
                                            <td><spring:eval expression="arvHist.startDate"/></td>
                                            <td><spring:eval expression="arvHist.endDate"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div><br/>
                        <b class="titleHeader">Mental Health  Detail</b><br/><br/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Health Condition</th>
                                        <th>Past</th>
                                        <th>Current</th>
                                        <th>Medication</th>
                                        <td>Period</td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="mental" items="${mentalHealths}">
                                        <tr>
                                            <td>${mental.mentalHealth.name}</td>
                                            <td>${mental.past}</td>
                                            <td>${mental.current}</td>
                                            <td>${mental.medication}</td>
                                            <td><spring:eval expression="mental.startDate"/> - <spring:eval expression="mental.endDate"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <b class="titleHeader">SRH History Detail</b><hr/>
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
                            </table> 
                        </c:if>
                        <b class="titleHeader">Obstetric History  Detail</b><hr/>
                        <c:if test="${obstericHist != null}">
                            <table class="table-condensed" width="100%">
                                <tr>
                                    <th>Have you ever been pregnant</th>
                                    <td>${obstericHist.pregnant}</td>
                                </tr>
                                <c:if test="${preg}">
                                    <tr>
                                        <th>How many times</th>
                                        <td>${obstericHist.numberOfPreg}</td>
                                    </tr>
                                    <tr>
                                        <th>Are you currently pregnant</th>
                                        <td>${obstericHist.pregCurrent}</td>
                                    </tr>
                                    <tr>
                                        <th>Have you ever given birth</th>
                                        <td>${obstericHist.givenBirth}</td>
                                    </tr>
                                </c:if>
                                <c:if test="${birth}">
                                    <tr>
                                        <th>Birth Type</th>
                                        <td>${obstericHist.pregType}</td>
                                    </tr>
                                    <tr>
                                        <th>How many children</th>
                                        <td>${obstericHist.children}</td>
                                    </tr>
                                    <tr>
                                        <th>Children HIV Status</th>
                                        <td>${obstericHist.childrenHivStatus}</td>
                                    </tr>
                                </c:if>
                            </table>            
                        </c:if>
                        <b class="titleHeader">Social History Detail</b><hr/>
                        <c:if test="${socialHist != null}">
                            <table class="table-condensed" width="100%">
                                <tr>
                                    <th>Who do you currently live with</th>
                                    <td>${socialHist.liveWith}</td>
                                </tr>
                                <tr>
                                    <th>Have you ever been physically, sexually or emotionally abused</th>
                                    <td>${socialHist.abuse.name}</td>
                                </tr>
                                <c:if test="${abuse}">
                                    <tr>
                                        <th>Did you tell anyone</th>
                                        <td>${socialHist.dosclosure}</td>
                                    </tr>
                                    <tr>
                                        <th>Outcome</th>
                                        <td>${socialHist.abuseOutcome.name}</td>
                                    </tr>
                                    <tr>
                                        <th>Do you feel safe where you live now</th>
                                        <td>${socialHist.feelSafe.name}</td>
                                    </tr>
                                </c:if>
                            </table>            
                        </c:if> 
                        <b class="titleHeader">Mental Health Screening Detail</b><hr/>
                        <c:if test="${mentalHealthScreen != null}">
                            <table class="table-condensed" width="100%">
                                 <tr>
                                    <th>Screened For Mental Health</th>
                                    <td>${item.screenedForMentalHealth}</td>
                                </tr>
                                <tr>
                                    <th>Identified Risks</th>
                                    <td>${item.identifiedRisk}</td>
                                </tr>
                                <tr>
                                    <th>Action Taken</th>
                                    <td>${item.actionTaken.name}</td>
                                </tr>
                                <tr>
                                    <th>Other</th>
                                    <td>${item.other}</td>
                                </tr>
                                 <tr>
                                    <th>Rescreened For Mental Health</th>
                                    <td>${item.mentalScreenResult}</td>
                                </tr>
                                <tr>
                                    <th>Identified Risks</th>
                                    <td>${item.identifiedRisk}</td>
                                </tr>
                                <tr>
                                    <th>Action Taken</th>
                                    <td>${item.actionTaken.name}</td>
                                </tr>
                                </c:if>
                            </table>            
                        </c:if> 
                        <b class="titleHeader">Substance Use Detail</b>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Substance</th>
                                        <th>Current</th>
                                        <th>Lifetime</th>
                                        <th>Intervention</th>
                                        <th>Type/ Amount</th>
                                        <th>How Long</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="sub" items="${substances}">
                                        <tr>
                                            <td>${sub.substance.name}</td>
                                            <td>${sub.current.name}</td>
                                            <td>${sub.past.name}</td>
                                            <td>${sub.drugIntervention.name}</td>
                                            <td>${sub.typeAmount}</td>
                                            <td>${sub.howLong}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <b class="titleHeader">Family History  Detail</b><hr/>
                        <c:if test="${family != null}">
                            <table class="table-condensed" width="100%">
                                <tr>
                                    <th>Orphan Status</th>
                                    <td>${family.orphanStatus.name}</td>
                                </tr>
                                <tr>
                                    <th>Number of siblings</th>
                                    <td>${family.numberOfSiblings}</td>
                                </tr>
                            </table> 
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>