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
                    <div class="col-lg-6"><a style="text-align: right;" href="${page}/beneficiary/socialhist/item.list?id=${patient.id}">Next To Social History &DoubleRightArrow;</a></div>
                </div> 
                <br/>
                <div class="row">
                    <div class="col-lg-12">
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
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>
                                        <a href="${page}/beneficiary/familyhist/item.form?itemId=${family.id}">Edit</a> | 
<%--                                        <a href="${page}/beneficiary/familyhist/item.delete?id=${family.id}">Delete</a>--%>
                                    </td>
                                </tr>
                            </table>            
                        </c:if>
                        <c:if test="${family == null}">
                            <c:if test="${canEdit}"><a href="${page}/beneficiary/familyhist/item.form?patientId=${patient.id}">Add Family History </a></c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>