package com.solomennicova.AuthTemplate.Service;

import com.solomennicova.AuthTemplate.Exception.DontImageException;
import com.solomennicova.AuthTemplate.Exception.ImageDontLoad;
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

    public String loadImage(MultipartFile file, String filename) throws DontImageException, ImageDontLoad {
        if (!file.isEmpty()) {
            if(file.getContentType().contains("image")) {
                try {
                    new File(filePath).mkdirs();
                    byte[] bytes = file.getBytes();
                    String filePathName = filePath + "/" + filename + "." + file.getContentType().replace("image/", "");
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File(filePathName)));
                    stream.write(bytes);
                    stream.close();
                    return filePathName;
                } catch (Exception e) {
                    throw new ImageDontLoad(e.getMessage());
                }
            }
            else {
                throw new DontImageException("Need load image, a give " + file.getContentType());
            }
        } else {
            return "Вам не удалось загрузить " + filename + " потому что файл пустой.";
        }

    }

    public String getPathImage() {
        return "";
    }
}
