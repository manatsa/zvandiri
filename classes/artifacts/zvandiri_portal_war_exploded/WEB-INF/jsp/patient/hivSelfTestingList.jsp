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
                    <div class="col-lg-10">
                        <div class="panel panel-default">                            
                            <%@include file="../template/dashboard/patientProfile.jspf" %>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">HIV Self-Testing Detail</b> | <a href="item.form?patientId=${person.id}">Add HIV Self-Testing Item </a>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Tested At Health Facility Result</th>
                                        <th>Self Test Kit Distributed?</th>
                                        <th>HIS Self Testing Result</th>
                                        <th>Home Based Testing Result</th>
                                        <th>Confirmatory Testing Result</th>
                                        <th>ART Initiation</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${items}">
                                        <tr>
                                            <td>${item.testedAtHealthFacilityResult}</td>
                                            <td>${item.selfTestKitDistributed}</td>
                                            <td>${item.hisSelfTestingResult}</td>
                                            <td>${item.homeBasedTestingResult}</td>
                                            <td>${item.confirmatoryTestingResult}</td>
                                            <td>${item.artInitiation}</td>
                                            <td>
                                                <a href="item.form?id=${item.id}">Edit</a> |
<%--                                                <a href="item.delete?id=${item.id}">Delete</a>--%>
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