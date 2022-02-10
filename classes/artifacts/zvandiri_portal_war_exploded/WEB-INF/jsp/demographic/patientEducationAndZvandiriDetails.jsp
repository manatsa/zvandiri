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
                        <form:form commandName="patient" id="patientDemographicForm">
                            <form:hidden path="currentElement"/>
                            <%@include file="../../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Education</label>
                                <form:select path="education" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${educations}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="education" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Highest / Current Education Level</label>
                                <form:select path="educationLevel" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${educationLevels}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="educationLevel" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group education-level hide">
                                <label>Reason for not reaching O-Level</label>
                                <form:select path="reasonForNotReachingOLevel" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${reasonForNotReachingOLevels}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="reasonForNotReachingOLevel" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Date Joined Zvandiri</label>
                                <form:input path="dateJoined" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateJoined" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Referrer</label>
                                <form:select path="referer" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${referers}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="referer" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Referrer Name</label>
                                <form:input path="refererName" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="refererName" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit" id="back" name="_eventId_back">&Lt;&Lt;Back</button>
                                <button class="btn btn-primary" type="submit" id="next" name="_eventId_next">Next&Gt;&Gt;</button>
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
<script type="text/javascript">
    $("#educationLevel").change(function () {
        var name = $("#educationLevel :selected").text();
        var education = $("#education :selected").text();
        if (education === "Out of School" && (name === "N/A" || name === "Primary School")) {
            $(".education-level").removeClass("hide");
        } else {
            $(".education-level").addClass("hide");
        }
    });
    $(function () {
        window.onload = function () {
            var name = $("#educationLevel :selected").text();
            var education = $("#education :selected").text();
            if (education === "Out of School" && (name === "N/A" || name === "Primary School")) {
                $(".education-level").removeClass("hide");
            }
        };
    });
</script>