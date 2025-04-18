/*
 *
 *  * | Licensed 未经许可不能去掉「OPENIITA」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [OPENIITA]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.obast.charer.s3mock.util;

import com.obast.charer.s3mock.dto.AccessControlPolicy;
import com.obast.charer.s3mock.dto.Grantee;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 * Utility class with helper methods to serialize / deserialize JAXB annotated classes.
 */
public class XmlUtil {

  private XmlUtil() {
    // private constructor for utility classes
  }

  public static AccessControlPolicy deserializeJaxb(String toDeserialize)
      throws JAXBException, XMLStreamException {
    return deserializeJaxb(AccessControlPolicy.class, toDeserialize,
        AccessControlPolicy.class,
        Grantee.CanonicalUser.class,
        Grantee.Group.class,
        Grantee.AmazonCustomerByEmail.class);
  }

  public static <T> T deserializeJaxb(Class<T> clazz, String toDeserialize,
      Class<?>... additionalTypes)
      throws JAXBException, XMLStreamException {

    var xif = XMLInputFactory.newInstance();
    xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
    xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
    var reader = xif.createXMLStreamReader(new StringReader(toDeserialize));
    var jaxbContext = JAXBContext.newInstance(additionalTypes);
    var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    return jaxbUnmarshaller.unmarshal(reader, clazz).getValue();
  }

  public static String serializeJaxb(AccessControlPolicy toSerialize)
      throws JAXBException {
    return serializeJaxb(toSerialize, AccessControlPolicy.class,
        Grantee.CanonicalUser.class,
        Grantee.Group.class,
        Grantee.AmazonCustomerByEmail.class);
  }

  public static String serializeJaxb(Object toSerialize, Class<?>... additionalTypes)
      throws JAXBException {
    var jaxbContext = JAXBContext.newInstance(additionalTypes);
    var jaxbMarshaller = jaxbContext.createMarshaller();

    var writer = new StringWriter();
    jaxbMarshaller.marshal(toSerialize, writer);

    return writer.toString();
  }
}
