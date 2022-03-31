<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <a href="../index.htm">Option Tables</a> | <a href="user.form">New User</a> | <a href="user.list">User
                List</a>
                <hr/>
                <%@include file="../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-10">
                        <ul>
                            <li>
                                Username must be an email address
                            </li>
                            <li>
                                Passwords must be at least 8 characters long
                            </li>
                            <li>
                                Passwords must at least have an uppercase, lowercase and digit
                            </li>
                            <li>
                                Passwords must also have at least a special character @#$%
                            </li>
                        </ul>
                        <%@include file="../template/userForm.jspf" %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    $("#userLevel").change(function () {
        $this = $(this);
        var userLevel = $.trim($this.val());
        if (userLevel !== "" || userLevel !== 1) {
            $("form").attr("action", "reload-form").submit();
        }
    });
    $("form").validate({
        rules: {
            firstName: {
                required: true
            },
            lastName: {
                required: true
            },
            userName: {
                required: true
            },
            /*userRoles: {
                required: true
            },*/
            password: {
                required: true
            },
            confirmPassword: {
                required: true
            },
            userType: {
                required: true
            }
        }
    });
    $(window).scrollTop("<c:out value="${item.currentElement}"/>");
</script>
<%@include file="../template/footer.jspf" %>