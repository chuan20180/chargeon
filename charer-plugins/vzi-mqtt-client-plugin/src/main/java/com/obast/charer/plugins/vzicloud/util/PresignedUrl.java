package com.obast.charer.plugins.vzicloud.util;

import com.obast.charer.plugins.vzicloud.http.Headers;
import com.obast.charer.plugins.vzicloud.http.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class PresignedUrl {
  private HttpRequest request;
  private String baseUrl;
  private String accessKeyId;
  private String accessKeySecret;
  private String expires;

  private static final String NEW_LINE = "\n";

  public PresignedUrl(HttpRequest request, String baseUrl, String accessKeyId, String accessKeySecret) {
    this.request = request;
    this.baseUrl = baseUrl;
    this.accessKeyId = accessKeyId;
    this.accessKeySecret = accessKeySecret;
  }

  public PresignedUrl setExpires(Date expires) {
    this.expires = String.valueOf(expires.getTime() / 1000L);
    return this;
  }

  private static String buildCanonicalizedResource(String resourcePath, Map<String, String> parameters) {
    if (!resourcePath.startsWith("/")) {
      throw new IllegalArgumentException("Resource path should start with slash character");
    }

    StringBuilder builder = new StringBuilder();
    builder.append(resourcePath);

    if (parameters != null) {
      String[] parameterNames = parameters.keySet().toArray(new String[parameters.size()]);
      Arrays.sort(parameterNames);

      char separater = '?';
      for (String paramName : parameterNames) {
        builder.append(separater);
        builder.append(paramName);
        String paramValue = parameters.get(paramName);
        if (paramValue != null) {
          builder.append("=").append(paramValue);
        }
        separater = '&';
      }
    }
    return builder.toString();
  }

  public String generateSignature() {
    try {
      String body = request.getBody();
      int bodyLength = body.getBytes(request.getContentEncoding()).length;
      request.addHeader(Headers.CONTENT_LENGTH, Integer.toString(bodyLength));

      HashMap<String, String> headers = request.getHeaders();
      String method = request.getHttpMethod().toString();

      String contentMD5 = "";
      String contentType = "";
      if (bodyLength > 0) {
        try {
          contentMD5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(body.getBytes(request.getContentEncoding())));
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
          return null;
        }

        if (headers.containsKey(Headers.CONTENT_TYPE)) {
          contentType = headers.get(Headers.CONTENT_TYPE);
        }
      }

      StringBuilder canonicalStringBuilder = new StringBuilder();
      canonicalStringBuilder.append(method).append(NEW_LINE);
      canonicalStringBuilder.append(contentMD5).append(NEW_LINE);
      canonicalStringBuilder.append(contentType).append(NEW_LINE);
      canonicalStringBuilder.append(this.expires).append(NEW_LINE);
      canonicalStringBuilder.append(buildCanonicalizedResource(this.request.getPath(), this.request.getParams()));
      String canonicalString = canonicalStringBuilder.toString();
      String signature = ServiceSignature.create().computeSignature(this.accessKeySecret, canonicalString);
      return signature;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return "";
    }
  }

  public URL generatePresignedUrl() {
    String signature = generateSignature();
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("expires", this.expires);
    params.put("accesskey_id", this.accessKeyId);
    params.put("signature", signature);
    params.putAll(this.request.getParams());

    String queryString = HttpUtil.paramToQueryString(params, this.request.getContentEncoding());
    String tempUrl = this.baseUrl + this.request.getPath();
    int pos = tempUrl.indexOf("?");
    if(pos != -1) {
      tempUrl = tempUrl.substring(0, pos);
    }
    String url = tempUrl + "?" + queryString;

    try {
      return new URL(url);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
