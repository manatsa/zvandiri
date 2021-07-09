<%@include file="../template/header.jspf"%>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">${pageTitle}</div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To
                Reports DashBoard Home</a><br /> <span class="text-error right" style="text-align: right;">Invisible columns will be visible in exported data!</span>
            <%@include file="../template/searchClientFragment.jspf"%>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Date of Birth</th>
                        <th>Age</th>
                        <th>Gender</th>
                        <th>Status</th>
                        <td>IsCATS</td>
                        <td>Address</td>
                        <td>Address 2</td>
                        <td>Mobile Number</td>
                        <td>Secondary Number</td>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Date of Birth</th>
                        <th>Age</th>
                        <th>Gender</th>
                        <th>Status</th>
                        <td>IsCATS</td>
                        <td>Address</td>
                        <td>Address 2</td>
                        <td>Mobile Number</td>
                        <td>Secondary Number</td>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td>${item.name}</td>
                                <td><spring:eval expression="item.dateOfBirth" /></td>
                                <td>${item.age}</td>
                                <td>${item.gender.name}</td>
                                <td>${item.status.name}</td>
                                <td>${item.cat.name}</td>
                                <td>${item.address}</td>
                                <td>${item.address1}</td>
                                <td>${item.mobileNumber}</td>
                                <td>${item.secondaryMobileNumber}</td>
                                <td>${item.primaryClinic.district.province.name}</td>
                                <td>${item.primaryClinic.district.name}</td>
                                <td>${item.primaryClinic.name}</td>
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
