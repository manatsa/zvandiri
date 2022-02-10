<%@include file="../template/header.jspf"%>
<div class="col-lg-12">
	<div class="panel panel-default">
		<div class="panel-heading">
			${pageTitle}
		</div>
		<div class="panel-body">
			<a href="${page}/report/index.htm">&DoubleLeftArrow; Back To
				Reports DashBoard Home</a><br />
			<%@include file="../template/contactSearchFragment.jspf"%>
			<div class="panel-footer" style="text-align: right">
				Export/ View As <a href="${page}${excelExport}"> <img
					src="<c:url value="/resources/images/excel.jpeg"/>" />
			</a>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<table id="tableList" class="display" cellspacing="0">
						<thead>
                            <th>OI/ART Number</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Age</th>
                            <th>Gender</th>
                            <th>Phone No.</th>
                            <th>Second Phone</th>
                            <th>Address</th>
                            <th>Second Address</th>
                            <th>IsCATS</th>
                            <th>Province</th>
                            <th>District</th>
                            <th>Clinic</th>
						</thead>
						<tfoot>
                            <th>OI/ART Number</th>
							<th>First Name</th>
                            <th>Last Name</th>
							<th>Age</th>
							<th>Gender</th>
							<th>Phone No.</th>
                            <th>Second Phone</th>
                            <th>Address</th>
                            <th>Second Address</th>
                            <th>IsCATS</th>
                            <th>Province</th>
							<th>District</th>
							<th>Clinic</th>

						</tfoot>
						<tbody>
							<c:forEach var="item" items="${items}">
								<tr>
                                    <td>${item.oINumber}</td>
									<td>${item.firstName}</td>
                                    <td>${item.lastName}</td>
									<td>${item.age}</td>
									<td>${item.gender.name}</td>
									<td>${item.mobileNumber}</td>
                                    <td>${item.secondaryMobileNumber}</td>
                                    <td>${item.address}</td>
                                    <td>${item.address1}</td>
                                    <td>${item.address}</td>
                                    <td>${item.primaryClinic.district.province.name}</td>
									<td>${item.primaryClinic.district.name}</td>
									<td>${item.primaryClinic.name}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

		<div class="panel-footer" style="text-align: right">
			Export/ View As <a href="${page}${excelExport}"> <img
				src="<c:url value="/resources/images/excel.jpeg"/>" />
			</a>
		</div>
	</div>
</div>
<%@include file="../template/footer.jspf"%>
<script type="text/javascript">
	$(".sidebar-nav").addClass("custom-side-bar-ref");
	$("#page-wrapper").addClass("main-wrp");
	// ensire toggle side bar is pointing right
	$("span.toggle-span").addClass("fa-long-arrow-right");
	$("span.toggle-span").removeClass("fa-long-arrow-left");
</script>
