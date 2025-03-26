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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HeadXml implements Serializable {

    @JacksonXmlProperty(localName = "DTS", isAttribute = true)
    private String DTS;

    @JacksonXmlProperty(localName = "QM",isAttribute = true)
    private String QM;

    @JacksonXmlProperty(localName = "QID",isAttribute = true)
    private String QID;

    @JacksonXmlProperty(localName = "OP",isAttribute = true)
    private String OP;

}
