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
                        <form:form commandName="patient" id="patientContactDetailsForm">
                            <form:hidden path="currentElement"/>
                            <div class="form-group">
                                <label>Mobile Number</label>
                                <form:input path="mobileNumber" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="mobileNumber" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Own Mobile Phone</label>
                                <form:select path="mobileOwner" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="mobileOwner" class="alert-danger"/>
                                </p>
                            </div>
                                    <div class="form-group own-mobile hide">
                                        <label>Mobile Owner</label>
                                        <form:input path="ownerName" class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="ownerName" class="alert-danger"/>
                                        </p>
                                    </div>
                                <div class="form-group own-mobile hide">
                                <label>Owner Relationship with Client</label>
                                <form:select path="mobileOwnerRelation" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${relationships}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="mobileOwnerRelation" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Secondary Mobile Number</label>
                                <form:input path="secondaryMobileNumber" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="secondaryMobileNumber" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Own Secondary Mobile Phone</label>
                                <form:select path="ownSecondaryMobile" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="ownSecondaryMobile" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group sec-own-mobile hide">
                                <label>Secondary Mobile Owner</label>
                                <form:input path="secondaryMobileOwnerName" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="secondaryMobileOwnerName" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group sec-own-mobile hide">
                                <label>Owner Relationship with Client</label>
                                <form:select path="secondaryMobileownerRelation" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${relationships}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="secondaryMobileownerRelation" class="alert-danger"/>
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
    $("#mobileOwner").change(function () {
        var name = $("#mobileOwner :selected").text();
        if (name === "No") {
            $(".own-mobile").removeClass("hide");
        } else {
            $(".own-mobile").addClass("hide");
        }
    });
    $("#ownSecondaryMobile").change(function () {
        var name = $("#ownSecondaryMobile :selected").text();
        if (name === "No") {
            $(".sec-own-mobile").removeClass("hide");
        } else {
            $(".sec-own-mobile").addClass("hide");
        }
    });
    $(function () {
        window.onload = function () {
            var name = $("#mobileOwner :selected").text();
            if (name === "No") {
                $(".own-mobile").removeClass("hide");
            }
            var name = $("#ownSecondaryMobile :selected").text();
            if (name === "No") {
                $(".sec-own-mobile").removeClass("hide");
            }
        };
    });
</script>