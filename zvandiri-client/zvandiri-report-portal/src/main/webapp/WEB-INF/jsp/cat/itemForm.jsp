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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="row">
                        <div class="col-lg-10">
                            <ul>
                                <li>
                                    Passwords must be at least 8 characters long
                                </li>
                                <li>
                                    Passwords must at least have an uppercase, lowercase and digit
                                </li>
                                <li>
                                    Passwords must also have at least a special character @#$%
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-10">
                        <form:form commandName="item" id="catDetailForm" action="${formAction}">
                            <form:hidden path="currentElement"/>
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Date Enrolled As CATS</label>
                                <form:input path="dateAsCat" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateAsCat" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Region</label>
                                <form:select path="province" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${provinces}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="province" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>District</label>
                                <form:select path="district" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${districts}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="district" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Primary Clinic</label>
                                <form:select path="primaryClinic" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${facilities}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="primaryClinic" class="alert-danger"/>
                                </p>
                            </div>
                            <c:if test="${not empty item.patient.id}">
                                <div class="form-group">
                                    <label>Username</label>
                                    <form:input path="userName" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="userName" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>Password</label>
                                    <form:password path="password" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="password" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>Confirm Password</label>
                                    <form:password path="confirmPassword" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="confirmPassword" class="alert-danger"/>
                                    </p>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}"><button class="btn btn-primary" type="button">Cancel</button></a>
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
    $("#catDetailForm").validate({
        rules: {
            dateAsCat: {
                required: true
            },
            userName: {
                required: true
            },
            password: {
                required: true
            },
            confirmPassword: {
                required: true
            },
            province: {
                required: true
            },
            district: {
                required: true
            },
            primaryClinic: {
                required: true
            }
        }
    });
</script>