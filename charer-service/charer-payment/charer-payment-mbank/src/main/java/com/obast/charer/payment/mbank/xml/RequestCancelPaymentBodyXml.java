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
public class RequestCancelPaymentBodyXml implements Serializable {
    @JacksonXmlProperty(localName = "SERVICE_ID" ,isAttribute = true)
    private String serviceId;

    @JacksonXmlProperty(localName = "PARAM1" ,isAttribute = true)
    private String param1;

    @JacksonXmlProperty(localName = "SUM" ,isAttribute = true)
    private String sum;

    @JacksonXmlProperty(localName = "REQUISITE1" ,isAttribute = true)
    private String requisite1;

    @JacksonXmlProperty(localName = "REQUISITE2" ,isAttribute = true)
    private String requisite2;

    @JacksonXmlProperty(localName = "REQUISITE3" ,isAttribute = true)
    private String requisite3;

    @JacksonXmlProperty(localName = "REQUISITE4" ,isAttribute = true)
    private String requisite4;

    @JacksonXmlProperty(localName = "REQUISITE5" ,isAttribute = true)
    private String requisite5;



}
