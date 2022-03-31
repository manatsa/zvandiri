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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back
                    To ${patient.name} Dashboard</a><br/>
                <div class="row">
                    <div class="col-lg-12">
                        <br/>
                        <b class="titleHeader"><a href="item.form?catId=${item.id}">Add Item </a></b><br/><br/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Certificate Number</th>
                                    <th>Date Received Mentorship</th>
                                    <th>Date Issued</th>
                                    <th>Mentorship Type</th>
                                    <th>Assigned Phone</th>
                                    <th>Phone Model</th>
                                    <th>Serial Number</th>
                                    <th>Phone Status</th>
                                    <th>Issued Bicycle</th>
                                    <th>Province</th>
                                    <th>District</th>
                                    <th>&nbsp;</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="act" items="${items}">
                                    <tr>
                                        <td>${act.certificateNumber}</td>
                                        <td><spring:eval expression="act.dateReceivedMentorship"/></td>
                                        <td><spring:eval expression="act.dateIssued"/></td>
                                        <td>${act.catsMentorship}</td>
                                        <td>${act.assignedPhone}</td>
                                        <td>${act.phoneModel}</td>
                                        <td>${act.serialNumber}</td>
                                        <td>${act.phoneStatus}</td>
                                        <td>${act.issuedBicycle}</td>
                                        <td>${act.district.province.name}</td>
                                        <td>${act.district.name}</td>
                                        <td>
                                            <a href="item.form?itemId=${act.id}">Edit</a> |
                                            <a href="item.delete?id=${act.id}">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <br/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>