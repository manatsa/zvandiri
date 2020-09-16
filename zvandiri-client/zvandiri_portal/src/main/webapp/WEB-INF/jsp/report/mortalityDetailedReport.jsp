<%@include file="../template/header.jspf"%>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">${pageTitle}</div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To
                Reports DashBoard Home</a><br />
            <%@include file="../template/searchClientFragment.jspf"%>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>OI/ ART Number</th>
                        <th>Age</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Support Group</th>
                        <th>Referrer</th>
                        <th>Date Deceased</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>OI/ ART Number</th>
                        <th>Age</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Support Group</th>
                        <th>Referrer</th>
                        <th>Date Deceased</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td>${item.name}</td>
                                <td>${item.oINumber}</td>
                                <td>${item.age}</td>
                                <td><spring:eval expression="item.dateOfBirth" /></td>
                                <td>${item.gender.name}</td>
                                <td>${item.primaryClinic.district.province.name}</td>
                                <td>${item.primaryClinic.district.name}</td>
                                <td>${item.primaryClinic.name}</td>
                                <td>${item.supportGroup.name}</td>
                                <td>${item.referer.name}</td>
                                <th>${item.dateModified}</th>
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
