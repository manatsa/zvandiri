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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back
                    To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item">
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <div class="form-group">
                                <label>First Name</label>
                                <form:input path="firstName" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="firstName" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Last Name</label>
                                <form:input path="lastName" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="lastName" class="alert-danger"/>
                                </p>
                            </div>
                            <div cla
                            <div class="form-group">
                                <label>Date Of Birth</label>
                                <form:input path="dateOfBirth" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="dateOfBirth" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Gender</label>
                                <form:select path="gender" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="gender" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>HIV Status</label>
                                <form:select path="hivStatus" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="hivStatus" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <c:if test="${canEdit}">
                                    <button class="btn btn-primary" type="submit">Save</button>
                                </c:if>
                                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">
                                    <button class="btn btn-primary" type="button">Cancel</button>
                                </a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    $("#dateOfBirth").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "dd/mm/yy"
    });
</script>