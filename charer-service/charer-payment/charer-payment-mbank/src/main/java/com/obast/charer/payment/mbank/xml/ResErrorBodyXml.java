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
public class ResErrorBodyXml implements Serializable {
    @JacksonXmlProperty(localName = "STATUS" ,isAttribute = true)
    private String status;

    @JacksonXmlProperty(localName = "ERR_MSG" ,isAttribute = true)
    private String errMsg;

}
