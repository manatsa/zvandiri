<%@include file="../template/header.jspf"%>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">${pageTitle}</div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To
                Reports DashBoard Home</a><br /> 
            <%@include file="../template/searchClientFragment.jspf"%>
            <div class="row">
                <div class="panel-footer" style="text-align: right">
                    Export/ View As <a href="${page}${excelExport}"> <img
                        src="<c:url value="/resources/images/excel.jpeg"/>" />
                </a>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                            <th>OiNumber</th>
                            <th>Name</th>
                            <th>Gender</th>
                            <th>Date Of Birth</th>
                            <th>Date Joined</th>
                            <th>Address</th>
                            <th>Phone</th>
                            <th>Facility</th>
                            <th>District</th>
                            <th>Region</th>
                            <th>Last Contact Date</th>
                            <th>Last Care Level</th>
                        </thead>
                        <tfoot>
                            <th>OiNumber</th>
                            <th>Name</th>
                            <th>Gender</th>
                            <th>Date Of Birth</th>
                            <th>Date Joined</th>
                            <th>Address</th>
                            <th>Phone</th>
                            <th>Facility</th>
                            <th>District</th>
                            <th>Region</th>
                            <th>Last Contact Date</th>
                            <th>Last Care Level</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td>${item.patient.oINumber}</td>
                                <td>${item.patient.name}</td>
                                <td>${item.patient.gender}</td>
                                <td><spring:eval expression="item.patient.dateOfBirth" /></td>
                                <td><spring:eval expression="item.patient.dateJoined" /></td>
                                <td>${item.patient.address}</td>
                                <td>${item.patient.mobileNumber}</td>
                                <td>${item.patient.primaryClinic.name}</td>
                                <td>${item.patient.primaryClinic.district.name}</td>
                                <td>${item.patient.primaryClinic.district.province.name}</td>
                                <td><spring:eval expression="item.contactDate" /></td>
                                <td>${item.followUp.name}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <div class="panel-footer" style="text-align: right">
            Export/ View As <a href="${page}${excelExport}"> <img
                src="<c:url value="/resources/images/excel.jpeg"/>" />
        </a>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf"%>
<script type="text/javascript">
    $(".sidebar-nav").addClass("custom-side-bar-ref");
    $("#page-wrapper").addClass("main-wrp");
    // ensire toggle side bar is pointing right
    $("span.toggle-span").addClass("fa-long-arrow-right");
    $("span.toggle-span").removeClass("fa-long-arrow-left");
</script>
