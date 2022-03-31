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
                            <%@include file="../template/dashboard/cadreProfile.jspf" %>
                        </div>
                    </div>
                </div>
                <a href="${page}/cadre/view?id=${cadre.id}">&DoubleLeftArrow; Back To ${cadre.name}
                    Dashboard</a><br/><br/>
                <div class="row">

                    <div class="col-lg-10">
                        <form:form commandName="item" id="catPhoneForm" action="${formAction}">
                            <form:hidden path="cadre" value="${item.cadre}"/>
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Mobile Phone Make</label>
                                <form:input path="phoneMake" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="phoneMake" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Mobile Phone Model</label>
                                <form:input path="serialNumber" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="serialNumber" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Mobile Phone Serial Number</label>
                                <form:input path="phoneModel" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="phoneModel" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Phone Number 1</label>
                                <form:input path="msisdn1" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="msisdn1" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Phone Number 2</label>
                                <form:input path="msisdn2" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="msisdn2" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Imei Number 1</label>
                                <form:input path="imei1" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="imei1" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Imei Number 2</label>
                                <form:input path="imei2" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="imei2" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group sec-own-mobile">
                                <label>Phone Condition</label>
                                <form:select path="phoneCondition" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${phoneCondition}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="phoneCondition" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group sec-own-mobile">
                                <label>Phone Status</label>
                                <form:select path="phoneStatus" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${phoneStatus}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="phoneStatus" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group sec-own-mobile">
                                <label>Date Issued</label>
                                <form:input path="dateIssued" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateIssued" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group sec-own-mobile">
                                <label>Date Recovered</label>
                                <form:input path="dateRecovered" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateRecovered" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group">
                                <label>Phone Issues</label>
                                <form:textarea path="phoneIssues" size="6" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="phoneIssues" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="${page}/cadre/view?id=${cadre.id}">
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