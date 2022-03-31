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
                        <form:form commandName="item" action="${action}">
                        <form:hidden path="user" value="${item.user.id}"/>
                        <div class="form-group">
                            <label>Username:</label>
                            <label class="form-control">${item.user.userName}</label>
                        </div>
                        <div class="form-group">
                            <label>User Roles</label><br/><br/>
                            <form:checkboxes path="user.userRoles" items="${roles}" itemLabel="printName" itemValue="id"
                                             delimiter="<br/>"/>
                            <p class="help-block">
                                <form:errors path="user.userRoles" class="alert-danger"/>
                            </p>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary" type="submit">Save</button>
                            <a href="../admin/user/">
                                <button class="btn btn-primary" type="button">Cancel</button>
                            </a>
                        </div>
                    </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
