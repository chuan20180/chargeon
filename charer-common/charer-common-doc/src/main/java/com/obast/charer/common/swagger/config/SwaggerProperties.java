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

package com.obast.charer.common.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 石恒
 * @Date: 2023/5/6 22:26
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    private Boolean enabled;
    private String title; //标题
    private String group; //自定义组名
    private String description = "在线文档"; //描述
    private String version = "1.0"; //版本
    private Contact contact = new Contact(); //联系人
    private String basePackage = "com.example"; //swagger会解析的包路径
    private List<String> basePath = new ArrayList<>(); //swagger会解析的url规则
    private List<String> excludePath = new ArrayList<>();//在basePath基础上需要排除的url规则
    private Map<String, DocketInfo> docket = new LinkedHashMap<>(); //分组文档
    public String getGroup() {
        if (group == null || "".equals(group)) {
            return title;
        }
        return group;
    }
    @Data
    public static class DocketInfo {
        private String title = "在线文档"; //标题
        private String group = ""; //自定义组名
        private String description = "在线文档"; //描述
        private String version = "1.0"; //版本
        private Contact contact = new Contact(); //联系人
        private String basePackage = "com.example"; //swagger会解析的包路径
        private List<String> basePath = new ArrayList<>(); //swagger会解析的url规则
        private List<String> excludePath = new ArrayList<>();//在basePath基础上需要排除的url
        public String getGroup() {
            if (group == null || "".equals(group)) {
                return title;
            }
            return group;
        }
    }
    @Data
    public static class Contact {
        private String name = ""; //联系人
        private String url = ""; //联系人url
        private String email = ""; //联系人email
    }
}
