<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">${pageTitle}</div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To
                Reports DashBoard Home</a><br/>
            <%@include file="../template/searchClientFragment.jspf" %>
            <div class="row">
                <div class="panel-footer" style="text-align: right">
                    Export/ View As <a href="${page}${excelExport}"> <img
                        src="<c:url value="/resources/images/excel.jpeg"/>"/>
                </a>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Gender</th>
                        <th>Date Of Birth</th>
                        <th>Facility</th>
                        <th>District</th>
                        <th>Region</th>
                        <th>Phone Make</th>
                        <th>Phone Model</th>
                        <th>Line 1</th>
                        <th>Line 2</th>
                        <th>IMEI 1</th>
                        <th>IMEI 2</th>
                        <th>Condition</th>
                        <th>Status</th>
                        <th>Issues</th>
                        <th>Date Issued</th>
                        <th>Date Recovered</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Gender</th>
                        <th>Date Of Birth</th>
                        <th>Facility</th>
                        <th>District</th>
                        <th>Region</th>
                        <th>Phone Make</th>
                        <th>Phone Model</th>
                        <th>Line 1</th>
                        <th>Line 2</th>
                        <th>IMEI 1</th>
                        <th>IMEI 2</th>
                        <th>Condition</th>
                        <th>Status</th>
                        <th>Issues</th>
                        <th>Date Issued</th>
                        <th>Date Recovered</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td>${item.cadre.name}</td>
                                <td>${item.cadre.gender}</td>
                                <td><spring:eval expression="item.cadre.dateOfBirth"/></td>
                                <td>${item.cadre.primaryClinic.name}</td>
                                <td>${item.cadre.primaryClinic.district.name}</td>
                                <td>${item.cadre.primaryClinic.district.province.name}</td>
                                <td>${item.phoneMake}</td>
                                <td>${item.phoneModel}</td>
                                <td>${item.msisdn1}</td>
                                <td>${item.msisdn2}</td>
                                <td>${item.imei1}</td>
                                <td>${item.imei2}</td>
                                <td>${item.phoneCondition.name}</td>
                                <td>${item.phoneStatus.name}</td>
                                <td>${item.phoneIssues}</td>
                                <td><spring:eval expression="item.dateIssued"/></td>
                                <td><spring:eval expression="item.dateRecovered"/></td>
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
