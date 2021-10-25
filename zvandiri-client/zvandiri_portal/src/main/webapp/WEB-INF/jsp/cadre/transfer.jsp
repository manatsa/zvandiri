<%--
  Created by IntelliJ IDEA.
  User: ManatsaChinyeruse
  Date: 14/10/2021
  Time: 06:49
  To change this template use File | Settings | File Templates.
--%>
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
                            <%--                            <%@include file="../template/dashboard/patientProfile.jspf" %>--%>
                        </div>
                    </div>
                </div>
                <a href="${page}/cadre/view?id=${cadre.id}">&DoubleLeftArrow; Back To ${cadre.name} Dashboard</a><br/><br/>
                <div class="row">

                    <div class="col-lg-10">
                        <form:form commandName="item" id="transferForm">
                           <%--<form:hidden path="id" value="${item.id}"/>--%>
                            <%--<%@include file="../template/formState.jspf" %>--%>

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

                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="${page}/cadre/view?id=${cadre.id}"><button class="btn btn-primary" type="button">Cancel</button></a>
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
    $("#catPhoneForm").validate({
        rules: {
            msisdn1: {
                required: true
            },
            imei1: {
                required: true
            },
            phoneMake: {
                required: true
            },
            phoneModel: {
                required: true
            },

        }
    });
</script>
