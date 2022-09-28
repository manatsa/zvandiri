<%@include file="../template/header.jspf" %>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading bold">
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
                        <form:form commandName="item" id="mentalHealthScreeningForm">
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="currentElement"/>
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
                                        <label>Symptoms</label><br/>
                                        <form:checkboxes path="identifiedRisks" items="${risks}" itemLabel="name"
                                                         itemValue="code" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="identifiedRisks" class="alert-danger"/>
                                        </p>
                                    </div>
                                </div>

                            </div>

                            <div class="panel panel-default risks hide">
                                <div class="panel-heading">
                                    <div class="form-group risks hide">
                                        <div class="form-group risks hide">
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
                                            <label>Supported By</label><br/>
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
                            <div class="form-group risks hide">
                                <label>Patient Referred</label>
                                <form:select path="referral" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="referral" class="alert-danger"/>
                                </p>
                            </div>


                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="#"><button class="btn btn-primary" type="button">Cancel</button></a>
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
        };
    });
</script>