<%@include file="../template/header.jspf"%>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">${pageTitle}</div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To
                Reports DashBoard Home</a><br />
                <form:form commandName="item" role="form">
                <table class="table">
                    <tbody>
                        <tr>
                            <c:if test="${userLevel == 'NATIONAL'}">
                                <td>
                                    <div class="form-group">
                                        <label>Region</label>
                                        <form:select path="province" class="form-control">
                                            <form:option value="" label="--Select Item--"/>
                                            <form:options items="${provinces}" itemLabel="name" itemValue="id"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="province"/>
                                        </p>
                                    </div> 
                                </td>
                            </c:if>
                            <c:if test="${userLevel == 'NATIONAL' or userLevel == 'PROVINCE'}">
                                <td>
                                    <div class="form-group">
                                        <label>District</label>
                                        <form:select path="district" class="form-control">
                                            <form:option value="" label="--Select Item--"/>
                                            <form:options items="${districts}" itemLabel="name" itemValue="id"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="district"/>
                                        </p>
                                    </div> 
                                </td>
                            </c:if>
                            <td>
                                <div class="form-group">
                                    <label>Facility</label>
                                    <form:select path="primaryClinic" class="form-control">
                                        <form:option value="" label="--Select Item--"/>
                                        <form:options items="${facilities}" itemLabel="name" itemValue="id"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="primaryClinic"/>
                                    </p>
                                </div> 
                            </td>
                            <td>
                                <div class="form-group">
                                    <label>Gender</label>
                                    <form:select path="gender" class="form-control">
                                        <form:option value="" label="--Select Item--"/>
                                        <form:options itemLabel="name" itemValue="code"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="gender"/>
                                    </p>
                                </div> 
                            </td>
                            <td>
                                <div class="form-group">
                                    <label>Start Date</label>
                                    <form:input path="startDate" class="form-control general"/>
                                    <p class="help-block">
                                        <form:errors path="startDate"/>
                                    </p>
                                </div> 
                            </td>
                            <td>
                                <div class="form-group">
                                    <label>End Date</label>
                                    <form:input path="endDate" class="form-control general"/>
                                    <p class="help-block">
                                        <form:errors path="endDate"/>
                                    </p>
                                </div> 
                            </td>
                            <td>
                                <div class="form-group">
                                    <label>&nbsp;</label><br/>
                                    <button class="btn btn-primary" type="submit">Search</button>
                                </div> 
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form:form>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>D.O.B</th>
                        <th>Age</th>
                        <th>Sex</th>
                        <th>Certificate Number</th>
                        <th>Phone Number</th>
                        <th>Date Joined</th>
                        <th>Facility</th>
                        <th>Graduation Date</th>
                        <th>Bled For VL</th>
                        <th>VL Date</th>
                        <th>VL Result</th>
                        <th>Regimen</th>
                        <th>Date Started Regimen</th>
                        <th>Sexually Active</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>D.O.B</th>
                        <th>Age</th>
                        <th>Sex</th>
                        <th>Certificate Number</th>
                        <th>Phone Number</th>
                        <th>Date Joined</th>
                        <th>Facility</th>
                        <th>Graduation Date</th>
                        <th>Bled For VL</th>
                        <th>VL Date</th>
                        <th>VL Result</th>
                        <th>Regimen</th>
                        <th>Date Started Regimen</th>
                        <th>Sexually Active</th>
                        </tfoot>
                        <tbody>
                            <c:forEach var="item" items="${items}">
                                <tr>
                                    <td>${item.patient.name}</td>
                                    <td>${item.patient.dateOfBirth}</td>
                                    <td>${item.patient.age}</td>
                                    <td>${item.patient.gender}</td>
                                    <td></td>
                                    <td>${item.patient.mobileNumber}</td>
                                    <td>${item.dateAsCat}</td>
                                    <td>${item.primaryClinic.name}</td>
                                    <td>${item.graduationDate}</td>
                                    <td>${item.vlResultTaken}</td>
                                    <td>${item.vlDate}</td>
                                    <td>${item.patient.viralLoad}</td>
                                    <td>${item.patient.currentArvRegimen}</td>
                                    <td>${item.regimenDate}</td>
                                    <td>${item.sexuallyActive}</td>
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