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
                                <label>Disability</label>
                                <form:select path="disabilityCategory" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${disabilityCategories}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="disabilityCategory" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Severity</label>
                                <form:select path="severity" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="severity" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group">
                                <label>Screened</label>
                                <form:select path="screened" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="screened" class="alert-danger"/>
                                </p>
                            </div>


                            <div class="form-group">
                                <label>Date Screened</label>
                                <form:input path="dateScreened" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateScreened" class="alert-danger"/>
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
    $("form").validate({
        rules: {
            disabilityCategory: {
                required: true
            },
            dateScreened: {
                required: true
            },
            screened: {
                required: true
            },
            severity: {
                required: true
            }
        }
    });
</script>