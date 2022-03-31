<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">${pageTitle}</div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To
                Reports DashBoard Home</a><br/> <span class="text-error right" style="text-align: right;">Invisible columns will be visible in exported data!</span>
            <%@include file="../template/searchClientFragment.jspf" %>
            <div class="panel-footer" style="text-align: right">
                Export/ View As <a href="${page}${excelExport}"> <img
                    src="<c:url value="/resources/images/excel.jpeg"/>"/>
            </a>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>UIC</th>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Gender</th>
                        <th>IsCATS</th>
                        <th>IsYMM</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Date Of Entry</th>
                        <th>ARV Medicine</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        </thead>
                        <tfoot>
                        <th>UIC</th>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Gender</th>
                        <th>IsCATS</th>
                        <th>IsYMM</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Date Of Entry</th>
                        <th>ARV Medicine</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td>${item.patient.oINumber}</td>
                                <td>${item.patient.name}</td>
                                <td>${item.patient.age}</td>
                                <td>${item.patient.gender.name}</td>
                                <td>${item.patient.cat.name}</td>
                                <td>${item.patient.youngMumGroup.name}</td>
                                <td>${item.patient.primaryClinic.district.name}</td>
                                <td>${item.patient.primaryClinic.name}</td>
                                <td><spring:eval expression="item.dateCreated"/></td>
                                <td>${item.medicines}</td>
                                <td><spring:eval expression="item.startDate"/></td>
                                <td><spring:eval expression="item.endDate"/></td>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <div class="panel-footer" style="text-align: right">
            Export/ View As <a href="${page}${excelExport}"> <img
                src="<c:url value="/resources/images/excel.jpeg"/>"/>
        </a>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    $(".sidebar-nav").addClass("custom-side-bar-ref");
    $("#page-wrapper").addClass("main-wrp");
    // ensire toggle side bar is pointing right
    $("span.toggle-span").addClass("fa-long-arrow-right");
    $("span.toggle-span").removeClass("fa-long-arrow-left");
</script>
