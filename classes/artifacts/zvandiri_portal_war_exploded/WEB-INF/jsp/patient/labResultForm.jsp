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
                        <form:form commandName="item" id="labResultForm">
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <div class="form-group">
                                <label>Date Taken</label>
                                <form:input path="dateTaken" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateTaken" class="alert-danger"/>
                                </p>
                            </div>
                            <c:if test="${!eid}">
                                <div class="form-group">
                                    <label>Test Type</label>
                                    <form:select path="testType" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options itemValue="code" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="testType" class="alert-danger"/>
                                    </p>
                                </div>
                            </c:if>
                            <c:if test="${!eid}">
                                <div class="form-group">
                                    <label>Result</label>
                                    <form:input path="result" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="result" class="alert-danger"/>
                                    </p>
                                </div>
                            </c:if>
                            <c:if test="${eid}">
                                <div class="form-group">
                                    <label>Result</label><br/>
                                    Positive : <form:radiobutton path="status" value="true" /> 
                                    Negative : <form:radiobutton path="status" value="false" />
                                    <p class="help-block">
                                        <form:errors path="status" class="alert-danger"/>
                                    </p>
                                </div>
                            </c:if>
                            <c:if test="${viralLoad}">
                                <div class="form-group">
                                    <label>TND</label>
                                    <form:input path="tnd" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="tnd" class="alert-danger"/>
                                    </p>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label>Source</label>
                                <form:select path="source" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="source" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Next Lab Due</label>
                                <form:input path="nextTestDate" class="form-control otherdate"/>
                                <p class="help-block">
                                    <form:errors path="nextTestDate" class="alert-danger"/>
                                </p>
                            </div>
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
    $("#labResultForm").validate({
        rules: {
            dateTaken: {
                required: true
            },
            source: {
                required: true
            }
        }
    });
</script>