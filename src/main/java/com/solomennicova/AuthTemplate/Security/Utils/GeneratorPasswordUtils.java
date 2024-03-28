package com.solomennicova.AuthTemplate.Security.Utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class GeneratorPasswordUtils {

    public String generatePassword(){
        String generatedString = RandomStringUtils.randomAlphanumeric(10);
        System.out.println(generatedString);
        return generatedString;
    }
}
