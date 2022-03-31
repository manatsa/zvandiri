<%@include file="template/header.jspf" %>
<style type="text/css">
    .removeelement {
        display: none;
    }
</style>
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
                        <div id="firstDiv" class="col-lg-4 ourdiv" style="border: 1px solid #ccc; background: green">
                            First Div
                        </div>
                        <div id="secondDiv" class="col-lg-4 ourdiv" style="border: 1px solid #ccc; background: red">
                            Second Div
                        </div>
                        <div id="thirdId" class="col-lg-4 ourdiv" style="border: 1px solid #ccc; background: yellow">
                            Third Div
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="template/footer.jspf" %>
<script type="text/javascript">
    $(function () {
        $(".ourdiv").click(function () {
            $currentElement = $(this);
            $currentElement.addClass("removeelement");
        });
        $("#thirdId").click(function () {
            $("div").removeClass("removeelement");
        });
    });
</script>