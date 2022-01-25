<%@include file="template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-12">

                                <table class="table table-striped table-hover" cellspacing="0">
                                    <thead>
                                    <th></th>
                                    <th style="font-size: 13px;" >Clients are active and require service.</th>
                                    <th style="font-size: 13px; text-align: center;" >Clients whom we can not account for.</th>
                                    </thead>
                                </table><br/>

                    </div>
                </div><br/>
                <div class="row">
                    <div class="col-lg-6 col-md-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="medium">
                                        <div>${patientStat['ACTIVE']}</div>
                                        <div>Active Clients</div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <span class="pull-left">&nbsp;</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6">
                        <div class="panel panel-red">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="medium">
                                        <div>${patientStat['LOST_TO_FOLOWUP']}</div>
                                        <div>Lost To Followup</div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <span class="pull-left">&nbsp;</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading"></div>
                                    <table class="table table-striped table-hover" cellspacing="0">
                                        <thead>
                                        <th></th>
                                        <th style="font-size: 13px;" >Clients who have been deceased.</th>
                                        <th style="font-size: 13px; text-align: center;" >Clients who have reached the age of 25.</th>
                                        </thead>
                                    </table>

                        </div>
                        </div>
                    </div><br/>
                <div class="row">
                    <div class="col-lg-6 col-md-6">
                        <div class="panel panel-yellow">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="medium">
                                        <div>${patientStat['GRADUATED']}</div>
                                        <div>GRADUATED Clients</div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <span class="pull-left">&nbsp;</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6">
                        <div class="panel panel-yellow">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="medium">
                                        <div>${patientStat['GRADUATED']}</div>
                                        <div>Graduated Clients</div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <span class="pull-left">&nbsp;</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading"></div>
                                    <table class="table table-striped table-hover" cellspacing="0">
                                        <thead>
                                        <th></th>
                                        <th style="font-size: 13px;">Clients who have chosen to leave the program.</th>
                                        <th style="font-size: 13px; text-align: center;" >Clients with status yet to be established. eg. yet to be located,etc. </th>
                                        </thead>
                                    </table>

                        </div>
                    </div>
                </div><br/>
                <div class="row">
                    <!-- Start of new notifications -->
                    <div class="col-lg-6 col-md-6">
                        <div class="panel panel-red">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="medium">
                                        <div>${patientStat['OPT_OUT']}</div>
                                        <div>Opted Out Clients</div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <span class="pull-left">&nbsp;</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6">
                        <div class="panel panel-red">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="medium">
                                        <div>${patientStat['OTHER']}</div>
                                        <div>Other Clients</div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <span class="pull-left">&nbsp;</span>
                            </div>
                        </div>
                    </div>
                </div>
                <hr  style="border: darkgreen solid 2px;"/>

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