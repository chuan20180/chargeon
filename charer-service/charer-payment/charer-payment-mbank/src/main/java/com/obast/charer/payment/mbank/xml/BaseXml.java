package com.obast.charer.payment.mbank.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ Author：chuan
 * @ Date：2024-12-18-14:10
 * @ Version：1.0
 * @ Description：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "XML") //XML根节点名称
public class BaseXml<T> implements Serializable {

    @JacksonXmlProperty(localName = "HEAD")
    private HeadXml head;

    @JacksonXmlProperty(localName = "BODY")
    private T body;

    public BaseXml(T body) {
        this.body = body;
    }
}
