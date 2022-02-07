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
                            <%@include file="../../template/dashboard/patientProfile.jspf" %>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <form:form modelAttribute="contacts" id="comtactForm">
                            <%@include file="../../template/formState.jspf" %>
                            <%--<form:hidden path="patient" value="${item.patient.id}"/>--%>
                            <%--<form:hidden path="parent" value="${item.parent.id}"/>--%>
                            <div class="row">
                                <div class="col-lg-6">
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
                                        <form:select path="careLevel" class="form-control" value="${patient.getCurrentCareLevelObject()}" disabled="true">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="careLevel" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Care Level After Assessment</label>
                                        <form:select path="careLevelAfterAssessment" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="careLevel" class="alert-danger"/>
                                        </p>
                                    </div>

                                    <%--Place of Contact panel--%>
                                    <div class="panel-default">
                                        <div class="panel-heading">
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
                                        </div>
                                        <div class="panel-body">
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
                                        </div>
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

                                    <%--EAC Sessions Panel--%>
                                    <div class="panel-default">
                                        <div class="panel-heading eac-head">
                                            <div class="form-group">
                                                <label>EAC Session</label>
                                                <form:select path="eac" class="form-control">
                                                    <form:option value="" label="--Select Item"/>
                                                    <form:options itemValue="code" itemLabel="name"/>
                                                </form:select>
                                                <p class="help-block">
                                                    <form:errors path="eac" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="panel-body">
                                            <div class="form-group eac hide">
                                                <label>EAC Session 1</label>
                                                <form:checkbox path="eac1" value="1" class="form-control" />
                                                <p class="help-block">
                                                    <form:errors path="eac1" class="alert-danger"/>
                                                </p>
                                            </div>
                                            <div class="form-group eac hide">
                                                <label>EAC Session 2</label>
                                                <form:checkbox path="eac2" value="1" class="form-control"/>
                                                <p class="help-block">
                                                    <form:errors path="eac2" class="alert-danger"/>
                                                </p>
                                            </div>
                                            <div class="form-group eac hide">
                                                <label>EAC Session 3</label>
                                                <form:checkbox path="eac3" value="1" class="form-control"/>
                                                <p class="help-block">
                                                    <form:errors path="eac3" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label>Contact Made By: <small><small>&LT;surname middlename firstname&GT;</small></small> </label>
                                        <form:input path="contactMadeBy" class="form-control" />
                                        <p class="help-block">
                                            <form:errors path="contactMadeBy" class="alert-danger"/>
                                        </p>
                                    </div>






                                </div>


                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Services Offered</label><br/>
                                        <form:checkboxes path="serviceOffereds" items="${servicesOffered}" itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="serviceOffereds" class="alert-danger"/>
                                        </p>
                                    </div>


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

                                    </div>
                            </div>

                            <div class="form-group row">
                                <button class="btn btn-primary" type="submit" id="back" name="_eventId_back">&Lt;&Lt;Back</button>
                                <button class="btn btn-primary " type="submit" id="next" name="_eventId_next">Proceed&Gt;&Gt;</button>
                                <button class="btn btn-primary " type="submit" id="cancel" name="_eventId_cancel">Cancel</button>
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
    $("#eac").change(function () {
        var eac=$("#eac").val();
        if(eac==1){
            $(".eac").removeClass("hide")
            $("#eac-head").addClass("panel-heading")
        }else{
            $(".eac").addClass("hide")
            $("#eac-head").removeClass("panel-heading")
        }
    })

    $("#location").change(function () {
        var name = $("#location :selected").text().toLowerCase();
        if (name === "phone") {
            $(".locationPhone").removeClass("hide");
        } else {
            $("#contactPhoneOption").val('');
            $(".locationPhone").addClass("hide");
        }
    });

    $("#contactPhoneOption").change(function () {
        var name = $("#contactPhoneOption :selected").text().toLowerCase();
        if (name === "sms") {
            $(".contactPhoneOptionSms").removeClass("hide");
        } else {
            $("#numberOfSms").val('');
            $(".contactPhoneOptionSms").addClass("hide");
        }
    });
    var patientId = "<c:out value="${patient.id}"/>"
    $("#visitOutcome").change(function () {
        var name = $.trim($("#visitOutcome :selected").text().toLowerCase());
        if (name === "changed location") {
            location.href = path + "/patient/change-facility/item.form?id=" + patientId;
        } else if (name === "deceased") {
            location.href = path + "/patient/patient-death/item.form?id=" + patientId;
        }
    });

    $(function () {
        window.onload = function () {

            var name = $("#location :selected").text();
            if (name === "Phone") {
                $(".locationPhone").removeClass("hide");
            }
            var name = $("#contactPhoneOption :selected").text().toLowerCase();
            if (name === "sms") {
                $(".contactPhoneOptionSms").removeClass("hide");
            }
            var eac=$("#eac").val();
            if(eac==1){
                $(".eac").removeClass("hide")
                $("#eac-head").addClass("panel-heading")
            }else{
                $(".eac").addClass("hide")
                $("#eac-head").removeClass("panel-heading")
            }
        };
    });
    <%--$(window).scrollTop("<c:out value="${item.currentElement}"/>");--%>
    //actionTaken
</script>