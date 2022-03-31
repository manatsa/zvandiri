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
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item" action="${formAction}">
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <div class="form-group">
                                <label>Screened For Tb</label>
                                <form:select path="screenedForTb" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="screenedForTb" class="alert-danger"/>
                                </p>
                            </div>
                            <c:if test="${showForm}">
                                <div class="form-group">
                                    <label>Date Screened</label>
                                    <form:input path="dateScreened" class="form-control general"/>
                                    <p class="help-block">
                                        <form:errors path="dateScreened" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>Presence with signs or symptoms of TB</label><br/>
                                    <form:checkboxes path="tbSymptoms" items="${symptoms}" itemLabel="name"
                                                     itemValue="code" delimiter="<br/>"/>
                                    <p class="help-block">
                                        <form:errors path="tbSymptoms" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>Identified with TB</label>
                                    <form:select path="identifiedWithTb" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="identifiedWithTb" class="alert-danger"/>
                                    </p>
                                </div>
                                <c:if test="${showActionTaken}">
                                    <div class="form-group">
                                        <label>Action Taken</label>
                                        <form:select path="tbIdentificationOutcome" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options items="${tbIdentificationOutcomes}" itemValue="code"
                                                          itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="tbIdentificationOutcome" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <c:if test="${onTbTreatment}">
                                        <div class="form-group">
                                            <label>Date Started Treatment</label>
                                            <form:input path="dateStartedTreatment" class="form-control general"/>
                                            <p class="help-block">
                                                <form:errors path="dateStartedTreatment" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>
                                </c:if>
                                <div class="form-group">
                                    <label>Outcome</label>
                                    <form:select path="tbTreatmentOutcome" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${outcomes}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="tbTreatmentOutcome" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>Referred For IPT</label>
                                    <form:select path="referredForIpt" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="referredForIpt" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>On IPT</label>
                                    <form:select path="onIpt" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="onIpt" class="alert-danger"/>
                                    </p>
                                </div>
                                <c:if test="${onIpt}">
                                    <div class="form-group">
                                        <label>Date Started On IPT</label>
                                        <form:input path="dateStartedIpt" class="form-control general"/>
                                        <p class="help-block">
                                            <form:errors path="dateStartedIpt" class="alert-danger"/>
                                        </p>
                                    </div>
                                </c:if>
                            </c:if>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="#">
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
    $("#screenedForTb").change(function () {
        $("form").attr("action", "reload-form").submit();
    });
    $("#identifiedWithTb").change(function () {
        $("form").attr("action", "reload-form").submit();
    });
    $("#tbIdentificationOutcome").change(function () {
        $("form").attr("action", "reload-form").submit();
    });
    $("#onIpt").change(function () {
        $("form").attr("action", "reload-form").submit();
    });
</script>