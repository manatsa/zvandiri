<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="../template/message.jspf" %>
                <%@include file="../template/searchDateRangeFragment.jspf" %>
                <div class="row">
                    <div class="col-lg-12">
                        <table class="display itemList" cellspacing="0">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Age</th>
                                <th>Gender</th>
                                <th>Date of Contact</th>
                                <th>Level of Care</th>
                                <th>Reason</th>
                                <th>&nbsp;</th>
                            </tr>
                            </thead>
                            0776677617
                            <tbody>
                            <c:forEach var="contact" items="${contacts}">
                                <tr>
                                    <td>${contact.patient.name}</td>
                                    <td>${contact.patient.age}</td>
                                    <td>${contact.patient.gender.name}</td>
                                    <td><spring:eval expression="contact.contactDate"/></td>
                                    <td>${contact.levelOfCare.name}</td>
                                    <td>${contact.reason.name}</td>
                                    <td>
                                        <a href="${page}/beneficiary/contact/item.form?id=${contact.id}">Edit</a> |
                                            <%--                                            <a href="${page}/beneficiary/contact/item.delete?id=${contact.id}">Delete</a>--%>
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
<%@include file="../template/footer.jspf" %>