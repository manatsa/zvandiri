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
                    <div class="col-lg-6">
                        <c:if test="${female}">
                            <a href="${page}/beneficiary/obstetric/item.list?id=${patient.id}">Next To Obstetric History &DoubleRightArrow;</a>
                        </c:if>
                        <c:if test="${!female}">
                            <a href="${page}/beneficiary/mental-health/item.list?id=${patient.id}">Next To Mental History &DoubleRightArrow;</a>
                        </c:if>                       

                    </div>
                </div> 
                <br/>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">Social History Detail</b><hr/>
                        <c:if test="${socialHist != null}">
                            <table class="table-condensed" width="100%">
                                <tr>
                                    <th>Who do you currently live with</th>
                                    <td>${socialHist.liveWith}</td>
                                </tr>
                                <tr>
                                    <th>Relationship</th>
                                    <td>${socialHist.relationship.name}</td>
                                </tr>
                                <tr>
                                    <th>Have you ever been physically, sexually or emotionally abused</th>
                                    <td>${socialHist.abuse.name}</td>
                                </tr>
                                <tr>
                                    <th>Types of abuse</th>
                                    <td>${socialHist.abuseTypes}</td>
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
                                    <tr>
                                        <th>Social Support</th>
                                        <td>${socialHist.socialSupport}</td>
                                    </tr>
                                    <tr>
                                        <th>Loss Of Other Significant Relationships</th>
                                        <td>${socialHist.lossOfSignificantRelationships}</td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>
                                        <a href="${page}/beneficiary/socialhist/item.form?itemId=${socialHist.id}">Edit</a> | 
<%--                                        <c:if test="${canEdit}"><a href="${page}/beneficiary/socialhist/item.delete?itemId=${socialHist.id}">Delete</a></c:if>--%>
                                        </td>
                                    </tr>
                                </table>            
                        </c:if>
                        <c:if test="${socialHist == null}">
                            <c:if test="${canEdit}"><a href="${page}/beneficiary/socialhist/item.form?patientId=${patient.id}">Add Social History </a></c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>