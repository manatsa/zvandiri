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
                <a href="${page}/cadre/view?id=${cadre.id}">&DoubleLeftArrow; Back To ${cadre.name} Dashboard</a><br/><br/>
                <div class="row">

                    <div class="col-lg-10">
                        <form:form commandName="item" id="catPhoneForm" action="${formAction}">
                            <form:hidden path="cadre" value="${cadre}"/>
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Bicycle Type</label>
                                <form:input path="bikeType" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="bikeType" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group sec-own-mobile">
                                <label>Bike Condition</label>
                                <form:select path="bikeCondition" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${bikeCondition}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="bikeCondition" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group sec-own-mobile">
                                <label>Bicycle Status</label>
                                <form:select path="bikeStatus" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${bikeStatus}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="bikeStatus" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group sec-own-mobile">
                                <label>Date Issued</label>
                                <form:input  path="dateIssued"  class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateIssued" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group sec-own-mobile">
                                <label>Date Recovered</label>
                                <form:input  path="dateRecovered"  class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateRecovered" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group">
                                <label>Bicycle Issues</label>
                                <form:textarea  path="bikeIssues" size="6" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="bikeIssues" class="alert-danger"/>
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