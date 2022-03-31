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
                        <form:form commandName="tbScreening" id="tbScreeningForm2">
                            <%@include file="../../template/formState.jspf" %>
                            <%--<form:hidden path="patient" value="${tbScreening.patient}"/>--%>
                            <div class="row">
                                <div class="col-lg-6">
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

                                    <div class="form-group major hide">
                                        <label>Date Screened</label>
                                        <form:input path="dateScreened" class="form-control general"/>
                                        <p class="help-block">
                                            <form:errors path="dateScreened" class="alert-danger"/>
                                        </p>
                                    </div>

                                    <div class="panel panel-default major hide">
                                        <div class="panel-heading major hide">
                                            <div class="form-group major hide">
                                                <label>Identified with TB Symptoms</label>
                                                <form:select path="identifiedWithTb" class="form-control">
                                                    <form:option value="" label="--Select Item"/>
                                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                                </form:select>
                                                <p class="help-block">
                                                    <form:errors path="identifiedWithTb" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>

                                        <div class="panel-body symptoms hide">
                                            <div class="form-group symptoms hide">
                                                <label>Identified Signs/Symptoms of TB</label><br/>
                                                <form:checkboxes path="tbSymptoms" items="${tbSymptoms}"
                                                                 itemLabel="name"
                                                                 itemValue="code"
                                                                 delimiter="<br/>"/>
                                                <p class="help-block">
                                                    <form:errors path="tbSymptoms" class="alert-danger"/>
                                                </p>
                                            </div>
                                            <div class="form-group symptoms hide">
                                                <label>Referred For TB Investigations</label>
                                                <form:select path="referredForInvestigation" class="form-control">
                                                    <form:option value="" label="--Select Item"/>
                                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                                </form:select>
                                                <p class="help-block">
                                                    <form:errors path="referredForInvestigation" class="alert-danger"/>
                                                </p>
                                            </div>
                                            <div class="form-group symptoms hide">
                                                <label>Screened By HCW</label>
                                                <form:select path="screenedByHcw" class="form-control">
                                                    <form:option value="" label="--Select Item"/>
                                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                                </form:select>
                                                <p class="help-block">
                                                    <form:errors path="screenedByHcw" class="alert-danger"/>
                                                </p>
                                            </div>
                                            <div class="form-group hcw hide">
                                                <label>Identified With TB By HCW</label>
                                                <form:select path="identifiedWithTbByHcw" class="form-control">
                                                    <form:option value="" label="--Select Item"/>
                                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                                </form:select>
                                                <p class="help-block">
                                                    <form:errors path="identifiedWithTbByHcw" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default symptoms hide">
                                        <div class="panel-heading symptoms hide">
                                            <div class="form-group symptoms hide ">
                                                <label>On TB Treatment</label>
                                                <form:select path="onTBTreatment" class="form-control">
                                                    <form:option value="" label="--Select Item"/>
                                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                                </form:select>
                                                <p class="help-block">
                                                    <form:errors path="onTBTreatment" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="panel-body treatment hide">
                                            <div class="form-group treatment hide ">
                                                <label>Date Started Treatment</label>
                                                <form:input path="dateStartedTreatment" class="form-control general"/>
                                                <p class="help-block">
                                                    <form:errors path="dateStartedTreatment" class="alert-danger"/>
                                                </p>
                                            </div>
                                            <div class="form-group treatment hide">
                                                <label>Date Completed Treatment</label>
                                                <form:input path="dateCompletedTreatment" class="form-control general"/>
                                                <p class="help-block">
                                                    <form:errors path="dateCompletedTreatment" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6">

                                    <div class="row" style="height: 20px;">

                                    </div>
                                    <div class="panel panel-default notreatment hide">
                                        <div class="panel-heading notreatment hide">
                                            <div class="form-group notreatment hide">
                                                <label> On TPT or Had TPT in Last 3 Years</label>
                                                <form:select path="onIpt" class="form-control">
                                                    <form:option value="" label="--Select Item"/>
                                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                                </form:select>
                                                <p class="help-block">
                                                    <form:errors path="onIpt" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="panel-body ipt hide">
                                            <div class="form-group ipt hide">
                                                <label>Date Started TPT</label>
                                                <form:input path="dateStartedIpt" class="form-control general"/>
                                                <p class="help-block">
                                                    <form:errors path="dateStartedIpt" class="alert-danger"/>
                                                </p>
                                            </div>
                                            <div class="form-group ipt hide">
                                                <label>Date Completed TPT</label>
                                                <form:input path="dateCompletedIpt" class="form-control general"/>
                                                <p class="help-block">
                                                    <form:errors path="dateCompletedIpt" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default notreatment hide">
                                        <div class="panel-heading notreatment hide">
                                            <div class="form-group notreatment hide ">
                                                <label>Eligible For TPT</label>
                                                <form:select path="eligibleForIpt" class="form-control">
                                                    <form:option value="" label="--Select Item"/>
                                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                                </form:select>
                                                <p class="help-block">
                                                    <form:errors path="eligibleForIpt" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="panel-body referral hide">
                                            <div class="form-group referral hide">
                                                <label>Referred For TPT</label>
                                                <form:select path="referredForIpt" class="form-control">
                                                    <form:option value="" label="--Select Item"/>
                                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                                </form:select>
                                                <p class="help-block">
                                                    <form:errors path="referredForIpt" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default referral hide">
                                        <div class="panel-heading referral hide">
                                            <div class="form-group referral hide">
                                                <label>Started On TPT </label>
                                                <form:select path="startedOnIpt" class="form-control">
                                                    <form:option value="" label="--Select Item"/>
                                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                                </form:select>
                                                <p class="help-block">
                                                    <form:errors path="startedOnIpt" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="panel-body onipt hide">
                                            <div class="form-group onipt hide">
                                                <label>Date Started On TPT</label>
                                                <form:input path="dateStartedOnIpt" class="form-control general"/>
                                                <p class="help-block">
                                                    <form:errors path="dateStartedOnIpt" class="alert-danger"/>
                                                </p>
                                            </div>
                                            <div class="form-group onipt hide">
                                                <label>Date Completed On TPT</label>
                                                <form:input path="dateCompletedOnIpt" class="form-control general"/>
                                                <p class="help-block">
                                                    <form:errors path="dateCompletedOnIpt" class="alert-danger"/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>


                                </div>

                            </div>

                            <div class="form-group">
                                <button class="btn btn-primary" type="submit" id="back" name="_eventId_back">&Lt;&Lt;Back
                                </button>
                                <button class="btn btn-primary" type="submit" id="next" name="_eventId_next">Next&Gt;&Gt;
                                </button>
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
    $("#screenedForTb").change(function () {
        var screened = $("#screenedForTb").val();
        if (screened == 1) {
            $(".major").removeClass("hide")
        } else {
            $(".major").addClass("hide")
            $('#identifiedWithTb').val('')
            $('#onTBTreatment').val('')
        }
    });
    $("#identifiedWithTb").change(function () {
        var identified = $("#identifiedWithTb").val();
        if (identified == 1) {
            $(".symptoms").removeClass("hide")
        } else {
            $(".symptoms").addClass("hide")
        }
    });
    $("#eligibleForIpt").change(function () {
        var eligible = $("#eligibleForIpt").val();
        if (eligible == 1 && $("#eligibleForIpt").is(":visible")) {
            $(".referral").removeClass("hide")
        } else {
            $(".referral").addClass("hide")
        }
    });
    $("#screenedByHcw").change(function () {
        var eligible = $("#screenedByHcw").val();
        if (eligible == 1 && $("#screenedByHcw").is(":visible")) {
            $(".hcw").removeClass("hide")
        } else {
            $(".hcw").addClass("hide")
        }
    });

    $('#onTBTreatment').change(() => {
        var treat = $("#onTBTreatment").val();
        console.log('Treatment Value:' + treat)
        if (treat == 1 && $("#onTBTreatment").is(":visible")) {
            $(".treatment").removeClass("hide")
        } else {
            $(".treatment").addClass("hide")
        }
        if (treat == 2) {
            $(".notreatment").removeClass("hide")
        } else {
            $(".notreatment").addClass("hide")
        }
    })


    $("#onIpt").change(function () {
        var ipt = $("#onIpt").val();
        if (ipt == 1 && $("#onIpt").is(":visible")) {
            $(".ipt").removeClass("hide")
        } else {
            $(".ipt").addClass("hide")
        }
    });
    $("#startedOnIpt").change(function () {
        var ipt = $("#startedOnIpt").val();
        if (ipt == 1 && $("#startedOnIpt").is(":visible")) {
            $(".onipt").removeClass("hide")
        } else {
            $(".onipt").addClass("hide")
        }
    });


    // configure onload function
    $(function () {
        window.onload = function (ev) {
            var screened = $("#screenedForTb").val();
            if (screened == 1) {
                $(".major").removeClass("hide")
            } else {
                $(".major").addClass("hide")
                $('#identifiedWithTb').val('')
                $('#onTBTreatment').val('')
            }

            var identified = $("#identifiedWithTb").val();
            if (identified == 1) {
                $(".symptoms").removeClass("hide")
            } else {
                $(".symptoms").addClass("hide")
            }

            var eligible = $("#eligibleForIpt").val();
            if (eligible == 1) {
                $(".referral").removeClass("hide")
            } else {
                $(".referral").addClass("hide")
            }

            var treat = $("#onTBTreatment").val();
            if (treat == 1 && $("#onTBTreatment").is(":visible")) {
                $(".treatment").removeClass("hide")
            } else {
                $(".treatment").addClass("hide")
            }
            if (treat == 2) {
                $(".notreatment").removeClass("hide")

            } else {

                $(".notreatment").addClass("hide")
            }


            var eligible = $("#screenedByHcw").val();
            if (eligible == 1 && $("#screenedByHcw")) {
                $(".hcw").removeClass("hide")
            } else {
                $(".hcw").addClass("hide")
            }

            var ipt = $("#onIpt").val();
            if (ipt == 1 && $("#onIpt").is(":visible")) {
                $(".ipt").removeClass("hide")
            } else {
                $(".ipt").addClass("hide")
            }

            var ipt = $("#startedOnIpt").val();
            if (ipt == 1 && $("#startedOnIpt").is(":visible")) {
                $(".onipt").removeClass("hide")
            } else {
                $(".onipt").addClass("hide")
            }

        }
    })


</script>