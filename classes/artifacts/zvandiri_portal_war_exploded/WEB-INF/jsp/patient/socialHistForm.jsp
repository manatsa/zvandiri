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
                    <div class="col-lg-10">
                        <form:form commandName="item">
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="patient" value="${item.patient.id}"/>                            
                            <div class="form-group">
                                <label>Who do you currently live with</label>
                                <form:input path="liveWith" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="liveWith" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Relationship</label>
                                <form:select path="relationship" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${relationships}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="relationship" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Have you ever been physically, sexually or emotionally abused</label>
                                <form:select path="abuse" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="abuse" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Type of abuse</label><br/><br/>
                                <form:checkboxes path="abuseTypes" items="${abuseTypes}" itemLabel="name" itemValue="code" delimiter="<br/>"/>
                                <p class="help-block">
                                    <form:errors path="abuseTypes" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group abuse hide">
                                <label>Did you tell anyone</label>
                                <form:select path="dosclosure" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="dosclosure" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group abuse hide">
                                <label>Outcome</label>
                                <form:select path="abuseOutcome" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="abuseOutcome" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group abuse hide">
                                <label>Do you feel safe where you live now</label>
                                <form:select path="feelSafe" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="feelSafe" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group abuse hide">
                                <label>Social Support</label>
                                <form:textarea path="socialSupport" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="socialSupport" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group abuse hide">
                                <label>Losses Of Other Significant Relationships</label>
                                <form:textarea path="lossOfSignificantRelationships" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="lossOfSignificantRelationships" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <c:if test="${canEdit}"><button class="btn btn-primary" type="submit">Save</button></c:if>
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
    $("#abuse").change(function () {
        var name = $("#abuse :selected").text();
        if (name === "Yes") {
            $(".abuse").removeClass("hide");
        } else {
            $("#dosclosure").val('');
            $("#abuseOutcome").val('');
            $("#feelSafe").val('');
            $(".abuse").addClass("hide");
        }
    });
    $(function () {
        window.onload = function () {
            var name = $("#abuse :selected").text();
            if (name === "Yes") {
                $(".abuse").removeClass("hide");
            }
        };
    });
    $("form").validate({
        rules: {
            liveWith: {
                required: true
            },
            abuse: {
                required: true
            },
            dosclosure: {
                required: true
            },
            feelSafe: {
                required: true
            },
            abuseOutcome: {
                required: true
            }
        }
    });
</script>