<%@include file="../template/header.jspf" %>
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
                        <%@include file="../template/entryMediaTopPart.jspf" %>
                        <br/>
                        <%@include file="../template/entry/mediaMonitoringFormFragment.jspf" %>
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
    $("#interviewDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd/mm/yy'
    });
</script>