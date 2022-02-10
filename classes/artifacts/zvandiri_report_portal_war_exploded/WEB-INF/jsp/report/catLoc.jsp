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
                        <!--Dashboard panels here -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <%@include file="../template/table.jspf" %> 
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
    $("#province").change(function () {
        $("form#item").submit();
    });
    $("#district").change(function () {
        $("form#item").submit();
    });
</script>