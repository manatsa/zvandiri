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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back
                    To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item" id="beneficiaryContactForm">
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <div class="form-group">
                                <label>Mobile Number</label>
                                <form:input path="mobileNumber" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="mobileNumber" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Email</label>
                                <form:input path="email" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="email" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Address</label>
                                <form:textarea rows="4" path="address" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="address" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <c:if test="${canEdit}">
                                    <button class="btn btn-primary" type="submit">Save</button>
                                </c:if>
                                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">
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
    $("#beneficiaryContactForm").validate({
        rules: {
            contactDate: {
                required: true
            },
            subjective: {
                required: true
            },
            objective: {
                required: true
            },
            plan: {
                required: true
            },
            careLevel: {
                required: true
            },
            location: {
                required: true
            },
            position: {
                required: true
            },
            reason: {
                required: true
            },
            followUp: {
                required: true
            },
            actionTaken: {
                required: true
            },
            assessments: {
                required: true
            }
        }
    });
</script>