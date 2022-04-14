<%@include file="../template/header.jspf" %>
<style type="text/css">
    input[type="text"]{
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
                            <label>Search Clients</label>
                            <input type="text" name="search" placeholder="Search by uic first name or last name or both first name and lastname" id="search" class="form-control"/>
                        </div>
                    </form>
                    <table id="patientListing" class="display hide" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>UIC</th>
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
            search_url = $.get(path + "/patient/search-children-cats", {search: $this.val()}, function (pat) {
                $("#patientListing").dataTable().fnClearTable(true);
                $("#patientListing").removeClass("hide");
                $("#patientListing_paginate").removeClass("hide");
                current++;
                for (i = 0; i < pat.length; i++) {
                    var part_url = "<a href='dashboard/profile.htm?id=" + pat[i].id + "'>";
                    //var contact_url = "<a href='"+path+"/beneficiary/contact/item.form?patientId=" + pat[i].id + "'>";
                    var contact_url = "<a href='"+path+"/contact?id=" + pat[i].id + "'>";
                    var cadre_url = "<a href='"+path+"/patient/create-cadre?id=" + pat[i].id + "'>";
                   /* var referral_url = "<a href='"+path+"/patient/referral/item.list?id=" + pat[i].id + "'>";
                    var tb_screening_url = "<a href='"+path+"/patient/tb-screening/item.list?id=" + pat[i].id + "'>";
                    var hiv_self_testing_url = "<a href='"+path+"/patient/hiv-self-testing/item.list?id=" + pat[i].id + "'>";*/
                    var mental_health_screening_url = "<a href='"+path+"http://localhost:8080/zvandiri/contact?execution=e7s4?id=" + pat[i].id + "'>";
                    $("#patientListing").dataTable().fnAddData([part_url +pat[i].name+ "</a>",
                        pat[i].patientNumber,
                        pat[i].gender,
                        pat[i].age,
                        pat[i].dateJoined,
                        pat[i].district,
                        pat[i].primaryClinic,
                        pat[i].active === true ?
                            contact_url+"Add Contact | </a>"+cadre_url+" Create Cadre </a>" : ""]);
                        //contact_url+"Contact | </a>"+referral_url+"Referral | </a>" + tb_screening_url+"TB Screening | </a>" + mental_health_screening_url + "Mental Health Screening</a>" : ""]);
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
        "bInfo": false});
    $("form").submit(function (evt) {
        return false;
    });
    function cancelAjaxRequest(request) {
        if (request !== null)
            request.abort();
    }
    $("#patientListing_paginate").addClass("hide");
</script>