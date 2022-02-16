<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <a href="../index.htm">Option Tables</a> | <a href="item.form">New Item</a> | <a href="item.list">Item List</a><br/><br/>
                <%@include file="../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item" action="item.form">
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Name</label>
                                <form:input path="name" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="name" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Region</label>
                                <form:select path="province" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${provinces}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="province" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>District</label>
                                <form:select path="district" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${districts}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="district" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Description</label>
                                <form:textarea path="description" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="description"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="${itemDelete}"><button class="btn btn-primary" type="button">Cancel</button></a>
                            </div>
                        </form:form>
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
           },
           province: {
               required: true
           },
           district: {
               required: true
           }
       } 
    });
</script>