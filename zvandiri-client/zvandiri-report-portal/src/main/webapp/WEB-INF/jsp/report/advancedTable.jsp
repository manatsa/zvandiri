<%@include file="../template/header.jspf" %>
<style type="text/css">
    .sidebar-nav {
        display: none;
    }

    #page-wrapper {
        margin: 0 ! important;
    }

    .make-scroll {
        height: 445px;
        overflow: scroll;
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
                    <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To Reports DashBoard Home</a>
                    <%@include file="../template/searchAggregateFragment.jspf" %>
                    <div class="col-lg-12">
                        <!--Dashboard panels here -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <%@include file="../template/specTable.jspf" %>
                                    <div class="panel-footer" style="text-align: right">
                                        Export/ View As
                                        <a href="${page}${excelExport}">
                                            <img src="<c:url value="/resources/images/excel.jpeg"/>"/>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>