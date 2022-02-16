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
                        <form:form commandName="item" action="${formAction}">
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Name of Client</label>
                                <form:input path="nameOfClient" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="nameOfClient" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Age</label>
                                <form:input path="age" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="age" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Date of Birth</label>
                                <form:input path="dateOfBirth" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateOfBirth" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Gender</label>
                                <form:select path="gender" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${gender}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="gender" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Mother's Name</label>
                                <form:input path="nameOfMother" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="nameOfMother" class="alert-danger"/>
                                </p<>
                            </div>
                            <div class="form-group">
                                <label>Province</label>
                                <form:select path="province" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${provinces}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="province" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>District Of Birth</label>
                                <form:select path="district" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${district}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="district" class="alert-danger"/>
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
</script>