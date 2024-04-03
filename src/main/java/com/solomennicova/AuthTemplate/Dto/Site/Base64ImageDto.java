package com.solomennicova.AuthTemplate.Dto.Site;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import java.io.InputStream;

@Data
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@AllArgsConstructor
public class Base64ImageDto {

    private String base64Str;

    private String mimeType;

    private String filename;
}
