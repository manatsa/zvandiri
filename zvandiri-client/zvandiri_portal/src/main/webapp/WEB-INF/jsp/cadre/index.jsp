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
            <div class="row" style="margin-right: 10px;">
                ${pageTitle}
                <div class="pull-right"><a href="${page}/cadre/item.form">Add New Cadre</a></div>
            </div>
        </div>
        <div class="panel-body">
            <%@include file="../template/message.jspf" %>
            <div class="row">

            </div>
            <div class="row">
                <div class="col-lg-12">
                    <form method="post">
                        <div class="form-group">
                            <label>Search Cadres</label>
                            <input type="text" name="search"
                                   placeholder="Search by first name or last name or both first name and last name"
                                   id="search" class="form-control"/>
                        </div>
                    </form>
                    <table id="cadreListing" class="display" cellspacing="0">
                        <thead>
                        <th>Cadre Name</th>
                        <th>Gender</th>
                        <th>Cadre Type</th>
                        <th>Primary Clinic</th>
                        <th>District</th>
                        <th> Province</th>
                        <th> Cadre Management</th>
                        </thead>
                        <tbody>
                        <c:forEach var="cadre" items="${cadres}">
                            <tr>
                                <td>
                                    <a href='/zvandiri/cadre/view.htm?id=${cadre.id}'>${cadre.firstName} ${cadre.lastName}</a>
                                </td>
                                <td>${cadre.gender.name}</td>
                                <td>${cadre.caderType.name}</td>
                                <td>${cadre.primaryClinic.name}</td>
                                <td>${cadre.district.name}</td>
                                <td>${cadre.province.name}</td>
                                <td>
                                    <a href='/zvandiri/cadre/item.form?id=${cadre.id}'>Edit</a> | <a href='#'>Delete</a>
                                        <%--                                        <a href='/zvandiri/cadre/delete.htm?id=${cadre.id}'>Delete</a>--%>
                                </td>
                            </tr>
                        </c:forEach>
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
            search_url = $.get(path + "/cadre/search-cadres", {search: $this.val()}, function (pat) {
                $("#cadreListing").dataTable().fnClearTable(true);
                $("#cadreListing").removeClass("hide");
                $("#patientListing_paginate").removeClass("hide");
                current++;
                for (i = 0; i < pat.length; i++) {
                    var view_url = "<a href='/zvandiri/cadre/view.htm?id=" + pat[i].id + "'>";
                    var edit_url = "<a href='/zvandiri/cadre/edit?id=" + pat[i].id + "'>";
                    //var del_url = "<a href='/zvandiri/cadre/delete?id=" + pat[i].id + "'>";
                    var del_url = "<a href='#'>";

                    $("#cadreListing").dataTable().fnAddData([view_url + pat[i].firstName + ' ' + pat[i].lastName + "</a>",
                        pat[i].gender,
                        pat[i].type,
                        pat[i].primaryClinic,
                        pat[i].district,
                        pat[i].province.name,
                        pat[i].active === true ?
                            edit_url + " Edit | </a>" + del_url + " Delete  </a>" : " "]);
                }
            });
        } else {
            $("#cadreListing").dataTable().fnClearTable(true);
        }
    });
    $("#cadreListing").dataTable({
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

    $("#patientListing_paginate").addClass("hide");
</script>