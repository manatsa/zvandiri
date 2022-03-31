<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To Reports DashBoard Home</a><br/>
            <form:form modelAttribute="item" role="form">
                <table class="table">
                    <tbody>
                    <tr>
                        <c:if test="${userLevel == 'NATIONAL'}">
                            <td>
                                <div class="form-group">
                                    <label for="provinces">Region</label>
                                    <form:select path="provinces" size="15" class="form-control" id="provinces"
                                                 multiple="multiple">
                                        <form:option value="" label="--Select Item--"/>
                                        <form:options items="${provinces}" itemLabel="name" itemValue="id"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="provinces" class="alert-danger"/>
                                    </p>
                                </div>
                            </td>
                        </c:if>
                        <c:if test="${userLevel == 'NATIONAL' or userLevel == 'PROVINCE'}">
                            <td>
                                <div class="form-group">
                                    <label for="districts">District</label>
                                    <form:select path="districts" size="20" class="form-control" id="districts"
                                                 multiple="multiple">
                                        <form:option value="" label="--Select Item--"/>
                                        <form:options items="${districts}" itemLabel="name" itemValue="id"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="districts" class="alert-danger"/>
                                    </p>
                                </div>
                            </td>
                        </c:if>
                        <td>
                            <div class="form-group">
                                <label for="facilities">Facility</label>
                                <form:select path="facilities" size="25" class="form-control" id="facilities"
                                             multiple="multiple">
                                    <form:option value="" label="--Select Item--"/>
                                    <form:options items="${facilities}" itemLabel="name" itemValue="id"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="facilities"/>
                                </p>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label>Start Date</label>
                                <form:input path="startDate" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="startDate"/>
                                </p>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label>End Date</label>
                                <form:input path="endDate" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="endDate"/>
                                </p>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label>&nbsp;</label><br/>
                                <button class="btn btn-primary" type="submit">Export</button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form:form>
            <div class="row">
            </div>

        </div>
        <div class="panel-footer" style="text-align: right">

        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    $("#item").validate({
        rules: {
            province: {
                required: true
            }
        }
    });
</script>
