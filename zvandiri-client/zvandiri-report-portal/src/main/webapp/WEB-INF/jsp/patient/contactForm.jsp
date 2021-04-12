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
                    <div class="col-lg-12">
                        <form:form commandName="item" action="${formAction}">
                            <form:hidden path="currentElement"/>
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <form:hidden path="parent" value="${item.parent.id}"/>
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Visit Outcome</label>
                                        <form:select path="visitOutcome" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="visitOutcome" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Date of Contact</label>
                                        <form:input path="contactDate" class="form-control general"/>
                                        <p class="help-block">
                                            <form:errors path="contactDate" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Last Clinic Appointment Date</label>
                                        <form:input path="lastClinicAppointmentDate" class="form-control general"/>
                                        <p class="help-block">
                                            <form:errors path="lastClinicAppointmentDate" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Attended clinic appointment</label>
                                        <form:select path="attendedClinicAppointment" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="attendedClinicAppointment" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Next Clinic Appointment Date</label>
                                        <form:input path="nextClinicAppointmentDate" class="form-control general"/>
                                        <p class="help-block">
                                            <form:errors path="nextClinicAppointmentDate" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Current Level of Care</label>
                                        <form:select path="careLevel" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="careLevel" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Place of Contact</label>
                                        <form:select path="location" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options items="${locations}" itemValue="id" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="location" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group locationPhone hide">
                                        <label>Contact Type</label>
                                        <form:select path="contactPhoneOption" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="contactPhoneOption" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group contactPhoneOptionSms hide">
                                        <label>Number of SMSes</label>
                                        <form:input path="numberOfSms" class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="numberOfSms" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Contacted By</label>
                                        <form:select path="position" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options items="${positions}" itemValue="id" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="position" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Reason</label>
                                        <form:select path="reason" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="reason" class="alert-danger"/>
                                        </p>
                                    </div>
                                        <div class="form-group reason hide">
                                        <label>Other Reason</label>
                                        <form:input path="otherReason"  class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="otherReason" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Client's Observations <small>(What is the Client saying)</small></label>
                                        <form:textarea path="subjective" rows="5" class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="subjective" class="alert-danger"/>
                                        </p>
                                    </div> 
                                    <div class="form-group">
                                        <label>Findings <small>(What are you noticing)</small></label>
                                        <form:textarea path="objective" rows="5" class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="objective" class="alert-danger"/>
                                        </p>
                                    </div> 
                                    <div class="form-group">
                                        <label>Services Offered</label><br/>
                                        <form:checkboxes path="serviceOffereds" items="${servicesOffered}" itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="serviceOffereds" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Laboratory Requests</label><br/>
                                        <form:checkboxes path="labTasks" items="${labTasks}" itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="labTasks" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Differentiated Service Delivery</label>
                                        <form:select path="differentiatedService" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="differentiatedService" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Plan</label>
                                        <form:textarea path="plan" rows="5" class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="plan" class="alert-danger"/>
                                        </p>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <c:if test="${external}">
                                        <div class="form-group">
                                            <label>External Referral</label>
                                            <form:select path="externalReferral" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${externalReferrals}" itemValue="id" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="externalReferral" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>
                                    <c:if test="${internal}">
                                        <div class="form-group">
                                            <label>Internal Referral</label>
                                            <form:select path="internalReferral" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${internalReferrals}" itemValue="id" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="internalReferral" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>                            
                                    <c:if test="${stable}">
                                        <div class="form-group">
                                            <label>Stable</label><br/><br/>
                                            <form:checkboxes path="stables" items="${stables}" itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                            <p class="help-block">
                                                <form:errors path="stables" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>
                                    <c:if test="${enhanced}">
                                        <div class="form-group">
                                            <label>Enhanced</label><br/><br/>
                                            <form:checkboxes path="enhanceds" items="${enhanceds}" itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                            <p class="help-block">
                                                <form:errors path="enhanceds" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>
                                    <div class="form-group">
                                        <label>Clinical Assessment</label><br/>
                                        <form:checkboxes path="clinicalAssessments" items="${clinicalAssessments}" itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="clinicalAssessments" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Non Clinical Assessment</label><br/>
                                        <form:checkboxes path="nonClinicalAssessments" items="${nonClinicalAssessments}" itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="nonClinicalAssessments" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Follow Up</label>
                                        <form:select path="followUp" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="followUp" class="alert-danger"/>
                                        </p>
                                    </div>

                                    <div class="form-group">
                                        <label>Action Taken</label><br/>
                                        <form:select path="actionTaken" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options items="${actionTaken}" itemValue="id" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="actionTaken" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <c:if test="${internalStaff}">
                                        <div class="form-group">
                                            <label>User Level</label>
                                            <form:select path="userLevel" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options itemValue="code" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="userLevel" class="alert-danger"/>
                                            </p>
                                        </div>
                                        <c:if test="${showProvince}">
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
                                        </c:if>
                                        <c:if test="${showDistrict}">
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
                                        </c:if>
                                        <div class="form-group">
                                            <label>Zvandiri Staff Member</label><br/><br/>
                                            <form:select path="referredPerson" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${staff}" itemValue="id" itemLabel="displayName"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="referredPerson" class="alert-danger"/>
                                            </p>
                                        </div> 
                                    </c:if>
                                    <c:if test="${item.id == null}">
                                        <!-- start investigations section here -->
                                        <div class="form-group">
                                            <label>Viral Load Results Available</label>
                                            <form:select path="viralLoad.resultTaken" id="viralLoadResultTaken" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options itemValue="code" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="viralLoad.resultTaken" class="alert-danger"/>
                                            </p>
                                        </div>
                                        <div class="form-group viralLoad hide">
                                            <label>Viral Load Date Taken</label>
                                            <form:input path="viralLoad.dateTaken" class="form-control general viralLoadVal"/>
                                            <p class="help-block">
                                                <form:errors path="viralLoad.dateTaken" class="alert-danger"/>
                                            </p>
                                        </div>
                                        <div class="form-group viralLoad hide">
                                            <label>Viral Load Result</label>
                                            <form:input path="viralLoad.result" class="form-control viralLoadVal"/>
                                            <p class="help-block">
                                                <form:errors path="viralLoad.result" class="alert-danger"/>
                                            </p>
                                        </div>
                                        <div class="form-group viralLoad hide">
                                            <label>Viral Load Source</label>
                                            <form:select path="viralLoad.source" class="form-control viralLoadVal">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options itemValue="code" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="viralLoad.source" class="alert-danger"/>
                                            </p>
                                        </div>
                                        <div class="form-group viralLoad hide">
                                            <label>Viral Load Next Lab Due</label>
                                            <form:input path="viralLoad.nextTestDate" class="form-control otherdate viralLoadVal"/>
                                            <p class="help-block">
                                                <form:errors path="viralLoad.nextTestDate" class="alert-danger"/>
                                            </p>
                                        </div>
                                        <!-- start CD4 count section here -->
                                        <div class="form-group">
                                            <label>CD4 Count Results Available</label>
                                            <form:select path="cd4Count.resultTaken" id="cd4CountResultTaken" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options itemValue="code" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="cd4Count.resultTaken" class="alert-danger"/>
                                            </p>
                                        </div>
                                        <div class="form-group cd4Count hide">
                                            <label>CD4 Count  Date Taken</label>
                                            <form:input path="cd4Count.dateTaken" class="form-control general cd4CountVal"/>
                                            <p class="help-block">
                                                <form:errors path="cd4Count.dateTaken" class="alert-danger"/>
                                            </p>
                                        </div>
                                        <div class="form-group cd4Count hide">
                                            <label>CD4 Count  Result</label>
                                            <form:input path="cd4Count.result" class="form-control cd4CountVal"/>
                                            <p class="help-block">
                                                <form:errors path="cd4Count.result" class="alert-danger"/>
                                            </p>
                                        </div>
                                        <div class="form-group cd4Count hide">
                                            <label>CD4 Count Source</label>
                                            <form:select path="cd4Count.source" class="form-control cd4CountVal">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options itemValue="code" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="cd4Count.source" class="alert-danger"/>
                                            </p>
                                        </div>
                                        <div class="form-group cd4Count hide">
                                            <label>CD4 Count Next Lab Due</label>
                                            <form:input path="cd4Count.nextTestDate" class="form-control otherdate cd4CountVal"/>
                                            <p class="help-block">
                                                <form:errors path="cd4Count.nextTestDate" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
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
    $("#reason").change(function () {
        $("#currentElement").val($(window).scrollTop());
        $("form").attr("action", "reload-form").submit();
    });

    $("#actionTaken").change(function () {
        $("#currentElement").val($(window).scrollTop());
        $("form").attr("action", "reload-form").submit();
    });

    $("#province").change(function () {
        $("#currentElement").val($(window).scrollTop());
        $("form").attr("action", "reload-form").submit();
    });

    $("#district").change(function () {
        $("#currentElement").val($(window).scrollTop());
        $("form").attr("action", "reload-form").submit();
    });

    $("#userLevel").change(function () {
        $this = $(this);
        var userLevel = $.trim($this.val());
        if (userLevel !== "" || userLevel !== 1) {
            $("#currentElement").val($(window).scrollTop());
            $("form").attr("action", "reload-form").submit();
        }
    });
    $("#viralLoadResultTaken").change(function () {
        var name = $("#viralLoadResultTaken :selected").text();
        if (name === "Yes") {
            $(".viralLoad").removeClass("hide");
        } else {
            $(".viralLoadVal").val('');
            $(".viralLoad").addClass("hide");
        }
    });
    $("#cd4CountResultTaken").change(function () {
        var name = $("#cd4CountResultTaken :selected").text();
        if (name === "Yes") {
            $(".cd4Count").removeClass("hide");
        } else {
            $(".cd4CountVal").val('');
            $(".cd4Count").addClass("hide");
        }
    });
    $("#location").change(function () {
        var name = $("#location :selected").text();
        if (name === "Phone") {
            $(".locationPhone").removeClass("hide");
        } else {
            $("#contactPhoneOption").val('');
            $(".locationPhone").addClass("hide");
        }
    });
    $("#contactPhoneOption").change(function () {
        var name = $("#contactPhoneOption :selected").text();
        if (name === "Sms") {
            $(".contactPhoneOptionSms").removeClass("hide");
        } else {
            $("#numberOfSms").val('');
            $(".contactPhoneOptionSms").addClass("hide");
        }
    });
    var patientId = "<c:out value="${item.patient.id}"/>"
    $("#visitOutcome").change(function () {
        var name = $.trim($("#visitOutcome :selected").text());
        if (name === "Changed Location") {
            location.href = path + "/patient/change-facility/item.form?id=" + patientId;
        } else if (name === "Deceased") {
            location.href = path + "/patient/patient-death/item.form?id=" + patientId;
        }
    });
    $("#reason").change(function () {
        var name = $("#reason :selected").text();
        if (name === "Other") {
            $(".reason").removeClass("hide");
        } else {
            $("#otherReason").val('');
            $(".reason").addClass("hide");
        }
    });
    $(function () {
        window.onload = function () {
            var name = $("#viralLoadResultTaken :selected").text();
            if (name === "Yes") {
                $(".viralLoad").removeClass("hide");
            }
            var name = $("#cd4CountResultTaken :selected").text();
            if (name === "Yes") {
                $(".cd4Count").removeClass("hide");
            }
            var name = $("#location :selected").text();
            if (name === "Phone") {
                $(".locationPhone").removeClass("hide");
            }
            var name = $("#contactPhoneOption :selected").text();
            if (name === "Sms") {
                $(".contactPhoneOptionSms").removeClass("hide");
            }
            var name = $("#reason :selected").text();
            if (name === "Other") {
                $(".reason").removeClass("hide");
            }
        };
    });
    $(window).scrollTop("<c:out value="${item.currentElement}"/>");
    //actionTaken
</script>