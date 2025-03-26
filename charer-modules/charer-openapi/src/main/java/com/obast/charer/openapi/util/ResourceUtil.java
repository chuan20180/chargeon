package com.obast.charer.openapi.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @ Author：chuan
 * @ Date：2024-10-20-15:30
 * @ Version：1.0
 * @ Description：ResourceUtil
 */
public class ResourceUtil {
    public static String getResourceText(String path) {
        StringBuilder template = new StringBuilder();
        try (InputStream inputStream = ResourceUtil.class.getResourceAsStream("/html/public.html")) {
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    template.append(line);
                }
            }
        } catch (Exception ignored) {}


        return template.toString();
    }
}
