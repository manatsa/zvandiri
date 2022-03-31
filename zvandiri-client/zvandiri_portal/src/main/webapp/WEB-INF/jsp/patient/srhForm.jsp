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
                            <c:if test="${female}">
                                <div class="form-group">
                                    <label>How old were you when you started menstruating</label>
                                    <form:input path="ageStartMen" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="ageStartMen" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>How often do you bleed</label>
                                    <form:input path="bleedHowOften" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="bleedHowOften" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>How many days</label>
                                    <form:input path="bleeddays" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="bleeddays" class="alert-danger"/>
                                    </p>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label>Have you ever had sexual intercourse</label>
                                <form:select path="sexualIntercourse" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="sexualIntercourse" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Are you currently sexually active</label>
                                <form:select path="sexuallyActive" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="sexuallyActive" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group sex-active hide">
                                <label>Do you use condoms</label>
                                <form:select path="condomUse" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="condomUse" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group sex-active hide">
                                <label>Do you use any other form of birth control</label>
                                <form:select path="birthControl" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="birthControl" class="alert-danger"/>
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
    $("#sexuallyActive").change(function () {
        var name = $("#sexuallyActive :selected").text();
        if (name === "Yes") {
            $(".sex-active").removeClass("hide");
        } else {
            $("#condomUse").val('');
            $("#birthControl").val('');
            $(".sex-active").addClass("hide");
        }
    });
    $(function () {
        window.onload = function () {
            var name = $("#sexuallyActive :selected").text();
            if (name === "Yes") {
                $(".sex-active").removeClass("hide");
            }
        };
    });
    $("form").validate({
        rules: {
            ageStartMen: {
                required: true
            },
            bleedHowOften: {
                required: true
            },
            bleeddays: {
                required: true
            },
            sexualIntercourse: {
                required: true
            },
            sexuallyActive: {
                required: true
            },
            condomUse: {
                required: true
            },
            birthControl: {
                required: true
            }
        }
    });
</script>