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
                    <div class="col-lg-6"><a href="${page}/beneficiary/arv-hist/item.list?id=${patient.id}">Next To ARV Use History &DoubleRightArrow;</a></div>
                </div> <br/>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">Substance Use Detail</b>  <c:if test="${canEdit}">| <a href="${page}/beneficiary/substance/item.form?patientId=${patient.id}">Add Substance Use Hist </a></c:if>
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
                                        <th>&nbsp;</th>
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
                                            <td>
                                                <a href="${page}/beneficiary/substance/item.form?itemId=${sub.id}">Edit</a> |
<%--                                                <a href="${page}/beneficiary/substance/item.delete?id=${sub.id}">Delete</a>--%>
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