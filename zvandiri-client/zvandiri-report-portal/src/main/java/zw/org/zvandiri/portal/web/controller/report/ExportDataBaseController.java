/*
 * Copyright 2017 jmuzinda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zw.org.zvandiri.portal.web.controller.report;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.OfficeExportService;

/**
 *
 * @author jmuzinda
 */
@Controller
@RequestMapping("/report/export-database")
public class ExportDataBaseController extends BaseController {

	@Resource
	private OfficeExportService officeExportService;
	@Resource
	private ProvinceService provinceService;
	@Resource
	private DistrictService districtService;
	@Resource
	private FacilityService facilityService;

	public void setUpModel(ModelMap model, SearchDTO item) {
		item = getUserLevelObjectState(item);
		model.addAttribute("pageTitle", APP_PREFIX + "Export Database");
		model.addAttribute("provinces", provinceService.getAll());
		if (item.getProvince() != null) {
			model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
			if (item.getDistrict() != null) {
				model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
			}
		}
		model.addAttribute("excelExport",
				"/report/export-database/excel" + item.getQueryString(item.getInstance(item)));
		model.addAttribute("item", item);
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getExportDatabaseIndex(ModelMap model) {
		setUpModel(model, new SearchDTO());
		return "report/exportDatabase";
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DATA_CLERK') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
	public void getExcelExport(HttpServletResponse response, @ModelAttribute("item") SearchDTO dto) {
		
		dto = getUserLevelObjectState(dto);
		String name = DateUtil.getFriendlyFileName("Zvandiri_Database");
		forceDownLoadXLSX(officeExportService.exportDatabase(name, dto), name, response);
	}
	
}
