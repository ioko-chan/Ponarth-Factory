package com.solomennicova.AuthTemplate.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class StoreService {

    @Value("${store.path}")
    private String filePath;

    public String loadImage(MultipartFile file, String filename){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath + filename + "-uploaded" + file.getContentType())));
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили " + filePath + filename + " в " + filename + "-uploaded !" + file.getContentType();
            } catch (Exception e) {
                return "Вам не удалось загрузить " + filePath + filename + file.getContentType() + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + filePath + filename + file.getContentType() + " потому что файл пустой.";
        }

    }

    public String getPathImage(){
        return "";
    }
}
