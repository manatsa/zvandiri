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
                                <label>Substance</label>
                                <form:select path="substance" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${substances}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="substance" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Current</label>
                                <form:select path="current" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="current" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Lifetime</label>
                                <form:select path="past" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="past" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Is the substance problematic</label>
                                <form:select path="problematic" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="problematic" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Type/ Amount</label>
                                <form:input path="typeAmount" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="typeAmount" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>How Often</label>
                                <form:input path="howOften" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="howOften" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Reason</label>
                                <form:input path="reason" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="reason" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Start Date (Using substance)</label>
                                <form:input path="startDate" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="startDate" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>End Date (Using substance)</label>
                                <form:input path="endDate" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="endDate" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Interventions</label>
                                <form:select path="drugIntervention" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="drugIntervention" class="alert-danger"/>
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
    $("#infectionDate").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "dd/mm/yy"
    });
    $("form").validate({
        rules: {
            startDate: {
                required: true
            },
            substance: {
                required: true
            },
            current: {
                required: true
            },
            past: {
                required: true
            },
            drugIntervention: {
                required: true
            }
        }
    });
</script>