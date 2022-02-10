<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <hr/>
            <%@include file="../template/generalSearchFragment.jspf" %>
            <div class="row">
                <div class="col-lg-12" id="duplicate-div">

                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Possible Duplicates</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Possible Duplicates</th>
                        </tfoot>
                        <tbody>
                            <c:forEach var="item" items="${patients}" >
                                <tr>
                                    <td>${item.fullName}</td>
                                    <td>${item.age}</td>
                                    <td><spring:eval expression="item.dateOfBirth"/></td>
                                    <td>${item.gender.name}</td>
                                    <td>${item.region}</td>
                                    <td>${item.district}</td>
                                    <td>${item.primaryFacility}</td>
                                    <td>
                                        <a href="patient-duplicates?id=${item.id}" class="duplicate-button btn btn-sm btn-primary">${item.count} Duplicate(s) : View</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div id="duplicate-div-right" class="hidden">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Patient Possible Duplicates
                        </div>
                        <div class="row">
                            <div class="col-lg-12 panel-body" id="left-detail-container">

                            </div>
                        </div>
                        <div class="panel-footer">&nbsp;</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel-footer">
            &nbsp;
        </div>
    </div>

    <%@include file="../template/footer.jspf" %>
    <script type="text/javascript">
        $(".sidebar-nav").addClass("custom-side-bar-ref");
        $("#page-wrapper").addClass("main-wrp");
        // ensire toggle side bar is pointing right
        $("span.toggle-span").addClass("fa-long-arrow-right");
        $("span.toggle-span").removeClass("fa-long-arrow-left");
        // unsafe but wil work for now
		var clicked_tr = null;
        $(".duplicate-button").click(function (evt) {
            evt.preventDefault();
            $this = $(this);
            clicked_tr = $this.parent("td").parent();
            var patientId = gup("id", $this.attr("href"));
            $.get(path + "/data/" + $this.attr("href"), function (data) {
                //duplicate-div duplicate-div-right
                $("#duplicate-div").removeClass("col-lg-12").addClass("col-lg-8");
                $("#duplicate-div-right").addClass("col-lg-4");
                var duplicateDetails = "<table class=\"table\">";
                for (var i = 0; i < data.length; i++) {
                    console.log(data[i]);
                    var mergeLink = "<a href=\"merge-patients?id=" + data[i].id + "&patientId=" + patientId + "\" class=\"merge-button btn btn-sm btn-primary\"> Merge</a>";
                    var deleteLink = "<a href=\"" + path + "/patient/item.delete?id=" + data[i].id + "\" class=\"btn btn-sm btn-danger\"> Delete</a>";
                    duplicateDetails += "<tr><th>Name :</th><td>" + data[i].fullName + "</td></tr>";
                    duplicateDetails += "<tr><th>Age :</th><td>" + data[i].age + "</td></tr>";
                    duplicateDetails += "<tr><th>DOB :</th><td>" + data[i].dateOfBirth + "</td></tr>";
                    duplicateDetails += "<tr><th>Gender :</th><td>" + data[i].gender + "</td></tr>";
                    duplicateDetails += "<tr><th>Region :</th><td>" + data[i].region + "</td></tr>";
                    duplicateDetails += "<tr><th>District :</th><td>" + data[i].district + "</td></tr>";
                    duplicateDetails += "<tr><th>Primary Clinic :</th><td>" + data[i].primaryFacility + "</td></tr>";
                    duplicateDetails += "<tr><th>&nbsp;</th><td>" + mergeLink +  "</td></tr>";
                }
                duplicateDetails += "</table>";
                console.log(duplicateDetails);
                $("#left-detail-container").html(duplicateDetails);
                $("#duplicate-div-right").removeClass("hidden");
            });
        });

        $("div#left-detail-container").on("click", ".merge-button", function (evt) {
            evt.preventDefault();
            $this = $(this);
            $.get($this.attr("href"), function (mergedPatientId) {
                $("#duplicate-div").removeClass("col-lg-8").addClass("col-lg-12");
                $("#duplicate-div-right").removeClass("col-lg-4").addClass("hidden");
                $(clicked_tr).remove();
            });
        });

    </script>