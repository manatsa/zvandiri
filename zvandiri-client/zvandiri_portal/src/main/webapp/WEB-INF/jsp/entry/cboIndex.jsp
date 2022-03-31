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
                        <%@include file="../template/searchCBoFragment.jspf" %>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    $("#province").change(function () {
        /*
         * un select district or else un selection will happen after post
         */
        $("#district").val('');
        $("form").attr("action", "index.htm").submit();
    });
    $("#district").change(function () {
        $("form").attr("action", "index.htm").submit();
    });
    $("#community").change(function () {
        $("form").attr("action", "index.htm").submit();
    });
    $("#orgForm").change(function () {
        $("form").attr("action", "index.htm").submit();
    });
    $("#facility").change(function () {
        $("form").attr("action", "index.htm").submit();
    });
    $("#period").change(function () {
        $("form").attr("action", "index.htm").submit();
    });
</script>