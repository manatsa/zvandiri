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
			<div class="row">
				<div class="panel-footer" style="text-align: right">
					Export/ View As <a href="${page}${excelExport}"> <img
						src="<c:url value="/resources/images/excel.jpeg"/>" />
				</a>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<table id="tableList" class="display" cellspacing="0">
						<thead>
						<th>Name</th>
						<th>Age</th>
						<th>Gender</th>
						<th>Phone No.</th>
						<th>District</th>
						<th>Clinic</th>
						<th>Contact Date</th>
						<th>Current Care Level</th>
						<th>Place of Contact</th>
						<th>Previous Care Level</th>
						<th>Contacted By</th>
						</thead>
						<tfoot>
						<thead>
						<th>Name</th>
						<th>Age</th>
						<th>Gender</th>
						<th>Phone No.</th>
						<th>District</th>
						<th>Clinic</th>
						<th>Contact Date</th>
						<th>Current Care Level</th>
						<th>Place of Contact</th>
						<th>Previous Care Level</th>
						<th>Contacted By</th>
						</thead>
						</tfoot>
						<tbody>
						<c:forEach var="item" items="${items}">
							<tr>
								<td>${item.patient.name}</td>
								<td>${item.patient.age}</td>
								<td>${item.patient.gender.name}</td>
								<td>${item.patient.mobileNumber}</td>
								<td>${item.patient.primaryClinic.district.name}</td>
								<td>${item.patient.primaryClinic.name}</td>
								<td><spring:eval expression="item.contactDate" /></td>
								<td>${item.careLevelAfterAssessment.name}</td>
								<td>${item.location.name}</td>
								<td>${item.careLevel.name}</td>
								<td>${item.position.name}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
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

	$("#item").validate({
		rules: {
			startDate: {
				required: true
			},
			endDate: {
				required: true
			}
		}
	});
</script>
