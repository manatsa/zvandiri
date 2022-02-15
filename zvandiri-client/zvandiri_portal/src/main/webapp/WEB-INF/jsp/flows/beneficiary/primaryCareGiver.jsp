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
                </div><br/>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="patient" id="primaryCareGiver">
                            <div class="form-group">
                                <label>Registering Self</label>
                                <form:select path="selfPrimaryCareGiver" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="selfPrimaryCareGiver" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group self-care-giver hide">
                                <label>Relationship to child</label>
                                <form:select path="relationship" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${relationships}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="relationship" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group self-care-giver hide">
                                <label>First Name</label>
                                <form:input path="pfirstName" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="pfirstName" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group self-care-giver hide">
                                <label>Last Name</label>
                                <form:input path="plastName" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="plastName" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group self-care-giver hide">
                                <label>Mobile Number</label>
                                <form:input path="pmobileNumber" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="pmobileNumber" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group self-care-giver hide">
                                <label>Gender</label>
                                <form:select path="pgender" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="pgender" class="alert-danger"/>
                                </p>
                            </div>                            
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit" id="back" name="_eventId_${heuval}">&Lt;&Lt;Back</button>
                                <button class="btn btn-primary" type="submit" id="save" name="_eventId_save">Save</button>
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
    $("#selfPrimaryCareGiver").change(function () {
        var name = $("#selfPrimaryCareGiver").val();
        if (name == 2) {
            $(".self-care-giver").removeClass("hide");
        } else {
            $(".self-care-giver").addClass("hide");
        }
    });
    $(function () {
        window.onload = function () {
            var name = $("#selfPrimaryCareGiver").val();
            if (name === 2) {
                $(".self-care-giver").removeClass("hide");
            }
        };
    });
</script>