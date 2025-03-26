package com.obast.charer.payment.mbank.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ Author：chuan
 * @ Date：2024-12-18-14:10
 * @ Version：1.0
 * @ Description：
 */
@Data
@ToString
public class RequestMakePaymentBodyXml implements Serializable {
    @JacksonXmlProperty(localName = "SERVICE_ID" ,isAttribute = true)
    private String serviceId;

    @JacksonXmlProperty(localName = "PARAM1" ,isAttribute = true)
    private String param1;

    @JacksonXmlProperty(localName = "SUM" ,isAttribute = true)
    private String sum;
}
