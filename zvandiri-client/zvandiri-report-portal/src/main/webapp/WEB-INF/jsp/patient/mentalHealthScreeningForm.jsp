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
                        <form:form commandName="item" action="${formAction}">
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="patient" value="${item.patient.id}"/>    
                            <div class="form-group">
                                <label>Screened For Mental Health</label>
                                <form:select path="screenedForMentalHealth" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="screenedForMentalHealth" class="alert-danger"/>
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
                                    <label>Screening Type</label>
                                    <form:select path="screening" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${mentalHealthScreeningType}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="screening" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>Risk</label>
                                    <form:select path="risk" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="risk" class="alert-danger"/>
                                    </p>
                                </div>
                                <c:if test="${showRisk}">
                                    <div class="form-group">
                                        <label></label><br/>
                                        <form:checkboxes path="identifiedRisks" items="${risks}" itemLabel="name" itemValue="code" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="identifiedRisks" class="alert-danger"/>
                                        </p>
                                    </div>
                                </c:if>
                                <div class="form-group">
                                    <label>Support</label>
                                    <form:select path="support" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="support" class="alert-danger"/>
                                    </p>
                                </div>
                                <c:if test="${showSupport}">
                                    <div class="form-group">
                                        <label></label><br/>
                                        <form:checkboxes path="supports" items="${supports}" itemLabel="name" itemValue="code" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="supports" class="alert-danger"/>
                                        </p>
                                    </div>
                                </c:if>
                                <div class="form-group">
                                    <label>Referral</label>
                                    <form:select path="referral" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="referral" class="alert-danger"/>
                                    </p>
                                </div>
                                <c:if test="${showReferral}">
                                    <div class="form-group">
                                        <label></label><br/>
                                        <form:checkboxes path="referrals" items="${referrals}" itemLabel="name" itemValue="code" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="referrals" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Referral Complete</label>
                                        <form:select path="referralComplete" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="referralComplete" class="alert-danger"/>
                                        </p>
                                    </div>
                                </c:if>
                                <div class="form-group">
                                    <label>Diagnosis</label>
                                    <form:select path="diagnosis" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="diagnosis" class="alert-danger"/>
                                    </p>
                                </div>
                                <c:if test="${showDiagnosis}">
                                    <div class="form-group">
                                        <label></label><br/>
                                        <form:checkboxes path="diagnoses" items="${diagnoses}" itemLabel="name" itemValue="code" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="diagnoses" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Other</label>
                                        <form:input path="otherDiagnosis" class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="otherDiagnosis" class="alert-danger"/>
                                        </p>
                                    </div>
                                </c:if>
                                <div class="form-group">
                                    <label>Intervention</label>
                                    <form:select path="intervention" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="intervention" class="alert-danger"/>
                                    </p>
                                </div>
                                <c:if test="${showIntervention}">
                                    <div class="form-group">
                                        <label></label><br/>
                                        <form:checkboxes path="interventions" items="${interventions}" itemLabel="name" itemValue="code" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="interventions" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Other</label>
                                        <form:input path="otherIntervention" class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="otherIntervention" class="alert-danger"/>
                                        </p>
                                    </div>
                                </c:if>
                            </c:if>

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
    $("#screenedForMentalHealth").change(function () {
        $("form").attr("action", "reload-form").submit();
    });
    $("#risk").change(function () {
        $("form").attr("action", "reload-form").submit();
    });
    $("#support").change(function () {
        $("form").attr("action", "reload-form").submit();
    });
    $("#referral").change(function () {
        $("form").attr("action", "reload-form").submit();
    });
    $("#diagnosis").change(function () {
        $("form").attr("action", "reload-form").submit();
    });
    $("#intervention").change(function () {
        $("form").attr("action", "reload-form").submit();
    });
</script>