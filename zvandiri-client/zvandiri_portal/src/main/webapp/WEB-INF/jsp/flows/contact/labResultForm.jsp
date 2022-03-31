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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back
                    To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="labResult" id="labResultForm">
                            <%@include file="../../template/formState.jspf" %>
                            <%--<form:hidden path="patient" value="${item.patient.id}"/>--%>
                            <div class="form-group">
                                <label>VL/CD4 count Test Done</label>
                                <form:select path="testDone" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="testDone" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group major hide">
                                <label>Date Taken</label>
                                <form:input path="dateTaken" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateTaken" class="alert-danger"/>
                                </p>
                            </div>
                            <%--<c:if test="${!eid}">--%>
                            <div class="form-group major hide">
                                <label>Test Type</label>
                                <form:select path="testType" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="testType" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group major hide">
                                <label>VL/CD4 Results Available</label>
                                <form:select path="haveResult" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="haveResult" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group result hide">
                                <label>VL/CD4 Result</label>
                                <form:input path="result" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="result" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group result hide">
                                <label>TND</label>
                                <form:input path="tnd" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="tnd" class="alert-danger"/>
                                </p>
                            </div>
                            <%--</c:if>--%>
                            <div class="form-group major hide">
                                <label>Source</label>
                                <form:select path="source" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="source" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group major hide">
                                <label>Next Lab Due</label>
                                <form:input path="nextTestDate" class="form-control otherdate"/>
                                <p class="help-block">
                                    <form:errors path="nextTestDate" class="alert-danger"/>
                                </p>
                            </div>

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
    /* $("#labResultForm").validate({
         rules: {
             dateTaken: {
                 required: true
             },
             source: {
                 required: true
             }
         }
     });*/

    $("#testDone").change(function () {
        var result = $("#testDone").val();
        if (result == 1) {
            $(".major").removeClass("hide")
        } else {
            $(".major").addClass("hide")
        }
    });

    $("#haveResult").change(function () {
        var result = $("#haveResult").val();
        if (result == 1) {
            $(".result").removeClass("hide")
        } else {
            $(".result").addClass("hide")
        }
    });

    $("#result").change(function () {

        var result = $("#result").val();
        var tnd = $("#tnd").val();
        if (result != null && result != '') {
            $("#tnd").val('');
        } else {
            $("#tnd").val(tnd);
        }
    })

    $("#tnd").change(function () {
        var result = $("#result").val();
        var tnd = $("#tnd").val();
        if (tnd != null && tnd != '') {
            $("#result").val('');
        } else {
            $("#result").val(result);
        }

    })

    // configure onload function
    $(function () {
        window.onload = function (ev) {
            var result = $("#testDone").val();
            if (result == 1) {
                $(".major").removeClass("hide")
            } else {
                $(".major").addClass("hide")
            }

            var result = $("#haveResult").val();
            if (result == 1) {
                $(".result").removeClass("hide")
            } else {
                $(".result").addClass("hide")
            }


            var result = $("#result").val();
            var tnd = $("#tnd").val();
            //console.log("Result Change : "+result)
            if (result != null && result != '') {
                $("#tnd").val('');
            } else {
                $("#tnd").val(tnd);
            }

            if (tnd != null && tnd != '') {
                $("#result").val('');
            } else {
                $("#result").val(result);
            }


        }
    })

</script>