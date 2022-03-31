<%@include file="../template/header.jspf" %>
<style type="text/css">
    input[type="text"] {
        height: 4em;
        font-size: 16px;
    }
</style>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <%@include file="../template/message.jspf" %>
            <div class="row">
                <div class="col-lg-12">
                    <form method="post">
                        <div class="form-group">
                            <label>Search Users</label>
                            <input type="text" name="search"
                                   placeholder="Search by username or by combination of firstname and lastname"
                                   id="search" class="form-control"/>
                        </div>
                    </form>
                    <table id="userListing" class="display hide" cellspacing="0">
                        <thead>
                        <th>User Name</th>
                        <th>Name</th>
                        <th>&nbsp</th>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <div class="panel-footer">
            &nbsp;
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    var search_url = null;
    var count = 0;
    var current = 0;
    $(":text#search").focus();
    var num_chars = 0;
    $(":text#search").keyup(function () {
        $this = $(this);
        num_chars = $this.val().length;
        if (num_chars >= 3) {
            count++;
            if (count - current >= 1) {
                cancelAjaxRequest(search_url);
            }
            search_url = $.get(path + "/admin/user/search-users", {search: $this.val()}, function (us) {
                $("#userListing").dataTable().fnClearTable(true);
                $("#userListing").removeClass("hide");
                $("#userListing_paginate").removeClass("hide");
                current++;
                for (i = 0; i < us.length; i++) {
                    var user_url = "<a href='" + path + "/admin/user/user.form?id=" + us[i].id + "'>";
                    var manage_password_url = "<a href='" + path + "/admin/managepassword.htm?id=" + us[i].id + "'>";
                    var change_priviledges_url = "<a href='" + path + "/admin/changeprivileges.htm?id=" + us[i].id + "'>";
                    var delete_user_url = "<a href='" + path + "/admin/user/user.delete?id=" + us[i].id + "'>";
                    $("#userListing").dataTable().fnAddData([user_url + us[i].userName + "</a>",
                        us[i].lastName + " " + us[i].firstName,
                        manage_password_url + "Change Password | </a>" + change_priviledges_url + "Edit Privileges | </a>" + delete_user_url + "Delete </a>"]);
                }
            });
        } else {
            $("#userListing").dataTable().fnClearTable(true);
        }
    });
    $("#userListing").dataTable({
        "bFilter": false,
        "bSort": false,
        "bLengthChange": false,
        "bInfo": false
    });
    $("form").submit(function (evt) {
        return false;
    });

    function cancelAjaxRequest(request) {
        if (request !== null)
            request.abort();
    }

    $("#userListing_paginate").addClass("hide");
</script>