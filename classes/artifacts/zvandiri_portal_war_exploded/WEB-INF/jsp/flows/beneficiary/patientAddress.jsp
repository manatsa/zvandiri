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
                            <%@include file="profileFragment.jspf" %>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="patient" id="patientAddress">
                            <form:hidden path="patientExist"/>
                            <div class="form-group">
                                <label>&nbsp;</label>
                                <p class="help-block">
                                    <form:errors path="patientExist" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Address 1</label>
                                <form:input path="address" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="address" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Street Name</label>
                                <form:input path="address1" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="address1" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Region</label>
                                <form:select path="province" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${provinces}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="province" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Primary Clinic District</label>
                                <form:select path="district" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${districts}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="district" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Primary Clinic</label>
                                <form:select path="primaryClinic" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${facilities}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="primaryClinic" class="alert-danger"/>
                                </p>
                            </div>
                            <c:if test="${!patient.heuReg}">
                                <div class="form-group">
                                    <label>Support District</label>
                                    <form:select path="supportGroupDistrict" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${districts}" itemValue="id" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="supportGroupDistrict" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>Support Group</label>
                                    <form:select path="supportGroup" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${supportGroups}" itemValue="id" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="supportGroup" class="alert-danger"/>
                                    </p>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit" id="back" name="_eventId_${heuval}">&Lt;&Lt;Back</button>
                                <button class="btn btn-primary" type="submit" id="next" name="_eventId_${heunext}">Next&Gt;&Gt;</button>
                                <button class="btn btn-primary" type="submit" id="cancel" name="_eventId_cancel">Cancel</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../../template/footer.jspf" %>