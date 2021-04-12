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
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <div class="form-group">
                                <label>First Name</label>
                                <form:input path="pfirstName" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="pfirstName" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Last Name</label>
                                <form:input path="plastName" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="plastName" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Mobile Number</label>
                                <form:input path="pmobileNumber" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="pmobileNumber" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Gender</label>
                                <form:select path="pgender" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="pgender" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Relationship to child</label>
                                <form:select path="relationship" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${relationships}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="relationship" class="alert-danger"/>
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
    $("#pdateOfBirth").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "dd/mm/yy",
        yearRange: caregiver
    });
    $("form").validate({
       rules: {
           pfirstName: {
               required: true
           },
           plastName: {
               required: true
           },
           pmobileNumber: {
               required: true
           },
           pgender: {
               required: true
           },
           relationship: {
               required: true
           }
       } 
    });
</script>