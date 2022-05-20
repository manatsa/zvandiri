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
                                <label>Type of Disclosure</label>
                                <form:select path="disclosureType" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="disclosureType" class="alert-danger"/>
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
                            <div class="form-group status-known hide">
                                <label>HIV Disclosure Location</label>
                                <form:select path="hIVDisclosureLocation" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="hIVDisclosureLocation" class="alert-danger"/>
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
                                <form:input path="disablityType" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="disablityType" class="alert-danger"/>
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
    $("#hivStatusKnown").change(function () {
        var name = $("#hivStatusKnown").val();
        console.log($("#hivStatusKnown").val())
        if (name == 1) {
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
            var name = $("#hivStatusKnown").val();
            if (name == 1) {
                $(".status-known").removeClass("hide");
            }else{
                $(".status-known").addClass("hide");
            }
            var result=$('#disability').val();
            if(result==1){
                $('.disable').removeClass('hide');
            }else{
                $('.disable').addClass('hide')
            }
        };
    });
</script>