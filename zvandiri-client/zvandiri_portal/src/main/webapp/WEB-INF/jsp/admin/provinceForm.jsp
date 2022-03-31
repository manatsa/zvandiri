<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <a href="../index.htm">Option Tables</a> | <a href="item.form">New Region</a> | <a href="item.list">Region
                List</a>
                <hr/>
                <%@include file="../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-10">
                        <%@include file="../template/nameDescForm.jspf" %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    $("form").validate({
        rules: {
            name: {
                required: true
            }
        }
    });
</script>