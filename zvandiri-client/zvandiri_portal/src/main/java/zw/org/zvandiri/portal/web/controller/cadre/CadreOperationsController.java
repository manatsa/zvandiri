package zw.org.zvandiri.portal.web.controller.cadre;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.Cadre;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.service.CadreService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.IAppTitle;

import javax.annotation.Resource;


@RequestMapping("cadre/operations/")
@Controller
public class CadreOperationsController implements IAppTitle {

    @Resource
    CadreService cadreService;
    @Resource
    FacilityService facilityService;
    @Resource
    DistrictService districtService;
    @Resource
    ProvinceService provinceService;


    @RequestMapping(value = "transfer", method = RequestMethod.GET)
    public String transferCadreGet(Model model, @RequestParam(required=true)  String id){
        if(id==null || id.isEmpty())
            throw new IllegalArgumentException("Cadre ID Cannot be null");
        Cadre cadre=cadreService.get(id);
        model.addAttribute("pageTitle", APP_PREFIX + " " + "Cadre Operations");
        model.addAttribute("cadre", cadre);
        model.addAttribute("item", cadre);
        model.addAttribute("facilities", facilityService.getOptByDistrict(cadre.getDistrict()));
        model.addAttribute("districts", districtService.getDistrictByProvince(cadre.getProvince()));
        model.addAttribute("provinces", provinceService.getAll());
        return "cadre/transfer";

    }

    @RequestMapping(value = "transfer", method = RequestMethod.POST)
    public String transferCadrePost(ModelMap map, @ModelAttribute("item") Cadre item, BindingResult result, @ModelAttribute("page") String page){
        map.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return "redirect:index.html?type=2&id=" + item.getId();
        }
        //map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.MESSAGE).build());
        Cadre cadre=cadreService.get(item.getId());
        cadre.setProvince(item.getProvince());
        cadre.setDistrict(item.getDistrict());
        cadre.setPrimaryClinic(item.getPrimaryClinic());
        cadreService.save(cadre);
        return "redirect:"+page+"/cadre/view?type=1&id="+item.getId();

    }

    @RequestMapping(value = "change-status", method = RequestMethod.GET)
    public String changeStatusCadreGet(Model model, @RequestParam(required=true)  String id){
        if(id==null || id.isEmpty())
            throw new IllegalArgumentException("Cadre ID Cannot be null");
        Cadre cadre=cadreService.get(id);
        System.err.println(cadre);
        model.addAttribute("cadre", cadre);
        model.addAttribute("pageTitle", APP_PREFIX + " " + "Cadre Operations");
        model.addAttribute("item", cadre);
        model.addAttribute("status", PatientChangeEvent.values());
        return "cadre/changeStatusForm";

    }

    @RequestMapping(value = "change-status", method = RequestMethod.POST)
    public String changeStatusCadrePost(ModelMap map, @ModelAttribute("item") Cadre item, BindingResult result, @ModelAttribute("page") String page){
        map.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return "redirect:index.html?type=2&id=" + item.getId();
        }
        //map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.MESSAGE).build());
        Cadre cadre=cadreService.get(item.getId());
        cadre.setStatus(item.getStatus());
        cadreService.save(cadre);
        return "redirect:"+page+"/cadre/view?type=1&id="+item.getId();

    }



}
