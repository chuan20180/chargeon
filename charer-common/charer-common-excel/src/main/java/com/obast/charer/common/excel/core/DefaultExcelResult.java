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

package com.obast.charer.common.excel.core;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认excel返回对象
 *
 * @author Yjoioooo
 * @author Lion Li
 */
public class DefaultExcelResult<T> implements ExcelResult<T> {

    /**
     * 数据对象list
     */
    @Setter
    private List<T> list;

    /**
     * 错误信息列表
     */
    @Setter
    private List<String> errorList;

    public DefaultExcelResult() {
        this.list = new ArrayList<>();
        this.errorList = new ArrayList<>();
    }

    public DefaultExcelResult(List<T> list, List<String> errorList) {
        this.list = list;
        this.errorList = errorList;
    }

    public DefaultExcelResult(ExcelResult<T> excelResult) {
        this.list = excelResult.getList();
        this.errorList = excelResult.getErrorList();
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public List<String> getErrorList() {
        return errorList;
    }

    /**
     * 获取导入回执
     *
     * @return 导入回执
     */
    @Override
    public String getAnalysis() {
        int successCount = list.size();
        int errorCount = errorList.size();
        if (successCount == 0) {
            return "读取失败，未解析到数据";
        } else {
            if (errorCount == 0) {
                return CharSequenceUtil.format("恭喜您，全部读取成功！共{}条", successCount);
            } else {
                return "";
            }
        }
    }
}
