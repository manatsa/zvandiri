<%@include file="../../template/header.jspf" %>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading bold">
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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back
                    To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="mentalHealthScreening" id="mentalHealthScreeningForm">
                        <%@include file="../../template/formState.jspf" %>
                        <form:hidden path="currentElement"/>
                            <%--<form:hidden path="patient" value="${mentalHealthScreening.patient}"/>--%>

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

                        <div class="form-group major hide">
                            <label>Date Screened</label>
                            <form:input path="dateScreened" class="form-control general"/>
                            <p class="help-block">
                                <form:errors path="dateScreened" class="alert-danger"/>
                            </p>
                        </div>
                            <%--<div class="form-group major hide">
                                <label>Screening Type</label>
                                <form:select path="screening" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${mentalHealthScreeningType}" itemValue="code"
                                                  itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="screening" class="alert-danger"/>
                                </p>
                            </div>--%>
                        <div class="panel panel-default major hide">
                            <div class="panel-heading">
                                <div class="form-group major hide">
                                    <label>Identified With Signs/Symptoms</label>
                                    <form:select path="risk" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="risk" class="alert-danger"/>
                                    </p>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group risks hide">
                                    <label></label><br/>
                                    <form:checkboxes path="identifiedRisks" items="${mh_risks}" itemLabel="name"
                                                     itemValue="code" delimiter="<br/>"/>
                                    <p class="help-block">
                                        <form:errors path="identifiedRisks" class="alert-danger"/>
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="panel panel-default major hide">
                            <div class="panel-heading">
                                <div class="form-group major hide">
                                    <div class="form-group major hide">
                                        <label>Support</label>
                                        <form:select path="support" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="support" class="alert-danger"/>
                                        </p>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="form-group supports hide">
                                        <label></label><br/>
                                        <form:checkboxes path="supports" items="${supports}" itemLabel="name"
                                                         itemValue="code"
                                                         delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="supports" class="alert-danger"/>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>

                                <%--<div class="form-group risks hide">
                                   <label>Referral</label>
                                   <form:select path="referral" class="form-control">
                                       <form:option value="" label="--Select Item"/>
                                       <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                   </form:select>
                                   <p class="help-block">
                                       <form:errors path="referral" class="alert-danger"/>
                                   </p>
                               </div>

                              <div class="form-group referrals hide">
                                   <label></label><br/>
                                   <form:checkboxes path="referrals" items="${referrals}" itemLabel="name" itemValue="code"
                                                    delimiter="<br/>"/>
                                   <p class="help-block">
                                       <form:errors path="referrals" class="alert-danger"/>
                                   </p>
                               </div>
                               <div class="form-group referrals hide">
                                   <label>Referral Complete</label>
                                   <form:select path="referralComplete" class="form-control">
                                       <form:option value="" label="--Select Item"/>
                                       <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                   </form:select>
                                   <p class="help-block">
                                       <form:errors path="referralComplete" class="alert-danger"/>
                                   </p>
                               </div>

                               <div class="form-group risks hide">
                                   <label>Diagnosis</label>
                                   <form:select path="diagnosis" class="form-control">
                                       <form:option value="" label="--Select Item"/>
                                       <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                   </form:select>
                                   <p class="help-block">
                                       <form:errors path="diagnosis" class="alert-danger"/>
                                   </p>
                               </div>

                               <div class="form-group diagnoses hide">
                                   <label>Diagnoses</label><br/>
                                   <form:checkboxes path="diagnoses" items="${diagnoses}" itemLabel="name" itemValue="code"
                                                    delimiter="<br/>"/>
                                   <p class="help-block">
                                       <form:errors path="diagnoses" class="alert-danger"/>
                                   </p>
                               </div>
                               <div class="form-group diagnoses hide">
                                   <label>Other</label>
                                   <form:input path="otherDiagnosis" class="form-control"/>
                                   <p class="help-block">
                                       <form:errors path="otherDiagnosis" class="alert-danger"/>
                                   </p>
                               </div>

                               <div class="form-group risks hide">
                                   <label>Intervention</label>
                                   <form:select path="intervention" class="form-control">
                                       <form:option value="" label="--Select Item"/>
                                       <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                   </form:select>
                                   <p class="help-block">
                                       <form:errors path="intervention" class="alert-danger"/>
                                   </p>
                               </div>
                               <div class="form-group interventions hide">
                                   <label></label><br/>
                                   <form:checkboxes path="interventions" items="${interventions}" itemLabel="name"
                                                    itemValue="code" delimiter="<br/>"/>
                                   <p class="help-block">
                                       <form:errors path="interventions" class="alert-danger"/>
                                   </p>
                               </div>
                               <div class="form-group intervention hide">
                                   <label>Other</label>
                                   <form:input path="otherIntervention" class="form-control"/>
                                   <p class="help-block">
                                       <form:errors path="otherIntervention" class="alert-danger"/>
                                   </p>
                               </div>--%>

                            <div class="form-group">
                                <button class="btn btn-primary" type="submit" id="back" name="_eventId_back">&Lt;&Lt;Back</button>
                                <button class="btn btn-primary" type="submit" id="next" name="_eventId_next">Next&Gt;&Gt;</button>
                                <button class="btn btn-primary" type="submit" id="cancel" name="_eventId_cancel">
                                    Cancel
                                </button>
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
        $("#screenedForMentalHealth").change(function () {
            var screened = $("#screenedForMentalHealth").val();
            console.log("Screened : " + screened);
            if (screened == 1) {
                $(".major").removeClass("hide");
            } else {
                $(".major").addClass("hide")
            }
        });

        $("#risk").change(function () {
            var risk = $("#risk").val();
            if (risk == 1 && $("#risk").is(":visible")) {
                $(".risks").removeClass("hide")
            } else {
                $(".risks").addClass("hide")
            }
        });
        $("#support").change(function () {
            var support = $("#support").val();
            console.log("Support :" + support)
            if (support == 1 && $("#support").is(":visible")) {
                $(".supports").removeClass("hide")
            } else {
                $(".supports").addClass("hide")
            }
        });
        /*$("#referral").change(function () {
            var referral = $("#referral").val();
            console.log("referral : " + referral)
            if (referral == 1) {
                $(".referrals").removeClass("hide")
            } else {
                $(".referrals").addClass("hide")
            }
        });
        $("#diagnosis").change(function () {
            var diag = $("#diagnosis").val();
            console.log("Diagnosis :" + diag)
            if (diag == 1) {
                $(".diagnoses").removeClass("hide");
            } else {
                $(".diagnoses").addClass("hide")
            }
        });
        $("#intervention").change(function () {
            var inter = $("#intervention").val();
            console.log("Intervention : " + inter)
            if (inter == 1) {
                $(".interventions").removeClass("hide")
            } else {
                $(".interventions").addClass("hide")
            }
        });*/


        $(function () {
            window.onload = function () {
                var name = $("#screenedForMentalHealth :selected").val();
                if (name == 1) {
                    $(".major").removeClass("hide");
                }

                var risk = $("#risk").val();
                if (risk == 1 && $("#risk").is(":visible")) {
                    $(".risks").removeClass("hide")
                } else {
                    $(".risks").addClass("hide")
                }

                var support = $("#support").val();
                console.log("Support :" + support)
                if (support == 1 && $("#support").is(":visible")) {
                    $(".supports").removeClass("hide")
                } else {
                    $(".supports").addClass("hide")
                }

                /*var referral = $("#referral:selected").val();
                if (referral == 1) {
                    $(".referrals").removeClass("hide")
                }

                var diag = $("#diagnosis:selected").val();
                if (diag == 1) {
                    $(".diagnoses").removeClass("hide");
                }

                var inter = $("#intervention:selected").val();
                if (inter == 1) {
                    $("#interventions").removeClass("hide")
                }*/

            };
        });
    </script>