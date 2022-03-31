/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.portal.util;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Edward Zengeni
 */
public class UploadItem implements Serializable {

    private String name;
    private CommonsMultipartFile fileData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommonsMultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }
}
