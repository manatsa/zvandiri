<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <a href="item.list">Item List</a><br/><br/>
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-12">
                    <form:form commandName="item">
                        <form:hidden path="id"/>
                        <form:hidden path="version"/>
                        <div class="row">
                            <div class="col-lg-10">
                                <c:if test="${cbo}">
                                    <div class="form-group">
                                        <label>CBO</label>
                                        <form:select path="organisation" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options items="${organisations}" itemValue="id" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="organisation" class="alert-danger"/>
                                        </p>
                                    </div>
                                </c:if>
                                <div class="form-group">
                                    <label>Period</label>
                                    <form:select path="period" class="form-control">
                                        <form:option value="" label="--Select Item"/>
                                        <form:options items="${periods}" itemValue="id" itemLabel="name"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="period" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>HTC Male</label>
                                    <form:input path="htc" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="htc" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>HTC Female</label>
                                    <form:input path="htcFemale" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="htcFemale" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>VMMC</label>
                                    <form:input path="vmmc" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="vmmc" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>PMTCT</label>
                                    <form:input path="pmtct" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="pmtct" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>Family Planning</label>
                                    <form:input path="familyPlanning" class="form-control"/>
                                    <p class="help-block">
                                        <form:errors path="familyPlanning" class="alert-danger"/>
                                    </p>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-primary" type="submit">Save</button>
                                    <a href="${itemDelete}">
                                        <button class="btn btn-primary" type="button">Cancel</button>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
        <div class="panel-footer">
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>