/*
 * Copyright 2016 Judge Muzinda.
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
package zw.org.zvandiri.portal.web.controller.patient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.util.dto.PatientFiles;
import zw.org.zvandiri.portal.web.controller.BaseController;

/**
 *
 * @author Judge Muiznda
 */
@Controller
@RequestMapping("/patient/dashboard")
public class UploadFilesController extends BaseController {
 
    @Resource
    private PatientService patientService;
    
    @RequestMapping(value = "/upload-patient-files", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam String id){
        Patient patient = patientService.get(id);
        model.addAttribute("pageTitle", APP_PREFIX+"Upload Patient Files");
        model.addAttribute("patient", patient);
        //model.addAttribute("item", new PatientFiles(patient));
        return "patient/fileUploadForm";
    }
    
    @RequestMapping(value = "/upload-patient-files", method = RequestMethod.POST)
    public String uploadFiles(@RequestParam("patient") String id ,
            @RequestParam("photo") String photo,
            @RequestParam("consent") String consent,
            @RequestParam("mhealth") String mhealth,
            @RequestParam("file") MultipartFile[] files){
        String [] names = {photo,consent,mhealth};
        PatientFiles item = new PatientFiles(patientService.get(id));
        item.setPhoto(names[0]);
        item.setConsentForm(names[1]);
        item.setMhealthForm(names[2]);
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String name = names[i];
            try {
                byte[] bytes = file.getBytes();
                String rootPath = File.separator+"home"+File.separator+".zvandiriappfiles";
                File dir = new File(rootPath);
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                try (BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile))) {
                    stream.write(bytes);
                }
                patientService.save(item.getInstance(item));
            } catch (Exception e) {
                System.out.println("********************************************************");
                System.out.println("The message :"+e.getMessage());
                return "redirect:profile.htm?type=5&id="+item.getPatient().getId();
            }
        }
        return "redirect:profile.htm?type=1&id="+item.getPatient().getId();
    }
}