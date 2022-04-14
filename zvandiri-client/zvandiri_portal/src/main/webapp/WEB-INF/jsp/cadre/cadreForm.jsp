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
                    <div class="col-lg-10">
                        <div class="panel panel-default">                            
<%--                            <%@include file="../template/dashboard/patientProfile.jspf" %>--%>
                        </div>
                    </div>
                </div>
<%--                <a href="${page}/cadre/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back To ${patient.name} Dashboard</a><br/><br/>--%>
                <div class="row">

                    <div class="col-lg-10">
                        <form:form commandName="item" id="cadreForm" action="${formAction}">
                            <form:hidden path="patientId" value="${item.patientId}"/>
                            <%@include file="../template/formState.jspf" %>

                            <div class="form-group sec-own-mobile">
                                <label>Cadre Type</label>
                                <form:select path="caderType" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${caderTypes}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="caderType" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group">
                                <label>First Name</label>
                                <form:input path="firstName" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="firstName" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group">
                                <label>Last Name</label>
                                <form:input path="lastName" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="lastName" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group sec-own-mobile">
                                <label>Gender</label>
                                <form:select path="gender" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${gender}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="gender" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group">
                                <label>Address</label>
                                <form:input path="address" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="address" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group">
                                <label>Mobile Number</label>
                                <form:input path="mobileNumber" class="form-control word-case"/>
                                <p class="help-block">
                                    <form:errors path="mobileNumber" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group sec-own-mobile">
                                <label>Date Of Birth</label>
                                <form:input  path="dateOfBirth"  class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateOfBirth" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group sec-own-mobile">
                                <label>Cadre Status</label>
                                <form:select path="status" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${status}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="status" class="alert-danger"/>
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
                                <label>Primary Clinic District</label>
                                <form:select path="district" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${districts}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="district" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Primary Clinic</label>
                                <form:select path="primaryClinic" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${facilities}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="primaryClinic" class="alert-danger"/>
                                </p>
                            </div>

                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="${page}/cadre/view?id=${cadre.id}"><button class="btn btn-primary" type="button">Cancel</button></a>
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
    $("#catPhoneForm").validate({
        rules: {
            msisdn1: {
                required: true
            },
            imei1: {
                required: true
            },
            phoneMake: {
                required: true
            },
            phoneModel: {
                required: true
            },

        }
    });

    $("#caderType").change(function () {
        var type=$("#caderType").val()
        if(type && (type==1 || type==2)){
            window.location="/zvandiri/patient/index.htm?type=8"
        }
    });
</script>

<%--
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
                    <div class="col-lg-10">
                        <div class="panel panel-default">
                            &lt;%&ndash;                            <%@include file="../template/dashboard/patientProfile.jspf" %>&ndash;%&gt;
                        </div>
                    </div>
                </div>
                &lt;%&ndash;                <a href="${page}/cadre/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back To ${patient.name} Dashboard</a><br/><br/>&ndash;%&gt;
                <div class="row">

                    <div class="col-lg-10">
                        <form:form commandName="item" id="cadreForm" action="${formAction}">
                            <form:hidden path="patientId" value="${item.patientId}"/>
                            &lt;%&ndash;                            <form:hidden path="id" value="${item.id!=null? item.id:null}"/>&ndash;%&gt;
                            <%@include file="../template/formState.jspf" %>
                            <table>
                                <tr>Cadre Personal Information</tr>
                                <tr>
                                    <td>
                                        <div class="form-group">
                                            <label>First Name</label>
                                            <form:input path="firstName" class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="firstName" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label>Last Name</label>
                                            <form:input path="lastName" class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="lastName" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="form-group sec-own-mobile">
                                            <label>Gender</label>
                                            <form:select path="gender" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${gender}" itemValue="code" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="gender" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group sec-own-mobile">
                                            <label>Date Of Birth</label>
                                            <form:input  path="dateOfBirth"  class="form-control general"/>
                                            <p class="help-block">
                                                <form:errors path="dateOfBirth" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="form-group">
                                            <label>Address</label>
                                            <form:input path="address" class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="address" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label>Mobile Number</label>
                                            <form:input path="mobileNumber" class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="mobileNumber" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="form-group sec-own-mobile">
                                            <label>Cadre Type</label>
                                            <form:select path="caderType" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${caderTypes}" itemValue="code" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="caderType" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group sec-own-mobile">
                                            <label>Cadre Status</label>
                                            <form:select path="status" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${status}" itemValue="code" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="status" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
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
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label>Primary Clinic District</label>
                                            <form:select path="district" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${districts}" itemValue="id" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="district" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="form-group">
                                            <label>Primary Clinic</label>
                                            <form:select path="primaryClinic" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${facilities}" itemValue="id" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="primaryClinic" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label>Support Group</label>
                                            <form:select path="supportGroup" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${supportGroups}" itemValue="id" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="supportGroup" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>Mobile Phone Details</tr>
                                <tr>
                                    <td>
                                        <div class="form-group">
                                            <label>Mobile Phone Make</label>
                                            <form:input path="phoneMake" class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="phoneMake" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label>Mobile Phone Model</label>
                                            <form:input path="phoneModel" class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="phoneModel" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="form-group">
                                            <label>Phone Number 1</label>
                                            <form:input path="msisdn1" class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="msisdn1" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label>Phone Number 2</label>
                                            <form:input path="msisdn2" class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="msisdn2" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="form-group">
                                            <label>Imei Number 1</label>
                                            <form:input path="imei1"  class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="imei1" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label>Imei Number 2</label>
                                            <form:input path="imei2" class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="imei2" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="form-group sec-own-mobile">
                                            <label>Phone Condition</label>
                                            <form:select path="phoneCondition" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${phoneCondition}" itemValue="code" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="phoneCondition" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group sec-own-mobile">
                                            <label>Phone Status</label>
                                            <form:select path="phoneStatus" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${phoneStatus}" itemValue="code" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="phoneStatus" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="form-group sec-own-mobile">
                                            <label>Date Issued</label>
                                            <form:input  path="dateIssued"  class="form-control general"/>
                                            <p class="help-block">
                                                <form:errors path="dateIssued" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group sec-own-mobile">
                                            <label>Date Recovered</label>
                                            <form:input  path="dateRecovered"  class="form-control general"/>
                                            <p class="help-block">
                                                <form:errors path="dateRecovered" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="form-group">
                                            <label>Phone Issues</label>
                                            <form:textarea  path="phoneIssues" size="6" class="form-control word-case"/>
                                            <p class="help-block">
                                                <form:errors path="phoneIssues" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </td>
                                    <td>

                                    </td>
                                </tr>
                                <tr>
                                    <td>

                                    </td>
                                    <td>

                                    </td>
                                </tr>
                                <tr>
                                    <td>

                                    </td>
                                    <td>

                                    </td>
                                </tr>
                                <tr>
                                    <td>

                                    </td>
                                    <td>

                                    </td>
                                </tr>
                            </table>




                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="${page}/cadre/view?id=${cadre.id}"><button class="btn btn-primary" type="button">Cancel</button></a>
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
    $("#catPhoneForm").validate({
        rules: {
            msisdn1: {
                required: true
            },
            imei1: {
                required: true
            },
            phoneMake: {
                required: true
            },
            phoneModel: {
                required: true
            },

        }
    });
</script>--%>
