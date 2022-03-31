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
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <%@include file="../template/dashboard/patientProfile.jspf" %>
                        </div>
                    </div>
                </div>
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back
                    To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="col-lg-12">
                        <form:form commandName="item">
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Referral Date</label>
                                <form:input path="referralDate" size="10" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="referralDate" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Expected Visit Date</label>
                                <form:input path="expectedVisitDate" size="10" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="expectedVisitDate" class="alert-danger"/>
                                </p>
                            </div>
                            <br/>
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            Services Referred/ Requested
                                        </div>
                                        <div class="panel-body">
                                            <p class="help-block">
                                                <form:errors path="servicesRequestedError" class="alert-danger"/>
                                            </p>
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>HIV/STI Prevention</label><br/><br/>
                                                    <form:checkboxes path="hivStiServicesReq" items="${hivStiItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="hivStiServicesReq" class="alert-danger"/>
                                                    </p>
                                                </div>
                                                <div class="form-group">
                                                    <label>Laboratory Diagnoses</label><br/><br/>
                                                    <form:checkboxes path="laboratoryReq" items="${laboratoryItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="laboratoryReq" class="alert-danger"/>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>OI/ART Services</label><br/><br/>
                                                    <form:checkboxes path="oiArtReq" items="${oiArtItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="oiArtReq" class="alert-danger"/>
                                                    </p>
                                                </div>
                                                <div class="form-group">
                                                    <label>TB Services</label><br/><br/>
                                                    <form:checkboxes path="tbReq" items="${tbItems}" itemLabel="name"
                                                                     itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="tbReq" class="alert-danger"/>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>SRH Services</label><br/><br/>
                                                    <form:checkboxes path="srhReq" items="${srhItems}" itemLabel="name"
                                                                     itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="srhReq" class="alert-danger"/>
                                                    </p>
                                                </div>
                                                <div class="form-group">
                                                    <label>Psycho-social & Economic Support</label><br/><br/>
                                                    <form:checkboxes path="psychReq" items="${psychItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="psychReq" class="alert-danger"/>
                                                    </p>
                                                </div>
                                                <div class="form-group">
                                                    <label>Legal Services</label><br/><br/>
                                                    <form:checkboxes path="legalReq" items="${legalItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="legalReq" class="alert-danger"/>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Organisation Referred</label>
                                <form:input path="organisation" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="organisation" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Date Attended To</label>
                                <form:input path="dateAttended" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateAttended" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            Servces Provided/ Received
                                        </div>
                                        <div class="panel-body">
                                            <p class="help-block">
                                                <form:errors path="servicesAvailedError" class="alert-danger"/>
                                            </p>
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>HIV/STI Prevention</label><br/><br/>
                                                    <form:checkboxes path="hivStiServicesAvailed" items="${hivStiItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="hivStiServicesAvailed" class="alert-danger"/>
                                                    </p>
                                                </div>
                                                <div class="form-group">
                                                    <label>Laboratory Diagnoses</label><br/><br/>
                                                    <form:checkboxes path="laboratoryAvailed" items="${laboratoryItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="laboratoryAvailed" class="alert-danger"/>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>OI/ART Services</label><br/><br/>
                                                    <form:checkboxes path="oiArtAvailed" items="${oiArtItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="oiArtAvailed" class="alert-danger"/>
                                                    </p>
                                                </div>
                                                <div class="form-group">
                                                    <label>TB Services</label><br/><br/>
                                                    <form:checkboxes path="tbAvailed" items="${tbItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="tbAvailed" class="alert-danger"/>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>SRH Services</label><br/><br/>
                                                    <form:checkboxes path="srhAvailed" items="${srhItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="srhAvailed" class="alert-danger"/>
                                                    </p>
                                                </div>
                                                <div class="form-group">
                                                    <label>Psycho-social & Economic Support</label><br/><br/>
                                                    <form:checkboxes path="psychAvailed" items="${psychItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="psychAvailed" class="alert-danger"/>
                                                    </p>
                                                </div>
                                                <div class="form-group">
                                                    <label>Legal Services</label><br/><br/>
                                                    <form:checkboxes path="legalAvailed" items="${legalItems}"
                                                                     itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                                    <p class="help-block">
                                                        <form:errors path="legalAvailed" class="alert-danger"/>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Attending Officer</label>
                                <form:input path="attendingOfficer" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="attendingOfficer" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Officer Designation</label>
                                <form:input path="designation" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="designation" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Action Taken</label>
                                <form:select path="actionTaken" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="actionTaken" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <c:if test="${canEdit}">
                                    <button class="btn btn-primary" type="submit">Save</button>
                                </c:if>
                                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">
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
            referralDate: {
                required: true
            },
            organisation: {
                required: true
            },
            referralStatus: {
                required: true
            }
        }
    });
</script>