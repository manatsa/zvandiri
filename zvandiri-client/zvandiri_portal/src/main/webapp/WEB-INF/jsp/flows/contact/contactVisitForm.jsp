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
                        <form:form modelAttribute="contactVisit" id="contactVisitForm">
                            <%@include file="../../template/formState.jspf" %>

                            <div class="row">
                                <div class="col-lg-12">
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
                                </div>
                            </div>

                            <div class="form-group">
                                <button class="btn btn-primary  nextBtn" type="submit" id="next" name="_eventId_next"
                                        disabled>Next&Gt;&Gt;
                                </button>
                                <button class="btn btn-primary " type="submit" id="cancel" name="_eventId_cancel">
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

    var patientId = "<c:out value="${patient.id}"/>"
    $("#visitOutcome").change(function () {
        var name = $.trim($("#visitOutcome :selected").text().toLowerCase());
        console.log("NAME:" + name)
        if (name === "successful") {
            $(".nextBtn").prop('disabled', false);
        } else {
            $(".nextBtn").prop('disabled', true);
        }
        if (name === "changed location") {
            location.href = path + "/patient/change-facility/item.form?id=" + patientId;
        } else if (name === "deceased") {
            location.href = path + "/patient/patient-death/item.form?id=" + patientId;
        }
    });

</script>