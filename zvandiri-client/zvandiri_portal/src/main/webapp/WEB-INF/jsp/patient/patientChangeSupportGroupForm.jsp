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
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item">
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <div class="form-group">
                                <label>Current Region : </label>${item.patient.primaryClinic.district.province.name}
                            </div>
                            <div class="form-group">
                                <label>Current District : </label>${item.patient.primaryClinic.district.name}
                            </div>
                            <div class="form-group">
                                <label>Current Support Group : </label>${item.patient.supportGroup.name}
                            </div>
                            <div class="form-group">
                                <label>Region</label>
                                <form:select path="patient.province" id="province" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${provinces}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="patient.province" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>District</label>
                                <form:select path="patient.district" id="supportGroupDistrict" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${districts}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="patient.district" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Support Group</label>
                                <form:select path="patient.supportGroup" id="supportGroup" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${supportGroups}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="patient.supportGroup" class="alert-danger"/>
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