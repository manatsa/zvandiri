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
                        <br/>
                        <b class="titleHeader">${item.arvMedicine.name} : Adverse Effects</b>  <c:if test="${canEdit}">| <a href="item.form?arvHistId=${item.id}">Add ${item.arvMedicine.name} : Adverse Effect Item </a></c:if>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Event</th>
                                        <th>Date Commenced</th>
                                        <th>Status</th>
                                        <th>Source</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="eff" items="${items}">
                                        <tr>
                                            <td>${eff.event}</td>
                                            <td><spring:eval expression="eff.dateCommenced"/></td>
                                            <td>${eff.status.name}</td>
                                            <td>${eff.source.name}</td>
                                            <td>
                                                <a href="item.form?itemId=${eff.id}">Edit</a> |
<%--                                                <c:if test="${canEdit}"><a href="item.delete?id=${eff.id}">Delete</a></c:if>--%>
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