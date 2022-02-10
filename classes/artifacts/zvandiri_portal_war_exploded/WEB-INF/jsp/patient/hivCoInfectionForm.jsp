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
                        <form:form commandName="item">
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="patient" value="${item.patient.id}"/>                            
                            <div class="form-group">
                                <label>HIV co-Infection</label>
                                <form:select path="hivCoInfection" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${hivCoInfections}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="hivCoInfection" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Diagnosis Date</label>
                                <form:input path="infectionDate" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="infectionDate" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Medication</label>
                                <form:input path="medication" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="medication" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Current Status</label>
                                <form:input path="resolution" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="resolution" class="alert-danger"/>
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
    $("form").validate({
       rules: {
           infectionDate: {
               required: true
           },
           medication: {
               required: true
           },
           resolution: {
               required: true
           },
           hivCoInfection: {
               required: true
           }
       } 
    });
</script>