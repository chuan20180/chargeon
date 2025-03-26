package com.obast.charer.plugins.vzicloud.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
  private HashMap<String, String> headers;
  private HashMap<String, String> params;
  private String body = "";
  private String path;
  private HttpMethod httpMethod;
  private String contentEncoding;
  private int connectionTimeoutMillis;
  private int socketTimeoutMillis;

  public HttpRequest( HttpMethod method, String path) {
    this.headers = new HashMap<String, String>();
    this.params = new HashMap<String, String>();
    this.httpMethod = method;
    this.contentEncoding = HttpCharacterEncoding.DEFAULT_ENCODING;
    this.path = path;
  }

  public int getConnectionTimeoutMillis() {
    return this.connectionTimeoutMillis;
  }

  public void setConnectionTimeoutMillis(int connectionTimeoutMillis) {
    this.connectionTimeoutMillis = connectionTimeoutMillis;
  }

  public int getSocketTimeoutMillis() {
    return this.socketTimeoutMillis;
  }

  public void setSocketTimeoutMillis(int socketTimeoutMillis) {
    this.socketTimeoutMillis = socketTimeoutMillis;
  }

  public String getContentEncoding() {
    return this.contentEncoding;
  }

  public void setContentEncoding(String contentEncoding) {
    this.contentEncoding = contentEncoding;
  }

  public void addHeader(String key, String value) {
    this.headers.put(key, value);
    if (key.equals(Headers.CONTENT_ENCODING)) {
      this.contentEncoding = value;
    }
  }

  public void addParam(String key, String value) {
    this.params.put(key, value);
  }

  public HashMap<String, String> getParams() {
    return this.params;
  }

  public String getParamStr() {
    StringBuffer buffer = new StringBuffer();
    for (Map.Entry<String, String> entry : this.params.entrySet()) {
      buffer.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
    }
    if (buffer.length() > 0) {
      buffer.deleteCharAt(buffer.length() - 1);
    }
    return buffer.toString();
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setJsonBody(String body) {
    this.body = body;
    this.headers.put(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
  }

  public void setParams(HashMap<String, String> params) {
    this.params = params;
  }

  public HashMap<String, String> getHeaders() {
    return this.headers;
  }

  public void setHeaders(HashMap<String, String> headers) {
    this.headers = headers;
  }

  public String getPath() {
    return this.path;
  }

  public HttpMethod getHttpMethod() {
    return this.httpMethod;
  }

  public void setHttpMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
  }
}
