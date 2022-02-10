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
                        <form:form commandName="item">
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <div class="form-group">
                                <label>Date of death</label>
                                <form:input path="dateOfDeath" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateOfDeath" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Cause of death</label>
                                <form:select path="causeOfDeath" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="causeOfDeath" class="alert-danger"/>
                                </p>
                            </div>
                                <div class="form-group">
                                <label>Cause of death description </label>
                                <form:textarea path="causeOfDeathDetails" rows="5" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="causeOfDeathDetails" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Received enhanced care</label>
                                <form:select path="receivingEnhancedCare" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="receivingEnhancedCare" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Date received enhanced care</label>
                                <form:input path="datePutOnEnhancedCare" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="datePutOnEnhancedCare" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Contact with ZM</label>
                                <form:select path="contactWithZM" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="contactWithZM" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Date of contact with ZM</label>
                                <form:input path="dateOfContactWithZim" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateOfContactWithZim" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Case background </label>
                                <form:textarea path="caseBackground" rows="5" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="caseBackground" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Case provided </label>
                                <form:textarea path="careProvided" rows="5" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="careProvided" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Home factors that contributed to poor outcome </label>
                                <form:textarea path="home" rows="5" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="home" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Beneficiary factors that contributed to poor outcome </label>
                                <form:textarea path="beneficiary" rows="5" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="beneficiary" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Facility factors that contributed to poor outcome </label>
                                <form:textarea path="facility" rows="5" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="facility" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>CATS factors that contributed to poor outcome </label>
                                <form:textarea path="cats" rows="5" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="cats" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Other factors that contributed to poor outcome </label>
                                <form:textarea path="other" rows="5" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="other" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Description of case </label>
                                <form:textarea path="descriptionOfCase" rows="5" class="form-control" />
                                <p class="help-block">
                                    <form:errors path="descriptionOfCase" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Learning points </label>
                                <form:textarea path="learningPoints" rows="5" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="learningPoints" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>Action plan </label>
                                <form:textarea path="actionPlan" rows="5" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="actionPlan" class="alert-danger"/>
                                </p>
                            </div> 
                            <div class="form-group">
                                <c:if test="${canEdit}"><button class="btn btn-primary" type="submit">Save</button></c:if>
                                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}"><button class="btn btn-primary" type="button">Cancel</button></a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>