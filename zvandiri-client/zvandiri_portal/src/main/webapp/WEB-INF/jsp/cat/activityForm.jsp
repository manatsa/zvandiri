<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">${pageTitle}</div>
            <div class="panel-body">
                <%@include file="../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-10">
                        <div class="panel panel-default">
                            <%@include file="../template/dashboard/patientProfile.jspf" %>
                        </div>
                    </div>
                </div>
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow;
                    Back To ${patient.name} Dashboard</a><br/> <br/>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item">
                            <form:hidden path="catDetail" value="${item.catDetail.id}"/>
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Certificate Number</label>
                                <form:input path="certificateNumber" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="certificateNumber" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Date Received Mentorship</label>
                                <form:input path="dateReceivedMentorship"
                                            class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateReceivedMentorship" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Mentorship Type</label>
                                <form:select path="catsMentorship" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="catsMentorship" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Date Issued</label>
                                <form:input path="dateIssued" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateIssued" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Assigned Phone</label>
                                <form:select path="assignedPhone" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="assignedPhone" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Phone Model</label>
                                <form:input path="phoneModel" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="phoneModel" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Serial Number</label>
                                <form:input path="serialNumber" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="serialNumber" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Phone Status</label>
                                <form:select path="phoneStatus" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="phoneStatus" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Issued Bicycle</label>
                                <form:select path="issuedBicycle" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="issuedBicycle" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Province</label>
                                <form:select path="province" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${provinces}" itemValue="id"
                                                  itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="province" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>District</label>
                                <form:select path="district" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${districts}" itemValue="id"
                                                  itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="district" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Comment</label>
                                <form:textarea rows="4" path="description" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="description" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="">
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