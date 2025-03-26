package com.obast.charer.plugins.vzicloud.service;

import com.obast.charer.plugins.vzicloud.http.HttpClient;

import com.obast.charer.plugins.vzicloud.http.HttpMethod;
import com.obast.charer.plugins.vzicloud.http.HttpRequest;
import com.obast.charer.plugins.vzicloud.http.HttpResponse;
import com.obast.charer.plugins.vzicloud.config.VziConfig;
import cn.hutool.core.codec.Base64Encoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@Component
@Data
public class Downloader {
    public static String downloadImage(VziConfig config, String basePath, String imagePath) {
        HttpClient client = new HttpClient(config.getBaseUrl(), config.getAccesskeyId(), config.getAccesskeySecret());
        HttpRequest request = new HttpRequest(HttpMethod.GET, basePath + imagePath);
        request.addParam("timeout", "300");
        HttpResponse response = client.doRequest(request);
        int status = response.getStatus();
        if(status != 200) {
            log.error("初始化 vzi 参数失败, 返回状态异常 {} {}", response.getStatus(), response.getBodyStr());
            return null;
        }
        return Base64Encoder.encode(response.getBody());
    }

    public static String getFileName(String imagePath) {
        Path basePath = Paths.get(imagePath);
        return basePath.getFileName().toString();
    }

    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
