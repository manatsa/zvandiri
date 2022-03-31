<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <a href="../index.htm">Option Tables</a>
                <hr/>
                <%@include file="../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item">
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Patient Minimum Age</label>
                                <form:input path="patientMinAge" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="patientMinAge" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Patient Maximum Age</label>
                                <form:input path="patientMaxAge" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="patientMaxAge" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Heu Mother Max Age</label>
                                <form:input path="heuMotherMaxAge" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="heuMotherMaxAge" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Min Age To Give Birth</label>
                                <form:input path="minAgeToGiveBirth" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="minAgeToGiveBirth" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Number Of Months To Auto Retire Patient After Maximum Age Has Been
                                    Reached</label>
                                <form:input path="patientAutoExpireAfterMaxAge" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="patientAutoExpireAfterMaxAge" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Cat Minimum Age</label>
                                <form:input path="catMinAge" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="catMinAge" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Cat Maximum Age</label>
                                <form:input path="catMaxAge" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="catMaxAge" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>CD4 Minimum Value For Flagging</label>
                                <form:input path="cd4MinCount" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="cd4MinCount" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Viral Load Maximum Count For Flagging</label>
                                <form:input path="viralLoadMaxCount" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="viralLoadMaxCount" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Maximum Number of Contacts For Index Screen Display</label>
                                <form:input path="maxNumContactIndex" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="maxNumContactIndex" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="${itemDelete}">
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
    $("form").validate({
        rules: {
            patientMinAge: {
                required: true
            },
            patientMaxAge: {
                required: true
            },
            catMinAge: {
                required: true
            },
            catMaxAge: {
                required: true
            },
            cd4MinCount: {
                required: true
            },
            viralLoadMaxCount: {
                required: true
            },
            patientAutoExpireAfterMaxAge: {
                required: true
            },
            maxNumContactIndex: {
                required: true
            }
        }
    });
</script>