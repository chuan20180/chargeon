package com.obast.charer.payment.mbank.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class ResPostPaymentBodyXml implements Serializable {
    @JacksonXmlProperty(localName = "STATUS" ,isAttribute = true)
    private String status;

    @JacksonXmlProperty(localName = "SUM" ,isAttribute = true)
    private String sum;

    @JacksonXmlProperty(localName = "PENNY" ,isAttribute = true)
    private String penny;

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
