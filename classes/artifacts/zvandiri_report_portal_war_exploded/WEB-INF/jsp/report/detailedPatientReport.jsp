<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            Regions List
        </div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To Reports DashBoard Home</a><br/>
            <hr/>
            <%@include file="../template/searchPeriodFragment.jspf" %>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Viral Load</th>
                        <th>CD4 Count</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Date Joined</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Support Group</th>
                        <th>Mobile Number</th>
                        <th>Referrer</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Viral Load</th>
                        <th>CD4 Count</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Date Joined</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Support Group</th>
                        <th>Mobile Number</th>
                        <th>Referrer</th>
                        </tfoot>
                        <tbody>
                            <c:forEach var="item" items="${items}" >
                                <tr>
                                    <td>${item.name}</td>
                                    <td>${item.age}</td>
                                    <td>${item.viralLoad}</td>
                                    <td>${item.cd4Count}</td>
                                    <td><spring:eval expression="item.dateOfBirth"/></td>
                                    <td>${item.gender.name}</td>
                                    <td><spring:eval expression="item.dateJoin"/></td>
                                    <td>${item.primaryClinic.district.province.name}</td>
                                    <td>${item.primaryClinic.district.name}</td>
                                    <td>${item.primaryClinic.name}</td>
                                    <td>${item.supportGroup.name}</td>
                                    <td>${item.mobileNumber}</td>
                                    <td>${item.referer.name}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
            <div class="panel-footer" style="text-align: right">
                    Export/ View As
                    <a href="${page}${excelExport}">
                        <img src="<c:url value="/resources/images/excel.jpeg"/>"/>
                    </a>
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