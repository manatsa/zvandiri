<%@include file="../template/header.jspf"%>
<form:form commandName="item">
	<legend style="color: green">Reports Dash Board</legend>

	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">View and Export Aggregate Reports</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table width="100%" class="table table-bordered">
						<tbody>
							<tr>
								<td><sec:authorize
										access="
                                                   hasRole('ROLE_ADMINISTRATOR') or 
                                                   hasRole('ROLE_DATA_CLERK') or 
                                                   hasRole('ROLE_M_AND_E_OFFICER') or
                                                   hasRole('ROLE_HOD_M_AND_E')
                                                   ">
										<a href="${page}/report/pivot/patient/index.htm">Client
											Pivot Report</a>
									</sec:authorize></td>
								<td><a href="${page}/report/pivot/contact/index.htm">Contact
										Pivot Reports</a></td>
							</tr>
							<tr>
								<td><a
									href="${page}/report/pivot/patient/patients-exiting-program.htm">Client
										Exiting Program Pivot Report</a></td>
								<td><sec:authorize
										access="
                                                   hasRole('ROLE_ADMINISTRATOR') or 
                                                   hasRole('ROLE_DATA_CLERK') or 
                                                   hasRole('ROLE_M_AND_E_OFFICER') or
                                                   hasRole('ROLE_HOD_M_AND_E')
                                                   ">
										<a href="${page}/report/export-database/index.htm">Export
											Database</a>
									</sec:authorize></td>
							</tr>
							<tr>
								<td><a
										href="${page}/report/new/contact/detailed.htm">New Contact Detailed Report</a></td>
								<td><sec:authorize
										access="
                                                   hasRole('ROLE_ADMINISTRATOR') or
                                                   hasRole('ROLE_DATA_CLERK') or
                                                   hasRole('ROLE_M_AND_E_OFFICER') or
                                                   hasRole('ROLE_HOD_M_AND_E')
                                                   ">
									<a href="${page}/report/export-districts-databases/index.htm">Export
										Database By Districts</a>
								</sec:authorize></td>
							</tr>
							<tr>
								<td><sec:authorize
										access="
                                                   hasRole('ROLE_ADMINISTRATOR') or 
                                                   hasRole('ROLE_ZM') or 
                                                   hasRole('ROLE_M_AND_E_OFFICER') or
                                                   hasRole('ROLE_HOD_M_AND_E') or 
                                                   hasRole('ROLE_MANAGEMENT')
                                                   ">
										<a href="${page}/report/problem/problem-report.htm">Combined
											Statistical Report</a>
									</sec:authorize></td>
								<sec:authorize
									access="
                                               hasRole('ROLE_ADMINISTRATOR') or 
                                               hasRole('ROLE_ZM') or 
                                               hasRole('ROLE_M_AND_E_OFFICER') or
                                               hasRole('ROLE_HOD_M_AND_E')
                                               ">
									<td><a href="${page}/report/referral/detailed.htm">Referral
											Detailed Report</a></td>
								</sec:authorize>
							</tr>
							<tr>
								<td><a href="${page}/report/contact/detailed.htm">Contact
										Detailed Report</a></td>
								<td><a
									href="${page}/report/aggregate/contact-carelevel-healthcenter.htm">Contact
										Care Level Cross Tabulation (Health Center)</a></td>
							</tr>
							<tr>
								<td><a href="${page}/report/aggregate/data-entry.htm">Combined
										Data Entry Report</a></td>
								<td><a href="${page}/report/cat/detailed.htm">CATS
										Detailed Report</a></td>
							</tr>
							<tr>
								<td><a href="${page}/report/detailed/range.htm">Client
										Detailed Report</a></td>
								<td><a
									href="${page}/report/test-results/index.htm?type=viral-load">Viral
										Load Results Report</a></td>
							</tr>
							<tr>
								<td><a href="${page}/report/hiv-self-testing/detailed.htm">HIV
										Self Testing Report</a></td>
								<td><a href="${page}/report/detailed/hei.htm">HEI
										Report</a></td>
							</tr>
							<tr>
								<td><a href="${page}/report/tb-screening/screening">TB
										Screening/IPT Report</a>
								<td><a
									href="${page}/report/test-results/index.htm?type=cd4-count">CD4
										Count Results Report</a></td>
								</td>
							</tr>
							<tr>
								<td><a href="${page}/report/mortality/range.htm">Mortality
										Report</a>
								<td><a href="${page}/report/morbidity/range.htm">Morbidity
										Report</a></td>
								</td>
							</tr>
							<tr>
								<td><a href="${page}/report/uncontacted/range.htm">Uncontacted
										Clients Report</a></td>
								<td><a href="${page}/report/mental-health/range.htm">Mental
										Health Screening Report</a></td>
							</tr>
							<tr>
								<td><a href="${page}/report/unique/contacted/range.htm">Unique
										Contacted Clients Report</a></td>
								<td><a href="${page}/report/arvhist/range.htm">ARV_History
										Report</a></td>
							</tr>
							<tr>
								<td><a href="${page}/report/last-contacted/range.htm">Last Client Contact Report</a></td>
								<td><a href="${page}/report/invalidvl/range.htm?type=viral-load">Invalid VL Report</a></td>
							</tr>

							<tr>
								<td><a href="${page}/report/mobile/phone/range.htm">Cadres Mobile Phones Report</a></td>
								<td><a href="${page}/report/bicycle/range.htm">Cadres Bicycles Report</a></td>
							</tr>
							<tr>
								<td><a href="${page}/report/cadre/range.htm">Cadres Tools Report</a></td>
								<td><sec:authorize
										access="
                                                   hasRole('ROLE_ADMINISTRATOR') or
                                                   hasRole('ROLE_DATA_CLERK') or
                                                   hasRole('ROLE_M_AND_E_OFFICER') or
                                                   hasRole('ROLE_HOD_M_AND_E')
                                                   ">
									<a href="${page}/report/hierarchical-database-export/index.htm">Hierarchical Database Export</a>
								</sec:authorize></td>
							</tr>


							<tr>
								<th colspan="2">Visual Reports</th>
							</tr>
							<tr>
								<td><a href="${page}/report/graphs/contact-distribution">Number
										Of Contacts Past 6 Months</a></td>
								<td><a href="${page}/report/graphs/contact-level-of-care">Contact
										Trends By Care Level</a></td>
							</tr>
							<tr>
								<td><a
									href="${page}/report/graphs/patient-gender-distribution">Client
										Distribution By Gender</a></td>
								<td><a
									href="${page}/report/graphs/patient-age-group-distribution.htm">Client
										Distribution By Age Group</a></td>
							</tr>
							<tr>
								<td><a
									href="${page}/report/graphs/patient-status-distribution">Client
										Distribution By Status</a></td>
								<td><a href="${page}/report/graphs/referral-distribution">Number
										of External Client Referrals Past 6 Months</a></td>
							</tr>
							<tr>
								<td><a
									href="${page}/report/graphs/contact-care-level-distribution">Contact
										Distribution By Care Level</a></td>
								<td><a
									href="${page}/report/graphs/patient-contact-distribution">Client
										Distribution By Contact</a></td>
							</tr>
							<tr>
								<td><a
									href="${page}/report/graphs/patient-viral-load-distribution">Viral
										Load Distribution</a></td>
								<td><a
									href="${page}/report/graphs/patient-viral-suppression-distribution">Viral
										Load Suppression</a></td>
							</tr>
							<tr>
								<td><a
									href="${page}/report/graphs/tb-screening-treatment-status">TB
										Screening By Treatment Status</a></td>
								<td><a
									href="${page}/report/graphs/tb-screening-treatment-outcome">TB
										Screening By Treatment Outcome</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</form:form>
<%@include file="../template/footer.jspf"%>
