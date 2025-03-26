package com.obast.charer.sms.nikita;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.ISmsRecordData;
import com.obast.charer.sms.core.ISmsService;
import com.obast.charer.sms.core.SmsResult;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Component
public class NikitaISmsService implements ISmsService {

    String identifier = "Nikita";

    String sendUrl = "http://smspro.nikita.kg/api/message";

    NikitaSmsProperties properties;

    @Autowired
    private ISmsRecordData smsRecordData;

    @Override
    public void setProperties(String smsProperties) {
        try {
            properties = JSONObject.parseObject(smsProperties, NikitaSmsProperties.class);
        } catch (Exception e) {
            throw new BizException(ErrCode.PARSE_JSON_EXCEPTION);
        }
    }

    @Override
    public NikitaSmsProperties getProperties() {
        return properties;
    }

    @Override
    public SmsResult sendNotify(String id, String message, String phoneNumber) {

        NikitaSmsProperties nikitaSmsProperties = getProperties();

        String template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<message>" +
                "<login>%s</login>" +
                "<pwd>%s</pwd>" +
                "<id>%s</id>" +
                "<sender>%s</sender>" +
                "<text>%s</text>" +
                "<time>%s</time>" +
                "<phones>%s</phones>" +
                "<test>%s</test>" +
                "</message>";
        String phoneString = String.format("<phone>%s</phone>", phoneNumber);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String body = String.format(template,
                nikitaSmsProperties.getLogin(),
                nikitaSmsProperties.getPassword(),
                id,
                nikitaSmsProperties.getSender(),
                message,
                sdf.format(new Date()),
                phoneString,
                nikitaSmsProperties.getTest() ? 1 : 0
        );

        HttpRequest request = HttpRequest.post(sendUrl).body(body);
        //log.debug("[Nikita]请求内容: {}", request);
        HttpResponse response = request.execute();
        String responseBody = response.body();
        log.debug("[通知调试] Nikita 返回内容: {}", responseBody);

        if(responseBody == null) {
            throw new BizException(ErrCode.SMS_SEND_RESPONSE_EMPTY);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Map<String, String> result = new HashMap<>();
        try {
            byte[] bytes = responseBody.getBytes();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            DocumentBuilder builder =  factory.newDocumentBuilder();
            Document d = builder.parse(inputStream);
            NodeList sList = d.getElementsByTagName("response");
            for (int i = 0; i < sList.getLength(); i++) {
                Element element = (Element)sList.item(i);
                NodeList childNodes = element.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE){
                        result.put(childNodes.item(j).getNodeName(), childNodes.item(j).getFirstChild().getNodeValue());
                    }
                }
            }

        } catch (Exception e) {
            log.debug("[通知调试] Nikita 解析短信服务商返回消息异常 {}", e.getLocalizedMessage());
        }

        if(result.isEmpty()) {
            throw new BizException(ErrCode.SMS_SEND_RESPONSE_EMPTY);
        }

        if(!result.containsKey("status") || StringUtils.isBlank(result.get("status"))) {
            throw new BizException(ErrCode.SMS_SEND_RESPONSE_STATUS_EMPTY);
        }

        String status = result.get("status");
        //发送成功
        if((nikitaSmsProperties.getTest() && status.equals("11")) || (!nikitaSmsProperties.getTest() && status.equals("0"))) {
            return  SmsResult.success();
        }
        throw new BizException(ErrCode.SMS_SEND_FAIL, String.format("%s(%s)", ErrCode.SMS_SEND_FAIL.getValue(), status));
    }

    @Override
    public SmsResult sendVerificationCode(String id, String message, String phoneNumber) {

        NikitaSmsProperties nikitaSmsProperties = getProperties();

        String responseBody;
        try {

            String template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<message>" +
                    "<login>%s</login>" +
                    "<pwd>%s</pwd>" +
                    "<id>%s</id>" +
                    "<sender>%s</sender>" +
                    "<text>%s</text>" +
                    "<time>%s</time>" +
                    "<phones>%s</phones>" +
                    "<test>%s</test>" +
                    "</message>";
            String phoneString = String.format("<phone>%s</phone>", phoneNumber);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String body = String.format(template,
                    nikitaSmsProperties.getLogin(),
                    nikitaSmsProperties.getPassword(),
                    id,
                    nikitaSmsProperties.getSender(),
                    message,
                    sdf.format(new Date()),
                    phoneString,
                    nikitaSmsProperties.getTest() ? 1 : 0
            );

            HttpRequest request = HttpRequest.post(sendUrl).body(body);
            log.debug("请求内容: {}", request);
            HttpResponse response = request.execute();
            responseBody = response.body();
            log.debug("返回内容: {}", responseBody);
        } catch (Exception e) {
            log.error("短信发送异常, 消息: {}", e.getLocalizedMessage());
            throw new BizException(ErrCode.SMS_SEND_EXCEPTION, e.getLocalizedMessage());
        }

        if(responseBody == null) {
            throw new BizException(ErrCode.SMS_SEND_RESPONSE_EMPTY);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Map<String, String> result = new HashMap<>();
        try {
            byte[] bytes = responseBody.getBytes();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            DocumentBuilder builder =  factory.newDocumentBuilder();
            Document d = builder.parse(inputStream);
            NodeList sList = d.getElementsByTagName("response");
            for (int i = 0; i < sList.getLength(); i++) {
                Element element = (Element)sList.item(i);
                NodeList childNodes = element.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE){
                        result.put(childNodes.item(j).getNodeName(), childNodes.item(j).getFirstChild().getNodeValue());
                    }
                }
            }

        } catch (Exception e) {
            log.debug("解析短信服务商返回消息异常 {}", e.getLocalizedMessage());
        }

        if(result.isEmpty()) {
            throw new BizException(ErrCode.SMS_SEND_RESPONSE_EMPTY);
        }

        if(!result.containsKey("status") || StringUtils.isBlank(result.get("status"))) {
            throw new BizException(ErrCode.SMS_SEND_RESPONSE_STATUS_EMPTY);
        }

        String status = result.get("status");
        //发送成功
        if((nikitaSmsProperties.getTest() && status.equals("11")) || (!nikitaSmsProperties.getTest() && status.equals("0"))) {
            return  SmsResult.success();
        }
        throw new BizException(ErrCode.SMS_SEND_FAIL, String.format("%s(%s)", ErrCode.SMS_SEND_FAIL.getValue(), status));
    }
}