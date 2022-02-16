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
                        <form:form commandName="patient" id="patientDemographicForm">
                            <div class="form-group">
                                <label>First Name</label>
                                <form:input path="firstName" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="firstName" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Middle Name</label>
                                <form:input path="middleName" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="middleName" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Last Name</label>
                                <form:input path="lastName" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="lastName" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Gender</label>
                                <form:select path="gender" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="gender" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Date Of Birth</label>
                                <form:input path="dateOfBirth" class="form-control beneficiary"/> <c:if test="${item.id != null}"> ( ${item.age} years)</c:if>
                                    <p class="help-block">
                                    <form:errors path="dateOfBirth" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group hei-infant hide">
                                <label>HIV exposed uninfected</label>
                                <form:select path="hei" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="hei" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>OI Number</label>
                                <form:input path="oINumber" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="oINumber" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit" id="submit_btn" name="_eventId_next">Next&Gt;&Gt;</button>
                                <a href="${itemDelete}"><button class="btn btn-primary" type="button">Cancel</button></a>
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
    $("#dateOfBirth").change(function () {
        var strDate = $("#dateOfBirth").val();
        strDate = strDate.split("/");
        if (monthDiff(new Date(strDate[2], strDate[1], strDate[0]), new Date()) <= 18) {
            $(".hei-infant").removeClass("hide");
            $("#submit_btn").attr("name", "_eventId_heuval");
        } else {
            $(".hei-infant").addClass("hide");
            $("#submit_btn").attr("name", "_eventId_next");
        }
    });
    $(function () {
        window.onload = function () {
            var strDate = $("#dateOfBirth").val();
            strDate = strDate.split("/");
            if (monthDiff(new Date(strDate[2], strDate[1], strDate[0]), new Date()) <= 24) {
                $(".hei-infant").removeClass("hide");
                $("#submit_btn").attr("name", "_eventId_heuval");
            } else {
                $(".hei-infant").addClass("hide");
                $("#submit_btn").attr("name", "_eventId_next");
            }
        };
    });
</script>