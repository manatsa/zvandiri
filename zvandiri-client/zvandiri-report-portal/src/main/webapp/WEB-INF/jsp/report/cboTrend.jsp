<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-12">
                        <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To Reports DashBoard Home</a>
                        <%@include file="../template/reportSearchCBO.jspf" %>
                        <!--Dashboard panels here -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <img src="${page}${trendChartUrl}"/>
                                    <div class="panel-footer" style="text-align: right">
                                        Export/ View As
                                        <a href="${page}${barUrl}">
                                            <i class="fa fa-bar-chart-o fa-fw"></i>
                                         </a> | 
                                        <a href="${page}${trendExport}">
                                            <img src="<c:url value="/resources/images/pdf.png"/>"/>
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
<script type="text/javascript">
    $("#organisation").change(function () {
        $("form").submit();
    });
    $("#province").change(function () {
        $("#district").val('');
        $("#ward").val('');
        $("form").submit();
    });
    $("#district").change(function () {
        $("#facility").val('');
        $("form").submit();
    });
    $("#facility").change(function () {
        $("form").submit();
    });    
</script>