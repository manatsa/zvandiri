<%@include file="../template/header.jspf" %>
<style type="text/css">
    input[type="text"]{
        width: 130px;
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
                    <form:form commandName="item">
                        <form:hidden path="id"/>
                        <form:hidden path="version"/>
                        <%@include file="../template/entryTopPart.jspf" %><br/>
                        <%@include file="../template/entry/actionPlanningForm.jspf" %>
                    </form:form>
                </div>
            </div>
        </div>
        <div class="panel-footer">
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
        $("#facility").val('');
        $("#community").val('');
        $("form").attr("action", "loadcontextualform").submit();
    });
    $("#district").change(function () {
        $("#facility").val('');
        $("#community").val('');
        $("form").attr("action", "loadcontextualform").submit();
    });
    $("#community").change(function () {
        $("form").attr("action", "loadcontextualform").submit();
    });
    $("#organisation").change(function () {
        $("form").attr("action", "loadcontextualform").submit();
    });
    $("#orgForm").change(function () {
        $("form").attr("action", "loadcontextualform").submit();
    });
    $("#facility").change(function () {
        $("#community").val('');
        $("form").attr("action", "loadcontextualform").submit();
    });
    $("#period").change(function () {
        $("form").attr("action", "loadcontextualform").submit();
    });
    $("input[id*='when']").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd/mm/yy'
    });
</script>