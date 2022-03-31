<%@include file="template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="template/message.jspf" %>
                <%@include file="template/indexTopNotifications.jspf" %>
                <div class="row">
                    <!--Start of top left panel -->
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Distribution of Clients By Age Group
                            </div>
                            <div class="panel-body">
                                <img class="img-responsive" src="${page}${patientAgeGroupDistribution}"/>
                            </div>
                        </div>
                    </div>
                    <!--Start of top right panel -->
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Notifications
                            </div>
                            <div class="panel-body">
                                <div class="list-group">
                                    <table class="table table-striped table-hover" cellspacing="0">
                                        <thead>
                                        <th></th>
                                        <th style="font-size: 13px;">3 Months</th>
                                        <th style="font-size: 13px;">6 Months</th>
                                        <th style="font-size: 13px;">12 Months</th>
                                        </thead>
                                        <tbody>
                                        <c:set var="count" value="0"/>
                                        <c:forEach var="d" items="${notifications}">
                                            <c:if test="${count <= 8}">
                                                <tr>
                                                    <td>${d.name}</td>
                                                    <td>${d.num}</td>
                                                    <td>${d.num6Months}</td>
                                                    <td>${d.num12Months}</td>
                                                </tr>
                                            </c:if>
                                            <c:set var="count" value="${count + 1}"/>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <!-- Start of  bottom left panel-->
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Number of contacts past 6 months
                            </div>
                            <div class="panel-body">
                                <img class="img-responsive" src="${page}${contactLevelOfCareDistribution}"/>
                            </div>
                        </div>
                    </div>
                    <!--Start of bottom right panel -->
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Trends of Contacts by care level
                            </div>
                            <div class="panel-body">
                                <img class="img-responsive" src="${page}${contactLevelTrend}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="template/footer.jspf" %>
    <script type="text/javascript">
        $(".sidebar-nav").addClass("custom-side-bar-ref");
        $("#page-wrapper").addClass("main-wrp");
        // ensire toggle side bar is pointing right
        $("span.toggle-span").addClass("fa-long-arrow-right");
        $("span.toggle-span").removeClass("fa-long-arrow-left");
    </script>