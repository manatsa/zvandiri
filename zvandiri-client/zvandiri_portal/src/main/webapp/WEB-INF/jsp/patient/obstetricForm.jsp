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
                                <label>Have you ever been pregnant</label>
                                <form:select path="pregnant" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="pregnant" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group has-preg hide">
                                <label>Are you currently breast feeding</label>
                                <form:select path="breafFeedingCurrent" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="breafFeedingCurrent" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group has-preg hide">
                                <label>Are you currently pregnant</label>
                                <form:select path="pregCurrent" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="pregCurrent" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group preg-curr hide">
                                <label>Number of ANC visits</label>
                                <form:select path="numberOfANCVisit" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="numberOfANCVisit" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group preg-curr hide">
                                <label>Gestational age at first ANC visit</label>
                                <form:select path="gestationalAge" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="gestationalAge" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group preg-curr hide">
                                <label>ART Started</label>
                                <form:select path="artStarted" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="artStarted" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group has-preg hide">
                                <label>How many living children</label>
                                <form:input path="children" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="children" class="alert-danger"/>
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
    $("#pregnant").change(function () {
        var name = $("#pregnant :selected").text();
        if (name === "Yes") {
            $(".has-preg").removeClass("hide");
        } else {
            $("#breafFeedingCurrent").val('');
            $("#pregCurrent").val('');
            $("#numberOfAncVisit").val('');
            $("#gestationalAgeAtFirstAncVisit").val('');
            $("#artStarted").val('');
            $("#children").val('');
            $(".has-preg").addClass("hide");
        }
    });
    $("#pregCurrent").change(function () {
        var name = $("#pregCurrent :selected").text();
        if (name === "Yes") {
            $(".preg-curr").removeClass("hide");
        } else {
            $("#numberOfAncVisit").val('');
            $("#gestationalAgeAtFirstAncVisit").val('');
            $("#artStarted").val('');
            $(".preg-curr").addClass("hide");
        }
    });
    $(function () {
        window.onload = function () {
            var name = $("#pregnant :selected").text();
            if (name === "Yes") {
                $(".has-preg").removeClass("hide");
            }
            var name = $("#pregCurrent :selected").text();
            if (name === "Yes") {
                $(".preg-curr").removeClass("hide");
            }
        };
    });
</script>