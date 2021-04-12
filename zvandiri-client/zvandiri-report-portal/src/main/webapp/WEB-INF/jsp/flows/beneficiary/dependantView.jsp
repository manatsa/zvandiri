<%@include file="../../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="../../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-10">
                        <div class="panel panel-default">                            
                            <%@include file="profileFragment.jspf" %>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-10">
                        <a href="district.form">New District</a><br/>
                        <table id="tableList" class="display" cellspacing="0">
                            <thead>
                            <th>Name</th>
                            <th>Relationship</th>
                            <th>Gender</th>
                            <th>Age</th>
                            <th>Mobile Number</th>
                            <th>&nbsp</th>
                            </thead>
                            <tfoot>
                            <th>Name</th>
                            <th>Relationship</th>
                            <th>Gender</th>
                            <th>Age</th>
                            <th>Mobile Number</th>
                            <th>&nbsp</th>
                            </tfoot>
                            <tbody>
                                <c:forEach var="depe" items="${dependents}" >
                                    <tr>
                                        <td>${depe.name}</td>
                                        <td>${depe.relationship.name}</td>
                                        <td>${depe.gender.name}</td>
                                        <td>${depe.age}</td>
                                        <td>${depe.mobileNumber}</td>
                                        <td>        
                                            <form:form>
                                                <input type="hidden" name="id" id="id" value="${depe.id}" />
                                                <button  type="submit" name="_eventId_updateEmployeeQualification">Update</button>
                                                <button  type="submit" onclick="return  confirm('Are you sure you want to permanently delete this record?');" name="_eventId_deletePatientDependant">Delete</button>
                                            </form:form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../../template/footer.jspf" %>