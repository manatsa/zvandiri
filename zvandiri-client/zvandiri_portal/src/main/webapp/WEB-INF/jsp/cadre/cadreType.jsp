<%@include file="../template/header.jspf" %>
<form:form commandName="item" id="cadreForm" action="${formAction}">
    <div class="form-group sec-own-mobile">
        <label>Cadre Type</label>
        <form:select path="caderType" class="form-control">
            <form:option value="" label="--Select Item"/>
            <form:options items="${caderTypes}" itemValue="code" itemLabel="name"/>
        </form:select>
        <p class="help-block">
            <form:errors path="caderType" class="alert-danger"/>
        </p>
    </div>
    <div class="form-group">
        <button class="btn btn-primary" type="submit"> &nbsp; OK &nbsp; </button>
    </div>
</form:form>
<%@include file="../template/footer.jspf" %>
