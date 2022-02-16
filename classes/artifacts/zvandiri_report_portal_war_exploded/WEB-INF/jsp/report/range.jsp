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
                    <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To Reports DashBoard Home</a>
                    <%@include file="../template/searchDateRangeFragment.jspf" %>
                    <div class="col-lg-12">
                        <!--Dashboard panels here -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <%@include file="../template/detailedTable.jspf" %> 
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
<script type="text/javascript">
    $(".sidebar-nav").addClass("custom-side-bar-ref");
    $("#page-wrapper").addClass("main-wrp");
    // ensire toggle side bar is pointing right
    $("span.toggle-span").addClass("fa-long-arrow-right");
    $("span.toggle-span").removeClass("fa-long-arrow-left");
    ("#item").validate({
        rules: {
            province: {
                required: true
            }
        } 
     });
</script>