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
                        <form:form commandName="item" action="${formAction}">
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="person" value="${item.person.id}"/>
                            <div class="form-group">
                                <label>Tested At Health Facility Result</label>
                                <form:select path="testedAtHealthFacilityResult" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${results}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="testedAtHealthFacilityResult" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Self Test Kit Distributed?</label>
                                <form:select path="selfTestKitDistributed" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="selfTestKitDistributed" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>HIS Self Testing Result</label>
                                <form:select path="hisSelfTestingResult" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${results}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="hisSelfTestingResult" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Home Based Testing Result</label>
                                <form:select path="homeBasedTestingResult" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${results}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="homeBasedTestingResult" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Confirmatory Testing Result</label>
                                <form:select path="confirmatoryTestingResult" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${results}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="confirmatoryTestingResult" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>ART Initiation</label>
                                <form:select path="artInitiation" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="artInitiation" class="alert-danger"/>
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