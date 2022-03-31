<%@include file="../template/header.jspf" %>
<style type="text/css">
    input[type="text"] {
        height: 4em;
        font-size: 16px;
    }
</style>
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
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back
                    To ${patient.name} Dashboard</a><br/><br/>
                <c:if test="${item.motherOfHeu != null}">
                    <div class="row">
                        <div class="col-lg-10">
                            <table class="table-condensed" id="emp-profile" width="100%" style="font-size: 1.1em;">
                                <tbody>
                                <tr>
                                    <th>Name :</th>
                                    <td>
                                            ${item.motherOfHeu.name}
                                    </td>
                                    <th>Age</th>
                                    <td>
                                            ${item.motherOfHeu.age}
                                    </td>
                                    <th>District</th>
                                    <td>
                                            ${item.motherOfHeu.primaryClinic.district.name}
                                    </td>
                                    <th>Primary Clinic</th>
                                    <td>
                                            ${item.motherOfHeu.primaryClinic.name}
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <form:form commandName="item">
                                <form:hidden path="patient" value="${item.patient.id}"/>
                                <form:hidden path="motherOfHeu" value="${item.motherOfHeu.id}"/>
                                <div class="form-group">
                                    <c:if test="${canEdit}">
                                        <button class="btn btn-primary" type="submit">Save</button>
                                    </c:if>
                                    <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">
                                        <button class="btn btn-primary" type="button">Cancel</button>
                                    </a>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </c:if>
                <c:if test="${item.motherOfHeu == null}">
                    <div class="row">
                        <div class="col-lg-10">
                            <form method="post">
                                <div class="form-group">
                                    <label>Search Mothers of HEUs</label>
                                    <input type="text" name="search"
                                           placeholder="Search by first name or last name or both first name and lastname"
                                           id="search" class="form-control"/>
                                </div>
                            </form>
                            <table id="patientListing" class="display hide" cellspacing="0">
                                <thead>
                                <th>Name</th>
                                <th>Gender</th>
                                <th>Age</th>
                                <th>Joined</th>
                                <th>District</th>
                                <th>Primary Clinic</th>
                                <th>&nbsp</th>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    var search_url = null;
    var count = 0;
    var current = 0;
    var patientId = "<c:out value="${patient.id}"/>";
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
            search_url = $.get(path + "/patient/heu/search-heu-mothers", {
                search: $this.val(),
                patientId: patientId
            }, function (pat) {
                $("#patientListing").dataTable().fnClearTable(true);
                $("#patientListing").removeClass("hide");
                $("#patientListing_paginate").removeClass("hide");
                current++;
                for (i = 0; i < pat.length; i++) {
                    var part_url = "<a href='dashboard/profile.htm?id=" + pat[i].id + "'>";
                    var heu_url = "<a href='" + path + "/patient/heu/item.save?patient=" + patientId + "&motherOfHeu=" + pat[i].id + "'>";
                    $("#patientListing").dataTable().fnAddData([heu_url + pat[i].name + "</a>",
                        pat[i].gender,
                        pat[i].age,
                        pat[i].dateJoined,
                        pat[i].district,
                        pat[i].primaryClinic,
                        pat[i].active === true ?
                            heu_url + "Link to Mother</a>" : ""]);
                }
            });
        } else {
            $("#patientListing").dataTable().fnClearTable(true);
        }
    });
    $("#patientListing").dataTable({
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