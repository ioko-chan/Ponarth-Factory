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

    public String loadImage(MultipartFile file, String filename) {
        if (!file.isEmpty()) {
            try {
                new File(filePath).mkdirs();
                byte[] bytes = file.getBytes();
                String filePathName = filePath + "/" + filename + "." + file.getContentType().replace("image/", "");
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePathName)));
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили " + filename + " в " + filePathName + "-uploaded !" + file.getContentType();
            } catch (Exception e) {
                return "Вам не удалось загрузить " + filename + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + filename + " потому что файл пустой.";
        }

    }

    public String getPathImage() {
        return "";
    }
}
