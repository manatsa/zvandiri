<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <div class="row">
                    <!--Start of top left panel -->
                    <div class="col-lg-12">
                        <div class="panel-body">
                            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To Reports DashBoard Home</a>
                            <%@include file="../template/searchDateRangeNewFragment.jspf" %>
                            <img class="img-responsive" src="${page}/report/graphs${report}"/>
                        </div>  
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../template/footer.jspf" %>