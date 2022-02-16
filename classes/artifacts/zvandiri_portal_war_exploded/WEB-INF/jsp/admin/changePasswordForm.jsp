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
                        <ul>
                            <li>
                                Length at least 8 characters and maximum of 100
                            </li>
                            <li>
                                Must contains one digit from 0-9
                            </li>
                            <li>
                                Must contains one at least lowercase character
                            </li>
                            <li>
                                Must contain at least one uppercase character
                            </li>
                            <li>
                                must contains one special symbols in the list "@#$%"
                            </li>
                        </ul>
                        <form:form commandName="item" action="${action}">
                            <form:hidden path="user" value="${item.user.id}"/>
                            <div class="form-group">
                                <label >Username:</label>
                                <label class="form-control">${item.user.userName}</label>
                            </div> 
                            <div class="form-group">
                                <label>Old Password:</label>
                                <form:password path="oldPassword" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="oldPassword" class="alert-danger"  />
                                </p>
                            </div> 
                            <div class="form-group">
                                <label>New Password:</label>
                                <form:password path="newPassword" class="form-control"  />
                                <p class="help-block">
                                    <form:errors path="newPassword" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Confirm Password:</label>
                                <form:password path="confirmPassword" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="confirmPassword" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="../"><button class="btn btn-primary" type="button">Cancel</button></a>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
