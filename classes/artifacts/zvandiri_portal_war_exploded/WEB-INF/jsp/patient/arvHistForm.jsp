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
                        <form:form commandName="item" id="arvHistForm">
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>ARV Medicine 1</label>
                                <form:select path="arvMedicine" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${aRVDrugRegimens}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="arvMedicine" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>ARV Medicine 2</label>
                                <form:select path="arvMedicine2" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${aRVDrugRegimens}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="arvMedicine2" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>ARV Medicine 3</label>
                                <form:select path="arvMedicine3" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${aRVDrugRegimens}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="arvMedicine3" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Start Date</label>
                                <form:input path="startDate" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="startDate" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>End Date</label>
                                <form:input path="endDate" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="endDate" class="alert-danger"/>
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
    $().ready(function(){
        $("#arvHistForm").validate({
            rules: {
                arvMedicine: "required",
                arvMedicine2: "required",
                arvMedicine3: "required",
                startDate: "required"
            }
        });
    });
</script>