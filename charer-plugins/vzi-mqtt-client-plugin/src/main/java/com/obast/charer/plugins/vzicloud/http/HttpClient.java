package com.obast.charer.plugins.vzicloud.http;

import com.obast.charer.plugins.vzicloud.util.PresignedUrl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
  protected String baseUrl;
  protected String accessKeyId;
  protected String accessKeySecret;

  public HttpClient(String baseUrl, String accessKeyId, String accessKeySecret) {
    this.baseUrl = baseUrl;
    this.accessKeyId = accessKeyId;
    this.accessKeySecret = accessKeySecret;
  }

  public URL generatePresignedUrl(HttpRequest request, Date expires, String accessKeyId, String accessKeySecret) {
    PresignedUrl presignedUrl = new PresignedUrl(request, this.baseUrl, accessKeyId, accessKeySecret)
            .setExpires(expires);
    return presignedUrl.generatePresignedUrl();
  }

  public HttpResponse doRequest(HttpRequest request) {
    String charset = request.getContentEncoding();
    String content = request.getBody();
    HashMap<String, String> header = request.getHeaders();

    HttpResponse response = new HttpResponse();

    DataOutputStream out = null;
    InputStream is = null;

    try {
      long expiresLong = System.currentTimeMillis();
      expiresLong += (60 * 1000);
      Date expires = new Date(expiresLong);
      URL url = generatePresignedUrl(request, expires, this.accessKeyId, this.accessKeySecret);

      System.out.println(url.toString());

      HttpURLConnection conn = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);

      // set timeout
      if (request.getConnectionTimeoutMillis() > 0) {
        conn.setConnectTimeout(request.getConnectionTimeoutMillis());

      }
      if (request.getSocketTimeoutMillis() > 0) {
        conn.setReadTimeout(request.getSocketTimeoutMillis());
      }

      conn.setDoOutput(true);
      conn.setRequestMethod(request.getHttpMethod().toString());

      // 添加header
      for (Map.Entry<String, String> entry : header.entrySet()) {
        conn.setRequestProperty(entry.getKey(), entry.getValue());
      }

      conn.connect();
      if (content.length() > 0) {
        out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes(charset));
        out.flush();
      }

      int statusCode = conn.getResponseCode();
      response.setHeader(conn.getHeaderFields());
      response.setStatus(statusCode);
      response.setCharset(charset);

      if (statusCode == 200) {
        is = conn.getInputStream();
      } else {
        is = conn.getErrorStream();
      }

      if (is != null) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
          outStream.write(buffer, 0, len);
        }
        response.setBody(outStream.toByteArray());
      }
      return response;

    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }
}
