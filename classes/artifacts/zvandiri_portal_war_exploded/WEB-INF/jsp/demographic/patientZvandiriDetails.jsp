<%@include file="../../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="../../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-10">
                        <div class="panel panel-default">                            
                            <%@include file="profileFragment.jspf" %>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="patient" id="patientDemographicForm">
                            <div class="form-group">
                                <label>Consent M-Health</label>
                                <form:select path="consentToMHealth" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="consentToMHealth" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>CATS Member</label>
                                <form:select path="cat" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="cat" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Is In Young People's Group</label>
                                <form:select path="youngMumGroup" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="youngMumGroup" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit" id="back" name="_eventId_back">&Lt;&Lt;Back</button>
                                <button class="btn btn-primary" type="submit" id="next" name="_eventId_next">Next&Gt;&Gt;</button>
                                <button class="btn btn-primary" type="submit" id="cancel" name="_eventId_cancel">Cancel</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../../template/footer.jspf" %>
<script type="text/javascript">
    $("#vstStudent").change(function () {
        var name = $("#vstStudent :selected").text();
        if (name === "Yes") {
            $(".status-known").removeClass("hide");
        } else {
            $(".status-known").addClass("hide");
        }
    });
    $(function () {
        window.onload = function () {
            var name = $("#vstStudent :selected").text();
            if (name === "Yes") {
                $(".status-known").removeClass("hide");
            }
        };
    });
</script>