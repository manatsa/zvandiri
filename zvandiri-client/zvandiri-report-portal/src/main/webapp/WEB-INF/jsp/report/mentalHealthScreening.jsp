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
                        <th>Age</th>
                        <th>Gender</th>
                        <th>IsCATS</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Date Screened</th>
                        <th>Screening Type</th>
                        <th>Risk</th>
                        <th>Received Support</th>
                        <th>Referral</th>
                        <th>Diagnosis</th>
                        <th>Other Diagnosis</th>
                        <th>Intervention</th>
                        <th>Other Interventions</th>
                        </thead>
                        
                        <tfoot>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Gender</th>
                        <th>IsCATS</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Date Screened</th>
                        <th>Screening Type</th>
                        <th>Risk</th>
                        <th>Received Support</th>
                        <th>Referral</th>
                        <th>Diagnosis</th>
                        <th>Other Diagnosis</th>
                        <th>Intervention</th>
                        <th>Other Interventions</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td>${item.patient.name}</td>
                                <td>${item.patient.age}</td>
                                <td>${item.patient.gender.name}</td>
                                <td>${item.patient.cat.name}</td>
                                <td>${item.patient.primaryClinic.district.province.name}</td>
                                <td>${item.patient.primaryClinic.district.name}</td>
                                <td>${item.patient.primaryClinic.name}</td>
                                <td><spring:eval expression="item.dateScreened" /></td>
                                <td>${item.screening.name}</td>
                                <td>${item.risk.name}</td>
                                <td>${item.support}</td>
                                <td>${item.referral.name}</td>
                                <td>${item.diagnosis.name}</td>
                                <td>${item.otherDiagnosis}</td>
                                <td>${item.intervention}</td>
                                <td>${item.otherIntervention}</td>
                                
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
