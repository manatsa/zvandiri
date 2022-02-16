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
                    <div class="col-lg-6"><a href="${page}/beneficiary/chronic-infection/item.list?id=${patient.id}">Next To OI History &DoubleRightArrow;</a></div>
                </div><br/>
                <div class="row">
                    <div class="col-lg-12">
                        <br/>
                        <b class="titleHeader">ARV Hist Detail</b>  <c:if test="${canEdit}">| <a href="item.form?patientId=${patient.id}">Add ARV Hist Item </a></c:if>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>ARV Regimen</th>
                                        <th>Start Date</th>
                                        <th>End Date</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="arvHist" items="${arvHists}">
                                        <tr>
                                            <td>${arvHist.medicines}</td>
                                            <td><spring:eval expression="arvHist.startDate"/></td>
                                            <td><spring:eval expression="arvHist.endDate"/></td>
                                            <td>
                                                <a href="${page}/patient/arv-adverse-effects/item.list?id=${arvHist.id}">Adverse Effects</a> | 
                                                <a href="item.form?itemId=${arvHist.id}">Edit</a> |
<%--                                                <c:if test="${canEdit}"><a href="item.delete?id=${arvHist.id}">Delete</a></c:if>--%>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div><br/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>