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
                                Assign users to sections of the system they will be able to see
                            </li>
                            <li>
                                For example for users at national level just select national
                            </li>
                        </ul>
                        <form:form commandName="item" action="${formAction}">
                            <form:hidden path="user" value="${item.user.id}"/>
                            <div class="form-group">
                                <label >Username:</label>
                                <label class="form-control">${item.user.userName}</label>
                            </div> 
                            <div class="form-group">
                                <label>User Level:</label>
                                <form:select path="user.userLevel" class="form-control">
                                    <form:option value="" label="--Select Item--"/>
                                    <form:options items="${levels}" itemLabel="name" itemValue="code"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="user.userLevel" class="alert-danger"  />
                                </p>
                            </div>
                            <div class="form-group">
                                <label>CBO:</label>
                                <form:select path="user.organisation" class="form-control">
                                    <form:option value="" label="--Select Item--"/>
                                    <form:options items="${organisations}" itemLabel="name" itemValue="id"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="user.organisation" class="alert-danger"  />
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
<script type="text/javascript">
    $("select").change(function () {
        $this = $(this);
        $("form").attr("action", "re-load-user-pref.htm").submit();
    });
    $("#user.province").change(function () {
        $this = $(this);
        $("form").attr("action", "re-load-user-pref.htm").submit();
    });
</script>