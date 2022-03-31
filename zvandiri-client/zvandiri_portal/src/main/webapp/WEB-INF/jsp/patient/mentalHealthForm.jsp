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
                                <label>Mental Health</label>
                                <form:select path="mentalHealth" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${mentalHealths}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="mentalHealth" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Past</label>
                                <form:input path="past" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="past" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Current</label>
                                <form:input path="current" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="current" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Age</label>
                                <form:input path="age" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="age" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Professional Help Provided By</label>
                                <form:input path="professionalCareProvidedBy" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="professionalCareProvidedBy" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Medication</label>
                                <form:input path="medication" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="medication" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Received Professional Care</label>
                                <form:select path="receivedProfessionalHelp" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="receivedProfessionalHelp" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group prof-care hide">
                                <label>Professional Care Start Date</label>
                                <form:input path="profHelpStart" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="profHelpStart" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group prof-care hide">
                                <label>Professional Care End Date</label>
                                <form:input path="profHelpEnd" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="profHelpEnd" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Psychiatric Hospitalization</label>
                                <form:select path="beenHospitalized" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="beenHospitalized" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Problem Description</label>
                                <form:textarea path="mentalHistText" rows="4" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="mentalHistText" class="alert-danger"/>
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
    $("#receivedProfessionalHelp").change(function () {
        var name = $("#receivedProfessionalHelp :selected").text();
        if (name === "Yes") {
            $(".prof-care").removeClass("hide");
        } else {
            $("#profHelpStart").val('');
            $("#profHelpEnd").val('');
            $(".prof-care").addClass("hide");
        }
    });
    $(function () {
        window.onload = function () {
            var name = $("#receivedProfessionalHelp :selected").text();
            if (name === "Yes") {
                $(".prof-care").removeClass("hide");
            }
        };
    });
    $("form").validate({
        rules: {
            past: {
                required: true
            },
            current: {
                required: true
            },
            mentalHistText: {
                required: true
            },
            startDate: {
                required: true
            },
            mentalHealth: {
                required: true
            },
            receivedProfessionalHelp: {
                required: true
            },
            profHelpStart: {
                required: true
            },
            profHelpEnd: {
                required: true
            }
        }
    });
</script>