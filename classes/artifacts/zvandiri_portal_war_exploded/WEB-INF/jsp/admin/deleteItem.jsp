<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <div class="${message.msgType}<c:if test="${!message.exist}"> hidden</c:if>">
                    ${message.message}
                </div>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item" action="item.delete">
                            <form:hidden path="id"/>
                            <div class="form-group">
                                <label>Name</label>
                                ${item.name}
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Delete</button>
                                <a href="${item.cancelRedirect}"><button class="btn btn-primary" type="button">Cancel</button></a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>