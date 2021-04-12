<%@include file="template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <div class="${message.msgType}<c:if test="${!message.exist}"> hidden</c:if>">
                        <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                    ${message.message}
                    <a href="${referer}" class="alert-link">Go back</a>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="template/footer.jspf" %>