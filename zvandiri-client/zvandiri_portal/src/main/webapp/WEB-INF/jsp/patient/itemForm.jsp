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
                        <form:form commandName="item" action="${formAction}">
                            <form:hidden path="patientExist"/>
                            <form:hidden path="currentElement"/>
                            <form:hidden path="status"/>
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>&nbsp;</label>
                                <p class="help-block">
                                    <form:errors path="patientExist" class="alert-danger"/>
                                </p>
                            </div>
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
                            <div class="form-group">
                                <label>OI Number</label>
                                <form:input path="oINumber" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="oINumber" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Art Regimen</label>
                                <form:input path="artRegimen" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="artRegimen" class="alert-danger"/>
                                </p>
                            </div>
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
                                <label>Owner Relationship with Beneficiary</label>
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
                                <label>Owner Relationship with Beneficiary</label>
                                <form:select path="secondaryMobileownerRelation" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${relationships}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="secondaryMobileownerRelation" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Address 1</label>
                                <form:input path="address" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="address" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Street Name</label>
                                <form:input path="address1" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="address1" class="alert-danger"/>
                                </p>
                            </div>
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
                                <label>Support District</label>
                                <form:select path="supportGroupDistrict" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${districts}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="supportGroupDistrict" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Support Group</label>
                                <form:select path="supportGroup" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${supportGroups}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="supportGroup" class="alert-danger"/>
                                </p>
                            </div>
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
                                <label>HIV Status Known</label>
                                <form:select path="hivStatusKnown" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="hivStatusKnown" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group status-known hide">
                                <label>Mode of Transmission</label>
                                <form:select path="transmissionMode" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="transmissionMode" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group status-known hide">
                                <label>Date Tested</label>
                                <form:input path="dateTested" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateTested" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group status-known ">
                                <label>HIV Disclosure Location</label>
                                <form:select path="hIVDisclosureLocation" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="hIVDisclosureLocation" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group status-known hide">
                                <label>Type of Disclosure</label>
                                <form:select path="disclosureType" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="disclosureType" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Has disability</label>
                                <form:select path="disability" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="disability" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group disable hide">
                                <label>Disability Type</label>
                                <form:input path="disablityType" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="disablityType" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Consent M-Health</label>
                                <form:select path="consentToMHealth" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="consentToMHealth" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>CATS Member</label>
                                <form:select path="cat" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="cat" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Is In YMM Programme</label>
                                <form:select path="youngMumGroup" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options  itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="youngMumGroup" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Is In YMD Programme</label>
                                <form:select path="youngDadGroup" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="youngMumGroup" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <c:if test="${canEdit}"><button class="btn btn-primary" type="submit">Save</button></c:if>
                                <a href="${page}/patient/index.htm?type=3"><button class="btn btn-primary" type="button">Cancel</button></a>
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
    $("#educationLevel").change(function () {
        var name = $("#educationLevel :selected").text();
        var education = $("#education :selected").text();
        if (education === "Out of School" && (name === "N/A" || name === "Primary School")) {
            $(".education-level").removeClass("hide");
        } else {
            $(".education-level").addClass("hide");
        }
    });

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

    $("#hivStatusKnown").change(function () {
        var name = $("#hivStatusKnown").val();
        console.log('HIV STATUS KNOWN :', name, ' TYPE:', typeof name)
        if (name === '1') {
            console.log('HIV STATUS KNOWN :', name)
            $(".status-known").removeClass("hide");
        } else {
            $("#transmissionMode").val('');
            $("#dateTested").val('');
            $("#hIVDisclosureLocation").val('');
            $(".status-known").addClass("hide");
        }
    });

    $('#disability').change(()=>{
        var dis=$('#disability').val();
        if(dis==1){
            $('.disable').removeClass('hide');
        }else{
            $('.disable').addClass('hide')
        }
    })

    $(function () {
        window.onload = function () {
            var name = $("#educationLevel :selected").text();
            var education = $("#education :selected").text();
            if (education === "Out of School" && (name === "N/A" || name === "Primary School")) {
                $(".education-level").removeClass("hide");
            }
            var name = $("#hivStatusKnown").val();
            if (name == '1') {
                $(".status-known").removeClass("hide");
            }else{
                $(".status-known").addClass("hide");
            }
            var name = $("#mobileOwner :selected").text();
            if (name === "No") {
                $(".own-mobile").removeClass("hide");
            }
            var name = $("#ownSecondaryMobile :selected").text();
            if (name === "No") {
                $(".sec-own-mobile").removeClass("hide");
            }

            var result=$('#disability').val();
            if(result==1){
                $('.disable').removeClass('hide');
            }else{
                $('.disable').addClass('hide')
            }
        };
    });
    <%--$(window).scrollTop("<c:out value="${item.currentElement}"/>");--%>
</script>