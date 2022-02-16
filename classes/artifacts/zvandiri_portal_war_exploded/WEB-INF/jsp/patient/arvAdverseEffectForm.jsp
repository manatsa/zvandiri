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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item" id="arvAdverseEffectForm">
                            <form:hidden path="arvHist" value="${item.arvHist.id}"/>
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Event</label>
                                <form:input path="event" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="event" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Date Commenced</label>
                                <form:input path="dateCommenced" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateCommenced" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Status</label>
                                <form:select path="status" class="form-control">
                                    <form:option value="" label="--Select Item--"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="status" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Source</label>
                                <form:select path="source" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="source" class="alert-danger"/>
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
<script type="text/javascript">
    $(function){
        $("#arvAdverseEffectForm").validate({
            rules: {
                event: {
                    required: true
                },
                dateCommenced: {
                    required: true
                },
                status: {
                    required: true
                },
                source: {
                    required: true
                }
            }
        });
    }
</script>