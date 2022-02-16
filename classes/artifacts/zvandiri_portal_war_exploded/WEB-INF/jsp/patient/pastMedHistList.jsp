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
                    <div class="col-lg-6"><a href="${page}/beneficiary/srh/item.list?id=${patient.id}">Next To SRH History &DoubleRightArrow;</a></div>
                </div><br/>                            
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">Hospitalisation History</b>  <c:if test="${canEdit}">| <a href="item.form?patientId=${patient.id}">Add Hospitalisation History Item </a></c:if>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>When</th>
                                        <th>Region</th>
                                        <th>District</th>
                                        <th>Clinic/ Hospital</th>
                                        <th>Cause</th>
                                        <th>Outcome <smal>(Resolved)</smal></th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="medHist" items="${medHists}">
                                        <tr>
                                            <td><spring:eval expression="medHist.hospWhen"/></td>
                                            <td>${medHist.primaryClinic.district.province.name}</td>
                                            <td>${medHist.primaryClinic.district.name}</td>
                                            <td>${medHist.primaryClinic.name}</td>
                                            <td>${medHist.hospCause.name}</td>
                                            <td>${medHist.outcome.name}</td>
                                            <td>
                                                <a href="item.form?itemId=${medHist.id}">Edit</a> |
<%--                                                <c:if test="${canEdit}"><a href="item.delete?id=${medHist.id}">Delete</a></c:if>--%>
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